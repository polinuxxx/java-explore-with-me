package ru.practicum.controller.admin;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin: События", description = "API для работы с событиями")
public class AdminEventController {
    private final EventService eventService;

    private final EventConverter eventConverter;

    @GetMapping
    @Operation(summary = "Получение списка событий")
    public List<EventFullView> getAll(
            @RequestParam(required = false) @Parameter(description = "Список id инициаторов") List<Long> users,
            @RequestParam(required = false) @Parameter(description = "Список статусов") List<EventStatus> states,
            @RequestParam(required = false) @Parameter(description = "Список id категорий") List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Parameter(description = "Дата события с") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Parameter(description = "Дата события по") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") @Min(0)
            @Parameter(description = "Количество элементов в наборе, которые нужно пропустить") Integer from,
            @RequestParam(defaultValue = "10") @Min(1)
            @Parameter(description = "Количество элементов в наборе") Integer size) {
        return eventConverter.convertFull(eventService.getAllAdmin(users, states, categories, rangeStart, rangeEnd,
                from, size));
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "Редактирование события")
    public EventFullView patch(
            @PathVariable @Parameter(description = "Идентификатор события", required = true) Long eventId,
            @RequestBody @Valid EventPatchAdminRequest request) {
        return eventConverter.convert(eventService.patch(eventId, eventConverter.convert(request)));
    }
}
