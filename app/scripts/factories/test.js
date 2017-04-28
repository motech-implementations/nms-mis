/**
 * Created by prasanna.
 */

'use strict';
(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory ('TestFactory', ['$http', '$q', function ($http, $q) {
			var _deferred = $q.defer();

			$http.get('scripts/json/user.json')
				.success(function(data, status, headers, config) {
					_deferred.resolve(data);
				})
				.error(function(data, status, headers, config) {
					_deferred.reject("Error");
				});
				
			return {
				getUsers: function(){
					return _deferred.promise;
				}
			}
		}]);
})()
