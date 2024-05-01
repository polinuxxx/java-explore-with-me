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
import ru.practicum.dto.compilation.CompilationView;
import ru.practicum.mapper.CompilationConverter;
import ru.practicum.model.Compilation;
import ru.practicum.service.compilation.CompilationService;

/**
 * Контроллер для {@link Compilation} (публичный).
 */
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
@Tag(name = "Public: Подборки событий", description = "Публичный API для работы с подборками событий")
public class CompilationController {
    private final CompilationService compilationService;

    private final CompilationConverter compilationConverter;

    @GetMapping
    @Operation(summary = "Получение списка подборок событий")
    public List<CompilationView> getAll(
            @RequestParam(required = false) @Parameter(description = "Признак закрепленной подборки") Boolean pinned,
            @RequestParam(defaultValue = "0") @Min(0)
            @Parameter(description = "Количество элементов в наборе, которые нужно пропустить") Integer from,
            @RequestParam(defaultValue = "10") @Min(1)
            @Parameter(description = "Количество элементов в наборе") Integer size) {
        return compilationConverter.convert(compilationService.getAll(pinned, from, size));
    }

    @GetMapping("/{compId}")
    @Operation(summary = "Получение подборки событий по идентификатору")
    public CompilationView getById(
            @PathVariable @Parameter(description = "Идентификатор подборки событий", required = true) Long compId) {
        return compilationConverter.convert(compilationService.getById(compId));
    }
}
