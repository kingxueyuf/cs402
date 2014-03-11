package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebRequestImpl implements WebRequest {

	private String method;
	private String path;
	private String version;
	private Map<String, String> header = new HashMap();
	private UniformResourceLocator url;

	/**
	 * Setter
	 * 
	 * @param method
	 * 
	 */
	@Override
	public void setMethod(String method) {
		// TODO Auto-generated method stub
		this.method = method;
	}

	/**
	 * Setter
	 * 
	 * @param path
	 * 
	 */
	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub
		this.path = path;
	}

	/**
	 * Setter
	 * 
	 * @param version
	 * 
	 */
	@Override
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		this.version = version;
	}

	/**
	 * getter
	 */
	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return this.method;
	}

	/**
	 * getter
	 */
	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	/**
	 * getter
	 */
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	/**
	 * setter
	 * 
	 * @param name
	 * @param value
	 */
	@Override
	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub
		this.header.put(name, value);

	}

	/**
	 * setter
	 * 
	 * @param name
	 */
	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return this.header.get(name);
	}

	/**
	 * getter
	 * 
	 * @return set of headernames
	 */
	@Override
	public Set<String> getHeaderNames() {
		// TODO Auto-generated method stub

		return this.header.keySet();
	}

	/**
	 * getter
	 * 
	 * @return UniformResourceLocator
	 */
	@Override
	public UniformResourceLocator getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}

	/**
	 * setter
	 * 
	 * @param url
	 */
	@Override
	public void setUrl(UniformResourceLocator url) {
		// TODO Auto-generated method stub
		this.url = url;
	}

}
