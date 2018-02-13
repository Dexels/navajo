package com.dexels.navajo.entity.listener;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.EntityMapper;
import com.dexels.navajo.entity.Key;

public class EntityApiDocListener extends HttpServlet  {
    private static final long serialVersionUID = -2642151786192206338L;

    private final static Logger logger = LoggerFactory.getLogger(EntityApiDocListener.class);

    private EntityManager myManager;

    private EntityMapper myMapper;

    public void activate() {
    }



    public void setEntityManager(EntityManager em) {
        myManager = em;
    }

    public void clearEntityManager(EntityManager em) {
        myManager = null;
    }
    
    public void setEntityMapper(EntityMapper mapp) {
        myMapper = mapp;
    }

    public void clearEntityMapper(EntityMapper mapp) {
        myMapper = null;
    }
    

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        boolean debug = Boolean.valueOf(request.getParameter("developer"));

        if (path == null) {
            path = "";
        } else {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (path.endsWith("/")) {
                path = path.substring(0, path.length()-1);
            }
        }
        path = StringEscapeUtils.escapeHtml(path); /* Prevent javascript injection attacks */
        String sourcetemplate = getTemplate("source.template");
        String operationtemplate = getTemplate("operation.template");

        String result = sourcetemplate.replace("{{ENTITY_PATH}}", path);
        Set<String> entityNames;
        if (debug) {
            String entityPath = path.replace("/", ".");
            entityNames = myManager.getRegisteredEntities(entityPath);
        } else {
            entityNames = myMapper.getEntities(path);
        }
        

        String operations = "";
        for (String entityName : entityNames) {
            if (myManager.getEntity(entityName) == null) {
                logger.warn("Missing entity: {} at {}", entityName, path);
                continue;
            }

            operations += writeEntityOperations(operationtemplate, entityName, path);
           
            if (debug) {
                operations += writeHeadEntityOperation(operationtemplate, entityName);
            }
        }

