package com.team4.artgallery.controller.domain.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexViewController {

    @GetMapping
    public String root() {
        return "main/main";
    }

}
