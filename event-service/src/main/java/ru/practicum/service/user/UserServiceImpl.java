package ru.practicum.service.user;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.User;
import ru.practicum.repo.UserRepository;

/**
 * Реализация сервиса для {@link User}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(User user) {
        log.info("Создание пользователя {}", user);

        checkEmail(user.getEmail());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll(List<Long> ids, Integer from, Integer size) {
        log.info("Получение списка пользователей");

        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (ids != null && !ids.isEmpty()) {
            return userRepository.findAllByIdInOrderById(ids, pageRequest);
        } else {
            return userRepository.findAllByOrderById(pageRequest);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Удаление пользователя по id = {}", id);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Не найден пользователь по id = " + id);
        }
    }

    private void checkEmail(String email) {
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new EntityExistsException("Пользователь с адресом электронной почты " + email
                    + "уже существует");
        }
    }
}
