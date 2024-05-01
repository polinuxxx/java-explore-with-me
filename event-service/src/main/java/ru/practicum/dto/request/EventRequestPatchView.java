package ru.practicum.dto.request;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Параметры ответа для редактирования заявок на участие в событии")
public class EventRequestPatchView {
    @Schema(description = "Список подтвержденных заявок")
    List<EventRequestView> confirmedRequests;

    @Schema(description = "Список отклоненных заявок")
    List<EventRequestView> rejectedRequests;
}
