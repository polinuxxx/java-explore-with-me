package ru.practicum.dto.event;

import java.time.LocalDateTime;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Параметры запроса для редактирования события")
public class EventPatchBaseRequest {
    @Size(min = 20, max = 2000)
    @Schema(description = "Аннотация", example = "It's a good event", minLength = 20, maxLength = 2000)
    String annotation;

    @Schema(description = "Идентификатор категории", example = "1")
    Long category;

    @Size(min = 20, max = 7000)
    @Schema(description = "Описание", example = "description", minLength = 20, maxLength = 7000)
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата события", example = "2024-03-14 12:00:00", pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @Schema(description = "Местоположение события")
    LocationRequest location;

    @Schema(description = "Признак платного события", example = "true")
    Boolean paid;

    @PositiveOrZero
    @Schema(description = "Лимит участников", example = "10")
    Integer participantLimit;

    @Schema(description = "Признак модерации", example = "false")
    Boolean requestModeration;

    @Size(min = 3, max = 120)
    @Schema(description = "Заголовок", example = "Concert", minLength = 3, maxLength = 120)
    String title;

    /**
     * Параметры запроса для координат.
     */
    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Schema(description = "Параметры запроса для редактирования координат")
    public static class LocationRequest {
        @JsonProperty("lat")
        @Schema(description = "Широта", example = "55.5")
        Double latitude;

        @JsonProperty("lon")
        @Schema(description = "Долгота", example = "55.5")
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
