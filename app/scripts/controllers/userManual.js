(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManualController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

			UserFormFactory.isAdminLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

		}])
})();


var app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/banana", {
        template : "<h1>Banana</h1><p>Bananas contain around 75% water.</p>"
    })
    .when("/tomato", {
        template : "<h1>Tomato</h1><p>Tomatoes contain around 95% water.</p>"
    })
    .otherwise({
        template : "<h1>Nothing</h1><p>Nothing has been selected</p>"
    });
});
