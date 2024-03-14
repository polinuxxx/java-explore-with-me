package ru.practicum.service.event;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.SortVariant;
import ru.practicum.model.projection.EventFullProjection;
import ru.practicum.model.projection.EventShortProjection;

/**
 * Сервис для {@link Event}.
 */
public interface EventService {
    Event create(Long userId, Event event);

    Event patch(Long userId, Long eventId, Event event);

    Event patch(Long eventId, Event event);

    List<EventFullProjection> getAllAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           Integer from, Integer size);

    List<EventShortProjection> getAllPublic(String text, List<Long> categories, Boolean paid,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Boolean onlyAvailable, SortVariant sort, Integer from, Integer size,
                                            List<Long> locationIds, List<String> areas);

    EventFullProjection getById(Long id);

    List<EventShortProjection> getByInitiatorId(Long userId, Integer from, Integer size);

    EventFullProjection getByInitiatorIdAndId(Long userId, Long id);

    void setViews(Long id, Integer count);
}
