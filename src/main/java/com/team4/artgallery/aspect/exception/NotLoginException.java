package com.team4.artgallery.aspect.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotLoginException extends RuntimeException {

    private String returnUrl;

}
