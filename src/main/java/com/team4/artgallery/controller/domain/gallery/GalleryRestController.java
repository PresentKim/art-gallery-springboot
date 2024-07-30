package com.team4.artgallery.controller.domain.gallery;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.ForbiddenException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.dto.gallery.GalleryCreateDto;
import com.team4.artgallery.dto.gallery.GalleryUpdateDto;
import com.team4.artgallery.dto.view.Views;
import com.team4.artgallery.entity.GalleryEntity;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/galleries", produces = MediaType.APPLICATION_JSON_VALUE)
public class GalleryRestController implements GalleryRestControllerDocs {

    private final GalleryService galleryService;

    public GalleryRestController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping(path = "")
    @JsonView(Views.Summary.class)
    public Page<GalleryEntity> getList(
            @Valid
            KeywordFilter filter,
            @Valid
            Pagination pagination
    ) {
        return galleryService.getGalleries(filter, pagination);
    }

    @GetMapping("{gseq}")
    @JsonView(Views.Detail.class)
    public GalleryEntity getById(
            @PathVariable(name = "gseq")
            Integer gseq
    ) {
        galleryService.increaseReadCountIfNew(gseq);
        return galleryService.getGallery(gseq);
    }

    @CheckLogin
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Valid
            GalleryCreateDto galleryCreateDto,

            @LoginMember
            MemberEntity loginMember
    ) throws NotFoundException, SqlException, FileException {
        galleryService.createGallery(galleryCreateDto, loginMember);
    }

    @CheckLogin
    @PutMapping(path = "{gseq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("gseq")
            String gseq,
            @Valid
            GalleryUpdateDto galleryUpdateDto,

            @LoginMember
            MemberEntity loginMember
    ) throws NotFoundException, SqlException, FileException {
        try {
            galleryService.updateGallery(Integer.parseInt(gseq), galleryUpdateDto, loginMember);
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckLogin
    @DeleteMapping(path = "{gseq}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable("gseq")
            String gseq,

            @LoginMember
            MemberEntity loginMember
    ) throws NotFoundException, ForbiddenException, SqlException {
        try {
            galleryService.deleteGallery(Integer.parseInt(gseq), loginMember);
        } catch (NumberFormatException e) {
            System.out.println(gseq);
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

}
