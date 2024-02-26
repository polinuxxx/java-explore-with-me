package ru.practicum.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для {@link HitView}.
 */
@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class HitViewTest {
    private final JacksonTester<HitView> json;

    @Test
    @SneakyThrows
    void testSerialization() {
        HitView view = HitView.builder()
                .id(1L)
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        JsonContent<HitView> result = json.write(view);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.ip");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.timestamp");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(view.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(view.getApp());
        assertThat(result).extractingJsonPathStringValue("$.ip").isEqualTo(view.getIp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(view.getUri());
        assertThat(result).extractingJsonPathStringValue("$.timestamp").isEqualTo(view.getCreationDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}