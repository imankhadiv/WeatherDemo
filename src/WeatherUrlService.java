import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * WeatherUrlService.java
 * 
 * This class implements WeatherService and obtains data from a url to return a
 * list of WeatherData
 * 
 * @version 1.1 1 January 2013
 * 
 * @author Iman Rastkhadiv
 * 
 */

public class WeatherUrlService implements WeatherService {
	String urlName;

	public WeatherUrlService(String url) {
		this.urlName = url;
	}

	@Override
	public List<WeatherData> readData() {
		List<WeatherData> weatherDataList = new ArrayList<>();

		// Open connection
		URL u;
		try {
			u = new URL(urlName);
			URLConnection connection = u.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			InputStream instream = httpConnection.getInputStream();
			Scanner in = new Scanner(instream);
			in.next();
			while (in.hasNextLine()) {
				String input = in.nextLine();
				WeatherData wData = new WeatherData(input);
				// each line should contain 14 different information which are
				// separated by","
				// if its format was different it is not added to the
				// weatherData list
				if (wData.getData().length == 14) {
					weatherDataList.add(new WeatherData(input));
				}

			}
			in.close();
		} catch (java.net.UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return weatherDataList;

	}

}
