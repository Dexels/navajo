package com.dexels.navajo.camel;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.spi.ComponentResolver;
import org.apache.camel.spi.Registry;
import org.apache.camel.spi.TypeConverterLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.NavajoCamelComponent;
import com.dexels.navajo.camel.smtpserver.SMTPComponent;

public class NavajoComponentResolver implements ComponentResolver {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoComponentResolver.class);
	
	private NavajoCamelComponent camelComponent;
	
	public NavajoComponentResolver() {
		super();
		logger.info("Camel component resolver");
	}
	
	public void setCamelComponent(NavajoCamelComponent component) {
		this.camelComponent = component;
	}

	public void clearCamelComponent(NavajoCamelComponent component) {
		this.camelComponent = null;
	}

	public void activate(Map<String,Object> params) {
		for (Entry<String,Object> e : params.entrySet()) {
			logger.info("Activation param: {}",e.getKey(),e.getValue());
		}
	}
	
	public void deactivate() {
		
	}
	
	public void registerProcessors(CamelContext context) {
		Registry r = context.getRegistry();
		System.err.println("Registry clsss: "+r);
	}
	
	@Override
	public Component resolveComponent(String name, CamelContext context)
			throws Exception {
//		registerProcessors(context);
		try {
			if(name.equals("navajo")) {
				return camelComponent;
			}
			if(name.equals("smtpserver")) {
				return new SMTPComponent();
			}
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
		return null;
	}
	
	
	
	
	  protected void bindComponentResolver(ComponentResolver cr) 
	  { 
		  logger.trace("Binding ComponentResolver"); 
	    // Ignored, since the ComponentResolver is not directly used 
	  } 

	  protected void unbindComponentResolver(ComponentResolver cr) 
	  { 
		  logger.trace("Unbinding ComponentResolver"); 
	    // Ignored, since the ComponentResolver is not directly used 
	  } 

	  protected void bindTypeConverterLoader(TypeConverterLoader tcl) 
	  { 
		  logger.trace("Binding TypeConverterLoader"); 
	    // Ignored, since the ComponentResolver is not directly used 
	  } 

	  protected void unbindTypeConverterLoader(TypeConverterLoader tcl) 
	  { 
		  logger.trace("Unbinding TypeConverterLoader"); 
	    // Ignored, since the ComponentResolver is not directly used 
	  } 

}
