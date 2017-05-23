(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserFormFactory', ['$http', function($http) {
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

				getDistricts: function(stateId){
					return $http.get(backend_root + 'nms/location/districts/' + stateId);
				},

				getBlocks: function(districtId){
					return $http.get(backend_root + 'nms/location/blocks/' + districtId);
				},

				getUser: function(id){
					return $http.get(backend_root + 'nms/user/user/' + id);
				},

				getUserDto: function(id){
					return $http.get(backend_root + 'nms/user/dto/' + id);
				}

			};
		}]);
})();