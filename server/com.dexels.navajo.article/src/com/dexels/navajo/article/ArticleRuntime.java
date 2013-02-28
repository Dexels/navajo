package com.dexels.navajo.article;

import java.io.IOException;
import java.io.Writer;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.document.Navajo;

public interface ArticleRuntime {
	public String resolveArgument(String name);

	public void execute(ArticleContext articleServlet) throws ArticleException;

	public void pushNavajo(String name,Navajo res);

	public Navajo getNavajo(String name);

	public String getPassword();
	public String getUsername();
	
	public void setMimeType(String mime);
	
	public Writer getOutputWriter() throws IOException;

	public Navajo getNavajo();

	public String getArticleName();
	
	public ObjectNode getMetadataRootNode();

}
