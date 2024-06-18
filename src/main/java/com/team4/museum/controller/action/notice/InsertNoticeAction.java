package com.team4.museum.controller.action.notice;

import static com.team4.museum.controller.action.member.LoginAjaxAction.getLoginUser;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.NoticeDao;
import com.team4.museum.util.Security;
import com.team4.museum.vo.MemberVO;
import com.team4.museum.vo.NoticeVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class InsertNoticeAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 권한이 없으면 404 페이지로 포워딩
		if (!Security.adminOr404Forward(request, response)) {
			return;
		}

		NoticeDao ndao = NoticeDao.getInstance();
		NoticeVO nvo = new NoticeVO();

		HttpSession session = request.getSession();
		MemberVO mvo = getLoginUser(request, response);
		if (mvo == null) {
			return;
		}

		nvo.setTitle(request.getParameter("title"));
		nvo.setAuthor(mvo.getId());
		nvo.setContent(request.getParameter("content"));
		nvo.setCategory(request.getParameter("category"));

		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath("static/image/notice");

		// 저장경로 생성
		File uploadDir = new File(uploadFilePath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		String fileName = "";
		for (Part p : request.getParts()) {
			fileName = "";
			// 전송된 파라미터들중 filename 이라는 글자가 포함된 헤더를 찾고, 있으면 이름 추출
			for (String content : p.getHeader("content-disposition").split(";")) {
				if (content.trim().startsWith("filename"))
					fileName = content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
			String saveFilename = "";
			// 추출된 이름이 있다면
			if (!fileName.equals("")) {
				Calendar today = Calendar.getInstance();
				long dt = today.getTimeInMillis();
				String fn1 = fileName.substring(0, fileName.indexOf("."));
				String fn2 = fileName.substring(fileName.indexOf("."));
				saveFilename = fn1 + dt + fn2;
				p.write(uploadFilePath + File.separator + saveFilename); // 파일 저장
				nvo.setImage(fileName);
				nvo.setSavefilename(saveFilename);
			}
		}

		ndao.insertNotice(nvo);
		response.sendRedirect("museum.do?command=noticeList");

	}

}
