package com.team4.artgallery.controller;

import com.team4.artgallery.annotation.CheckLogin;
import com.team4.artgallery.annotation.LoginMember;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.GalleryService;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ajax.ResponseHelper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model
    ) {
        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 갤러리 목록을 가져옵니다.
        Pagination.Pair<GalleryDto> pair = galleryService.getOrSearchGalleries(page, search, "gallery");
        model.addAttribute("search", search);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("galleryList", pair.list());
        return "gallery/galleryList";
    }

    @GetMapping({"/{gseq}", "/view/{gseq}"})
    public String view(@PathVariable(value = "gseq") Integer gseq, HttpSession session, Model model) {
        // 갤러리 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        if (galleryDto == null) {
            return "util/404";
        }

        // 갤러리를 읽은 것으로 처리
        galleryService.markAsRead(session, gseq);

        // 갤러리 정보를 뷰에 전달
        model.addAttribute("galleryDto", galleryDto);
        return "gallery/galleryView";
    }

    @CheckLogin("/gallery/update?gseq=${gseq}")
    @GetMapping("/update")
    public String update(@RequestParam(value = "gseq") Integer gseq, @LoginMember MemberDto loginMember, Model model) {
        // 갤러리 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        GalleryDto galleryDto = galleryService.getGallery(gseq);
        if (galleryDto == null) {
            return "util/404";
        }

        // 작성자가 아닌 경우 alert 페이지로 포워딩
        if (!loginMember.getId().equals(galleryDto.getAuthorId())) {
            model.addAttribute("message", "작성자만 수정할 수 있습니다.");
            return "util/alert";
        }

        // 갤러리 정보를 뷰에 전달
        model.addAttribute("galleryDto", galleryDto);
        return "gallery/galleryForm";
    }

    @CheckLogin()
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @ModelAttribute GalleryDto galleryDto,
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
            if (!galleryService.saveImage(imageFile, galleryDto)) {
                // 이미지 저장에 실패하면 오류 결과 반환
                return internalServerError("이미지 저장에 실패했습니다.");
            }
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

    @CheckLogin("/gallery/write")
    @GetMapping("/write")
    public String write() {
        // 갤러리 작성 페이지로 이동
        return "gallery/galleryForm";
    }

    @CheckLogin()
    @PostMapping("/write")
    public ResponseEntity<?> write(
            @ModelAttribute GalleryDto galleryDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @LoginMember MemberDto loginMember
    ) {
        // 이미지 파일이 없을 경우 오류 결과 반환
        if (imageFile == null || imageFile.isEmpty()) {
            return badRequest("이미지 파일을 업로드해주세요.");
        }

        // 이미지 저장에 실패하면 오류 결과 반환
        if (!galleryService.saveImage(imageFile, galleryDto)) {
            return internalServerError("이미지 저장에 실패했습니다.");
        }

        // 작성자 ID 를 설정
        galleryDto.setAuthorId(loginMember.getId());

        // 갤러리 작성 실패 시 오류 결과 반환
        if (galleryService.createGallery(galleryDto) != 1) {
            return internalServerError("갤러리 작성에 실패했습니다.");
        }

        // 갤러리 작성 성공 시 성공 결과 반환
        return ok("갤러리가 작성되었습니다.", "/gallery/" + galleryDto.getGseq());
    }

    @CheckLogin("/gallery/write")
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
