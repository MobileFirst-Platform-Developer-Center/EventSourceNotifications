/*
    COPYRIGHT LICENSE: This information contains sample code provided in source code form. You may copy, modify, and distribute
    these sample programs in any form without payment to IBM® for the purposes of developing, using, marketing or distributing
    application programs conforming to the application programming interface for the operating platform for which the sample code is written.
    Notwithstanding anything to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS AND IBM DISCLAIMS ALL WARRANTIES,
    EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY,
    FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR ANY DIRECT,
    INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE.
    IBM HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR MODIFICATIONS TO THE SAMPLE SOURCE CODE.
 */

package com.worklight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpWorker {
	public static void callAdapter(String userId, String notificationText, String serverUrl) throws Exception {
		notificationText = notificationText.replace(" ", "%20");
		Logger.debug("sending notification");
		URL url = new URL(serverUrl
						+ "/dev/invoke?adapter=PushAdapter&procedure=submitNotification&parameters=['" + userId + "','" + notificationText + "']");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setReadTimeout(10000);
		connection.setDoOutput(true);

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String response = "";
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response+= inputLine;
		
		in.close();
		
		int responseStart = response.indexOf("<textarea");
		responseStart = response.indexOf("{", responseStart);
		int responseEnd = response.indexOf("</textarea>");
		response = response.substring(responseStart, responseEnd);
		
		Logger.debug("Server response :: " + response);
		
		connection.disconnect();
	}
}
