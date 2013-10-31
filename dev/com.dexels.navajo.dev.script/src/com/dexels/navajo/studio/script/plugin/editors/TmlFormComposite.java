/*
 * Created on Mar 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.HyperlinkGroup;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.studio.eclipse.ServerEntry;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.ServerInstance;
import com.dexels.navajo.swtclient.GenericPropertyComponent;
import com.dexels.navajo.swtclient.SwtFactory;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class TmlFormComposite extends Composite {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TmlFormComposite.class);
	
	private static final Color LINK_BACKGROUND_COLOR = new Color(Display.getCurrent(), 240, 240, 220);
//	private static final Color BLUE_BACKGROUND_COLOR = new Color(Display.getCurrent(), 220, 220, 240);

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


	private Section methodSection;

	private Section birtSection;

	private Navajo myCurrentNavajo;

	// private final Menu popup;

	private String myCurrentName;

	private ServerEntry myServerEntry;

	private final List<INavajoScriptListener> myScriptListeners = new ArrayList<INavajoScriptListener>();

	private ServerInstance serverInstance;

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
			@Override
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

	public void setNavajo(Navajo n, String scriptName) {
		if (mainMessageContainer != null) {
			mainMessageContainer.dispose();
		}
		myCurrentName = scriptName;
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
		setHeader(n, mainMessageContainer);
		setMessages(n, mainMessageContainer);
		setMethods(n, scriptName);
		setBirtSection(n);
		addRunReportHref(scriptName, headComp);

		mainMessageContainer.setVisible(true);
		// mainMessageContainer.getParent().layout();
		fireGotoScript(scriptName, n);
		myForm.reflow(true);
	}



	/**
	 * @param n
	 * @param myFile
	 */
	private void setMessages(Navajo n, Composite container) {
		List<Message> al;
		try {
			al = n.getAllMessages();
		} catch (NavajoException e) {
			logger.error("Error: ", e);
			return;
		}
		for (Iterator<Message> iter = al.iterator(); iter.hasNext();) {
			Message element = iter.next();
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
			List<Property> al = element.getAllProperties();
			if (!element.getAllMessages().isEmpty()) {
				return false;
			}
			for (int j = 0; j < al.size(); j++) {
				Property p = al.get(j);
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

			@Override
			public void expansionStateChanging(ExpansionEvent e) {
			}

			@Override
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
				tc.getTable().setLayoutData(tff);
				
				Hyperlink h = getKit().createHyperlink(headerRow, "Print as birt", SWT.NONE);
				h.addHyperlinkListener(new HyperlinkAdapter(){

					@Override
					public void linkActivated(HyperlinkEvent e) {
						List<Property> props = reference.getAllProperties();
							final String[] names = new String[props.size()];
						final int[] sizes = new int[props.size()];
						for (int i = 0; i < names.length; i++) {
							names[i] = props.get(i).getName();
							sizes[i]=tc.getTable().getColumn(i).getWidth();
						}
						Object[] aa = tc.getColumnProperties();
						for (int i = 0; i < aa.length; i++) {
							System.err.println("Column#"+i+" is: "+aa[i].getClass());
						}
						printBirtMessage(element,names, names, sizes);
					}
				});
			}
		} else {
			List<Property> al = element.getAllProperties();
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
			List<Message> subm = element.getAllMessages();

			if (subm.size() != 0) {
				Composite submsgs = getKit().createComposite(s, SWT.NONE);
				setupMenuListener(submsgs);
				submsgs.setBackground(new Color(Display.getCurrent(), 240, 220, 240));
				TableWrapData tdd = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);

				submsgs.setLayoutData(tdd);
				submsgs.setLayout(new TableWrapLayout());
				for (Iterator<Message> iter = subm.iterator(); iter.hasNext();) {
					Message submsg = iter.next();
					addMessage(submsg, submsgs);
				}
			}
		}
		ss.setClient(s);
	}

	protected void printBirtMessage(final Message element, final String[] propertyNames, final String[] propertyTitles, final int[] columnWidths) {
		Job j = new Job("Running Navajo...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				Navajo cp = NavajoFactory.getInstance().createNavajo();
				cp.addMessage(element.copy(cp));
				if(myServerEntry!=null) {
						Binary b = myServerEntry.getArrayMessageReport(element, propertyNames, propertyTitles, columnWidths, "pdf","landscape", new int[]{5,5,5,5});
						GenericPropertyComponent.openBinary(b);
					
				}
				return Status.OK_STATUS;
			}
		};
		j.schedule();
	}

	private void setupMenuListener(Composite c) {
		c.addMouseListener(new MouseAdapter() {
			@Override
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
		List<Property> al = element.getAllProperties();
		for (Iterator<Property> iter = al.iterator(); iter.hasNext();) {
			Property prop = iter.next();
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

		birtSection = getKit().createSection(getForm().getBody(), ExpandableComposite.TITLE_BAR);
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

	private void setMethods(final Navajo n, final String scriptName) {
		HyperlinkGroup hg = new HyperlinkGroup(mainMessageContainer.getDisplay());
		if (methodSection != null) {
			methodSection.dispose();
		}
		hg.setBackground(new Color(mainMessageContainer.getDisplay(), 255, 255, 255));
		methodSection = getKit().createSection(getForm().getBody(), ExpandableComposite.TITLE_BAR);
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

		for (Iterator<Method> iter = n.getAllMethods().iterator(); iter.hasNext();) {
			final Method element = iter.next();
			final Hyperlink hl = whiteKit.createHyperlink(list, element.getName(), SWT.NONE);
			hl.setHref(element.getName());

			TableWrapData tdd = new TableWrapData();
			hl.setLayoutData(tdd);
			hl.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					try {
						runHref(myCurrentNavajo, element.getName(),false, scriptName);
					} catch (Exception e1) {
						logger.error("Error: ", e1);
					}
				}
			});
		}
	}

	/**
	 * @param n
	 * @param myFile
	 */

	private void setHeader(final Navajo n, Composite parent)  {
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

			@Override
			public void expansionStateChanging(ExpansionEvent e) {
			}

			@Override
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

		Section performanceSection = getKit().createSection(list, ExpandableComposite.TITLE_BAR);
		performanceSection.setText("Performance:");
		Section transactionSection = getKit().createSection(list, ExpandableComposite.TITLE_BAR);
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

		Map<String,String> headerAttributes = h.getHeaderAttributes();
		if (headerAttributes != null) {
			Set<String> s = headerAttributes.keySet();
			for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
				String element = iter.next();
				addLabel(performanceList, element + ": " + h.getHeaderAttribute(element));
			}
		}
	}

	private void addLabel(Composite transactionList, String label) {
		getKit().createLabel(transactionList, label);
	}

	


	private void addBirtHref(Composite list, final Navajo n) {
		// final Method element = (Method) iter.next();

		// final Text birtReportName = whiteKit.createText(list, "");
		TableWrapData tdd = new TableWrapData();
		// birtReportName.setLayoutData(tdd);

		final Combo birtCombo = new Combo(list, SWT.NONE);

		birtCombo.setItems(new String[] {"pdf","ppt", "postscript", "xls", "doc" });
		tdd = new TableWrapData();
		birtCombo.setLayoutData(tdd);
		// for now:
		// birtReportName.setVisible(false);

		final Hyperlink hcreate = whiteKit.createHyperlink(list, "[[CREATE REPORT]]", SWT.NONE);
		tdd = new TableWrapData();
		hcreate.setLayoutData(tdd);
		hcreate.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				createBirt(myCurrentName);
			}
		});

		final Hyperlink hl = whiteKit.createHyperlink(list, "[[RUN REPORT]]", SWT.NONE);
		tdd = new TableWrapData();
		hl.setLayoutData(tdd);
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				runBirtReport(birtCombo);
		
			}
		});
		
		final Hyperlink h3 = whiteKit.createHyperlink(list, "[[RUN TABLE REPORT]]", SWT.NONE);
		tdd = new TableWrapData();
		h3.setLayoutData(tdd);
		h3.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				Navajo copiedNavajo = n.copy();
				try {
					runHref(copiedNavajo, "ProcessPrintGenericBirt", false, null);
				} catch (Exception e1) {
					logger.error("Error: ", e1);
				}
		}
		});
	}




	private void addRunReportHref(final String name, Composite list)  {
			final Hyperlink hl = getKit().createHyperlink(list, "[[Run report]]", SWT.NONE);
		hl.setHref(name);
		// TableWrapData tdd = new TableWrapData();
		// hl.setLayoutData(new
		// TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
		// hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
		hl.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				try {

					runHref(myCurrentNavajo.copy(), "ProcessPrintGenericBirt", true, myCurrentName);

				} catch (Exception e1) {
					logger.error("Error: ", e1);
				}
			}
		});
	}

	


	/**
	 * @return Returns the kit.
	 */
	public FormToolkit getKit() {
		return kit;
	}

	private void runHref(final Navajo nav,final String name, final boolean reload,
			final String sourceTmlName) throws Exception {
		fireScriptCalled(nav,name);
		nav.getHeader().setHeaderAttribute("sourceScript", sourceTmlName);
		nav.getHeader().write(System.err);
		
		// I think this is for the TmlBrowser
		runScript(nav, name);
		// I think this is for the TmlViewer
		

	}

	private void runScript(final Navajo nav, final String name) {
//		if (myServerEntry != null) {
			Job j = new Job("Running Navajo...") {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						Navajo output = null;
						if(serverInstance!=null) {
							System.err.println("Calling embedded server: "+name);
							output = serverInstance.callService(nav, name);
						} else {
							output = myServerEntry.runProcess(name, nav);
						}
						final Navajo n = output;
					
						nav.getHeader().write(System.err);
						n.getHeader().write(System.err);
						Display.getDefault().syncExec(new Runnable() {

							@Override
							public void run() {
								setNavajo(n, name);
							}
						});
					} catch (ClientException e) {
						logger.error("Error: ", e);
					}
					return Status.OK_STATUS;
				}
			};
			j.schedule();
			return;
