/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.SystemException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class ForAll extends FunctionInterface {
	
	private final static Logger logger = LoggerFactory.getLogger(ForAll.class);
	
	public ForAll() {
	}
	@Override
	public String remarks() {
		return "Evaluates a condition (expression evaluating to a boolean) under all the messages of an array message. It will return a 'AND' of all the results";
	}
	@Override
	public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		/**@todo Implement this com.dexels.navajo.parser.FunctionInterface abstract method*/
        Message arrayMessage = null;
        String messagePath = null;
	    if (getOperand(0) instanceof Message) {
	        arrayMessage = (Message)getOperand(0);
        } else {
            messagePath = (String) getOperand(0);
        }
		String expression = (String) getOperand(1);
		String filter = null;
		if (getOperands().size() > 2) {
			filter = (String) getOperand(2);
		}
		Message parent = getCurrentMessage();
		Navajo doc = getNavajo();
        
		//try {
		try {
            List<Message> arrayMsg = null;
            
            if (arrayMessage != null) {
                arrayMsg = arrayMessage.getAllMessages();
            } else {
                arrayMsg = (parent != null ? parent.getMessages(messagePath) :
                    doc.getMessages(messagePath));
            }
			if (arrayMsg == null) {
				throw new TMLExpressionException(this,
						"Empty or non existing array message: " +
						messagePath);
			}
			
			for (int i = 0; i < arrayMsg.size(); i++) {
				Message current = arrayMsg.get(i);
				try {
					boolean evaluate = (filter != null ?
							Condition.evaluate(filter, doc, null, current,getAccess()) : true);
					if (evaluate) {
						
						Operand result = Expression.evaluate(expression, doc, null, current);
						if (result==null) {
							throw new TMLExpressionException(this,"Error evaluating expression: "+expression+" in message: "+current.getFullMessageName());
						}
						if (result.value==null) {
							throw new TMLExpressionException(this,"Error evaluating expression: "+expression+" in message: "+current.getFullMessageName());
						}
						String res2 =""+result.value;
						Operand result2 = Expression.evaluate(res2, doc, null, current);
						if (result2==null) {
							throw new TMLExpressionException(this,"Error evaluating expression: "+res2+" in message: "+current.getFullMessageName());
						}
						if (result2.value==null) {
							throw new TMLExpressionException(this,"Error evaluating expression: "+res2+" in message: "+current.getFullMessageName());
						}
						boolean res = ((Boolean)result2.value).booleanValue();
						if (!res) {
							return Boolean.FALSE;
						}
					}
				} catch (SystemException ex) {
					logger.error("Error: ", ex);
				}
			}
		} catch (NavajoException ex) {
			throw new TMLExpressionException(this,"Error evaluating message path");
		}
		
		return Boolean.TRUE;
	}
	@Override
	public String usage() {
		return "ForAll('ArrayMessage','Expression'[, 'Filter']) *ArrayMessage can be both a path and a message";
	}
	
}
