package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;

import javax.swing.event.*;
import javax.swing.tree.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.tree.*;

public class TipiNavajoTree extends TipiSwingDataComponentImpl {

	private final Map<String,Boolean> expansionMap = new HashMap<String,Boolean>();
	private TipiNavajoTreeModel navajoTreeModel;
	private int lastSelectedRow = -1;
	public Object createContainer() {
		final TipiSwingNavajoTree tipiSwingNavajoTree = new TipiSwingNavajoTree();
		tipiSwingNavajoTree.addTreeSelectionListener(new TreeSelectionListener(){

		
			public void valueChanged(TreeSelectionEvent tse) {
				TreePath newLeadSelectionPath = tse.getNewLeadSelectionPath();
				int last = tipiSwingNavajoTree.getRowForPath(newLeadSelectionPath);
				if(last>=0) {
					lastSelectedRow = last;
				}
				System.err.println("LastROw: "+lastSelectedRow);
			
				if(newLeadSelectionPath==null) {
					return;
				}
				MessageTreeNode o = (MessageTreeNode) newLeadSelectionPath.getLastPathComponent();
				Message m = o.getMessage();
				if(m!=null) {
					String selectedPath = m.getFullMessageName();
					Map<String,Object> map = new HashMap<String,Object>();
//					System.err.println("Selected: "+selectedPath);
					map.put("selectedPath", selectedPath);
					map.put("selectedMessage", m);
					setComponentValue("selectedPath",selectedPath);
					System.err.println("Selected: "+selectedPath);
					try {
						performTipiEvent("onComponentSelected", map, false);
						
					} catch (TipiException e) {
						e.printStackTrace();
					}
				}
			}});
		
		tipiSwingNavajoTree.addTreeExpansionListener(new TreeExpansionListener(){

			public void treeCollapsed(TreeExpansionEvent te) {
				MessageTreeNode mm = (MessageTreeNode) te.getPath().getLastPathComponent();
				expansionMap.put(mm.getMessage().getFullMessageName(), false);
				System.err.println("AAP:" +mm.getMessage().getFullMessageName());
				System.err.println("Exp: "+expansionMap);
			}

			public void treeExpanded(TreeExpansionEvent te) {
				MessageTreeNode mm = (MessageTreeNode) te.getPath().getLastPathComponent();
				expansionMap.put(mm.getMessage().getFullMessageName(), true);
				System.err.println("NOOT:" +mm.getMessage().getFullMessageName());
				System.err.println("Exp: "+expansionMap);
			}});
		return tipiSwingNavajoTree;
		}
	

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		NavajoTreeNode tn;
		try {
			tn = createRootNode(n);
			((TipiSwingNavajoTree) getContainer()).setRootVisible(false);
			navajoTreeModel = new TipiNavajoTreeModel(tn);
			((TipiSwingNavajoTree) getContainer()).setDragEnabled(true);
			
			((TipiSwingNavajoTree) getContainer()).setModel(navajoTreeModel);
			dump(tn);
			((TipiSwingNavajoTree) getContainer()).expandPath((new TreePath(tn)));
			for (int i = 0; i < tn.getChildCount(); i++) {
				TreeNode tnn = tn.getChildAt(i);
				updateExpansion((MessageTreeNode) tnn);
			}
			if(lastSelectedRow>=0) {
				System.err.println("Row: "+lastSelectedRow);
				TreePath ttt = ((TipiSwingNavajoTree) getContainer()).getPathForRow(lastSelectedRow);
				System.err.println("SelectedPath: "+ttt);
				((TipiSwingNavajoTree) getContainer()).setSelectionPath(ttt);
			}
		} catch (NavajoException e) {
			throw new TipiException("Error building tree. ", e);
		}
//		((TipiSwingNavajoTree) getContainer()).expandPath();
		
	}

	private void updateExpansion(MessageTreeNode tn) {
		Boolean bb = expansionMap.get(tn.getMessage().getFullMessageName());
		if(bb!=null) {
			if (bb) {
				((TipiSwingNavajoTree) getContainer()).expandPath(new TreePath(navajoTreeModel.getPathToRoot(tn)));
			} else {
				((TipiSwingNavajoTree) getContainer()).collapsePath(new TreePath(navajoTreeModel.getPathToRoot(tn)));
			}
		}
		for (int i = 0; i < tn.getChildCount(); i++) {
			TreeNode tnn = tn.getChildAt(i);

			updateExpansion((MessageTreeNode) tnn);
		}
	}


	@SuppressWarnings("unchecked")
	private void dump(TreeNode tn) {
//		System.err.println("My node: "+tn.toString());
//		System.err.println("#of children: "+tn.getChildCount());
		Enumeration<TreeNode> children = tn.children();
		while (children.hasMoreElements()) {
			TreeNode element = children.nextElement();
			dump(element);
		}

	}

	private NavajoTreeNode createRootNode(Navajo n) throws NavajoException {
		NavajoTreeNode tnt = new NavajoTreeNode(n);
		return tnt;
	}


}
