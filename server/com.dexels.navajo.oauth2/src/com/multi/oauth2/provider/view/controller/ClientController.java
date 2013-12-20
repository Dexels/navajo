package com.multi.oauth2.provider.view.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import net.oauth.v2.OAuth2ErrorConstant;
import net.oauth.v2.OAuth2Exception;
import net.oauth.v2.OAuth2Scope;
import net.oauth.v2.OAuth2Util;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.UserVO;

public class ClientController extends HttpServlet {

	private static final long serialVersionUID = -7601378744208766092L;
	private OAuth2Service dao;
	
	private final ObjectMapper om = new ObjectMapper();
	
	public void setDao(OAuth2Service service) {
		dao = service;
	}
	
	public void clearDao(OAuth2Service service) {
		dao = null;
	}
	
	
@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getPathInfo();
		System.err.println("PATH: "+path);
		if(path.equals("/list")) {
			try {
				List<ClientVO> list = getClientList(req.getSession());
				resp.setContentType("application/json");
				om.writerWithDefaultPrettyPrinter().writeValue(resp.getWriter(), list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

	//	@RequestMapping(value="clientlist.do", method=RequestMethod.GET)
	public List<ClientVO> getClientList(HttpSession session) throws Exception {
		UserVO loginVO = (UserVO)session.getAttribute("userVO");
		if (loginVO == null) {
			// login?
			return null;
		}
		 
		return dao.getClientList(loginVO.getUserid());
	}
	
	public ClientVO detailClient(HttpSession session, String client_id) throws Exception {
		UserVO loginVO = (UserVO)session.getAttribute("userVO");
		if (loginVO == null) {
			// login?
			return null;
		}

		ClientVO vo2 = dao.getClientOne(client_id);
		return vo2;
	}
	
//	@RequestMapping(value="deleteclient.do", method=RequestMethod.GET)
	public String deleteClient(HttpSession session, 
			String client_id) throws Exception {
		UserVO loginVO = (UserVO)session.getAttribute("userVO");
		if (loginVO == null) {
			return "redirect:login.do";
		}

		
		dao.deleteClient(client_id);
		
		return "redirect:clientlist.do";
	}
	
//	@RequestMapping(value="insertclient.do" , method=RequestMethod.GET) 
	public String insertView(HttpSession session) {
		UserVO loginVO = (UserVO)session.getAttribute("userVO");
		if (loginVO == null) {
			return "redirect:login.do";
		}

		return "client/insert";
	}

//	@RequestMapping(value="insertclient.do" , method=RequestMethod.POST)
	public String insertClient(ClientVO vo,HttpSession session) throws Exception {
		UserVO loginVO = (UserVO)session.getAttribute("userVO");
		if (loginVO == null) {
			return "redirect:login.do";
		}
		
		String strScope = "";
		for (int i=0; i < vo.getScopes().size(); i++) {
			if (i>0)   strScope+=",";
			strScope+=vo.getScopes().get(i);
		}
		
		System.out.println(strScope);
		if (!OAuth2Scope.isScopeExistInMap(strScope)) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_SCOPE);
		}
		vo.setScope(strScope);
		
		vo.setUserid(loginVO.getUserid());
		OAuth2Util.generateClientIDSecret(vo);
		dao.insertClient(vo.getClient_id(),vo.getClient_secret(),vo.getUserid(),vo.getClient_name(),vo.getDescription());
		
		return "redirect:clientlist.do";
	}
	
}
