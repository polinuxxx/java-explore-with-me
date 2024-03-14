package ru.practicum.controller.user;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Private: Заявки на участие в событии",
        description = "Закрытый API для работы с заявками текущего пользователя на участие в событиях")
public class UserEventRequestController {
    private final EventRequestService eventRequestService;

    private final EventRequestConverter eventRequestConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание новой заявки на участие в событии")
    public EventRequestView create(
            @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) Long userId,
            @RequestParam @Parameter(description = "Идентификатор события", required = true) Long eventId) {
        return eventRequestConverter.convert(eventRequestService.create(userId, eventId));
    }

    @PatchMapping("/{requestId}/cancel")
    @Operation(summary = "Отмена заявки на участие в событии")
    public EventRequestView patch(
            @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) Long userId,
            @PathVariable @Parameter(description = "Идентификатор заявки", required = true) Long requestId) {
        return eventRequestConverter.convert(eventRequestService.cancel(userId, requestId));
    }

    @GetMapping
    @Operation(summary = "Получение заявок на участие текущего пользователя в чужих событиях")
    public List<EventRequestView> getAllByRequesterId(
            @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) Long userId) {
        return eventRequestConverter.convert(eventRequestService.getByRequesterId(userId));
    }
}
