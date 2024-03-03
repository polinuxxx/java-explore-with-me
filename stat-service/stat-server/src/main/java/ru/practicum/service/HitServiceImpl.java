package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.Hit;
import ru.practicum.model.StatProjection;
import ru.practicum.repo.HitRepository;

/**
 * Реализация сервиса для {@link Hit}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    @Transactional
    public Hit create(Hit hit) {
        log.info("Сохранение информации, что на uri {} сервиса {} был отправлен запрос пользователем с ip = {}",
                hit.getUri(), hit.getApp(), hit.getIp());
        return hitRepository.save(hit);
    }

    @Override
    public List<StatProjection> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                         Boolean unique) {
        log.info("Получение статистики по посещениям с {} по {} для uris {} unique = {}", start, end, uris, unique);

        if (end.isBefore(start)) {
            throw new ValidationException("Дата начала выборки не может быть позже даты окончания");
        }

        return hitRepository.getStats(start, end, uris, unique);
    }
}
