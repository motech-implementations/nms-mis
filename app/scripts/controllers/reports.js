(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$http', 'UserFormFactory', function($scope, $http, UserFormFactory){

			$scope.reports = [
				{
					'name': 'Mobile Academy Reports',
					'icon': 'images/drop-down-1.png',
					'options': [
						{
							'name': 'Cumulative Completion Reports',
							'reportEnum': "CumulativeCourseCompletion",
							'icon': 'images/drop-down-3.png',
						},
						{
							'name': 'Circle wise Anonymous Reports',
							'reportEnum': "AnonymousUsers",
							'icon': 'images/drop-down-3.png',
						},
						{
							'name': 'Cumulative Inactive Users',
							'reportEnum': "CumulativeInactiveUsers",
							'icon': 'images/drop-down-3.png',
						}
					]
				},
				{
					'name': 'Kilkari Reports',
					'icon': 'images/drop-down-1.png',
					'options': [
						{
							'name': 'Deactivation for not answering',
							'reportEnum': "KilkariLowUsage",
							'icon': 'images/drop-down-3.png',
						},
						{
							'name': 'Listen to < 25% this month',
							'reportEnum': "KilkariSelfDeactivated",
							'icon': 'images/drop-down-3.png',
						},
						{
							'name': 'Self Deactivations',
							'reportEnum': "KilkariSixWeeksNoAnswer",
							'icon': 'images/drop-down-3.png',
						},
					]
				}
			];

			$scope.reportCategory="Select";

			$scope.selectReportCategory = function(item){
				$scope.reportCategory = item.name;
				$scope.reportNames = item.options;
				$scope.reportName = 'Select';
			}

			$scope.reportName="Select";

			$scope.selectReport = function(item){
				$scope.reportName = item.name;
				$scope.reportEnum = item.reportEnum;
				console.log(item);
			}

			$scope.isCircleReport = function(){
				return $scope.reportName != null && $scope.reportName == 'Circle wise Anonymous Reports';
			}

			$scope.getStates = function(){
				return UserFormFactory.getStates()
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

			$scope.getStates();

			$scope.getReport = function(){
				var reportRequest = {};

			    reportRequest.reportType = $scope.reportEnum;
			    reportRequest.toDate = $scope.dt,
			    reportRequest.fromDate = "",
			    reportRequest.stateId = 1;
			    reportRequest.districtId = 1,
			    reportRequest.blockId = 1
				
				console.log(reportRequest)

				$http({
					method  : 'POST',
					url     : backend_root + 'nms/user/getReport',
					data    : reportRequest, //forms user object
					headers : {'Content-Type': 'application/json'} 
				})/*.then(function(result){
					alert(result.data);
					// $scope.open()
				})*/
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