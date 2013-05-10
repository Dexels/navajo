package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public abstract class BaseRuntimeImpl implements ArticleRuntime {

	private final static Logger logger = LoggerFactory
			.getLogger(BaseRuntimeImpl.class);

	private final Stack<Navajo> navajoStack = new Stack<Navajo>();
	private final Map<String, Navajo> navajoStore = new HashMap<String, Navajo>();
	protected final XMLElement article;
	private final String articleName;

	private final ObjectMapper mapper = new ObjectMapper();
	private final ObjectNode rootNode;

	protected BaseRuntimeImpl(String articleName, XMLElement article) {
		rootNode = mapper.createObjectNode();
		this.article = article;
		this.articleName = articleName;
	}

	protected BaseRuntimeImpl(String articleName, File articleFile)
			throws IOException {
		article = new CaseSensitiveXMLElement();
		rootNode = mapper.createObjectNode();
		this.articleName = articleName;
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

	public ObjectNode getGroupNode(ObjectNode parent, String name) throws ArticleException {
		JsonNode existing = parent.get(name);
		if(existing!=null) {
			if(existing instanceof ObjectNode) {
				return (ObjectNode) existing;
			} else {
				throw new ArticleException("Error getting group node: "+name+" there is an existing node, but it is not an ObjectNode");
			}
		}
		ObjectNode result = mapper.createObjectNode();
		rootNode.put(name, result);
		return result;
	}
	
	@Override
	public ObjectNode getGroupNode( String name) throws ArticleException {
		System.err.println("||||||||||||||||| GETTTING>>>> "+name);
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

	public void execute(ArticleContext context) throws ArticleException, DirectOutputThrowable {
		List<XMLElement> children = article.getChildren();
		int i = 0;
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
					throw new ArticleException("Unknown command: " + name);
				}
				Map<String, String> parameters = new HashMap<String, String>();

				for (Iterator<String> iterator = e.enumerateAttributeNames(); iterator
						.hasNext();) {
					String attributeName = iterator.next();
					parameters.put(attributeName,
							e.getStringAttribute(attributeName));
				}
				System.err.println("Calling command # " + (i++));
				JsonNode node =ac.execute(this, context, parameters, e);
				
				if (node!=null) {
					elements.add(node);
				}
			}
			// TODO bit shaky now
			if(elements.size()==0) {
				writeNode(rootNode);
			} else if(elements.size()==1) {
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
		setMimeType("text/json");
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

}
