package com.team4.museum.util.ajax;

final public class AjaxException extends Exception {

    private static final long serialVersionUID = 4010270985151127478L;

    private final int statusCode;

    private final String url;

    public AjaxException(int statusCode, String message) {
        this(statusCode, message, "");
    }

    public AjaxException(int statusCode, String message, String url) {
        super(message);
        this.statusCode = statusCode;
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUrl() {
        return url;
    }
}