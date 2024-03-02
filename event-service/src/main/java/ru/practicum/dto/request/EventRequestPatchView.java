package ru.practicum.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.EventRequest;

/**
 * Параметры ответа для редактирования {@link EventRequest}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestPatchView {
    List<EventRequestView> confirmedRequests;

    List<EventRequestView> rejectedRequests;
}
