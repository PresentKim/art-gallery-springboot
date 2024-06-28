package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/artwork")
@CheckAdmin
@RequiredArgsConstructor
public class AdminArtworkController {

    private final ArtworkService artworkService;

    @Delegate
    private final ResponseService responseService;

    @GetMapping({"", "/"})
    public String list(
            @Valid @ModelAttribute("filter") ArtworkFilter filter,
            @Valid @ModelAttribute("pagination") Pagination pagination,
            Model model
    ) {
        pagination.setUrlTemplate("/admin/artwork?page=%d" + filter.getUrlParam());
        model.addAttribute("artworkList", artworkService.getArtworksPair(filter, pagination).list());
        return "admin/adminArtworkList";
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseBody> edit(@RequestParam(value = "aseqs", required = false) List<Integer> aseqs) throws Exception {
        Assert.notEmpty(aseqs, "예술품을 선택해주세요", MissingRequestValueException::new);
        Assert.isSingle(aseqs, "예술품을 하나만 선택해주세요", IllegalArgumentException::new);

        return ok("", "/artwork/update?aseq=" + aseqs.get(0));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "aseqs", required = false) List<Integer> aseqs) throws Exception {
        artworkService.deleteArtwork(aseqs);

        // 예술품 정보 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("예술품이 삭제되었습니다.", "/artwork");
    }

}
