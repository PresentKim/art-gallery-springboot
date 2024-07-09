package com.team4.artgallery.dto;

import lombok.Getter;

/**
 * 응답 메시지와 URL을 담는 DTO 레코드
 */
public record ResponseDto(@Getter String message, @Getter String url) {
}