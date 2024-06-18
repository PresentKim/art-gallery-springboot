package com.team4.artgallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artwork")
public class ArtworkController {

    @GetMapping("/")
    public String root() {
        return "artwork/artworkList";
    }

    @GetMapping("/view")
    public String view() {
        return "artwork/artworkView";
    }

    @GetMapping("/update")
    public String update() {
        return "artwork/artworkUpdateForm";
    }

    @GetMapping("/write")
    public String write() {
        return "artwork/artworkWriteForm";
    }

}
