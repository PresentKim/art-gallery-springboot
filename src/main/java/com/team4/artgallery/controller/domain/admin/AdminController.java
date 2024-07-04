package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
@CheckAdmin
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping({"", "/"})
    public String root() {
        return "admin/adminMain";
    }

    @PostMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Object reset() throws SQLException, IOException {
        adminService.resetDatabase();
        return "데이터베이스가 초기화되었습니다.";
    }

}
