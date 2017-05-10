(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserFormFactory', ['$http', function($http) {
			var roles = [];
			var users = [];
			var currentUser = {};

			var userToEdit;

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



				getStates: function(){
					return $http.get(backend_root + 'nms/location/states');
				},

				getDistricts: function(stateId){
					return $http.get(backend_root + 'nms/location/districts/' + stateId);
				},

				getBlocks: function(districtId){
					return $http.get(backend_root + 'nms/location/blocks/' + districtId);
				},

				getUserToEdit: function(){
					return userToEdit;
				},
				setUserToEdit: function(user){
					userToEdit = user;
				},

				getUser: function(id){
					return $http.get(backend_root + 'nms/user/user/' + id);
				},

				downloadCreator: function(){
					return $http.get(backend_root + 'nms/user/currentUser');
				},

				createUserSubmitDto: function(newUser){
					$http({
						method  : 'post',
						url     : 'http://localhost:8080/NMSReportingSuite/nms/user/createFromDto',
						data    : newUser, //forms user object
						headers : {
							'Content-Type': 'application/json', 
							'Access-Control-Allow-Origin' : '*', 
							'Access-Control-Allow-Credentials' : true} 
					});
				}

			};
		}]);
})();