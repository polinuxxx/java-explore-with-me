package ru.practicum.dto.event;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.common.IdNameView;
import ru.practicum.model.Event;

/**
 * Параметры ответа для {@link Event}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры ответа для получения краткой информации о событии")
public class EventShortView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Аннотация", example = "It's a good event")
    String annotation;

    @Schema(description = "Категория")
    IdNameView category;

    @Schema(description = "Количество подтвержденных заявок на участие", example = "5")
    Integer confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата события", example = "2024-03-14 12:00:00", pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @Schema(description = "Инициатор события")
    IdNameView initiator;

    @Schema(description = "Признак платного события", example = "true")
    Boolean paid;

    @Schema(description = "Заголовок", example = "Concert")
    String title;

    @Schema(description = "Количество просмотров", example = "100")
    Integer views;
}
