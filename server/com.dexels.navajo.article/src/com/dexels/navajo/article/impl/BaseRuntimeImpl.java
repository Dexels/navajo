package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public abstract class BaseRuntimeImpl  implements ArticleRuntime {
	private final Stack<Navajo> navajoStack = new Stack<Navajo>();
	private final Map<String,Navajo> navajoStore = new HashMap<String,Navajo>();
	protected final XMLElement article;
	private final String articleName;

	protected BaseRuntimeImpl(String articleName, XMLElement article) {
		this.article = article;
		this.articleName = articleName;
	}
	
	protected BaseRuntimeImpl(String articleName, File articleFile) throws IOException {
		article = new CaseSensitiveXMLElement();
		this.articleName = articleName;
		Reader r = null;
		try {
			r = new FileReader(articleFile);
			article.parseFromReader(r);
		} catch (IOException e) {
			throw e;
		}  finally {
			if(r!=null) {
				r.close();
			}
		}
	}

	public void execute(ArticleContext context) throws ArticleException {
		List<XMLElement> children = article.getChildren();
		int i = 0;
		try {
			getOutputWriter().write("{");
			boolean first = true;
			int count = children.size();
			
			for (XMLElement e : children) {
				String name = e.getName();
				ArticleCommand ac = context.getCommand(name);
				if(ac==null) {
					throw new ArticleException("Unknown command: "+name);
				}
				Map<String,String> parameters = new HashMap<String, String>();
				 
				for (Iterator<String> iterator = e.enumerateAttributeNames(); iterator.hasNext();) {
					String attributeName = iterator.next();
					parameters.put(attributeName, e.getStringAttribute(attributeName));
				}
				System.err.println("Calling command # "+(i++));
				if(ac.execute(this, context, parameters)) {
					first = false;
					if(!first && count!=i) {
						getOutputWriter().write(",");
					}
				}

			}
			getOutputWriter().write("}");
	} catch (IOException e1) {
			e1.printStackTrace();
		}
	//		setMimeType("text/plain");
		
	}
	@Override
	public void pushNavajo(String name,Navajo res) {
		navajoStack.push(res);
		navajoStore.put(name, res);
	}
	@Override
	public Navajo getNavajo(String name) {
		return navajoStore.get(name);
	}
	
	@Override
	public Navajo getNavajo() {
		if(navajoStack.isEmpty()) {
			return null;
		}
		return navajoStack.peek();
	}

	@Override
	public String getArticleName() {
		return articleName;
	}
}
