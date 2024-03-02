package ru.practicum.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.User;

/**
 * Репозиторий для {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findAllByIdInOrderById(List<Long> ids, Pageable pageable);

    List<User> findAllByOrderById(Pageable pageable);
}
