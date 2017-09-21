/* Generated By:JJTree&JavaCC: Do not edit this line. ASTForAllNode.java */

package com.dexels.navajo.parser.compiled;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTForAllNode extends SimpleNode {

    String functionName;
//    Navajo doc;
//    Message parentMsg;
//    MappableTreeNode mapObject;
    private Access access;

    private final static Logger logger = LoggerFactory.getLogger(ASTForAllNode.class);

    public ASTForAllNode(int id) {
        super(id);
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }


	@Override
	public ContextExpression interpretToLambda() {
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return false;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) throws TMLExpressionException {
				ContextExpression a = jjtGetChild(0).interpretToLambda();
				ContextExpression b = jjtGetChild(1).interpretToLambda();
				return interpret(doc,parentMsg,parentParamMsg,parentSel,selectionOption,mapNode,tipiLink,access, a,b);
			}
		};
	}
	
    /**
     * FORALL(<EXPRESSION>, `[$x] <EXPRESSION>`) E.G.
     * FORALL([/ClubMembership/ClubMemberships/ClubIdentifier],
     * `CheckRelatieCode([$x])`)
     * 
     * @return
     * @throws TMLExpressionException
     */
    public final Object interpret(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access, ContextExpression a,ContextExpression b) throws TMLExpressionException {

        boolean matchAll = true;

        if (functionName.equals("FORALL"))
            matchAll = true;
        else
            matchAll = false;

        String msgList = (String) a.apply(doc, parentMsg, parentParamMsg, parentSel, selectionOption, mapNode, tipiLink, access);
        System.err.println("MsgList: "+msgList);
        try {
            List<Message> list = null;

            if (parentMsg == null) {
                list = doc.getMessages(msgList);
            } else {
                list = parentMsg.getMessages(msgList);
            }

            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);

                parentMsg = (Message) o;

                // ignore definition messages in the evaluation
                if (parentMsg.getType().equals(Message.MSG_TYPE_DEFINITION))
                    continue;

//                String expr = (String) b;

                Object apply = b.apply(doc, parentMsg, parentParamMsg, parentSel, selectionOption, mapNode, tipiLink, access);
                System.err.println(">><> "+apply);
				boolean result = (Boolean)apply;

                if ((!(result)) && matchAll)
                    return Boolean.FALSE;
                if ((result) && !matchAll)
                    return Boolean.TRUE;
            }

        } catch (NavajoException ne) {
            logger.error("Error: ", ne);
            throw new TMLExpressionException("Invalid expression in FORALL construct: \n" + ne.getMessage());
        }

        if (matchAll)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

}
