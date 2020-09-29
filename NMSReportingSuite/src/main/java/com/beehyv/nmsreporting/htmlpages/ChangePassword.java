package com.beehyv.nmsreporting.htmlpages;

public class ChangePassword {
    public static String pageContent ="\n" +
            "    <div class='login-header'>\n" +
            "        <!-- <img src=\"images/NHM_Logo.png\"> -->\n" +
            "    </div>\n" +
            "\n" +
            "\n" +
            "<div class=\"container-fluid\">\n" +
            "    <div class=\" col-xs-offset-4 col-xs-4\">\n" +
            "        <form name=\"changePasswordForm\" class=\"form-horizontal\" novalidate>\n" +
            "\n" +
            "            <div class=\"form-group\" data-ng-class=\"{'has-error':changePasswordForm.oldPassword.$invalid && !changePasswordForm.oldPassword.$pristine}\">\n" +
            "\n" +
            "                <div class=\"col-xs-10\">\n" +
            "                    <input name=\"oldPassword\" id=\"oldPassword\" autocomplete=\"off\"  type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"password.oldPassword\" placeholder=\"Old Password\" required>\n" +
            "\n" +
            "                    <div class=\"errorMessage\" data-ng-messages=\"changePasswordForm.oldPassword.$error\" role=\"alert\" data-ng-show=\"!changePasswordForm.oldPassword.$pristine\">\n" +
            "                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "\n" +
            "            <div class=\"form-group\" data-ng-class=\"{'has-error':changePasswordForm.newPassword.$invalid && !changePasswordForm.newPassword.$pristine}\">\n" +
            "\n" +
            "                <div class=\"col-xs-10\">\n" +
            "                    <input name=\"newPassword\" id=\"newPassword\" autocomplete=\"off\"  type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"password.newPassword\" placeholder=\"New Password\"  data-ng-pattern=\"/^(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$/\"  required>\n" +
            "\n" +
            "                    <div class=\"errorMessage\" data-ng-messages=\"changePasswordForm.newPassword.$error\" role=\"alert\" data-ng-show=\"!changePasswordForm.newPassword.$pristine\">\n" +
            "                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                        <div data-ng-message=\"pattern\">Password should be at least 8 characters\n" +
            "                            long and should contain at least one number,one upper case, one lower case and one special\n" +
            "                            character</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "            <div class=\"form-group\" data-ng-class=\"{'has-error':changePasswordForm.confirmPassword.$invalid && !changePasswordForm.confirmPassword.$pristine && (changePasswordForm.newPassword != changePasswordForm.confirmPassword)}\">\n" +
            "\n" +
            "                <div class=\"col-xs-10\">\n" +
            "                    <input name=\"confirmPassword\" id=\"confirmPassword\" autocomplete=\"off\"  type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"confirmPassword\" placeholder=\"Confirm Password\" required\n" +
            "                           data-ui-validate=\"'$value==password.newPassword'\" data-ui-validate-watch=\" 'password.newPassword'\">\n" +
            "\n" +
            "                    <div class=\"errorMessage\" data-ng-messages=\"changePasswordForm.confirmPassword.$error\" role=\"alert\" data-ng-show=\"!changePasswordForm.confirmPassword.$pristine\">\n" +
            "                        <div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "                        <div data-ng-message=\"validator\">Password does not match</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "            <div class=\"form-group\">\n" +
            "                <div class=\"col-xs-offset-2 col-xs-10\">\n" +
            "                    <button type=\"button\" data-ng-click=\"changePasswordSubmit()\" class=\"btn btn-sm btn-primary\">Save</button>\n" +
            "                    <button type=\"button\" data-ng-click=\"clearForm()\" class=\"btn btn-sm btn-default\">Cancel</button>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </form>\n" +
            "    </div>\n" +
            "</div>";
}
