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

WL.Server.createEventSource({
	name: 'PushEventSource',
	onDeviceSubscribe: 'deviceSubscribeFunc',
	onDeviceUnsubscribe: 'deviceUnsubscribeFunc',
	securityTest:'PushSecurityTest'
});

function deviceSubscribeFunc(userSubscription, deviceSubscription){
	WL.Logger.debug(">> deviceSubscribeFunc");
	WL.Logger.debug(userSubscription);
	WL.Logger.debug(deviceSubscription);
}

function deviceUnsubscribeFunc(userSubscription, deviceSubscription){
	WL.Logger.debug(">> deviceUnsubscribeFunc");
	WL.Logger.debug(userSubscription);
	WL.Logger.debug(deviceSubscription);
}

function submitNotification(userId, notificationText){
	var userSubscription = WL.Server.getUserNotificationSubscription('PushAdapter.PushEventSource', userId);
	
	if (userSubscription==null){
		return { result: "No subscription found for user :: " + userId };
	}

	var badgeDigit = 1;
	
	var notification = WL.Server.createDefaultNotification(notificationText, badgeDigit, {custom:"data"});
	
	WL.Logger.debug("submitNotification >> userId :: " + userId + ", text :: " + notificationText);
	
	WL.Server.notifyAllDevices(userSubscription, notification);
	
	return { 
		result: "Notification sent to user :: " + userId 
	};
}