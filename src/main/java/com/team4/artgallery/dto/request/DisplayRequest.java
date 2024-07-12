package com.team4.artgallery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record DisplayRequest(
        @Schema(description = "공개 여부", example = "true")
        Boolean display
) {
}
