/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.impl.apache;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import org.apache.http.entity.AbstractHttpEntity;

import com.dexels.navajo.document.Navajo;
import com.jcraft.jzlib.DeflaterOutputStream;

public class NavajoRequestEntity extends AbstractHttpEntity {
    private Navajo n = null;
    private boolean useCompression = true;
    private boolean forceGzip = false;

    public NavajoRequestEntity(Navajo n, boolean useCompression, boolean forceGzip) {
        this.n = n;
        this.forceGzip = forceGzip;
        this.useCompression = useCompression;
        setContentType("text/xml; charset=UTF-8");
        if (useCompression) {
            if (forceGzip) {
                setContentEncoding("gzip");
            } else {
                setContentEncoding("jzlib");
            }
        }
    }

    @Override
    public InputStream getContent() throws IOException {
        StringWriter sw = new StringWriter();
        this.n.write(sw);
        return new ByteArrayInputStream(sw.toString().getBytes());
    }

    @Override
    public long getContentLength() {
        return -1;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public boolean isStreaming() {
        return false;
    }

    @Override
    public void writeTo(OutputStream os) throws IOException {
        BufferedWriter out = null;

        try {
            if (!useCompression) {
                out = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            } else if (forceGzip) {
                out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(os), StandardCharsets.UTF_8));
            } else {
                out = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(os), StandardCharsets.UTF_8));
            }
            n.write(out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
