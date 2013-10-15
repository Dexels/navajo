package jnlp.sample.servlet;

import java.io.File;
import java.io.IOException;

public interface ResourceVerifier {
	void verifyResource(String path, File f) throws SecurityException, IOException;
}
