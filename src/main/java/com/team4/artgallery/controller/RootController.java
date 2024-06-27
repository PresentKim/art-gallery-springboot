package com.team4.artgallery.controller;

import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

    private final NoticeService noticeService;

    private final ArtworkService artworkService;

    @GetMapping("/")
    public String root(Model model) {
        // 최근 공지사항 5개를 가져옵니다.
        model.addAttribute("noticeList", noticeService.getNotices(null, new Pagination().setDisplayCount(5)));

        // 랜덤 예술품 목록을 가져와 4개의 그룹으로 나눕니다.
        List<ArtworkDto> randomArtworkList = artworkService.getRandomArtworks(24);
        model.addAttribute("artworkList1", randomArtworkList.subList(0, 6));
        model.addAttribute("artworkList2", randomArtworkList.subList(6, 12));
        model.addAttribute("artworkList3", randomArtworkList.subList(12, 18));
        model.addAttribute("artworkList4", randomArtworkList.subList(18, 24));

        // 메인 화면으로 포워딩
        return "main/main";
    }

}
