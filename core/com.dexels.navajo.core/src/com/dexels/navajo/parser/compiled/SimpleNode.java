/* Generated By:JJTree: Do not edit this line. Node.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.dexels.navajo.parser.compiled;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;
import com.dexels.replication.api.ReplicationMessage;

public abstract class SimpleNode implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;

    public SimpleNode(int i) {
        id = i;
    }

    @Override
	public void jjtOpen() {}

    @Override
	public void jjtClose() {}

    @Override
	public void jjtSetParent(Node n) {
        parent = n;
    }

    @Override
	public Node jjtGetParent() {
        return parent;
    }

    @Override
	public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new Node[i + 1];
        } else if (i >= children.length) {
            Node c[] = new Node[i + 1];

            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
    }

    @Override
	public Node jjtGetChild(int i) {
        return children[i];
    }

    @Override
	public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

    @Override
	public String toString() {
        return CompiledParserTreeConstants.jjtNodeName[id];
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    /* Override this method if you want to customize how the node dumps
     out its children. */

    public void dump(String prefix) {
        //System.out.println(toString(prefix));
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = (SimpleNode) children[i];

                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }

    /************************* Added by Sreeni. *******************/

    /** Symbol table */
    // protected static java.util.Hashtable symtab = new java.util.Hashtable();

    /** Stack for calculations. */
    // protected static Object[] stack = new Object[1024];
    // protected static int top = -1;

	public ContextExpression lazyBiFunction(BiFunction<Object, Object, Object> func) {
		ContextExpression expA = jjtGetChild(0).interpretToLambda();
		ContextExpression expB = jjtGetChild(1).interpretToLambda();
		
		return new ContextExpression() {
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage) throws TMLExpressionException {
		        Object a = expA.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode,tipiLink,access,immutableMessage,paramMessage);
		        Object b = expB.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode,tipiLink,access,immutableMessage,paramMessage);
				return func.apply(a, b);
			}

			@Override
			public boolean isLiteral() {
				return expA.isLiteral() && expB.isLiteral();
			}
		};
	}
	
	public ContextExpression lazyFunction(Function<Object, Object> func) {
		ContextExpression expA = jjtGetChild(0).interpretToLambda();
		
		return new ContextExpression() {
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage) throws TMLExpressionException {
		        Object a = expA.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access,immutableMessage,paramMessage);
				return func.apply(a);
			}

			@Override
			public boolean isLiteral() {
				return expA.isLiteral();
			}
		};
	}
}

