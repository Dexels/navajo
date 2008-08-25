/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.util;

import com.dexels.navajo.document.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public final class Util {

    @SuppressWarnings("unused")
	private static ResourceBundle rb = null;

    private final static Random random;
    private static String id;
    
    static {
    	random = new Random(System.currentTimeMillis());
    	try {
			id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			id = "Navajo";
		}
    }
    
    public final static boolean isRegularExpression(String s) {

        if (s.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR))
          return isRegularExpression(s.substring((Navajo.PARENT_MESSAGE +Navajo.MESSAGE_SEPARATOR).length()));
        if ((s.indexOf("*") != -1) || (s.indexOf(".") != -1)
                || (s.indexOf("\\") != -1) || (s.indexOf("?") != -1)
                || (s.indexOf("[") != -1) || (s.indexOf("]") != -1)
                )
            return true;
        else
            return false;

    }

    /**
     * Method to generate the random GUID.
     */
    public static String getRandomGuid() {
            MessageDigest md5 = null;
            StringBuffer sbValueBeforeMD5 = new StringBuffer();

            try {
                    md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
            }

            long time = System.currentTimeMillis();
            long rand = 0;

          
            rand = random.nextLong();
         

            // This StringBuffer can be a long as you need; the MD5
            // hash will always return 128 bits. You can change
            // the seed to include anything you want here.
            // You could even stream a file through the MD5 making
            // the odds of guessing it at least as great as that
            // of guessing the contents of the file!
            sbValueBeforeMD5.append(id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));

            String valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());

            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                    int b = array[j] & 0xFF;
                    if (b < 0x10)
                            sb.append('0');
                    sb.append(Integer.toHexString(b));
            }
            
            return sb.toString();
    }

    public static void main(String args[]) throws Exception {
        java.util.Date d = new java.util.Date();
      //  System.out.println(formatDate(d));
        for (int i = 0; i < 10; i++)
        System.err.println(getRandomGuid());
    }

}

