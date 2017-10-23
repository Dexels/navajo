package com.dexels.navajo.server.descriptionprovider.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;

public class PropertyFileDescriptionProvider extends BaseDescriptionProvider implements DescriptionProviderInterface, EventHandler {
    private static final String RESOURCES_FOLDER = "resources" + File.separator + "texts";

    private static final Logger logger = LoggerFactory.getLogger(PropertyFileDescriptionProvider.class);
    private NavajoIOConfig navajoIOConfig;

    private final Map<String, Properties> localeProperties = new HashMap<>();

    @Override
    public void updateProperty(Navajo in, Property element, String locale, String tenant) {
        if (locale == null) {
            logger.warn("No locale set!");
            return;
        }
        Properties p = localeProperties.get(locale);
        if (p == null) {
            p = loadLocale(locale);
        }
        String service = in.getHeader().getRPCName();

        update(service, element, p);
    }

    private void update(String service, Property property, Properties p) {
        String entry = p.getProperty(getFqdnPropertyLookupKey(property, service));
        if (entry != null) {
            property.setDescription(entry);
            return;
        }
        entry = p.getProperty(getMessagePropertyLookupKey(property));
        if (entry != null) {
            property.setDescription(entry);
            return;
        }
        
        entry = p.getProperty(getPropertyLookupKey(property));
        if (entry != null) {
            property.setDescription(entry);
            return;
        }
    }
    
    
    // A property can be reached by either the property name,
    // the message path + property name, or the 
    // webservice + message path + property name 
    private String getPropertyLookupKey(Property property) {
        return property.getName();
    }
    
    private String getMessagePropertyLookupKey(Property property) {
        return property.getFullPropertyName();
    }
    
    private String getFqdnPropertyLookupKey(Property property, String service) {
        return service + ":" + property.getFullPropertyName();
    }

    public void activate() throws IOException {
        logger.info("Activating PropertyFileDescriptionProvider");

    }

    private Properties loadLocale(String locale) {
        File resources = new File(navajoIOConfig.getRootPath(), "resources");
        File texts = new File(resources, "texts");
        File description = new File(texts, "description_" + locale + ".properties");
        Properties old = localeProperties.get(locale);
        if (old != null) {
            long fileStamp = description.lastModified();
            Long parsedAt = (Long) old.get("parsedAt");
            if (parsedAt != null) {
                if (fileStamp < parsedAt) {
                    // ok to use old one:
                    return old;
                } else {
                    logger.warn("Description file changed. Reloading.");
                }
            }
        }
        Properties properties = new Properties();

        if (!description.exists()) {
            logger.debug("No locale file found for locale: {} path: {}", locale, description.getAbsolutePath());
            return properties;
        }
        properties.put("parsedAt", System.currentTimeMillis());
       
        try (InputStream fileInputStream = new FileInputStream(description)) {
            Reader r = new InputStreamReader(fileInputStream, "UTF-8");
            properties.load(r);
        } catch (IOException ioe) {
            logger.error("Error reading locale file for: " + description.getAbsolutePath());
        }
        localeProperties.put(locale, properties);
        return properties;
    }

    public void deactivate() {
        logger.info("Deactivating PropertyFileDescriptionProvider");
    }

    public void setNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
        this.navajoIOConfig = navajoIOConfig;
    }

    public void clearNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
        this.navajoIOConfig = null;
    }
    
    @Override
    public void handleEvent(Event e) {
        List<String> deletedContent = RepositoryEventParser.filterDeleted(e, RESOURCES_FOLDER);
        List<String> changedContent = RepositoryEventParser.filterChanged(e, RESOURCES_FOLDER);
        if (deletedContent.size() > 0 || changedContent.size() > 0) {
            logger.debug("Detected a change in the resources folder - clearing cache");
            localeProperties.clear();
        }
    }

}
