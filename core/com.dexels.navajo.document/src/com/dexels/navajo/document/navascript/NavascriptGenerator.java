package com.dexels.navajo.document.navascript;

import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;

public class NavascriptGenerator {

	public static void main(String [] args) {
		
		NavascriptTag navascript = new NavascriptTag();
		
		navascript
			.addParam(null, "MyParam").addExpression("1 == 2", "60").addExpression(null, "78");
		
		MapTag map = navascript.addMap(null, "sqlquery");
		
		navascript.addMap(null, "sqlquery");
		
		map.addParam(null, "AnotherParam")
			.addExpression("1 == 2", "'AAP'")
			.addExpression("IsEmpty([/Fluit/Ketel]", "'MIES'")
			.addExpression(null, "'NOOT'");
		
		map.addField(null, "transactionContext")
			.addExpression("?[/Transaction/TransactionContext]", "[/Transaction/TransactionContext]")
			.addExpression(null, "23124343");
		
		navascript.addMessage("SomeMessage", null)
			.addProperty(null, "LastName", "string")
				.addExpression(null, "'Bergman'");
		
		map.addMessage("ResultSet", null)
			.addMap(null, "resultSet", map, false)
				.addProperty(null, "FirstName", "string")
					.addExpression(null, "$columnValue('firstname')");
		
		navascript.write(System.out);
		
	}
}
