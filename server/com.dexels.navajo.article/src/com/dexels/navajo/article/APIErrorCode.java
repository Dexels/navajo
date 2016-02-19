package com.dexels.navajo.article;

public enum APIErrorCode {
	ConditionError(400, 1, null),
	MissingRequiredArgument(400, 2, "Missing required argument"),
	
	TokenUnauthorized(401, 1, "Invalid or expired token"),
	ClientIdUnauthorized(401, 2, "Invalid or expired client id"),
	
	MissingRequiredScopes(403, 1, "Required scopes missing"),
	
	ArticleNotFound(404, 1, "Article does not exist"),
	
	InternalError(500, 1, "Internal server error");

	private int httpStatusCode;
	private int internalCode;
	private String description;
	
	APIErrorCode(int httpStatusCode, int externalCode, String description) {
		this.httpStatusCode = httpStatusCode;
		this.internalCode = externalCode;
		this.description = description;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public Integer getExternalCode() {
		return Integer.valueOf(httpStatusCode + "" + internalCode);
	}

	public String getDescription() {
		return description;
	}
}
