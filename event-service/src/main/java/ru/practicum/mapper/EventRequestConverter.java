package ru.practicum.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.dto.request.EventRequestPatchRequest;
import ru.practicum.dto.request.EventRequestPatchView;
import ru.practicum.dto.request.EventRequestView;
import ru.practicum.model.EventRequest;
import ru.practicum.model.EventRequestStatus;
import ru.practicum.model.projection.EventRequestProjection;

/**
 * Конвертер для {@link EventRequest}.
 */
@Mapper(componentModel = "spring")
public interface EventRequestConverter {
    @Mapping(source = "requester.id", target = "requester")
    @Mapping(source = "event.id", target = "event")
    EventRequestView convert(EventRequest eventRequest);

    List<EventRequestView> convert(List<EventRequest> eventRequests);

    EventRequestPatchView convert(EventRequestProjection eventRequest);

    @Mapping(source = "requestId", target = "id")
    EventRequest convert(Long requestId);

    List<EventRequest> convertRequests(List<Long> ids);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void convert(@MappingTarget EventRequest target, EventRequest source);

    default List<EventRequest> convertRequestLoList(EventRequestPatchRequest request) {
        List<EventRequest> eventRequests = convertRequests(request.getRequestIds());

        if (!eventRequests.isEmpty() && request.getStatus() != null) {
            eventRequests.forEach(req -> req.setStatus(request.getStatus() != null ?
                    request.getStatus() : EventRequestStatus.CONFIRMED));
        }
        return eventRequests;
    }
}
