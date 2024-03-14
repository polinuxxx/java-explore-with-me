package ru.practicum.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.User;

/**
 * Параметры запроса для создания {@link User}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры запроса для создания нового пользователя")
public class UserCreateRequest {
    @NotBlank
    @Size(min = 2, max = 250)
    @Schema(description = "Имя", example = "Ivan", minLength = 2, maxLength = 250)
    String name;

    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    @Schema(description = "Электронная почта", example = "ivan@mail.ru", minLength = 6, maxLength = 254)
    String email;
}
