package com.dexels.navajo.tester.js.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tester.js.NavajoTesterHelper;
import com.dexels.navajo.tester.js.model.NavajoFileSystemEntry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NavajoTesterServlet extends HttpServlet {

    private static final long serialVersionUID = -1084836955280214020L;
    private final static Logger logger = LoggerFactory.getLogger(NavajoTesterServlet.class);
    
    private NavajoTesterHelper helper;
    private ObjectMapper mapper = new ObjectMapper();

    protected void service(final HttpServletRequest request, HttpServletResponse response) throws IOException { 
        String query = request.getParameter("query");
        String result = "";
        if (query.equals("getscripts")) {
            List<NavajoFileSystemEntry> files = helper.getAllScripts().getEntries();
            result = mapper.writeValueAsString(files);
            response.setContentType("text/json");
        } else if (query.equals("getfilecontent")) {
            result = helper.getFileContent(request.getParameter("file"));
            response.setContentType("text/plain");
        } else if (query.equals("getcompiledcontent")) {
            result = helper.getCompiledScriptContent(request.getParameter("file"));
            response.setContentType("text/plain");
        } else if (query.equals("gettenants")) {
                List<String> files = helper.getSupportedTenants();
                result = mapper.writeValueAsString(files);
                response.setContentType("text/json");
        } else if (query.equals("getapplications")) {
            response.setContentType("text/json");
            JsonNode node = helper.getApplicationListContent();
            result = mapper.writeValueAsString(node);
            
        } else {
            logger.warn("Unsupported request: {}", query);
        }
   
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.close();
    }
    
    public void setNavajoTesterHelper(NavajoTesterHelper helper) {
        this.helper = helper;
    }
    
    public void clearNavajoTesterHelper(NavajoTesterHelper helper) {
        this.helper = null;
    }

}