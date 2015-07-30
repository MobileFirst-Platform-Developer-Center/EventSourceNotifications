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

var pushAppRealmChallengeHandler = WL.Client.createChallengeHandler("PushAppRealm");

pushAppRealmChallengeHandler.isCustomResponse = function(response) {
    if (!response || response.responseText === null) {
        return false;
    }
    var indicatorIdx = response.responseText.search('j_security_check');
    
    if (indicatorIdx >= 0){
		return true;
	}  
	return false;
};

pushAppRealmChallengeHandler.handleChallenge = function(response) {
	$('#AppBody').hide();
	$('#AuthBody').show();
	$('#passwordInputField').val('');
};

pushAppRealmChallengeHandler.submitLoginFormCallback = function(response) {
    var isLoginFormResponse = pushAppRealmChallengeHandler.isCustomResponse(response);
    if (isLoginFormResponse){
    	pushAppRealmChallengeHandler.handleChallenge(response);
    } else {
		$('#AppBody').show();
		$('#AuthBody').hide();
		pushAppRealmChallengeHandler.submitSuccess();
    }
};

$('#loginButton').bind('click', function () {
    var reqURL = '/j_security_check';
    var options = {};
    options.parameters = {
        j_username : $('#usernameInputField').val(),
        j_password : $('#passwordInputField').val()
    };
    options.headers = {};
    pushAppRealmChallengeHandler.submitLoginForm(reqURL, options, pushAppRealmChallengeHandler.submitLoginFormCallback);
});
