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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/artwork", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ArtworkRestController {

    private final ArtworkService artworkService;

    @Delegate
    private final ResponseService responseService;

    @GetMapping({"", "/"})
    public Pagination.Pair<ArtworkDto> list(
            @Validated(ArtworkFilter.ExcludeDisplay.class) ArtworkFilter filter,
            @Valid @ModelAttribute Pagination pagination
    ) {
        filter.setIncludeDisplay(false);
        pagination.setUrlTemplate("/artwork?page=%d" + filter.getUrlParam());
        return artworkService.getArtworksPair(filter, pagination);
    }

    @GetMapping({"/{aseq}", "/view/{aseq}"})
    public ArtworkDto view(
            @PathVariable(value = "aseq") Integer aseq
    ) throws NotFoundException {
        return artworkService.getArtwork(aseq);
    }

    @CheckAdmin
    @PostMapping("/update")
    public ResponseEntity<ResponseBody> update(
            @Valid @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        artworkService.updateArtwork(artworkDto, imageFile);
        return ok("예술품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/toggleArtworkDisplay")
    public ResponseEntity<ResponseBody> toggleArtworkDisplay(@RequestParam(value = "aseq") Integer aseq) throws Exception {
        artworkService.toggleArtworkDisplay(aseq);
        return ok("전시 여부가 변경되었습니다.");
    }

    @CheckAdmin
    @PostMapping("/write")
    public ResponseEntity<ResponseBody> write(
            @Valid @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        artworkService.createArtwork(artworkDto, imageFile);
        return ok("예술품이 등록되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    public ResponseEntity<ResponseBody> delete(@RequestParam(value = "aseq") Integer aseq) throws Exception {
        artworkService.deleteArtwork(aseq);
        return ok("예술품이 삭제되었습니다.", "/artwork");
    }

}
