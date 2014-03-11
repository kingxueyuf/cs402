package com;

public class test {
	public static void main(String[] args) {
		UniformResourceLocator jin = new UniformResourceLocatorlmpl()
				.scheme("https").host("www.google.com").port(443)
				.path("search").queryParameter("q", "lion")
				.queryParameter("safe", "active");
		System.out.println(jin.toString());

	}
}
