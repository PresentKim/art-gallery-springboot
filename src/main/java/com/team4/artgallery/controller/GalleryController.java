package com.team4.artgallery.controller;

import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gallery")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping({"", "/"})
    public String list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model
    ) {
        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 예술품 목록을 가져옵니다.
        Pagination.Pair<GalleryDto> pair = galleryService.getOrSearchGalleries(page, search);
        model.addAttribute("search", search);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("galleryList", pair.list());
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
