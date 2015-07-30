/**
* Copyright 2015 IBM Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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
