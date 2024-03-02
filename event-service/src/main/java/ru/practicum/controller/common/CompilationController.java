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
public class CompilationController {
    private final CompilationService compilationService;

    private final CompilationConverter compilationConverter;

    @GetMapping
    public List<CompilationView> getAll(@RequestParam(required = false) Boolean pinned,
                                        @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                        @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        return compilationConverter.convert(compilationService.getAll(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public CompilationView getById(@PathVariable Long compId) {
        return compilationConverter.convert(compilationService.getById(compId));
    }
}
