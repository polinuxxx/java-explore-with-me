package ru.practicum.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.EventRequest;
import ru.practicum.model.EventRequestStatus;

/**
 * Репозиторий для {@link EventRequest}.
 */
@Repository
public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
    @Transactional(readOnly = true)
    int countByEventIdAndStatusEquals(Long eventId, EventRequestStatus status);

    @Transactional(readOnly = true)
    Optional<EventRequest> findByRequesterIdAndEventId(Long userId, Long eventId);

    List<EventRequest> findAllByRequesterIdOrderById(Long userId);

    @Transactional(readOnly = true)
    Optional<EventRequest> findByIdAndRequesterId(Long id, Long userId);

    List<EventRequest> findByEventInitiatorIdAndEventId(Long userId, Long eventId);
}
