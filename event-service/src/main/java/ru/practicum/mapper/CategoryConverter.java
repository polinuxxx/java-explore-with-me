package ru.practicum.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.dto.category.CategoryCreateRequest;
import ru.practicum.dto.category.CategoryPatchRequest;
import ru.practicum.dto.category.CategoryView;
import ru.practicum.model.Category;

/**
 * Конвертер для {@link Category}.
 */
@Mapper(componentModel = "spring")
public interface CategoryConverter {
    Category convert(CategoryCreateRequest request);

    Category convert(CategoryPatchRequest request);

    CategoryView convert(Category category);

    List<CategoryView> convert(List<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void convert(@MappingTarget Category target, Category source);
}
