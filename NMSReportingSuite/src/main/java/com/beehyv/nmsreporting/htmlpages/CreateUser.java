package com.beehyv.nmsreporting.htmlpages;

public class CreateUser {
    public static String pageContent ="<div data-ng-controller=\"CreateUserController\" class=\"createUser\" data-ng-if=\"currentUser.roleId!= null && currentUser.roleName != 'USER'\" >\n" +
            "\t<div class=\"block\">\n" +
            "\t\t<div class=\"block\">\n" +
            "\t\t\t<h1>Create new User</h1>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<form name=\"createUserForm\" class=\"form-horizontal\" novalidate>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.fullName.$invalid && !createUserForm.fullName.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Full Name</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"fullName\" id=\"fullName\" type=\"text\" class=\"form-control input input-sm\" placeholder=\"Full Name\" data-ng-model=\"newUser.fullName\" required\n" +
            "\t\t\t\t\tdata-ng-pattern=\"/^[A-Za-z ]*$/\" data-ng-minlength=\"3\">\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"createUserForm.fullName.$error\" role=\"alert\" data-ng-show=\"!createUserForm.fullName.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"pattern\">Name should only contain alphabetical characters</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"minlength\">Name is too short</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.username.$invalid && !createUserForm.username.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Username</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"username\" id=\"username\" type=\"text\" class=\"form-control input input-sm\" placeholder=\"Username\" data-ng-model=\"newUser.username\" required\n" +
            "\t\t\t\t\tdata-ng-pattern=\"/^[A-Za-z0-9]*$/\" data-ng-minlength=\"5\">\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"createUserForm.username.$error\" role=\"alert\" data-ng-show=\"!createUserForm.username.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"pattern\">Username should not contain special characters or spaces</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"minlength\">Username is too short</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<hr>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.accessType.$invalid && !createUserForm.accessType.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Access Type</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"accessType\" id=\"accessType\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': roleLoading}\" data-ng-model=\"newUser.roleId\" data-ng-options=\"item.roleId as item.roleDescription for item in accessTypeList | filter:filterAccessType\" required>\n" +
            "\t\t\t\t\t</select>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"createUserForm.accessType.$error\" role=\"alert\" data-ng-show=\"!createUserForm.accessType.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">Select an option</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.accessLevel.$invalid && !createUserForm.accessLevel.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Access Level</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"accessLevel\" id=\"accessLevel\" class=\"form-control input input-sm\"  data-ng-model=\"newUser.accessLevel\" data-ng-options=\"item for item in accessLevelList | filter: filterAccessLevel\" required>\n" +
            "\t\t\t\t\t</select>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"createUserForm.accessLevel.$error\" role=\"alert\" data-ng-show=\"!createUserForm.accessLevel.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">Select an option</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<hr>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.state.$invalid && !createUserForm.state.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">State</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"state\" id=\"state\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': stateLoading}\" data-ng-model=\"newUser.stateId\" data-ng-options=\"item.stateId as item.stateName for item in states | orderBy : 'stateName'\" data-ng-disabled=\"getAccessLevel(1)\" data-ng-required=\"!getAccessLevel(1)\">\n" +
            "\t\t\t\t\t</select>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.district.$invalid && !createUserForm.district.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">District</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"district\" id=\"district\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': stateLoading}\" data-ng-model=\"newUser.districtId\" data-ng-options=\"item.districtId as item.districtName for item in districts | orderBy : 'districtName'\" data-ng-disabled=\"getAccessLevel(2)\" data-ng-required=\"!getAccessLevel(2)\">\n" +
            "\t\t\t\t\t</select>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.block.$invalid && !createUserForm.block.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Block</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<select name=\"block\" id=\"block\" class=\"form-control input input-sm\" data-ng-class=\"{'loading': stateLoading}\" data-ng-model=\"newUser.blockId\" data-ng-options=\"item.blockId as item.blockName for item in blocks | orderBy : 'districtName'\" data-ng-disabled=\"getAccessLevel(3)\" data-ng-required=\"!getAccessLevel(3)\">\n" +
            "\t\t\t\t\t</select>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<hr>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.emailId.$invalid && !createUserForm.emailId.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Email</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"emailId\" id=\"emailId\" autocomplete=\"off\" type=\"email\" class=\"form-control input input-sm\" data-ng-model=\"newUser.emailId\" placeholder=\"Email\" required>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"createUserForm.emailId.$error\" role=\"alert\" data-ng-show=\"!createUserForm.emailId.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"email\">Enter a valid email</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t\t<div class=\"form-group\" data-ng-class=\"{'has-error':createUserForm.phoneNumber.$invalid && !createUserForm.phoneNumber.$pristine}\">\n" +
            "\t\t\t\t<label class=\"control-label col-xs-2\">Contact no.</label>\n" +
            "\t\t\t\t<div class=\"col-xs-10\">\n" +
            "\t\t\t\t\t<input name=\"phoneNumber\" id=\"phoneNumber\" autocomplete=\"off\" type=\"text\" class=\"form-control input input-sm\" data-ng-model=\"newUser.phoneNumber\" placeholder=\"Contact no.\" required\n" +
            "\t\t\t\t\tdata-ng-pattern=\"/^[0-9]*$/\" data-ng-minlength=\"10\" data-ng-maxlength=\"10\">\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"errorMessage\" data-ng-messages=\"createUserForm.phoneNumber.$error\" role=\"alert\" data-ng-show=\"!createUserForm.phoneNumber.$pristine\">\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"required\">This field cannot be empty</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"pattern\">Input contains non-numeric characters</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"minlength\">Number is too short</div>\n" +
            "\t\t\t\t\t\t<div data-ng-message=\"maxlength\">Number is too long</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<hr>\n" +
            "\t\t\t<div class=\"form-group\">\n" +
            "\t\t\t\t<div class=\"col-xs-offset-2 col-xs-10\">\n" +
            "\t\t\t\t\t<button  type=\"button\" class=\"btn btn-sm btn-default\" data-ui-sref=\"userManagement.userTable({pageNum: 1})\">Cancel</button>\n" +
            "\t\t\t\t\t<button  type=\"button\" data-ng-click=\"createUserSubmit()\" class=\"btn btn-sm btn-primary\">Create New User</button>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</form>\n" +
            "\t</div>\n" +
            "</div>";
}
