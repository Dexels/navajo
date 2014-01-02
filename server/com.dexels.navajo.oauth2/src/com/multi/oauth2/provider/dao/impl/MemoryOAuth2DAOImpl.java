package com.multi.oauth2.provider.dao.impl
;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oauth.v2.OAuth2Exception;

import com.multi.oauth2.provider.dao.OAuth2Service;
import com.multi.oauth2.provider.vo.ClientVO;
import com.multi.oauth2.provider.vo.TokenVO;
import com.multi.oauth2.provider.vo.UserVO;

public class MemoryOAuth2DAOImpl implements OAuth2Service {
	

	private final Map<String,UserVO> users = new HashMap<String,UserVO>();
	private final Map<String,List<ClientVO>> userClientMap = new HashMap<String, List<ClientVO>>();
	private final Map<String,ClientVO> clients = new HashMap<String, ClientVO>();
	private final Map<String,TokenVO> tokenByAccess = new HashMap<String, TokenVO>();
	private final Map<String,TokenVO> tokenByRefresh = new HashMap<String, TokenVO>();
	private final Map<String,TokenVO> tokenByCode = new HashMap<String, TokenVO>();
	
	public MemoryOAuth2DAOImpl() {
		addUser(new UserVO("alfredo","4lfredo","Alfredo Strudelini",99L));
		try {
			final String clientId = "client123";
			insertClient(clientId, "client_secret123", "alfredo", "client name","desciption of client");
			ClientVO cc = getClientOne(clientId);
			cc.setRedirect_uri("http://aap.nl");
		} catch (OAuth2Exception e) {
			e.printStackTrace();
		}

	}

	
	public void addUser(UserVO user) {
		users.put(user.getUserid(),user);
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#loginProcess(java.lang.String, java.lang.String)
	 */
	@Override
	public UserVO loginProcess(String userid, String password) throws OAuth2Exception {
		UserVO found = users.get(userid);
		if(found==null) {
			throw new OAuth2Exception("No such user");
		}
		if(password.equals(found.getPassword())) {
			return found;
		}
		throw new OAuth2Exception("Login failed!");
	}

	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#getUserInfo(java.lang.String)
	 */
	@Override
	public UserVO getUserInfo(String userid) throws OAuth2Exception {
		UserVO found = users.get(userid);
		if(found==null) {
			throw new OAuth2Exception("No such user");
		}
		throw new OAuth2Exception("No such user");
	}
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#getClientList(java.lang.String)
	 */
	@Override
	public List<ClientVO> getClientList(String userid) throws OAuth2Exception {
		List<ClientVO> result = userClientMap.get(userid);
		if(result==null) {
			List<ClientVO> entry = new ArrayList<ClientVO>();
			userClientMap.put(userid, entry);
			return entry;
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#getClientOne(java.lang.String)
	 */
	@Override
	public ClientVO getClientOne(String clientId) throws OAuth2Exception {
		ClientVO result = clients.get(clientId);
		if(result==null) {
			throw new OAuth2Exception("No client found for id: "+clientId);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#deleteClient(java.lang.String)
	 */
	@Override
	public void deleteClient(String clientId) throws OAuth2Exception {
		for (List<ClientVO> clients : userClientMap.values()) {
			Set<ClientVO> toDelete = new HashSet<ClientVO>(); 
			for (ClientVO client : clients) {
				if(client.getClient_id().equals(clientId)) {
					toDelete.add(client);
				}
			}
			clients.removeAll(toDelete);
		}
		clients.remove(clientId);
	}
	
//	#client_id#, #client_secret#, #userid#, #client_name#, #description#,
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#insertClient(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertClient(String clientId, String clientSecret, String userid, String clientName, String description) throws OAuth2Exception {
		ClientVO client = new ClientVO(clientId,clientSecret,userid,clientName,description,"client_url","client_type","scope","redirect_url",new Date());
		clients.put(clientId, client);
		List<ClientVO> prev = getClientList(userid);
		
		prev.add(client);
	}

//	#client_id#, #userid#, #access_token#, #refresh_token#, #token_type#, #scope#, #code#, #client_type#, #created_at#, #created_rt#, #expires_in#
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#createToken(com.multi.oauth2.provider.vo.TokenVO)
	 */
	@Override
	public void createToken(TokenVO vo) throws OAuth2Exception {
		tokenByAccess.put(vo.getAccess_token(), vo);
		tokenByRefresh.put(vo.getRefresh_token(), vo);
		tokenByCode.put(vo.getCode(), vo);
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#selectRefreshToken(java.lang.String)
	 */
	@Override
	public TokenVO selectRefreshToken(String refreshToken) throws OAuth2Exception {
		TokenVO result = tokenByRefresh.get(refreshToken);
		if(result==null) {
			throw new OAuth2Exception("No token found");
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#selectToken(java.lang.String)
	 */
	@Override
	public TokenVO selectToken(String accessToken) throws OAuth2Exception {
		TokenVO result = tokenByAccess.get(accessToken);
		if(result==null) {
			throw new OAuth2Exception("No token found");
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#selectTokenByCode(java.lang.String)
	 */
	@Override
	public TokenVO selectTokenByCode(String code) throws OAuth2Exception {
		TokenVO result = tokenByAccess.get(code);
		if(result==null) {
			throw new OAuth2Exception("No token found");
		}
		return result;
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
		TokenVO token = selectToken(accessToken);
		tokenByAccess.remove(token.getAccess_token());
		tokenByRefresh.remove(token.getRefresh_token());
		tokenByCode.remove(token.getCode());
	}
	
	/* (non-Javadoc)
	 * @see com.multi.oauth2.provider.dao.impl.OAuth2Service#deleteExpiredToken(long)
	 */
	@Override
	public void deleteExpiredToken(long ms) throws OAuth2Exception {
		
	}
	
	
	
}
