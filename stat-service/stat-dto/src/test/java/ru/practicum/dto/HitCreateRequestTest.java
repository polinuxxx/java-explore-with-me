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
 * Тесты для {@link HitCreateRequest}.
 */
@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class HitCreateRequestTest {
    private final JacksonTester<HitCreateRequest> json;

    @Test
    @SneakyThrows
    void testSerialization() {
        HitCreateRequest request = HitCreateRequest.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .creationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        JsonContent<HitCreateRequest> result = json.write(request);

        assertThat(result).hasJsonPath("$.app");
        assertThat(result).hasJsonPath("$.ip");
        assertThat(result).hasJsonPath("$.uri");
        assertThat(result).hasJsonPath("$.timestamp");
        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo(request.getApp());
        assertThat(result).extractingJsonPathStringValue("$.ip").isEqualTo(request.getIp());
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo(request.getUri());
        assertThat(result).extractingJsonPathStringValue("$.timestamp").isEqualTo(request.getCreationDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}