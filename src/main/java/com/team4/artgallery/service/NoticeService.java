package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dao.INoticeDao;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final INoticeDao noticeDao;

    private final SessionProvider sessionProvider;

    /**
     * 소식지 정보를 추가합니다
     *
     * @param noticeDto   소식지 정보
     * @param loginMember 로그인 멤버 정보
     * @throws SqlException 소식지 정보 추가에 실패한 경우 예외 발생
     */
    public void createNotice(NoticeDto noticeDto, MemberDto loginMember) throws ResponseStatusException {
        noticeDto.setAuthor(loginMember.getId());
        noticeDao.createNotice(noticeDto);
    }

    /**
     * 검색 조건에 따라 소식지 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 소식지 목록과 페이지 정보
     */
    public Pagination.Pair<NoticeDto> getNoticesPair(NoticeFilter filter, Pagination pagination) {
        return pagination
                .setItemCount(noticeDao.countNotices(filter))
                .pair(noticeDao.getNotices(filter, pagination));
    }

    /**
     * 최근 소식지 목록을 가져옵니다.
     *
     * @param count 최근 소식지 개수
     */
    public List<NoticeDto> getRecentNotices(int count) {
        return noticeDao.getNotices(null, new Pagination().setItemCount(0).setDisplayCount(count));
    }

    /**
     * 소식지 정보를 가져옵니다.
     *
     * @param nseq 소식지 번호 (notice sequence)
     * @return 소식지 정보
     * @throws NotFoundException 소식지 정보를 찾을 수 없는 경우 예외 발생
     */
    public NoticeDto getNotice(int nseq) {
        NoticeDto noticeDto = noticeDao.getNotice(nseq);
        Assert.exists(noticeDto, "소식지 정보를 찾을 수 없습니다.");

        return noticeDto;
    }

    /**
     * 소식지 정보를 수정합니다
     *
     * @param noticeDto   소식지 정보
     * @param loginMember 로그인 멤버 정보
     * @throws NotFoundException 소식지 정보를 찾을 수 없는 경우 예외 발생
     * @throws SqlException      소식지 정보 수정에 실패한 경우 예외 발생
     */
    public void updateNotice(NoticeDto noticeDto, MemberDto loginMember) throws ResponseStatusException {
        NoticeDto oldNotice = getNotice(noticeDto.getNseq());
        Assert.exists(oldNotice, "소식지 정보를 찾을 수 없습니다.");

        noticeDto.setAuthor(loginMember.getId());
        noticeDao.updateNotice(noticeDto);
    }

    /**
     * 소식지 정보를 삭제합니다.
     *
     * @param nseqList 소식지 번호 목록
     * @throws SqlException 소식지 삭제에 실패한 경우 예외 발생
     */
    public void deleteNotice(List<Integer> nseqList) throws SqlException {
        noticeDao.deleteNotices(nseqList);
    }

    /**
     * 소식지 정보를 삭제합니다.
     *
     * @param nseq 소식지 번호 (notice sequence)
     * @throws SqlException 소식지 삭제에 실패한 경우 예외 발생
     */
    public void deleteNotice(int nseq) throws SqlException {
        noticeDao.deleteNotice(nseq);
    }

    private String hashNseq(int nseq) {
        return "noticeHash" + nseq;
    }

    /**
     * 소식지에 대한 조회 기록이 세션에 저장되어 있는지 확인합니다.
     *
     * @param nseq 소식지 번호 (notice sequence)
     * @return 조회 기록이세션에 저장되어 있으면 true, 그렇지 않으면 false
     */
    public boolean checkReadStatus(int nseq) {
        return sessionProvider.getSession().getAttribute(hashNseq(nseq)) != null;
    }

    /**
     * 소식지를 읽은 것으로 처리합니다.
     *
     * @param nseq 소식지 번호 (notice sequence)
     * @throws NotFoundException 갤러리 정보를 찾을 수 없는 경우 예외 발생
     * @throws SqlException      갤러리 조회수 증가에 실패한 경우 예외 발생
     */
    public void markAsRead(int nseq) throws NotFoundException, SqlException {
        Assert.exists(noticeDao.getNotice(nseq), "소식지 정보를 찾을 수 없습니다.");

        // 소식지를 읽은 기록이 있는 경우 무시
        if (checkReadStatus(nseq)) {
            return;
        }

        // 소식지의 조회수를 증가시키고, 소식지를 읽은 것으로 처리
        noticeDao.increaseReadCount(nseq);
        sessionProvider.getSession().setAttribute(hashNseq(nseq), true);
    }

}
