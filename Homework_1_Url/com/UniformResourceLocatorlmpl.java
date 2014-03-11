package com;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;

public class UniformResourceLocatorlmpl implements UniformResourceLocator{
private static final Object String = null;
private String scheme ="";
private String host ="";
private Integer port;
private String path ="";
private Set<String> keys;
private Map<String,String> map = new HashMap<String,String>();
	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return this.scheme;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return this.host;
	}

	@Override
	public Integer getPort() {
		// TODO Auto-generated method stub
		return this.port;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	@Override
	public Set<String> getQueryKeys() {
		// TODO Auto-generated method stub
		/*-----------fixed here------------------------------*/
		//you should add "this." in front of "map" so that "this.map" is more specified
		return this.map.keySet();
	}

	@Override
	public String getQueryValue(String key) {
		// TODO Auto-generated method stub
		/*-----------fixed here------------------------------*/
		String Value=this.map.get(key);
		return Value;
	}

	@Override
	public Map<String, String> getQuery() {
		// TODO Auto-generated method stub
		/*-----------fixed here------------------------------*/
		//just return your map here
		return this.map;
	}

	@Override
	public String getFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL toURL() throws IllegalStateException {
		
		// TODO Auto-generated method stub
	    URL url;
		try {
			url = new URL(this.getScheme(), this.getHost(), this.getPort(), this.getPath());
			return url;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UniformResourceLocator scheme(String s) {
		// TODO Auto-generated method stub
		this.scheme=s;
		return this;
	}

	@Override
	public UniformResourceLocator host(String h) {
		// TODO Auto-generated method stub
		this.host=h;
		return this;
	}

	@Override
	public UniformResourceLocator port(Integer h) {
		// TODO Auto-generated method stub
		this.port=h;
		return this;
	}

	@Override
	public UniformResourceLocator path(String h) {
		// TODO Auto-generated method stub
		this.path=h;
		return this;
	}

	@Override
	public UniformResourceLocator queryParameter(String key, String value) {
		// TODO Auto-generated method stub
		/*-----------fixed here------------------------------*/
		this.map.put(key,value);
		//once the function queryParameter(String key, String value) was called the key and value are supposed to put into map immediately
		return this;
	}

	@Override
	public UniformResourceLocator fragment(String h) {
		// TODO Auto-generated method stub
		return null;
	}
    public String toString()throws IllegalStateException{

		/*-----------fixed here------------------------------*/
    	if(this.scheme.length() ==0 ||this.path.length() ==0 ||this.host.length() ==0)
    	{
    		throw new IllegalStateException();
    	}else
    	{
    		String url = this.scheme+"://"+this.host;
    		if(this.port != null)
    		{
    			// port has been set
    			url += ":"+this.port;
    		}
    		url += "/"+this.path;

    		String k;
    		String v;
    		Iterator<Entry<String, String>> iter=map.entrySet().iterator();
    		if(iter.hasNext())
    		{
    			url +="?";
    		}
    		while(iter.hasNext()){
				Map.Entry entry=(Map.Entry)iter.next();
	    		k=(String)entry.getKey();
	    		v=(String)entry.getValue();
	    		url += k+"="+v;
	    		if(iter.hasNext())
	    		{
	    			url+="&";
	    		} 
    		}
    		return url;
    	}
	}
}