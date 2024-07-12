package com.team4.artgallery.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CountRequest(
        @Schema(description = "개수", example = "10")
        Integer count
) {
}
