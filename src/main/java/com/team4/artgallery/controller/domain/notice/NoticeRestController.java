package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeRestController {

    private final NoticeService noticeService;

    @CheckAdmin
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseBody update(
            @Valid
            NoticeDto noticeDto,

            @LoginMember
            MemberDto loginMember
    ) {
        noticeDto.setAuthor(loginMember.getId());
        noticeService.updateNotice(noticeDto);
        return new ResponseBody("소식지 수정이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseBody write(
            @Valid
            NoticeDto noticeDto,

            @LoginMember
            MemberDto loginMember
    ) {
        noticeDto.setAuthor(loginMember.getId());
        noticeService.createNotice(noticeDto);
        return new ResponseBody("소식지 작성이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(
            @RequestParam(value = "nseq")
            Integer nseq
    ) {
        noticeService.deleteNotice(nseq);
        return new ResponseBody("소식지가 삭제되었습니다.", "/notice");
    }

}
