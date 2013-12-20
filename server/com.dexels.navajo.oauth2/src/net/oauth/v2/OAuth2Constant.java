package net.oauth.v2;


public class OAuth2Constant {
	
	//---refresh token�� ��� ����
	//������ ���ݿ� �°� �����Ѵ�. �����ϰ� ������ ���� ������� �ʴ´�.
	public static final boolean USE_REFRESH_TOKEN = false;
	//access token�� �����ͺ��̽��� �������� �ʰ� ������ ��Ģ�� ���� �����ϰ��� �� �� ����� ��ȣȭŰ
	//�ܺη� ���� ����Ǿ�� �ȵ�. ����� ��� ����ϰ� ���Ӱ� �����ؾ� ��. OAuth2AES Ŭ���� ����
	//Ű���̴� �ݵ�� 16,24,32���ڿ��� ��.
	public static final String AES_ENCRYPTION_KEY = "oauth2provider_1";
	
	//�������� ó�� �� ����� ���
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String RESPONSE_TYPE = "response_type";
	public static final String STATE = "state";
	public static final String SCOPE = "scope";
	public static final String CODE = "code";
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String GRANT_TYPE = "grant_type";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String REFRESH_TOKEN = "refresh_token";	
	public static final String TOKEN_TYPE = "token_type";
	public static final String EXPIRES_IN = "expires_in";
	public static final String ISSUED_AT = "issued_at";
	
	//Expires_in �� : �����ڰ� ���� �ʴ����� �����Ѵ�. �⺻�� �ѽð����� �����Ͽ���.
	public static final long EXPIRES_IN_VALUE = 60*60;		
	
	//Response Type
	public static final String RESPONSE_TYPE_CODE = "code";
	public static final String RESPONSE_TYPE_TOKEN = "token";
	
	
	//Token Type
	public static final String TOKEN_TYPE_BEARER = "bearer";
	public static final String TOKEN_TYPE_MAC = "mac";
	public static final String TOKEN_TYPE_JWT = "jwt";
		
	//Grant type
	public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
	public static final String GRANT_TYPE_PASSWORD = "password";
	public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
	public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	
	//���� �߻��� ����� ���
	public static final String ERROR = "error";
	public static final String ERROR_DESCRIPTION = "error_description";
	
	//Request Header
	public static final String HEADER_AUTHORIZATION = "Authorization";

	//���ͼ��Ϳ��� ��ȿ�� ��� controller�� forward��ų �� request��ü�� �߰��� �� ����ϴ� Ű��
	public static final String RESOURCE_TOKEN_NAME = "resource_token";
	
	public static void main(String[] args) {
		
	}
}
