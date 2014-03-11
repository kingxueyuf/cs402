package com;

import java.net.URL;
import java.util.Map;
import java.util.Set;

public interface UniformResourceLocator {
	public String getScheme();

	public String getHost();

	public Integer getPort();

	public String getPath();

	public Set<String> getQueryKeys();

	public String getQueryValue(String key);

	public Map<String, String> getQuery();

	public String getFragment();

	public String toString() throws IllegalStateException;

	public URL toURL() throws IllegalStateException;

	public UniformResourceLocator scheme(String s);

	public UniformResourceLocator host(String h);

	public UniformResourceLocator port(Integer h);

	public UniformResourceLocator path(String h);

	public UniformResourceLocator queryParameter(String key, String value);

	public UniformResourceLocator fragment(String h);
}
