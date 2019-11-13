package com.dexels.navajo.api.util;

import java.util.Optional;

public class NavajoRequestConfig {
	/**
	 * Will calculate a request timeout, from the NAVAJO_REQUEST_TIMEOUT environment variable. 
	 * @param orLowerValue default value, or when lower than the env it will use that.
	 * @return
	 */
	public static long getRequestTimeout(long orLowerValue) {
		return Optional.ofNullable(System.getenv("NAVAJO_REQUEST_TIMEOUT"))
					.filter(e->!e.equals(""))
					.map(Long::parseLong)
					.map(e-> (e>orLowerValue) ? orLowerValue : e)
					.orElse(orLowerValue);
	}

}
