package com.team4.artgallery.service;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.dto.filter.ArtworkFilter;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    @Delegate
    private final IArtworkDao artworkDao;

    private final MultipartFileService fileService;

    private final ResponseService responseService;

    /**
     * 예술품 정보를 수정합니다
     *
     * @param artworkDto 예술품 정보
     * @throws NotFoundException   예술품 정보를 찾을 수 없는 경우 예외 발생
     * @throws FileUploadException 이미지 저장에 실패한 경우 예외 발생
     * @throws SQLException        예술품 등록에 실패한 경우 예외 발생
     */
    public ResponseEntity<ResponseBody> createArtwork(ArtworkDto artworkDto, MultipartFile imageFile) throws Exception {
        // 이미지 파일이 없을 경우 오류 결과 반환
        if (imageFile == null || imageFile.isEmpty()) {
            throw new FileUploadException("이미지 파일을 업로드해주세요.");
        }

        // 이미지 저장에 실패하면 오류 결과 반환
        if (!saveImage(imageFile, artworkDto)) {
            throw new FileUploadException("이미지 저장에 실패했습니다.");
        }

        // 예술품 등록 실패 시 오류 결과 반환
        if (createArtwork(artworkDto) != 1) {
            throw new SQLException("예술품 등록에 실패했습니다.");
        }

        // 예술품 등록 성공 시 성공 결과 반환
        return responseService.ok("예술품이 등록되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

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
     * 예술품 정보를 수정합니다
     *
     * @param artworkDto 예술품 정보
     * @throws NotFoundException   예술품 정보를 찾을 수 없는 경우 예외 발생
     * @throws FileUploadException 이미지 저장에 실패한 경우 예외 발생
     * @throws SQLException        예술품 수정에 실패한 경우 예외 발생
     */
    public ResponseEntity<ResponseBody> updateArtwork(ArtworkDto artworkDto, MultipartFile imageFile) throws Exception {
        // 기존 정보가 있어야 UPDATE 쿼리를 실행할 수 있습니다.
        ArtworkDto oldArtwork = getArtwork(artworkDto.getAseq());

        // 이미지 파일이 있을 경우 이미지 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!saveImage(imageFile, artworkDto)) {
                // 이미지 저장에 실패하면 오류 결과 반환
                throw new FileUploadException("이미지 저장에 실패했습니다.");
            }
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 파일 정보를 가져옵니다.
            artworkDto.setImage(oldArtwork.getImage());
            artworkDto.setSavefilename(oldArtwork.getSavefilename());
        }

        // 예술품 수정 실패 시 오류 결과 반환
        if (updateArtwork(artworkDto) != 1) {
            throw new SQLException("예술품 수정에 실패했습니다.");
        }

        return responseService.ok("예술품이 수정되었습니다.", "/artwork/" + artworkDto.getAseq());
    }

    /**
     * 예술품 전시 여부를 토글합니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @throws NotFoundException 예술품 정보를 찾을 수 없는 경우 예외 발생
     * @throws SQLException      전시 여부 변경에 실패한 경우 예외 발생
     */
    public ResponseEntity<ResponseBody> toggleArtworkDisplay(int aseq) throws Exception {
        // 예술품 정보 존재 여부 확인
        if (artworkDao.getArtwork(aseq) == null) {
            throw new NotFoundException("예술품 정보를 찾을 수 없습니다.");
        }

        // 전시 여부 변경 실패 시 오류 결과 반환
        if (artworkDao.toggleArtworkDisplay(aseq) != 1) {
            throw new SQLException("전시 여부 변경에 실패했습니다.");
        }

        return responseService.ok("전시 여부가 변경되었습니다.");
    }


    /**
     * 예술품 정보를 삭제합니다.
     *
     * @param aseq 예술품 번호 (artwork sequence)
     * @throws SQLException 예술품 삭제에 실패한 경우 예외 발생
     */
    public ResponseEntity<ResponseBody> deleteArtwork(int aseq) throws SQLException {
        // 예술품 삭제 실패 시 오류 결과 반환
        if (artworkDao.deleteArtwork(aseq) != 1) {
            throw new SQLException("예술품 삭제에 실패했습니다.");
        }

        // 예술품 삭제 성공 시 성공 결과 반환
        return responseService.ok("예술품이 삭제되었습니다.", "/artwork");
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
