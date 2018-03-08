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

			$scope.disableChangePassword = function(){
                                    				            return (!(($state.current.name)===("reports")));

                                    			            }

                                    			$scope.disableProfile = function(){
                                                				return (!(($state.current.name)===("reports"))||!(($state.current.name)===("profile"))||!
                                                				(($state.current.name)===("forgotPassword"))||!(($state.current.name)===("changePassword"))||!
                                                				(($state.current.name)===("userManagement.bulkUpload"))||!(($state.current.name)===("userManagement"))||!
                                                				(($state.current.name)===("userManagement.createUser"))||!(($state.current.name)===("userManagement.userTable"))||!
                                                				(($state.current.name)===("userManagement.editUser")));
                                                			}

                                                $scope.disableUserManagement = function(){
                                                            				return (!(($state.current.name)===("reports"))||!(($state.current.name)===("profile"))||!
                                                            				(($state.current.name)===("forgotPassword"))||!(($state.current.name)===("changePassword"))||!
                                                            				(($state.current.name)===("userManagement.bulkUpload"))||!(($state.current.name)===("userManagement"))||!
                                                            				(($state.current.name)===("userManagement.createUser"))||!(($state.current.name)===("userManagement.userTable"))||!
                                                            				(($state.current.name)===("userManagement.editUser")));
                                                            			}
                                                $scope.disableReportTab = function(){
                                                            				return (!(($state.current.name)===("reports"))||!(($state.current.name)===("profile"))||!
                                                            				(($state.current.name)===("forgotPassword"))||!(($state.current.name)===("changePassword"))||!
                                                            				(($state.current.name)===("userManagement.bulkUpload"))||!(($state.current.name)===("userManagement"))||!
                                                            				(($state.current.name)===("userManagement.createUser"))||!(($state.current.name)===("userManagement.userTable"))||!
                                                            				(($state.current.name)===("userManagement.editUser")));
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
				excelHeaderName.stateName = "ALL";
				$scope.clearDistrict();
				$scope.districts = [];
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateOptions();
                $scope.clearFile();
			}
			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);
					$scope.clearDistrict()
					$scope.district = district;
					excelHeaderName.districtName = district.districtName;
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
				excelHeaderName.districtName = "ALL";
				$scope.clearBlock();
				$scope.blocks = [];
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateOptions();
                $scope.clearFile();
			}
			$scope.selectBlock = function(block){
				if(block != null){
					$scope.clearBlock();
					$scope.block = block;
					excelHeaderName.blockName = block.blockName;
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
				excelHeaderName.blockName = "ALL";
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateOptions();
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
              if($scope.sundaysTable)
                $scope.sundaysTable = false;
            });

            $scope.exportToExcel = function(){

                   exportUiGridService.exportToExcel('sheet 1', $scope.gridApi,$scope.gridApi1, 'visible', 'visible', excelHeaderName);

            }

            $scope.exportCsv = function() {
              var fileName = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';
                 fileName = fileName.replace("*","");
                 fileName += '.csv';
            $scope.gridOptions.exporterCsvFilename = fileName;
              var grid = $scope.gridApi.grid;
              var rowTypes = uiGridExporterConstants.ALL;
              var colTypes = uiGridExporterConstants.ALL;
              uiGridExporterService.csvExport(grid, rowTypes, colTypes);
            };

            $scope.exportPdf = function() {
//              var grid = $scope.gridApi.grid;
//              var exportColumnHeaders = uiGridExporterService.getColumnHeaders(grid, uiGridExporterConstants.ALL);
//              var exportData = uiGridExporterService.getData(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.ALL, true);
//              var datapdf = [];
//
//              for (i = 0; i < exportData.length; i++) {
//              var tempcol=[];
//                  for (j = 0; j < exportData[i].length; j++) {
//                      tempcol.push( exportData[i][j].value);
//                  }
//                  datapdf.push(tempcol);
//              }
//
//              var grid1 = $scope.gridApi1.grid;
//                            var exportColumnHeaders1 = uiGridExporterService.getColumnHeaders(grid1, uiGridExporterConstants.ALL);
//                            var exportData1 = uiGridExporterService.getData(grid1, uiGridExporterConstants.ALL, uiGridExporterConstants.ALL, true);
//                            var datapdf1 = [];
//
//                            for (i = 0; i < exportData1.length; i++) {
//                            var tempcol=[];
//                                for (j = 0; j < exportData1[i].length; j++) {
//                                    tempcol.push( exportData1[i][j].value);
//                                }
//                                datapdf1.push(tempcol);
//                            }

      if(excelHeaderName.reportName == "Kilkari Message Matrix" ||  excelHeaderName.reportName == "Kilkari Repeat Listener"){
              var grid = $scope.gridApi.grid;
                            var exportColumnHeaders = uiGridExporterService.getColumnHeaders(grid, uiGridExporterConstants.ALL);
                            var exportData = uiGridExporterService.getData(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.ALL, true);
                            var datapdf = [];

                            for (i = 0; i < exportData.length; i++) {
                            var tempcol=[];
                                for (j = 0; j < exportData[i].length; j++) {
                                    tempcol.push( exportData[i][j].value);
                                }
                                datapdf.push(tempcol);
                            }

                            var grid1 = $scope.gridApi1.grid;
                                          var exportColumnHeaders1 = uiGridExporterService.getColumnHeaders(grid1, uiGridExporterConstants.ALL);
                                          var exportData1 = uiGridExporterService.getData(grid1, uiGridExporterConstants.ALL, uiGridExporterConstants.ALL, true);
                                          var datapdf1 = [];

                                          for (i = 0; i < exportData1.length; i++) {
                                          var tempcol=[];
                                              for (j = 0; j < exportData1[i].length; j++) {
                                                  tempcol.push( exportData1[i][j].value);
                                              }
                                              datapdf1.push(tempcol);
                                          }

              var docDefinition = {
               content: [

                  {
                       // you'll most often use dataURI images on the browser side
                       // if no width/height/fit is provided, the original size will be used
                       image: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCAMfAgQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD6pooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiisnUPEuiaaxXUNWsbZh1Esyrj8zQBrUVyUvxJ8FxZ3+KNIz7XSn+Rqsfir4IB/5GXTP+/4oA7aiuJ/4Wr4I/wChl03/AL/Cnp8UfBDDP/CT6UPrcLQB2dFcivxL8FMMjxRpH43K/wCNPX4j+DGGR4p0b/wLT/GgDq6K5UfETwaTgeKNGJ/6/E/xp/8AwsHwh/0M2j/+Baf40AdPRXMr4+8JN93xJpB+l2n+NO/4Tvwp/wBDHpP/AIFJ/jQB0lFcy3j7wkv3vEukD63af400/ELweOvifRx/29p/jQB1FFcx/wALB8If9DNo/wD4Fp/jR/wsHwh/0M2j/wDgWn+NAHT0VzK+P/CLHC+JdIJ9rtP8akTxz4WkcKniHSmY9ALpMn9aAOiopkMsc8SSwurxuMqynIIp9ABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAfIH7TPxd1c+I5/DOg3D2ljbYEs0TFXkb2I5AFcD8PPhP4w+JNmdRiuGFgWK+fczEliPQE81l/H2zex+KmtQyZ3eYG59+a+uv2ZdWstT+FOnLZlFeAtHJGDyCD1I96APn7Uv2YPGMKE2M1lct6NKErz3xN8KPGPhzd/aGkSsF6mD96P0r9Gs0hGQQRkHtQB+WE8MsEhjnjeNx1Vxgio6/Rzxj8LPCPiy3ZNT0mBJW/5b26iOT/voCvkL4sfBjVPh/fi+Eb33h8yj99GMlFz0YdfxoA4G28G+IbnRm1WDSrp7Ec+YIz09fpWXZaXe30F3NaW8ksdqoeYqM7ATjJr9JvAy2c/gbRVtUjazksYgFAGCCgyDXyekUXhrxt8UbSxC/Y2i5RR8qAucL+GaAOL8H/CDVPFXw7v/ABRptzGxtZGUWpHzOFAJOfxql8LPhXrPxEkvf7LeGKK0O2R5Gx83pivbv2Ptfi/4RbxJo0zqFh3XZz2Urg/yrC/Y+11Lbx5rWku4SK6R5YwT1YMBj8qAPKdP+FXiXUPHl54RtYYP7WtQWkDyhV24znP0NZGseCdd0rxW/hya0Muqrz5UB35HrxX0BoXiyzl/a1v76CVVtJUNoWJwCQoU5/EGs74ReIrfUf2n9Ru7px8/2iCKRj6ZAH40AfPVzo9/bawNLubeSK+Mgi8twQdxOBX2f8KvgJoOheG0l8S2q3mrTx5m34Kxj+6P8a4zxt/YmqftZ6PFK9usEUSi4dmCr5iqxAPvkCvqET28yHbNEysMZVwaAPzW+JFjp+m+Otas9HBGnw3BWEE5+XipfBngLxF4yuBFoWnvMCcea/yx/wDfR4r6a8QfAbwhH4pvdc1rW44tJZzM9v5wVvpmqlt8ahHeJ4e+FPhN57aFvLRkjARz6+31NAHJ6J+yx4huUU6tqlrYseoQebj8jV7V/wBlfUbCwmubDxHHPNEhcIYTHnAz13V9LaRrF3Y+EF1TxkIdOnjQyXALDbGB246mmaH4o0vxj4SvNT0C4+0WTLLEsm0jLKOev1oA+Y/2XPHuq6V44PhHWLiS4gui0cSyOW8uRck4J7YHSvsOvzp8HXj6T8YrK4LFXivyCT7kj+tfonCd0KN6qDQA+iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA+Bv2pyp+M2r7e0cQP121Z/Zo1bxZpfjBR4a0+W/spiFvIgcKF/vZJAyOtcz8dNTXV/ifrV0jbh5uzP+7xX1p+zPr3hm++H9lY6GYIL+3XFzb8CTd3Y+xoA9V1TU7TStPe+1KZba2jGXd+i157d/Hb4d27sn/CQRSMvBCRP/hWp8ZPBL+PfBVzpNvOILokPE7HC5B6GvmSb9mDxbDpU9w15p73SAssCOxLD06daAPeZP2hPACZ26oX+kbf4Vk61+0P4CmspreZZL6CVSrxFOGB7EEV8UajYXWnXs1pewSQ3MTbXjcYINb/AIU8A+JvFUyJoukXVwjHBkCfKo9SfSgD27U/2j7PSNDfSvAuizW6ZfY92wIQN/cC9AO1dD8Bvhpe694V8Saz4peRbjxHHtUv94fMWD/jmrXwk/Zws9Ilh1Lxo8d5eIQyWqHMS9xu4ByK+jYkSKNY4kCRqMKqjAAoA+fPB3wC1Twpofiu3sdct3vdWtPssEgVgIvmzk/yrC+F37PfiXwv430zXLrV7JIrSXe6IG3SDB4r6izRmgD5wP7NGPFtzqkXiB47eWV5lVQRICxzjOMd6z9D/Zq1fSPFK6vH4jtysUhmjWNWEhOcgE9PrX0/mjNAHwN48+Fvj+TxPq+p3OhXTiW5ebzlZSGy2cjnNchJ4m8XaQWtX1TVLXZwUMjLiv0oOD1AP1rD8R+E9B8SWpt9b0q2u4j2deR+IoA/O/SLm68VeJNOsde1Wc2886o80rltgPev0K8C+EtF8JaLBaaDaxRRlAWlVRukPqSOtfP/AMSf2ZI2El74IuSkmd5tZ2+X6JgZ/OuR8IfF7xr8LL1dC8Y2M09rGcCO5X94i/7Bzz+NAFr9rb4gahfeI5PCUSvbWFpteQZ/1xIBBPsK9U/Y/ljm+Eb2xOWW8m3D2OKxfEKfDz49WSGxv107xIF/diTCyscdGHORxW5+zh4K8R+Ab7X9H12IGx+SS1uI8mOQkncAfXpQB8vfFLTpfDvxf1RJFMaJqHmxn1TfkGv0A8L6jFq/h3Tr+D/Vzwq4/Kvmz9s3winlad4ntYyZi32ecjsoHBP413f7Jvib+2/hpHYSOPN0uT7MFJ5243A/rQB7ZRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFUtU1bTtJi83VL62tI+u6eUID+dAF2ivIvEf7QfgXRpHhF9Ndzr0EERZT/wIcVwt7+1bpcchFpoM8yf3jLt/pQB9L0V87aH+1N4duplXVtNurGM9WQ+bj8ABXtXhDxfofi6xF1oN/FcpjLIGAdfqvUUAb9FFFABRRRQAVn+IdSTR9DvtRlxstYWlOfYZrQryL9qDxINA+F19ArfvdRH2UKDzhhyaAPhXWrn7ZrF9c5z507yZ+rE1JoWs6hoWpQ32lXL291EwZWU9/p3rT8D+Ctd8bap9g8PWbTyjlnb5Y0/3m6Ct7wB4Zis/i5YaH4pRVEF1slUHKl1PAz3BIoA+5vhhq+pa74G0nUdai8u9ngV3OMb8gfNjtmk8Y+JJrK7tdG0ZBPrd5jYnaFP+ej+i8EZ9at+MvEemeCfDFxqeoMsFpbJiNFHU9FUD64rnPg7BPqOhnxXqiD+0tZJuF3ctDEekYPpxn8aAOq/4R3TZRC97Z21xcoOZWiXJPvxWpBBDANtvDHEPRFA/lVDxDrmneHdKn1LWLqO2tIVLMzHk47Adz7V5FFqfjT4q6bLe6DqI8K+HGJWG4kiEklyuSN2Dgr0oA9wwaCCK+Zb74I+I5naSy+J9zc3fUR72TJ/B8Vy+oeJfiz8ILuL+3na/wBJ3YLHEisPd8HaaAPsGivMfhP8YtB+IMKwRsLHVwPmtJG6n/ZP8Vem0ALRSVQ13V7LQtJuNS1SdYLSBSzMxx+A96ALlzcQ2tu891KkMKDLO7YAHua4DXPjL4I0iYxy6zBcMOv2Z1cfzr5F+MPxc1jx5rU0dpcT22jK22C3jYrvHTLY659K7r4JfAVNWsE8Q+OT9m0sjfHbsxQsv95jkbRQB9B6B8YfBGtSiK31y1gkPCrcSKhJ9BzWx418GaB480Y22r28dxE4zHOh+ZT2II615br2p/BO1ZdDuo7An/ViS3QHHv5i9PrXk/hr4rP8MfiBeaXY6jLq/hEyLje5coCAdyk5PGTxQBy3xd+Fes/C/VY7y2lkm0t3zBdx5BQ9g2OAfT1r3D9mT4wXfiR18MeIm82+jX/Rrju6gH5SPYDrXtl7baR478HtEfKu9N1CDKMQG25HB9iM18KTHUvg78W3ligbzNPnYwh+PNiOQDz6igD6+/aUt4p/gz4haXG6KNXQn13rXzf+yR4pGjfEH+zJ3Pk6moiRM/8ALTsf0rM+Mfxw1L4iafFp0Nn/AGZp6ndJEsm8yH3OBxXluhanPo2sWeo2bFLi2kEiMOxFAH6i0Vh+CNftfE/hbT9WsX3w3EQOffHP61uUAFFFFABRRRQAUVFc3ENrA811LHDCgyzyMFUD3JryTxX+0J4J0G4lto7uW+uUOMW6FkP/AAIcUAewUV4VpP7QttqMgaHwrrslqT/rYLZ5Bj8BXovhzx/p2vYFtp2twMe11p8kWPzFAHYUVzFp498MXWqS6cms2a3kTbWiklVTn0GTzXTghgCDkHkEUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABWT4n8Q6Z4Y0ibUtauo7a1iGSWPJ9gO5qzreqWujaVc6hfyrFbwIXZmOOg6V8C/Enxnr3xc8aCCxjuJrYylbGyXoo7EjpnHegD074jftPX0801p4MtEt4ASv2qcby49QOMV4Vr+ueKvE4N1q1zqd7CxyC+9ox9O1eueHP2X/FGoQQzalf2WnhsF4pAWYD044zX1f4Y8H6VoPha00KO2iltIEC4dQdx7mgD4L+GXwv17x9qhtrGFra2T/W3MyHan+NfQ+ifsr6HDEBrGq3F1J3MP7sf1r6IsbG10+AQ2VvHBEP4I1wKsUAfPWqfst+F5bZ1029vbeYj5WkfeB+FeEeJtG8X/AjxhGbK/cI/wA8U8YIinUdmX+lffleU/tJ+E4/E/wzv5Am6605TdQgDksBjH60AcD4I/ai0u6iig8VWElpMBh7iI7lb3244r0Wz+O3gC7IEWtAE9mjKn9a+U/2bfDOheJ/iF9g8SqskSwO8cLnAdwRx7/Svobx5+zl4Y1mykfQYxpl+qny9nEZPuKAPXNB8UaLr0QfStRtrjPRVkG78utbNfmZK2s+BPFc8Vvcy2epWMxQvE2M4P8AI19x/AD4jL4/8HpJdsBq1p+7uVzy3o/0NAHqFfFH7WXi1/EPj2LQbFi0OnDymVTkPIec/rivdf2gvitL8PtMFnZWE0l9exEQ3PHlxn39TXxRo2oPceL7S/v5DI73SySs5znJ5oA+/fg74KsPBHgyytbONPtM8azTzY+Z2Izgn2zUGqfCPwrqXjaLxTPbzpqaMGxHJtjZh0JXHWu201w2m2bL90woR9NoqyOooA+WPjjqeofEr4q6X4G0Rt1laP5kxHTeM7g3sAvevo2/u9O8H+FHuLhhBp2nQc+wA6fnXH/CrwAPDeteJ9c1FA+q6nqErrIe0JOVA/M0vx68Gat458E/2bod4tvMsnmOjZxMuPufnQB8oePPG3iP4y+NYdNsd8dpLKI7SyD4UZ7t2J969j0r4F+O30W1srrx9NZWsS4W0hVwqZ7cMAa8PPgD4g/D7VoNZ/sa6gezcSpKmHU49gc4r6e+HPx88N+IbSK212f+ytWUBXSYHDt3Ix0/GgDkT+zv4ohcS2fxDuUlHOSr5/MNXqPhLwjq1x4FuvDnxAuYdYLbkSdk5Zf4Sc9x1rr4Ne0i4iEsWp2bIRnPnKP61zfir4qeEPDFtJLf6xA0qglYYzuZz6DHFAHwx4x067+HvxH1Cz024lim025IgmU4YgdDX3B8FPHa+PvBUGoSbRfQYiulXoHxXw38UPFCeMvHOqa1BC0MFzKWijb7wXtn3r6t/ZH8L6j4f8C313qUbRDUp1nhQ8HYFxkigD3XNfMn7Z/iW5gsNJ0C2kK29wWluF/vbSNv9a+m6+ev2u/BF3rnh+x17TYzJJp5ZZ41GWZWxz+GKAPE/wBmrwFF418crJfpv07TgJ5k/vnPyj88V6T8Xtb8Z+PvHNz4K8G21xZ6Zp7eTKY8xrIe5LD+HnpXPfso+PtD8L31/p2uTLZm7A8q4YcE56GvrWz1PRSjz2l5YhZTud1lUbj6nmgD5jsv2UL2S2V7zxNBHOwyUW3J2n655rj/ABz+zp4n8N2k95ZTwanaQIZJJEHllVAyTgmvqzxN8UPB/huJ21LW7YSKOI0JYsfQYzXzN8V/jnq/jyddA8HQ3NpZXDeUQpHmzk8bcjsfSgD1X9kHV7q88AT2FwSYrOUiMn/aY5FdN8ZdE0C41PQdS8RaXb3FkJTFczlBuAOAgJ9NxrW+CXgz/hB/ANhp04U3rjzZ2A5yxztP0zitD4r6J/wkHw/1ixXHmeV5yezJ84/UUAYuv/BPwLrNg1sdHitCR8stqBGwPrkCvkr41/CPUPh1qAljZrrR5j+6nC/d/wBlvevrf4C+MR4x+HlhPM5a/tFFvc7jyXXqa6jxz4dtvFfhTUdHvEVkuImVWIyUPqPegD5x/Y38byC7vfCd7IWRkNxbFm4QLwVH1zmvrCvzR8Naxc+CPHMF/CH32FyQyA4LqDgj8a+9vA/xP8L+L9PgmsNTt0uGUGSCRtrI3cc0AdxRVQ6lYgZN7bAf9dV/xrn9d+InhPQQx1TXbOHb1w27+WaAOrrK8TeINN8NaTPqWsXUdvbQqXYsRk47AdzXg/jj9p/RbFZbfwxZzX0+PkuGwI8/Q8182+KfGXiT4ka/DHq19JL58oWK3BPlxk8fKtAHsusa74y+PfiGfT/DTS6X4WhbY0mSAw6ZYjG7PXHavWfAX7P3hDwzHFLfW39q3wHzvcfNGT6hD0ruvhv4csvC3gzTNN0+FYlWFXkwMEuQCxP45rpxQBV07TrLTYRFp9rBbRD+CJAo/IVaoooA+fPjh8ENGurDWPFehyT2WsxI10VRvkdlHQD+E8V5L8Iv2gNa8L3cdh4nkl1PSidpeRsyxds5PUD0r7V1Kzi1CwuLO4GYZ0Mbj2NfPWr/ALLmiTabdtY6jcrqbszxtIf3YycgEUAe/aHq1lrmlW+o6ZOk9pOodHU56+vvV+vif4LeOdU+FHjyXwr4kMg0t5zC6HpG5OA6+gJ5r7WikSWNZImV0YZVlOQRQA6iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKRmCKWYgKBkk9q+cvjt8fbPSYLrQvCMouNRIMc10v3IvXae5oAxP2uviRG8MXhHSLgNk770ocjH8K/zzVn9jLwtYNpmo+I5VSS98z7OgYZ2Ac5HpXyjd3E11cyT3UjyTSHczuckn1r0j4KfFa++HGqvmP7VpdxgTQE428/eX3oA/QaiuE8EfFbwn4wtY307Ukinfj7NOQkgP0ruwcjI6UAFFFc+fF2kDxg3hh7nZq/kidYmGN6nPT16UAb9V9QtYr2wuLadd0UqFGHsRVimt0P0oA/Mi6uL3w74suJrCeS1vLS5YxyIcFSGODXtOh/tI+OpLBbCDTLO/utu0XBRzJ9eDjNUPAvgSy8ffHXWLO+cjTbaV55VX/lpgj5foc19j6L4c0fRLKO00zTraGGP7oEYz+dAH51eKdO8S3l/datrlhdiWdzI8jxnGSc1ufBf4iXXw58Ui8VWksLjCXUQ6sozjH0Jr9CXtbaRcPbwsPRkBrz/wAdfB3wl4wSZrmwS1vnGFuYFwVP06GgDhvip4y+GvxG+HksN1r1jDfKnnWyO2JYpMdMfpXxhIAkrBG3KrEBh3969R+LvwZ1v4fSPdf8fujlsJcoOVH+2O1cP4N8L6l4v16DSNGjR7qY4Bc4VR6k9hQB94/AnxhH4x+HthcmRTd2yCCaMHldowM/UCpPjn4qvPBnw11LV9LA+2IUijY/wl227vwr5M0PVPF3wC8ZPBfW6tHMP3kIcmKcDuDjnFegfFb4+eHPGXw1u9HgsLtdSudhKOg8tSDnhs5/SgDvv2WfH+peMNA1G11/UXvtTtZPM8yTG7yzgAcds5r3Kvzj+FXjm88A+LLfVrQGSH7k8OcCRPf6ZzX6CeE/EeneKtCttX0eYS2s6gj+8p9COxoA1nVXGHVWB7MM14x8XPgb4d8R6deajpEK6Zq6I0u+HhZWH97P9K9o69K8A/aU+Ltv4e0u58NaJKJNWuVMc7qf9ShHI+pBoA+Pr4XVje3FrJO++GRo2w5xkHFVHkd/vszfU5odmdizksxOST1Jro/BqaNdTyWniFZobOXA+3Qrua3OeDjpgng0ASfC/TrPVviDoVjqZUWc9yqybumOT/Sv0hhjSGGOKJQsaKAoHQDFfn54j+FfiLQ4o9W0cLqukn95De2LFwB2Lehr0bwH+0xquj2sVh4nsFvliG37TuIk44wR0oA+wM1DeW8V5Zz21yoaCZCjg9Np4NeDj9qLwj5OTY6l5uOnljGfzrz34g/tJ6nrtlLpfhawFkJ/k+0ZJkIPGAPegDxDxzZQab401uyscC2t7yWKLb/dDEDFY/2m4XjzpR7bjXungD4TjTNHufG/xHb7NpsCNLFayffnkx8u70GcV5DqsV3rUuoa4tsIbR5SSQMKD/dHvigDGeR3++7N9TmvqD9j/wAC6feRXvijUEWe5gkEVsh5C9y31yK+Yp7ae3SF5onRJl3xlhjcvTIr239l74kxeEfEMmjao5XTNScAN12S9FJ9ByaAPtus7xFdw2OgalcXTrHClvIWZjgfdPFS6nqVnpemyahf3EcFlGu8ys3GK+Of2g/jbJ4ueXQfDbPFoanEsucG4IP/AKDkfjQBf/ZP8Wi3+I+p6aWK2uqBjDETwrZ3ZH4V9hudqOT0Ck/pX54fAWeS2+LnhuWL74uCPzVq/Q48ofdf6UAfmH4hma417UJW6tcSH/x41RjlkjOY3Zf904q3rqNHrd+jDBFxIP8Ax416F8Dfhbd/ELXkaYNDo1uwa4mx97H8I+vSgDG8CeC/Fvju5EOjR3k1upw87O3lx/U5r3LRP2WZpYlPiDXpPMP3hb4I/wDHq+ldA0XT/D2lRWOlW8dvawrgBRj8TXBePvjb4R8H74ZLs316OBDbAPhvRueKAOFi/ZS8Mj/Wa7q59gIx/Suy8E/AXwd4Vv4L1IJb+6gYPHJckEqw6HjFeOyftBeNPGOsR6b4Q0eKCVjhAjFyR6txxXofwP8Ai/qHiDxJc+FPFsUUWrwKSkyNxIR1B9+aAPeB0wOKWkxS0AKKKSloAKK4/wAb/Efwz4MUjW9RRJ8Z+zp80hHrivI/FH7Uvh+CzceHNPvLq66L9pTy0/QmgDy39sC2tofih58BC3ElvEZAvHQcGvd/2WfGMvib4epaX0ga805zD1yTGMbSf1r4/wBUufEfxN8XXF6Lea/1K5b7ka7ti9h9BX1J+z98OvFHw70e/vruC1lur0D/AEYyn5FHIPTr7UAfQdFeXal8XodEdk1zwz4hhVes8Npvi/PIqfw78bfA+tzeTHqy2k2cbLseWc+lAHpVFR288VzCk1vIskTjKspyCKkoAKKKKACiiigAooooAKKKKACiiigArl/HnjrQvA+mNd67eJEcZSFTmR/ovWqPxb8f2Xw88KyandL5tw58u2gBx5j/AF9utfOvgP4Xa78X9WPi/wAd3ksOlzuXSIMQWT0Tn5RQBgePvjF4y+Jd42l+GLS6ttOLY8m0VmdvQswGQK7L4Sfs3Tvcwat47cqFIkWyRt289RubOR9K9W0fxL8M/AN/F4f0+4s7W74TekYYtn+9IBXqkE0dxCksEiyRuMqynIIoA5++8DeGL2yFrcaHp5iC7RtgVSB9QM15J4z/AGZPDGqI8ugTz6VccnGTKrH05PFe/UUAfnt45+EnjHwHctO9pPJbAkpc2hLYHqdv3am+Hfxp8V+D76EPfS31gGxLBcHeSPZjkivv28Nv9mkF4Yvs5GH83G3Hvnivhr9paHwOniVW8HuoviT9rjhXEQOf4cDH5UAfbuiajHq2jWWoQ48u5hSUDOcbgDj9a+YP2tkn8N+N/DninTpGivHBTchwSExx+te//CYAfDvQgDn/AEZP5Cvl/wDbG8Tw6p4usdGtnEi6fGXZlOQGbqPwxQB9V/D/AMRJ4r8HaVrKbQ11AryKv8LEciuZ+OnxDb4c+Eo9QgtluLq6lNvCrNgBtpOf0rP/AGYrO5s/hHpn2oEeaxljz/cIGK4D9tlm/wCEc0Ff4PtRP47DQB88/Dr4gah4M8cDxDbgStKzC4iY8SIxyR/9evt/wJ8VfCnjO1R9P1KGG5IAa3uGEb59AD1r4i+E/wAPLz4ja7NptjdRWrRQtKZJF3A4xxjPvW/4t+CfjfwczXi2xmt4+VuLWT5vyByKAPvkngY6HvWd4gtpLzQ76CGR4pWiYo6dQwGR+tfG/wAJvjzrnhG8h03xQZr7S87XM2fOjyeuTyQPSvsvRNUtNb0m01LTZPOs7qMSRvjqp9qAPCPBnxf0HWPBl/4e+IzRw6paRtDcJKuRPj+Ie/TivFP2e9YtdK+NNv8AZyBb3kzW8WfRm4qz+1P4OPhz4gzajAp+y6oTPnGArnqteU+GtSbRvEGn6ihIa1nWUEexoA+7Pj58NB8RfDiJZBE1i0bNu7cAjupPvXzVpn7N3jy5uQl1bWlvCDhn+0KT+A719tabP9q0uyuOvnQJJn6qDVgkAFnOFAySewoA/OL4n+Br74f+JBpGpOkkjRLMrKcgqSR/StP4R/FDVvh5rCSWztPpkjYuLRm+Vh3I9DWh+0j4pt/FXxOvLizOYbSMWYYdGKM2SPzriPBvhu+8WeI7PRtLTdc3L7QT0UepNAH1j8VP2gtKsPCFq/g+dbjVNRjJUsP+PcdCSPUHsa+O7+8uL+8muryV5riVi7u5ySTXtHxZ+Ad74G8Mx6xa6iuoRodtwoj2lOM7uvSvGtM0+41K5+z2a75yCQmcFvp7+1AFSu68FaJezlL/AMPXVlPcRj99aXbou8Hgqqt96uRWN9O1AJf2zjY2JInG0kdxz0+teiaHongrxFNENK1658Oal/BFcI0wZvaQYAoA9A8HX1xpDkWU+oeENRP37G+haSxuD675MKgPtXVXF14d1qQQ/EPwTFHngX+iAzRP7s0Ywv51zuneGvibpEGbG50XxVp+OBfzJP8AL6BWbIqza6trFlIE1rwZrEHtp95ttvxjUYNAHX6b8B/hnryfaNFuHli/iSO6LlfY85Brdi8G+Avhlbtd6do32u7xgNLmcK3bcTkJ9a1vhHrWn6ik0VvaJZ3I5aNLAwdu7Y+Y1Q+IBuYruR4tJadm4Li+VIiP9qM9aAPIPG99q3jvVE/tdJ9RSM5ttG03L2uOxknTgY9DWXqulaHo8cFz8RL+3kki4s/D2luHTjp5jpzn3IzW3rc9xcK0ereK7fw5YfxW+i6a6Ow9CyHk1D4d1z4d+FJvO8PeGdY8U6k/LXF5Awy3qN68UAJ4T+GOs/FXxHHr/iixGi+GoV2wWirsIjHIQDggdTmvH/jDbaNp/wARtVt/C7AaXCyLCUbOCFGefrmvoTXb/wCLfxFsWttM0uHw9oUgwztMFkRf94EH8K+Y/GmjQaDr09hb6kmpGM/PMiFRu7jk80AP1bxl4g1bTINPv9VuprOEYWMyHGPf1rn69d+FfwN1/wAcRJfXDLpmknnz5lyWHsuQfxriPGXhyHRPG1zoVjereJFOIBMowCScUAesfsm+BbnVvGEfiW4jZNP00lo3I4eTBGPwBr7QPIP0Nct8M/D0XhfwLo+lxRqjxQL5pAxufHJPvXT0Afm/8VdPOl+P9atCMFJ2OPrzXvXhT46eD/AngGw0vw3YS3N5HGDKkiNGGkPLZbHPNeb/ALUmmmy+LWoz4wt1iQD6ACt39nj4Lf8ACYmPxBr5KaJG58uIdZ2B/QA/nQBam8U/FP40XDWuiQSWGlk5/dExIPX97gZ+ldNo/wCyuZwk+veIZVnb5pI0iD5Pf5s19L2FnY6Lp6QWcUFlZxDgKAij69q8s+I3x88L+EWmtLRm1TU048mLhB778YoA1dN8M+EPg14Qv9StYI4zFGXeeVsyStjopPP4CvhYeI9QtvFb69Y3DxXwuDOkg7HORXS/FH4qa/8AEK6X+05RFZRH91bRZCj3PqfeuChieaVIolLyOdqqBkk0AfUnwH+I3jr4hfEG3h1K+B021gZpdkKqueMAkdzzX1WeteWfs8+AE8DeB4TcKp1O/AnncDseVH4A16VqF/a6baSXV/cR28CDLPIwAoAtCvKvj18Urf4feHmjspYpNcuflgiyCU/2iPSuG+J37S2l6bFcWPg6Fr29wU+0yAqkbfQ/er51t/DfjT4j3l3q6Wt1fSHLvLISBjr8u7t9KAOS1nVb3WdQmvdTuZbi5lYszyMSea9O+B/wa1D4hXf2u98yz0KI/PPt5kP91fX69q4Dwhof9teMtM0W4Ji+03S27nptycGv0o0HSrXRNItdPsYkigt41QKgwOB1/GgDK8F+CtB8HadHaaHYQwhBzKVzI3rljzXSUUUAMmhinjKTxpIh6q6gg/ga43xR8LvB3iaPbqeiWoP9+BBE31yuK7Worq4htLeSe5kWKGMFmdjgAUAeFX/gPxZ8MjLq3w/1O51XTYvnl0e8bd8g6hGJJz9K9J+GXj7TfHmim6sj5N7CfLu7R+HgkHUEHnrmvLvFH7T/AIc0rVZLTTNOudTijJVp0fyxkegI5r5++HPxButK+MY1y0V47fU75hLbBvlKyvgZHfG6gD9A6KKKACiiigAooooAKKKKACiiuS+KXi628FeC9Q1a5b50jKxIDhmc8DH0zmgD5U/at8Wx618R7XTIZi1jpmElTsJdx3H8q3/jP8drYaNF4c+H04S18lUlu4VKgDHKp0I+teDaZpmt+PPFMiWUEl7qd5IZH2DPJ5JPtXqPjn9nfWvCngmbXZb+3uZLZPMuIYwflXvjigDw52Z3ZnJZmOST3NfSP7LXxVv7XXIPCuszvPYTjFszkkxN6fQ15f8ACP4Vax8Srq4GmyRW1nbECa4lB2gnnaMDrivqz4W/APQfBGpRapLcTahqKD5TLgIh9VAA/WgD2WvNvi78TD4GthFZaTealqEi5QQplY/Qt7V6TQQD1AoA/PX4i/FPxv4rklTV7i5srJsj7LErRRke4PWvNa/TPxb4P0PxXYva63p8NwrKVDEYK+4NfBPxn8Ay/D7xfLp255LKXMltIwwWXP8ASgD03wX8dNdh8AWnhnw9o89zrsamJblV3IFPQgA5BAqX4c/s/wDiHxJrx1jx9vtLd5DNLE7AyzEnPUZAHrXmvwY+Jt18ONca4S3S5sbggXEZ+8QO6n15r7u8F+LNJ8ZaHDqmh3KTQOPmUEbo27q3uKANbT7O306xgs7KJYbaBBHHGowFUdBXz1+2xFnwZoMvpfFf/IbV9GV87ftq3EQ8D6Lb7lMrX+4LnnGxuaAPEf2ZfFNr4W+J1tJfuEtr2NrUseiliME/lX3srBlDIQyMMgjkEV+WIJByODX0l8GP2h5dHtLfRfGQkubVSEivFxuQf7XsKAPXfjR8HND8YaNeX1haxWWtxI0qzxrjzcDJDY6+1fMfhL4w+M/AGmXHh6xe2aON2QLcxl2iPTC8jFfdOiaxpuv6al5pN3DeWcg+/G2Qfavm/wDaG+CWnWuk6v4w0OaaO4RvPntTgqckcrxnPNAHR+P/AA5feP8A9nTTNR1EfaNdt7YXoKjl36Efka+MXUo7IwwynBHoa/QD9nzXbbxF8JtIjRg8lpB9luBn+LBzXyD8ePCP/CH/ABG1G0hQrZTP51uT3U9f1zQB9q/BzVP7Y+G+i3e7d+5CZ/3QBXlv7R/xkg0XT7jw34buQ+qTLsuJo+REp6jPqRXjPgX4i+O7jwdD4L8HWRfYWPnW6FpcE5IPbFdJ4f8A2Z/E+sYvdc1G1s2lJeSN93m5PXPGKAPntiWYliSTySe9fT37GGgRve6trsi7mVDbISPung5HvXi/xb8A3Pw78UDSbq5juQ8QmSRAcbSTwffivo39jCUf8IVqsXG77aW9/urQAn7V3xHg0vSD4TsVEt7eITcOeRGh42/XvXyJp95Pp99b3lo5juIHEkbjswOQa9a/aq065s/ixfXE4YRXarJCT0KgAcfiK8doA+79F8IeEfir4E0rVtW0+KS8mgVJruEBZTIAA3zfWuE8Qfss2MzsdA1drdewuiW/kK86+BPxubwBYto2r2sl3o5cunlY3xMTzjPGK9rv/wBpXwdbWvmxQX1w+MiNNuf50AcNpf7NPivSXZ7HxjZ2qg5Ji8xfxqPV9dh+HUwt9d+Ieu63eRnDWmn3ZG38SMfrXK/Er9ojX/EsM1joaDStPfILL/rXX0Y8j8q8OYtI5YksxOSeuaAPpuw/acjtriJZdNv57WPj95IpkYe59a9P8L+N/C/xBtC3h+OwOpL88lnfJmQ+wPSviE6RqAg877FceVjO7YcVFY3dzp15Hc2crwXERyrrwVNAH2hrnjuz8KF/7c+GV9AqniaJY2V/cYJNYVx+05odhbtHpnhbUIZCON21Vz7iuU+Hv7TF7YwRWXjGx+3wKu03MQHmt/vZ4r1Gy8ZfB3xPGJ9Rk0uGd/8Alncthv0oA8D8cfGvxt42WTT7FZLOylO0R2sbB3HoxHWuu+CHwHmnuU13x9CLaxjIeK1mYAyN1y3bHtXskPjP4U+FYGn03UdIicDhIWyzfTNfOXxz+NN346uV07QvPs9FTIMfG6dvU4oA90/aa13XtB8AWo8Ir5enSfuri4tufKTgKoI6A8ivkn4cWbaz4/0eGYl2kuldixySQc8/lX238MPDD2/wWs9D1f8AeG4tHLb+yyDI/IGvk/4Z2EOl/Hu1sI+Yba+liX6LuAoA++XPzUlDdaT8vxoA+N/2y4lj8caW6j5pLZy3/fQrP+FHx9u/BHhI6Jc2P2uOEk2zrjK5Ofmyeayv2nfE1v4h+JM6WcizW1iDCsinIY9Tiup8LfAbSPHPhiz1jwjr7rvUCeG7wTG44YfKOmelAHE+L/iv42+Id6LKO4lSKU7Vs7IFQ/1Gea7PwF+zPr2qpDd+JZ49NgPLW55lI+oyK9y+DXwZ0z4dPJfSS/btXkG3zmHyxj/Z+teka3rOm6FYPe6xew2dqgyZJWwKAPJbL9nHwFYWkhuVvZiFJeSWYYHv04r55+Dmg6bqXx2trWw3SadZ3LTRiTncEYdfWuz+Ofx/OuW1xoPhDdFYuCk92SMyj0XHavH/AITeLR4L8d6frMyNJbo+2cL94oTzj34oA/Rm9uYbGznubhhHb28bSOeyqoyf0r4J+NHxQ1b4heIZrO2llGjpLst7aPOJMHAYjvnrXs3xx+O+i3fgg6d4TuRcXmoptlIIIijIwyt781hfsl/DO21FT4v1eMTRRuUs4z03gkMT9OMUAJ8Df2e21CKDXPGyFLdsPBY9GYer+3tX0vrOqaF4H8NvPdPb6fp1rH8sa4UY7ACuT+OfxLi+HHhpZ4Y1m1O6yltGx+UEY5PtivjDX9X8a/EYXWpXxvdStbT53KrlIB+H1oA5/V9Ykl8U3WrWDG3kNw00TJwV5yCK9/8AB37Ul/Y2UcHiXTPtrxqFEluAhIAxk5PWvmpUZnCKpLE4AHXNfW/w7/Zm0mTQ7S98UXtxLeToJDDCQEUEZA5Gc0AaNj+1X4bnmVLjRNStwers6ED8q9e8CfELw544hdtA1BJ5YwDJCQQyfga87uP2afBUykI15FnurD/Ct34bfBTw/wCANZk1PS7m9nuHXbidhgfkKAPUyQASegr5g+OuseNPiFqsvhnwNpd6+kW/y3My/IJ2PuSPl56V3nxS+JPizwBcyT3Hh2C+0bqt1b7sIPRsnrXm0/7VpETfZ9AUS443txn3waAPHPHnwi8SeCNAi1bW44o4XcRlQ2SGNQ/A3wzJ4p+JOkWiqfLilFw7dgE+bn8qd8Uvitr3xFmjGqGOCzjOUtoc7AfXnnNe/wDwO+E13Z/C651GK5l0/wAR6iFntp1HMSryg57NnmgD6UoryH4VfFSbVNZn8J+M7ddO8T2vygnhLoDjcmec8E169QAUUUUAFFFFABRRRQBFdXEVrbS3FxIscMSl3djgKB1NfFfxS8Sat8bPiPDoHhkPLpVvJsiA+5xw0pI7Yrvf2uPiNJaxReD9HkIuJgJLtk+8B/Cox685rvP2bPh5beD/AAXbahPGG1bUYxNI5HKowBVcdsUAdL8KPhtpPw90VLeyiSS+dR590R8zt3+grD/aX8UWvh74W6pbzNm51JDawqOoJGckenFenalfW+m2M95ezJDbwqWd3OAB9a+QYbhf2gvjQ9reXT22hWMTPDEOGeNWHH1JJ5oA6v8AYiml/sTxJCYyYftEbB+wO08V9OVwGqaBc+Bfh5cWfw106BbuFSyJIck8HJ5zuPtXxX4m+JHjqXV5hrGpXVveRsQ8ezy9p/3aAP0ToNfB/gn9oTxh4dlRLyWPU7IdYZQFP4NjNe0aZ+1L4altQ2oWF5BPjlI13jP1oA+h6+a/209GSbw5pGsbfngl+zhvQNk/0rt/hV8X5viL4jubTTdDkh0qBNzXsjEc/wB3GMZ/Gs79r2HzvhIcfwX0Tfo1AHyf8Nfhtr/xDuriHQI4dlvjzZZnKomenOD6V9Vfs9/CXXfh1fahcavqMTx3KKv2eBiyEjPJz9a+Yvg58TL/AOG+utcW8YuLC4IFzATjcOxz6jNfYHhH44eCvEuxItQNnMR8y3eIgD6Ak80AW/jjF4tPg17rwLfy2moWhMsiRIGaZMfdGQenWvg7xX4p1zxReLP4i1Ce9uIxtBkwNv4Div0vhkiuYFeJ0lgkXhlOVYH0Nfn/APtDaBD4f+KWrQ2ihLaZ/NjQdFBHSgDS/Zx8Aaf468U3MOsgtYwQM20d24x/Ouk+MX7PWoeHIn1Twp5mpaeOZIQv7yIeoHcD1rn/ANmPxta+EvHqxapII7G+QwlzwEc4wSew4r7qR0liDxsskLjIYcqwNAH51/DHx7rHgLxLBcWdxIlt5gS5t2Pysuecjtjmvrz4ueNtHuPgnfalFdwN/aFsvkRBwS7EjIH0rk/j38CrXXLW517wpEINVQGSa3H3Zh3wOx+nWvkdo703kemXjzxFJREYpCf3Zzg/KelAHffBP4p3nw51p2ZGudKuOJ4M9P8AaA9au/tCfEnTfiPrWm3OlWckMdpAYi8owzEnPSvcr39m/wANt4A+zWXmHXxBvF4ZDtd8Z+70xXx9qthPpepXNldoyTwOY2Vhg5BoA+9f2fNM0Wz+GumXGixRZmUmWYD5mbvk16UTmviD9nb4tN4I1M6Vq7F9Eu2GST/qW7Ee3rX2zaXMF5axXNpKs1vKoZJFOQwNAHy9+2d4euWuNK1+KJntyvkSMozsx0z6ZzTf2LtVj+16zpRbEnlmcA9xlRX01rkGn3GlXKaxHC9iEJlEuNuPxr8/5/FaeE/iffaz4FJtrKK5Y2yMdwMfoc9RQB9hfHP4ZQfEXQFEBSHWLQE28rD7w/uH2Jr4c8UeGNY8L37WeuWE9pMCQPMUgNjuD3FfY/wv+P2geLAlprOzSdTOOHf903vuOK9N8R+HdF8YaQ9tqltDe2ky8Sp1x6qwoA/NJQWYKoJJOAB3rutS+FHi/T/DkGtzaVK9jKgkzGCWRSM5YY4ru/HHwdPg34p+HrS1n83SdQuVkg3/AHlCMpZT+Y5r7PaGIQG28tTbhdnlkZG30oA/NjwJ4fPinxfpehidbc3swi81ui8E5/Svun4f/CPwt4Lto/s1lHd3ygB7uZclvwORXivx9+DdxpF6/i7wUjqiN509vFktGw/iTH8q9A+BfxnsvF9gml+IJorTXIEALyMFWcDuM9/agD2X7Ha4x9lt8enlL/hXlnxg+Deh+MNGuZ9OtI7LWY1LxyxLjzMc7SOnNes4JXcPu9c9qxNZ8W6BocbS6nq9lCU5KGZQ5+gzQB+bWoWc+n31xZ3aGO4gcxyIf4WBwRWh4Y8Oat4n1EWWh2U15cdSsak7R6n0FdX43trfxx8WL1PB8U08d/cbh8nO4n5m47V9j/CfwDp/w18KmEeW16VMt1cnu2OgPYcUAfBfijQNR8Na1caXrEJiu4Dhl7fhXsv7OHwiuvEWrQeIdct2h0e2cPCJFx57g5GB3XjmuC8b+IrPxf8AFu71W+3LptxeDK9NsYwP6V9neIPiP4R8G+F7e4bULSSNIVENtayKzE7fQHigCT4x+MrPwP4EvruVgs8sZt7WJfvbiMAgeg4r4V8G67LaePdO1e5ctK12ryN67m5P61p/Ff4i6n8Qtea8vT5VpGSLe3U8IP8AHiuJh3eamz724Y+tAH6gvcLJZNc2xEiGIyRkdG4yK+EviR8WfG97r2rWFxq9zbWglaP7KmAEX0zjNfW+ja9beGvgzpmq61LsSDTVLk9WYrgAfjXwJ4m1Rta8QX+osoU3MrSYHagB+gaFq/ifUWtNEsbnULzaZDHCu5sDqa7D4YePdb+FXil/Mgl8nJS6sZflz2z9RW1+zF4u03wn8QWl1iQQ21zbtCJT/CxIxk+nFJ+03rPh/W/iDJdeHnSVvLVZ5Y8bHIHGMUAd54w/aju7i1MHhrS1t3cf8fEjfMh9hyDXlNlYeP8A4t6qJAb3Ut7bXnfIhj+uBgVr/AL4USfEPVpLnUC8Gi2pHmyAf6w/3V/rX3BoGjafoOmQafo9qltbRKFVUHJHue9AHzP4b/ZVaW2VvEWum3n7paoJF/M4rmPi3+zxd+ENDn1nQ799RsbZS86yIFdFHVsDtX2Dq2s6bo0Jl1W/trNAM/vpAmfpmvl74+/Hqz1jR7vw14TV3guAYrq6cY3L3VR3B9aAPmKvrf8AZI+IWmx+HX8KajPFbXNu7TQNIwHm7iSQPpXzB4Z8Oar4m1FLLRbOW5mYgHYpIX3JHQV60v7Nfj+C3W4gawWYDO1LrBH44oAvftb6/aa748sNN025S4FvEqsY2yquxI2/Wvpb4Q+DrXwr8OtP017VFuJYA13lfvuRyTXzj8MvgB4ml8ZWl54ojij0+2lEkxMhZnI6YyOecV9UeOvFWneDfDV1q2qSokcSfJGWAaQ+ijuaAPgG30+OH4qpZbcxDUdu3239K/RqxUJY2ygYAiUfpX5weFb19S+J2nXjklp9QWT82r9IbXP2aH/cX+VAEtLVe5u7a1Gbm4ihH+24X+dZ0vinQITibW9MjP8AtXSD+tAGpdW8N3A8NzEksTjDI65BFfHH7Svwaj8MP/wkXhi3I0mRsT26AnyG65H+zx1r68sdc0rUHCWOpWVy56LDOrn9DVfxloq+IvCuqaQ5AF7bvDk9sjFAH54/Ca2sLv4i6BDqrKLRruPcG6N8wwD7Gv0jgjjigjjgVViRQqBegA6Yr85PFXw28V+F9QuFn0i9MVuxIuIImZAAeDuA4r234E/tAG0S18P+NGJiXEUN6eqjoA/6c0AeyfGb4YweN9PS901xZeIrP57W7TgkjopI7VT+C/jnUb4zeFPGkT23inTlwxk4FzH0EgPGa9UtbiG7gSe1ljmhcbleNgysPUEV8+ftQTSeEvEHhLxppp238Nz5D+joBnB9epoA+iKKoaDqcGs6NZ6jaPvguIxIp+tX6ACiiigAooooA/PDxgL4fGuQeId4f+1Fz5n/ADy83j8MV+hNs0CWcbQMgtlQFSD8oXH8sV83/tZ/DWTULePxdokLNeQYS6RBlmXsw+nNeOyfHjxW3gBfDKyohCeSbxciXyxwFzn04zQB1/7T/wAW18QXjeGPD1wx063ci6lQ8TODjaPVelbf7I/w61O11R/FepxPb2piMVvG4Ks5JB3fSvJPgFa+Fbzx7AvjWRPsoGYUl/1byeje1fesuqaPpOl+c93aW9jAmQQ4Cqo9MUAaM80dvC808ixxICzOxwFA7k1+fX7QninTPFfxHvrvRY0+yx4i80LgyMowT78iu8/aC+Os3iN5tA8JztFo4+We5UkNcew9F55HevnegD0f4S/CXXPiNdF7Pba6ZGcS3coyo9lHc/SvqDw9+zf4I063iOoQXF7drjdIZmCk/wC7Wz+zTd6fc/CfTv7MVESN3WVV/vjGSa6HQPiR4b1zxHf6Da3yLqlnK0TQvkFsHGRkc0Ab+g6Jpvh/T1stHs4bS3X+GJAuT6nHU15d+1aQPhHdfLk/aI8fka9iNeN/tYPs+Ek+RnN1GP0NAHmXg79n7w/4x+H+n6rpOrNHd3EeWlILKH7jGexrxP4lfDrXPh9qv2XV4d0LH91cx/ckH9D7V3v7N3xZfwbqq6Jq8udDu3GCx/1DnuPQEnmvrbxx4U0f4geFn0/UAk1vMu+CdMZU9QwP5UAeDfspfEaG20PU9J8Q6qkcNkpuIjcyAHHHyqT178V4p8Udam+I/wAUL640SKS4juJRHaoqncV//Xmue8feFL7wZ4nvNG1JGDwsfLkIwJEzww9jXuf7GXh+2uta1XWriNZJLdBFFuGdjZB3D3oA8r134PeN9Dsftd9okvk4z+6IkbH0XJrQ8AfGPxd4FmW1NxLcWakb7a6BZgPQFuVr79ycH0PtXlvxH+CvhbxpE8otk07UiDi5t1C7j2L+tAFn4VfF3QPiDEIbR/smqquXtJDz/wABPf8ACvBf2v8AwpZ6N4i0/XrAJDLqBIljTgl153/jmuJ8UfDzxp8JvEEOq2sczw28gMN9APlf2IBzg9DXP+N/GPiX4meILd9TXzrsARRW1uhVQfZfWgD6i/Zv+LNp4l0O28P6vcLHrNpGEQyNjz1HcH19q8a/a68PQ6P8R4r2FQv9qQfaGA9Qdv8ASvM9X8OeKPBF/b3GoWV5pl0h3xS9Cp9Qwqp4o8U6z4puIJ9ev5r2WBPLjaVslV9KAKWjaVeazfLZ6bC09ywJVF6mvYfhT8Ytb+Gly+ieIrW5udOjYg28pKyRH1BIzj2ryfwnrtz4b8Q2WrWZIltpA+AcbhnkfjX2pfeDPBfxr8M2WvtGI7yZB5lxakK4cDlWPfB4oA8T+Lnxo1H4iwx6B4RsbuC1kP71Y8s83oOBkCvENc0bUNDv2s9WtZba5UZ2SLgketffXw4+FPhnwFmXSrZpr49bq4wzj2B7CuS/aO+FMvjnT49W0RA2uWq7fL/57RjPyj0OTmgD4j6V1nh34ieKvD+Bpus3axgYEckhdR9ATWfqnhHxBpczxX2k3kTr1Hlk4/KsSRHjcpIrIw6hhgigD0PR/iFrWsfELQtT8Q3r3JhuERQThUDMASB2r9ARIkyiWJg0bjcrDoR61+XSsVYMpIYHII7V90/s5fEK38X+DrfTriZBq+nRrE8ZPzOg4De+aAPXGAZSrAMrDBB6EV4v8SfgFoviS5OoeHphomqFtxeNTsJ9lGMV6t4h13TPDmmvf65eR2dmnWR/8O9cIfjr8PPMK/2/HtH8XlPz+lAHiWp/CH4w2xNvYazeXlsOAw1Ax5H0LZqtpX7OPjXWbtX8TahHajvJJJ55/Q19E2Hxb8B6gQLXxHbMT2Ksv8xXTWPiDR79d1pqdpIP+uqj+ZoA5v4Z/DLQfAFkq6bAst+RiS7cZYnvgnkD2qt8ftRm0z4T69LallleLaHX+H3ruRe2na7tv+/y/wCNeffG3xL4Zt/AGr2Gsahbs1zCVSFHDM59BjOKAPgMkkknqaV3Z8b2LY6ZPSkbG47emeKSgArovh9oNz4l8Y6Xplmm+SWZSRj+EHJ/QGu+/Zr+H1p448WzSavEZdLsEDSp/eY52g+3FfWHgz4XeFvB2s3OqaJZFLqc8FyCIvZPTrQBZ+IXga08W+BpfDgkNuqxhYHHRSOmR3r4C8aeG73wl4lvtG1JcXFq+wsOjDsRX6WAgcscAck+gr4O/aV12z134pXxsNrR237gyL0kI5z+tAGl8Bfg2fiFBfahqc7W2nQ/u0IBy7kcEewrzn4g+F7nwb4tv9EvcGS3f5SO6nlT+WK+6fgXoy6J8MNFtVXEjR73OMFieea+S/2ntTtNV+LuqS2LpJGiRxF1PVlUAj8DQB9E/sranpY+Eaok0EL29xK06swBHTk15n8XP2itTk1G80rweFtbSJzE1ycOZcd19K+etN1nUtMhni0++uLaOcbZVjcqHHvXZ/Bf4cXfxE8TragPFp0Pz3U+OFX0B9aAOQ1nX9W1qUyarqN3dsTn99KzAfQE8Vl1+h1n8IfAtpo39njw/YyRhCDNLGDIeOpb1r4E8TW1tZ+INQt7F99tFOyxn2BoA+4/2bfCFr4Z+HVlciJDqF8PNmmxyQeVH5GvT9Rv7XTLKW81C4it7aIbnkkYKB+deT/s0eObPxN4EtdPknRdUsB5UkROCV/hI9eBXBftpalqtvZ6LYpK8Wl3DOWVTgSsAD83rjNAGt8Rf2mdJ00TWfhOB7+5AKi5cbURvoR81fL/AI28ca/40vftOv38k5BysYO2NfovQVjaNpl3rOp29hp0Lz3U7BERRkmvtb4VfAXw94b0XzfEtnb6rqcyZk+0IGSLjoAen1oA+JdPvJtPvoLu1bbPC4dGxnBHSu5uviV4/wBcASPU9SbjAFqGH/oNP8OeGrDxJ8ZotGso86TJf7GC9o93OK+7dH8G+HdGWJdO0aygMYADJEAeO9AHwuvhb4l6rpc+ozLrcltEhdzPO4OByTgnNedTzTSt+/kkdh/fYmv0Z+L3iuy8I+A9UvL11DSQtDFHnlmYFRgfjX5ySuZZGdvvMcmgC3p2rahpsyy6ffXNtIpyGhlZD+hr6Z+C37Rgijj0nx27tyFhvlXOPZgBn8a+WKKAP1F0/UtM1yy8yxubW9tpV52Mrgg9iK+bfE37Lkup65qt/Z69b20VxK00MAtyQmSTt69K+dfB/ifxXoTF/DN9qEKR/M625Yrj/aA7V6Rpn7S/jqxQRXA0+5C8Eywnf+eaALXh/wAZ+M/gT4l/sXxDHLeaX1ELvlWToCjHOOnSmftEfFzS/iPpOi2GjQTJ5Mhml8wYwxGNvv8AWuR+K/xZ1L4j2ljDqdhZQNauXEsSYdsjGCc9K7L9m/4PS+KdSi17X7dk0a3cNGjji4Ydvp0oA+ofgnp9zpfwr8OWd+hS5itQHU9RyT/Wu3pEUIiqowqjAHoKWgAooooAKKKKAGTxRzwvFMgeNwVZWGQQeor4q/aJ+C114Wv7nxB4fhafQ5n3yRovNsSfT+76V9sVFd20N5bSW91EksEilXRxkMDQB+WPSrM2oXk1slvLcyvCn3UZiQK+s/iR+zJaahdveeD7lbNnJJtZP9Wv0PWvnO78D3+i/EG38M66nkzG4WNmX7rKe6nuKAOPII6ikr6q/aF+Cka6Np2r+CdKw0EQS6t7cFi/Gd+PbFfLl3aXFnKYruGSGQdVdcGgD2v9lz4jR+EfE0mk6rNs0zUSqBnPyxPng/jnFd3+0J8KbzStUn8f+E5irxyfabiNOGBzy64r5URmR1ZCQynII7Gvd/Dn7QV/b/D+88Pa5arey/Z/Jt525JHTDfh3oA+oPg14uPjT4f6bqc2Ptmzy7gDoHFcR+2BJ5fwkAHVr+Jf0al/ZHtJrf4YvLMCFublpY8+h9Ko/tlXCr8NLODcAz3yNt74CtQB8V19Mfsx/GE2U0XhTxNOTayH/AES5kb/Vn+4fboBXpvgr4UfDfxJ8P9OntNKgmFzbLvuldi4k2/MevUHNfN3xn+Feo/DTV4rm0kln0qR90FzjlGB6HHTtQB9J/tMfDpPF/hGTU7CEHV9OXzF2D5pU/u59gSa8q/Yw1hLfxHrGlTMEMsHmJk9WyBivd/gX4kl8YfDDTrq/BedU+yzM3/LQgYLfjmvlnxgx+FPx6ku9OUpYw3KyrEvG6PjIP45oA9U+LfxN8e/DfxxcNJZ29zoUxDWvmBihXuMg8HOa634cftA+GfFUiWmqE6RqDEKBMfkkY9lIz+tei6jp2h+P/CsQv4Yb7TryIOrDnGR2PXg5r5T+Kn7POseHZnvvCXn6nYZz5aj98n4DsPWgD7JkWK5gKuscsMgwQcMCDXP6d4G8LaZqw1TT9BsbfUQci4RPmzXjX7LPiLxZcPe6D4it7lrK0RfKlnQhozk/Lmuu+OHxhtfh1FFZ2UUV5rUqhxC5O1F7Fsc+tAHovifQdO8TaNPpmsW0c9tMpBDKDtPqK+CfjN8PLr4e+KpLJ90lhPmS1mI+8mf6V9i/Bn4nWXxG0QygR2+rQD/SLVT0/wBpc87azv2kfCkPiT4a6hc7B9r02M3KOB82F/hoA+DK9U+BPxSuPh/ryxXZeXRblgs8efuZ/iH9a8rooA/T7Tb611OwgvbCZJ7WdBJHIpyGU9Ks5r5O/ZM+Is8OpnwhqczPb3GXtCxzscDJGfTAr6l1XUbPSdOnv9SuI7a0gUvJJIcACgBmu6xY6FpVxqWqzRQWkCF3dgOgr87PiPr8Xifxpqmr28CwQ3EuURRgYHAP44zX0BqWpX37Qni2fR9MvH03wtp67yV+/M394g/U1n+Lf2X7u1tGl8M6obyRRkx3O1CfpjvQB81VqeG9e1Hw3q8GpaNdSW13Ecq6HHHcVY8TeFNb8M3LQ63ptzaEHAaRCA3uDWHQB3fxE+KHiDx5b2tvrMyi3t+VjjJ2lv7xz3rhKKKAFyaUOwPDMPoabRQBaF/dhdouZgvpvNV2ZmOWYk+5ptFABRXWfDjwNqnjvX49N0qM7essxHyxr3JNfQHi79nDQ9H8BajfW+p3cmo2Nu9wWZVw5UZ2/SgDQ/Yzto4vDetXC48yV0DevBOK+i6+U/2MtfSPUNZ0OZx5lwiywL/u5LV7d8XviPp/w98PvPK6SanMpFtb55J9fwzmgDmf2i/ihB4N8Oy6Vps4Ou3qFVCnmFD/ABH69K+IGdpZy8hLOzZJPck1oeJddv8AxHrN1qmqztNdXDl2YnpnsPas1Pvr9aAPu34t+K5PAnwZtp7M7bu4hitoipwULIDuH0xXhnwi+BN14502bXvEt1cWdrPuaLbjzJW/vHI6Zr6N1fwXpfxB8IeHodZeT7PbCKfYnSTao+U+xzXUa3Pb+H/CN/LAqQW1jZuY1HAAVeB+lAH5v+INPGl69f6fGxcW87RBj1ODivu79njwzD4a+GGlBFAuL5BdysRyCw6Z/Cvg/Vr9r7Wbq/P3ppjLz7nNe0ar+0PrH/CD2Og6HbR2M8dssEl2pJcYGDt7UAeuftE/GG08NaVceH9BnWfWLlCksiH/AI9wR6/3q+LnZpHZmJLMck+pru/hv8Pdf+JWsv8AZRIYFbNxeSklV9snqfavrPwn8APBGiWKR31h/a11j5p7gkHPfgHFAHxBoGtaj4f1KO/0i7ltbqPpJG2D9K6bx38Q/EPxC/s211iTz2tjthVc5ZmwM/U4FaH7QOg6T4b+KGpab4fiWKwjSNhGpyFYqCR+dd7+yr8NF1/WD4l1i3LabZn/AEdWHyyyZx/46cGgD1f9mz4UJ4R0WPXNahVtbvFDIjDm3XsP97rmtX9pD4jQ+DPCEthayj+2dQXZEqn5o1/vn24xXSfFn4jab8PfD73V06PfyKRbW2eXP09K+CPGXijU/F2uT6prE7SzyMSATwg9B7UAeofsmw/bPiwsknLLA8uT619WfFX4k6N8OtKW51NjJdzZ+z2qfek9T9BXyn+yPOsPxUUOQPMtnQZ9TivW/wBrX4e3/iDTbTxBpEctxLYBkmgQZO0nO7HtigDwjxPrHjX42+IppbGwuL2O35jtIPuQr68nqax9Q+EvjvToTNeeGb+KMdWIUj9DR8KPiHqfw58Ri9sgHt5CEurdxw6/zyK+6Ph78QdA+IGmefpNxG8yj97auRvj+o9KAPzjuLea3kZJ4njdTghlIIqKv0q8R/D/AMK+JDnWtEtLk9M7dp/8dxXzv+038J/DXhbwha6x4ZsRYyrcCKRFcsrKQTnknnigDe/Zo1Xwx4b+Emo6pfz2ySgk3gbG5sFtq475FfKviq8g1LxPqt5Yx7La4upJIkA6KWJA/Ks+KScr5MTybXONik4Y/SvqX9nr4ERvFbeJPGVuxbIktrKQYGOoZv0IoA574C/Aa712W313xZC9tpgIkhtmHzTjqCf9mvsOxtILG0itbOJIbeJQiRoMBR6CpkRY0CIoVQMAAYApaACiiigAooooAKKKKACiiigAr5a/a98O31lrei+MrFR5NuFgk2jkPuLAn2xxX1LWV4o0Gx8S6FeaTqsQltLlCjjuPcHsaAM34d+KLDxj4SsdT06QPG8YR0PVWHBBH4V4b+2Rd6Ja+HNP09ILcatNLvUxoAyKME5x61474usvGHwV8T3Fjp9/cwWUxLQyKx8uRfp0yOlcDLdat4x8SW6Xt3Nd397MsSvKxbliAPwoAxK674beBdV8deIbew0yBjEXHnTEfJGvfJr668F/s9eENJ0m2/teze81LaDM7SEru9AK9T0vSdG8M6e40+0tNOtY1y7IioMDuxoAh8N6TY+D/CNnpsTrFY6fBtMjnAAHJJr4k/aJ+JP/AAnvisRWJI0mw3RQdvM55Yiuy/aN+NTa7LP4c8MXDLpiEpcTocecfQH0r5zoA9n/AGfvi9L4EvxpmqbpdDuXG7nJhPqB6c819i+IdI0fx74Qks7jZdadexho5BzgnkEV+aoRipYKSo6kDpX0N+yv8TbnS9eTwxrFyz6ZdA+Q0hz5LgdPoeBQBZvfGHjf4G20/hprGO601HLWl6RgFT0zgYzx0rwnxVrWreKNWuNa1h3muJzlpNuF/Cv0n1LTbHU4RDqdlbXkQ5CXEYcD8DVA+FvD5h8r+wtL8r+79lTH5YoA+Tf2efjYnhOJfD/iXe+ksf3M68mE/wB3Hp15r6u8OeLND8SKf7E1O3unxkxo4Lge4rznx78APCXiSKWbTYTpeosPlkiOIx/wAcV8v+KPCvi/4PeI4bkNNAA4aG6hY+XIAejY9fQ0Aff8MUUcmY4o4yx5KKBn61+fHx5kvJviz4hF6zMy3TJFk5xHn5QK+vvAfxY0bXfh5/wkF9dwQXNrEftMLMFYuo7D3I4r5E/ta38a/GddRvR/od7f7wr8YTOQKAK/w11rWvAHizTdaWzukt2cLIrIVEyE9K+1/ilqccXwk1++b5fN05nVT6lQcfrXTT6Npc8ccc+m2UqIBsV4VIXjtXif7XPiJNL8DW2jQSCO5vHB2DvEODQB8ZscsSep5pKuaTp11q2pW9hYRGW5ncJGg7k19e+AP2bfD1lpkL+LfOvdSdcskUpRYyf4cd8etAHyT4b1q78Pa1a6pp7bbm3bchrqviL8U/EfjvbHqlyY7NeVt4zhfx9ad8b/AA9ovhjx7daZ4cdmso0BKs+4o+Tlc+2BXAUAdv8ACTx/efD3xOupWyedBIvlzwk4DJkE/jxX3h4K8V6X4y0OHVdFm8yFwN6Hhoz6EV+bFezfsueLj4f+IEdjd3Xlabfo0bIxwpk6If50Afat9Y2eoRtHfWkFypGP3sYb+deEeIPA3gTUfitP4V1DS/s8txaJNaSRSbfnIy3A9q9/9COQeQR0I9q+P/2m9euNE+N9jqGnSlLizt4XDKfbkflxQA74jfs26rpMU194Wul1G1QF3if5HUeijndXgN5az2VzJb3cLwzxnDI4wQfpX6EfCz4haX498P293ZTol+qhbi2Jw6PjnA7j3rF+L3wj0jx5p8ksEMVpraAmO4Qbd5/2sdfxoA+Cq91/Z7+D1p45s7vVtdldLGNtkUaf8tD3ye2K8b8QaPeaDrN1pmoxGK6t3KOp/Q/jX23+y/bJbfB/TtoG555WJ9ckUAcV4o/Zg0y5hkk8P6rJbzgfJDImVY+7Z4r5n8Y+E9V8Ia8+ka1AIrpehU5Vwe4PcV+knXpXyv8AtgXNi3ifw9AChvkCtIR1CZ6H8aAPZPgN4Ng8H/D+xj8pV1C7UTXL453Htn0xirXxx1iPRvhfrkkpA+0272qk/wB5lOK7Wz2mztvL+75SYx/uivl79sHxnFPJY+FrKZZBEfPugp+64+6PyNAHg3w+8V3PgvxTa63ZIJJYMjYTgMD1FJ498W6h408R3WranIxeVjsjzkRr2UVzlFABSjgg1qeF9Av/ABPrtrpGkRCW9uW2xoWwDxnqa6/x58H/ABZ4K04ahq9kv2PoZI5A+0+4FAH2b8FdTGr/AAw0O7DZLRbT7Y4rgv2sPGA0TwQmi277b3UmByOyA/MD9RXj3wM+Ny+A9Gu9K1e2ku7Unfb7TjYf7v41z16PFHxz+Ics1pA7BzgZ4jt4x6npnH50AeW1u+CPDtz4r8U6fo1kMzXUgXJ6AdSfyFfWGkfADwZ4Y8MXV34laS8uYoWZ5jKUQccAD1rz79k4+H0+IOuXDzxQkR4sVnYA/e7E9TigD6f8IeHNL8E+GYNN08JDa26Zklbjd6sxrgfiP8dPDXhnSrkaVdpqGq4KwpFygb1Jrqfi/oureIPAGqaboDlb+aIhVDbd/HTPavje3+Bvj+5naJNGO8HBLSqB+ZNAHC6lfz+IPEEt5qE2Jbyfc7schdx/kM1+hvgy1sfC3wzsm09Ue0tLD7Sxj6SEJuY/jivhvxr8JvGHgzTRf69pgitCwXzI5VkAJ9cdKztN+IXijTtEl0i11e5FhIhjMbOThT2HoKAF+J/jO88deLbvVbp38pmK28RPEceeBU+q/D/UtJ+Htn4q1HEEF5cCG3iP3nUru3Vd+B3g3/hNPiBp9lPGzWEbiS5IPRRkj8yK9s/bOu4rbRvDujW6pHFG/nJGowFG0rwKAPm/wT4jufCfifT9ZsxuktZRIUzgOB2Nfdnw++L/AIV8b26JBdLbXrKBJbXOFyccgZ618S/Dr4fa54+v5bXQoUfyl3SPI4VR+J71P41+HfizwBcxyavZS24zlLi3fco/4EvQ0AfYnjn4E+EfFd3JeeQbG8k5aWDof+A9K8a8ZfAfxH4AH9veBdWmvPs/zyJjy3AHPAB+ar37O/xyuWvbbw14uuPMik+S2u36qeyse+elfV2AQQcFSPwNAHzT8LP2k7a4EWmeOYWt7sfILuJch2/2h/DUH7WnjrQ9W8FadpejajBeTzXCzt5LhgqAEc46HJri/wBrD4dweG9fi8QaVCsOn6i+2RF6CbknA7DAry34aeBdU8e+IodN0yJvK3Azz4+WJM8kn19qAPaP2VPhVFq06+LdbRXtIWxaQkZDsDyx+hFfXqgKoVQABwAO1ZHhHw/Z+FvDtlo+mxiO2tkCgDue5/E5rYoAKKKKACiiigAooooAKKKKACiiigAooooA5T4keBtK8eaBJpurR/NyYZh96NscGviybwfqPws+Kuj/ANuRFrCO9jKXWMIybhzn2Fff1Y3i3wxpPizR5tM120jubWQdGHKnswPYigCvrnjDQdF0Y6rqGpW8dmU3q28EsMcYFfIvxd+NOr/ES+Xw94YhkttMlk8sIvMlwenJHbvitz4ifs3eIYr1W8N351DTRxHDPITJH+mMV3PwH+Ar+ENVj17xLLDPqMYzbxRnIiJ7nI64NAHn9j+y5rM3ho3tzq0EepNH5i2vlk7T/dJz1rwvRdAuNS8V2uhcx3M1z9myR0OcV+nLYCnPTFfA6arZRftEwX0DILNdTGT2Azg0AfVUPw08KeG/AF1aNpFtMYLJ2mldAXdgvJz9elfEfhG3a5+IdjHpoKr9tBQDqEDf4V+jt3DDqNhNA5DW91EUJHdWH+Br4LvbOX4VfGUvq1q32eCdpYwo6xMTtI/CgD75uZEiWSWVgkaLuZjwAAK+VvHn7Sl9Y+OPs/huG3m0O1fZIzrlp/Uqc8Csj42ftA/8JLpcmjeE0mtbOYYnncYZh6Ag15X4P+F3i7xdafbNH0ieWzPAnOApPtQB97eCvE+n+L/Dtrq+lSBoZlyVzyjdwaXxp4csvFnhu80nUYUlSVCI9w+4+MBh9M18pfBzVPEPwh+INvoPiu3ms9N1NtpWT7m44AkH0r7IjKsUZCCrYII7igD8ydbtZtG1bU9K8xwsE7QuP72xiBmoLZbuwltr4QzIqOJEkKkA4OeDXXfGGKFfilrSR48tro7sepbmvsjR/B2jeKfg7omkX1rE8DaegicAZjbHBB/KgCz8KviLpnjXwhBqDXMMN5DGBdxM4XYwHPX6V8lfHXxPc/ET4kXA0qOS6tLTNvaiNSx29zx75rjvGOhan4I8S6jo1xJJG8bGMuhIEqZ4I9q739nL4g6L4F128l12AlbhMLOoBKcdPpQBzHwh1W38K/EjT7rW42hijYo/mLgoT3Ir6f8Ait8edB0HSJovDdymoarMpCFPuxZ/iNfMXxs8V6f4y8e3mraRbmG1cBRuADNjucVwYySAMk0AWNQvLjUr+e8u3MlzO5kkY9WYnmvS/hp8E/EnjZY7naNO01ul1OhIP0HWvUf2ffgfA9vB4j8ZWokVwJLWzfoQeQzD+VfS80ttp9m0kzR29rCmSThVUCgD4M+NXwwm+GupWEDX0d7FdxGQOqlduDjHNecRu0UivGxV1OQR1Br1L9onx5D448bb9Pffptihgt3H8Yzkn86870LR77XdUg0/Srd7i7mbaiIOaAPTfBPx98YeGLWO0aWDULVcAfaU3OAOwOa4Lx34pvPGPiW61jUAommPCqOFXsK+l/AP7NOlw6WkvjC4ml1CQZMVuwCR57HI6ivA/jT4W0vwf47utJ0S5e4tI0VsuQSrHqvHpQBy/h/XdS8P6gl7o93La3Kn7yHGfY17joH7TviC1tRFq9ha3TKMK8a7SfrmvEvDfhvWPEt6LTQtPnvbg/wxD+td5P8AAfx9FB5iaLLK2M7EIz9OtAHF+O/E934x8VX2uagkaXF0wLKgwAAAB+grv/hZ8cdY8CaJ/ZC20N3YK5dAw+ZSevNeVahZXOnXktpfQtDcxNteNuqn0roPDXgDxV4ntftGgaJd30Gcb4wMZ/E0Aeu65+09r9xavFpOn2tq7DG+Rd5H0rwvW9b1DXNUfUNUuXuLtzku5zVvxH4R1/w1KI9d0q6sn/6aL/UVhUAe2L+0V4qh8KQaRbR2qTxx+UbkpliPUc8GvHNRvrnUr2W7vpnnuJTl3c5JNe2/DT4CS+M/BKayupxQTz5MKE/KB/tcV5t8QvAOueBNTFrrVsRG/MU6jKSD2NAGFoGk3eu6xa6bp8bSXNw4RQBnqetem/FT4J6l4B8OWeqzXsV2snE6IhHknj8+TWF8DPFem+DPiHZ6vrMTy2aRyRnaASpYYDfhXo/7Qvxm0zxfo0Wh+HY5Gt2O64mkHDcggL+IoA8b+HnieXwd4x03XIE8xrSTcUP8QIII/WvafjV8d7Dxb4OGjaLZSxPcjNw8jAhR3Ue9fPFvGHuIkkyqswBPoCa+ktV/Zvtr/wAI2mqeENUe4uZIRKUnYFZD6JgfzoA+dtD02TV9Ys9PgYLLcyCJSegJr9DPhh4K07wL4XttN06P96yhp5j96Rz1yfTPSvz4vLXUvDWtmG5jls9RtJM4YYZWHevvL4I+PIfHPgq2uVZf7TtVEVxF3BHAJ+vWgDyj9rrx/wDZ7eHwhp0g3yATXjA/w9VA/Wvla2nltp0mt3aOVDuVlOCDXp/iXwR4s8W/FXU7T+z7lria5b53X5Vjzwc+mK6P45/CvQPhz4M0hoLqa41u4l2yksCv3cnAx0oA+h/2e/FF14q+G1lc6hIZbu3PkySHq565Nej3d3DaWzz3cyQwIMs7kAAfjXzJ+xj4jj+zax4dlf8AebvtcYPpgLgVS+POlfFLxj4qutNsNGv28PxPi3WHASQYHzNzzzmgCt+0l8ZbLX7B/DHhphNaCQNcXXZiP4V/WvmuvfvCv7M3ifUVSbWLm20+L+KJyfM/lius8Rfs06LpPhi/vzrU8c9tC0u6R18skAnHTPNADP2KNOQSeINQIBd0SIe2Dn+tcx+2Vf8A2rx/ptup+W3s9pHvvNZ/7NnxP0zwBf6pba6XNncqPLeMcKwJzn68VxHxk8Xw+NvHN7qlmjpaMxWEP12570Adx+yf4uj0Dx8um3bhbfUv3Sf9dDwK+1Ne0ex17S59O1SBLi0mGGRh+o96+QtL+DEcHwSXxjb3FxD4hhjN8gDYChScKOOvGa+i/gj47t/Hfgq1u1dRfW6iG5iB5VgMZP1xmgD44+Nvw6u/hv4uIty502V/Ns5xxjnO3PqK9K+Gv7TM+k6VDp/ivT5L4xAIlzCwU4/2s9TX0t8RfBWmeOvDdxpWqRg7hmKUfejfsQfrXy/4f/Zf1yXxI0OsXkEekxPkyxk7pV/2eOtAGx428X3P7QE2n+F/CukTW1rDOJ7i9nO5Y1wR2+tfQHwx8A6V8P8AQE07S03SN8007fekb1NaXgvwnpHg7RYtM0O1SCBB8zAfNIf7zHua3qACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAPDv2nvibdeCtCi0rR2CanqCH94escfTcPfNfD7Su0rSsx8wncW759a94/bHu/tPxJtI92RBZhMenzE1F8JPgla/EDwHe6jDqhh1NJAI025UcfdbnjNAHsv7O/xftPFek22g6xIkGtWyCOMsQBMijjHuAK6X45fDK2+Ifh/EeItXtQWt5f73qp+uK+J9e0PX/h54oSO9ils7+2kDxSDIDYPBU9xX338M/Ef/CUeB9M1aT5ZWiUTMePnCjcaAPzp1PT7nR9VmsdRhaK5t5NksbDBBB5FfoR8Fr2zvvhd4fl08KsPkYKDqpyeDXyH+05rGk618UrqXRCjxwwrDNIg4eUE7j79RzWz+zZ8Vk8G6k+ja3Kf7GvHBV2PELnA3fTFAHs37WPhebWPAqatZRl7rTnDvgcrEMkn9ayPAv7Q2iw/D1G1tnGt2cXlrCMYkIHy456cDNfQDrbajYFWEdxZ3CYI6q6mvhL9oX4eDwH4vH2PnS78GW3Ppj7w/M0Aed+INTk1nXL/UphiS6neYj03EnFfoP8H3aT4V+FHfO46fFnP0r5R/Zy+GOmeP7vUZ9XuGENjsYQqPv5J6nPHSvtGxtbXRtIitrSMRWdlDtRP7qqM4oA+Vv2zra0XxFpE6EC8aDayjuvr+dfN1eytCfjN8cJ7e51DybGadhCTyVjH8KjvTfjP8KbDwH4v0Swg1LzLTUQCxkG0xDdgnr096AOK8HfDvxL4ujaXRdMnmtx/wAtth2E+mfWuelin0jV2inTbc2c+GRuzK3IP4iv0H1DVtE+HHgCG4MlvZ20NsphQEDzn2jp6k1+fWvX51XXNQ1BgQbq4knIPbcxP9aAPoaz/ahurTQ7a3j0G2e7hjEeWYhDgdeK8r+IPxa8UeN98Oo3ZhsGORaRfcH49a8/AycDrV660fUbS0S6urG5ht34WV4yFP0NAFOKN5pVjiRnkc4VVGSTX3F+z/8AC+28E6FDqV8iya7dxhnY9IlPRR+BGa+d/wBmHw1b+I/iVEbsKyWERvAp7lWGOPxr7B+IfiaHwh4Q1HWZcAwRkQr2MmPlFAHIfG74r2fw/wBNNtbbbjXLhT5UWf8AVAj7zfzFfJ/w+8I6v8VPG0is7nzZDPeXZHCAnJJ+vauc1bUNS8ZeKmuLl3mvr+cKoJzgs2APoM194/CbwVbeBfCFpp0Uai8dA91Jjkueoz6ZoA0fAvg7SfBOhxaZo0IVVGZJWHzyN3Y1N438VWPg7w5daxqcgEcQ+RCcGRv7o96u65q9joWlXGo6rcJb2kClmdzj8K+GPjX8Tbz4g66SjPFpNuStvDngjP3iPWgDi/FWszeIPEF9qlx/rLmUvjGMDsPyr6G/ZZ+KFtZwr4Q1qRIELF7SdsAFj1Un16Yr5ps7We9uora0heaeVgqRoMsxPYCtvWfCfiTw4qXOp6Vf2KAgrK8TKAfr60Afopq+mWWr2M1lqdtHcW0o2ujDqPr1r5K+OvwMfw0kmt+FVebSustvgloPp/s/Wu8/Z7+M8Gr2lt4c8UXAj1GMbLe5kbAlA6KT619ByxpJG8UyBo3BVkYZBB9qAPkT9mL4pQeHro+GddkEen3Um6GdjgRPwAD7da+ovGHhnSvGOgy6Zq8SzW0q5SQdUOOGU/jXxx+0V8OT4K8VNfabGV0e+bzItvSJj/Bn8M1meCPjT4u8J2i2dvfvc2S9IpsEj/gRBNAD/il8Hte8E38rRQSX2lZJS5iUsFX/AGvQ1i/B230e5+I+j2/iaFZdMeQrJG5wCcHb+uK+vfgj8TIfiZpN1HeWkMV9aY82Energ5wRke1eA/tR+FbTwr47tdU0dUtlvAJfJTjbIvJbHbJoA988ffBPwpr+h3ENlp6affohaKeIkkEDIGDxXmfwO+Kg8H3cngbxo4gS0lMNvcucCL/ZbPbv+Nej/CH4uaD4g8IWravqltaanaxBbhbiQKXIHLDPXNfI/wAZNVs9b+J/iLUdMdZLO4uS0br0YYAz+lAHsP7XTeGr5tJ1HSry2m1NwQ/kMDvUnqf0rk/2VvEsujfEeGxdyLK+RlkT1bHy/rXm9n4U8SarpTana6VqF1Ypx5yxsygfX0pfAmsL4c8ZaXqNwGEdtcI0oA5Chhu49aAP0imaK3SSaUIixqWd8AEAdea+CPj945Pjbx3cy275061/cW4ByGCk/P8AjXpXx4+PVn4g0b+xvBslwkE+DcXDrsbH90Cof2c/gtFrkMHibxRDusCc21s2R5vox9qAPGfh74gvPBnivStcSOQQxyqzDGBKueRX6L6deR39hb3kDBop41kBB45Ga+Pf2ttc0ybxLYaFo8Vsi6fH+/8AJQLsk5+U49jXrv7LHja31/wRFoc06/2jpg2bHb55FJJ3AdwOBQB654h1uw8PaPcanq9wsFpApYsxxuOM7R7mvij40fGrU/HryaZp6tZ6Hv4iH3psdC3/ANauw/bH8Tzy+IbHw7BcFYLaPzJ4lP3mbBUkfTNW/wBmf4OpepF4q8UWwa262ds/8RB+8R6dCKAPHNO+FXjDUNAfV7bRbs2oXeuYzudfVfUVxBUpJtcEMpwQe1fp3q1/Z6Not1d3skVtY20RLE4VFGOB6e1fm54z1K31fxVqmoWcQitridpI0AxtBoA+4bS9tm/Z0lu1K/ZxpTnOeMAEV8y/s3eOz4S+ISQTM39m6k3kug6byflb8K5SL4leIY/ADeEVuiNLJwBgZCHqv0rjYJZIJklhYpIhDKw6g0AfqgCCAQcg9xRXE/BjxKniv4c6PqKnc6xCCQ56ugAY/nXbUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFVdV1C20rTri+vpUhtoELu7nAAFWq+dv2yfEz2HhCx0a1mKvfTHz0B6xgZH6igD5t+MPib/hNfiDq+q2QeSyMmIflPyoPX8c16d+yH44j0bxBd+HL6RUttRxJGzH/loowFH1zXa/shaFompeAtWlvdPtbu4N2Y3M0YY7dg457VwP7Q3wym8A67beJfC6NFpskm/MY/495M5A+lAH1p4l8LaJ4kjEeuabbXZXhXkQF1+hPSvL/2h/FSfDn4dW+m+Hkjs574mCAIuAqrjdx64PWvQPhh4jXxV4E0jVlJLSQhHJOcuowx/PNeCftswyBPDUxJMTNKoHoQFoA80+AHw2sviTrOopq19JGlrEJSq8s+Tjk5969d1j9lnSplzpWuS2rDs8Zkz+tfP3we8cTeAvGlrqa7mtHIjuY1ON6f/WPNfoHomq2Wt6Vb6jpdwlxZzruSRe/+FAHn/wAI9D8Y+EoToWvOmo6RED9nvS4DoOykZJP1rzH9tO6tRaaBattN4d7L6quRmu/+NfxC8UeBbeS603QjNpvCi9LKyqx9V618Y+M/Feq+MNbl1TW7gzXD8ADhVHoB2oA9n/Y21gWnjLUtLZhm/hUqD/sZNfSfxb1c6D8Nte1FW2tFBgf8CO3+tfLv7NXgzxHb/ETSdabTp4dOVHYzt8owVOPfnNey/tZar9h+Gwtd2Pt0nl49dpBoA+MNM1G70vUIr7T55Le6ibckkbYINWvEXiLVfEd8t3rN7NdTqNqtIxO0eg9K9d+CWn/De00K81bxnqEM9+qkLayRkqgx6dzmvG9XNvcazdHTYmFu8reSgGTjPFAEmp69q2qQRw6lqd5dQxgBEmmZ1UD0BNUrS2mu7qK3to2kmlYIiKOSScCvRdQ+EesaR8O5fFeuSJYxeYiRWzjLybu/tXR/sn6Da6r8Qvtd2iyfYoy6IwyCSOv4UAetfBb4E6boVlBqviq3S91SRQy28gykWR0I6E1W/an8T6JpHhIeG7W2tH1G5IzGiAeQn94eh4xiuj+Nfxm0/wAE2j2OlSx3mvSjgKQywj1btn2r4s1vVb3W9UuNQ1Od57qdizu5ySaANf4eeML/AMEeJYNX005ZflljJwJEyCV/HFeofGf45R+PPCMWiWem/ZVeRZpnMhOCvYce9Vv2d/hOnja6l1bWlYaPauFC9DK/XH0617J8f/A/hKw+F93cQaVY2E1sVEM0MYRi3YEjrmgDwn9l/TbTUvivZJfKjpHE8io3dhjB/CvtnWNStNI0+e/1OdILWFSzu5wK/NzQNZvtA1a31HS52guoGDKyn9D7V1nj/wCKPijx8IrfV7kCAEAW9uuxHPbIHWgDuPiJ4u8R/GjxX/YfhO3lfSYm2xopKh/9tyeg9jWX4j/Z+8Y6Po73626XIiXdLHGwLKPYZ5r6E/Zq8Hnwt8Pobm6gEeoaifPcsuHVSMBD+Vdb8TvGdn4G8KXOp3ciicjZbxZ5kf0x+dAHz1+yH4Ttb7W9T1u9iDzacwhRHH3WYHnHqMV9ValY2mq25t9TtobuA/wToHH5Gvhz4N/FJ/BPi+6vLyNpNMv5We4jXqpJPzfhmvsnwz4z8PeJrRbjR9VtpUIHyu4RgfTB5oA8V+K/wAt/Lm1vwIz219EfONpu4YjklT/D9KvfAj4x/wBo48NeNJRbatAfLinm+XzP9ls9D7mvflPRhyOx7GvkT9pz4bXWk68/irR4neyum3z+WDmKTu3sOlAHtP7SulQal8KNRlnUFrMfaIm9wMf1r5l/Zz8JaR4y8ftp+vo0lolq8wRX27mBGOfxrG1H4qeKtR8Hnw3fX3n6eerSDMhHoW9Kw/BHiS68J+JrLV7NmDQSAuqnG9MglfxxQB9GfET4Naj4MWbxD8Mby8h8sFprNZCW29znPPfivm7xRr+sa/qBn166nnuU+TEpPyY7YPSv0L8GeKNO8X6Bb6tpUySRSqC6A8o3dSPrXz5+058J0SKfxd4fgVFXm9hjXuT94D155oA818I/A7xd4k8Nxaxp8USwzpvhBlUF1/PiuA8UeG9V8L6pJp+t2cltcoejDhvcHoRX0r+yx8TLV9PXwlrM4iuI+bSSQgKw/ue2K7D9qjRdPv8A4cyX94iLd2h3QSn7x/2QfT2oA0P2Z9Us9S+Eun2dv5bSWmYriPqMnpkfSvnP9p3wpZeGviA8mlosNteKJPJUcK3f8zXJ/DH4i6z8PtUkudJkDQzDE0DjKv749feqXxE8aal468RS6tqrKHYBVjQYVAPQUAcwpwwJGQDnHrX0f/w0WulfDvT9G8P6YsWpQ2q2/ms3yxYGCQMc14t8N/CNz448XWeh2brG82WZ2GQqqMmt/wCM3w0uPh54ghtFnF3aXChopVGOT/Dz3oA4S7nvNVvLi7uGmubhyZJZDlj9Sal0PWdR0K/W80m7mtLlRjfExUkehx2r60+Efw6sPDvwb1DU9Xs4ZNR1C2aVzKmWiXpt/TNfIF3gXU23pvbH50AdFof2zxn4801NWnlu5ry6jjmkkbcSpYA9fav0YsbW20jS4bSLbFaWcQQHoAqjqfwFfm54J1YaF4s0nUnOIre5jeTjPyhgT+lfolcG18YeEJjp91/o2pWxCTRtnaHXvj68igD5Q/aP+LE/inVn8M6BN/xKYX8uRkP+vfP6jOK9C+EX7PWiQ6Bbaj4viludRuEEnkbiqw5HQjvWX4M/Zlm0vxdbXus6rbXemW0nmeUkbBpMdOfrivpHWNUstF0ya/1S4S2s4Fy8jnt7etAHxj+0/wDD3RPBGt6a/h9WgivIS7wu+7BBxkegrw+vQ/jP43uPiL47lu7ZJWtIz5FlCBuYL7Y9TzWlD8CvGR8LXOuXVoltDDF53lSMN7JjJPXigD139i3xI8lnqugTyAJCfOgXPUsfmr6ir4B/Zm1v+xfizpbSORBOHhdc8EsuB+tff1ABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXxL+2Jqf2r4jwWiPmK3tlyP9o5zX21X5+/HppNZ+NGrWkRHmfa/s65PAOcUAdH+yn46j8OeLm0a/l2WOpkKuTgLL0BP4V9la1pdhrWnS2GqW0V3ZzDDRyDIIr89fiJ8O9e+Ht/CmqopjkAeK6gJ2MevB9a+o/wBmv4r/APCYacNA1mT/AInNpHlJCf8AXRgck+44oA9g8P6LpvhrR49O0i3jtLCHcyxrwq5OTXy1+17400jXJNI0bSLqO6msnked0OQpIAx9eDXtH7Sur3Wi/CLU7ixdklkkjgLKcHa5wa+MPheNDk8c6aPFZJ0pnxJ9f4c+2cUAc/aabe3efs1pPLj+6hNe0/AP4n6h4C1NdF8RRzrolw4GZFP+jseN30r7F0qx0210+BNLt7ZbLYDF5aAgr2xS3+l2GoQmK9s7eWPrhox/hQBh/EaC01X4c66k217aWxdgxHYjINfnJMoSaRVOQrEA/jX1x8f/AIw6JYeGL7wr4ZlFzeXCGCV4/wDVxIeGGf71fIdAH6LfCPWoNd+HGhXVsVKx2yW7Ef3kUA14d+2lqQaLw/pgb5o3eYj2Kgf0qv8AsgeN0inuvCl/MEWT97aAnjdkl68//ag8RRa/8ULlLZw0VjGtqcHjcpOTQB5IAxBwDjvXtP7LfhTTdf8AGj3mrtE0dgvmxxOwGXHIJz2rsfgL/wAK4/4V1ff8JG1iNQIbzxc/exg9PbOK+dNVlii1e9OlSSJamVhGQcZXPHSgD6H/AGsPiHZalBbeFdGnjnhjcSXTJ0DL90D9a+fvDniTV/DV09xod/PZTOpRniOCQe1ZLEscsST6mnQRPPNHFCpeSRgqqOpJOAKAH3VxNeXMk9zI8s8jFndjksT3qIqVOGBH1FfX/wAG/gJpemadBqni6Fb2/nQOtq4+SEH1/wBqvM/2sLPRdN8Y2Npo9rFBKtqrS+WMDqeD70AfQ37PNvbw/CDw+bXZ+8jZpGHG47j1rwf9qP4lR69qA8M6PMJNOtH3TyKciSQdMfTkVwPhb4teIPDXgm98N6eyCC4YlZyx3xAjBC9sd64mz06/1Vp5bW3luSuXkZRnHfJoA2/hp4Rn8b+LrTRbZ/LMuXdvRBjdj3xX2N4Y+B3grw/NBOtkb2eHBD3WD8w78V8v/s769B4a+KdhPfnZFIGtzn+82AP1r66+M+uXHhr4a63qlk+y5hRURgem47f60AX/ABt410LwVpEl3q13ChVf3VurDdIfQYr4g+KvxF1T4ga2bm9Yx2cRIt7dT8qLn+dclfX17qt2ZbyeW5nkbq7Ekk17J4C/Z48QeILJL3Vpo9Mt3GVjf/WH8KAPH9C0bUNe1KOw0e0ku7yT7sUY5NdVf/D3x54WxeXGi6jZbORKuOPyNbmhXM3wU+Mbxagq3S2h8mZl7xuAcj3xX2roGtad4j0qHUdKniurSUAg8HB9DnuKAPjPwL8dfFvhi7jj1W4k1KxBAeK6yWVf9k9q+u/DOuaN4/8ACa3lsiXGn3S7JYJOdp7q3vWb4v8Ahj4U8VLI2o6XCt0/S4jXDL+HSq/gvwtpHwj8I6mF1G4m01ZGu5HnUDy+MYGO3FAHy5+0B8L/APhA9aW700s+jXbZjz1jY5+Q/lXdfDb4JeH/ABt8JLLUormaHW5/MzICCqsGIAI9K8z+NfxPu/iFrf7tWg0i3OIIc/e/2m9+a7n9lf4iw6HqM3hvVpdtreuDbux+5J0Cj65oA47QPEHiz4JeMZ7OeORYg22a2kB8uZOzD3xyK99m+PXg3WvBN+b0mG7lt3j+xScszFSBjjHU12Xxg+Hdj8QfDj20irHqcILWtwAMhvQn0PFfB/iLRrzw/rN1pmpR+XdW7lGHY47j2oApW0ssFwktszpKh3KyHkGun8R/EHxP4j0eDS9a1W4urSE5VHbqfevTv2SNB0bXPE+tLrNrFdNDaK0SSDgEtgkfhXY/HP4D2r2Nzrvg2IQSwqXlsVHysPVfegDxj4E+ENL8b+O00nXLiSC18h5QYyAWYYwvP1ru/jj8CU8H6U+t+G557jTo2AlimwXTPfjtXiGj6ne6Fq8F9YSvBeWz7lYHBBFeyeMf2htV8TeCJNDn0u2huJ1CS3CsSSB1P1NAGL+zHrun6D8ULWbVp47a3eJ186Top2nH510fx28WWXjf4r6VpumTJLp1rJHGZV/icHnHtXgYJByDg1PYXcllfQXURPmQyLIDnuDmgD78+OOrW/h34TauEKxNLbGC3Xp82M4FfDfgjwvqHjPxLbaRpiFpp2y74yI1zyx9hXW/F34u6r8R4LG2u7aKztLX5vKiYkO+Mbjn2r3P9kDwklh4cu/ENxGpuLwhYHxyI+Qw/MUAcB8dfhBo3gHwJpl5YzTSagJBHOzkYcnrj2FcL8Mvi94k8BMsNnOLrTc82k+Sg9Svoa9e/bQ1sD+w9GibJIeWUemCMfzrwH4eeDdR8c+JINJ0tQHfmSRvuovcmgD6Ztf2ptBOn77nRL0XYXlFddpPt7V4V8V/i7r3xCnEEzC10tW/d2sXAb0LeprI+K/gK8+Hnib+yryZJ1eITRyp0KnPH14rR+AOhWviD4oaPa3+1rdZN7Rt/HgHigD6A/Zv+Ddro+m2vibxJbeZqsv7y2gkHEA7Ej+93q3+1D8T4dA0KTw1pMytqt4MTlT/AKqPuD7mvSfi14vi8B+Br3U1A85U8m1Xt5mPlH0r4k8HeD/EvxX8T3ElsGlZ3L3F1KTsjyc4z/KgDmvBt09n4s0a4jYqUvIjkem8Zr9N7aZbiCOVPuuoYV88aV+zBoNraWzTaxeNfRukrSKi4yCCQPavoSwgS1s4YI23JGoUH1AoAnooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAr86fijeG2+NfiC8DY8vVnfPsHr9Fq/Nr4vKV+KHihW+8L+XP8A31QB9429voXxH8BWcl7bR3em31uCAw5XjBweo5FeKaR+z/rPhP4j6Zq/hjUUfSopw83mybHEeeVx3rmv2WvirFo06+E9dl22lzJ/oszHiNj/AAn0HvX1td3MVnaS3Vw2IIkLsR6UAeZftO+T/wAKf1Q3BAQyx7c+ueK+BxwcivVPH3xA1D4g/ECO01a5ZNDa/SHyEOF8sOBk++Oa+m9a+BPgfXNHgjgtJbdxEPJuI5COMcEjvQB4P8AvjVe+Gb+20PxBO1xocrbFd+WgJ6Ee1fZE6R31hIkcuY5oyBIh9R2NfHXiT9mfxbY6jjQZbbULPI2zPIImH/ASa+q/A+n3Ph3wNpljrM6G5soCLiUtxnJOc0Afnp410i50LxXqmm3wIuIJ2DZ9zkfoRXp/7PPwr074jWuuy6ncSRG0CRx7P7zg4P4YrifjJr9v4n+JWuarZDFvNNhOOoUBc/pXrn7GeqGHxDq+l54uYxLj/d//AF0AeJeMtA1DwT4svNLneSG5t3OyRWKlkP3WyPUVz0kjyyM8js7sclmOST9a+lP2ztFit9T0XWFUCa8DxO3qEAxXzppM8FrqVtPdwfaII3DPFu27wD0zQBLLpWpW+mpfyWdzHYTHas5QiN/YHoam8LaBf+J9ctdK0qIyXNw4Qei+5PYV6h8XPjFaeMvCljoOkaHHplpA4kbDbu2MAY/WtD9ku80iw8ZX1zqt1BbTLbMI2mYKuPYnvQBJ8XPgdZ+Avh2mtrqctxfLNHFJGVAXLdcH2rmP2bvDkXiH4n6et3GHtLcNM2ezAZX9a639pz4pWfimSDQNBkMmn27755SOJHB4x9Oa88+Dnj4fD3xR/aclmbyBo2R4g20njjmgD7m8b+KNP8I+HrrV9VlEcUY+RR1duwA784r89/GfiG78VeJb7WL9iZrmQvjPCj0FdN8WfihqvxE1FHulFtYRE+TbKchfqe5rz6gDpPh/4UvPGfie00ixVv3jAyuB/q0zgsfpmvvjwd4Q0fwnokGmabZQBI1Ad2QMXbuST75rxn9kDwylp4f1HX5kH2m5fyoW/wCmeOR+Yr3vVtSs9H06e/1OdYLSBSzu38h6n2oA+Of2pfD1poHxBju9LjW2S7jEmyPgK4xkj05NcV4g+JvirxD4ag0HVNSeewiwAm0AsB0BPU1d+OXjiPx144uL60UrYRARQA9SBxu/GmfA/wAHnxn4+sbOUH7HCwmnYdgOR+ZGKAPZv2avhLAtpB4r8R2wklch7KGToo/vkf0Ne8+KPFmieFord9dvorRZmCRhiB/kVsRRw2tqkcYWK3hUKB0VQK+Qf2uPEVlrHi/TLTTrlZ0srdo5dhyu/d6/SgD2P41fDCw+I2jLrGiNE2rJFuhmRvlnHXBNfLfhvxZ4s+GOuTQWs09nNG22a2lHysPofX1Fe+fsmeOFvdHn8M6hcf6TbtutAx5KclufrXoHxc+FGlfEGyDnFpq8Q/dXKj73sw7/ANKAMT4X/HfQvFfkWOsFdM1Z+MNxEx/3j3Nep+INItdf0S70u/TfaXUZRxnqK/Pbxr4R1jwVrb2OrQtFKhykq/db0INfTv7Kvj6/8S6fe6BqrmabTohLFO3UoTjafU570AfOHxU8EXngPxXPpl0C0By9vKf+WkfrWcnhTXU8Pw+IILKc6cWO24iBO0g9SR06V9D/ALZBs/sWj7iv9obhgd/L5/TNXv2S9dttX8I6l4a1BUl+zvujhcZDIcljj6mgBvwE+OMF/bwaB4vuVhvI1CW945wsgHQMegPTHrTP2tvCFjdaLaeKrXykuUASR1/5bKSAuPXGa4X4/wDwek8JTya/4eDvpEsmXjBy0DE/yzn6V45e67qt7Yx2d5qFzNbR/djkkLAUAaHgHxfqPgnxFb6vpT4kjPzxk/LIvoa+o/Df7THhi7s/+KitbqzuCuCkEXmL09Sa8a074B+KNS8FWviCxMEn2iETpb7gGKEZ/P2ryW9tJ7G7ltbuJ4Z4mKujjBU0Aes+APB+j/FP4salb2kr2WkkPdKoHzMARxj8a7j4y/APTfD/AITn1rw5cSmS1x5kDnhl7nPtXgfgzxNqHhHX7fVtJk2XEXGD0Ze4Ney/ET9oiXxV4Jl0e30cWl3cgCafzdwx3wMd/wBKAPn6vYPgF8JT8Qbye71J3g0iAFd6/wDLR/7v4ZBrx+vqH9kj4gWdrB/whd1EUubid57eYc72IGVPpwDQB4X8UPCEngnxnfaOzNJDE37mRurp619vfBaKDS/hDoAkZY4IbdndycADJJJrw79s7RFTUNF1tBjen2ZsdyMtmuIl+N18PhFD4NtbTyrgIYJLzfndGScgDsenNAHL/GvxUfF/xE1TUVYmFX8mMdsJ8uR9cV9S/sueB08NeCE1W5h26nqYDMSP+Wecpj8DXxx4P046x4q0mwILLcXUcb/7pYA/pX6HeIdXsPAvgyS7u5Alrp1uI4/9plGFH4kCgD5Y/bG1GC48d2VnEVaWC1UuR2znivEfDusXnh/WrTVNNlMV3bPvRh61b8beIbjxV4o1DWbrIkupS4TOdgJ4FS3PgzW7XwlF4luLQx6VLN5KSMcFmxnIHpjvQB9r+FtY8KfG7wRbw6tDHcvEVe4szIVZJAOvBziu70zTtF8IaKyWUNvp2nQLl2GAAB6nv+NfnL4T8T6t4U1eHUdEumguI23DurexHcV2vxF+NPijxxpken380cFoMF0gXbvPuR29qAOs+O3xyvvEl9caP4XuXttEQ7WljO1pyOpz2H0619Hfs7W97B8K9KbUZJZJZgZVaRiTtOMcmvhLwRo0viDxXpemwpvM9wgYf7ORu/TNfpbpGnw6VpdrYWwxBbxiNB7CgC3RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABX50/HK1a3+LviZZPlWS/kcH2Jr9Fq+M/2wPB9xp/iuDxDBEfsF6ojdhziQcnNAHM+JfgxqNh8PdL8X6DP9ttpIPOuEjzuj5+8Pb6VseHvj5eR/DfVPDmvxy3N69uYLW6zyoxj5u5r1r9lDxTDr/w+l0C9KNLp5MSxNzuiIzn8ya+fP2g/AI8C+NnjtgRpt9unts9QM8j8zQB5hubfuyd2c575r67/AGdvjTaajp0HhzxTcrBfwKEtp34WRAMBSexAHevmrwP4G1zxrPcRaDatO0C7nx9OB+lZGuaNqOgai9lq1rLaXcZ5SQYIoA/TVWV0DxsHQ9GU5B+hrxf9qG68UL4PW08NWsz2kuTezRcnZj7uOtfP3wy+OfiPwhNBbXkp1HSlIVoZuWRf9k9q+0PCXiGw8W+HLfVtO+a1uoydjclevBoA+CfhF4LHjvxpDo01x9nTY0sjHqQpGR9ea+yfhl8JdB+HtxPeac00l267WllfIVe/0r5Q0HWrbwR8cjdxMFsIr0pKc9Iyfmr67+JGieI/GGi29t4V1qz0uwuk3XErgmR1boFI4AweaAPmr9qzx3ZeJ/EdrpGlyie20stmVehdgNw/AivCK+vNN/Zd0MKDq2s30sp6mAqMn8RXi/xb+G+neEviLYeHtIv3nhuVjYmYjdHuOOccUAeV0V9AfHrwN4K8D+D9Ms9KuPN1/eN5Dgsy7eScds14TplnJqGoW9nDjzJ3Ea59TQB1nw5+GviDx5cuukWxFtH/AKy4fhF/x/Cs/wCIng+98DeJ5tE1N0e4jRZNydCGGRX3n4U0zSvAvga1hTbb2NrbiaZicc7QWP518K/FfxS3jHx1qWrnBjd/LiI7xrwp/KgCb4Z/DnWviBqUlvpMYS3hGZrlx8ien1/CmfE/4f6l8PtbTT9TeObzIxJHLHwGB9vwr6//AGfdDg8O/CyykRebqP7bI3c5UEj9K+T/AI4eNZPG3jq7uxgWdv8A6PbAf3Aep96APpL9lTX7PUfh6ulwuq3li5V484ZgcnIHfrXmX7WfjS7uvEg8LwTMlpZ4aZFPDsQCM/TJrw7w7r+qeHL/AO26Jey2dzjaXjOCR6V6H8LPAWr/ABb8VXF/rVzObNSGu7xvvMeyg+vFAHk9fV37Heixw6Rq2sbQZLjEAJ7bWzXnf7QPwktfAS2V/oUs82mz5RxMQWjYepHrT/gh8ZbX4f6BfabqFhPdBiZIDEwGHPrntQB6N+1X8QLrR4LXw3pNy0E9xH5tw8Zwdh6DI6civk52Z2LOxZjySTkmt7xv4lvfGPii81e9/wBbcvlY16IOyivQ/APwA8TeK9PN5cSw6TCw3RfakYmQY6jFAHlWi6reaLqUF/ps7wXULBkdTj8/avrr4UfHvSfEFvFYeJ5FsNTUBRKV+SY/h0/GvlBPD91J4qXQYir3bXP2VSOhbdgGu88VfAvxhoFkboWn22NBmTyFOU+uaAPsPxb4X0Px1ob2mqRQ3du6/u54yGaM9irDpXypr2g+LPgP4pOqaKyzWE42LOU3Iw67WFcl4F+KPijwPdRx2ty8lpEcNZzjKe/HY19aeA/GOgfF7wrc21xbKX27bqzkwSAf4hjtQB8V+NvF2r+NNafVNdnEtwwwoUYVB6KOwq98LfF9x4J8Y2eqwuVh3CO4AGd0RI3D9K2vjZ8NrjwB4iZIS0uk3B328pHQf3T78GpfD3wc13xD4ETxNo80F0rFh9jQHzRtJH07UAfbsbab4p8PBiEudMv4eRwQVYdPqM18N/HDwBL4D8XSwwof7LuSZLV+wB/h+o4rrfgX8WrnwJf/APCP+JVk/sguUw/DWzZ5P0z1r0v9qHUtA1n4bWd5b3cFzKX3WbowJOSN36UAdt+z14otvEXw10uGGRTc6dEttKn8QK98envXm37WXgG3ksI/FenQKlyp2XQQY3D++fevAPhz461bwFrq6jpDghsLNC/KSr6EV6v8Uvj7Z+MfAz6NaaTcW13ONs8kjKUx3245oA8BggmuJNlvFJK+M7UUsfyFRsCrFWBDA4IPavbP2TLjTYviTKmpeUGktHWJpDxuyOPrVH9pzQtH0T4hy/2KYk89BJLBGchGI6/j1oA8gr3z9kjwlLqXjRtfmjZbXTlLRSHo0h4I/I14IBkgDqa+5/hlJpXwz+B9hqGoSJEksAvJATy0jDp+eKAOA/bQ1WH7HoWkbh9oDm5x324K18rV1HxF8X33jnxVc6te5BkO2KIdI17KKvD4X+Lf+EVm8QvpE8emRJ5pkYY+QdW+lAGV4C12Pw14s0/VZ4TPFbyq7IDgkAg/0rsPjR8XNS+It95KBrTRom3RW2eSfViOteXU+FBJNGjMFVmALHtz1oA9b/Z7+F0njvxAt7qMbDQrNw0x6eaf7oP5Zr2v9rt7bS/hjpNhaxJFE12Io4kGAqhCeBXonwjsdI8M/DHSEtLi3S2NutxNLvA3MRy1fL/7T3xEs/GXiO3sdGk83TbAFTIOkkmT8w9sUAeJU+WN4m2yoyNjOGGDX0D+zf8ABpfE7R+I/EkR/smNswQMP+Pgjuf9muL/AGjrvTrn4o36aRHEtrAiRAx9CQMGgDs/2OvDQ1Hxvc61IuU06Ihc9CzgivtGvGP2U/Dg0X4Y291NHtu72R5GPqmfl/SvZ6ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACuK+M2hReIfhvrdjKgZjAXRsZKkEHI/Ku1qO5hW4tpYZOUkQo30IxQB+cvwq8a3HgDxrb6lEXNsH8u5jXrJHnkV63+1R458NeLNI0IaFew3tzjzGMRz5Q7qfQ/wCFeXfG/wAEyeBvHt7YqrCxmYzWrN/Eh/8Ar5rj9C0e/wBd1GOx0m1lubp+Qkaljj147UAep/sweMYvDPxBhtr6QR2V/wDumP8AtnhP1NfVnxX+G+k/EHRHtruNIr+ME290q/Mp9Poa+BtY0bWPDOopHqlndafdo25PNQocg9Rmvuf4AeOD428B28t1gahZ4glGckhQAGP1oA+HPF/hzUPCmv3WkatF5dzAxGezjsw9jX0H8NvjXoPhD4Px2CiR9bgVo44QBhmOcMfbmt79sDwjbXWgWniS3jC3sDiOZwPvR9v1NfOfwn8PWfinx7pOkajK0dtczBG29T7UAcve3D3d5PcSkl5XLkn1JzX1Z+y38UIrzTx4U124Iu4staSyN99SeVJ9egFc/wDGr9n6LQtJm1nwi0ssMAzNasCSFHUjqTXzrY3dzp19FdWkrwXMLh0deCrA0Afol8TvEr+D/A+p6zHE0ssChUVRkhm4B/A1+e3iDWr7X9ZudU1Kd5budy7OT39q+wPg/wDE/S/if4dl8NeJxGmqmHy5FcjE46bl9W7n0r52+Nnw0u/h94jdI45JNInO62nwcAf3SfUUAecySyStuldnb1Y5qXT7uWwvoLq3OJYXDqfcVLo2l3ms6nb6fpsDz3c7bY40GSTV7xN4W1nwzdCDW9PuLRj91pEIVvoT1oA7j4lfGnXvHGjWulSRpYWUSqJEgY/viBwWryyvRvgn8N3+IniGS2luGtrC3XfNKoyc9gPrW9+0J8M9H+H0ulHR72WZroN5kMgGUx3/ABoA+jf2ftXTxB8KLJQ2RbobFvbCgf1r5B+Lnhafwj461LTpYysJkMkDf34yeD/OvoP9jm9aTwzq1kSdkUvmge5wKp/tk2WmjStGvfkGrmbyiP4vK2kj8M0AfOngfwvfeL/EdrpOmxlpJWG98cRrnlj7Cv0B8HeHLLwr4ds9I06NUigQBio+83c/nmvkH9nXx74d8CanqFz4ggnM08eyKaFAxUccc13fxB/aVSW0ktfBlnJG7gj7VcDDp9ByDQBN+1z4wtGtLTwxaSLJc7hNcgH7nda+XkRpHVEBZmOAB3NdHc6J4n123utfuLDULqFmLy3bRsVOeSc9MV137OOg6ZrvxItE1iSPy4B5qQOf9a3OB+HWgD1n9n34MRWdtb+I/FVusl0+HtrVx/qx2Y+9el/Gf4g2ngLwtI6SL/atwuy0hUjI/wBojsKi+LHxV0f4f6c0atHdasy7YbSNh8p7FsdBXxX4t8Tat4w1uTUNXne4uZCdq9lB7AUAZ8Gp3MGsrqkT7btZ/tAb0fdnP5193/Cj4i6T460K3NvcoNTijVbi3kI3ZA5bHoTmvmnwV8BNd17w7Pq2oyDTU8svDFIMO3HGQegNeVW95f6Bqsv2G6lt7iCQoXicjJBx+NAH2D8bfg3p/i2wuNU0SGK11yNS52jCz46g18z/AA38cav8L/Et1Pb2qtMy+RcW83GQDnBro9K/aC8b2Vr5M15HdYXarSIAR+Q5rzi5fVPFevXFysEt5qN05d1hjyWJ9AKAOm+KXxO1j4h3cT6gqW1rF9y2iOVB9eee9egfsvfEiDw5qc3h/WJdljfODDIx4SToF+hzXmK/DXxi0PmDw5qeOuPs7Z/LFc9qemajot0ItRtLmyuByFlQow9+aAPsH4/fCK18WaZLregQxR63Cu9tgwLlQP546V8b3cdxbzSW10JEkiYo0b9VI6jFfR3wV+Pv2KG30XxmWkhQBIbwYJHoG7Y6c1wX7R0Hh9vHP9oeGL21uLe9iWWVbdwwWQ9ScdCfSgDR8Nfs9+IvEPhKy1uxvtOC3cQmjidyG2noDx1rzjxl4O1zwfqBtNesZbZj9xyPlkHqp9K9b+Cnx3HhHSo9F8R28lxp0X+qmi5kQf3cdMVp/HP4yeEvGnhL+ztKsbma8Jys1zGFMX0IOfwoA+c4XkjkDwsyuvIZTgiiaWSaQvM7u56sxya+of2PND0e/wBG1+8vLOC6vY5kiHnIG2qVyQAf515H8f8AQLDQPirqenaJHttzscRrzhmGSB+JoA4bTdH1HUoZ5tPs57iO3G6Vo1yEHqas6x4n1rWrG0s9U1K4ubW0QRwROfljUdAK+zvh/wCGtN8C/BaZ54YzLc2bzTO6gtudThefQ18Mk5JNAHvf7KngC38R65c67qsIlsdPYLGrDIM3B5/CvoD9oPXrfw/8KNYWTarXsRs4lHGCw4wPwrkf2SZ7KD4X38vmxxLFdFrh2OAp29T+FeH/ALRPxLPjjxKbPTmYaNYsUi5/1p/vEfnQB5EAWIABJPQCtDVNF1LSo4JNSsp7ZJ13RmRcbx7V7l+y/wDC2HxHeN4k12Avp1q4FvE4+WVwevvgiuo/bNv7GLTdB0yCKEXSu7MFUAouBjpQB8u/b7v7OIPtM3kjom84/Kun+FPhGTxt42sNJGRA77p3H8CetcfXvn7HF1Z2/wAQ9Qju3jSaayKQbj1fcDge+KAPYvjt8RLf4YeFLXw/oEaxanPb7INnAhToWHvmvjnw/p9x4k8UWdmS8k97cBXbqSWPJP517P8Atk2rwfEDT5Hk3Ca1LBc/d+bGKtfse+DTqnim78Q3ce61sE8tFdeGduhH0xQB9d+HtOTR9B0/TowAtrAkPHT5VA/pWhRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAeXfH74cx+PvCEotY1/te0UyWzYwWP90n0r4u+HHiOfwD4+tNQuEdPs0hjuEx82zPzD9K/SKvk/wDar+E6wM3i7w/bnYxxewoOAf74HYetAHuetaF4Y+KPhWGa6ghu7S6jDxTr9+M9cbhzweorI+EHwtg+Gx1QW2oteJelcKybfLAOfXmvHP2PPGdx/aFz4TuXaSGRTNbgniPaCWA+ter/ALRHj9/A3gzFi6rql+TFBnnaP4j+RoA80/ar+Jem3GmnwlpMouLneDdOpyqj+7n1yK+Z/D2q3Gh65ZanZttuLWUSofcVTuZ5bmeSe4keWaRizu5yWJ7k16n4O+ENz4y+HNzr+gXIm1C2mMclpjlsLng54NAH2d4J8RWPi/wpZatZMssFzEA6Nzg9CGH1zXx/+0v8PU8H+K11DTItmk6jl0VRxG46g/U0fAT4m3Xw+8RnSNb3ro1xJsnRwcwP03fQele//tHQ6Zr3winu0nhliWRJYZkYHsSBn39KAPifR5L6HUYJNJM4vUYNH5Gd+fbHNexav8abzVPh/feFfGejG71DbsiuZP3bxnI6rjg8da0P2QNBt9S8X6lqF1bJMLCFShdcgFiRWb+1vZWln8UI/scSRGWyjkkCDGWJPJ96AOU+AeqQaT8V9AuLtlSAz7Wdv4cg819a/tDQ6HcfDLVZde8rzBCzWTv97zMZXb/hXwh5V1aeRcGOaHdh4pCpUN7g962vE/jPXvE8NtDrWozXENuoVEZjtGO+PWgC78OPH2seAtUkvNGkAMqFXRgCG9+azfGPinVPF2tS6nrVw01w/A7BR2AFZenWU+o30FnZxmS4ncJGg7k9K9f8c/AXVfCfgT/hIbjUYpZYwhntBHgx7u27ODigDoP2ffG2i+A/Ams6nqlwjXMkmyK2U5dzx264968e+IXjHUPG3iO41TUnbDnEUWciNewFc3FG80ixxIzu3AVRkmruraNqOkGEanaTWxlQOgkUjIoAz6+ifgD8E31ZoPEPiuErYfetrRgQ0p/vMOw9j1zXFfs9+AV8b+MAb2MtplkPOlBGQ5BHyfiK+ifjT8VtP8BaMdM0Z4pdYZPKiiQ5EAAxk49OOKAOR/aa8e2eh6Cng3w+UimkAacQYURJ2GB6ivlW2uJrWZZraaSGVfuvGxVh9CK2YLTXfGuuyyQRXGoahcOWdgCeSfXsKqeIdC1Hw7qT2GsWr212oBMbdcHoaAKk011qFyGnlmubhzgF2LsT9TzX1Z8AfgvHpcMOveKrZZb5wGt7Zxnyxjqw9a4X9l34fQeINWk8Q6rD5tlYSbYVP3WlAzgjuMGvX/2jPiHP4K8Ow2eluE1W/GEbukfdh+WKAKvx5+LeneGtGu9E0mdbjWLiIxERHKwqeOo6EeleHfs3aNoXibx1LZeJIPtMksTSRBmIBI5JNeT3dzNeXMlxdSvLNIdzO5ySa0vCevXXhrX7TVbFyssDgnBxuXuPxFAH038Uf2etMu7Ce+8Gj7JdxKXNs7FllxycEnivHfgd4ztfhx42uJNfs28mVPImYx7ngwc5A65r6y8NfErw7rvhT+2l1G2i2xF5oXcKyMByuDyfwr4a8dapDrfi/VtStRiC5naRBjHFAH0l8Rv2lLKKxe38EefLduOLqdSojP8AunrXW6Polp8Y/g/YXfiCOF9VuUk2XYQKySAkA8dvaviKvq39kbxlFcaVdeF7uVVngJltVJxlOrfqaAPmfxRod34c1680rUI2Se3cqcjGR2P4jmrHgrwzfeL/ABJaaLpSqbq5JC7jgAAZJ/KvoH9r7wjGq2Pie1iw7Hyrt8dTwErwDwL4mu/CHimx1qwwZrZ87T0ZTwR+WaAPTvHf7PmueGfD02rW93Ffx267po1Xayj255rxMjBwetfYfiv49+EtQ+Ht41pM39r3EO0WLI3yseCN2MV8/wDwp+F+qfEi/uTaSCzsoifMunTcqt124yOaAMXwH491/wAC3U9x4euxA8ybXVlDKffB71d8CW+o+NvifprXTvdXU92s8zNzlQwLfhiuc8TaNP4f1280u6IM1s5Qkd/evo/9kHwjZMl14nlkWS8j3QxIOsYPBz9aAPQ/2ndSGjfCa8trU+W80kcceOyhuf0r5f8Aht8IvEnj21e80uKOOxVinnSttBYdhnrXuX7Zt00Xhrw/bKTi4nkz+AFaknjSy+FXwI8PNaJG2p3lohii/vSEcsRQB8ueK9K1/wACave6BeXc1uwOJY4Jjsf6gHBqr4F8OXHizxXp+j2wbdcyhXcDOxc8sap+I9cv/EesXGp6tOZrudtzMen4elen/szeIvDnhbxfean4luhbBLV1iZkLDnHTHegD7DsodL8B+DEjcx22n6bBlznAZgPmx7nBr4G+KHi6fxr4yv8AV5mYxu+yFT2jHC8fSu9+PHxluPHE50vRi1voUTZ4JzMfU+3tXDfCTwyfFvj/AEnSgMo8nmOMdVX5iPyFAHuf7N/wWs77TovE3iy381ZsG0tnyBt/vn1z6V478Z7a00D4qa1F4eka2SG4YKIWK+WfQEV9qfETxNpvw78CT3KhI1gh8q0twcbjjAA+mc1+euq39zqup3F9eyGW5uHLux7k0Aa2j2WueOfEdnp8ctzf39wwjRpXZ9o9ST0FfoX8NfCNr4J8I2Wj2YH7td0jd2c9efrXl/7Lvwzi8M+G49f1SD/icX67kDrhoY+m38ete8UAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVFeW0F5ay211EksEqlXjcZDA9jUtFAHypqWk2v7P/AMQrnxEdLn1LRb1W+zvCVDW5bgoSe2Tx7V4X8VviBqHxC8RvqN6PJt0GyC3U8Ivr9fWv0K8T6Bp/ibRbnS9Xt1ntJ1IKnsexHuK+CvjL8K9U+HutMGjefSJmP2e5UZHXhT79KAPNa+nP2MvEKRXWs6BK+3zR9qTJ+83C4ry7x58MJ/Cfw98OeJJrgyPquN0X/PLK7hniuN8IeILzwv4istX05ys9tIHAzw2OxoA9j/a28IQ6P4vg1mxQJFqKb5gBgB84/kK8VbXdUbRxpLX9ydNDbxbFzsz64r7C8e6LB8d/hzp174WvLdL1HUutw23ZgHcpxnBzXi/jr4Aav4R8ISaxdahZzPCQJI43OMc9OPagD1H9nXUNB8E/CKXX9WvIYpJ5JPMGfmYKflXHXmvD9VnvvjF8X82cbbLuYJEGGfLhBJGfwzXnljBdX9zDZWiySyyuESJcnJJ44r7f+APwyj8C+Hlu9QiX+3LxQ0xI5iXsnsRzQBl/H34d2l38JYIdKhCy6FGGh2r8zoBjb+ua8T/Zo8LeG/EfiW8h8S+XLLEhMVtNja3HU5r7SuoUubaaCVQ8ciMpU9DkV+dfxF0eXwx471jTFZ0a3nIBBwcH5h/OgD1ZrLwr4R/aISKzuI00iEbskZWN/QYq3+0b8Y7XxJbzeGvDjGTT94Nxc9BIQc4X8a+eWdmbczEt6k802gD379kzQdIvdd1TV9XaBn0+IPEkpGFJPLc+1c1+0f43tvGXjk/2a++xsoxbo46SEE/MPzrymOWSPPluy567TjNN60Adl4G+Iut+CtN1Gz0Noo/to+eUg70OMZU54NUPDGi6t498XQ2MLvcX12+6SaQ52rnlj9K5uvrX9kfwvBaeH7vxBLGr3N0dkTkcooJDAfWgD1b4aeBdM8B6FFYafGhuGANxckDdI3fn09K+Qv2jPEEPiL4oahNbnKWyraE+pTg19I/H34l2/grw/JYWMqPrd4hRYwc+WhGCx9OvFfEc0rzSvLKxeRyWZj1JPegD1b4K/F+f4eJPZXVo15pUz+a0UZAcNjGQTx2FV/il4r1L4ueKYLnS9JnWG2iMUEWMsFznk9M113wL+CX/AAkdtDr3ihXj0xiGgt+hmHqf9mvfvFOu+F/hX4Y80WttbRAYgtY1BaQ47ZoA+Cr6zuLC6ktryJop4zhkbqK9R0r4EeLNU8PwaraJC4mTekOfmIxke1ef+MNfuPE/iS+1e8wJrmTdgDGB0A/LFfbPwA1c6x8LtMuC2WjLQn224FAHwxqmn3WlahPY6hC0F1A5SSNuqkV9XfBD4O+HbjwLZarrtrHfXOox+aokUERqeMD34rm/2wPDdtbz6X4ggjC3FyfIlK8ZCjg0fBn47aV4e8Gx6P4miuWkshttmhUEFOw5PXNAHmfx58FWngfxs1lprs1ncR+fGrYygJxj9K4fQNYvNB1e21LTpTHc27h1IJwcHOD7VufFDxjP438XXerSgpCx2wxn+BPSuSoA+m/Hnxh8OeOvg7d2N8xttbLRn7MwzvZT94HoB3r5korqPCvgLxN4pAfRNIurmHODKqfIv1NAHL17b8EPjRa/Dvw5qGl3ulTXfnymaN4WVcNtx82a8o8T+H9S8M6tLp2sWz291H1Vh1HqPasmgDU8Rarca/rl5qVwP3tw5kIHYV6l+zP4+Xwn4uGm377dN1IiNjn7snRfwzWD+z/ZW2o/Ee0s76FJ7WaNkkjcZDDjitr48/Ca78C6vLqekxySaDNJuSResDE/dPpz0oA9K/bWYHTPCgVgf3k5yPotfOXinxRfeJE0tL0gR6faR2cSqTgqufmPvzWxqfinxF4/stB8O3X+mS2Tslu/8ZDYGD7DFbHxD+C3ibwNoceq6k1rPakgP9ncsYv97IoA4rwv4X1nxTdy22gWEt7PEnmOqYGF9eayJo3hleKRSroxVgexHWvsb9j6ws4fAOo3kQRrqW5KyP3UbPu/Svlf4gzQXHjXWJLQqYGuG246def1oA56vX/2Y9Z0zQfiA+oavOsEMNtIwdh/sngVweleCPE2r6aNQ03RL25sjnE0ceV461gzwzWs7wzo8UqEqysMEGgD0T44/Ei4+IPidpImZNItiUtYs8Y/vkepq/8As4+AR438cxteIG0ywHnzhhkP2C/rmvKY0aR1RBlmIAHqa+//ANnPwQvg34fWxlj239+BcXAYcqxGNv6UAeoxIsUaRoMIgCgegFOoooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKp6rplnq1o1tqNvHcQHkq65FXKKAPEf2p9HgHwhdYIgkViymNR/COBXwzX6E/tIRCX4L+JcjJWFGH4SLX57UAdT4C8c614I1Vb3Rbl0HSSEn5JB3BFd58RvjhrvxE0OHQF0uCzWSRd32eRmaVugGD9a8ar67/AGavhJBpenR+JvEVqH1Cbm1ikGREv97HqeCDQBo/s+fBuPwpbxa/4gRZNZlTMUJAItwf/ZsdfSvdT1pTyaSgBO4r4t/a00pbH4lC7VcG+h80n1wcf0r7SNfK37aFsDq/h+5xytsyZ/4FQB4P4V8J6r4oF8dJiVxZwtPKWOAFH9awSCCQRgivqr9jvSEn8O+JpJkB+1NHEp/2cHIrwq28PjUPi1LowTEX9pvGy/7AkOR+VAEngz4WeKfF8H2jSrHbbdppjsVvoa6PU/2ffHFhbNMYLOcAZ2wzbm/LFfa+n2NvpdjDYWEaw2sChERRgACpwSOhNAH5qa1o+oaJeG11azmtLgc7JVwa9h+GHxuXwT4Cn0hLEzXyE/ZmJ+UZJJJr6h8eeB9E8a6ZJaaxaoZSD5dwow6N656n6V8M/EnwVqHgbxJPpmoRny8kwy/wyL6g/iKAMfxJrd74i1m51LU5mluJ2LEk5wPQe1P8I20N54n0uC5YLE9zGGJ9Nw4rQ8AeCtW8ca0un6NDuI+aSRjhY19Sa9Q8Xfs9694d0v8AtTSLtb6S2xK6KNrLjnI9aAPri3ghsbKOG3jEcEEXyoOgAXNfA/xe8Y3XjLxle3c0jG1jdo7eLPCLnpXd237QviG08KPo9xaI+oLGYReOcMvblfWvD3Yu7M3Vjk0Adn4D+GniPxurPotsggU486dtiZ+uK+qPA8Nj8F/hxDbeLtQhWcyvJtibduzjhfWt3TdQ0H4dfDGxneWGGyitFlVQQDM5UEgepya+LviJ4z1Lxr4gn1DUZmaPcRDFnComeMD1xQB6B+0F8V9P8fR2FjocE8dlasZDJMNrOSOmPSvFqKKACrukaZeavfR2em28lxcyHCogyTVWKNpZUjjUs7EAAdzX3R8E/hnY+B/D8M88Mcus3CiSWcjcVB5AX049KAOH+E/7Pdrpot9T8ZMtzdYDrZryiHqMn19q+g7S3gs4EhtIY4YlGAsahePwqTryetAoA+bP2xPDatZ6V4hhTM2/7PKQOiheD+dfLFffnx40n+2PhXrkKoGnjjEkXsQwJ/SvFP2W/h9puv6NrmraxbpOuWtI1cZA+XO4fjQB59+zh/yVPTs/3W/pX3NrGnWurWFzYahCk1rOpR0YZyDXxB8DIPs3xnggxt8uWRcfRq+6n++31oA+Jfih4A1T4R+LbbW9HzLpfmB4ZP7hzyjeg7Zo+Kfx31Lx34aTR/7MgsYnw07xyFi59Oe1fZHiHRbDxDpE+m6rAs9rMMEMM4PYivg34wfDy+8AeJZbaWNm02Vt1rP1DL2BPr7UAYvhrxpr/hmxvLTRNRltbe7G2ZE6NWVpUAv9YtoZ2OJpQGb6nmqNOR2jdXQlWU5BHagD9MPC1lBZeHNKtbWKNIktowAFGD8o5r4v/ak1HSb/AOJtyNISPdDGsc0kYABYDBHHcGrV9+0R4nl8HQaLapFbXCRCB7teWZAMcDscd68WmlknleWZ2eRyWZmOSSe5oA774GeEm8X/ABF0uzkQtZxSCW4YfwqASP1FfokqhVCgYAGBXzX+xf4ZFtoOqeIpFw91J9mTPonOR+dfStABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAcD8eo/O+EXiVPW3H/oS187fA74Z6T4++EGspcII9UW8b7PdDqhCcA+o9q+mPi5D9o+G+vx4zutj/MV45+xlNnwNrkJPK6gCB7eWKAPmfw5oZt/iNp+jatGV23ywyqw7bq/RW2RYrSCNPuJGqr9AOK+M/2l7D/hGfjFHq8C7TdbbsAccqQP6V9h6LcLdaJp86tuElvG2fcqKALlJS0lACGvm39sm33aZo9xj7p2Z/GvpI14h+1vYG5+GkM6LmSG7Uk+i4oAb+yHGE8CTOOrz8/hXkXhaJU/aZmUgEG8mbn8a9f/AGRZFfwDIg6pPz+deKW94LL9o55c4zfsn5sRQB9rN9402nyDDsPemGgBD1ryn9o/wcvinwDcXMEW7UNNBnjYDJ2DllA9+K9WNNdQylWAKkYIPegDxv8AZe8KnQfAR1C6gaG+1GQl1cYIQH5f0NexhQ+Ub7rjafoeDQAFUKoAAGAB2pU++v1oA/Pz4zWMenfFDxHbQKEhW7bYo7CuLr0z9oqIR/FfWSvV5N1cCNI1IjI0+8I/64t/hQA691nUr60htby+uJ7eEYjjdyVX6Cs+rj6Zfp9+yulx6xMP6VVZWRsMCp9CMUAIOTgV9B+Cf2fTrvgBdYu78wX9zEZoI9pwq4PX8q8K0GH7Trmnw4z5lxGuPqwr9IbKyjsbGKyiAEUcflgD0x/9egD87/BsSp410yKbot0qtn2NforB/wAe0OOnlr/KvhD4m6M/g/4u3cYQpbrdCWFugZCRyPxzX3bZHdYWjf3oUP8A46KAJ6UUlKKAKurW6Xek3tvIu5ZIHXH/AAE4rzn9nTSW0XwPd2kgxILly2Rj1r1DGcg9KitLSG0E32eNYxJl2A7nFAHxh8J4yPj4yjtPIf8Ax6vtl/vt9a+Kfg6fP+PcjKR/rZT/AOPV9rP99vrQAVyfxQ8F2njrwldaXdALMFL28uMmNwP69K6ylFAH5na9pN3oWsXWm6jGYrq3fY6nsaq2dtLeXUVvboXmlYKijuTX0n+2D4PWC6sPFFnEFSc/Z7jaOS/J3H8OK8X+D9r9t+J/hqEjKtex7vpmgDE8SeH9S8N6j9h1i2a3udivtPcMMjms23iae4ihjGXkYIo9ycV9U/tm6AhsdF1iJQpgLW7kD72Txn8q8B+Euitr/wAQ9EsVGf8ASFlI9Qp3H+VAH3n8HtEXQPhvoNiE2SrbI0vu5HJrsqRFVECoAqgYAHaloAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDG8Z2v23wpq1uP47Z/0Ga+cf2MLn/QtftM8+b5mPwAr6f1Jd+nXSf3onH6GvlH9kxW0/4jeKdLf5fLt2YA/74/xoAb+2lZK2paDe/wAS25i/Nia9x+C98dS+GmjXBOcoUz9OK8i/bMjB0zSJO4IH6mu9/ZflMvwY0gk5KzTr+T0Aeq0lLSGgBDXD/GvSxqvwu8Qx43PFavKg9WArtZ5VhiaSQ4RRkmoZoI9Q0+WCdQYp0KsPUGgD50/Yx1mL7PrekTyqtwJEkijJwSozurw7xJfBfjNdXSHAj1jqPaXmsfXl1Dwr4v1GK2lktLiKdwGjbB2ljj9KwvtMjXn2mRi8xk8wsepOc5oA/TG3nF1bxXC/dlUOPxp5rC8A3QvfAvh65ByZbGJj9StbpoAQ0lOrmfiPrr+G/BGr6nCdtxDA3kt6Pjg0AdHQv31+teNfs3/EW/8AG2j6ha67P5+p2bh/NbALqxOAAPTFezJ99frQB8O/tCf8lhvv+uq/zr7105VOnWpKj/VJ29hXwL8fZPM+Mepj+7cBf1r7703/AJB1r/1yT+QoAdcWsFxG0c8MciMMEMueK8K+O/wQ0XWfD1zqvh20jsdWtVMreXnbMo5II9cdK97ps0azRPHIMo4KsPUGgD80/hxbfaPHejwuMf6SpIPsc1+ib/e/KvhyLTxpX7Qk1ooCxx6o+wf7O44r7jf71AHz7+1j4N/tDRLbxPaxlriyxFMB2i5O4/ia9r8H3f2/wxplxnO6BR+QAq3q2n22raZc6ffRiS1uE2SIe4qPw9p8GlaRBZWknmQRZCt+PSgDRpaSloAR3WNd0jBV9SacWCo7E4ARjn8K8W+OfiSZfFvhHwzYytHNNdCacKfvR44B/GvWddm+y+HL2bOPLts5/wCA0AfG/wAAT53xuZ89Xmb/AMer7cb7x+tfEX7Nw834xhj/AHJm/wDHhX2633j9aAClFJTqAOM+MWhp4g+G+t2hTdMIGaH2bHWvjz9ny2L/ABc0dGHMU24/ga+9JI0mhkjkAZGQgg9+K+KPgJam3+OxhI/1ckv/AKFQB73+1nai5+E1wwGXjvYmB9vmzXhn7IWlm8+KaXuMrZQOT9WUgV9EftIJ5nwq1IekgP6GvJv2IbTOp+Jrpl4EUKKfxbNAH1pRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFACMoZSp6EYNfJ/gWRfDn7UniKwX5Eun8lB/eB2mvrGvkT4/Mvgn4+6B4jAPlSbLpyB1wxBH6UAa37Zz4sNIjz15x+Jrsv2UmJ+EVmCeBcTY/wC+zXmH7YWsW9/ceGRazRzJPaeeGjYMOp9PrXp37KKkfCO0J6G4mx/32aAPYjSGlNIaAOd+IV2bHwVqt0Dgxxqc/wDAgK27Nt1nbMP4okP5qK4X463n2f4e3VvnBvHWEf8AfQP9K7iwGNOsx6QRj/xwUAfF/wC1XoY0z4kyXka7Yb5N6jHGV4P868Xr6t/bN0rztN0LVAP+PfdCT67j/wDWr5SoA+9/gBfG/wDhdpTFt3kqIfphRXoleL/slXX2j4WSRscvFeyDHttGK9ooASvL/wBpNzH8JtRK55kQHH416ga84/aFtzc/CnVlUZ2Yc/gDQB85fsqao1l8TorVn2wXMLhvcjpX2mg/eKPcV+eXwr1JtK8faNOpwWuEi/76YCv0OmOxpD/dyfyoA+CfjZL5nxn18f3L7b+or9CNN/5B1r/1yT+Qr86PijN9p+LGuy5zv1An9RX6MaeNthbD0jUfoKAJ6KKKAPhX4pFdM/aLMi8bruNj9WavtCQfOfw/lXxb+0Yps/ju0jcYa3l/8ezX2Ppd4NQ021vFIImjDAigCv4k1NNG8P6hqUmAlrCZCTWP8LLt7/wJp11KxdpWkbJ9N5xXI/tN64NH+Ft3CGw+oP8AZdoPOCM/0roPgeSfhXoRbrsb/wBCoA7kU5eWGenekqprNytno2oXLttEVvI2fcKcUAfML6i3iv8Aatso2+aCxuGt8jptRW/rX0H8VLn7D8NPEtyDjybNjn8q+af2XkfXvixqerSDLxobg57bmx/Wvc/2jNRFj8KdYQnH2qMwj36UAfOn7Kaef8WgxGf9Elf9Vr7Z718Y/sfR7vipMx6Lp8381r7OFABTqQUtACjofof5V8hfCa3WP9pXUY0HyoZSP/Ha+vex+h/lXyV8HP337S2rt1wkx4/4DQB7f+0Q234WaoePvAc/Q1w/7F9kIvC2pXeOZpNufoTXX/tLyiL4Tagx7yqv5g1T/ZEtRD8IrecjDTXM35BuKAPbKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK+b/ANtHQxc+FtJ1aJC01vP5TkdkKk/zr6Qrzr9oPTU1H4SeIcrukgtmlT6igD8+bq+uruOBLqeSVIF2RB2zsXOcCvuH9luLy/gzpRIwXnnP/j9fCtfoN8CbA6d8LNGt2GDh3x9TmgDvTSUtJQB41+0leEWXhPT4j+8udVUMv+ztNevWoxaW49IkH/jorwD433Zvvjf4H0ZDuUOspHo2DX0Gg2xovooH6UAeO/tX2guPhLLIFzJFeREH25zXxFX3L+1HcrbfCK6LY+e6iQD65r4aoA+tv2Obrf4Z1a1z9ybfj64FfQxr5e/Yuud134kts/cgjk/NsV9Q0AJXL/E+y+3/AA78RQYyxspSo99vFdRUN5arfWc9rJ9ydDG30NAH5u+H2a28SaazDDRXcRPsQ4r9GZLnzdDkus/etWlz/wABJr88/F1o2jeONTgZSv2e9cr9A5xX3PFq0cXwgh1JyNj6SDn3aMigD4Y1+5+3+N7qfOfNvM/+PV+l1l/x5wf9c1/lX5haOrXGvWa5yz3C8/8AAq/T604tYR/sL/KgCWiiigD4c/a/heH4vNMowr2UJB9xmvof4C64mvfDDSHVw8tqn2eUg/xDn+teQ/tqaYYtY0jUtvyzL5Wf90GvLvhR8WdV+Hdtf21nBFdW10MiKUnCP03DHegDtf2tPFQ1Hxdb6HayhoLFB5yg5/e5P9DX0b8IYDb/AA40WNhgiMn8zXwFqN9daxq817eSNNdXMu92JySSa/Rzw3aCx8P6dbr0WBD+ag0AaNcD8edVOj/CnW7mNsSlVjUeu5sH+dd+K8G/a+1P7H4L06zzj7ZKwx67SDQBzX7GWn7LjXdQx/rIRDn6Nmuh/bD1EQ+C9MsUbEktzvYeq4qb9j63A8A31xj5muymfwrzr9sLV/tXjXT7CJv3dta4df8Ab3H+lADf2OIs/EG+lx0snX+VfYVfJn7GiD/hJdVk/iEBFfWdACilFIKUUAL2b/dP8q+Q/wBmxzffHvWbk8gQXJz/AMCAH8q+s9Sn+y6ddT/8842P6V8r/sj2rS/EHXr3HyqkiZ+pzQB6l+1jL5fwcnI/ivYV/PdXTfs62P8AZ/wm0aLGN6mT/vrmuM/a9l2/CpIu7XsR/LNer/DO1Fp8PvDsQ6/YYWP1KA0AdNRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXOfEaBbnwLrkL/AHXtXB/KujrH8Yrv8Laovrbt/KgD80LeykvdXSzgGZJZvLUe5OK/Sbw5Zrp/h/TbVBtEdugI99ozXwj8F9JOr/F/SYcZjhvPOceqq3NfoAQASAMAcAUANoHUZ6Upqjrd7Hp2jX15O22OGF2J99px+tAHzLb3TeIv2s0b78Ol3RVT2wBj+tfUrfeP1r5Q/ZVil174l6/r14vPks4P+2WH9K+rwCTjvQB83ftla2INH0bRlbJuWM7AdtpwM/nXCfDD4X2XiD4MeJdfu7ffdgE2k3ePyzlsfUVzP7RPigeJviRevBIGtLb9zEB2I+9+or6h+F2k/wBlfs6SwBceZp9xPj/eQmgDxf8AYzn8vxXr0Wf9baxj8mJr60r43/ZFn8v4iTxZ/wBbBj8smvsk0AJQDgg0UGgD4g/ai0caX8U7t4kxBcxRyA+rEfN+tbkPxWtJf2f7nw5PKy60u2GJR08oMP6V0P7ZumhLjw9qCLzIsiSH0xjFfMtAG/4Dh+0eMtIixndcrX6ZQDEEY/2R/Kvzl+Clr9t+Kvhq3PPmXYH6E1+jijCgegxQAtFFFAHgP7Y+kG++H1pfhc/YLjcT6bsLXxTX6R/F/QR4l+HOt6af44DIPqnzf0r83SCCQRgigDoPh7p/9q+N9Fsdu7z7lEx61+jEKeXBEn9xFX8hivhz9mfR/wC1firp0mM/YQbr8sD+tfSXiv4uQaD8VrDwk1oJI5yscs27BR2I28emDQB6pXy9+2dcM0/h+3JO2PzGA+oFfUZGGIznHevmL9s+yZV8O3oBKuZEJ9MAUAdB+yVqUFt8MtUEsqL9nuXnbJ6ALXzR8UfEH/CUePNY1VWYxTzkxg9lHHH5Vn6P4n1bR9L1DTtPumhtb9BHOgH3gDng9qxjyeaAPoT9ja6VfG2o2hI3PatIB9Mf419eV8Dfs++IP+Ee+J+mTMQqXObVmPQBsf4V99cEkqQVPQjuKAClFJS0AY/jIkeEtXK53fZ2xj6V4r+x/o8tvoev6nPGVF1cjyWP8SjcD+tey+PdVGheCtZ1RgpFrbtJhuhrA+At2L/4TaDdFER5VkZggA58xqAOJ/a5BbwRpqfwtexg/ma9t8Irs8K6Oo/hs4R/44K8X/a1BHgGwkxwt/Fk/ia9p8Jnd4W0dvWziP8A44KANWiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACsPxzL5Pg/V5M4227n9K3K5P4sTi2+G/iKYnASzc0AfKX7Jdj9q+K97dOuY4bWbB/2iwxX2UeufWvlv9jS08y81y/x91jHn6gGvqQ9KAENeS/tOa/8A2L8L7y2V9kuo/uUI68EE4/CvWq8F/aS0DUfGPiHwh4esImaN7hnd9pKqCozk/QUATfsl+Hn0vwFNqVxGVlv5Q8beseK7z4xeLIvB/gHUr8yiO6kjMVt6+YeldRoum2uhaHaadaAR2dnCETJ6KBXxn+0x8Qh4s8WHTNOl3aVpxMasp4lbqW/DpQB4+TJfXxZjmaeQkn1JNfo1aW3kfB9LYDG3Q9mPfyK/PXwhB9q8U6TB18y5jX82FfpNd2vl+FJ7TH3bJov/ABzFAHxD+yzKYfi/p8J4MiuuPopr7er4b+Bp/s34+acp4EVxMhz/ALrCvuUigBppKdSUAeHftb2An+HUN4VybadVB9Nxr4zr70/aI086l8J9UgAJKukvH+zk18F0Aesfsx6a198XNHnUZFo/mn24Ir78r48/Yq05ZvFetXsgyIrZQn1LV9h0AFFFFADLiJZ4JIn5SRSh+hGK/OH4waF/wjvxG1vT1j2QJOTF7r2NfpDXxP8Atk6etr8SLO4jXC3FkrMf9oM3/wBagDof2NtE/eaxrm37oNpu+oDVxPxouGg/aPup2JBjvLdvphVr3r9l3TBp/wALLaULt+2yeefft/SvBv2qLGTT/i1PfrlftSLIhx/dAFAH2fYv5tjbSf34lb8xXnP7Q/hg+JPhrf8AkReZe2gEsPsM5b9BXY+Bb9NU8G6NdRnIa1jUn3CgGtm5gW5tZrd/uzRtGfowx/WgD8xK6TwR4L1vxtqUlj4etRcTxpvbcwUAfU034g6MPD3jXWdLRSI7a5eNPdQeK+lP2MtNjHhjW9TK/vjdCAH/AGdgP86APlnUbK/0HV5LW7jktb62fBU8FSK++/g14ri8YfD/AEy/Vh9ojTyJl7gp8uT9cV4V+2H4Ujt9Q07xHaxhRcAxXLY+8+fl/QVzH7LXjk+HfGH9j3kgXT9S+UknpJ/Dj6mgD7TFKKUjaSPSgUAcR8cI/N+EPisAZP2JiKyP2am3fB3Qx/dV/wD0M103xSgNz8N/EcIGS9owxXJ/sxvu+EelL3TeP/H2oAf+0npT6v8ADVLeIZk/tCBgPUDPFeneF4jD4a0qJgVKWsSkHthBXBfHqWS1+Hkt5DktZ3Mdzj12ZNdz4P1NNZ8LaTqMZBFzaxynHYlQSKANeiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACvO/2grpbb4QeJgThpbRox+NeiV4j+1xqP2H4YiPdj7VP5P1+UmgDlv2K4QPCHiKUjk3qAH22CvomvBf2N49ngDVGA+/dg/+O170aAEPWkIHXAz64pTSZHU9B1oA8R/ak8dSeGvCKaTp0uzUNROxypwyR4zkfyr4qZizFmJJJySe9eo/tH+JH8QfE7Uk3b4LBzbREHIKj0ry2gDs/g5Ytf8AxK0CNRnZcpIR7A5r9H5EEkbI3RgQa+E/2TdOW/8Ai3bmRSY4LWWTOOhGMV93UAfnv539gfHm8bOwQ6rKg+hYj+tfefYfQfyr4T+O9p/ZPxlvpMbd9wLj83Jz+lfbvh7UV1fQdP1GMgpcwq4I6UAXjRS0GgDP16wXVdD1CwZQxubd4lB7MVIB/Wvzj8SaPcaDrd7pt4jLLbStEdwxnBxmv0rrxf8Aac8JaNe/DzUdclt4otUs9hjmUYLZYAg+tAHLfsToNmuv3+Ufyr6nr5Z/YnlHla7F3GG/lX1NQAUUUUAFfI/7asSnXNDk/iMW39TX1xXx7+2fdZ8a6Ja54W2EmP8AgRFAHvfwdtfsPwu8N24GNlqOPxJrz/8Aal8BzeJfDUOt6ege70pGLr6xdWP8q6TU/GkHgb4M6ZrTRxyyeSscETHCu57fzrvI/J8Q+HNowYb+22t6fMORQB5d+yvro1T4Zw2LvvuLGRw5J5wzHb+leyDqK+Uv2YNV/sb4oeIvDbHZHcySbQen7pmxX1bQB8S/tW6Wmm/E93jGBdW6zkjuT1r2n9kBQPhtfY73uT/3zXnv7ZVuo8SaVcY+doQmfbFd/wDsdyiT4cakoP3L7B/75FAHSftKaQNX+FV+NuTauLnPptB/xr4VtLiW0uobi3cpNE4dGHZgcg1+jHxJtxd/D7xDARnfZuP5V+ckqGOV0PVSRQB+hHwZ8YReNfAllfq+bmIeROG6716k/Wu5r4u/ZS8ZnQvGj6PeShdP1JcEu3COuSMfXpX2ljnBoAoa/am+0G/tQMmWFlx68V5P+yndCXwFd2wOTaXLRkenztXtHZvcEfpXgf7NhOj+LfHfh6TIZbwTKp6gYJ/rQB6l8VbAaj8OvEEJGdtnLIB9ENch+yhro1X4V2ttLJuubOR42Gei5wv6CvS/EEH2nw9qsB5EtrIn5rivmf8AZC1Uad428SaDI/yyf6pCehRmzj8KAPrSiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACvl39trVh9h0HSN3JkN1j8CtfURIAJJwB3r4I/ag8TjxH8ULuKJ1kt9OX7LGyngjqf50Ae6/sfqF+HV6fW5z+le6mvAf2Nr2O48C6rbK6+dDdAbM84K5zivfSQrBWYBj0BPNABWZ4lv49K8P6jfTnbHDA5J9yCB+taZGDzXk37TmvLo/wsvrbeEm1DEUZzg8EE4/CgD4b1G6kvb64uZ2LSyuXYnuTVairmjxW8+q2kV65jtnkVZGHZc80AfUX7Ffh91i1rW7iFlUlIYHI+8CDux+lfU1cr4bu/DPh7wvZRWV5ptpYpCpG2ZFB+Uc9eteVfEr9pDQtEhntfC4/tLUQCocgrGh9c4w1AHh/7V80U3xanMDBgttGrY7MC2RXqv7MPxQsr3RbTwjqs3lahbjZaFjxIg6KD69eK5P4C6D4b+Id7r2s+OrpLnVZJC5jkmEYUE8kA9uTxXJ+HPC0A/aITSvCkryadaX5KTI27y0A67u/1oA+3sV478YPjdpfgkyadpapqGtdGQNhYvcn19q2vjr4/HgLwc0luynVLoeTbg9VOPv47jivinw5our+O/Fa2VpuudQu3LuzN2zyST6ZoA7O++PfxAuLtpotaaFM5WNYkIX9K57xp8TfFfjO0jtfEGqPcW0Z3CNUCAn1OBzVX4heBdZ8CawLDW4CjMN0cg5Vx7GuVoA+nf2JLgHWvEFv3Fuj/wDj2K+t6+Lf2M7wW3j7UoiQPtFqEx6/NmvtKgAooooAK+Hf2vb4XfxTRVP/AB72iRfQhmP9a+4q/PP9oi8N38XvEIJyIJzEPwH/ANegCz8SfG/9seAfBmhwTeYlraCS5GekoZh/LFfWvwI1D+1PhdoN2TksrqfwbFfnzX3H+yrdGb4U2UJPEErj82JoA+dNb1JvCPx8kvIXMaJfAyHp8jN836V9x2V3Ff2cF5bnMNwglQ+qnpXwN8e08v4p60AMfOD+lfU37M3iseJPhxb208oa904+QyZyRGMBSfrQB5X+2Ww/tjSE7hM/pXQfsY36/wDCO65p+4bzdCbHtsArL/bN06RW0XUdp8p2MOfcLmvOP2cPG8Hg7xyg1CTZYXyiCRz0TP8AFQB9qeLQD4W1YN0Nu2a/NvUgBqN0B081/wCZr9Avij4q0nTfh5rF2mpWkhktmWAJKrF2PQcGvz3lcyyvI33nYsfxoAm065ey1C2uY2KtDIrgg88HNfpJ4M1X+3PCWkaoWDG7tklJHqRX5pV9mfsleMV1fwhLoFzKGvdPJZAT/wAsuABQB70K8isNAl0H9oy51LG2y1qwkkL9F8xdqhfqea9erw79q6TUdN8KaVrekzSwzWd4mHjJ4OCefagD2y8wLK53dBG2a+FPhj4gTw58elvS22N7+aA+hDsV/rXv3hz41abrXwe1LUNQuYYNbt7ZreSBnwzSFSFYeucZ4r40F9MNSF+GxcCXzg3+1nOfzoA/UqivP/gh41t/GvgLT7tZ1kv4YxDdJn5hIAMnHp716BQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUVxfxS8Zr4O0e0liCyXl5cpbQxHkktnnH4UAdfc3ENrA01xIkUSjJZzgCuUu/iT4StC3n6zbrjrjJ/lXnn7VLXMnwVNyZWjYy2/moDjJJGa+aPgh8NLj4j+IZIHkeHTLYBrmUHBGc4A9+KAPfvi5+0Votpo11p/g6U32oTKY/tG0qkWepwcZ/CvjqaR5pWklYu7HJYnJJr1b9oD4c6d4C8UWVjoMk8tvcQqQszBn3nPsOKNI+AXjrULWOdtNNsHGQsxwcfhQB53oXiHWNAleTRdSurF3GGMEhTP1xV9/HPiqSYSv4h1VpB0Y3L5/nXqNl+zN4xuHAluLC3B7yFv6CvVPh7+zbpGiXMV74luf7RuoiGWKM/uSfcEZoA9W+GF1f3/AIC0W41YlryS3Qsx6sMDBPua+af2xvEgvfE+naDE26KyiEzYPAduCP0r6s1zU7Tw54fur+cxwWtlCWUdAMD5VH5Yr85/HGvyeJ/Fep6xKW/0udpFVv4VJ4FAGFRXX/Dz4fa7481E22iWxMaf624f7kY9zXqfib9nC98PeE73Vr3XLEPaQmVxk7TjsOOtAHhlsdQ1OSDT4HnuGdgscIYkZ9hXa/ED4W6j4G8M6TqWt3MSXeoE4swPmQD1PSvRv2O9I0+78S6pe3Vv511bIFhcjKqDnP48U79r3W93jjS9OkG+OxVZih6HdyRQB5J4Q8DeLdcdH0DT7oiTjeG8sEfUkcV9d/Av4Uw/D7TWur9kn1y5XErgcRr/AHR+NdJ8J/FejeKfB+ntossW63gSOaBRgxMB0rV8beI7fwroE2qXSlkQgADuSQP60AeRfGj4Q+IviN4xiuk1K2tdMgj8uHzELbRnJyAa6v4S/B/SPh47XkcpvNWdNjTsPlUdwo6jNenId0aMOjKG/MZrn/EnjPw54Zube317VraynuDiNJCct+VAGP8AF/wLbePfCNxYyhVvYVMltLj5gwGdoPucV+f+pWU+m39xZXieXcQOY5F9GBwa/TWCWOeGKeBw8Uih0cdGB6EV8k/taeBV0vWoPEthCFtr47JlQcK4HLH6k0AcX+zRqBsvi9okIzi6l8o/98k1+gFfmd8OtV/sTxzouo7tv2e5Vs+nb+tfpdC2+GNv7yg0APooooAR22ozHsM1+bnxfn+0/E3xHNnO+7Y/yr9H7rm1m/3D/KvzV+JQK+PNcB6i5agDmq+1f2SA3/Ct3J6Gc4/M18XQRPPMkUSl5HIVVA5Jr9A/gj4Vl8H/AA803T7vH2xgZZcdPmOR+hoA+Qf2h1K/FnWQfVP/AEEVS+Efj7Uvh9rh1Cziaayl2rdR4OGUe/TPNehfGLwPqXi39oa80nTIJGWZYmeUL8qJsG459q9k8V/Dnw/4Y+C2qaZb2cJdIA0txj5pHHfP1FAHS6nYaF8YPh3Flj9lu4xJG4+/A5/rXxf8Vvh3qfw8177FfkTW0o329wgwsi5/Q16t+yL43ay1yfwvfT/6NeDdaoe0vU/pXrf7T+hw6r8L7y5kjBlsMzqwHI6CgD4s0u01bXpF0+wW4u2UbxCCSAB3xWfdW8trcSQXEbRyoSrKwwQa9E/Z/wDF8fg74i2l5ckLbXCm2lY9FDEfMfpivYv2rfh7bXOmR+NNEiTJ2i58sf6xW+635UAfKld18F/F7eDPH+naizN9kZxHcIP4lPH88GuFpQcHI60AfqHDLHPDHNC4eJ1DKw6EVneKdBsvE3h+90jUk3W11GY2I6rnuPQ147+yj47/AOEg8KvoF/MG1DTR+7BPLQ+v5mvd6APgX4p/B7xB4Gv5X8hrzSWY+TcxDOR6EdcivMmUqxDAgjqDX6jsquhV1VlIwQRnNeE/G74Fad4j0+fVPCttFZ6xGDI0SDCT+ox60AfJng7xTrfhbVEufD95NbzMQCiMdsnoGA6171b/ALQ3jrw9LCvizRIHgZQQUiMZI+pJGa8M8KhvD/jzT11i3aN7W6VZopBypzjmv0M8TeHNH8XaKbHWLWG7tJVBUkA7TjqDQBzPgH4xeF/F+mQXEd0LK5dhG9vOcFGPbd0r0aKWOVA8Tq6noVORXgHhn9nbS7Ox8Qadq0yXNldy77GRT+9gG3GTxjINeY3upfEX4DawIZZ3v9AZiIjJl4mX26YbFAH2fRXA/Cn4n6J8Q9LWXT5RDqEY/f2khw6H1HqDXfUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAEdxPHbQSTTuEijUszHoBXzv4def4x/FpdekjZPC3h5iloSMGWXOQSPTg1pftLeLrkTaV4G0i4MF5rTBZ5F/hiJxjPY8V1HiPxF4c+DHw+t4IhFHJHCBbWinLzPjqe+M96APKP2yvGCBLLwtbOGZv39yv9wggpXY/shadDb/AAra9AAmnu5A7f7K4xXydN/b3xL8azyQRSXuqXrM4Qc7VHb6AVqDxb458A6dd+EjdXGm25YtJbtGAeepBIzg47UAdR+0T4rh8QfGFDZPut9OaO2yOjOrckV9tabc/bNOtrgDb5kYOPTivhn9nn4f2nxB8WT/ANqXpjjslW5aPOWlO7p6193RoscapGAqKAAB2oAU02nVl+Ide0rw5Ytd65fQWcABOZXClvYZ6mgD58/bE8YPbabZ+GLNyvnt5l2PUDBWvkyvSfj343svHXjqfUdMiZbRFWJHbgvgYzivNqAPpj4K/F/wr4E+HDWt1BO2pB9zRRKMyNjrknpXnHxb+MGtfEOYWuPsekq3yWsZ++exY+teXUoJBBHBFAH3d8J/DGmfCn4bm/1CRVmuY1uLmUdXJGVUe/JriP2jPh9YeI9IPj3+0jZg2sbeVKAAwK/KPrXiVh4+8S+Nde8N6N4g1R59OjuoY1jChBjIHOOvHrXpn7Yeoaja3Wk6JGskWjxxgx44WQgfrigDmv2Rb24g+JLW0RPkXMOJR2wMkV9IfHXS7jWPh9c2toheQupwP94V4N+xfZRXHi/XLh/9bbWqNGPqxBr3n4k/Fbwz4Js5VvLqK71HGEtIiHO7/aHYUAd9AuIIVPURqP8Ax0V8JftL3FxN8YdcS5Y4jZAi54UbF6Va8V/H7xprV6z2V6NNtM/LBCoIH4kZrzfxPr+o+J9an1bWrj7RfT7fMk2gbsAAcD2AoA+xv2fPijpPiHwtY6Le3KW2sWcawiJz/rVAwCv9a7b4w+G18TfDvWNPdRvERmRs/dKfN/SvzygmlgkEkEjxyDoyMVI/EV0T+O/Ez6YdPfWbs2pXaVMhyR6Z60Ac7G3lTqw/gYH8jX6T/C7Xf+Ek8A6Lqufmnt1LexHH9K/NSvqL9lP4padpWn3HhvxHfJbKGD2sszBUC4wVye+aAPrKiq1jqFpfxCSyuYbiM8ho3DA/lVhmCgliAB3NAA6h0ZT0IxX5v/GS1+xfFHxJb/8APO7YfoK/QrU/E2iaXE8moarZW6KMkyTKv9a/Pv41a1YeIPiVrWo6Uwe0lmJWQfx+9AG7+zT4ZTxJ8T7MzYMWnqbx1PRgpAx+tfdwXsBwOBX5o+GvEWqeGdR+3aJdvaXW3bvTuPSunufi743uT+912c/RVH8hQB+gS28Uc7zCNBM4AZ8DJx715r+0dqyaX8JtXG4CefYkYPf5hn+dfJ+j/GfxzpU6yQa3KwB5SRVYMPTkVe+L3xdvviJpuk2c9utslopMuwn965AyT+VAHnWh6ncaNq9pqNk5juLeQSKw9q+8fBniPSPi/wDDa4t5WVWuYPs97Ap5jbHb8hXwBW94Q8Waz4R1NL7Qr2S2mU5IHKt9QeDQB3Hxp+EN38OTb3S3aXWn3DEK3RlOeAR9K9J+Ffxa0zW/hxqXhLxlcxxXEdnKlvcS9HG35R9R0FeQ/Ez4qa78Qbezg1YxpDbj7qD7zeprz6gCW5RI7iRI33orEBvUV2vwk+HV98RtbubGymSCO2iM0sjdh0GPxrha+jv2Std0vRYfE01/dQ2zx2pfdIwXIyMAfjQB5ToGqap8LPiNuDA3FhOEuI1PyyLwSPpX394Z1qz8R6DZavp0gktbqMOpHr3H55r84vGGsHX/ABPqOqMCDdSl+fy/pXrf7NnxX/4RHVhomuTkaLdsArueIX7H2HJzQB9rAZIFfPXin433ug/GyPQzGraFvjgdcfNvbjP0zX0HazRXEUU9vIskEgDI6nIYHoRX55/FTVUm+LOq3sbFlgviMj/Yf/61AH0B+1J8Ll1OybxjoKr9pt0BukX/AJaL2Zffmr/7M/xag13SrfwvrcqxaraqEtnY/wCvUdv96vXPAV/aeJfAOlzxvHc29xaqkoBDckcg+/NfEfxu8HT/AA5+IcsNg729vMTc2bIxDKmSOv4GgD6P/ad+JV/4Is9LsNCuTb6pOwuC4AP7sHBFdv4O1fRvix8OoZb2FZ4Z4/LuIm+8jgYJ9uc18CeJPEWq+Jb1bvW7yS7nVQis/YDtXcfBD4p3nw41ti4afSLnAuIOuPRh7igDQ8daFrHwQ+J8V3o0zi2D+bbSdnQ9Yz+HFfZfwx8aWfjvwnbaxZbVZvkmjH/LOQdVr5W/aZ+Jnhvx5p2ixeHzNLLAzPI0sewoCOlR/sjeMJdH8cNoUrM1rqa7UUn5Ucc5+poA+2KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKCcDJ4FAHwT+05qNzJ8YNRxI6NakJEynBUYzx+deZ6zfarqLxz6xPdzsVxG85Y8e2f6V7t+0PBodh8a9M1S923+ky7ZL5ITnkE5QnpnGK4n4yfEmy8aJZWmkaLbaZYWowihF8wY6fMO1AFn9lzVrfSPi7p8l2wWOaKSAE/3mGAPzr0n9tiDTkm8PzRCP+1HMgmwfmKADbn9a+X7eeW2njnt5GjmjYMjqcFSOhBq3rGsalrV19p1e+ub24xt8yeQu2PTJoA0vAnivUPBniW01jSnxNC2WQ/dde4Ir61sP2mfB8ulJNeRXcF8V+a3WMsoP+9XxTRQB9J+Mf2otVuRJD4X06KxXoJpj5hYeu0jivB/E3ijWfE149zrN9NcOxztLHaPovQViUUAFFFFABRRRQA+KR4ZFkiYpIpyrKcEGtHW9f1TXPJ/tW9muvJXanmMTtFZdFAFzTNUvtLlaTTbue1kYYZoXKkj3xTZJTdu817cyPMf4nyxb8TVajB9KAEop20+lAUnoKAG0U5lK9etJQAlFO2ndtIIPTBr3Dwl8B7i8s9B1LWdThhstWkWKJFU79xBIz+RoA8ftde1e1QLa6pfQqOgjnZQPyNTt4o19hhtb1Ij0N0/+NejeMPgP4s0vxc2maVYSXlnIwEFyCArA9zk8Yr25fgl4RsvEXhPS7yxdpLqzma6HmfekRFJx+JNAHx5c6he3QIurueYHtJIW/nVWvZPiB8D/ABJp3ju4sdE0uWfTJ5s20ikEKhPAJz2FdFrX7OF1Fe6fp2natA2pSQ+bcRMhOwc8/TIxQB880Voa/pc+iazd6bd48+2kMb49RVAgg4IINACUUoGelLsb0oAbRTtp9KTBoASiiigAp6SOisEdlDDDAHGR70yigAooooA9C8MfGDxj4a0GbR9N1FfscqlMTRh2UEY+VjyK4GeV55pJZWLyOxZmPUk9TUdLQBuaF4q17RV8rSdSuoYyf9Wjnbn6VW1/VtU1i7WfWrm4uJ1XapmJJA9BntWajMjBkJVh0Ip000s7bppGkb1Y5oAjooooAK734Fy+T8U9AfOMT9fwrgqvaLqdxo2qW2oWTBbiBw6EjIyKAP1GHSivjbSf2pPEERT+07C3nUfe8pQua99+D3xe0n4kxTxW8LWWowctbSMGLL/eBHagD0yiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAbNKkMTyysEjRSzMegA6mvFvGPj19atbx4NROh+FYGKS6mDiW6I/gh9M+pHSrPx28VWtrpstlcXLw6fbgSXZiI3S5+7GvsSMH2NfLuva9feLX+0XI8mzjXbbWiH93AnZR9PegDp/EXxS8CXOj3GhWnhCZrGXJkvJWU3LydPMDdM14VJtMjeWCEydoPXFT6hGYryVSMYNNsrWa9u4ra1jMk8rBEQdST2oAtaRoupawzrplnNclPveWucVe1Dwfr+nWTXd7pV1FbrgM7JwM9K+lPhp/Y3wR8PSR+L7+Earq22Y26AMY1HTqPfmvR/DXxP8GeJpvslnqVqJ34EEwGX+gxQB8DEEHBBB9DSV9e/Hn4Pafqegz6t4W06ODVICZZY4RzOO/wDj+FfIhQhyrAhgcEGgBtFTrbsetTJa+1AFOlCMegNaSWv+zUyWvtQBlLCx7U8WzGtlLT2qZLT2oAxFtPXNSrZ+1biWftT2t1ijLyYCrySaAMQWgAyRgVWldFbbGNze1bWiaRqnjHW49L0K2eaRz0UcAepr6r+GfwM0LwvBFd66kWp6mo3kuPkib/Z9fxoA+UYfCeuXOh3GsGxlj0yJSTO4wpI7D3rnkYqcivbv2lfiFF4g1hNA0WRf7JsG+cxgBJZPUY9AcV5N4U8P3/ijXrXSNJiEl3cMFXJwFHqfYUAZJJJya9++CHwt0vX/AAVP4ruB9svbOfiykGYiin5iw6n5c45612fgL4QeD9Q0qTwr4it5LPxbauXllYYeRD0Kc8rivoLwV4R0nwd4ej0fR7dY7Vcl89ZGPUn60AeZz/BXwD46/s7xFZQTWcEkauIbVgqP/vDBrrPifYQaN4BF5p0IjXQit1bxIO68YH51Q0q9/wCEI+Ic2iXjFdI1uQz2DngJOesQ9gBn8a9D1mwi1XS7mxnAMU6FGzQAaNdi/wBIsrsEHzoUkOPUqDXF+KZvL+MHgiPPD219x/wBKo/s+64dU8Fy2NwxN9pd1LbTA9hvbZ/47iqPxF1AWvxt+Hy56Q3gP/AlUf0oA9cwPSvO/Bk41z4keJtWQ5isVGk8dMqwc/8AoVdX4y1eHQvC2qalcyeWkFu7BvfBx+uK439ny3kPw7ttUuFxcatI17IfUtxn9KAMvxN8AfCOv+LpPEF6bpJJH82a3RlEUh9xjNee+OPhV4Y8XWPiDXtFhXSbDSoG8iW3XEd0VXLMfoRivYPivr80UNp4Y0WUrrmsMIUZD81vGc5lPsMY/Guo0zw7p9l4ZXQxbobIxGKSPs+7735kmgD8xmG1yAcgHgjvVu0kRyEk4PY19e6z8BfAnhlNQ1DUri4nW6DR2tqQMrI2Qoj55OcYzXzP8S/AWqeBNWig1GF0guUEtuzfe2n+FvRh3FAGQ1n7CqEzoshRVLMOOK19CulucW8v+sA+UnvSSCXQNetdRWFZUjkEgV/ukjsaAMUhv4oWA+lCCN2x90192eCU8HeO/CttqFtpVpJGygSwlfmjbHIOK8V/aW+GmjeHNNt9b8P2q2cRcJLCn3ck8HmgDwBrU9qYbdhWvbhf7PE8h+VeM1S+2wknKEelAFExMKbtPpXcfDr4b698RJbw6R5EcVsu5pZ2KoT/AHQQDzXJPC1neTWt0NrxMUbPqDigCnSVo/Z1dQy4I9RUT2voKAKdFTNAwqMow6igBtFFFABTkUuwUdTTa0tDt/PuTxkCgChLGY22muv+GGvXvhXxNaazYu6mBwZEU/6xM8qfasXxBa+RIpx14rc8LWRey3EdaAP0U0bUItV0q0voGVo541kG08DI6VcryP8AZp1w6j4G/s2QlptNfy2Ynk7iSK9coAKKKKACiiigAooooAKKKKACiiigAooooAKzvEepLpGh3t8zAGGJmXPdgOB+daNeV/tI381l8OWW1bE8l1CPqu75v0oA+d/i1qE+s+HTcSlgkkvmlD2Y9awNCsM6YOP4a67xta7/AAVFIV+8wNR+HbHdpAOP4aAPFPFMPk6q4A6813P7N+gR6/8AE6zWUZWzja7/ABXGP51zHxAtzHrmMY+X+tenfsgp/wAXBvX7iycfyoA9Z+PPwqh8bbdVtL1bbVY1KpFIwCTe2T0/CvkLV9M1TwzrD2l/FNZX0J91P1B9Pevef2q7HxNa+IYtXjuJ00LaqQmKUrsbgHIB7muY8RO3jH4GWuvX5EmraPc/Z5JsfM8RIVAx6nHNAHq/7M/xJufE1lN4f1uTzby0j3RTOeZE6BffvXN/tIfCaKCGXxT4bt9gGWvYE6AY++B+QxXjXwg8Sjwp8QNJ1KUn7OsuyVR3U8fzIr74uIbfUdPkhlCzWtxEQR1DAigD86tOAnj5+8vFaUdt6ikubI6X4y1CxPISV1HbvxW0lvQBnJbe1Tpaj0rTjt/arCW9AGZHa+1TLa+1a0dt7VOlt9RQBjmBI4y7kKijJJrn7a21HxfrsGkaJA8rSNtVVGfqx9q1/HkptdMjhU4aVs5HoK+lP2b/AAVaeHvBNvqjxK2qX43tKRyEPKgenFAHQfCT4b6f8P8AR1SICbVZlBuLgjkH+6vtmuK/aN+KS+G9Ok8P6LMDq1yuJpFP+pQ/+zcdK9C+KnjS28DeE7jUpvnuXHl28WcF2P8AhnNfBWt6ld6xqdxqGoSNJc3DmR2PcmgCtFHNd3KxxK8s8rYCqMlia9m+FPwn8b2vxE0W4fTbmygt5o7iS4YFV2AgkZ7nHasD9n7StA1jx/b2viO6ktywH2PZxumyNoJ/Ovr671/xF4GUf8JDbHVtEU7ft9qmJIx2BiGSeO+e1AGn8SPAyeJ7aK80+drHxBZZe0vI+Dn+63qpwOvSsr4X/EZtau5fDniiNdP8V2fyywP8onA43pnqD14rtvDniHS/Edil3pF3HPGwyVBG5PZl6g/WuD+N/wAOT4t02PVNEk+x+JNOPm21xH8rPj+EkckYFAGz8Y/Cr+KPBtytllNWswbixlU4Mcgx0/AVQ+B3xCi8d+FwbgrHrFkfJu4ehDDuB6VxXwc+NY1C6Hhbxyv2LXoW8jzH4WUj19DXC/EV7r4LfGmHxHpyk6Nq2XmhQfLtzyv14zQB0/w/1b/hEf2jvE3hmSQR2OpMs656GTYCMfXNM+NmqfZ/jz4NTd/q8r/31iuN/aD1CDTPif4O8Z6fJut7yNLsOvojLkH+VN+ON99v+PPhWaLgTJZyrjsHINAHqH7WfiE2nhGx8PwOPN1i5SJwDyqhgf1r0fSriy8C/DW0k1KRbe1060G8txjHavmn4m3LePf2l7LSLZ2NvZTJDszwGjJLH9K0/j74tufiJ8QNO8A+GpC1tFOEndSQHk7g+qgUAei/AiC88X61qvxD1yNklu2a30+I8rHb5B4/EGvTPG3i7TfCOl/atRlzM/y29unMkz9lVepJrj/GPjfw/wDB3wTZ2CgS3MEIitbJD8zN7+gznmsD4P8AhXWPFGq/8J54/G+9mH+g2LD5LdM8NtPRuBzQB1/grw/qWrX0fifxiM3zgmzsDzHaIehx3YjBOeh6V4h+1R8PvFev+OrbUtIsri/0+SBIUSEFvLYdSR2zkc19Uajf2mm2j3N/cRW9ugyzysFA/OvNn+IuoeKL6XT/AIfac1yqHZLqVz+7ii91BH7z8DQB8I6zpWo+GtZks9Sgktry3bDKwx+XtXY2kUGv6LkgbiMNj+Fq9C/ak8L6bokOm3d9qrXviy5cm6ITarpjqFz8vNeSfD2/EGqfY5D8lwcKP9qgDtvgL4zuvA3jf+yr9j/Zt63lyIx4V+ivX098XfDI8ZfD7UNNg+eQqLiAjqzLyoH1r49+JdiIHtbuPKP90kcZNfZvwt1Rta+H+i3xO4+QsZYeqgCgD4Uj0DX5L4aEmnXX2rzMGHyznP8AhXuV74P+Gfg3w1puneN3kOvlBNcfZ3LMCR93A7V9NNZ26ytcRWlut3g7ZfLG7P1r8/8A4rNft8Q9d/tUuboXTg7s4xnjHtQB9U/BTxr4JvJZfDvg20mtEiTzQZVwZO3JPJPNcn+0p8M9FtfDNz4l0ezaLUjOrXGwkqVxycdBXhXwl8ZDwR4ug1OSDz4DhJVBwdue1fc11FZeLfCUySDFjqFsQxdfuqR1oA+A9GRZbMgcsp5qzJbe1O8Q2TeEPF2o6cZIbiKKQgGFwylc/LyO+KrSa9GT8tvkf71ACSW3tVeS39q0LS9gvDtGVkPY1PJb0Ac+9v7c1A8BHSt6SD2qrJBQBjFSOorqvBFt50jnGecViywdeK7/AOG9hvTp1NAGL43s/LgDY6Gul8GWW7TlwO1L8R7ApZOfpXT+ArLdpY47UAd1+zRfNZeMtW0vOFuk84j12j/69fS1fJfwsuV0n43aeX4We3lhx7tjFfWlABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXz/wDtY6wLGy8O2Octdzt8v02/417zqF5b6fYz3l5IIraBDJI56KoGSa+Cfir4/f4hfE2G5g3rplvMI7aNjngEAt+OM0AeoeM7PHw/tDjqRU/hWwzoYO3PyVteNbUf8K4sG9TV/wAG2e7w9nHRKAPm34o2+zXwMfw/1rrv2RZxH8S7qFjgPYykfUEVj/F6HZ4jXj+H+tcb4V8QX/gnxRBq+nKrSR5G1vusp6g0AfT37WOsW1r8OV0mRsXN5OksY9Qjc1xPwS8LjXfgj4micFluGJAPTMfzcV5b8TfHuq/E/X7Fp7WNHjHk28EQ7sRkde5r7A+EXhQeFPh/Y6TOuJJV82de6s4G5TQB8BEMj4IKsp+hBr7I/Zj8c/8ACReEzot7KG1DThtUMfmeL+8fXk4r5t+M/h9vDnxE1a02bIZJTNCMfwMTiq3wp8Uy+EPGunaisjLbiRVuFH8SZ5FAG58WrFtG+MuqxTDbGbgMrHoVIHNXY4QeRyD0r3L48/C1vH1hb65oBX+1YoQRECMTKfmxn+9zXzd4a1Oeyv5dM1jMbxZBL9VI7UAdRHB7Vajgz2rCu/F+mWjFY99wR/c4/nVdPH9kD81jcY/3hQB1yW/tVmO39BxWV4e8T6bq9ytvDuinb7qvzn2rrI7f2oA8v+LEJjGnHHGG/nX2T8MnVvh7oDEgKLOPJ9MKM18g/Gf92NLjx95WP5EV9Z+CrVofhLYQgkNJpQIP+9HQB8w/HDxRdeP/AIhnS7Es1jYM0SJ/CWXO5/xAryfURGdQkjtzmJW2Jn0rrPDk/wDZWs680p/fRhkDHqDkg1yOmy28WpW8t5G0lskgaRFPLDPSgD6V+C/wAvU1fRvEmp6nbfZ4XW4FvEctkHIBI4r60ZFdCjKCpGCD0IryfwfY6H488N2ureEdV1PRIsBWgsZBEFYdmGOa0Li0+I2iAnT7zStXs0/5Zzq/2hv+BZAoAp+N/hV500mreBL6TQNbB3kQMUgnPX50HUn1riNH+OOt+ENUGjfFTSJLeYHaL6BP3ZHrxnP4Vuar8crzwtMsHjLwZqti5P8ArkdHQj14JqLV/in8KviBo507xFdxRK4wguISWjY9wcHBoA5j40eCtE+J2kf8Jh8PbuCfVoV3yxwna84HOdvB3e59K870TxbL8RPB03gTxfIf7atjv0u6m+VmkUY8tyfx5PpU+t/DbXPB903iP4Wa22o6Yvz7oJQksa+jg4z+Fc1qNxZ/EW7WaKKPRfGK87EXy47xvbuH9ycUARtJPq/wvvtDv1Lav4fnP2eM/eWDJaQ/TNbF68uteNfCuqbi3lWSuG9oVBp+jfaNb1mK78kw61j+y9XtscyRN8pkT2Cjmu28O/DHxXa2OmyJpMhEEWoQLl1B2SACI9e4oA870DWH0q68V+OmwL24mlj06Rv+WkjsQ+PorVoeANWt/hb4fn8S6rELrxVqiEWEEvzGJOomb0ycjHWm3ujNo0GmW+q20o07SgvlWWPnur88OB2wDgkntWZqclroutPrXjRF1PxC53Q6TGN0UB7eZnIK+wPWgDv/AITeCbnxNrB+IXxSuVWwUmWCO8fHmkdDtP8AD14ruvGf7Q+nxzjSPAFlLrGoMNkciIfLU9AMEZIrxmy8M+PPi3Ot7rF0NP0AHKvPJtggUdlTOf0r2TwPd/CX4SWxjGtW1xqxHz3DxFpD6gELwKALnhD4a+JfF86az8V9Rkmjch49HhcrCnpvXkH6V7bpmn2el2Udpp1tFa2sYwkUShVX6AV40P2htJ1G++xeGNB1TWbljhRCAgP4tiujs9Q+JOuoJINO0zQYW6x34aSQD2KHGaAOL/aF+C99471uHXNJvIopo4fKkjl6bVBIINfIGo2dz4c8RTWspX7VZy7SVORkV9/T+FnsbaXVPEnibVJYYVMk8Im/0cgdtpGa+H/i7q+i63471K+8N25t7GR+ARgEjjIFAHoevaKnibw5EI2AZlV0f/axXMeH/FPj7wHA2n6c8gtVJ2xuvmIM9wM8V2fwxl+1+DrQE5aLKsTXRXKLDC8shxGgLMfQCgDjPDfxy8aad4jtE1topbaZ1V4miwcE4yOeK+j/ABb8OfCnjdFuNWsMyyKCLiDCSEf71fGut6tb6/8AEGwlg3LbCeGME85wwyfxr79WNYlWKMYVQFAoA8k0/wDZ/wDAenXaXjRXsghO/bPcAx8f3hjpXi3xn+K2talrM/h7w/JJYaVbP5UaW2VeTA6ZXqPauj/af+JN4uqDwvotxLbR25zdvG5VnYj7hx/DjBxXcfs8+E9MuvA2na7q2mWtzqUpLxXcse6QYYjrQB598K/2f7rVRbax4ylaG1lHmfYwSJXzyGLcjB9K97tfhn4MtoBCvh3T3UDbukhVm/P1rr6ZLLHCheaRI0HUswAoA+IPjz4c0/wl8TTaaPEYLaSKOcJnhSxOce3FUZYeOlbn7Suq2WtfFVZNLuFuY4raKF2ToHBOR+tUpYun0oAxpIevFVJIfatmWOqssdAGLLFjORXq/wAKrMPAvGc15tLHwc17V8H7UPbxcdhQBmfFGxC2L8eldJ8ObHdpQ47UvxdtdljJx6V0nwutd+kdP4aAPHvHGqt4W8d6Jq4DFbS6WR1HVlByRX2l4b1m18QaHZapYurwXUSyDawO3Izg+4r4l/aEh8u9iwP4jXafsi/EhbC5bwdqbEQzuZLN84CNyWB+vGKAPriiiigAooooAKKKKACiiigAooooAKKKKAPEf2tNZutO+HAtrGZoZLmYCQqeseDkfjXxd4aH/E8sv+uq/wA6+2/2qfD11rfwymlsELTWconk9owDu/pXxL4aONasj/01X+dAH2J42Uf8Kx0z1L1t+BbcHwzIcdI81z3i6YH4d6evo1dd8PyB4UuM9TFxQB8zfGaPHiZOP4f61w6IG4IBFd98aOfE6/7p/nXCxCgCr4e1GHw34607UbiItbQTJI4Az8uRnHvX3n4Z8R6X4p0tNT0S5S4tZD1U8qe6n3FfButi2+xkz4MmPk55r6C/Y9t7xNA1meXd9hkcLD6bgfm/pQBrftMfDqbxPo0et6PB5up2YxKij5pIgOg9cda+PWVkYqwKsDgg9RX6Y/XkV5v4w+DXhLxRqDX1zaNb3TnLvExAb8OlAHinwA+KviQa5pXheZPt9hI4jDMCXgUnrn0Fdx8VvgJJ4o8VSatoV5BarPjzopTgAjjK4HevTPAnw68PeCY2/sa0xcP9+dyWZvz6V19AHjPhL9nvwppMaPqwl1Scj51mI2A+2MV1r/CTwC8ez/hF7ED23ZH613NFAHyZ8Xvg5qvh/wAQw6n4E06eayPzbIBuaFvp6Vycek/FW75ttM1fC9dkQr7f/lSFljVmYqigZJ6ACgD4ksfh18QPGGtWkOs6bfJBGdrT3CbVjXvzX1hrmvaL4E8I21vrOoQQCC0EESs3zSFVxwK87+J3x/0vw6ZrDw0i6jqC5Vpd37pD6gjqRXhmn6F4h+JOsPq/iG6kSCQ5LuMZHYKOlAHJ3kh1OfxHqNsrCFpPMz6Bn4pPh94Xl8Y+LLDRIJkga5fb5jnhRXrXjnwxp3h74Z6lFpsW07oy7k5LfMK8p+G2qaTo3jLTr3xDDNNpkT5mSEkMRjtgj+dAH3l8LfCmlfDvwtHpMF8kr7t0sruPmaupl17SogTJqFsoHq4rzbwl4K+HXjbQYNX0mynltZs4D3UgZTnoRu4NXp/gZ4BnGJNIlP8A2+Tf/FUAdDqnjTwasbR6lrGmbCMESuDxXjPjbR/gTrqyE61penXTHLTWcmGJ+hBFdfefs6eAZ/8AVWNxD9Lh2/ma4bxb8D/hnoMbtqfiI6cRzsaRdx+gJ5oA8rv/AAdo2l6gbnwZ8SdIVIzlPtNyVkP4BcGpJbbVtZby9e0228RE8f2jp5zcoP8AZ+6P0rL17TvhVYTPHYatrt5Ip/59kCH6ENUWjwabdDdovhS+vrYHDXE08sUa+7Mp4oA+ofg14ZsdJ0BvFGtQ3BuYomEVxqCgXEUIHzBgvHavO9W/ane38SvBY6PDJo8chQyPnzWAOMjBxXpvwYNhrfwzuvDaNZRrAj20qWV0bhUEmT99uSee9fPuqfs1+L18RyW1o1m9i8hKTmQjCZ78dcUAfRfjWy0vx38PYvEWlC5iuWhE0E9sqmdAw+ZQDxnBINfMi250pyNHh0rTpAcrqOqOwuQffqv6V9P6lDa/Df4Sw6V58z3MduIkW3UPLK+PmKIeuByfavmG6t01CP7VFotj4nBJxCt263S/70adKAK82gjXr1Z/E/xM0OSM/e8u5bd+W0CvR/Bvgv4JadKsmp+LbPWJj1juZhsz7YAryOzvvAMt40PiHQb7SGVsOls7SMn4MRXrHg34bfCDxKqvZeIJN7dLe5kWOQ/huoA958OeIvAGn2sdtoOoaRDAowqwsBXTQa9pU4Bh1C2cH0cV5hZfs7/D6IKz6dNPxkE3LjP5Gty1+DHge1ULBpUigdP9Kl/+KoA6zxBa6f4i0K90ya4Qw3UZRirjIr4J+NPw2n+HOuwW0l1HdQXSGaJ1PIXOMHjrX2ld/DvwbpVjNdz2jw28CF3Y3UmAB/wKvjr47eKvDPiPWbVfCNtcxW1shjd52JLtk8jJPFAHS/A0+b4cvFznZKo/Sut8VIR4c1Qrwfs0n/oJrlPgPHs8OXzf35lP6V6BfwJc200EozHKhRvoaAOG/ZK8PaNrOq65catZQ3VzZpE9uZP+WZLHJFfWMrlYpHH3lRmH1Ar4Z1ay1f4X+ILfV9AvSkTsQh/mrD0r7G+HuvN4q8G6XqsyeXLdQKZVHQMRzigD4V+JF9Lqfj3Wby5BEstxlgfoBX3h4Git4PB+kx2ShLcQLtUduBn9a+JPjdpv9mfFPXYFGIjPuT6YFfavw7iaHwNo0cmdwgGc0AdFXif7V1hqV14EtbnT3kFtay5uVT+IEjGfpXtleSftJ+ML3wp4Lii00Rrcaixi8xwG2KOuAeDkGgD4+0e8srQ+fcRyzXQbgHlR/wDXrVk8WSsfltlx9K95/Z28BeF/FXhI6/rGlLLqEd00O7zG2kgA7sdO/SvdbfwxoltD5UOmWwT0KA0AfCUXiaJji4hZc/3RWpHLFcxeZAwZT3FfWXiz4ReEfEttIs+ni2uWBCXERIKH1x0NfKPxF8Caz8M9ZCTZnsJSfKnUfKw9D2BoAqSr1r3j4KQhoIePSvBIbhbq2WZOAw/I19DfAxQUt8+386ALfxqtwlnKMdMV0XwfhD6W+f7lZfx4ULDcY6DFa/wfkCaU+e6UAeEftHIBex/75/nXjGkXUlhrVjdwkiSCdJFx6hga9p/aNOb6P/fP8685+GHhS+8YeN9N06wiLqJlkmYg7VRTk5PbgGgD9EPDV8+p+H9OvpRiS4t0lYehIBrSqKzto7O1htoF2xRKEQegHSpaACiiigAooooAKKKKACiiigAooooAjuIYrmCSG4jWSGRSrowyGB6g18RfGz4YS+AvG9rqNjGW0S+uN0bKOImyCVPpyeK+4a80/aG05dS+Gl1G3WKeKYH/AHWzQB5n4mut3gmyTPQiu08D3O3w04z1jrynXb4t4QswT6V3HhC826BgHOUoA8V+MkgbxMn+6f51wktyltEZJDx2HrXV/F+5UeIA7HgJ/WuV8F+F9U8f+I4dN01GCdZJSCVjXuT/AIUAO8BaNH418dWOm3k/kQTN8xJ7DsPc194eHdDsPDmkQ6ZpMCwWsIwFHc+p96+OfiV4WT4ZfFDSBprOLaMwSo7H7zLt3n6E19oabcm+060u8c3ESy8f7QzQAl9f2WnxiTULy3tYycBppAgJ+pqW3niuYVmtpUmiblXRsqfoa+R/2r9dnvvHkGkW05e1t7dGMangSHOal/Zx+JE2ga6fDuv3DGwuSFhMjf6qQ+56DFAH1vRQMEAg5BGQfUUjsERnY4VRkn0FAC0Vxnhb4leG/E3iC60bS7stfW5K7WXG/HXHr0rs6ACuE+OF9c2Hww1ySzZkke3ZCynBAx1Bru6w/HGlJrfg7WNNdd32i2dBjqCRQB8d/CHw/pupR3F/fxi4nicbQx4GfUd69ntysaKkaqqDoqjAH4V4h8KL9NM16/02aQIHYgbjjJUkV7DHNigCHxzbf2h4Q1G2A3FlBx9DmvmTSrM3+p29oJFjM0gTe3Rc96+qDIJYZIzj51K/mK+X9ctDpWv3VtyDBMQPwNAH3T8NLbw58K/B0Wk3Wu2lzef62URyAuxPomSamvPixLeM0XhTwxq+pTjgG4ge2jP0Zhg1wHwh+JXgLW7vStJvdKij1qUCNbieMOZJM4AzjivpCNFjQKihVHQDtQB8/wCu6T8afGDukk9j4e06T/llFKkjgf7wANYH/DNaKraj4x8YyXEUY3SGUEBfX5i1e4/EH4haH4HsDPqs++4YHyrWL5pJD2GByPrXgmoaf8SPjhcKZ0Ph7wuzEorg7iv+0OC1AHE+KdX+GfhG4ay8GaLJrWrIdhvZpWMav/sochqyLxdXlt01fx9fNpWnOP3enWyeRJcegMa4Kj/aIr1jxF4c8EfAjQEu5LdNY8TTJiBbg7lD/wB7b2FeX6Ro19r8N78QfiJJKdJtzmGGQlTcv1EaA9Bj+VAGj4d1u+0i50+7sB/ZVxeN9n0nTo+NiscGaY8ZwecN1FepRfHDVDawR+TG00yzW8c2AA00XDNjHQmvD9Pv7mfSPEHjvUV5I/s+zhIxt3rhWX/dwKa/mWFv4A0+TPni6eRyepEzJQB1vijxZf8AiS7s7zWr6SK1vlFnDcJx9mvI+WIA7McA9sGuZntzq2qyaddzroHjSE7VuIn8qC89BuGFX696f9hbVtR8b+Fky91b3Utxp6dNrq5Lkf8AAVp3hjRX+K/hWS2tnVfFuiRbos8faoB0X/fByc0AWbDxfHpd+ug/Fvw0t3ar8nnrF5Mqr/e3AAv9c16bp/wM8CeM7FdT8Ca5LaRnkbXMjxn3BbIrnvhT4n0jxzbjwD8T7LN/HmG2vX+Sbj+Asec9ea3b74L+Lvh5qjax8MtV+0R53NZTdwPXnDUAbWlfDH4neDHB8M+MV1GEdYbxAcj0yxOK7Sy8a+MtGRU8XeE22L965sJ/tBYeuxV4+lQfDb4w2uvXI0fxPaSaHryDa0dx8schHUqxwPwr1hWDKGUgqeQR3oA4K78VeGvGOgXumXdxNp0d1GYyL+M2zDPThsd6+EviT4Zi8JeLbzSbfUItQjibiePoQfxNfZfxr+JXhPwXdQWms6THqmoyLvEJVcqvZskHvXxT4t1QeJ/GF/qNrbCBbybckK9EHAxQB7d8IbYWvgq2lxgzksfwrrZHHPNZejwpp2j2lpH91Ix/KpZJqAPLvjVdG61DTNNQ4IbJ/wCBYAr65+HemDRvA2hWIGGhtUVvc4618ffFy2lj1Ox1WNSyqQD7FeRmvp74OfETS/Gnh+2iilWHVIIws1uxGSR3HrQBy3xU+CbeNPGltq9vfJbW7gC5UrknnqOfSvaLWBba1hgjACRIqDHsMVLRQAV8u/tha7Z3d3omjwSBrqyLyTAHOA4GK+nrm4jtLaa6mIEUCGRyfQcmvgLW7mTxf8UJWY+ct3qHlrjn935mB+lAH1b+zPpEukfCq088c3kzXa/7rAY/lXqlU9F06PSNHstOgAEVrCsSgegrP8a+JbPwl4bvNY1A5jgXKpnBdvQUAbleZftG6Yup/Cy9VkDNDKkoOORjNcX8CPip4k8b+Or+z1NYW04xPKgji2+XjoM1237ReqjSPhZfycFppEgA/wB7NAHx34fkzYsn90k19J/BGQLDAfpXzPoi7LRn/vEivoT4N3Oy3i57CgDqfjjOHs5yT6Vc+F1yE0rr/DXN/Ga636bMc54H86k+HV7s0kc/w0AecfH0yXepW0NujSTSy7ERRksxPAFfSfwA+Hlt4G8F2zSor6teoJ7iUrhhkZCfhnFeO6bo3/CUfGbQbfIxaSG+Oeh8sg4r6yoAKKKKACiiigAooooAKKKKACiiigAooooAK5f4m2hu/A+rJjISFpD9FBNdRVLXLU32jX9ooyZ4HiA+qkf1oA+Kte1AHwzbbT8uRiuw0TWYbDwzJcXEgSKOPLEmvPfiOE01Hso+BbyCJv8AeB5FYfi7WpT4ftNMtn+acjeo7jsPzoANN0fWPiv43MGmRlYd3zSMPlhj9TX0RqGr+EvgL4VSwtEW51idQTGmPMlbpvY+g9DWd4NFh8G/gwNZvIk/tm+TzAknVpMcJ9Mc15D8MfCuo/GDx3d3+v3M0lqhMlzOefog/CgDIu7zxR8W/G9tNJBNcFpAqKi4jhTPPt0619G/Ez4rWHw30Ox0ayKXuuR26R7EIKxYGMtXOfGbxvZfC7TIfC3gi0t7S8kjzJKg+aIev1PWvmUtPqF5JealM800hLMznJYmgDSF3e6/4kuNa1M7pppDK5PQsewqTWtM+1kXFtgTjqP73/16SKYAADAHtVqOf3oA7bwZ8e/E/huwj07UYVv4oRtV5RmQDsMmrPjD9oLxBrumSafplotms6lHdV+fB4wCK4YPFJ/rFVvwqxB5MZyiIp+lAGL4d1DVvBniKx1yJWE0b7snncD94H6ivpLQ/wBpfw7ezwwajpV7ZFsB52dSin1wOa8RZ45oykyq6Hsao3Oh6bcptWLyT/eTr+tAH2Ja/E3wZdBTb6/atu6cEV02n6jZ6hCs1hdQzxt0KOCfy618EnwpFg+Xdy+2SKfp0vinwpcfaNC1C4jYHO6Fs/oaAPYfjz8GrmC9/wCEi8F2rNljJc28XBRs53KP51514d+IE1tKtl4gidHX5fMI2kf7w616N8OP2iphPHYeNoVMfCi7QfNn1fPavRvHvwx8L/ErRv7T0hoIr6VN0V5bYxIewb2oA87tb+OeJJYJFeNhkMDnNeU/F/SympRanEo8qYBHI/v8mo45dZ+HniGXRtdikSMNyrdCM4Dr7HFdjrEcGvaJJCGDJKm6Nx2PqKAMH9nHStI1D4iQXWuX6WcOmqLxA3/LRlYYWvrPUfFHiLxcz2fgS1NpZE7W1i6XEZHQ7F+9nrzivgm2mudD1mOZV2XNrIGAb1HrX038O/2ktR1TxbpOjX+j2UVhcvHbB4d28McAE5OMZoA9o8J/CzR9IujqWsF9b1xyGa9vf3jKfRM9BWp8S/G2n+BPDU2pXzbpSNlvAv3pX7ACtTxb4jsPC+jS6jqcoRF+WNM/NI5+6i+5PFeceGPBd94012Lxb4+iOxTv0/SX+5br2Zh/fxjNAHn3w++F+r/ErxI3jj4jhltZX32thJ3TqBjsvtWF8eb258f/ABD0z4f+FF2WennynjQfu1f+9x2AOK+kvib4ng8GeB9S1RtnmQxEQRdN7HAAA/GvPv2b/h5NoWnT+J/ECM2v6r+8Jk+/Eh52/jxQB4v8e9Gh0/VfBvw/0E7Vt4hFKB/HNIwO4/mai+NVh/ZPxj8K6eihVt4rFMD+8CoJ/MV3GiaSvjT9qjVdRlRpLDSyrK3ULIqDaPzBqn8f9M874/eF3xkTPEf++SDQBznjCD/hBf2m4LiTiK7kWViOjCbKkfrTviboN58G/jBZeJNJiYaNczCRAnCD+9Gf516J+1z4bH2DR/FVrF+8sLlPtEmP4Mjb+tela14f0/4pfC+3i1BQxu7fzYZgMtG+PvCgDjfil8KdM+Iml2/irwlLHa646LPHcRcCc++O/bPtWl8EfiHd6gZPCnjON7LxVYjBWbj7QnZlPftWb+zXrt5YQaj4C8QHZquiyMsMZ6mDjB/M13fxG+H9n4rWC/tXNh4gsjvtL6LhlYchW9VzjNAGz4s8IaJ4rtfJ1qwhuCvMcpX54z6qexrjbXTPFvgN8afNN4j0BTxbSvm6iHszcbR6e1a/w88X3OpSyaH4kh+x+I7MYkjIwJ1HSRfXI5/GvOPj/wDG/UvAHiW30XRbG3lm8pZpZLgEgg9AMH2NAHnP7V1x4b1tdM13T7p/7aY/Zp7VgVMagZ5BHr3FeQfDTSjf+IYp3H7m2PmHPQn0pnxD8aal4/8AEh1TU44o5mAjSKEYVR2AruvBtgui6OiMMXEvzyex9KAO2kufQ1XkuayXu/eoXus980AXr1obqB4bhFkiYYKsMivJ9aE3hDxTDc6FcyW8yYljZDgr7fSvQnueetcdp9k3iv4oadp0eWLzLHgdwuSf5UAezeCv2kIgILPxfpssc5IU3EQCqvuV619D6de22pWMF7YyrNbTLujkXoRXzF+1rpmhaYdJXT9Ot7bUpj5ks0fDOm3GCOnWvRv2XtYk1L4bx2kjZFg3lrn0JJoAm/aX8SS6D8OZYbOQpc3sghyO8ZBDCvmf4K+IvDnhfxQdU8TWlxctEo+zCMjardywP4Yr3f8Aa8eEeDtPR3UTmcFFzyRnmvm3TbC0m06NpYR5h6t3oA+ntQ/aS8K28JeCxvbl8cKjAfzrwf4xfFa9+IclvB5JtdNgbekBxkt0yce1c6+n2MYLGMED1qfwD4Vm8deM7bS7KMx2xbMroP8AVp6n+VAH0n+yhoqab4AutSmiCPezeYsr8fKBjg+nFeWftH/EqHxbqUWh6KS2n2bkSSdpXB7fTmvQfj74ytvAfhG08G+GmWGeWDY/l/8ALNOhHsSea+X57W70nUPK1O3khnKhysgw2G5B/GgC9F+5tlTpgc/WvZPhNebLdBmvEJJsjrXo/wAM7/ZFjI60Adp8Xb7OlTHPYfzpngW/C6YAW7Vy/wAUr4yaZKAewp/w+le5tFRSdgGXYfwL6mgD3P4D6OL3xTqmuyAhrZfIjz3Djn+Ve8VyHwt0b+x/CVqkiAXEo3yNj73JwfyNdfQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAfGv7VGhPpWsFoEVbNmFxvPBd26j8MV5J4YgXW/HWi2sx+RpUX8uf6V9s/HnwUfF3hJpLWISajZBpIQTxgj5vrwOK+Erq1uLC8WW1eWOe3b5SRscY747UAez/td6rK3iLR9JQlbaCzVyvYt0z+Qrs/2NkjPhbXtg+c3Ue4/8BNfM/izxVq/iu5tp9cuBcTW0QhRtgU7Qe+Otei/s/fFOz+H0uoW2qQO9ldsshdMkqyjAGB9aAOW+NGqy6t8S9YuZjlll8r8F4H8q5KO4q/401WHW/FGo6jaoUhuJmdQfQmsSgDVjuTVmO496wwxHQ1Is7CgDoEuPerCXIHeucS596sJc+9AHRpde9WEuq5tLv3qdLv3oA6VLr3qZLr3rm0uvepGvRHGzk8AZoAiu7F9d8Y22nW21JLmVIVPQAk9a9A0HxB4q+B3i5tO1WN5tNL4eIsSki5+8p9R1rlfhHDdav8UNINpbNO4nDsqjO1QeTXVftTeJP7a+I09jA4e1sVCKR13/AMX60Ae2/FLwtp3xa+H0GsaIV+3Rxia3dhgkYyUb6CvmTwZq0trLJpd2GVkJ2huoPpX0X+yRf/aPh7dWbPveG7ZipOSFIA/KvGP2h9CXwt8VpZ7dPLjvQLtQOg3Ejj8qAOf8Z6UL1fttsB5yj51/vD1+tZHw48QW/hXxnpur31mLuG1lDNGeo5+8PcV0C3uQMsORXOa7paMWuLQcnlkH9KAPsbw94i8O+JHt/G/i7WLGK23NHp9m8mUtwOrH1Y8Z9K9j0+9ttQs4rqxnjntpBuSSM5Vh7V+W7TS+UIWkk8tSSELHAP0r6t+AnjmzsPhDJotrfifxA87RxW0jYYK7Bcr67QSaAPRNcsj8R/ibDZN83h3w5JvnyOJrnoYz7AYNesXU8VjZyTSkJDEuT7AVx3hmbw94GsLXRL/WbGPVJf3kzTTKkk0h6uQT16D8Kf8AGG6kj+H+pW9m+Ly9TyLcju55GPwBoA5P9mzRnt/D2r6/cD/SNZvpZeeoVHZR+lZfxg0wT/GvwA+M+Ys5/wC+Qtev+EtOj0rw3ptnGmzy4E3D/aIBb9c1wvxAg874wfD84+7Henp/spQB1nxI0GHxN4I1jSrgZWa3Yjj+IDI/UCuX/Z2vJZ/hhptlcjFxp2bSQejLz/WvTCARg8ivNfh3b/2D4/8AFmhjhLqT+1o19FchOPb5aAM/4v6HJour2HxB0aMm80sgX0aDBntsncPc5Ir07StRt9T0yC+tpFaCZA4YHgVl6v4o8N219/ZGqapp6XMw2G3llXJz2IJrynU9e0z4Y6Tr3hfWb94NOngeXSZFO99rAlhxz95uKAOk8Va/4L8SXbxWviGyt9d00mSKdXwVK/eU+o4wa+T/AI8/EL/hONct4/s1uDp4MJuomz9oI4LfTjivNbiY/bJ5IZZCHdsPkgsCe/1rS0XSxMwmuvliByF/vUAavgzSQJVv7pflXmNT3PrXaPd5rBF0FUBCAo4AHamNee9AG41371E9371hteCq9xfiKMuxwBQBo6vqq2lo8h5YjCivR/2TvCcl3rF34rv1Bt4AUgc9fMH3v0NeSeEvD2q/ELxNBp2nQsY85kf+GNe5J7V9beMbix+E/wAIZrfTCqvDD5MGePOkPBJ98GgD5l/aG8TnxN8Rrzym32tiPs8LA9V65/M17t+yjAtn8P725lIVJZA5PoBmvkB3eRmd2LSSHknkmvtn4a6NL4b+BWy8UxXLWbyyqeNuSSP0NAHzb8bPGlx478bzKAUsLJ2ggjz0AOGb8SM1zolWKJUXooxWRNNt1a9kB+9K5z+JpHuvegCfU7pmAhjyWfjAr6s+CHhm2+G/w1vPE2tKFubiITyEj5o4yMBPzwa8E+BHhBvGfj62WeMvYWjCa59l5x+or6G/al1j+yfhoLSMhft0wt9g4+UDP9KAPly+8Tya38Rhr2qgTiS7EjIxyCoPA/IV7D+1tpUHk+F9Xtokjaa2xJtGM5A2/kK+d7OB7q6igj5eRgo+pr6a/atIj8G+EoWPzi3QY+iigD5iMhIxXZeAbryiyk964qtnw1c+TdEdzjHuaAOq8eXRmtxGuSzkKB6mvbvhH4DudH0/SNPvo8azquLi4iYcw2YO1gfcnBqT4WfDS10+xHjjx2gitrRfOtrWYcD0Zh+OMGvX/hZYXt79t8U63CYr/U2zFAw5toRwEHscA0Ad7DGsMMcScKihR9BT6KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAIBBBGQa+XP2kvhDc7brxN4Yi8xCTJdWy/eXPVl9R7CvqOhgGUhgCCMEGgD8rZFZHZXUq4OCCMEGm19yfFf9nzQ/F0kl/oZTStWf7zAfuXPqVAzn3rwrxJ+zZ410o/8S9bfVR6wHb/6FigDxCivTD8C/iOP+ZZuD/21j/8Aiqoaz8IvHOi2Ru9U0Ce2tgQDI0iEAn6E0AcFRXRReC9elkCR2Ds56AEUs3grX4ZCkunyK47EjNAHOUtdA3g3XVOGsHB9CRQ3g3XV+9YSD6kUAYAdh3p6zMK3D4N10KGNhIFPfIoHg3XSpYWEm0d8igDHW5Ip5utylW6GtZfBuuMDtsJDjrgikHg/WicCxkJ9iKAMvT7280258/TLye1mxgSQyFGx6ZFAkknuZJ7uV5ZXyWd23Fie5JrVXwdrZYKthIWPYEUp8H66rbTYyBumMigB3hPxXrfhC6ll0O7aEyjDDJKsPpmo/FPiLV/FupJe63cedMqhAewX6VJJ4O1+Jtr2Eqn0JFOfwfr8YBewlUHpkigCOO7CqBnpUgvAO9OHhHXxH5jafL5f97IpyeEtddSyafMVHUgjigDNvYIrg70wj/oaz4ZLmwukmt5ZIZ4zlZI2KkH2Iro4vCmuyEiPT5mx1wRQPCutyNsGnSsf7uRQBgX2qX2o3wvNRu57q5BB8yVyzce5r6k0L40eF9bsPCdhrV3cWs+mzpPK8iM4kZVIxn8a+dJfB2shyn9nSrJ02kiopfBuvxNtfTpQfTIoA+jfE/7TzWfjEwaRZR3GhROFZzw8g7kelerarrGnap8Q/AWopd26wvZ3U3Mq/LujQgE+tfCN14d1e1x9osJkz6io2s9U2qrQ3W0fdyDgUAfUvxG/aQutB8cy6ZpFnBPp1rJ5czlgxkwedpFX/EHxw8I2euaR4msZpZbu5txBdWyodyRAlhlsc818mw6FqlwT5dlM3qcVbj8H67J93TpT+IoAf4+8Q/8ACS+NNU1uESxrdTmVA75ZRxjn8KzNQ1LUdYmR9RvLm8kUbVaeQuQPQZ6VqxeD9YJw2nys3oCKnHhbWEO1dNmU+mRQBk2drHEweUhm9PStE3gxwcCp5fCutxnEmmzqT6sKafCusrgvp04B6fMBQBAbz3qNrzjrVpvCmr7dx064C+u4YpP+ES1gru/s642+u4YoAotee9Ur+4Mihe1bQ8I6uwJGnXBA6kMKRfCGrOcLp1wx9mBoA9O+Ffxq0XwF4XTTofDDz3ZctLcrOFMmexOM4rl/jL8Vrr4iz2saWhsdOt/mWAvvO/GCc1zA8IaqTgadOT6bhSnwhqobadOnDem4ZoA52GYwzxyoFJjIIDDI4r1jUPj/AOMdQ0O40u5On+RNB5DFIMHbjHHPtXFN4Q1NThtNuAfQsKVvCGqL97TbkfVhQBzDMWZmPVjk02upPhDU1UE6bcAHoSwoPhDVAoY6bc7T33CgB3gDx7rfgO7u7jw/JCkl0gjk82PeCAcjv70vjnx/r3jZ4Dr1wkgh+4sa7VHvjNMHg/VCu4aZc7fXcKs2fgHXb1He10e6lVPvEMvFAHK2F3LYXsF1Bt82Fw67hkZFdj8RfiVrPj220yHWVgUWKFE8pNuenX8qsaJ8J/F3iBJW0bQ7iVYW2yM0igA+nJrsfDvwM16CXPiDwnq1wnYWt1Ev8zQB47omj6hrl/FZaVay3NxIwVVRc8n1Pavrf4LfAK28LOmveNWimvYhvS2ODHDju3UNW14OXVPB1qIdA+Flzbtja0wlh8x/947ua2jp3i/x/crD4ht5fD3h9D+9tVkHn3HsWUkbfagB6tL8TfEKiJdng/S5+c/8vsq9sd0wQfTIr1VVCqFUYAGAKr6ZYWul2EFlp8EdvaQLsjijGFUegqzQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFY/i3QYPEuhXGmXTMkcuDuXqCDkVsUUAfMS6avgTxstprDxzoEEgdem0+uai1nWNL1LxnDdLEEs1wPb61B8Vbp7rx3qhkOfKkMQ+gNckCD0oA7jxrfaNceILFtPA+zpjzCDUnj+/0W5trGPTFBcffI7CuE60lAHoPiLUNCfwXBBaBTfYGQCKfo+o6FD4LliuQpvduAMjOa87zRntQB3vgO+0S10y9Gp7RKVO3J71B4Fm0f+2rl9RAWEsSgPpXE0uce1AHaaXe6NB42mmcD7Dn5SelRa1qOly+NkubZB9hBGT2rj+lLQB3HjnVtIvdSsDp8YMUfMhHQ07x5q2j32mWMWmovnKBvIrhfwooA9BuNZ0T/AIQg2qIPtpGPcUzwrrGjWfha5hvFU3RUhQTzmuApfwoA7rwBq2j2L3jami/PkpmqHhvUdNh8WTXN2gFozkrnpiuUooA7TVtV0mfxvHdQxgWSkAnPFHjjVNJvdds5NOQeRGPnIri6SgDu/HWr6PeaXZQ6dGplUAuRip9Z1HQm8FRQ26ob3jIAGa894zRmgD0TQNQ0KDwfcRXQT7YVIA461T+HN5pFqLr+1tuTnbk9q4fvQTigDrPD13pa+MZZ7oYsmY7cntU2vX+kv4xjntUBs1I3HtXHfnSGgDuvHGraPf6np509AIoyPMI/CneOr/RZ9MsY9NCmYAbyD0rg+KKAPQtY1DQm8ERwW4U3xAGBRpuoaFH4HlimA+2lSAD1rz7tRnigDv8AwZqGh22gXa6iF88g7R3qv4AvdGt728fVAAjFimf0rh6M0Adl4fvNJXxdPNchRZljtzT9QvtHk8dJPEB9gBxnPFcUCO1FAHb+M77R5/E1m+ngfZkxvI6VN47v9Euf7PXTlB2kGQj0rgsilzmgDvfGN/ok3hyxhsADcjG/HpUmq6hoTeBo4YVU32BwDzXn2aTNAHomkajoUPgqWK5Cm924AyM5rK8LeNLTwxpF8Ly3DqVOD6VyNI1rHe4t5v8AVyEBvzoA+mPgesU/w+sNTWDyZtQ3Tyj1O4gfoBXfVS0W0gsdIs7a1jWOGOJQqr0HFXaACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDxX4ieGrWx+IOm6xep/xKLqUC4bPCvycn0HSrHxTtfDt/qnh2OGW0/e3AjlMDrjyvU4969Y1TT7XVLKS0voUmgkGGVhmuIHwh8KDB+z3WR0P2huPpQBzvxP8KeH7U6G0apA0tzHC4D4zEc5P/16i+LnhfQ9P0XTJrVUhlMyRghvvIeprqr/AOF2h3QBnfULlkGE827Zto9BmsvUPhpb3IVZor25VBhBNfFgo9BnpQBm/Ezwtodh8Pbe6s4kS4j8vypA3LbiNx9+Kb4j8L6LbfCIX8EC/a0t1lSUNyXJGfr9KkvfhvdXEKwyQXk0CDCRyX5Kr9ATVeb4b6o9sLcxXRtlGBC19lB+GaAJbLwtocnwgS+khVbprYTPLu5D/wCe1N8F+CdCv/hv9vuowbqWN3aUuR5ZBIHH0FU3+G2ufZzbItwLQ/8ALH7b8n5ZxUH/AArXxRFF9ntZporRhh4Fu/lb8M0AbPw28N6Fd+Arue5SOSdy4mctyuCcH24o+EWhaDcaHqjziKWTzJI3LNnbGDgH2471gxfDHxNBE8VsXhif76JdYVvqM81DD8LfFFsHFqfJVxhxHcBdw9DzzQB0nwk0PQZjrxKxTMlw8ShmziIdD/8AXrP+GOjaJL4512ECOaGBmFurHI25HI9e9ZVt8LfFdrvNowgLjDeXcAbh6Hmm2/wp8VWsnmWzrDJ/ejnCn+dAGv4a0fSH+MmrWbRxNYwRh4ELfLv46evU8VK2i6G3xnW1ZYhbeWZPL3YXzABgfz4rCj+FPiyOfzo5lSbOfMEw3fnmnD4U+KzcfaGmU3Gc+Z5w3Z9c5oA1vHejaGnxM0uL93FHLgzIrYAIxj6ZrQ+J+h6FF4h8ObUhiM0+yZVbHyADbkdue9czP8KfFNxcefPIsk3XzHmBb88064+FXiq6lWS6nErrjDPMGIx070Abfxm0bRrQ6G9qkUUrzCNwjf8ALP1x/Wj426TpWn+ENOm06CJLgSoiFDklCDn6/WsW5+FXim6ZWubgTsowpeYHA9BzSz/CvxTcIi3Fz5qoMKrzZC/TmgDd+IHhXQ7H4bwXVpCkU6RoUcNksSMke/NWNZ8JeHoPhhHcQqiOIVl87fk7yASPz7Vz83wu8V3FstvcXrSQL92J58qv0GaVvhX4oe0FrJfsbUHIhM2Uz9M0Ab3g3wnoF58M3ubqNJJJImd5d+CrAZxn61R+DHhvSNW8K31zqNuslwZGjbc2NqAZH/66ow/CvxRFatbRai0dsxy0Sy4U/hmn2nwo8S2iMlpq7W6P95Y3IDfXBoA0vg7oeiTtrbXAimnjnaJNzf8ALLA5x9e9L8NfDHh+88Q+Id0aTrBKI4VL5G0jLfrWZa/CLXrVme21byGYYYxnBI9+aks/hFr9nIz2euG2dvvNESpP5GgCXwL4Z0Kf4h63bOEmjtseQhbg5+99cVa0bwr4fb4p6la4R4oo1kji38FzncPwqla/B7W7e4Nxb68YLgnmVAQ3PXkGpY/g5rMd19qj8TFLrOfOVCHz9c0ATWXhXQP+FtXNnhDCkIlWLfx5hPI/+tUGp+F9CX4t2lkoQW8kYkeINxvz938qcnwa1dbv7Wvidlu8584Rnfn65oPwZ1V7v7U/idjdA7vOMRL59c5oAZ458MaLb/EbQrWCNY4LsE3CB+CQcAe3FN+J/h3SbDxb4bhtYFiiupRHOoPVQQB9KsTfBvVJ7gT3Hih5ZxyJGiJYH65p0/wc1S5lSS68USzyIfleSMkr9DmgCt8XvDuk6bqfhwWVusX2iYxTBTnKDGP5nmnfGLw1pGlRaE+n26xNLcLA+GzmPH+eas3Hwd1K7ZWvPE8s7J90yIWK/TJp1x8Hr+62/bPEs1xs+75is236c0AU/jP4c0nR9F0iXTbdYZGnWIkNnKYJ/wAmpPi34b0jS/Bem3VhbrFcCWKMMDklWGT9at3HwfvLtVW98Rz3Cp90SBmC/TJp83whurmNY7zxDcTxL9xJNxC/QZoAzvib4b0jTfhvYXtjbrHdjysOGySGGW+tP8d+G9IsPhPb39pbKt2scTq4bJLNjd9e9aMnwhlniWG6165mgUfLG5Yqv0GafJ8JHlgFvPrt1JbL92JmYqPwzQB5bL8NNSltB4oa/js9GSxjfyB8zTyZOc5+6ORV34T+FJPEPiBJpkP2C2O6RiOG/wBnPrXp9r8KIRtgvdWvZ9OAH+jCVgv5Zr0HSdMtNJs0tbCBIYV7KMZ9z6mgC2ihEVV4AGBS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXnXxJ+LWgeBNT0/T9Rl33N1IA4XpCmRlm9Otei15l8b/hZZfEXQWEYSDWbcFrafA5P91j6HjntQB6Jpt9balYwXljKs1tMgeN1OQwPSrNfHnwT+JOo/DDxJN4L8bpKlj5uxHYkmBicZHqp9a+wIJY54UlhdZInAZWU5BB7igB9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXEfFT4j6T8O9Hju9TJknmbbDAn3mPr9K7euR+JvgPTPH/h2TTNUUI4+aGcLlom9RQBr+E/EOn+KdCttV0mdJradcgqeh7g1r18TeDvEuv8AwD8ey6Jr6PLok0mZACSrJ/z0T1IHavszRtUs9Z0y3v8ATZ1ntZ1Do6nOQaALtFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBwPxj+IsXw20C11Oexe9WecQ7EcLjjOf0rx/8A4axsP+hbuP8Av8K+jNc0LS9etkt9ZsLe9gRt6pMm4A+tYv8Awrfwb/0LWl/9+BQB4b/w1jYf9C5cf9/hR/w1jYf9C3cf9/hXmPxJ0fTbT9o+30y1sYIdPN7aobdFwhBK5GPevsA/DjwaTn/hGtL/AO/AoA8N/wCGsbD/AKFy4/7/AAo/4axsP+hcuP8Av8Kg/a88K6FoPg/RptG0mzspXvCrPDGFJGzoa7b4A+CPDGq/CjQrvUdC0+4upIiXlkiBZjuPU0Ach/w1jYf9C3cf9/hR/wANY2H/AELdx/3+Fe5f8K38G/8AQtaX/wB+BR/wrfwb/wBC1pf/AH4FAHhv/DWNh/0Ldx/3+FH/AA1jYf8AQt3H/f4V7l/wrfwb/wBC1pf/AH4FH/Ct/Bv/AELWl/8AfgUAeG/8NY2H/Qt3H/f4Uf8ADWNh/wBC5cf9/hXuX/Ct/Bv/AELWl/8AfgV87ftW/C/SNC0u28SaDbx2SeYIJreIYUk9GAoA1/8AhrGw/wChbuP+/wAKP+GsbD/oXLj/AL/Cup+AGjeF/GPw6sr3UvDelG9jJikYQD5scA15h+1/4d0bQJ9BGi6Za2IkD7/ITbu+tAHTf8NY2H/Qt3H/AH+FH/DWNh/0Ldx/3+Feo/D7wB4Tu/Avh+4ufD2myzSWMLu7QgliVBJNdD/wrfwb/wBC1pf/AH4FAHhv/DWNh/0Llx/3+FH/AA1jYf8AQuXH/f4V2/xv8DeF9M+GOvXVhoOnwXMds7JJHEAynHUGvNP2PvDGia/o3iJ9a0u0vnimiVDPGGKghs4oA4P40fFPw78R7OOVfDstlrMPCXfmjlfRgOvtnpWp8I/2hL7wZof9lazaS6paxDFuQ4DRj0JPUV9OeJvh54Qg8N6tLF4d0xJEtJWVhAMghCQRXzP+yNoOk674s1yHWdPtr2KO2RkWdNwU7jyKAO4/4axsP+hbuP8Av8KP+GsbD/oW7j/v8K9y/wCFb+Df+ha0v/vwKzNc8KfDrQ7Yz6ro2jW0f+3EufyoA8g/4axsP+hbuP8Av8KP+GsbD/oXLj/v8K9Q8NaV8L/E0kkeiaVo908f3gtvjH514B+1r4f0jQvEGgx6Np1tZJIrFxCm0NyOtAHaWv7VdjcXUMI8OXAMjqmfOHGTivpHTboXthBchdolUNj0rgPAvgDwnP4Q0K5l8Pac87WkUhkMIyW2g5z65r0WKNIo1jjUKijAA6AUAOooooA434s+OY/h74Rk1yaze8VZUi8pWCk7jjOa8T/4axsP+hbuP+/wr6Q1nSNP1uyNnq9nDeWpYMYpl3LkdDisH/hW/g3/AKFrS/8AvwKAPDf+GsbD/oW7j/v8KP8AhrGw/wChbuP+/wAK9y/4Vv4N/wCha0v/AL8Cj/hW/g3/AKFrS/8AvwKAPDf+GsbD/oW7j/v8KP8AhrGw/wChcuP+/wAK9wf4ceDQjH/hGtL6f88BXyP8A9F0zVPj9eafqFjb3FipvMQSJuQbScce1AHpH/DWNh/0Ldx/3+FH/DWNh/0Llx/3+Fe5f8K38G/9C1pf/fgVFc/DLwXPbyRN4b00K6lSVhAI+lAHiX/DWFh/0Llx/wB/hR/w1jYf9C3cf9/hXk/ifw9Z/DH41RWklrDqGkCdWSG5XfujPY++a+xrb4eeDZreKYeGtLG9Q2PIHcZoA8R/4axsP+hcuP8Av8KP+GsbD/oW7j/v8K80+H+jabdftB3WnXNjby2AnlUW7LlAA/HFfXv/AArfwb/0LWl/9+BQB4b/AMNY2H/Qt3H/AH+FH/DWNh/0Ldx/3+Fe5f8ACt/Bv/QtaX/34FH/AArfwb/0LWl/9+BQB8s/FL43eGviFoEllqPheZbxVP2a680bom+o5I9q5v4KfGnUPh151ndRSahpEgytvuwUbsVJ6D1FfZQ+HHg0f8y1pf8A34FfH3wh0bTb/wCPVxp97YwT2IkuAIHXKDDccUAe/wDwq+Pdp4/8Ww6HDo81o8kbuJGkDD5RnpXt1YGj+DfDmi3ou9J0axtLoAgSwxBWAPXmt+gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooACQASSAB1JqjperWGqiU6ddw3IibZJ5bZ2n0NV/Fmktr3hvUNLjupLRrqExiePG5M9xXwk2seNPgt4k1XS7e5e3kmzliuVk9HGRyaANf4j6xYn9pD+0rm4RLK3voWkk6hQhGf5V9Rn44/DsE/wDFS2p/4C3+FfPfwY+B8/j2KfxF4ruZYrS5dnTZ96ViTlj+Nepf8MweEf8An8vf0oA4P9qn4heF/GHhPSLXw7q0N7PDdmR0QEFV24zyK9q/Zt/5I54e/wCuR/8AQjXzx+0V8ItE+HnhvTb7SJ7iSW4uTEwkxjG3NfQ/7Nv/ACRzw9/1yP8A6EaAOh+I/jzSPAOhPqWsyE9o4EI3yH0FfOd/+1JrUtwx0nQ4Tb5+XzUYt+ODWR+1bcz638VtN0mSQpDFGsKeg3HOf1r6k8AeDND8L+HbO10qwhjBiVnYjcWYjJOT70AfNP8Aw0/4s/6AVn/36f8AxqWz/aa8Vz3lvC2h2YEkqoT5b9CQPWvrb7Jb/wDPCL/vgUC1twciCLP+4KAPN/Gnxp8L+D9aXStUkla68sO/ljIQ/wB0+9fPXx0+LLfFI2Phvwpp9xJamUMdyjfLJ224PT61337UXwjk1eN/Ffh6FpLyIZu7dQT5i/3h71yfwI+KfgfwxbR2eraAmm6ovyNex/MH923Hg/SgD6E+CPg+XwT8P7DS7vabsjzZiPVucfhXhv7bf/Hz4d+j16trPx+8DabaGZNQ+2Nj/V25Vm/nXyn8cfilJ8StXgdLMWtja5EAJy5B7tQB9wfDP/knnhv/ALB8H/oArV13W9N0Gwe91i9hs7VPvSStgCsr4Z/8k88N/wDYPg/9AFfK/wAarjUfiJ8eYPB32o29qk4tEH8IIBJb64oA9P8AjP8AFnwTrfw71vTdK162ubyaBkjjQN8xI7ZFed/so+PvDXg7SNfi8R6pDYyXE0TRK4J3ABgeg961fiP+z54d8I/D/U9Vgv7ye8tYGkUyBQGYD2rj/wBnL4UaN8RdM1i41ma4ja0ljSPy8YIYEn+VAH0H4i+NXw/uvD2qW8HiO2aaW1lRF2t8zFCAOnvXiv7FRz4y8QEf8+qf+hmu81v9mrwpY6JqF3Hd3pkgt5JVBxjKqSP5Vwf7FIx4x8QD/p1T/wBDNAH1T421+Hwv4U1PWrkExWcJkIHfsP1Ir4v8J+HfFPx78X3d9qN8UsYGJeVydkYPIRBzg96+sPjpplxq/wAJ/EdnZo0lw9tlEX+Igg4rwz9jXxXpemwax4fv50t764uBPEXYAOAoXAz3oA9R+EXwbh+HWtT3ttfvcpKm0q55Bx9BXkf7aH/Iy+Hf9x/5rX1fqOoWem2rXN/cxW9uoy0kjBQB9a+Jv2nPHOj+MfF2nx6HKZ4rIFGmBBRySPukfSgD7E+H3/IjaB/14w/+gCt+sD4ff8iNoH/XjD/6AK36ACiiigAoyD0NU9ZS6l0i9TT5PKvGhcQyYztfB2n88V8zfAn4ka1a/FHV/DPjbUZbq5nlMMUkoChHQtn068UAdr8evih4p8C6tZ23hzTLa7hmTczSxO5B/wCAkV4/L+1B44hmMU2laRHIOCrQyA/+hV9nPFG5y6Kx9xmvl79srwpYW1hpniW2jEV6Zvs8m0YDDqD9aAOe/wCGjPiMV/5F/TyCOv2aX/4qsH9li5lvfjx9quFCzTw3UrqBgBmGSPzNfVnwn1E6/wDDjRtRu4ojPLbgMdg7cf0r5f8A2cP+Tj77HHN9/M0AfaF9dRWVnNc3DhIYULux7ADNeKXv7S3guCCd4hdzuhIVUUfP6Ec9K9p1Czhv7Ge0uV3QzIUcexGK+Ffib8PX+FfjyO9u9JXVvDTS74kk3CNlOfkZh3FAGh4cstX+NnxiGrvZvHpccyySkjAjjB6Z9a+34YxFCka/dRQo/CvDPh18bvh3HoyW9pGuhKgwYZcKM+xzyKi8b/tK+GtItZY9BjfU77B8sjHlZ9yDmgDyD4bf8nL3n/XzN/6GK+2iQASSAB1Jr4N+A+py6z8cotRnVUlunklZV6AlgcCvsz4l6hJpngPW7qEfvFtZAD6ZUjNAHlHxS/aK03wzq8+kaDatqF9ASskvWIN/d6gkivOz+0/4rJ+XQrPHb92/+NUf2TPB+m+KfEmqarrsYu5LMK8Suf4yTkmvseOxtY0CJbQqo4ACCgD5GH7T/ivP/IBsz/2yf/GuT/Z0vZNS+OMV5MoSScTSMo6Ak5r7n+y246QRf98Cvif4JgD9oy5AAA8y56f71AH29RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFeafHD4Y2vxE8ONHEqRaxbjdbTEdf9gn0Nel0UAfFvwN+JGofDHxTL4U8XrNHpzTeWwkz/o7ZwCM/wknOfSvs+CaO4gjmgdZIpFDo6nIYHkEV4n+0H8Gk8dQLquhJHFr0YCkH5VmX/aPqBXd/CHwzqnhLwRZ6Xreotf3SDduYf6sED5Ac8gUAeUftsf8AIkaH/wBfx/8AQDXffs2/8kc8Pf8AXI/+hGtr4ofD3S/iJpdrY6y8yRW8plQxtg5xitfwV4atPCPhuz0XTmdra1Xahc5PXNAHz/8Atf8AgW5uIrTxdpaMTaKI7kRjJAyTvPsMV6F8APijYeOfDcFnPKkOuWiBJbcnllHAZfXjrXqWo2VvqVjPZ3sSzW06FJI2GQwPavmOf9nzW9F+J9pf+D9SNnpBfzTNuO+LBBKYzyDQB9SUUyBXSGNZX8yQKAz4xuPrin0ADAMpDAEHgg15R4/+BXhLxdcy3n2b7BfyctNADg/8ByBXq9FAHzxpX7Lfh21uVe+1S5vIgeYzHsz+IavOf2rfCWieELfw5ZeH7FLSHDk4JJY+5PNfZtef/FH4WaN8RZLJtZknQ2udnlNjOaANv4Z/8k88N/8AYPg/9AFeAftB/DDxJB41Txr4LikmmyJHSAZlWTn5gO4xX0voemw6Po1lptsSYLSFYU3HJwowKu0AfEfifXPi94+0+LQ7zRtSS3YgSf6IUDdvmbbwK+kvgL4Ak8AeDI7S9KHUrgiS52HIB5wPyNelYooAyvFn/Iq6z/15Tf8AoBr5Q/Yr/wCRz8Qf9eqf+hmvrzULVL6wubSXPlzxNE2OuGBB/nXn/wAMfhHofw81O8vdHkuGkuYxGwkfIABzQB6O6h0ZWAKsMEHuK+cPih+ziup6pJq3gu7WwuZGLNbElVye4bPFfSFFAHxvafs7fEO9mEOsazGtqThj9saXj6GuP+OPw1svhtdaHZ2t093NOGaWVl25II4xn3r74rzv4m/CjRfiDfWV1rEk6vaghPLcjOcf4UAdL8Pv+RG0D/rxh/8AQBW/VTR7CLStKtLC3z5NtEsSZPOFGBVugAooooAK+av2kPhLqV5qyeMfB0ZN9EA1xDCMPlTw6gdT619K0EZGD0oA+VfC37TVxpenJYeL9ImOo267HlBIaQjuy44Ncl408SeK/j5r+n6bomky2+kRPgdTGD/fdsccV9dXvg/w3fTGa80HS5pScl3tUJP1OK0NN0rT9LjKabY2toh6rBEqD9BQBS8KaHF4c8MWWlW/3LaEJ+OOf1r5D/Zx/wCTkL76338zX2sRkEHvXmPgv4NaD4S8ay+JdPkuGvpDKSHclf3nXj8aAPTqo61pNhreny2WqWsdzbSDDJIM/l6VeooA8A8Q/sw+F9Qu2l0u8n0yIn/VKpkA/EtWp4Q/Zz8IaHcx3F+r6pLGcr5uVGfXAPNe10UAfEPwugjtv2j7iGBFSKOeVVVRgABxgV9oa7p0er6Ne6fMBsuYXiOe24EZrz3RPgxoGkeN5PE9tJcG+kdnKlyVyxyeK9QoA+IfAWs3PwL+L19pespKulzuIpZNvLRZO1wO4zX2ppmoWmqWEN7p9xHcWsyhkkjbKsD71518bvhRp/xF0guoWDWrdD9nuAOv+y3qP8azf2cvAniXwRoNxD4k1AukpzFZZ3iH6Nnj6YoA9hr4h+Cn/Jxlz/11uf8A0Kvt6vLvDHwW8P8Ah3xm/iSykuTeuzsVZyVyxyeKAPUaKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//9k=',
                       fit: [300, 300]
                  },
                  { image:'data:image/png;base64,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',
                  fit:[200,200]
                  },
                  {
                  image:'data:image/png;base64,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',
                  fit:[200,200]
                  },
                  {text: 'Ministry of Health and Family Welfare', style: 'header'},
                  {text: 'Mobile Academy and Kilkari', style: 'header',
                  pageBreak: 'after'},
               		{text: $scope.reportCategory, style: 'header'},
               		{text: excelHeaderName.reportName, style: 'subheader'},
               		'State - ' ,excelHeaderName.stateName ,
               		'District - ' ,excelHeaderName.districtName ,
               		'Health Block - ' ,excelHeaderName.blockName ,
               		'Duration - ' ,excelHeaderName.timePeriod ,
               		{text: $scope.matrixContent1, style: 'subheader'},
               		'The following table has nothing more than a body array',
               		{
               			style: 'tableExample',
               			table: {
               				body:
               					datapdf


               			}
               		},
               		{text: $scope.matrixContent2, style: 'subheader'},
               		'It is of course possible to nest any other type of nodes available in pdfmake inside table cells',
               		{
                        style: 'tableExample',
               			table: {
               				body: datapdf1
               			}
               		}

               			],
                    	styles: {
                    		header: {
                    			fontSize: 18,
                    			bold: true,
                    			margin: [0, 0, 0, 10]
                    		},
                    		subheader: {
                    			fontSize: 16,
                    			bold: true,
                    			margin: [0, 10, 0, 5]
                    		},
                    		tableExample: {
                    			margin: [75, 5, 70, 15]
                    		},
                    		tableHeader: {
                    			bold: true,
                    			fontSize: 13,
                    			color: 'black'
                    		}
                    	},
                    	defaultStyle: {
                    		 alignment: 'center'
                    	}


              };
              }
              else { var grid = $scope.gridApi.grid;
                                                var exportColumnHeaders = uiGridExporterService.getColumnHeaders(grid, uiGridExporterConstants.ALL);
                                                var exportData = uiGridExporterService.getData(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.ALL, true);
                                                var datapdf = [];

                                                for (i = 0; i < exportData.length; i++) {
                                                var tempcol=[];
                                                    for (j = 0; j < exportData[i].length; j++) {
                                                        tempcol.push( exportData[i][j].value);
                                                    }
                                                    datapdf.push(tempcol);
                                                }
               var docDefinition = {
                             content: [
                             		{text: 'Tables', style: 'header'},
                             		'Official documentation is in progress, this document is just a glimpse of what is possible with pdfmake and its layout engine.',
                             		{text: 'A simple table (no headers, no width specified, no spans, no styling)', style: 'subheader'},
                             		'The following table has nothing more than a body array',
                             		{
                             			style: 'tableExample',
                             			table: {
                             				body:
                             					datapdf


                             			}
                             		}

                             			],
                                  	styles: {
                                  		header: {
                                  			fontSize: 18,
                                  			bold: true,
                                  			margin: [0, 0, 0, 10]
                                  		},
                                  		subheader: {
                                  			fontSize: 16,
                                  			bold: true,
                                  			margin: [0, 10, 0, 5]
                                  		},
                                  		tableExample: {
                                  			margin: [0, 5, 0, 15]
                                  		},
                                  		tableHeader: {
                                  			bold: true,
                                  			fontSize: 13,
                                  			color: 'black'
                                  		}
                                  	},
                                  	defaultStyle: {
                                  		// alignment: 'justify'
                                  	}


                            };


              }
              //docDefinition = uiGridExporterService.prepareAsPdf(grid, exportColumnHeaders, exportData);

                              if (uiGridExporterService.isIE() || navigator.appVersion.indexOf("Edge") !== -1) {
                                uiGridExporterService.downloadPDF(grid.options.exporterPdfFilename, docDefinition);
                              } else {
                                pdfMake.createPdf(docDefinition).download();
                              }
              //uiGridExporterService.pdfExport(grid, rowTypes, colTypes);
            };

            var canceler = $q.defer();
            $scope.gridOptions1 = {
                enableSorting: true,
                showGridFooter: true,
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
                                                             { field: 'billableMinutes', name: 'Total billable minutes played',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                             { field: 'averageDuration',cellFilter: 'number: 2', name: 'Average duration of call', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[4].getAggregationValue()/grid.columns[3].getAggregationValue() | number:2}}</div>',   width:"*", enableHiding: false},

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
                                                     { field: 'billableMinutes', name: 'Total Billable minutes',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
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