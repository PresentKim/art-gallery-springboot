package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/artwork")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @Delegate
    private final ResponseService responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @Validated(ArtworkFilter.OnlyDisplay.class) @ModelAttribute ArtworkFilter filter,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model
    ) {
        // 검색 조건에 따라 예술품 목록을 가져옵니다.
        Pagination pagination = new Pagination()
                .setPage(page)
                .setItemCount(artworkService.countArtworks(filter))
                .setUrlTemplate("/artwork?page=%d" + filter.setIncludeDisplay(false).toUrlParam());

        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pagination);
        model.addAttribute("artworkList", artworkService.getArtworks(filter, pagination));
        return "artwork/artworkList";
    }

    @GetMapping({"/{aseq}", "/view/{aseq}"})
    public String view(@PathVariable(value = "aseq") Integer aseq, Model model) {
        // 예술품 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        ArtworkDto artworkDto = artworkService.getArtwork(aseq);
        if (artworkDto == null) {
            return "util/404";
        }

        // 예술품 정보를 뷰에 전달
        model.addAttribute("artworkDto", artworkDto);
        return "artwork/artworkView";
    }

    @CheckAdmin
    @GetMapping("/update")
    public String update(@RequestParam(value = "aseq") Integer aseq, Model model) {

        // 예술품 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        ArtworkDto artworkDto = artworkService.getArtwork(aseq);
        if (artworkDto == null) {
            return "util/404";
        }

        // 예술품 정보를 뷰에 전달
        model.addAttribute("artworkDto", artworkDto);
        return "artwork/artworkForm";
    }

    @CheckAdmin
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Valid @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        // 예술품 정보를 가져올 수 없는 경우 NOT FOUND 결과 반환
        // 기존 정보가 있어야 UPDATE 쿼리를 실행할 수 있습니다.
        ArtworkDto oldArtwork = artworkService.getArtwork(artworkDto.getAseq());
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

        // 예술품 수정 실패 시 오류 결과 반환
        if (artworkService.updateArtwork(artworkDto) != 1) {
            return internalServerError("예술품 수정에 실패했습니다.");
        }

        // 예술품 수정 성공 시 성공 결과 반환
        return ok("예술품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/toggleArtworkDisplay")
    public ResponseEntity<?> toggleArtworkDisplay(@RequestParam(value = "aseq") Integer aseq) {
        // 전시 여부 변경 실패 시 오류 결과 반환
        if (artworkService.toggleArtworkDisplay(aseq) != 1) {
            return badRequest("전시 여부 변경에 실패했습니다.");
        }

        // 전시 여부 변경 성공 시 성공 결과 반환
        return ok("전시 여부가 변경되었습니다.");
    }

    @CheckAdmin
    @GetMapping("/write")
    public String write() {
        return "artwork/artworkForm";
    }

    @CheckAdmin
    @PostMapping("/write")
    public ResponseEntity<?> write(
            @Valid @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile") MultipartFile imageFile
    ) {
        // 이미지 파일이 없을 경우 오류 결과 반환
        if (imageFile == null || imageFile.isEmpty()) {
            return badRequest("이미지 파일을 업로드해주세요.");
        }

        // 이미지 저장에 실패하면 오류 결과 반환
        if (!artworkService.saveImage(imageFile, artworkDto)) {
            return internalServerError("이미지 저장에 실패했습니다.");
        }

        // 예술품 등록 실패 시 오류 결과 반환
        if (artworkService.createArtwork(artworkDto) != 1) {
            return internalServerError("예술품 등록에 실패했습니다.");
        }

        // 예술품 등록 성공 시 성공 결과 반환
        return ok("예술품이 등록되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "aseq") Integer aseq) {
        // 예술품 삭제 실패 시 오류 결과 반환
        if (artworkService.deleteArtwork(aseq) != 1) {
            return badRequest("예술품 삭제에 실패했습니다.");
        }

        // 예술품 삭제 성공 시 성공 결과 반환
        return ok("예술품이 삭제되었습니다.", "/artwork");
    }

}
