var app=angular.module('App', ['ngResource']);

app.factory('HelloMessage', ['$resource', function($resource) {
	return $resource('/api/hello');
}]);

app.controller('AppController', function($scope, HelloMessage){
	$scope.greatings = "This is my main greatings message";
	$scope.readGreatingsFromServer = function(){
		HelloMessage.get(function(message) {
			$scope.greatings = message.userMessage;
		});
	};
});