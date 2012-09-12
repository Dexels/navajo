package tipi;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public class ServiceRegistrationUtils {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ServiceRegistrationUtils.class);
	@SuppressWarnings("rawtypes")
	static ServiceRegistration registerWhiteBoardExtension(TipiExtension extension,
			BundleContext context) throws FileNotFoundException {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("extensionId", extension.getId());
		props.put("type", "tipiExtension");
		props.put("extensionDescription", extension.getDescription());
		props.put("extensionClass", extension.getClass().getName());
		// getIncludes();
		for (String include : extension.getIncludes()) {
			
			readDefinitionFile(extension, include,context);
		}
		return context.registerService(TipiExtension.class.getName(), extension, props);

	}
	
	
	@SuppressWarnings("rawtypes")
	static ServiceRegistration registerCoreExtension(TipiCoreExtension extension,
			BundleContext context) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("extensionId", extension.getId());
		props.put("type", "tipiExtension");
		props.put("extensionDescription", extension.getDescription());
		props.put("extensionClass", extension.getClass().getName());
		return context.registerService(TipiCoreExtension.class.getName(), extension, props);
	}
	
	@SuppressWarnings("rawtypes")
	public static ServiceRegistration registerMainExtension(TipiMainExtension extension,
			BundleContext context)  {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("extensionId", extension.getId());
		props.put("type", "tipiExtension");
		props.put("extensionDescription", extension.getDescription());
		props.put("extensionClass", extension.getClass().getName());
		return context.registerService(TipiMainExtension.class.getName(), extension, props);
	}
	
	
	private static final void readDefinitionFile(TipiExtension extension, String include, BundleContext context) throws FileNotFoundException {
		// Read config file.
		
		InputStream is = extension.getClass().getClassLoader().getResourceAsStream(include);
		CaseSensitiveXMLElement xml = new CaseSensitiveXMLElement();
		if(is==null) {
			throw new FileNotFoundException("Can not load include: "+include+" for extension: "+extension);
		}
		try {
			Reader r = new InputStreamReader(is);
			xml.parseFromReader(r);
			is.close();
			List<XMLElement> children = xml.getChildren();
			for (int i = 0; i < children.size(); i++) {
				XMLElement element = children.get(i);
				registerTipiElement(element, extension, context);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void registerTipiElement(XMLElement element, TipiExtension extension, BundleContext context) throws ClassNotFoundException {
		String type = element.getName();
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("extensionId", extension.getId());
		props.put("extension", extension);
		props.put("type", type);
		String className = element.getStringAttribute("class");
		String packageName = element.getStringAttribute("package");
		if(className!=null) {
			String fullName = null;
			if(packageName!=null) {
				fullName = packageName+"."+className;
			} else {
				fullName = className;
			}
			Class<?> ex = Class.forName(fullName,true,extension.getClass().getClassLoader());
			props.put("class", fullName);
			props.put("classInstance", ex);
		}
		String elementName = element.getStringAttribute("name");
		if(elementName!=null) {
			props.put("name", elementName);
		} else {
			logger.warn("element without name: "+element);
		}
		if(type.equals("tipi-parser")) {
			parseParser(context, props, element, extension);
			return;
		}
		if(type.equals("tipiaction")) {
			registerTipiAction(element,props, extension,context);
			return;
		}
		
//		Class<? extends TipiComponent> componentClass = (Class<? extends TipiComponent>) Class.forName(packageName+"."+className, true, extension.getClass().getClassLoader());
//		TipiComponent tipiComponent = componentClass.newInstance();
		context.registerService(XMLElement.class.getName(), element, props);
	}
	


	public static void parseParser(BundleContext bundleContext, Dictionary<String, Object> props, XMLElement xe, final TipiExtension extension) {
//		String name = xe.getStringAttribute("name");
		final String parserClass = xe.getStringAttribute("parser");
		final String classType = xe.getStringAttribute("type");
		
		try {
		final Class<TipiTypeParser> pClass =  (Class<TipiTypeParser>) Class.forName(parserClass, true,
					extension.getClass().getClassLoader());

		props.put("parser", pClass);

		ServiceFactory<TipiTypeParser> ttp = new ServiceFactory<TipiTypeParser>() {

			@Override
			public TipiTypeParser getService(Bundle bundle,
					org.osgi.framework.ServiceRegistration<TipiTypeParser> registration) {
				TipiTypeParser ttp = null;
				try {
					ttp = pClass.newInstance();
				} catch (IllegalAccessException ex1) {
					logger.error("Error instantiating class for parser: "
							+ parserClass,ex1);
					ex1.printStackTrace();
					return null;
				} catch (InstantiationException ex1) {
					logger.error("Error instantiating class for parser: "
							+ parserClass,ex1);
					return null;
				}
				try {
					Class<?> cc = Class.forName(classType, true, extension.getClass().getClassLoader());
					ttp.setReturnType(cc);
				} catch (ClassNotFoundException ex) {
					logger.error("Error verifying return type class for parser: "
							+ classType,ex);
					return ttp;
				}				
				return ttp;
			}

			@Override
			public void ungetService(
					Bundle bundle,
					org.osgi.framework.ServiceRegistration<TipiTypeParser> arg1,
					TipiTypeParser registration) {
			}
		};
		bundleContext.registerService(TipiTypeParser.class.getName(), ttp, props);
		} catch (ClassNotFoundException ex) {
			System.err
					.println("Error loading class for parser: " + parserClass);
			return;
		}
	}
	
	//	@SuppressWarnings("unchecked")
	private static void registerTipiAction(XMLElement element,	Dictionary<String, Object> props,TipiExtension extension, BundleContext context) throws ClassNotFoundException {
		props.put("extensionId", extension.getId());
		props.put("type", "tipiAction");
		String className = element.getStringAttribute("class");
		String packageName = element.getStringAttribute("package");
		props.put("class", packageName+"."+className);
		TipiActionFactory taf = new TipiActionFactory();
		taf.load(element,extension);
		context.registerService(TipiActionFactory.class.getName(), taf, props);
	}
}
