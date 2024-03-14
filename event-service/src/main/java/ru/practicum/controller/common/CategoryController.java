package ru.practicum.controller.common;

import java.util.List;
import javax.validation.constraints.Min;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.category.CategoryView;
import ru.practicum.mapper.CategoryConverter;
import ru.practicum.model.Category;
import ru.practicum.service.category.CategoryService;

/**
 * Контроллер для {@link Category} (публичный).
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Public: Категории", description = "Публичный API для работы с категориями")
public class CategoryController {
    private final CategoryService categoryService;

    private final CategoryConverter categoryConverter;

    @GetMapping("/{catId}")
    @Operation(summary = "Получение категории по идентификатору")
    public CategoryView getById(
            @PathVariable @Parameter(description = "Идентификатор категории", required = true) Long catId) {
        return categoryConverter.convert(categoryService.getById(catId));
    }

    @GetMapping
    @Operation(summary = "Получение списка категорий")
    public List<CategoryView> getAll(
            @RequestParam(defaultValue = "0") @Min(0)
            @Parameter(description = "Количество элементов в наборе, которые нужно пропустить") Integer from,
            @RequestParam(defaultValue = "10") @Min(1)
            @Parameter(description = "Количество элементов в наборе") Integer size) {
        return categoryConverter.convert(categoryService.getAll(from, size));
    }
}
