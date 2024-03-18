package ru.practicum.controller.common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Public: События", description = "Публичный API для работы с событиями")
public class EventController {
    private static final String SERVICE_NAME = "event-service";

    private final EventService eventService;

    private final EventConverter eventConverter;

    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${stat-server.url}")
    private String statServerUrl;

    @GetMapping
    @Operation(summary = "Получение списка событий")
    public List<EventShortView> getAll(
            @RequestParam(required = false) @Parameter(description = "Строка поиска в аннотации/описании") String text,
            @RequestParam(required = false) @Parameter(description = "Список id категорий") List<Long> categories,
            @RequestParam(required = false) @Parameter(description = "Признак платного события") Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Parameter(description = "Дата события с") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Parameter(description = "Дата события по") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false")
            @Parameter(description = "Признак неисчерпанного лимита на участие") Boolean onlyAvailable,
            @RequestParam(required = false) @Parameter(description = "Список id локаций") List<Long> locations,
            @RequestParam(required = false) @Parameter(description = "Список областей") List<String> areas,
            @RequestParam(defaultValue = "EVENT_DATE") @Parameter(description = "Сортировка") SortVariant sort,
            @RequestParam(defaultValue = "0") @Min(0)
            @Parameter(description = "Количество элементов в наборе, которые нужно пропустить") Integer from,
            @RequestParam(defaultValue = "10") @Min(1)
            @Parameter(description = "Количество элементов в наборе") Integer size,
            HttpServletRequest httpServletRequest) {
        addHit(httpServletRequest);
        List<String> decodedAreas;
        if (areas != null) {
            decodedAreas = areas.stream()
                    .map(area -> area.replaceAll("comma", ","))
                    .collect(Collectors.toList());
        } else {
            decodedAreas = null;
        }
        return eventConverter.convert(eventService.getAllPublic(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, locations, decodedAreas));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение события по идентификатору")
    public EventFullView getById(
            @PathVariable @Parameter(description = "Идентификатор события", required = true) Long id,
            HttpServletRequest httpServletRequest) {
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
