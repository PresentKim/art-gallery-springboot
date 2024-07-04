package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        pagination.setItemCount(galleryService.countGalleries(filter))
                .setUrlTemplate("/gallery?page=%d" + filter.getUrlParam());
        model.addAttribute("galleryList", galleryService.getGalleries(filter, pagination));
        return "gallery/galleryList";
    }

    @GetMapping("/{gseq}")
    public String view(
            @PathVariable(name = "gseq")
            Integer gseq,

            Model model
    ) {
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        Assert.exists(galleryDto, "갤러리 정보를 찾을 수 없습니다.");

        galleryService.markAsRead(gseq);

        model.addAttribute("galleryDto", galleryDto);
        return "gallery/galleryView";
    }

    @CheckLogin("/gallery/update/${gseq}")
    @GetMapping("/update/{gseq}")
    public String update(
            @PathVariable(name = "gseq")
            Integer gseq,

            @LoginMember
            MemberDto loginMember,
            Model model
    ) {
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        Assert.exists(galleryDto, "갤러리 정보를 찾을 수 없습니다.");

        Assert.trueOrForbidden(loginMember.getId().equals(galleryDto.getAuthorId()), "작성자만 수정할 수 있습니다.");

        model.addAttribute("galleryDto", galleryDto);
        return "gallery/galleryForm";
    }

    @CheckLogin("/gallery/write")
    @GetMapping("/write")
    public String write() {
        return "gallery/galleryForm";
    }

}
