package com.team4.museum.util;

import java.io.IOException;

import com.team4.museum.controller.action.member.LoginAjaxAction;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

final public class Security {

	/**
	 * 관리자가 아닐 경우 404 페이지로 포워딩
	 * 
	 * @return 관리자 여부
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean adminOr404Forward(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		return trueOr404Forward(LoginAjaxAction.isAdmin(request), request, response);
	}

	/**
	 * 멤머 아이디가 일치하지 않을 경우 404 페이지로 포워딩
	 * 
	 * @return 멤버 아이디 일치 여부
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean memberEqualsOr404Forward(
			String memberId,
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		MemberVO mvo = LoginAjaxAction.getLoginUserFrom(request);
		return trueOr404Forward(mvo != null && memberId.equals(mvo.getId()), request, response);
	}

	/**
	 * 주어진 조건이 참이 아닐 경우 404 페이지로 포워딩
	 * 
	 * @return 관리자 여부
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean trueOr404Forward(
			boolean condition,
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		if (!condition) {
			request.getRequestDispatcher("/WEB-INF/views/util/404.jsp").forward(request, response);
		}
		return condition;
	}

	/**
	 * 관리자가 아닐 경우 경고창 스크립트만 발송
	 * 
	 * @return 관리자 여부
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean adminOrAlert(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		return trueOrAlert(LoginAjaxAction.isAdmin(request), response);
	}

	/**
	 * 멤머 아이디가 일치하지 않을 경우 404 페이지로 포워딩
	 * 
	 * @return 멤버 아이디 일치 여부
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean memberEqualsOrAlert(
			String memberId,
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		MemberVO mvo = LoginAjaxAction.getLoginUserFrom(request);
		return trueOrAlert(mvo != null && memberId.equals(mvo.getId()), response);
	}

	/**
	 * 주어진 조건이 참이 아닐 경우 경고창 스크립트만 발송
	 * 
	 * @return 관리자 여부
	 * @throws IOException
	 * @throws ServletException
	 */
	public static boolean trueOrAlert(
			boolean condition,
			HttpServletResponse response)
			throws ServletException, IOException {
		if (!condition) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script>alert('잘못된 요청입니다.'); history.back();</script>");
		}
		return condition;
	}

}