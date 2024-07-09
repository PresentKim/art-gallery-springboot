package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.enums.NoticeCategory;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/notice", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeRestController {

    private final NoticeService noticeService;

    public NoticeRestController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public Pagination.Pair<NoticeDto> root(
            @Valid
            NoticeFilter filter,
            @Valid
            Pagination pagination
    ) {
        String category = filter.getCategory();
        Assert.isFalse(NoticeCategory.MAGAZINE.isEquals(category), "잘못된 카테고리입니다.", BadRequestException::new);
        Assert.isFalse(NoticeCategory.NEWSPAPER.isEquals(category), "잘못된 카테고리입니다.", BadRequestException::new);

        return pagination.pair(noticeService.getNotices(filter, pagination));
    }

    @GetMapping("recent")
    public List<NoticeDto> recent(
            @RequestParam(name = "count", defaultValue = "5")
            Integer count
    ) {
        return noticeService.getRecentNotices(count);
    }

    @GetMapping("{nseq}")
    public NoticeDto view(
            @PathVariable(name = "nseq")
            Integer nseq
    ) throws NotFoundException, SqlException {
        noticeService.increaseReadCountIfNew(nseq);
        return noticeService.getNotice(nseq);
    }

    @CheckAdmin
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto write(
            @Valid
            NoticeDto noticeDto,

            @LoginMember
            MemberDto loginMember
    ) throws SqlException, NotFoundException {
        if (noticeDto.getNseq() == null) {
            noticeService.createNotice(noticeDto, loginMember);
        } else {
            noticeService.updateNotice(noticeDto, loginMember);
        }
        return new ResponseDto("소식지 작성이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "소식지를 선택해주세요")
            @RequestParam(name = "nseq", required = false)
            List<Integer> nseq
    ) throws SqlException {
        noticeService.deleteNotice(nseq);
        return new ResponseDto("소식지가 삭제되었습니다.", "/notice");
    }

    @CheckAdmin
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Object edit(
            @Valid
            @NotEmpty(message = "소식지를 선택해주세요.")
            @Size(max = 1, message = "한 번에 하나의 소식지만 수정할 수 있습니다.")
            @RequestParam(name = "nseq") List<Integer> nseq
    ) throws URISyntaxException {
        return new URI("/notice/update/" + nseq.get(0));
    }

}
