/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.core.impl.dom;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSS2Properties;

/**
 * w3c {@link CSS2Properties} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSS2PropertiesImpl implements CSS2Properties {

	protected Object widget;

	protected CSSEngine engine;

	public CSS2PropertiesImpl(Object widget, CSSEngine engine) {
		this.widget = widget;
		this.engine = engine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getAzimuth()
	 */
	public String getAzimuth() {
		return engine.retrieveCSSProperty(widget, "azimut");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackground()
	 */
	public String getBackground() {
		return engine.retrieveCSSProperty(widget, "background");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundAttachment()
	 */
	public String getBackgroundAttachment() {
		return engine.retrieveCSSProperty(widget, "background-attachment");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundColor()
	 */
	public String getBackgroundColor() {
		return engine.retrieveCSSProperty(widget, "background-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundImage()
	 */
	public String getBackgroundImage() {
		return engine.retrieveCSSProperty(widget, "background-image");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundPosition()
	 */
	public String getBackgroundPosition() {
		return engine.retrieveCSSProperty(widget, "background-position");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundRepeat()
	 */
	public String getBackgroundRepeat() {
		return engine.retrieveCSSProperty(widget, "background-repeat");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorder()
	 */
	public String getBorder() {
		return engine.retrieveCSSProperty(widget, "border");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottom()
	 */
	public String getBorderBottom() {
		return engine.retrieveCSSProperty(widget, "border-bottom");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottomColor()
	 */
	public String getBorderBottomColor() {
		return engine.retrieveCSSProperty(widget, "border-bottom-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottomStyle()
	 */
	public String getBorderBottomStyle() {
		return engine.retrieveCSSProperty(widget, "border-bottom-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottomWidth()
	 */
	public String getBorderBottomWidth() {
		return engine.retrieveCSSProperty(widget, "border-bottom-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderCollapse()
	 */
	public String getBorderCollapse() {
		return engine.retrieveCSSProperty(widget, "border-collapse");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderColor()
	 */
	public String getBorderColor() {
		return engine.retrieveCSSProperty(widget, "border-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeft()
	 */
	public String getBorderLeft() {
		return engine.retrieveCSSProperty(widget, "border-left");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeftColor()
	 */
	public String getBorderLeftColor() {
		return engine.retrieveCSSProperty(widget, "border-left-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeftStyle()
	 */
	public String getBorderLeftStyle() {
		return engine.retrieveCSSProperty(widget, "border-left-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeftWidth()
	 */
	public String getBorderLeftWidth() {
		return engine.retrieveCSSProperty(widget, "border-left-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRight()
	 */
	public String getBorderRight() {
		return engine.retrieveCSSProperty(widget, "border-right");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRightColor()
	 */
	public String getBorderRightColor() {
		return engine.retrieveCSSProperty(widget, "border-right-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRightStyle()
	 */
	public String getBorderRightStyle() {
		return engine.retrieveCSSProperty(widget, "border-right-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRightWidth()
	 */
	public String getBorderRightWidth() {
		return engine.retrieveCSSProperty(widget, "border-right-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderSpacing()
	 */
	public String getBorderSpacing() {
		return engine.retrieveCSSProperty(widget, "border-spacing");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderStyle()
	 */
	public String getBorderStyle() {
		return engine.retrieveCSSProperty(widget, "border-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTop()
	 */
	public String getBorderTop() {
		return engine.retrieveCSSProperty(widget, "border-top");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTopColor()
	 */
	public String getBorderTopColor() {
		return engine.retrieveCSSProperty(widget, "border-top-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTopStyle()
	 */
	public String getBorderTopStyle() {
		return engine.retrieveCSSProperty(widget, "border-top-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTopWidth()
	 */
	public String getBorderTopWidth() {
		return engine.retrieveCSSProperty(widget, "border-top-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderWidth()
	 */
	public String getBorderWidth() {
		return engine.retrieveCSSProperty(widget, "border-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBottom()
	 */
	public String getBottom() {
		return engine.retrieveCSSProperty(widget, "border-bottom");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCaptionSide()
	 */
	public String getCaptionSide() {
		return engine.retrieveCSSProperty(widget, "caption-side");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getClear()
	 */
	public String getClear() {
		return engine.retrieveCSSProperty(widget, "clear");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getClip()
	 */
	public String getClip() {
		return engine.retrieveCSSProperty(widget, "clip");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getColor()
	 */
	public String getColor() {
		return engine.retrieveCSSProperty(widget, "color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getContent()
	 */
	public String getContent() {
		return engine.retrieveCSSProperty(widget, "content");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCounterIncrement()
	 */
	public String getCounterIncrement() {
		return engine.retrieveCSSProperty(widget, "counter-increment");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCounterReset()
	 */
	public String getCounterReset() {
		return engine.retrieveCSSProperty(widget, "counter-reset");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCssFloat()
	 */
	public String getCssFloat() {
		return engine.retrieveCSSProperty(widget, "float");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCue()
	 */
	public String getCue() {
		return engine.retrieveCSSProperty(widget, "cue");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCueAfter()
	 */
	public String getCueAfter() {
		return engine.retrieveCSSProperty(widget, "cue-after");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCueBefore()
	 */
	public String getCueBefore() {
		return engine.retrieveCSSProperty(widget, "cue-before");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCursor()
	 */
	public String getCursor() {
		return engine.retrieveCSSProperty(widget, "cursor");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getDirection()
	 */
	public String getDirection() {
		return engine.retrieveCSSProperty(widget, "direction");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getDisplay()
	 */
	public String getDisplay() {
		return engine.retrieveCSSProperty(widget, "display");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getElevation()
	 */
	public String getElevation() {
		return engine.retrieveCSSProperty(widget, "elevation");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getEmptyCells()
	 */
	public String getEmptyCells() {
		return engine.retrieveCSSProperty(widget, "empty-cells");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFont()
	 */
	public String getFont() {
		return engine.retrieveCSSProperty(widget, "font");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontFamily()
	 */
	public String getFontFamily() {
		return engine.retrieveCSSProperty(widget, "font-family");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontSize()
	 */
	public String getFontSize() {
		return engine.retrieveCSSProperty(widget, "font-size");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontSizeAdjust()
	 */
	public String getFontSizeAdjust() {
		return engine.retrieveCSSProperty(widget, "font_size-adjust");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontStretch()
	 */
	public String getFontStretch() {
		return engine.retrieveCSSProperty(widget, "font-stretch");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontStyle()
	 */
	public String getFontStyle() {
		return engine.retrieveCSSProperty(widget, "font-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontVariant()
	 */
	public String getFontVariant() {
		return engine.retrieveCSSProperty(widget, "font-variant");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontWeight()
	 */
	public String getFontWeight() {
		return engine.retrieveCSSProperty(widget, "font-weight");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getHeight()
	 */
	public String getHeight() {
		return engine.retrieveCSSProperty(widget, "height");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getLeft()
	 */
	public String getLeft() {
		return engine.retrieveCSSProperty(widget, "left");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getLetterSpacing()
	 */
	public String getLetterSpacing() {
		return engine.retrieveCSSProperty(widget, "letter-spacing");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getLineHeight()
	 */
	public String getLineHeight() {
		return engine.retrieveCSSProperty(widget, "line-height");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStyle()
	 */
	public String getListStyle() {
		return engine.retrieveCSSProperty(widget, "list-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStyleImage()
	 */
	public String getListStyleImage() {
		return engine.retrieveCSSProperty(widget, "list-style-image");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStylePosition()
	 */
	public String getListStylePosition() {
		return engine.retrieveCSSProperty(widget, "list-style-position");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStyleType()
	 */
	public String getListStyleType() {
		return engine.retrieveCSSProperty(widget, "list-style-type");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMargin()
	 */
	public String getMargin() {
		return engine.retrieveCSSProperty(widget, "margin");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginBottom()
	 */
	public String getMarginBottom() {
		return engine.retrieveCSSProperty(widget, "margin-bottom");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginLeft()
	 */
	public String getMarginLeft() {
		return engine.retrieveCSSProperty(widget, "margin-left");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginRight()
	 */
	public String getMarginRight() {
		return engine.retrieveCSSProperty(widget, "margin-right");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginTop()
	 */
	public String getMarginTop() {
		return engine.retrieveCSSProperty(widget, "margin-top");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarkerOffset()
	 */
	public String getMarkerOffset() {
		return engine.retrieveCSSProperty(widget, "marker-offset");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarks()
	 */
	public String getMarks() {
		return engine.retrieveCSSProperty(widget, "marks");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMaxHeight()
	 */
	public String getMaxHeight() {
		return engine.retrieveCSSProperty(widget, "max-height");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMaxWidth()
	 */
	public String getMaxWidth() {
		return engine.retrieveCSSProperty(widget, "max-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMinHeight()
	 */
	public String getMinHeight() {
		return engine.retrieveCSSProperty(widget, "min-height");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMinWidth()
	 */
	public String getMinWidth() {
		return engine.retrieveCSSProperty(widget, "min-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOrphans()
	 */
	public String getOrphans() {
		return engine.retrieveCSSProperty(widget, "orphans");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutline()
	 */
	public String getOutline() {
		return engine.retrieveCSSProperty(widget, "outline");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutlineColor()
	 */
	public String getOutlineColor() {
		return engine.retrieveCSSProperty(widget, "outline-color");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutlineStyle()
	 */
	public String getOutlineStyle() {
		return engine.retrieveCSSProperty(widget, "outline-style");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutlineWidth()
	 */
	public String getOutlineWidth() {
		return engine.retrieveCSSProperty(widget, "outline-width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOverflow()
	 */
	public String getOverflow() {
		return engine.retrieveCSSProperty(widget, "overflow");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPadding()
	 */
	public String getPadding() {
		return engine.retrieveCSSProperty(widget, "padding");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingBottom()
	 */
	public String getPaddingBottom() {
		return engine.retrieveCSSProperty(widget, "padding-bottom");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingLeft()
	 */
	public String getPaddingLeft() {
		return engine.retrieveCSSProperty(widget, "padding-left");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingRight()
	 */
	public String getPaddingRight() {
		return engine.retrieveCSSProperty(widget, "padding-right");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingTop()
	 */
	public String getPaddingTop() {
		return engine.retrieveCSSProperty(widget, "padding-top");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPage()
	 */
	public String getPage() {
		return engine.retrieveCSSProperty(widget, "page");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPageBreakAfter()
	 */
	public String getPageBreakAfter() {
		return engine.retrieveCSSProperty(widget, "page-break-after");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPageBreakBefore()
	 */
	public String getPageBreakBefore() {
		return engine.retrieveCSSProperty(widget, "page-break-before");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPageBreakInside()
	 */
	public String getPageBreakInside() {
		return engine.retrieveCSSProperty(widget, "page-break-inside");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPause()
	 */
	public String getPause() {
		return engine.retrieveCSSProperty(widget, "pause");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPauseAfter()
	 */
	public String getPauseAfter() {
		return engine.retrieveCSSProperty(widget, "pause-after");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPauseBefore()
	 */
	public String getPauseBefore() {
		return engine.retrieveCSSProperty(widget, "pause-before");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPitch()
	 */
	public String getPitch() {
		return engine.retrieveCSSProperty(widget, "pitch");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPitchRange()
	 */
	public String getPitchRange() {
		return engine.retrieveCSSProperty(widget, "pitch-range");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPlayDuring()
	 */
	public String getPlayDuring() {
		return engine.retrieveCSSProperty(widget, "play-during");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPosition()
	 */
	public String getPosition() {
		return engine.retrieveCSSProperty(widget, "position");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getQuotes()
	 */
	public String getQuotes() {
		return engine.retrieveCSSProperty(widget, "quotes");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getRichness()
	 */
	public String getRichness() {
		return engine.retrieveCSSProperty(widget, "richness");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getRight()
	 */
	public String getRight() {
		return engine.retrieveCSSProperty(widget, "right");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSize()
	 */
	public String getSize() {
		return engine.retrieveCSSProperty(widget, "size");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeak()
	 */
	public String getSpeak() {
		return engine.retrieveCSSProperty(widget, "speak");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeakHeader()
	 */
	public String getSpeakHeader() {
		return engine.retrieveCSSProperty(widget, "speak-header");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeakNumeral()
	 */
	public String getSpeakNumeral() {
		return engine.retrieveCSSProperty(widget, "speak-numeral");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeakPunctuation()
	 */
	public String getSpeakPunctuation() {
		return engine.retrieveCSSProperty(widget, "speak-punctuation");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeechRate()
	 */
	public String getSpeechRate() {
		return engine.retrieveCSSProperty(widget, "speech-rate");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getStress()
	 */
	public String getStress() {
		return engine.retrieveCSSProperty(widget, "stress");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTableLayout()
	 */
	public String getTableLayout() {
		return engine.retrieveCSSProperty(widget, "table-layout");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextAlign()
	 */
	public String getTextAlign() {
		return engine.retrieveCSSProperty(widget, "text-align");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextDecoration()
	 */
	public String getTextDecoration() {
		return engine.retrieveCSSProperty(widget, "text-decoration");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextIndent()
	 */
	public String getTextIndent() {
		return engine.retrieveCSSProperty(widget, "text-indent");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextShadow()
	 */
	public String getTextShadow() {
		return engine.retrieveCSSProperty(widget, "text-shadow");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextTransform()
	 */
	public String getTextTransform() {
		return engine.retrieveCSSProperty(widget, "text-transform");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTop()
	 */
	public String getTop() {
		return engine.retrieveCSSProperty(widget, "top");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getUnicodeBidi()
	 */
	public String getUnicodeBidi() {
		return engine.retrieveCSSProperty(widget, "unicode-bidi");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVerticalAlign()
	 */
	public String getVerticalAlign() {
		return engine.retrieveCSSProperty(widget, "vertical-align");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVisibility()
	 */
	public String getVisibility() {
		return engine.retrieveCSSProperty(widget, "visibility");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVoiceFamily()
	 */
	public String getVoiceFamily() {
		return engine.retrieveCSSProperty(widget, "voice-family");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVolume()
	 */
	public String getVolume() {
		return engine.retrieveCSSProperty(widget, "volume");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWhiteSpace()
	 */
	public String getWhiteSpace() {
		return engine.retrieveCSSProperty(widget, "white-space");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWidows()
	 */
	public String getWidows() {
		return engine.retrieveCSSProperty(widget, "widows");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWidth()
	 */
	public String getWidth() {
		return engine.retrieveCSSProperty(widget, "width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWordSpacing()
	 */
	public String getWordSpacing() {
		return engine.retrieveCSSProperty(widget, "word-spacing");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getZIndex()
	 */
	public String getZIndex() {
		return engine.retrieveCSSProperty(widget, "z-index");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setAzimuth(java.lang.String)
	 */
	public void setAzimuth(String azimuth) throws DOMException {
		parseAndApplyStyle("azimuth", azimuth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackground(java.lang.String)
	 */
	public void setBackground(String background) throws DOMException {
		parseAndApplyStyle("background", background);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundAttachment(java.lang.String)
	 */
	public void setBackgroundAttachment(String backgroundAttachment)
			throws DOMException {
		parseAndApplyStyle("background-attachment", backgroundAttachment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundColor(java.lang.String)
	 */
	public void setBackgroundColor(String backgroundColor) throws DOMException {
		parseAndApplyStyle("background-color", backgroundColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundImage(java.lang.String)
	 */
	public void setBackgroundImage(String backgroundImage) throws DOMException {
		parseAndApplyStyle("background-image", backgroundImage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundPosition(java.lang.String)
	 */
	public void setBackgroundPosition(String backgroundPosition)
			throws DOMException {
		parseAndApplyStyle("background-position", backgroundPosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundRepeat(java.lang.String)
	 */
	public void setBackgroundRepeat(String backgroundRepeat)
			throws DOMException {
		parseAndApplyStyle("background-repeat", backgroundRepeat);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorder(java.lang.String)
	 */
	public void setBorder(String border) throws DOMException {
		parseAndApplyStyle("border", border);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderBottom(java.lang.String)
	 */
	public void setBorderBottom(String borderBottom) throws DOMException {
		parseAndApplyStyle("border-bottom", borderBottom);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderBottomColor(java.lang.String)
	 */
	public void setBorderBottomColor(String borderColor) throws DOMException {
		parseAndApplyStyle("border-color", borderColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderBottomStyle(java.lang.String)
	 */
	public void setBorderBottomStyle(String borderBottomStyle)
			throws DOMException {
		parseAndApplyStyle("border-bottom-style", borderBottomStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderBottomWidth(java.lang.String)
	 */
	public void setBorderBottomWidth(String borderBottomWidth)
			throws DOMException {
		parseAndApplyStyle("border-bottom-width", borderBottomWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderCollapse(java.lang.String)
	 */
	public void setBorderCollapse(String borderCollapse) throws DOMException {
		parseAndApplyStyle("border-collapse", borderCollapse);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderColor(java.lang.String)
	 */
	public void setBorderColor(String borderColor) throws DOMException {
		parseAndApplyStyle("border-color", borderColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeft(java.lang.String)
	 */
	public void setBorderLeft(String borderLeft) throws DOMException {
		parseAndApplyStyle("border-left", borderLeft);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeftColor(java.lang.String)
	 */
	public void setBorderLeftColor(String borderLeftColor) throws DOMException {
		parseAndApplyStyle("border-left-color", borderLeftColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeftStyle(java.lang.String)
	 */
	public void setBorderLeftStyle(String borderLeftStyle) throws DOMException {
		parseAndApplyStyle("border-left-style", borderLeftStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeftWidth(java.lang.String)
	 */
	public void setBorderLeftWidth(String borderLeftWidth) throws DOMException {
		parseAndApplyStyle("border-left-width", borderLeftWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRight(java.lang.String)
	 */
	public void setBorderRight(String borderRight) throws DOMException {
		parseAndApplyStyle("border-right", borderRight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRightColor(java.lang.String)
	 */
	public void setBorderRightColor(String borderRightColor)
			throws DOMException {
		parseAndApplyStyle("border-right-color", borderRightColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRightStyle(java.lang.String)
	 */
	public void setBorderRightStyle(String borderRightStyle)
			throws DOMException {
		parseAndApplyStyle("border-right-style", borderRightStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRightWidth(java.lang.String)
	 */
	public void setBorderRightWidth(String borderRightWidth)
			throws DOMException {
		parseAndApplyStyle("border-right-width", borderRightWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderSpacing(java.lang.String)
	 */
	public void setBorderSpacing(String borderSpacing) throws DOMException {
		parseAndApplyStyle("border-spacing", borderSpacing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderStyle(java.lang.String)
	 */
	public void setBorderStyle(String borderStyle) throws DOMException {
		parseAndApplyStyle("border-style", borderStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTop(java.lang.String)
	 */
	public void setBorderTop(String borderTop) throws DOMException {
		parseAndApplyStyle("border-top", borderTop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTopColor(java.lang.String)
	 */
	public void setBorderTopColor(String borderTopColor) throws DOMException {
		parseAndApplyStyle("border-top-color", borderTopColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTopStyle(java.lang.String)
	 */
	public void setBorderTopStyle(String borderTopStyle) throws DOMException {
		parseAndApplyStyle("border-top-style", borderTopStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTopWidth(java.lang.String)
	 */
	public void setBorderTopWidth(String borderTopWidth) throws DOMException {
		parseAndApplyStyle("border-top-width", borderTopWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderWidth(java.lang.String)
	 */
	public void setBorderWidth(String borderWidth) throws DOMException {
		parseAndApplyStyle("border-width", borderWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBottom(java.lang.String)
	 */
	public void setBottom(String bottom) throws DOMException {
		parseAndApplyStyle("bottom", bottom);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCaptionSide(java.lang.String)
	 */
	public void setCaptionSide(String captionSide) throws DOMException {
		parseAndApplyStyle("caption-side", captionSide);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setClear(java.lang.String)
	 */
	public void setClear(String clear) throws DOMException {
		parseAndApplyStyle("clear", clear);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setClip(java.lang.String)
	 */
	public void setClip(String clip) throws DOMException {
		parseAndApplyStyle("clip", clip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setColor(java.lang.String)
	 */
	public void setColor(String color) throws DOMException {
		parseAndApplyStyle("color", color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setContent(java.lang.String)
	 */
	public void setContent(String content) throws DOMException {
		parseAndApplyStyle("content", content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCounterIncrement(java.lang.String)
	 */
	public void setCounterIncrement(String counterIncrement)
			throws DOMException {
		parseAndApplyStyle("counter-increment", counterIncrement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCounterReset(java.lang.String)
	 */
	public void setCounterReset(String counterReset) throws DOMException {
		parseAndApplyStyle("counter-reset", counterReset);
	}

	public void setCssFloat(String cssFloat) throws DOMException {
		parseAndApplyStyle("float", cssFloat);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCue(java.lang.String)
	 */
	public void setCue(String cue) throws DOMException {
		parseAndApplyStyle("cue", cue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCueAfter(java.lang.String)
	 */
	public void setCueAfter(String cueAfter) throws DOMException {
		parseAndApplyStyle("cue-after", cueAfter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCueBefore(java.lang.String)
	 */
	public void setCueBefore(String cueBefore) throws DOMException {
		parseAndApplyStyle("cue-before", cueBefore);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCursor(java.lang.String)
	 */
	public void setCursor(String cursor) throws DOMException {
		parseAndApplyStyle("cursor", cursor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setDirection(java.lang.String)
	 */
	public void setDirection(String direction) throws DOMException {
		parseAndApplyStyle("direction", direction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setDisplay(java.lang.String)
	 */
	public void setDisplay(String display) throws DOMException {
		parseAndApplyStyle("display", display);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setElevation(java.lang.String)
	 */
	public void setElevation(String elevation) throws DOMException {
		parseAndApplyStyle("elevation", elevation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setEmptyCells(java.lang.String)
	 */
	public void setEmptyCells(String emptyCells) throws DOMException {
		parseAndApplyStyle("empty-cells", emptyCells);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFont(java.lang.String)
	 */
	public void setFont(String font) throws DOMException {
		parseAndApplyStyle("font", font);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontFamily(java.lang.String)
	 */
	public void setFontFamily(String fontFamily) throws DOMException {
		parseAndApplyStyle("font-family", fontFamily);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontSize(java.lang.String)
	 */
	public void setFontSize(String fontSize) throws DOMException {
		parseAndApplyStyle("font-size", fontSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontSizeAdjust(java.lang.String)
	 */
	public void setFontSizeAdjust(String fontSizeAdjust) throws DOMException {
		parseAndApplyStyle("font-size-adjust", fontSizeAdjust);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontStretch(java.lang.String)
	 */
	public void setFontStretch(String fontStretch) throws DOMException {
		parseAndApplyStyle("font-stretch", fontStretch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontStyle(java.lang.String)
	 */
	public void setFontStyle(String fontStyle) throws DOMException {
		parseAndApplyStyle("font-style", fontStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontVariant(java.lang.String)
	 */
	public void setFontVariant(String fontVariant) throws DOMException {
		parseAndApplyStyle("font-variant", fontVariant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontWeight(java.lang.String)
	 */
	public void setFontWeight(String fontWeight) throws DOMException {
		parseAndApplyStyle("font-weight", fontWeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setHeight(java.lang.String)
	 */
	public void setHeight(String height) throws DOMException {
		parseAndApplyStyle("height", height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setLeft(java.lang.String)
	 */
	public void setLeft(String left) throws DOMException {
		parseAndApplyStyle("left", left);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setLetterSpacing(java.lang.String)
	 */
	public void setLetterSpacing(String letterSpacing) throws DOMException {
		parseAndApplyStyle("letter-spacing", letterSpacing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setLineHeight(java.lang.String)
	 */
	public void setLineHeight(String lineHeight) throws DOMException {
		parseAndApplyStyle("line-height", lineHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStyle(java.lang.String)
	 */
	public void setListStyle(String listStyle) throws DOMException {
		parseAndApplyStyle("list-style", listStyle);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStyleImage(java.lang.String)
	 */
	public void setListStyleImage(String listStyleImage) throws DOMException {
		parseAndApplyStyle("list-style-image", listStyleImage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStylePosition(java.lang.String)
	 */
	public void setListStylePosition(String listStylePosition)
			throws DOMException {
		parseAndApplyStyle("list-style-position", listStylePosition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStyleType(java.lang.String)
	 */
	public void setListStyleType(String listStyleType) throws DOMException {
		parseAndApplyStyle("list-style-type", listStyleType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMargin(java.lang.String)
	 */
	public void setMargin(String margin) throws DOMException {
		parseAndApplyStyle("margin", margin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginBottom(java.lang.String)
	 */
	public void setMarginBottom(String marginBottom) throws DOMException {
		parseAndApplyStyle("margin-bottom", marginBottom);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginLeft(java.lang.String)
	 */
	public void setMarginLeft(String marginLeft) throws DOMException {
		parseAndApplyStyle("margin-left", marginLeft);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginRight(java.lang.String)
	 */
	public void setMarginRight(String marginRight) throws DOMException {
		parseAndApplyStyle("margin-right", marginRight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginTop(java.lang.String)
	 */
	public void setMarginTop(String marginTop) throws DOMException {
		parseAndApplyStyle("margin-top", marginTop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarkerOffset(java.lang.String)
	 */
	public void setMarkerOffset(String markerOffset) throws DOMException {
		parseAndApplyStyle("marker-offset", markerOffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarks(java.lang.String)
	 */
	public void setMarks(String marks) throws DOMException {
		parseAndApplyStyle("marks", marks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMaxHeight(java.lang.String)
	 */
	public void setMaxHeight(String maxHeight) throws DOMException {
		parseAndApplyStyle("max-height", maxHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMaxWidth(java.lang.String)
	 */
	public void setMaxWidth(String maxWidth) throws DOMException {
		parseAndApplyStyle("max-width", maxWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMinHeight(java.lang.String)
	 */
	public void setMinHeight(String minHeight) throws DOMException {
		parseAndApplyStyle("min-height", minHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMinWidth(java.lang.String)
	 */
	public void setMinWidth(String minWidth) throws DOMException {
		parseAndApplyStyle("min-width", minWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOrphans(java.lang.String)
	 */
	public void setOrphans(String orphans) throws DOMException {
		parseAndApplyStyle("orphans", orphans);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutline(java.lang.String)
	 */
	public void setOutline(String outline) throws DOMException {
		parseAndApplyStyle("outline", outline);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutlineColor(java.lang.String)
	 */
	public void setOutlineColor(String outlineColor) throws DOMException {
		parseAndApplyStyle("outline-color", outlineColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutlineStyle(java.lang.String)
	 */
	public void setOutlineStyle(String outlineStyle) throws DOMException {
		parseAndApplyStyle("outline-style", outlineStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutlineWidth(java.lang.String)
	 */
	public void setOutlineWidth(String outlineWidth) throws DOMException {
		parseAndApplyStyle("outline-width", outlineWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOverflow(java.lang.String)
	 */
	public void setOverflow(String overflow) throws DOMException {
		parseAndApplyStyle("overflow", overflow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPadding(java.lang.String)
	 */
	public void setPadding(String padding) throws DOMException {
		parseAndApplyStyle("padding", padding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingBottom(java.lang.String)
	 */
	public void setPaddingBottom(String paddingBottom) throws DOMException {
		parseAndApplyStyle("padding-bottom", paddingBottom);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingLeft(java.lang.String)
	 */
	public void setPaddingLeft(String paddingLeft) throws DOMException {
		parseAndApplyStyle("padding-left", paddingLeft);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingRight(java.lang.String)
	 */
	public void setPaddingRight(String paddingRight) throws DOMException {
		parseAndApplyStyle("padding-right", paddingRight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingTop(java.lang.String)
	 */
	public void setPaddingTop(String paddingTop) throws DOMException {
		parseAndApplyStyle("padding-top", paddingTop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPage(java.lang.String)
	 */
	public void setPage(String page) throws DOMException {
		parseAndApplyStyle("page", page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPageBreakAfter(java.lang.String)
	 */
	public void setPageBreakAfter(String pageBreakAfter) throws DOMException {
		parseAndApplyStyle("page-break-after", pageBreakAfter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPageBreakBefore(java.lang.String)
	 */
	public void setPageBreakBefore(String pageBreakBefore) throws DOMException {
		parseAndApplyStyle("page-break-before", pageBreakBefore);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPageBreakInside(java.lang.String)
	 */
	public void setPageBreakInside(String pageBreakInside) throws DOMException {
		parseAndApplyStyle("page-break-inside", pageBreakInside);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPause(java.lang.String)
	 */
	public void setPause(String pause) throws DOMException {
		parseAndApplyStyle("pause", pause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPauseAfter(java.lang.String)
	 */
	public void setPauseAfter(String pauseAfter) throws DOMException {
		parseAndApplyStyle("pause-after", pauseAfter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPauseBefore(java.lang.String)
	 */
	public void setPauseBefore(String pauseBefore) throws DOMException {
		parseAndApplyStyle("pause-before", pauseBefore);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPitch(java.lang.String)
	 */
	public void setPitch(String pitch) throws DOMException {
		parseAndApplyStyle("pitch", pitch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPitchRange(java.lang.String)
	 */
	public void setPitchRange(String pitchRange) throws DOMException {
		parseAndApplyStyle("pitch-range", pitchRange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPlayDuring(java.lang.String)
	 */
	public void setPlayDuring(String playDuring) throws DOMException {
		parseAndApplyStyle("playDuring", playDuring);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPosition(java.lang.String)
	 */
	public void setPosition(String position) throws DOMException {
		parseAndApplyStyle("position", position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setQuotes(java.lang.String)
	 */
	public void setQuotes(String quotes) throws DOMException {
		parseAndApplyStyle("quotes", quotes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setRichness(java.lang.String)
	 */
	public void setRichness(String richness) throws DOMException {
		parseAndApplyStyle("richness", richness);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setRight(java.lang.String)
	 */
	public void setRight(String right) throws DOMException {
		parseAndApplyStyle("right", right);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSize(java.lang.String)
	 */
	public void setSize(String size) throws DOMException {
		parseAndApplyStyle("size", size);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeak(java.lang.String)
	 */
	public void setSpeak(String speak) throws DOMException {
		parseAndApplyStyle("speak", speak);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeakHeader(java.lang.String)
	 */
	public void setSpeakHeader(String speakHeader) throws DOMException {
		parseAndApplyStyle("speak-header", speakHeader);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeakNumeral(java.lang.String)
	 */
	public void setSpeakNumeral(String speakNumeral) throws DOMException {
		parseAndApplyStyle("speak-numeral", speakNumeral);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeakPunctuation(java.lang.String)
	 */
	public void setSpeakPunctuation(String speakPunctuation)
			throws DOMException {
		parseAndApplyStyle("speak-punctuation", speakPunctuation);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeechRate(java.lang.String)
	 */
	public void setSpeechRate(String speechRate) throws DOMException {
		parseAndApplyStyle("speech-rate", speechRate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setStress(java.lang.String)
	 */
	public void setStress(String stress) throws DOMException {
		parseAndApplyStyle("stress", stress);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTableLayout(java.lang.String)
	 */
	public void setTableLayout(String tableLayout) throws DOMException {
		parseAndApplyStyle("table-layout", tableLayout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextAlign(java.lang.String)
	 */
	public void setTextAlign(String textAlign) throws DOMException {
		parseAndApplyStyle("text-align", textAlign);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextDecoration(java.lang.String)
	 */
	public void setTextDecoration(String textDecoration) throws DOMException {
		parseAndApplyStyle("text-decoration", textDecoration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextIndent(java.lang.String)
	 */
	public void setTextIndent(String textIndent) throws DOMException {
		parseAndApplyStyle("text-indent", textIndent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextShadow(java.lang.String)
	 */
	public void setTextShadow(String textShadow) throws DOMException {
		parseAndApplyStyle("text-shadow", textShadow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextTransform(java.lang.String)
	 */
	public void setTextTransform(String textTransform) throws DOMException {
		parseAndApplyStyle("text-transform", textTransform);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTop(java.lang.String)
	 */
	public void setTop(String top) throws DOMException {
		parseAndApplyStyle("top", top);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setUnicodeBidi(java.lang.String)
	 */
	public void setUnicodeBidi(String unicodeBidi) throws DOMException {
		parseAndApplyStyle("unicode-bidi", unicodeBidi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVerticalAlign(java.lang.String)
	 */
	public void setVerticalAlign(String verticalAlign) throws DOMException {
		parseAndApplyStyle("vertical-align", verticalAlign);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVisibility(java.lang.String)
	 */
	public void setVisibility(String visibility) throws DOMException {
		parseAndApplyStyle("visibility", visibility);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVoiceFamily(java.lang.String)
	 */
	public void setVoiceFamily(String voiceFamily) throws DOMException {
		parseAndApplyStyle("voice-family", voiceFamily);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVolume(java.lang.String)
	 */
	public void setVolume(String volume) throws DOMException {
		parseAndApplyStyle("volume", volume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWhiteSpace(java.lang.String)
	 */
	public void setWhiteSpace(String whiteSpace) throws DOMException {
		parseAndApplyStyle("white-space", whiteSpace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWidows(java.lang.String)
	 */
	public void setWidows(String widows) throws DOMException {
		parseAndApplyStyle("widows", widows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWidth(java.lang.String)
	 */
	public void setWidth(String width) throws DOMException {
		parseAndApplyStyle("width", width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWordSpacing(java.lang.String)
	 */
	public void setWordSpacing(String wordSpacing) throws DOMException {
		parseAndApplyStyle("word-spacing", wordSpacing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setZIndex(java.lang.String)
	 */
	public void setZIndex(String zIndex) throws DOMException {
		parseAndApplyStyle("z-index", zIndex);
	}

	/**
	 * Parse and apply CSS property name <code>propertyName</code> with value
	 * <code>propertyValue</code> to the widget.
	 * 
	 * @param propertyName
	 * @param propertyValue
	 */
	protected void parseAndApplyStyle(String propertyName, String propertyValue) {
		try {
			String property = propertyName + ":" + propertyValue;
			engine.parseAndApplyStyleDeclaration(widget, property);
		} catch (Exception e) {
			throw new DOMException(DOMException.SYNTAX_ERR, e.getMessage());
		}
	}

}
