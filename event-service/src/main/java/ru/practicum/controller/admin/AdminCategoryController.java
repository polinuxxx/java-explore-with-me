package ru.practicum.controller.admin;

import javax.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.category.CategoryCreateRequest;
import ru.practicum.dto.category.CategoryPatchRequest;
import ru.practicum.dto.category.CategoryView;
import ru.practicum.mapper.CategoryConverter;
import ru.practicum.model.Category;
import ru.practicum.service.category.CategoryService;

/**
 * Контроллер для {@link Category} (админ).
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Tag(name = "Admin: Категории", description = "API для работы с категориями")
public class AdminCategoryController {
    private final CategoryService categoryService;

    private final CategoryConverter categoryConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание новой категории")
    public CategoryView create(@RequestBody @Valid CategoryCreateRequest request) {
        return categoryConverter.convert(categoryService.create(categoryConverter.convert(request)));
    }

    @PatchMapping("/{catId}")
    @Operation(summary = "Редактирование категории")
    public CategoryView patch(
            @PathVariable @Parameter(description = "Идентификатор категории", required = true) Long catId,
            @RequestBody @Valid CategoryPatchRequest request) {
        return categoryConverter.convert(categoryService.patch(catId, categoryConverter.convert(request)));
    }

    @DeleteMapping("/{catId}")
    @Operation(summary = "Удаление категории")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Parameter(description = "Идентификатор категории", required = true) Long catId) {
        categoryService.delete(catId);
    }
}
