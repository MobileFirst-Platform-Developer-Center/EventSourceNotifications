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
