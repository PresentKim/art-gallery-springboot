package com.team4.artgallery.controller.domain.favorite;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/favorite", produces = MediaType.TEXT_HTML_VALUE)
public class FavoriteViewController {

    private final FavoriteService favoriteService;

    public FavoriteViewController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @CheckLogin("/favorite")
    @GetMapping(path = "")
    public String favorite(
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            @LoginMember
            MemberDto loginMember,
            Model model
    ) {
        model.addAttribute("artworkList", favoriteService.getFavorites(loginMember.getId(), pagination));
        return "favorite/favoriteList";
    }

}
