package com.team4.artgallery.dto.filter.annotation;

import com.team4.artgallery.dto.filter.IFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 필터링에 사용되는 필드임을 나타내는 어노테이션
 *
 * @implNote 어노테이션 확인은 {@link IFilter#getFilters} 에서 처리합니다.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterField {
}