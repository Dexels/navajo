package com.dexels.navajo.camel.typeconversion;

import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

public class EventTypeConverter extends TypeConverterSupport {

	@Override
	public <T> T convertTo(Class<T> arg0, Exchange arg1, Object arg2)
			throws TypeConversionException {
		return null;
	}

}
