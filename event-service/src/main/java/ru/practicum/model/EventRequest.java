package ru.practicum.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

/**
 * Заявка на событие.
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"requester", "event"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "requests")
public class EventRequest extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    Event event;

    @Enumerated(EnumType.STRING)
    EventRequestStatus status;

    @Column(name = "creation_date")
    LocalDateTime creationDate;
}
