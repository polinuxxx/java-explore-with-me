package ru.practicum.model.projection;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;

/**
 * Проекция для получения краткой информации о {@link Event}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventShortProjection {
    Long id;

    User initiator;

    String annotation;

    Category category;

    LocalDateTime eventDate;

    Boolean paid;

    String title;

    Integer confirmedRequests;

    Integer views;
}
