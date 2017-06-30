(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory', function($scope, $state, $http, UserFormFactory){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
				else{
					UserFormFactory.downloadCurrentUser()
					.then(function(result){
						UserFormFactory.setCurrentUser(result.data);

						$scope.getStatesByService(null);
					})
				}
			})


			$scope.reports = [];

			$scope.states = [];
			$scope.districts = [];
			$scope.blocks = [];
			$scope.circles = [];

			$scope.disableReportCategory = function(){
				return $scope.reports[0] == null;
			}
			$scope.disableReport = function(){
				return $scope.reportCategory == null;
			}

			$scope.disableDate = function(){
				return $scope.report == null || $scope.report.reportEnum == null;
			}
			$scope.disableState = function(){
				return $scope.states[0]  == null || $scope.userHasState() || $scope.report == null;
			}
			$scope.disableDistrict = function(){
				return $scope.districts[0]  == null || $scope.userHasDistrict();	
			}
			$scope.disableBlock = function(){
				return $scope.blocks[0]  == null || $scope.userHasBlock();
			}
			$scope.disableCircle = function(){
				return $scope.circles[0]  == null || $scope.userHasOneCircle();
			}


			$scope.userHasState = function(){
				return UserFormFactory.getCurrentUser().stateId != null;
			}
			$scope.userHasDistrict = function(){
				return UserFormFactory.getCurrentUser().districtId != null;
			}
			$scope.userHasBlock = function(){
				return UserFormFactory.getCurrentUser().blockId != null;
			}
			$scope.userHasOneCircle = function(){
				return $scope.circles.length == 1;
			}
			
			$scope.reportsLoading = true;
			UserFormFactory.getReportsMenu()
			.then(function(result){
				$scope.reports = result.data;
				$scope.reportsLoading = false;
				if($scope.reports.length == 1){
				    $scope.selectReportCategory($scope.reports[0]);
				}
			})

			$scope.reportCategory=null;

			$scope.selectReportCategory = function(item){
				$scope.reportCategory = item.name;
				$scope.reportNames = item.options;

				$scope.report = null;
				
				if(!$scope.userHasState()){
					$scope.clearState();
				}
				if(!$scope.userHasDistrict()){
					$scope.clearDistrict();
				}
				if(!$scope.userHasBlock()){
					$scope.clearBlock();
				}
				$scope.clearCircle();
				$scope.dt == null;

				$scope.getStatesByService(item.service);
				$scope.getCirclesByService(item.service);
			}

			$scope.selectReport = function(item){
				$scope.report = item;

				if(!$scope.userHasState()){
					$scope.clearState();
				}
				if(!$scope.userHasDistrict()){
					$scope.clearDistrict();
				}
				if(!$scope.userHasBlock()){
					$scope.clearBlock();
				}
				$scope.clearCircle();
				$scope.dt = null;
				$scope.setDateOptions();
				if($scope.userHasOneCircle()){
                	$scope.selectCircle($scope.circles[0]);
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
				return $scope.report != null && $scope.report.reportEnum == 'MAAnonymousUsers';
			}

			

			$scope.getStatesByService = function(service){
			    $scope.statesLoading = true;
			    $scope.states = [];
			    $scope.clearState();
                if(service == null){
                    return UserFormFactory.getStates()
                    .then(function(result){
                        $scope.states = result.data;
                        $scope.districts = [];
                        $scope.blocks = [];
                        $scope.statesLoading = false;

                        if($scope.userHasState()){
                            $scope.selectState($scope.states[0]);
                        }
                    });
                }
                else{
                    return UserFormFactory.getStatesByService(service)
                    .then(function(result){
                        $scope.states = result.data;
                        $scope.districts = [];
                        $scope.blocks = [];
                        $scope.statesLoading = false;

                        if($scope.userHasState()){
                            $scope.selectState($scope.states[0]);
                        }
                    });
                }
//				return returnFactory()
//                    .then(function(result){
//                        $scope.states = result.data;
//                        $scope.districts = [];
//                        $scope.blocks = [];
//                        $scope.statesLoading = false;
//
//                        if($scope.userHasState()){
//                            $scope.selectState($scope.states[0]);
//                        }
//                    });
			}
			
			$scope.getDistricts = function(stateId){
				$scope.districtsLoading = true;
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districts = result.data;
					$scope.blocks = [];
					$scope.districtsLoading = false;

					if($scope.userHasDistrict()){
						$scope.selectDistrict($scope.districts[0]);
					}
				});
			}

			$scope.getBlocks =function(districtId){
				$scope.blocksLoading = true;
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blocks = result.data;
					$scope.blocksLoading = false;

					if($scope.userHasBlock()){
						$scope.selectBlock($scope.blocks[0]);
					}
				});
			}

			$scope.getCirclesByService = function(service){
				$scope.circlesLoading = true;
				return UserFormFactory.getCirclesByService(service)
				.then(function(result){
					$scope.circles = result.data;

					$scope.circlesLoading = false;

					if($scope.userHasOneCircle()){
						$scope.selectCircle($scope.circles[0]);
					}
				});
			}

			$scope.setDateOptions =function(){
				var minDate = new Date(2015, 09, 01);
				if($scope.report != null && $scope.report.service == 'M'){
					minDate = new Date(2015, 10, 01);
				}
				if($scope.report != null && $scope.report.reportEnum == 'MACumulativeInactiveUsers'){
                	minDate = new Date(2017, 04, 30);
                }
                if($scope.report != null && $scope.report.reportEnum == 'MAAnonymousUsers'){
                    minDate = new Date(2017, 04, 30);
                }
//                var minDate = $scope.report.minDate;
//                console.log(minDate);
				if(!$scope.isCircleReport() && $scope.state != null && Date.parse($scope.state.serviceStartDate) > minDate){
					minDate = $scope.state.serviceStartDate;
//					console.log($scope.state.serviceStartDate);
//					console.log(minDate);
				}
				if($scope.isCircleReport() && $scope.circle != null && Date.parse($scope.circle.serviceStartDate) > minDate){
					minDate = $scope.circle.serviceStartDate;
//					console.log(minDate);
				}



				$scope.dateOptions = {
					minMode: 'month',
					dateDisabled: disabled,
					formatYear: 'yy',
					maxDate: new Date().setMonth(new Date().getMonth()-1),
					minDate: minDate,
					startingDay: 1
				};
			}

			$scope.selectState = function(state){
				if(state != null){
					$scope.getDistricts(state.stateId);
					$scope.clearState();
					$scope.state = state;
				}
				$scope.setDateOptions();
			}
			$scope.clearState = function(){
				$scope.state = null;
				$scope.clearDistrict();
				$scope.districts = [];
			}
			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);
					$scope.clearDistrict()
					$scope.district = district;
				}
				
			}
			$scope.clearDistrict = function(){
				$scope.district = null;
				$scope.clearBlock();
				$scope.blocks = [];
			}
			$scope.selectBlock = function(block){
				if(block != null){
					$scope.clearBlock();
					$scope.block = block;
				}
			}
			$scope.clearBlock = function(){
				$scope.block = null;
			}
			$scope.selectCircle = function(circle){
				if(circle != null){
//					$scope.clearBlock();
					$scope.circle = circle;
				}	
			}
			$scope.clearCircle = function(){
				$scope.circle = null;
			}

			$scope.waiting = false;

			$scope.fileName = "";

			$scope.$watch('dt', function(newDate){
				if((newDate != null) && newDate.getDate() == 1){
					$scope.dt = new Date($scope.dt.getFullYear(), $scope.dt.getMonth() + 1, 0, 23, 59, 59);
				}
			});

			$scope.serviceStarted = function(state){
				if($scope.dt == null){
					return true;
				}
				return new Date(state.serviceStartDate) < $scope.dt ;
			}

			$scope.getReport = function(){

				if($scope.report == null){
					alert("Please select a report")
					return;
				}
				if($scope.dt == null){
					alert("Please select a month")
					return;
				}

				var reportRequest = {};

			    reportRequest.reportType = $scope.report.reportEnum;

			    reportRequest.stateId = 0;
			    reportRequest.districtId = 0;
			    reportRequest.blockId = 0;

			    reportRequest.circleId = 0;
			    
			    if(!$scope.isCircleReport()){
			    	if($scope.state != null){
				    	reportRequest.stateId = $scope.state.stateId;
				    }
                    else{
                        alert("Please select a state");
                        return;
                    }
				    if($scope.district != null){
				    	reportRequest.districtId = $scope.district.districtId;
				    }
				    if($scope.block != null){
				    	reportRequest.blockId = $scope.block.blockId;
				    }
		    	}
		    	else{
		    		if($scope.circle != null){
				    	reportRequest.circleId = $scope.circle.circleId;
				    }
                    else{
                     	alert("Please select a circle");
                     	return;
                    }
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
				$scope.report = null;
				$scope.reportCategory = null;

				if(!$scope.userHasState()){
					$scope.clearState();
				}
				if(!$scope.userHasDistrict()){
					$scope.clearDistrict();
				}
				if(!$scope.userHasBlock()){
					$scope.clearBlock();
				}
				$scope.clearCircle();
				$scope.dt = null;
			}

			// datepicker stuff

			// $scope.select = function(date) {
			// 	$scope.dt = date;
			// }

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