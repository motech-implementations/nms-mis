(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$http', 'UserFormFactory', function($scope, $http, UserFormFactory){

			// $scope.reports = [];
			// $scope.maReports = {
			// 	'name': 'Mobile Academy Reports',
			// 	'icon': 'images/drop-down-1.png',
			// 	'options': [
			// 		{
			// 			'name': 'Cumulative Completion Reports',
			// 			'reportEnum': "CumulativeCourseCompletion",
			// 			'icon': 'images/drop-down-3.png',
			// 			'service': 'M',
			// 		},
			// 		{
			// 			'name': 'Circle wise Anonymous Reports',
			// 			'reportEnum': "AnonymousUsers",
			// 			'icon': 'images/drop-down-3.png',
			// 			'service': 'M',
			// 		},
			// 		{
			// 			'name': 'Cumulative Inactive Users',
			// 			'reportEnum': "CumulativeInactiveUsers",
			// 			'icon': 'images/drop-down-3.png',
			// 			'service': 'M',
			// 		}
			// 	]
			// };
			// $scope.kReports = {
			// 	'name': 'Kilkari Reports',
			// 	'icon': 'images/drop-down-1.png',
				
			// 	'options': [
			// 		{
			// 			'name': 'Deactivation for not answering',
			// 			'reportEnum': "KilkariSixWeeksNoAnswer",
			// 			'icon': 'images/drop-down-3.png',
			// 			'service': 'K',
			// 		},
			// 		{
			// 			'name': 'Listen to < 25% this month',
			// 			'reportEnum': "KilkariLowUsage",
			// 			'icon': 'images/drop-down-3.png',
			// 			'service': 'K',
			// 		},
			// 		{
			// 			'name': 'Self Deactivations',
			// 			'reportEnum': "KilkariSelfDeactivated",
			// 			'icon': 'images/drop-down-3.png',
			// 			'service': 'K',
			// 		},
			// 	]
			// }

			// $scope.reports.push($scope.maReports);
			// $scope.reports.push($scope.kReports);

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
						maxDate: new Date(),
						minDate: new Date(2017, 06, 01),
						startingDay: 1
					};
				}
				else{
					$scope.dateOptions = {
						minMode: 'month',
						dateDisabled: disabled,
						formatYear: 'yy',
						maxDate: new Date(),
						minDate: new Date(2010, 01, 01),
						startingDay: 1
					};
				}
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
				}
				
			}
			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);

					$scope.district = district;
					$scope.block = null;
				}
				
			}
			$scope.selectBlock = function(block){
				if(block != null){
					$scope.block = block;
				}
				
			}
			$scope.selectCircle = function(circle){
				if(circle != null){
					$scope.circle = circle;
				}
				
			}
			
			$scope.getCircles();

			$scope.fileName = "";

			$scope.getReport = function(){

				if($scope.reportEnum == "" || $scope.reportEnum == null){
					return;
				}
				if($scope.dt == null){
					return;
				}

				var reportRequest = {};

			    reportRequest.reportType = $scope.reportEnum;

			    reportRequest.fromDate = $scope.dt;
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

				$http({
					method  : 'POST',
					url     : $scope.getReportUrl,
					data    : reportRequest, //forms user object
					headers : {'Content-Type': 'application/json'} 
				})
				.then(function(result){
					$scope.status = result.data.status;
					if($scope.status == 'success'){
						$scope.fileName = result.data.file;
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
				$scope.state = null;
				$scope.district = null;
				$scope.block = null;
				$scope.circle = null;
				$scope.reportName = 'Select';
				$scope.reportCategory = 'Select';
				$scope.reportEnum = null;
				$scope.dt = $scope.today();
			}

			// datepicker stuff

			$scope.today = function() {
				$scope.dt = new Date();
			};
			$scope.today();

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
				maxDate: new Date(),
				minDate: new Date(2010, 01, 01),
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