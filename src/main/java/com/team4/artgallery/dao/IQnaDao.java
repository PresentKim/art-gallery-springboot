package com.team4.artgallery.dao;

import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IQnaDao {

    int insert(QnaDto artworkDto);

    QnaDto get(int qseq);

    List<QnaDto> getAll(Pagination pagination);

    int getAllCount();

    int update(QnaDto artworkDto);

    int delete(int qseq);

}
