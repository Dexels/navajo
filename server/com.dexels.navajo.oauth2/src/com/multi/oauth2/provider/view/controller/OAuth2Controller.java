package com.multi.oauth2.provider.view.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.oauth.v2.OAuth2Constant;
import net.oauth.v2.OAuth2ErrorConstant;
import net.oauth.v2.OAuth2Exception;
import net.oauth.v2.OAuth2Scope;
import net.oauth.v2.OAuth2Util;
import net.oauth.v2.RequestAccessTokenVO;
import net.oauth.v2.RequestAuthVO;
import net.oauth.v2.ResponseAccessTokenVO;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.util.OAuth2AccessTokenService;
import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.TokenVO;
import com.multi.oauth2.provider.vo.UserVO;

public class OAuth2Controller extends HttpServlet {

	private static final long serialVersionUID = 5956732067611136952L;

	private OAuth2Service dao;
	
	private OAuth2AccessTokenService tokenService;
	
	public void setDao(OAuth2Service service) {
		dao = service;
	}
	
	public void clearDao(OAuth2Service service) {
		dao = null;
	}
	
	public void setTokenService(OAuth2AccessTokenService service) {
		tokenService = service;
	}
	
	public void clearTokenService(OAuth2AccessTokenService service) {
		tokenService = null;
	}

	
	
