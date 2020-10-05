package com.beehyv.nmsreporting.htmlpages;

public class UserTable {
    public static String pageContent ="<div data-ng-controller=\"UserTableController\"  class='userTable'>\n" +
            "\t<div class=\"block header\">\n" +
            "\t\t<div class='block inputs row'>\n" +
            "\t\t\t<h1 class='col-sm-6'>User Management</h1>\n" +
            "\t\t\t<input type=\"text\" class=\"form-control input input-sm\" data-ng-model=\"filterText\" name=\"tableSearch\" placeholder=\"Search\">\n" +
            "\t\t\t<input type=\"button\" class=\"btn btn-sm btn-primary\" name=\"bulkUpload\" data-ui-sref=\"userManagement.bulkUpload\" value=\"Upload Bulk User\">\n" +
            "\t\t\t<input type=\"button\" class=\"btn btn-sm btn-primary\" name=\"newUser\" data-ng-click=\"createUser()\" value=\"Create new User\">\n" +
            "\t\t</div>\n" +
            "\t</div>\n" +
            "\n" +
            "\t<div>\n" +
            "\t\t<button class=\"btn btn-sm btn-primary resetButton\" data-ng-click=\"resetFilters()\">Reset Filters</button>\n" +
            "\t\t<table class=\"table table-bordered\">\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<th class=\"sortable-columns\" data-ng-click=\"sort_by('id')\"><a>Id</a></th>\n" +
            "\t\t\t\t<th class=\"sortable-columns\" data-ng-click=\"sort_by('name')\"><a>Name</a></th>\n" +
            "\t\t\t\t<th class=\"sortable-columns\" data-ng-click=\"sort_by('username')\"><a>Username</a></th>\n" +
            "\t\t\t\t<th class=\"sortable-columns\" data-ng-click=\"sort_by('phoneNumber')\"><a>Phone no.</a></th>\n" +
            "\t\t\t\t<th class=\"sortable-columns\" data-ng-click=\"sort_by('email')\"><a>Email id</a></th>\n" +
            "\t\t\t\t<th class=\"th-dropdown\">\n" +
            "\t\t\t\t\t<div data-uib-dropdown>\n" +
            "\t\t\t\t\t\t<div data-uib-dropdown-toggle id=\"accessTypeDropdown\">\n" +
            "\t\t\t\t\t\t\t<a href>\n" +
            "\t\t\t\t\t\t\t\tAccess Type\n" +
            "\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t<a href data-ng-if=\"exists(accType)\">\n" +
            "\t\t\t\t\t\t\t\t({{crop(accType)}})\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<ul class=\"dropdown-menu\" data-uib-dropdown-menu aria-labelledby=\"accessTypeDropdown\">\n" +
            "\t\t\t\t\t\t\t<li><a data-ng-click=\"setUniqueAccessTypes('')\">ALL</a></li>\n" +
            "\t\t\t\t\t\t\t<li data-ng-repeat=\"aType in uniqueAT = (getUniqueAccessTypes())\">\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueAccessTypes(aType)\" >{{aType}}</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</th>\n" +
            "\t\t\t\t<th class=\"th-dropdown\">\n" +
            "\t\t\t\t\t<div data-uib-dropdown>\n" +
            "\t\t\t\t\t\t<div data-uib-dropdown-toggle id=\"accessLevelDropdown\">\n" +
            "\t\t\t\t\t\t\t<a href>\n" +
            "\t\t\t\t\t\t\t\tAccess Level\n" +
            "\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t<a href data-ng-if=\"exists(accLevel)\">\n" +
            "\t\t\t\t\t\t\t\t({{crop(accLevel)}})\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<ul class=\"dropdown-menu\" data-uib-dropdown-menu aria-labelledby=\"accessLevelDropdown\">\n" +
            "\t\t\t\t\t\t\t<li>\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueAccessLevels('')\">ALL</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t<li data-ng-repeat=\"aLevel in uniqueAL = (getUniqueAccessLevels())\">\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueAccessLevels(aLevel)\" >{{aLevel}}</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</th>\n" +
            "\t\t\t\t<th class=\"th-dropdown\">\n" +
            "\t\t\t\t\t<div data-uib-dropdown>\n" +
            "\t\t\t\t\t\t<div data-uib-dropdown-toggle id=\"stateDropdown\">\n" +
            "\t\t\t\t\t\t\t<a href>\n" +
            "\t\t\t\t\t\t\t\tState\n" +
            "\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t<a href data-ng-if=\"exists(stateName)\">\n" +
            "\t\t\t\t\t\t\t\t({{crop(stateName)}})\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<ul class=\"dropdown-menu\" data-uib-dropdown-menu aria-labelledby=\"stateDropdown\">\n" +
            "\t\t\t\t\t\t\t<li>\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueStates('')\">ALL</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t<li data-ng-repeat=\"sName in sNames = (getUniqueStates()) | orderBy\">\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueStates(sName)\" >{{sName}}</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</th>\n" +
            "\t\t\t\t<th class=\"th-dropdown\">\n" +
            "\t\t\t\t\t<div data-uib-dropdown>\n" +
            "\t\t\t\t\t\t<div data-uib-dropdown-toggle id=\"districtDropdown\">\n" +
            "\t\t\t\t\t\t\t<a href>\n" +
            "\t\t\t\t\t\t\t\tDistrict\n" +
            "\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t<a href data-ng-if=\"exists(districtName)\">({{crop(districtName)}})\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<ul class=\"dropdown-menu\" data-uib-dropdown-menu aria-labelledby=\"districtDropdown\">\n" +
            "\t\t\t\t\t\t\t<li>\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueDistricts('')\">ALL</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t<li data-ng-repeat=\"dName in dNames = (getUniqueDistricts()) | orderBy\">\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueDistricts(dName)\" >{{dName}}</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</th>\n" +
            "\t\t\t\t<th class=\"th-dropdown\">\n" +
            "\t\t\t\t\t<div data-uib-dropdown>\n" +
            "\t\t\t\t\t\t<div data-uib-dropdown-toggle id=\"blockDropdown\">\n" +
            "\t\t\t\t\t\t\t<a href>Block\n" +
            "\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t<a href data-ng-if=\"exists(blockName)\">\n" +
            "\t\t\t\t\t\t\t\t({{crop(blockName)}})\n" +
            "\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<ul class=\"dropdown-menu\" data-uib-dropdown-menu aria-labelledby=\"blockDropdown\">\n" +
            "\t\t\t\t\t\t\t<li><a data-ng-click=\"setUniqueBlocks('')\">ALL</a></li>\n" +
            "\t\t\t\t\t\t\t<li data-ng-repeat=\"bName in bNames = (getUniqueBlocks()) | orderBy\">\n" +
            "\t\t\t\t\t\t\t\t<a data-ng-click=\"setUniqueBlocks(bName)\" >{{bName}}</a>\n" +
            "\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</th>\n" +
            "\t\t\t\t<th><a>Edit</a></th>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr data-ng-repeat=\"item in filterData = (getAllUsers() | filter : search ) | orderBy : sorter : reverse  | limitTo : numPerPage : numPerPage * (currentPageNo - 1)\">\n" +
            "\t\t\t\t<td>{{item.id - 1}}</td>\n" +
            "\t\t\t\t<td>{{item.name}}</td>\n" +
            "\t\t\t\t<td>{{item.username}}</td>\n" +
            "\t\t\t\t<td>{{item.phoneNumber}}</td>\n" +
            "\t\t\t\t<td>{{item.email}}</td>\n" +
            "\t\t\t\t<td>{{item.accessType}}</td>\n" +
            "\t\t\t\t<td>{{item.accessLevel}}</td>\n" +
            "\t\t\t\t<td>{{item.state}}</td>\n" +
            "\t\t\t\t<td>{{item.district}}</td>\n" +
            "\t\t\t\t<td>{{item.block}}</td>\n" +
            "\t\t\t\t<td><span data-ng-if=\"item.createdBy\"><a class=\"pointer\" data-ng-click=\"editUser(item.id)\">Edit</a></span></td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t</table>\n" +
            "\n" +
            "\t\t<div data-ng-if=\"waiting\" class=\"loading\">\n" +
            "\t\t\t<img src=\"images/pageloader.gif\" alt=\"loading...\">\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"block pagination\">\n" +
            "\t\t\t<pagination  total-items=\"filterData.length\" items-per-page=\"numPerPage\"\n" +
            "\t\t\t\t\t\t current-page=\"currentPageNo\" class=\"pagination-small\">\n" +
            "\t\t\t</pagination>\n" +
            "\t\t</div>\n" +
            "\t</div>" +
            "</div>";
}
