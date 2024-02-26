package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.StatServerApp;
import ru.practicum.model.Hit;
import ru.practicum.model.StatProjection;
import ru.practicum.repo.HitRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тесты сервиса для {@link HitServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = StatServerApp.class)
class HitServiceImplTest {
    @Mock
    private HitRepository hitRepository;

    @InjectMocks
    private HitServiceImpl hitService;

    private Hit hit;

    private StatProjection statProjection;

    @BeforeEach
    void setUp() {
        hit = Hit.builder()
                .id(1L)
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.1.5")
                .creationDate(LocalDateTime.now().plusDays(1))
                .build();
        statProjection = StatProjection.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(2L)
                .build();
    }

    @Test
    void create_whenHitIsValid_thenHitSaved() {
        when(hitRepository.save(hit)).thenReturn(hit);

        Hit actual = hitService.create(hit);

        assertThat(hit.getId(), equalTo(actual.getId()));
        assertThat(hit.getApp(), equalTo(actual.getApp()));
        assertThat(hit.getUri(), equalTo(actual.getUri()));
        assertThat(hit.getIp(), equalTo(actual.getIp()));
        assertThat(hit.getCreationDate(), equalTo(actual.getCreationDate()));

        verify(hitRepository).save(hit);
    }

    @Test
    void getStats_whenDatesPassed_thenStatsReturned() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        when(hitRepository.getStats(start, end, null, false))
                .thenReturn(List.of(statProjection));
        List<StatProjection> actual = hitService.getStats(start, end, null, false);

        assertThat(actual, hasSize(1));
        assertThat(actual.get(0).getApp(), equalTo(hit.getApp()));
        assertThat(actual.get(0).getUri(), equalTo(hit.getUri()));
        assertThat(actual.get(0).getHits(), equalTo(2L));

        verify(hitRepository).getStats(start, end, null, false);
    }
}