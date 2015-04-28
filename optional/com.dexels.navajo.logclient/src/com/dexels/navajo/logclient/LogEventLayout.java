package com.dexels.navajo.logclient;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggingEvent;
import org.codehaus.jackson.map.ObjectMapper;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;

public class LogEventLayout {

    ObjectMapper mapper;
    private SessionTokenProvider sessionTokenProvider;

    public LogEventLayout() {
        mapper = new ObjectMapper();
        sessionTokenProvider = SessionTokenFactory.getSessionTokenProvider();
    }

    public String format(LoggingEvent event) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, logToMap(event));
        } catch (IOException e) {
            // Something went wrong in converting the map to JSON. Very
            // interesting!

        }
        return writer.toString();
    }

    @SuppressWarnings("deprecation")
    private Map<String, Object> logToMap(LoggingEvent event) {

        Map<String, Object> result = new HashMap<>();
        result.put("categoryName", event.categoryName);
        result.put("fqnOfCategoryClass", event.fqnOfCategoryClass);
        result.put("level", event.getLevel().toString());
        result.put("message", event.getMessage());
        result.put("timeStamp", new Date(event.timeStamp));
        result.put("session", sessionTokenProvider.getSessionToken());

        if (event.getLocationInformation() != null) {
            Map<String, Object> locationMap = new HashMap<>();
            locationMap.put("className", event.getLocationInformation().getClassName());
            locationMap.put("fileName", event.getLocationInformation().getFileName());
            locationMap.put("lineNumber", event.getLocationInformation().getLineNumber());
            locationMap.put("methodName", event.getLocationInformation().getMethodName());
            result.put("location", locationMap);
        }
        if (event.getThrowableInformation() != null) {
            result.put("throwable", event.getThrowableInformation().getThrowable());
        }
        result.put("mdc", MDC.getContext());

        result.put("threadName", event.getThreadName());
        return result;
    }
}
