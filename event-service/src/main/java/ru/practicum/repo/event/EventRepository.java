package ru.practicum.repo.event;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.projection.EventFullProjection;

/**
 * Репозиторий для {@link Event}
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event>,
        CustomizedEventRepository {
    String QUERY_SELECT_PART =
            "select new ru.practicum.model.projection.EventFullProjection(event.id, event.initiator, " +
                    "event.annotation, event.category, event.eventDate, event.paid, event.title, " +
                    "cast(count(eventRequest.id) as int), event.views, event.description, event.participantLimit, " +
                    "event.requestModeration, event.creationDate, event.location, event.publicationDate, event.status) " +
                    "from Event event " +
                    "left join EventRequest eventRequest with eventRequest.status = 'CONFIRMED' " +
                    "join event.initiator initiator " +
                    "join event.category category ";
    String QUERY_GROUP_BY_PART =
            "group by event.id, event.initiator, event.annotation, event.category, event.eventDate, event.paid, " +
            "event.title, event.views, event.description, event.participantLimit, event.requestModeration, " +
            "event.creationDate, event.location, event.publicationDate, event.status";

    @Query(QUERY_SELECT_PART +
            "where event.initiator.id = :userId and event.id = :eventId " +
            QUERY_GROUP_BY_PART)
    Optional<EventFullProjection> findByInitiatorIdAndIdWithRequestCount(Long userId, Long eventId);

    @Query(QUERY_SELECT_PART + "where event.id = :id and event.status = 'PUBLISHED' " + QUERY_GROUP_BY_PART)
    Optional<EventFullProjection> findByIdWithRequestCount(Long id);

    List<Event> findByCategoryId(Long categoryId);

    Optional<Event> findByInitiatorIdAndId(Long userId, Long eventId);

    Set<Event> findAllByIdIn(Set<Long> ids);
}
