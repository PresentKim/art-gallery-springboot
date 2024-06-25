package com.team4.artgallery.controller.admin;

import com.team4.artgallery.annotation.CheckAdmin;
import com.team4.artgallery.service.DataBaseService;
import com.team4.artgallery.util.ajax.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
@CheckAdmin
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminController {

    private final DataBaseService dataBaseService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String root() {
        return "admin/adminMain";
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset() {
        // 데이터베이스 초기화 시도
        try {
            dataBaseService.resetDatabase();
        } catch (Exception e) {
            // 오류가 발생한 경우 실패 응답 반환
            System.out.println(Arrays.toString(e.getStackTrace()));
            return internalServerError("데이터베이스 초기화에 실패했습니다.");
        }

        // 데이터베이스 초기화에 성공한 경우 성공 응답 반환
        return ok("데이터베이스가 초기화되었습니다.");
    }

}
