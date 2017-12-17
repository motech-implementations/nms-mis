(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserTableFactory', ['$http', function($http) {
			var users = []
			return {
				getUsers: function() {
					return $http.get(backend_root + 'nms/user/tableList')
					// return $http.get('newUser.json');
				},
				setUsers: function(values){
					users = values;
				},
				getAllUsers: function() {   
					return users;
				},
				clearAllUsers: function(){
					users = [];
				}
			};
		}]);
})();