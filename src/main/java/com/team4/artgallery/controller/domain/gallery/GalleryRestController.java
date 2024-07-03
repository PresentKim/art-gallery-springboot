package com.team4.artgallery.controller.domain.gallery;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.service.helper.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GalleryRestController {

    private final GalleryService galleryService;

    @Delegate
    private final ResponseService responseHelper;

    @CheckLogin
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Valid GalleryDto galleryDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @LoginMember MemberDto loginMember
    ) {
        // 갤러리 정보를 가져올 수 없는 경우 NOT FOUND 결과 반환
        // 기존 정보가 있어야 UPDATE 쿼리를 실행할 수 있습니다.
        GalleryDto oldGallery = galleryService.getGallery(galleryDto.getGseq());
        if (oldGallery == null) {
            return notFound();
        }

        // 작성자가 아닌 경우 요청 거부 결과 반환
        if (!loginMember.getId().equals(oldGallery.getAuthorId())) {
            return forbidden();
        }

        // 이미지 파일이 있을 경우 이미지 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            galleryService.saveImage(imageFile, galleryDto);
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
            galleryDto.setImage(oldGallery.getImage());
            galleryDto.setSavefilename(oldGallery.getSavefilename());
        }

        // 갤러리 수정 실패 시 오류 결과 반환
        if (galleryService.updateGallery(galleryDto) != 1) {
            return internalServerError("갤러리 수정에 실패했습니다.");
        }

        // 갤러리 수정 성공 시 성공 결과 반환
        return ok("갤러리가 수정되었습니다.", "/gallery/" + galleryDto.getGseq());
    }

    @CheckLogin
    @PostMapping("/write")
    public ResponseEntity<?> write(
            @Valid GalleryDto galleryDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @LoginMember MemberDto loginMember
    ) {
        // 이미지 파일이 없을 경우 오류 결과 반환
        if (imageFile == null || imageFile.isEmpty()) {
            return badRequest("이미지 파일을 업로드해주세요.");
        }
        galleryService.saveImage(imageFile, galleryDto);

        // 작성자 ID 를 설정
        galleryDto.setAuthorId(loginMember.getId());

        // 갤러리 작성 실패 시 오류 결과 반환
        if (galleryService.createGallery(galleryDto) != 1) {
            return internalServerError("갤러리 작성에 실패했습니다.");
        }

        // 갤러리 작성 성공 시 성공 결과 반환
        return ok("갤러리가 작성되었습니다.", "/gallery/" + galleryDto.getGseq());
    }

    @CheckLogin
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "gseq") Integer gseq, @LoginMember MemberDto loginMember) {
        // 갤러리 정보를 가져올 수 없는 경우 NOT FOUND 결과 반환
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        if (galleryDto == null) {
            return notFound();
        }

        // 작성자 혹은 관리자가 아닌 경우 요청 거부 결과 반환
        if (!loginMember.getId().equals(galleryDto.getAuthorId()) && !loginMember.isAdmin()) {
            return forbidden();
        }

        // 갤러리 삭제 실패 시 오류 결과 반환
        if (galleryService.deleteGallery(gseq) != 1) {
            return badRequest("갤러리 삭제에 실패했습니다.");
        }

        // 갤러리 삭제 성공 시 성공 결과 반환
        return ok("갤러리가 삭제되었습니다.", "/gallery");
    }

}
