package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class BlockTag extends BaseNode implements NS3Compatible {

	List<BaseNode> myChildren = new ArrayList<>();
	String condition = null;
	NS3Compatible parent;
	
	public NS3Compatible getParentTag() {
		return parent;
	}
	
	public void addParent(NS3Compatible p) {
		parent = p;
		
	}
	public BlockTag(Navajo n) {
		super(n);
	}

	public void add(NS3Compatible node) {
		node.addParent(this);
		myChildren.add((BaseNode) node);
	}

	// add <block/>
	public BlockTag addBlock(BlockTag bt) {
		bt.addParent(this);
		myChildren.add(bt);
		return bt;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(getCondition()));
		sb.append(NS3Constants.OPEN_BLOCK);
		sb.append("\n");
		w.write(sb.toString().getBytes());
		// Loop over children
		for ( BaseNode n : getChildren() ) {
			if ( n instanceof NS3Compatible ) {
				((NS3Compatible) n).formatNS3(indent + 1, w);
			}
		}

		w.write((NS3Utils.generateIndent(indent) + NS3Constants.CLOSE_BLOCK + "\n").getBytes());

	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> attr = new HashMap<>();
		attr.put(Attributes.CONDITION, condition);
		return attr;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return myChildren;
	}

	@Override
	public String getTagName() {
		return Tags.BLOCK;
	}

	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
		myChildren.add(cb);
	}

	public void addSynchronized(SynchronizedTag st) {
		st.addParent(this);
		myChildren.add(st);
	}

	public void addDebug(DebugTag child) {
		child.addParent(this);
		myChildren.add(child);
	}


}
