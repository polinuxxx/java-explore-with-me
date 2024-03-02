package ru.practicum.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.practicum.dto.user.UserCreateRequest;
import ru.practicum.dto.user.UserView;
import ru.practicum.model.User;

/**
 * Конвертер для {@link User}.
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    User convert(UserCreateRequest request);

    UserView convert(User user);

    List<UserView> convert(List<User> users);
}
