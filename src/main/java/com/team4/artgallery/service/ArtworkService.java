package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.artwork.ArtworkCreateDto;
import com.team4.artgallery.dto.artwork.ArtworkUpdateDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.entity.ArtworkEntity;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.repository.ArtworkRepository;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    private final MultipartFileService fileService;

    public ArtworkService(ArtworkRepository artworkRepository, MultipartFileService fileService) {
        this.artworkRepository = artworkRepository;
        this.fileService = fileService;
    }

    /**
     * 예술품 정보를 추가합니다
     *
     * @param artworkCreateDto 예술품 정보
     * @return 추가된 예술품 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     * @throws SqlException  예술품 정보 추가에 실패한 경우 예외 발생
     */
    @Transactional
    public ArtworkEntity createArtwork(ArtworkCreateDto artworkCreateDto) throws SqlException {
        return artworkRepository.save(artworkCreateDto.toEntity(null, saveImage(artworkCreateDto.getImageFile())));
    }

    /**
     * 검색 조건에 따라 예술품 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 예술품 목록
     */
    public Page<ArtworkEntity> getArtworks(ArtworkFilter filter, Pagination pagination) {
        Page<ArtworkEntity> result = artworkRepository.findAll(filter.toSpec(), pagination.toPageable());
        pagination.setItemCount((int) result.getTotalElements());
        return result;
    }

    /**
     * 예술품 정보를 가져옵니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @return 예술품 정보
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     */
    public ArtworkEntity getArtwork(int aseq, MemberEntity loginMember) throws NotFoundException {
        ArtworkEntity artworkEntity = artworkRepository.findById(aseq)
                .orElseThrow(() -> new NotFoundException("예술품 정보를 찾을 수 없습니다."));
        if (!artworkEntity.getDisplay() && (loginMember == null || !loginMember.isAdmin())) {
            throw new NotFoundException("예술품 정보를 찾을 수 없습니다.");
        }

        return artworkEntity;
    }

    /**
     * 랜덤 예술품 목록을 가져옵니다.
     *
     * @param count 가져올 예술품 개수
     * @return 랜덤 예술품 목록
     */
    public List<ArtworkEntity> getRandomArtworks(int count) {
        return artworkRepository.findRandomArtworks(count).stream().toList();
    }

    /**
     * 예술품 정보를 수정합니다
     *
     * @param aseq             예술품 번호
     * @param artworkUpdateDto 예술품 정보
     * @param loginMember      로그인한 회원 정보
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     * @throws FileException     이미지 저장에 실패한 경우 예외 발생
     */
    @Transactional
    public void updateArtwork(int aseq, ArtworkUpdateDto artworkUpdateDto, MemberEntity loginMember) throws NotFoundException {
        ArtworkEntity artworkEntity = getArtwork(aseq, loginMember);

        artworkEntity.setName(artworkUpdateDto.getName());
        artworkEntity.setArtist(artworkUpdateDto.getArtist());
        artworkEntity.setCategory(artworkUpdateDto.getCategory());
        artworkEntity.setMaterial(artworkUpdateDto.getMaterial());
        artworkEntity.setYear(artworkUpdateDto.getYear());
        artworkEntity.setSize(artworkUpdateDto.getSize());
        artworkEntity.setDisplay(artworkUpdateDto.getDisplayyn() == 'Y');
        artworkEntity.setContent(artworkUpdateDto.getContent());

        MultipartFile imageFile = artworkUpdateDto.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            artworkEntity.setImageFileName(saveImage(imageFile));
        }
    }

    /**
     * 예술품 전시 여부를 수정합니다.
     *
     * @param aseq        예술품 번호 (artwork sequence)
     * @param display     전시 여부
     * @param loginMember 로그인한 회원 정보
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     */
    @Transactional
    public void updateDisplay(int aseq, boolean display, MemberEntity loginMember) throws NotFoundException {
        ArtworkEntity artworkEntity = getArtwork(aseq, loginMember);
        artworkEntity.setDisplay(display);
    }

    /**
     * 예술품 정보를 삭제합니다.
     *
     * @param aseq 예술품 번호
     * @throws NotFoundException 예술품 삭제에 실패한 경우 예외 발생
     */
    @Transactional
    public void deleteArtwork(Integer aseq) throws NotFoundException {
        artworkRepository.deleteById(aseq);
    }

    /**
     * 예술품 이미지를 저장하고  이미지 경로를 반환합니다.
     *
     * @param file 이미지 파일
     * @return 이미지 파일이 저장된 경로
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     */
    private String saveImage(MultipartFile file) throws FileException {
        return fileService.saveFile(file, "/static/image/artwork");
    }

}
