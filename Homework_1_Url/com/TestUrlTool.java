package com;

public class TestUrlTool {

	public void unitTest() {
		UniformResourceLocator url = new UniformResourceLocatorImpl()
				.scheme("hT Tps").host("www.GoO  gle.com").path("sea rch")
				.queryParameter("q", "lion").queryParameter("safe", "active")
				.queryParameter("a", "abs").queryParameter("c", "!;:");// .queryParameter("c",
																		// "!;:")
		String urlAsText = url.toString();
		System.out.println(urlAsText);
	}

	public static void main(String args[]) {
		new TestUrlTool().unitTest();
	}
}
