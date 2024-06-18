package com.team4.artgallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    @GetMapping("/")
    public String root() {
        return "gallery/galleryList";
    }

    @GetMapping("/view")
    public String view() {
        return "gallery/galleryView";
    }

    @GetMapping("/update")
    public String update() {
        return "gallery/galleryUpdateForm";
    }

    @GetMapping("/write")
    public String write() {
        return "gallery/galleryWriteForm";
    }

}
