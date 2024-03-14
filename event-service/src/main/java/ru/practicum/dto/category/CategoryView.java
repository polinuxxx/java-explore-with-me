package ru.practicum.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Category;

/**
 * Параметры ответа для {@link Category}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры ответа для категории")
public class CategoryView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Название", example = "Theatres")
    String name;
}
