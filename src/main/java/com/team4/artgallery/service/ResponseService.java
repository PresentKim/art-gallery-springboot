package com.team4.artgallery.service;

import com.team4.artgallery.dto.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

@Service
public class ResponseService {

    /**
     * OK(200) 요청이 성공적으로 처리되었음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> ok() {
        return ok("요청이 성공적으로 처리되었습니다");
    }

    /**
     * OK(200) 요청이 성공적으로 처리되었음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> ok(String message) {
        return ok(message, "");
    }

    /**
     * OK(200) 요청이 성공적으로 처리되었음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> ok(String message, String url) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody(message, url));
    }

    /**
     * CREATED(201) 요청을 성공적으로 처리하고 새로운 리소스를 생성했음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> created() {
        return created("새로운 리소스가 생성되었습니다");
    }

    /**
     * CREATED(201) 요청을 성공적으로 처리하고 새로운 리소스를 생성했음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> created(String message) {
        return created(message, "");
    }

    /**
     * CREATED(201) 요청을 성공적으로 처리하고 새로운 리소스를 생성했음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> created(String message, String url) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBody(message, url));
    }

    /**
     * NO_CONTENT(204) 요청이 성공적으로 처리되었으나 반환할 콘텐츠가 없음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> noContent() {
        return noContent("내용이 없습니다");
    }

    /**
     * NO_CONTENT(204) 요청이 성공적으로 처리되었으나 반환할 콘텐츠가 없음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> noContent(String message) {
        return noContent(message, "");
    }

    /**
     * NO_CONTENT(204) 요청이 성공적으로 처리되었으나 반환할 콘텐츠가 없음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> noContent(String message, String url) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseBody(message, url));
    }

    /**
     * BAD_REQUEST(400) 잘못된 요청이나 잘못된 매개변수가 전달되었음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> badRequest() {
        return badRequest("잘못된 요청입니다");
    }

    /**
     * BAD_REQUEST(400) 잘못된 요청이나 잘못된 매개변수가 전달되었음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> badRequest(String message) {
        return badRequest(message, "");
    }

    /**
     * BAD_REQUEST(400) 잘못된 요청이나 잘못된 매개변수가 전달되었음을 나타내는 코드를 반환
     *
     * @param errors 에러 객체
     */
    public ResponseEntity<ResponseBody> badRequest(Errors errors) {
        return badRequest(Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
    }

    /**
     * BAD_REQUEST(400) 잘못된 요청이나 잘못된 매개변수가 전달되었음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> badRequest(String message, String url) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(message, url));
    }

    /**
     * UNAUTHORIZED(401) 인증이 필요함을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> unauthorized() {
        return unauthorized("요청이 인증되지 않았습니다");
    }

    /**
     * UNAUTHORIZED(401) 인증이 필요함을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> unauthorized(String message) {
        return unauthorized(message, "");
    }

    /**
     * UNAUTHORIZED(401) 인증이 필요함을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> unauthorized(String message, String url) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseBody(message, url));
    }

    /**
     * FORBIDDEN(403) 요청이 권한이 없어 거부됨을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> forbidden() {
        return forbidden("요청이 거부되었습니다");
    }

    /**
     * FORBIDDEN(403) 요청이 권한이 없어 거부됨을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> forbidden(String message) {
        return forbidden(message, "");
    }

    /**
     * FORBIDDEN(403) 요청이 권한이 없어 거부됨을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> forbidden(String message, String url) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseBody(message, url));
    }

    /**
     * NOT_FOUND(404) 요청된 리소스를 찾을 수 없음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> notFound() {
        return notFound("찾을 수 없습니다");
    }

    /**
     * NOT_FOUND(404) 요청된 리소스를 찾을 수 없음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> notFound(String message) {
        return notFound(message, "");
    }

    /**
     * NOT_FOUND(404) 요청된 리소스를 찾을 수 없음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> notFound(String message, String url) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseBody(message, url));
    }

    /**
     * METHOD_NOT_ALLOWED(405) 요청된 메소드가 허용되지 않음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> methodNotAllowed() {
        return methodNotAllowed("허용되지 않은 요청입니다");
    }

    /**
     * METHOD_NOT_ALLOWED(405) 요청된 메소드가 허용되지 않음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> methodNotAllowed(String message) {
        return methodNotAllowed(message, "");
    }

    /**
     * METHOD_NOT_ALLOWED(405) 요청된 메소드가 허용되지 않음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> methodNotAllowed(String message, String url) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ResponseBody(message, url));
    }

    /**
     * INTERNAL_SERVER_ERROR(500) 요청이 서버 오류로 실패했음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> internalServerError() {
        return internalServerError("요청을 처리하는 중 서버 오류가 발생했습니다");
    }

    /**
     * INTERNAL_SERVER_ERROR(500) 요청이 서버 오류로 실패했음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> internalServerError(String message) {
        return internalServerError(message, "");
    }

    /**
     * INTERNAL_SERVER_ERROR(500) 요청이 서버 오류로 실패했음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> internalServerError(String message, String url) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseBody(message, url));
    }

    /**
     * NOT_IMPLEMENTED(501) 서버가 요청을 처리할 수 있는 기능을 갖추지 못했음을 나타내는 코드를 반환
     */
    public ResponseEntity<ResponseBody> notImplemented() {
        return notImplemented("구현되지 않은 요청입니다");
    }

    /**
     * NOT_IMPLEMENTED(501) 서버가 요청을 처리할 수 있는 기능을 갖추지 못했음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     */
    public ResponseEntity<ResponseBody> notImplemented(String message) {
        return notImplemented(message, "");
    }

    /**
     * NOT_IMPLEMENTED(501) 서버가 요청을 처리할 수 있는 기능을 갖추지 못했음을 나타내는 코드를 반환
     *
     * @param message 응답 메시지 (alert 메시지)
     * @param url     이동할 URL
     */
    public ResponseEntity<ResponseBody> notImplemented(String message, String url) {
        return ResponseEntity.status(SC_NOT_IMPLEMENTED).body(new ResponseBody(message, url));
    }
}
