(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("AshaIndividualCertificateController", ['$scope', '$state', '$http','UserFormFactory','$location','$timeout', function($scope, $state, $http, UserFormFactory, $location, $timeout){
            $scope.countdownValue = 30;
            $scope.otpButtonDisabled = false;
            $scope.countdownMessage = "";
            $scope.getAshaCertificate = function() {
                $scope.errorMessage=false;
                $scope.fileDownloadedSuccessFully=false;
                const l = '' + $scope.mobile_number;
                if(l.length==10) {
                    $scope.isBeingGenerated=true;
                    var token = 'dhty'+UserFormFactory.getCurrentUser().userId+'alkihkf';
                    $http({
                        method: 'POST',
                        url: backend_root + 'nms/user/asha/getCertificate' + "?msisdn=" + $scope.mobile_number + "&otp=" + $scope.certificate_otp,
                        headers: {'Content-Type': 'application/json', 'csrfToken': token}
                    }).then(function (result) {
                        $scope.isBeingGenerated=false;
                        if (result.data.status == "success") {
                            $scope.fileDownloadedSuccessFully = true;
//                            result.data.downloadCertificateUrl = backend_root + 'nms/user/downloadCertificate?fileName=' + result.data.file + '&rootPath=' + result.data.path;
                            console.log("result", result);
                            $scope.certificate = result.data;
                            console.log("certificate", $scope.certificate);
                        } else {
                            $scope.errorMessage = true;
                            $scope.message = result.data.cause;
                        }
                    });
                } else {
                    $scope.errorMessage = true;
                    $scope.message = "Please Enter Valid Mobile Number";
                }
            }
             $scope.getAshaCertificateOTP = function() {
                            $scope.errorMessage=false;
                            $scope.fileDownloadedSuccessFully=false;
                            $scope.otpButtonDisabled = true;
                            const l = '' + $scope.mobile_number;
                            if(l.length==10) {
                                const captchaResponse = grecaptcha.getResponse();
                                 if (!captchaResponse) {
                                            $scope.errorMessage = true;
                                            $scope.message = "Please complete the CAPTCHA validation.";
                                            $scope.otpButtonDisabled = false;
                                            grecaptcha.reset();
                                            return;
                                 }
                                var token = 'dhty'+UserFormFactory.getCurrentUser().userId+'alkihkf';
                                $http({
                                      method: 'POST',
                                      url: backend_root + 'nms/user/asha/generateOTP',
                                      data: {
                                                      msisdn: $scope.mobile_number,
                                                      captchaResponse: captchaResponse
                                                  },
                                      headers: {'Content-Type': 'application/json', 'csrfToken': token}
                                      }).then(function (result) {
                                      $scope.errorMessage = true;
                                      $scope.message = result.data;
                                }).catch(function (error) {
                                              $scope.errorMessage = true;
                                              $scope.message = "Failed to generate OTP. Please try again.";
                                              console.error(error);
                                          }).finally(function () {
                                              $scope.otpButtonDisabled = false; // Re-enable the button
                                               grecaptcha.reset();
                                          });
                                $scope.updateCountdown();
                            } else {
                                    $scope.errorMessage = true;
                                    $scope.message = "Please Enter Valid Mobile Number";
                                    $scope.otpButtonDisabled = false; // Re-enable OTP button if validation fails
                                     grecaptcha.reset();
                            }
                        }

                $scope.updateCountdown = function () {
                    if ($scope.countdownValue > 0) {
                        $scope.countdownMessage = "Resend OTP in " + $scope.countdownValue + " seconds";
                        $scope.countdownValue--;

                        // Continue the countdown after 1 second
                        $timeout($scope.updateCountdown, 1000);
                    } else {
                        // Enable the OTP request button and clear the countdown message
                        $scope.otpButtonDisabled = false;
                        $scope.countdownValue = 30; // Reset the countdown value to 30 seconds
                        $scope.countdownMessage = "";
                    }
                }
             $scope.validateMobileNumber = function () {
                 if ($scope.mobile_number) {
                     $scope.mobile_number = $scope.mobile_number.replace(/[^0-9]/g, '');

                     if ($scope.mobile_number.length > 10) {
                         $scope.mobile_number = $scope.mobile_number.substring(0, 10);
                     }
                 }
             };

             $scope.validateOTP = function () {
                if ($scope.certificate_otp) {
                    $scope.certificate_otp = $scope.certificate_otp.replace(/[^0-9]/g, '');

                    if ($scope.certificate_otp.length > 6) {
                        $scope.certificate_otp = $scope.certificate_otp.substring(0, 6);
                    }
                }
             };

            var param= $location.search();
            if(param.mobileNo){
                $scope.mobile_number = Number(param.mobileNo);
                $scope.getAshaCertificateOTP();
            }
		}])
})();