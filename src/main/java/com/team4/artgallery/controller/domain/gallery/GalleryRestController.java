package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.ForbiddenException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.service.GalleryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
public class GalleryRestController {

    private final GalleryService galleryService;

    public GalleryRestController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @CheckLogin
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto write(
            @Valid
            GalleryDto galleryDto,
            @Valid
            @RequestParam(name = "imageFile", required = false)
            MultipartFile imageFile,

            @LoginMember
            MemberDto loginMember
    ) throws NotFoundException, SqlException, FileException {
        if (galleryDto.getGseq() == null) {
            galleryService.createGallery(galleryDto, imageFile, loginMember);
        } else {
            galleryService.updateGallery(galleryDto, imageFile, loginMember);
        }
        return new ResponseDto("갤러리가 등록되었습니다.", "/gallery/" + galleryDto.getGseq());
    }

    @CheckLogin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "갤러리를 선택해주세요")
            @RequestParam(name = "gseq", required = false)
            List<Integer> gseq,

            @LoginMember
            MemberDto loginMember
    ) throws NotFoundException, ForbiddenException, SqlException {
        if (loginMember.isAdmin()) {
            galleryService.deleteGallery(gseq);
        } else {
            galleryService.deleteGallery(gseq, loginMember);
        }
        return new ResponseDto("갤러리가 삭제되었습니다.", "/gallery");
    }

}
