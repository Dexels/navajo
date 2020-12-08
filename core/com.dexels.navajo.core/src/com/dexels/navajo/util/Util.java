/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ResourceBundle;

import com.dexels.navajo.document.Navajo;

public final class Util {

    private static final Random random;
    private static String id;
    
    static {
    	random = new Random(System.currentTimeMillis());
    	try {
			id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			id = "Navajo";
		}
    }
    
    private Util() {
    	// - no instances
    }
    public static final boolean isRegularExpression(String s) {

        if (s.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR))
          return isRegularExpression(s.substring((Navajo.PARENT_MESSAGE +Navajo.MESSAGE_SEPARATOR).length()));
        return (s.indexOf('*') != -1) || (s.indexOf('.') != -1)
                || (s.indexOf('\\') != -1) || (s.indexOf('?') != -1)
                || (s.indexOf('[') != -1) || (s.indexOf(']') != -1);

    }

    /**
     * Method to generate the random GUID.
     */
    public static String getRandomGuid() {
            MessageDigest md5 = null;
            StringBuilder sbValueBeforeMD5 = new StringBuilder();

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
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < array.length; ++j) {
                    int b = array[j] & 0xFF;
                    if (b < 0x10)
                            sb.append('0');
                    sb.append(Integer.toHexString(b));
            }
            
            return sb.toString();
    }

}

