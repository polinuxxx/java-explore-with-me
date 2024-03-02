package ru.practicum.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class UserCreateRequest {
    @NotBlank
    @Size(min = 2, max = 250)
    String name;

    @NotBlank
    @Email
    @Size(min = 6, max = 254)
    String email;
}
