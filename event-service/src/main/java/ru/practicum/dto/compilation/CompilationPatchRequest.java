package ru.practicum.dto.compilation;

import java.util.Set;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Compilation;

/**
 * Параметры запроса для редактирования {@link Compilation}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры запроса для редактирования подборки событий")
public class CompilationPatchRequest {
    @Schema(description = "Признак закрепленной подборки", example = "true")
    Boolean pinned;

    @Size(min = 1, max = 50)
    @Schema(description = "Заголовок", example = "Theatres", minLength = 1, maxLength = 50)
    String title;

    @Schema(description = "Множество идентификаторов событий, входящих в подборку", example = "[1, 2]")
    Set<Long> events;
}
