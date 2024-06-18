package com.team4.museum.controller;

import com.team4.museum.controller.action.Action;
import com.team4.museum.controller.action.IndexAction;
import com.team4.museum.controller.action.admin.*;
import com.team4.museum.controller.action.artwork.*;
import com.team4.museum.controller.action.gallery.*;
import com.team4.museum.controller.action.member.*;
import com.team4.museum.controller.action.member.mypage.*;
import com.team4.museum.controller.action.notice.*;
import com.team4.museum.controller.action.qna.*;

public class ActionFactory {

    private ActionFactory() {
    }

    private static final ActionFactory instance = new ActionFactory();

    public static ActionFactory getInstance() {
        return instance;
    }

    public Action getAction(String command) {
        return switch (command != null ? command : "") {

            // index action
            case "", "index" -> new IndexAction();

            // member actions
            case "loginForm" -> new LoginFormAction();
            case "login" -> new LoginAjaxAction();
            case "logout" -> new LogoutAjaxAction();
            case "join" -> new JoinAjaxAction();
            case "joinForm" -> new JoinFormAction();
            case "idCheck" -> new IdCheckAjaxAction();
            case "mypage" -> new MyPageAction();
            case "mypageEditForm" -> new MyPageEditFormAction();
            case "mypageEdit" -> new MyPageEditAjaxAction();
            case "mypageFavorite" -> new MyPageFavoriteAjaxAction();
            case "mypageFavoriteList" -> new MyPageFavoriteListAction();
            case "contract" -> new ContractAcion();
            case "withdrawForm" -> new WithdrawFormAction();
            case "withdraw" -> new WithdrawAjaxAction();

            // artwork actions
            case "artworkList" -> new ArtworkListAction();
            case "artworkView" -> new ArtworkViewAction();
            case "artworkWrite" -> new ArtworkWriteAction();
            case "artworkWriteForm" -> new ArtworkWriteFormAction();
            case "artworkUpdate" -> new ArtworkUpdateAction();
            case "artworkUpdateForm" -> new ArtworkUpdateFormAction();
            case "artworkDelete" -> new ArtworkDeleteAction();
            case "artworkDisplaySet" -> new ArtworkDisplaySetAction();

            // QnA actions
            case "qnaList" -> new QnaListAction();
            case "qnaPwdCheck" -> new QnaPwdCheckAjaxAction();
            case "qnaView" -> new QnaViewAction();
            case "qnaReply" -> new QnaReplyAjaxAction();
            case "qnaWriteForm" -> new QnaWriteFormAction();
            case "qnaWrite" -> new QnaWriteAjaxAction();

            // user gallery
            case "galleryList" -> new GalleryListAction();
            case "galleryView" -> new GalleryViewAction();
            case "galleryWriteForm" -> new GalleryWriteFormAction();
            case "galleryWrite" -> new GalleryWriteAction();

            case "galleryUpdateForm" -> new GalleryUpdateFormAction();
            case "galleryUpdate" -> new GalleryUpdateAction();
            case "galleryDelete" -> new GalleryDeleteAction();

            // notice
            case "noticeList" -> new NoticeListAction();
            case "noticeView" -> new NoticeViewAction();
            case "insertNoticeForm" -> new InsertNoticeFormAction();
            case "insertNotice" -> new InsertNoticeAction();
            case "updateNoticeForm" -> new UpdateNoticeFormAction();
            case "updateNotice" -> new UpdateNoticeAction();
            case "noticeViewWithoutCnt" -> new NoticeViewWithoutCntAction();
            case "deleteNotice" -> new DeleteNoticeAction();

            // admin
            case "admin" -> new AdminMainAction();
            case "grantAdminRights" -> new GrantAdminRightsAction();
            case "adminMemberList" -> new AdminMemberListAction();
            case "adminArtworkList" -> new AdminArtworkListAction();
            case "adminNoticeList" -> new AdminNoticeListAction();
            case "adminGalleryList" -> new AdminGalleryListAction();
            case "adminQnaList" -> new AdminQnaListAction();
            case "adminDeleteMember" -> new AdminDeleteMemberAction();
            case "adminDeleteArtwork" -> new AdminDeleteArtworkAction();
            case "adminDeleteNotice" -> new AdminDeleteNoticeAction();
            case "adminDeleteGallery" -> new AdminDeleteGalleryAction();
            case "adminDeleteQna" -> new AdminDeleteQnaAction();
            case "adminDbReset" -> new AdminDbResetAjaxAction();

            // default
            default -> null;

        };
    }

}
