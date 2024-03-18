package ru.practicum.dto.event;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Schema(description = "Параметры ответа для получения полной информации о событии")
public class EventFullView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Аннотация", example = "It's a good event")
    String annotation;

    @Schema(description = "Категория")
    IdNameView category;

    @Schema(description = "Количество подтвержденных заявок на участие", example = "5")
    Integer confirmedRequests;

    @JsonProperty("createdOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата создания", example = "2024-03-14 12:00:00", pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;

    @Schema(description = "Описание", example = "description")
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата события", example = "2024-03-14 12:00:00", pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @Schema(description = "Местоположение события")
    LocationView location;

    @Schema(description = "Инициатор события")
    IdNameView initiator;

    @Schema(description = "Признак платного события", example = "true")
    Boolean paid;

    @Schema(description = "Лимит участников", example = "10")
    Integer participantLimit;

    @Schema(description = "Признак модерации", example = "false")
    Boolean requestModeration;

    @JsonProperty("publishedOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата публикации", example = "2024-03-14 12:00:00", pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publicationDate;

    @Schema(description = "Заголовок", example = "Concert")
    String title;

    @JsonProperty("state")
    @Schema(description = "Статус", example = "PUBLISHED")
    String status;

    @Schema(description = "Количество просмотров", example = "100")
    Integer views;

    /**
     * Параметры ответа для координат.
     */
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(description = "Параметры запроса для координат")
    public static class LocationView {
        @JsonProperty("lat")
        @Schema(description = "Широта", example = "55.5")
        Double latitude;

        @JsonProperty("lon")
        @Schema(description = "Долгота", example = "55.5")
        Double longitude;
    }
}
