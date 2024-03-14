package ru.practicum.dto.location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Location;

/**
 * Параметры запроса для создания {@link Location}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры запроса для создания новой локации")
public class LocationCreateRequest {
    @NotBlank
    @Size(min = 1, max = 200)
    @Schema(description = "Название", example = "Moscow", minLength = 1, maxLength = 200)
    String title;

    @NotBlank
    @Schema(description = "Область", example = "POLYGON((55.1 36.1, 55.2 36.1, 55.2 36.0, 55.1 36.0, 55.1 36.1))")
    String area;
}
