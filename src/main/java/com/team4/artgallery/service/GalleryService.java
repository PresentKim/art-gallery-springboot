package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.ForbiddenException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.dto.gallery.GalleryCreateDto;
import com.team4.artgallery.dto.gallery.GalleryUpdateDto;
import com.team4.artgallery.entity.GalleryEntity;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.repository.GalleryRepository;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ReadCountHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;

    private final MultipartFileService fileService;

    private final SessionProvider sessionProvider;

    public GalleryService(GalleryRepository galleryRepository, MultipartFileService fileService, SessionProvider sessionProvider) {
        this.galleryRepository = galleryRepository;
        this.fileService = fileService;
        this.sessionProvider = sessionProvider;
    }

    /**
     * 갤러리 정보를 추가합니다
     *
     * @param galleryCreateDto 갤러리 정보
     * @param loginMember      로그인 멤버 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     * @throws SqlException  갤러리 정보 추가에 실패한 경우 예외 발생
     */
    @Transactional
    public GalleryEntity createGallery(GalleryCreateDto galleryCreateDto, MemberEntity loginMember) throws FileException, SqlException {
        return galleryRepository.save(galleryCreateDto.toEntity(null, saveImage(galleryCreateDto.getImageFile()), loginMember));
    }

    /**
     * 검색 조건에 따라 갤러리 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 갤러리 목록
     */
    public Page<GalleryEntity> getGalleries(KeywordFilter filter, Pagination pagination) {
        //noinspection unchecked
        Page<GalleryEntity> result = galleryRepository.findAll((Specification<GalleryEntity>) filter.toSpec("author", "title", "content"), pagination.toPageable());
        pagination.setItemCount((int) result.getTotalElements());
        return result;
    }

    /**
     * 갤러리 정보를 가져옵니다.
     *
     * @param gseq 갤러리 번호 (gallery sequence)
     * @return 갤러리 정보
     * @throws NotFoundException 갤러리 정보를 찾을 수 없는 경우 예외 발생
     */
    public GalleryEntity getGallery(int gseq) {
        return galleryRepository.findById(gseq)
                .orElseThrow(() -> new NotFoundException("갤러리 정보를 찾을 수 없습니다."));
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
    public GalleryEntity getGalleryOnlyAuthor(int gseq, MemberEntity loginMember) throws NotFoundException, ForbiddenException {
        GalleryEntity galleryEntity = getGallery(gseq);
        Assert.trueOrForbidden(loginMember.getId().equals(galleryEntity.getAuthor().getId()), "작성자만 수정할 수 있습니다.");
        return galleryEntity;
    }

    /**
     * 갤러리 정보를 수정합니다
     *
     * @param gseq             갤러리 번호
     * @param galleryUpdateDto 갤러리 정보
     * @param loginMember      로그인 멤버 정보
     * @throws NotFoundException  갤러리 정보를 찾을 수 없는 경우 예외 발생
     * @throws ForbiddenException 작성자가 아닌 경우 예외 발생
     * @throws FileException      이미지 저장에 실패한 경우 예외 발생
     */
    @Transactional
    public void updateGallery(int gseq, GalleryUpdateDto galleryUpdateDto, MemberEntity loginMember) throws NotFoundException, ForbiddenException, FileException {
        MultipartFile imageFile = galleryUpdateDto.getImageFile();
        String fileName;
        if (imageFile != null && !imageFile.isEmpty()) {
            fileName = saveImage(imageFile);
        } else {
            fileName = getGallery(gseq).getImageFileName();
        }

        galleryRepository.save(galleryUpdateDto.toEntity(gseq, fileName, loginMember));
    }

    /**
     * 갤러리 정보를 삭제합니다.
     *
     * @param gseq 갤러리 번호
     * @throws NotFoundException  갤러리 정보를 찾을 수 없는 경우 예외 발생
     * @throws ForbiddenException 작성자 혹은 관리자가 아닌 경우 예외 발생
     */
    @Transactional
    public void deleteGallery(int gseq, MemberEntity loginMember) throws NotFoundException, ForbiddenException {
        GalleryEntity oldGallery = getGallery(gseq);
        Assert.trueOrForbidden(
                loginMember.isAdmin() || loginMember.getId().equals(oldGallery.getAuthor().getId()),
                "작성자 혹은 관리자만 삭제할 수 있습니다."
        );

        galleryRepository.deleteById(gseq);
    }

    /**
     * 갤러리 조회 기록을 확인하고, 조회 기록이 없는 경우 조회수를 증가시킵니다.
     *
     * @param gseq 갤러리 번호 (gallery sequence)
     * @throws NotFoundException 갤러리 정보를 찾을 수 없는 경우 예외 발생
     */
    @Transactional
    public void increaseReadCountIfNew(int gseq) throws NotFoundException {
        ReadCountHelper.increaseReadCountIfNew(
                gseq,
                sessionProvider.getSession(),
                "gallery-read-",
                galleryRepository::increaseReadCountById
        );
    }

    /**
     * 갤러리 이미지를 저장하고 GalleryDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file 이미지 파일
     * @return 이미지 파일이 저장된 경로
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     */
    private String saveImage(MultipartFile file) throws FileException {
        return fileService.saveFile(file, "/static/image/artwork");
    }

}
