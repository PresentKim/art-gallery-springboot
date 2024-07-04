package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Assert;
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
    ) throws SqlException, FileException {
        GalleryDto oldGallery = galleryService.getGallery(galleryDto.getGseq());
        Assert.exists(oldGallery, "갤러리 정보를 찾을 수 없습니다.");
        Assert.trueOrForbidden(loginMember.getId().equals(oldGallery.getAuthorId()), "작성자만 수정할 수 있습니다.");

        // 이미지 파일이 있을 경우 이미지 저장, 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
        if (imageFile != null && !imageFile.isEmpty()) {
            galleryService.saveImage(imageFile, galleryDto);
        } else {
            galleryDto.setImage(oldGallery.getImage());
            galleryDto.setSavefilename(oldGallery.getSavefilename());
        }

        galleryService.updateGallery(galleryDto);

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
    ) throws SqlException, FileException {
        galleryService.saveImage(imageFile, galleryDto);

        // 작성자 아이디를 로그인 멤버 아이디로 설정
        galleryDto.setAuthorId(loginMember.getId());
        galleryService.createGallery(galleryDto);

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
    ) throws SqlException {
        GalleryDto oldGallery = galleryService.getGallery(gseq);
        Assert.exists(oldGallery, "갤러리 정보를 찾을 수 없습니다.");

        Assert.trueOrForbidden(
                loginMember.getId().equals(oldGallery.getAuthorId()) || loginMember.isAdmin(),
                "작성자 혹은 관리자만 삭제할 수 있습니다."
        );

        galleryService.deleteGallery(gseq);

        return new ResponseDto("갤러리가 삭제되었습니다.", "/gallery");
    }

}
