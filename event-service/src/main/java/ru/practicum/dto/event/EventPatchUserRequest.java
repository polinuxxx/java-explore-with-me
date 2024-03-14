package ru.practicum.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.Event;
import ru.practicum.model.EventStateActionUser;

/**
 * Параметры запроса для редактирования {@link Event} пользователем.
 */
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@Schema(description = "Параметры запроса для редактирования события пользователем")
public class EventPatchUserRequest extends EventPatchBaseRequest {
    @Schema(description = "Действие", example = "SEND_TO_REVIEW")
    EventStateActionUser stateAction;
}
