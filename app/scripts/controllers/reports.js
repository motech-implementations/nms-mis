(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory','$window','$q','uiGridConstants', function($scope, $state, $http, UserFormFactory,$window,$q,uiGridConstants){

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

			var reportRequest = {};
            $scope.sundays = [];
 			$scope.reports = [];
			$scope.states = [];
			$scope.districts = [];
			$scope.blocks = [];
			$scope.circles = [];
			$scope.datePickerContent = "Select Month";
			$scope.reportDisplayType = 'TABLE';
			$scope.gridOptions = {};
			$scope.gridOptions1 = {};
			$scope.MA_Performance_Column_Definitions = [];
			$scope.MA_Cumulative_Column_Definitions = [];
			$scope.MA_Subscriber_Column_Definitions = [];
			$scope.hideGrid = true;
			$scope.hideMessageMatrix = true;
			$scope.showEmptyData = false;
			$scope.content = "There is no data available for the selected inputs";
			$scope.periodType = ['Year','Month','Quarter', 'Custom Range'];
			$scope.quarterType = ['Q1 (Jan to Mar)','Q2 (Apr to Jun)','Q3 (Jul to Sep)', 'Q4 (Oct to Dec)'];
			$scope.periodDisplayType = "";
			$scope.dataPickermode = "";
			$scope.periodTypeContent = "";
            $scope.dateFormat = '';
            $scope.reportBreadCrumbData = [];
            $scope.headerFromDate = '';
            $scope.headerToDate = '';
            $scope.matrixContent1 = '';
            $scope.matrixContent2 = '';
            var parentScope = $scope.$parent;
            parentScope.child = $scope;

            $scope.popup2 = {
                opened: false
            };

            $scope.popup3 = {
                opened: false
            };

            $scope.open2 = function() {
                $scope.popup2.opened = true;
            };

            $scope.open3 = function() {
                $scope.popup3.opened = true;
            };

            $scope.setDate = function(year, month, day) {
                $scope.dt1 = new Date(year, month, day);
            };

            $scope.setDate = function(year, month, day) {
                $scope.dt2 = new Date(year, month, day);
            };

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

			$scope.selectReportType = function(item){
			    $scope.reportDisplayType = item;
			    $scope.hideGrid = true;
			    $scope.showEmptyData = false;
			    $scope.hideMessageMatrix = true;
			}

			$scope.selectPeriodType = function(item){
			    $scope.finalDateOptions = {};
                $scope.periodDisplayType = item;
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.quarterDisplayType = '';
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                if($scope.periodDisplayType == 'Year' || $scope.periodDisplayType == 'Quarter' ){
                    $scope.periodTypeContent = "Select year";
                    $scope.dateFormat = "yyyy";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'year';
                    $scope.datePickerOptions.minMode = 'year';
                    $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                }
                if($scope.periodDisplayType == 'Month'){
                    $scope.periodTypeContent = "Select month";
                    $scope.dateFormat = "yyyy-MM";
                     $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'month';
                    $scope.datePickerOptions.minMode ='month';
                    $scope.datePickerOptions.maxDate = new Date().setMonth(new Date().getMonth() -1);
                }
                if($scope.periodDisplayType == 'Custom Range'){
                    $scope.periodTypeContent = "Start Date";
                    $scope.dateFormat = "yyyy-MM-dd";
                    delete $scope.datePickerOptions.datepickerMode;
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.maxDate = new Date().setDate(new Date().getDate() - 1);
                }
                console.log($scope.datePickerOptions);

            }

            $scope.selectQuarterType = function(item){
                $scope.quarterDisplayType = item;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
            }


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
				$scope.periodDisplayType = '';
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
				$scope.showEmptyData = false;
				$scope.clearFile();

				$scope.getStatesByService(item.service);
				$scope.getCirclesByService(item.service);
			}

			$scope.selectReport = function(item){
				$scope.report = item;
                console.log($scope.report.reportEnum);
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
				$scope.setDateOptions();
				if($scope.userHasOneCircle()){
                	$scope.selectCircle($scope.circles[0]);
                }
                if($scope.report.reportEnum == 'Kilkari_Message_Matrix' || $scope.report.reportEnum == 'Kilkari_Listening_Matrix' || $scope.report.reportEnum == 'Kilkari_Usage' || $scope.report.reportEnum == 'Kilkari_Message_Listenership'){
                    $scope.periodType = ['Year','Month','Quarter'];
                }
                else if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                    $scope.periodType = ['Month'];
                }
                else
                    $scope.periodType = ['Year','Month','Quarter', 'Custom Range'];
                if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).includes(angular.lowercase("rejected")))  ){
                	$scope.datePickerContent = "Select Week";
                }
                else
                    $scope.datePickerContent = "Select Month";
                $scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                if($scope.report.name == 'MA Cumulative Summary'){
                    $scope.dateFormat = 'yyyy-MM-dd';
                }

			}

			$scope.crop = function(name){
				if(name == null){
					return "";
				}
				if(name.length > 17){
					return name.substring(0, 14) + "..."
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

            $scope.isAggregateReport = function(){
            	return $scope.report != null && $scope.report.icon == 'images/drop-down-2.png';
            }

			$scope.reportTypes = ['TABLE'];

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
			$scope.isClickAllowed=function(name){
			    if(name == 'BAR GRAPH' || name == 'PIE CHART'){
			        return false;
			     }
			    else {
			         true
			    }
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
                    minDate = new Date(2017, 08, 01);
                 }
                 if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Mother_Import_Rejects'){
                    minDate = new Date(2017, 08, 01);
                 }
                 if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Child_Import_Rejects'){
                    minDate = new Date(2017, 08, 01);
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


                $scope.datePickerOptions = {
                    formatYear: 'yyyy',
                    maxDate: new Date(),
                    minDate: minDate,
                    startingDay: 1
                };

				$scope.dateOptions = {
					minMode: 'month',
					dateDisabled: disabled,
					formatYear: 'yyyy',
					maxDate: new Date().setMonth(new Date().getMonth() -1),
					minDate: minDate,
					startingDay: 1
				};

				$scope.endDatePickerOptions = {
                    formatYear: 'yyyy',
                    maxDate: new Date() - 1,
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
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
				$scope.setDateOptions();
				$scope.clearFile();
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
                $scope.periodDisplayType = '';
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
				$scope.showEmptyData = false;
				$scope.clearFile();
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
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.clearFile();
			}
			$scope.clearBlock = function(){
				$scope.block = null;
			}
			$scope.selectCircle = function(circle){
				if(circle != null){
//					$scope.clearBlock();
					$scope.circle = circle;
				}
                $scope.periodDisplayType = '';
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.showEmptyData = false;
				$scope.clearFile();
			}
			$scope.clearCircle = function(){
				$scope.circle = null;
			}

			$scope.waiting = false;
			$scope.wasSundaySelected = false;

			$scope.fileName = "";

			$scope.$watch('dt', function(newDate){
                if($scope.wasSundaySelected){
                $scope.format = 'yyyy-MM-dd';
                $scope.wasSundaySelected = false;
                 return;
                }
                $scope.format = 'yyyy-MM';
			    if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).includes(angular.lowercase("rejected"))) && $scope.dt != null) {
			    	 $scope.getSundays($scope.dt);
                     $scope.sundaysTable = true;
			    	 $scope.popup1.opened = true;
			    }

                if(!$scope.wasSundaySelected){
                    if((newDate != null) && newDate.getDate() == 1){
                        $scope.dt = new Date($scope.dt.getFullYear(), $scope.dt.getMonth() + 1, 0, 23, 59, 59);
                     }
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
				if($scope.dt == null && (angular.lowercase($scope.report.name).includes(angular.lowercase("rejected"))) ){
					alert("Please select a week")
					return;
				}
				else if($scope.dt == null && (!$scope.isAggregateReport() )){
                	alert("Please select a month")
					return;
				}
				else if($scope.periodDisplayType == '' && ($scope.isAggregateReport() ) && $scope.report.name != 'MA Cumulative Summary'){
                    alert("Please select a period type")
                    return;
                }
				else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType != 'Custom Range' && $scope.periodDisplayType != 'Quarter' && $scope.report.name != 'MA Cumulative Summary') ){
                    alert("Please " +  $scope.periodDisplayType)
                    return;
                }
                else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Custom Range')){
                    alert("Please select a start date")
                    return;
                }
                 else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Quarter')){
                    alert("Please select a year")
                    return;
                }
                else if($scope.dt2 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Custom Range' || $scope.report.name == 'MA Cumulative Summary' )){
                    alert("Please select an end date")
                    return;
                }

                else if($scope.periodDisplayType == 'Quarter' && $scope.quarterDisplayType == '' && ($scope.isAggregateReport() )){
                    alert("Please select a quarter type")
                    return;
                }
                else if( ($scope.periodDisplayType == 'Custom Range') && ($scope.dt2 < $scope.dt1)){
                    alert("End date should be greater than start date")
                    return;
                }


			    reportRequest.reportType = $scope.report.reportEnum;

			    reportRequest.stateId = 0;
			    reportRequest.districtId = 0;
			    reportRequest.blockId = 0;

			    reportRequest.circleId = 0;
			    
			    if(!$scope.isCircleReport() ){

			    	if(!$scope.isAggregateReport())
			    	{
                        if($scope.state != null){
                            reportRequest.stateId = $scope.state.stateId;
                        }
                        else{
                            alert("Please select a state");
                            return;
                        }
                    }
                    else
                    {
                        if($scope.state != null){
                            reportRequest.stateId = $scope.state.stateId;
                        }
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

		    	if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).includes(angular.lowercase("rejected"))) && $scope.format == 'yyyy-MM'){
                    alert("Please select a week");
                    return;
		    	}

                if(!$scope.isAggregateReport())
                {
                    reportRequest.fromDate = $scope.dt;
                }
                else
                {

                    reportRequest.periodType = $scope.periodDisplayType;

                    if($scope.periodDisplayType == 'Year' ){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),11,31);
                    }

                    else if($scope.periodDisplayType == 'Month' ){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth(),1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth() + 1,0);
                    }
                    else if($scope.periodDisplayType == 'Quarter' ){
                         if($scope.quarterDisplayType == 'Q1 (Jan to Mar)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),2,31);
                         }
                         if($scope.quarterDisplayType == 'Q2 (Apr to Jun)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),3,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),5,30);
                         }
                         if($scope.quarterDisplayType == 'Q3 (Jul to Sep)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),6,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),8,30);
                         }
                         if($scope.quarterDisplayType == 'Q4 (Oct to Dec)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),9,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),11,31);
                         }

                    }
                    else{
                        reportRequest.fromDate = $scope.dt1;
                        reportRequest.toDate = $scope.dt2;
                    }
                }

                console.log(reportRequest);
			    $scope.waiting = true;

                $scope.headerFromDate = reportRequest.fromDate;
                $scope.headerToDate = reportRequest.toDate;

				$http({
					method  : 'POST',
					url     : $scope.getReportUrl,
					data    : reportRequest, //forms user object
					headers : {'Content-Type': 'application/json'} 
				})
				.then(function(result){
                    console.log(result);
					if(!$scope.isAggregateReport()){
					    $scope.waiting = false;
                        $scope.status = result.data.status;
                        if($scope.status == 'success'){
                            $scope.fileName = result.data.file;
                            $scope.pathName = result.data.path;
                            angular.element('#downloadReportLink').trigger('click');
                        }
                        if($scope.status == 'fail'){

                        }

					}

					if($scope.isAggregateReport()){
					    $scope.waiting = false;

					    if($scope.report.reportEnum == 'MA_Cumulative_Summary'){
					        $scope.gridOptions1.columnDefs = $scope.MA_Cumulative_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'MA_Performance'){
					        $scope.gridOptions1.columnDefs = $scope.MA_Performance_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'MA_Subscriber')
					        $scope.gridOptions1.columnDefs = $scope.MA_Subscriber_Column_Definitions;
					    else if($scope.report.reportEnum == 'Kilkari_Cumulative_Summary'){
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Cumulative_Summary_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Usage')
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Usage_Definitions;
					    else if($scope.report.reportEnum == 'Kilkari_Beneficiary'){
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Aggregate_Beneficiaries_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Beneficiary_Completion'){
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Beneficiary_Completion_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Message_Matrix'){
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Message_Matrix_Motherpack_Definitions;
                            $scope.gridOptions2.columnDefs = $scope.Kilkari_Message_Matrix_Childpack_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Call'){
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Call_Report_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Listening_Matrix'){
					         $scope.gridOptions1.columnDefs = $scope.Kilkari_Listening_Matrix_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Subscriber'){
                             $scope.gridOptions1.columnDefs = $scope.Kilkari_Subscriber_Definitions;
                        }
                        else if($scope.report.reportEnum == 'Kilkari_Message_Listnership'){
                             $scope.gridOptions1.columnDefs = $scope.Kilkari_Message_Listnership_Definitions;
                        }
                        else if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                             $scope.gridOptions1.columnDefs = $scope.Kilkari_RepeatListener_Numberdata_Definitions;
                             $scope.gridOptions2.columnDefs = $scope.Kilkari_RepeatListener_Percentdata_Definitions;
                        }
                        else if($scope.report.reportEnum == 'Kilkari_Thematic_Content'){
                             $scope.gridOptions1.columnDefs = $scope.Kilkari_Thematic_Content_Definitions;
                        }
                        if($scope.report.reportEnum != 'Kilkari_Message_Matrix' && $scope.report.reportEnum != 'Kilkari_Listening_Matrix' && $scope.report.reportEnum != 'Kilkari_Repeat_Listener_Month_Wise' && $scope.report.reportEnum != 'Kilkari_Thematic_Content' ){
                            if(result.data.tableData.length >0){
                             $scope.gridOptions1.data = result.data.tableData;
                             $scope.reportBreadCrumbData = result.data.breadCrumbData;
                             $scope.hideGrid = false;
                             if(angular.lowercase(result.data.tableData[0].locationType) == 'state'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'State';
                                 }
                                 else if(angular.lowercase(result.data.tableData[0].locationType) == 'district'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'District';

                                 }
                                  else if(angular.lowercase(result.data.tableData[0].locationType) == 'block'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Block';

                                 }
                                 else if(angular.lowercase(result.data.tableData[0].locationType) == 'subcenter'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'SubCenter';
                                 }
                            }
                            else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }

                        }
                        if($scope.report.reportEnum == 'Kilkari_Listening_Matrix'){
                            if(result.data.tableData.length >0){
                                 $scope.gridOptions1.data = result.data.tableData;
                                 $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                 $scope.hideGrid = false;
                            }
                            else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                        }
                        if($scope.report.reportEnum == 'Kilkari_Thematic_Content'){
                            if(result.data.tableData.length >0){
                                 $scope.gridOptions1.data = result.data.tableData;
                                 $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                 $scope.hideGrid = false;
                            }
                            else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                        }
                        if($scope.report.reportEnum == 'Kilkari_Message_Matrix'){
                            console.log(result.data.motherData.length);
                            if(result.data.motherData.length >0){
                                $scope.gridOptions1.data = result.data.motherData;
                                $scope.hideGrid = false;
                                $scope.matrixContent1 ='Kilkari MotherPack Data';

                            }
                             else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                            if(result.data.childData.length >0){
                                $scope.gridOptions2.data = result.data.childData;
                                $scope.hideMessageMatrix = false;
                                $scope.matrixContent2 ='Kilkari ChildPack Data';
                            }
                            else{
                                $scope.hideMessageMatrix = true;
                            }
                        }
                       if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                            if(result.data.numberData.length >0){
                                $scope.gridOptions1.data = result.data.numberData;
                                $scope.hideGrid = false;
                                $scope.matrixContent1 ='Beneficiary Count';
                            }
                             else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                            if(result.data.percentData.length >0){
                                $scope.gridOptions2.data = result.data.percentData;
                                $scope.hideMessageMatrix = false;
                                $scope.matrixContent2 ='Beneficiary Percentage';
                            }
                            else{
                                $scope.hideMessageMatrix = true;
                            }
                       }

                         $scope.gridOptions = $scope.gridOptions1;
                         $scope.gridOptions_Message_Matrix = $scope.gridOptions2;
                    }
                }

				)
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
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
				$scope.periodDisplayType = '';
				$scope.showEmptyData = false;
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

            var startMonth = 8 //September
            var startDate = 1 //Start Date

			$scope.open1 = function() {
				$scope.popup1.opened = true;
				var currentDate = new Date();

				console.log(currentDate.getMonth() + " " + currentDate.getDate() + " " +currentDate.getFullYear());
				if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).includes(angular.lowercase("rejected"))) ){
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

				if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).includes(angular.lowercase("rejected"))) && ($scope.format == 'yyyy-MM-dd' || $scope.format == 'yyyy-MM' )){
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

            var canceler = $q.defer();

            $scope.gridOptions1 = {
                enableSorting: true,
                onRegisterApi: function(gridApi){
                      $scope.gridApi = gridApi;
                    },
              };

            $scope.gridOptions2 = {
                enableSorting: true,
                onRegisterApi: function(gridApi){
                      $scope.gridApi = gridApi;
                    },
              };

            $scope.MA_Cumulative_Column_Definitions =[
                                                       {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                       { field: 'locationName',
                                                         cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                         width: '11%', enableHiding: false
                                                       },
                                                       { field: 'ashasRegistered', name : 'No of Registered Asha', width:"10%", enableHiding: false},
                                                       { field: 'ashasStarted', name : ' No of Asha Started Course',  width:"10%", enableHiding: false},
                                                       { field: 'ashasNotStarted', name : ' No of Asha Not Started Course', width:"10%", enableHiding: false},
                                                       { field: 'ashasCompleted' , name : 'No of Asha Successfully Completed the Course', width:"13%", enableHiding: false},
                                                       { field: 'ashasFailed' , name : 'No of Asha who failed the course', width:"11%", enableHiding: false},
                                                       { field: 'notStartedpercentage' , name : '% Not Started Course', width:"10%", enableHiding: false},
                                                       { field: 'completedPercentage' , name : '% Successfully Completed', width:"9%", enableHiding: false},
                                                       { field: 'failedpercentage' , name : '% Failed the course', width:"10%", enableHiding: false},
                                                      ],


            $scope.MA_Performance_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"7%", enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                         { field: 'locationName',
                                                            cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                            enableHiding: false, width:"11%"
                                                         },
                                                         { field: 'ashasStarted', name: 'Number of Asha Started Course', width:"15%",enableHiding: false },
                                                         { field: 'ashasAccessed', name: 'Number of Asha Pursuing Course', width:"16%", enableHiding: false },
                                                         { field: 'ashasNotAccessed', name: 'Number of Asha not Pursuing Course', width:"17%", enableHiding: false},
                                                         { field: 'ashasCompleted', name: 'Number of Asha Successfully Completed Course', width:"18%",enableHiding: false},
                                                         { field: 'ashasFailed',  name: 'Number of Asha who Failed the Course', width:"17%", enableHiding: false},
                                                        ],

            $scope.MA_Subscriber_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                         { field: 'locationName',
                                                            cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                            enableHiding: false,width:"10%"
                                                         },
                                                         { field: 'registeredNotCompletedStart', name: 'Number of Asha Registered But Not Completed the Course(Period Start)',width:"16%", enableHiding: false },
                                                         { field: 'recordsReceived', name: 'Number of Asha Received Through Web Service', width:"13%", enableHiding: false },
                                                         { field: 'ashasRejected', name: 'Number of Asha Records Rejected', width:"13%", enableHiding: false},
                                                         { field: 'ashasRegistered', name: 'Number of Asha Subscriptions Added', width:"13%", enableHiding: false},
                                                         { field: 'ashasCompleted',  name: 'Number of Asha Successfully Completed the Course', width:"13%", enableHiding: false},
                                                         { field: 'registeredNotCompletedend',  name: 'Number of Asha Registered But Not Completed the Course (Period End)', width:"16%", enableHiding: false},
                                                        ],

            $scope.Kilkari_Cumulative_Summary_Definitions =[
                                                             {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                             { field: 'locationName',
                                                                cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                                enableHiding: false,width:"*"
                                                             },
                                                             { field: 'uniqueBeneficiaries', name: 'Total unique beneficiaries',width:"*", enableHiding: false },
                                                             { field: 'successfulCalls', name: 'Total successful calls', width:"*", enableHiding: false },
                                                             { field: 'billableMinutes', name: 'Total billable minutes played', width:"*", enableHiding: false},
                                                             { field: 'averageDuration', name: 'Average duration of call', width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Usage_Definitions =[
                                                 {name: 'S No.', displayName: 'S No.',width:"7%", enableSorting: false,cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                 { field: 'locationName',
                                                    cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                    enableHiding: false,width:"*"
                                                 },
                                                 { field: 'beneficiariesCalled', name: 'Total beneficiaries Called',width:"*", enableHiding: false },
                                                 { field: 'answeredCall', name: 'Beneficiaries who have answered at least one call', width:"*", enableHiding: false },
                                                 { field: 'calls_75_100', name: 'Beneficiaries who have listened more than 75% content (avg)', width:"*", enableHiding: false},
                                                 { field: 'calls_50_75', name: 'Beneficiaries who have listened 50 to 75% content (avg)', width:"*", enableHiding: false},
                                                 { field: 'calls_25_50', name: 'Beneficiaries who have listened 25 to 49.9% content (avg)',width:"*", enableHiding: false },
                                                 { field: 'calls_1_25', name: 'Beneficiaries who have listened less than 25% content (avg)', width:"*", enableHiding: false },
                                                 { field: 'calledInbox', name: 'Beneficiaries who have called the Kilkari Inbox', width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Aggregate_Beneficiaries_Definitions =[
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                     { field: 'locationName',
                                                        cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                        enableHiding: false,width:"7%"
                                                     },
                                                     { field: 'beneficiariesCalled', name: 'Total beneficiaries Called',width:"*", enableHiding: false },
                                                     { field: 'answeredCall', name: 'Beneficiaries who have answered at least one call',width:"*", enableHiding: false },
                                                     { field: 'selfDeactivated', name: 'Beneficiaries who have self-deactivated', width:"*", enableHiding: false },
                                                     { field: 'notAnswering', name: 'Beneficiaries deactivated for not answering.', width:"*", enableHiding: false},
                                                     { field: 'lowListenership', name: 'Beneficiaries deactivated for low listenership.', width:"*", enableHiding: false},
                                                     { field: 'systemDeactivation', name: 'Beneficiaries deactivated by system through MCTS/RCH updates',width:"11%", enableHiding: false },
                                                     { field: 'motherCompletion', name: 'Beneficiaries completed Mother Pack', width:"*", enableHiding: false },
                                                     { field: 'childCompletion', name: 'Beneficiaries completed Child Pack', width:"*", enableHiding: false},
                                                     { field: 'calledInbox', name: 'Beneficiaries who have called the Kilkari Inbox', width:"*", enableHiding: false },
                                                     { field: 'joinedSubscription', name: 'Beneficiaries who have joined the subscription', width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Beneficiary_Completion_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                     { field: 'locationName',
                                                        cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                        enableHiding: false,width:"10%"
                                                     },
                                                     { field: 'completedBeneficiaries', name: 'Total beneficiaries Completed Program',width:"*", enableHiding: false },
                                                     { field: 'avgWeeks', name: 'Average Number of Weeks in Service',width:"*", enableHiding: false },
                                                     { field: 'calls_75_100', name: 'Beneficiaries who have listened more than 75% content (consolidated)', width:"*", enableHiding: false },
                                                     { field: 'calls_50_75', name: 'Beneficiaries who have listened 50 to 75% content (consolidated)', width:"*", enableHiding: false},
                                                     { field: 'calls_25_50', name: 'Beneficiaries who have listened 25 to 49.9% content (consolidated)',width:"*", enableHiding: false },
                                                     { field: 'calls_1_25', name: 'Beneficiaries who have listened less than 25% content (consolidated)', width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Listening_Matrix_Definitions =[
                                                     { field: 'percentageCalls', name: 'Listening Percentage', width:"30%", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Listening > 75 % content', width:"15%", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Listening 50 to 75 % content',width:"15%", enableHiding: false },
                                                     { field: 'content_25_50', name: 'Listening 25 to 50 % content', width:"15%", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening < 25 % content',width:"15%", enableHiding: false },
                                                     { field: 'total', name: 'Total', width:"11%", enableHiding: false },

            ]

            $scope.Kilkari_Call_Report_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                     { field: 'locationName',
                                                        cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                        enableHiding: false,width:"7%"
                                                     },
                                                     { field: 'successfulCalls', name: 'Total Calles Attempted',width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Total Number of Successful Calls', width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Total calls where > 75% content listened to ', width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Total calls where 50% to 75% content listened to', width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Total calls where 25% to 49.9% content listened to',width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Total calls where < 25%  content listened to',width:"*", enableHiding: false },
                                                     { field: 'billableMinutes', name: 'Total Billable minutes', width:"*", enableHiding: false },
                                                     { field: 'avgDuration', name: 'Average Duration of Calls', width:"*", enableHiding: false},
                                                     { field: 'callsToInbox', name: 'Total number of calls to inbox where content is played', width:"*", enableHiding: false },
            ]

            $scope.Kilkari_Message_Matrix_Motherpack_Definitions =[
                                                     { field: 'messageWeek', name: 'Message Week',width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Total calls where > 75% content listened to ', width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Total calls where 50% to 75% content listened to', width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Total calls where 25% to 49.9% content listened to',width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening < 25 % content',width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Message_Matrix_Childpack_Definitions =[
                                                     { field: 'messageWeek', name: 'Message Week',width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Total calls where > 75% content listened to ', width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Total calls where 50% to 75% content listened to', width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Total calls where 25% to 49.9% content listened to',width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening < 25 % content',width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Message_Listnership_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                     { field: 'locationName',
                                                        cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                        enableHiding: false,width:"10%"
                                                     },
                                                     { field: 'totalBeneficiariesCalled', name: 'Total beneficiaries Called',width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredAtleastOnce', name: 'Beneficiaries who have answered at least one call',width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredMoreThan75', name: 'Beneficiaries who have answered more than 75% content (consolidated)', width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnswered50To75', name: 'Beneficiaries who have answered 50 to 75% content (consolidated)', width:"*", enableHiding: false},
                                                     { field: 'beneficiariesAnswered25To50', name: 'Beneficiaries who have answered 25 to 49.9% content (consolidated)',width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnswered1To25', name: 'Beneficiaries who have answered less than 25% content (consolidated)', width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredNoCalls', name: 'Beneficiaries who have not answered any calls', width:"*", enableHiding: false },
            ]

            $scope.Kilkari_Subscriber_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                     { field: 'locationName',
                                                        cellTemplate:'<a class="btn primary aggregate-location" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType)">{{ COL_FIELD }}</a>',
                                                        enableHiding: false,width:"10%"
                                                     },
                                                     { field: 'totalSubscriptionsStart', name: 'Total Subscription at the start of the period',width:"12%", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsReceived', name: 'Total beneficiary Records Received from RCH/MCTS',width:"13%", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsEligible', name: 'Total beneficiary Records Found Eligible for Subscriptions', width:"13%", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsRejected', name: 'Total beneficiary records rejected due to wrong/duplicate mobile numbers', width:"14%", enableHiding: false},
                                                     { field: 'totalBeneficiaryRecordsAccepted', name: 'Total beneficiary Records accepted As Subscriptions',width:"12%", enableHiding: false },
                                                     { field: 'totalSubscriptionsCompleted', name: 'Total number of subscriptions who have completed their packs', width:"12%", enableHiding: false },
                                                     { field: 'totalSubscriptionsEnd', name: 'Total Subscription at the end of the period', width:"10%", enableHiding: false },
            ]

            $scope.Kilkari_Thematic_Content_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, cellTemplate: '<div>{{rowRenderIndex+1}}</div>'},
                                                     { field: 'theme', name: 'Theme',width:"*", enableHiding: false },
                                                     { field: 'messageWeekNumber', name: 'Message Number (Week)',width:"*", enableHiding: false },
                                                     { field: 'uniqueBeneficiariesCalled', name: 'Number of unique beneficiaries called', width:"*", enableHiding: false },
                                                     { field: 'callsAnswered', name: 'Number of calls answered', width:"*", enableHiding: false},
                                                     { field: 'minutesConsumed', name: 'Number of minutes consumed ',width:"*", enableHiding: false }
             ]

            $scope.Kilkari_RepeatListener_Numberdata_Definitions =[
                                                     { field: 'month', name: 'Month number',width:"*", enableHiding: false },
                                                     { field: 'fiveCallsAnswered', name: '5 calls answered', width:"*", enableHiding: false},
                                                     { field: 'fourCallsAnswered', name: '4 calls answered', width:"*", enableHiding: false},
                                                     { field: 'threeCallsAnswered', name: '3 calls answered',width:"*", enableHiding: false },
                                                     { field: 'twoCallsAnswered', name: '2 calls answered',width:"*", enableHiding: false },
                                                     { field: 'oneCallAnswered', name: '1 call answered',width:"*", enableHiding: false },
                                                     { field: 'noCallsAnswered', name: '0 calls answered',width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', width:"*", enableHiding: false },

            ]

            $scope.Kilkari_RepeatListener_Percentdata_Definitions =[
                                                     { field: 'month', name: 'Month number',width:"*", enableHiding: false },
                                                     { field: 'fiveCallsAnsweredPercent', name: '5 calls answered', width:"*", enableHiding: false},
                                                     { field: 'fourCallsAnsweredPercent', name: '4 calls answered', width:"*", enableHiding: false},
                                                     { field: 'threeCallsAnsweredPercent', name: '3 calls answered',width:"*", enableHiding: false },
                                                     { field: 'twoCallsAnsweredPercent', name: '2 calls answered',width:"*", enableHiding: false },
                                                     { field: 'oneCallAnsweredPercent', name: '1 call answered',width:"*", enableHiding: false },
                                                     { field: 'noCallsAnsweredPercent', name: '0 calls answered',width:"*", enableHiding: false }
            ]



            $scope.drillDownData = function(locationId,locationType){

                  if(angular.lowercase(locationType) == "state"){
                    reportRequest.stateId = locationId;
                    reportRequest.districtId = 0;
                    reportRequest.blockId = 0;

                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json'}
                        })
                        .then(function(result){

                            if($scope.isAggregateReport()){
                                $scope.waiting = false;
                                if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                    $scope.hideGrid = false;
                                    $scope.gridOptions1.columnDefs[1].displayName = 'District';
                                    $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                }
                                else{
                                    $scope.showEmptyData = true;
                                    $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                            }

                        })
                  }
                  else if(angular.lowercase(locationType) == "national"){
                       reportRequest.stateId = 0;
                       reportRequest.districtId = 0;
                       reportRequest.blockId = 0;
                       $http({
                               method  : 'POST',
                               url     : $scope.getReportUrl,
                               data    : reportRequest, //forms user object
                               headers : {'Content-Type': 'application/json'}
                           })
                           .then(function(result){

                               if($scope.isAggregateReport()){
                                   $scope.waiting = false;
                                   if(result.data.tableData.length >0){
                                      $scope.gridOptions1.data = result.data.tableData;
                                      $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                       $scope.hideGrid = false;
                                       $scope.gridOptions1.columnDefs[1].displayName = 'State';
                                       $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                   }
                                   else{
                                       $scope.showEmptyData = true;
                                       $scope.hideGrid = true;
                                   }
                                   $scope.gridOptions = $scope.gridOptions1;
                               }

                           })
                     }
                  else if(angular.lowercase(locationType) == "district"){
                     reportRequest.districtId = locationId;
                     reportRequest.blockId = 0;
                     $http({
                             method  : 'POST',
                             url     : $scope.getReportUrl,
                             data    : reportRequest, //forms user object
                             headers : {'Content-Type': 'application/json'}
                         })
                         .then(function(result){

                             if($scope.isAggregateReport()){
                                 $scope.waiting = false;
                                 if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                     $scope.hideGrid = false;
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Block';
                                     $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                 }
                                 else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                 }
                                 $scope.gridOptions = $scope.gridOptions1;
                             }

                         })
                  }
                  else if(angular.lowercase(locationType) == "block"){
                    reportRequest.blockId = locationId;
                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json'}
                        })
                        .then(function(result){

                            if($scope.isAggregateReport()){
                                $scope.waiting = false;
                                if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                    $scope.hideGrid = false;
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Subcenter';
                                     $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                }
                                else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                            }

                        })
                  }
            }

		}])
})()