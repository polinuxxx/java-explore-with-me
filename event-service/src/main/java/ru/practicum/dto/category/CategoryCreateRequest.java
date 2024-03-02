package ru.practicum.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class CategoryCreateRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    String name;
}
