package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/artwork", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ArtworkRestController {

    private final ArtworkService artworkService;

    @GetMapping({"", "/"})
    public Pagination.Pair<ArtworkDto> list(
            @Validated(ArtworkFilter.ExcludeDisplay.class)
            ArtworkFilter filter,
            @Valid
            Pagination pagination
    ) {
        filter.setIncludeDisplay(false);
        pagination.setUrlTemplate("/artwork?page=%d" + filter.getUrlParam());
        return artworkService.getArtworksPair(filter, pagination);
    }

    @GetMapping("/{aseq}")
    public ArtworkDto view(
            @PathVariable(name = "aseq")
            Integer aseq
    ) throws NotFoundException {
        return artworkService.getArtwork(aseq);
    }

    @CheckAdmin
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto update(
            @Valid
            ArtworkDto artworkDto,
            @RequestParam(name = "imageFile", required = false)
            MultipartFile imageFile
    ) throws SqlException, FileException {
        artworkService.updateArtwork(artworkDto, imageFile);
        return new ResponseDto("예술품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/toggleArtworkDisplay")
    @ResponseStatus(HttpStatus.CREATED)
    public Object toggleArtworkDisplay(
            @RequestParam(name = "aseq")
            Integer aseq
    ) throws SqlException {
        artworkService.toggleArtworkDisplay(aseq);
        return "전시 여부가 변경되었습니다.";
    }

    @CheckAdmin
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto write(
            @Valid
            ArtworkDto artworkDto,
            @Valid
            @NotNull(message = "이미지 파일을 업로드해주세요.")
            @RequestParam(name = "imageFile", required = false)
            MultipartFile imageFile
    ) throws SqlException, FileException {
        artworkService.createArtwork(artworkDto, imageFile);
        return new ResponseDto("예술품이 등록되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto delete(
            @RequestParam(name = "aseq")
            Integer aseq
    ) throws SqlException {
        artworkService.deleteArtwork(aseq);
        return new ResponseDto("예술품이 삭제되었습니다.", "/artwork");
    }

}
