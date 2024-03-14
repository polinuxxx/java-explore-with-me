package ru.practicum.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Location;

/**
 * Параметры ответа для {@link Location}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры ответа для локации")
public class LocationView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Название", example = "Moscow", minLength = 1, maxLength = 200)
    String title;

    @Schema(description = "Область", example = "POLYGON((55.1 36.1, 55.2 36.1, 55.2 36.0, 55.1 36.0, 55.1 36.1))")
    String area;
}
