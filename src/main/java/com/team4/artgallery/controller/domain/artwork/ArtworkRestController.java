package com.team4.artgallery.controller.domain.artwork;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.artwork.ArtworkCreateDto;
import com.team4.artgallery.dto.artwork.ArtworkUpdateDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.dto.request.DisplayRequest;
import com.team4.artgallery.dto.view.Views;
import com.team4.artgallery.entity.ArtworkEntity;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/artworks", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArtworkRestController implements ArtworkRestControllerDocs {

    private final ArtworkService artworkService;

    public ArtworkRestController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping(path = "")
    @JsonView(Views.Summary.class)
    public Page<ArtworkEntity> getList(
            @Validated(ArtworkFilter.ExcludeDisplay.class)
            ArtworkFilter filter,
            @Valid
            Pagination pagination
    ) {
        return artworkService.getArtworks(
                filter.setDisplayyn("Y").setIncludeDisplay(false),
                pagination
        );
    }

    @CheckAdmin
    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Identifier.class)
    public ArtworkEntity create(
            @Valid
            ArtworkCreateDto artworkCreateDto
    ) throws NotFoundException, SqlException, FileException {
        return artworkService.createArtwork(artworkCreateDto);
    }

    @GetMapping(path = "{aseq}")
    @JsonView(Views.Detail.class)
    public ArtworkEntity getById(
            @PathVariable("aseq")
            int aseq
    ) throws NotFoundException {
        return artworkService.getArtwork(aseq);
    }

    @CheckAdmin
    @PutMapping(path = "{aseq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("aseq")
            String aseq,
            @Valid
            ArtworkUpdateDto artworkUpdateDto
    ) throws NotFoundException, SqlException, FileException {
        try {
            artworkService.updateArtwork(Integer.parseInt(aseq), artworkUpdateDto);
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @DeleteMapping(path = "{aseq}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("aseq")
            String aseq
    ) throws SqlException {
        try {
            artworkService.deleteArtwork(Integer.parseInt(aseq));
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @PutMapping("/{aseq}/display")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateDisplay(
            @PathVariable("aseq")
            String aseq,
            @RequestBody
            DisplayRequest request
    ) throws SqlException {
        try {
            artworkService.updateDisplay(Integer.parseInt(aseq), request.display());
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @GetMapping(path = "random")
    @JsonView(Views.Summary.class)
    public List<ArtworkEntity> getRandomList(
            @RequestParam("count")
            Integer count
    ) {
        return artworkService.getRandomArtworks(count);
    }

}
