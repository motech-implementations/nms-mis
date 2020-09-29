package com.beehyv.nmsreporting.htmlpages;

public class FeedbackForm {
    public static String pageContent ="    <div data-ui-view></div>\n" +
            "\n" +
            "    <div class=\"row margin-top-40 margin-bottom-40\">\n" +
            "        <div class=\"col-xs-4\"></div>\n" +
            "        <div class=\" ff-background col-xs-4\">\n" +
            "            <div class=\"row ff-icon\">\n" +
            "                <div class=\" col-xs-1\"><span><img alt=\"feedback\" src=\"images/feedback.png\"></span></div>\n" +
            "                <div class=\" col-xs-11\"><span>Feedback Form</span></div>\n" +
            "            </div>\n" +
            "            <div>\n" +
            "                <form name=\"FeedbackForm\" class=\"form-horizontal\" data-ng-submit=\"feedback($event)\" novalidate>\n" +
            "                    <div class=\"form-group row ff-padding-top\">\n" +
            "                        <div class=\" col-xs-1\"></div>\n" +
            "                        <div class=\" col-xs-3 ff-input-textalign\">\n" +
            "                        <label for=\"name\">Name<span style=\"color : red\">*</span></label>\n" +
            "                        </div>\n" +
            "                        <div class=\"col-xs-8\">\n" +
            "                            <input name=\"name\" id=\"name\" autocomplete=\"off\"  type=\"text\" class=\"form-control input input-sm\" data-ng-model=\"email.name\" placeholder=\"Name\" style=\"width: 220px;\" required>\n" +
            "\n" +
            "                            <div class=\"errorMessage\" data-ng-messages=\"FeedbackForm.name.$error\" role=\"alert\" data-ng-show=\"!FeedbackForm.name.$pristine\">\n" +
            "                                <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"form-group row\">\n" +
            "                        <div class=\" col-xs-1\"></div>\n" +
            "                        <div class=\" col-xs-3 ff-input-textalign\">\n" +
            "                        <label for=\"subject\">Subject<span style=\"color : red\">*</span></label>\n" +
            "                        </div>\n" +
            "                        <div class=\"col-xs-8\">\n" +
            "                            <input name=\"subject\" autocomplete=\"off\"  id=\"subject\" type=\"text\" class=\"form-control input input-sm\" data-ng-model=\"email.subject\" placeholder=\"Subject\" style=\"width: 220px;\" required>\n" +
            "\n" +
            "                            <div class=\"errorMessage\" data-ng-messages=\"FeedbackForm.subject.$error\" role=\"alert\" data-ng-show=\"!FeedbackForm.subject.$pristine\">\n" +
            "                                <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class=\"form-group row\" style=\"padding-bottom: 10px;height:30px;\">\n" +
            "                        <div class=\" col-sm-4 col-xs-4 col-lg-4 cf-txtAlign\">\n" +
            "                            <label for=\"phoneNo\">Phone No.</label>\n" +
            "                        </div>\n" +
            "                        <div data-ng-class=\"{'has-error':FeedbackForm.phoneNo.$invalid && !FeedbackForm.phoneNo.$pristine}\">\n" +
            "\n" +
            "                            <div class=\"col-sm-8 col-xs-8 col-lg-8 fake-input\">\n" +
            "                                <input name=\"phoneNo\" autocomplete=\"off\"  id=\"phoneNo\" type=\"text\" class=\"form-control input input-sm\" data-ng-model=\"email.phoneNo\" placeholder=\"Contact no.\"\n" +
            "                                       style=\"width: 220px;\" data-ng-pattern=\"/^[0-9]*$/\" data-ng-minlength=\"10\" data-ng-maxlength=\"10\">\n" +
            "                                <p class=\"fake-input\"><b>+91</b></p>\n" +
            "                                <div class=\"errorMessage\" data-ng-messages=\"FeedbackForm.phoneNo.$error\" role=\"alert\" data-ng-show=\"!FeedbackForm.phoneNo.$pristine\">\n" +
            "                                    <div data-ng-message=\"pattern\">Input contains non-numeric characters</div>\n" +
            "                                    <div data-ng-message=\"minlength\">Number is too short</div>\n" +
            "                                    <div data-ng-message=\"maxlength\">Number is too long</div>\n" +
            "                                </div>\n" +
            "\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class=\"form-group row\">\n" +
            "                        <div class=\" col-xs-1\"></div>\n" +
            "                        <div class=\" col-xs-3 ff-input-textalign\">\n" +
            "                        <label for=\"email\">Email</label>\n" +
            "                        </div>\n" +
            "                        <div data-ng-class=\"{'has-error':FeedbackForm.email.$invalid &amp;&amp; !FeedbackForm.email.$pristine}\" style=\"\">\n" +
            "                            <div class=\"col-xs-8 \">\n" +
            "                                <input id=\"email\" name=\"email\" type=\"email\" autocomplete=\"off\"  class=\"form-control input input-sm ng-touched ng-dirty ng-invalid ng-not-empty ng-invalid-email ng-valid-required ng-invalid-remove ng-valid-email-add ng-invalid-email-remove\" data-ng-model=\"email.email\" placeholder=\"Email\"  aria-invalid=\"true\" style=\"width:220px;\" >\n" +
            "                                <div class=\"errorMessage ng-active\" data-ng-messages=\"FeedbackForm.email.$error\" role=\"alert\" data-ng-show=\"!FeedbackForm.email.$pristine\" aria-live=\"assertive\" aria-hidden=\"false\" style=\"\">\n" +
            "                                    <!-- ngMessage: required --><div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                                    <!-- ngMessage: email --><div data-ng-message=\"email\" class=\"ng-scope\" style=\"\">Enter a valid email</div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"form-group row\">\n" +
            "                        <div class=\" col-xs-1\"></div>\n" +
            "                        <div class=\" col-xs-3 ff-input-textalign\">\n" +
            "                        <label>Feedback<span style=\"color : red\">*</span></label>\n" +
            "                        </div>\n" +
            "                        <div data-ng-class=\"{'has-error':FeedbackForm.body.$invalid && !FeedbackForm.body.$pristine}\">\n" +
            "\n" +
            "                            <div class=\"col-xs-8\">\n" +
            "                                <textarea name=\"body\" class=\"form-control input input-sm ng-pristine ng-valid ng-empty ng-touched\" data-ng-model=\"email.body\" style=\"width: 220px; height: 95px; resize: none;\" aria-invalid=\"false\">\n" +
            "                                    Your Message Here\n" +
            "                                </textarea>\n" +
            "\n" +
            "                                <div class=\"errorMessage\" data-ng-messages=\"FeedbackForm.body.$error\" role=\"alert\" data-ng-show=\"!FeedbackForm.body.$pristine\">\n" +
            "                                    <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <div class=\"form-group row\" style=\"padding-left: 60px\">\n" +
            "                        <div vc-recaptcha key=\"'6LcJgYgUAAAAANaRqYM6UFUaY8H3pPUTK6Tt1qsL'\"></div>\n" +
            "                    </div>\n" +
            "                    <input type=\"hidden\" name=\"from\" data-ng-model=\"email.from\" value=\"nsp-reports@beehyv.com\">\n" +
            "                    <!--<div vc-recaptcha key=\"'6LfKH08UAAAAAKmEugRBtMKYaagj-CiluRt9h1tK'\"data-ng-model=\"captchaResponse\" class=\"recaptcha ff-captcha\"></div>-->\n" +
            "                    <!--<div class=\"form-group row d-flex flex-row justify-content-between\" data-ng-init=\"Captcha()\">-->\n" +
            "                        <!--<div class=\" col-xs-1\"></div>-->\n" +
            "                        <!--<div class=\" col-xs-3 ff-input-textalign\">-->\n" +
            "                            <!--<label>Captcha<span style=\"color : red\">*</span></label>-->\n" +
            "                        <!--</div>-->\n" +
            "                        <!--<button type=\"button\" id=\"refresh\" data-ng-click=\"Captcha()\" class=\"m-2\" style=\"margin-left:0px\"><span class=\"glyphicon glyphicon-refresh\"></span></button>-->\n" +
            "                        <!--<input type=\"text\" autocomplete=\"off\" data-ng-cut=\"$event.preventDefault()\" data-ng-copy=\"$event.preventDefault()\"  class=\"form-control input input-sm login-inputs\" data-ng-disabled=\"true\" name=\"mainCaptchaCode\" style=\"background-color:black;font-weight:bold;font-style:italic;height:40px;width:45%;padding-left:40px;color:white;\" data-ng-model=\"email.mainCaptchaCode\"/>-->\n" +
            "                    <!--</div>-->\n" +
            "                    <!--<div class=\"form-group\"><input type=\"text\" data-ng-paste=\"$event.preventDefault()\" style=\"text-transform:uppercase\" class=\"form-control input input-sm login-inputs\" name=\"captchaCode\" autocomplete=\"off\" data-ng-model=\"email.captchaCode\"/>-->\n" +
            "                    <!--</div>-->\n" +
            "                    <div class=\"form-group ff-submit\"><button type=\"submit\" id=\"hide_me\" class=\"btn btn-sm btn-primary\">Submit</button></div>\n" +
            "\n" +
            "                </form>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"col-xs-4\"></div>\n" +
            "    </div>";
}
