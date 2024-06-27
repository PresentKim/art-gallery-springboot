package com.team4.artgallery.service;

import com.team4.artgallery.dao.INoticeDao;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
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

}