//		}
	}

	public void reflow() {

		myForm.reflow(false);
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
		for (int i = 0; i < myScriptListeners.size(); i++) {
			INavajoScriptListener current = myScriptListeners.get(i);
			current.gotoScript(scriptName, n);
		}
	}

	private void fireScriptCalled(Navajo nav, String scriptName) {
		for (int i = 0; i < myScriptListeners.size(); i++) {
			INavajoScriptListener current = myScriptListeners.get(i);
			current.callScript(scriptName, nav) ;
		}
	}


	public Navajo getCurrentNavajo() {
		return myCurrentNavajo;
	}

	public String getCurrentScript() {
		return myCurrentName;
	}
	protected void createBirt(String service) {
		BirtUtils b = new BirtUtils();
		try {
			IFile birt = getCurrentReport();
			if (birt==null) {
				SaveAsDialog sd = new SaveAsDialog(getShell());
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
				File createdFile = new File(rez);

				InputStream template = getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/birt/blank.rptdesign");
				b.createEmptyReport(myCurrentNavajo, createdFile, template);
				iff.refreshLocal(0, null);
				IDE.openEditor(NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage(), iff);
				
			} else {
				boolean dirty = NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().isDirty();
				boolean ok = NavajoScriptPluginPlugin.getDefault().showConfirm("Are you sure?","Do you really want to rewrite the datasources of this report?");
				if(!ok) {
					return;
				}
				if(dirty) {
					boolean ok2 = NavajoScriptPluginPlugin.getDefault().showConfirm("Are you sure?","Is it ok to discard your unsaved changes?");
					if(!ok2) {
						return;
					}
				}

				birt.refreshLocal(0, null);
				final IEditorPart editor = NavajoScriptPluginPlugin
						.getDefault().getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActiveEditor();
				if (editor.getSite().getWorkbenchWindow().getActivePage() != null) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							editor.getSite().getWorkbenchWindow().getActivePage().closeEditor(editor, false);
						}
					});
				}
				
				IDE.openEditor(NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage(), birt);
				

			}
		} catch (IOException e) {
			logger.error("Error: ", e);
		} catch (CoreException e) {
			logger.error("Error: ", e);
		} catch(Throwable t) {
			logger.error("Error: ", t);
		}
	}

	private IFile getCurrentReport() {
		IEditorPart part = NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage()
		.getActiveEditor();
		IFile iff = null;
		
		if (part != null) {
			IEditorInput ei = part.getEditorInput();
			if (ei != null) {
				iff = ResourceUtil.getFile(ei);
			}
		} else {
			return null;
		}
		if (iff != null && iff.getFullPath().toString().endsWith(".rptdesign")) {
//			InputStream contents;
//			try {
//				contents = iff.getContents();
//				Binary b = new Binary(contents);
//				return b;
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
			return iff;
			
			
		} 
		return null;
	}
	
	private void runBirtReport(final Combo birtCombo) {
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
				runHref(copiedNavajo, "ProcessPrintGenericBirt", false, getCurrentScript());

			} else {
			
				runHref(copiedNavajo, "ProcessPrintGenericBirt",false, getCurrentScript());

			}

		} catch (Exception e1) {
			logger.error("Error: ", e1);
		}
	}

	@Deprecated
	public void setServerInstance(ServerInstance si) {
		this.serverInstance = si;
		
	}

}
