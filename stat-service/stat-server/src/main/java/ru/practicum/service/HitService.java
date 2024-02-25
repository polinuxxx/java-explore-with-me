package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.Hit;
import ru.practicum.model.StatProjection;

/**
 * Сервис для {@link Hit}.
 */
public interface HitService {
    Hit create(Hit hit);

    List<StatProjection> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