	private TokenVO createTokenToTable(String clientId, String scope, String userId, String clientType) throws OAuth2Exception {
		TokenVO tVO;
		try {
			tVO = new TokenVO();
			tVO.setClient_id(clientId);
			
			tVO.setUserid(userId);
			tVO.setToken_type(OAuth2Constant.TOKEN_TYPE_BEARER);
			tVO.setScope(scope);
			
			tVO.setExpires_in(OAuth2Constant.EXPIRES_IN_VALUE);
			tVO.setRefresh_token(OAuth2Util.generateToken());
			tVO.setCode(OAuth2Util.generateToken());
			tVO.setClient_type(clientType);
			
			tVO.setAccess_token(OAuth2Util.generateToken());
			long currentTimeStamp = OAuth2Util.getCurrentTimeStamp();
			tVO.setCreated_at(currentTimeStamp);
			tVO.setCreated_rt(currentTimeStamp);
			
			System.out.println(tVO);
			dao.createToken(tVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		return tVO;
	}
	
	private TokenVO refreshToken(String clientRefreshToken) throws OAuth2Exception {
		TokenVO tVO= null;
		try {
			tVO = dao.selectRefreshToken(clientRefreshToken);
		} catch(Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		if (tVO == null) {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_TOKEN);
		}
		
		tVO.setAccess_token(OAuth2Util.generateToken());
		tVO.setCreated_at(OAuth2Util.getCurrentTimeStamp());
		
		try {
			dao.updateAccessToken(tVO.getRefresh_token(),tVO.getAccess_token(),tVO.getCreated_at());
		} catch(Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(401, OAuth2ErrorConstant.INVALID_TOKEN);
		}
		
		return tVO;
	}
	
//	@RequestMapping(value = "auth", method = RequestMethod.GET)
	public Map<String,Object> authorize(RequestAuthVO vo, HttpServletResponse response,
			HttpServletRequest request) throws OAuth2Exception {
//		ModelAndView mav = new ModelAndView();
		Map<String,Object> result = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserVO loginnedVO = (UserVO) session.getAttribute("userVO");

		if (loginnedVO != null) {
			result.put("isloginned", true);
		} else {
			result.put("isloginned", false);
		}
		
		System.out.println("## server flow 2.1");
		ClientVO cVO=null;
		try {
			cVO = dao.getClientOne(vo.getClient_id());
		} catch (Exception e) {
			throw new OAuth2Exception(500,OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		if (cVO == null) {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		
		System.out.println("## server flow 2.2");
		System.out.println(vo.getResponse_type());
		
		if (!vo.getResponse_type().equals(OAuth2Constant.RESPONSE_TYPE_CODE) && 
			!vo.getResponse_type().equals(OAuth2Constant.RESPONSE_TYPE_TOKEN)	) {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		}
		
		System.out.println("## server flow 3");
		if (!OAuth2Scope.isScopeValid(vo.getScope(), cVO.getScope())) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_SCOPE);
		}
		
		System.out.println("## server flow 4");
		String gt = vo.getResponse_type();
		if (!gt.equals(OAuth2Constant.RESPONSE_TYPE_CODE) 
				&& !gt.equals(OAuth2Constant.RESPONSE_TYPE_TOKEN)) {
			throw new OAuth2Exception(400,
					OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		}

		System.out.println("## server flow 5");
		result.put("requestAuthVO", vo);
		result.put("clientVO", cVO);
		return result;
	}
	
//	@RequestMapping(value = "auth", method = RequestMethod.POST)
	public String authorizePost(RequestAuthVO rVO, HttpServletRequest request, HttpServletResponse response) throws OAuth2Exception {
		String isAllow = request.getParameter("isallow");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if (!isAllow.equals("true")) {
			return "auth/auth_deny";
		}
		
		UserVO uVO = null;
		if (userid != null && password!=null) {
			try {
				uVO = dao.loginProcess(userid,password);
			} catch (Exception e) {
				e.printStackTrace();
				return "auth/auth.jsp";
			}
		} else if (request.getSession().getAttribute("userVO") != null){
			uVO = (UserVO)request.getSession().getAttribute("userVO");
		} else {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		
		System.out.println("## rVO ClientID : " + rVO.getClient_id());
		ClientVO cVO;
		try {
			cVO = dao.getClientOne(rVO.getClient_id());
		} catch (Exception e) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		if (cVO == null) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		
		if (!OAuth2Scope.isScopeValid(rVO.getScope(), cVO.getScope())) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_SCOPE);
		}

		if (!rVO.getRedirect_uri().equals(cVO.getRedirect_uri())) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.NOT_MATCH_REDIRECT_URI);
		}

		TokenVO tVO = createTokenToTable(rVO.getClient_id(),rVO.getScope(), uVO.getUserid(), cVO.getClient_type());
		
		String response_type = rVO.getResponse_type();
		String redirect = "";
		if (response_type.equals(OAuth2Constant.RESPONSE_TYPE_CODE)) {
			redirect = "redirect:"+ rVO.getRedirect_uri() + "?code=" + tVO.getCode();
			if (rVO.getState() != null) {
				redirect += "&state=" + rVO.getState(); 
			}
		} else if (response_type.equals(OAuth2Constant.RESPONSE_TYPE_TOKEN)) {
			ResponseAccessTokenVO tokenVO = null;

			if (OAuth2Constant.USE_REFRESH_TOKEN) {
				tokenVO =new ResponseAccessTokenVO(
						tVO.getAccess_token(), tVO.getToken_type(), 
						tVO.getExpires_in(), tVO.getRefresh_token(), rVO.getState(), tVO.getCreated_at());				
			} else {
				tokenVO = new ResponseAccessTokenVO(
						this.tokenService.generateAccessToken(cVO.getClient_id(), cVO.getClient_secret(), uVO.getUserid(), password), 
						OAuth2Constant.TOKEN_TYPE_BEARER, 0, null, rVO.getState(), tVO.getCreated_at());
				tokenVO.setExpires_in(0);
				tokenVO.setRefresh_token(null);
			}

			String acc = OAuth2Util.getAccessTokenToFormUrlEncoded(tokenVO);
			redirect = "redirect:" + rVO.getRedirect_uri() + "#" + acc;
		} else {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		}
		
		return redirect;
	}
	
	
//	@RequestMapping(value = "token")
	public String accessToken(RequestAccessTokenVO ratVO, HttpServletRequest request) throws OAuth2Exception {
		
		String json = "";
		
		System.out.println("### token flow 1");
		System.out.println("### grant_type : " + ratVO.getGrant_type());
		
		if (ratVO.getGrant_type().equals(OAuth2Constant.GRANT_TYPE_AUTHORIZATION_CODE)) {
			ResponseAccessTokenVO resVO = accessTokenServerFlow(ratVO, request);
			json = OAuth2Util.getJSONFromObject(resVO);
		} else if (ratVO.getGrant_type().equals(OAuth2Constant.GRANT_TYPE_PASSWORD)) {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		} else if (ratVO.getGrant_type().equals(OAuth2Constant.GRANT_TYPE_CLIENT_CREDENTIALS)) {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		} else if (ratVO.getGrant_type().equals(OAuth2Constant.GRANT_TYPE_REFRESH_TOKEN)) {
			if (OAuth2Constant.USE_REFRESH_TOKEN) {
				ResponseAccessTokenVO resVO = refreshTokenFlow(ratVO, request);
				json = OAuth2Util.getJSONFromObject(resVO);
			} else {
				throw new OAuth2Exception(500, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
			}
		} else {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.UNSUPPORTED_RESPONSE_TYPE);
		}
		return json;
	}

	private ResponseAccessTokenVO accessTokenServerFlow(RequestAccessTokenVO ratVO, HttpServletRequest request) throws OAuth2Exception {
		
		
		System.out.println("### token flow 2");
		if (request.getMethod().equalsIgnoreCase("GET")) {
			String authHeader = request.getHeader("Authorization");
			if (authHeader == null || authHeader.equals("")) {
				throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
			}
			OAuth2Util.parseBasicAuthHeader(authHeader, ratVO);
		}
		
		System.out.println("### token flow 3");
		if (ratVO.getClient_id() ==null || ratVO.getClient_secret() == null ) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
		}
		
		ClientVO cVO = null;
		try {
			cVO = dao.getClientOne(ratVO.getClient_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		System.out.println("### token flow 4");
		if (cVO == null) {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		
		System.out.println("### token flow 5");
		if (!ratVO.getRedirect_uri().equals(cVO.getRedirect_uri())) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.NOT_MATCH_REDIRECT_URI);
		}
		
		System.out.println("### token flow 6");
		if (ratVO.getCode() == null) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
		}
		
		TokenVO tVO = null;
		try {
			tVO = dao.selectTokenByCode(ratVO.getCode());
		} catch(Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		if (tVO == null) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_CODE);
		}
		
