package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.*;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.dto.qna.QnaUpdateDto;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.entity.QnaEntity;
import com.team4.artgallery.repository.QnaRepository;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberService memberService;
    private final SessionProvider sessionProvider;

    public QnaService(QnaRepository qnaRepository, MemberService memberService, SessionProvider sessionProvider) {
        this.qnaRepository = qnaRepository;
        this.memberService = memberService;
        this.sessionProvider = sessionProvider;
    }

    /**
     * 문의글 정보를 추가합니다
     *
     * @param qnaDto 문의글 정보
     * @throws SqlException 문의글 정보 추가에 실패한 경우 예외 발생
     */
    public QnaEntity createInquiry(QnaUpdateDto qnaDto) throws SqlException {
        QnaEntity qnaEntity = qnaRepository.save(qnaDto.toEntity(null));
        saveAuthorize(qnaEntity.getQseq());
        return qnaEntity;
    }

    /**
     * 검색 조건에 따라 문의글 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 문의글 목록
     */
    public Page<QnaEntity> getInquiries(QnaFilter filter, Pagination pagination) {
        MemberEntity loginMember = memberService.getLoginMember();
        if (loginMember == null || !loginMember.isAdmin()) {
            filter = new QnaFilter();
        }

        Page<QnaEntity> result = qnaRepository.findAll(filter.toSpec(), pagination.toPageable());
        pagination.setItemCount((int) result.getTotalElements());
        return result;
    }

    /**
     * 문의글 정보를 가져옵니다.
     *
     * @param qseq 문의글 번호 (qna sequence)
     * @return 문의글 정보
     * @throws NotFoundException     문의글 정보를 찾을 수 없는 경우 예외 발생
     * @throws UnauthorizedException 접근 권한이 없는 경우 예외 발생
     */
    public QnaEntity getInquiry(int qseq) throws NotFoundException, UnauthorizedException {
        Assert.trueOrUnauthorized(authorizeForRestrict(qseq), "접근 권한이 없습니다.");

        return qnaRepository.findById(qseq)
                .orElseThrow(() -> new NotFoundException("문의글 정보를 찾을 수 없습니다."));
    }

    /**
     * 문의글 정보를 수정합니다
     *
     * @param qnaDto 문의글 정보
     * @throws NotFoundException     문의글 정보를 찾을 수 없는 경우 예외 발생
     * @throws UnauthorizedException 접근 권한이 없는 경우 예외 발생
     */
    public void updateInquiry(int qseq, QnaUpdateDto qnaDto) throws NotFoundException, UnauthorizedException {
        Assert.trueOrUnauthorized(authorizeForPersonal(qseq), "접근 권한이 없습니다.");
        qnaRepository.save(qnaDto.toEntity(qseq));
    }

    /**
     * 문의글 답변을 수정합니다.
     *
     * @param qseq  문의글 번호 (qna sequence)
     * @param reply 답변 내용
     * @throws NotFoundException 문의글 정보를 찾을 수 없는 경우 예외 발생
     */
    public void updateReply(int qseq, String reply) throws NotFoundException {
        qnaRepository.findById(qseq).orElseThrow(() -> new NotFoundException("문의글 정보를 찾을 수 없습니다."));
        qnaRepository.updateReplyById(qseq, reply);
    }

    /**
     * 문의글 정보를 삭제합니다.
     *
     * @param qseq 문의글 번호 (qna sequence)
     * @throws SqlException 문의글 삭제에 실패한 경우 예외 발생
     */
    public void deleteInquiry(int qseq) throws SqlException {
        qnaRepository.deleteById(qseq);
    }

    private String hashQseq(int qseq) {
        return "qnaHash" + qseq;
    }

    /**
     * 문의글에 대한 인증 결과가 세션에 저장되어 있는지 확인합니다.
     *
     * @param qseq 문의글 번호
     * @return 인증 결과가 세션에 저장되어 있으면 true, 그렇지 않으면 false
     */
    public boolean isAuthorized(int qseq) {
        return sessionProvider.getSession().getAttribute(hashQseq(qseq)) != null;
    }

    /**
     * 문의글에 대한 인증 결과를 세션에 저장합니다
     *
     * @param qseq 문의글 번호
     */
    public void saveAuthorize(int qseq) {
        sessionProvider.getSession().setAttribute(hashQseq(qseq), true);
    }

    /**
     * 문의글에 대한 인증을 처리합니다
     *
     * @param qseq 문의글 번호
     * @param mode 인증 모드
     * @param pwd  문의글 비밀번호
     * @return 인증 결과에 따른 처리 결과
     * @throws UnauthorizedException 비밀번호가 입력되지 않은 경우 예외 발생
     * @throws BadRequestException   잘못된 모드 혹은 비밀번호가 일치하지 않는 경우 예외 발생
     * @throws URISyntaxException    URI 생성에 실패한 경우 예외 발생
     */
    public Object authorize(int qseq, String mode, String pwd) throws UnauthorizedException, BadRequestException, URISyntaxException {
        switch (mode) {
            case "view":
                if (authorizeForRestrict(qseq)) {
                    return new URI("/qna/" + qseq);
                }
                break;
            case "delete":
                if (authorizeForPrivilege(qseq)) {
                    deleteInquiry(qseq);
                    return new ResponseDto("문의글이 삭제되었습니다.", "/qna");
                }
                break;
            case "update":
                if (authorizeForPersonal(qseq)) {
                    return new URI("/qna/write?qseq=" + qseq);
                }
                break;
            default:
                throw new NotImplementsException("지원하지 않는 모드입니다.");
        }

        Assert.trueOrUnauthorized(pwd != null && !pwd.trim().isEmpty(), "비밀번호를 입력해주세요.");
        Assert.isTrue(authorizeWithPwd(qseq, pwd), "비밀번호가 일치하지 않습니다.", BadRequestException::new);

        return authorize(qseq, mode, null);
    }

    /**
     * 비밀번호를 이용해 문의글에 대한 인증을 처리합니다
     *
     * @param qseq 문의글 번호
     * @param pwd  문의글 비밀번호
     * @return 인증 성공 시 true, 실패 시 false
     * @throws NotFoundException 문의글 정보를 찾을 수 없는 경우 예외 발생
     */
    public boolean authorizeWithPwd(int qseq, String pwd) throws NotFoundException {
        if (isAuthorized(qseq)) {
            return true;
        }

        QnaEntity qnaEntity = qnaRepository.findById(qseq)
                .orElseThrow(() -> new NotFoundException("문의글 정보를 찾을 수 없습니다."));
        if (!qnaEntity.getPwd().equals(pwd)) {
            return false;
        }

        saveAuthorize(qseq);
        return true;
    }

    /**
     * 문의글에 대한 인증을 처리합니다 (글 수정 등)
     * <p>
     * PERSONAL(개인) : 작성자만 접근 가능
     */
    public boolean authorizeForPersonal(int qseq) {
        return isAuthorized(qseq);
    }

    /**
     * 문의글에 대한 인증을 처리합니다 (글 삭제 등)
     * <p>
     * PRIVILEGE(허가) : 작성자 및 관리자만 접근 가능
     */
    public boolean authorizeForPrivilege(int qseq) {
        return authorizeForPersonal(qseq) || memberService.isAdmin();
    }

    /**
     * 문의글에 대한 인증을 처리합니다 (글 조회 등)
     * <p>
     * RESTRICT(제한) : 공개글이거나 작성자 및 관리자만 접근 가능
     */
    public boolean authorizeForRestrict(int qseq) {
        QnaEntity qnaEntity = qnaRepository.findById(qseq)
                .orElseThrow(() -> new NotFoundException("문의글 정보를 찾을 수 없습니다."));
        return authorizeForPrivilege(qseq) || qnaEntity.getDisplay();
    }

}
