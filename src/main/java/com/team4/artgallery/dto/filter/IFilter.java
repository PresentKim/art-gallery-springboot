package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface IFilter {

    /**
     * 필터를 Map 형태로 반환합니다.
     *
     * @return 필터 Map
     */
    default Map<String, Object> getFilters() {
        Map<String, Object> filters = new HashMap<>();

        // FilterField 어노테이션이 있는 필드를 찾아서 필터에 추가합니다.
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            // FilterField 어노테이션이 없는 필드는 필터에 추가하지 않습니다.
            if (field.getAnnotation(FilterField.class) == null) {
                continue;
            }

            try {
                // 필드 값을 가져옵니다.
                boolean accessible = field.canAccess(this);
                field.setAccessible(true);
                Object value = field.get(this);
                field.setAccessible(accessible);

                // null 값은 필터에 추가하지 않습니다.
                if (value == null) {
                    continue;
                }

                // 문자열 값이 비어있는 경우 필터에 추가하지 않습니다.
                if (value instanceof String && ((String) value).isEmpty()) {
                    continue;
                }

                // 필터에 추가합니다.
                filters.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field " + field.getName(), e);
            }
        }

        // 필터를 반환합니다.
        System.out.println("filters = " + filters);
        return filters;
    }

    /**
     * 모든 필터가 비어있는지 확인합니다.
     *
     * @return 모든 필터가 비어있으면 true, 아니면 false
     */
    default boolean isEmpty() {
        return getFilters().isEmpty();
    }

    /**
     * URL 파라미터 형식으로 변환합니다.
     *
     * @return URL 파라미터 문자열
     */
    default String toUrlParam() {
        Map<String, Object> filters = getFilters();
        if (filters.isEmpty()) {
            return "";
        }

        return "&" + String.join(
                "&",
                filters.entrySet().stream()
                        .filter(e -> urlParamFilter(e.getKey()))
                        .map(e -> e.getKey() + "=" + e.getValue())
                        .toList()
        );
    }

    /**
     * 주어진 필드 이름을 URL 파라미터로 변환할지 여부를 반환합니다.
     *
     * @param fieldName 필드 이름
     * @return URL 파라미터로 변환할지 여부
     */
    default boolean urlParamFilter(String fieldName) {
        return true;
    }

}
