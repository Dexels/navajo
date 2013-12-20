package com.multi.oauth2.provider.view.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import net.oauth.v2.OAuth2Constant;
import net.oauth.v2.OAuth2ErrorConstant;
import net.oauth.v2.OAuth2Exception;
import net.oauth.v2.OAuth2Util;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.TokenVO;
import com.multi.oauth2.provider.vo.UserVO;


public class ResourceController extends HttpServlet {
	
	private static final long serialVersionUID = 135451975671707061L;

	private OAuth2Service dao;
	
	public void setDao(OAuth2Service service) {
		dao = service;
	}
	
	public void clearDao(OAuth2Service service) {
		dao = null;
	}
	
	
//	@RequestMapping(value="resource/myinfo.do", method=RequestMethod.GET)
	public String getMyInfo(HttpServletRequest request) throws OAuth2Exception {
		
		TokenVO tVO = (TokenVO)request.getAttribute(OAuth2Constant.RESOURCE_TOKEN_NAME);

		UserVO uVO = null;
		try {
			uVO = dao.getUserInfo(tVO.getUserid());
		} catch(Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		uVO.setPassword(null);		
		
		
		ClientVO cVO = null;
		try {
			cVO = dao.getClientOne(tVO.getClient_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		String jsonClient = OAuth2Util.getJSONFromObject(cVO);
		System.out.println("##Client : " + jsonClient);
		
				
		String jsonUser = OAuth2Util.getJSONFromObject(uVO);
		System.out.println("##user : " + jsonUser);
		return jsonUser;
	}
}
