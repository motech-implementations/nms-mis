package com.beehyv.nmsreporting.htmlpages;

public class BulkUserHtml {
   public static String pageContent = "<div data-ng-controller=\"BulkUserController\"  class=\"bulkUpload\">\n" +
           "<div class=\"block\">\n" +
           "\t\t<div class=\"col-xs-6\" style=\"padding-right: 40px;border-right: 1px solid black;\">\n" +
           "\n" +
           "\t\t\t<div>\n" +
           "\t\t\t\t<ul data-ng-if=\"currentUser.accessLevel != null && currentUser.accessLevel == 'NATIONAL'\" class=\"ng-scope\" style=\"padding-left: 0px;\">\n" +
           "\t\t\t\t\t<h4><b><i><u>General Guideline:</u></i></b></p></h4>\n" +
           "\t\t\t\t\t<p>You, as a <b>National Admin</b>, have access to create NationalUser, StateAdmin, StateUser, DistrictAdmin, DistrictUser and BlockUser.</p>\n" +
           "\n" +
           "\t\t\t\t\t<p>In the CSV you want to upload, <b>the header row must be provided</b>, or else, the first row will not be processed as a user. Following is the description of the headers: </p>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Full Name (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be provided for the user being created.</li>\n" +
           "\t\t\t\t\t\t<li>Must have only alphabets and spaces without any special characters.</li>\n" +
           "\t\t\t\t\t\t<li>Must be at least 3 characters long.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>State</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Should be empty in case of NationalUser</li>\n" +
           "\t\t\t\t\t\t<li>Must be a valid state in case of StateAdmin, StateUser, DistrictAdmin, DistrictUser and BlockUser.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>District</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Should be empty in case of a NationalUser, StateAdmin and StateUser.</li>\n" +
           "\t\t\t\t\t\t<li>Must be a valid district in the provided state in case of DistrictAdmin, DistrictUser and BlockUser.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Block</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Should be empty in case of NationalUser, StateAdmin, StateUser, DistrictAdmin and DistrictUser.</li>\n" +
           "\t\t\t\t\t\t<li>Must be a valid block in the provided district in case of BlockUser.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Phone number (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a valid 10-digit mobile number.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Email ID (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a valid email ID.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Username (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a unique username.</li>\n" +
           "\t\t\t\t\t\t<li>Must have only alphabets and numbers without any special characters or spaces.</li>\n" +
           "\t\t\t\t\t\t<li>Must be at least 5 characters long.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Access Level (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be one of ‘NATIONAL’, ‘STATE’, ‘DISTRICT’ or ‘BLOCK’.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Access Type (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be ‘USER’ if access level is ‘NATIONAL’ or ‘BLOCK’.</li>\n" +
           "\t\t\t\t\t\t<li>Must be one of ‘USER’ or ‘ADMIN’ if access level is ‘STATE’ or ‘DISTRICT’.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t</ul>\n" +
           "\n" +
           "\t\t\t\t<ul data-ng-if=\"currentUser.accessLevel != null && currentUser.accessLevel == 'STATE'\">\n" +
           "\t\t\t\t\t<h4><b><i><u>General Guideline:</u></i></b></p></h4>\n" +
           "\t\t\t\t\t<p>You, as a <b>State Admin</b>, have access to create StateUser, DistrictAdmin, DistrictUser and BlockUser.</p>\n" +
           "\n" +
           "\t\t\t\t\t<p>In the CSV you want to upload, <b>the header row must be provided</b>, or else, the first row will not be processed as a user. Following is the description of the headers: </p>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Full Name (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be provided for the user being created.</li>\n" +
           "\t\t\t\t\t\t<li>Must have only alphabets and spaces without any special characters.</li>\n" +
           "\t\t\t\t\t\t<li>Must be at least 3 characters long.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>State</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be the state in which you belong.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>District</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Should be empty in case of <b>StateUser.</b></li>\n" +
           "\t\t\t\t\t\t<li>Must be a valid district belonging to your state in case of <b> DistrictAdmin, DistrictUser </b> and <b> BlockUser.</b></li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Block</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Should be empty in case of <b>StateUser, DistrictAdmin </b> and <b>DistrictUser.</b></li>\n" +
           "\t\t\t\t\t\t<li>Must be a valid block in the provided district in case of <b>BlockUser.</b></li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Phone number</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a valid 10-digit mobile number.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Email ID</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a valid email ID.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Username</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a unique username.</li>\n" +
           "\t\t\t\t\t\t<li>Must have only alphabets and numbers without any special characters or spaces.</li>\n" +
           "\t\t\t\t\t\t<li>Must be at least 5 characters long.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Access Level (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be one of ‘STATE’, ‘DISTRICT’ or ‘BLOCK’.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Access Type (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be ‘USER’ if access level is ‘STATE’ or ‘BLOCK’.</li>\n" +
           "\t\t\t\t\t\t<li>Must be one of  ‘USER’ or ‘ADMIN’ if access level is ‘DISTRICT’.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t</ul>\n" +
           "\n" +
           "\t\t\t\t<ul data-ng-if=\"currentUser.accessLevel != null && currentUser.accessLevel == 'DISTRICT'\">\n" +
           "\t\t\t\t\t<h4><b><i><u>General Guideline:</u></i></b></p></h4>\n" +
           "\t\t\t\t\t<p>You, as a <b>District Admin</b>, have access to create DistrictUser and BlockUser.</p>\n" +
           "\n" +
           "\t\t\t\t\t<p>In the CSV you want to upload, <b>the header row must be provided</b>, or else, the first row will not be processed as a user. Following is the description of the headers: </p>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Full Name (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be provided for the user being created.</li>\n" +
           "\t\t\t\t\t\t<li>Must have only alphabets and spaces without any special characters.</li>\n" +
           "\t\t\t\t\t\t<li>Must be at least 3 characters long.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>State</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be the state in which you district belongs.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>District</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be the district in which you belong.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Block</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Should be empty in case of a <b>DistrictUser.</b></li>\n" +
           "\t\t\t\t\t\t<li>Must be a valid block belonging to your district in case of a <b>BlockUser</b></li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Phone number</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a valid 10-digit mobile number.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Email ID</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a valid email ID.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Username</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be a unique username.</li>\n" +
           "\t\t\t\t\t\t<li>Must have only alphabets and numbers without any special characters or spaces.</li>\n" +
           "\t\t\t\t\t\t<li>Must be at least 5 characters long.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Access Level (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be one of ‘DISTRICT’ or ‘BLOCK’.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t\t<h4><b>Access Type (Required)</b></p></h4>\n" +
           "\t\t\t\t\t<div class=\"margin-left-40px\">\n" +
           "\t\t\t\t\t\t<li>Must be ‘USER’.</li>\n" +
           "\t\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t</ul>\n" +
           "\n" +
           "\t\t\t</div>\n" +
           "\n" +
           "\n" +
           "\t\t</div>\n" +
           "\n" +
           "\t\t<div class=\"col-xs-6\" style=\"padding-left: 0px;\">\n" +
           "<!--\n" +
           "\t\t<div class=\"form-group\" style=\"display: inline-block; width: 300px;\">\n" +
           "\t\t\t<input type=\"file\" class=\"form-control\" name=\"bulkCsv\" value=\"Select .csv file\" data-ng-files=\"getTheFiles($files)\" />\n" +
           "\t\t</div>\n" +
           "\t\t<button type=\"submit\" class=\"btn btn-primary\" name=\"uploadCsv\">Upload File</button>\n" +
           "\t\t<button class=\"btn btn-primary\" name=\"uploadCsv\" data-ng-click=\"uploadFiles()\">Upload File</button>\n" +
           "\n" +
           "\t\t<br>\n" +
           "\t\t\n" +
           " -->\n" +
           "\n" +
           "\t\t<div class=\"bulkUpload-header block\">\n" +
           "\n" +
           "\t\t\t<label for=\"file-upload\" class=\"btn btn-sm btn-default custom-file-upload\">\n" +
           "\t\t\t\t<i class=\"fa fa-cloud-upload\"></i>\n" +
           "\t\t\t\t<span data-ng-if=\"!myFile\">Click here to select CSV file</span>\n" +
           "\t\t\t\t<span data-ng-if=\"myFile\">{{myFile.name}}</span>\n" +
           "\t\t\t</label>\n" +
           "\t\t\t<input id=\"file-upload\" class=\"input input-sm\" type=\"file\" name=\"bulkCsv\" file-model=\"myFile\" style=\"display: none;\" />\n" +
           "\t\t\t<br><br>\n" +
           "\t\t\t\t<button class=\"btn btn-sm btn-primary\" data-ng-click=\"uploadFile()\">Upload Users</button>\n" +
           "\t\t\t<br><br>\n" +
           "\n" +
           "\t\t\t<a class=\"btn btn-sm btn-primary\" href=\"{{downloadTemplateUrl}}\">\n" +
           "\t\t\t\t<span class=\"glyphicon glyphicon-disk-save\"></span>\n" +
           "\t\t\t\tDownload Sample Template\n" +
           "\t\t\t</a>\n" +
           "\n" +
           "\t\t</div>\n" +
           "\n" +
           "\t\t\t<hr>\n" +
           "\t\t\t\t<div class=\"bulkUpload-body block\" data-ng-if=\"errorsObj.length > 0 && uploadedFlag\">\n" +
           "\t\t\t\t<!-- <ul>\n" +
           "                    <li data-ng-repeat=\"row in listErrors()\">{{row}}</li>\n" +
           "                </ul> -->\n" +
           "\t\t\t\t<p class=\"grey\">Status of bulk upload users Import(assuming first row to be header)</p>\n" +
           "\t\t\t\t<p class=\"red\">Error: Following rows were not imported successfully:</p>\n" +
           "\t\t\t\t<table class=\"table table-bordered\">\n" +
           "\t\t\t\t\t<tr>\n" +
           "\t\t\t\t\t\t<th>Row no.</th>\n" +
           "\t\t\t\t\t\t<th>Error Message</th>\n" +
           "\t\t\t\t\t</tr>\n" +
           "\t\t\t\t\t<tr data-ng-repeat=\"row in errorsObj\">\n" +
           "\t\t\t\t\t\t<td>{{row.line}}</td>\n" +
           "\t\t\t\t\t\t<td>{{row.name}}</td>\n" +
           "\t\t\t\t\t</tr>\n" +
           "\t\t\t\t</table>\n" +
           "\t\t\t\t</div>\n" +
           "\n" +
           "\t\t\t\t<div class=\"bulkUpload-body block\" data-ng-if=\"errorsObj.length == 0 && uploadedFlag\">\n" +
           "\t\t\t\t\t<p class=\"green\">Records successfully imported</p>\n" +
           "\t\t\t\t</div>\n" +
           "\t\t</div>\n" +
           "\n" +
           "\n" +
           "\n" +
           "\t</div>\n" +
           "</div>";

}
