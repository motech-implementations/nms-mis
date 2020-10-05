package com.beehyv.nmsreporting.htmlpages;

public class UserManual {
    public static String pageContent ="<div class=\"container-fluid usermanual-padding0\" data-ng-controller=\"UserManualController\">" +
            "  <div class=\"userManual-block container-fluid\">\n" +
            "    <p>This guide will explain the feature-functionality across all sections of the MIS portal and how to use the functionality. Select your user role from the drop down below to start the walk through of the MIS portal. </p>\n" +
            "  <!--  <p>I am a:\n" +
            "        <select data-ng-model=\"selectRole\" data-ng-change=\"fnk();\" class=\"userManualSelectpicker\">\n" +
            "            <option data-ng-repeat=\"x in roles\" value=\"{{x.id}}\">{{x.role}}</option>\n" +
            "        </select></p>-->\n" +
            "        <span class = \"dropdown\"> I am a:</span>\n" +
            "\n" +
            "        <div class=\"btn-group\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "            <button id=\"btn-append-to-body\" type=\"button\" class=\"btn btn-sm btn-default\" data-uib-dropdown-toggle >\n" +
            "                {{selectRoleValue}}\n" +
            "                <span data-ng-if=\"selectRoleValue == null\">Select Role</span>\n" +
            "                <span class=\"caret\"></span>\n" +
            "            </button>\n" +
            "            <ul class=\"dropdown-menu report-dropdown-menu\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "                <li role=\"menuitem\" data-ng-repeat=\"x in roles\">\n" +
            "                    <a class=\"report_category\" data-ng-click='fnk(x);'>\n" +
            "                        <span>{{x.role}}</span>\n" +
            "                    </a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "\n" +
            "    </div>\n" +
            "    <div class=\"col-md-3 col-lg-3 col-sm-3 col-xs-3 user-manual\">\n" +
            "\n" +
            "            <div data-ng-click=\"active1='wi'; func('wi')\" data-ng-class=\"{'sideNavbar': active1 =='wi'}\">Website Information\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\"></span>\n" +
            "            </div>\n" +
            "            <div data-ng-click=\"active1='kr';func('kr')\" data-ng-class=\"{'sideNavbar': active1 =='kr'}\" data-ng-hide=\"selectRole < 1\">Kilkari Line Listing Reports\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\" ></span>\n" +
            "            </div>\n" +
            "            <div data-ng-click=\"active1='kr-agg';func('kr-agg')\" data-ng-class=\"{'sideNavbar': active1 =='kr-agg'}\" data-ng-hide=\"selectRole < 1\">Kilkari Aggregate Reports\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\" ></span>\n" +
            "            </div>\n" +
            "            <div data-ng-click=\"active1='ma';func('ma')\" data-ng-class=\"{'sideNavbar': active1 =='ma'}\" data-ng-hide=\"selectRole < 1\">Mobile Academy Line Listing Reports\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\"></span>\n" +
            "            </div>\n" +
            "            <div data-ng-click=\"active1='ma-agg';func('ma-agg')\" data-ng-class=\"{'sideNavbar': active1 =='ma-agg'}\" data-ng-hide=\"selectRole < 1\">Mobile Academy Aggregate Reports\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\"></span>\n" +
            "            </div>\n" +
            "            <div data-ng-click=\"active1='um';func('um')\" data-ng-class=\"{'sideNavbar': active1 =='um'}\" data-ng-hide=\"selectRole < 5\">User Management\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\"></span>\n" +
            "            </div>\n" +
            "            <div data-ng-click=\"active1='pr';func('pr')\" data-ng-class=\"{'sideNavbar': active1 =='pr'}\">My Profile\n" +
            "                <span class=\"glyphicon glyphicon-plus plusSign\" ></span>\n" +
            "            </div>\n" +
            "\n" +
            "    </div>\n" +
            "    <div class=\"col-md-9 col-sm-9 col-lg-9 col-xs-9\" >\n" +
            "        <div data-ng-app=\"myApp\" class=\"userManual-right\">\n" +
            "            <div data-ui-view ></div>\n" +
            "    </div>\n" +
            "    </div>" +
            "</div>";
}
