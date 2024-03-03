package ru.practicum.repo.event;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import ru.practicum.model.EventStatus;
import ru.practicum.model.SortVariant;
import ru.practicum.model.projection.EventShortProjection;

/**
 * Репозиторий для запросов по событиям с QueryDSL.
 */
public interface CustomizedEventRepository {
    <T extends EventShortProjection> List<T> findAll(List<Long> users, List<EventStatus> states, String text,
                                       List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean onlyAvailable, SortVariant sort,
                                       Pageable pageable, Boolean detailed);
}
