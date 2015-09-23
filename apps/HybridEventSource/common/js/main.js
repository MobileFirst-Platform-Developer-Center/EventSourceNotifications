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

function wlCommonInit() {
	WL.Client.connect({onSuccess: connectSuccess, onFailure: connectFailure});
}

function connectSuccess() {
	WL.Logger.debug ("Successfully connected to MobileFirst Server.");
}

function connectFailure() {
	WL.Logger.debug ("Failed connecting to MobileFirst Server.");
	WL.SimpleDialog.show("Push Notifications", "Failed connecting to MobileFirst Server. Try again later.", 
			[{
				text : 'Reload',
				handler : WL.Client.reloadapp
			},
			{
				text: 'Close',
				handler : function() {}
			}]
		);
}

function isPushSupported() {
	var isSupported = false;
	if (WL.Client.Push){
		isSupported = WL.Client.Push.isPushSupported();
	}	
	alert(isSupported);
	WL.SimpleDialog.show("Push Notifications", JSON.stringify(isSupported), [ {
	    text : 'Close',
	    handler : function() {}
	  }
	  ]);
}

function isPushSubscribed() {
	var isSubscribed = false;
	if (WL.Client.Push){
		isSubscribed = WL.Client.Push.isSubscribed('myPush');
	}
	WL.SimpleDialog.show("Push Notifications", JSON.stringify(isSubscribed), [ {
	    text : 'Close',
	    handler : function() {}
	  }
	  ]);
}

//---------------------------- Set up push notifications -------------------------------
if (WL.Client.Push) {	
	WL.Client.Push.onReadyToSubscribe = function() {
		
		WL.SimpleDialog.show("Push Notifications", "onReadyToSubscribe", [ {
		    text : 'Close',
		    handler : function() {}
		  }
		  ]);
		
		$('#SubscribeButton').removeAttr('disabled');
		$('#UnsubscribeButton').removeAttr('disabled');

		WL.Client.Push.registerEventSourceCallback(
			"myPush", 
			"PushAdapter", 
			"PushEventSource", 
			pushNotificationReceived);
	};
}

// --------------------------------- Subscribe ------------------------------------
function doSubscribe() {
	WL.Client.Push.subscribe("myPush", {
		onSuccess: doSubscribeSuccess,
		onFailure: doSubscribeFailure
	});
}

function doSubscribeSuccess() {
	WL.SimpleDialog.show("Push Notifications", "doSubscribeSuccess", [ {
	    text : 'Close',
	    handler : function() {}
	  }
	  ]);
}

function doSubscribeFailure() {
	WL.SimpleDialog.show("Push Notifications", "doSubscribeFailure", [ {
	    text : 'Close',
	    handler : function() {}
	  }
	  ]);
}

//------------------------------- Unsubscribe ---------------------------------------
function doUnsubscribe() {
	WL.Client.Push.unsubscribe("myPush", {
		onSuccess: doUnsubscribeSuccess,
		onFailure: doUnsubscribeFailure
	});
}

function doUnsubscribeSuccess() {
	WL.SimpleDialog.show("Push Notifications", "doUnsubscribeSuccess", [ {
	    text : 'Close',
	    handler : function() {}
	  }
	  ]);
}

function doUnsubscribeFailure() {
	WL.SimpleDialog.show("Push Notifications", "doUnsubscribeFailure", [ {
	    text : 'Close',
	    handler : function() {}
	  }
	  ]);
}

//------------------------------- Handle received notification ---------------------------------------
function pushNotificationReceived(props, payload) {
	
	WL.SimpleDialog.show("Push Notifications", "Provider notification data: " + JSON.stringify(props), [ {
	    text : 'Close',
	    handler : function() {
	    	WL.SimpleDialog.show("Push Notifications", "Application notification data: " + JSON.stringify(payload), [ {
	    	    text : 'Close',
	    	    handler : function() {}
	    	  }]);    	
	    }
	}]);
}
