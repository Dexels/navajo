package com.dexels.navajo.adapter;

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;

import org.eclipse.birt.chart.device.*;
import org.eclipse.birt.chart.exception.*;
import org.eclipse.birt.chart.factory.*;
import org.eclipse.birt.chart.model.*;
import org.eclipse.birt.chart.model.attribute.*;
import org.eclipse.birt.chart.model.attribute.impl.*;
import org.eclipse.birt.chart.model.component.*;
import org.eclipse.birt.chart.model.component.Label;
import org.eclipse.birt.chart.model.component.impl.*;
import org.eclipse.birt.chart.model.data.*;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.*;
import org.eclipse.birt.chart.model.type.*;
import org.eclipse.birt.chart.model.type.impl.*;
import org.eclipse.birt.chart.util.*;
import org.eclipse.birt.core.framework.*;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;

public class ChartEngine {
	public final static int TYPE_LINE = 0;
	public final static int TYPE_AREA = 1;
	public final static int TYPE_BAR = 2;

	private ArrayList myData = new ArrayList();
	private ArrayList myTypes = new ArrayList();
	private ArrayList mySerieIds = new ArrayList();

	private Chart myChart;
	private String title, xProp, yProp, xLabel, yLabel;
	private int width = 800;
	private int height = 300;

	public ChartEngine() {
	}

	public void addData(Message data, int chartType, String seriesLabel) {
		myData.add(data);
		myTypes.add(new Integer(chartType));
		mySerieIds.add(seriesLabel);
	}

	public Chart createChart() {
		myChart = ChartWithAxesImpl.create();
		ChartWithAxes cwa = (ChartWithAxes) myChart;

		if(title != null && !"".equals(title)){
			cwa.getTitle().getLabel().getCaption().setValue(title);
		}else{
			cwa.getTitle().setVisible(false);
		}
		cwa.getPlot().getClientArea().setBackground(ColorDefinitionImpl.WHITE());
		cwa.getPlot().getClientArea().getOutline().setVisible(true);
		cwa.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);

