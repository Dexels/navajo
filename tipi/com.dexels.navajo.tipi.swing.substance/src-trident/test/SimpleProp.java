package test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProp {
	
	private final static Logger logger = LoggerFactory
			.getLogger(SimpleProp.class);
	public void setValue(float value) {
		logger.info("Cool " + value);
	}
	
	public static void main(String[] args) throws Exception {
		SimpleProp prop = new SimpleProp();
		PropertyDescriptor desc = new PropertyDescriptor("value", prop.getClass(),
				null, "setValue");
		Method writer = desc.getWriteMethod();
		writer.invoke(prop, new Float(2.0));
		logger.info(""+Float.class.isAssignableFrom(float.class));
	}
}
