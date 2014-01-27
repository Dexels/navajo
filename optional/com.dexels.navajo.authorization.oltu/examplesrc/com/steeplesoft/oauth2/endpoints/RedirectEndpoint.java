/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.oauth2.endpoints;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author jdlee
 */
@Path("/redirect")
public class RedirectEndpoint {

    @Context
    HttpHeaders httpHeaders;
    @Context
    UriInfo uriInfo;

    @GET
    public String redirect() {
        JSONObject object = new JSONObject();
        JSONObject headers = new JSONObject(); 
        JSONObject qp = new JSONObject();
        String json = "error!";
        try {
            for (Map.Entry<String, List<String>> entry : httpHeaders.getRequestHeaders().entrySet()) {
                headers.put(entry.getKey(), entry.getValue().get(0));
            }
            object.put("headers", headers);
            for (Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
                qp.put(entry.getKey(), entry.getValue().get(0));
            }
            object.put("queryParameters", qp);
            json = object.toString(4);
        } catch (JSONException ex) {
            Logger.getLogger(RedirectEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
