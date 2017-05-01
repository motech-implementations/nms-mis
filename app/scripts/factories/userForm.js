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
				getUser: function(){
					return users[2];
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

				downloadCreator: function(){
					return $http.get(backend_root + 'nms/user/currentUser');
				},
				setCreator: function(data){
					currentUser = data;
				},
				getCreator: function(){
					return currentUser;
				}

			};
		}]);
})();