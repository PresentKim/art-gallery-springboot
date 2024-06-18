package com.team4.museum.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

final public class MultipartFileInfo {

    private String fileName = "";

    private String saveFileName = "";

    public String getFileName() {
        return fileName;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public boolean isEmpty() {
        return fileName.isEmpty();
    }

    /**
     * 요청에서 파일을 읽어서 저장
     *
     * @param request       요청
     * @param uploadDirName 업로드 디렉토리 이름
     * @return 파일 정보
     */
    public static MultipartFileInfo getFromRequest(HttpServletRequest request, String uploadDirName)
            throws ServletException, IOException {
        // 서블릿 컨텍스트를 이용하여 업로드 디렉토리를 생성
        ServletContext context = request.getSession().getServletContext();
        File uploadDir = new File(context.getRealPath(uploadDirName));
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 파일 정보를 읽어서 저장
        MultipartFileInfo info = new MultipartFileInfo();
        for (Part p : request.getParts()) {
            String fileName = "";
            // 요청 헤더의 content-disposition에서 파일 이름을 찾아 fileName에 저장
            for (String content : p.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    fileName = content.substring(content.indexOf("=") + 2, content.length() - 1).trim();
                    break;
                }
            }

            // fileName이 비어있으면 다음 Part
            if (fileName.equals("")) {
                continue;
            }

            // 파일 이름에 타임스탬프를 붙여서 저장 파일 이름을 생성
            long timestamp = Calendar.getInstance().getTimeInMillis();
            info.fileName = fileName;
            info.saveFileName = fileName.replaceFirst("\\.", timestamp + ".");

            // 파일 저장
            p.write(uploadDir.getAbsolutePath() + File.separator + info.saveFileName);
        }

        // 파일 정보를 반환 (파일이 없으면 null 반환)
        return info;
    }

}
