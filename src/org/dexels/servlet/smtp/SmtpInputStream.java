package org.dexels.servlet.smtp;


import javax.servlet.ServletInputStream;
import java.io.*;


/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class SmtpInputStream extends ServletInputStream {

    protected String content = "";
    protected int offset = 0;

    public SmtpInputStream() {}

    public int read() throws java.io.IOException {
        if (content == null)
            throw new IOException("Empty smtp request");
        if (offset >= content.length())
            return -1;
        int a = (int) content.charAt(offset);

        offset++;
        return a;
    }
}
