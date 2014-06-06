import java.util.List;
/**
 * View.java
 * 
 * The view interface can be implemented by any views such as ConsoleView or GUIView
 * 
 * @version 1.1  1 Dec 2013
 * 
 * @author Iman Rastkhadiv 
 *
 */


public interface View  {

	public String getQuery();
	
	public void setQueryListener(QueryListener query);

	public void showTemperature(List<String> time, List<String> temperature);

	public void showPressure(List<String> time, List<String> pressure);

	public void showWeather(List<String> time, List<String> conditions);

	public void showWind(List<String> list1, List<String> list2);

	public void showTotalPrecipitaion(double precipitation);

	public void showAverageTemperature(double temp);
	
	public void showPressureTrend(List<String> pressure,String trend);
	
	public void showDate(String date);

	public void showGraph(List<String> temperature,double temp,List<String> pressure,String trend,List<String> windSpeed,double avgwind,List<String> gustSpeed,double avgGust,double totalPrecipitation);

	void showQuitMessage();


}