package ru.practicum.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.dto.compilation.CompilationCreateRequest;
import ru.practicum.dto.compilation.CompilationPatchRequest;
import ru.practicum.dto.compilation.CompilationView;
import ru.practicum.model.Compilation;

/**
 * Конвертер для {@link Compilation}.
 */
@Mapper(componentModel = "spring", uses = {EventConverter.class})
public interface CompilationConverter {
    @Mapping(defaultValue = "false", target = "pinned")
    Compilation convert(CompilationCreateRequest request);

    Compilation convert(CompilationPatchRequest request);

    CompilationView convert(Compilation compilation);

    List<CompilationView> convert(List<Compilation> compilations);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void convert(@MappingTarget Compilation target, Compilation source);
}
