package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/gallery")
@CheckAdmin
@RequiredArgsConstructor
public class AdminGalleryController {

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
                .setUrlTemplate("/admin/gallery?page=%d" + filter.getUrlParam());

        model.addAttribute("galleryList", galleryService.getGalleries(filter, pagination));
        return "admin/adminGalleryList";
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "갤러리를 선택해주세요")
            @RequestParam(value = "gseqs", required = false)
            List<Integer> gseqs
    ) {
        galleryService.deleteGalleries(gseqs);
        return new ResponseDto("갤러리 정보를 제거했습니다", ":reload");
    }

}
