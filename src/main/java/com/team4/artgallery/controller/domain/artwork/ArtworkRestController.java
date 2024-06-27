package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@RestController
@RequestMapping(path = "/artwork", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ArtworkRestController {

    private final ArtworkService artworkService;

    @Delegate
    private final ResponseService responseHelper;

    @GetMapping({"", "/"})
    public Pagination.Pair<ArtworkDto> list(
            @Valid @ModelAttribute ArtworkFilter filter,
            @Valid @ModelAttribute Pagination pagination
    ) {
        pagination.setUrlTemplate("/artwork?page=%d" + filter.setIncludeDisplay(false).toUrlParam());
        return artworkService.getArtworksPair(filter, pagination);
    }

    @GetMapping({"/{aseq}", "/view/{aseq}"})
    public ArtworkDto view(@PathVariable(value = "aseq") Integer aseq) throws NotFoundException {
        return artworkService.getArtwork(aseq);
    }

    @CheckAdmin
    @PostMapping("/update")
    public ResponseEntity<ResponseBody> update(
            @Valid @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        // 기존 정보가 있어야 UPDATE 쿼리를 실행할 수 있습니다.
        ArtworkDto oldArtwork = artworkService.getArtwork(artworkDto.getAseq());

        // 이미지 파일이 있을 경우 이미지 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!artworkService.saveImage(imageFile, artworkDto)) {
                // 이미지 저장에 실패하면 오류 결과 반환
                throw new FileUploadException("이미지 저장에 실패했습니다.");
            }
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
            artworkDto.setImage(oldArtwork.getImage());
            artworkDto.setSavefilename(oldArtwork.getSavefilename());
        }

        // 예술품 수정 실패 시 오류 결과 반환
        if (artworkService.updateArtwork(artworkDto) != 1) {
            throw new SQLException("예술품 수정에 실패했습니다.");
        }

        // 예술품 수정 성공 시 성공 결과 반환
        return ok("예술품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/toggleArtworkDisplay")
    public ResponseEntity<?> toggleArtworkDisplay(@RequestParam(value = "aseq") Integer aseq) throws Exception {
        // 예술품 정보 존재 여부 확인
        artworkService.assertArtworkExists(aseq);

        // 전시 여부 변경 실패 시 오류 결과 반환
        if (artworkService.toggleArtworkDisplay(aseq) != 1) {
            throw new SQLException("전시 여부 변경에 실패했습니다.");
        }

        // 전시 여부 변경 성공 시 성공 결과 반환
        return ok("전시 여부가 변경되었습니다.");
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
