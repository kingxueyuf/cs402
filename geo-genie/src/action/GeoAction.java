package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import data.GeoData;
import data.OutputData;
import data.Weather;

import util.HttpClientHelper;
import util.JSONHelper;
import util.Util;

public class GeoAction extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String latlonData = doLatLonGet(req);

		GeoData geoData = doParseJSON(latlonData, req);

		updateSession(geoData, req, getIp(), getWeather(req));

		JSONArray outputArray = formatJSONArray(req.getSession());

		output(resp, outputArray);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		HttpSession session = req.getSession();
		OutputData[] list = (OutputData[]) session.getAttribute("list");
		String pointer = (String) session.getAttribute("pointer");
		int pointerNum = Integer.valueOf(pointer);
		for (int i = 0; i < pointerNum; i++) {
			if (list[i].getId().equals(id)) {
				for (int j = i; j < pointerNum; j++) {
					list[j] = list[j + 1];
				}
				pointerNum--;
				break;
			}
		}
		session.setAttribute("list", list);
		session.setAttribute("pointer", pointerNum + "");

		JSONArray outputArray = formatJSONArray(session);

		output(resp, outputArray);

	}

	public Weather getWeather(HttpServletRequest req) {
		String lat;
		String lon;
		if (req.getParameter("lat") == null) {
			lat = "43.81";
		} else {
			lat = req.getParameter("lat");
		}
		if (req.getParameter("lon") == null) {
			lon = "-91.23";
		} else {
			lon = req.getParameter("lon");
		}

		HttpClientHelper httpHelper = new HttpClientHelper();
		// http://api.openweathermap.org/data/2.1/find/city?lat=43&lon=-83&cnt=1
		String url = "http://api.openweathermap.org/data/2.1/find/city?lat="
				+ lat + "&lon=" + lon + "&cnt=1";
		httpHelper.initGet(url);
		String weatherJSON = httpHelper.doGet();
		Weather weather = JSONHelper.getWeather(weatherJSON);
		return weather;
	}

	public String getIp() {
		HttpClientHelper helper = new HttpClientHelper();
		helper.initGet("http://ip.jsontest.com/");
		String responseString = helper.doGet();

		return JSONHelper.getIp(responseString);
	}

	public void output(HttpServletResponse resp, JSONArray outputArray) {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println(outputArray.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateSession(GeoData geoData, HttpServletRequest req,
			String ip, Weather weather) {
		HttpSession session = req.getSession(true);
		OutputData[] list = (OutputData[]) session.getAttribute("list");
		String pointer = (String) session.getAttribute("pointer");
		if (list == null) {
			list = new OutputData[10];
		}
		if (pointer == null) {
			pointer = "0";
		}
		OutputData newData = new OutputData();
		newData.setAddress(geoData.getAddress());
		newData.setId(Util.getRandomId());
		newData.setLat(geoData.getLat());
		newData.setLon(geoData.getLon());
		newData.setSourceIp(ip);
		newData.setTime(System.currentTimeMillis());
		newData.setWeatherDes(weather.description);
		newData.setWeatherTemp(weather.temp);

		if (pointer.equals("10")) {
			for (int i = 0; i < 9; i++) {
				list[i] = list[i + 1];
			}
			list[9] = newData;
		} else {
			list[Integer.valueOf(pointer)] = newData;
			int pointerNum = Integer.valueOf(pointer);
			pointerNum++;
			pointer = pointerNum + "";
		}
		session.setAttribute("list", list);
		session.setAttribute("pointer", pointer);
	}

	public JSONArray formatJSONArray(HttpSession session) {
		JSONArray jsonOutputData = new JSONArray();

		OutputData[] list = (OutputData[]) session.getAttribute("list");
		int pointer = Integer.valueOf((String) session.getAttribute("pointer"));
		try {
			for (int i = 0; i < pointer; i++) {
				JSONObject item = new JSONObject();
				item.put("id", list[i].getId());
				JSONObject query = new JSONObject();
				query.put("lat", list[i].getLat());
				query.put("lon", list[i].getLon());
				item.put("query", query);
				JSONObject response = new JSONObject();
				response.put("address", list[i].getAddress());
				response.put("sourceIp", list[i].getSourceIp());
				response.put("time", list[i].getTime());
				response.put("weather", list[i].getWeatherDes());
				response.put("temp", list[i].getWeatherTemp());

				item.put("response", response);
				jsonOutputData.put(item);
			}
		} catch (Exception e) {

		}
		return jsonOutputData;
	}

	public GeoData doParseJSON(String json, HttpServletRequest req) {
		GeoData data = JSONHelper.parseGeo(json);
		String lat;
		String lon;
		if (req.getParameter("lat") == null) {
			lat = "43.81";
		} else {
			lat = req.getParameter("lat");
		}
		if (req.getParameter("lon") == null) {
			lon = "-91.23";
		} else {
			lon = req.getParameter("lon");
		}
		data.setLat(lat);
		data.setLon(lon);
		return data;
	}

	public String doLatLonGet(HttpServletRequest req) {

		String lat = req.getParameter("lat");
		String lon = req.getParameter("lon");

		String respData;
		String url;
		boolean isValid = checkParameter(lat, lon);
		HttpClientHelper helper = new HttpClientHelper();
		if (isValid) {
			url = this.getUrl(lat, lon);
		} else {
			url = this.getUrl("43.81", "-91.23");
		}

		helper.initGet(url);

		respData = helper.doGet();
		
		this.log(respData);
		
		return respData;
	}

	public boolean checkParameter(String lat, String lon) {
		if (lat != null && lon != null) {
			return true;
		}
		return false;
	}

	public String getUrl(String lat, String lon) {

		// http://maps.googleapis.com/maps/api/geocode/json?latlng=43.81,-91.23&sensor=false
		String query = "latlng=" + lat + "," + lon + "&sensor=false";
		String url = "http://maps.googleapis.com/maps/api/geocode/json?"
				+ query;
		return url;
	}
}
