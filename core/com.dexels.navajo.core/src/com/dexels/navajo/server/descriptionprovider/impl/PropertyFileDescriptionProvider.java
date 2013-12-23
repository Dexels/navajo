package com.dexels.navajo.server.descriptionprovider.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;

public class PropertyFileDescriptionProvider extends BaseDescriptionProvider
		implements DescriptionProviderInterface {

	private final static Logger logger = LoggerFactory
			.getLogger(PropertyFileDescriptionProvider.class);
	private NavajoIOConfig navajoIOConfig;

	private final Map<String,Properties> localeProperties = new HashMap<String, Properties>();
	@Override
	public void updateProperty(Navajo in, Property element, String locale) {
		if(locale==null) {
			logger.warn("No locale set!");
		}
		Properties p = localeProperties.get(locale);
		if(p == null) {
			p = loadLocale(locale);
		}
		String service = in.getHeader().getRPCName();
		
		update(service, element, p);
	}



	private void update(String service, Property element, Properties p) {
		String entry = p.getProperty(element.getName());
		if(entry!=null) {
			element.setDescription(entry);
		} else {
			element.setDescription("[No translation]");
		}
	}



	public void activate() throws IOException {
		logger.info("Activating PropertyFileDescriptionProvider");

	}



	private Properties loadLocale(String locale)  {
		File f = new File(navajoIOConfig.getConfigPath());
		File description = new File(f, "description_"+locale+".properties");
		Properties old = localeProperties.get(locale);
		if(old!=null) {
			long fileStamp = f.lastModified();
			Long parsedAt = (Long) old.get("parsedAt");
			if(parsedAt!=null) {
				if(fileStamp<parsedAt) {
					// ok to use old one:
					return old;
				} else {
					logger.warn("Description file changed. Reloading.");
				}
			} else {
				logger.warn("No parsedAt element found in properties. Caching will not work.");
			}
		}
		Properties properties = new Properties();
		
		if (!description.exists()) {
			logger.warn("No locale file found for locale: "+locale+" path: "+description.getAbsolutePath());
			return properties;
		}
		properties.put("parsedAt", System.currentTimeMillis());
		InputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(description);
			Reader r = new InputStreamReader(fileInputStream,"UTF-8");
			properties.load(r);
		} catch(IOException ioe) {
			logger.error("Error reading locale file for: "+f.getAbsolutePath());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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

	
}
