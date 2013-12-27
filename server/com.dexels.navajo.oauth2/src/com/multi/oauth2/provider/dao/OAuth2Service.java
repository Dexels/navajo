package com.multi.oauth2.provider.dao;

import java.util.List;

import net.oauth.v2.OAuth2Exception;

import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.TokenVO;
import com.multi.oauth2.provider.vo.UserVO;

public interface OAuth2Service {

	public UserVO loginProcess(String userid, String password) throws OAuth2Exception;

	public UserVO getUserInfo(String userid) throws OAuth2Exception;

	public List<ClientVO> getClientList(String userid) throws OAuth2Exception;

	public ClientVO getClientOne(String clientId) throws OAuth2Exception;

	public void deleteClient(String clientId) throws OAuth2Exception;

	public void insertClient(String clientId, String clientSecret,
			String userid, String clientName, String description)
			throws OAuth2Exception;

	//	#client_id#, #userid#, #access_token#, #refresh_token#, #token_type#, #scope#, #code#, #client_type#, #created_at#, #created_rt#, #expires_in#
	public void createToken(TokenVO vo) throws OAuth2Exception;

	public TokenVO selectRefreshToken(String refreshToken) throws OAuth2Exception;

	public TokenVO selectToken(String accessToken) throws OAuth2Exception;

	public TokenVO selectTokenByCode(String code) throws OAuth2Exception;

	public void updateAccessToken(String refreshToken, String accessToken,
			long created_at) throws OAuth2Exception;

	public void deleteToken(String accessToken) throws OAuth2Exception;

	public void deleteExpiredToken(long ms) throws OAuth2Exception;

}