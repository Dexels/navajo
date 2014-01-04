package com.multi.oauth2.provider.view.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.oauth.v2.OAuth2Exception;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.UserVO;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1750016357882414605L;
	private OAuth2Service dao;

	public void setDao(OAuth2Service service) {
		dao = service;
	}

	public void clearDao(OAuth2Service service) {
		dao = null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userid = req.getParameter("userid");
		String password = req.getParameter("password");
		String clientId = req.getParameter("clientId");
		req.getSession().setAttribute("clientId", clientId);
		try {
			String result = login(req.getSession(), userid, password);
			ClientVO cv =  dao.getClientOne(clientId);
			if(cv!=null) {
				String red = cv.getRedirect_uri();
				if(red!=null) {
					resp.sendRedirect(red);
				}
			}
			resp.sendRedirect(result);
			resp.getWriter().write(result);
		} catch (OAuth2Exception e) {
			e.printStackTrace();
			resp.sendError(e.getStatusCode(),  e.getMessage());
		}
	}

	// @RequestMapping(value="login.do", method=RequestMethod.POST)
	private String login(HttpSession session, String userid, String password)
			throws OAuth2Exception {
		UserVO vo2 = dao.loginProcess(userid, password);
		if (vo2 == null) {
			return "/auth/index.jsp?message='Login failed'";
		} else {
			session.setAttribute("user", vo2);
			List<ClientVO> clients = dao.getClientList(vo2.getUserid());
			session.setAttribute("clients", clients);
			return "/auth/client.jsp";
		}
	}
}
