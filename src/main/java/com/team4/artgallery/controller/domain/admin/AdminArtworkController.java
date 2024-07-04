package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/admin/artwork")
@CheckAdmin
@RequiredArgsConstructor
public class AdminArtworkController {

    private final ArtworkService artworkService;

    @GetMapping({"", "/"})
    public String list(
            @Valid
            @ModelAttribute("filter")
            ArtworkFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        pagination.setUrlTemplate("/admin/artwork?page=%d" + filter.getUrlParam());
        model.addAttribute("artworkList", artworkService.getArtworksPair(filter, pagination).list());
        return "admin/adminArtworkList";
    }

    @PostMapping("/update")
    @ResponseBody
    public Object edit(
            @Valid
            @NotEmpty(message = "예술품을 선택해주세요.")
            @Size(max = 1, message = "한 번에 하나의 예술품만 수정할 수 있습니다.")
            @RequestParam(name = "aseqs")
            List<Integer> aseqs
    ) throws URISyntaxException {
        return new URI("/artwork/update/" + aseqs.get(0));
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "예술품을 선택해주세요.")
            @RequestParam(name = "aseqs", required = false)
            List<Integer> aseqs
    ) throws SqlException {
        artworkService.deleteArtwork(aseqs);
        return new ResponseDto("예술품이 삭제되었습니다.", "/artwork");
    }

}
