package com.team4.artgallery.controller;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.service.MemberService;
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
@RequestMapping("/artwork")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArtworkController {

    private final ArtworkService artworkService;

    private final MemberService memberService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute IArtworkDao.ArtworkFilter filter,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model
    ) {
        // 전시 여부는 무시합니다. 항상 전시 중인 작품만 보여줍니다.
        filter.setDisplayyn("Y");

        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 작품 목록을 가져옵니다.
        Pagination.Pair<ArtworkDto> pair = artworkService.getOrSearchArtworks(page, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("artworkList", pair.list());
        return "artwork/artworkList";
    }

    @GetMapping({"/{aseq}", "/view/{aseq}"})
    public String view(@PathVariable(value = "aseq") Integer aseq, Model model) {
        // 작품 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        ArtworkDto artworkDto = artworkService.findArtwork(aseq);
        if (artworkDto == null) {
            return "util/404";
        }

        // 작품 정보를 뷰에 전달
        model.addAttribute("artworkDto", artworkDto);
        return "artwork/artworkView";
    }

    @GetMapping("/update")
    public String update(@RequestParam(value = "aseq") int aseq, Model model, HttpSession session) {
        // 관리자가 아닌 경우 404 페이지로 포워딩
        if (!memberService.isAdmin(session)) {
            return "util/404";
        }

        // 작품 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        ArtworkDto artworkDto = artworkService.findArtwork(aseq);
        if (artworkDto == null) {
            return "util/404";
        }

        // 작품 정보를 뷰에 전달
        model.addAttribute("artworkDto", artworkDto);
        return "artwork/artworkForm";
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            HttpSession session
    ) {
        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 작품 정보를 가져올 수 없는 경우 NOT FOUND 결과 반환
        // 기존 정보가 있어야 UPDATE 쿼리를 실행할 수 있습니다.
        ArtworkDto oldArtwork = artworkService.findArtwork(artworkDto.getAseq());
        if (oldArtwork == null) {
            return notFound();
        }

        // 이미지 파일이 있을 경우 이미지 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!artworkService.saveImage(imageFile, artworkDto)) {
                // 이미지 저장에 실패하면 오류 결과 반환
                return internalServerError("이미지 저장에 실패했습니다.");
            }
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
            artworkDto.setImage(oldArtwork.getImage());
            artworkDto.setSavefilename(oldArtwork.getSavefilename());
        }

        // 작품 수정 실패 시 오류 결과 반환
        if (artworkService.updateArtwork(artworkDto) != 1) {
            return internalServerError("작품 수정에 실패했습니다.");
        }

        // 작품 수정 성공 시 성공 결과 반환
        return ok("작품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @PostMapping("/toggleArtworkDisplay")
    public ResponseEntity<?> toggleArtworkDisplay(@RequestParam(value = "aseq") int aseq, HttpSession session) {
        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 전시 여부 변경 실패 시 오류 결과 반환
        if (artworkService.toggleArtworkDisplay(aseq) != 1) {
            return badRequest("전시 여부 변경에 실패했습니다.");
        }

        // 전시 여부 변경 성공 시 성공 결과 반환
        return ok("전시 여부가 변경되었습니다.");
    }

    @GetMapping("/write")
    public String write(HttpSession session) {
        // 관리자가 아닌 경우 404 페이지로 포워딩
        if (!memberService.isAdmin(session)) {
            return "util/404";
        }

        return "artwork/artworkForm";
    }

    @PostMapping("/write")
    public ResponseEntity<?> write(
            @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            HttpSession session
    ) {
        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 이미지 저장에 실패하면 오류 결과 반환
        if (!artworkService.saveImage(imageFile, artworkDto)) {
            return internalServerError("이미지 저장에 실패했습니다.");
        }

        // 작품 등록 실패 시 오류 결과 반환
        if (artworkService.insertArtwork(artworkDto) != 1) {
            return internalServerError("작품 등록에 실패했습니다.");
        }

        // 작품 등록 성공 시 성공 결과 반환
        return ok("작품이 등록되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "aseq") int aseq, HttpSession session) {
        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 작품 삭제 실패 시 오류 결과 반환
        if (artworkService.deleteArtwork(aseq) != 1) {
            return badRequest("작품 삭제에 실패했습니다.");
        }

        // 작품 삭제 성공 시 성공 결과 반환
        return ok("작품이 삭제되었습니다.", "/artwork");
    }

}
