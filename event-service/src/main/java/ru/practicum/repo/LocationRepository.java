package ru.practicum.repo;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Location;

/**
 * Репозиторий для {@link Location}.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByIdInOrderById(List<Long> ids, Pageable pageable);

    List<Location> findAllByOrderById(Pageable pageable);
}
