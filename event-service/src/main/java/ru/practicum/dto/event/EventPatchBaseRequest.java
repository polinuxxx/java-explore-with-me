package ru.practicum.dto.event;

import java.time.LocalDateTime;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.Event;

/**
 * Общие параметры запроса для редактирования {@link Event}.
 */
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
public class EventPatchBaseRequest {
    @Size(min = 20, max = 2000)
    String annotation;

    Long category;

    @Size(min = 20, max = 7000)
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    LocationRequest location;

    Boolean paid;

    @PositiveOrZero
    Integer participantLimit;

    Boolean requestModeration;

    @Size(min = 3, max = 120)
    String title;

    /**
     * Параметры запроса для координат.
     */
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LocationRequest {
        @JsonProperty("lat")
        Double latitude;

        @JsonProperty("lon")
        Double longitude;
    }

    /**
     * Проверка, что дата события не раньше, чем через 2 часа от текущего момента времени.
     */
    @JsonIgnore
    @AssertTrue(message = "Дата события не может быть раньше, чем через 2 часа от текущего момента времени")
    public boolean isEventDateValid() {
        return eventDate == null || !eventDate.isBefore(LocalDateTime.now().plusHours(2));
    }
}
