// NotEmptyMultipartFile.java
package com.team4.artgallery.aspect.annotation;

import com.team4.artgallery.aspect.NotEmptyMultipartFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotEmptyMultipartFileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyMultipartFile {
    String message() default "파일이 비어 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}