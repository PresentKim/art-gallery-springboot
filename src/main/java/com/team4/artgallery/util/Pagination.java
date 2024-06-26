package com.team4.artgallery.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Pagination {

    /**
     * 페이지네이션에 표시할 아이템의 총 갯수
     */
    private int itemCount;

    /**
     * 한 페이지당 표시할 아이템의 갯수
     */
    private int itemsPerPage = 10;

    /**
     * 표시될 페이지 범위
     * <p>
     * 이전/다음과 ...을 제외한 수입니다.
     * <p>
     * e.g. 5일 때 [이전 1 ... 4 5 6 7 8 ... 99 다음]
     */
    private int pageRange = 5;

    /**
     * 현재 페이지
     */
    private int currentPage;

    /**
     * 페이지의 URL 템플릿
     */
    private String urlTemplate = "";

    /**
     * 현재 페이지를 반환합니다.
     *
     * @return 현재 페이지
     */
    public int getCurrentPage() {
        return fitPage(currentPage);
    }

    /**
     * 현재 페이지의 URL을 반환합니다.
     *
     * @return 현재 페이지의 URL
     */
    public String getUrl() {
        return getUrl(getCurrentPage());
    }

    /**
     * 특정 페이지의 URL을 반환합니다. 현재 페이지일 경우 #을 반환합니다.
     *
     * @param page 페이지 번호
     * @return 페이지의 URL
     */
    public String getUrl(int page) {
        return currentPage == page ? "#" : String.format(urlTemplate, page);
    }

    /**
     * 페이지의 URL 템플릿을 설정합니다.
     *
     * @return Pagination 객체
     */
    public Pagination setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
        return this;
    }

    /**
     * 범위를 벗어나지 않는 페이지 번호를 반환합니다.
     *
     * @return 페이지 번호
     */
    private int fitPage(int page) {
        return Math.min(getMaxPage(), Math.max(1, page));
    }

    /**
     * 페이지의 총 갯수를 반환합니다.
     *
     * @return 페이지의 총 갯수
     */
    public int getMaxPage() {
        return (int) Math.ceil((double) itemCount / itemsPerPage);
    }

    /**
     * SQL 쿼리를 위한 개수 LIMIT 값을 반환합니다.
     *
     * @return 페이지 LIMIT 값
     */
    public int getLimit() {
        return itemsPerPage;
    }

    /**
     * SQL 쿼리를 위한 OFFSET 값을 반환합니다.
     *
     * @return 페이지 OFFSET 값
     */
    public int getOffset() {
        return Math.max(0, getCurrentPage() - 1) * itemsPerPage;
    }

    /**
     * 페이지 범위의 시작을 반환합니다.
     * <p>
     * (현재 페이지 - 페이지 범위 / 2) 혹은 (최대 페이지 - 페이지 범위 - 1) 중 작은 값을 반환합니다.
     *
     * @return 페이지 범위의 시작
     */
    public int getBegin() {
        return fitPage(Math.min(currentPage - pageRange / 2, getMaxPage() - pageRange - 1));
    }

    /**
     * 페이지 범위의 끝을 반환합니다.
     * <p>
     * (현재 페이지 + 페이지 범위 / 2) 혹은 (페이지 범위 + 2) 중 큰 값을 반환합니다.
     *
     * @return 페이지 범위의 끝
     */
    public int getEnd() {
        return fitPage(Math.max(currentPage + pageRange / 2, pageRange + 2));
    }

    /**
     * 페이지 링크가 넓은 화면에서만 표시되는 지 여부를 반환합니다.
     *
     * @param page 페이지 번호
     * @return 넓은 화면에서만 표시되는 경우 true
     */
    public boolean isOnlyWide(int page) {
        if (getMaxPage() < pageRange + 2) { // 말 줄임표가 없는 경우 숨김 처리 불필요
            return false;
        }

        // 앞 말줄임표가 필요한 경우, 페이지 범위의 시작을 숨기고, 아니면 페이지 범위의 끝의 이전 페이지를 숨김
        // 뒷 말줄임표가 필요한 경우, 페이지 범위의 끝을 숨기고, 아니면 페이지 범위의 시작의 다음 페이지를 숨김
        // 결과적으로 총 2개의 페이지가 숨겨짐
        return page == (needPrevSkip() ? getBegin() : getEnd() - 1)
                || page == (needNextSkip() ? getEnd() : getBegin() + 1);
    }

    /**
     * 페이지 범위의 시작의 이전 페이지를 반환합니다.
     *
     * @return 이전 페이지
     */
    public int getPrevPage() {
        return fitPage(getBegin() - 1);
    }

    /**
     * 페이지 범위의 끝의 다음 페이지를 반환합니다.
     *
     * @return 다음 페이지
     */
    public int getNextPage() {
        return fitPage(getEnd() + 1);
    }

    /**
     * 첫 페이지(1) 이동 링크의 필요 여부를 반환합니다.
     *
     * @return 페이지 범위의 시작이 1보다 큰 경우 true
     */
    public boolean needFirstLink() {
        return getBegin() > 1;
    }

    /**
     * 마지막 페이지 이동 링크의 필요 여부를 반환합니다.
     *
     * @return 페이지 범위의 끝이 총 페이지보다 작은 경우 true
     */
    public boolean needLastLink() {
        return getEnd() < getMaxPage();
    }

    /**
     * 앞 말줄임표의 필요 여부를 반환합니다.
     *
     * @return 페이지 범위의 시작이 2보다 큰 경우 true
     */
    public boolean needPrevSkip() {
        return getBegin() > 2;
    }

    /**
     * 뒷 말줄임표의 필요 여부를 반환합니다.
     *
     * @return 페이지 범위의 끝이 총 (페이지 - 1)보다 작은 경우 true
     */
    public boolean needNextSkip() {
        return getEnd() < getMaxPage() - 1;
    }

    /**
     * Pagination 객체와 리스트를 묶어 Pair 객체로 반환합니다.
     *
     * @param list 리스트
     * @param <T>  리스트의 타입
     * @return Pagination 객체와 리스트를 묶은 Pair 객체
     */
    public <T> Pair<T> pair(List<T> list) {
        return new Pair<>(this, list);
    }

    public record Pair<T>(
            Pagination pagination,
            List<T> list
    ) {
    }
}
