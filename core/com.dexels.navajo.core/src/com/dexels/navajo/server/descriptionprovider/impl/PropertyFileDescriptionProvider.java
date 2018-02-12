package com.dexels.navajo.server.descriptionprovider.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;
import com.dexels.resourcebundle.ResourceBundleStore;

public class PropertyFileDescriptionProvider extends BaseDescriptionProvider implements DescriptionProviderInterface {
    private static final String FQDN_SEPARATOR = "|";

    private static final Logger logger = LoggerFactory.getLogger(PropertyFileDescriptionProvider.class);

    private ResourceBundleStore resourceBundle;
    private boolean enabled = false;


    public void activate(Map<String, Object> settings) throws IOException {
        if (settings.containsKey("enabled")) {
            String en = (String) settings.get("enabled");
            if ("true".equalsIgnoreCase(en)) {
                enabled = true;
            }
        }
        logger.info("PropertyFileDescriptionProvider is enabled? {}", enabled);

    }

    public void deactivate() {
        logger.info("Deactivating PropertyFileDescriptionProvider");
    }

    
    @Override
    public void updatePropertyDescriptions(Navajo in, Navajo out, Access access) {
        if (!enabled) return;
        
        try {
            String locale = in.getHeader().getHeaderAttribute("locale");
            if (locale==null) {
 return;

                // TODO 
//                in.getHeader().setHeaderAttribute("locale", "NL");
            }
            ResourceBundle properties;
            try {
            		properties = this.getResourceBundle(in, access);
            } catch (IOException e) {
                logger.error("Exception getting resources", e);
                return;
            }
            

            for (Message message : out.getAllMessages()) {
                updateMessage(properties, message, access);
            }
        } catch (Throwable t) {
            logger.error("Exception in handling property descriptions", t);
        }
        

    }

    private void updateMessage(ResourceBundle properties, Message m, Access access) {
        for (Message submsg : m.getAllMessages()) {
            updateMessage(properties, submsg, access);
        }
        for (Property prop : m.getAllProperties()) {
            updateProperty(properties, prop, access);
        }
    }

    public void updateProperty(ResourceBundle properties, Property property, Access access) {
        if (properties.containsKey(getFqdnPropertyLookupKey(property, access.getRpcName()))) {
            String entry = properties.getString(getFqdnPropertyLookupKey(property, access.getRpcName()));
            if (entry != null) {
                property.setDescription(entry);
                return;
            }
        }
        
        if (properties.containsKey(getPropertyLookupKey(property))) {
            String entry = properties.getString(getPropertyLookupKey(property));
            if (entry != null) {
                property.setDescription(entry);
                return;
            }
        }
    }


    @Override
    public void updateProperty(Navajo in, Property element, String locale, String tenant) {
        throw new UnsupportedOperationException();
        
    }

    private ResourceBundle getResourceBundle(Navajo in, Access access) throws IOException {
        String locale = in.getHeader().getHeaderAttribute("locale");
        String sublocale = in.getHeader().getHeaderAttribute("sublocale");

        String props = resourceBundle.getResource(null, "description", access.getTenant(), sublocale, locale);
        PropertyResourceBundle p = null;
        try (InputStream stream = new ByteArrayInputStream(props.getBytes(StandardCharsets.UTF_8))) {
            p = new PropertyResourceBundle(stream);
        }

        return p;
    }

    // A property can be reached by either the property name,
    // or the webservice + : + full property path
    private String getPropertyLookupKey(Property property) {
        return property.getName();
    }

    private String getFqdnPropertyLookupKey(Property property, String service) {
        return service + FQDN_SEPARATOR + property.getFullPropertyName();
    }

    public void setResourceBundle(ResourceBundleStore rb) {
        this.resourceBundle = rb;
    }

    public void clearResourceBundle(ResourceBundleStore rb) {
        this.resourceBundle = null;
    }


}
