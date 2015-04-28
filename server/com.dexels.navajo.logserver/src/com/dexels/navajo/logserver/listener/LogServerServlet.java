package com.dexels.navajo.logserver.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jzlib.InflaterInputStream;

public class LogServerServlet extends HttpServlet {
    private final static Logger logger = LoggerFactory.getLogger(LogServerServlet.class);
    private static final long serialVersionUID = 8297735604970605787L;

    public static final String COMPRESS_GZIP = "gzip";
    public static final String COMPRESS_JZLIB = "jzlib";

    private EventAdmin eventAdmin = null;

    public void setEventAdmin(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    public void clearEventAdmin(EventAdmin eventAdmin) {
        this.eventAdmin = null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String incomingEncoding = request.getHeader("Content-Encoding");

        BufferedReader r = null;
        Writer writer = new StringWriter();

        if (incomingEncoding != null) {

            if (incomingEncoding.equals(COMPRESS_JZLIB)) {
                r = new BufferedReader(new java.io.InputStreamReader(new InflaterInputStream(request.getInputStream()), "UTF-8"));
            } else if (incomingEncoding.equals(COMPRESS_GZIP)) {
                r = new BufferedReader(new java.io.InputStreamReader(new java.util.zip.GZIPInputStream(request.getInputStream()),
                        "UTF-8"));
            } else {
                throw new IOException("Unsupported encoding!");
            }
        } else {
            r = new BufferedReader(request.getReader());
        }

        String line = null;
        while ((line = r.readLine()) != null) {
            writer.append(line);
        }

        sendLogEvent("logserver/logentry", writer.toString());
//        if (request.getHeader("Content-Type").startsWith("text/json")) {
//            try {
//                DBObject dbObject = (DBObject) JSON.parse();
//                // LogEvent res = om.readValue(writer.toString().getBytes(),
//                // LogEvent.class);
//                // System.out.println("REMOTE LOG " + res.getTimeStamp() + " " +
//                // res.getMessage());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
    }

    private void sendLogEvent(String topic, String logEvent) {
        if (eventAdmin == null) {
            logger.warn("No event administrator, not sending any events");
            return;
        }
        Map<String, Object> properties = new HashMap<>();
        properties.put("logEntry", logEvent);
        properties.put("servlet", this);
        
        Event event = new Event(topic, properties);

        eventAdmin.postEvent(event);
    }

}
