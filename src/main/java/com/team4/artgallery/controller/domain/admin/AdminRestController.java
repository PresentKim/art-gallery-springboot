package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.InternalServerErrorException;
import com.team4.artgallery.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/admin")
@CheckAdmin
public class AdminRestController implements AdminRestControllerDocs {

    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(path = "reset", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void resetDatabase() throws InternalServerErrorException {
        try {
            adminService.resetDatabase();
        } catch (SQLException | IOException e) {
            throw new InternalServerErrorException("데이터베이스 초기화에 실패했습니다.");
        }
    }

}
