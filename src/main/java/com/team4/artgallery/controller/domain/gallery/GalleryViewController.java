package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Pagination;
import jakarta.servlet.http.HttpSession;
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
            @ModelAttribute("filter") KeywordFilter filter,
            @Valid @ModelAttribute("pagination") Pagination pagination,
            Model model
    ) {
        // 검색 조건에 따라 갤러리 목록을 가져옵니다.
        pagination.setItemCount(galleryService.countGalleries(filter))
                .setUrlTemplate("/gallery?page=%d" + filter.getUrlParam());

        model.addAttribute("galleryList", galleryService.getGalleries(filter, pagination));
        return "gallery/galleryList";
    }

    @GetMapping({"/{gseq}", "/view/{gseq}"})
    public String view(@PathVariable(value = "gseq") Integer gseq, HttpSession session, Model model) {
        // 갤러리 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        if (galleryDto == null) {
            return "util/404";
        }

        // 갤러리를 읽은 것으로 처리
        galleryService.markAsRead(session, gseq);

        // 갤러리 정보를 뷰에 전달
        model.addAttribute("galleryDto", galleryDto);
        return "gallery/galleryView";
    }

    @CheckLogin("/gallery/update?gseq=${gseq}")
    @GetMapping("/update")
    public String update(@RequestParam(value = "gseq") Integer gseq, @LoginMember MemberDto loginMember, Model model) {
        // 갤러리 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        if (galleryDto == null) {
            return "util/404";
        }

        // 작성자가 아닌 경우 alert 페이지로 포워딩
        if (!loginMember.getId().equals(galleryDto.getAuthorId())) {
            model.addAttribute("message", "작성자만 수정할 수 있습니다.");
            return "util/alert";
        }

        // 갤러리 정보를 뷰에 전달
        model.addAttribute("galleryDto", galleryDto);
        return "gallery/galleryForm";
    }

    @CheckLogin("/gallery/write")
    @GetMapping("/write")
    public String write() {
        // 갤러리 작성 페이지로 이동
        return "gallery/galleryForm";
    }

}
