package net.oauth.v2;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.multi.oauth2.provider.vo.ClientVO;

@SuppressWarnings("deprecation")
public class OAuth2Util {

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		// 1.9.x ���� ����
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
		// 1.8.x ���� ����.
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
				false);
		mapper.configure(SerializationConfig.Feature.WRITE_EMPTY_JSON_ARRAYS,
				false);
		mapper.configure(
				DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
				true);
	}

	// URL Encoding Utility
	public static String encodeURIComponent(String s) {
		String result;

		try {
			result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!").replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(").replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}

		return result;
	}

	public static String decodeURIComponent(String s) {
		if (s == null) {
			return null;
		}
		String result = null;
		try {
			result = URLDecoder.decode(s, "UTF-8");
		}
		// This exception should never occur.
		catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}

	public static byte[] hexToBinary(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer
					.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	// byte[] to hex
	public static String binaryToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	public static String getHmacSha256(String str) {
		byte[] binary = null;
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes("UTF-8")); 
			binary = sh.digest();
		}catch(Exception e){
			e.printStackTrace(); 
		}

		return binaryToHex(binary);
	}

	public static String encodeBase64String(String data) {
		byte[] binary;
		try {
			binary = data.getBytes("UTF-8");
			return new String( Base64.encodeBase64(binary));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decodeBase64String(String base64String) {
		try {
			byte[] binary = Base64.decodeBase64(base64String.getBytes());
			return new String(binary, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String generateToken() {
		SecureRandom secureRandom;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(secureRandom.generateSeed(256));
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] dig = digest.digest((secureRandom.nextLong() + "")
					.getBytes());
			return binaryToHex(dig);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ���� : GET �������� access token�� ������ �� ����
	// grant_type�� password�� ����(Password Credential ������ ���� Access Token ������ ��)
	//
	// Authorization : Basic XXXXXXXXXX
	public static String generateBasicAuthHeaderString(RequestBaseVO token) {
		try {
			String base = "";
			if (token.getClient_secret() == null
					|| token.getClient_secret() == "") {
				base = encodeURIComponent(token.getClient_id());
			} else {
				base = encodeURIComponent(token.getClient_id()) + ":"
						+ encodeURIComponent(token.getClient_secret());
			}

			return "Basic " + Base64.encodeBase64(base.getBytes("UTF-8"));

		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static void parseBasicAuthHeader(String authHeader,
			RequestBaseVO token) throws OAuth2Exception {
		try {
			// Basic XXXXXXXXX
			String basicToken = authHeader.split(" ")[1];
			String decoded = new String(Base64.decodeBase64(basicToken.getBytes()), "utf-8");
			String[] temp = decoded.split(":");
			if (temp.length == 2) {
				token.setClient_id(temp[0]);
				token.setClient_secret(temp[1]);
			} else {
				token.setClient_id(temp[0]);
			}
		} catch (Exception e) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
		}
	}

	public static String generateBearerToken(String access_token) {
		return "Bearer " + access_token;
	}

	public static String parseBearerToken(String authHeader) {
		return authHeader.split(" ")[1];
	}

	public static String getJSONFromObject(Object obj) {
		try {
			StringWriter sw = new StringWriter(); // serialize
			mapper.writeValue(sw, obj);
			sw.close();

			return sw.getBuffer().toString();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T getObjectFromJSON(String json, Class<T> classOfT) {
		try {
			return mapper.readValue(json.getBytes("UTF-8"), classOfT);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void generateClientIDSecret(ClientVO vo) {
		try {
			UUID uuid = UUID.randomUUID();
			vo.setClient_id(uuid.toString());
			vo.setClient_secret(OAuth2Util.generateToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long getCurrentTimeStamp() {
		return System.currentTimeMillis();
	}
	
	public static String getAccessTokenToFormUrlEncoded(ResponseAccessTokenVO vo) {
		String s = OAuth2Constant.ACCESS_TOKEN + "=" + encodeURIComponent(vo.getAccess_token());
		s+= "&" + OAuth2Constant.TOKEN_TYPE + "=" + OAuth2Constant.TOKEN_TYPE_BEARER;

		if (vo.getRefresh_token() != null)
			s+= "&" + OAuth2Constant.REFRESH_TOKEN + "=" + encodeURIComponent(vo.getRefresh_token());
		if (vo.getExpires_in() != 0)
			s+= "&" + OAuth2Constant.EXPIRES_IN + "=" + +vo.getExpires_in();
		if (vo.getIssued_at() != 0)
			s+= "&" + OAuth2Constant.ISSUED_AT + "=" + vo.getIssued_at();
		if (vo.getState() != null) {
			s+= "&" + OAuth2Constant.STATE + "=" + vo.getState();
		}
		
		return s;
	}
	
	public static String getAccessTokenToJson(ResponseAccessTokenVO vo) {
		return getJSONFromObject(vo);
	}
	
	
}
