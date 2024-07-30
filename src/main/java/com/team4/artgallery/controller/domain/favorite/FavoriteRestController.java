package com.team4.artgallery.controller.domain.favorite;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.view.Views;
import com.team4.artgallery.entity.ArtworkEntity;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavoriteRestController implements FavoriteRestControllerDocs {

    private final FavoriteService favoriteService;

    public FavoriteRestController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @CheckLogin
    @GetMapping("")
    @JsonView(Views.Summary.class)
    public Page<ArtworkEntity> getList(
            @Valid
            Pagination pagination,

            @LoginMember
            MemberEntity loginMember
    ) {
        return favoriteService.getFavoriteArtworks(loginMember.getId(), pagination);
    }

    @CheckLogin
    @GetMapping("{aseq}")
    public ResponseEntity<?> getById(
            @PathVariable(name = "aseq")
            String aseq,

            @LoginMember
            MemberEntity loginMember
    ) {
        try {
            if (favoriteService.isFavorite(loginMember.getId(), Integer.parseInt(aseq))) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckLogin
    @PostMapping("{aseq}")
    @ResponseStatus(HttpStatus.OK)
    public void create(
            @PathVariable(name = "aseq")
            String aseq,

            @LoginMember
            MemberEntity loginMember
    ) throws SqlException {
        try {
            favoriteService.createFavorite(loginMember.getId(), Integer.parseInt(aseq));
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckLogin
    @DeleteMapping("{aseq}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable(name = "aseq")
            String aseq,

            @LoginMember
            MemberEntity loginMember
    ) throws SqlException {
        try {
            favoriteService.deleteFavorite(loginMember.getId(), Integer.parseInt(aseq));
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

}
