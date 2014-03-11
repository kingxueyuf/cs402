package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//
//[METH] [REQUEST-URI] HTTP/[VER]
//[fieldname1]: [field-value1]
//[fieldname2]: [field-value2]
//
//[request body, if any]

public class WebRequestFactory {

	/**
	 * 
	 * Create Request Object based on the HTTP text
	 * 
	 * @param input
	 * @return request
	 * @see WebRequest
	 */
	public static WebRequest createRequest(BufferedReader input) {
		WebRequest request = new WebRequestImpl();
		try {
			// 处理request line
			String requestLine = input.readLine();
			System.out.println("____" + requestLine);
			String[] requestLineArray = requestLine.split(" ");
			// System.out.println("____length="+requestLineArray.length);
			String method = requestLineArray[0];
			String requestURI = requestLineArray[1];
			String httpVER = requestLineArray[2];

			request.setMethod(method);
			request.setPath(requestURI);
			request.setVersion(httpVER);

			// 处理header
			String oneLineHeader;
			while (true) {
				oneLineHeader = input.readLine();
				if (oneLineHeader.length() == 0)
					break;
				int loc = oneLineHeader.indexOf(":");
				String key = oneLineHeader.substring(0, loc);
				String value = oneLineHeader.substring(loc + 1,
						oneLineHeader.length());
				// System.out.println(key);
				// System.out.println(value);
				request.addHeader(key, value);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return request;

	}
}
