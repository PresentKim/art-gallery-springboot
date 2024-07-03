package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin", produces = MediaType.TEXT_HTML_VALUE)
@CheckAdmin
@RequiredArgsConstructor
public class AdminViewController {

    @GetMapping({"", "/"})
    public String root() {
        return "admin/adminMain";
    }

}
