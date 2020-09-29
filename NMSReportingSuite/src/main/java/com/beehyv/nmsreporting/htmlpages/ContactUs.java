package com.beehyv.nmsreporting.htmlpages;

public class ContactUs {
    public static String pageContent ="    <div data-ui-view></div>\n" +
            "\n" +
            "    <div class=\"contactUs-background\" style=\"margin-top:0px;\">\n" +
            "        <div class=\"row\">\n" +
            "            <div class=\"col-sm-12 col-xs-12 col-lg-12\">\n" +
            "                <table class=\"contactPageHeading\">\n" +
            "                    <tr>\n" +
            "                        <th><p style=\"font-size: 30px; margin-top: 10px; padding-left: 15px;font-weight: 300\">Contact\n" +
            "                            us</p></th>\n" +
            "                        <th>\n" +
            "                            <hr class=\"infoHR\"/>\n" +
            "                        </th>\n" +
            "                    </tr>\n" +
            "                </table>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"row cs-content contactUs-paddingLeft\">\n" +
            "            <!-- <div class=\"col-sm-3 col-xs-3 col-lg-3 contactUs-locationColumn\">\n" +
            "                 <div class=\"contactUs-locationColumn-icon\"><img alt=\"contact location\" src=\"images/contact_location.png\"></div>\n" +
            "                 <div style=\"text-align: justify;\">\n" +
            "                     Lorem ipsum dolor sit nostra\n" +
            "                     Lorem ipsum dolor sit nostra\n" +
            "                     Lorem ipsum dolor sit nostra\n" +
            "                     Lorem ipsum dolor sit nostra\n" +
            "                     Lorem ipsum dolor sit nostra\n" +
            "                     India\n" +
            "                 </div>\n" +
            "             </div>\n" +
            "             <div class=\"col-sm-2 col-xs-2 col-lg-2 contactUs-phoneColumn\">\n" +
            "                 <div class=\"contactUs-phoneColumn-icon\"><img alt =\"contact phone\" src=\"images/contact_phone.png\"></div>\n" +
            "                 <div>\n" +
            "                     080 235252352\n" +
            "                     040 523652365\n" +
            "                     070 253652352\n" +
            "                 </div>\n" +
            "             </div>\n" +
            "             <div class=\"col-sm-3 col-xs-3 col-lg-3 contactUs-mailColumn\">\n" +
            "                 <div class=\"contactUs-mailColumn-icon\"><img alt =\"contact mail\" src=\"images/contact_mail.png\"></div>\n" +
            "                 <div>\n" +
            "                     Sampleemail@sampleemail.com\n" +
            "                     Sampleemail@sampleemail.com\n" +
            "                     Sampleemail@sampleemail.com\n" +
            "                 </div>\n" +
            "             </div>-->\n" +
            "            <div class=\"col-sm-8 col-xs-8 col-lg-8 contactUs-locationColumn\">MMP Cell, MoHFW, Room No.509-D, Nirman\n" +
            "                Bhawan, and New Delhi 110001.\n" +
            "            </div>\n" +
            "            <div class=\"col-sm-4 col-xs-4 col-lg-4\" style=\"background-color : #f2f2f2\">\n" +
            "                <div class=\"row cf-icon\">\n" +
            "                    <div class=\" col-sm-1 col-xs-1 col-lg-1\">\n" +
            "                        <span><img src=\"images/feedback.png\"></span>\n" +
            "                    </div>\n" +
            "                    <div class=\" col-sm-11 col-xs-11 col-lg-11\">\n" +
            "                        <span>Contact Us Form</span>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div>\n" +
            "                    <form name=\"ContactUsForm\" class=\"form-horizontal\" data-ng-submit=\"contactUs($event)\" novalidate>\n" +
            "                        <div class=\"form-group row cf-top-padding\">\n" +
            "                            <div class=\" col-sm-4 col-xs-4 col-lg-4 cf-txtAlign\">\n" +
            "                                <label for=\"name\">Name<span style=\"color : red\">*</span></label>\n" +
            "                            </div>\n" +
            "                            <div data-ng-class=\"{'has-error':ContactUsForm.name.$invalid && !ContactUsForm.name.$pristine}\">\n" +
            "\n" +
            "                                <div class=\"col-sm-8 col-xs-8 col-lg-8\">\n" +
            "                                    <input name=\"name\" id=\"name\" type=\"text\" autocomplete=\"off\"\n" +
            "                                           class=\"form-control input input-sm\" data-ng-model=\"email.name\"\n" +
            "                                           placeholder=\"Name\" style=\"width: 220px;\" required>\n" +
            "\n" +
            "                                    <div class=\"errorMessage\" data-ng-messages=\"ContactUsForm.name.$error\" role=\"alert\"\n" +
            "                                         data-ng-show=\"!ContactUsForm.name.$pristine\">\n" +
            "                                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"form-group row\" style=\"padding-bottom: 10px;height:30px;\">\n" +
            "                            <div class=\" col-sm-4 col-xs-4 col-lg-4 cf-txtAlign\">\n" +
            "                                <label for=\"phoneNo\">Phone No.<span style=\"color : red\">*</span></label>\n" +
            "                            </div>\n" +
            "                            <div data-ng-class=\"{'has-error':ContactUsForm.phoneNo.$invalid && !ContactUsForm.phoneNo.$pristine}\">\n" +
            "\n" +
            "                                <div class=\"col-sm-8 col-xs-8 col-lg-8 fake-input\">\n" +
            "                                    <input name=\"phoneNo\" autocomplete=\"off\" id=\"phoneNo\" type=\"text\"\n" +
            "                                           class=\"form-control input input-sm\" data-ng-model=\"email.phoneNo\"\n" +
            "                                           placeholder=\"Contact no.\" required\n" +
            "                                           style=\"width: 220px;\" data-ng-pattern=\"/^[0-9]*$/\" data-ng-minlength=\"10\"\n" +
            "                                           data-ng-maxlength=\"10\">\n" +
            "                                    <p class=\"fake-input\"><b>+91</b></p>\n" +
            "                                    <div class=\"errorMessage\" data-ng-messages=\"ContactUsForm.phoneNo.$error\"\n" +
            "                                         role=\"alert\" data-ng-show=\"!ContactUsForm.phoneNo.$pristine\">\n" +
            "                                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                                        <div data-ng-message=\"pattern\">Input contains non-numeric characters</div>\n" +
            "                                        <div data-ng-message=\"minlength\">Number is too short</div>\n" +
            "                                        <div data-ng-message=\"maxlength\">Number is too long</div>\n" +
            "                                    </div>\n" +
            "\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"form-group row\" style=\"padding-bottom: 10px;\">\n" +
            "                            <div class=\" col-sm-4 col-xs-4 col-lg-4 cf-txtAlign\">\n" +
            "                                <label for=\"email\">Email<span style=\"color : red\">*</span></label>\n" +
            "                            </div>\n" +
            "                            <div data-ng-class=\"{'has-error':ContactUsForm.email.$invalid &amp;&amp; !ContactUsForm.email.$pristine}\"\n" +
            "                                 style=\"\">\n" +
            "                                <div class=\"col-sm-8 col-xs-8 col-lg-8\">\n" +
            "                                    <input id=\"email\" name=\"email\" type=\"email\" autocomplete=\"off\"\n" +
            "                                           class=\"form-control input input-sm ng-touched ng-dirty ng-invalid ng-not-empty ng-invalid-email ng-valid-required ng-invalid-remove ng-valid-email-add ng-invalid-email-remove\"\n" +
            "                                           data-ng-model=\"email.email\" placeholder=\"Email\" aria-invalid=\"true\"\n" +
            "                                           style=\"width:220px;\" required>\n" +
            "                                    <div class=\"errorMessage ng-active\" data-ng-messages=\"ContactUsForm.email.$error\"\n" +
            "                                         role=\"alert\" data-ng-show=\"!ContactUsForm.email.$pristine\"\n" +
            "                                         aria-live=\"assertive\" aria-hidden=\"false\" style=\"\">\n" +
            "                                        <!-- ngMessage: required -->\n" +
            "                                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                                        <!-- ngMessage: email -->\n" +
            "                                        <div data-ng-message=\"email\" class=\"ng-scope\" style=\"\">Enter a valid email</div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"form-group row\" style=\"padding-bottom: 0px;\">\n" +
            "                            <div class=\" col-sm-4 col-xs-4 col-lg-4 cf-txtAlign\">\n" +
            "                                <label>Message<span style=\"color : red\">*</span></label>\n" +
            "                            </div>\n" +
            "                            <div data-ng-class=\"{'has-error':ContactUsForm.body.$invalid && !ContactUsForm.body.$pristine}\">\n" +
            "\n" +
            "                                <div class=\"col-sm-8 col-xs-8 col-lg-8\">\n" +
            "                                <textarea name=\"body\"\n" +
            "                                          class=\"form-control input input-sm ng-pristine ng-valid ng-empty ng-touched\"\n" +
            "                                          data-ng-model=\"email.body\" style=\"width: 220px; height: 95px; resize: none;\"\n" +
            "                                          aria-invalid=\"false\">\n" +
            "                                    Your Message Here\n" +
            "                                </textarea>\n" +
            "\n" +
            "                                    <div class=\"errorMessage\" data-ng-messages=\"ContactUsForm.body.$error\" role=\"alert\"\n" +
            "                                         data-ng-show=\"!ContactUsForm.body.$pristine\">\n" +
            "                                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <!--<div class=\"form-group row \" data-ng-init=\"Captcha()\">-->\n" +
            "                        <!--<div class=\" col-sm-4 col-xs-4 col-lg-4 cf-txtAlign\">-->\n" +
            "                        <!--<label>Captcha<span style=\"color : red\">*</span></label>-->\n" +
            "                        <!--</div>-->\n" +
            "\n" +
            "                        <!--<div class=\"col-sm-8 col-xs-8 col-lg-8\">-->\n" +
            "                        <!--<button type=\"button\" id=\"refresh\" data-ng-click=\"Captcha()\"  style=\"margin-left:0px\"><span class=\"glyphicon glyphicon-refresh\"></span></button>-->\n" +
            "                        <!--<input type=\"text\" autocomplete=\"off\" data-ng-cut=\"$event.preventDefault()\" data-ng-copy=\"$event.preventDefault()\"  class=\"form-control input input-sm login-inputs\" data-ng-disabled=\"true\" name=\"mainCaptchaCode\" style=\"background-color:black;font-weight:bold;font-style:italic;height:42px;width:75%;padding-left:53px;color:white;\" data-ng-model=\"email.mainCaptchaCode\"/>-->\n" +
            "\n" +
            "                        <!--</div>-->\n" +
            "                        <!--</div>-->\n" +
            "                        <!--<div class=\"form-group\"><input type=\"text\" data-ng-paste=\"$event.preventDefault()\" style=\"text-transform:uppercase\" class=\"form-control input input-sm login-inputs\" name=\"captchaCode\" autocomplete=\"off\" data-ng-model=\"email.captchaCode\"/>-->\n" +
            "\n" +
            "                        <!--</div>-->\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "                            <input type=\"hidden\" name=\"from\" data-ng-model=\"email.from\" value=\"nsp-reports@beehyv.com\">\n" +
            "                            <input type=\"hidden\" name=\"subject\" data-ng-model=\"email.subject\"\n" +
            "                                   value=\"Subject For Contact\">\n" +
            "                        <div class=\"form-group row\" style=\"padding-left: 60px\">\n" +
            "                            <div vc-recaptcha key=\"'6LcJgYgUAAAAANaRqYM6UFUaY8H3pPUTK6Tt1qsL'\"></div>\n" +
            "                        </div>\n" +
            "                            <div class=\"cf-submit\">\n" +
            "                                <button type=\"submit\" id=\"hide_me\" class=\"btn btn-sm btn-primary\">Submit</button>\n" +
            "                            </div>\n" +
            "\n" +
            "                    </form>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>";
}
