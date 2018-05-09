package com.dexels.navajo.entity;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class TestEntity {

    // static Message entity;
    static EntityManager manager;

    @Before
    public void setup() throws Exception {

        manager = new EntityManager();

        NavajoFactory f = NavajoFactory.getInstance();

        Navajo n = f.createNavajo();
        Message entity = f.createMessage(n, "MyEntity");
        entity.setExtends("navajo://Activity");
        n.addMessage(entity);

        Property keyProp = f.createProperty(n, "MatchId", Property.INTEGER_PROPERTY, "", 0, "", "");
        keyProp.setKey("true,auto");
        entity.addProperty(keyProp);

        Property keyProp2 = f.createProperty(n, "_id", Property.STRING_PROPERTY, "", 0, "", "");
        keyProp2.setKey("true,auto");
        entity.addProperty(keyProp2);

        entity.addProperty(f.createProperty(n, "CalendarDate", Property.DATE_PROPERTY, "", 0, "", ""));
        entity.addProperty(f.createProperty(n, "HomeOrganizationId", Property.STRING_PROPERTY, "", 0, "", ""));
        entity.addProperty(f.createProperty(n, "AwayOrganizationId", Property.STRING_PROPERTY, "", 0, "", ""));

        Property keyProp1_ALT = f.createProperty(n, "ExternalMatchId", Property.STRING_PROPERTY, "", 0, "", "");
        keyProp1_ALT.setKey("true,auto,id=ALT");
        entity.addProperty(keyProp1_ALT);
        Property keyProp2_ALT = f.createProperty(n, "SeasonId", Property.STRING_PROPERTY, "", 0, "", "");
        keyProp2_ALT.setKey("true,id=ALT");
        entity.addProperty(keyProp2_ALT);
        Property keyProp3_ALT = f.createProperty(n, "OrganizingDistrictId", Property.STRING_PROPERTY, "", 0, "", "");
        keyProp3_ALT.setKey("true,id=ALT");
        entity.addProperty(keyProp3_ALT);

        entity.write(System.err);

        // Create Activity entity.

        Message activity = f.createMessage(n, "Activity");
        activity.addProperty(f.createProperty(n, "ActivityType", Property.STRING_PROPERTY, "", 0, "", ""));
        Property keyPropAct = f.createProperty(n, "ActivityId", Property.INTEGER_PROPERTY, "", 0, "", "");
        keyPropAct.setKey("true,auto");
        activity.addProperty(keyPropAct);

        activity.write(System.err);

        Entity e1 = new Entity(entity, manager);
        e1.entityName = "MyEntity";
        e1.messageName = "MyEntity";

        Entity e2 = new Entity(activity, manager);
        e2.entityName = "Activity";
        e2.messageName = "Activity";

        manager.registerEntity(e1);
        manager.registerEntity(e2);

        e1.addSuperEntity(e2, null);
        e1.startEntity();
        e2.startEntity();

    }

    @Test
    public void testEntityGetKeySize() throws Exception {
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Assert.assertEquals(4, e.getKeys(Entity.DEFAULT_VERSION).size());
        System.err.println("AFTER EXTEND: ******************************************* ");
        e.getMessage(Entity.DEFAULT_VERSION).write(System.err);
    }

    @Test
    public void testEntityGetKeyMessage() throws Exception {
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Set<Key> keys = e.getKeys(Entity.DEFAULT_VERSION);
        int found = 3;
        for (Key k : keys) {
            Navajo m = k.generateRequestMessage();
            m.write(System.err);

            Assert.assertNotNull(m.getMessage("MyEntity"));

            if (m.getMessage("MyEntity").getProperty("MatchId") != null) {
                found--;
                Assert.assertEquals(1, m.getMessage("MyEntity").getAllProperties().size());
            } else if (m.getProperty("/MyEntity/_id") != null) {
                found--;
                Assert.assertEquals(1, m.getMessage("MyEntity").getAllProperties().size());
            } else if (m.getProperty("/MyEntity/ExternalMatchId") != null) {
                Assert.assertNotNull(m.getProperty("/MyEntity/SeasonId"));
                Assert.assertNotNull(m.getProperty("/MyEntity/OrganizingDistrictId"));
                found--;
            }
        }
        Assert.assertEquals(0, found);
    }

    @Test
    public void testMatchKeyByPropertySet1() throws Exception {
        NavajoFactory f = NavajoFactory.getInstance();
        Navajo p_n = f.createNavajo();
        Message m = f.createMessage(p_n, "MyEntity");
        Property p = f.createProperty(p_n, "MatchId", Property.INTEGER_PROPERTY, "100", 0, "", "");
        m.addProperty(p);
        p_n.addMessage(m);
        
        HashSet<Property> matchingProperties = new HashSet<Property>();
        matchingProperties.add(p);
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Key k = e.getKey(matchingProperties, Entity.DEFAULT_VERSION);
        Assert.assertNotNull(k);
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/MatchId"));

    }

    @Test
    public void testMatchKeyByPropertySet2() throws Exception {
        NavajoFactory f = NavajoFactory.getInstance();
        Navajo p_n = f.createNavajo();
        Message m = f.createMessage(p_n, "MyEntity");
        Property p = f.createProperty(p_n, "MatchId", Property.STRING_PROPERTY, "", 0, "", "");
        m.addProperty(p);
        p_n.addMessage(m);
        
        HashSet<Property> matchingProperties = new HashSet<Property>();
        matchingProperties.add(p);
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Key k = e.getKey(matchingProperties, Entity.DEFAULT_VERSION);
        Assert.assertNull(k);
    }

    @Test
    public void testMatchKeyByPropertySet3() throws Exception {
        NavajoFactory f = NavajoFactory.getInstance();
        Navajo p_n = f.createNavajo();
        Message m = f.createMessage(p_n, "MyEntity");
        Property p1 = f.createProperty(p_n, "MatchId", Property.INTEGER_PROPERTY, "100", 0, "", "");
        m.addProperty(p1);
        Property p2 = f.createProperty(p_n, "Irrelevant", Property.INTEGER_PROPERTY, "5", 0, "", "");
        m.addProperty(p2);
        p_n.addMessage(m);
        
        HashSet<Property> matchingProperties = new HashSet<Property>();
        matchingProperties.add(p1);
        matchingProperties.add(p2);
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Key k = e.getKey(matchingProperties, Entity.DEFAULT_VERSION);
        Assert.assertNotNull(k);
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/MatchId"));
    }

    @Test
    public void testMatchKeyByPropertySet4() throws Exception {
        NavajoFactory f = NavajoFactory.getInstance();
        Navajo p_n = f.createNavajo();
        Message m = f.createMessage(p_n, "MyEntity");
        Property p = f.createProperty(p_n, "_id", Property.STRING_PROPERTY, "12", 0, "", "");
        m.addProperty(p);
        p_n.addMessage(m);
        
        HashSet<Property> matchingProperties = new HashSet<Property>();
        matchingProperties.add(p);
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Key k = e.getKey(matchingProperties, Entity.DEFAULT_VERSION);
        Assert.assertNotNull(k);
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/_id"));

    }

    @Test
    public void testMatchKeyByPropertySet5() throws Exception {
        NavajoFactory f = NavajoFactory.getInstance();
        Navajo p_n = f.createNavajo();
        Message m = f.createMessage(p_n, "MyEntity");
        Property p1 = f.createProperty(p_n, "SeasonId", Property.STRING_PROPERTY, "1", 0, "", "");
        Property p2 = f.createProperty(p_n, "ExternalMatchId", Property.STRING_PROPERTY, "2", 0, "", "");
        Property p3 = f.createProperty(p_n, "OrganizingDistrictId", Property.STRING_PROPERTY, "3", 0, "", "");
        m.addProperty(p1);
        m.addProperty(p2);
        m.addProperty(p3);
        p_n.addMessage(m);
        
        HashSet<Property> matchingProperties = new HashSet<Property>();
        matchingProperties.add(p1);
        matchingProperties.add(p2);
        matchingProperties.add(p3);
        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Key k = e.getKey(matchingProperties, Entity.DEFAULT_VERSION);
        Assert.assertNotNull(k);
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/SeasonId"));
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/ExternalMatchId"));
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/OrganizingDistrictId"));

    }

    @Test
    public void testMatchKeyById() throws Exception {

        Entity e = manager.getEntity("MyEntity");
        e.startEntity();
        Key k = e.getKey("ALT", Entity.DEFAULT_VERSION);
        Assert.assertNotNull(k);
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/SeasonId"));
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/ExternalMatchId"));
        Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/OrganizingDistrictId"));

    }

    @Test
    public void testSetMessageChangedSuperEntity() throws Exception {

        Entity m = manager.getEntity("MyEntity");
        m.startEntity();
        Assert.assertEquals(1, m.getSuperEntities().size());

        m.getMessage(Entity.DEFAULT_VERSION).setExtends("");
        m.getMessage(Entity.DEFAULT_VERSION).getProperty("MatchId").setExtends("");

        System.err.println("**************************************************");
        m.getMessage(Entity.DEFAULT_VERSION).write(System.err);
        System.err.println("**************************************************");

        Assert.assertEquals(0, m.getSuperEntities().size());

    }

    @Test
    public void testSetMessage() throws Exception {

        System.err.println("=================================================================================");
        Entity a = manager.getEntity("Activity");
        Entity m = manager.getEntity("MyEntity");
        Assert.assertEquals(1, m.getSuperEntities().size());

        // Create new Activity Message
        NavajoFactory f = NavajoFactory.getInstance();
        Navajo n = f.createNavajo();
        Message activity = f.createMessage(n, "Activity");
        activity.addProperty(f.createProperty(n, "ActivityType", Property.STRING_PROPERTY, "", 0, "", ""));
        activity.addProperty(f.createProperty(n, "ActivityLocation", Property.STRING_PROPERTY, "", 0, "", ""));
        Property keyPropAct = f.createProperty(n, "ActivityId", Property.INTEGER_PROPERTY, "", 0, "", "");
        keyPropAct.setKey("true,auto");
        activity.addProperty(keyPropAct);

        Property keyPropActAlt = f.createProperty(n, "AltActivityId", Property.INTEGER_PROPERTY, "", 0, "", "");
        keyPropActAlt.setKey("true,auto");
        activity.addProperty(keyPropActAlt);

        System.err.println("INJECTING NEW MESSAGE!!!!!!!");

        // m = manager.getEntity("MyEntity");
        // m.activate();
        m.getMessage(Entity.DEFAULT_VERSION).write(System.err);

        // for ( Key k : m.getKeys() ) {
        // System.err.println("KEY:");
        // k.generateRequestMessage().write(System.err);
        // }
        // System.err.println("=================================================================================");

        Assert.assertEquals(1, m.getSuperEntities().size());
        Assert.assertEquals(5, m.getKeys(Entity.DEFAULT_VERSION).size());
    }
}
