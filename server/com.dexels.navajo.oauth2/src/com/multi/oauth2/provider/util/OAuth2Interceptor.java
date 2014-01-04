package com.multi.oauth2.provider.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.v2.OAuth2Constant;
import net.oauth.v2.OAuth2ErrorConstant;
import net.oauth.v2.OAuth2Exception;
import net.oauth.v2.OAuth2Scope;
import net.oauth.v2.OAuth2Util;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.vo.TokenVO;

public class OAuth2Interceptor implements Filter {

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

	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response) throws OAuth2Exception {
		String uri = request.getMethod() + " " + 
				request.getRequestURI().substring(request.getContextPath().length());
		
		String scope = OAuth2Scope.getScopeFromURI(uri);
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
		}
		

		String accessToken = OAuth2Util.parseBearerToken(authHeader);
		TokenVO tVO = null;
		if (OAuth2Constant.USE_REFRESH_TOKEN) {
			
			try {
				tVO = dao.selectToken(accessToken);
			} catch (Exception e) {
				e.printStackTrace();
				throw new OAuth2Exception(500, OAuth2ErrorConstant.SERVER_ERROR);
			}
			
			if (tVO == null) {
				throw new OAuth2Exception(401, OAuth2ErrorConstant.UNAUTHORIZED_CLIENT);
			}
		} else {
			tVO = tokenService.validateAccessToken(accessToken);
		}
		
		if (!OAuth2Scope.isUriScopeValid(scope, tVO.getScope())) {
			throw new OAuth2Exception(401, OAuth2ErrorConstant.INSUFFICIENT_SCOPE);
		}
		
		if (OAuth2Constant.USE_REFRESH_TOKEN) {
			long expires_in = tVO.getExpires_in();		
			long created_at = tVO.getCreated_at();	
			long current_ts = OAuth2Util.getCurrentTimeStamp();	
			if (current_ts > created_at + expires_in * 1000) {
				throw new OAuth2Exception(401, OAuth2ErrorConstant.EXPIRED_TOKEN);
			}
		}
		
		if (tVO.getClient_type().equals("U")) {
			String referer = request.getHeader("Referer");
			
			int index = referer.indexOf("/", 7);
			String origin = referer.substring(0, index);
			
			System.out.println("### origin : " + origin);
			if (origin != null) {
				response.addHeader("Access-Control-Allow-Origin", origin);	
			}
		}
		
		if (OAuth2Constant.USE_REFRESH_TOKEN) {
			dao.deleteExpiredToken(7*24*60*60*1000);
		}
		
		request.setAttribute(OAuth2Constant.RESOURCE_TOKEN_NAME, tVO);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		
		return true;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest)req;
		final HttpServletResponse response = (HttpServletResponse)resp;
		
		try {
			preHandle(request, response);
		} catch (OAuth2Exception e) {
			response.sendError(e.getStatusCode(), e.getMessage());
			return;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
}

