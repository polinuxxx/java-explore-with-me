package ru.practicum.service.request;

import java.util.List;
import ru.practicum.model.EventRequest;
import ru.practicum.model.projection.EventRequestProjection;

/**
 * Сервис для {@link EventRequest}.
 */
public interface EventRequestService {
    EventRequest create(Long userId, Long eventId);

    EventRequest cancel(Long userId, Long id);

    List<EventRequest> getByRequesterId(Long userId);

    List<EventRequest> getByInitiatorIdAndEventId(Long userId, Long eventId);

    EventRequestProjection patch(Long userId, Long eventId, List<EventRequest> requests);
}
