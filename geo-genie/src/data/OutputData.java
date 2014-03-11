package data;

import java.io.Serializable;

public class OutputData implements Serializable {
	String id;
	String lat;
	String lon;
	String address;
	String sourceIp;
	Long time;
	String weatherDes;
	String weatherTemp;

	public String getWeatherDes() {
		return weatherDes;
	}

	public void setWeatherDes(String weatherDes) {
		this.weatherDes = weatherDes;
	}

	public String getWeatherTemp() {
		return weatherTemp;
	}

	public void setWeatherTemp(String weatherTemp) {
		this.weatherTemp = weatherTemp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}
