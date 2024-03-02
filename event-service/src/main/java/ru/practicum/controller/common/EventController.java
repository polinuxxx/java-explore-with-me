package ru.practicum.controller.common;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.StatClient;
import ru.practicum.dto.HitCreateRequest;
import ru.practicum.dto.StatView;
import ru.practicum.dto.event.EventFullView;
import ru.practicum.dto.event.EventShortView;
import ru.practicum.mapper.EventConverter;
import ru.practicum.model.Event;
import ru.practicum.model.SortVariant;
import ru.practicum.service.event.EventService;

/**
 * Контроллер для {@link Event} (публичный).
 */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventController {
    private static final String SERVICE_NAME = "event-service";

    private final EventService eventService;

    private final EventConverter eventConverter;

    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${stat-server.url}")
    private String statServerUrl;

    @GetMapping
    public List<EventShortView> getAll(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Long> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(defaultValue = "EVENT_DATE") SortVariant sort,
                                       @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                       @RequestParam(defaultValue = "10") @Min(1) Integer size,
                                       HttpServletRequest httpServletRequest) {
        addHit(httpServletRequest);
        return eventConverter.convert(eventService.getAllPublic(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size));
    }

    @GetMapping("/{id}")
    public EventFullView getById(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        addHit(httpServletRequest);
        StatClient statClient = new StatClient(statServerUrl, restTemplateBuilder);

        List<StatView> stats = statClient.getStats(LocalDateTime.now().minusYears(5), LocalDateTime.now().plusYears(5),
                List.of(httpServletRequest.getRequestURI()), true);

        long hits = stats.get(0).getHits();
        eventService.setViews(id, (int) hits);

        return eventConverter.convert(eventService.getById(id));
    }

    private void addHit(HttpServletRequest httpServletRequest) {
        StatClient statClient = new StatClient(statServerUrl, restTemplateBuilder);

        HitCreateRequest request = HitCreateRequest.builder()
                .app(SERVICE_NAME)
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .creationDate(LocalDateTime.now())
                .build();
        statClient.create(request);
    }
}
