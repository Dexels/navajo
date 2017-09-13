package com.dexels.navajo.article;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.oauth.api.Token;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ArticleRuntime {
	public String resolveArgument(String name) throws APIException;

	public void execute(ArticleContext articleServlet) throws APIException, NoJSONOutputException;

	public void pushNavajo(String name,Navajo res);

	public Navajo getNavajo(String name);


	public String getUsername();

	public void setUsername(String username);
	
	public void setMimeType(String mime);
	
	public Writer getOutputWriter() throws IOException;

	public Navajo getNavajo();

	public String getArticleName();
	
	public ObjectNode getMetadataRootNode();

	public void commit() throws IOException;

	public void writeNode(JsonNode node) throws IOException;

	public ObjectMapper getObjectMapper();

	public Map<String, String[]> getParameterMap();

	public ObjectNode getGroupNode(String name) throws APIException;

	public Set<String> getRequiredScopes();

	public Object resolveScope(String name) throws APIException;

	public String getInstance();

	public ObjectNode getRootNode();

	public Set<String> getSuppliedScopes();

	public Map<String, Object> getUserAttributes();

	public Token getToken();
	
	public void setAccess(Access a);

    public Access getAccess();
	
}
