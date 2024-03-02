package ru.practicum.controller.common;

import java.util.List;
import javax.validation.constraints.Min;
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
public class CategoryController {
    private final CategoryService categoryService;

    private final CategoryConverter categoryConverter;

    @GetMapping("/{catId}")
    public CategoryView getById(@PathVariable Long catId) {
        return categoryConverter.convert(categoryService.getById(catId));
    }

    @GetMapping
    public List<CategoryView> getAll(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                     @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        return categoryConverter.convert(categoryService.getAll(from, size));
    }
}
