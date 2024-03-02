package ru.practicum.service.user;

import java.util.List;
import ru.practicum.model.User;

/**
 * Сервис для {@link User}.
 */
public interface UserService {
    User create(User user);

    List<User> getAll(List<Long> ids, Integer from, Integer size);

    void delete(Long id);
}
