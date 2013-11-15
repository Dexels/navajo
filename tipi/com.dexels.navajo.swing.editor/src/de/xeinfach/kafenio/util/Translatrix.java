package de.xeinfach.kafenio.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Description: This class translates string-ids into language specific messages.
 * 
 * @author Howard Kistler, Karsten Pawlik
 */
public final class Translatrix {
	
	private static LeanLogger log = new LeanLogger("Translatrix.class");
	
	private ResourceBundle langResources;
	private String bundleName;
	private Locale locale;

	/**
	 * private constructor for utility classes
	 */
	public Translatrix() {}

	/**
	 * private constructor for utility classes
	 * @param newBundleName name of the resourcebundle to use
	 */
	public Translatrix(String newBundleName) {
		setBundleName(newBundleName);
	}

	/**
	 * sets the resourcebundle name for this class and tries to load it.
	 * @param bundle name of the resourcebundle to load.
	 */
	public void setBundleName(String bundle) {
		bundleName = bundle;
		try {
			langResources = ResourceBundle.getBundle(bundleName);
		} catch(MissingResourceException mre) {
			log.error("MissingResourceException while loading language file" + mre.fillInStackTrace());
		}
	}

	/**
	 * sets the current locale, loads the default language file if locale-version could not be found.
	 * @param newLocale locale string.
	 */
	public void setLocale(Locale newLocale) {
		locale = newLocale;
		log.debug("setting locale to: " + locale);
		if(bundleName == null) {
			log.debug("bundle-name not set, returning null.");
			return;
		}
		if(locale != null) {
			try {
				langResources = ResourceBundle.getBundle(bundleName, locale);
			} catch(MissingResourceException mre1) {
				try {
					langResources = ResourceBundle.getBundle(bundleName);
				} catch(MissingResourceException mre2) {
					log.error("MissingResourceException while loading language file" + mre2.fillInStackTrace());
				}
			}
		} else {
			try {
				langResources = ResourceBundle.getBundle(bundleName);
			} catch(MissingResourceException mre) {
				log.error("MissingResourceException while loading language file" + mre.fillInStackTrace());
			}
		}
	}

	/**
	 * @return returns the currently set locale for this object.
	 */
	public Locale getLocale() {
		return locale;
	}
	/**
	 * sets the locale using language and country information provided as string. 
	 * @param language language as string
	 * @param country country-code as string
	 */
	public void setLocale(String language, String country) {
		if(language != null && country != null) {
			setLocale(new Locale(language, country));
		}
	}

	/**
	 * returns the translated string for the given originalText
	 * @param originalText the property name to return the translated string for.
	 * @return returns a string containing the language-specific value for the given property name.
	 */
	public String getTranslationString(String originalText) {
		if(bundleName == null) {
			return originalText;
		}
		try {
			return langResources.getString(originalText);
		} catch (NullPointerException n) {
			return "";
		}
	}

	/**
	 * logs an exception to standard err.
	 * @param internalMessage an internal message
	 * @param e the exception to log.
	 */
	private void logException(String internalMessage, Exception e) {
		System.err.println(internalMessage);
		e.printStackTrace(System.err);
	}
}