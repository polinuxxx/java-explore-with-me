package ru.practicum.service.compilation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mapper.CompilationConverter;
import ru.practicum.model.AbstractEntity;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repo.compilation.CompilationRepository;
import ru.practicum.repo.event.EventRepository;

/**
 * Реализация сервиса для {@link Compilation}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    private final EventRepository eventRepository;

    private final CompilationConverter compilationConverter;

    @Override
    @Transactional
    public Compilation create(Compilation compilation) {
        log.info("Создание подборки событий {}", compilation);

        updateEvents(compilation);

        return compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public Compilation patch(Long id, Compilation compilation) {
        log.info("Редактирование подборки событий по id = {}", id);

        Compilation currentCompilation = compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена подборка по id = " + id));
        updateEvents(compilation);
        compilationConverter.convert(currentCompilation, compilation);

        return compilationRepository.save(currentCompilation);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Удаление подборки событий по id = {}", id);

        if (compilationRepository.existsById(id)) {
            compilationRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Не найдена подборка по id = " + id);
        }
    }

    @Override
    public Compilation getById(Long id) {
        log.info("Получение подборки событий по id = {}", id);

        return compilationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена подборка по id = " + id));
    }

    @Override
    public List<Compilation> getAll(Boolean pinned, Integer from, Integer size) {
        log.info("Получение списка подборок событий");

        PageRequest pageRequest = PageRequest.of(from / size, size);

        if (pinned != null && pinned) {
            return compilationRepository.findAllByPinned(true, pageRequest);
        }
        return compilationRepository.findAll(pageRequest).getContent();
    }

    private void updateEvents(Compilation compilation) {
        if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
            Set<Long> eventIds = compilation.getEvents().stream()
                    .map(AbstractEntity::getId).collect(Collectors.toSet());
            Set<Event> events = eventRepository.findAllByIdIn(eventIds);
            compilation.setEvents(events);
        }
    }
}
