package com.team4.artgallery.service;

import com.team4.artgallery.dao.INoticeDao;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NoticeService {

    @Delegate
    private final INoticeDao noticeDao;

}
