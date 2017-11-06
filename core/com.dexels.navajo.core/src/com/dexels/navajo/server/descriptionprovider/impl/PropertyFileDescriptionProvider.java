package com.dexels.navajo.server.descriptionprovider.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.impl.ArticleBaseServlet;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;
import com.dexels.resourcebundle.ResourceBundleStore;

public class PropertyFileDescriptionProvider extends BaseDescriptionProvider implements DescriptionProviderInterface {
    private static final Logger logger = LoggerFactory.getLogger(PropertyFileDescriptionProvider.class);

    private ResourceBundleStore resourceBundle;

    @Override
    public void updatePropertyDescriptions(Navajo in, Navajo out, Access access) throws NavajoException {
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
        String locale = in.getHeader().getHeaderAttribute("locale");
        if (locale==null) {
            return;
            //locale = "NL";
        }
        String sublocale = in.getHeader().getHeaderAttribute("sublocale");
        
        Properties p = this.getProperties();
        
                
        p.getProperty(getFqdnPropertyLookupKey(property, access.getRpcName()));
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


    
    private Properties getProperties() {
        if (cachedProperties.get() ) {
            
        }
        String entry = resourceBundle.getResource(null, "properties", access.getTenant(), sublocale, locale);

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
