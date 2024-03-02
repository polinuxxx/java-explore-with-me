package ru.practicum.dto.event;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.common.IdNameView;
import ru.practicum.model.Event;

/**
 * Параметры ответа для {@link Event}.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortView {
    Long id;

    String annotation;

    IdNameView category;

    Integer confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    IdNameView initiator;

    Boolean paid;

    String title;

    Integer views;
}
