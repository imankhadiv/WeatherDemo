/**
 * WeatherData.java
 * 
 * @version 1.2 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */

public class WeatherData {
	private String[] data;

	public WeatherData(String line) {
		line = line.replaceFirst("<br />", "");
		data = line.split(",");
	}

	/**
	 * This constructor is implemented to cope with the situation where no data
	 * are available at all, for example if the data requested is in the future.
	 * 
	 */
	public WeatherData() {
		data = new String[14];
		for (int i = 0; i < 14; i++) {
			data[i] = ("No Data Is Available");
		}
	}

	public String[] getData() {
		return data;
	}

}
