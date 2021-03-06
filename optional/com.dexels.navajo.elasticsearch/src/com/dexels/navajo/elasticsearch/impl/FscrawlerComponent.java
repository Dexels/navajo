/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.elasticsearch.FscrawlerFactory;
import com.dexels.navajo.elasticsearch.FscrawlerService;

public class FscrawlerComponent implements FscrawlerService {

    private final static Logger logger = LoggerFactory.getLogger(FscrawlerComponent.class);

    private CloseableHttpClient httpclient;

    private String url;

    public void activate(Map<String, Object> settings) {

        logger.info("Activating...");

        httpclient = HttpClients.createDefault();
        this.url = (String) settings.get("url");
        FscrawlerFactory.setInstance(this);
    }

    public void deactivate() {

        logger.info("Deactivating...");
        FscrawlerFactory.setInstance(null);
        if (httpclient != null) {
            try {
                System.out.println(">>>>>Connection Closed (in deactivate)");
                httpclient.close();
            } catch (IOException e) {
                logger.error("Error: ", e);
            }
        }
    }

    public void upload(Binary data, String id, String name) throws IOException {

        logger.info("IN UPLOAD: " + name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copyResource(baos, data.getDataAsStream());

        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", new ByteArrayBody(baos.toByteArray(), ContentType.TEXT_PLAIN, name))
                .addPart("id", new StringBody(id, ContentType.TEXT_PLAIN)).build();

        HttpPost request = new HttpPost(url);
        request.setEntity(entity);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        System.out.println(response.getStatusLine());

        System.out.println(">>>>>Connection Closed (under status line)");
        httpclient.close();

    }

    private static void copyResource(OutputStream out, InputStream in) throws IOException {

        try (BufferedInputStream bin = new BufferedInputStream(in);
                BufferedOutputStream bout = new BufferedOutputStream(out);) {
            byte[] buffer = new byte[1024];
            int read = -1;
            boolean ready = false;
            while (!ready) {

                read = bin.read(buffer);
                System.err.println("data: " + read);
                if (read > -1) {
                    bout.write(buffer, 0, read);
                }

                if (read <= -1) {
                    ready = true;
                }
            }
        }
    }

}
