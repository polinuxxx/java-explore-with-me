package ru.practicum.controller.admin;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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
public class AdminUserController {
    private final UserService userService;

    private final UserConverter userConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserView create(@RequestBody @Valid UserCreateRequest request) {
        return userConverter.convert(userService.create(userConverter.convert(request)));
    }

    @GetMapping
    public List<UserView> getAll(@RequestParam(required = false) List<Long> ids,
                                 @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                 @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        return userConverter.convert(userService.getAll(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
