import java.util.List;

/**
 * WeatherController.java
 * 
 * This is the controller in Model-View-Controller design pattern. It delegates
 * the View and Model
 * 
 * @version 1.2 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */
public class WeatherController {

	private WeatherService weatherService;
	private View view;

	public WeatherController(WeatherService weatherService, View view) {
		this.setWeatherService(weatherService);
		this.view = view;
		view.setQueryListener(new QueryListener() {

			public void changeQuery(String text) {
				triggerController();// whenever a button of the ButtonPanel is
									// pressed triggerController() is called
			}
		});
	}

	public void triggerController() {

		String query = view.getQuery().toLowerCase();

		switch (query) {

		case "temperature":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showTemperature(WeatherCalculator.getTime(),
					WeatherCalculator.getTemperature());
			break;
		case "pressure":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showPressure(WeatherCalculator.getTime(),
					WeatherCalculator.getPressure());
			break;
		case "weather":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showWeather(WeatherCalculator.getTime(),
					WeatherCalculator.getConditions());
			break;
		case "wind":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showWind(WeatherCalculator.getTime(),
					WeatherCalculator.getWind());
			break;
		case "total precipitation":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showTotalPrecipitaion(WeatherCalculator
					.getTotalPrecipitation());
			break;
		case "average temperature":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showAverageTemperature(WeatherCalculator
					.getAverageTemperature());
			break;
		case "pressure trend":

			view.showDate(WeatherCalculator.getDates().get(0));
			view.showPressureTrend(WeatherCalculator.getPressure(),
					WeatherCalculator.getPressureTrend());
			break;
		case "quit":

			view.showQuitMessage();
			return;
		case "graphs":

			view.showGraph(WeatherCalculator.getTemperature(),
					WeatherCalculator.getAverageTemperature(),
					WeatherCalculator.getPressure(),
					WeatherCalculator.getPressureTrend(),
					WeatherCalculator.getWindSpeed(),
					WeatherCalculator.getAverageWindSpeed(),
					WeatherCalculator.getGustSpeed(),
					WeatherCalculator.getAverageGustSpeed(),
					WeatherCalculator.getTotalPrecipitation());
			break;
		}
	}

	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
		List<WeatherData> weatherDataList = this.weatherService.readData();
		WeatherCalculator.setWeatherDataList(weatherDataList);
	}
}
