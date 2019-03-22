package com.dexels.navajo.adapter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dexels.navajo.document.*;
import com.dexels.navajo.script.api.*;

public class PropertyMapTest {
	
	// test requirement of a message in Access
	@Test( expected = UserException.class )
	public void testMessageRequired() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Access a = new Access();
		a.setOutputDoc(n);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		// do the magic
		pm.store();
		
		// verification is that we should get an exception
	}

	// create a property with different value and type when a property with the same name already exists (with removeExisting is false) -> exception
	@Test( expected = UserException.class )
	public void testPropertyAlreadyExists() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		Property p = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, "PC", 0, "", Property.DIR_OUT);
		m.addProperty(p);
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		// do the magic
		pm.store();
		
		// verification is that we should get an exception
	}


	// create a property with different value and type when a property with the same name already exists (with removeExisting is true)
	@Test
	public void testPropertyIsOverwritten() throws Exception
	{
		String description = "Marte is crazy";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.INTEGER_PROPERTY, "8", 0, description, "out");
		m.addProperty(p2);
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getType(), Property.STRING_PROPERTY );
		assertEquals( p.getTypedValue() , value );
		assertEquals( p.getDirection(), Property.DIR_OUT );
		assertEquals( p.getDescription(), description );
	}

	// create a string property "Property" with value Marte 
	@Test
	public void testCreateStringProperty() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getType(), Property.STRING_PROPERTY );
		assertEquals( p.getTypedValue() , value );

	}
	
	// create a date property with value 04-10-1981
	@Test
	public void testDateProperty() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = new SimpleDateFormat("yyyy-MM-dd").parseObject("1981-10-04");
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.DATE_PROPERTY);
		pm.setCurrentValue( value );
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getType(), Property.DATE_PROPERTY );
		assertEquals( p.getTypedValue() , value );
	}

	// create a selection property where all options are defined in the optionList variable
	// create a selection property with one value selected (single selection type)
	@Test
	public void testCreateSingleSelectionPropertyFromOptionList() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.SELECTION_PROPERTY);
		// Add options, option 1
		pm.setOptionName("Martin");
		pm.setOptionValue("1");
		pm.setOptionSelected(false);
		// option 2
		pm.setOptionName("Carlo");
		pm.setOptionValue("2");
		pm.setOptionSelected(false);		
		// option 3
		pm.setOptionName("Robin");
		pm.setOptionValue("3");
		pm.setOptionSelected(true);
		
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getAllSelections().size(), 3 );
		assertNotNull( p.getAllSelectedSelections() );
		assertEquals( p.getAllSelectedSelections().size(), 1 );
		assertNotNull( p.getSelected() );
		assertEquals( p.getSelected().getValue(), "3");

	}
	
	// create a selection property with multiple values selected
	@Test
	public void testCreateMultipleSelectionPropertyFromOptionList() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.SELECTION_PROPERTY);
		pm.setMultiple(true);
		// Add options, option 1
		pm.setOptionName("Martin");
		pm.setOptionValue("1");
		pm.setOptionSelected(true);
		// option 2
		pm.setOptionName("Carlo");
		pm.setOptionValue("2");
		pm.setOptionSelected(false);		
		// option 3
		pm.setOptionName("Robin");
		pm.setOptionValue("3");
		pm.setOptionSelected(true);
		
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertNotNull( p.getAllSelectedSelections() );
		assertEquals( p.getAllSelections().size(), 3 );
		assertEquals( p.getAllSelectedSelections().size(), 2);
		
		List<String> selectedValues = new ArrayList<String>();
		for(Selection sel : p.getAllSelectedSelections())
		{
			selectedValues.add(sel.getValue());
		}
		assertArrayEquals(selectedValues.toArray(), new String[] {"1", "3"} );

	}
	
	// create a property with subtype = required
	@Test
	public void testCreateSubTypedProperty() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		String subtype = "required=true";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setSubtype(subtype);
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getSubType(), subtype );

	}
	
	// create a property with length = 32
	@Test
	public void testCreatePropertyWithLength() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		int length = 32; 
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setLength(length);
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
	    assertEquals( p.getLength(), length );
	}
	
	// create a property with empty length
	@Test
	public void testCreatePropertyWithEmptyLength() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getLength() , -1 );

	}
	
	// create a property with description = 'Marte is crazy'
	@Test
	public void testCreatePropertyWithDescription() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		String description = "Marte is crazy";
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDescription(description);
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getDescription() , description );

	}
	
	// create a property with direction out
	@Test
	public void testCreatePropertyWithDirectionOut() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDirection(Property.DIR_OUT);
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getDirection() , Property.DIR_OUT );

	}
	
	// create a property with direction in
	@Test
	public void testCreatePropertyWithDirectionIn() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDirection(Property.DIR_IN);
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertEquals( p.getDirection() , Property.DIR_IN );

	}
	
	
	// create a property one message up (succesfull)
	@Test
	public void testCreatePropertyOneMessageUp() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "SuperMessage");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Message m2 = NavajoFactory.getInstance().createMessage(n, "Message");
		m2.setType(Message.MSG_TYPE_SIMPLE);
		m.addMessage(m2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m2);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("../Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDirection(Property.DIR_IN);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("SuperMessage").getProperty("Property") );
		assertNull( n.getMessage("SuperMessage").getMessage("Message").getProperty("Property") );

	}
	
	// create a property one message up but there is no higher message (exception) - no exception, because it is actually allowed!
	@Test
	public void testCreatePropertyOneMessageUpWithFailure() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		n.addMessage(m);
		m.setType(Message.MSG_TYPE_SIMPLE);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("../Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDirection(Property.DIR_IN);
		// do the magic
		pm.store();

		// verification
		assertNull( n.getMessage("Message").getProperty("Property") );		
//		assertNull( n.getProperty("Property") );

	}
	
	
	// create a property one message up while the same propertyname already exists in the current message. Both need to exist afterwards
	@Test
	public void testCreatePropertyOneMessageUpWhileOtherExists() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "SuperMessage");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Message m2 = NavajoFactory.getInstance().createMessage(n, "Message");
		m2.setType(Message.MSG_TYPE_SIMPLE);
		String otherValue = "Carlo";
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, otherValue, -1, "Marte", Property.DIR_OUT);
		m2.addProperty(p2);
		m.addMessage(m2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m2);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("../Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDirection(Property.DIR_IN);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("SuperMessage").getProperty("Property") );
		assertNotNull( n.getMessage("SuperMessage").getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("SuperMessage").getProperty("Property").getTypedValue(), value );
		assertEquals( n.getMessage("SuperMessage").getMessage("Message").getProperty("Property").getTypedValue(), otherValue );

	}
	
	// create a property in another message on the same level but not the current one
	@Test
	public void testCreatePropertyOtherMessageSameLevel() throws Exception
	{
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "OtherMessage");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Message m2 = NavajoFactory.getInstance().createMessage(n, "Message");
		m2.setType(Message.MSG_TYPE_SIMPLE);
		n.addMessage(m2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m2);
		
		Object value = "Marte";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setName("../OtherMessage/Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setCurrentValue( value );
		pm.setDirection(Property.DIR_IN);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("OtherMessage").getProperty("Property") );
		assertNull( n.getMessage("Message").getProperty("Property") );

	}
	
	// Overwriting existing property, changing type from String to Int (and its value automatically gets cleared)
	@Test
	public void testOverwritePropertyChangeType() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.INTEGER_PROPERTY);
		pm.setDirection(Property.DIR_OUT);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.INTEGER_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), null );

	}
	
	// Overwrite existing property, changing value (same type)
	@Test
	public void testOverwritePropertyChangeValueSameType() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object otherValue = "Carlo";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setDirection(Property.DIR_OUT);
		pm.setCurrentValue(otherValue);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.STRING_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), otherValue );

	}
	
	// Overwrite existing property, changing value (different type)
	@Test
	public void testOverwritePropertyChangeValueDifferentType() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		Object otherValue = 8;
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.INTEGER_PROPERTY);
		pm.setDirection(Property.DIR_OUT);
		pm.setCurrentValue(otherValue);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.INTEGER_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), otherValue );

	}
	
	
	// Overwrite existing property, from selection to selection but with differing optionlists
	@Test
	public void testOverwritePropertyChangeSelections() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		// Use the propertymap to setup the final bit of the test. The only reason is to avoid using BaseSelectionImpl which is not readily available without changing the manifest
		PropertyMap pm2 = new PropertyMap();
		pm2.load(a);
		// define the specific variables for this test
		pm2.setRemoveExisting(true);
		pm2.setName("Property");
		pm2.setType(Property.SELECTION_PROPERTY);
		// Add options, option 1
		pm2.setOptionName("Tesselschadestraat");
		pm2.setOptionValue("Leiden");
		pm2.setOptionSelected(true);
		// option 2
		pm2.setOptionName("Grasweg");
		pm2.setOptionValue("Amsterdam");
		pm2.setOptionSelected(false);		
		// option 3
		pm2.setOptionName("van Marnixlaan");
		pm2.setOptionValue("Amersfoort");
		pm2.setOptionSelected(false);
		// option 4
		pm2.setOptionName("Cambridgelaan");
		pm2.setOptionValue("Utrecht");
		pm2.setOptionSelected(false);
		
		// do it
		pm2.store();
		// verify setup is correct
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p2 = n.getMessage("Message").getProperty("Property");
		assertNotNull( p2.getSelected() );
		assertEquals( p2.getAllSelections().size(), 4 );
		assertNotNull( p2.getAllSelectedSelections() );
		assertEquals( p2.getAllSelectedSelections().size(), 1 );
		assertEquals( p2.getSelected().getValue(), "Leiden" );
		assertEquals( p2.getCardinality(), Property.CARDINALITY_SINGLE );
		
		// now start the real test
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.SELECTION_PROPERTY);
		// Add options, option 1
		pm.setOptionName("Martin");
		pm.setOptionValue("1");
		pm.setOptionSelected(false);
		// option 2
		pm.setOptionName("Carlo");
		pm.setOptionValue("2");
		pm.setOptionSelected(false);		
		// option 3
		pm.setOptionName("Robin");
		pm.setOptionValue("3");
		pm.setOptionSelected(true);
		
		// do the magic
		pm.store();
		
		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		Property p = n.getMessage("Message").getProperty("Property");
		assertNotNull( p.getSelected() );
		assertEquals( p.getAllSelections().size(), 3 );
		assertNotNull( p.getAllSelectedSelections() );
		assertEquals( p.getAllSelectedSelections().size(), 1 );
		assertEquals( p.getSelected().getValue(), "3" );
		assertEquals( p.getCardinality(), Property.CARDINALITY_SINGLE );

	}
	
	// Overwrite existing property, changing direction from out to in
	@Test
	public void testOverwritePropertyChangeDirection() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setDirection(Property.DIR_IN);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.STRING_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), null );
		assertEquals( n.getMessage("Message").getProperty("Property").getDirection(), Property.DIR_IN );

	}
	
	// Overwrite existing property, changing description
	@Test
	public void testOverwritePropertyChangeDdescription() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		String description = "New description";
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setDirection(Property.DIR_OUT);
		pm.setDescription(description);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.STRING_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), null );
		assertEquals( n.getMessage("Message").getProperty("Property").getDirection(), Property.DIR_OUT );
		assertEquals( n.getMessage("Message").getProperty("Property").getDescription(), description );

	}
	
	
	// Overwrite existing property, changing length
	@Test
	public void testOverwritePropertyChangeLength() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		int length = 34;
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setDirection(Property.DIR_OUT);
		pm.setLength(length);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.STRING_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), null );
		assertEquals( n.getMessage("Message").getProperty("Property").getDirection(), Property.DIR_OUT );
		assertEquals( n.getMessage("Message").getProperty("Property").getLength(), length );

	}
	
	
	// Overwrite existing property, changing subType
	@Test
	public void testOverwritePropertyChangeSubType() throws Exception
	{
		String value = "Marte";
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Message");
		m.setType(Message.MSG_TYPE_SIMPLE);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Property", Property.STRING_PROPERTY, value, -1, "Marte", Property.DIR_OUT);
		m.addProperty(p2);
		n.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(n);
		a.setCurrentOutMessage(m);
		
		String subtype = "required=true"; 
		PropertyMap pm = new PropertyMap();
		pm.load(a);
		// define the specific variables for this test
		pm.setRemoveExisting(true);
		pm.setName("Property");
		pm.setType(Property.STRING_PROPERTY);
		pm.setDirection(Property.DIR_OUT);
		pm.setSubtype(subtype);
		// do the magic
		pm.store();

		// verification
		assertNotNull( n.getMessage("Message").getProperty("Property") );
		assertEquals( n.getMessage("Message").getProperty("Property").getType(), Property.STRING_PROPERTY );
		assertEquals( n.getMessage("Message").getProperty("Property").getTypedValue(), null );
		assertEquals( n.getMessage("Message").getProperty("Property").getDirection(), Property.DIR_OUT );
		assertEquals( n.getMessage("Message").getProperty("Property").getSubType(), subtype );

	}
	
}
