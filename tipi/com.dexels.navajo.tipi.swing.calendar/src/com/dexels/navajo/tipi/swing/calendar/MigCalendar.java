package com.dexels.navajo.tipi.swing.calendar;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

import com.miginfocom.ashape.shapes.TextAShape;
import com.miginfocom.beans.ActivityAShapeBean;
import com.miginfocom.beans.DateAreaBean;
import com.miginfocom.beans.DateHeaderBean;
import com.miginfocom.beans.DemoDataBean;
import com.miginfocom.beans.GridDimensionLayoutBean;
import com.miginfocom.calendar.datearea.DateAreaContainer;
import com.miginfocom.calendar.datearea.DefaultDateArea;
import com.miginfocom.calendar.decorators.GridLineDecorator;
import com.miginfocom.calendar.decorators.SubRowHeaderDecorator;
import com.miginfocom.calendar.header.DefaultSubRowLevel;
import com.miginfocom.calendar.header.SubRowGridHeader;
import com.miginfocom.calendar.layout.TimeBoundsLayout;
import com.miginfocom.util.dates.BoundaryRounder;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.gfx.GfxUtil;
import com.miginfocom.util.gfx.ShapeGradientPaint;
import com.miginfocom.util.gfx.geometry.AbsRect;
import com.miginfocom.util.gfx.geometry.numbers.AtEnd;
import com.miginfocom.util.gfx.geometry.numbers.AtFixed;
import com.miginfocom.util.gfx.geometry.numbers.AtFraction;
import com.miginfocom.util.gfx.geometry.numbers.AtStart;


