package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("artworkList", artworkService.getArtworks(filter, pagination));
        return "admin/adminArtworkList";
    }

}
