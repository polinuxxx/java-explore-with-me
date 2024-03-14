package ru.practicum.controller.admin;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.location.LocationCreateRequest;
import ru.practicum.dto.location.LocationPatchRequest;
import ru.practicum.dto.location.LocationView;
import ru.practicum.mapper.LocationConverter;
import ru.practicum.model.Location;
import ru.practicum.service.location.LocationService;

/**
 * Контроллер для {@link Location} (админ).
 */
@RestController
@RequestMapping("/admin/locations")
@RequiredArgsConstructor
@Validated
@Tag(name = "Admin: Локации", description = "API для работы с локациями")
public class AdminLocationController {
    private final LocationService locationService;

    private final LocationConverter locationConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание новой локации")
    public LocationView create(@RequestBody @Valid LocationCreateRequest request) {
        return locationConverter.convert(locationService.create(locationConverter.convert(request)));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Редактирование локации")
    public LocationView patch(@PathVariable @Parameter(description = "Идентификатор локации", required = true) Long id,
                              @RequestBody @Valid LocationPatchRequest request) {
        return locationConverter.convert(locationService.patch(id, locationConverter.convert(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение локации по идентификатору")
    public LocationView getById(@PathVariable @Parameter(description = "Идентификатор локации", required = true)
                                    Long id) {
        return locationConverter.convert(locationService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Получение списка локаций")
    public List<LocationView> getAll(
            @RequestParam(required = false) @Parameter(description = "Список идентификаторов локаций") List<Long> ids,
            @RequestParam(defaultValue = "0") @Min(0)
            @Parameter(description = "Количество элементов в наборе, которые нужно пропустить") Integer from,
            @RequestParam(defaultValue = "10") @Min(1)
            @Parameter(description = "Количество элементов в наборе") Integer size) {
        return locationConverter.convert(locationService.getAll(ids, from, size));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление локации")
    public void delete(@PathVariable @Parameter(description = "Идентификатор локации", required = true) Long id) {
        locationService.delete(id);
    }
}
