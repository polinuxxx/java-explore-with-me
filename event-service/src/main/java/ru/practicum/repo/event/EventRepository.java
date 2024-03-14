package ru.practicum.repo.event;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;

/**
 * Репозиторий для {@link Event}
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event>,
        CustomizedEventRepository {
    List<Event> findByCategoryId(Long categoryId);

    Optional<Event> findByInitiatorIdAndId(Long userId, Long eventId);

    Set<Event> findAllByIdIn(Set<Long> ids);
}
