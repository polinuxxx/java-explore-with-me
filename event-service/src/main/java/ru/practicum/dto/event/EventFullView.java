package ru.practicum.dto.event;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EventFullView {
    Long id;

    String annotation;

    IdNameView category;

    Integer confirmedRequests;

    @JsonProperty("createdOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;

    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    LocationView location;

    IdNameView initiator;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @JsonProperty("publishedOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publicationDate;

    String title;

    @JsonProperty("state")
    String status;

    Integer views;

    /**
     * Параметры ответа для координат.
     */
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LocationView {
        @JsonProperty("lat")
        Double latitude;

        @JsonProperty("lon")
        Double longitude;
    }
}
