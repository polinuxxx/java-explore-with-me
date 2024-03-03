package ru.practicum.service.event;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mapper.EventConverter;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.SortVariant;
import ru.practicum.model.User;
import ru.practicum.model.projection.EventFullProjection;
import ru.practicum.model.projection.EventShortProjection;
import ru.practicum.repo.CategoryRepository;
import ru.practicum.repo.event.EventRepository;
import ru.practicum.repo.UserRepository;

/**
 * Реализация сервиса для {@link Event}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final EventConverter eventConverter;

    @Override
    @Transactional
    public Event create(Long userId, Event event) {
        log.info("Создание события {} пользователем с id = {}", event, userId);

        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь по id = " + userId));
        event.setInitiator(initiator);

        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдена категория по id = "
                        + event.getCategory().getId()));
        event.setCategory(category);

        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event patch(Long userId, Long eventId, Event event) {
        log.info("Редактирование события с id = {} пользователем с id = {}", eventId, userId);

        Event currentEvent = eventRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено событие пользователя с id = " + userId
                        + " по id = " + eventId));
        if (currentEvent.getStatus().equals(EventStatus.PUBLISHED)) {
            throw new EntityExistsException("Нельзя изменить опубликованное событие");
        }
        prepareEventForUpdate(event);

        eventConverter.convert(currentEvent, event);

        return eventRepository.save(currentEvent);
    }

    @Override
    @Transactional
    public Event patch(Long eventId, Event event) {
        log.info("Редактирование события с id = {} администратором", eventId);

        Event currentEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено событие по id = " + eventId));

        if (!currentEvent.getStatus().equals(EventStatus.PENDING)) {
            throw new EntityExistsException("Публиковать и отклонять можно только события в статусе ожидания");
        }
        if (event.getStatus() != null && event.getStatus().equals(EventStatus.PUBLISHED)) {
            event.setPublicationDate(LocalDateTime.now());
            if (event.getPublicationDate().plusHours(1).isAfter(event.getEventDate() == null ?
                    currentEvent.getEventDate() : event.getEventDate())) {
                throw new ValidationException("Дата публикации не может быть раньше, чем за час до события.");
            }
        }
        prepareEventForUpdate(event);

        eventConverter.convert(currentEvent, event);

        return eventRepository.save(currentEvent);
    }

    @Override
    public List<EventFullProjection> getAllAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  Integer from, Integer size) {
        log.info("Получение списка событий администратором");
        if (categories != null && categoryRepository.findAllByIdIn(categories).isEmpty()) {
            throw new ValidationException("Запрос составлен некорректно");
        }

        PageRequest pageRequest = PageRequest.of(from / size, size);

        return eventRepository.findAll(users, states, null, categories, null, rangeStart, rangeEnd,
                null, SortVariant.EVENT_DATE, pageRequest, true);
    }

    @Override
    public List<EventShortProjection> getAllPublic(String text, List<Long> categories, Boolean paid,
                                                   LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                   Boolean onlyAvailable, SortVariant sort,
                                                   Integer from, Integer size) {
        log.info("Получение списка событий");
        if (categories != null && categoryRepository.findAllByIdIn(categories).isEmpty()) {
            throw new ValidationException("Запрос составлен некорректно");
        }

        PageRequest pageRequest = PageRequest.of(from / size, size);

        return eventRepository.findAll(null, List.of(EventStatus.PUBLISHED), text, categories, paid,
                rangeStart == null ? LocalDateTime.now() : rangeStart, rangeEnd, onlyAvailable, sort, pageRequest,
                false);
    }

    @Override
    public EventFullProjection getById(Long id) {
        log.info("Получение события по id = {}", id);

        return eventRepository.findByIdWithRequestCount(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено событие по id = " + id));
    }

    @Override
    public List<EventShortProjection> getByInitiatorId(Long userId, Integer from, Integer size) {
        log.info("Получение списка событий пользователя по id = {}", userId);

        PageRequest pageRequest = PageRequest.of(from / size, size);

        return eventRepository.findAll(List.of(userId), null, null, null, null,
                null, null, null, SortVariant.EVENT_DATE, pageRequest, false);
    }

    @Override
    public EventFullProjection getByInitiatorIdAndId(Long userId, Long id) {
        log.info("Получение события пользователя с id = {} по id = {}", userId, id);

        return eventRepository.findByInitiatorIdAndIdWithRequestCount(userId, id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено событие пользователя с id = " + userId
                        + " по id = " + id));
    }

    @Override
    @Transactional
    public void setViews(Long id, Integer count) {
        Event currentEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено событие по id = " + id));
        currentEvent.setViews(count);
        eventRepository.save(currentEvent);
    }

    private void prepareEventForUpdate(Event event) {
        if (event.getCategory().getId() != null) {
            Category category = categoryRepository.findById(event.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Категория с id " + event.getCategory().getId() + " не найдена"));
            event.setCategory(category);
        }
    }
}
