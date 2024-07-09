package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.service.QnaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/qna", produces = MediaType.APPLICATION_JSON_VALUE)
public class QnaRestController {

    private final QnaService qnaService;

    public QnaRestController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto write(
            @Valid
            QnaDto qnaDto
    ) throws NotFoundException, UnauthorizedException, SqlException {
        if (qnaDto.getQseq() != null) {
            qnaService.updateInquiry(qnaDto);
        } else {
            qnaService.createInquiry(qnaDto);
        }
        return new ResponseDto("문의글 작성이 완료되었습니다.", "/qna/" + qnaDto.getQseq());
    }

    @CheckAdmin
    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public Object reply(
            @RequestParam(name = "qseq")
            Integer qseq,
            @RequestParam(name = "reply")
            String reply
    ) throws NotFoundException, SqlException {
        qnaService.updateReply(qseq, reply);
        return "문의 답변이 완료되었습니다.";
    }

    @CheckAdmin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "문의글을 선택해주세요")
            @RequestParam(name = "qseq", required = false) List<Integer> qseq
    ) throws SqlException {
        qnaService.deleteInquiry(qseq);
        return new ResponseDto("문의글 정보를 제거했습니다", ":reload");
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
