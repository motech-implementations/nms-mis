(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory','$window', function($scope, $state, $http, UserFormFactory,$window){

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
            $scope.sundays = [];
 			$scope.reports = [];
			$scope.states = [];
			$scope.districts = [];
			$scope.blocks = [];
			$scope.circles = [];
			$scope.datePickerContent = "Select Month";

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
                if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1)  ){
                	$scope.datePickerContent = "Select Week";
                }
                else
                    $scope.datePickerContent = "Select Month";

			}

			$scope.crop = function(name){
				if(name == null){
					return "";
				}
				if(name.length > 14){
					return name.substring(0, 13) + "..."
				}
				return name;
			}

            $scope.cropState = function(name){
                if(name == null){
                    return "";
                }
                if(name.length > 12){
                    return name.substring(0, 10) + ".."
                }
                return name;
            }

			$scope.isCircleReport = function(){
				return $scope.report != null && $scope.report.reportEnum == 'MA_Anonymous_Users';
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
				if($scope.report != null && $scope.report.reportEnum == 'MA_Cumulative_Inactive_Users'){
                	minDate = new Date(2017, 04, 30);
                }
                if($scope.report != null && $scope.report.reportEnum == 'MA_Anonymous_Users'){
                    minDate = new Date(2017, 04, 30);
                }
                if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Low_Usage'){
                    minDate = new Date(2017, 03, 30);
                }
//                if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Low_Listenership_Deactivation'){
//                    minDate = new Date(2017, 08, 30);
//                }

                //In case of change in minDate for rejection reports, please change startMonth and startDate variable accordingly
                if($scope.report != null && $scope.report.reportEnum == 'MA_Asha_Import_Rejects'){
                    minDate = new Date(2017, 10, 01);
                 }
                 if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Mother_Import_Rejects'){
                    minDate = new Date(2017, 10, 01);
                 }
                 if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Child_Import_Rejects'){
                    minDate = new Date(2017, 10, 01);
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
					formatYear: 'yyyy',
					maxDate: new Date().setMonth(new Date().getMonth() -1),
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
			$scope.wasSundaySelected = false;

			$scope.fileName = "";

			$scope.$watch('dt', function(newDate){
                if ($scope.wasSundaySelected){
                $scope.format = 'yyyy-MM-dd';
                $scope.wasSundaySelected = false;
                 return;
                }
                $scope.format = 'yyyy-MM';
			    if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected"))  > -1 ) && $scope.dt != null) {
			    	 $scope.getSundays($scope.dt);
                     $scope.sundaysTable = true;
			    	 $scope.popup1.opened = true;
			    	 console.log(newDate);
			    }
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
				if($scope.dt == null && (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1 ) ){
					alert("Please select a week")
					return;
				}
				else if($scope.dt == null){
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

		    	if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected"))  > -1 ) && $scope.format == 'yyyy-MM'){
                    alert("Please select a week");
                    return;
		    	}

			    reportRequest.fromDate = $scope.dt;

			    $scope.waiting = true;

                console.log($scope.dt);

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
						$scope.pathName = result.data.path;
						angular.element('#downloadReportLink').trigger('click');
					}
					if($scope.status == 'fail'){

					}
				})
			}

			$scope.getReportUrl = backend_root + 'nms/user/getReport';
			$scope.$watch('pathName', function(){
			    $scope.downloadReportUrl = backend_root + 'nms/user/downloadReport?fileName='+
            			        $scope.fileName+'&rootPath='+$scope.pathName;
			})
			$scope.$watch('fileName', function(){
            			    $scope.downloadReportUrl = backend_root + 'nms/user/downloadReport?fileName='+
                        			        $scope.fileName+'&rootPath='+$scope.pathName;
            			})


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
				$scope.clearFile();
				$scope.dt = null;
				$scope.datePickerContent = "Select Month";

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

            var startMonth = 10 //November
            var startDate = 25 //Start Date

			$scope.open1 = function() {
				$scope.popup1.opened = true;
				var currentDate = new Date();


				console.log(currentDate.getMonth() + " " + currentDate.getDate() + " " +currentDate.getFullYear());
				if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected"))  > -1 ) ){
				    if(currentDate.getMonth() == startMonth && currentDate.getDate() >= startDate && currentDate.getFullYear() == 2017 && $scope.getSundays(currentDate) > 0){
				        $scope.dateOptions.maxDate = new Date().setMonth(new Date().getMonth());
				    }
				    else if(currentDate.getMonth() == startMonth && currentDate.getDate() >= startDate && currentDate.getFullYear() == 2017 && $scope.getSundays(currentDate) == 0){
                    	$scope.dateOptions.maxDate = new Date().setMonth(new Date().getMonth() - 1);
                     }
				    else if(currentDate.getMonth() == startMonth && currentDate.getDate() < startDate && currentDate.getFullYear() == 2017){
				         $scope.dateOptions.maxDate = new Date().setMonth(new Date().getMonth() - 1);
				    }
				    else {
				        if($scope.getSundays(currentDate) >0){
                            $scope.dateOptions.maxDate = new Date().setMonth(currentDate.getMonth());
                        }
                        else
                            $scope.dateOptions.maxDate = new Date().setMonth(currentDate.getMonth() - 1);
                        $scope.sundays = null;

				    }

				}

				if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected"))  > -1 ) && $scope.format == 'yyyy-MM' ){
                    $scope.getSundays($scope.dt);
                    $scope.sundaysTable = true;

                }
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
            $scope.sundays = null;

			$scope.getSundays = function(d){

                if( d  === undefined || d == null){
                    return;
                }
                var getTot = null;
                var sun = new Array(); //Declaring array for inserting Sundays
                var today = new Date();
                if(d.getMonth() == startMonth && d.getFullYear() == 2017 ){
                    if(d.getMonth() == today.getMonth()){
                        for(var i=startDate;i<=today.getDate()-1;i++){    //looping through days in month
                            var newDate = new Date(d.getFullYear(),d.getMonth(),i)
                            if(newDate.getDay()==0){   //if Sunday
                                sun.push(i);
                            }
                        }
                    }

                    else{
                        for(var i=startDate;i<=30;i++){    //looping through days in month
                            var newDate = new Date(d.getFullYear(),d.getMonth(),i)
                            if(newDate.getDay()==0){   //if Sunday
                                sun.push(i);
                            }
                        }
                    }

                 $scope.sundays = sun;
                 return ($scope.sundays.length);

                }


                else{

                    if(d.getMonth() == today.getMonth() && d.getFullYear() == today.getFullYear() )
                    {
                      var getTot = (today.getDate()) - 1;
                    }
                    else{
                      var getTot = new Date(d.getFullYear(),d.getMonth()+1,0).getDate();
                    }

                    console.log(getTot);
                     //Get total days in a month
                    var sun = new Array();   //Declaring array for inserting Sundays
                    for(var i=1;i<=getTot;i++){    //looping through days in month
                        var newDate = new Date(d.getFullYear(),d.getMonth(),i)
                        if(newDate.getDay()==0){   //if Sunday
                            sun.push(i);
                        }
                    }
                    $scope.sundays = sun;
                    return ($scope.sundays.length);

                }

			}

			$scope.sundaysTable = false;

			$scope.closeSundaysTable = function(date) {
            	$scope.sundaysTable = false;
            	$scope.sundays = [];
            	$scope.dt = new Date($scope.dt.getFullYear(),$scope.dt.getMonth(),date);
            	$scope.wasSundaySelected = true;
            };

            $window.addEventListener('click', function() {
              if($scope.sundaysTable)
                $scope.sundaysTable = false;
            });

		}])
})()