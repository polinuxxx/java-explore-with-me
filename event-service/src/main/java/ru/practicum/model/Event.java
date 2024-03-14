package ru.practicum.model;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

/**
 * Событие.
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"compilations"})
@ToString(exclude = {"initiator", "category", "compilations"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events")
public class Event extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    User initiator;

    String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    Category category;

    String description;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @Column(name = "is_paid")
    Boolean paid;

    @Column(name = "participant_limit")
    Integer participantLimit;

    @Column(name = "has_request_moderation")
    Boolean requestModeration;

    String title;

    @Column(name = "creation_date")
    LocalDateTime creationDate;

    Point<G2D> location;

    @Column(name = "publication_date")
    LocalDateTime publicationDate;

    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    Set<Compilation> compilations;

    @Enumerated(EnumType.STRING)
    EventStatus status;

    Integer views;
}
