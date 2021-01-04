/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.elasticsearch.ElasticSearchQueryFactory;
import com.dexels.navajo.elasticsearch.ElasticSearchQueryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticSearchQueryComponent implements ElasticSearchQueryService {

    private final static Logger logger = LoggerFactory.getLogger(FscrawlerComponent.class);

    private CloseableHttpClient httpclient;

    public String url;

    public void activate(Map<String, Object> settings) {

        logger.info("Activating...");
        httpclient = HttpClients.createDefault();
        this.url = (String) settings.get("url");
        ElasticSearchQueryFactory.setInstance(this);
    }

    public void deactivate() {
        logger.info("Deactivating...");
        ElasticSearchQueryFactory.setInstance(null);
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("Error: ", e);
            }
        }
    }

    @Override
    public ElasticSearchResult[] search(String keyword) throws IOException {
        ArrayList<ElasticSearchResult> esResult = new ArrayList<ElasticSearchResult>();
        String result = null;

        // curl -XGET "http://localhost:9200/job_10/_search?q=content:SOME_TEXT"
        // if we want to search exact phrase into a document or one or more fields we
        // can use OR, AND etc
        String esUrl = this.url + URLEncoder.encode(keyword, "UTF-8");

        HttpGet request = new HttpGet(esUrl);
        request.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            // return it as a String
            result = EntityUtils.toString(entity);
            System.out.println("ENTITY: " + result);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode n = mapper.readTree(result);
        JsonNode jsonArray = n.get("hits").get("hits");

        for (JsonNode jsonNode : jsonArray) {
            String id = jsonNode.get("_id").toString();
            String score = jsonNode.get("_score").toString();
            String fileName = jsonNode.get("_source").get("file").get("filename").toString();
            ElasticSearchResult esr = new ElasticSearchResult(id, score, fileName);
            esResult.add(esr);
        }

        ElasticSearchResult[] es_result_array = esResult.toArray(new ElasticSearchResult[esResult.size()]);
        response.close();

        return es_result_array;
    }

}
