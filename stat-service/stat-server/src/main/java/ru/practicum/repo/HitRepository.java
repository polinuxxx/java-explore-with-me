package ru.practicum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;

/**
 * Репозиторий для {@link Hit}.
 */
@Repository
public interface HitRepository extends JpaRepository<Hit, Long>, QuerydslPredicateExecutor<Hit>,
        CustomizedHitRepository {
}
