package ru.practicum.controller.admin;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.event.EventFullView;
import ru.practicum.dto.event.EventPatchAdminRequest;
import ru.practicum.mapper.EventConverter;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.service.event.EventService;

/**
 * Контроллер для {@link Event} (админ).
 */
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final EventService eventService;

    private final EventConverter eventConverter;

    @GetMapping
    public List<EventFullView> getAll(@RequestParam(required = false) List<Long> users,
                                       @RequestParam(required = false) List<EventStatus> states,
                                       @RequestParam(required = false) List<Long> categories,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                       @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        return eventConverter.convertFull(eventService.getAllAdmin(users, states, categories, rangeStart, rangeEnd,
                from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullView patch(@PathVariable Long eventId, @RequestBody @Valid EventPatchAdminRequest request) {
        return eventConverter.convert(eventService.patch(eventId, eventConverter.convert(request)));
    }
}
