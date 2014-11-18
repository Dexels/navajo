package com.dexels.navajo.tipi.swing.calendar;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.SwingConstants;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.miginfocom.beans.ActivityAShapeBean;
import com.miginfocom.beans.DateAreaBean;
import com.miginfocom.beans.DateHeaderBean;
import com.miginfocom.beans.DatePickerBean;
import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.ActivityDepository;
import com.miginfocom.calendar.activity.DefaultActivity;
import com.miginfocom.calendar.activity.recurrence.ByXXXRuleData;
import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;
import com.miginfocom.util.ActivityHelper;
import com.miginfocom.util.dates.DateFormatList;
import com.miginfocom.util.dates.DateRange;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.dates.ImmutableDateRange;
import com.miginfocom.util.dates.TimeSpanListEvent;
import com.miginfocom.util.gfx.geometry.numbers.AtFraction;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public final class TipiCalendar extends TipiSwingComponentImpl {

    private static final long serialVersionUID = -4916285139918344788L;

    private DateAreaBean myCalendar;

    @Override
    public Object createContainer() {
        //addTestData();

        myCalendar = new DateAreaImpl();

        setWeekView();
        return myCalendar;
    }

    private void setWeekView() {
        myCalendar.setPrimaryDimension(SwingConstants.VERTICAL);
        myCalendar.setPrimaryDimensionCellType(DateRangeI.RANGE_TYPE_MINUTE);
        myCalendar.setPrimaryDimensionCellTypeCount(30);
        myCalendar.setWrapBoundary(DateRangeI.RANGE_TYPE_DAY);

        DateRange genRange = new DateRange();
        genRange.setSize(DateRange.RANGE_TYPE_DAY, 7, AtFraction.START);
        myCalendar.getDateArea().setVisibleDateRange(genRange);
        DateHeaderBean northHeader = new DateHeaderBean();
        setDateHeaderProperties(northHeader);
        myCalendar.setNorthDateHeader(northHeader);

        DateHeaderBean westHeader = new DateHeaderBean();
        setHourHeaderProperties(westHeader);
        myCalendar.setWestDateHeader(westHeader);
        
        
        // Scroll to 09:00
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        myCalendar.scrollToShowRange(new DateRange(cal, null, null), 0.5f , 0.0f );
        
        ActivityAShapeBean abean = new ActivityAShapeBean();
        abean.setBackground(new Color(224,77,77));
        abean.setCornerRadius(10);
        abean.setDraggable(true);
        abean.setOutlinePaint(new Color(100,0,0));
        abean.setOutlineStrokeWidth(2);
        
//        DateFormatList dateFormatList = new DateFormatList("MM-dd-yyyy");
//        picker.setRangeFormat(DateRangeI.RANGE_TYPE_DAY, dateFormatList);
//        picker.setDateAreaContainer(myCalendar);
//        myCalendar.add(picker);
        
        DateAreaBean da = new DateAreaBean();
        DatePickerBean picker = new DatePickerBean();
        picker.setDateAreaContainer(da);
        picker.setMinimumPopupSize(new Dimension(300, 300));
        myCalendar.add(picker);

    }

    private void setHourHeaderProperties(DateHeaderBean westHeader) {
        westHeader
                .setHeaderRows(new com.miginfocom.calendar.header.CellDecorationRow[] { new com.miginfocom.calendar.header.CellDecorationRow(
                        com.miginfocom.util.dates.DateRangeI.RANGE_TYPE_HOUR, new com.miginfocom.util.dates.DateFormatList(
                                "HH':'mm", null), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(50.0f),
                        new com.miginfocom.util.gfx.geometry.AbsRect(new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f),
                                new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f),
                                new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f),
                                new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), null, null, null),
                        (java.awt.Paint[]) null, new java.awt.Paint[] { new java.awt.Color(102, 102, 102) },
                        new com.miginfocom.util.repetition.DefaultRepetition(0, 1, null, null),
                        new java.awt.Font[] { new java.awt.Font("Verdana", 0, 11) }, new java.lang.Integer[] { null },
                        new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f),
                        new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f)) });
        westHeader.setBackgroundPaint(new java.awt.Color(255, 255, 255));
        westHeader.setExpandToCorner(com.miginfocom.calendar.datearea.DateAreaContainer.CORNER_EXPAND_TOP_OR_LEFT);
        westHeader.setTextAntiAlias(com.miginfocom.util.gfx.GfxUtil.AA_HINT_ON);

    }

    private void setDateHeaderProperties(DateHeaderBean weekDateHeader) {
        weekDateHeader
                .setHeaderRows(new com.miginfocom.calendar.header.CellDecorationRow[] { new com.miginfocom.calendar.header.CellDecorationRow(
                        com.miginfocom.util.dates.DateRangeI.RANGE_TYPE_DAY,
                        new com.miginfocom.util.dates.DateFormatList("EEE', 'MMM' 'dd", null),
                        new com.miginfocom.util.gfx.geometry.numbers.AtFixed(25.0f),
                        new com.miginfocom.util.gfx.geometry.AbsRect(new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f),
                                new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f),
                                new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f),
                                new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), null, null, null),
                        (java.awt.Paint[]) new java.awt.Paint[] { new com.miginfocom.util.gfx.ShapeGradientPaint(
                                new java.awt.Color(234, 234, 234), new java.awt.Color(255, 255, 255), 90.0f, 0.5f, 0.25f, true) },
                        new java.awt.Paint[] { new java.awt.Color(102, 102, 102) },
                        new com.miginfocom.util.repetition.DefaultRepetition(0, 1, null, null),
                        new java.awt.Font[] { new java.awt.Font("Verdana", 0, 10) }, new java.lang.Integer[] { null },
                        new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f),
                        new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f)) });
        weekDateHeader.setBackgroundPaint(new java.awt.Color(204, 204, 204));

    }

    @SuppressWarnings("rawtypes")
    public void addTestData() {
        final String[] TITLES = { "Going to the Gym", "Meeting with the Board", "Taking Mick to Ice Hockey",
                "Lunch with Susanne", "Lunch with Matt", "Meeting about the Site", "Template work", "Fix the car",
                "Take the car to the shop", "Call Chris about the fishing trip", "Major cleaning", "Refactoring some code" };

        final String[] DESCRIPTIONS = { "This is a standard description of the event. It conveys details and notes and can be any text." };

        final Object homeID = 10;
        final Object workID = 11;

        final Object[] CATEGORIES = { homeID, workID };

        DateRange genRange = new DateRange();
        genRange.setSize(DateRangeI.RANGE_TYPE_WEEK, 10, AtFraction.CENTER);

        long startMillis = genRange.getStartMillis();
        long endMillis = genRange.getEndMillis();
        ImmutableDateRange dr = new ImmutableDateRange(startMillis, endMillis, false, null, null);

        // Whole Day activities
        ArrayList acts = ActivityHelper.createActivities(dr, TITLES, DESCRIPTIONS, CATEGORIES, null, 24 * 60 * 60000,
                -2 * 24 * 60000, 6 * 24 * 60 * 60000, 24 * 60 * 60000, 4 * 24 * 60 * 60000, 300, false, null);
        ActivityDepository.getInstance().addBrokedActivities(acts, this, TimeSpanListEvent.ADDED_CREATED);

        // Short activities
        acts = ActivityHelper.createActivities(dr, TITLES, DESCRIPTIONS, CATEGORIES, null, 60 * 60000, 6 * 60 * 60000,
                24 * 60 * 60000, 60 * 60000, 4 * 60 * 60000, 10000, true, "FlexGrid");
        ActivityDepository.getInstance().addBrokedActivities(acts, this, TimeSpanListEvent.ADDED_CREATED);

        // Create mark's recurring lunch.
        startMillis = new GregorianCalendar(2006, 6, 4, 11, 0).getTimeInMillis();
        endMillis = new GregorianCalendar(2006, 6, 4, 12, 0).getTimeInMillis();
        dr = new ImmutableDateRange(startMillis, endMillis, true, null, null);
        RecurrenceRule rr = new RecurrenceRule(Calendar.DAY_OF_YEAR, 1);
        rr.addByXXXRule(new ByXXXRuleData(Calendar.DAY_OF_WEEK, new int[] { Calendar.MONDAY, Calendar.TUESDAY,
                Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY }));
        Activity act = new DefaultActivity(dr, "mlunch");
        act.setRecurrence(rr);
        act.setSummary("Mark's Lunch");
        act.addCategoryID(homeID, 0);
        act.setLayoutContext("FlexGrid");
        ActivityDepository.getInstance().addBrokedActivity(act, this, TimeSpanListEvent.ADDED_CREATED);

    }

    @Override
    public void animateTransition(TipiEvent te, TipiExecutable executableParent, List<TipiExecutable> exe, int duration)
            throws TipiBreakException {
        mySwingTipiContext.animateDefaultTransition(this, te, executableParent, myCalendar.getParent(), exe, duration);
    }

    @Override
    protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
        runSyncInEventThread(new Runnable() {
            @Override
            public void run() {
                // doPerformMethod(name, compMeth);
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