		Axis baseAxis = cwa.getPrimaryBaseAxes()[0];
		Axis ortAxis = cwa.getPrimaryOrthogonalAxis(baseAxis);
		// baseAxis.setType(AxisType.LINEAR_LITERAL);
		ortAxis.setType(AxisType.LINEAR_LITERAL);
		ortAxis.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		ortAxis.getMajorGrid().getLineAttributes().setVisible(true);
		ortAxis.getMajorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);
		ortAxis.getMinorGrid().getLineAttributes().setVisible(true);
		ortAxis.getMinorGrid().getLineAttributes().setStyle(LineStyle.DOTTED_LITERAL);

		baseAxis.getLabel().setVisible(true);
		ortAxis.getLabel().setVisible(true);
		Label xL = LabelImpl.create();
		xL.getCaption().setValue(xLabel);
		baseAxis.setLabel(xL);
		Label yL = LabelImpl.create();
		yL.getCaption().setValue(yLabel);
		ortAxis.setLabel(yL);

		SeriesDefinition sdX = SeriesDefinitionImpl.create();
		sdX.getSeriesPalette().update(0); // color!
		baseAxis.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(createXAxisSeries((Message) myData.get(0)));

		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(1);
		sdY.getSeriesPalette().getEntries().add(0, ColorDefinitionImpl.ORANGE());
		sdY.getSeriesPalette().getEntries().add(1, ColorDefinitionImpl.create(255, 100, 100));
		sdY.getSeriesPalette().getEntries().add(2, ColorDefinitionImpl.create(100, 255, 100));
		sdY.getSeriesPalette().getEntries().add(3, ColorDefinitionImpl.create(100, 100, 255));
		ortAxis.getSeriesDefinitions().add(sdY);

		for (int i = 0; i < myData.size(); i++) {
			Message current = (Message) myData.get(i);
			int type = ((Integer) myTypes.get(i)).intValue();
			String name = (String) mySerieIds.get(i);
			sdY.getSeries().add(createSeries(current, type, name));
		}

		return myChart;
	}

	private DataSet createDataSet(Message data, String pName) {
		ArrayList d = new ArrayList();
		if (data.getArraySize() > 0) {
			Property dProperty = data.getMessage(0).getProperty(pName);
			if (dProperty != null) {
				for (int i = 0; i < data.getArraySize(); i++) {
					Message cur = data.getMessage(i);
					String value = cur.getProperty(pName).getValue();
					if (value == null) {
						value = "0";
					}
					d.add(new Double(value));
				}
			}
		}
		NumberDataSet nds = NumberDataSetImpl.create(d);
		return nds;
	}

	private Series createSeries(Message data, int type, String name) {
		DataSet ds = createDataSet(data, yProp);
		Series s;
		switch (type) {
		case TYPE_AREA:
			s = (AreaSeries) AreaSeriesImpl.create();
			s.setDataSet(ds);
			break;
		case TYPE_BAR:
			s = (BarSeries) BarSeriesImpl.create();
			s.setDataSet(ds);
			break;
		case TYPE_LINE:
			s = (LineSeries) LineSeriesImpl.create();
			s.setDataSet(ds);
			break;
		default:
			s = (LineSeries) LineSeriesImpl.create();
			s.setDataSet(ds);
			break;
		}
		s.setSeriesIdentifier(name);
		return s;
	}

	private Series createXAxisSeries(Message data) {
		Series sX = SeriesImpl.create();
		sX.setDataSet(createDataSet(data, xProp));
		return sX;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setXAxisProperty(String xProp, String label) {
		this.xProp = xProp;
		this.xLabel = label;
	}

	public void setYAxisProperty(String yProp, String label) {
		this.yProp = yProp;
		this.yLabel = label;
	}

	public void setChartSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void saveChart(String fileName) {
		PlatformConfig pc = new PlatformConfig();
		PluginSettings ps = PluginSettings.instance(pc);
		IDeviceRenderer idr = null;
		try {
			idr = ps.getDevice("dv.PNG");

			Bounds bo = BoundsImpl.create(0, 0, width, height);
			bo.scale(72d / idr.getDisplayServer().getDpiResolution());

			Generator gr = Generator.instance();
			GeneratedChartState gcs = null;

			gcs = gr.build(idr.getDisplayServer(), myChart, bo, null, null);

			Image img = new BufferedImage((int) bo.getWidth(), (int) bo.getHeight(), BufferedImage.TYPE_INT_ARGB);
			idr.setProperty(IDeviceRenderer.FILE_IDENTIFIER, fileName);
			gr.render(idr, gcs);
		} catch (ChartException x) {
			x.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ChartEngine ce = new ChartEngine();
		try {
			System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
			NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
			NavajoClientFactory.getClient().setServerUrl("ficus:3000/sportlink/knvb/servlet/Postman");
			NavajoClientFactory.getClient().setUsername("ROOT");
			NavajoClientFactory.getClient().setPassword(" ");

			Navajo in = NavajoClientFactory.getClient().doSimpleSend("statistics/InitGetStatistics");
		  Message init = in.getMessage("StoredGlobals");
			
			if (init != null) {
				init.getProperty("Module").setValue("REDCOUNT");
				init.getProperty("SeasonId").setValue("2005");
				Message result = NavajoClientFactory.getClient().doSimpleSend(init.getRootDoc(), "statistics/ProcessGetStoredStatistics", "Result");
				init.getProperty("Module").setValue("YELLOWCOUNT");
				Message women = NavajoClientFactory.getClient().doSimpleSend(init.getRootDoc(), "statistics/ProcessGetStoredStatistics", "Result");
				if (result != null) {

					ce.setTitle("");
					ce.setXAxisProperty("Week", "Week");
					ce.setYAxisProperty("Value", "Value");
					ce.addData(result, ChartEngine.TYPE_AREA, "Rode kaarten");
					ce.addData(women, ChartEngine.TYPE_LINE, "Gele kaarten");
					ce.setChartSize(800, 300);

					Chart c = ce.createChart();
					ce.saveChart("/home/aphilip/rymix.png");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
