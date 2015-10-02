package com.dexels.navajo.tester.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tester.NavajoTesterHelper;
import com.dexels.navajo.tester.model.NavajoFileSystemEntry;

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