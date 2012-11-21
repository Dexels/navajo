package com.dexels.navajo.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.spi.ComponentResolver;
import org.apache.camel.spi.TypeConverterLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.CamelComponent;

public class NavajoComponentResolver implements ComponentResolver {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoComponentResolver.class);
	
	private CamelComponent camelComponent;
	
	public NavajoComponentResolver() {
		super();
		logger.info("Camel component resolver");
	}
	
	public void setCamelComponent(CamelComponent component) {
		this.camelComponent = component;
	}

	public void clearCamelComponent(CamelComponent component) {
		this.camelComponent = null;
	}

	
	@Override
	public Component resolveComponent(String name, CamelContext context)
			throws Exception {
		
		return camelComponent;
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
