import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * GUIView.java
 * 
 * @version 1.1 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
@SuppressWarnings("serial")
public class GUIView extends JFrame implements View {

	private final String weatherMessage = "Displaying Weather Conditions";
	private final String pressureMessage = "Displaying Pressure hPa";
	private final String temperatureMessage = "Displaying Temperature C";
	private final String windMessage = "Displaying Wind Directon Wind SpeedKm/h WindDirDegrees";
	private TextPanel textPanel;
	private ButtonPanel buttenPanel;
	private String query = "graphs";
	private JPanel graphPanel;

	QueryListener queryListener;

	public GUIView() {
		super("GUIView Window");

		textPanel = new TextPanel();
		buttenPanel = new ButtonPanel();
		graphPanel = new JPanel();

		add(buttenPanel, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		add(graphPanel, BorderLayout.CENTER);

		buttenPanel.setQuery(new QueryListener() {

			public void changeQuery(String text) {
				query = text;
				checkQuery();
				queryListener.changeQuery(text);
			}

		});
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setVisible(true);
		setLocation(0, 250);
		setSize(dim.width, dim.height - 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setQueryListener(QueryListener queryListener) {
		this.queryListener = queryListener;
	}

	@Override
	public String getQuery() {
		// checkQuery();
		return query;
	}

	@Override
	public void showTemperature(List<String> time, List<String> temperature) {
		DisplaySelectedData(time, temperature, temperatureMessage);
	}

	@Override
	public void showPressure(List<String> time, List<String> pressure) {
		DisplaySelectedData(time, pressure, pressureMessage);
	}

	@Override
	public void showWeather(List<String> time, List<String> conditions) {
		DisplaySelectedData(time, conditions, weatherMessage);

	}

	@Override
	public void showWind(List<String> list1, List<String> list2) {
		DisplaySelectedData(list1, list2, windMessage);

	}

	@Override
	public void showTotalPrecipitaion(double precipitation) {
		textPanel.appendText("Displaying Total Precipitaion\n");
		if (precipitation == -1) {
			System.out.println("....");
			textPanel.appendText("Total precipitaion is not avaliable (N/A)\n");
		} else {
			textPanel.appendText("Total Precipitation Recorded mm\n  "
					+ precipitation);
		}

	}

	@Override
	public void showAverageTemperature(double temp) {
		textPanel.appendText("displaying average TemperatureC\n");
		textPanel.appendText(String.valueOf(temp));

	}

	@Override
	public void showPressureTrend(List<String> pressure, String trend) {
		textPanel.appendText("pressure " + trend + " from " + pressure.get(0)
				+ " to " + (pressure.get(pressure.size() - 1)) + " hPa\n");

	}

	@Override
	public void showDate(String date) {
		textPanel.appendText("data form " + date + "\n");
	}

	@Override
	public void showQuitMessage() {
		System.exit(0);
	}

	private void DisplaySelectedData(List<String> firstConditions,
			List<String> secondConditions, String message) {
		textPanel.appendText(message + "\n");
		for (int i = 0; i < firstConditions.size(); i++) {
			textPanel.appendText(firstConditions.get(i) + "  \t"
					+ secondConditions.get(i) + "\n");
		}
	}

	@Override
	public void showGraph(List<String> temperature, double averageTemperature,
			List<String> pressure, String pressureTrend, List<String> wSpeed,
			double avgwind, List<String> gSpeed, double avggust,
			double totalPrecipitation) {
		remove(textPanel);
		remove(graphPanel);
		graphPanel = new JPanel();

		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));

		GraphPanel g1 = new GraphPanel(temperature);
		g1.setyAxisName("C");
		g1.setFirstLineName("TEMPERATURE");
		g1.setFirstMessage("AVERAGE TEMPERATURE = "
				+ String.valueOf(averageTemperature));
		graphPanel.add(g1);

		GraphPanel g2 = new GraphPanel(pressure);
		g2.setyAxisName("hPa");
		g2.setFirstLineName("ATMOSPHERIC PRESSURE");
		g2.setFirstMessage("pressure " + pressureTrend + " from "
				+ pressure.get(0) + " to "
				+ (pressure.get(pressure.size() - 1)) + " hPa\n");
		graphPanel.add(g2);

		GraphPanel g3 = new GraphPanel(wSpeed, gSpeed);
		g3.setyAxisName("Km/h");
		g3.setFirstLineName("WIND SPEED");
		g3.setSecondLineName("GUST SPEED");
		g3.setFirstMessage("AVERAGE WIND SPEED = " + avgwind);
		g3.setSecondMessage("AVERAGE GUST SPEED = " + avggust);
		g3.setFirstAverage(avgwind);
		g3.setSecondAverage(avggust);
		graphPanel.add(g3);
		graphPanel.repaint();

		JLabel label = new JLabel();
		label.setBackground(Color.blue);
		label.setFont(label.getFont().deriveFont(20f));

		if (totalPrecipitation == -1) {
			label.setText("Total precipitaion is not avaliable (N/A)\n");
		} else {
			label.setText("Total Precipitation Recorded mm\n  "
					+ totalPrecipitation);
		}
		graphPanel.add(label);

		graphPanel.repaint();
		this.add(graphPanel, BorderLayout.CENTER);
		this.validate();
		this.repaint();

	}

	private void checkQuery() {
		if (!query.equals("graphs")) {

			remove(graphPanel);
			add(textPanel, BorderLayout.CENTER);
			validate();
			repaint();
			textPanel.textArea.setText("");
		}
	}
}
