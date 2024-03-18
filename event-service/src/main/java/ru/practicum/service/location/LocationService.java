package ru.practicum.service.location;

import java.util.List;
import ru.practicum.model.Location;

/**
 * Сервис для {@link Location}.
 */
public interface LocationService {
    Location create(Location location);

    Location patch(Long id, Location location);

    Location getById(Long id);

    List<Location> getAll(List<Long> ids, Integer from, Integer size);

    void delete(Long id);
}
