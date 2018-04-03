(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory','$window','$q','uiGridConstants','exportUiGridService','uiGridExporterService','uiGridExporterConstants', function($scope, $state, $http, UserFormFactory,$window,$q,uiGridConstants,exportUiGridService,uiGridExporterService,uiGridExporterConstants){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
				else{
					UserFormFactory.downloadCurrentUser()
					.then(function(result){
						UserFormFactory.setCurrentUser(result.data);
						if($scope.currentUser.accessLevel == "STATE"){
						    excelHeaderName.stateName = result.data.stateName;
						}
						else if($scope.currentUser.accessLevel == "DISTRICT"){
						    excelHeaderName.stateName = result.data.stateName;
                            excelHeaderName.districtName = result.data.districtName;
						}
						else if($scope.currentUser.accessLevel == "BLOCK"){
                            excelHeaderName.stateName = result.data.stateName;
                            excelHeaderName.districtName = result.data.districtName;
                            excelHeaderName.blockName = result.data.blockName;
                        }

                        if((($state.current.name)===("reports"))){
                            $scope.getStatesByService(null);
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }
					})
				}
			})

			var reportRequest = {};
			var ExcelData = {};
            $scope.sundays = [];
 			$scope.reports = [];
			$scope.states = [];
			$scope.districts = [];
			$scope.blocks = [];
			$scope.circles = [];
			$scope.datePickerContent = " Month";
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
            $scope.formatters = [];
            $scope.headerFromDate = '';
            $scope.headerToDate = '';
            $scope.matrixContent1 = '';
            $scope.matrixContent2 = '';
            $scope.state = "";
            $scope.district = "";
            var parentScope = $scope.$parent;
            parentScope.child = $scope;
            var fileName;
            var dateString;
            var excelHeaderName = {
                stateName : "ALL",
                districtName : "ALL",
                blockName : "ALL"

            };


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
				return ($scope.reports[0] == null || !(($state.current.name)===("reports")));
			}
			$scope.disableReport = function(){
				return ($scope.reportCategory == null || !(($state.current.name)===("reports")));
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

				if(!(($state.current.name)===("reports"))){
                    switch($state.current.name){
                        case "MA Cumulative Summary":
                        case "MA Subscriber":
                        case "MA Performance":
                        var reportCategorytemp = $scope.getArrayElementByName($scope.reports,"Mobile Academy Reports");
                        break;
                        case "Kilkari Cumulative Summary":
                        case "Kilkari Beneficiary Completion":
                        case "Kilkari Usage":
                        case "Kilkari Call":
                        case "Kilkari Message Matrix":
                        case "Kilkari Listening Matrix":
                        case "Kilkari Thematic Content":
                        case "Kilkari Repeat Listener":
                        case "Kilkari Subscriber":
                        case "Kilkari Message Listenership":
                        case "Kilkari Aggregate Beneficiary":
                        var reportCategorytemp = $scope.getArrayElementByName($scope.reports,"Kilkari Reports");
                        break;
                    }
                    $scope.selectReportCategory (reportCategorytemp);
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
                    $scope.periodTypeContent = " Year";
                    $scope.dateFormat = "yyyy";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'year';
                    $scope.datePickerOptions.minMode = 'year';
                    $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                }
                if($scope.periodDisplayType == 'Month'){
                    $scope.periodTypeContent = " Month";
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
                if(!(($state.current.name)===("reports"))){
                    $scope.selectReport ($scope.getArrayElementByName(item.options,$state.current.name));

                    //console.log();
                }
			}

			$scope.getArrayElementByName = function(arr, value) {
                var element = [];
              for (var i=0 ; i<arr.length; i++) {

                if (arr[i].name === value)
                 element.push(arr[i]);
              }
              return element[0];
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
				$scope.clearFile();
				$scope.dt = null;
				$scope.setDateOptions();
				if($scope.userHasOneCircle()){
                	$scope.selectCircle($scope.circles[0]);
                }
                if($scope.report.reportEnum == 'Kilkari_Message_Matrix' || $scope.report.reportEnum == 'Kilkari_Listening_Matrix' || $scope.report.reportEnum == 'Kilkari_Usage' || $scope.report.reportEnum == 'Kilkari_Message_Listenership' || $scope.report.reportEnum == 'Kilkari_Thematic_Content' || $scope.report.reportEnum == 'Kilkari_Aggregate_Beneficiary'){
                    $scope.periodType = ['Year','Month','Quarter'];
                }
                else if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                    $scope.periodType = ['Month'];
                }
                else
                    $scope.periodType = ['Year','Month','Quarter', 'Custom Range'];
                if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1)  ){
                	$scope.datePickerContent = "Select Week";
                }
                else
                    $scope.datePickerContent = " Month";
                $scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                if($scope.report.name == 'MA Cumulative Summary' || $scope.report.reportEnum == 'Kilkari_Cumulative_Summary'){
                    $scope.dateFormat = 'yyyy-MM-dd';
                }

                $scope.gridOptions1.exporterExcelSheetName = $scope.report.name;
                excelHeaderName.reportName = $scope.report.name;


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


			$scope.cropAggregate = function(name){
                if(name == null){
                    return "";
                }
                if(name.length > 30){
                    return name.substring(0, 27) + "..."
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
			    if($scope.isAggregateReport()){
			        var minDate = new Date(2016, 11, 01);
			    }
                else{
                    var minDate = new Date(2015, 09, 01);
                }
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

				if(!$scope.isCircleReport() && $scope.state != null && Date.parse($scope.state.serviceStartDate) > minDate){
					minDate = $scope.state.serviceStartDate;
				}
				if($scope.isCircleReport() && $scope.circle != null && Date.parse($scope.circle.serviceStartDate) > minDate){
					minDate = $scope.circle.serviceStartDate;
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
					excelHeaderName.stateName = state.stateName;
				}
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                            $scope.dt1 = null;
                            $scope.dt2 = null;
                            $scope.hideGrid = true;
                            $scope.hideMessageMatrix = true;
                            $scope.showEmptyData = false;
                            $scope.setDateOptions();
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");
                            }
                        }

				$scope.clearFile();

			}
			$scope.clearState = function(){
				$scope.state = null;
				excelHeaderName.stateName = "ALL";
				$scope.clearDistrict();
				$scope.districts = [];
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                            $scope.dt1 = null;
                            $scope.dt2 = null;
                            $scope.hideGrid = true;
                            $scope.hideMessageMatrix = true;
                            $scope.showEmptyData = false;
                            $scope.setDateOptions();
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }


                $scope.clearFile();
			}
			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);
					$scope.clearDistrict()
					$scope.district = district;
					excelHeaderName.districtName = district.districtName;
				}
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                            $scope.dt1 = null;
                            $scope.dt2 = null;
                            $scope.hideGrid = true;
                            $scope.hideMessageMatrix = true;
                            $scope.showEmptyData = false;
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }


				$scope.clearFile();
			}
			$scope.clearDistrict = function(){
				$scope.district = null;
				excelHeaderName.districtName = "ALL";
				$scope.clearBlock();
				$scope.blocks = [];
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                            $scope.dt1 = null;
                            $scope.dt2 = null;
                            $scope.hideGrid = true;
                            $scope.hideMessageMatrix = true;
                            $scope.showEmptyData = false;
                            $scope.setDateOptions();
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }


                $scope.clearFile();
			}
			$scope.selectBlock = function(block){
				if(block != null){
					$scope.clearBlock();
					$scope.block = block;
					excelHeaderName.blockName = block.blockName;
				}
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                            $scope.dt1 = null;
                            $scope.dt2 = null;
                            $scope.hideGrid = true;
                            $scope.hideMessageMatrix = true;
                            $scope.showEmptyData = false;
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }


                $scope.clearFile();
			}
			$scope.clearBlock = function(){
				$scope.block = null;
				excelHeaderName.blockName = "ALL";
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                            $scope.dt1 = null;
                            $scope.dt2 = null;
                            $scope.hideGrid = true;
                            $scope.hideMessageMatrix = true;
                            $scope.showEmptyData = false;
                            $scope.setDateOptions();
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }


                $scope.clearFile();
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
			    if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) && $scope.dt != null) {
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
			return	UserFormFactory.isLoggedIn()
            .then(function(result){
            	if(!result.data){
            		$state.go('login', {});
            	}else{

                if($scope.reportCategory == null){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a report category")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a report category")
                        return;
                    }
                }
				if($scope.report == null){
				    if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a report")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a report")
                        return;
                    }
				}
				if($scope.dt == null && (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) ){
					if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a week")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a week")
                        return;
                    }

				}
				else if($scope.dt == null && (!$scope.isAggregateReport() )){
                	if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a month")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a month")
                        return;
                    }
				}
				else if($scope.periodDisplayType == '' && ($scope.isAggregateReport() ) && ($scope.report.name != 'MA Cumulative Summary' && $scope.report.reportEnum != 'Kilkari_Cumulative_Summary')){
                   if(UserFormFactory.isInternetExplorer()){
                       alert("Please select a period type")
                       return;
                   }
                   else{
                       UserFormFactory.showAlert("Please select a period type")
                       return;
                   }
                }
				else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType != 'Custom Range' && $scope.periodDisplayType != 'Quarter' && $scope.report.name != 'MA Cumulative Summary' && $scope.report.reportEnum != 'Kilkari_Cumulative_Summary') ){
                    if(UserFormFactory.isInternetExplorer()){
                          alert("Please select a " +  $scope.periodDisplayType)
                          return;
                    }
                    else{
                          UserFormFactory.showAlert("Please select a " +  $scope.periodDisplayType)
                          return;
                    }
                }
                else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Custom Range')){
                   if(UserFormFactory.isInternetExplorer()){
                      alert("Please select a start date")
                      return;
                   }
                   else{
                      UserFormFactory.showAlert("Please select a start date")
                      return;
                   }
                }
                 else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Quarter')){

                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a year")
                         return;
                   }
                   else{
                     UserFormFactory.showAlert("Please select a year")
                     return;
                   }

                }
                else if($scope.dt2 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Custom Range' || $scope.report.name == 'MA Cumulative Summary' || $scope.report.reportEnum == 'Kilkari_Cumulative_Summary' )){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select an end date")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("Please select an end date")
                         return;
                   }

                }

                else if($scope.periodDisplayType == 'Quarter' && $scope.quarterDisplayType == '' && ($scope.isAggregateReport() )){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a quarter type")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("Please select a quarter type")
                         return;
                   }

                }
                else if( ($scope.periodDisplayType == 'Custom Range') && ($scope.dt2 < $scope.dt1)){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("End date should be greater than start date")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("End date should be greater than start date")
                         return;
                   }

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
                               if(UserFormFactory.isInternetExplorer()){
                                     alert("Please select a state")
                                     return;
                               }
                               else{
                                 UserFormFactory.showAlert("Please select a state")
                                 return;
                               }
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
                           if(UserFormFactory.isInternetExplorer()){
                                 alert("Please select a circle")
                                 return;
                           }
                           else{
                             UserFormFactory.showAlert("Please select a circle")
                             return;
                           }

                    }
		    	}

		    	if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) && $scope.format == 'yyyy-MM'){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a week")
                         return;
                   }
                   else{
                     UserFormFactory.showAlert("Please select a week")
                     return;
                   }
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
                         dateString = $scope.dt1.getFullYear();
                         excelHeaderName.timePeriod = dateString;
                    }

                    else if($scope.periodDisplayType == 'Month' ){
                         if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth() - 6,1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth() + 1,0);
                            dateString = reportRequest.fromDate.getDate() + "_" +(reportRequest.fromDate.getMonth() + 1 ) + "_" + reportRequest.fromDate.getFullYear() + "to" + reportRequest.toDate.getDate() + "-" +(reportRequest.toDate.getMonth() + 1) +
                                         "_" + reportRequest.toDate.getFullYear();
                            excelHeaderName.timePeriod = reportRequest.fromDate.getDate() + "-" + (reportRequest.fromDate.getMonth() + 1 ) + "-" + reportRequest.fromDate.getFullYear() + " to " + reportRequest.toDate.getDate() + "-" + (reportRequest.toDate.getMonth() + 1) +
                                         "-" + reportRequest.toDate.getFullYear();

                         }else{
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth(),1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth() + 1,0);
                            dateString = (reportRequest.fromDate.getMonth()+ 1 ) + "_" + reportRequest.fromDate.getFullYear();
                            excelHeaderName.timePeriod = (reportRequest.fromDate.getMonth()+ 1 ) + "-" + reportRequest.fromDate.getFullYear();
                         }
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

                         dateString = (reportRequest.fromDate.getMonth() + 1) + "_" + reportRequest.fromDate.getFullYear() + "to" + (reportRequest.toDate.getMonth() + 1)  +
                                         "_" + reportRequest.toDate.getFullYear();
                         excelHeaderName.timePeriod = (reportRequest.fromDate.getMonth() + 1) + "-" + reportRequest.fromDate.getFullYear() + " to " + (reportRequest.toDate.getMonth() + 1)  +
                                         "-" + reportRequest.toDate.getFullYear();



                    }
                    else if($scope.periodDisplayType == 'Custom Range' ){
                        reportRequest.fromDate = $scope.dt1;
                        reportRequest.toDate = $scope.dt2;
                        dateString = reportRequest.fromDate.getDate() + "_" + (reportRequest.fromDate.getMonth() + 1 ) + "_" + reportRequest.fromDate.getFullYear() + "to" + reportRequest.toDate.getDate() + "_" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                     "_" + reportRequest.toDate.getFullYear();
                        excelHeaderName.timePeriod = reportRequest.fromDate.getDate() + "-" + (reportRequest.fromDate.getMonth() + 1 ) + "-" + reportRequest.fromDate.getFullYear() + " to " + reportRequest.toDate.getDate() + "-" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                                                          "-" + reportRequest.toDate.getFullYear();
                    }
                    else{
                        reportRequest.toDate = $scope.dt2;
                        dateString =   "till_" + reportRequest.toDate.getDate() + "_" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                             "_" + reportRequest.toDate.getFullYear();
                        excelHeaderName.timePeriod = "till " + reportRequest.toDate.getDate() + "-" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                      "-" + reportRequest.toDate.getFullYear();
                    }
                }


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
					    else if($scope.report.reportEnum == 'Kilkari_Aggregate_Beneficiary'){
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
                        else if($scope.report.reportEnum == 'Kilkari_Message_Listenership'){
                             $scope.gridOptions1.columnDefs = $scope.Kilkari_Message_Listenership_Definitions;
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
                             $scope.gridOptions1.showColumnFooter = true;
                             $scope.reportBreadCrumbData = result.data.breadCrumbData;
                             $scope.hideGrid = false;
                             console.log(angular.lowercase(result.data.tableData[0].locationType));
                             if(angular.lowercase(result.data.tableData[0].locationType) == 'state'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'State';
                                 }
                                 else if(angular.lowercase(result.data.tableData[0].locationType) == 'district' || angular.lowercase(result.data.tableData[0].locationType) == 'differencestate'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'District';

                                 }
                                  else if(angular.lowercase(result.data.tableData[0].locationType) == 'block' || angular.lowercase(result.data.tableData[0].locationType) == 'differencedistrict'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Block';

                                 }
                                 else if(angular.lowercase(result.data.tableData[0].locationType) == 'subcenter' || angular.lowercase(result.data.tableData[0].locationType) == 'differenceblock'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Subcenter';
                                 }
                            }
                            else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }

                            fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;

                        }
                        if($scope.report.reportEnum == 'Kilkari_Listening_Matrix'){
                            if(result.data.tableData.length >0){
                                 $scope.gridOptions1.data = result.data.tableData;
                                 $scope.gridOptions1.showColumnFooter = false;
                                 $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                 $scope.hideGrid = false;
                                 fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                            }
                            else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }

                        }
                        if($scope.report.reportEnum == 'Kilkari_Thematic_Content'){
                            if(result.data.tableData.length >0){
                                 $scope.gridOptions1.data = result.data.tableData;
                                 $scope.gridOptions1.showColumnFooter = true;
                                 $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                 $scope.hideGrid = false;
                                 fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                            }
                            else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                        }
                        if($scope.report.reportEnum == 'Kilkari_Message_Matrix'){
                            $scope.reportBreadCrumbData = result.data.breadCrumbData;
                            if(result.data.motherData.length >0){
                                $scope.gridOptions1.data = result.data.motherData;
                                $scope.gridOptions1.showColumnFooter = false;
                                $scope.hideGrid = false;
                                $scope.matrixContent1 ='Kilkari MotherPack Data';

                            }
                             else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                            if(result.data.childData.length >0){
                                $scope.gridOptions2.data = result.data.childData;
                                $scope.gridOptions1.showColumnFooter = false;
                                $scope.hideMessageMatrix = false;
                                $scope.matrixContent2 ='Kilkari ChildPack Data';
                            }
                            else{
                                $scope.hideMessageMatrix = true;
                            }
                            fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName;
                        }
                       if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                            $scope.reportBreadCrumbData = result.data.breadCrumbData;
                            if(result.data.numberData.length >0){
                                $scope.gridOptions1.data = result.data.numberData;
                                $scope.gridOptions1.showColumnFooter = false;
                                $scope.hideGrid = false;
                                $scope.matrixContent1 ='Beneficiary Count';
                            }
                             else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                            if(result.data.percentData.length >0){
                                $scope.gridOptions2.data = result.data.percentData;
                                $scope.gridOptions1.showColumnFooter = false;
                                $scope.hideMessageMatrix = false;
                                $scope.matrixContent2 ='Beneficiary Percentage';
                            }
                            else{
                                $scope.hideMessageMatrix = true;
                            }
                            fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName;
                       }

                         $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;
                         $scope.gridOptions1.exporterExcelSheetName = $scope.report.name;
                         $scope.gridOptions = $scope.gridOptions1;
                         $scope.gridOptions_Message_Matrix = $scope.gridOptions2;


                    }
                }

				)
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

			return	UserFormFactory.isLoggedIn()
            .then(function(result){
            	if(!result.data){
            		$state.go('login', {});
            	}else{

                if((($state.current.name)===("reports"))){
                    $scope.report = null;
                    $scope.reportCategory = null;
                }
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
				$scope.datePickerContent = " Month";
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
                        if((($state.current.name)===("reports"))){
                            $scope.periodDisplayType = '';
                        }
                        else{
                            if(!(($state.current.name)===("Kilkari Cumulative Summary"))&&!(($state.current.name)===("MA Cumulative Summary"))){
                            $scope.setDateOptions();
                            $scope.selectPeriodType("Month");}
                        }

				$scope.showEmptyData = false;
				}
			})
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

            var startMonth = 10 //September
            var startDate = 18 //Start Date

			$scope.open1 = function() {
				$scope.popup1.opened = true;
				var currentDate = new Date();

				if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) ){
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

				if(($scope.reportCategory == 'Mobile Academy Reports' ||  $scope.reportCategory == 'Kilkari Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) && ($scope.format == 'yyyy-MM-dd' || $scope.format == 'yyyy-MM' )){
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

                return	UserFormFactory.isLoggedIn()
                   .then(function(result){
                    if(!result.data){
                              alert("Session Timed out...Please Login Again")
                                $window.location.href= backend_root+"nms/logout";
                   }})
              if($scope.sundaysTable)
                $scope.sundaysTable = false;
            });

            $scope.exportToExcel = function(){

                columns = $scope.gridApi.grid.options.showHeader ? uiGridExporterService.getColumnHeaders($scope.gridApi.grid, 'visible') : [];

                var headers=[]
                columns.forEach(function (c) {
                                        headers.push( c.displayName || c.value || columns[i].name);
                                    }, this);
                ExcelData.columnHeaders = headers;

                var exportData = uiGridExporterService.getData($scope.gridApi.grid, "visible", "visible");
                var data = [];

                for (i = 0; i < exportData.length; i++) {
                       var temprow=[];
                       for (j = 0; j < exportData[i].length; j++) {
                              var temp = exportData[i][j].value;
                                    if((excelHeaderName.reportName == "Kilkari Call" && (j == "7"||j == "8") )||
                                        (excelHeaderName.reportName == "MA Cumulative Summary" && (j == "6"||j == "7"||j == "8"))||
                                        (excelHeaderName.reportName == "Kilkari Cumulative Summary" && (j == "4"||j == "3") )||
                                        (excelHeaderName.reportName == "Kilkari Thematic Content" && (j == "4") )||
                                        (excelHeaderName.reportName == "Kilkari Beneficiary Completion" && (j == "2") )){
                                         temp = (temp.toFixed(2));
                                    }
                           temprow.push(temp);
                       }
                       data.push(temprow);
                    }
            ExcelData.reportData = data;

            var footerData = [];
                        var v;
                                if(excelHeaderName.reportName != "Kilkari Message Matrix" && excelHeaderName.reportName != "Kilkari Listening Matrix" && excelHeaderName.reportName != "Kilkari Repeat Listener"){
                                    $scope.gridApi.grid.columns.forEach(function (ft) {

                                       if(ft.displayName == "State" || ft.displayName == "District" || ft.displayName == "Block" || ft.displayName == "Subcenter" || ft.displayName == "Message Number (Week)" )
                                           v = "Total";

                                       else if(ft.displayName == "Average Duration Of Call" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                                           var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[4].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue());
                                           v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "Average Number of Weeks in Service" && excelHeaderName.reportName == "Kilkari Beneficiary Completion"){
                                           var temp = $scope.gridApi.grid.columns.length==0?0.00: ($scope.gridApi.grid.columns[3].getAggregationValue()/$scope.gridApi.grid.columns.length);
                                           v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "Average Duration Of Calls" && excelHeaderName.reportName == "Kilkari Call"){
                                           var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[8].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue());
                                           v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "Total Billable Minutes" && excelHeaderName.reportName == "Kilkari Call"){
                                           var temp = ft.getAggregationValue();
                                           v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "Total Billable Minutes Played" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                                           var temp = ft.getAggregationValue();
                                           v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "Number Of Minutes Consumed" && excelHeaderName.reportName == "Kilkari Thematic Content"){
                                           var temp = ft.getAggregationValue();
                                           v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "% Not Started Course" && excelHeaderName.reportName == "MA Cumulative Summary"){
                                          var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[4].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                                          v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "% Successfully Completed" && excelHeaderName.reportName == "MA Cumulative Summary"){
                                          var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00:($scope.gridApi.grid.columns[5].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue())*100;
                                          v = (temp.toFixed(2));
                                       }
                                       else if(ft.displayName == "% Failed the course" && excelHeaderName.reportName == "MA Cumulative Summary"){
                                          var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00:($scope.gridApi.grid.columns[6].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue())*100;
                                          v = (temp.toFixed(2));
                                       }
                                       else{
                                           v = ft.getAggregationValue();
                                       }

                                       if(ft.displayName != "S No."){
                                            footerData.push(v);
                                       }

                                   }, this);

                                }
                if(excelHeaderName.reportName == "Kilkari Message Matrix" ||  excelHeaderName.reportName == "Kilkari Repeat Listener"){
                columns1 = $scope.gridApi1.grid.options.showHeader ? uiGridExporterService.getColumnHeaders($scope.gridApi1.grid, 'visible') : [];

                var headers1=[]
                columns1.forEach(function (c) {
                                        headers1.push( c.displayName || c.value || columns1[i].name);
                                    }, this);
                ExcelData.columnHeaders1 = headers1;

                var exportData1 = uiGridExporterService.getData($scope.gridApi1.grid, "visible", "visible");
                var data1 = [];

                for (i = 0; i < exportData1.length; i++) {
                       var temprow1=[];
                       for (j = 0; j < exportData1[i].length; j++) {
                              var temp1 = exportData1[i][j].value;
                                    if((excelHeaderName.reportName == "Kilkari Call" && (j == "7"||j == "8") )||
                                        (excelHeaderName.reportName == "MA Cumulative Summary" && (j == "6"||j == "7"||j == "8"))||
                                        (excelHeaderName.reportName == "Kilkari Cumulative Summary" && (j == "4"||j=="3") )||
                                        (excelHeaderName.reportName == "Kilkari Thematic Content" && (j == "4") )||
                                        (excelHeaderName.reportName == "Kilkari Beneficiary Completion" && (j == "2") )){
                                         temp1 = (temp1.toFixed(2));
                                    }
                           temprow1.push(temp1);
                       }
                       data1.push(temprow1);
                    }
            ExcelData.reportData1 = data1;}else{
            ExcelData.columnHeaders1 = [];
            ExcelData.reportData1 = [];
            }

                  ExcelData.colunmFooters = footerData;
                  ExcelData.stateName = excelHeaderName.stateName;
                  ExcelData.districtName = excelHeaderName.districtName;
                  ExcelData.blockName = excelHeaderName.blockName;
                  ExcelData.reportName = excelHeaderName.reportName;
                  ExcelData.timePeriod = excelHeaderName.timePeriod;

                $http({
                                    method  : 'POST',
                                    url     : backend_root + 'nms/user/downloadAgg',
                                    data    : ExcelData, //forms user object
                                    responseType: 'arraybuffer',
                                    headers : {'Content-Type': 'application/json '}
                                }).then(function(response){
                                console.log(response);

                                     var fileName = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';
                                                         fileName = fileName.replace("*","");
                                                         fileName += '.xlsx';

                                         saveAs(new Blob([response.data], {
                                                                 type: 'application/octet-stream'
                                                             }), fileName);

                                         if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                                             window.navigator.msSaveOrOpenBlob(blob, fileName);
                                         } else {
                                             var objectUrl = URL.createObjectURL(blob);
                                             window.open(objectUrl);
                                         }
                                     }
                                );

                  // exportUiGridService.exportToExcel('sheet 1', $scope.gridApi,$scope.gridApi1, 'visible', 'visible', excelHeaderName);

            }

            $scope.exportCsv = function() {

              exportUiGridService.exportToCsv1($scope.gridApi,$scope.gridApi1, 'visible', 'visible', excelHeaderName);

            };

            $scope.exportPdf = function() {
            var fileName1 = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';
                             fileName1 = fileName1.replace("*","");
                             fileName1 += '.pdf';
            exportUiGridService.exportToPdf1($scope.gridApi,$scope.gridApi1,excelHeaderName,$scope.reportCategory,$scope.matrixContent1,$scope.matrixContent2,uiGridExporterConstants,'visible', 'visible',fileName1);
            };

            var canceler = $q.defer();
            $scope.gridOptions1 = {
                enableSorting: true,
                showColumnFooter: true,
                enableVerticalScrollbar : 0,
                excessRows :1000,
                onRegisterApi: function(gridApi){
                      $scope.gridApi = gridApi;
                    },
              };

            $scope.gridOptions2 = {
                enableSorting: true,
                useExternalSorting: true,
                enableVerticalScrollbar : 0,
                excessRows :1000,
                onRegisterApi: function(gridApi){
                      $scope.gridApi1 = gridApi;
                    },
              };

            $scope.MA_Cumulative_Column_Definitions =[
                                                       {name: 'S No.', displayName: 'S No.',width:"6%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no" >{{rowRenderIndex+1}}</p>'},
                                                       { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>', defaultSort: { direction: uiGridConstants.ASC },
                                                         cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                         width: '12%', enableHiding: false,
                                                       },
                                                       { field: 'ashasRegistered', displayName : 'No of Registered ASHA', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'ashasStarted', displayName : ' No of ASHA Started Course',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'ashasNotStarted', displayName : ' No of ASHA Not Started Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'ashasCompleted' , displayName : 'No of ASHA Successfully Completed the Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"13%", enableHiding: false},
                                                       { field: 'ashasFailed' , displayName : 'No of ASHA who failed the course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'notStartedpercentage' , displayName : '% Not Started Course',cellFilter: 'number: 2', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[2].getAggregationValue()==0 ? 0.00:(grid.columns[4].getAggregationValue()/grid.columns[2].getAggregationValue()) *100 | number:2}}</div>', width:"*", enableHiding: false},
                                                       { field: 'completedPercentage' , displayName : '% Successfully Completed',cellFilter: 'number: 2', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[3].getAggregationValue()==0 ? 0.00:(grid.columns[5].getAggregationValue()/grid.columns[3].getAggregationValue())*100 | number:2}}</div>', width:"*", enableHiding: false},
                                                       { field: 'failedpercentage' , displayName : '% Failed the course',cellFilter: 'number: 2', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[3].getAggregationValue()==0 ? 0.00:(grid.columns[6].getAggregationValue()/grid.columns[3].getAggregationValue()) *100 | number:2}}</div>', width:"*", enableHiding: false},
                                                     ],


            $scope.MA_Performance_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"6%", enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                         { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                            cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                            enableHiding: false, width:"12%",

                                                         },
                                                         { field: 'ashasStarted', displayName: 'Number of ASHA Started Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*",enableHiding: false },
                                                         { field: 'ashasAccessed', displayName: 'Number of ASHA Pursuing Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                         { field: 'ashasNotAccessed', displayName: 'Number of ASHA not Pursuing Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'ashasCompleted', displayName: 'Number of ASHA Successfully Completed Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"18%",enableHiding: false},
                                                         { field: 'ashasFailed',  displayName: 'Number of ASHA who Failed the Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                        ],

            $scope.MA_Subscriber_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                         { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                            cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                            enableHiding: false,width:"14%",

                                                         },
                                                         { field: 'registeredNotCompletedStart', displayName: 'Number of ASHA Registered But Not Completed the Course(Period Start)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false },
                                                         { field: 'recordsReceived', displayName: 'Number of ASHA Records Received Through Web Service', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                         { field: 'ashasRejected', displayName: 'Number of ASHA Records Rejected', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'ashasRegistered', displayName: 'Number of ASHA Subscriptions Added', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'ashasCompleted',  displayName: 'Number of ASHA Successfully Completed the Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'registeredNotCompletedend',  displayName: 'Number of ASHA Registered But Not Completed the Course (Period End)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false},
                                                        ],

            $scope.Kilkari_Cumulative_Summary_Definitions =[
                                                             {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                             { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                                cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                                enableHiding: false, width:"*",
                                                             },
                                                             { field: 'uniqueBeneficiaries', name: 'Total unique beneficiaries',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                             { field: 'successfulCalls', name: 'Total successful calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                             { field: 'billableMinutes',cellFilter: 'number: 2', name: 'Total billable minutes played', footerCellFilter: 'number:2',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                             { field: 'averageDuration',cellFilter: 'number: 2', name: 'Average duration of call', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[3].getAggregationValue()==0 ? 0.00 : grid.columns[4].getAggregationValue()/grid.columns[3].getAggregationValue() | number:2}}</div>',   width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Usage_Definitions =[
                                                 {name: 'S No.', displayName: 'S No.',width:"5%", enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                 { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                    cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                    enableHiding: false, width:"*",
                                                 },
                                                 { field: 'beneficiariesCalled', name: 'Total beneficiaries Called', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                 { field: 'answeredCall', name: 'Beneficiaries who have answered at least one call', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                 { field: 'calls_75_100', name: 'Beneficiaries who have listened more than 75% content (avg)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                 { field: 'calls_50_75', name: 'Beneficiaries who have listened 50 to 75% content (avg)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                 { field: 'calls_25_50', name: 'Beneficiaries who have listened 25 to 50% content (avg)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                 { field: 'calls_1_25', name: 'Beneficiaries who have listened less than 25% content (avg)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                 { field: 'calledInbox', name: 'Beneficiaries who have called the Kilkari Inbox', width:"*",  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false},

            ]

            $scope.Kilkari_Aggregate_Beneficiaries_Definitions =[
                                                     {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"10%"
                                                     },
                                                     { field: 'beneficiariesCalled', name: 'Total beneficiaries Called',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'answeredAtleastOneCall', name: 'Beneficiaries who have answered at least one call',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'selfDeactivated', name: 'Beneficiaries who have self-deactivated',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                     { field: 'notAnswering', name: 'Beneficiaries deactivated for not answering',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'lowListenership', name: 'Beneficiaries deactivated for low listenership',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                     { field: 'systemDeactivation', displayName: 'Beneficiaries deactivated by system through MCTS/RCH updates',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"11%", enableHiding: false },
                                                     { field: 'motherCompletion', name: 'Beneficiaries completed Mother Pack',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                     { field: 'childCompletion', name: 'Beneficiaries completed Child Pack',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'calledInbox', name: 'Beneficiaries who have called the Kilkari Inbox',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'joinedSubscription', name: 'Beneficiaries who have joined the subscription',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Beneficiary_Completion_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"12%"
                                                     },
                                                     { field: 'completedBeneficiaries', name: 'Total beneficiaries Completed Program',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'avgWeeks', name: 'Average Number of Weeks in Service',cellFilter: 'number: 2', footerCellFilter: 'number:2', aggregationType: uiGridConstants.aggregationTypes.avg, aggregationHideLabel: true,  aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'calls_75_100', name: 'Beneficiaries who have listened more than 75% content (consolidated)',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'calls_50_75', name: 'Beneficiaries who have listened 50 to 75% content (consolidated)',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'calls_25_50', name: 'Beneficiaries who have listened 25 to 50% content (consolidated)',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'calls_1_25', name: 'Beneficiaries who have listened less than 25% content (consolidated)',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Listening_Matrix_Definitions =[
                                                     { field: 'percentageCalls', name: 'Listening Percentage', enableSorting: false,width:"30%", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Listening > 75 % content', enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Listening 50 to 75 % content',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_25_50', name: 'Listening 25 to 50 % content', enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening < 25 % content',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', enableSorting: false,width:"10%", enableHiding: false },

            ]

            $scope.Kilkari_Call_Report_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"12%"
                                                     },
                                                     { field: 'callsAttempted', name: 'Total Calls Attempted',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'successfulCalls', name: 'Total Number of Successful Calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Total calls where > 75% content listened to',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Total calls where 50% to 75% content listened to',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Total calls where 25% to 50% content listened to',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Total calls where < 25%  content listened to',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'billableMinutes', name: 'Total Billable minutes',cellFilter: 'number: 2',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, footerCellFilter: 'number:2', width:"*", enableHiding: false },
                                                     { field: 'avgDuration', name: 'Average Duration of Calls',cellFilter: 'number: 2',footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[3].getAggregationValue()==0)?0.00: grid.columns[8].getAggregationValue()/grid.columns[3].getAggregationValue() | number:2}}</div>', width:"*", enableHiding: false},
                                                     { field: 'callsToInbox', name: 'Total number of calls to inbox where content is played',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
            ]

            $scope.Kilkari_Message_Matrix_Motherpack_Definitions =[
                                                     { field: 'messageWeek', name: 'Message Week',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Listening > 75% content', enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Listening 50 to 75% content', enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Listening 25 to 50% content',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening < 25 % content',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', enableSorting: false,width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Message_Matrix_Childpack_Definitions =[
                                                     { field: 'messageWeek', name: 'Message Week',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Listening > 75% content', enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Listening 50 to 75% content',enableSorting: false, width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Listening 25 to 50% content',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening < 25 % content',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', enableSorting: false,width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Message_Listenership_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"12%"
                                                     },
                                                     { field: 'totalBeneficiariesCalled', name: 'Total beneficiaries Called',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredAtleastOnce', name: 'Beneficiaries who have answered at least one call',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredMoreThan75', name: 'Beneficiaries who have answered more than 75% calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnswered50To75', name: 'Beneficiaries who have answered 50 to 75% calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'beneficiariesAnswered25To50', name: 'Beneficiaries who have answered 25 to 50% calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnswered1To25', name: 'Beneficiaries who have answered less than 25% calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredNoCalls', name: 'Beneficiaries who have not answered any calls',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
            ]

            $scope.Kilkari_Subscriber_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="btn aggregate-location remove-link" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false,width:"10%"
                                                     },
                                                     { field: 'totalSubscriptionsStart', name: 'Total Subscription at the start of the period',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsReceived', displayName: 'Total beneficiary Records Received from RCH/MCTS',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsEligible', name: 'Total beneficiary Records Found Eligible for Subscriptions',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalRecordsRejected', name: 'Total beneficiary records rejected',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false},
                                                     { field: 'totalBeneficiaryRecordsAccepted', name: 'Total beneficiary Records accepted As Subscriptions',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalSubscriptionsCompleted', name: 'Total number of subscriptions who have completed their packs',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalSubscriptionsEnd', name: 'Total Subscription at the end of the period', width:"10%",  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false },
            ]

            $scope.Kilkari_Thematic_Content_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'theme',  cellTooltip: true, name: 'Theme', width:"*", enableHiding: false },
                                                     { field: 'messageWeekNumber', name: 'Message Number (Week)', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>', width:"*", enableHiding: false },
                                                     { field: 'uniqueBeneficiariesCalled', name: 'Number of unique beneficiaries called', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                     { field: 'callsAnswered', name: 'Number of calls answered', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                     { field: 'minutesConsumed', name: 'Number of minutes consumed', cellFilter: 'number: 2', footerCellFilter: 'number:2',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false }
             ]

            $scope.Kilkari_RepeatListener_Numberdata_Definitions =[
                                                     { field: 'month', enableSorting: false,name: 'Month',width:"*", enableHiding: false },
                                                     { field: 'fiveCallsAnswered',enableSorting: false, name: '5 calls answered', width:"*", enableHiding: false},
                                                     { field: 'fourCallsAnswered',enableSorting: false, name: '4 calls answered', width:"*", enableHiding: false},
                                                     { field: 'threeCallsAnswered', enableSorting: false,name: '3 calls answered',width:"*", enableHiding: false },
                                                     { field: 'twoCallsAnswered', enableSorting: false,name: '2 calls answered',width:"*", enableHiding: false },
                                                     { field: 'oneCallAnswered', enableSorting: false,name: '1 call answered',width:"*", enableHiding: false },
                                                     { field: 'noCallsAnswered', enableSorting: false,name: '0 calls answered',width:"*", enableHiding: false },
                                                     { field: 'total', enableSorting: false,name: 'Total', width:"*", enableHiding: false },

            ]

            $scope.Kilkari_RepeatListener_Percentdata_Definitions =[
                                                     { field: 'month', enableSorting: false,name: 'Month',width:"*", enableHiding: false },
                                                     { field: 'fiveCallsAnsweredPercent', cellFilter: 'number: 2', enableSorting: false,name: '5 calls answered', width:"*", enableHiding: false},
                                                     { field: 'fourCallsAnsweredPercent',cellFilter: 'number: 2', enableSorting: false,name: '4 calls answered', width:"*", enableHiding: false},
                                                     { field: 'threeCallsAnsweredPercent', cellFilter: 'number: 2', enableSorting: false,name: '3 calls answered',width:"*", enableHiding: false },
                                                     { field: 'twoCallsAnsweredPercent', cellFilter: 'number: 2', enableSorting: false,name: '2 calls answered',width:"*", enableHiding: false },
                                                     { field: 'oneCallAnsweredPercent',cellFilter: 'number: 2', enableSorting: false,name: '1 call answered',width:"*", enableHiding: false },
                                                     { field: 'noCallsAnsweredPercent', cellFilter: 'number: 2', enableSorting: false,name: '0 calls answered',width:"*", enableHiding: false }
            ]


            $scope.drillDownData = function(locationId,locationType,locationName){

                  if(angular.lowercase(locationType) == "state"){
                    reportRequest.stateId = locationId;
                    reportRequest.districtId = 0;
                    reportRequest.blockId = 0;
                    $scope.waiting = true;

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
                                    fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                    $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;

                                }
                                else{
                                    $scope.showEmptyData = true;
                                    $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                                excelHeaderName.stateName = locationName;
                                excelHeaderName.districtName = "ALL";
                                excelHeaderName.blockName = "ALL";
                            }

                        })
                  }
                  else if(angular.lowercase(locationType) == "national"){
                       reportRequest.stateId = 0;
                       reportRequest.districtId = 0;
                       reportRequest.blockId = 0;
                       $scope.waiting = true;
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
                                       fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                       $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;

                                   }
                                   else{
                                       $scope.showEmptyData = true;
                                       $scope.hideGrid = true;
                                   }
                                   $scope.gridOptions = $scope.gridOptions1;
                                    excelHeaderName.stateName = "ALL";
                                    excelHeaderName.districtName = "ALL";
                                    excelHeaderName.blockName = "ALL";
                               }

                           })
                     }
                  else if(angular.lowercase(locationType) == "district"){
                     reportRequest.districtId = locationId;
                     reportRequest.blockId = 0;
                     $scope.waiting = true;
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
                                     fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                     $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;

                                 }
                                 else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                 }
                                 $scope.gridOptions = $scope.gridOptions1;
                                 excelHeaderName.districtName = locationName;
                                 excelHeaderName.blockName = "ALL";
                             }

                         })
                  }
                  else if(angular.lowercase(locationType) == "block"){
                    reportRequest.blockId = locationId;
                    $scope.waiting = true;
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
                                     fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                     $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;
                                }
                                else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                                excelHeaderName.blockName = locationName;
                            }

                        })
                  }
            }


		}])
})()