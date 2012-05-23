package com.dexels.navajo.server;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */


// This must be the dirtyest thing on earth.
// Note to future self: TODO: REFACTOR TO A NORMAL NAME!
import java.io.InputStream;

public interface InputStreamReader {

  public InputStream getResource(String name);

}