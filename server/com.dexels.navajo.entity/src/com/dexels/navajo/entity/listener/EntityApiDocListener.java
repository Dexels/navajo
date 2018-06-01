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
        if (ops != null) {

        for (String op : ops.keySet()) {
                for (String version : entity.getMyVersionKeys()) {
                    result += writeEntityOperation(operationtemplate, entity, version, path, ops.get(op));
                }
            }
        }

        return result;
        
    }

    private String writeEntityOperation(String template, Entity e, String entityVersion, String path, Operation op)
            throws ServletException {
        String result = "";
        String method = op.getMethod();
        String versionNum = entityVersion;
        
//        try {
//            e.setMessage(e.getMyMessageVersionMap().get(entityVersion));
//            e.setMyVersion(versionNum);
//            e.refreshEntityManagerOperations();
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }

        Navajo n = NavajoFactory.getInstance().createNavajo();
        n.addMessage(e.getMessage(entityVersion).copy(n));
        String entityNameUrl = path + "/" + e.getMessageName();

        result = template.replace("{{OP}}", method);
        result = result.replace("{{URL}}", entityNameUrl);
        result = result.replace("{{VERSION}}", " (v." + versionNum + ")");
        result = result.replace("{{ENOPID}}", op.getMethod() + '-' + e.getMessageName() + "-v" + versionNum);

        String oprequesttemplate = getTemplate("operationrequest.template");
        String opresponsetemplate = getTemplate("operationresponse.template");

        String requestBody = null;
        // String modelBody = printModel(e.getMessage(), method, "request");
        result = result.replace("{{OPREQUESTMODEL}}", "");

        if (method.equals(Operation.GET) || method.equals(Operation.DELETE)) {
            requestBody = printRequestKeysDefinition(e, entityVersion);
        } else {
            String requestbodyTemplate = getTemplate("operationrequestbody.template");
            requestBody = requestbodyTemplate.replace("{{REQUEST_BODY}}", writeEntityJson(n, "request"));
        }
        
        if (e.getMyValidations().size() > 0) {
            String validationrowtemplate = getTemplate("validationmodelrow.template");
            String validationrowtable = getTemplate("validationmodeltable.template");
            String valRows = "";
            for (Map.Entry<String, String> entry : e.getMyValidations().entrySet()) {
                valRows += validationrowtemplate.replace("{{DESCRIPTION}}", entry.getValue()).replace("{{NAME}}", entry.getKey());
            }
            String res = validationrowtable.replace("{{VALIDATION_ROWS}}", valRows);
            
            requestBody = requestBody.substring(0,requestBody.lastIndexOf("{{VALL}}"))+"{{VALL_}}"+requestBody.substring(requestBody.lastIndexOf("{{VALL}}")+8);
            requestBody = requestBody.replace("{{VALL}}", "");
            requestBody = requestBody.replace("{{VALL_}}", "{{VALL}}");
            requestBody = requestBody.replace("{{VALL}}", res);
        } else {
            requestBody = requestBody.replace("{{VALL}}", "");
        }
        
        String request = oprequesttemplate.replace("{{ENTITY_REQUEST_BODY}}", requestBody);
        request = request.replace("{{OP}}", method);
        result = result.replace("{{OPREQUEST}}", request);

        if (op.getDescription() != null) {
            result = result.replace("{{DESCRIPTION}}", op.getDescription());

        } else {
            result = result.replace("{{DESCRIPTION}}", operationDescription(method) + e.getMessage(entityVersion).getName());
        }

        String modelBody = printModel(e.getMessage(Entity.DEFAULT_VERSION), method, "response");
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
        n.addMessage(e.getMessage(Entity.DEFAULT_VERSION));
        String entityNameUrl = entityName.replace(".", "/");

        result = template.replace("{{OP}}", method);
        result = result.replace("{{URL}}", entityNameUrl);
        result = result.replace("{{DESCRIPTION}}", e.getMessage(Entity.DEFAULT_VERSION).getName());
        
        String opresponsetemplate = getTemplate("operationresponse.template");
        
        result = result.replace("{{OPREQUEST}}", "");
        result = result.replace("{{OPREQUESTMODEL}}", "");
        
        String responseBody = opresponsetemplate.replace("{{RESPONSE_JSON}}", writeEntityJson(n, ""));
        responseBody = responseBody.replace("{{OP}}", method);
        responseBody = responseBody.replace("{{RESPONSE_XML}}", StringEscapeUtils.escapeHtml(writeEntityXml(n)));
        result = result.replace("{{OPRESPONSE}}", responseBody);
        
        String modelBody = printModel(e.getMessage(Entity.DEFAULT_VERSION), method, "request");
        modelBody += printModel(e.getMessage(Entity.DEFAULT_VERSION), method, "response");
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

    private String printRequestKeysDefinition(Entity e, String version) throws ServletException {
        
        String result = "";

        for (Key key : e.getKeys(version)) {

            String rows = "";
            String opmodeltemplate = getTemplate("operationrequestmodel.template");
            Set<Property> properties = key.getKeyProperties();
            
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
            
            if (!rows.equals("")) {
                String modelTable = opmodeltemplate.replace("{{MODEL_TABLE_ROWS}}", rows);
                result += modelTable;
            }

        }
        
        if (result.equals("")) {
            result = getTemplate("operationrequestnoinput.template");
        }
        
        result.replace("{{CLASS}}", "inputmodel");  

        return result;

    }
    
    private String printModel(Message m, String op, String method) {
        String rows = "";
        String opmodeltemplate = getTemplate("operationmodel.template");

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

        for (Property p : m.getAllProperties()) {
            if (p.getDescription().equals("")) {
                continue;
            }

            String propertyMethod = p.getMethod();
            if (propertyMethod.equals("")) {
                Message parentMessage = p.getParentMessage();
                while (parentMessage != null && !parentMessage.getMethod().equals("") && propertyMethod.equals("")) {
                    propertyMethod = parentMessage.getMethod();
                    parentMessage = parentMessage.getParentMessage();
                }
            }
			
            // Print if the property matches the method, OR if we are a request, or if we are request,response
            // if we are a key and this is a GET or DELETE operation.
            if (method.equals("response") && propertyMethod.equals(method) || propertyMethod.equals("")
                    || (method.equals("request") && (op.equals(Operation.GET) || op.equals(Operation.DELETE)) && Key.isKey(p.getKey()))) {
                
                // Create the path of the property:
                String path = "";
                Message parent = p.getParentMessage();
                while (parent != null) {
                    if (parent.getParentMessage() != null && !parent.getParentMessage().getName().equals("")) {
                        path = parent.getName() + "/" + path;
                    }
                    parent = parent.getParentMessage();
                }
                // path = path.substring(1, path.length() - 1);

                String modelRow = getTemplate("operationmodelrow.template");
                modelRow = modelRow.replace("{{NAME}}", "/" + path + p.getName());
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
