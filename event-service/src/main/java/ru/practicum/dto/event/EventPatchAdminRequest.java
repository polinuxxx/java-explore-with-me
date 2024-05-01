package ru.practicum.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.Event;
import ru.practicum.model.EventStateActionAdmin;

/**
 * Параметры запроса для редактирования {@link Event} администратором.
 */
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@Schema(description = "Параметры запроса для редактирования события администратором")
public class EventPatchAdminRequest extends EventPatchBaseRequest {
    @Schema(description = "Действие", example = "PUBLISH_EVENT")
    EventStateActionAdmin stateAction;
}
