package com.dexels.navajo.entity;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.Key;


public class TestEntity {

	//static Message entity;
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
		keyProp.setExtends("navajo://Activity/ActivityId");
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
		
		Entity e1 = manager.addEntity(new Entity(entity, manager));
		Entity e2 = manager.addEntity(new Entity(activity, manager));
		e1.addSuperEntity(e2);
		e1.activate();
		e2.activate();
		
	}
	
	@Test
	public void testEntityGetKeySize() throws Exception {
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Assert.assertEquals(3, e.getKeys().size());
		System.err.println("AFTER EXTEND: ******************************************* ");
		e.getMessage().write(System.err);
	}
	
	@Test
	public void testEntityGetKeyMessage() throws Exception {
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Set<Key> keys = e.getKeys();
		int found = 3;
		for ( Key k : keys ) {
			Navajo m = k.generateRequestMessage();
			m.write(System.err);
			
			Assert.assertNotNull(m.getMessage("MyEntity"));
			
			if ( m.getMessage("MyEntity").getProperty("MatchId") != null ) {
				found--;
				Assert.assertEquals(1, m.getMessage("MyEntity").getAllProperties().size());
			} else if ( m.getProperty("/MyEntity/_id") != null ) {
				found--;
				Assert.assertEquals(1, m.getMessage("MyEntity").getAllProperties().size());
			} else if ( m.getProperty("/MyEntity/ExternalMatchId") != null ) {
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
		HashSet<Property> matchingProperties = new HashSet<Property>();
		matchingProperties.add(f.createProperty(p_n, "MatchId", Property.INTEGER_PROPERTY, "", 0, "", ""));
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Key k = e.getKey(matchingProperties);
		Assert.assertNotNull(k);
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/MatchId"));
		
	}
	
	@Test 
	public void testMatchKeyByPropertySet2() throws Exception {
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo p_n = f.createNavajo();
		HashSet<Property> matchingProperties = new HashSet<Property>();
		matchingProperties.add(f.createProperty(p_n, "MatchId", Property.STRING_PROPERTY, "", 0, "", ""));
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Key k = e.getKey(matchingProperties);
		Assert.assertNull(k);
	}
	
	@Test 
	public void testMatchKeyByPropertySet3() throws Exception {
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo p_n = f.createNavajo();
		HashSet<Property> matchingProperties = new HashSet<Property>();
		matchingProperties.add(f.createProperty(p_n, "MatchId", Property.INTEGER_PROPERTY, "", 0, "", ""));
		matchingProperties.add(f.createProperty(p_n, "Irrelevant", Property.INTEGER_PROPERTY, "", 0, "", ""));
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Key k = e.getKey(matchingProperties);
		Assert.assertNotNull(k);
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/MatchId"));
	}
	
	@Test 
	public void testMatchKeyByPropertySet4() throws Exception {
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo p_n = f.createNavajo();
		HashSet<Property> matchingProperties = new HashSet<Property>();
		matchingProperties.add(f.createProperty(p_n, "_id", Property.STRING_PROPERTY, "", 0, "", ""));
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Key k = e.getKey(matchingProperties);
		Assert.assertNotNull(k);
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/_id"));
		
	}
	
	@Test 
	public void testMatchKeyByPropertySet5() throws Exception {
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo p_n = f.createNavajo();
		HashSet<Property> matchingProperties = new HashSet<Property>();
		matchingProperties.add(f.createProperty(p_n, "SeasonId", Property.STRING_PROPERTY, "", 0, "", ""));
		matchingProperties.add(f.createProperty(p_n, "ExternalMatchId", Property.STRING_PROPERTY, "", 0, "", ""));
		matchingProperties.add(f.createProperty(p_n, "OrganizingDistrictId", Property.STRING_PROPERTY, "", 0, "", ""));
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Key k = e.getKey(matchingProperties);
		Assert.assertNotNull(k);
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/SeasonId"));
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/ExternalMatchId"));
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/OrganizingDistrictId"));
		
	}
	
	@Test 
	public void testMatchKeyById() throws Exception {
		
		Entity e = manager.getEntity("MyEntity");
		e.activate();
		Key k = e.getKey("ALT");
		Assert.assertNotNull(k);
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/SeasonId"));
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/ExternalMatchId"));
		Assert.assertNotNull(k.generateRequestMessage().getProperty("/MyEntity/OrganizingDistrictId"));
		
	}
	
	@Test
	public void testSetMessageChangedSuperEntity() throws Exception {
		
		Entity m = manager.getEntity("MyEntity");
		m.activate();
		Assert.assertEquals(1, m.getSuperEntities().size());
		
		m.getMessage().setExtends("");
		m.getMessage().getProperty("MatchId").setExtends("");
		
		System.err.println("**************************************************");
		m.getMessage().write(System.err);
		System.err.println("**************************************************");
		
		m.setMessage(m.getMessage());
		
		Assert.assertEquals(0, m.getSuperEntities().size());
		
	}
	
	@Test
	public void testSetMessage() throws Exception {
		
		System.err.println("=================================================================================");
		Entity a = manager.getEntity("Activity");
		a.activate();
		Entity m = manager.getEntity("MyEntity");
		m.activate();
		Assert.assertEquals(1, m.getSuperEntities().size());
		
		//  Create new Activity Message
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
		a.setMessage(activity);
		
//		m = manager.getEntity("MyEntity");
//		m.activate();
		m.getMessage().write(System.err);
		
//		for ( Key k : m.getKeys() ) {
//			System.err.println("KEY:");
//			k.generateRequestMessage().write(System.err);
//		}
//		System.err.println("=================================================================================");
		
		Assert.assertEquals(1, m.getSuperEntities().size());
		Assert.assertEquals(4, m.getKeys().size());
	}
}