		if (OAuth2Constant.USE_REFRESH_TOKEN) {
			System.out.println("### token flow 7");
			long expires = tVO.getCreated_at() + tVO.getExpires_in();
			if (System.currentTimeMillis() > expires) {
				throw new OAuth2Exception(400, OAuth2ErrorConstant.EXPIRED_TOKEN);
			}
		}	
		
		System.out.println("### token flow 9");
		ResponseAccessTokenVO resVO = new ResponseAccessTokenVO();

		resVO.setIssued_at(tVO.getCreated_at());
		resVO.setState(ratVO.getState());
		resVO.setToken_type(tVO.getToken_type());
		if (OAuth2Constant.USE_REFRESH_TOKEN) {
			resVO.setAccess_token(tVO.getAccess_token());
			resVO.setExpires_in(tVO.getExpires_in());
			resVO.setRefresh_token(tVO.getRefresh_token());
		} else {
			UserVO uVO = null;
			try {
				uVO = dao.getUserInfo(tVO.getUserid());
			} catch (Exception e) {
				e.printStackTrace();
				throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
			}
			
			if (uVO == null) {
				throw new OAuth2Exception(500, OAuth2ErrorConstant.INVALID_USER);
			}
			
			try {
				dao.deleteToken(tVO.getAccess_token());
			} catch (Exception e) {
				e.printStackTrace();
				throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
			}
			
			resVO.setAccess_token(this.tokenService.generateAccessToken(cVO.getClient_id(), cVO.getClient_secret(), uVO.getUserid(), uVO.getPassword()));
		}

		return resVO;
		
	}
	
	private ResponseAccessTokenVO refreshTokenFlow(RequestAccessTokenVO ratVO, HttpServletRequest request) throws OAuth2Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			String authHeader = request.getHeader("Authorization");
			if (authHeader == null || authHeader.equals("")) {
				throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
			}
			OAuth2Util.parseBasicAuthHeader(authHeader, ratVO);
		}
		
		if (ratVO.getClient_id() ==null || ratVO.getClient_secret() == null ) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
		}

		ClientVO cVO = null;
		try {
			cVO = dao.getClientOne(ratVO.getClient_id());
		} catch(Exception e) {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		if (cVO == null) {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		
		if (ratVO.getClient_secret() != null && !cVO.getClient_secret().equals(ratVO.getClient_secret())) {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		
		if (ratVO.getRefresh_token() == null) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
		}
		
		TokenVO tVO = null;
		try {
			tVO = dao.selectRefreshToken(ratVO.getRefresh_token());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}
		
		if (tVO == null) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_TOKEN);
		}
		
		tVO.setAccess_token(OAuth2Util.generateToken());
		tVO.setCreated_at(OAuth2Util.getCurrentTimeStamp());
		try {
			dao.updateAccessToken(tVO.getRefresh_token(),tVO.getAccess_token(),tVO.getCreated_at());
		} catch (Exception e) {
			throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
		}

		ResponseAccessTokenVO resVO = new ResponseAccessTokenVO(
				tVO.getAccess_token(), tVO.getToken_type(), 
				tVO.getExpires_in(), null, ratVO.getState(), tVO.getCreated_at());
		
		
		return resVO;
	}
}
