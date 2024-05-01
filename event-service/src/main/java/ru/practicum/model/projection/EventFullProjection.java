package ru.practicum.model.projection;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.User;

/**
 * Проекция для получения полной информации о {@link Event}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventFullProjection extends EventShortProjection {
    public EventFullProjection(Long id, User initiator, String annotation, Category category, LocalDateTime eventDate,
                               Boolean paid, String title, Integer confirmedRequests, Integer views, String description,
                               Integer participantLimit, Boolean requestModeration, LocalDateTime creationDate,
                               Point<G2D> location, LocalDateTime publicationDate, EventStatus status) {
        super(id, initiator, annotation, category, eventDate, paid, title, confirmedRequests, views);
        this.description = description;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.creationDate = creationDate;
        this.location = location;
        this.publicationDate = publicationDate;
        this.status = status;
    }

    String description;

    Integer participantLimit;

    Boolean requestModeration;

    LocalDateTime creationDate;

    Point<G2D> location;

    LocalDateTime publicationDate;

    EventStatus status;
}
