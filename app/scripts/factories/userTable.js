(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserTableFactory', ['$http', function($http) {
			var users = [];
			return {
				getUsers: function() {
					return $http.post(backend_root + 'nms/user/tableList');
				},
				setUsers: function(values) {
					users = values;
				},
				getAllUsers: function() {   
					return users;
				},
				clearAllUsers: function() {
					users = [];
				}
			};
		}]);
})();