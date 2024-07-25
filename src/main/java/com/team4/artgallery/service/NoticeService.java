package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dao.INoticeDao;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ReadCountHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final INoticeDao noticeDao;

    private final SessionProvider sessionProvider;

    public NoticeService(INoticeDao noticeDao, SessionProvider sessionProvider) {
        this.noticeDao = noticeDao;
        this.sessionProvider = sessionProvider;
    }

    /**
     * 소식지 정보를 추가합니다
     *
     * @param noticeDto   소식지 정보
     * @param loginMember 로그인한 회원 정보
     * @throws SqlException 소식지 정보 추가에 실패한 경우 예외 발생
     */
    public void createNotice(NoticeDto noticeDto, MemberEntity loginMember) throws SqlException {
        noticeDto.setAuthor(loginMember.id());
        noticeDao.createNotice(noticeDto);
    }

    /**
     * 검색 조건에 따라 소식지 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 소식지 목록
     */
    public List<NoticeDto> getNotices(NoticeFilter filter, Pagination pagination) {
        return noticeDao.getNotices(
                filter,
                pagination.setUrlTemplateFromFilter(filter).setItemCount(noticeDao.countNotices(filter))
        );
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
        return noticeDao.getNotice(nseq);
    }

    /**
     * 소식지 정보를 수정합니다
     *
     * @param noticeDto   소식지 정보
     * @param loginMember 로그인한 회원 정보
     * @throws NotFoundException 소식지 정보를 찾을 수 없는 경우 예외 발생
     */
    public void updateNotice(NoticeDto noticeDto, MemberEntity loginMember) throws NotFoundException {
        noticeDto.setAuthor(loginMember.id());
        noticeDao.updateNotice(noticeDto);
    }

    /**
     * 소식지 정보를 삭제합니다.
     *
     * @param nseq 소식지 번호
     * @throws NotFoundException 소식지 삭제에 실패한 경우 예외 발생
     */
    public void deleteNotice(Integer nseq) throws NotFoundException {
        noticeDao.deleteNotice(nseq);
    }

    /**
     * 소식지를 읽은 것으로 처리합니다.
     *
     * @param nseq 소식지 번호 (notice sequence)
     * @throws NotFoundException 소식지 정보를 찾을 수 없는 경우 예외 발생
     */
    public void increaseReadCountIfNew(int nseq) throws NotFoundException {
        ReadCountHelper.increaseReadCountIfNew(
                nseq,
                sessionProvider.getSession(),
                "notice-read-",
                noticeDao::increaseReadCount
        );
    }

}
