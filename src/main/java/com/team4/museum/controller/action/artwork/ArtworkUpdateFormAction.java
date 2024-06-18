package com.team4.museum.controller.action.artwork;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.util.Security;
import com.team4.museum.vo.ArtworkVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class ArtworkUpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 권한이 없으면 404 페이지로 포워딩
		if (!Security.adminOr404Forward(request, response)) {
			return;
		}

		int aseq = Integer.parseInt(request.getParameter("aseq"));
		ArtworkVO avo = ArtworkDao.getInstance().get(aseq);

		// 예술품 정보가 없으면 404 페이지로 포워딩
		if (!Security.trueOr404Forward(avo != null, request, response)) {
			return;
		}

		avo.setArtist(request.getParameter("artist"));
		avo.setName(request.getParameter("artname"));
		avo.setYear(request.getParameter("year"));
		avo.setMaterial(request.getParameter("material"));
		avo.setSize(request.getParameter("size"));
		avo.setCategory(request.getParameter("category"));
		avo.setDisplayyn(request.getParameter("displayYn"));
		avo.setContent(request.getParameter("content"));

		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String uploadFilePath = context.getRealPath("static/image/artwork");

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
				avo.setImage(fileName);
				avo.setSavefilename(saveFilename);
			}
		}

		ArtworkDao.getInstance().updateArtwork(avo);
		response.sendRedirect("museum.do?command=artworkView&aseq=" + aseq);
	}

}
