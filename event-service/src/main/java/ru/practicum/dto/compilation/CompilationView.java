package ru.practicum.dto.compilation;

import java.util.Set;
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
public class CompilationView {
    Long id;

    String title;

    Boolean pinned;

    Set<EventShortView> events;
}
