package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WebRequestHandler {

	/**
	 * Set data into output
	 * 
	 * @param request
	 * @param output
	 * 
	 */
	public void handleRequest(WebRequest request, DataOutputStream output) {
		try {
			String path = request.getPath();
			if (!path.contains("math")) {
				throw new InvalidPathException();
			}
			if (!request.getMethod().equals("GET")) {
				throw new InvalidPathException();
			}
			if (!path.contains("add") && !path.contains("sub")) {
				throw new InvalidPathException();
			}

			if (path.contains("?")) {// 有请求参数
				String[] array = path.split("\\?");// "/pr/search?a=bse&b=123"
				// System.out.println("______" + array.length);
				String action = array[0];
				// System.out.println(action);
				String queryParameters = array[1];
				// System.out.println(queryParameters);
				String[] parameter;
				if (!queryParameters.contains("&")) {
					parameter = new String[] { queryParameters };
				} else {
					parameter = queryParameters.split("\\&");
				}
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < parameter.length; i++) {
					String[] key_value = parameter[i].split("\\=");
					String key = key_value[0];
					// System.out.println("key = " + key);
					String value = key_value[1];
					// System.out.println("value = " + value);
					map.put(key, value);
				}

				String color = map.get("color");
				color = (color == null) ? "gray" : color;

				double result = 0;
				if (action.contains("math")) {
					if (action.contains("add")) {
						String x = map.get("x");
						String y = map.get("y");
						double X = (x == null) ? 0 : Double.valueOf(x);
						double Y = (y == null) ? 0 : Double.valueOf(y);
						result = X + Y;
						// System.out.println("result=" + result);
					} else if (action.contains("sub")) {
						String x = map.get("x");
						String y = map.get("y");
						double X = (x == null) ? 0 : Double.valueOf(x);
						double Y = (y == null) ? 0 : Double.valueOf(y);
						result = X - Y;
					}
				}

				// write HTML
				output.writeBytes("HTTP:/1.1 200 OK\n");
				output.writeBytes("Content-type: text/html; charset=UTF-8\n");
				output.writeBytes("\n");
				String html = "<html>";
				Set<String> headerNamesSet = request.getHeaderNames();
				// System.out.println("size="+headerNamesSet.size());
				// String[] namesArray = (String[])
				// headerNamesSet.toArray();
				Iterator<String> it = headerNamesSet.iterator();

				html += "<table" + " " + "bgcolor=\"" + color + "\""
						+ " border = black" + ">";
				html += "<tr> <th>NAME</th> <th>VALUE</th></tr>";
				// 写request line
				html += "<tr><td>method</td>" + "<td>" + request.getMethod()
						+ "</td></tr>";
				html += "<tr><td>path</td>" + "<td>" + request.getPath()
						+ "</td></tr>";
				html += "<tr><td>version</td>" + "<td>" + request.getVersion()
						+ "</td></tr>";
				// 写header
				while (it.hasNext()) {
					html += "<tr>";
					String key = it.next();
					html += "<td>" + key + "</td>";
					// System.out.println(key);
					html += "<td>" + request.getHeader(key) + "</td>";
					html += "</tr>";
				}
				// 写完header
				html += "</table>";
				html += "<br>";
				// 写result
				html += "<h4>" + "<div style = \"color:" + color + "\">"
						+ "VALUE =" + result + "</div></h4>";
				// 写完result
				html += "</html>";
				// System.out.print(html);
				output.writeBytes(html);
			}
		} catch (Exception e) {
			try {
				output.writeBytes("HTTP:/1.1 200 OK\n");
				output.writeBytes("Content-type: text/html; charset=UTF-8\n");
				output.writeBytes("\n");
				String text = "<html>" + "404 Not Found </html>";
				output.writeBytes(text);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	class InvalidPathException extends Exception {

	}
}
