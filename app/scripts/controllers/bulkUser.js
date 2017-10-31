(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("BulkUserController", ['$scope', '$state', 'UserFormFactory', '$http','$mdDialog', function($scope, $state, UserFormFactory, $http, $mdDialog){
			
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			var formdata = new FormData();
			$scope.getTheFiles = function ($files) {
				angular.forEach($files, function (value, key) {
					formdata.append(key, value);
				});
			};

			$scope.uploadFile = function(){
				var file = $scope.myFile;
				var fd = new FormData();

				if(file == null){
					$scope.showAlert("Please select a CSV file");
					return;
				}

				fd.append('bulkCsv', file);
	//We can send anything in name parameter, 
//it is hard coded to abc as it is irrelavant in this case.
				var uploadUrl = backend_root + "nms/admin/uploadFile";
				$http.post(uploadUrl, fd, {
					transformRequest: angular.identity,
					headers: {'Content-Type': undefined}
				})
				.then(function(result){
					$scope.listErrors(result.data)
				})
				// .error(function(){
				// });
			}

			$scope.downloadTemplateUrl = backend_root + 'nms/admin/getBulkDataImportCSV'
			$scope.downloadTemplate = function(){
				$http({
					url: $scope.downloadTemplateUrl, 
					method: "GET",
				});
			}

			$scope.errorsObj = [];
            $scope.uploadedFlag = false;
			$scope.listErrors = function(errors){
				$scope.errorsObj = [];
				$scope.uploadedFlag = true;
				for(i in errors){
					var error = {};
					error.line = i;
					error.name = errors[i];

					$scope.errorsObj.push(error);
				}
			}

			$scope.showAlert = function(message) {
                // Appending dialog to document.body to cover sidenav in docs app
                // Modal dialogs should fully cover application
                // to prevent interaction outside of dialog
                $mdDialog.show(
                  $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('MIS Alert!!')
                    .textContent(message)
                    .ariaLabel('Alert Dialog Demo')
                    .ok('Got it!')
                );
             };

		}])
		// .directive('ngFiles', ['$parse', function ($parse) {
		// 	function fn_link(scope, element, attrs) {
		// 		var onChange = $parse(attrs.ngFiles);
		// 		element.on('change', function (event) {
		// 			onChange(scope, { $files: event.target.files });
		// 		});
		// 	};

		// 	return {
		// 		link: fn_link
		// 	}
		// } ])
		.directive('fileModel', ['$parse', function ($parse) {
			return {
				restrict: 'A',
				link: function(scope, element, attrs) {
					var model = $parse(attrs.fileModel);
					var modelSetter = model.assign;

					element.bind('change', function(){
						scope.$apply(function(){
							modelSetter(scope, element[0].files[0]);
						});
					});
				}
			};



		}])
})()