(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserFormFactory', ['$http','$mdDialog','$window','$document','deviceDetector', function($http,$mdDialog,$window,$document,deviceDetector) {
			var users = [];
			var currentUser = {};

			return {
				downloadCurrentUser: function(){
					return $http.get(backend_root + 'nms/user/currentUser');
				},
				setCurrentUser: function(user){
					currentUser = user;
				},
				getCurrentUser: function(){
					return currentUser;
				},

				deactivateUser: function(userId){
					return $http.get(backend_root + 'nms/user/deleteUser/' + userId);
				},

				isLoggedIn: function(){
					return $http.get(backend_root + 'nms/user/isLoggedIn');
				},

				logoutUser: function(){
                    return $http.get(backend_root + 'nms/logout');
                },

				isAdminLoggedIn: function(){
					return $http.get(backend_root + 'nms/user/isAdminLoggedIn');
				},

				getRoles: function() {
					return $http.get(backend_root + 'nms/user/roles');
				},

				downloadUsers: function(id){
					return $http.get(backend_root + 'nms/user/list');
				},
				setUsers: function(value){
					users = value;
				},

				getStates: function(){
					return $http.get(backend_root + 'nms/location/states');
				},
				getStatesByService: function(service){
					return $http.get(backend_root + 'nms/location/state/' + service);
				},

				getDistricts: function(stateId){
					return $http.get(backend_root + 'nms/location/districts/' + stateId);
				},

				getBlocks: function(districtId){
					return $http.get(backend_root + 'nms/location/blocks/' + districtId);
				},

				getCircles: function(){
					return $http.get(backend_root + 'nms/location/circles');
				},
				getCirclesByService: function(service){
					return $http.get(backend_root + 'nms/location/circle/' + service);
				},

				getReportsMenu: function(){
				   return $http.get(backend_root + 'nms/user/reportsMenu/');
//                   return $http.get("scripts/json/reportDetails.json");
				},

				getUser: function(id){
					return $http.get(backend_root + 'nms/user/user/' + id);
				},

				sendMail: function(){
                    return $http.post(backend_root + 'nms/sendEmail');
                },

				getUserDto: function(id){
					return $http.get(backend_root + 'nms/user/dto/' + id);
				},

				showAlert : function(message) {
                    $mdDialog.show(
                      $mdDialog.alert()
                        .parent(angular.element(document.querySelector('#popupContainer')))
                        .clickOutsideToClose(true)
                        .title('MIS Alert!!')
                        .textContent(message)
                        .ariaLabel('Alert Dialog Demo')
                        .ok('Ok!')
                    );
                },
				showAlert2 : function (message) {
					var confirmAlert = $mdDialog.show(
                        $mdDialog.alert()
                            .parent(angular.element(document.querySelector('#popupContainer')))
                            //.clickOutsideToClose(true)
                            .title('Session Timeout')
                            .textContent(message)
                            .ariaLabel('Alert Dialog Demo')
                            .ok('OK')
                    );
					return confirmAlert;
                },
                isInternetExplorer : function(){
                    if(deviceDetector.browser === "ie" && ((deviceDetector.browser_version == "10.0")||(deviceDetector.browser_version == "9.0")))
                     return true;
                    else
                     return false;
                }

			};
		}]);
})();