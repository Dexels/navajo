package com.dexels.navajo.tipi.internal;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteValidationPropertiesHandler {
    private final static Logger logger = LoggerFactory.getLogger(RemoteValidationPropertiesHandler.class);

    
    private final String url;
    private final String union;
    private final String subunion;
    private final String locale;
    
    public RemoteValidationPropertiesHandler(String url, String union, String locale) {
        this.url = url;
        this.union = union.toLowerCase();
        if (union.toLowerCase().equals("knvb")) {
            this.subunion = "av";
        } else {
            this.subunion = null;
        }
        if (locale == null) {
            // fallback to default
            locale = "nl";
        }
        this.locale = locale.toLowerCase();
    }


    public InputStream getContents() {
        logger.info("Going to retrieve validation.properties for {} - {} - {}", locale, union, subunion);
        String fullurl = url + "?union=" + union + "&locale=" + locale + "&resource=validation";
        if (subunion != null) {
            fullurl += "&subunion=" + subunion;
        }
        try {
            URL u = new URL(fullurl);
            InputStream is = null;

            URLConnection uc = u.openConnection();

            uc.connect();

            is = uc.getInputStream();
            return is;
        } catch (Throwable t) {
            logger.error("Error on retrieving validation.properties!", t);
        }

        return null;

    }
    
}
