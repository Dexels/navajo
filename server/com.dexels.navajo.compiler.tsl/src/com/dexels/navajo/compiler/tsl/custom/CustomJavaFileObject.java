package com.dexels.navajo.compiler.tsl.custom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;

public class CustomJavaFileObject implements JavaFileObject {
	private final String binaryName;
	private final URI uri;
	private final String name;
	private Kind kind;
	private byte[] localContents;
	private URL lazyURL;
	
	
	public CustomJavaFileObject(String javaObjectName, URI uri, InputStream is,
			Kind kind) throws IOException {
		this(javaObjectName,uri,kind);
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			IOUtils.copy(is, baos);
			setContents(baos.toByteArray());
		}
	}

	public CustomJavaFileObject(String javaObjectName, URI uri, URL contents,
			Kind kind)  {
		this(javaObjectName,uri,kind);
		this.lazyURL = contents;
	}	
	
	private CustomJavaFileObject(String javaObjectName, URI uri, Kind kind) {
		this.uri = uri;
		this.binaryName = javaObjectName;
		this.kind = kind;
		String stripName = javaObjectName;
		if (stripName.endsWith("/")) {
			stripName = stripName.substring(0, stripName.length() - 1);
		}
		name = javaObjectName.substring(javaObjectName.lastIndexOf('/') + 1);
	}
	private void setContents(byte[] byteArray) {
		this.localContents = byteArray;
	}

	@Override
	public URI toUri() {
		return uri;
	}

	@Override
	public InputStream openInputStream() throws IOException {

		return getContents();
	}

	private InputStream getContents() throws IOException {
		if(lazyURL!=null) {
			return lazyURL.openStream();
		}
		return new ByteArrayInputStream(localContents);
	}
	
	@Override
	public OutputStream openOutputStream() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream() {

			@Override
			public void close() throws IOException {
				setContents( this.toByteArray());
				super.close();
			}};
		return baos;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return new InputStreamReader(openInputStream());
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		final Reader lc = new InputStreamReader(getContents());
		StringWriter sw = new StringWriter();
		IOUtils.copy(lc, sw);
		return sw.toString();
	}

	@Override
	public Writer openWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLastModified() {
		return 0;
	}

	@Override
	public boolean delete() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind k) {
		this.kind = k;
	}

	@Override
	public boolean isNameCompatible(String simpleName, Kind kind) {
		String baseName = simpleName + kind.extension;
		return kind.equals(getKind())
				&& (baseName.equals(getName()) || getName().endsWith(
						"/" + baseName));
	}

	@Override
	public NestingKind getNestingKind() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Modifier getAccessLevel() {
		throw new UnsupportedOperationException();
	}

	public String binaryName() {
		return binaryName;
	}

	@Override
	public String toString() {
		return "CustomJavaFileObject{" + "uri=" + uri + '}';
	}

}