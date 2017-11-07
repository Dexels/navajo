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

public class LookupParser extends BaseTipiParser {

    private static final String PROPERTIES_EXTENSION = ".properties";
    private static final Logger logger = LoggerFactory.getLogger(LookupParser.class);
    private static final long serialVersionUID = 5584565910100210484L;
    private static Map<String, ResourceBundle> cache = new HashMap<>();
    private static ResourceBundle globalBundle = null;

    @Override
    public Object parse(TipiComponent tc, String expression, TipiEvent event) {
        TipiContext context = event.getContext();
        String loc = resolveInclude(context, getHomeDefinitionName(tc));
        loc = "texts" + File.separator + loc.substring(0, loc.indexOf(".xml"));

        String result = lookupResourceBundle(context, loc, expression);
        if (result != null)
            return result;

        result = lookupGlobalResourceBundle(context, expression);
        if (result != null)
            return result;

        logger.warn("Missing translateion for {} in {}", expression, loc);
        return null;
    }

    private String lookupGlobalResourceBundle(TipiContext context, String expression) {
        if (LookupParser.globalBundle == null) {
            setGlobalResourceBundle(context);
        }
        if (LookupParser.globalBundle.containsKey(expression)) {
            return LookupParser.globalBundle.getString(expression);
        }
        return null;
    }

    private String lookupResourceBundle(TipiContext context, String loc, String expression) {
        ResourceBundle b = cache.get(loc);
        if (b == null) {
            try {
                b = getResourceBundle(context, loc);
            } catch (IOException e) {
                logger.error("Exception retrieving properties!");
                return null;
            }
            cache.put(loc, b);
        }
        if (b.containsKey(expression)) {
            return b.getString(expression);
        }
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

    private synchronized void setGlobalResourceBundle(TipiContext context) {
        if (LookupParser.globalBundle != null) {
            return;
        }
        TipiResourceBundle result = new TipiResourceBundle();

        String filename = "texts" + File.separator + "main" + PROPERTIES_EXTENSION;
        String localefilename = "texts" + File.separator + "main_" + context.getApplicationInstance().getLocaleCode()
                + PROPERTIES_EXTENSION;

        try {
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
                ResourceBundle defaultBundle = new PropertyResourceBundle(s);
                s.close();
                for (String key : defaultBundle.keySet()) {
                    result.put(key, defaultBundle.getObject(key));
                }
            }

            if (context.getApplicationInstance().getSubLocaleCode() != null
                    && !context.getApplicationInstance().getSubLocaleCode().isEmpty()) {
                String sublocalefilename = "texts" + File.separator + "main_" + context.getApplicationInstance().getLocaleCode() + "_"
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
        } catch (IOException e) {
            logger.error("Exception reading global resource bundle file!", e);
        }

        LookupParser.globalBundle = result;
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