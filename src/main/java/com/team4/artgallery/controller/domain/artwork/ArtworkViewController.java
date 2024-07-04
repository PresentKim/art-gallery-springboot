package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/artwork", produces = MediaType.TEXT_HTML_VALUE)
@RequiredArgsConstructor
public class ArtworkViewController {

    private final ArtworkService artworkService;

    @GetMapping({"", "/"})
    public String list(
            @Validated(ArtworkFilter.ExcludeDisplay.class)
            @ModelAttribute("filter")
            ArtworkFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        filter.setIncludeDisplay(false);
        pagination.setUrlTemplate("/artwork?page=%d" + filter.getUrlParam());
        model.addAttribute("artworkList", artworkService.getArtworksPair(filter, pagination).list());
        return "artwork/artworkList";
    }

    @GetMapping({"/{aseq}", "/view/{aseq}"})
    public String view(
            @PathVariable("aseq")
            Integer aseq,

            Model model
    ) throws NotFoundException {
        model.addAttribute("artworkDto", artworkService.getArtwork(aseq));
        return "artwork/artworkView";
    }

    @CheckAdmin
    @GetMapping("/update")
    public String update(
            @RequestParam("aseq")
            Integer aseq,

            Model model
    ) throws NotFoundException {
        model.addAttribute("artworkDto", artworkService.getArtwork(aseq));
        return "artwork/artworkForm";
    }

    @CheckAdmin
    @GetMapping("/write")
    public String write() {
        return "artwork/artworkForm";
    }

}
