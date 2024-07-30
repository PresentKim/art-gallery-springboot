package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.dto.notice.NoticeDto;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.entity.NoticeEntity;
import com.team4.artgallery.repository.NoticeRepository;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ReadCountHelper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final SessionProvider sessionProvider;

    public NoticeService(NoticeRepository noticeRepository, SessionProvider sessionProvider) {
        this.noticeRepository = noticeRepository;
        this.sessionProvider = sessionProvider;
    }

    /**
     * 소식지 정보를 추가합니다
     *
     * @param noticeDto   소식지 정보
     * @param loginMember 로그인한 회원 정보
     * @throws SqlException 소식지 정보 추가에 실패한 경우 예외 발생
     */
    public NoticeEntity createNotice(NoticeDto noticeDto, MemberEntity loginMember) throws SqlException {
        return noticeRepository.save(noticeDto.toEntity(null, loginMember));
    }

    /**
     * 검색 조건에 따라 소식지 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 소식지 목록
     */
    public Page<NoticeEntity> getNotices(NoticeFilter filter, Pagination pagination) {
        Page<NoticeEntity> result = noticeRepository.findAll(filter.toSpec(), pagination.toPageable());
        pagination.setItemCount((int) result.getTotalElements());
        return result;
    }

    /**
     * 최근 소식지 목록을 가져옵니다.
     *
     * @param count 최근 소식지 개수
     */
    public List<NoticeEntity> getRecentNotices(int count) {
        return noticeRepository.findRecentNotices(count);
    }

    /**
     * 소식지 정보를 가져옵니다.
     *
     * @param nseq 소식지 번호 (notice sequence)
     * @return 소식지 정보
     * @throws NotFoundException 소식지 정보를 찾을 수 없는 경우 예외 발생
     */
    public NoticeEntity getNotice(int nseq) {
        return noticeRepository.findById(nseq)
                .orElseThrow(() -> new NotFoundException("소식지 정보를 찾을 수 없습니다."));
    }

    /**
     * 소식지 정보를 수정합니다
     *
     * @param noticeDto   소식지 정보
     * @param loginMember 로그인한 회원 정보
     * @throws NotFoundException 소식지 정보를 찾을 수 없는 경우 예외 발생
     */
    public void updateNotice(int nseq, NoticeDto noticeDto, MemberEntity loginMember) throws NotFoundException {
        noticeRepository.save(noticeDto.toEntity(nseq, loginMember));
    }

    /**
     * 소식지 정보를 삭제합니다.
     *
     * @param nseq 소식지 번호
     * @throws NotFoundException 소식지 삭제에 실패한 경우 예외 발생
     */
    public void deleteNotice(Integer nseq) throws NotFoundException {
        noticeRepository.deleteById(nseq);
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
                noticeRepository::increaseReadCountById
        );
    }

}
