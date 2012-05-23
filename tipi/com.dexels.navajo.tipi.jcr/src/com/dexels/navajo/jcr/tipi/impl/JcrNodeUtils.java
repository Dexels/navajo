package com.dexels.navajo.jcr.tipi.impl;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

public class JcrNodeUtils {
	public static InputStream readFile(Node node) throws RepositoryException {
		if (node.hasProperty(Property.JCR_DATA)) {
			Property data = node.getProperty(Property.JCR_DATA);
			final Binary binary = data.getBinary();
			return new FilterInputStream(binary.getStream()) {
				@Override
				public void close() throws IOException {
					super.close();
					binary.dispose();
				}
			};
		} else if (node.hasNode(Node.JCR_CONTENT)) {
			return readFile(node.getNode(Node.JCR_CONTENT));
		} else {
			throw new RepositoryException("Unable to read file node: "
					+ node.getPath());
		}
	}
}
