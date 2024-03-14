package ru.practicum.mapper;

import java.util.List;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import org.geolatte.geom.codec.Wkt;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.dto.location.LocationCreateRequest;
import ru.practicum.dto.location.LocationPatchRequest;
import ru.practicum.dto.location.LocationView;
import ru.practicum.model.Location;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

/**
 * Конвертер для {@link Location}.
 */
@Mapper(componentModel = "spring")
public interface LocationConverter {
    @Mapping(source = "area", target = "area", qualifiedByName = "toPolygon")
    Location convert(LocationCreateRequest request);

    @Mapping(source = "area", target = "area", qualifiedByName = "toPolygon")
    Location convert(LocationPatchRequest request);

    @Mapping(source = "area", target = "area", qualifiedByName = "fromPolygon")
    LocationView convert(Location location);

    List<LocationView> convert(List<Location> locations);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void convert(@MappingTarget Location target, Location source);

    @Named("toPolygon")
    default Polygon<G2D> toPolygon(String area) {
        if (area == null || area.isBlank()) {
            return null;
        }
        return (Polygon<G2D>) Wkt.fromWkt(area, WGS84);
    }

    @Named("fromPolygon")
    default String fromPolygon(Polygon<G2D> area) {
        return Wkt.toWkt(area);
    }
}
