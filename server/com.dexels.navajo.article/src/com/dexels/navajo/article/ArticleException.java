package com.dexels.navajo.article;

public class ArticleException extends Exception {

	private static final long serialVersionUID = 2365373458411061048L;

	public ArticleException(String message) {
		super(message);
	}

	public ArticleException(String message, Throwable root) {
		super(message,root);
	}

}
