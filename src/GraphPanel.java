import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
/**
 *GraphPanel.java
 * 
 * This class creates a graph on a panel 
 
 * 
 * @version 1.1 1 January 2013
 * @author Iman Rastkhadiv
 *
 */

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Color firstLineColor = Color.blue;
	private final Color secondLineColor = Color.DARK_GRAY;
	private final Color pointColor = Color.red;
	private final Color gridColor = Color.green;
	private final Color firstMessageColor = Color.red;
	private final Color secondMessageColor = Color.magenta;
	private final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private final int padding = 25;
	private final int labelPadding = 25;
	private final int pointWidth = 4;
	private int numberOfYDivisions = 6;
	private List<Double> firstList;
	private List<Double> secondList;
	private String yAxisName = "";
	private String firstLineName = "";
	private String secondLineName = "";
	private String firstMessage = "";
	private String secondMessage = "";
	private double firstAverage;
	private double secondAverage;
	private boolean flag = false;
	private int startPoint = (getWidth() - padding * 2 - labelPadding) / (24)
			+ padding + labelPadding;

	public GraphPanel(List<String> list) {
		firstList = new ArrayList<Double>();
		for (String item : list) {
			if (checkDataFormat(item)) {
				firstList.add(Double.valueOf(item));
			}
		}
	}
	
	public GraphPanel(List<String> list1, List<String> list2) {
		firstList = new ArrayList<>();
		for (String item : list1) {
			if (checkDataFormat(item)) {
				firstList.add(Double.valueOf(item));
			}
		}
		secondList = new ArrayList<>();
		flag = true;
		for (String item : list2) {
			if (checkDataFormat(item)) {
				secondList.add(Double.valueOf(item));
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// drawing white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding)
				- labelPadding, getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);
		g2.drawString(yAxisName, 30, 15);
		g2.setColor(firstLineColor);
		g2.drawString(firstLineName, getWidth() / 5, 20);
		g2.setColor(secondLineColor);
		g2.drawString(secondLineName, 2 * getWidth() / 5, 20);
		g2.setColor(firstMessageColor);
		g2.drawString(firstMessage, 3 * getWidth() / 5, 20);
		g2.setColor(secondMessageColor);
		g2.drawString(secondMessage, 4 * getWidth() / 5, 20);
		// creating grid lines and hatch marks for y axis.
		for (int i = 0; i < numberOfYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding))
							/ numberOfYDivisions + padding + labelPadding);
			int y1 = y0;
			if (firstList.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0,
						getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = String
						.format("%.1f",
								((int) ((getMinScore() + (getMaxScore() - getMinScore())
										* ((i * 1.0) / numberOfYDivisions)) * 100) / 100.0));
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5,
						y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// creating grid lines and hatch marks for x axis.
		for (int i = 0; i < 25; i++) {
			int x0 = i * (getWidth() - padding * 2 - labelPadding) / (24)
					+ padding + labelPadding;
			int x1 = x0;

			int y0 = getHeight() - padding - labelPadding;
			int y1 = y0 - pointWidth;
			g2.setColor(gridColor);
			g2.drawLine(x0, getHeight() - padding - labelPadding - 1
					- pointWidth, x1, padding);
			g2.setColor(Color.black);
			String xLabel;
			if (i % 2 == 0) {
				if (i == 0)
					xLabel = "midnight ";
				else if (i == 12)
					xLabel = "noon ";
				else
					xLabel = i + "";

				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(xLabel);
				g2.drawString(xLabel, x0 - labelWidth / 2,
						y0 + metrics.getHeight() + 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}
		// scaling
		double xScale = ((double) getWidth() - (2 * padding) - labelPadding - (1.0 / 2 * startPoint))
				/ (firstList.size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding)
				/ (getMaxScore() - getMinScore());

		List<Point> graphPoints = new ArrayList<>();
		List<Point> graphPoints2 = new ArrayList<>();

		for (int i = 0; i < firstList.size(); i++) {
			int x1 = 0;
			if (firstList.get(i) != null) {
				x1 = (int) (i * xScale + padding + labelPadding + (int) (1.0 / 3 * startPoint));
				int y1 = (int) ((getMaxScore() - firstList.get(i)) * yScale + padding);
				graphPoints.add(new Point(x1, y1));
			}
			//
			if (secondList != null && secondList.size() > 0) {
				if (secondList.size() > i && secondList.get(i) != null) {
					x1 = (int) (i * xScale + padding + labelPadding + (int) (1.0 / 3 * startPoint));
					int z1 = (int) ((getMaxScore() - secondList.get(i))
							* yScale + padding);
					graphPoints2.add(new Point(x1, z1));

				}
			}
		}

		// create x and y axes
		g2.drawLine(padding + labelPadding, getHeight() - padding
				- labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding
				- labelPadding, getWidth() - padding, getHeight() - padding
				- labelPadding);

		Stroke oldStroke = g2.getStroke();
		g2.setColor(firstLineColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);
		}

		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}

		if (graphPoints2.size() > 0) {
			g2.setColor(new Color(144, 0, 144));
			for (int i = 0; i < graphPoints2.size(); i++) {
				int x = graphPoints2.get(i).x - pointWidth / 2;
				int z = graphPoints2.get(i).y - pointWidth / 2;
				int ovalW = pointWidth;
				int ovalH = pointWidth;
				g2.fillOval(x, z, ovalW, ovalH);

			}
			g2.setColor(secondLineColor);
			for (int i = 0; i < graphPoints2.size() - 1; i++) {
				int x1 = graphPoints2.get(i).x;
				int z1 = graphPoints2.get(i).y;
				int x2 = graphPoints2.get(i + 1).x;
				int z2 = graphPoints2.get(i + 1).y;
				g2.drawLine(x1, z1, x2, z2);

			}
		}
		// draw the average of the firstList
		if (graphPoints.size() != 0 && flag) {
			int avg1 = (int) ((getMaxScore() - firstAverage) * yScale + padding);
			int p1 = (int) (padding + labelPadding + (int) (1.0 / 3 * startPoint));
			int pn = (getWidth() - padding * 2 - labelPadding)
					+ (int) (2.50 / 3 * startPoint);
			g2.setColor(firstMessageColor);
			g2.drawLine(p1, avg1, pn, avg1);
		}
		// draw the average of the secondList
		if (graphPoints2.size() != 0) {
			int avg2 = (int) ((getMaxScore() - secondAverage) * yScale + padding);
			int k1 = (int) (padding + labelPadding + (int) (1.0 / 3 * startPoint));
			int kn = (getWidth() - padding * 2 - labelPadding)
					+ (int) (2.50 / 3 * startPoint);
			g2.setColor(secondMessageColor);
			g2.drawLine(k1, avg2, kn, avg2);
		}
	}

	// getting minimum scores of the list to set the minimum of the x axis
	private double getMinScore() {
		double minScore1 = Double.MAX_VALUE;
		for (Double score : firstList) {
			if (score != null)
				minScore1 = Math.min(minScore1, score);
		}
		if (secondList != null) {
			double minScore2 = Double.MAX_VALUE;
			for (Double score : secondList) {
				if (score != null)
					minScore2 = Math.min(minScore2, score);
			}
			return Math.min(minScore2, minScore1);
		}
		return minScore1;
	}

	// getting maximum scores of the list to set the maximum of the x axis

	private double getMaxScore() {
		double maxScore1 = Double.MIN_VALUE;
		for (Double score : firstList) {
			if (score != null)
				maxScore1 = Math.max(maxScore1, score);
		}

		if (secondList != null) {
			double maxScore2 = Double.MIN_VALUE;
			for (Double score : secondList) {
				if (score != null)
					maxScore2 = Math.max(maxScore2, score);
			}
			return Math.max(maxScore1, maxScore2);
		}
		return maxScore1;

	}

	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}

	public void setFirstLineName(String firstLineName) {
		this.firstLineName = firstLineName;
	}

	public void setSecondLineName(String secondLineName) {
		this.secondLineName = secondLineName;
	}

	public void setFirstMessage(String firstMessage) {
		this.firstMessage = firstMessage;
	}

	public void setSecondMessage(String secondMessage) {
		this.secondMessage = secondMessage;
	}

	public void setFirstAverage(double firstAverage) {
		this.firstAverage = firstAverage;
	}

	public void setSecondAverage(double secondAverage) {
		this.secondAverage = secondAverage;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}

	private boolean checkDataFormat(String data) {

		try {
			Double.parseDouble(data);

			return true;
		} catch (NumberFormatException ex) {
			if (flag)
				secondList.add(null);
			else
				firstList.add(null);
			return false;
		}
	}

}