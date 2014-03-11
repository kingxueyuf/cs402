package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.GeoData;
import data.Weather;

public class JSONHelper {

	public static GeoData parseGeo(String json) {

		GeoData geo = new GeoData();
		try {
			JSONObject jsonObj = new JSONObject(json);
			JSONArray jsonArr = (JSONArray) jsonObj.get("results");
			JSONObject jsonObj2 = (JSONObject) jsonArr.get(0);
			geo.setAddress(jsonObj2.get("formatted_address") + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return geo;
	}

	public static String getIp(String IpJSON) {

		try {
			JSONObject json = new JSONObject(IpJSON);
			return (String) json.get("ip");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Weather getWeather(String weatherJSON) {
		String result = "";
		Weather weather = new Weather();
		try {
			JSONObject json = new JSONObject(weatherJSON);
			JSONArray jsonArray = (JSONArray) json.get("list");
			JSONObject item = (JSONObject) jsonArray.get(0);
			JSONArray weatherArray = (JSONArray) item.get("weather");
			JSONObject item2 = (JSONObject) weatherArray.get(0);
			result = (String) item2.get("description");
			weather.description = result;
			JSONObject main = (JSONObject) item.get("main");
			weather.temp = main.get("temp")+"";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weather;
	}

}
