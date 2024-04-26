(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory','$window','$q','uiGridConstants','exportUiGridService','uiGridExporterService','uiGridExporterConstants','$location', function($scope, $state, $http, UserFormFactory,$window,$q,uiGridConstants,exportUiGridService,uiGridExporterService,uiGridExporterConstants,$location, user){

            UserFormFactory.isLoggedIn()
            			.then(function(result){
            				if(!result.data && user){
            					$state.go('login', {});
            				}

            			})
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
				else{
					UserFormFactory.downloadCurrentUser()
					.then(function(result){
						UserFormFactory.setCurrentUser(result.data);
                        localStorage.setItem("accessLevel",$scope.currentUser.accessLevel);
                        localStorage.setItem("roleName",$scope.currentUser.roleName);
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
            var token = 'dhty'+UserFormFactory.getCurrentUser().userId+'alkihkf';
			var reportRequest = {};
			var ExcelData = {};
            $scope.sundays = [];
 			$scope.reports = [];
			$scope.states = [];
			$scope.districts = [];
			$scope.blocks = [];
			$scope.circles = [];
			$scope.datePickerContent = " Monthly";
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
			$scope.currentPeriodType = ['Year', 'Quarter', 'Financial Year'];
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
            $scope.reportHeaderName = "";
            $scope.certificateList = [];
            var parentScope = $scope.$parent;
            parentScope.child = $scope;
            var fileName;
            var dateString;
            var toDateVal;
            var excelHeaderName = {
                stateName : "ALL",
                districtName : "ALL",
                blockName : "ALL"

            };
            var rejectionStartDate = new Date(2017, 7, 31);
            var rejectionStart;


            $scope.downloadType=["Monthly Certificate Generation","Single Certificate Generation"];
            $scope.selectedDownloadType=$scope.downloadType[0];
            $scope.certificateDistrict=[];
            $scope.certificateBlock=[];
            $scope.downloadBulk=true;
            $scope.selectedStateId='';
            $scope.selectedDistrictId='';
            $scope.selectedBlockId='';
            $scope.certificateDate='';
            $scope.downloadBulkCertificateUrl='';
            $scope.disableCertiState=false;
            $scope.disableCertiBlock=false;
            $scope.disableCertiDistrict=false;
            $scope.selectedState=null;
            var certificateRequest={};

            $scope.certificateDownloadType=function(selectedDownloadType){
                if(selectedDownloadType==$scope.downloadType[1]){
                    $scope.downloadBulk=false;
                    $scope.zipFileGeneratedSucessFully = false;
                }else{
                    $scope.downloadBulk=true;
                    $scope.fileDownloadedSucessFully=false;
                }
            }
            $scope.getCertificateInBulk=function(){
                certificateRequest.date =$scope.certificateDate;
                certificateRequest.stateId =$scope.selectedStateId;
                if($scope.selectedDistrictId=='' || $scope.selectedDistrictId==null){
                    certificateRequest.districtId=0;
                }else
                certificateRequest.districtId =$scope.selectedDistrictId;
                if($scope.selectedBlockId=='' || $scope.selectedBlockId==null){
                    certificateRequest.blockId=0;
                }else
                certificateRequest.blockId =$scope.selectedBlockId;

                $scope.errorMessage = false;
                $scope.zipFileGeneratedSucessFully = false;
                if($scope.certificateDate==''){
                    $scope.errorMessage = true;
                    $scope.message = "Please select Month";
                }else if($scope.selectedStateId=='' ||$scope.selectedState==''){
                    $scope.errorMessage = true;
                    $scope.message = "Please select State";
                }  else{
                    $scope.isBeingGenerated=true;
                    $http({
                        method: 'POST',
                        url: backend_root + 'nms/user/asha/bulkcertificate',
                        data: certificateRequest, //forms user object
                        headers: {'Content-Type': 'application/json', 'csrfToken': token}
                    }).then(function (result) {
                        $scope.isBeingGenerated=false;
                        if (result.data.status != "success") {
                            $scope.errorMessage = true;
                            $scope.message = result.data.message;
                        } else {
                            $scope.zipFileGeneratedSucessFully = true;
                            $scope.errorMessage = false;
                            $scope.zipMessage=result.data.message;
                            var zipDirName = result.data.fileDir;
                            zipDirName = zipDirName.replace("&","%26")
                            $scope.downloadBulkCertificateUrl = backend_root + 'nms/user/certificate/bulkdownload?zipDir=' + zipDirName;
                        }
                    },function(error){
                        $scope.isBeingGenerated=false;
                        $scope.errorMessage = true;
                        $scope.message = "Something went wrong :(";
                    });
                }
            }
            $scope.getDistrictForCertificate = function(state){
                try {
                    $scope.selectedStateId = JSON.parse(state).stateId;
                }catch (e) {
                }
                if( $scope.selectedStateId==undefined){
                    $scope.selectedStateId= state;
                }
                $scope.certificateDistrict=[];
                if( $scope.selectedStateId !='' &&  $scope.selectedStateId !=null) {
                    return UserFormFactory.getDistricts($scope.selectedStateId)
                        .then(function (result) {
                            $scope.certificateDistrict = result.data;
                            $scope.certificateBlock = [];
                            $scope.certificateDistrict.sort(function (a, b) {
                                if (a.districtName < b.districtName) {
                                    return -1;
                                }
                                if (a.districtName > b.districtName) {
                                    return 1;
                                }
                                return 0;
                            });
                            if ($scope.userHasDistrict()) {
                                $scope.selectedDistrict = $scope.certificateDistrict[0].districtName;
                                $scope.selectedDistrictId=$scope.certificateDistrict[0].districtId;
                                $scope.getBlockForCertificate($scope.certificateDistrict[0].districtId);
                                $scope.disableCertiDistrict = true;
                            }
                        });
                }
            }
            $scope.onSelectBlockForCertificate=function(blockId){

                $scope.selectedBlockId=blockId;
                // $scope.selectedDistrictId=$scope.selectedDistrict;
            }
            $scope.getBlockForCertificate =function(districtId){
                $scope.selectedDistrictId=districtId;
                $scope.certificateBlock=[];
                if($scope.selectedDistrictId!='' && $scope.selectedDistrictId!=null) {

                    return UserFormFactory.getBlocks(districtId)
                        .then(function (result) {
                            $scope.certificateBlock = result.data;
                            $scope.certificateBlock.sort(function (a, b) {
                                if (a.blockName < b.blockName) {
                                    return -1;
                                }
                                if (a.blockName > b.blockName) {
                                    return 1;
                                }
                                return 0;
                            })
                            if ($scope.userHasBlock()) {
                                $scope.selectedBlock = $scope.certificateBlock[0].blockName;
                                $scope.selectedBlockId=$scope.certificateBlock[0].blockId;
                                $scope.disableCertiBlock = true;
                            }
                        });
                }
            }

            $scope.getStatesForCerti = function(){

                    return UserFormFactory.getStates()
                        .then(function(result){
                            $scope.certificateStates = result.data;

                            $scope.certificateStates.sort(function(a, b){
                                if(a.stateName < b.stateName) { return -1; }
                                if(a.stateName > b.stateName) { return 1; }
                                return -1;
                            });


                            if($scope.userHasState()){
                                $scope.selectedState =$scope.certificateStates[0].stateName;
                                $scope.getDistrictForCertificate($scope.certificateStates[0].stateId);
                                $scope.disableCertiState=true;
                            }
                        });
            }

            $scope.getStatesForCerti();
            $scope.getCertificate = function() {
                $scope.errorMessage=false;
                $scope.fileDownloadedSucessFully=false;

                if($scope.state != null){
                }

                const l = '' + $scope.mobile_number;
                if(l.length==10) {
                    $scope.isBeingGenerated=true;
                    $http({
                        method: 'POST',
                        url: backend_root + 'nms/user/asha/certificate' + "?msisdn=" + $scope.mobile_number ,
                        headers: {'Content-Type': 'application/json', 'csrfToken': token}
                    }).then(function (result) {
                        $scope.isBeingGenerated=false;
                        if (result.data[0].status == "success") {
                            $scope.fileDownloadedSucessFully = true;

                            for(var i=0;i <result.data.length;i++){
                                result.data[i].downloadCertificateUrl = backend_root + 'nms/user/downloadCertificate?fileName=' + result.data[i].file + '&rootPath=' + result.data[i].path;
                            }
                            $scope.certificateList = result.data;
                        } else {
                            $scope.errorMessage = true;
                            $scope.message = result.data[0].status;
                        }
                    });
                } else {
                    $scope.errorMessage = true;
                    $scope.message = "Please Enter Valid Mobile Number";
                }
            }

            $scope.popup2 = {
                opened: false
            };

            $scope.popup3 = {
                opened: false
            };

            $scope.open2 = function() {
                $scope.popup2.opened = true;
                				var currentDate = new Date();

                				if($scope.showWeekTable()){
                				        if($scope.getSundays(currentDate) >0){
                                            $scope.dateOptions.maxDate = new Date().setMonth(currentDate.getMonth());
                                        }
                                        else
                                            $scope.dateOptions.maxDate = new Date().setMonth(currentDate.getMonth() - 1);
                                        $scope.sundays = null;


                				}

                				if($scope.showWeekTable() && ($scope.format == 'yyyy-MM-dd' || $scope.format == 'yyyy-MM' )){
                                    $scope.getSundays($scope.dt1);
                                    $scope.sundaysTable = true;

                                }
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
				return ($scope.report == null || $scope.report.reportEnum == null );
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

			$scope.showWeekTable = function(){
				//return (($state.current.name == 'Kilkari Message Matrix' || $state.current.name == 'Kilkari Listening Matrix' || $state.current.name == 'Kilkari Usage' || $state.current.name == 'Kilkari Message Listenership' || $state.current.name == 'Kilkari Thematic Content' || $state.current.name == 'Kilkari Aggregate Beneficiaries')&& ($scope.periodDisplayType == 'Week'));
				return ( $scope.isAggregateReport() &&  !($scope.report.reportEnum == 'Kilkari_Repeat_Listener') && ($scope.periodDisplayType == 'Week'));

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

			$scope.footerBold = function(){
            	return $scope.report.reportEnum == 'Kilkari_Thematic_Content'|| $scope.report.reportEnum == 'Kilkari_Listening_Matrix';
            }

            $scope.isIE9 = function(){
                return UserFormFactory.isInternetExplorer9();
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
                $scope.setDateOptions();
                if($scope.periodDisplayType == 'Year'){
                    $scope.periodTypeContent = " Year";
                    $scope.dateFormat = "yyyy";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'year';
                    $scope.datePickerOptions.minMode = 'year';
                    if(new Date().getMonth()>0){
                        $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear());
                    }
                    else
                        $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);


                }
                if( $scope.periodDisplayType == 'Quarter'){
                    $scope.periodTypeContent = " Year";
                    $scope.dateFormat = "yyyy";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'year';
                    $scope.datePickerOptions.minMode = 'year';
                    if(new Date().getMonth()>2){
                        $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear());
                    }else{
                        $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                    }

                }
                if($scope.periodDisplayType == 'Financial Year'){
                    $scope.periodTypeContent = "Select Start Year";
                    $scope.dateFormat = "yyyy";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'year';
                    $scope.datePickerOptions.minMode = 'year';
                    if(new Date().getMonth()>2){
                        $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear());
                    }else{
                        $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                    }

                }
                if($scope.periodDisplayType == 'Month'){
                    $scope.periodTypeContent = " Month";
                    $scope.dateFormat = "yyyy-MM";
                     $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'month';
                    $scope.datePickerOptions.minMode ='month';
                    $scope.datePickerOptions.maxDate = new Date().setMonth(new Date().getMonth() -1);
                }
                if($scope.periodDisplayType == 'Week'){
                    $scope.periodTypeContent = " Week";
                    $scope.dateFormat = "yyyy-MM-dd";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'month';
                    $scope.datePickerOptions.minMode ='month';
//                    $scope.datePickerOptions.maxDate = new Date().setMonth(new Date().getMonth() -1);
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
				$scope.setReportHeaderName($scope.report.name);
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
                if($scope.report.reportEnum == 'Kilkari_Message_Matrix' || $scope.report.reportEnum == 'Kilkari_Subscriber' || $scope.report.reportEnum == 'Kilkari_Subscriber_Report_with_RegistrationDate' || $scope.report.reportEnum == 'Kilkari_Listening_Matrix' || $scope.report.reportEnum == 'Kilkari_Usage' || $scope.report.reportEnum == 'Kilkari_Message_Listenership' || $scope.report.reportEnum == 'Kilkari_Thematic_Content' || $scope.report.reportEnum == 'Kilkari_Aggregate_Beneficiaries' || $scope.report.reportEnum == 'Kilkari_Beneficiary_Completion' || $scope.report.reportEnum == 'District-wise Performance of the State for Kilkari' || $scope.report.reportEnum == 'Aggregate_Whatsapp_Subscriber' || $scope.report.reportEnum == 'Aggregate_Whatsapp_Message' || $scope.report.reportEnum == 'Aggregate_Whatsapp_Opt_In_Report'){
                    $scope.periodType = ['Year','Financial Year','Quarter','Month','Week'];
                }
                else if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                    $scope.periodType = ['Month'];
                    $scope.periodDisplayType = "Month";
                }
                else
                    $scope.periodType = ['Year','Financial Year','Quarter','Month','Week','Custom Range'];
                if(( $scope.reportCategory == 'Kilkari Reports') &&  ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase()) > -1)  ){
                	$scope.datePickerContent = "Select Week";
                }
                else
                    $scope.datePickerContent = "Monthly";


                $scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                if($scope.report.name == 'District-wise Performance of the State for Mobile Academy') {
                $scope.dateFormat = 'yyyy-MM-dd';
                                    $scope.endDatePickerOptions.minDate = new Date(2015,12,01)
                                    $scope.endDatePickerOptions.maxDate = new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1);
                }
                if($scope.report.reportEnum == 'Kilkari_Cumulative_Summary'){
                    $scope.dateFormat = 'yyyy-MM-dd';
                    $scope.endDatePickerOptions.minDate = new Date(2016,11,01)
                    $scope.endDatePickerOptions.maxDate = new Date();
                }

                $scope.gridOptions1.exporterExcelSheetName = $scope.report.name;
                excelHeaderName.reportName = $scope.reportHeaderName;


			}

			$scope.setReportHeaderName = function(name){
			if(name == "Kilkari Repeat Listener"){
			    $scope.reportHeaderName = "Kilkari Repeat Listener Month Wise";
			}else if(name == "Kilkari Message Matrix"){
                $scope.reportHeaderName = "kilkari message matrix for only successful calls";
            }
			else{
			    $scope.reportHeaderName = name;
			    }
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


            $scope.cropCircle = function(name){
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
			         return true;
			    }
			}


			$scope.setDateOptions =function(){
                if($scope.isAggregateReport()&&($scope.periodDisplayType == 'Month'||$scope.periodDisplayType == 'Week'||$scope.periodDisplayType == 'Custom Range')){
                    var minDate = new Date(2016, 11, 01);
                }
                else if($scope.isAggregateReport()&&($scope.periodDisplayType == 'Year'||$scope.periodDisplayType == 'Quarter'||$scope.periodDisplayType == 'Financial Year')){
                    var minDate = new Date(2017, 00, 01);
                }
                else{
                    var minDate = new Date(2016, 11, 30);
                }

				if($scope.report != null && $scope.report.service == 'M'){
					minDate = new Date(2015, 10, 01);
				}
				if($scope.report != null && $scope.report.reportEnum == 'MA_Cumulative_Inactive_Users'){
                	minDate = new Date(2015, 12, 01);
                }
                if($scope.report != null && $scope.report.reportEnum == 'MA_Cumulative_Course_Completion'){
                	minDate = new Date(2015, 12, 01);
                }
                if($scope.report != null && $scope.report.reportEnum == 'MA_Anonymous_Users'){
                    minDate = new Date(2017, 01, 01);
                }
                if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Low_Usage'){
                    minDate = new Date(2016, 11, 30);
                }

                //In case of change in minDate for rejection reports, please change startMonth and startDate variable accordingly
                if($scope.report != null && $scope.report.reportEnum == 'MA_Asha_Import_Rejects'){
                    minDate = new Date(2017, 08, 01);
                 }
                 if($scope.report != null && ($scope.report.reportEnum == 'MA_Performance' || $scope.report.reportEnum == 'MA_Subscriber')) {
                    minDate = new Date(2015,12,01);
                 }
                 if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Mother_Import_Rejects'){
                    minDate = new Date(2017, 08, 01);
                 }
                 if($scope.report != null && $scope.report.reportEnum == 'Kilkari_Child_Import_Rejects'){
                    minDate = new Date(2017, 08, 01);
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
                    maxDate: new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1) ,
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

                 $scope.dt1 = null;
                 $scope.dt2 = null;
                 $scope.hideGrid = true;
                 $scope.hideMessageMatrix = true;
                 $scope.showEmptyData = false;
                 $scope.setDateMode();
				 $scope.clearFile();

			}



			$scope.setDateMode = function(){

			     $scope.setDateOptions();
                                 if($scope.periodDisplayType == 'Year'){
                                     $scope.periodTypeContent = " Year";
                                     $scope.dateFormat = "yyyy";
                                     $scope.datePickerOptions.minMode = '';
                                     $scope.datePickerOptions.datepickerMode = 'year';
                                     $scope.datePickerOptions.minMode = 'year';
                                     if(new Date().getMonth()>0){
                                         $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear());
                                     }
                                     else
                                         $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);


                                 }
                                 if( $scope.periodDisplayType == 'Quarter'){
                                     $scope.periodTypeContent = " Year";
                                     $scope.dateFormat = "yyyy";
                                     $scope.datePickerOptions.minMode = '';
                                     $scope.datePickerOptions.datepickerMode = 'year';
                                     $scope.datePickerOptions.minMode = 'year';
                                     if(new Date().getMonth()>2){
                                         $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear());
                                     }else{
                                         $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                                     }

                                 }
                                 if($scope.periodDisplayType == 'Financial Year'){
                                     $scope.periodTypeContent = "Select Start Year";
                                     $scope.dateFormat = "yyyy";
                                     $scope.datePickerOptions.minMode = '';
                                     $scope.datePickerOptions.datepickerMode = 'year';
                                     $scope.datePickerOptions.minMode = 'year';
                                     if(new Date().getMonth()>2){
                                         $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear());
                                     }else{
                                         $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                                     }

                                 }
                                 if($scope.periodDisplayType == 'Month'){
                                     $scope.periodTypeContent = " Month";
                                     $scope.dateFormat = "yyyy-MM";
                                      $scope.datePickerOptions.minMode = '';
                                     $scope.datePickerOptions.datepickerMode = 'month';
                                     $scope.datePickerOptions.minMode ='month';
                                     $scope.datePickerOptions.maxDate = new Date().setMonth(new Date().getMonth() -1);
                                 }
                                 if($scope.periodDisplayType == 'Week'){
                                     $scope.periodTypeContent = " Week";
                                     $scope.dateFormat = "yyyy-MM-dd";
                                     $scope.datePickerOptions.minMode = '';
                                     $scope.datePickerOptions.datepickerMode = 'month';
                                     $scope.datePickerOptions.minMode ='month';
                 //                    $scope.datePickerOptions.maxDate = new Date().setMonth(new Date().getMonth() -1);
                                 }
                                 if($scope.periodDisplayType == 'Custom Range'){
                                     $scope.periodTypeContent = "Start Date";
                                     $scope.dateFormat = "yyyy-MM-dd";
                                     delete $scope.datePickerOptions.datepickerMode;
                                     $scope.datePickerOptions.minMode = '';
                                     $scope.datePickerOptions.maxDate = new Date().setDate(new Date().getDate() - 1);
                                 }

			}

			$scope.clearState = function(){
				 $scope.state = null;
				 excelHeaderName.stateName = "ALL";
				 $scope.clearDistrict();
				 $scope.districts = [];
                 $scope.dt1 = null;
                 $scope.dt2 = null;
                 $scope.hideGrid = true;
                 $scope.hideMessageMatrix = true;
                 $scope.showEmptyData = false;
                 $scope.setDateMode();
                 $scope.clearFile();
			}


			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);
					$scope.clearDistrict()
					$scope.district = district;
					excelHeaderName.districtName = district.districtName;
				}
              $scope.dt1 = null;
              $scope.dt2 = null;
              $scope.hideGrid = true;
              $scope.hideMessageMatrix = true;
              $scope.showEmptyData = false;
              $scope.setDateMode();
			  $scope.clearFile();
			}



			$scope.clearDistrict = function(){
				$scope.district = null;
				excelHeaderName.districtName = "ALL";
				$scope.clearBlock();
				$scope.blocks = [];
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateMode();
                $scope.clearFile();
			}


			$scope.selectBlock = function(block){
				if(block != null){
					$scope.clearBlock();
					$scope.block = block;
					excelHeaderName.blockName = block.blockName;
				}
              $scope.dt1 = null;
              $scope.dt2 = null;
              $scope.hideGrid = true;
              $scope.hideMessageMatrix = true;
              $scope.showEmptyData = false;
              $scope.setDateMode();
              $scope.clearFile();
			}


			$scope.clearBlock = function(){
				$scope.block = null;
				excelHeaderName.blockName = "ALL";
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateMode();
                $scope.clearFile();
			}


			$scope.selectCircle = function(circle){
				if(circle != null){
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
               	if( ($scope.reportCategory == 'Kilkari Reports') &&  ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase()) > -1) && $scope.dt != null) {
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

			$scope.$watch('dt1', function(newDate){
                if($scope.wasSundaySelected){
                $scope.dateFormat = 'yyyy-MM-dd';
                $scope.wasSundaySelected = false;
                 return;
                }
                if($scope.periodDisplayType == 'Week'){
                    $scope.dateFormat = 'yyyy-MM';}
                if($scope.periodDisplayType == 'Quarter'){
                    $scope.quarterDisplayType = '';
                        if(($scope.dt1.getFullYear() == new Date().getFullYear())){
                            if(new Date().getMonth()> 0 && new Date().getMonth()< 4){
                                $scope.quarterType = ['Q1 (Jan to Mar)'];
                            }else if(new Date().getMonth() >= 4 && new Date().getMonth()< 7){
                                $scope.quarterType = ['Q1 (Jan to Mar)','Q2 (Apr to Jun)'];
                            }else if(new Date().getMonth() >= 7 && new Date().getMonth()< 10){
                               $scope.quarterType = ['Q1 (Jan to Mar)','Q2 (Apr to Jun)','Q3 (Jul to Sep)'];
                            }
                            else {
                                $scope.quarterType = ['Q1 (Jan to Mar)','Q2 (Apr to Jun)','Q3 (Jul to Sep)', 'Q4 (Oct to Dec)'];
                            }
                            }
                        else{
                            $scope.quarterType = ['Q1 (Jan to Mar)','Q2 (Apr to Jun)','Q3 (Jul to Sep)', 'Q4 (Oct to Dec)'];
                        }
                    }
               	if($scope.isAggregateReport() &&  $scope.showWeekTable() && $scope.dt1 != null) {
               		 $scope.getSundays($scope.dt1);
                     $scope.sundaysTable = true;
			    	 $scope.popup2.opened = true;
			    }

                if(!$scope.wasSundaySelected && $scope.periodDisplayType == 'Week'){
                    if((newDate != null) && newDate.getDate() == 1){
                        $scope.dt1 = new Date($scope.dt1.getFullYear(), $scope.dt1.getMonth() + 1, 0, 23, 59, 59);
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
				if($scope.dt == null && ($scope.reportCategory == 'Kilkari Reports') && ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase()) > -1) ){
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
				else if($scope.periodDisplayType == '' && ($scope.isAggregateReport() ) && ($scope.report.name != 'District-wise Performance of the State for Mobile Academy' && $scope.report.reportEnum != 'Kilkari_Cumulative_Summary')){
                   if(UserFormFactory.isInternetExplorer()){
                       alert("Please select a period type")
                       return;
                   }
                   else{
                       UserFormFactory.showAlert("Please select a period type")
                       return;
                   }
                }
				else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType != 'Custom Range' && $scope.periodDisplayType != 'Current Period' && $scope.periodDisplayType != 'Quarter' && $scope.report.name != 'District-wise Performance of the State for Mobile Academy' && $scope.report.reportEnum != 'Kilkari_Cumulative_Summary') ){
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
                else if($scope.dt2 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Custom Range' || $scope.report.name == 'District-wise Performance of the State for Mobile Academy' || $scope.report.reportEnum == 'Kilkari_Cumulative_Summary' )){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select an end date")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("Please select an end date")
                         return;
                   }

                }

		    	else if($scope.periodDisplayType == 'Week' && $scope.dateFormat == 'yyyy-MM'){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a week")
                         return;
                   }
                   else{
                     UserFormFactory.showAlert("Please select a week")
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
				    	if($scope.state != null) {
				    	reportRequest.stateId = $scope.state.stateId;
				    	}
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

		    	if(( $scope.reportCategory == 'Kilkari Reports') &&  ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase()) > -1) && $scope.format == 'yyyy-MM'){
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
                    $scope.currentDate = new Date();
                    $scope.currentPeriodDate = new Date($scope.currentDate.getFullYear(), $scope.currentDate.getMonth(), 0).getDate();
                    reportRequest.periodType = $scope.periodDisplayType;

                    if($scope.periodDisplayType == 'Year' ){

                        if($scope.dt1.getFullYear() == new Date().getFullYear()){
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.currentDate.getMonth() -1 ,$scope.currentPeriodDate);
                            reportRequest.periodType = 'CURRENT YEAR';

                        }
                        else{
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),11,31);

                        }

                         dateString = $scope.dt1.getFullYear();
                         excelHeaderName.timePeriod = dateString;
                         toDateVal = reportRequest.toDate;
                    }
                    else if($scope.periodDisplayType == 'Financial Year' ){
                         if($scope.dt1.getFullYear() == new Date().getFullYear() && $scope.currentDate.getMonth() >=3){
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),3,1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.currentDate.getMonth() -1,$scope.currentPeriodDate);
                            reportRequest.periodType = 'CURRENT FINANCIAL YEAR';

                         }
                         else if($scope.dt1.getFullYear() == new Date().getFullYear()-1 && $scope.currentDate.getMonth() < 3) {
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),3,1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear()+1,$scope.currentDate.getMonth() -1,$scope.currentPeriodDate);
                            reportRequest.periodType = 'CURRENT FINANCIAL YEAR';

                         }
                         else{
                             reportRequest.fromDate = new Date($scope.dt1.getFullYear(),3,1);
                             reportRequest.toDate = new Date($scope.dt1.getFullYear()+1,2,31);
                         }

                        dateString = reportRequest.fromDate.getDate() + "_" + (reportRequest.fromDate.getMonth() + 1 ) + "_" + reportRequest.fromDate.getFullYear() + "to" + reportRequest.toDate.getDate() + "_" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                     "_" + reportRequest.toDate.getFullYear();
                        excelHeaderName.timePeriod = reportRequest.fromDate.getDate() + "-" + (reportRequest.fromDate.getMonth() + 1 ) + "-" + reportRequest.fromDate.getFullYear() + " to " + reportRequest.toDate.getDate() + "-" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                                                          "-" + reportRequest.toDate.getFullYear();
                        toDateVal = reportRequest.toDate;
                    }

                    else if($scope.periodDisplayType == 'Month' ){
                         if($scope.report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'){
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth() - 5,1);
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
                            toDateVal = reportRequest.toDate;
                         }
                    }
                    else if($scope.periodDisplayType == 'Quarter' ){
                         if($scope.quarterDisplayType == 'Q1 (Jan to Mar)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);

                         if($scope.dt1.getFullYear() == new Date().getFullYear() && $scope.currentDate.getMonth() > 0 && $scope.currentDate.getMonth()< 4){
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.currentDate.getMonth()-1,$scope.currentPeriodDate);
                            if($scope.currentDate.getMonth()!= 3){
                                reportRequest.periodType = 'CURRENT QUARTER';
                            }

                         }
                         else{
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),2,31);
                         }
                         }
                         if($scope.quarterDisplayType == 'Q2 (Apr to Jun)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),3,1);
                         if($scope.dt1.getFullYear() == new Date().getFullYear() && $scope.currentDate.getMonth() >=4  && $scope.currentDate.getMonth()< 7){
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.currentDate.getMonth()-1,$scope.currentPeriodDate);
                            if($scope.currentDate.getMonth()!= 6){
                                reportRequest.periodType = 'CURRENT QUARTER';
                             }
                         }
                         else{
                             reportRequest.toDate = new Date($scope.dt1.getFullYear(),5,30);
                         }
                         }
                         if($scope.quarterDisplayType == 'Q3 (Jul to Sep)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),6,1);

                         if($scope.dt1.getFullYear() == new Date().getFullYear() && $scope.currentDate.getMonth() >=7 && $scope.currentDate.getMonth()< 10){
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.currentDate.getMonth()-1,$scope.currentPeriodDate);
                            if($scope.currentDate.getMonth()!= 9){
                                reportRequest.periodType = 'CURRENT QUARTER';
                            }
                         }
                         else{
                             reportRequest.toDate = new Date($scope.dt1.getFullYear(),8,30);
                         }
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),8,30);
                         }
                         if($scope.quarterDisplayType == 'Q4 (Oct to Dec)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),9,1);

                         if($scope.dt1.getFullYear() == new Date().getFullYear() &&  $scope.currentDate.getMonth() >= 10){
                             reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.currentDate.getMonth()-1,$scope.currentPeriodDate);
                             reportRequest.periodType = 'CURRENT QUARTER';

                          }
                          else{
                              reportRequest.toDate = new Date($scope.dt1.getFullYear(),11,31);
                          }
                         }

                         dateString = (reportRequest.fromDate.getMonth() + 1) + "_" + reportRequest.fromDate.getFullYear() + "to" + (reportRequest.toDate.getMonth() + 1)  +
                                         "_" + reportRequest.toDate.getFullYear();
                         excelHeaderName.timePeriod = (reportRequest.fromDate.getMonth() + 1) + "-" + reportRequest.fromDate.getFullYear() + " to " + (reportRequest.toDate.getMonth() + 1)  +
                                         "-" + reportRequest.toDate.getFullYear();
                         toDateVal = reportRequest.toDate;


                    }


                    else if($scope.periodDisplayType == 'Week' ){
                        reportRequest.toDate = $scope.dt1;
                        reportRequest.fromDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth(),$scope.dt1.getDate()-6);
                        dateString = reportRequest.fromDate.getDate() + "_" + (reportRequest.fromDate.getMonth() + 1 ) + "_" + reportRequest.fromDate.getFullYear() + "to" + reportRequest.toDate.getDate() + "_" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                     "_" + reportRequest.toDate.getFullYear();
                        excelHeaderName.timePeriod = reportRequest.fromDate.getDate() + "-" + (reportRequest.fromDate.getMonth() + 1 ) + "-" + reportRequest.fromDate.getFullYear() + " to " + reportRequest.toDate.getDate() + "-" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                                                          "-" + reportRequest.toDate.getFullYear();
                        toDateVal = reportRequest.toDate;

                    }
                    else if($scope.periodDisplayType == 'Custom Range' ){
                        reportRequest.fromDate = $scope.dt1;
                        reportRequest.toDate = $scope.dt2;
                        dateString = reportRequest.fromDate.getDate() + "_" + (reportRequest.fromDate.getMonth() + 1 ) + "_" + reportRequest.fromDate.getFullYear() + "to" + reportRequest.toDate.getDate() + "_" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                     "_" + reportRequest.toDate.getFullYear();
                        excelHeaderName.timePeriod = reportRequest.fromDate.getDate() + "-" + (reportRequest.fromDate.getMonth() + 1 ) + "-" + reportRequest.fromDate.getFullYear() + " to " + reportRequest.toDate.getDate() + "-" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                                                          "-" + reportRequest.toDate.getFullYear();
                        toDateVal = reportRequest.toDate;
                    }
                    else{
                        reportRequest.toDate = $scope.dt2;
                        dateString =   "till_" + reportRequest.toDate.getDate() + "_" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                             "_" + reportRequest.toDate.getFullYear();
                        excelHeaderName.timePeriod = "till " + reportRequest.toDate.getDate() + "-" +  ( reportRequest.toDate.getMonth() + 1 ) +
                                                      "-" + reportRequest.toDate.getFullYear();
                        toDateVal = reportRequest.toDate;
                    }

                    if(Date.parse(reportRequest.toDate) < rejectionStartDate){
                        rejectionStart = false;}
                    else{
                        rejectionStart = true;}

                }


			    $scope.waiting = true;

                $scope.headerFromDate = reportRequest.fromDate;
                $scope.headerToDate = reportRequest.toDate;

				$http({
					method  : 'POST',
					url     : $scope.getReportUrl,
					data    : reportRequest, //forms user object
					headers : {'Content-Type': 'application/json' , 'csrfToken': token}
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



					    if($scope.report.reportEnum == 'District-wise Performance of the State for Mobile Academy'){
					        $scope.gridOptions1.columnDefs = $scope.MA_Cumulative_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'MA_Performance'){
					        $scope.gridOptions1.columnDefs = $scope.MA_Performance_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'MA_Subscriber'){
					        $scope.gridOptions1.columnDefs = $scope.MA_Subscriber_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Cumulative_Summary'){
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Cumulative_Summary_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Usage')
					        $scope.gridOptions1.columnDefs = $scope.Kilkari_Usage_Definitions;
					    else if($scope.report.reportEnum == 'Kilkari_Aggregate_Beneficiaries'){
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
                        else if($scope.report.reportEnum == 'District-wise Performance of the State for Kilkari'){
                            $scope.gridOptions1.columnDefs = $scope.Kilkari_Call_Report_With_Beneficiaries_Definitions;
                        }
					    else if($scope.report.reportEnum == 'Kilkari_Listening_Matrix'){
					         $scope.gridOptions1.columnDefs = $scope.Kilkari_Listening_Matrix_Definitions;
					    }
					    else if($scope.report.reportEnum == 'Kilkari_Subscriber'){
					        if(!rejectionStart)
                                {$scope.gridOptions1.columnDefs = $scope.Kilkari_Subscriber_Definitions;}
                            if(rejectionStart)
                                {$scope.gridOptions1.columnDefs = $scope.Kilkari_Subscriber_Definitions2;}
                        }
                        else if($scope.report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate'){
                                $scope.gridOptions1.columnDefs = $scope.Kilkari_Subscriber_with_RegistrationDate_Definitions;
                        }
                        else if($scope.report.reportEnum == 'Aggregate_Whatsapp_Subscriber'){
                             $scope.gridOptions1.columnDefs = $scope.Aggregate_Whatsapp_Subscriber_Definitions;
                        }
                        else if($scope.report.reportEnum == 'Aggregate_Whatsapp_Message'){
                             $scope.gridOptions1.columnDefs = $scope.Aggregate_Whatsapp_Message_Definitions;
                        }
                        else if($scope.report.reportEnum == 'Aggregate_Whatsapp_Opt_In_Report'){
                             $scope.gridOptions1.columnDefs = $scope.Aggregate_Whatsapp_Opt_In_Report_Definitions;
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
                             if($scope.report.reportEnum == 'Kilkari_Subscriber'){
                                 if(!rejectionStart){
                                 var i;
                                    for(i=0; i<$scope.gridOptions1.data.length ;i++){
                                     $scope.gridOptions1.data[i].totalRecordsRejected = "N/A";
                                    }
                                 }
                             }
                             $scope.gridOptions1.showColumnFooter = true;
                             $scope.reportBreadCrumbData = result.data.breadCrumbData;
                             $scope.hideGrid = false;
                            // console.log(angular.lowercase(result.data.tableData[0].locationType));
                             if(result.data.tableData[0].locationType.toLowerCase() == 'state'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'State';
                                 }
                                 else if(result.data.tableData[0].locationType.toLowerCase() == 'district' || result.data.tableData[0].locationType.toLowerCase() == 'differencestate'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'District';

                                 }
                                  else if(result.data.tableData[0].locationType.toLowerCase() == 'block' || result.data.tableData[0].locationType.toLowerCase() == 'differencedistrict'){
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Block';

                                 }
                                 else if(result.data.tableData[0].locationType.toLowerCase() == 'subcenter' || result.data.tableData[0].locationType.toLowerCase() == 'differenceblock'){
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
                        if($scope.report.reportEnum == 'Kilkari_Message_Matrix'){
                            $scope.reportBreadCrumbData = result.data.breadCrumbData;
                            if(result.data.motherData.length >0){
                                $scope.gridOptions1.data = result.data.motherData;
                                $scope.gridOptions1.showColumnFooter = true;
                                $scope.hideGrid = false;
                                $scope.matrixContent1 ='Kilkari Pregnancy Content Data';

                            }
                             else{
                                $scope.hideGrid = true;
                                $scope.showEmptyData = true;
                            }
                            if(result.data.childData.length >0){
                                $scope.gridOptions2.data = result.data.childData;
                                $scope.gridOptions2.showColumnFooter = true;
                                $scope.hideMessageMatrix = false;
                                $scope.matrixContent2 ='Kilkari Child Content Data';
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

				 if((($state.current.name)===("reports"))){
                                    $scope.clearCircle();
                                    				$scope.clearFile();
                                    				$scope.dt = null;
                                    				$scope.datePickerContent = " Monthly";
                                    				$scope.dt1 = null;
                                    				$scope.dt2 = null;
                                    				$scope.hideGrid = true;
                                    				$scope.hideMessageMatrix = true;
                                                    $scope.periodDisplayType = '';

                                    				$scope.showEmptyData = false;

                                    }
                                 if($scope.isAggregateReport()){

                                                    $scope.clearFile();
                                                    $scope.dt = null;
                                                    $scope.dt1 = null;
                                                    $scope.dt2 = null;
                                                    $scope.hideGrid = true;
                                                    $scope.hideMessageMatrix = true;
                                                    $scope.periodDisplayType = '';
                                                    $scope.showEmptyData = false;

                                }



				}
			})
			}

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

				if(($scope.reportCategory == 'Kilkari Reports') &&  ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase())  > -1 ) ){
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

				if((  $scope.reportCategory == 'Kilkari Reports') &&  ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase()) > -1) && ($scope.format == 'yyyy-MM-dd' || $scope.format == 'yyyy-MM' )){
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
            	if(( $scope.reportCategory == 'Kilkari Reports') &&  ($scope.report.name.toLowerCase().indexOf(("rejected").toLowerCase()) > -1)  ){
            	$scope.dt = new Date($scope.dt.getFullYear(),$scope.dt.getMonth(),date);}
            	if($scope.showWeekTable()){
            	$scope.dt1 = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth(),date);}
            	$scope.wasSundaySelected = true;
            };

            $window.addEventListener('click', function() {
              if($scope.sundaysTable)
                $scope.sundaysTable = false;
            });

            $scope.exportDataFn = function(){

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
                                        (excelHeaderName.reportName == "District-wise Performance of the State for Kilkari" && (j == "4"||j == "6"||j == "8"||j == "9"||j == "10"))||
                                        (excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy" && (j == "3"||j == "5"||j == "7"||j=="9"))||
                                        (excelHeaderName.reportName == "Kilkari Cumulative Summary" && (j == "4"||j == "3") )||
                                        (excelHeaderName.reportName == "Kilkari Thematic Content" && (j == "4") )||
                                        (excelHeaderName.reportName == "Kilkari Beneficiary Completion" && (j == "2") )){
//                                         temp = parseFloat(temp);
                                    }
                           temprow.push(temp);
                       }
                       data.push(temprow);
                    }
            ExcelData.reportData = data;

                        var footerData=[];
                        var v;
                                if(excelHeaderName.reportName != "Kilkari Listening Matrix" && excelHeaderName.reportName != "Kilkari Repeat Listener Month Wise"&& excelHeaderName.reportName != "Kilkari Thematic Content"){
                                    $scope.gridApi.grid.columns.forEach(function (ft) {

                                       if(ft.displayName == "Message Week" || ft.displayName == "State" || ft.displayName == "District" || ft.displayName == "Block" || ft.displayName == "Subcenter" || ft.displayName == "Message Number (Week)" )
                                           v = "Total";

                                       else if(ft.displayName == "Average Duration Of Call" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                                           var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[4].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue());
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Average Number Of Weeks In Service" && excelHeaderName.reportName == "Kilkari Beneficiary Completion"){
                                           var temp = $scope.gridApi.grid.columns.length==0?0.00: ($scope.gridApi.grid.columns[3].getAggregationValue());
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Average Duration of Calls(Mins)" && excelHeaderName.reportName == "Kilkari Call"){
                                           var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[8].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue());
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Total Billable Minutes" && excelHeaderName.reportName == "Kilkari Call"){
                                           var temp = ft.getAggregationValue();
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Average Duration of Calls(Mins)" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                                           var temp = $scope.gridApi.grid.columns[4].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[10].getAggregationValue()/$scope.gridApi.grid.columns[4].getAggregationValue());
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Total Billable Minutes" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                                           var temp = ft.getAggregationValue();
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "% attempted calls that were successful" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                                           var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: (($scope.gridApi.grid.columns[4].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue()) * 100);
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "% successful calls where >= 75% content listened to" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                                           var temp = $scope.gridApi.grid.columns[4].getAggregationValue()==0?0.00: (($scope.gridApi.grid.columns[6].getAggregationValue()/$scope.gridApi.grid.columns[4].getAggregationValue()) * 100);
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "% successful calls where <25% content listened to" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                                           var temp = $scope.gridApi.grid.columns[4].getAggregationValue()==0?0.00: (($scope.gridApi.grid.columns[8].getAggregationValue()/$scope.gridApi.grid.columns[4].getAggregationValue()) * 100);
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Total Billable Minutes Played" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                                           var temp = ft.getAggregationValue();
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "Total Beneficiary Records Rejected" && excelHeaderName.reportName == "Kilkari Subscriber"&&!rejectionStart){
                                           v = "N/A";
                                       }
                                       else if(ft.displayName == "% Not Started (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                                          var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[7].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                                          v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "% Started (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                                           var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[3].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                                           v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "% Completed (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                                          var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00:($scope.gridApi.grid.columns[5].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                                          v = (parseFloat(temp));
                                       }
                                       else if(ft.displayName == "% Failed (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                                          var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00:($scope.gridApi.grid.columns[9].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                                          v = (parseFloat(temp));
                                       }
                                       else{
                                           v = ft.getAggregationValue();
                                       }

                                       if(ft.displayName != "S No."){
                                       if (ft.displayName == "Location Name") {
                                           v = "Total";
                                       }
                                            footerData.push(v);
                                       }

                                   }, this);

                                }
                if(excelHeaderName.reportName == "kilkari message matrix for only successful calls" ||  excelHeaderName.reportName == "Kilkari Repeat Listener Month Wise"){
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
                                        (excelHeaderName.reportName == "District-wise Performance of the State for Kilkari" && (j == "4"||j == "6"||j == "8"||j == "9"||j == "10"))||
                                        (excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy" && (j == "3"||j == "5"||j == "7"||j=="9"))||
                                        (excelHeaderName.reportName == "Kilkari Cumulative Summary" && (j == "4"||j=="3") )||
                                        (excelHeaderName.reportName == "Kilkari Thematic Content" && (j == "4") )||
                                        (excelHeaderName.reportName == "Kilkari Beneficiary Completion" && (j == "2") )){
                                    }
                           temprow1.push(temp1);
                       }
                       data1.push(temprow1);
                    }
            ExcelData.reportData1 = data1;
                    var footerData1=[];
                    var v1;
                    if(excelHeaderName.reportName == "kilkari message matrix for only successful calls"){
                        $scope.gridApi1.grid.columns.forEach(function (ft1) {

                            if(ft1.displayName == "Message Week" )
                                v1 = "Total";
                            else{
                                v1 = ft1.getAggregationValue();
                            }

                            if(ft1.displayName != "S No."){
                                if (ft1.displayName == "Location Name") {
                                    v1 = "Total";
                                }
                                footerData1.push(v1);
                            }

                        }, this);

                    }
                }else{
            ExcelData.columnHeaders1 = [];
            ExcelData.reportData1 = [];
            }

                  var months    = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
                  var toDateString = $scope.headerToDate.getDate()<10?"0"+$scope.headerToDate.getDate():$scope.headerToDate.getDate();

                  if($scope.report.reportEnum == 'Kilkari_Cumulative_Summary'||$scope.report.reportEnum == 'District-wise Performance of the State for Mobile Academy'){
                   excelHeaderName.timePeriod = "till "+toDateString+" "+months[$scope.headerToDate.getMonth()]+" "+$scope.headerToDate.getFullYear();}
                  else{
                  var fromDateString = $scope.headerFromDate.getDate()<10?"0"+$scope.headerFromDate.getDate():$scope.headerFromDate.getDate();
                  excelHeaderName.timePeriod = fromDateString+" "+months[$scope.headerFromDate.getMonth()]+" "+$scope.headerFromDate.getFullYear()+
                  " to "+toDateString+" "+months[$scope.headerToDate.getMonth()]+" "+$scope.headerToDate.getFullYear();
                  }

                  ExcelData.colunmFooters = footerData;
                  ExcelData.colunmFooters1 = footerData1;
                  ExcelData.stateName = excelHeaderName.stateName;
                  ExcelData.districtName = excelHeaderName.districtName;
                  ExcelData.blockName = excelHeaderName.blockName;
                  ExcelData.reportName = excelHeaderName.reportName;
                  ExcelData.timePeriod = excelHeaderName.timePeriod;
                  ExcelData.fileName = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';

            }

            $scope.exportToExcel = function(){
                $scope.exportDataFn();
                $http({
                                    method  : 'POST',
                                    url     : backend_root + 'nms/user/generateAgg',
                                    data    : ExcelData, //forms user object
                                    //responseType: 'arraybuffer',
                                    headers : {'Content-Type': 'application/json ' , 'csrfToken': token}
                                }).then(function(response){
                                    if(response.data =="success"){
                                    var fileName = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';
                                       fileName += '.xlsx';
                                       window.location.href = backend_root + 'nms/user/downloadAgg?fileName='+fileName;

                                    }
                                    }
                                );

            }

            $scope.exportCsv = function() {

              exportUiGridService.exportToCsv1($scope.gridApi,$scope.gridApi1, 'visible', 'visible', excelHeaderName,rejectionStart);

            };

            $scope.exportPdf = function() {
            var fileName1 = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';
                             fileName1 = fileName1.replace("*","");
                             fileName1 += '.pdf';

                  var months    = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
                  var toDateString = $scope.headerToDate.getDate()<10?"0"+$scope.headerToDate.getDate():$scope.headerToDate.getDate();

                  if($scope.report.reportEnum == 'Kilkari_Cumulative_Summary'||$scope.report.reportEnum == 'District-wise Performance of the State for Mobile Academy'){
                   excelHeaderName.timePeriod = "till "+toDateString+" "+months[$scope.headerToDate.getMonth()]+" "+$scope.headerToDate.getFullYear();}
                  else{
                  var fromDateString = $scope.headerFromDate.getDate()<10?"0"+$scope.headerFromDate.getDate():$scope.headerFromDate.getDate();
                  excelHeaderName.timePeriod = fromDateString+" "+months[$scope.headerFromDate.getMonth()]+" "+$scope.headerFromDate.getFullYear()+
                  " to "+toDateString+" "+months[$scope.headerToDate.getMonth()]+" "+$scope.headerToDate.getFullYear();
                  }

            exportUiGridService.exportToPdf1($scope.gridApi,$scope.gridApi1,excelHeaderName,$scope.reportCategory,$scope.matrixContent1,$scope.matrixContent2,uiGridExporterConstants,'visible', 'visible',fileName1,rejectionStart);
            };

            $scope.exportToPdf1 = function(){

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
                        if((excelHeaderName.reportName == "Kilkari Call" && (j == "7"||j == "8"))||
                            (excelHeaderName.reportName == "District-wise Performance of the State for Kilkari" && (j == "4"||j == "6"||j == "8"||j == "10"))||
                            (excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy" && (j == "3"||j == "5"||j == "7"||j=="9"))||
                            (excelHeaderName.reportName == "Kilkari Cumulative Summary" && (j == "4"||j == "3") )||
                            (excelHeaderName.reportName == "Kilkari Thematic Content" && (j == "4") )||
                            (excelHeaderName.reportName == "Kilkari Beneficiary Completion" && (j == "2") )){
                            temp = Number(temp);
                            // temp = indianDecimal(temp);
                        }else if((j!="0")&&!(excelHeaderName.reportName == "Kilkari Thematic Content"&&j=="1")){
                            //temp=indianInteger(temp);
                        }
                        temprow.push(temp);
                    }
                    data.push(temprow);
                }

                var footerData=[];

                var v;
                if(excelHeaderName.reportName != "Kilkari Listening Matrix" && excelHeaderName.reportName != "Kilkari Repeat Listener Month Wise"&& excelHeaderName.reportName != "Kilkari Thematic Content"){
                    $scope.gridApi.grid.columns.forEach(function (ft) {

                        if(ft.displayName == "Message Week" || ft.displayName == "State" || ft.displayName == "District" || ft.displayName == "Block" || ft.displayName == "Subcenter" || ft.displayName == "Message Number (Week)" )
                            v = "Total";

                        else if(ft.displayName == "Average Duration Of Call" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                            var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[4].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue());
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Average Number Of Weeks In Service" && excelHeaderName.reportName == "Kilkari Beneficiary Completion"){
                            var temp = $scope.gridApi.grid.columns.length==0?0.00: ($scope.gridApi.grid.columns[3].getAggregationValue());
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Average Duration of Calls(Mins)" && excelHeaderName.reportName == "Kilkari Call"){
                            var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[8].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue());
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Total Billable Minutes" && excelHeaderName.reportName == "Kilkari Call"){
                            var temp = ft.getAggregationValue();
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Average Duration of Calls(Mins)" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                            var temp = $scope.gridApi.grid.columns[4].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[10].getAggregationValue()/$scope.gridApi.grid.columns[4].getAggregationValue());
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Total Billable Minutes" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                            var temp = ft.getAggregationValue();
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "% attempted calls that were successful" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                            var temp = $scope.gridApi.grid.columns[3].getAggregationValue()==0?0.00: (($scope.gridApi.grid.columns[4].getAggregationValue()/$scope.gridApi.grid.columns[3].getAggregationValue()) * 100);
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "% successful calls where >= 75% content listened to" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                            var temp = $scope.gridApi.grid.columns[4].getAggregationValue()==0?0.00: (($scope.gridApi.grid.columns[6].getAggregationValue()/$scope.gridApi.grid.columns[4].getAggregationValue()) * 100);
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "% successful calls where <25% content listened to" && excelHeaderName.reportName == "District-wise Performance of the State for Kilkari"){
                            var temp = $scope.gridApi.grid.columns[4].getAggregationValue()==0?0.00: (($scope.gridApi.grid.columns[8].getAggregationValue()/$scope.gridApi.grid.columns[4].getAggregationValue()) * 100);
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Total Billable Minutes Played" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                            var temp = ft.getAggregationValue();
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "Total Beneficiary Records Rejected" && excelHeaderName.reportName == "Kilkari Subscriber"&&!rejectionStart){
                            v = "N/A";
                        }
                        else if(ft.displayName == "% Not Started (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                            var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[7].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "% Completed (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                            var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00:($scope.gridApi.grid.columns[5].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "% Started (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                            var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00: ($scope.gridApi.grid.columns[3].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                            v = indianDecimal(temp);
                        }
                        else if(ft.displayName == "% Failed (of total registered)" && excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy"){
                            var temp = $scope.gridApi.grid.columns[2].getAggregationValue()==0?0.00:($scope.gridApi.grid.columns[9].getAggregationValue()/$scope.gridApi.grid.columns[2].getAggregationValue())*100;
                            v = indianDecimal(temp);
                        }
                        else{
                            if (ft.displayName != "S No.") {
                                v = indianInteger(ft.getAggregationValue());}
                            else{
                                v = ft.getAggregationValue();
                            }
                        }

                        if(ft.displayName != "S No."){
                            footerData.push(v);
                        }

                    }, this);

                }
                if(excelHeaderName.reportName != "Kilkari Listening Matrix" && excelHeaderName.reportName != "Kilkari Repeat Listener Month Wise"&& excelHeaderName.reportName != "Kilkari Thematic Content"){
                    data.push(footerData);
                }
                ExcelData.reportData = data;
                if(excelHeaderName.reportName == "kilkari message matrix for only successful calls" ||  excelHeaderName.reportName == "Kilkari Repeat Listener Month Wise"){
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
                                (excelHeaderName.reportName == "District-wise Performance of the State for Kilkari" && (j == "4"||j == "6"||j == "8"||j == "10"))||
                                (excelHeaderName.reportName == "District-wise Performance of the State for Mobile Academy" && (j == "3"||j == "5"||j == "7"||j=="9"))||
                                (excelHeaderName.reportName == "Kilkari Cumulative Summary" && (j == "4"||j=="3") )||
                                (excelHeaderName.reportName == "Kilkari Thematic Content" && (j == "4") )||
                                (excelHeaderName.reportName == "Kilkari Beneficiary Completion" && (j == "2") )){
                            }
                            temprow1.push(temp1);
                        }
                        data1.push(temprow1);
                    }
                        ExcelData.reportData1 = data1;
                    var footerData1=[];
                    var v1;
                    if(excelHeaderName.reportName == "kilkari message matrix for only successful calls"){
                        $scope.gridApi1.grid.columns.forEach(function (ft1) {

                            if(ft1.displayName == "Message Week" )
                                v1 = "Total";
                            else{
                                v1 = ft1.getAggregationValue();
                            }

                            if(ft1.displayName != "S No."){
                                if (ft1.displayName == "Location Name") {
                                    v1 = "Total";
                                }
                                footerData1.push(v1);
                            }

                        }, this);

                    data1.push(footerData1);}
                }

                else{
                    ExcelData.columnHeaders1 = [];
                    ExcelData.reportData1 = [];
                }

                var months    = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
                var toDateString = $scope.headerToDate.getDate()<10?"0"+$scope.headerToDate.getDate():$scope.headerToDate.getDate();

                if($scope.report.reportEnum == 'Kilkari_Cumulative_Summary'||$scope.report.reportEnum == 'District-wise Performance of the State for Mobile Academy'){
                    excelHeaderName.timePeriod = "till "+toDateString+" "+months[$scope.headerToDate.getMonth()]+" "+$scope.headerToDate.getFullYear();}
                else{
                    var fromDateString = $scope.headerFromDate.getDate()<10?"0"+$scope.headerFromDate.getDate():$scope.headerFromDate.getDate();
                    excelHeaderName.timePeriod = fromDateString+" "+months[$scope.headerFromDate.getMonth()]+" "+$scope.headerFromDate.getFullYear()+
                        " to "+toDateString+" "+months[$scope.headerToDate.getMonth()]+" "+$scope.headerToDate.getFullYear();
                }

                ExcelData.colunmFooters = footerData;
                ExcelData.stateName = excelHeaderName.stateName;
                ExcelData.districtName = excelHeaderName.districtName;
                ExcelData.blockName = excelHeaderName.blockName;
                ExcelData.reportName = excelHeaderName.reportName;
                ExcelData.timePeriod = excelHeaderName.timePeriod;
                ExcelData.fileName = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';



                $http({
                    method  : 'POST',
                    url     : backend_root + 'nms/user/downloadAggPdf',
                    data    : ExcelData, //forms user object
//                                    responseType: 'arraybuffer',
                    headers : {'Content-Type': 'application/json ' , 'csrfToken': token}
                }).then(function(response){

                        if(response.data=="success"){
                            var fileName = $scope.gridApi.grid.options.exporterExcelFilename ? $scope.gridApi.grid.options.exporterExcelFilename : 'dokuman';
                            fileName += '.pdf';
                            window.location.href = backend_root + 'nms/user/downloadpdf?fileName='+fileName;
                        }
                    }

                );

                // exportUiGridService.exportToExcel('sheet 1', $scope.gridApi,$scope.gridApi1, 'visible', 'visible', excelHeaderName);

            }


            function indianDecimal( value){
                            if(!value || value == null){
                                return "N/A";
                            }
                            x=value.toString();
                            var isNegative = false;
                                            if (x.substring(0,1) === '-'){
                                                x = x.substring(1);
                                                isNegative = true;
                                            }
                            var afterPoint = '';
                            if(x.indexOf('.') > 0)
                               afterPoint = x.substring(x.indexOf('.'),x.length);
                               afterPoint = parseFloat(Number(afterPoint));
                               afterPoint = afterPoint.toString().substring(afterPoint.toString().indexOf('.'),afterPoint.length);
                            x = Math.floor(x);
                            x=x.toString();
                            var lastThree = x.substring(x.length-3);
                            var otherNumbers = x.substring(0,x.length-3);
                            if(otherNumbers != '')
                                lastThree = ',' + lastThree;
                            if (afterPoint != 0) {
                                var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
                             } else {
                                res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree;
                            }
                            if (isNegative) {
                                                   return '-' + res;
                                               } else {
                                                   return res;
                                               }
                    }

                    function indianInteger(value){
                            if(!value || value==null){
                                return "N/A";
                            }
                                        x=value.toString();
                                        var isNegative = false;
                                                                                    if (x.substring(0,1) === '-'){
                                                                                        x = x.substring(1);
                                                                                        isNegative = true;
                                                                                    }
                                    var lastThree = x.substring(x.length-3);
                                    var otherNumbers = x.substring(0,x.length-3);
                                    if(otherNumbers != '')
                                        lastThree = ',' + lastThree;
                                    var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree ;
                                    if (isNegative) {
                                                           return '-' + res;
                                                       } else {
                                                           return res;
                                                       }

                    }


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
                                                         cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                         width: '16%', enableHiding: false,
                                                       },
                                                       { field: 'ashasRegistered',headerTooltip:'Number of ASHAs registered with the program.', displayName : 'No of Registered ASHA',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',culture:'en-IN', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},

                    {
                        field: 'ashasStarted',
                        headerTooltip: 'Number of ASHA have started the course',
                        displayName: 'No of ASHA Started Course',
                        cellFilter: 'indianFilter',
                        footerCellFilter: 'indianFilter',
                        aggregationType: uiGridConstants.aggregationTypes.sum,
                        aggregationHideLabel: true,
                        width: "*",
                        enableHiding: false
                    },
                    {
                        field: 'startedPercentage',
                        headerTooltip: '% Started (of total registered)',
                        displayName: '% Started (of total registered)',
                        cellFilter: 'indianDecimalFilter',
                        footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[2].getAggregationValue()==0 ? 0.00:(grid.columns[3].getAggregationValue()/grid.columns[2].getAggregationValue())*100 | number:2}}</div>',
                        width: "*",
                        enableHiding: false
                    },
                {
                    field: 'ashasCompleted',
                    headerTooltip: 'number of ASHAs who have passed the course (first successful completion only)',
                    displayName: 'No of ASHA Completed Course',
                    cellFilter: 'indianFilter',
                    footerCellFilter: 'indianFilter',
                    aggregationType: uiGridConstants.aggregationTypes.sum,
                    aggregationHideLabel: true,
                    width: "*",
                    enableHiding: false
                },
                {
                    field: 'completedPercentage',
                    headerTooltip: '% Completed (of total registered)',
                    displayName: '% Completed (of total registered)',
                    cellFilter: 'indianDecimalFilter',
                    footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[2].getAggregationValue()==0 ? 0.00:(grid.columns[5].getAggregationValue()/grid.columns[2].getAggregationValue())*100 | number:2}}</div>',
                    width: "*",
                    enableHiding: false
                },
                {
                    field: 'ashasNotStarted',
                    headerTooltip: 'Number of ASHA have not started the course',
                    displayName: 'No of ASHA Not Started Course',
                    cellFilter: 'indianFilter',
                    footerCellFilter: 'indianFilter',
                    aggregationType: uiGridConstants.aggregationTypes.sum,
                    aggregationHideLabel: true,
                    width: "*",
                    enableHiding: false
                },
                {
                    field: 'notStartedpercentage',
                    headerTooltip: '% Not Started (of total registered)',
                    displayName: '% Not Started (of total registered)',
                    cellFilter: 'indianDecimalFilter',
                    culture: 'en-IN',
                    footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[2].getAggregationValue()==0 ? 0.00:(grid.columns[7].getAggregationValue()/grid.columns[2].getAggregationValue()) *100 | number:2}}</div>',
                    width: "*",
                    enableHiding: false
                },
                {
                    field: 'ashasFailed',
                    headerTooltip: 'No of ASHA Failed Course',
                    displayName: 'No of ASHA Failed Course',
                    cellFilter: 'indianFilter',
                    footerCellFilter: 'indianFilter',
                    aggregationType: uiGridConstants.aggregationTypes.sum,
                    aggregationHideLabel: true,
                    width: "*",
                    enableHiding: false
                },
                {
                    field: 'failedpercentage',
                    headerTooltip: '% Failed (of total registered)',
                    displayName: '% Failed (of total registered)',
                    cellFilter: 'indianDecimalFilter',
                    footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[2].getAggregationValue()==0 ? 0.00:(grid.columns[9].getAggregationValue()/grid.columns[2].getAggregationValue()) *100 | number:2}}</div>',
                    width: "*",
                    enableHiding: false
                }
                ],

                // createHeaderTemplate('Number of ASHA Started Course'),
            $scope.MA_Performance_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"6%", enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                         { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                            cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                            enableHiding: false, width:"12%",

                                                         },
                { field: 'ashasActivated',  headerTooltip: 'No of Registered ASHA', displayName: 'No of Registered ASHA ',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                { field: 'ashasStarted', headerTooltip: 'ASHAs who have started the course for the first time in the selected period.',displayName: 'Number of ASHA Started Course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*",enableHiding: false },
                { field: 'ashasAccessed', headerTooltip: 'Number of ASHAs who had started the course before the selected period, had accessed the course at least once with one bookmark during the selected period and not completed the course till the end of the selected period ',displayName: 'Number of ASHA Pursuing Course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                { field: 'ashasRefresherCourse',  headerTooltip: 'The No of ASHA\'s who completed the course before time period and again they completed the course from selected period.(As per KMA Dashboard Data)', displayName: 'Number of ASHA doing Refresher course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                { field: 'ashasCompleted',headerTooltip: 'Number of ASHAs who have successfully completed the course for first time, during the selected period and secured pass marks.', displayName: 'Number of ASHA Successfully Completed Course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"18%",enableHiding: false},
                { field: 'ashasCompletedInGivenTime',  headerTooltip: 'Number of ASHAs Started and Completed the Course in the Given time period', displayName: 'No of ASHA started and completed course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                { field: 'ashasNotAccessed',headerTooltip: 'Number of ASHAs who had started the course before the selected period and had not accessed the course once during the selected period. The count does NOT include ASHAs who have any successful completion till the end of the selected period.', displayName: 'Number of ASHA not Pursuing Course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                { field: 'ashasFailed', headerTooltip: 'Number of ASHAs who have completed the course during the selected period and did not secure passing marks even once till the end of selected period', displayName: 'Number of ASHA who Failed the Course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                { field: 'ashasJoined',  headerTooltip: 'Count of New ASHA Records in the selected time period', displayName: 'No of Asha joined',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                { field: 'ashasDeactivated',  headerTooltip: 'Count of ASHA Records whose job status has become Inactive in the selected time period', displayName: 'No of Asha left',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
            ],

            $scope.MA_Subscriber_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                         { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                            cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                            enableHiding: false,width:"14%",

                                                         },
                                                         { field: 'registeredNotCompletedStart',headerTooltip: 'Number of ASHAs who have registered in the MA course prior to the start of the period but have not completed the course.', displayName: 'Number of ASHA Registered But Not Completed the Course(Period Start)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false },
                                                         { field: 'recordsReceived', headerTooltip : 'Number of ASHA Records that have been received from web service from MCTS/RCH during the period', displayName: 'Number of ASHA Records Received Through Web Service',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                         { field: 'ashasRejected', headerTooltip: 'Number of the records that have been rejected',displayName: 'Number of ASHA Records Rejected',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'ashasRegistered',headerTooltip:'Number of ASHAs who have been added/subscribed in MA course for the first time in the selected period ', displayName: 'Number of ASHA Added',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'ashasCompleted', headerTooltip:'Number of ASHAs who have successfully completed the course for the first time', displayName: 'Number of ASHA Successfully Completed the Course',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'registeredNotCompletedend', headerTooltip:'Number of ASHAs presently Subscribed in the Mobile Academy program but are yet to start or complete the course', displayName: 'Number of ASHA Registered But Not Completed the Course (Period End)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false},
                                                        ],

            $scope.Kilkari_Cumulative_Summary_Definitions =[
                                                             {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                             { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                                cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                                enableHiding: false, width:"*",
                                                             },
                                                             { field: 'uniqueBeneficiaries', name: 'Total unique beneficiaries',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                             { field: 'successfulCalls', name: 'Total successful calls',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                             { field: 'billableMinutes',cellFilter: 'indianDecimalFilter', name: 'Total billable minutes played', footerCellFilter: 'indianDecimalFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                             { field: 'averageDuration',cellFilter: 'indianDecimalFilter', footerCellFilter: 'indianDecimalFilter', name: 'Average duration of call', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{grid.columns[3].getAggregationValue()==0 ? 0.00 : grid.columns[4].getAggregationValue()/grid.columns[3].getAggregationValue() | number:2}}</div>',   width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Usage_Definitions =[
                                                 {name: 'S No.', displayName: 'S No.',width:"5%", enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                 { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                    cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                    enableHiding: false, width:"*",
                                                 },
                                                 { field: 'beneficiariesCalled', name: 'Total beneficiaries Called',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                 { field: 'answeredCall', name: 'Beneficiaries who have answered at least one call',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                 { field: 'calls_75_100', name: 'Beneficiaries who have listened greater than or equal to 75% content (avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                 { field: 'calls_50_75', name: 'Beneficiaries who have listened greater than or equal to 50% and less than 75% content (avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                 { field: 'calls_25_50', name: 'Beneficiaries who have listened greater than or equal to 25% and less than 50% content (avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                 { field: 'calls_1_25', name: 'Beneficiaries who have listened less than 25% content (avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                 { field: 'calledInbox', name: 'Beneficiaries who have called the Kilkari Inbox', width:"*",cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false},

            ]

            $scope.Kilkari_Aggregate_Beneficiaries_Definitions =[
                                                     {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"10%"
                                                     },
                                                     { field: 'beneficiariesCalled', name: 'Total beneficiaries Called',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'answeredAtleastOneCall', name: 'Beneficiaries who have answered at least one call',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'selfDeactivated', name: 'Beneficiaries who have self-deactivated',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                     { field: 'notAnswering', name: 'Beneficiaries deactivated for not answering',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'lowListenership', name: 'Beneficiaries deactivated for low listenership', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                     { field: 'systemDeactivation', displayName: 'Beneficiaries deactivated by system through MCTS/RCH updates', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"11%", enableHiding: false },
                                                     { field: 'motherCompletion', name: 'Beneficiaries completed Mother Pack',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                     { field: 'childCompletion', name: 'Beneficiaries completed Child Pack',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'calledInbox', name: 'Beneficiaries who have called the Kilkari Inbox',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'joinedSubscription', name: 'Beneficiaries who have joined the subscription',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Beneficiary_Completion_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"12%"
                                                     },
                                                     { field: 'completedBeneficiaries', name: 'Total beneficiaries Completed Program',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'avgWeeks', name: 'Average Number of Weeks in Service',cellFilter: 'indianDecimalFilter', footerCellFilter: 'number:2', aggregationType: uiGridConstants.aggregationTypes.avg, aggregationHideLabel: true,  aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'calls_75_100', name: 'Beneficiaries who have listened greater than or equal to 75% content (Consolidated avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'calls_50_75', name: 'Beneficiaries who have listened greater than or equal to 50% and less than 75% content (Consolidated avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'calls_25_50', name: 'Beneficiaries who have listened greater than or equal to 25% and less than 50% content (Consolidated avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'calls_1_25', name: 'Beneficiaries who have listened less than 25% content (Consolidated avg)',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Listening_Matrix_Definitions =[
                                                     { field: 'percentageCalls', name: 'Listening Percentage', enableSorting: false,width:"30%", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Listening greater than or equal to 75% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Listening greater than or equal to 50% to less than 75% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_25_50', name: 'Listening greater than or equal to 25% to less than 50% content', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Listening less than 25% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', enableSorting: false,width:"10%", enableHiding: false },

            ]

            $scope.Kilkari_Call_Report_Definitions = [
                                                    {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                    { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"12%"
                                                    },
                                                    { field: 'callsAttempted', name: 'Total Calls Attempted',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                    { field: 'successfulCalls', name: 'Total Number of Successful Calls',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                                                    { field: 'content_75_100', name: 'Total calls where greater than or equal to 75% content listened to',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                    { field: 'content_50_75', name: 'Total calls where  greater than or equal to 50% to less than 75% content listened to',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                                                    { field: 'content_25_50', name: 'Total calls where greater than or equal to 25% to less than 50% content listened to',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                    { field: 'content_1_25', name: 'Total calls where less than 25%  content listened to',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                    { field: 'billableMinutes', name: 'Total Billable Minutes',cellFilter: 'indianDecimalFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, footerCellFilter: 'indianDecimalFilter', width:"*", enableHiding: false },
                                                    { field: 'avgDuration', name: 'Average Duration of Calls',displayName: 'Average Duration of Calls(Mins)',cellFilter: 'indianDecimalFilter', footerCellFilter: 'indianDecimalFilter', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[3].getAggregationValue()==0)?0.00: grid.columns[8].getAggregationValue()/grid.columns[3].getAggregationValue() | number:2}}</div>', width:"*", enableHiding: false},
                                                    { field: 'callsToInbox', name: 'Total number of calls to inbox where content is played',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },

            ]

            $scope.Kilkari_Call_Report_With_Beneficiaries_Definitions = [
                {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                    cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                    enableHiding: false, width:"12%"
                },
                { field: 'beneficiariesCalled', displayName: 'Total Beneficiaries Called',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                { field: 'callsAttempted', displayName: 'Total Calls Attempted',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                { field: 'successfulCalls', displayName: 'Total Number of Successful Calls',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false },
                { field: 'percentageCallsResponded', displayName: '% attempted calls that were successful',cellFilter: 'indianDecimalFilter',footerCellFilter: 'indianDecimalFilter', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[3].getAggregationValue()==0)?0.00: (grid.columns[4].getAggregationValue()/grid.columns[3].getAggregationValue()) * 100 | number:2}}</div>',  width:"*", enableHiding: false},
                { field: 'content_75_100', displayName: 'Total calls where >=75% content listened to',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,  width:"*", enableHiding: false},
                { field: 'percentageCalls_75_100', displayName: '% successful calls where >= 75% content listened to',cellFilter: 'indianDecimalFilter',footerCellFilter: 'indianDecimalFilter', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[4].getAggregationValue()==0)?0.00: (grid.columns[6].getAggregationValue()/grid.columns[4].getAggregationValue()) * 100 | number:2}}</div>',  width:"*", enableHiding: false},
                { field: 'content_1_25', displayName: 'Total calls where <25%  content listened to',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                { field: 'percentageCalls_1_25', displayName: '% successful calls where <25% content listened to',cellFilter: 'indianDecimalFilter',footerCellFilter: 'indianDecimalFilter', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[4].getAggregationValue()==0)?0.00: (grid.columns[8].getAggregationValue()/grid.columns[4].getAggregationValue()) * 100 | number:2}}</div>',  width:"*", enableHiding: false},
                { field: 'billableMinutes', displayName: 'Total Billable Minutes',cellFilter: 'indianDecimalFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, footerCellFilter: 'indianDecimalFilter', width:"*", enableHiding: false },
                { field: 'avgDuration', displayName: 'Average Duration of Calls(Mins)',cellFilter: 'indianDecimalFilter', footerCellFilter: 'indianDecimalFilter', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[4].getAggregationValue()==0)?0.00: grid.columns[10].getAggregationValue()/grid.columns[4].getAggregationValue() | number:2}}</div>', width:"*", enableHiding: false},

            ]

            $scope.Kilkari_Message_Matrix_Motherpack_Definitions =[
                                                     { field: 'messageWeek',name: 'Message Week',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_75_100', name: 'Beneficiaries listened greater than or equal to 75% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Beneficiaries listened greater than or equal to 50% to less than 75% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Beneficiaries listened greater than or equal to 25% to less than 50% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Beneficiaries listened less than 25 % content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', enableSorting: false,width:"*",cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false },

            ]

            $scope.Kilkari_Message_Matrix_Childpack_Definitions =[
                                                     { field: 'messageWeek',name: 'Message Week',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_75_100', name: 'Beneficiaries listened greater than or equal to 75% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableSorting: false,width:"*", enableHiding: false},
                                                     { field: 'content_50_75', name: 'Beneficiaries listened greater than or equal to 50% to less than 75% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,enableSorting: false, width:"*", enableHiding: false},
                                                     { field: 'content_25_50', name: 'Beneficiaries listened greater than or equal to 25% to less than 50% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'content_1_25', name: 'Beneficiaries listened less than 25% content',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true,enableSorting: false,width:"*", enableHiding: false },
                                                     { field: 'total', name: 'Total', enableSorting: false,width:"*",cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false },

            ]

            $scope.Kilkari_Message_Listenership_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName',footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false, width:"12%"
                                                     },
                                                     { field: 'totalBeneficiariesCalled', name: 'Total beneficiaries Called',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredAtleastOnce', name: 'Beneficiaries who have answered at least one call',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredMoreThan75', name: 'Beneficiaries who have answered greater than or equal to 75% calls',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnswered50To75', name: 'Beneficiaries who have answered greater than or equal to 50% to less than 75% calls', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'beneficiariesAnswered25To50', name: 'Beneficiaries who have answered greater than or equal to 25% to less than 50% calls', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnswered1To25', name: 'Beneficiaries who have answered less than 25% calls', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'beneficiariesAnsweredNoCalls', name: 'Beneficiaries who have not answered any calls', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
            ]

            $scope.Kilkari_Subscriber_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false,width:"10%"
                                                     },
                                                     { field: 'totalSubscriptionsStart', displayName: 'Total subscriptions at the start of the period', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsReceived', displayName: 'Unique records received from RCH/MCTS', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsEligible', displayName: 'Unique records found eligible for subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalRecordsRejected', displayName: 'Unique records rejected',cellTemplate:'<p class="ui-grid-cell-contents">N/A</p>',footerCellTemplate:'<p class="ui-grid-cell-contents">N/A</p>', aggregationHideLabel: true, width:"12%", enableHiding: false},
                                                     { field: 'totalBeneficiaryRecordsAccepted', displayName: 'Unique records accepted as subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalSubscriptionsCompleted', displayName: 'Total number of subscriptions who have completed their packs', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalSubscriptionsEnd', displayName: 'Total subscriptions at the end of the period', width:"10%", cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false },
            ]


            $scope.Aggregate_Whatsapp_Subscriber_Definitions = [
                                                                 {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                                 { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                                    cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                                    enableHiding: false,width: "15%"
                                                                 },
                                                                 { field: 'activeWhatsAppSubscribers', displayName: 'Total Subscribers', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                                 { field: 'newWhatsAppOptIns', displayName: 'New opt-ins', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                                 { field: 'newWhatsAppSubscribers', displayName: 'New Subscribers', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                                 { field: 'selfWhatsAppDeactivatedSubscriber', displayName: 'Subscribers who have self-deactivated', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                                 { field: 'deliveryFailureWhatsAppDeactivatedSubscriber', displayName: 'Subscribers deactivated for delivery failures', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                                 { field: 'motherPackCompletedSubscribers', displayName: 'Subscribers who completed Mother Pack', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                                 { field: 'childPackCompletedSubscribers', displayName: 'Subscribers who completed Child Pack', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                        ]
            $scope.Aggregate_Whatsapp_Message_Definitions = [
                                                               {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                               { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                                  cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                                  enableHiding: false,width: "15%"
                                                               },
                                                               { field: 'totalSubscribers', displayName: 'Total Subscribers', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                               { field: 'coreMessagesSent', displayName: 'Core Messages Sent', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'coreMessagesDelivered', displayName: 'Core Messages Delivered', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'coreMessagesRead', displayName: 'Core Messages Read', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                ]
            $scope.Aggregate_Whatsapp_Opt_In_Report_Definitions = [
                                                               {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                               { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                                  cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                                  enableHiding: false,width: "15%"
                                                               },
                                                               { field: 'welcomeCallSuccessfulCalls', displayName: 'Welcome call: No. of successful calls', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                               { field: 'welcomeCallCallsAnswered', displayName: 'Welcome call: No. of calls answered', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'welcomeCallEnteredAnOption', displayName: 'Welcome call: Entered an option', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'welcomeCallProvidedOptIn', displayName: 'Welcome call: Provided opt-in', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                               { field: 'welcomeCallProvidedOptOut', displayName: 'Welcome call: Provided opt-out', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                               { field: 'optInSuccessfulCalls', displayName: 'Opt-in call: No. of successful calls', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'optInCallsAnswered', displayName: 'Opt-in call: No. of calls answered', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'optInEnteredAnOption', displayName: 'Opt-in call: Entered an option', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                               { field: 'optInProvidedOptIn', displayName: 'Opt-in call: Provided opt-in', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                               { field: 'optInProvidedOptOut', displayName: 'Opt-in call: Provided opt-out', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                ]
            $scope.Kilkari_Subscriber_Definitions2 = [
                                                     {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false,width:"10%"
                                                     },
                                                     { field: 'totalSubscriptionsStart', displayName: 'Total subscriptions at the start of the period', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsReceived', displayName: 'Unique records received from RCH/MCTS', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalBeneficiaryRecordsEligible', displayName: 'Unique records found eligible for subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalRecordsRejected', displayName: 'Unique records rejected',cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false},
                                                     { field: 'totalBeneficiaryRecordsAccepted', displayName: 'Unique records accepted as subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalSubscriptionsCompleted', name: 'Total number of subscriptions who have completed their packs', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalSubscriptionsEnd', displayName: 'Total subscriptions at the end of the period', width:"10%", cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, enableHiding: false },
            ]

            $scope.Kilkari_Subscriber_with_RegistrationDate_Definitions = [
                                                     {name: 'S No.', displayName: 'S No.',width:"4%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',defaultSort: { direction: uiGridConstants.ASC },
                                                        cellTemplate:'<a   ng-if= !row.entity.link class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a><p ng-if= row.entity.link class="ui-grid-cell-contents" title="{{COL_FIELD}}" >{{ COL_FIELD }}</p>',
                                                        enableHiding: false,width: "15%"
                                                     },
                                                     { field: 'totalSubscriberCount', displayName: 'Total Subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"12%", enableHiding: false },
                                                     { field: 'totalBeneficiaryWithActiveStatus', displayName: 'Active Subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalRejectedSubscriberCount', displayName: 'Rejected Subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalBeneficiaryWithOnHoldStatus', displayName: 'On Hold Subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                     { field: 'totalBeneficiaryWithPendingStatus', displayName: 'Pending Activation Subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalBeneficiaryWithDeactivatedStatus', displayName: 'Deactivated subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                     { field: 'totalBeneficiaryWithCompletedStatus', displayName: 'Completed subscriptions', cellFilter: 'indianFilter',footerCellFilter: 'indianFilter', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
            ]

            $scope.Kilkari_Thematic_Content_Definitions = [
                                                    // {name: 'S No.', displayName: 'S No.',width:"7%",enableSorting: false, exporterSuppressExport: true, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                     { field: 'theme',  cellTooltip: true, name: 'Theme', width:"15%", cellClass: 'text-left', enableHiding: false,enableSorting: false },
                                                     { field: 'messageWeekNumber',cellTooltip: true, name: 'Message Number', width:"35%", cellClass: 'text-left', enableHiding: false,enableSorting: false },
                                                     { field: 'uniqueBeneficiariesCalled', name: 'Number of unique beneficiaries called',cellFilter: 'indianFilter',  width:"*", enableHiding: false,enableSorting: false },
                                                     { field: 'callsAnswered', name: 'Number of calls answered', cellFilter: 'indianFilter',  width:"*", enableHiding: false,enableSorting: false},
                                                     { field: 'minutesConsumed', name: 'Number of minutes consumed', cellFilter: 'indianDecimalFilter', footerCellFilter: 'indianDecimalFilter', width:"*", enableHiding: false,enableSorting: false }
             ]

            $scope.Kilkari_RepeatListener_Numberdata_Definitions =[
                                                     { field: 'month', enableSorting: false,name: 'Month',width:"*", enableHiding: false },
                                                     { field: 'moreThanFiveCallsAnswered',enableSorting: false, name: 'more than 5 calls answered',cellFilter: 'indianFilter', width:"*", enableHiding: false},
                                                     { field: 'fiveCallsAnswered',enableSorting: false, name: '5 calls answered',cellFilter: 'indianFilter', width:"*", enableHiding: false},
                                                     { field: 'fourCallsAnswered',enableSorting: false, name: '4 calls answered',cellFilter: 'indianFilter', width:"*", enableHiding: false},
                                                     { field: 'threeCallsAnswered', enableSorting: false,name: '3 calls answered',cellFilter: 'indianFilter',width:"*", enableHiding: false },
                                                     { field: 'twoCallsAnswered', enableSorting: false,name: '2 calls answered',cellFilter: 'indianFilter',width:"*", enableHiding: false },
                                                     { field: 'oneCallAnswered', enableSorting: false,name: '1 call answered',cellFilter: 'indianFilter',width:"*", enableHiding: false },
                                                     { field: 'noCallsAnswered', enableSorting: false,name: '0 call answered',cellFilter: 'indianFilter',width:"*", enableHiding: false },
                                                     { field: 'total', enableSorting: false,name: 'Total',cellFilter: 'indianFilter',width:"*", enableHiding: false },

            ]

            $scope.Kilkari_RepeatListener_Percentdata_Definitions =[
                                                     { field: 'month', enableSorting: false,name: 'Month',width:"*", enableHiding: false },
                                                     { field: 'moreThanFiveCallsAnsweredPercent', cellFilter: 'indianDecimalFilter', enableSorting: false,name: 'more than 5 calls answered', width:"*", enableHiding: false},
                                                     { field: 'fiveCallsAnsweredPercent', cellFilter: 'indianDecimalFilter', enableSorting: false,name: '5 calls answered', width:"*", enableHiding: false},
                                                     { field: 'fourCallsAnsweredPercent',cellFilter: 'indianDecimalFilter', enableSorting: false,name: '4 calls answered', width:"*", enableHiding: false},
                                                     { field: 'threeCallsAnsweredPercent', cellFilter: 'indianDecimalFilter', enableSorting: false,name: '3 calls answered',width:"*", enableHiding: false },
                                                     { field: 'twoCallsAnsweredPercent', cellFilter: 'indianDecimalFilter', enableSorting: false,name: '2 calls answered',width:"*", enableHiding: false },
                                                     { field: 'oneCallAnsweredPercent',cellFilter: 'indianDecimalFilter', enableSorting: false,name: '1 call answered',width:"*", enableHiding: false },
                                                     { field: 'noCallsAnsweredPercent', cellFilter: 'indianDecimalFilter', enableSorting: false,name: '0 call answered',width:"*", enableHiding: false }
            ]


            $scope.drillDownData = function(locationId,locationType,locationName){

                  if(locationType.toLowerCase() == "state"){
                    reportRequest.stateId = locationId;
                    reportRequest.districtId = 0;
                    reportRequest.blockId = 0;
                    $scope.waiting = true;

                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json' , 'csrfToken': token}
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
                                         if($scope.report.reportEnum == 'Kilkari_Subscriber' || $scope.report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate'){
                                             if(!rejectionStart){
                                             var i;
                                                for(i=0; i<$scope.gridOptions1.data.length ;i++){
                                                 $scope.gridOptions1.data[i].totalRecordsRejected = "N/A";
                                                }
                                             }
                                         }
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
                  else if(locationType.toLowerCase() == "national"){
                       reportRequest.stateId = 0;
                       reportRequest.districtId = 0;
                       reportRequest.blockId = 0;
                       $scope.waiting = true;
                       $http({
                               method  : 'POST',
                               url     : $scope.getReportUrl,
                               data    : reportRequest, //forms user object
                               headers : {'Content-Type': 'application/json' , 'csrfToken': token}
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
                                             if($scope.report.reportEnum == 'Kilkari_Subscriber' || $scope.report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate'){
                                                 if(!rejectionStart){
                                                 var i;
                                                    for(i=0; i<$scope.gridOptions1.data.length ;i++){
                                                     $scope.gridOptions1.data[i].totalRecordsRejected = "N/A";
                                                    }
                                                 }
                                             }
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
                  else if(locationType.toLowerCase() == "district"){
                     reportRequest.districtId = locationId;
                     reportRequest.blockId = 0;
                     $scope.waiting = true;
                     $http({
                             method  : 'POST',
                             url     : $scope.getReportUrl,
                             data    : reportRequest, //forms user object
                             headers : {'Content-Type': 'application/json', 'csrfToken': token}
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
                                         if($scope.report.reportEnum == 'Kilkari_Subscriber' || $scope.report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate'){
                                          if(!rejectionStart){
                                             var i;
                                                for(i=0; i<$scope.gridOptions1.data.length ;i++){
                                                 $scope.gridOptions1.data[i].totalRecordsRejected = "N/A";
                                                }
                                             }
                                         }
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
                  else if(locationType.toLowerCase() == "block"){
                    reportRequest.blockId = locationId;
                    $scope.waiting = true;
                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json', 'csrfToken': token}
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
                                        if($scope.report.reportEnum == 'Kilkari_Subscriber' || $scope.report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate'){
                                            if(!rejectionStart){
                                            var i;
                                               for(i=0; i<$scope.gridOptions1.data.length ;i++){
                                                $scope.gridOptions1.data[i].totalRecordsRejected = "N/A";
                                               }
                                            }
                                        }
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

		.filter('indianDecimalFilter', function () {
          return function (value) {
            x=value.toString();
            var isNegative = false;
                                                        if (x.substring(0,1) === '-'){
                                                            x = x.substring(1);
                                                            isNegative = true;
                                                        }
        var afterPoint = '';
        if(x.indexOf('.') > 0)
           afterPoint = x.substring(x.indexOf('.'),x.length);
           afterPoint = Number(afterPoint).toFixed(2);
           afterPoint = afterPoint.substring(afterPoint.indexOf('.'),afterPoint.length);
        x = Math.floor(x);
        x=x.toString();
        var lastThree = x.substring(x.length-3);
        var otherNumbers = x.substring(0,x.length-3);
        if(otherNumbers != '')
            lastThree = ',' + lastThree;
        var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
        if (isNegative) {
                                                           return '-' + res;
                                                       } else {
                                                           return res;
                                                       }

          };
        })
        		.filter('indianFilter', function () {
                  return function (value) {
                    x=value.toString();
                    var isNegative = false;
                                                                if (x.substring(0,1) === '-'){
                                                                    x = x.substring(1);
                                                                    isNegative = true;
                                                                }
                var lastThree = x.substring(x.length-3);
                var otherNumbers = x.substring(0,x.length-3);
                if(otherNumbers != '')
                    lastThree = ',' + lastThree;
                var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree ;
                if (isNegative) {
                                                                   return '-' + res;
                                                               } else {
                                                                   return res;
                                                               }

                  };
                })
})()
