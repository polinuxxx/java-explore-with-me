package ru.practicum.service.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mapper.EventRequestConverter;
import ru.practicum.model.AbstractEntity;
import ru.practicum.model.Event;
import ru.practicum.model.EventRequest;
import ru.practicum.model.EventRequestStatus;
import ru.practicum.model.EventStatus;
import ru.practicum.model.User;
import ru.practicum.model.projection.EventRequestProjection;
import ru.practicum.repo.event.EventRepository;
import ru.practicum.repo.EventRequestRepository;
import ru.practicum.repo.UserRepository;

/**
 * Реализация сервиса для {@link EventRequest}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EventRequestServiceImpl implements EventRequestService {
    private final EventRequestRepository eventRequestRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final EventRequestConverter eventRequestConverter;

    @Override
    @Transactional
    public EventRequest create(Long userId, Long eventId) {
        log.info("Создание заявки на событие с id = {} пользователем с id = {}", eventId, userId);

        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь по id = " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено событие по id = " + eventId));

        if (userId.equals(event.getInitiator().getId())) {
            throw new EntityExistsException("Невозможно добавить заявку на участие в своем событии");
        }
        if (eventRequestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new EntityExistsException("Заявка на данное событие уже создана");
        }
        if (!event.getStatus().equals(EventStatus.PUBLISHED)) {
            throw new EntityExistsException("Данное событие еще не опубликовано");
        }
        int requestsCount = eventRequestRepository.countByEventIdAndStatusEquals(eventId,
                EventRequestStatus.CONFIRMED);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(requestsCount)) {
            throw new EntityExistsException("Достигнут лимит заявок на участие");
        }

        EventRequest eventRequest = EventRequest.builder()
                .requester(requester)
                .event(event)
                .status(!event.getRequestModeration() || event.getParticipantLimit() == 0  ?
                        EventRequestStatus.CONFIRMED : EventRequestStatus.PENDING)
                .creationDate(LocalDateTime.now())
                .build();

        return eventRequestRepository.save(eventRequest);
    }

    @Override
    @Transactional
    public EventRequest cancel(Long userId, Long id) {
        log.info("Отмена заявки на событие с id = {} пользователем с id = {}", id, userId);

        EventRequest request = eventRequestRepository.findByIdAndRequesterId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена заявка на событие по id = " + id));

        request.setStatus(EventRequestStatus.CANCELED);

        return eventRequestRepository.save(request);
    }

    @Override
    public List<EventRequest> getByRequesterId(Long userId) {
        log.info("Получение заявок на события пользователя с id = {}", userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь по id = " + userId));

        return eventRequestRepository.findAllByRequesterIdOrderById(userId);
    }

    @Override
    public List<EventRequest> getByInitiatorIdAndEventId(Long userId, Long eventId) {
        log.info("Получение заявок на событие с id = {} инициатора с id = {}", eventId, userId);

        return eventRequestRepository.findByEventInitiatorIdAndEventId(userId, eventId);
    }

    @Override
    @Transactional
    public EventRequestProjection patch(Long userId, Long eventId, List<EventRequest> requests) {
        log.info("Редактирование заявок на событие с id = {} пользователем с id = {}", eventId, userId);
        if (requests.isEmpty()) {
            return EventRequestProjection.builder().build();
        }

        List<EventRequest> requestsFromDb = eventRequestRepository.findByEventInitiatorIdAndEventId(userId, eventId);
        if (requestsFromDb.isEmpty()) {
            return EventRequestProjection.builder().build();
        }

        List<Long> requestIdsFromDb = requestsFromDb.stream()
                .map(AbstractEntity::getId)
                .collect(Collectors.toList());

        requests.forEach(req -> {
            if (!requestIdsFromDb.contains(req.getId())) {
                throw new EntityExistsException("Заявка с id = " + req.getId() +
                        " не принадлежит событию с id = " + eventId);
            }
        });

        requestsFromDb.forEach(req -> {
            if (!req.getStatus().equals(EventRequestStatus.PENDING)) {
                throw new EntityExistsException("Невозможно отредактировать заявку с id = " + req.getId() +
                        " которая находится в статусе " + req.getStatus());
            }
        });

        Event event = eventRepository.findById(eventId).get();

        int confirmedCount = eventRequestRepository.countByEventIdAndStatusEquals(eventId,
                EventRequestStatus.CONFIRMED);
        int limit = event.getParticipantLimit();

        if (limit != 0 && confirmedCount == limit) {
            throw new EntityExistsException("Достигнут лимит по заявкам на событие");
        }
        if (limit == 0 || !event.getRequestModeration()) {
            return EventRequestProjection.builder().build();
        }

        EventRequestProjection projection = EventRequestProjection.builder()
                .confirmedRequests(new ArrayList<>())
                .rejectedRequests(new ArrayList<>())
                .build();

        for (EventRequest req : requests) {
            EventRequest current = requestsFromDb.stream()
                    .filter(request -> request.getId().equals(req.getId()))
                    .findFirst().get();
            if (confirmedCount < limit) {
                if (req.getStatus().equals(EventRequestStatus.CONFIRMED)) {
                    confirmedCount++;
                }
            } else {
                req.setStatus(EventRequestStatus.REJECTED);
            }
            eventRequestConverter.convert(current, req);
            eventRequestRepository.save(current);
            if (current.getStatus().equals(EventRequestStatus.CONFIRMED)) {
                projection.getConfirmedRequests().add(current);
            } else {
                projection.getRejectedRequests().add(current);
            }
        }
        return projection;
    }
}
