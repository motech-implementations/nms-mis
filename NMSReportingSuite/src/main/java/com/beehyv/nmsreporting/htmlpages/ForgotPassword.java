package com.beehyv.nmsreporting.htmlpages;

public class ForgotPassword {
    public static String pageContent ="<div class=\"block login\" data-ng-controller=\"ForgotPasswordController\">" +
            "\n" +
            "    <div class='login-header'>\n" +
            "        <!-- <img src=\"images/NHM_Logo.png\"> -->\n" +
            "    </div>\n" +
            "\n" +
            "\n" +
            "<div   class=\"container-fluid login-body\">\n" +
            "    <div class=\"form-horizontal col-xs-offset-4 col-xs-4\">\n" +
            "        <form name=\"forgotPasswordForm\">\n" +
            "\n" +
            "            <div  data-ng-if=\"!waiting\" class=\"form-group\" data-ng-class=\"{'has-error':forgotPasswordForm.username.$invalid && !forgotPasswordForm.username.$pristine}\">\n" +
            "\n" +
            "                    <input name=\"username\" id=\"username\" type=\"text\" autocomplete=\"off\" class=\"form-control input input-sm\" placeholder=\"Username\" data-ng-model=\"forgotPassword.username\" required\n" +
            "                           data-ng-minlength=\"2\">\n" +
            "\n" +
            "                    <div class=\"errorMessage\" data-ng-messages=\"forgotPasswordForm.username.$error\" role=\"alert\" data-ng-show=\"!forgotPasswordForm.username.$pristine\">\n" +
            "                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                        <div data-ng-message=\"minlength\">Username is too short</div>\n" +
            "                    </div>\n" +
            "\n" +
            "            </div>\n" +
            "            <!--<div class=\"form-group\" data-ng-class=\"{'has-error':forgotPasswordForm.phoneNumber.$invalid && !forgotPasswordForm.phoneNumber.$pristine}\">-->\n" +
            "\n" +
            "                    <!--<input name=\"phoneNumber\" id=\"phoneNumber\" type=\"text\" autocomplete=\"off\" class=\"form-control input input-sm\" data-ng-model=\"forgotPassword.phoneNumber\" placeholder=\"Contact no.\" required-->\n" +
            "                           <!--data-ng-pattern=\"/^[0-9]*$/\" data-ng-minlength=\"10\" data-ng-maxlength=\"10\">-->\n" +
            "\n" +
            "                    <!--<div class=\"errorMessage\" data-ng-messages=\"forgotPasswordForm.phoneNumber.$error\" role=\"alert\" data-ng-show=\"!forgotPasswordForm.phoneNumber.$pristine\">-->\n" +
            "                        <!--<div data-ng-message=\"required\">This field cannot be empty</div>-->\n" +
            "                        <!--<div data-ng-message=\"pattern\">Input contains non-numeric characters</div>-->\n" +
            "                        <!--<div data-ng-message=\"minlength\">Number is too short</div>-->\n" +
            "                        <!--<div data-ng-message=\"maxlength\">Number is too long</div>-->\n" +
            "                    <!--</div>-->\n" +
            "\n" +
            "            <!--</div>-->\n" +
            "            <!--<div class=\"form-group\" data-ng-class=\"{'has-error':forgotPasswordForm.newPassword.$invalid && !forgotPasswordForm.newPassword.$pristine}\">-->\n" +
            "\n" +
            "                    <!--<input name=\"newPassword\" id=\"newPassword\" type=\"password\" autocomplete=\"off\" class=\"form-control input input-sm\" data-ng-model=\"forgotPassword.newPassword\" placeholder=\"New Password\" data-ng-pattern=\"/^(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$/\" required>-->\n" +
            "\n" +
            "                    <!--<div class=\"errorMessage\" data-ng-messages=\"forgotPasswordForm.newPassword.$error\" role=\"alert\" data-ng-show=\"!forgotPasswordForm.newPassword.$pristine\">-->\n" +
            "                        <!--<div data-ng-message=\"required\">This field cannot be empty</div>-->\n" +
            "                        <!--<div data-ng-show = \"!registerForm.userPassword.$valid\" data-ng-message=\"pattern\">Password should be at least 8 characters-->\n" +
            "                            <!--long and should contain at least one number,one upper case, one lower case and one special-->\n" +
            "                            <!--character</div>-->\n" +
            "                    <!--</div>-->\n" +
            "\n" +
            "            <!--</div>-->\n" +
            "            <!--<div class=\"form-group\" data-ng-class=\"{'has-error':forgotPasswordForm.confirmPassword.$invalid && !forgotPasswordForm.confirmPassword.$pristine && (forgotPasswordForm.newPassword != forgotPasswordForm.confirmPassword)}\">-->\n" +
            "\n" +
            "                    <!--<input name=\"confirmPassword\" id=\"confirmPassword\" type=\"password\" autocomplete=\"off\" class=\"form-control input input-sm\" data-ng-model=\"forgotPassword.confirmPassword\" placeholder=\"Confirm Password\" required-->\n" +
            "                           <!--data-ui-validate=\"'$value==forgotPassword.newPassword'\" data-ui-validate-watch=\" 'forgotPassword.confirmPassword'\">-->\n" +
            "\n" +
            "                    <!--<div class=\"errorMessage\" data-ng-messages=\"forgotPasswordForm.confirmPassword.$error\" role=\"alert\" data-ng-show=\"!forgotPasswordForm.confirmPassword.$pristine\">-->\n" +
            "                        <!--<div data-ng-message=\"required\">This field cannot be empty</div>-->\n" +
            "                        <!--<div data-ng-message=\"validator\">Password does not match</div>-->\n" +
            "                    <!--</div>-->\n" +
            "\n" +
            "            <!--</div>-->\n" +
            "\n" +
            "\n" +
            "            <!--<div data-ng-if=\"!waiting\" class=\"form-group row d-flex flex-row justify-content-between\" data-ng-init=\"Captcha()\">-->\n" +
            "                <!--<div class=\" col-xs-1\"></div>-->\n" +
            "                <!--&lt;!&ndash;<div class=\" col-xs-3 ff-input-textalign\">&ndash;&gt;-->\n" +
            "                    <!--&lt;!&ndash;<label>Captcha<span style=\"color : red\">*</span></label>&ndash;&gt;-->\n" +
            "                <!--&lt;!&ndash;</div>&ndash;&gt;-->\n" +
            "                <!--<button type=\"button\" id=\"refresh\" data-ng-click=\"refreshCaptcha()\" class=\"m-2\" style=\"margin-left:-40px\"><span class=\"glyphicon glyphicon-refresh\"></span></button>-->\n" +
            "                <!--<input type=\"text\" autocomplete=\"off\" data-ng-cut=\"$event.preventDefault()\" data-ng-copy=\"$event.preventDefault()\"  class=\"form-control input input-sm login-inputs\" data-ng-disabled=\"true\" name=\"mainCaptchaCode\" style=\"background-color:black;font-weight:bold;font-style:italic;height:40px;width:59%;padding-left:40px;color:white; margin-right : 27%\" data-ng-model=\"forgotPassword.mainCaptchaCode\"/>-->\n" +
            "                    <!--</div>-->\n" +
            "            <!--<div data-ng-if=\"!waiting\" class=\"form-group\"><input type=\"text\" data-ng-paste=\"$event.preventDefault()\" style=\"text-transform:uppercase; margin-right : 27%; width : 59%\" class=\"form-control input input-sm login-inputs\" name=\"captchaCode\" autocomplete=\"off\" data-ng-model=\"forgotPassword.captchaCode\"/>-->\n" +
            "            <!--</div>-->\n" +
            "            <div style=\"margin-bottom: 10px\" data-ng-if=\"!waiting\" vc-recaptcha key=\"'6LcJgYgUAAAAANaRqYM6UFUaY8H3pPUTK6Tt1qsL'\"></div>\n" +
            "\n" +
            "            <div data-ng-if=\"!waiting\" class=\"form-group\">\n" +
            "                <button data-ng-click=\"forgotPasswordSubmit()\"  id=\"btn-login\" class=\"btn btn-sm btn-primary\">submit</button>\n" +
            "                <button id =\"back-to-login\" type=\"button\" data-ui-sref = \"login\" class=\"btn btn-sm btn-default\">Back To Login</button>\n" +
            "            </div>\n" +
            "\n" +
            "\n" +
            "        </form>\n" +
            "        <div data-ng-if=\"waiting\" class=\"loading\">\n" +
            "            <h1>please wait while loading</h1>\n" +
            "            <img src=\"images/pageloader.gif\" alt=\"loading...\">\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>" +
            "</div>";
}
