package ru.practicum.dto.request;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Параметры запроса для редактирования заявок на участие в событии")
public class EventRequestPatchRequest {
    @Schema(description = "Список идентификаторов заявок на участие в событии", example = "[1, 2]")
    List<Long> requestIds;

    @Schema(description = "Статус", example = "CONFIRMED")
    EventRequestStatus status;
}
