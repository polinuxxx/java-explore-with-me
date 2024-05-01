package ru.practicum.service.location;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mapper.LocationConverter;
import ru.practicum.model.Location;
import ru.practicum.repo.LocationRepository;

/**
 * Реализация сервиса для {@link Location}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    private final LocationConverter locationConverter;

    @Override
    @Transactional
    public Location create(Location location) {
        log.info("Создание локации {}", location);

        return locationRepository.save(location);
    }

    @Override
    @Transactional
    public Location patch(Long id, Location location) {
        log.info("Редактирование локации с id = {}", id);

        Location currentLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена локация по id = " + id));
        locationConverter.convert(currentLocation, location);

        return locationRepository.save(currentLocation);
    }

    @Override
    public Location getById(Long id) {
        log.info("Получение локации по id = {}", id);

        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена локация по id = " + id));
    }

    @Override
    public List<Location> getAll(List<Long> ids, Integer from, Integer size) {
        log.info("Получение списка локаций");

        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (ids != null && !ids.isEmpty()) {
            return locationRepository.findAllByIdInOrderById(ids, pageRequest);
        } else {
            return locationRepository.findAllByOrderById(pageRequest);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Удаление локации по id = {}", id);

        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Не найдена локация по id = " + id);
        }
    }
}
