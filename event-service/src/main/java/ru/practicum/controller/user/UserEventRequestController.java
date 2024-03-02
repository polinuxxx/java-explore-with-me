package ru.practicum.controller.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.request.EventRequestView;
import ru.practicum.mapper.EventRequestConverter;
import ru.practicum.model.EventRequest;
import ru.practicum.service.request.EventRequestService;

/**
 * Контроллер для {@link EventRequest}.
 */
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserEventRequestController {
    private final EventRequestService eventRequestService;

    private final EventRequestConverter eventRequestConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventRequestView create(@PathVariable Long userId, @RequestParam Long eventId) {
        return eventRequestConverter.convert(eventRequestService.create(userId, eventId));
    }

    @PatchMapping("/{requestId}/cancel")
    public EventRequestView patch(@PathVariable Long userId, @PathVariable Long requestId) {
        return eventRequestConverter.convert(eventRequestService.cancel(userId, requestId));
    }

    @GetMapping
    public List<EventRequestView> getAllByRequesterId(@PathVariable Long userId) {
        return eventRequestConverter.convert(eventRequestService.getByRequesterId(userId));
    }
}
