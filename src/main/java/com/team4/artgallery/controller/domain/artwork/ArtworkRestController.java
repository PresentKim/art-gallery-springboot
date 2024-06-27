package com.team4.artgallery.controller.domain.artwork;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/artwork", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ArtworkRestController {

    private final ArtworkService artworkService;

    @GetMapping({"", "/"})
    public Pagination.Pair<ArtworkDto> list(
            @Valid @ModelAttribute ArtworkFilter filter,
            @Valid @ModelAttribute Pagination pagination
    ) {
        filter.setIncludeDisplay(false);
        pagination.setUrlTemplate("/artwork?page=%d" + filter.toUrlParam());
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
        return artworkService.updateArtwork(artworkDto, imageFile);
    }

    @CheckAdmin
    @PostMapping("/toggleArtworkDisplay")
    public ResponseEntity<ResponseBody> toggleArtworkDisplay(@RequestParam(value = "aseq") Integer aseq) throws Exception {
        return artworkService.toggleArtworkDisplay(aseq);
    }

    @CheckAdmin
    @PostMapping("/write")
    public ResponseEntity<ResponseBody> write(
            @Valid @ModelAttribute ArtworkDto artworkDto,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws Exception {
        return artworkService.createArtwork(artworkDto, imageFile);
    }

    @CheckAdmin
    @PostMapping("/delete")
    public ResponseEntity<ResponseBody> delete(@RequestParam(value = "aseq") Integer aseq) throws Exception {
        return artworkService.deleteArtwork(aseq);
    }

}
