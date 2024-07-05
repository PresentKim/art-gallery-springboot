package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.*;
import com.team4.artgallery.dao.IQnaDao;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final IQnaDao qnaDao;

    private final MemberService memberService;

    private final SessionProvider sessionProvider;

    /**
     * 문의글 정보를 추가합니다
     *
     * @param qnaDto 문의글 정보
     * @throws SqlException 문의글 정보 추가에 실패한 경우 예외 발생
     */
    public void createInquiry(QnaDto qnaDto) throws SqlException {
        qnaDao.createInquiry(qnaDto);
        saveAuthorize(qnaDto.getQseq());
    }

    /**
     * 검색 조건에 따라 문의글 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 문의글 목록
     */
    public List<QnaDto> getInquiries(QnaFilter filter, Pagination pagination) {
        return qnaDao.getInquiries(
                filter,
                pagination.setUrlTemplateFromFilter(filter).setItemCount(qnaDao.countInquiries(filter))
        );
    }

    /**
     * 문의글 정보를 가져옵니다.
     *
     * @param qseq 문의글 번호 (qna sequence)
     * @return 문의글 정보
     * @throws NotFoundException     문의글 정보를 찾을 수 없는 경우 예외 발생
     * @throws UnauthorizedException 접근 권한이 없는 경우 예외 발생
     */
    public QnaDto getInquiry(int qseq) throws NotFoundException, UnauthorizedException {
        QnaDto qnaDto = qnaDao.getInquiry(qseq);
        Assert.exists(qnaDto, "문의글 정보를 찾을 수 없습니다.");

        Assert.trueOrUnauthorized(authorizeForRestrict(qseq), "접근 권한이 없습니다.");
        return qnaDto;
    }

    /**
     * 문의글 정보를 수정합니다
     *
     * @param qnaDto 문의글 정보
     * @throws NotFoundException     문의글 정보를 찾을 수 없는 경우 예외 발생
     * @throws UnauthorizedException 접근 권한이 없는 경우 예외 발생
     */
    public void updateInquiry(QnaDto qnaDto) throws NotFoundException, UnauthorizedException {
        Assert.trueOrUnauthorized(authorizeForPersonal(qnaDto.getQseq()), "접근 권한이 없습니다.");
        qnaDao.updateInquiry(qnaDto);
    }

    /**
     * 문의글 답변을 수정합니다.
     *
     * @param qseq  문의글 번호 (qna sequence)
     * @param reply 답변 내용
     * @throws NotFoundException 문의글 정보를 찾을 수 없는 경우 예외 발생
     */
    public void updateReply(int qseq, String reply) throws NotFoundException {
        qnaDao.updateReply(qseq, reply);
    }

    /**
     * 문의글 정보를 삭제합니다.
     *
     * @param qseqList 문의글 번호 목록
     * @throws NotFoundException 문의글 삭제에 실패한 경우 예외 발생
     */
    public void deleteInquiry(List<Integer> qseqList) throws NotFoundException {
        qnaDao.deleteInquiries(qseqList);
    }

    /**
     * 문의글 정보를 삭제합니다.
     *
     * @param qseq 문의글 번호 (qna sequence)
     * @throws SqlException 문의글 삭제에 실패한 경우 예외 발생
     */
    public void deleteInquiry(int qseq) throws SqlException {
        qnaDao.deleteInquiry(qseq);
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
                    return new URI("/qna/update/" + qseq);
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
     */
    public boolean authorizeWithPwd(int qseq, String pwd) {
        if (isAuthorized(qseq)) {
            return true;
        }

        QnaDto qnaDto = qnaDao.getInquiry(qseq);
        if (qnaDto == null || !qnaDto.getPwd().equals(pwd)) {
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
        return authorizeForPrivilege(qseq) || qnaDao.getInquiry(qseq).isDisplay();
    }

}
