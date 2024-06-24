package com.team4.museum.controller;

import com.team4.museum.controller.action.Action;
import com.team4.museum.controller.action.IndexAction;
import com.team4.museum.controller.action.admin.*;

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
