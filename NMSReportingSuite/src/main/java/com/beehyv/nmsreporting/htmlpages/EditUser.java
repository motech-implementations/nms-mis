package com.beehyv.nmsreporting.htmlpages;

public class EditUser {
    public static String pageContent ="<div data-ng-controller=\"EditUserController\"  class=\"editUser\">" +
            "\t<div class=\"block\">\n" +
            "\t\t<div class=\"block\">\n" +
            "\t\t\t<h1>Edit User</h1>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<form name=\"editUserForm\" class=\"form-horizontal\" novalidate>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.fullName.$invalid && !editUserForm.fullName.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Full Name</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"fullName\" id=\"fullName\" autocomplete=\"off\" type=\"text\" class=\"form-control input input-sm\" placeholder=\"Name\" data-ng-model=\"editUser.fullName\" required\n" +
            "\t\t\t\t\tdata-ng-pattern=\"/^[A-Za-z ]*$/\" data-ng-minlength=\"3\">\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"editUserForm.fullName.$error\" role=\"alert\" data-ng-show=\"!editUserForm.fullName.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"pattern\">Name should only contain alphabetical characters</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"minlength\">Name is too short</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.username.$invalid && !editUserForm.username.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Username</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"username\" type=\"text\" autocomplete=\"off\" class=\"form-control input input-sm\" placeholder=\"Username\" data-ng-model=\"editUser.username\" data-ng-disabled=\"true\">\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<hr>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.accessType.$invalid && !editUserForm.accessType.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Access Type</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"accessType\" id=\"accessType\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': roleLoading}\" data-ng-model=\"editUser.roleId\" data-ng-options=\"item.roleId as item.roleDescription for item in accessTypeList | filter: filterAccessType\" required>\n" +
            "\t\t\t\t\t</select>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"editUserForm.accessType.$error\" role=\"alert\" data-ng-show=\"!editUserForm.accessType.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">Select an option</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.accessLevel.$invalid && !editUserForm.accessLevel.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Access Level</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"accessLevel\" id=\"accessLevel\" class=\"form-control input input-sm\" data-ng-model=\"editUser.accessLevel\" data-ng-options=\"item for item in accessLevelList | filter: filterAccessLevel\" required>\n" +
            "\t\t\t\t\t</select>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"editUserForm.accessLevel.$error\" role=\"alert\" data-ng-show=\"!editUserForm.accessLevel.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">Select an option</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<hr>\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.state.$invalid && !editUserForm.state.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">State</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"state\" id=\"state\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': stateLoading}\" data-ng-model=\"place.stateId\" data-ng-options=\"item.stateId as item.stateName for item in states | orderBy : 'stateName'\" data-ng-disabled=\"getAccessLevel(1)\" data-ng-required=\"!getAccessLevel(1)\">\n" +
            "\t\t\t\t\t</select>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.district.$invalid && !editUserForm.district.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">District</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"district\" id=\"district\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': districtLoading}\" data-ng-model=\"place.districtId\" data-ng-options=\"item.districtId as item.districtName for item in districts | orderBy : 'districtName'\" data-ng-disabled=\"getAccessLevel(2)\" data-ng-required=\"!getAccessLevel(2)\">\n" +
            "\t\t\t\t\t</select>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.block.$invalid && !editUserForm.block.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Block</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"block\" id=\"block\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': blockLoading}\" data-ng-model=\"place.blockId\" data-ng-options=\"item.blockId as item.blockName for item in blocks | orderBy : 'blockName'\" data-ng-disabled=\"getAccessLevel(3)\" data-ng-required=\"!getAccessLevel(3)\">\n" +
            "\t\t\t\t\t</select>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<hr>\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.emailId.$invalid && !editUserForm.emailId.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Email Id</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"emailId\" id=\"emailId\" autocomplete=\"off\"  type=\"email\" class=\"form-control input input-sm\" data-ng-model=\"editUser.emailId\" placeholder=\"Email\" required>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"editUserForm.emailId.$error\" role=\"alert\" data-ng-show=\"!editUserForm.emailId.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"emailId\">Enter a valid emailId</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.phoneNumber.$invalid && !editUserForm.phoneNumber.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Contact no.</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"phoneNumber\" id=\"phoneNumber\" autocomplete=\"off\" type=\"text\" class=\"form-control input input-sm\" data-ng-model=\"editUser.phoneNumber\" placeholder=\"Contact no.\" required\n" +
            "\t\t\t\t\tdata-ng-pattern=\"/^[0-9]*$/\" data-ng-minlength=\"10\" data-ng-maxlength=\"10\">\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"editUserForm.phoneNumber.$error\" role=\"alert\" data-ng-show=\"!editUserForm.phoneNumber.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"pattern\">Input contains non-numeric characters</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"minlength\">Number is too short</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"maxlength\">Number is too long</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "<!-- \n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.newPassword.$invalid && !editUserForm.newPassword.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">New Password</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"newPassword\" id=\"newPassword\" type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"editUser.newPassword\" placeholder=\"New Password\" required\n" +
            "\t\t\t\t\ttooltip-placement=\"top\" uib-tooltip=\"Errors: {{editUserForm.newPassword.$error|json}}\">\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':editUserForm.confirmPassword.$invalid && !editUserForm.confirmPassword.$pristine && (editUserForm.newPassword != editUserForm.confirmPassword)}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Confirm Password</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"confirmPassword\" id=\"confirmPassword\" type=\"password\" class=\"form-control input input-sm\" data-ng-model=\"confirmPassword\" placeholder=\"Confirm Password\" required\n" +
            "\t\t\t\t\tui-validate=\"'$value==editUser.newPassword'\" ui-validate-watch=\" 'newPassword'\"\n" +
            "\t\t\t\t\ttooltip-placement=\"top\" uib-tooltip=\"Errors: {{editUserForm.confirmPassword.$error|json}}\">\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            " -->\t\t<hr>\n" +
            " \t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t<div class=\"col-xs-offset-2 col-xs-10\">\n" +
            "\t\t\t\t\t<button type=\"button\" class=\"btn btn-sm btn-default\" data-ui-sref=\"userManagement.userTable({pageNum: $stateParams.pageNum})\">Cancel</button>\n" +
            "\t\t\t\t\t<button type=\"button\" data-ng-click=\"editUserSubmit()\" class=\"btn btn-sm btn-primary\">Update User</button>\n" +
            "\t\t\t\t\t<button data-ng-click=\"deactivateUserSubmit()\" data-ng-if=\"!editUser.loggedAtLeastOnce\" type=\"button\" class=\"btn btn-sm btn-primary floatR\">Delete User</button>\n" +
            "\t\t\t\t\t<button data-ng-click=\"resetPasswordSubmit()\" type=\"button\" class=\"btn btn-sm btn-primary floatR\">Reset Password</button>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\t\t\t<!--<div class=\"form-group\">-->\n" +
            "\t\t\t\t<!--<div class=\"col-xs-offset-2 col-xs-10\">-->\n" +
            "\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\t\t\t<!--</div>-->\n" +
            "\n" +
            "\t\t</form>\n" +
            "<!-- \n" +
            "\t\t<div>\n" +
            "\t\t\t<script type=\"text/data-ng-template\" id=\"myModalContent.html\">\n" +
            "\t\t\t\t<div class=\"modal-header\">\n" +
            "\t\t\t\t\t<h3>I'm a modal!</h3>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div class=\"modal-body\">\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div class=\"modal-footer\">\n" +
            "\t\t\t\t\t<button class=\"btn btn-warning\" data-ng-click=\"cancel()\">Cancel</button>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</script>\n" +
            "\n" +
            "\t\t\t<button class=\"btn\" data-ng-click=\"open()\">Open me!</button>\n" +
            "\t\t</div>\n" +
            " -->\n" +
            "\t</div>" +
            "</div>";

}
