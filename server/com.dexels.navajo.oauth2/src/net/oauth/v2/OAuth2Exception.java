package net.oauth.v2;

public class OAuth2Exception extends Exception {

	private static final long serialVersionUID = 7796537621665049332L;
	private final int statusCode;
	
	protected OAuth2Exception() {
        this.statusCode = -1;
    }

    public OAuth2Exception(int statusCode, String message) {
        super(statusCode + ":" + message);
        this.statusCode = statusCode;
    }

    public OAuth2Exception(String message, Throwable cause) {
        super(message,cause);
        this.statusCode = -1;
    }

    public OAuth2Exception(String message) {
        super(message);
        this.statusCode = -1;
    }

	public int getStatusCode() {
		return statusCode;
	}

}
