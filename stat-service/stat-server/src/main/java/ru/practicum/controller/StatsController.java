package ru.practicum.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.HitCreateRequest;
import ru.practicum.dto.HitView;
import ru.practicum.dto.StatView;
import ru.practicum.mapper.HitConverter;
import ru.practicum.model.Hit;
import ru.practicum.service.HitService;

/**
 * Контроллер для {@link Hit}
 */
@RestController
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final HitService hitService;

    private final HitConverter hitConverter;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitView create(@RequestBody @Valid HitCreateRequest request) {
        return hitConverter.convert(hitService.create(hitConverter.convert(request)));
    }

    @GetMapping("/stats")
    public List<StatView> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                   @RequestParam(required = false) Collection<String> uris,
                                   @RequestParam(defaultValue = "false") Boolean unique) {
        return hitConverter.convert(hitService.getStats(start, end, uris, unique));
    }
}
