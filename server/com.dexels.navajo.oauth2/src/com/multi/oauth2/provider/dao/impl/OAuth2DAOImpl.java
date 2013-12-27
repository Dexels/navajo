package com.multi.oauth2.provider.dao.impl
;

import java.util.List;

import net.oauth.v2.OAuth2Exception;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.TokenVO;
import com.multi.oauth2.provider.vo.UserVO;

public class OAuth2DAOImpl implements OAuth2Service {
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#loginProcess(java.lang.String, java.lang.String)
	 */
	@Override
	public UserVO loginProcess(String userid, String password) throws OAuth2Exception {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#getUserInfo(java.lang.String)
	 */
	@Override
	public UserVO getUserInfo(String userid) throws OAuth2Exception {
		return null;
	}
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#getClientList(java.lang.String)
	 */
	@Override
	public List<ClientVO> getClientList(String userid) throws OAuth2Exception {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#getClientOne(java.lang.String)
	 */
	@Override
	public ClientVO getClientOne(String clientId) throws OAuth2Exception {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#deleteClient(java.lang.String)
	 */
	@Override
	public void deleteClient(String clientId) throws OAuth2Exception {
	}
	
//	#client_id#, #client_secret#, #userid#, #client_name#, #description#,
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#insertClient(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertClient(String clientId, String clientSecret, String userid, String clientName, String description) throws OAuth2Exception {
	}

//	#client_id#, #userid#, #access_token#, #refresh_token#, #token_type#, #scope#, #code#, #client_type#, #created_at#, #created_rt#, #expires_in#
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#createToken(com.multi.oauth2.provider.vo.TokenVO)
	 */
	@Override
	public void createToken(TokenVO vo) throws OAuth2Exception {
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#selectRefreshToken(java.lang.String)
	 */
	@Override
	public TokenVO selectRefreshToken(String refreshToken) throws OAuth2Exception {
		return null;
	}
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#selectToken(java.lang.String)
	 */
	@Override
	public TokenVO selectToken(String accessToken) throws OAuth2Exception {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#selectTokenByCode(java.lang.String)
	 */
	@Override
	public TokenVO selectTokenByCode(String code) throws OAuth2Exception {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#updateAccessToken(java.lang.String, java.lang.String, long)
	 */
	@Override
	public void updateAccessToken(String refreshToken, String accessToken, long created_at) throws OAuth2Exception {
		TokenVO r = selectRefreshToken(refreshToken);
		r.setAccess_token(accessToken);
		r.setCreated_at(created_at);
	}
	

	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#deleteToken(java.lang.String)
	 */
	@Override
	public void deleteToken(String accessToken) throws OAuth2Exception {

	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#deleteExpiredToken(long)
	 */
	@Override
	public void deleteExpiredToken(long ms) throws OAuth2Exception {
		
	}
	
	
	
}
