package ru.practicum.dto.request;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.EventRequest;

/**
 * Параметры ответа для {@link EventRequest}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры ответа для заявки на участие в событии")
public class EventRequestView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Идентификатор пользователя, подавшего заявку", example = "1")
    Long requester;

    @Schema(description = "Идентификатор события, на которое подана заявка", example = "1")
    Long event;

    @Schema(description = "Статус", example = "CONFIRMED")
    String status;

    @JsonProperty("created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата создания", example = "2024-03-14 12:00:00", pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
}
