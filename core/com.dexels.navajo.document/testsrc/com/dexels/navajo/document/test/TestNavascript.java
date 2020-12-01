package com.dexels.navajo.document.test;

import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.Field;
import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.Param;
import com.dexels.navajo.document.Property;

public class TestNavascript {

	public static void main(String [] args) throws Exception {
		Navascript navascript = NavajoFactory.getInstance().createNavaScript();
		Param param = NavajoFactory.getInstance().createParam(navascript, "", "MyFirstParam");
		navascript.addParam(param);
		
		ExpressionTag expression1 = NavajoFactory.getInstance().createExpression(navascript, "true", "[/Binary/Data]");
		ExpressionTag expression2 = NavajoFactory.getInstance().createExpression(navascript, null, "null");
		param.addExpression(expression1);
		param.addExpression(expression2);
		
		MapAdapter map1 = NavajoFactory.getInstance().createMapObject(navascript, "sqlquery", null);
		navascript.addMap(map1);
		map1.addAttributeNameValue("datasource", "'sportlinkkernel'");
		
		Field field1 = NavajoFactory.getInstance().createField(navascript, null, "transactionContext");
		map1.addField(field1);
		
		Field field2 = NavajoFactory.getInstance().createField(navascript, null, "query");
		map1.addField(field2);
		ExpressionTag expression3 = NavajoFactory.getInstance().createExpression(navascript, null, "'SELECT * FROM PERSON'");
		field2.addExpression(expression3);
		
		ExpressionTag field1Expression = NavajoFactory.getInstance().createExpression(navascript, null, "28932909");
		field1.addExpression(field1Expression);
		
		Message result = NavajoFactory.getInstance().createMessage(navascript, "Results");
		map1.addMessage(result);
		
		MapAdapter ref = NavajoFactory.getInstance().createMapRef(navascript, "resultSet", null, "true", map1);
		result.addMapRef(ref);
		
		Property p1 = NavajoFactory.getInstance().createProperty(navascript, "DocumentId", "integer", null, 0, "", "out");
		ref.addProperty(p1);
		
		ExpressionTag p1ex = NavajoFactory.getInstance().createExpression(navascript, null, "$columnValue('id')");
		p1.addExpression(p1ex);
		
		navascript.write(System.out);
	}

}
