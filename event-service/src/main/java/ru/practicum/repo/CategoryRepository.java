package ru.practicum.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.Category;

/**
 * Репозиторий для {@link Category}.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Transactional(readOnly = true)
    Optional<Category> findByNameIgnoreCase(String name);

    @Transactional(readOnly = true)
    List<Category> findAllByIdIn(List<Long> ids);
}
