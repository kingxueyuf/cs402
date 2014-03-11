package com;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author robin-xue
 * @project_name Homework_1_Url
 * @file_name UniformResourceLocatorImpl.java
 * @tag 
 * @date 2013-9-10
 */
public class UniformResourceLocatorImpl implements UniformResourceLocator {

	private String scheme = "";
	private String host = "";
	private Integer port;
	private String path = "";
	private Map<String, String> pair = new LinkedHashMap<String, String>();;


	/**
	 * get Scheme you ever set
	 * @return scheme
	 */
	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return this.scheme;
	}

	/**
	 * get Host you ever set
	 * @return Host
	 */
	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return this.host;
	}

	/**
	 * get Port you ever set
	 * @return port
	 */
	@Override
	public Integer getPort() {
		// TODO Auto-generated method stub
		return this.port;
	}

	/**
	 * get Path you ever set
	 * @return path
	 */
	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	/**
	 * get key set of Query you ever set
	 * @return key set
	 */
	@Override
	public Set<String> getQueryKeys() {
		// TODO Auto-generated method stub
		return this.pair.keySet();
	}

	/**
	 * get value of Query you ever set based on the key 
	 * @param key
	 * @return value
	 */
	@Override
	public String getQueryValue(String key) {
		// TODO Auto-generated method stub
		return (String) this.pair.get(key);
	}

	/**
	 * get map of Query you ever set
	 * @return pair
	 */
	@Override
	public Map<String, String> getQuery() {
		// TODO Auto-generated method stub
		return this.pair;
	}

	
	@Override
	public String getFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * get URL instance
	 * @return URL instance
	 * @throws IllegalStateException
	 */
	@Override
	public URL toURL() throws IllegalStateException {
		// TODO Auto-generated method stub
		if (this.host.length() > 0 && this.pair.size() > 0
				&& this.path.length() > 0 && this.scheme.length() > 0) {
			String urlText = this.toStringFunc();
			URL url;
			try {
				url = new URL(urlText);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				System.out.print(e.toString());
				return null;
			}
			return url;
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * get instance of UniformResourceLocator with scheme
	 * @param s scheme
	 * @return UniformResourceLocator instance including the scheme
	 */
	@Override
	public UniformResourceLocator scheme(String s) {
		// TODO Auto-generated method stu
		this.scheme = s.replace(" ", "");
		return this;
	}

	/**
	 * get instance of UniformResourceLocator with host
	 * @param h host
	 * @return UniformResourceLocator instance including the host
	 */	
	@Override
	public UniformResourceLocator host(String h) {
		// TODO Auto-generated method stub
		this.host = h.replace(" ", "");
		return this;
	}

	/**
	 * get instance of UniformResourceLocator with port
	 * @param h port
	 * @return UniformResourceLocator instance including the port
	 */
	@Override
	public UniformResourceLocator port(Integer h) {
		// TODO Auto-generated method stub
		this.port = h;
		return this;
	}

	/**
	 * get instance of UniformResourceLocator with path
	 * @param h path
	 * @return UniformResourceLocator instance including the path
	 */
	@Override
	public UniformResourceLocator path(String h) {
		// TODO Auto-generated method stub;
		this.path = h.replace(" ", "");
		return this;
	}

	/**
	 * get instance of UniformResourceLocator with queryParameter
	 * @param key 
	 * @param value
	 * @return UniformResourceLocator instance including the query parameter
	 */
	@Override
	public UniformResourceLocator queryParameter(String key, String value) {
		// TODO Auto-generated method stub
		this.pair.put(key, value);
		return this;
	}

	@Override
	public UniformResourceLocator fragment(String h) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * get result string 
	 * @return String of parameters you set
	 */
	public String toString() {
		if (this.host.length() > 0 && this.pair.size() > 0
				&& this.path.length() > 0 && this.scheme.length() > 0) {
			return toStringFunc();
		} else {
			throw new IllegalStateException();
		}
	}

	
	public String toStringFunc() {
		String url;
		if (this.port != null) {
			url = this.scheme.toLowerCase() + "://" + this.host.toLowerCase()
					+ ":" + this.port.intValue() + "/" + this.path + "?";
		} else {
			url = this.scheme.toLowerCase() + "://" + this.host.toLowerCase()
					+ "/" + this.path + "?";
		}
		// sorted alphabetically in ascending order by key
		sortThisMap();

		Set<Map.Entry<String, String>> set = this.pair.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			try {
				url += entry.getKey().toString()
						+ "="
						+ java.net.URLEncoder.encode(entry.getValue()
								.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out
						.println("UnsupportedEncodingException while encode the query");
			}
			if (it.hasNext()) {
				url += "&";
			}
		}
		return url;
	}

	public void sortThisMap() {
		Set<String> set = this.pair.keySet();
		String[] array = set.toArray(new String[0]);
		Arrays.sort(array, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				if (((String) o1).compareTo(((String) o2)) > 0) {
					return 1;
				} else if (((String) o1).compareTo(((String) o2)) == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < array.length; i++) {
			map.put(array[i], this.pair.get(array[i]));
		}
		this.pair = map;
	}
}
