package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface IFilter {

    /**
     * 클래스의 {@link FilterField} 어노테이션이 있는 필드를 찾아서 필터 맵을 생성해 반환합니다.
     *
     * @return 필터 맵
     * @implNote 리플렉션을 사용하여 모든 필드를 읽어와 필드 맵을 생성합니다.
     * @implSpec 필터 맵은 필드 이름과 필드 값으로 구성되어 있습니다.
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

                // 필드 이름이 Url 파라미터에 포함되는 경우 필터에 추가합니다.
                String fieldName = field.getName();
                if (isFieldIncludedAsUrlParam(fieldName)) {
                    filters.put(fieldName, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field " + field.getName(), e);
            }
        }

        // 필터를 반환합니다.
        return filters;
    }

    /**
     * 필터로부터 URL 파라미터 문자열을 생성해 반환합니다.
     *
     * @return URL 파라미터 문자열
     */
    default String getUrlParam() {
        StringBuilder urlParam = new StringBuilder();
        for (Map.Entry<String, Object> entry : getFilters().entrySet()) {
            urlParam.append("&")
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        return urlParam.toString();
    }

    /**
     * 주어진 필드 이름이 URL 파라미터에 포함될지 여부를 반환합니다.
     *
     * @return URL 파라미터에 포함될지 여부
     * @implNote 이 메서드는 {@link #getUrlParam()} 메서드에서 사용됩니다.
     */
    default boolean isFieldIncludedAsUrlParam(String fieldName) {
        return true;
    }

}
