package ru.practicum.controller.admin;

import javax.validation.Valid;
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
public class AdminCategoryController {
    private final CategoryService categoryService;

    private final CategoryConverter categoryConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryView create(@RequestBody @Valid CategoryCreateRequest request) {
        return categoryConverter.convert(categoryService.create(categoryConverter.convert(request)));
    }

    @PatchMapping("/{catId}")
    public CategoryView patch(@PathVariable Long catId, @RequestBody @Valid CategoryPatchRequest request) {
        return categoryConverter.convert(categoryService.patch(catId, categoryConverter.convert(request)));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        categoryService.delete(catId);
    }
}
