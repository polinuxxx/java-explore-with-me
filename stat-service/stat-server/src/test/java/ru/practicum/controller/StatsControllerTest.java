package ru.practicum.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.StatServerApp;
import ru.practicum.dto.HitCreateRequest;
import ru.practicum.mapper.HitConverter;
import ru.practicum.model.Hit;
import ru.practicum.model.StatProjection;
import ru.practicum.service.HitService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты для {@link StatsController}
 */
@WebMvcTest(controllers = {StatsController.class, HitConverter.class})
@ContextConfiguration(classes = StatServerApp.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatsControllerTest {

    @MockBean
    private HitService hitService;

    private final ObjectMapper objectMapper;

    private final MockMvc mockMvc;

    private HitCreateRequest createRequest;

    private Hit hit;

    private StatProjection statProjection;

    @BeforeEach
    void setUp() {
        hit = Hit.builder()
                .id(1L)
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.1.10")
                .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
                .build();
        statProjection = StatProjection.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(2L)
                .build();
    }

    @Test
    @SneakyThrows
    void create_whenHitIsValid_thenHitSaved() {
        createRequest = HitCreateRequest.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.168.1.10")
                .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
                .build();
        when(hitService.create(any())).thenReturn(hit);

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(hit.getId()), Long.class))
                .andExpect(jsonPath("$.app", is(hit.getApp())))
                .andExpect(jsonPath("$.uri", is(hit.getUri())))
                .andExpect(jsonPath("$.ip", is(hit.getIp())))
                .andExpect(jsonPath("$.timestamp", is(hit.getCreationDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
        verify(hitService).create(any());
    }

    @Test
    @SneakyThrows
    void create_whenHitHasNullFields_thenBadRequestReturned() {
        createRequest = HitCreateRequest.builder()
                .build();

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(hitService, never()).create(any());
    }

    @Test
    @SneakyThrows
    void getStats_whenStatFound_thenStatReturned() {
        when(hitService.getStats(any(), any(), any(), anyBoolean()))
                .thenReturn(List.of(statProjection));

        mockMvc.perform(get("/stats")
                        .param("start", "2024-02-23 00:00:00")
                        .param("end", "2024-02-24 00:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(statProjection.getApp())))
                .andExpect(jsonPath("$[0].uri", is(statProjection.getUri())))
                .andExpect(jsonPath("$[0].hits", is(statProjection.getHits()), Long.class));
    }

    @Test
    @SneakyThrows
    void getStats_whenRequiredParamsNotSent_thenBadRequestReturned() {
        mockMvc.perform(get("/stats"))
                .andExpect(status().isBadRequest());
        verify(hitService, never()).getStats(any(), any(), any(), anyBoolean());
    }

    @Test
    @SneakyThrows
    void getStats_whenEndHasInvalidFormat_thenBadRequestReturned() {
        mockMvc.perform(get("/stats")
                        .param("start", "2024-02-23 00:00:00")
                        .param("end", "2024-02-24T00:00:00")
                )
                .andExpect(status().isBadRequest());
        verify(hitService, never()).getStats(any(), any(), any(), anyBoolean());
    }
}