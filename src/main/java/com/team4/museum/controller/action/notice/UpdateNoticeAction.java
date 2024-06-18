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

public class UpdateNoticeAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 권한이 없으면 404 페이지로 포워딩
		if (!Security.adminOr404Forward(request, response)) {
			return;
		}

		// 로그인 정보가 없는 경우 로그인 페이지로 이동
		MemberVO mvo = getLoginUser(request, response);
		if (mvo == null) {
			return;
		}

		NoticeDao ndao = NoticeDao.getInstance();
		NoticeVO nvo = new NoticeVO();
		nvo.setNseq(Integer.parseInt(request.getParameter("nseq")));
		nvo.setAuthor(mvo.getId());
		nvo.setTitle(request.getParameter("title"));
		nvo.setContent(request.getParameter("content"));
		nvo.setCategory(request.getParameter("category"));

		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath("static/image/notice");
		File uploadDir = new File(uploadFilePath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		String fileName = "";
		String saveFilename = "";
		for (Part p : request.getParts()) {
			// System.out.println(p.getHeader("content-disposition") );
			fileName = "";
			for (String content : p.getHeader("content-disposition").split(";")) {
				if (content.trim().startsWith("filename")) {
					fileName = content.substring(content.indexOf("=") + 2, content.length() - 1);
					System.out.println("filename : " + fileName);
					if (!fileName.equals("")) {
						Calendar today = Calendar.getInstance();
						long dt = today.getTimeInMillis();
						String fn1 = fileName.substring(0, fileName.indexOf("."));
						String fn2 = fileName.substring(fileName.indexOf("."));
						saveFilename = fn1 + dt + fn2;
						p.write(uploadFilePath + File.separator + saveFilename); // 파일 저장
						nvo.setImage(fileName);
						nvo.setSavefilename(saveFilename);
					} else {
						nvo.setImage(request.getParameter("oldimage"));
					}
				}
			}
		}
		ndao.updateNotice(nvo);

		response.sendRedirect("museum.do?command=noticeViewWithoutCnt&nseq=" + nvo.getNseq());
	}

}
