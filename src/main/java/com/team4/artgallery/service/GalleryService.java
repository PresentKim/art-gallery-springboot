package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.ForbiddenException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dao.IGalleryDao;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ReadCountHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final IGalleryDao galleryDao;

    private final MultipartFileService fileService;

    private final SessionProvider sessionProvider;

    /**
     * 갤러리 정보를 추가합니다
     *
     * @param galleryDto  갤러리 정보
     * @param imageFile   이미지 파일
     * @param loginMember 로그인 멤버 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     * @throws SqlException  갤러리 정보 추가에 실패한 경우 예외 발생
     */
    public void createGallery(GalleryDto galleryDto, MultipartFile imageFile, MemberDto loginMember) throws FileException, SqlException {
        saveImage(imageFile, galleryDto);
        galleryDto.setAuthorId(loginMember.getId());
        galleryDao.createGallery(galleryDto);
    }

    /**
     * 검색 조건에 따라 갤러리 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 갤러리 목록
     */
    public List<GalleryDto> getGalleries(KeywordFilter filter, Pagination pagination) {
        return galleryDao.getGalleries(
                filter,
                pagination.setUrlTemplateFromFilter(filter).setItemCount(galleryDao.countGalleries(filter)));
    }

    /**
     * 갤러리 정보를 가져옵니다.
     *
     * @param gseq 갤러리 번호 (gallery sequence)
     * @return 갤러리 정보
     * @throws NotFoundException 갤러리 정보를 찾을 수 없는 경우 예외 발생
     */
    public GalleryDto getGallery(int gseq) {
        GalleryDto galleryDto = galleryDao.getGallery(gseq);
        Assert.exists(galleryDto, "갤러리 정보를 찾을 수 없습니다.");

        return galleryDto;
    }

    /**
     * 갤러리 정보를 가져옵니다.
     *
     * @param gseq        갤러리 번호 (gallery sequence)
     * @param loginMember 로그인 멤버 정보
     * @return 갤러리 정보
     * @throws NotFoundException  갤러리 정보를 찾을 수 없는 경우 예외 발생
     * @throws ForbiddenException 작성자가 아닌 경우 예외 발생
     */
    public GalleryDto getGalleryOnlyAuthor(int gseq, MemberDto loginMember) throws NotFoundException, ForbiddenException {
        GalleryDto galleryDto = galleryDao.getGallery(gseq);
        Assert.exists(galleryDto, "갤러리 정보를 찾을 수 없습니다.");
        Assert.trueOrForbidden(loginMember.getId().equals(galleryDto.getAuthorId()), "작성자만 수정할 수 있습니다.");

        return galleryDto;
    }

    /**
     * 갤러리 정보를 수정합니다
     *
     * @param galleryDto  갤러리 정보
     * @param imageFile   이미지 파일
     * @param loginMember 로그인 멤버 정보
     * @throws NotFoundException  갤러리 정보를 찾을 수 없는 경우 예외 발생
     * @throws ForbiddenException 작성자가 아닌 경우 예외 발생
     * @throws FileException      이미지 저장에 실패한 경우 예외 발생
     */
    public void updateGallery(GalleryDto galleryDto, MultipartFile imageFile, MemberDto loginMember)
            throws NotFoundException, ForbiddenException, FileException {

        GalleryDto oldGallery = getGalleryOnlyAuthor(galleryDto.getGseq(), loginMember);
        if (imageFile != null && !imageFile.isEmpty()) {
            saveImage(imageFile, galleryDto);
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
            galleryDto.setImage(oldGallery.getImage());
            galleryDto.setSavefilename(oldGallery.getSavefilename());
        }

        galleryDao.updateGallery(galleryDto);
    }

    /**
     * 갤러리 정보를 삭제합니다.
     *
     * @param gseqList 갤러리 번호 목록
     * @throws NotFoundException 갤러리 삭제에 실패한 경우 예외 발생
     */
    public void deleteGallery(List<Integer> gseqList) throws NotFoundException {
        galleryDao.deleteGalleries(gseqList);
    }

    /**
     * 갤러리 정보를 삭제합니다.
     *
     * @param gseq        갤러리 번호 (gallery sequence)
     * @param loginMember 로그인 멤버 정보
     * @throws NotFoundException  갤러리 정보를 찾을 수 없는 경우 예외 발생
     * @throws ForbiddenException 작성자 혹은 관리자가 아닌 경우 예외 발생
     */
    public void deleteGallery(int gseq, MemberDto loginMember) throws NotFoundException, ForbiddenException {
        GalleryDto oldGallery = galleryDao.getGallery(gseq);
        Assert.exists(oldGallery, "갤러리 정보를 찾을 수 없습니다.");

        Assert.trueOrForbidden(
                loginMember.getId().equals(oldGallery.getAuthorId()) || loginMember.isAdmin(),
                "작성자 혹은 관리자만 삭제할 수 있습니다."
        );
        galleryDao.deleteGallery(gseq);
    }

    /**
     * 갤러리 조회 기록을 확인하고, 조회 기록이 없는 경우 조회수를 증가시킵니다.
     *
     * @param gseq 갤러리 번호 (gallery sequence)
     * @throws NotFoundException 갤러리 정보를 찾을 수 없는 경우 예외 발생
     */
    public void increaseReadCountIfNew(int gseq) throws NotFoundException {
        ReadCountHelper.increaseReadCountIfNew(
                gseq,
                sessionProvider.getSession(),
                "gallery-read-",
                galleryDao::increaseReadCount
        );
    }

    /**
     * 갤러리 이미지를 저장하고 GalleryDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file       이미지 파일
     * @param galleryDto 갤러리 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     */
    public void saveImage(MultipartFile file, GalleryDto galleryDto) throws FileException {
        // 이미지 파일을 저장하고 저장된 파일 이름을 GalleryDto 객체에 저장합니다.
        MultipartFileService.FileNamePair pair = fileService.saveFile(file, "/static/image/gallery");

        galleryDto.setImage(pair.fileName());
        galleryDto.setSavefilename(pair.saveFileName());
    }

}
