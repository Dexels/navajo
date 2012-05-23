package com.dexels.navajo.tipi.charting;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;

import javax.swing.JPanel;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.parsers.TipiGradientPaint;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiDialChart extends TipiChart {

	private static final long serialVersionUID = -5657434586825857241L;
	private JPanel myContainer;
	private DefaultValueDataset ds = new DefaultValueDataset(0);
	private int size = 200;
	private Paint paint;
	private String dataProperty;
	double minimum = 0d, maximum = 100d;
	private Animator anim;

	public Object createContainer() {
		super.createContainer();
		myContainer = new JPanel();
		Dimension sizeDim = new Dimension(size, size);
		myContainer.setMinimumSize(sizeDim);
		myContainer.setMaximumSize(sizeDim);
		myContainer.setPreferredSize(sizeDim);
		myContainer.setLayout(new BorderLayout());
		myContainer.add(myChart, BorderLayout.CENTER);

		JFreeChart chart = createChart(xAxis, message, title, label_x, label_y);
		myChart.setChart(chart);

		return myContainer;
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		Message m = n.getMessage(message);
		if (m != null) {
			Property p = m.getProperty(dataProperty);
			if (p != null) {
				ds.setValue((Number) p.getTypedValue());
			}
		}
	}

	public JFreeChart createChart(String nameProp, String messageName, String title, String xaxis, String yaxis) {
		try {			
			StandardDialFrame dialFrame = new StandardDialFrame();
			dialFrame.setForegroundPaint(Color.darkGray);
			dialFrame.setStroke(new BasicStroke(1.0f));

			DialPlot plot = new DialPlot(ds);

			plot.addPointer(new DialPointer.Pin());
			plot.setDialFrame(dialFrame);
			DialBackground background = new DialBackground();
			GradientPaint gp = new GradientPaint(0, 0, Color.white, 0, size, Color.decode("#BADA55"));
			if (paint == null) {
				paint = gp;
			}
			background.setPaint(paint);
			plot.setBackground(background);

			double range = maximum - minimum;
			double eighty_perc = 0.8 * range;

			StandardDialRange upperRange = new StandardDialRange(eighty_perc, maximum, Color.red);

			upperRange.setInnerRadius(0.90);
			upperRange.setOuterRadius(0.90);
			plot.addLayer(upperRange);

			DialValueIndicator dvi = new DialValueIndicator(0);
			dvi.setFont(new Font("Nimbus Sans L", Font.PLAIN, 10));
			dvi.setOutlinePaint(Color.black);
			plot.addLayer(dvi);

			DialCap cap = new DialCap();
			cap.setRadius(0.05);
			cap.setOutlinePaint(Color.black);
			cap.setFillPaint(Color.red);
			plot.setCap(cap);

			if (title != null) {
				DialTextAnnotation annotation1 = new DialTextAnnotation(title);
				annotation1.setFont(new Font("Nimbus Sans L", Font.BOLD, 14));
				annotation1.setRadius(0.6);
				plot.addLayer(annotation1);
			}

			StandardDialScale scale = new StandardDialScale(minimum, maximum, -135, -270, range / 10.0, 4);

			scale.setTickRadius(0.88);
			scale.setTickLabelOffset(0.25);
			scale.setTickLabelFont(new Font("Nimbus Sans L", Font.PLAIN, 14));
			scale.setTickLabelPaint(Color.black);
			plot.setOutlinePaint(Color.black);
			plot.addScale(0, scale);
			JFreeChart chart = new JFreeChart("", plot);

			return chart;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);

		if ("size".equals(name)) {
			this.size = Integer.parseInt(object.toString());
			Dimension sizeDim = new Dimension(size, size);
			myContainer.setMinimumSize(sizeDim);
			myContainer.setMaximumSize(sizeDim);
			myContainer.setPreferredSize(sizeDim);
		}
		if ("background".equals(name)) {
			if (object instanceof TipiGradientPaint) {
				TipiGradientPaint gp = (TipiGradientPaint) object;
				this.paint = gp.getPaint();
			}
		}
		if ("dataProperty".equals(name)) {
			this.dataProperty = object.toString();
		}
		if ("range_minimum".equals(name)) {
			this.minimum = (Double) object;
			JFreeChart chart = createChart(xAxis, message, title, label_x, label_y);
			myChart.setChart(chart);
		}
		if ("range_maximum".equals(name)) {
			this.maximum = (Double) object;
			JFreeChart chart = createChart(xAxis, message, title, label_x, label_y);
			myChart.setChart(chart);
		}
	}

	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if ("setValue".equals(name)) {
			double v = (Double) compMeth.getEvaluatedParameter("value", event).value;			
			if (v > maximum){
				maximum = v;
				JFreeChart chart = createChart(xAxis, message, title, label_x, label_y);
				myChart.setChart(chart);
			}
			animateToValue((float)v);
			
		}
	}

	private void animateToValue(float v) {
		if (anim == null || !anim.isRunning()) {
			anim = PropertySetter.createAnimator(1000, this, "value", ds.getValue().floatValue(), v);
			anim.setAcceleration(0.6f);
			anim.setDeceleration(.2f);
			anim.start();
		}
	}

	public void setValue(float val) {
		ds.setValue(val);
	}

}
