package com.dexels.navajo.tipi.actions;

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

public class TipiUpdateAllExpressions extends TipiAction {
	
	private int refreshCount = 0;
	
	public TipiUpdateAllExpressions() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
		refreshCount = 0;
		Operand to = getEvaluatedParameter("path", event);
		if (to == null) {
			throw new TipiException("Null evaluation in TipiUpdateExpressions");
		}
		System.err.println("Refreshing");
		TipiDataComponent toData = (TipiDataComponent) to.value;
		doRefresh(toData);
	
	}

	private void doRefresh(TipiDataComponent toData) {
//		System.err.println("Starting refresh");
		Navajo n = toData.getNearestNavajo();
		ArrayList<Message> al;
//		try {
//			al = n.getAllMessages();
//			for (int i = 0; i < al.size(); i++) {
//				Message current = al.get(i);
//				try {
//					current.refreshExpression();
//				} catch (ExpressionChangedException e) {
////					e.printStackTrace();
////					System.err.println("Change detected");
//					refreshCount++;
//					this.doRefresh(toData);
//					return;
//				}
//			}
//
//		} catch (NavajoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			List<Property> pp =n.refreshExpression();
			Set<Property> aa = new HashSet<Property>(pp);
			System.err.println("Changes detected: "+pp.size()+" \n "+pp);
			System.err.println("Unique detected: "+aa.size()+" \n "+aa);
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
