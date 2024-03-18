package ru.practicum.dto.compilation;

import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.event.EventShortView;
import ru.practicum.model.Compilation;

/**
 * Параметры ответа для {@link Compilation}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры ответа для подборки событий")
public class CompilationView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Заголовок", example = "Theatres")
    String title;

    @Schema(description = "Признак закрепленной подборки", example = "true")
    Boolean pinned;

    @Schema(description = "Множество событий, входящих в подборку")
    Set<EventShortView> events;
}
