package ru.practicum.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.Category;

/**
 * Параметры запроса для создания {@link Category}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Jacksonized
@Schema(description = "Параметры запроса для редактирования категории")
public class CategoryPatchRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Название", example = "Theatres", minLength = 1, maxLength = 50)
    String name;
}
