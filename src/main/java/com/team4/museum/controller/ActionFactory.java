package com.team4.museum.controller;

import com.team4.museum.controller.action.Action;
import com.team4.museum.controller.action.IndexAction;
import com.team4.museum.controller.action.admin.AdminArtworkListAction;
import com.team4.museum.controller.action.admin.AdminDbResetAjaxAction;
import com.team4.museum.controller.action.admin.AdminDeleteArtworkAction;
import com.team4.museum.controller.action.admin.AdminDeleteGalleryAction;
import com.team4.museum.controller.action.admin.AdminDeleteMemberAction;
import com.team4.museum.controller.action.admin.AdminDeleteNoticeAction;
import com.team4.museum.controller.action.admin.AdminDeleteQnaAction;
import com.team4.museum.controller.action.admin.AdminGalleryListAction;
import com.team4.museum.controller.action.admin.AdminMainAction;
import com.team4.museum.controller.action.admin.AdminMemberListAction;
import com.team4.museum.controller.action.admin.AdminNoticeListAction;
import com.team4.museum.controller.action.admin.AdminQnaListAction;
import com.team4.museum.controller.action.admin.GrantAdminRightsAction;
import com.team4.museum.controller.action.artwork.ArtworkDeleteAction;
import com.team4.museum.controller.action.artwork.ArtworkDisplaySetAction;
import com.team4.museum.controller.action.artwork.ArtworkListAction;
import com.team4.museum.controller.action.artwork.ArtworkUpdateAction;
import com.team4.museum.controller.action.artwork.ArtworkUpdateFormAction;
import com.team4.museum.controller.action.artwork.ArtworkViewAction;
import com.team4.museum.controller.action.artwork.ArtworkWriteAction;
import com.team4.museum.controller.action.artwork.ArtworkWriteFormAction;
import com.team4.museum.controller.action.gallery.GalleryDeleteAction;
import com.team4.museum.controller.action.gallery.GalleryListAction;
import com.team4.museum.controller.action.gallery.GalleryUpdateAction;
import com.team4.museum.controller.action.gallery.GalleryUpdateFormAction;
import com.team4.museum.controller.action.gallery.GalleryViewAction;
import com.team4.museum.controller.action.gallery.GalleryWriteAction;
import com.team4.museum.controller.action.gallery.GalleryWriteFormAction;
import com.team4.museum.controller.action.member.ContractAcion;
import com.team4.museum.controller.action.member.IdCheckAjaxAction;
import com.team4.museum.controller.action.member.JoinAjaxAction;
import com.team4.museum.controller.action.member.JoinFormAction;
import com.team4.museum.controller.action.member.LoginAjaxAction;
import com.team4.museum.controller.action.member.LoginFormAction;
import com.team4.museum.controller.action.member.LogoutAjaxAction;
import com.team4.museum.controller.action.member.WithdrawAjaxAction;
import com.team4.museum.controller.action.member.WithdrawFormAction;
import com.team4.museum.controller.action.member.mypage.MyPageAction;
import com.team4.museum.controller.action.member.mypage.MyPageEditAjaxAction;
import com.team4.museum.controller.action.member.mypage.MyPageEditFormAction;
import com.team4.museum.controller.action.member.mypage.MyPageFavoriteAjaxAction;
import com.team4.museum.controller.action.member.mypage.MyPageFavoriteListAction;
import com.team4.museum.controller.action.notice.DeleteNoticeAction;
import com.team4.museum.controller.action.notice.InsertNoticeAction;
import com.team4.museum.controller.action.notice.InsertNoticeFormAction;
import com.team4.museum.controller.action.notice.NoticeListAction;
import com.team4.museum.controller.action.notice.NoticeViewAction;
import com.team4.museum.controller.action.notice.NoticeViewWithoutCntAction;
import com.team4.museum.controller.action.notice.UpdateNoticeAction;
import com.team4.museum.controller.action.notice.UpdateNoticeFormAction;
import com.team4.museum.controller.action.qna.QnaListAction;
import com.team4.museum.controller.action.qna.QnaPwdCheckAjaxAction;
import com.team4.museum.controller.action.qna.QnaReplyAjaxAction;
import com.team4.museum.controller.action.qna.QnaViewAction;
import com.team4.museum.controller.action.qna.QnaWriteAjaxAction;
import com.team4.museum.controller.action.qna.QnaWriteFormAction;

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
