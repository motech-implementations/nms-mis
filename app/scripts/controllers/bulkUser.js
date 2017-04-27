(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("BulkUserController", ['$scope', function($scope){
			$scope.listErrors = function(){
				var toRet = [];
				for(var i = 0; i < 15; i++){
					toRet.push("error " + i);
				}
				return toRet;
			}
		}])
})()