(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory( 'AuthFactory', ['$http', function($http) {
			return {
				logout: function(){
					return $http.get(backend_root + 'nms/logout');
				},
			}
		}])
})()