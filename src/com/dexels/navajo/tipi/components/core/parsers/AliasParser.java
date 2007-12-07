package com.dexels.navajo.tipi.components.core.parsers;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class AliasParser extends BaseTipiParser {
	public AliasParser() {
	}

	private String findAlias(TipiComponent current, String expression) {
		if(current==null) {
			return null;
		}
		String alias = current.getAlias(expression);
		if(alias!=null) {
			return alias;
		}
		return findAlias(current.getTipiParent(), expression);
		
	}
	
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		String alias = findAlias(source, expression);
		if(alias==null) {
			throw new RuntimeException("Error. Can not find alias: "+expression);
		}
		System.err.println("Found alias: "+alias);
		try {
			Operand evaluate = myContext.evaluate(alias, source, event);
			System.err.println("Evaluate: "+evaluate.value);
			return evaluate.value;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}

}
