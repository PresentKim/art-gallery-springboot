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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/artworks", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArtworkRestController implements ArtworkRestControllerDocs {

    private final ArtworkService artworkService;

    public ArtworkRestController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping(path = "")
    public Pagination.Pair<ArtworkDto> getList(
            @Validated(ArtworkFilter.ExcludeDisplay.class)
            ArtworkFilter filter,
            @Valid
            Pagination pagination
    ) {
        return pagination.pair(artworkService.getArtworks(
                filter.setDisplayyn("Y").setIncludeDisplay(false),
                pagination)
        );
    }

    @CheckAdmin
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto create(
            @Valid
            ArtworkDto artworkDto,
            @Valid
            @RequestPart(name = "imageFile")
            MultipartFile imageFile
    ) throws NotFoundException, SqlException, FileException {
        artworkService.createArtwork(artworkDto, imageFile);
        return new ResponseDto("예술품이 등록되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    @GetMapping(path = "{aseq}")
    public ArtworkDto getById(
            @PathVariable("aseq")
            int aseq
    ) throws NotFoundException {
        return artworkService.getArtwork(aseq);
    }

    @CheckAdmin
    @PutMapping(path = "{aseq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto update(
            @PathVariable("aseq")
            String aseq,
            @Valid
            ArtworkDto artworkDto,
            @Valid
            @RequestPart(name = "imageFile", required = false)
            MultipartFile imageFile
    ) throws NotFoundException, SqlException, FileException {
        try {
            artworkDto.setAseq(Integer.parseInt(aseq));
            artworkService.updateArtwork(artworkDto, imageFile);
            return new ResponseDto("예술품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @DeleteMapping(path = "{aseq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("aseq")
            Integer aseq
    ) throws SqlException {
        artworkService.deleteArtwork(aseq);
    }

    @CheckAdmin
    @PutMapping("/{aseq}/toggleDisplay")
    @ResponseStatus(HttpStatus.CREATED)
    public Object toggleArtworkDisplay(
            @PathVariable("aseq")
            String aseq
    ) throws SqlException {
        try {
            artworkService.toggleArtworkDisplay(Integer.parseInt(aseq));
            return "전시 여부가 변경되었습니다.";
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @GetMapping(path = "random")
    public List<ArtworkDto> getRandomList(
            @RequestParam("count")
            Integer count
    ) {
        return artworkService.getRandomArtworks(count);
    }

}
