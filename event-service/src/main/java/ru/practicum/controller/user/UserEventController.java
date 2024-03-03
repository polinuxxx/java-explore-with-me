package ru.practicum.controller.user;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.event.EventCreateRequest;
import ru.practicum.dto.event.EventFullView;
import ru.practicum.dto.event.EventPatchUserRequest;
import ru.practicum.dto.event.EventShortView;
import ru.practicum.dto.request.EventRequestPatchRequest;
import ru.practicum.dto.request.EventRequestPatchView;
import ru.practicum.dto.request.EventRequestView;
import ru.practicum.mapper.EventConverter;
import ru.practicum.mapper.EventRequestConverter;
import ru.practicum.model.Event;
import ru.practicum.service.event.EventService;
import ru.practicum.service.request.EventRequestService;

/**
 * Контроллер для {@link Event} (приватный).
 */
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
public class UserEventController {
    private final EventService eventService;

    private final EventRequestService eventRequestService;

    private final EventConverter eventConverter;

    private final EventRequestConverter eventRequestConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullView create(@PathVariable Long userId, @RequestBody @Valid EventCreateRequest request) {
        return eventConverter.convert(eventService.create(userId, eventConverter.convert(request)));
    }

    @PatchMapping("/{eventId}")
    public EventFullView patch(@PathVariable Long userId, @PathVariable Long eventId,
                               @RequestBody @Valid EventPatchUserRequest request) {
        return eventConverter.convert(eventService.patch(userId, eventId, eventConverter.convert(request)));
    }

    @GetMapping
    public List<EventShortView> getByInitiatorId(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                 @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        return eventConverter.convert(eventService.getByInitiatorId(userId, from, size));
    }

    @GetMapping("/{eventId}")
    public EventFullView getByInitiatorIdAndId(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventConverter.convert(eventService.getByInitiatorIdAndId(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public List<EventRequestView> getRequestsByInitiatorIdAndEventId(@PathVariable Long userId,
                                                                     @PathVariable Long eventId) {
        return eventRequestConverter.convert(eventRequestService.getByInitiatorIdAndEventId(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestPatchView patch(@PathVariable Long userId,
                                       @PathVariable Long eventId,
                                       @RequestBody @Valid EventRequestPatchRequest request) {
        return eventRequestConverter.convert(eventRequestService.patch(userId, eventId,
                eventRequestConverter.convertRequestLoList(request)));
    }
}
