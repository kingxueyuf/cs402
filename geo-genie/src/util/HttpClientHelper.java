package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientHelper {

	private HttpClient client;
	private String getUrl;
	private String postUrl;
	private List<NameValuePair> nvp;

	public HttpClientHelper() {
		client = new DefaultHttpClient();
	}

	public void initGet(String url) {
		this.getUrl = url;
	}

	public void initPost(String url) {
		this.postUrl = url;
		this.nvp = new ArrayList<NameValuePair>();
	}

	public String doGet() {
		HttpGet httpGet = new HttpGet(getUrl);
		try {
			HttpResponse response = this.client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addKeyValue(String key, String value) {
		nvp.add(new BasicNameValuePair(key, value));
	}

	public String doPost() {
		HttpPost httpPost = new HttpPost(this.postUrl);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse response = this.client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
