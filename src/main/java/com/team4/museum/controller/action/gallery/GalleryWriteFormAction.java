package com.team4.museum.controller.action.gallery;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.team4.museum.controller.action.Action;
import com.team4.museum.controller.action.member.LoginAjaxAction;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.vo.MemberGalleryVO;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class GalleryWriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 정보가 없으면 로그인 페이지로 이동
		MemberVO mvo = LoginAjaxAction.getLoginUser(request, response);
		if (mvo == null) {
			return;
		}

		MemberGalleryDao mgdao = MemberGalleryDao.getInstance();
		MemberGalleryVO mgvo = new MemberGalleryVO();
		mgvo.setTitle(request.getParameter("title"));
		mgvo.setContent(request.getParameter("content"));
		mgvo.setAuthorId(mvo.getId());

		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath("static/image/gallery");

		File uploadDir = new File(uploadFilePath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		String fileName = "";
		for (Part p : request.getParts()) {
			fileName = "";
			for (String content : p.getHeader("content-disposition").split(";")) {
				if (content.trim().startsWith("filename"))
					fileName = content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
			String saveFilename = "";
			if (!fileName.equals("")) {
				Calendar today = Calendar.getInstance();
				long dt = today.getTimeInMillis();
				String fn1 = fileName.substring(0, fileName.indexOf("."));
				String fn2 = fileName.substring(fileName.indexOf("."));
				saveFilename = fn1 + dt + fn2;
				p.write(uploadFilePath + File.separator + saveFilename); // 파일 저장
				mgvo.setImage(fileName);
				mgvo.setSavefilename(saveFilename);
			}
		}

		mgdao.insertMemberGallery(mgvo);
		response.sendRedirect("museum.do?command=galleryList");

	}

}