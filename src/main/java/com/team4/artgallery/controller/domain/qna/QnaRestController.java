package com.team4.artgallery.controller.domain.qna;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.dto.view.Views;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/qnas", produces = MediaType.APPLICATION_JSON_VALUE)
public class QnaRestController implements QnaRestControllerDocs {

    private final QnaService qnaService;

    public QnaRestController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping
    @JsonView(Views.Summary.class)
    public List<QnaDto> getList(
            @ParameterObject
            QnaFilter filter,
            @Valid
            Pagination pagination
    ) {
        return qnaService.getInquiries(filter, pagination);
    }

    @GetMapping("{qseq}")
    @JsonView(Views.Detail.class)
    public QnaDto getById(
            @PathVariable(name = "qseq")
            String qseq
    ) throws NotFoundException, UnauthorizedException {
        try {
            return qnaService.getInquiry(Integer.parseInt(qseq));
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Detail.class)
    public QnaDto create(
            @Valid
            QnaDto qnaDto
    ) throws NotFoundException, UnauthorizedException, SqlException {
        qnaService.createInquiry(qnaDto);
        return qnaDto;
    }

    @PutMapping("{qseq}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable(name = "qseq")
            String qseq,
            @Valid
            QnaDto qnaDto
    ) throws NotFoundException, UnauthorizedException, SqlException {
        try {
            qnaDto.setQseq(Integer.parseInt(qseq));
            qnaService.updateInquiry(qnaDto);
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @PutMapping("{qseq}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateReply(
            @PathVariable(name = "qseq")
            String qseq,
            @RequestParam(name = "reply")
            String reply
    ) throws NotFoundException, SqlException {
        try {
            qnaService.updateReply(Integer.parseInt(qseq), reply);
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @DeleteMapping("{qseq}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable(name = "qseq")
            String qseq
    ) throws SqlException, NotFoundException {
        try {
            qnaService.deleteInquiry(Integer.parseInt(qseq));
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/authorize")
    public Object authorize(
            @RequestParam(name = "qseq")
            Integer qseq,
            @RequestParam(name = "mode")
            String mode,
            @RequestParam(name = "pwd", required = false)
            String pwd
    ) throws UnauthorizedException, BadRequestException, URISyntaxException {
        return qnaService.authorize(qseq, mode, pwd);
    }

}
