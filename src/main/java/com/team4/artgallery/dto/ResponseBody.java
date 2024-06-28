package com.team4.artgallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 응답 메시지와 URL을 담는 DTO 클래스
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseBody {

    private String message;

    private String url;

}