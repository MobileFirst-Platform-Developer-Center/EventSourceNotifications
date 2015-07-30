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

public class PushBackendEmulator {
	
	public static void main(String [] args){
		Logger.debug("PushBackendEmulator");
		if (args.length < 3){
			Logger.debug("Syntax is: java -jar PushBackendEmulator.jar <userId> \"<notification text>\" <context-root-required> <server-port-optional>");
			Logger.debug("e.g: java -jar PushBackendEmulator.jar bjones \"Money transfer complete\" myContextRoot");
			Logger.debug("e.g: java -jar PushBackendEmulator.jar bjones \"Money transfer complete\"  myContextRoot 8181");
			System.exit(0);
		}
		
		String userId = args[0];
		String notificationText = args[1];
		String serverPort = "10080";
		
		if (args.length > 3) {
			serverPort = args[3];
		}
		
		String serverUrl = 
				"http://localhost:" +
				serverPort + "/" +
				args[2];
						
		Logger.debug("User Id: " + userId);
		Logger.debug("Notification text: " + notificationText);
		Logger.debug("Server URL: " + serverUrl);
		
		try {
			HttpWorker.callAdapter(userId, notificationText, serverUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
