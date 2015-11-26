package com.dexels.navajo.article;

public class ArticleClientException extends Exception {
	private static final long serialVersionUID = 1592234822425045793L;
	
	public ArticleClientException(String string) {
		super(string);
	
	}
	
	public ArticleClientException(String message, Throwable root) {
		super(message,root);
	}
}