        result = result.replace("{{OPERATIONS}}", operations);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(result.getBytes());

    }
    
    
    private String writeEntityOperations(String operationtemplate, String entityName, String path) throws ServletException {
        String result = "";
        Map<String, Operation> ops = myManager.getOperations(entityName);
        Entity entity = myManager.getEntity(entityName);
        for (String op : ops.keySet()) {
            result += writeEntityOperation(operationtemplate, entity, path, ops.get(op));
        }
        return result;
        
    }

    private String writeEntityOperation(String template, Entity e, String path, Operation op) throws ServletException {
        String result = "";
        String method = op.getMethod();
        
        Navajo n = NavajoFactory.getInstance().createNavajo();
        n.addMessage(e.getMessage().copy(n));
        String entityNameUrl = path + "/" + e.getMessageName();

        result = template.replace("{{OP}}", method);
        result = result.replace("{{URL}}", entityNameUrl);

        String oprequesttemplate = getTemplate("operationrequest.template");
        String opresponsetemplate = getTemplate("operationresponse.template");

        String requestBody = null;
//        String modelBody =  printModel(e.getMessage(), method, "request");
        result = result.replace("{{OPREQUESTMODEL}}", "");
        if (method.equals(Operation.GET) || method.equals(Operation.DELETE)) {
            requestBody =  printRequestKeysDefinition(e);
        } else {
            String requestbodyTemplate = getTemplate("operationrequestbody.template");
            requestBody = requestbodyTemplate.replace("{{REQUEST_BODY}}", writeEntityJson(n, "request"));
        }
        String request = oprequesttemplate.replace("{{ENTITY_REQUEST_BODY}}", requestBody);
        request = request.replace("{{OP}}", method);
        result = result.replace("{{OPREQUEST}}", request);
        if (op.getDescription() != null) {
            result = result.replace("{{DESCRIPTION}}", op.getDescription());

        } else {
            result = result.replace("{{DESCRIPTION}}", operationDescription(method) + e.getMessage().getName());
        }

        String modelBody =  printModel(e.getMessage(), method, "response");
        result = result.replace("{{OPRESPONSEMODEL}}", modelBody);
        
        String responseBody = opresponsetemplate.replace("{{RESPONSE_JSON}}", writeEntityJson(n, "response"));
        responseBody = responseBody.replace("{{OP}}", method);
        responseBody = responseBody.replace("{{RESPONSE_XML}}", StringEscapeUtils.escapeHtml(writeEntityXml(n)));
        result = result.replace("{{OPRESPONSE}}", responseBody);
        return result;
    }
    
    private String writeHeadEntityOperation(String template, String entityName) throws ServletException {
        String result = "";
        String method = "HEAD";
        
        Entity e = myManager.getEntity(entityName);
        Navajo n = NavajoFactory.getInstance().createNavajo();
        n.addMessage(e.getMessage());
        String entityNameUrl = entityName.replace(".", "/");

        result = template.replace("{{OP}}", method);
        result = result.replace("{{URL}}", entityNameUrl);
        result = result.replace("{{DESCRIPTION}}", e.getMessage().getName());
        
        String opresponsetemplate = getTemplate("operationresponse.template");
        
        result = result.replace("{{OPREQUEST}}", "");
        result = result.replace("{{OPREQUESTMODEL}}", "");
        
        String responseBody = opresponsetemplate.replace("{{RESPONSE_JSON}}", writeEntityJson(n, ""));
        responseBody = responseBody.replace("{{OP}}", method);
        responseBody = responseBody.replace("{{RESPONSE_XML}}", StringEscapeUtils.escapeHtml(writeEntityXml(n)));
        result = result.replace("{{OPRESPONSE}}", responseBody);
        
        String modelBody = printModel(e.getMessage(), method, "request");
        modelBody += printModel(e.getMessage(), method, "response");
        result = result.replace("{{OPRESPONSEMODEL}}", modelBody);
        return result;
    }
        
    private String writeEntityJson(Navajo n, String method) throws ServletException {
        StringWriter writer = new StringWriter();
        JSONTML json = JSONTMLFactory.getInstance();
        Navajo masked = n.copy().mask(n, method);
        if (method.equals("request")) {
            // Remove all auto keys since they are not 
            for (Message m : masked.getAllMessages()) {
                for (Property p : m.getAllProperties()) {
                    if (p.getKey() != null && p.getKey().contains("auto")) {
                        m.removeProperty(p);
                    }
                }
            }
        }
        try {
            json.formatDefinition(masked, writer, true);
        } catch (Exception ex) {
            logger.error("Error in writing entity output in JSON!", ex);
            throw new ServletException("Error producing output");
        }
        return StringEscapeUtils.escapeHtml(writer.toString());
    }
    
    private String writeEntityXml(Navajo n) throws ServletException {
        StringWriter writer = new StringWriter();
        n.write(writer);
        return writer.toString();
    }

    private String printRequestKeysDefinition(Entity e) throws ServletException {
        String result = "";
		String rows = "";
		String opmodeltemplate = getTemplate("operationrequestmodel.template");
		Set<Property> properties;
        for (Key key : e.getKeys()) {

			rows = "";
			properties = key.getKeyProperties();
           
            for (Property prop : properties) {
                String modelRow = getTemplate("operationrequestmodelrow.template");
                modelRow = modelRow.replace("{{NAME}}", prop.getName());
                modelRow = modelRow.replace("{{TYPE}}", prop.getType());
                modelRow = modelRow.replace("{{COMMENT}}", prop.getDescription());
                if (prop.getKey().contains("optional")) { 
                    modelRow = modelRow.replace("{{REQUIREDCLASS}}", "optional");
                } else {
                    modelRow = modelRow.replace("{{REQUIREDCLASS}}", "required");
                }
                rows += modelRow;
            }

			result += rows;
        }

		String modelTable = opmodeltemplate.replace("{{MODEL_TABLE_ROWS}}", result);
        if (result.equals("")) {
            result = getTemplate("operationrequestnoinput.template");
		} else {
			result = modelTable;
        }

        result.replace("{{CLASS}}", "inputmodel");

        return result;
    }
    
    private String printModel(Message m, String op, String method) {
        String rows = "";
        String opmodeltemplate = getTemplate("operationmodel.template");

		for (Message subMessage : m.getAllMessages()) {
			if (subMessage.getMethod().equals("")) {
				logger.debug(" Implementing method inheritance for :: " + subMessage.getName());
				m.getMessage(subMessage.getName()).setMethod(m.getMethod());
			}
		}

        String propertiesResult = printPropertiesForMessage(m, op, method);
        if (!propertiesResult.equals("")){
            rows += propertiesResult;
        }

        // And other submessages
        for (Message submessage : m.getAllMessages()) {
			propertiesResult = printPropertiesForMessage(submessage, op, method);
            if (!propertiesResult.equals("")){
                rows += propertiesResult;
            }
        }

        if (!rows.equals("")) {
            String modelTable = opmodeltemplate.replace("{{MODEL_TABLE_ROWS}}", rows);
            return modelTable;
        }
        return "";
    }
    
    private String printPropertiesForMessage(Message m, String op, String method) {
        // Check entity message
        String rows = "";
		for (Message subMessage : m.getAllMessages()) {
			if (subMessage.getMethod().equals("")) {
				logger.debug(" Implementing method inheritance for :: " + subMessage.getName());
				m.getMessage(subMessage.getName()).setMethod(m.getMethod());
			}
		}

        for (Property p : m.getAllProperties()) {
        	
            if (p.getDescription().equals("")) {
                continue;
            }
            // Print if the property matches the method, OR if we are a request,
            // if we are a key and this is a GET or DELETE operation.
            String propertyMethod = p.getMethod();
			try {
				if (propertyMethod.equals("")) {
					propertyMethod = p.getParentMessage().getMethod();
				}
			} catch (Exception e) {
				logger.debug("Adding inherited method for "+p.getName()+" failed"
						+ ". Parrent not found. Leaving method empty.");
			}

			// Create the path of the property:
			String path = "";
			try {
				Message parent = p.getParentMessage();
				while (parent != null) {
					if (parent != null) {
						path = parent.getName() + "/" + path;
					}
					parent = parent.getParentMessage();
				}
				// path = path.substring(1, path.length() - 1);
			} catch (Exception e) {
				logger.debug("Parrent not found for path building");
			}

            if (method.equals("response") && propertyMethod.equals(method)
                    || (method.equals("request") && (op.equals(Operation.GET) || op.equals(Operation.DELETE)) && Key.isKey(p.getKey()))) {
                String modelRow = getTemplate("operationmodelrow.template");
				modelRow = modelRow.replace("{{NAME}}", path + p.getName());
				modelRow = modelRow.replace("{{COMMENT}}", p.getDescription());
                rows += modelRow;
            }
        }

        return rows;
    }

    private String operationDescription(String op) {
        if (op.equals(Operation.GET)) {
            return "Get ";
        }
        if (op.equals(Operation.POST)) {
            return "Create ";
        }
        if (op.equals(Operation.PUT)) {
            return "Update ";
        }
        if (op.equals(Operation.DELETE)) {
            return "Delete ";
        }
        return "";
    }

    private String getTemplate(String name) {
        try {
            URL url = getClass().getResource("/entityApi" + File.separator + "template" + File.separator + name);
            String content = IOUtils.toString(url.openStream(), "utf-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }
}
