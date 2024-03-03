package ru.practicum.model.projection;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.model.EventRequest;

/**
 * Проекция для редактирования {@link EventRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestProjection {
    List<EventRequest> confirmedRequests;

    List<EventRequest> rejectedRequests;
}
