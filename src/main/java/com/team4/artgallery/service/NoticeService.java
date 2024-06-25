package com.team4.artgallery.service;

import com.team4.artgallery.dao.INoticeDao;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.util.Pagination;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    @Delegate
    private final INoticeDao noticeDao;

    private String hashNseq(int nseq) {
        return "noticeHash" + nseq;
    }

    /**
     * 소식지에 대한 조회 기록이 세션에 저장되어 있는지 확인합니다.
     *
     * @param session 세션 객체
     * @param nseq    소식지 번호 (notice sequence)
     * @return 조회 기록이세션에 저장되어 있으면 true, 그렇지 않으면 false
     */
    public boolean checkReadStatus(HttpSession session, int nseq) {
        return session.getAttribute(hashNseq(nseq)) != null;
    }

    /**
     * 소식지를 읽은 것으로 처리합니다.
     *
     * @param session 세션 객체
     * @param nseq    소식지 번호 (notice sequence)
     */
    public void markAsRead(HttpSession session, int nseq) {
        // 소식지 정보를 가져올 수 없는 경우 무시
        if (noticeDao.getNotice(nseq) == null) {
            return;
        }

        // 소식지를 읽은 기록이 있는 경우 무시
        if (checkReadStatus(session, nseq)) {
            return;
        }

        // 소식지의 조회수를 증가시키고, 소식지를 읽은 것으로 처리
        noticeDao.increaseReadCount(nseq);
        session.setAttribute(hashNseq(nseq), true);
    }

    /**
     * 소식지 목록을 가져옵니다.
     *
     * @param page   페이지 번호
     * @param filter 검색 조건 (검색 조건이 비어있으면 전체 소식지 목록을 가져옵니다)
     * @param path   페이지 경로
     * @return 소식지 목록과 페이지네이션 정보
     */
    public Pagination.Pair<NoticeDto> getOrSearchNotices(int page, INoticeDao.Filter filter, String path) {
        // 검색 조건이 없을 경우 전체 소식지 목록을 가져옵니다.
        if (filter.isEmpty()) {
            Pagination pagination = new Pagination()
                    .setCurrentPage(page)
                    .setItemCount(countNotices())
                    .setUrlTemplate("/" + path + "?page=%d");

            return pagination.pair(getNotices(pagination));
        }

        // 검색 조건이 있을 경우 검색 결과를 가져옵니다.
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(countSearchNotices(filter))
                .setUrlTemplate("/" + path + "?page=%d&" + filter.toUrlParam());
        return pagination.pair(searchNotices(filter, pagination));
    }

}
