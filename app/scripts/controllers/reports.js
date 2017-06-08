(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory', function($scope, $state, $http, UserFormFactory){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})
			
			UserFormFactory.getReportsMenu()
			.then(function(result){
				$scope.reports = result.data;
			})

			$scope.reportCategory="Select";

			$scope.selectReportCategory = function(item){
				$scope.reportCategory = item.name;
				$scope.reportNames = item.options;
				$scope.reportName = 'Select';
				
				$scope.state = null;
				$scope.district = null;
				$scope.block = null;
			}

			$scope.reportName="Select";

			$scope.dateOptions = {
				minMode: 'month',
				dateDisabled: disabled,
				formatYear: 'yy',
				maxDate: new Date().setMonth(new Date().getMonth()-1),
				minDate: new Date(2015, 09, 01),
				startingDay: 1
			};

			$scope.selectReport = function(item){
				$scope.reportName = item.name;
				$scope.reportEnum = item.reportEnum;
				$scope.getStatesByService(item.service);

				$scope.state = null;
				$scope.district = null;
				$scope.block = null;

				if($scope.reportEnum == 'CumulativeInactiveUsers'){
					$scope.dateOptions = {
						minMode: 'month',
						dateDisabled: disabled,
						formatYear: 'yy',
						maxDate: new Date().setMonth(new Date().getMonth()-1),
						minDate: new Date(2017, 04, 01),
						startingDay: 1
					};
				}
				else{
					$scope.dateOptions = {
						minMode: 'month',
						dateDisabled: disabled,
						formatYear: 'yy',
						maxDate: new Date().setMonth(new Date().getMonth()-1),
						minDate: new Date(2015, 09, 01),
						startingDay: 1
					};
				}
			}

			$scope.crop = function(name){
				if(name == null){
					return "";
				}
				if(name.length > 18){
					return name.substring(0, 15) + "..."
				}
				return name;
			}

			$scope.isCircleReport = function(){
				return $scope.reportName != null && $scope.reportName == 'Circle wise Anonymous Reports';
			}

			$scope.getStatesByService = function(service){
				return UserFormFactory.getStatesByService(service)
				.then(function(result){
					$scope.states = result.data;
					$scope.districts = [];
					$scope.blocks = [];
				});
			}
			
			$scope.getDistricts = function(stateId){
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districts = result.data;
					$scope.blocks = [];
				});
			}

			$scope.getBlocks =function(districtId){
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blocks = result.data;
				});
			}

			$scope.getCircles =function(){
				return UserFormFactory.getCircles()
				.then(function(result){
					$scope.circles = result.data;
				});
			}

			$scope.selectState = function(state){
				if(state != null){
					$scope.getDistricts(state.stateId);

					$scope.state = state;
					$scope.district = null;
					$scope.block = null;

					if($scope.reportEnum == 'CumulativeInactiveUsers'){
						$scope.dateOptions = {
							minMode: 'month',
							dateDisabled: disabled,
							formatYear: 'yy',
							maxDate: new Date().setMonth(new Date().getMonth()-1),
							minDate: new Date(2017, 04, 01),
							startingDay: 1
						};
					}
					else{
						$scope.dateOptions = {
							minMode: 'month',
							dateDisabled: disabled,
							formatYear: 'yy',
							maxDate: new Date().setMonth(new Date().getMonth()-1),
							minDate: new Date($scope.state.serviceStartDate),
							startingDay: 1
						};
					}
				}
				else{
					$scope.dateOptions = {
						minMode: 'month',
						dateDisabled: disabled,
						formatYear: 'yy',
						maxDate: new Date().setMonth(new Date().getMonth()-1),
						minDate: new Date(2015, 09, 01),
						startingDay: 1
					};
				}
			}
			$scope.clearState = function(){
				$scope.state = null;
				$scope.district = null;
				$scope.block = null;
			}
			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);

					$scope.district = district;
					$scope.block = null;
				}
				
			}
			$scope.clearDistrict = function(){
				$scope.district = null;
				$scope.block = null;
			}
			$scope.selectBlock = function(block){
				if(block != null){
					$scope.block = block;
				}
				
			}
			$scope.clearBlock = function(){
				$scope.block = null;
			}
			$scope.selectCircle = function(circle){
				if(circle != null){
					$scope.circle = circle;
				}	
			}
			$scope.clearCircle = function(){
				$scope.circle = null;
			}

			$scope.waiting = false;
			
			$scope.getCircles();

			$scope.fileName = "";

			$scope.serviceStarted = function(state){
				if($scope.dt == null){
					return true;
				}
				return new Date(state.serviceStartDate) > $scope.dt ;
			}

			$scope.getReport = function(){

				if($scope.reportEnum == "" || $scope.reportEnum == null){
					alert("Please select a report")
					return;
				}
				if($scope.dt == null){
					alert("Please select a month")
					return;
				}

				var reportRequest = {};

			    reportRequest.reportType = $scope.reportEnum;
			    

			    if($scope.state != null){
			    	reportRequest.stateId = $scope.state.stateId;
			    }
			    else{
			    	reportRequest.stateId = 0;
			    }
			    if($scope.district != null){
			    	reportRequest.districtId = $scope.district.districtId;
			    }
			    else{
			    	reportRequest.districtId = 0;
			    }
			    if($scope.block != null){
			    	reportRequest.blockId = $scope.block.blockId;
			    }
			    else{
			    	reportRequest.blockId = 0;
			    }
			    if($scope.circle != null){
			    	reportRequest.circleId = $scope.circle.circleId;
			    }
			    else{
			    	reportRequest.circleId = 0;
			    }

			    reportRequest.fromDate = $scope.dt;

			    $scope.waiting = true;

				$http({
					method  : 'POST',
					url     : $scope.getReportUrl,
					data    : reportRequest, //forms user object
					headers : {'Content-Type': 'application/json'} 
				})
				.then(function(result){
					$scope.waiting = false;
					$scope.status = result.data.status;
					if($scope.status == 'success'){
						$scope.fileName = result.data.file;
						angular.element('#downloadReportLink').trigger('click');
					}
					if($scope.status == 'fail'){

					}
				})
			}

			$scope.getReportUrl = backend_root + 'nms/user/getReport';
			$scope.downloadReportUrl = backend_root + 'nms/user/downloadReport',

			$scope.clearFile = function(){
				$scope.status = "";
				$scope.fileName = "";
			}

			$scope.reset =function(){
				$scope.reportName = "Select";
				$scope.reportEnum = null;
				$scope.reportCategory = "Select";
				$scope.state = null;
				$scope.district = null;
				$scope.block = null;
				$scope.circle = null;
				$scope.dt = null;
			}

			// datepicker stuff

			$scope.select = function(date) {
				$scope.dt = date;
			}

			// $scope.today = function() {
			// 	$scope.dt = new Date();
			// };

			$scope.clear = function() {
				$scope.dt = null;
			};

			$scope.inlineOptions = {
				customClass: getDayClass,
				minDate: new Date(),
				showWeeks: true
			};

			$scope.dateOptions = {
				minMode: 'month',
				dateDisabled: disabled,
				formatYear: 'yy',
				maxDate: new Date().setMonth(new Date().getMonth()-1),
				minDate: new Date(2015, 09, 01),
				startingDay: 1
			};

			// Disable weekend selection
			function disabled(data) {
				var date = data.date,
				mode = data.mode;
				return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
			}

			$scope.open1 = function() {
				$scope.popup1.opened = true;
			};

			$scope.setDate = function(year, month, day) {
				$scope.dt = new Date(year, month, day);
			};

			  $scope.format = 'yyyy-MM'
			  $scope.altInputFormats = ['yyyy-M!'];

			$scope.popup1 = {
				opened: false
			};

			var tomorrow = new Date();
			tomorrow.setDate(tomorrow.getDate() + 1);
			var afterTomorrow = new Date();
			afterTomorrow.setDate(tomorrow.getDate() + 1);
			$scope.events = [
				{
					date: tomorrow,
					status: 'full'
				},
				{
					date: afterTomorrow,
					status: 'partially'
				}
			];

			function getDayClass(data) {
				var date = data.date,
				 	mode = data.mode;
				if (mode === 'day') {
				  	var dayToCheck = new Date(date).setHours(0,0,0,0);

				  	for (var i = 0; i < $scope.events.length; i++) {
						var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

						if (dayToCheck === currentDay) {
							return $scope.events[i].status;
						}
					}
				}

				return '';
			}

		}])
})()