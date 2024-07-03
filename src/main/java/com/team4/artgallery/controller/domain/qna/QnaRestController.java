package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.service.helper.ResponseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/qna", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QnaRestController {

    private final QnaService qnaService;

    @Delegate
    private final ResponseService responseHelper;

    @PostMapping("/write")
    public ResponseEntity<?> write(@Valid QnaDto qnaDto) {
        // 문의글 작성에 실패한 경우 500 에러 반환
        if (qnaService.createInquiry(qnaDto) == 0) {
            return internalServerError("문의 작성에 실패했습니다.");
        }

        // 문의글 작성에 성공한 경우 200 성공 반환
        return ok("문의 작성이 완료되었습니다.", "/qna/" + qnaDto.getQseq());
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid QnaDto qnaDto, HttpSession session) {
        int qseq = qnaDto.getQseq();

        // 문의글 정보가 없는 경우 404 실패 반환
        if (qnaService.getInquiry(qseq) == null) {
            return notFound("문의글 정보를 찾을 수 없습니다.", "/qna");
        }

        // 접근 권한이 없는 경우 403 실패 반환
        if (!qnaService.authorizeForPersonal(session, qseq)) {
            return forbidden("접근 권한이 없습니다.");
        }

        // 문의글 수정에 실패한 경우 500 에러 반환
        if (qnaService.updateInquiry(qnaDto) == 0) {
            return internalServerError("문의 수정에 실패했습니다.");
        }

        // 문의글 수정에 성공한 경우 200 성공 반환
        return ok("문의 수정이 완료되었습니다.", "/qna/" + qseq);
    }

    @CheckAdmin
    @PostMapping("/reply")
    public ResponseEntity<?> reply(
            @RequestParam(value = "qseq") Integer qseq,
            @RequestParam(value = "reply") String reply
    ) {
        // 문의글 정보가 없는 경우 404 실패 반환
        if (qnaService.getInquiry(qseq) == null) {
            return notFound("문의글 정보를 찾을 수 없습니다.", "/qna");
        }

        // 문의글 답변 수정에 실패한 경우 500 에러 반환
        if (qnaService.updateReply(qseq, reply) == 0) {
            return internalServerError("문의 답변에 실패했습니다.");
        }

        // 문의글 답변 수정에 성공한 경우 200 성공 반환
        return ok("문의 답변이 완료되었습니다.");
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(
            @RequestParam(value = "qseq") Integer qseq,
            @RequestParam(value = "mode") String mode,
            @RequestParam(value = "pwd", required = false) String pwd,
            Model model,
            HttpSession session
    ) {
        switch (mode) {
            case "view":
                if (qnaService.authorizeForRestrict(session, qseq)) {
                    return ok("", "/qna/" + qseq);
                }
                break;
            case "delete":
                if (qnaService.authorizeForPrivilege(session, qseq)) {
                    if (qnaService.deleteInquiry(qseq) == 0) {
                        return internalServerError("문의글 삭제에 실패했습니다.");
                    }

                    return ok("문의글이 삭제되었습니다.", "/qna");
                }
                break;
            case "update":
                if (qnaService.authorizeForPersonal(session, qseq)) {
                    return ok("", "/qna/update?qseq=" + qseq);
                }
                break;
            default:
                return badRequest("잘못된 요청입니다.");
        }

        if (pwd == null || pwd.trim().isEmpty()) {
            model.addAttribute("qseq", qseq);
            return unauthorized("비밀번호를 입력해주세요.");
        }

        if (!qnaService.authorizeWithPwd(session, qseq, pwd)) {
            return badRequest("비밀번호가 일치하지 않습니다.");
        }

        return authorize(qseq, mode, null, model, session);
    }

}
