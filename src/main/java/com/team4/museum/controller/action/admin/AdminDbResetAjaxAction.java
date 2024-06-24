package com.team4.museum.controller.action.admin;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.util.Db;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.team4.museum.util.AccountUtil.isAdmin;

public class AdminDbResetAjaxAction extends AjaxAction {

    protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws AjaxException {
        // 사용자가 관리자가 아닌 경우
        if (!isAdmin(request)) {
            return forbidden("접근 권한이 없습니다");
        }

        // DB 초기화 쿼리를 실행하고 성공 메시지를 반환
        Db.executeSqlFile("database/init.sql");
        Db.executeSqlFile("database/init_view.sql");
        Db.executeSqlFile("database/insert_artwork.sql");
        Db.executeSqlFile("database/insert_member.sql");
        Db.executeSqlFile("database/insert_gallery.sql");
        Db.executeSqlFile("database/insert_notice.sql");
        Db.executeSqlFile("database/insert_qna.sql");
        return created("DB가 초기화되었습니다.");
    }

}
