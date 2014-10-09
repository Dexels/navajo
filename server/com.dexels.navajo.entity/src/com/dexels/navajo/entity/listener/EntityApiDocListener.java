package com.dexels.navajo.entity.listener;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.Key;


public class EntityApiDocListener extends HttpServlet  implements ResourceMapping {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2642151786192206338L;

	private final static Logger logger = LoggerFactory.getLogger(EntityApiDocListener.class);

	private EntityManager myManager;

	private final DefaultResourceMapping resourceMapping = new DefaultResourceMapping();

	
	
	public void activate() {
		resourceMapping.setAlias("/entityApi");
		resourceMapping.setPath("entityApi");

	}

	@Override
	public String getAlias() {
		return resourceMapping.getAlias();
	}

	@Override
	public String getHttpContextId() {
		return resourceMapping.getHttpContextId();
	}

	@Override
	public String getPath() {
		return resourceMapping.getPath();
	}
	
	
	
	public void setEntityManager(EntityManager em) {
		myManager = em;
	}

	public void clearEntityManager(EntityManager em) {
		myManager = null;
	}


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = request.getPathInfo();

		String out = "";
		out += "<!DOCTYPE html>";
		out += "<html>";
		out += "<head>";
		out += " <title>Navajo Entity API documentation</title>";
		out += " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> ";
		out += " <link rel=\"stylesheet\" href=\"entityApi/css/style.css\"> ";
		out += "<script type=\"text/javascript\" src=\"entityApi/jquery-1.9.1.min.js\" ></script>";

		out += "<script type=\"text/javascript\" src=\"https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js\" ></script>";
		
		out += "<script> ";
		out += "$(document).ready(function() {";
		out +=		"$(\"a\").click(function(event) { ";
		out +=  "	    $(this).next().filter(\".entityDescription\").slideToggle(); ";
		out += "    });" ;
		out += "});" ;
		out += "</script> ";
		out +=  "</head>";

		out += "<body class=\"bodycenter\">";	
		out += "<h1>Entity API Documentation</h1>";
		
		Set<String> entityNames = myManager.getRegisteredEntities();
		for (String entityName : entityNames) {
			Entity e = myManager.getEntity(entityName);
			
			Map<String, Operation> ops = myManager.getOperations(entityName);
			

			Navajo n = NavajoFactory.getInstance().createNavajo();
			n.addMessage(e.getMessage());
			
			for (String op : ops.keySet()) {
				out += "<ul class=\"operations\">";
				out += "<li class=\"operation "+op+"\" >";
				out += "<a href=\"#\" > ";
				out += "<div class=\"operationHeader "+op+"\">";
				out += "<div class=\"method http"+op+"\">" + op + "</div>" ;
				out += "<div class=\"url\" > /" +entityName +"</div>";
				out += "<div class=\"descrption "+ op + "\" > " + operationDescription(op) + " a " + entityName + "</div>";
				out += "</div>"; //operationHeader
				out += "</a>";
				out += "<div  class=\"entityDescription\" style=\"display: none \"> ";
				if ( (op.equals(Operation.GET) || op.equals(Operation.DELETE)) && e.getKeys().size() > 0 ) {
					out += "<h3> Keys </h3>";
					for (Key key : e.getKeys()) {
						Set<Property> properties = key.getKeyProperties();
						for (Property prop : properties) {
							out += "<code> " + prop.getName() + "</code>;  ";
						}
					}
				}
				
				
				out += "<h3> Model </h3>";
				out +=  "<pre class=\"prettyprint\">";
				out +=  writeEntityJson(n);
				out += "</pre>";
				
				out += "</div>";
				out += "</li></ul>"; // description div
			}
			;
			
		}
		
		out += "</body>";
		out += "</html>";
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().write(out.getBytes());
		
	}

	
	
	private String writeEntityJson(Navajo n) throws ServletException {
		StringWriter writer = new StringWriter();
		JSONTML json = JSONTMLFactory.getInstance();

		try {
			json.formatDefinition(n, writer, true);
		} catch (Exception ex) {
			logger.error("Error in writing entity output in JSON!");
			throw new ServletException("Error producing output");
		}
		return StringEscapeUtils.escapeHtml(writer.toString());
	}
	
	private String writeEntityXml(Navajo n) throws ServletException {
		StringWriter writer = new StringWriter();
		NavajoLaszloConverter.writeBirtXml(n, writer);
		String xml = writer.toString();
		return xml.replace(">",">\n");
	}
	
	private String operationDescription(String op) {
		if (op.equals(Operation.GET)) {
			return "Get ";
		}
		if (op.equals(Operation.POST)) {
			return "Create ";
		}
		if (op.equals(Operation.PUT)) {
			return "Update";
		}
		if (op.equals(Operation.DELETE)) {
			return "Delete ";
		}
		return "";
	}


}
