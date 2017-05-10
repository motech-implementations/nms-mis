(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'UserTableFactory', ['$http', function($http) {
			var users = []
			// $http.get(backend_root + 'nms/user/list')
			// .then(function(data){
			// 	console.log(JSON.stringify(data));
			// })
			return {
				getUsers: function() {
					// return $http.get(backend_root + 'nms/user/tableList/1')
					return $http.get('newUser.json');
				},
				setUsers: function(values){
					users = values;
				},
				getAllUsers: function() {   
					return users;
				},
				headers: function(){
					return Object.keys(users[0]);
				}
			};
		}]);
})();