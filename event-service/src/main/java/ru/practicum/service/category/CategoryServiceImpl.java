package ru.practicum.service.category;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.Category;
import ru.practicum.repo.CategoryRepository;
import ru.practicum.repo.event.EventRepository;

/**
 * Реализация сервиса для {@link Category}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Category create(Category category) {
        log.info("Создание категории {}", category);

        checkName(category.getName());

        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category patch(Long id, Category category) {
        log.info("Редактирование категории {}", category);

        Category currentCategory = getById(id);

        if (!currentCategory.getName().equals(category.getName())) {
            checkName(category.getName());
        }
        currentCategory.setName(category.getName());

        return categoryRepository.save(currentCategory);
    }

    @Override
    public Category getById(Long id) {
        log.info("Получение категории по id = {}", id);

        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена категория по id = " + id));
    }

    @Override
    public List<Category> getAll(Integer from, Integer size) {
        log.info("Получение списка категорий");

        PageRequest pageRequest = PageRequest.of(from / size, size);

        Page<Category> page = categoryRepository.findAll(pageRequest);

        return page.hasContent() ? page.getContent() : Collections.emptyList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Удаление категории по id = {}", id);

        if (categoryRepository.existsById(id)) {
            if (eventRepository.findByCategoryId(id).isEmpty()) {
                categoryRepository.deleteById(id);
            } else {
                throw new EntityExistsException("С категорией с id = " + id + " связаны события");
            }
        } else {
            throw new EntityNotFoundException("Не найдена категория по id = " + id);
        }
    }

    private void checkName(String name) {
        if (categoryRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new EntityExistsException("Категория с именем " + name
                    + "уже существует");
        }
    }
}
