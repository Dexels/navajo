package com.dexels.navajo.tipi.components.swingimpl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.tree.MessageTreeNode;
import com.dexels.navajo.tipi.components.swingimpl.tree.NavajoTreeNode;
import com.dexels.navajo.tipi.components.swingimpl.tree.TipiNavajoTreeModel;

public class TipiNavajoTree extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 3882394810767197190L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiNavajoTree.class);
	private final Map<String, Boolean> expansionMap = new HashMap<String, Boolean>();
	private TipiNavajoTreeModel navajoTreeModel;
	private int lastSelectedRow = -1;

	public Object createContainer() {
		final TipiSwingNavajoTree tipiSwingNavajoTree = new TipiSwingNavajoTree();
		tipiSwingNavajoTree
				.addTreeSelectionListener(new TreeSelectionListener() {

					public void valueChanged(TreeSelectionEvent tse) {
						TreePath newLeadSelectionPath = tse
								.getNewLeadSelectionPath();
						int last = tipiSwingNavajoTree
								.getRowForPath(newLeadSelectionPath);
						if (last >= 0) {
							lastSelectedRow = last;
						}
						logger.debug("LastROw: " + lastSelectedRow);

						if (newLeadSelectionPath == null) {
							return;
						}
						MessageTreeNode o = (MessageTreeNode) newLeadSelectionPath
								.getLastPathComponent();
						Message m = o.getMessage();
						if (m != null) {
							String selectedPath = m.getFullMessageName();
							Map<String, Object> map = new HashMap<String, Object>();
							// logger.debug("Selected: "+selectedPath);
							map.put("selectedPath", selectedPath);
							map.put("selectedMessage", m);
							setComponentValue("selectedPath", selectedPath);
							logger.debug("Selected: " + selectedPath);
							try {
								performTipiEvent("onComponentSelected", map,
										false);

							} catch (TipiException e) {
								logger.error("Error detected",e);
							}
						}
					}
				});

		tipiSwingNavajoTree
				.addTreeExpansionListener(new TreeExpansionListener() {

					public void treeCollapsed(TreeExpansionEvent te) {
						MessageTreeNode mm = (MessageTreeNode) te.getPath()
								.getLastPathComponent();
						expansionMap.put(mm.getMessage().getFullMessageName(),
								false);
						logger.debug("AAP:"
								+ mm.getMessage().getFullMessageName());
						logger.debug("Exp: " + expansionMap);
					}

					public void treeExpanded(TreeExpansionEvent te) {
						MessageTreeNode mm = (MessageTreeNode) te.getPath()
								.getLastPathComponent();
						expansionMap.put(mm.getMessage().getFullMessageName(),
								true);
						logger.debug("NOOT:"
								+ mm.getMessage().getFullMessageName());
						logger.debug("Exp: " + expansionMap);
					}
				});
		return tipiSwingNavajoTree;
	}

	@Override
	public void loadData(final Navajo n, final String method)
			throws TipiException, TipiBreakException {
		super.loadData(n, method);
		runSyncInEventThread(new Runnable() {

			public void run() {
				NavajoTreeNode tn;
				try {
					tn = createRootNode(n);
					((TipiSwingNavajoTree) getContainer())
							.setRootVisible(false);
					navajoTreeModel = new TipiNavajoTreeModel(tn);
					((TipiSwingNavajoTree) getContainer()).setDragEnabled(true);

					((TipiSwingNavajoTree) getContainer())
							.setModel(navajoTreeModel);
					dump(tn);
					((TipiSwingNavajoTree) getContainer())
							.expandPath((new TreePath(tn)));
					for (int i = 0; i < tn.getChildCount(); i++) {
						TreeNode tnn = tn.getChildAt(i);
						updateExpansion((MessageTreeNode) tnn);
					}
					if (lastSelectedRow >= 0) {
						logger.debug("Row: " + lastSelectedRow);
						TreePath ttt = ((TipiSwingNavajoTree) getContainer())
								.getPathForRow(lastSelectedRow);
						logger.debug("SelectedPath: " + ttt);
						((TipiSwingNavajoTree) getContainer())
								.setSelectionPath(ttt);
					}
				} catch (NavajoException e) {
					// throw new TipiException("Error building tree. ", e);
					logger.error("Error detected",e);
				}

			}
		});
		// ((TipiSwingNavajoTree) getContainer()).expandPath();

	}

	private void updateExpansion(MessageTreeNode tn) {
		Boolean bb = expansionMap.get(tn.getMessage().getFullMessageName());
		if (bb != null) {
			if (bb) {
				((TipiSwingNavajoTree) getContainer()).expandPath(new TreePath(
						navajoTreeModel.getPathToRoot(tn)));
			} else {
				((TipiSwingNavajoTree) getContainer())
						.collapsePath(new TreePath(navajoTreeModel
								.getPathToRoot(tn)));
			}
		}
		for (int i = 0; i < tn.getChildCount(); i++) {
			TreeNode tnn = tn.getChildAt(i);

			updateExpansion((MessageTreeNode) tnn);
		}
	}

	@SuppressWarnings("unchecked")
	private void dump(TreeNode tn) {
		// logger.debug("My node: "+tn.toString());
		// logger.debug("#of children: "+tn.getChildCount());
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
