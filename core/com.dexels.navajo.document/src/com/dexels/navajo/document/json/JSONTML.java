package com.dexels.navajo.document.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.dexels.navajo.document.Navajo;

public interface JSONTML {

	public abstract Navajo parse(InputStream is) throws Exception;

	public abstract Navajo parse(Reader r) throws Exception;

	public abstract void format(Navajo n, OutputStream os) throws Exception;

	public abstract void format(Navajo n, Writer w) throws Exception;

}