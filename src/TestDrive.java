import javax.swing.SwingUtilities;

/**
 * TestDrive.java
 * 
 * @version 1.2 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
public class TestDrive {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				go();
			}
		});
	}

	public static void go() {

		MainFrame frame = new MainFrame();
		frame.setURLListener(new URLListener() {
			WeatherController controller;

			public void changeURL(String url) {
				if (controller == null)
					controller = new WeatherController(new WeatherUrlService(
							url), new GUIView());
				else
					controller.setWeatherService(new WeatherUrlService(url));

				controller.triggerController();
			}

		});
	}

}