public class MigCalendar extends DateAreaBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1904480531890842368L;

	
	public MigCalendar() {
		super();

		initialize();
	}
	
	
	private void initialize() {
		DateHeaderBean    northHeader = new com.miginfocom.beans.DateHeaderBean();
		GridDimensionLayoutBean    verticalLayout = new com.miginfocom.beans.GridDimensionLayoutBean();
		GridDimensionLayoutBean   horizontalLayout = new com.miginfocom.beans.GridDimensionLayoutBean();
		ActivityAShapeBean activityAShape = new com.miginfocom.beans.ActivityAShapeBean();

		DemoDataBean demoData = new com.miginfocom.beans.DemoDataBean();
		

        demoData.setGapMinutesMax(2000);
        demoData.setLengthMinutesMax(4000);
        demoData.setOnlyDesignTime(false);
        demoData.setRoundToMinutes(30);
        demoData.setEnabled(true);
        activityAShape.setBackground(new java.awt.Color(255, 229, 230));
        activityAShape.setCornerRadius(4.0);
        activityAShape.setOutlinePaint(new java.awt.Color(221, 178, 174));
        activityAShape.setRepaintPadding(new java.awt.Insets(10, 10, 10, 10));
        activityAShape.setResizeHandles(javax.swing.SwingConstants.HORIZONTAL);
        activityAShape.setTitleAlignY(new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f));
        activityAShape.setTitleFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        activityAShape.setTitleForeground(new java.awt.Color(166, 99, 99));
        activityAShape.setTitlePlaceRect(new com.miginfocom.util.gfx.geometry.AbsRect(new com.miginfocom.util.gfx.geometry.numbers.AtStart(2.0f), new com.miginfocom.util.gfx.geometry.numbers.AtStart(1.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), null, null, null));
        activityAShape.setTitleTemplate("$startTime$ $summary$");

		northHeader.setHeaderRows(new com.miginfocom.calendar.header.CellDecorationRow[] {
			    new com.miginfocom.calendar.header.CellDecorationRow(
			        com.miginfocom.util.dates.DateRangeI.RANGE_TYPE_MONTH,
			        new com.miginfocom.util.dates.DateFormatList("MMMM' 'yyyy", null),
			        new com.miginfocom.util.gfx.geometry.numbers.AtFixed(24.0f),
			        new com.miginfocom.util.gfx.geometry.AbsRect(new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), null, null, null),
			        (java.awt.Paint[]) new java.awt.Paint[] {new java.awt.Color(255, 255, 255)},
			        new java.awt.Paint[] {new java.awt.Color(0, 0, 0)},
			        null,
			        new java.awt.Font[] {new java.awt.Font("Dialog", 1, 13)},
			        new java.lang.Integer[] {null},
			        new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f),
			        new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f)),
			        	new com.miginfocom.calendar.header.CellDecorationRow(
			                com.miginfocom.util.dates.DateRangeI.RANGE_TYPE_DAY,
			                new com.miginfocom.util.dates.DateFormatList("EEEE", null),
			                new com.miginfocom.util.gfx.geometry.numbers.AtFixed(19.0f),
			                new com.miginfocom.util.gfx.geometry.AbsRect(new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), null, null, null),
			                (java.awt.Paint[]) null,
			                new java.awt.Paint[] {new java.awt.Color(70, 70, 70)},
			                new com.miginfocom.util.repetition.DefaultRepetition(0, 1, null, null),
			                new java.awt.Font[] {new java.awt.Font("Dialog", 0, 11)},
			                new java.lang.Integer[] {null},
			                new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f),
			                new com.miginfocom.util.gfx.geometry.numbers.AtFraction(0.5f))});
		
		northHeader.setBackgroundPaint(new java.awt.Color(204, 204, 204));
			

		verticalLayout.setSubRowSizeExpandedFolder(new com.miginfocom.util.gfx.geometry.SizeSpec(new com.miginfocom.util.gfx.geometry.numbers.AtFixed(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(0.0f)));
		verticalLayout.setSubRowSizeFoldedFolder(new com.miginfocom.util.gfx.geometry.SizeSpec(new com.miginfocom.util.gfx.geometry.numbers.AtFixed(30.0f), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(30.0f), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(30.0f)));
		verticalLayout.setSubRowSizeLeaf(new com.miginfocom.util.gfx.geometry.SizeSpec(new com.miginfocom.util.gfx.geometry.numbers.AtFixed(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(40.0f), new com.miginfocom.util.gfx.geometry.numbers.AtFixed(40.0f)));
		this.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory
				.createEmptyBorder(10, 10, 10, 10), javax.swing.BorderFactory.createMatteBorder(1,
				1, 1, 1, new java.awt.Color(204, 204, 204))));
		this.setNorthDateHeader(northHeader);
		
		this.setActivityLayouts(new com.miginfocom.calendar.layout.ActivityLayout[] {
				new com.miginfocom.calendar.layout.TimeBoundsLayout(new com.miginfocom.util.gfx.geometry.numbers.AtFixed(1.0f), new com.miginfocom.util.gfx.geometry.numbers.AtStart(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(0.0f), new com.miginfocom.util.gfx.geometry.numbers.AtStart(2.0f), new com.miginfocom.util.gfx.geometry.numbers.AtEnd(-2.0f), 2, null, null, new com.miginfocom.util.gfx.geometry.numbers.AtFixed(17.0f), new String[] {"TimeBounds"}, null)});

		this.setDemoDataBean(demoData);
		this.setCategoryRoot(demoData);
		this.setPrimaryDimensionCellTypeCount(1);
		this.setPrimaryDimensionLayout(horizontalLayout);
		this.setSecondaryDimensionLayout(verticalLayout);
		this.setVisibleDateRangeString("20140501T000000000-20140531T230000000");
		this.setWrapBoundary(null);
		this.setCategoryShowRoot(true);
		this.setDateAreaInnerBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
		this.setDesignTimeHelp(false);
		this.setNoExpandedFolderGridLine(true);
		
		this.setHorizontalGridLinePaintEven(new java.awt.Color(224, 224, 224));
		this.setHorizontalGridLinePaintOdd(new java.awt.Color(237, 237, 237));
		
		this.setVerticalGridLinePaintEven(new java.awt.Color(224, 224, 224));
		this.setVerticalGridLinePaintOdd(new java.awt.Color(224, 224, 224));
		
				
		
	}
}
