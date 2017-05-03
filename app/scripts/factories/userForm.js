(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserFormFactory', ['$http', function($http) {
			var roles = [];
			var users = [];
			var locations = [];
			var currentUser = {};

			return {
				downloadRoles: function() {
					return $http.get(backend_root + 'nms/user/roles');
				},
				setRoles: function(values){
					roles = values;
				},
				getRoles: function() {   
					return roles;
				},

				downloadUsers: function(id){
					return $http.get(backend_root + 'nms/user/list');
				},
				setUsers: function(value){
					users = value;
				},

				getUser: function(id){
					return $http.get(backend_root + 'nms/user/dto/' + id);
				},

				downloadLocations: function(){
					return $http.get(backend_root + 'nms/location/list');
				},
				setLocations: function(value){
					locations = value;
				},
				getLocations: function(){
					return locations;
				},

				getChildLocations: function(locId){
					if(locId == null){
						locId = 1;
					}
					var toRet = [];
					for(var i = 0; i < locations.length; i++){
						var loc = locations[i]
						if(loc.referenceId != null && loc.referenceId.locationId == locId){
							toRet.push(loc);
						}
					}
					return toRet;
				},
				getLocationByName: function(name){
					for(loc in locations){
						if(loc.location == name){
							return loc;
						}
					}
					return null;
				},

				downloadCreator: function(){
					return $http.get(backend_root + 'nms/user/currentUser');
				},
				setCreator: function(data){
					currentUser = data;
				},
				getCreator: function(){
					return currentUser;
				},

				createUserSubmitDto: function(newUser){
					$http({
						method  : 'post',
						url     : 'http://localhost:8080/NMSReportingSuite/nms/user/create-new',
						data    : newUser, //forms user object
						headers : {'Content-Type': 'application/json'} 
					});
				}

			};
		}]);
})();