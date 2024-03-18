package ru.practicum.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Общие параметры ответа для идентификатора и имени.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Общие параметры ответа")
public class IdNameView {
    @Schema(description = "Идентификатор", example = "1")
    Long id;

    @Schema(description = "Название", example = "Theatres")
    String name;
}
