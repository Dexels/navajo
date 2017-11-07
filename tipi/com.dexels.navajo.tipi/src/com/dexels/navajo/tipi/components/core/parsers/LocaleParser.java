package com.dexels.navajo.tipi.components.core.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class LocaleParser extends BaseTipiParser {

    private static final String PROPERTIES_EXTENSION = ".properties";

    private static final Logger logger = LoggerFactory.getLogger(LocaleParser.class);

    private static final long serialVersionUID = 5584565910100210484L;

    private static Map<String, ResourceBundle> cache = new HashMap<>();

    @Override
    public Object parse(TipiComponent tc, String expression, TipiEvent event) {
        String loc = resolveInclude(event.getContext(), getHomeDefinitionName(tc));
        loc = "css" + File.separator + loc.substring(0, loc.indexOf(".xml"));
        
        ResourceBundle b = cache.get(loc);
        if (b == null) {
            try {
                b = getResourceBundle(event.getContext(), loc);
            } catch (IOException e) {
                logger.error("Exception retrieving properties!");
                return null;
            }
            cache.put(loc,  b);
        }
        if (b.containsKey(expression)) {
            return b.getString(expression);
        }
        logger.warn("Missing translateion for {} in {}", expression, loc);

        return null;
    }

    
    private String resolveInclude(TipiContext context, String definition) {
        return context.resolveInclude(definition);
    }

    private String getHomeDefinitionName(TipiComponent tc) {
        if (tc == null) {
            return null;
        } else if (tc.getHomeComponent() != null) {
            return tc.getHomeComponent().getName();
        } else {
            return tc.getName();
        }
    }

    private ResourceBundle getResourceBundle(TipiContext context, String baseLocation) throws IOException {
        String filename = baseLocation + PROPERTIES_EXTENSION;
        String localefilename = baseLocation + "_" + context.getApplicationInstance().getLocaleCode() + PROPERTIES_EXTENSION;

        TipiResourceBundle result = new TipiResourceBundle();

        InputStream s = context.getGenericResourceStream(filename);
        if (s != null) {
            ResourceBundle defaultBundle = new PropertyResourceBundle(s);
            s.close();
            for (String key : defaultBundle.keySet()) {
                result.put(key, defaultBundle.getObject(key));
            }
        }

        s = context.getGenericResourceStream(localefilename);
        if (s != null) {
            ResourceBundle localeBundle = new PropertyResourceBundle(s);
            s.close();
            for (String key : localeBundle.keySet()) {
                result.put(key, localeBundle.getObject(key));
            }
        }

        if (context.getApplicationInstance().getSubLocaleCode() != null && !context.getApplicationInstance().getSubLocaleCode().isEmpty()) {
            String sublocalefilename = baseLocation + "_" + context.getApplicationInstance().getLocaleCode() + "_"
                    + context.getApplicationInstance().getSubLocaleCode().toLowerCase() + PROPERTIES_EXTENSION;

            s = context.getGenericResourceStream(sublocalefilename);
            if (s != null) {
                ResourceBundle sublocaleBundle = new PropertyResourceBundle(s);
                s.close();
                for (String key : sublocaleBundle.keySet()) {
                    result.put(key, sublocaleBundle.getObject(key));
                }
            }
        }
        return result;
    }
    
    private class TipiResourceBundle extends ResourceBundle {
        private Map<String, Object> data = new HashMap<>();
        
        protected void put(String key, Object value) {
            data.put(key, value);
        }
        
        @Override
        protected Object handleGetObject(String key) {
            return data.get(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return new Vector<String>(data.keySet()).elements();
        }
        
    }

}
