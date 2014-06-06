import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WeatherCalculator.java
 * 
 * This class has some static methods that calculate all of the information
 * needed for the controller Some private methods are implemented to deal with
 * either missing observations or bad format information
 * 
 * @version 1.2 1 December 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */

public class WeatherCalculator {

	private static List<WeatherData> weatherDataList;
	private static final String[] notValidInformation = { "", "N/A", "-" };

	public static void setWeatherDataList(List<WeatherData> weatherDataList) {
		WeatherCalculator.weatherDataList = weatherDataList;
		changeInvalidData();
	}

	public static List<String> getTime() {
		return getSelectedData(0);
	}

	public static List<String> getTemperature() {
		return getSelectedData(1);
	}

	public static List<String> getPressure() {
		return getSelectedData(4);
	}

	public static List<String> getWind() {
		return getSelectedData(6, 7, 12);
	}

	public static List<String> getWindSpeed() {
		List<String> list = getSelectedData(7);
		List<String> temp = checkWindData(list);
		return temp;
	}

	public static List<String> getGustSpeed() {
		List<String> list = getSelectedData(8);
		List<String> temp = checkWindData(list);
		return temp;
	}

	public static List<String> getConditions() {
		return getSelectedData(11);
	}

	public static String getPressureTrend() {

		List<String> pressure = getPressure();
		if (pressure.size() > 0)
			if (checkDataFormat(pressure.get(0).trim())
					&& checkDataFormat(pressure.get(pressure.size() - 1).trim())) {
				Double firstPressure = Double.parseDouble(pressure.get(0)
						.trim());
				Double lastPressure = Double.parseDouble(pressure.get(
						pressure.size() - 1).trim());

				int trend = firstPressure.compareTo(lastPressure);
				if (trend > 0)
					return "fall";
				if (trend < 0)
					return "rise";
				return "remain static";
			}
		return "Not Available";
	}

	public static List<String> getDates() {
		ArrayList<String> date = new ArrayList<String>();
		for (WeatherData item : weatherDataList) {
			// Separating date from time
			String[] s = item.getData()[13].split(" ");
			date.add(s[0]);
		}
		return date;
	}

	public static double getAverageTemperature() {

		return getAverage(1);
	}

	public static double getAverageWindSpeed() {
		return getAverage(7);
	}

	public static double getAverageGustSpeed() {
		return getAverage(8);
	}

	public static double getTotalPrecipitation() {
		double total = 0;
		int number = 0;
		for (WeatherData item : weatherDataList) {
			String data = item.getData()[9];
			if (checkDataFormat(data))
				if (checkNumberBoundry(data)) {
					total += Double.parseDouble(item.getData()[9]);
					number++;
				}
		}
		if (number > 0)
			return total;
		return -1;// if the amount of precipitation was unavailable -1 is
					// returned
	}

	/*
	 * This method is implemented for those public methods above that have a
	 * same structure in their body to avoid code duplication this method has a
	 * variable number of arguments because for getting wind three different
	 * indices are needed
	 */
	private static List<String> getSelectedData(int... indices) {
		ArrayList<String> data = new ArrayList<String>();
		for (WeatherData item : weatherDataList) {
			StringBuilder line = new StringBuilder();
			for (int location : indices) {
				line.append(item.getData()[location] + " ");
			}
			data.add(line.toString());
		}
		return data;
	}

	private static double getAverage(int index) {
		double average = 0;
		int count = 0;

		for (WeatherData item : weatherDataList) {
			String avg = item.getData()[index];

			if (checkDataFormat(avg))
				if (checkNumberBoundry(avg)) {
					average += Double.parseDouble(avg);
					count++;
				}
		}
		average = average / count;
		String d = String.format("%.2f", average);
		average = Double.valueOf(d);
		return average;
	}

	private static boolean checkValidityOfData(String data) {
		if (checkDataFormat(data)) {
			if (checkNumberBoundry(data))
				return true;
			return false;
		}
		if (checkAvailabilityOfData(data))
			return true;
		return false;
	}

	/*
	 * This method checks if some measurements are incorrect. It is assumed that
	 * numbers less than 1000 or greater than 3000 are incorrect.
	 */
	private static boolean checkNumberBoundry(String data) {

		Double d = Double.parseDouble(data);
		if (d < -1000 || d > 3000)

			return false;
		return true;
	}

	private static boolean checkAvailabilityOfData(String data) {

		if (Arrays.asList(notValidInformation).contains(data))
			return false;
		return true;
	}

	private static boolean checkDataFormat(String data) {

		try {
			Double.parseDouble(data);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/*
	 * If two weather records are provided by wunderground for the same time,
	 * this method selects the first record.
	 */
	private static void removeDuplicatedata() {
		for (int i = 0; i < weatherDataList.size() - 1; i++) {
			if ((weatherDataList.get(i).getData()[0]).equals(weatherDataList
					.get(i + 1).getData()[0])) {
				weatherDataList.remove(i + 1);
			}

		}
	}

	/*
	 * this method iterates through the weatherDataList and changes invalid data
	 * with "Not Available"
	 */
	private static void changeInvalidData() {
		removeDuplicatedata();
		for (WeatherData item : weatherDataList) {
			for (int i = 0; i < item.getData().length; i++) {
				if (!checkValidityOfData(item.getData()[i])) {
					item.getData()[i] = "Not Available";
				}
			}
		}
		if (weatherDataList.size() == 0) {
			weatherDataList.add(new WeatherData());
		}
	}

	private static List<String> checkWindData(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("Calm ")) {
				list.set(i, "0");// 'calm' is equivalent to a wind speed of 0)

			}
		}
		return list;
	}
}
