package ru.practicum.repo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import ru.practicum.model.StatProjection;

/**
 * Репозиторий для запросов с QueryDSL.
 */
public interface CustomizedHitRepository {
    List<StatProjection> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean unique);
}
