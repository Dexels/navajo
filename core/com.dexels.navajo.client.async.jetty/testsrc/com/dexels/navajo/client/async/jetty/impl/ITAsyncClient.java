/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.async.jetty.impl;

import java.io.StringWriter;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.document.BinaryOpenerFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.runtime.config.TestConfig;

public class ITAsyncClient {

    private static final Logger logger = LoggerFactory.getLogger(ITAsyncClient.class);

    @Test @Ignore
    public void test() throws Exception {
        final ManualAsyncClient ac = new AsyncClientImpl();
        ac.setClientCertificate("SunX509", "JKS", getClass().getClassLoader().getResourceAsStream("client.jks"), "password".toCharArray());
        // TODO make actual test
    }

    @Test
    public void testPost() throws Exception {

        final ManualAsyncClient ac = new AsyncClientImpl();

        String service = "ProcessPrintGenericBirt";
        System.err.println(TestConfig.NAVAJO_TEST_SERVER.getValue());
        ac.setServer(TestConfig.NAVAJO_TEST_SERVER.getValue());
        ac.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
        ac.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
        Navajo input = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("test.xml"));
        final NavajoResponseHandler showOutput = new NavajoResponseHandler() {
            @Override
            public void onResponse(Navajo n) {
                logger.info("Navajo finished!");
                try {
                    StringWriter sw = new StringWriter();
                    n.write(sw);
                    Binary b = (Binary) n.getMessage("Result").getProperty("Data").getTypedValue();
                    BinaryOpenerFactory.getInstance().open(b);
                    logger.info("Response2 : {}",sw);
                } catch (NavajoException e) {
                    logger.error("Error: ", e);
                }
            }

            @Override
            public void onFail(Throwable t) {
                logger.error("whoops: ", t);
            }

            @Override
            public Throwable getCaughtException() {
                return null;
            }
        };
        ac.callService(input, service, showOutput);
        logger.info("Exchange sent");
        Thread.sleep(10000);
    }

    @Test @Ignore
    public void testAsync() throws Exception {

        final ManualAsyncClient ac = new AsyncClientImpl();

        String service = "club/InitUpdateClub";
        System.err.println(TestConfig.NAVAJO_TEST_SERVER.getValue());
        ac.setServer(TestConfig.NAVAJO_TEST_SERVER.getValue());
        ac.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
        ac.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
        Navajo input = NavajoFactory.getInstance().createNavajo();
        final NavajoResponseHandler showOutput = new NavajoResponseHandler() {
            @Override
            public void onResponse(Navajo n) {
                logger.info("Navajo finished!");
                try {
                    StringWriter sw = new StringWriter();
                    n.write(sw);
                    logger.info("Response2 : {}",sw);
                } catch (NavajoException e) {
                    logger.error("Error: ", e);
                }
            }

            @Override
            public void onFail(Throwable t) {
                logger.error("whoops: ", t);
            }

            @Override
            public Throwable getCaughtException() {
                return null;
            }
        };
        for (int i = 0; i < 10; i++) {
            ac.callService(input, service, showOutput);
            logger.info("Exchange sent");
        }
        Thread.sleep(10000);
    }
}
