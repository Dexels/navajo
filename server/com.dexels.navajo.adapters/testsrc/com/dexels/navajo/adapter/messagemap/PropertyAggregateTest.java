package com.dexels.navajo.adapter.messagemap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.adapter.messagemap.PropertyAggregate.Aggregate;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class PropertyAggregateTest {

	private void addProperty(PropertyAggregate pa, Property agg, List<Property> p) {
		Map<String,Object> group = new TreeMap<String, Object>();
		for ( int i = 0; i < p.size(); i++ ) {
			Property prop = p.get(i);
			group.put(prop.getName(), prop.getValue());
		}
		pa.addProperty(agg, group);
	}

	private void addSinglePropertyGroup(PropertyAggregate pa, Property agg, Property group) {
		List<Property> groupList = new ArrayList<Property>();
		groupList.add(group);
		addProperty(pa, agg, groupList);
	}


	@Test
	public void test1() {

		Navajo n = NavajoFactory.getInstance().createNavajo();

		Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "200", 0, "", "out");

		Property p3 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "Book", 0, "", "out");
		Property p4 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "5", 0, "", "out");

		Property p5 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property p6 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "100", 0, "", "out");

		Property p7 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "Book", 0, "", "out");
		Property p8 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "20", 0, "", "out");

		Property p9 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property p10 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "300", 0, "", "out");


		PropertyAggregate pa =  new PropertyAggregate();
		addSinglePropertyGroup(pa, p2, p1);
		addSinglePropertyGroup(pa, p4, p3);
		addSinglePropertyGroup(pa, p6, p5);
		addSinglePropertyGroup(pa, p8, p7);
		addSinglePropertyGroup(pa, p10, p9);

		Map<String,Object> groupDef = new TreeMap<String,Object>();
		groupDef.put("Product", "PC");
		Aggregate ag = pa.getAggregate(groupDef);

		Assert.assertEquals(3, ag.getCount());
		Assert.assertEquals((double) 200.0, ag.getAvg(), 0.0);
		Assert.assertEquals((int) 600, (int) ag.getSum());

		groupDef.clear();
		groupDef.put("Product", "Book");
		ag = pa.getAggregate(groupDef);
		Assert.assertEquals(2, ag.getCount());
		Assert.assertEquals((double)12.5, ag.getAvg(), 0.0);
		Assert.assertEquals((int) 25, (int) ag.getSum());
	}

	@Test
	public void test2() {


		Navajo n = NavajoFactory.getInstance().createNavajo();

		Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
		Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "200", 0, "", "out");

		Property p3 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "Book", 0, "", "out");
		Property p3a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Novel", 0, "", "out");
		Property p4 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "5", 0, "", "out");

		Property p5 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property p5a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
		Property p6 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "100", 0, "", "out");

		Property p7 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "Book", 0, "", "out");
		Property p7a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Novel", 0, "", "out");
		Property p8 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "20", 0, "", "out");

		Property p9 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property p9a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
		Property p10 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, "300", 0, "", "out");

		PropertyAggregate pa =  new PropertyAggregate();
		List<Property> group = new ArrayList<Property>();
		// 1
		group.add(p1);
		group.add(p1a);
		addProperty(pa, p2, group);
		// 2
		group.add(p3);
		group.add(p3a);
		addProperty(pa, p4, group);
		// 3
		group.add(p5);
		group.add(p5a);
		addProperty(pa, p6, group);
		// 3
		group.add(p7);
		group.add(p7a);
		addProperty(pa, p8, group);
		// 3
		group.add(p9);
		group.add(p9a);
		addProperty(pa, p10, group);
		
		
		Map<String,Object> groupDef = new TreeMap<String,Object>();
		groupDef.put("Sub", "Desktop");
		groupDef.put("Product", "PC");
		Aggregate ag = pa.getAggregate(groupDef);

		Assert.assertEquals(2, ag.getCount());
		Assert.assertEquals((double) 200.0, ag.getAvg(), 0.0);
		Assert.assertEquals((int) 400, (int) ag.getSum());
		
		groupDef.clear();
		groupDef.put("Sub", "Novel");
		groupDef.put("Product", "Book");
		ag = pa.getAggregate(groupDef);
		Assert.assertEquals(2, ag.getCount());
		Assert.assertEquals((double) 12.5, ag.getAvg(), 0.0);
		Assert.assertEquals((int) 25, (int) ag.getSum());
	}
}
