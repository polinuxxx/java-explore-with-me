package ru.practicum.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.User;

/**
 * Параметры ответа для {@link User}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Параметры ответа для пользователя")
public class UserView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Имя", example = "Ivan")
    String name;

    @Schema(description = "Электронная почта", example = "ivan@mail.ru")
    String email;
}
