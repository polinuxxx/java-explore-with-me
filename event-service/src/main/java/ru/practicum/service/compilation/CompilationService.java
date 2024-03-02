package ru.practicum.service.compilation;

import java.util.List;
import ru.practicum.model.Compilation;

/**
 * Сервис для {@link Compilation}.
 */
public interface CompilationService {
    Compilation create(Compilation compilation);

    Compilation patch(Long id, Compilation compilation);

    void delete(Long id);

    Compilation getById(Long id);

    List<Compilation> getAll(Boolean pinned, Integer from, Integer size);
}
