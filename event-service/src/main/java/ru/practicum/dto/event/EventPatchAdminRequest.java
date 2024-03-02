package ru.practicum.dto.event;

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
public class EventPatchAdminRequest extends EventPatchBaseRequest {
    EventStateActionAdmin stateAction;
}
