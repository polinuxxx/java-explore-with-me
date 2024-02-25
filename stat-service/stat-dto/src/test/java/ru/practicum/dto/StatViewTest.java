package ru.practicum.dto;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для {@link StatView}.
 */
@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatViewTest {
    private final JacksonTester<StatView> json;

    @Test
    @SneakyThrows
    void testSerialization() {
        StatView view = StatView.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(10L)
                .build();

        JsonContent<StatView> result = json.write(view);

        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.hits");
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(view.getApp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(view.getUri());
        assertThat(result).extractingJsonPathNumberValue("$.hits").isEqualTo(view.getHits().intValue());
    }
}