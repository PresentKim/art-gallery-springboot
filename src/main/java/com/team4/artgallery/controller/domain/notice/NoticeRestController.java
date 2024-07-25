package com.team4.artgallery.controller.domain.notice;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.enums.NoticeCategory;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.dto.view.Views;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/notices", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticeRestController implements NoticeRestControllerDocs {

    private final NoticeService noticeService;

    public NoticeRestController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("")
    @JsonView(Views.Summary.class)
    public List<NoticeDto> getList(
            @Valid
            NoticeFilter filter,
            @Valid
            Pagination pagination
    ) {
        String category = filter.getCategory();
        Assert.isFalse(NoticeCategory.MAGAZINE.isEquals(category), "잘못된 카테고리입니다.", BadRequestException::new);
        Assert.isFalse(NoticeCategory.NEWSPAPER.isEquals(category), "잘못된 카테고리입니다.", BadRequestException::new);

        return noticeService.getNotices(filter, pagination);
    }

    @GetMapping("{nseq}")
    public NoticeDto getById(
            @PathVariable(name = "nseq")
            String nseq
    ) throws NotFoundException, SqlException {
        try {
            int nseqInt = Integer.parseInt(nseq);
            noticeService.increaseReadCountIfNew(nseqInt);
            return noticeService.getNotice(nseqInt);
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeDto create(
            @Valid
            NoticeDto noticeDto,

            @LoginMember
            MemberEntity loginMember
    ) throws SqlException {
        noticeService.createNotice(noticeDto, loginMember);
        return noticeDto;
    }

    @CheckAdmin
    @PutMapping("{nseq}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("nseq")
            String nseq,
            @Valid
            NoticeDto noticeDto,

            @LoginMember
            MemberEntity loginMember
    ) throws SqlException, NotFoundException {
        try {
            noticeDto.setNseq(Integer.parseInt(nseq));
            noticeService.updateNotice(noticeDto, loginMember);
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @CheckAdmin
    @DeleteMapping("{nseq}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable("nseq")
            String nseq
    ) throws SqlException {
        try {
            noticeService.deleteNotice(Integer.parseInt(nseq));
        } catch (NumberFormatException e) {
            throw new NotFoundException("요청하신 리소스를 찾을 수 없습니다.");
        }
    }

    @GetMapping("recent")
    public List<NoticeDto> getRecentList(
            @RequestParam(name = "count", defaultValue = "5")
            Integer count
    ) {
        return noticeService.getRecentNotices(count);
    }

}
