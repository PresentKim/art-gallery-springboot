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
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GalleryRestController {

    private final GalleryService galleryService;

    @CheckLogin
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto update(
            @Valid
            GalleryDto galleryDto,
            @RequestParam(name = "imageFile", required = false)
            MultipartFile imageFile,

            @LoginMember
            MemberDto loginMember
    ) throws NotFoundException, ForbiddenException, SqlException, FileException {
        galleryService.updateGallery(galleryDto, imageFile, loginMember);
        return new ResponseDto("갤러리가 수정되었습니다.", "/gallery/" + galleryDto.getGseq());
    }

    @CheckLogin
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto write(
            @Valid
            GalleryDto galleryDto,
            @Valid
            @NotNull(message = "이미지 파일을 업로드해주세요.")
            @RequestParam(name = "imageFile", required = false)
            MultipartFile imageFile,

            @LoginMember
            MemberDto loginMember
    ) throws FileException, SqlException {
        galleryService.createGallery(galleryDto, imageFile, loginMember);
        return new ResponseDto("갤러리가 작성되었습니다.", "/gallery/" + galleryDto.getGseq());
    }

    @CheckLogin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto delete(
            @RequestParam(name = "gseq")
            Integer gseq,

            @LoginMember
            MemberDto loginMember
    ) throws NotFoundException, ForbiddenException, SqlException {
        galleryService.deleteGallery(gseq, loginMember);
        return new ResponseDto("갤러리가 삭제되었습니다.", "/gallery");
    }

}
