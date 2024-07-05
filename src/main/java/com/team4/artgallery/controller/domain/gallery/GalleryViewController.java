package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/gallery", produces = MediaType.TEXT_HTML_VALUE)
@RequiredArgsConstructor
public class GalleryViewController {

    private final GalleryService galleryService;

    @GetMapping({"", "/"})
    public String list(
            @Valid
            @ModelAttribute("filter")
            KeywordFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        model.addAttribute("galleryList", galleryService.getGalleries(filter, pagination));
        return "gallery/galleryList";
    }

    @GetMapping("/{gseq}")
    public String view(
            @PathVariable(name = "gseq")
            Integer gseq,

            Model model
    ) {
        galleryService.increaseReadCountIfNew(gseq);
        model.addAttribute("galleryDto", galleryService.getGallery(gseq));
        return "gallery/galleryView";
    }

    @CheckLogin("/gallery/write?gseq=${gseq}")
    @GetMapping("/write")
    public String write(
            @RequestParam(name = "gseq", required = false)
            Integer gseq,

            @LoginMember
            MemberDto loginMember,
            Model model
    ) throws NotFoundException {
        if (gseq != null) {
            model.addAttribute("galleryDto", galleryService.getGalleryOnlyAuthor(gseq, loginMember));
        }
        return "gallery/galleryWrite";
    }

}
