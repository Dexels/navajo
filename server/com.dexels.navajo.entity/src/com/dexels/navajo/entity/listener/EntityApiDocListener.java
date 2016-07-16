package com.dexels.navajo.entity.listener;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;
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
import com.dexels.navajo.entity.Key;

public class EntityApiDocListener extends HttpServlet implements ResourceMapping {
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
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        String basePath = "";

        if (path != null && !path.equals("/")) {
            for (String subPath : path.split("/")) {
                basePath += subPath + ".";
            }
            basePath = basePath.substring(1);
        }
        String sourcetemplate = getTemplate("source.template");
        String operationtemplate = getTemplate("operation.template");

        String result = sourcetemplate.replace("{{ENTITY_PATH}}", path.substring(1));

        List<String> entityNames = myManager.getRegisteredEntities(basePath);

        String operations = "";
        for (String entityName : entityNames) {
            Map<String, Operation> ops = myManager.getOperations(entityName);
            for (String op : ops.keySet()) {
                operations += writeEntityOperation(operationtemplate, entityName, op);
            }
        }

        result = result.replace("{{OPERATIONS}}", operations);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(result.getBytes());

    }

    private String writeEntityOperation(String template, String entityName, String op) throws ServletException {
        String result = "";

        Entity e = myManager.getEntity(entityName);
        Navajo n = NavajoFactory.getInstance().createNavajo();
        n.addMessage(e.getMessage());
        String entityNameUrl = entityName.replace(".", "/");

        result = template.replace("{{OP}}", op);
        result = result.replace("{{URL}}", entityNameUrl);
        result = result.replace("{{DESCRIPTION}}", operationDescription(op) + entityNameUrl);

        String oprequesttemplate = getTemplate("operationrequest.template");
        String opresponsetemplate = getTemplate("operationresponse.template");

        String requestBody = null;
        if ((op.equals(Operation.GET) || op.equals(Operation.DELETE)) && e.getKeys().size() > 0) {
            requestBody = printRequestKeysDefinition(e);
        } else {
            String requestbodyTemplate = getTemplate("operationrequestbody.template");
            requestBody = requestbodyTemplate.replace("{{REQUEST_BODY}}", writeEntityJson(n, "request"));
        }
        String request = oprequesttemplate.replace("{{ENTITY_REQUEST_BODY}}", requestBody);
        result = result.replace("{{OPREQUEST}}", request);
        
        String commentBody =  printPropertiesDescription(e.getMessage(), op, "request");
        result = result.replace("{{OPREQUESTCOMMENT}}", commentBody);
        
        String responseBody = opresponsetemplate.replace("{{RESPONSE_JSON}}", writeEntityJson(n, "response"));
        responseBody = responseBody.replace("{{RESPONSE_XML}}", StringEscapeUtils.escapeHtml(writeEntityXml(n)));
        result = result.replace("{{OPRESPONSE}}", responseBody);
        
        commentBody =  printPropertiesDescription(e.getMessage(), op, "response");
        result = result.replace("{{OPRESPONSECOMMENT}}", commentBody);
        return result;
    }
    
    private String printRequestKeysDefinition(Entity e) throws ServletException {
        String result = "";
        Set<Property> unboundRequestProperties = new HashSet<>();
        for (Property p : e.getMessage().getAllProperties()) {
            if (!Key.isKey(p.getKey()) && p.getMethod().equals("request")) {
                unboundRequestProperties.add(p);
            }
        }
        for (Key key : e.getKeys()) {
            String requestbody = getTemplate("operationrequestbody.template");
            // Get all properties for this key, put them in a temp Navajo and use the JSONTML to print it
            Set<Property> properties = key.getKeyProperties();

            Navajo nkey = NavajoFactory.getInstance().createNavajo();
            Message mkey = NavajoFactory.getInstance().createMessage(nkey, "keys");
            nkey.addMessage(mkey);

            for (Property prop : properties) {
                if (!Key.isAutoKey(prop.getKey())) {
                    mkey.addProperty(prop.copy(nkey));
                }
            }
            for (Property p : unboundRequestProperties) {
                mkey.addProperty(p.copy(nkey));
            }

            // Printing result.
            requestbody = requestbody.replace("{{REQUEST_BODY}}", writeEntityJson(nkey, "request"));
            result += requestbody;
        }
        return result;
    }
    
    private String writeEntityJson(Navajo n, String method) throws ServletException {
        StringWriter writer = new StringWriter();
        JSONTML json = JSONTMLFactory.getInstance();
        Navajo masked = n.copy().mask(n, method);
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

    
    private String printPropertiesDescription(Message m, String op, String method) {
        String rows = "";
        String opcommenttemplate = getTemplate("operationcomment.template");


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
            String commentTable = opcommenttemplate.replace("{{COMMENT_TABLE_ROWS}}", rows);
            return commentTable;
        }
        return "";
    }
    
    private String printPropertiesForMessage(Message m, String op, String method) {
        // Check entity message
        String rows = "";
        for (Property p : m.getAllProperties()) {
            if (p.getDescription() == null ||  p.getDescription().equals("")) {
                continue;
            }
            // Property has a description. Print if the property matches the method, OR if we are a request,
            // if we are a key and this is a GET or DELETE operation.
            String propertyMethod = p.getMethod();
            if (method == null) {
                propertyMethod =  p.getParentMessage().getMethod();
            }
            if (propertyMethod.equals(method)
                    || (method.equals("request") && (op.equals(Operation.GET) || op.equals(Operation.DELETE)) && Key.isKey(p.getKey()))) {
                String commentRow = getTemplate("operationcommentrow.template");
                commentRow = commentRow.replace("{{COMMENT_KEY}}", p.getName());
                commentRow = commentRow.replace("{{COMMENT_VALUE}}", p.getDescription());
                rows += commentRow;
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
            URL url = getClass().getResource("/entityApi" + File.separator + name);
            String content = IOUtils.toString(url.openStream(), "utf-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }
}
