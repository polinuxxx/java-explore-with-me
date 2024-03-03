package ru.practicum.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.EventRequest;
import ru.practicum.model.EventRequestStatus;

/**
 * Параметры запроса для редактирования {@link EventRequest}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestPatchRequest {
    List<Long> requestIds;

    EventRequestStatus status;
}
