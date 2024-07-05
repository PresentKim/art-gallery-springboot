package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final IArtworkDao artworkDao;

    private final MultipartFileService fileService;

    /**
     * 예술품 정보를 추가합니다
     *
     * @param artworkDto 예술품 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     * @throws SqlException  예술품 정보 추가에 실패한 경우 예외 발생
     */
    public void createArtwork(ArtworkDto artworkDto, MultipartFile imageFile) throws ResponseStatusException {
        saveImage(imageFile, artworkDto);
        artworkDao.createArtwork(artworkDto);
    }

    /**
     * 검색 조건에 따라 예술품 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 예술품 목록
     */
    public List<ArtworkDto> getArtworks(ArtworkFilter filter, Pagination pagination) {
        return artworkDao.getArtworks(
                filter,
                pagination.setUrlTemplateFromFilter(filter).setItemCount(artworkDao.countArtworks(filter))
        );
    }

    /**
     * 예술품 정보를 가져옵니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @return 예술품 정보
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     */
    public ArtworkDto getArtwork(int aseq) throws NotFoundException {
        ArtworkDto artworkDto = artworkDao.getArtwork(aseq);
        Assert.exists(artworkDto, "예술품 정보를 찾을 수 없습니다.");

        return artworkDto;
    }

    /**
     * 랜덤 예술품 목록을 가져옵니다.
     *
     * @param count 가져올 예술품 개수
     * @return 랜덤 예술품 목록
     */
    public List<ArtworkDto> getRandomArtworks(int count) {
        return artworkDao.getRandomArtworks(count);
    }

    /**
     * 예술품 정보를 수정합니다
     *
     * @param artworkDto 예술품 정보
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     * @throws FileException     이미지 저장에 실패한 경우 예외 발생
     * @throws SqlException      예술품 정보 수정에 실패한 경우 예외 발생
     */
    public void updateArtwork(ArtworkDto artworkDto, MultipartFile imageFile) throws ResponseStatusException {
        ArtworkDto oldArtwork = getArtwork(artworkDto.getAseq());
        Assert.exists(oldArtwork, "예술품 정보를 찾을 수 없습니다.");

        if (imageFile != null && !imageFile.isEmpty()) {
            saveImage(imageFile, artworkDto);
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
            artworkDto.setImage(oldArtwork.getImage());
            artworkDto.setSavefilename(oldArtwork.getSavefilename());
        }

        artworkDao.updateArtwork(artworkDto);
    }

    /**
     * 예술품 전시 여부를 토글합니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     * @throws SqlException      전시 여부 변경에 실패한 경우 예외 발생
     */
    public void toggleArtworkDisplay(int aseq) throws ResponseStatusException {
        Assert.exists(artworkDao.getArtwork(aseq), "예술품 정보를 찾을 수 없습니다.");
        artworkDao.toggleArtworkDisplay(aseq);
    }

    /**
     * 예술품 정보를 삭제합니다.
     *
     * @param aseqList 예술품 번호 목록
     * @throws SqlException 예술품 삭제에 실패한 경우 예외 발생
     */
    public void deleteArtwork(List<Integer> aseqList) throws SqlException {
        artworkDao.deleteArtworks(aseqList);
    }

    /**
     * 예술품 정보를 삭제합니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @throws SqlException 예술품 삭제에 실패한 경우 예외 발생
     */
    public void deleteArtwork(int aseq) throws SqlException {
        artworkDao.deleteArtwork(aseq);
    }

    /**
     * 예술품 이미지를 저장하고 ArtworkDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file       이미지 파일
     * @param artworkDto 예술품 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     */
    private void saveImage(MultipartFile file, ArtworkDto artworkDto) throws FileException {
        // 이미지 파일을 저장하고 저장된 파일 이름을 ArtworkDto 객체에 저장합니다.
        MultipartFileService.FileNamePair pair = fileService.saveFile(file, "/static/image/artwork");
        artworkDto.setImage(pair.fileName());
        artworkDto.setSavefilename(pair.saveFileName());
    }

}
