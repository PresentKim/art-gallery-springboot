package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.service.AdminService;
import com.team4.artgallery.service.helper.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckAdmin
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;

    @Delegate
    private final ResponseService responseHelper;

    @PostMapping("/reset")
    public ResponseEntity<?> reset() throws Exception {
        // 데이터베이스 초기화
        adminService.resetDatabase();

        // 데이터베이스 초기화에 성공한 경우 성공 응답 반환
        return ok("데이터베이스가 초기화되었습니다.");
    }

}
