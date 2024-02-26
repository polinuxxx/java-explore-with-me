package ru.practicum.repo;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.StatServerApp;
import ru.practicum.model.Hit;
import ru.practicum.model.StatProjection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Тесты для {@link CustomizedHitRepositoryImpl}.
 */
@DataJpaTest
@ContextConfiguration(classes = StatServerApp.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CustomizedHitRepositoryImplTest {
    private final HitRepository hitRepository;

    private Hit firstHit;

    private Hit secondHit;

    @BeforeEach
    void setUp() {
        firstHit = Hit.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.1.5")
                .creationDate(LocalDateTime.now().plusDays(1))
                .build();
        hitRepository.save(firstHit);
        secondHit = Hit.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.1.5")
                .creationDate(LocalDateTime.now().plusDays(5))
                .build();
        hitRepository.save(secondHit);
    }

    @Test
    void getStats_whenUniqueIsFalse_thenCountTwoReturned() {
        List<StatProjection> result = hitRepository.getStats(LocalDateTime.now(), LocalDateTime.now().plusDays(10),
                null, false);

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getApp(), equalTo(firstHit.getApp()));
        assertThat(result.get(0).getUri(), equalTo(firstHit.getUri()));
        assertThat(result.get(0).getHits(), equalTo(2L));
    }

    @Test
    void getStats_whenUniqueIsTrue_thenCountOneReturned() {
        List<StatProjection> result = hitRepository.getStats(LocalDateTime.now(), LocalDateTime.now().plusDays(10),
                null, true);

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getApp(), equalTo(firstHit.getApp()));
        assertThat(result.get(0).getUri(), equalTo(firstHit.getUri()));
        assertThat(result.get(0).getHits(), equalTo(1L));
    }

    @Test
    void getStats_whenNothingFound_thenEmptyListReturned() {
        List<StatProjection> result = hitRepository.getStats(LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(11),
                null, true);

        assertThat(result, hasSize(0));
    }
}