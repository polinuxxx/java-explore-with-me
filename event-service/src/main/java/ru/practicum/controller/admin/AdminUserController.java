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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.user.UserCreateRequest;
import ru.practicum.dto.user.UserView;
import ru.practicum.mapper.UserConverter;
import ru.practicum.model.User;
import ru.practicum.service.user.UserService;

/**
 * Контроллер для {@link User} (админ).
 */
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Admin: Пользователи", description = "API для работы с пользователями")
public class AdminUserController {
    private final UserService userService;

    private final UserConverter userConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового пользователя")
    public UserView create(@RequestBody @Valid UserCreateRequest request) {
        return userConverter.convert(userService.create(userConverter.convert(request)));
    }

    @GetMapping
    @Operation(summary = "Получение списка пользователей")
    public List<UserView> getAll(
            @RequestParam(required = false)
            @Parameter(description = "Список идентификаторов пользователей") List<Long> ids,
            @RequestParam(defaultValue = "0") @Min(0)
            @Parameter(description = "Количество элементов в наборе, которые нужно пропустить") Integer from,
            @RequestParam(defaultValue = "10") @Min(1)
            @Parameter(description = "Количество элементов в наборе") Integer size) {
        return userConverter.convert(userService.getAll(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя")
    public void delete(@PathVariable @Parameter(description = "Идентификатор пользователя", required = true)
                           Long userId) {
        userService.delete(userId);
    }
}
