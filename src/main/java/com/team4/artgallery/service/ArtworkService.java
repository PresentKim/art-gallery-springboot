package com.team4.artgallery.service;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    @Delegate
    private final IArtworkDao artworkDao;

    private final MultipartFileService fileService;

    /**
     * 검색 조건에 따라 예술품 목록을 가져옵니다.
     *
     * @param filter     검색 조건
     * @param pagination 페이지 정보
     * @return 예술품 목록과 페이지 정보
     */
    public Pagination.Pair<ArtworkDto> getArtworksPair(ArtworkFilter filter, Pagination pagination) {
        return pagination
                .setItemCount(countArtworks(filter))
                .pair(getArtworks(filter, pagination));
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
        if (artworkDto == null) {
            throw new NotFoundException("예술품 정보를 찾을 수 없습니다.");
        }

        return artworkDto;
    }

    /**
     * 예술품 정보가 존재하는지 확인합니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     */
    public void assertArtworkExists(int aseq) throws NotFoundException {
        if (artworkDao.getArtwork(aseq) == null) {
            throw new NotFoundException("예술품 정보를 찾을 수 없습니다.");
        }
    }

    /**
     * 예술품 이미지를 저장하고 ArtworkDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file       이미지 파일
     * @param artworkDto 예술품 정보
     */
    public boolean saveImage(MultipartFile file, ArtworkDto artworkDto) {
        // 이미지 파일을 저장하고 저장된 파일 이름을 ArtworkDto 객체에 저장합니다.
        MultipartFileService.FileNamePair pair = fileService.saveFile(file, "/static/image/artwork");
        if (pair != null) {
            artworkDto.setImage(pair.fileName());
            artworkDto.setSavefilename(pair.saveFileName());
            return true;
        }

        return false;
    }

}
