package mundo;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings({ "serial" })
public class Grafico extends ApplicationFrame
{
	private DefaultCategoryDataset dataSet;
	
	public Grafico( String tituloAplicacion, String tituloGrafica, DefaultCategoryDataset dataSet, String nombreEjeX, String nombreEjeY )
	{
		super(tituloAplicacion);
		this.dataSet = dataSet;
		JFreeChart lineChart =  ChartFactory.createLineChart(
								tituloGrafica,
								nombreEjeX, nombreEjeY,
								this.dataSet,
								PlotOrientation.VERTICAL,
								true,true,false);

		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );
		
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}
	
	public void agregarValor(double valorY, String elementoEjeY, String elementoEjeX)
	{
		dataSet.addValue(valorY, elementoEjeY, elementoEjeX);
	}
}