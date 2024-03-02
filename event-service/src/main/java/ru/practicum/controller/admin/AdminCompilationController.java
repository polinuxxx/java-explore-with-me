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
import ru.practicum.dto.compilation.CompilationCreateRequest;
import ru.practicum.dto.compilation.CompilationPatchRequest;
import ru.practicum.dto.compilation.CompilationView;
import ru.practicum.mapper.CompilationConverter;
import ru.practicum.model.Compilation;
import ru.practicum.service.compilation.CompilationService;

/**
 * Контроллер для {@link Compilation} (админ).
 */
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    private final CompilationConverter compilationConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationView create(@RequestBody @Valid CompilationCreateRequest request) {
        return compilationConverter.convert(compilationService.create(compilationConverter.convert(request)));
    }

    @PatchMapping("/{compId}")
    public CompilationView patch(@PathVariable Long compId, @RequestBody @Valid CompilationPatchRequest request) {
        return compilationConverter.convert(compilationService.patch(compId, compilationConverter.convert(request)));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        compilationService.delete(compId);
    }
}
