package com.team4.artgallery.service;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    @Delegate
    private final IArtworkDao artworkDao;

    private final MultipartFileService fileService;

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
