package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Assert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/qna", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QnaRestController {

    private final QnaService qnaService;

    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto write(
            @Valid
            QnaDto qnaDto
    ) {
        qnaService.createInquiry(qnaDto);
        qnaService.authorize(qnaDto.getQseq());
        return new ResponseDto("문의글 작성이 완료되었습니다.", "/qna/" + qnaDto.getQseq());
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto update(
            @Valid
            QnaDto qnaDto
    ) {
        int qseq = qnaDto.getQseq();
        Assert.exists(qnaService.getInquiry(qseq), "문의글 정보를 찾을 수 없습니다.");
        Assert.trueOrUnauthorized(qnaService.authorizeForPersonal(qseq), "접근 권한이 없습니다.");

        qnaService.updateInquiry(qnaDto);
        return new ResponseDto("문의 수정이 완료되었습니다.", "/qna/" + qseq);
    }

    @CheckAdmin
    @PostMapping("/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public Object reply(
            @RequestParam(name = "qseq")
            Integer qseq,
            @RequestParam(name = "reply")
            String reply
    ) {
        Assert.exists(qnaService.getInquiry(qseq), "문의글 정보를 찾을 수 없습니다.");

        qnaService.updateReply(qseq, reply);
        return "문의 답변이 완료되었습니다.";
    }

    @PostMapping("/authorize")
    public Object authorize(
            @RequestParam(name = "qseq")
            Integer qseq,
            @RequestParam(name = "mode")
            String mode,
            @RequestParam(name = "pwd", required = false)
            String pwd
    ) throws Exception {
        switch (mode) {
            case "view":
                if (qnaService.authorizeForRestrict(qseq)) {
                    return new URI("/qna/" + qseq);
                }
                break;
            case "delete":
                if (qnaService.authorizeForPrivilege(qseq)) {
                    qnaService.deleteInquiry(qseq);
                    return new ResponseDto("문의글이 삭제되었습니다.", "/qna");
                }
                break;
            case "update":
                if (qnaService.authorizeForPersonal(qseq)) {
                    return new URI("/qna/update/" + qseq);
                }
                break;
            default:
                throw new BadRequestException("잘못된 요청입니다.");
        }

        Assert.trueOrUnauthorized(pwd != null && !pwd.trim().isEmpty(), "비밀번호를 입력해주세요.");
        Assert.isTrue(qnaService.authorizeWithPwd(qseq, pwd), "비밀번호가 일치하지 않습니다.", BadRequestException::new);

        return authorize(qseq, mode, null);
    }

}
