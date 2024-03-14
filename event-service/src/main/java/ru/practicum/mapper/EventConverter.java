package ru.practicum.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ValueMapping;
import ru.practicum.dto.event.EventCreateRequest;
import ru.practicum.dto.event.EventFullView;
import ru.practicum.dto.event.EventPatchAdminRequest;
import ru.practicum.dto.event.EventPatchBaseRequest;
import ru.practicum.dto.event.EventPatchUserRequest;
import ru.practicum.dto.event.EventShortView;
import ru.practicum.model.Event;
import ru.practicum.model.EventStateActionAdmin;
import ru.practicum.model.EventStateActionUser;
import ru.practicum.model.EventStatus;
import ru.practicum.model.projection.EventFullProjection;
import ru.practicum.model.projection.EventShortProjection;

import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.builder.DSL.point;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

/**
 * Конвертер для {@link Event}.
 */
@Mapper(componentModel = "spring", imports = {LocalDateTime.class}, uses = {CategoryConverter.class})
public interface EventConverter {
    @Mapping(expression = "java(LocalDateTime.now())", target = "creationDate")
    @Mapping(constant = "PENDING", target = "status")
    @Mapping(constant = "0", target = "views")
    @Mapping(source = "category", target = "category.id")
    @Mapping(source = "paid", target = "paid", defaultValue = "false")
    @Mapping(source = "participantLimit", target = "participantLimit", defaultValue = "0")
    @Mapping(source = "requestModeration", target = "requestModeration", defaultValue = "true")
    @Mapping(source = "location", target = "location", qualifiedByName = "createRequestToPoint")
    Event convert(EventCreateRequest request);

    @Mapping(source = "category", target = "category.id")
    @Mapping(source = "location", target = "location", qualifiedByName = "patchRequestToPoint")
    @Mapping(source = "stateAction", target = "status")
    Event convert(EventPatchUserRequest request);

    @Mapping(source = "category", target = "category.id")
    @Mapping(source = "location", target = "location", qualifiedByName = "patchRequestToPoint")
    @Mapping(source = "stateAction", target = "status")
    Event convert(EventPatchAdminRequest request);

    @Mapping(constant = "0", target = "confirmedRequests")
    @Mapping(source = "location", target = "location", qualifiedByName = "fromPoint")
    EventFullView convert(Event event);

    @ValueMapping(source = "CANCEL_REVIEW", target = "CANCELED")
    @ValueMapping(source = "SEND_TO_REVIEW", target = "PENDING")
    EventStatus convertEventStatus(EventStateActionUser stateAction);

    @ValueMapping(source = "PUBLISH_EVENT", target = "PUBLISHED")
    @ValueMapping(source = "REJECT_EVENT", target = "CANCELED")
    EventStatus convertEventStatus(EventStateActionAdmin stateAction);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "compilations", ignore = true)
    void convert(@MappingTarget Event target, Event source);

    @Mapping(source = "eventId", target = "id")
    Event convert(Long eventId);

    Set<Event> convert(Set<Long> events);

    EventShortView convert(EventShortProjection event);

    List<EventShortView> convert(List<EventShortProjection> events);

    EventShortView convertShort(Event event);

    Set<EventShortView> convertEvents(Set<Event> events);

    @Mapping(source = "location", target = "location", qualifiedByName = "fromPoint")
    EventFullView convert(EventFullProjection event);

    List<EventFullView> convertFull(List<EventFullProjection> events);

    @Named("createRequestToPoint")
    default Point<G2D> toPoint(EventCreateRequest.LocationRequest request) {
        return point(WGS84, g(request.getLongitude(), request.getLatitude()));
    }

    @Named("patchRequestToPoint")
    default Point<G2D> toPoint(EventPatchBaseRequest.LocationRequest request) {
        if (request == null) {
            return null;
        }
        return point(WGS84, g(request.getLongitude(), request.getLatitude()));
    }

    @Named("fromPoint")
    default EventFullView.LocationView fromPoint(Point<G2D> point) {
        return EventFullView.LocationView.builder()
                .latitude(point.getPosition().getLat())
                .longitude(point.getPosition().getLon()).build();
    }
}
