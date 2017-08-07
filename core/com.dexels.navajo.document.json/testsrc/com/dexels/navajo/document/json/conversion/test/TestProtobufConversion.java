package com.dexels.navajo.document.json.conversion.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.conversion.JsonTmlFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.api.ReplicationMessage.Operation;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class TestProtobufConversion {

    @Before
    public void setup() {
        ReplicationFactory.setInstance(new FallbackReplicationMessageParser());
    }

    @Test
    public void testConversion() throws JsonGenerationException, JsonMappingException, IOException {
        ReplicationMessage createReplicationMessage = createReplicationMessage();
        // convert to protobuf
        byte[] serializedMessage = ReplicationFactory.getInstance().serialize(createReplicationMessage);
        System.out.println(new String(serializedMessage));
        assertNotNull(serializedMessage);
        // parse protobuf
        
        ReplicationMessage rmsg = ReplicationFactory.getInstance().parseBytes(serializedMessage);
        System.err.println("Rescribe: " + new String(rmsg.toBytes(new JSONReplicationMessageParserImpl())));
        Navajo rr = JsonTmlFactory.getInstance().toReplicationNavajo(rmsg, "Tenant", "Table",
                Optional.of("Datasource"));
        rr.write(System.out);
        // Check

        Assert.assertEquals(6, rr.getMessage("Transaction/Columns").getArraySize());


    }

    private ReplicationMessage createReplicationMessage() {
        // Create replication message
        List<String> pks = new ArrayList<>();
        Map<String, Object> values = new HashMap<>();
        Map<String, String> types = new HashMap<>();
        pks.add("matchid");

        values.put("matchid", 15106351);
        types.put("matchid", "integer");
        
        values.put("organizingdistrictid", "KNZB-KRING-LANDELIJK");
        types.put("organizingdistrictid", "string");
        
        values.put("date", new Date(1501583644));
        types.put("date", "date");
        
        values.put("starttime", new Date(39604));
        types.put("starttime", "clocktime");
        
        values.put("sortorder", 15.2F);
        types.put("sortorder", "float");
        
        values.put("played", true);
        types.put("played", "boolean");
    
        return ReplicationFactory.createReplicationMessage("2.27.89433", new Date().getTime(),
                Operation.UPDATE, pks, types, values, Collections.emptyMap(),
                Collections.emptyMap(), Optional.empty());
    }

}
