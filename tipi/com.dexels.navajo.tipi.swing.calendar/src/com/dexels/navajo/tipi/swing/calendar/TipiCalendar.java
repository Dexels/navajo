package com.dexels.navajo.tipi.swing.calendar;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.miginfocom.beans.DateAreaBean;
import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.ActivityDepository;
import com.miginfocom.calendar.activity.DefaultActivity;
import com.miginfocom.calendar.activity.recurrence.ByXXXRuleData;
import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;
import com.miginfocom.util.ActivityHelper;
import com.miginfocom.util.dates.*;
import com.miginfocom.util.gfx.geometry.numbers.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class TipiCalendar
		extends TipiSwingComponentImpl{

	private static final long serialVersionUID = -4916285139918344788L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiCalendar.class);
	
	private DateAreaBean myCalendar;

	@Override
	public Object createContainer() {
		addTestData();
		
		myCalendar = new DateAreaImpl();
		
		return myCalendar;
	}
	
	public void addTestData()
	{
		final String[] TITLES = {"Going to the Gym", "Meeting with the Board", "Taking Mick to Ice Hockey",
			"Lunch with Susanne", "Lunch with Matt", "Meeting about the Site", "Template work", "Fix the car",
			"Take the car to the shop", "Call Chris about the fishing trip", "Major cleaning", "Refactoring some code"
		};

		final String[] DESCRIPTIONS = {"This is a standard description of the event. It conveys details and notes and can be any text."};

		final Object homeID = 10;
		final Object workID = 11;

	    final Object[] CATEGORIES = {homeID, workID};

		DateRange genRange = new DateRange();
		genRange.setSize(DateRangeI.RANGE_TYPE_WEEK, 10, AtFraction.CENTER);

		long startMillis = genRange.getStartMillis();
		long endMillis = genRange.getEndMillis();
		ImmutableDateRange dr = new ImmutableDateRange(startMillis, endMillis, false, null, null);

		// Whole Day activities
		ArrayList acts = ActivityHelper.createActivities(dr, TITLES, DESCRIPTIONS, CATEGORIES, null, 24 * 60 * 60000, -2 * 24 * 60000, 6 * 24 * 60 * 60000, 24 * 60 * 60000, 4 * 24 * 60 * 60000, 300, false, null);
		ActivityDepository.getInstance().addBrokedActivities(acts, this, TimeSpanListEvent.ADDED_CREATED);

		// Short activities
		acts = ActivityHelper.createActivities(dr, TITLES, DESCRIPTIONS, CATEGORIES, null, 60 * 60000, 6 * 60 * 60000, 24 * 60 * 60000, 60 * 60000, 4 * 60 * 60000, 10000, true, "FlexGrid");
		ActivityDepository.getInstance().addBrokedActivities(acts, this, TimeSpanListEvent.ADDED_CREATED);

		// Create mark's recurring lunch.
		startMillis = new GregorianCalendar(2006, 6, 4, 11, 0).getTimeInMillis();
		endMillis = new GregorianCalendar(2006, 6, 4, 12, 0).getTimeInMillis();
		dr = new ImmutableDateRange(startMillis, endMillis, true, null, null);
		RecurrenceRule rr = new RecurrenceRule(Calendar.DAY_OF_YEAR, 1);
		rr.addByXXXRule(new ByXXXRuleData(Calendar.DAY_OF_WEEK, new int[] {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY}));
		Activity act = new DefaultActivity(dr, "mlunch");
		act.setRecurrence(rr);
		act.setSummary("Mark's Lunch");
		act.addCategoryID(homeID, 0);
		act.setLayoutContext("FlexGrid");
		ActivityDepository.getInstance().addBrokedActivity(act, this, TimeSpanListEvent.ADDED_CREATED);

		
	}

	@Override
	public void animateTransition(TipiEvent te,
			TipiExecutable executableParent, List<TipiExecutable> exe,
			int duration) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this, te, executableParent,
				myCalendar.getParent(), exe, duration);
	}

	@Override
	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, TipiEvent event) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
//				doPerformMethod(name, compMeth);
			}
		});
	}

	@Override
	public boolean isReusable() {
		return true;
	}

	@Override
	public void disposeComponent() {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				DateAreaBean jj = (DateAreaBean) getContainer();
				ActivityDepository.getInstance().removeAllBrokedActivities();
				if (jj != null) {
					Container parent = jj.getParent();
					if (parent != null) {
						parent.remove(jj);
					}
				}
				clearContainer();
				myCalendar = null;
			}
		});
		TipiCalendar.super.disposeComponent();

	}

	@Override
	public void reUse() {
		// if (myParent!=null) {
		// myParent.addToContainer();
		// }
		// myWindow.setVisible(true);
	}
}
