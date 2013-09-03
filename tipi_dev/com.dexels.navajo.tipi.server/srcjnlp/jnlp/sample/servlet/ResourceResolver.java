package jnlp.sample.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public interface ResourceResolver {
	public URL getResource(String path) throws IOException;

	public String getMimeType(String path);

	public File getDir(String dirPath);

	public long getLastModified(String path);
}
