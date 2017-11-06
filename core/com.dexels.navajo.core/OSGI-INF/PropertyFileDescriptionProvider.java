package com.dexels.navajo.server.descriptionprovider.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
    private static final Logger logger = LoggerFactory.getLogger(PropertyFileDescriptionProvider.class);

    private ResourceBundleStore resourceBundle;

    private Map<String, ResourceBundle> cachedProperties = new HashMap<>();

    @Override
    public void updatePropertyDescriptions(Navajo in, Navajo out, Access access) {
        String locale = in.getHeader().getHeaderAttribute("locale");
        if (locale==null) {
            return;
        }
        for (Message message : out.getAllMessages()) {
            updateMessage(in, message, access);
        }

    }

    private void updateMessage(Navajo in, Message m, Access access) {
        for (Message submsg : m.getAllMessages()) {
            updateMessage(in, submsg, access);
        }
        for (Property prop : m.getAllProperties()) {
            updateProperty(in, prop, access);
        }
    }

    public void updateProperty(Navajo in, Property property, Access access) {
        ResourceBundle properties = cachedProperties.get(getCacheKey(in, access));
        if (properties == null) {
            try {
                properties = this.getResourceBundle(in, access);
            } catch (IOException e) {
                logger.error("Exception getting resources", e);
                return;
            }
        }
       
        
                
        String entry = properties.getString(getFqdnPropertyLookupKey(property, access.getRpcName()));
        if (entry != null) {
            property.setDescription(entry);
            return;
        }
        entry = properties.getString(getMessagePropertyLookupKey(property));
        if (entry != null) {
            property.setDescription(entry);
            return;
        }
        
        entry = properties.getString(getPropertyLookupKey(property));
        if (entry != null) {
            property.setDescription(entry);
            return;
        }


    }


    @Override
    public void updateProperty(Navajo in, Property element, String locale, String tenant) {
        throw new UnsupportedOperationException();
        
    }

    
    private String getCacheKey(Navajo in, Access access) {
        String locale = in.getHeader().getHeaderAttribute("locale");
        String sublocale = in.getHeader().getHeaderAttribute("sublocale");
        return access.getTenant() + locale + sublocale;
    }

    private ResourceBundle getResourceBundle(Navajo in, Access access) throws IOException {
        String locale = in.getHeader().getHeaderAttribute("locale");
        String sublocale = in.getHeader().getHeaderAttribute("sublocale");

        String props = resourceBundle.getResource(null, "properties", access.getTenant(), sublocale, locale);
        InputStream stream = new ByteArrayInputStream(props.getBytes(StandardCharsets.UTF_8));
        PropertyResourceBundle p = new PropertyResourceBundle(stream);
        cachedProperties.put(getCacheKey(in, access), p);
        return null;
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

    public void deactivate() {
        logger.info("Deactivating PropertyFileDescriptionProvider");
    }

    public void setResourceBundle(ResourceBundleStore rb) {
        this.resourceBundle = rb;
    }

    public void clearResourceBundle(ResourceBundleStore rb) {
        this.resourceBundle = null;
    }


}
