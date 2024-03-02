package ru.practicum.dto.compilation;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Compilation;

/**
 * Параметры запроса для создания {@link Compilation}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationCreateRequest {
    Boolean pinned;

    @NotBlank
    @Size(min = 1, max = 50)
    String title;

    Set<Long> events;
}
