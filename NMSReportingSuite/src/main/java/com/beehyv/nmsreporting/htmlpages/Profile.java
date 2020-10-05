package com.beehyv.nmsreporting.htmlpages;

public class Profile {
    public static String pageContent ="<div class=\"container-fluid\">\n" +
            "\t<div data-ng-controller=\"ProfileController\" class=\"profile\" >" +
            "<div class=\"block\">\n" +
            "\t\t\t<div class=\"block\">\n" +
            "\t\t\t\t<h1>Profile</h1>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<form name=\"profileForm\" class=\"form-horizontal\" novalidate>\n" +
            "\n" +
            "\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t<label class=\"control-label col-xs-2\">Full Name</label>\n" +
            "\t\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t\t<input name=\"name\" type=\"text\" class=\"form-control input input-sm\" autocomplete=\"off\" placeholder=\"Full Name\" data-ng-model=\"user.name\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t<label class=\"control-label col-xs-2\">Username</label>\n" +
            "\t\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t\t<input name=\"username\" type=\"text\" class=\"form-control input input-sm\" autocomplete=\"off\"  placeholder=\"Username\" data-ng-model=\"user.username\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t<hr style=\"margin:30px\">\n" +
            "\n" +
            "\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t<div>\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">Access Level</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<select name=\"accessLevel\" class=\"form-control input input-sm\"  data-ng-model=\"user.accessLevel\" data-ng-options=\"item for item in accessLevelList\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t\t\t</select>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t<div>\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">Access Type</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<select name=\"accessLevel\" class=\"form-control input input-sm\"  data-ng-model=\"user.accessType\" data-ng-options=\"item for item in accessTypeList\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t\t</select>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t<div>\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">State</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<select name=\"state\" class=\"form-control input input-sm\"  data-ng-model=\"user.state\" data-ng-options=\"item for item in stateList\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t\t\t</select>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t<div>\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">District</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<select name=\"district\" class=\"form-control input input-sm\"  data-ng-model=\"user.district\" data-ng-options=\"item for item in districtList\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t\t</select>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t<div>\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">Block</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<select name=\"block\" class=\"form-control input input-sm\"  data-ng-model=\"user.block\" data-ng-options=\"item for item in blockList\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t\t\t</select>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</form>\n" +
            "\n" +
            "\t\t\t<hr style=\"margin:30px\">\n" +
            "\n" +
            "\t\t\t<form name=\"contactsForm\" class=\"form-horizontal\" novalidate>\n" +
            "\n" +
            "\t\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t\t<div data-ng-class=\"{'has-error':contactsForm.email.$invalid && !contactsForm.email.$pristine}\">\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">Email</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<input name=\"email\" type=\"email\" class=\"form-control input input-sm\" autocomplete=\"off\"  data-ng-model=\"contact.email\" placeholder=\"Email\" required>\n" +
            "\t\t\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"contactsForm.email.$error\" role=\"alert\" data-ng-show=\"!contactsForm.email.$pristine\">\n" +
            "\t\t\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t\t\t<div data-ng-message=\"email\">Enter a valid email</div>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t<div data-ng-class=\"{'has-error':contactsForm.phoneNumber.$invalid && !contactsForm.phoneNumber.$pristine}\">\n" +
            "\t\t\t\t\t\t<label class=\"control-label col-xs-2\">Contact no.</label>\n" +
            "\t\t\t\t\t\t<div class=\"col-xs-2\">\n" +
            "\t\t\t\t\t\t\t<input name=\"phoneNumber\" type=\"text\" class=\"form-control input input-sm\" autocomplete=\"off\" data-ng-model=\"contact.phoneNumber\" placeholder=\"Contact no.\" required\n" +
            "\t\t\t\t\t\t\tdata-ng-pattern=\"/^[0-9]*$/\" data-ng-minlength=\"10\" data-ng-maxlength=\"10\">\n" +
            "\n" +
            "\t\t\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"contactsForm.phoneNumber.$error\" role=\"alert\" data-ng-show=\"!contactsForm.phoneNumber.$pristine\">\n" +
            "\t\t\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t\t\t<div data-ng-message=\"pattern\">Input contains non-numeric characters</div>\n" +
            "\t\t\t\t\t\t\t\t<div data-ng-message=\"minlength\">Number is too short</div>\n" +
            "\t\t\t\t\t\t\t\t<div data-ng-message=\"maxlength\">Number is too long</div>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t<div class=\"form-group\" style=\"margin-top:30px\">\n" +
            "\t\t\t\t\t<div class=\"col-xs-offset-2 col-xs-10\">\n" +
            "\t\t\t\t\t\t<button type=\"button\" data-ng-click=\"resetContacts()\" class=\"btn btn-sm btn-default\">Reset</button>\n" +
            "\t\t\t\t\t\t<button type=\"button\" data-ng-click=\"updateContactsSubmit()\" class=\"btn btn-sm btn-primary\">Update Contact Info</button>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t</form>\n" +
            "\n" +
            "\t\t\t<!--<form name=\"changePasswordForm\" class=\"form-horizontal\" novalidate>-->\n" +
            "\n" +
            "\t\t\t\t<!--<div class=\"form-group\" data-ng-class=\"{'has-error':changePasswordForm.oldPassword.$invalid && !changePasswordForm.oldPassword.$pristine}\">-->\n" +
            "\t\t\t\t\t<!--<label class=\"control-label col-xs-2\">Old Password</label>-->\n" +
            "\t\t\t\t\t<!--<div class=\"col-xs-10\">-->\n" +
            "\t\t\t\t\t\t<!--<input name=\"oldPassword\" id=\"oldPassword\" type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"password.oldPassword\" placeholder=\"Old Password\" required>-->\n" +
            "\n" +
            "\t\t\t\t\t\t<!--<div class=\"errorMessage\" data-ng-messages=\"changePasswordForm.oldPassword.$error\" role=\"alert\" data-ng-show=\"!changePasswordForm.oldPassword.$pristine\">-->\n" +
            "\t\t\t\t\t\t\t<!--<div data-ng-message=\"required\">This field cannot be empty</div>-->\n" +
            "\t\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\n" +
            "\n" +
            "\t\t\t\t<!--<div class=\"form-group\" data-ng-class=\"{'has-error':changePasswordForm.newPassword.$invalid && !changePasswordForm.newPassword.$pristine}\">-->\n" +
            "\t\t\t\t\t<!--<label class=\"control-label col-xs-2\">New Password</label>-->\n" +
            "\t\t\t\t\t<!--<div class=\"col-xs-10\">-->\n" +
            "\t\t\t\t\t\t<!--<input name=\"newPassword\" id=\"newPassword\" type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"password.newPassword\" placeholder=\"New Password\" data-ng-minlength=\"6\" required>-->\n" +
            "\n" +
            "\t\t\t\t\t\t<!--<div class=\"errorMessage\" data-ng-messages=\"changePasswordForm.newPassword.$error\" role=\"alert\" data-ng-show=\"!changePasswordForm.newPassword.$pristine\">-->\n" +
            "\t\t\t\t\t\t\t<!--<div data-ng-message=\"required\">This field cannot be empty</div>-->\n" +
            "\t\t\t\t\t\t\t<!--<div data-ng-message=\"minlength\">Password has to be atleast 6 characters long</div>-->\n" +
            "\t\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\n" +
            "\t\t\t\t<!--<div class=\"form-group\" data-ng-class=\"{'has-error':changePasswordForm.confirmPassword.$invalid && !changePasswordForm.confirmPassword.$pristine && (changePasswordForm.newPassword != changePasswordForm.confirmPassword)}\">-->\n" +
            "\t\t\t\t\t<!--<label class=\"control-label col-xs-2\">Confirm Password</label>-->\n" +
            "\t\t\t\t\t<!--<div class=\"col-xs-10\">-->\n" +
            "\t\t\t\t\t\t<!--<input name=\"confirmPassword\" id=\"confirmPassword\" type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"confirmPassword\" placeholder=\"Confirm Password\" required -->\n" +
            "\t\t\t\t\t\t<!--data-ui-validate=\"'$value==password.newPassword'\" data-ui-validate-watch=\" 'password.newPassword'\">-->\n" +
            "\n" +
            "\t\t\t\t\t\t<!--<div class=\"errorMessage\" data-ng-messages=\"changePasswordForm.confirmPassword.$error\" role=\"alert\" data-ng-show=\"!changePasswordForm.confirmPassword.$pristine\">-->\n" +
            "\t\t\t\t\t\t\t<!--<div data-ng-message=\"required\">This field cannot be empty</div>-->\n" +
            "\t\t\t\t\t\t\t<!--<div data-ng-message=\"validator\">Password does not match</div>-->\n" +
            "\t\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\n" +
            "\t\t\t\t<!--<div class=\"form-group\">-->\n" +
            "\t\t\t\t\t<!--<div class=\"col-xs-offset-2 col-xs-10\">-->\n" +
            "\t\t\t\t\t\t<!--<button type=\"button\" data-ng-click=\"clearForm()\" class=\"btn btn-sm btn-default\">Clear</button>-->\n" +
            "\t\t\t\t\t\t<!--<button type=\"button\" data-ng-click=\"changePasswordSubmit()\" class=\"btn btn-sm btn-primary\">Change Password</button>-->\n" +
            "\t\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\t\t\t<!--</form>-->\n" +
            "\t\t</div>" +
            "\t</div>\n" +
            "</div>";
}
