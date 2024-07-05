package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.SqlException;
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
        model.addAttribute("galleryList", galleryService.getGalleriesPair(filter, pagination).list());
        return "admin/adminGalleryList";
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "갤러리를 선택해주세요")
            @RequestParam(name = "gseq", required = false)
            List<Integer> gseq
    ) throws SqlException {
        galleryService.deleteGallery(gseq);
        return new ResponseDto("갤러리 정보를 제거했습니다", ":reload");
    }

}
