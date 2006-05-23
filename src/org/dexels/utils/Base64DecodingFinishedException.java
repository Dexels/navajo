/*
 * Created on May 15, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.dexels.utils;

import java.io.*;

public class Base64DecodingFinishedException extends IOException {

    public Base64DecodingFinishedException(char[] data, int offset, int len) {
        System.err.println("Base64 finished at: "+offset+" len: "+len);
    }


}
