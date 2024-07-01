package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.ResponseEntity;
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

    @Delegate
    private final ResponseService responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute("filter") KeywordFilter filter,
            @Valid @ModelAttribute("pagination") Pagination pagination,
            Model model
    ) {
        // 검색 조건에 따라 갤러리 목록을 가져옵니다.
        pagination.setItemCount(galleryService.countGalleries(filter))
                .setUrlTemplate("/admin/gallery?page=%d" + filter.getUrlParam());

        model.addAttribute("galleryList", galleryService.getGalleries(filter, pagination));
        return "admin/adminGalleryList";
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "gseqs", required = false) List<Integer> gseqs) {
        // gseqs 값이 없는 경우 요청 거부 결과 반환
        if (gseqs == null || gseqs.isEmpty()) {
            return badRequest("갤러리를 선택해주세요");
        }

        // 갤러리 정보 제거에 실패한 경우 실패 결과 반환
        if (galleryService.deleteGalleries(gseqs) == 0) {
            return badRequest("갤러리 정보 제거에 실패했습니다");
        }

        // 갤러리 정보 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("갤러리 정보를 제거했습니다", ":reload");
    }

}
