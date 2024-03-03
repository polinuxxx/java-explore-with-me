package ru.practicum.dto.event;

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
public class EventPatchUserRequest extends EventPatchBaseRequest {
    EventStateActionUser stateAction;
}
