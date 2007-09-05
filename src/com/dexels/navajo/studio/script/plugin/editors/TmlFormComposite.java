/*
 * Created on Mar 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import java.io.*;
import java.io.File;
import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.debug.core.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.events.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.ide.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.ViewPart;

import org.eclipse.core.resources.*;

import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.swtclient.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class TmlFormComposite extends Composite {

	private static final Color LINK_BACKGROUND_COLOR = new Color(Display.getCurrent(), 240, 240, 220);
	private static final Color BLUE_BACKGROUND_COLOR = new Color(Display.getCurrent(), 220, 220, 240);

	private static final Color FORM_BACKGROUND_COLOR = LINK_BACKGROUND_COLOR;

	/**
	 * @param parent
	 * @param style
	 */
	private final ScrolledForm myForm;

	private final FormToolkit kit;

	private final FormToolkit whiteKit;

	// private final TmlEditor myEditor;

	private Composite mainMessageContainer;

	private IFile myCurrentFile = null;

	private Section methodSection;

	private Section birtSection;

	private Navajo myCurrentNavajo;

	// private final Menu popup;

	private String myCurrentName;

	private ServerEntry myServerEntry;

	private final ArrayList myScriptListeners = new ArrayList();

	// private ScrolledComposite mainMessageScroll;

	public TmlFormComposite(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout());
		// myEditor = ee;
		kit = new FormToolkit(parent.getDisplay());
		whiteKit = new FormToolkit(parent.getDisplay());

		myForm = new ScrolledForm(this, SWT.V_SCROLL | SWT.H_SCROLL);
		myForm.setExpandHorizontal(true);
		myForm.setExpandVertical(true);

		// popup = new Menu(getShell(), SWT.POP_UP);
		// MenuItem back = new MenuItem(popup, SWT.PUSH);
		myForm.getBody().setLayout(new TableWrapLayout());
		myForm.setBackground(FORM_BACKGROUND_COLOR);
		kit.getHyperlinkGroup().setBackground(LINK_BACKGROUND_COLOR);
		myForm.getBody().addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				// System.err.println("CLICK!");
				if (e.button == 3) {
					// popup.setVisible(true);
				}
			}
		});

	}

	public ScrolledForm getForm() {
		return myForm;
	}

	public void setNavajo(Navajo n, IFile myFile, String scriptName) {
		if (mainMessageContainer != null) {
			mainMessageContainer.dispose();
		}
		myCurrentName = scriptName;
		myCurrentFile = myFile;
		myCurrentNavajo = n;
		mainMessageContainer = getKit().createComposite(myForm.getBody(), SWT.NONE);
		mainMessageContainer.setVisible(false);
		getKit().adapt(mainMessageContainer);
		TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
		tff.grabHorizontal = false;
		mainMessageContainer.setLayoutData(tff);

		mainMessageContainer.setBackground(FORM_BACKGROUND_COLOR);
		mainMessageContainer.setLayout(new TableWrapLayout());
		Composite headComp = new Composite(mainMessageContainer, SWT.BORDER);
		headComp.setBackground(FORM_BACKGROUND_COLOR);
		headComp.setLayout(new RowLayout());
		Label l = new Label(headComp, SWT.BOLD);
		l.setFont(new Font(Display.getCurrent(), "Arial", 15, SWT.BOLD));
		l.setBackground(FORM_BACKGROUND_COLOR);
		l.setText(scriptName);
		headComp.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
		try {
			addEditScriptHref(scriptName, headComp, n, myCurrentFile);
			addEditTmlHref(scriptName, headComp, n, myCurrentFile);
			addEditReportHref(scriptName, headComp, n, myCurrentFile);
			addRunReportHref(scriptName, headComp, n, myCurrentFile);

		} catch (NavajoPluginException e1) {
			e1.printStackTrace();
		}

		try {
			setHeader(n, myFile, scriptName, mainMessageContainer);
			setMessages(n, mainMessageContainer);
			setMethods(n, myCurrentFile, scriptName);
			setBirtSection(n);
		} catch (NavajoPluginException e1) {
			e1.printStackTrace();
		}

		mainMessageContainer.setVisible(true);
		// mainMessageContainer.getParent().layout();
		fireGotoScript(scriptName, n);
		myForm.reflow(true);
	}

	public void reload() throws NavajoPluginException {
		reload(myCurrentNavajo, myCurrentFile, null);
	}

	// public void setTreeNavajo(Navajo n, IFile myFile) {
	// System.err.println("Setting navajo");
	// 
	// final TreeViewer tv = SwtFactory.getInstance().createNavajoTree(n,
	// getForm().getBody());
	// // book.setContent(tv.getTree());
	// // System.err.println("Bookheight: "+book.getSize().y);
	// getForm().getBody().setBackground(new
	// Color(Workbench.getInstance().getDisplay(), 240, 240, 220));
	//
	// GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true,
	// false);
	// gd.grabExcessHorizontalSpace = true;
	// tv.getTree().setLayoutData(gd);
	// tv.getTree().addTreeListener(new TreeListener() {
	//
	// public void treeCollapsed(TreeEvent e) {
	// System.err.println("Tree opened!");
	// // tv.getTree().pack();
	// // tv.getTree().layout();
	// // getForm().getBody().layout();
	// }
	//
	// public void treeExpanded(TreeEvent e) {
	// System.err.println("Tree opened!");
	// // tv.getTree().pack();
	// // tv.getTree().layout();
	// // getForm().getBody().layout();
	// }
	// });
	// myForm.reflow(true);
	// }

	/**
	 * @param n
	 * @param myFile
	 */
	private void setMessages(Navajo n, Composite container) {
		ArrayList al;
		try {
			al = n.getAllMessages();
		} catch (NavajoException e) {
			e.printStackTrace();
			return;
		}
		for (Iterator iter = al.iterator(); iter.hasNext();) {
			Message element = (Message) iter.next();
			System.err.println("Adding message: " + element.getName());
			addMessage(element, container);
		}

	}

	private boolean isSuitableForTreeTable(Message m) {
		if (m == null) {
			return false;
		}
		if (!Message.MSG_TYPE_ARRAY.equals(m.getType())) {
			return false;
		}
		for (int i = 0; i < m.getArraySize(); i++) {
			Message element = m.getMessage(i);
			// System.err.println("ELEMENT: "+element);
			// System.err.println("ELEMENT: "+element.getClass());

			// If there is a binary property, don't put it in a table
			ArrayList al = element.getAllProperties();
			if (element.getAllMessages().size() > 0) {
				return false;
			}
			for (int j = 0; j < al.size(); j++) {
				Property p = (Property) al.get(j);
				if (Property.BINARY_PROPERTY.equals(p.getType())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param element
	 * @param spb
	 */
	public void addMessage(final Message element, final Composite spb) {
		final ExpandableComposite ss = getKit().createExpandableComposite(spb, ExpandableComposite.TWISTIE);
		ss.setText("-");
		ss.setExpanded(true);
		ss.setBackground(new Color(Display.getCurrent(), 240, 220, 220));
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
		ss.setLayoutData(td);
		final Composite s = getKit().createComposite(ss, SWT.BORDER);
		ss.addExpansionListener(new IExpansionListener() {

			public void expansionStateChanging(ExpansionEvent e) {
			}

			public void expansionStateChanged(ExpansionEvent e) {
				myForm.reflow(true);
			}
		});
		s.setLayout(new TableWrapLayout());
		
		ss.setText(element.getName());
		if(Message.MSG_TYPE_ARRAY.equals(element.getType())) {
			ss.setText(element.getName()+" ["+element.getArraySize()+"]");
				
		}
		
		System.err.println("Double");
//
//		final Menu popup = new Menu(spb.getShell(),SWT.POP_UP);
//		MenuItem item = new MenuItem(popup,SWT.PUSH);
//		item.setText("Print as BIRT");
//		item.addListener(SWT.Selection, new Listener(){
//
//			public void handleEvent(Event event) {
//				System.err.println("Hoei!");
//			}});
//
//		
//		s.setMenu(popup);

		if (isSuitableForTreeTable(element)) {
			if (element.getArraySize() == 0) {
				Label l = getKit().createLabel(s, "Empty table.");
				TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
				l.setLayoutData(tff);
			} else {
				Composite headerRow = getKit().createComposite(s);
				headerRow.setLayout(new RowLayout());
				TableWrapData thf = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
				final Message reference = element.getDefinitionMessage()!=null?element.getDefinitionMessage():element.getMessage(0);
				headerRow.setLayoutData(thf);
				final TableViewer tc = SwtFactory.getInstance().addTable(element, s);
				TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
				tff.grabHorizontal = true;
				if (tc != null) {
					tc.getTable().setLayoutData(tff);
				}
				
				Hyperlink h = getKit().createHyperlink(headerRow, "Print as birt", SWT.NONE);
				h.addHyperlinkListener(new HyperlinkAdapter(){

					public void linkActivated(HyperlinkEvent e) {
						// TODO Auto-generated method stub
						ArrayList props = reference.getAllProperties();
							final String[] names = new String[props.size()];
						final int[] sizes = new int[props.size()];
						for (int i = 0; i < names.length; i++) {
							names[i] = ((Property) props.get(i)).getName();
							sizes[i]=tc.getTable().getColumn(i).getWidth();
						}
						Object[] aa = tc.getColumnProperties();
						for (int i = 0; i < aa.length; i++) {
							System.err.println("Column#"+i+" is: "+aa[i].getClass());
						}
						printBirtMessage(element,e, names, names, sizes);
					}
				});
			}
		} else {
			ArrayList al = element.getAllProperties();
			if (al.size() > 0) {
				TableWrapLayout llayout = new TableWrapLayout();
				llayout.numColumns = 2;
				Composite props = getKit().createComposite(s, SWT.NONE);
				setupMenuListener(props);
				props.setLayout(llayout);
				TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
				tff.grabHorizontal = true;
				props.setLayoutData(tff);
				addProperties(element, props);
			}
			ArrayList subm = element.getAllMessages();

			if (subm.size() != 0) {
				Composite submsgs = getKit().createComposite(s, SWT.NONE);
				setupMenuListener(submsgs);
				submsgs.setBackground(new Color(Display.getCurrent(), 240, 220, 240));
				TableWrapData tdd = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);

				submsgs.setLayoutData(tdd);
				submsgs.setLayout(new TableWrapLayout());
				for (Iterator iter = subm.iterator(); iter.hasNext();) {
					Message submsg = (Message) iter.next();
					addMessage(submsg, submsgs);
				}
			}
		}
		ss.setClient(s);
	}

	protected void printBirtMessage(final Message element, final HyperlinkEvent e, final String[] propertyNames, final String[] propertyTitles, final int[] columnWidths) {
		Job j = new Job("Running Navajo...") {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					Navajo cp = NavajoFactory.getInstance().createNavajo();
					cp.addMessage(element.copy(cp));
					if(myServerEntry!=null) {
							Binary b = myServerEntry.getArrayMessageReport(element, propertyNames, propertyTitles, columnWidths, "pdf","landscape", new int[]{5,5,5,5});
							GenericPropertyComponent.openBinary(b);
						
					}
					
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		j.schedule();
	}

	private void setupMenuListener(Composite c) {
		c.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (e.button != 1) {
					// popup.setVisible(true);
				}
			}

		});
	}

	/**
	 * @param element
	 * @param spb
	 */
	private void addProperties(Message element, Composite spb) {
		// System.err.println("adding properties");
		ArrayList al = element.getAllProperties();
		for (Iterator iter = al.iterator(); iter.hasNext();) {
			Property prop = (Property) iter.next();
			addFormProperty(prop, spb);
		}
	}

	/**
	 * @param prop
	 * @param element
	 * @param spb
	 */
	private void addFormProperty(Property prop, Composite spb) {
		Label l = getKit().createLabel(spb, prop.getDescription() + " [" + prop.getName() + "]");
		l.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP));
		GenericPropertyComponent gpc = SwtFactory.getInstance().createProperty(spb, myForm);
		gpc.showLabels(false);
		gpc.setProperty(prop);
		gpc.adapt(getKit());
		setupMenuListener(gpc.getComposite());

		TableWrapData tableWrapData = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
		gpc.getComposite().setLayoutData(tableWrapData);
	}

	private void setBirtSection(Navajo n) {
		if (birtSection != null) {
			birtSection.dispose();
		}

		birtSection = getKit().createSection(getForm().getBody(), Section.TITLE_BAR);
		birtSection.setText("BIRT:");
		Composite list = getKit().createComposite(birtSection);
		addBirtHref(list, n);
		birtSection.setClient(list);
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.BOTTOM);
		td.grabVertical = false;
		birtSection.setLayoutData(td);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 4;
		list.setLayout(layout);

	}

	/**
	 * @param n
	 * @param myFile
	 */

	private void setMethods(final Navajo n, final IFile myFile, final String scriptName) throws NavajoPluginException {
		HyperlinkGroup hg = new HyperlinkGroup(mainMessageContainer.getDisplay());
		if (methodSection != null) {
			methodSection.dispose();
		}
		hg.setBackground(new Color(mainMessageContainer.getDisplay(), 255, 255, 255));
		methodSection = getKit().createSection(getForm().getBody(), Section.TITLE_BAR);
		methodSection.setText("Methods:");
		setupMenuListener(methodSection);
		Composite list = getKit().createComposite(methodSection);
		setupMenuListener(list);
		methodSection.setClient(list);
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.BOTTOM);
		td.grabVertical = false;
		methodSection.setLayoutData(td);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		list.setLayout(layout);
		addSaveHref(scriptName, list, n, myFile);
		addBackHref(scriptName, list, n, myFile);
		addReloadHref(scriptName, list, n, myFile);
		addRestartHref(scriptName, list, n, myFile);
		addRefreshAdaptersHref(list, myFile);
		addRecompileHref(list, scriptName, myFile);

		for (Iterator iter = n.getAllMethods().iterator(); iter.hasNext();) {
			final Method element = (Method) iter.next();
			final Hyperlink hl = whiteKit.createHyperlink(list, element.getName(), SWT.NONE);
			hl.setHref(element.getName());
			if (myFile != null) {
				if (!NavajoScriptPluginPlugin.getDefault().isScriptExisting(myFile.getProject(), element.getName())) {
					hl.setForeground(new Color(null, 200, 0, 0));
					hl.setToolTipText("This script does not exist!");
				}
			}
			TableWrapData tdd = new TableWrapData();
			hl.setLayoutData(tdd);
			hl.addHyperlinkListener(new HyperlinkAdapter() {
				public void linkActivated(HyperlinkEvent e) {
					try {
						runHref(myCurrentNavajo, myFile, element.getName(), e, false, scriptName);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * @param n
	 * @param myFile
	 */

	private void setHeader(final Navajo n, final IFile myFile, String scriptName, Composite parent) throws NavajoPluginException {
		final ExpandableComposite ss = getKit().createExpandableComposite(parent, ExpandableComposite.TWISTIE);
		ss.setBackground(new Color(Display.getCurrent(), 240, 220, 220));
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
		ss.setLayoutData(td);
		if (n.getHeader() == null) {
			// no header. Don't think it happens
			return;
		}
		Header h = n.getHeader();

		ss.addExpansionListener(new IExpansionListener() {

			public void expansionStateChanging(ExpansionEvent e) {
			}

			public void expansionStateChanged(ExpansionEvent e) {
				myForm.reflow(true);
			}
		});
		String locale = n.getHeader().getHeaderAttribute("locale");
		if (locale == null) {
			ss.setText("Header");
		} else {
			ss.setText("Header locale: " + locale);
		}
		ss.setExpanded(false);
		Composite list = getKit().createComposite(ss);
		ss.setClient(list);

		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		list.setLayout(new FillLayout(SWT.HORIZONTAL));

		Section performanceSection = getKit().createSection(list, Section.TITLE_BAR);
		performanceSection.setText("Performance:");
		Section transactionSection = getKit().createSection(list, Section.TITLE_BAR);
		transactionSection.setText("Transaction:");

		// TableWrapData td3 = new TableWrapData(TableWrapData.FILL_GRAB,
		// TableWrapData.FILL_GRAB);
		// ss.setLayoutData(td3);
		//		
		// TableWrapData td4 = new TableWrapData(TableWrapData.FILL_GRAB,
		// TableWrapData.FILL_GRAB);
		// ss.setLayoutData(td4);
		//		

		Composite performanceList = getKit().createComposite(performanceSection);
		TableWrapLayout tw = new TableWrapLayout();
		tw.numColumns = 1;
		performanceList.setLayout(tw);
		performanceSection.setClient(performanceList);

		Composite transactionList = getKit().createComposite(transactionSection);
		TableWrapLayout tw2 = new TableWrapLayout();
		tw2.numColumns = 1;
		transactionList.setLayout(tw2);
		transactionSection.setClient(transactionList);

		addLabel(transactionList, "Username: " + h.getRPCUser());
		addLabel(transactionList, "Password: " + h.getRPCPassword());
		addLabel(transactionList, "Webservice: " + h.getRPCName());

		Map headerAttributes = h.getHeaderAttributes();
		if (headerAttributes != null) {
			Set s = headerAttributes.keySet();
			for (Iterator iter = s.iterator(); iter.hasNext();) {
				String element = (String) iter.next();
				addLabel(performanceList, element + ": " + h.getHeaderAttribute(element));
			}
		}
	}

	private void addLabel(Composite transactionList, String label) {
		Label l = getKit().createLabel(transactionList, label);
	}

	private void addRefreshAdaptersHref(Composite list, final IFile myFile) {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = whiteKit.createHyperlink(list, "[[Update adapters]]", SWT.NONE);
		TableWrapData tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					IFile cp = myFile.getProject().getFile(".classpath");
					cp.touch(null);
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					NavajoScriptPluginPlugin.getDefault().log("Error reloading adapters: ", e1);
				}
			}

		});
	}

	private void addRecompileHref(Composite list, final String scriptName, final IFile myFile) {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = whiteKit.createHyperlink(list, "[[Recompile]]", SWT.NONE);
		TableWrapData tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					IFile iff;
					try {
						iff = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), scriptName);
						iff.touch(null);
					} catch (NavajoPluginException e1) {
						NavajoScriptPluginPlugin.getDefault().log("Error recompiling current script?!: ", e1);
						e1.printStackTrace();
					}
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					NavajoScriptPluginPlugin.getDefault().log("Error recompiling current script?!: ", e1);
				}
			}

		});
	}

	private void addBirtHref(Composite list, final Navajo n) {
		// final Method element = (Method) iter.next();

		// final Text birtReportName = whiteKit.createText(list, "");
		TableWrapData tdd = new TableWrapData();
		// birtReportName.setLayoutData(tdd);

		final Combo birtCombo = new Combo(list, SWT.NONE);
		birtCombo.setItems(new String[] { "", "pdf", "html", "ppt", "postscript", "xls_prototype", "doc" });
		tdd = new TableWrapData();
		birtCombo.setLayoutData(tdd);
		// for now:
		// birtReportName.setVisible(false);

		final Hyperlink hcreate = whiteKit.createHyperlink(list, "[[CREATE REPORT]]", SWT.NONE);
		tdd = new TableWrapData();
		hcreate.setLayoutData(tdd);
		hcreate.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				createBirt(myCurrentName);
			}
		});

		final Hyperlink hl = whiteKit.createHyperlink(list, "[[RUN REPORT]]", SWT.NONE);
		tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				runBirtReport(birtCombo, e);
		
			}
		});
		
		final Hyperlink h3 = whiteKit.createHyperlink(list, "[[RUN TABLE REPORT]]", SWT.NONE);
		tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Navajo copiedNavajo = n.copy();
				try {
					runHref(copiedNavajo, null, "ProcessPrintGenericBirt", e, false, null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		});
	}

	/**
	 * @param list
	 * @param n
	 */
	private void addReloadHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = whiteKit.createHyperlink(list, "[[Reload]]", SWT.NONE);
		hl.setHref(name);
		TableWrapData tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					reload(n, myFile, e);
				} catch (NavajoPluginException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	private void addRestartHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final IProject p = myFile.getProject();
		final Hyperlink hl = whiteKit.createHyperlink(list, "[[Restart]]", SWT.NONE);
		hl.setHref(name);
		TableWrapData tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					NavajoScriptPluginPlugin.getDefault().startSocketRunner(p);
				} catch (DebugException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	private void addEditTmlHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = getKit().createHyperlink(list, "[[Edit TML]]", SWT.NONE);
		hl.setHref(name);
		// TableWrapData tdd = new TableWrapData();
		// hl.setLayoutData(new
		// TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
		// hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), name);
					NavajoScriptPluginPlugin.getDefault().openInEditor(tmlFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void addEditReportHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = getKit().createHyperlink(list, "[[Edit report]]", SWT.NONE);
		hl.setHref(name);
		// TableWrapData tdd = new TableWrapData();
		// hl.setLayoutData(new
		// TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
		// hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					IFile reportFile = NavajoScriptPluginPlugin.getDefault().getReportFile(myFile.getProject(), name);

					if (!reportFile.exists()) {
						// public void createReport(IProject p, String name,
						// Navajo n, File sourceFile) throws
						// NavajoPluginException, IOException, NavajoException {
						boolean res = NavajoScriptPluginPlugin.getDefault().showQuestion(
								"No report found. Create: " + name + " fileloc: " + myFile.getLocation());
						if (res) {
							NavajoScriptPluginPlugin.getDefault().createReport(myFile.getProject(), name, n, myFile.getLocation().toFile());
						}
					}
					NavajoScriptPluginPlugin.getDefault().openInEditor(reportFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void addRunReportHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = getKit().createHyperlink(list, "[[Run report]]", SWT.NONE);
		hl.setHref(name);
		// TableWrapData tdd = new TableWrapData();
		// hl.setLayoutData(new
		// TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
		// hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {

					runHref(myCurrentNavajo.copy(), myCurrentFile, "ProcessPrintGenericBirt", e, true, myCurrentName);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	//    
	// public IFile getScriptFile(IProject p, String path) throws
	// NavajoPluginException{
	// IFolder iff = p.getFolder(getScriptPath(p));
	// IFile ifff = iff.getFile(path + ".xml");
	// return ifff;
	// }

	private void addEditScriptHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = getKit().createHyperlink(list, "[[Edit Script]]", SWT.NONE);
		hl.setHref(name);
		// TableWrapData tdd = new TableWrapData();
		// hl.setLayoutData(new
		// TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
		// hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));

		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), name);
					NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void addSaveHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		final Hyperlink hl = whiteKit.createHyperlink(list, "[[Save]]", SWT.NONE);
		hl.setHref(name);
		// TableWrapData tdd = new TableWrapData();
		// hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				try {
					// System.err.println("My id:
					// "+myEditor.getEditorSite().getId());
					saveFile();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		// MenuItem mi = new MenuItem(popup, SWT.PUSH);
		// mi.setText("Save");
		// mi.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// try {
		// saveFile();
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// }
		// });

	}

	private void reload(final Navajo n, final IFile myFile, HyperlinkEvent e) throws NavajoPluginException {
		String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(myFile);
		String sourceTml = null;
		if (n.getHeader() != null) {
			sourceTml = n.getHeader().getHeaderAttribute("sourceScript");
		}
		System.err.println(">>> IN RELOAD. " + sourceTml + "<<<");
		try {
			n.write(System.err);
		} catch (NavajoException e2) {
			e2.printStackTrace();
		}
		try {
			Navajo nn = NavajoFactory.getInstance().createNavajo();
			if (sourceTml == null || "".equals(sourceTml)) {
				runHref(nn, null, scriptName, e, true, null);
			} else {
				IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
				if (sourceTmlFile != null && sourceTmlFile.exists()) {
					if (!sourceTmlFile.isSynchronized(0)) {
						sourceTmlFile.refreshLocal(0, null);
					}
					nn = NavajoScriptPluginPlugin.getDefault().loadNavajo(sourceTmlFile);
					// InputStream is = sourceTmlFile.getContents();
					// nn = NavajoFactory.getInstance().createNavajo(is);
					// is.close();
					// System.err.println("LOCATED SOURCE: "+sourceTml);
					// nn.write(System.err);
				}
				runHref(nn, sourceTmlFile, scriptName, e, true, sourceTml);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// if (myEditor != null) {
		// NavajoScriptPluginPlugin.getDefaultWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(myEditor,
		// false);
		// }
	}

	private void addBackHref(final String name, Composite list, final Navajo n, final IFile myFile) {
		if (n.getHeader() == null) {
			return;
		}
		final String sourceTml = n.getHeader().getHeaderAttribute("sourceScript");

		if (sourceTml == null || "".equals(sourceTml) || myFile == null) {
			return;
		}
		final Hyperlink hl = whiteKit.createHyperlink(list, "[[Back]]", SWT.NONE);
		hl.setHref(name);
		TableWrapData tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				if (sourceTml != null) {
					try {
						back(myFile, sourceTml);
					} catch (NavajoPluginException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
	}

	private void saveFile() throws IOException, CoreException, NavajoException {
		if (myCurrentFile == null) {
			return;
		}
		// if (myEditor != null) {
		// myEditor.doSave(null);
		// } else {
		File currentF = new File(myCurrentFile.getLocation().toOSString());
		FileOutputStream fos = new FileOutputStream(currentF);
		myCurrentNavajo.write(fos);
		fos.flush();
		fos.close();
		myCurrentFile.refreshLocal(0, null);
		// }

	}

	/**
	 * @return Returns the kit.
	 */
	public FormToolkit getKit() {
		return kit;
	}

	private void runHref(final Navajo nav, final IFile myFile, final String name, HyperlinkEvent e, final boolean reload,
			final String sourceTmlName) throws Exception {
		fireScriptCalled(name);
		if (myFile == null) {
			System.err.println("RUNHREF: file: [[null]] name: " + name + " reload: " + reload);

		} else {
			System.err.println("RUNHREF: file: " + myFile.getFullPath().toOSString() + "name: " + name + " reload: " + reload+" sourceTmlName: "+sourceTmlName);
		}
		// I think this is for the TmlBrowser
		if (myServerEntry != null) {
			Job j = new Job("Running Navajo...") {

				protected IStatus run(IProgressMonitor monitor) {
					try {
						final Navajo n = myServerEntry.runProcess(name, nav);
						try {
							System.err.println("Href run: ");
							nav.getHeader().write(System.err);
							n.getHeader().write(System.err);
							System.err.println("End of href.");
						} catch (NavajoException e) {
							e.printStackTrace();
						}
						Display.getDefault().syncExec(new Runnable() {

							public void run() {
								setNavajo(n, null, name);
							}
						});
					} catch (ClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
			};
			j.schedule();
			return;
		}
		// I think this is for the TmlViewer

		System.err.println("Found a null myServerEntry, I think. ");
		saveFile();
		final IProject ipp = myCurrentFile.getProject();
		IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(ipp, name);
		IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(ipp, name);
		int stateMask = 0;

		if (e != null) {
			stateMask = e.getStateMask();
		}

		if ((stateMask & SWT.SHIFT) != 0) {
			NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
			return;
		}
		if ((stateMask & SWT.CTRL) != 0) {
			if (!tmlFile.exists()) {
				return;
			}
			if (tmlFile != null && !tmlFile.isSynchronized(0)) {
				tmlFile.refreshLocal(0, null);
			}
			//            
			// InputStream is = tmlFile.getContents();
			Navajo n = NavajoScriptPluginPlugin.getDefault().loadNavajo(tmlFile);

			// NavajoFactory.getInstance().createNavajo(is);
			// is.close();
			//            
			setNavajo(n, tmlFile, myCurrentName);
			fireGotoScript(name, n);
			myForm.reflow(false);
			return;
		}
		final IFile finalScript = scriptFile;
		String ll = myCurrentNavajo.getHeader().getHeaderAttribute("local");
		if ("true".equals(ll)) {
			// THIS IS DEPRECATED
			try {
				Launch l = NavajoScriptPluginPlugin.getDefault().runNavajo("com.dexels.navajo.client.impl.NavajoRunner", finalScript,
						myFile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			Job j = new Job("Running Navajo...") {

				protected IStatus run(IProgressMonitor monitor) {

					if (reload) {
						// String sourceTmlName =
						// nav.getHeader().getAttribute("sourceScript");
						// System.err.println("RELOAD: LOOKING IN
						// HEADER:"+sourceTmlName);
						// nav.getHeader().write(System.err);
						// myCurrentNavajo.getHeader().setAttribute("sourceScript",
						// sourceTmlName);
						// try {
						// IFile sourceFile = null;
						// // if (sourceTmlName!=null &&
						// !"".equals(sourceTmlName)) {
						// sourceFile =
						// NavajoScriptPluginPlugin.getDefault().getTmlFile(ipp,
						// sourceTmlName);
						// }
						NavajoScriptPluginPlugin.getDefault().runRemoteNavajo(ipp, name, myFile, sourceTmlName);
						// } catch (NavajoPluginException e) {
						// e.printStackTrace();
						// }

					} else {
						System.err.println("Setting header to: " + sourceTmlName);
						myCurrentNavajo.getHeader().setHeaderAttribute("sourceScript", sourceTmlName);
						NavajoScriptPluginPlugin.getDefault().runRemoteNavajo(ipp, name, myFile, sourceTmlName);

					}
					return Status.OK_STATUS;
				}
			};
			j.schedule();
		}

	}

	public void reflow() {

		myForm.reflow(false);
	}

	private void back(final IFile myFile, final String sourceTml) throws NavajoPluginException {
		if (myFile == null) {
			return;
		}
		IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
		System.err.println("SourceMTL: " + sourceTml);
		System.err.println("SourceTML full path: " + sourceTmlFile.getFullPath());
		// if (myEditor != null) {
		// NavajoScriptPluginPlugin.getDefault().openInEditor(sourceTmlFile);
		// }
		Navajo n = NavajoScriptPluginPlugin.getDefault().loadNavajo(sourceTmlFile);
		setNavajo(n, sourceTmlFile, sourceTml);
		fireGotoScript(sourceTml, n);
		// BEware here...

		myForm.reflow(true);
		// runHref(sourceTmlFile, scriptName, e);
	}

	public void setServerEntry(ServerEntry se) {
		myServerEntry = se;
	}

	public void addNavajoScriptListener(INavajoScriptListener listener) {
		myScriptListeners.add(listener);
	}

	public void removeNavajoScriptListener(INavajoScriptListener listener) {
		myScriptListeners.remove(listener);
	}

	private void fireGotoScript(String scriptName, Navajo n) {
		System.err.println("goto Script hit!!!");
		for (int i = 0; i < myScriptListeners.size(); i++) {
			INavajoScriptListener current = (INavajoScriptListener) myScriptListeners.get(i);
			current.gotoScript(scriptName, n);
		}
	}

	private void fireScriptCalled(String scriptName) {
		System.err.println("Script called hit!!!");
		for (int i = 0; i < myScriptListeners.size(); i++) {
			INavajoScriptListener current = (INavajoScriptListener) myScriptListeners.get(i);
			current.callingScript(scriptName);
		}
	}

	public void back() throws NavajoPluginException {
		final String sourceTml = myCurrentNavajo.getHeader().getHeaderAttribute("sourceScript");
		if (sourceTml == null || "".equals(sourceTml) || myCurrentFile == null) {
			return;
		}
		back(myCurrentFile, sourceTml);
	}

	public Navajo getCurrentNavajo() {
		return myCurrentNavajo;
	}

	public String getCurrentScript() {
		return myCurrentName;
	}

	public IFile getCurrentFile() {
		return myCurrentFile;
	}

	protected void createBirt(String service) {
		// TODO Auto-generated method stub
		// FileDialog fd = new FileDialog(formComposite.getShell());
		SaveAsDialog sd = new SaveAsDialog(getShell());
		// fd.setText("Choose report name");
		// sd.showClosedProjects(false);
		// fd.setFileName("NewReport.rptdesign");
		sd.setOriginalName(service);

		int result = sd.open();
		if (result == Window.CANCEL) {
			return;
		}
		IPath ip = sd.getResult();
		IPath ipp = ip.addFileExtension("rptdesign");
		IFile iff = ResourcesPlugin.getWorkspace().getRoot().getFile(ipp);

		String rez = iff.getLocation().toString();
		System.err.println("Result: " + rez);
		BirtUtils b = new BirtUtils();
		File createdFile = new File(rez);
		try {
			b.createEmptyReport(myCurrentNavajo, createdFile, service);
			iff.refreshLocal(0, null);
			IDE.openEditor(NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage(), iff);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void runBirtReport(final Combo birtCombo, HyperlinkEvent e) {
		try {
			if (myCurrentNavajo == null) {
				return;
			}

			final Navajo copiedNavajo = myCurrentNavajo.copy();

			IEditorPart part = NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();

			IFile iff = null;
			Message reportDef = NavajoFactory.getInstance().createMessage(copiedNavajo, "__ReportDefinition");
			copiedNavajo.addMessage(reportDef);
			if (part != null) {
				IEditorInput ei = part.getEditorInput();
				if (ei != null) {
					iff = ResourceUtil.getFile(ei);
				}
			}
			int i = birtCombo.getSelectionIndex();
			if (i > 0) {
				String ss = birtCombo.getItem(i);
				if (ss != null && !"".equals(ss)) {
					System.err.println("Selected reporting type: ");
					Property reportProperty = NavajoFactory.getInstance().createProperty(copiedNavajo, "OutputFormat",
							Property.STRING_PROPERTY, null, 0, null, Property.DIR_IN, null);
					reportDef.addProperty(reportProperty);
					reportProperty.setValue(ss);
				}
			}

			// Check for the open editor: If the editor is editing a birt report,
			// use it as definition
			if (iff != null && iff.getFullPath().toString().endsWith(".rptdesign")) {
				System.err.println("Editor resource: " + iff.getLocation());
				InputStream contents = iff.getContents();
				Binary b = new Binary(contents);
				contents.close();
				// There is a supplied report. Use it, disregard
				// reportName
				Property reportProperty = NavajoFactory.getInstance().createProperty(copiedNavajo, "Report",
						Property.BINARY_PROPERTY, null, 0, null, Property.DIR_IN, null);
				reportDef.addProperty(reportProperty);
				reportProperty.setAnyValue(b);

				copiedNavajo.write(System.err);
				runHref(copiedNavajo, null, "ProcessPrintGenericBirt", e, false, null);

			} else {
				// disabled for now
				// if (birtReportName.getText() != null &&
				// !"".equals(birtReportName.getText())) {
				//
				// // No supplied report file, but filename found.
				// Property reportNameProperty =
				// NavajoFactory.getInstance().createProperty(copiedNavajo,
				// "ReportName",
				// Property.STRING_PROPERTY, null, 0, null,
				// Property.DIR_IN, null);
				// reportDef.addProperty(reportNameProperty);
				// reportNameProperty.setAnyValue(birtReportName.getText());
				// }

				runHref(copiedNavajo, null, "ProcessPrintGenericBirt", e, false, null);

			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
