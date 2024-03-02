package ru.practicum.service.category;

import java.util.List;
import ru.practicum.model.Category;

/**
 * Сервис для {@link Category}.
 */
public interface CategoryService {
    Category create(Category category);

    Category patch(Long id, Category category);

    Category getById(Long id);

    List<Category> getAll(Integer from, Integer size);

    void delete(Long id);
}
