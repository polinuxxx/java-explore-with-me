package ru.practicum.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.practicum.dto.HitCreateRequest;
import ru.practicum.dto.HitView;
import ru.practicum.dto.StatView;
import ru.practicum.model.Hit;
import ru.practicum.model.StatProjection;

/**
 * Конвертер для {@link Hit}
 */
@Mapper(componentModel = "spring")
public interface HitConverter {
    Hit convert(HitCreateRequest request);

    HitView convert(Hit hit);

    StatView convert(StatProjection stat);

    List<StatView> convert(List<StatProjection> stats);
}
