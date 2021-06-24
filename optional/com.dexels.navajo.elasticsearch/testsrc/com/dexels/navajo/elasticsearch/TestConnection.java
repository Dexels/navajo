/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.elasticsearch.impl.ElasticSearchComponent;
import com.dexels.navajo.runtime.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class TestConnection {

    private static final Logger logger = LoggerFactory.getLogger(TestConnection.class);

    private static Navajo tmlDoc;

    @BeforeClass
    public static void parseTml() throws IOException {
        logger.info("parsing tml..");
        try (InputStream resourceAsStream = TestConnection.class.getClassLoader()
                .getResourceAsStream("tmlexample.xml")) {
            tmlDoc = NavajoFactory.getInstance().createNavajo(resourceAsStream);
        }
    }

    @Test
    @Ignore
    public void testInsert() throws IOException {
        ElasticSearchComponent esc = new ElasticSearchComponent();
        Map<String, Object> settings = new HashMap<>();
        String server = TestConfig.ELASTICSEARCH_SERVER.getValue();
        if (server == null) {
            logger.warn("No server defined, skipping test");
            return;
        }
        settings.put("url", server);
        settings.put("index", TestConfig.ELASTICSEARCH_INDEX);
        settings.put("type", TestConfig.ELASTICSEARCH_TYPE);
        settings.put("id_property", "_id");

        esc.activate(settings);
        Message m = tmlDoc.getMessage("Transaction");
        for (Message e : m.getAllMessages()) {
            ElasticSearchFactory.getInstance().insert(e);
        }
    }

    @Test
    @Ignore
    public void testMessageToJSON() throws IOException {
        Message m = tmlDoc.getMessage("Transaction");
        ObjectMapper objectMapper = new ObjectMapper();
        ElasticSearchComponent e = new ElasticSearchComponent();
        e.activate(Collections.emptyMap());
        ArrayNode nn = (ArrayNode) e.messageToJSON(m);
        objectMapper.writer().withDefaultPrettyPrinter().writeValue(System.err, nn);
        Assert.assertEquals(m.getArraySize(), nn.size());
    }

}
