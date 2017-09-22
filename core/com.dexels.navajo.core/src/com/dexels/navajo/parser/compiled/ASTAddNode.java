/* Generated By:JJTree&JavaCC: Do not edit this line. ASTAddNode.java */
package com.dexels.navajo.parser.compiled;


import java.util.ArrayList;

import com.dexels.navajo.parser.Utils;

@SuppressWarnings({"unchecked","rawtypes"})
public final class ASTAddNode extends SimpleNode {

    ASTAddNode(int id) {
        super(id);
    }


	@Override
	public ContextExpression interpretToLambda() {
		return lazyBiFunction((a,b)->interpret(a, b));
	}
	
	public final Object interpret(Object a,Object b) {

        if (!(a instanceof ArrayList || b instanceof ArrayList)) {
            return Utils.add(a, b);
        } else if ((a instanceof ArrayList) && !(b instanceof ArrayList)) {
            ArrayList list = (ArrayList) a;
            ArrayList result = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                Object rel = Utils.add(val, b);

                result.add(rel);
            }
            return result;
        } else if ((b instanceof ArrayList) && !(a instanceof ArrayList)) {
            ArrayList list = (ArrayList) b;
            ArrayList result = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                Object rel = Utils.add(a, val);

                result.add(rel);
            }
            return result;
        } else if (a instanceof ArrayList && b instanceof ArrayList) {
            ArrayList list1 = (ArrayList) a;
            ArrayList list2 = (ArrayList) b;

            if (list1.size() != list2.size())
                throw new RuntimeException("Can only add lists of equals length");
            ArrayList result = new ArrayList();

            for (int i = 0; i < list1.size(); i++) {
                Object val1 = list1.get(i);
                Object val2 = list2.get(i);
                Object rel = Utils.add(val1, val2);

                result.add(rel);
            }
            return result;
        } else
            throw new RuntimeException("Unknown type");
    }

}