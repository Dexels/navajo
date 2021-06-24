/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.TmlNavajoTypeSerializer;
import com.dexels.navajo.document.json.TmlPropertySerializer;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.oauth.api.OAuthToken;
import com.dexels.oauth.api.Scope;
import com.dexels.oauth.api.Token;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class BaseRuntimeImpl implements ArticleRuntime {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseRuntimeImpl.class);

	private final Stack<Navajo> navajoStack = new Stack<Navajo>();
	private final Map<String, Navajo> navajoStore = new HashMap<String, Navajo>();
	private final Set<String> suppliedScopes; 
	protected final XMLElement article;
	private final String articleName;

	private final ObjectMapper mapper = new ObjectMapper();
	private final ObjectNode rootNode;

	private final String instance;

	private final Map<String, Object> userAttributes;
	private final OAuthToken token;


	protected BaseRuntimeImpl(String articleName, XMLElement article, Set<String> suppliedScopes, String instance) {
		rootNode = mapper.createObjectNode();
		setupJackson();
		this.article = article;
		this.articleName = articleName;
		this.suppliedScopes = suppliedScopes;
		this.instance = instance;
		this.userAttributes = new HashMap<String, Object>();
		this.token = null;
	}

	@SuppressWarnings("deprecation")
	protected BaseRuntimeImpl(String articleName, File articleFile,String instance, OAuthToken token)
			throws IOException {
		article = new CaseSensitiveXMLElement();
		setupJackson();
		rootNode = mapper.createObjectNode();
		this.token = token;
		
		this.suppliedScopes = new HashSet<String>();
		if(token!=null) {
			for (Scope scope : this.token.getScopes()) {
				this.suppliedScopes.add(scope.getId());
			}
			if (this.token.getUser() != null) {
				this.userAttributes = this.token.getUser().getAttributes();
			} else {
				this.userAttributes = this.token.getAttributes();
			}
			
		} else {
			
			this.userAttributes = new HashMap<String, Object>();

		}
		this.articleName = articleName;
		this.instance = instance;
		Reader r = null;
		try {
			r = new FileReader(articleFile);
			article.parseFromReader(r);
		} catch (IOException e) {
			throw e;
		} finally {
			if (r != null) {
				r.close();
			}
		}
	}
	
	   private void setupJackson() {
	        SimpleModule module = new SimpleModule("MyModule", Version.unknownVersion());
	        module.addSerializer(Binary.class, new TmlNavajoTypeSerializer());
	        module.addSerializer(Property.class, new TmlPropertySerializer());
	        mapper.registerModule(module);
	    }
	
	protected void verifyScopes() throws APIException {
		Set<String> missing = null;
		for (String scope : getRequiredScopes()) {
			if(!suppliedScopes.contains(scope)) {
				if(missing==null) {
					missing = new HashSet<String>();
				}
				missing.add(scope);
			}
		}
		if(missing!=null && !missing.isEmpty()) {
			throw new APIException("Required scopes: " + missing + " missing", null, APIErrorCode.MissingRequiredScopes);
		}
	}
	
	@Override
	public Set<String> getRequiredScopes() {
		Set<String> result = new HashSet<String>();
		String required = article.getStringAttribute("scopes");
		if(required!=null) {
			String[] arr = required.split(",");
			for (String element : arr) {
				result.add(element);
			}
		}
		return result;
	}
	
	@Override
	public Object resolveScope(String name) throws APIException {
		if(!name.startsWith("$")) {
			throw new APIException("scope references should start with $", null, APIErrorCode.InternalError);
		}
		String stripped = name.substring(1);
		if (!userAttributes.containsKey(stripped)) {
			throw new APIException("Article problem in " + articleName + ". setvalue refers to scope: " + name + " which is not supplied", null, APIErrorCode.InternalError);
		}
		return userAttributes.get(stripped);
	}

	@Override
	public Set<String> getSuppliedScopes() {
		return suppliedScopes;
	}
	
	public ObjectNode getGroupNode(ObjectNode parent, String name) throws APIException {
		JsonNode existing = parent.get(name);
		if(existing!=null) {
			if(existing instanceof ObjectNode) {
				return (ObjectNode) existing;
			} else {
				throw new APIException("Error getting group node: "+name+" there is an existing node, but it is not an ObjectNode", null, APIErrorCode.InternalError);
			}
		}
		ObjectNode result = mapper.createObjectNode();
		rootNode.set(name, result);
		return result;
	}
	
	@Override
	public ObjectNode getRootNode() {
		return rootNode;
	}
	
	@Override
	public ObjectNode getGroupNode( String name) throws APIException {
		String[] split = name.split("/");
		int i = 0;
		ObjectNode current = null;
		for (String path : split) {
			if(i==0) {
				current = getGroupNode(rootNode,path);
			} else {
				current = getGroupNode(current,path);
			}
			i++;
		}
		return current;
	}

	@Override
	public String getInstance() {
		return instance;
	}
	

	@Override
	public void execute(ArticleContext context) throws APIException, NoJSONOutputException {
		verifyScopes();
		List<XMLElement> children = article.getChildren();
		try {
			List<JsonNode> elements = new ArrayList<JsonNode>();
			for (XMLElement e : children) {
				String name = e.getName();
				if (name.startsWith("_")) {
					// tags starting with '_' are declarative, not interesting
					// at runtime
					continue;
				}
				ArticleCommand ac = context.getCommand(name);
				if (ac == null) {
					throw new APIException("Unknown command: " + name, null, APIErrorCode.InternalError);
				}
				Map<String, String> parameters = new HashMap<String, String>();

				for (Iterator<String> iterator = e.enumerateAttributeNames(); iterator
						.hasNext();) {
					String attributeName = iterator.next();
					parameters.put(attributeName,
							e.getStringAttribute(attributeName));
				}
				JsonNode node = ac.execute(this, context, parameters, e);
				
				if (node!=null) {
					elements.add(node);
				}
			}
			if(elements.size()==0) {
				writeNode(rootNode);
			} else if(elements.size()==1 ) {
				// HUH?
				writeNode(elements.iterator().next());
			} else {
				ArrayNode an = getObjectMapper().createArrayNode();
				for (JsonNode jsonNode : elements) {
					an.add(jsonNode);
				}
				writeNode(an);
			}
//			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
//			mapper.writeValue(getOutputWriter(), rootNode);
//			commit();
		} catch (IOException e1) {
			logger.error("Error: ", e1);
		}
	}

	@Override
	public ObjectMapper getObjectMapper() {
		return mapper;
	}
	
	@Override
	public void writeNode(JsonNode node) throws IOException {
		setMimeType("application/json; charset=utf-8");
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		writer.writeValue(getOutputWriter(),node);
	}
	
	@Override
	public void pushNavajo(String name, Navajo res) {
		navajoStack.push(res);
		navajoStore.put(name, res);
	}

	@Override
	public Navajo getNavajo(String name) {
		return navajoStore.get(name);
	}

	@Override
	public Navajo getNavajo() {
		if (navajoStack.isEmpty()) {
			return null;
		}
		return navajoStack.peek();
	}

	@Override
	public String getArticleName() {
		return articleName;
	}

	@Override
	public ObjectNode getMetadataRootNode() {
		return rootNode;
	}
	
	@Override
	public Map<String,Object> getUserAttributes() {
		return userAttributes;
	}
	
	@Override
	public Token getToken() {
		return token;
	}

}
