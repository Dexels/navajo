package com.dexels.navajo.compiler.tsl.custom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;


public class CustomJavaFileObject implements JavaFileObject {
	private final String binaryName;
	private final URI uri;
	private String name;
	private Kind kind;
	private byte[] localContents;
	private URI lazyURL;

	public CustomJavaFileObject(String javaObjectName, URI uri, InputStream is,
			Kind kind) throws IOException {
		this(javaObjectName, uri, kind);
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			IOUtils.copy(is, baos);
			byte[] data = baos.toByteArray();
			setContents(data);
			File generated = File.createTempFile("compile", ".java");
			File parent = generated.getParentFile();
			File current = parent;
			List<String> parts = foldersToCreate(javaObjectName);
			for (String element : parts) {
				current = new File(current,element);
				current.mkdirs();
			}
			String resolvedName = fileName(javaObjectName);
			File tmp = new File(current,resolvedName);
			FileOutputStream fos = new FileOutputStream(tmp);
			IOUtils.copy(new ByteArrayInputStream(data), fos);
			fos.close();
			this.name = tmp.getAbsolutePath();
			this.lazyURL = Paths.get(tmp.getAbsolutePath()).toUri();
		}
	}

	private List<String> foldersToCreate(String javaObjectName) {
		List<String> result = new ArrayList<>();
		String[] parts = javaObjectName.split("/");
		for (int i = 0; i < parts.length-1; i++) {
			result.add(parts[i]);
		}
		return result;
	}
	
	private String fileName(String javaObjectName) {
		String[] parts = javaObjectName.split("/");
		return parts[parts.length-1];
	}

	public CustomJavaFileObject(String javaObjectName, URI uri, URI contents,
			Kind kind) {
		this(javaObjectName, uri, kind);
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
		name = uri.toString(); // javaObjectName.substring(javaObjectName.lastIndexOf('/') + 1);
	}

	private void setContents(byte[] byteArray) {
		this.localContents = byteArray;
	}

	@Override
	public URI toUri() {
		return lazyURL;
	}

	@Override
	public InputStream openInputStream() throws IOException {

		return getContents();
	}

	private InputStream getContents() throws IOException {
		if (lazyURL != null) {
			return lazyURL.toURL().openStream();
		}
		return new ByteArrayInputStream(localContents);
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream() {

			@Override
			public void close() throws IOException {
				setContents(this.toByteArray());
				super.close();
			}
		};
		return baos;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return new InputStreamReader(openInputStream(), "UTF-8");
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		final Reader lc = new InputStreamReader(getContents(), "UTF-8");
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