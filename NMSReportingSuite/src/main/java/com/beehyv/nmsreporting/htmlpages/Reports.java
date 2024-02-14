package com.beehyv.nmsreporting.htmlpages;

public class Reports {
    public static String pageContent ="<div  class=\"container-fluid\" data-ng-controller=\"ReportsController\">\n" +
            "\n" +
            "\t\t<div class=\"block reports-header\">\n" +
            "\t\t\t<form name=\"reportsForm\" class=\"form container-fluid block\"  novalidate=\"novalidate\">\n" +
            "\t\t\t\t<div class=\"row\">\n" +
            "\t\t\t\t\t<div class=\"col-xs-7 left\">\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\">\n" +
            "\t\t\t\t\t\t\t<label> Reports Category</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: reportsLoading, disabled:disableReportCategory()&&!reportsLoading }\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-3\" type=\"button\" class=\"btn btn-sm btn-default\" data-uib-dropdown-toggle data-ng-disabled=\"disableReportCategory()\" >\n" +
            "\t\t\t\t\t\t\t\t\t{{reportCategory}}\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"reportCategory == null\">Select</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu report-dropdown-menu\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in reports\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<a class=\"report_category\" data-ng-click='selectReportCategory(item)'>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span>{{item.name}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\">\n" +
            "\t\t\t\t\t\t\t<label> Reports</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-uib-dropdown data-ng-class=\"{ disabled: disableReport()}\" data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-4\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled='disableReport()' data-uib-dropdown-toggle>\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{report.simpleName}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"report.name == null\">Select</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in reportNames\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<a data-ng-if=\"item.showItem\" class=\"select_report\" data-ng-click='selectReport(item)'>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<img alt=\"Report Icon\" class='block' src=\"{{item.icon}}\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span>{{item.simpleName}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\" data-ng-hide = \"isAggregateReport()\">\n" +
            "\t\t\t\t\t\t\t<label>{{datePickerContent}}</label><br>\n" +
            "\t\t\t\t\t\t\t<p class=\"input-group\" data-ng-class=\"{disabled :disableDate() }\" data-ng-disabled=\"disableDate()\">\n" +
            "\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control input input-sm\" data-uib-datepicker-popup=\"{{format}}\" data-ng-model=\"dt\" is-open=\"popup1.opened\" datepicker-options=\"dateOptions\" datepicker-mode='month' data-ng-required = \"true\" close-text=\"Close\" alt-input-formats=\"altInputFormats\" data-ng-disabled='disableDate()' readonly />\n" +
            "\t\t\t\t\t\t\t\t<span class=\"input-group-btn\">\n" +
            "\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-sm btn-default\" data-ng-click=\"open1()\"  data-ng-disabled='disableDate()' ><i class=\"glyphicon glyphicon-calendar\" data-ng-disabled=\"disableDate()\" ></i></button>\n" +
            "\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t</p>\n" +
            "\t\t\t\t\t\t\t<ul  class=\"sundays-list\"  tabindex=\"-1\" data-ng-blur=\"onBlur()\" focus=\"true\" data-ng-if= \" (reportCategory == 'Mobile Academy Reports' ||  reportCategory == 'Kilkari Reports') &&  (report.name == 'Mother Rejected Records' || report.name =='Child Rejected Records') && dt != null \" data-ng-show = \"popup1.opened\">\n" +
            "\t\t\t\t\t\t\t\t<li class=\"sunday-list-header\"><p>Sun</p></li>\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t<li data-ng-repeat=\"date in sundays\"   data-ng-click='closeSundaysTable(date)' class=\"sunday-list-items\"><p>{{date}}</p></li>\n" +
            "\n" +
            "\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\" data-ng-if = \"isAggregateReport()\">\n" +
            "\t\t\t\t\t\t\t<label> State</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: statesLoading, disabled: disableState()&&!statesLoading}\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-5\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableState()\" data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{state.stateName}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"state == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu type-aggregate-state\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\"><a class=\"select_state\" data-ng-click=\"clearState()\">ALL</a>\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in states | orderBy : 'stateName' | filter:serviceStarted\"><a data-ng-click='selectState(item)'>{{item.stateName}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"col-xs-5 right\" data-ng-if=\"!isCircleReport()\">\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\" data-ng-if = \"!isAggregateReport()\">\n" +
            "\t\t\t\t\t\t\t<label> State</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: statesLoading, disabled: disableState()&&!statesLoading }\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-6\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableState()\" data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{cropState(state.stateName)}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu non-aggregate\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in states | orderBy : 'stateName' | filter:serviceStarted\"><a data-ng-click='selectState(item)'>{{cropState(item.stateName)}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-6\" data-ng-if = \"isAggregateReport()\" >\n" +
            "\t\t\t\t\t\t\t<!-- <div> -->\n" +
            "\t\t\t\t\t\t\t<label> District</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: districtsLoading, autoSelected, disabled: disableDistrict()&&!(districtsLoading || autoSelected)}\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-7\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableDistrict()\" data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{cropAggregate(district.districtName)}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"district == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu type-aggregate-district\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\"><a class=\"select_district\" data-ng-click=\"clearDistrict()\">ALL</a>\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in districts | orderBy : 'districtName'\"><a data-ng-click='selectDistrict(item)'>{{cropAggregate(item.districtName)}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\" data-ng-if = \"!isAggregateReport()\">\n" +
            "\t\t\t\t\t\t\t<!-- <div> -->\n" +
            "\t\t\t\t\t\t\t<label> District</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: districtsLoading, autoSelected, disabled: disableDistrict()&&!(districtsLoading || autoSelected)}\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-8\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableDistrict()\" data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{crop(district.districtName)}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"district == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu non-aggregate\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\"><a class=\"select_district\" data-ng-click=\"clearDistrict()\">ALL</a>\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in districts | orderBy : 'districtName'\"><a data-ng-click='selectDistrict(item)'>{{crop(item.districtName)}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-6\" data-ng-if = \"isAggregateReport()\">\n" +
            "\t\t\t\t\t\t\t<label> Block</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: blocksLoading, disabled: disableBlock()&&!blocksLoading}\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-9\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableBlock()\" data-uib-dropdown-toggle>\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{cropAggregate(block.blockName)}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"block == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu small type-aggregate-block\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\"><a class=\"select_block\"  data-ng-click=\"clearBlock()\">ALL</a>\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in blocks | orderBy : 'blockName'\"><a data-ng-click='selectBlock(item)'>{{cropAggregate(item.blockName)}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-4\" data-ng-if = \"!isAggregateReport()\">\n" +
            "\t\t\t\t\t\t\t<label> Block</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: blocksLoading, disabled: disableBlock()&&!blocksLoading}\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body-10\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableBlock()\" data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{crop(block.blockName)}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"block == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu small non-aggregate\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\"><a class=\"select_block\" data-ng-click=\"clearBlock()\">ALL</a>\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in blocks | orderBy : 'blockName'\"><a data-ng-click='selectBlock(item)'>{{crop(item.blockName)}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"col-xs-4 right\" data-ng-if=\"isCircleReport()\">\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-5\">\n" +
            "\t\t\t\t\t\t\t<label> Circle</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-ng-class=\"{loading: circlesLoading, disabled : disableCircle()&&!circlesLoading}\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body\" type=\"button\" class=\"btn btn-sm btn-default\" data-ng-disabled=\"disableCircle()\" data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{cropCircle(circle.circleName)}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<!--<span data-ng-if=\"circle == null\">ALL</span>-->\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu small non-aggregate\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<!--<li role=\"menuitem\"><a data-ng-click=\"clearCircle()\">ALL</a>-->\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"item in circles | orderBy : 'circleName' | filter:serviceStarted\"><a class=\"select_circle\" data-ng-click='selectCircle(item)'>{{item.circleName}}</a></li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t<div class=\"row\" data-ng-hide= \"!isAggregateReport() \">\n" +
            "\t\t\t\t\t<div class=\"col-xs-12 left\">\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-2\" data-ng-hide = \"report.name == 'District-wise Performance of the State for Mobile Academy' || report.reportEnum == 'Kilkari_Cumulative_Summary'\">\n" +
            "\t\t\t\t\t\t\t<label> Period Type</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group \" data-uib-dropdown data-dropdown-append-to-body >\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body9\" type=\"button\"  class=\"btn btn-sm btn-default\"  data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{periodDisplayType}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"reportDisplayType == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu report-type\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"name in periodType\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<a data-ng-click='selectPeriodType(name)'>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span>{{name}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-2\" data-ng-hide = \" periodDisplayType == '' || (report.name == 'District-wise Performance of the State for Mobile Academy' ||  report.reportEnum == 'Kilkari_Cumulative_Summary' || periodDisplayType == 'Current Period' ) \">\n" +
            "\t\t\t\t\t\t\t<label>{{periodTypeContent}}</label><br>\n" +
            "\t\t\t\t\t\t\t<p class=\"input-group\" data-ng-class=\"{disabled :disableDate() }\" data-ng-disabled=\"disableDate()\">\n" +
            "\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control input input-sm\" data-uib-datepicker-popup=\"{{dateFormat}}\" data-ng-model=\"dt1\" is-open=\"popup2.opened\" datepicker-options=\"datePickerOptions\"  data-ng-required = \"true\" close-text=\"Close\" alt-input-formats=\"altInputFormats\" data-ng-disabled='disableDate()' readonly />\n" +
            "\t\t\t\t\t\t\t\t<span class=\"input-group-btn\">\n" +
            "\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-sm btn-default\" data-ng-click=\"open2()\"  data-ng-disabled='disableDate()' ><i class=\"glyphicon glyphicon-calendar\" data-ng-disabled=\"disableDate()\" ></i></button>\n" +
            "\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t</p>\n" +
            "\t\t\t\t\t\t\t<ul  class=\"sundays-list\"  tabindex=\"-1\" data-ng-blur=\"onBlur()\" focus=\"true\" data-ng-if= \" showWeekTable() && dt1 != null\" data-ng-show = \"popup2.opened\">\n" +
            "\t\t\t\t\t\t\t\t<li class=\"sunday-list-header\"><p>Sun</p></li>\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t<li data-ng-repeat=\"date in sundays\"   data-ng-click='closeSundaysTable(date)' class=\"sunday-list-items\"><p>{{date}}</p></li>\n" +
            "\n" +
            "\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-2\" data-ng-hide = \" (report.name != 'District-wise Performance of the State for Mobile Academy' &&  report.reportEnum != 'Kilkari_Cumulative_Summary' && periodDisplayType != 'Custom Range')\">\n" +
            "\t\t\t\t\t\t\t<label>End Date</label><br>\n" +
            "\t\t\t\t\t\t\t<p class=\"input-group\" data-ng-class=\"{disabled :disableDate() }\" data-ng-disabled=\"disableDate()\">\n" +
            "\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control input input-sm\" data-uib-datepicker-popup=\"{{dateFormat}}\" data-ng-model=\"dt2\" is-open=\"popup3.opened\" datepicker-options=\"endDatePickerOptions\"  data-ng-required= \"true\" close-text=\"Close\" alt-input-formats=\"altInputFormats\" data-ng-disabled='disableDate()' readonly />\n" +
            "\t\t\t\t\t\t\t\t<span class=\"input-group-btn\">\n" +
            "                            <button type=\"button\" class=\"btn btn-sm btn-default\" data-ng-click=\"open3()\" data-ng-disabled='disableDate()'><i class=\"glyphicon glyphicon-calendar\" data-ng-disabled='disableDate()'></i></button>\n" +
            "\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t</p>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-2\" data-ng-hide = \"periodDisplayType != 'Quarter'\">\n" +
            "\t\t\t\t\t\t\t<label>Quarter</label><br>\n" +
            "\t\t\t\t\t\t\t<div class=\"btn-group\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "\t\t\t\t\t\t\t\t<button id=\"btn-append-to-body10\" type=\"button\" class=\"btn btn-sm btn-default\"  data-uib-dropdown-toggle >\n" +
            "\t\t\t\t\t\t\t\t<span class=\"block\">\n" +
            "\t\t\t\t\t\t\t\t\t<span>{{quarterDisplayType}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span data-ng-if=\"reportDisplayType == null\">ALL</span>\n" +
            "\t\t\t\t\t\t\t\t\t<span class=\"caret\"></span>\n" +
            "\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t\t\t\t</button>\n" +
            "\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu report-type\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "\t\t\t\t\t\t\t\t\t<li role=\"menuitem\" data-ng-repeat=\"name in quarterType\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<a data-ng-click='selectQuarterType(name)'>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<span>{{name}}</span>\n" +
            "\t\t\t\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t\t\t<!--<div class=\"form-group col-xs-2\">\n" +
            "                            <label> Report Type</label><br>\n" +
            "                            <div class=\"btn-group disabled\" data-uib-dropdown data-dropdown-append-to-body>\n" +
            "                                <button data-ng-disabled = \"true\"  id=\"btn-append-to-body1\" type=\"button\" class=\"btn btn-sm btn-default\"  data-uib-dropdown-toggle >\n" +
            "                                    <span class=\"block\">\n" +
            "                                        <span>{{reportDisplayType}}</span>\n" +
            "                                        <span data-ng-if=\"reportDisplayType == null\">ALL</span>\n" +
            "                                        <span class=\"caret\"></span>\n" +
            "                                    </span>\n" +
            "                                </button>\n" +
            "                                <ul class=\"dropdown-menu report-type\" data-uib-dropdown-menu role=\"menu\" aria-labelledby=\"btn-append-to-body\">\n" +
            "                                    <li data-ng-click =\"isClickAllowed(name)\" role=\"menuitem\" data-ng-repeat=\"name in reportTypes\">\n" +
            "                                        <a data-ng-click='selectReportType(name)'>\n" +
            "                                            <span>{{name}}</span>\n" +
            "                                        </a>\n" +
            "                                    </li>\n" +
            "                                </ul>\n" +
            "                            </div>\n" +
            "                        </div>-->\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"form-group col-xs-2 aggregate-submit\">\n" +
            "\t\t\t\t\t\t\t<button class=\"btn btn-sm btn-primary\" data-ng-click=\"getReport()\">Submit</button>\n" +
            "\t\t\t\t\t\t\t<button class=\"btn btn-sm btn-primary\" data-ng-click=\"reset()\">Reset</button>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\n" +
            "\t\t\t\t<!--<div class=\"row\">-->\n" +
            "\t\t\t\t<!--<div class=\"form-group col-xs-4\">-->\n" +
            "\t\t\t\t<!--<label>final date picker</label><br>-->\n" +
            "\t\t\t\t<!--<p class=\"input-group\" data-ng-disabled=\"disableDate()\">-->\n" +
            "\t\t\t\t<!--<input type=\"text\" class=\"form-control input input-sm\" data-uib-datepicker-popup=\"{{finalFormat}}\" data-ng-model=\"dt3\" is-open=\"popup4.opened\" datepicker-options=\"finalDateOptions\" datepicker-mode=\"'date'\" data-ng-required ==\"true\" close-text=\"Close\" alt-input-formats=\"altInputFormats\" data-ng-disabled='disableDate()' readonly />-->\n" +
            "\t\t\t\t<!--<span class=\"input-group-btn\">-->\n" +
            "\t\t\t\t<!--<button type=\"button\" class=\"btn btn-sm btn-default\" data-ng-click=\"open4()\"  data-ng-disabled='disableDate()' ><i class=\"glyphicon glyphicon-calendar\" data-ng-disabled=\"disableDate()\" ></i></button>-->\n" +
            "\t\t\t\t<!--</span>-->\n" +
            "\t\t\t\t<!--</p>-->\n" +
            "\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\t\t\t\t<!--</div>-->\n" +
            "\n" +
            "\n" +
            "\t\t\t\t<div class=\"row\" data-ng-hide= \"isAggregateReport() \">\n" +
            "\t\t\t\t\t<div class=\"form-group col-xs-4\">\n" +
            "\t\t\t\t\t\t<button class=\"btn btn-sm btn-primary\" data-ng-click=\"getReport()\">Submit</button>\n" +
            "\t\t\t\t\t\t<button class=\"btn btn-sm btn-primary\" data-ng-click=\"reset()\">Reset</button>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</form>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\n" +
            "\t\t<div class=\"block reports-body\" data-ng-if=\"hideGrid && showEmptyData\">\n" +
            "\t\t\t<p>{{content}}</p>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"nav\">\n" +
            "\t\t\t<ul class=\"breadcrumb aggregate-bread-crumb\"  data-ng-hide = \"hideGrid || report.reportEnum == 'Kilkari_Message_Matrix' || report.reportEnum == 'Kilkari_Listening_Matrix' || report.reportEnum == 'Kilkari_Thematic_Content' || report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise'\">\n" +
            "\t\t\t\t<li data-ng-repeat=\"item in reportBreadCrumbData\">\n" +
            "\t\t\t\t\t<a  data-ng-click = \"drillDownData(item.locationId,item.locationType,item.locationName)\" data-ng-class=\"{'active': item.status }\" >{{item.locationName}}</a>\n" +
            "\t\t\t\t</li>\n" +
            "\t\t\t</ul>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"reportheader\" data-ng-hide=\"hideGrid\">\n" +
            "\t\t\t<p class=\"ng-binding\" style=\"display: inline-block;\"> {{reportHeaderName}} Report</p>\n" +
            "\t\t\t<div style=\"display: inline-block;float: right\">\n" +
            "\t\t\t\t<i class=\"glyphicon glyphicon-calendar aggregate-calender\"> <span data-ng-if=\" report.name != 'District-wise Performance of the State for Mobile Academy' && report.reportEnum != 'Kilkari_Cumulative_Summary' \" class=\"calender-date\">{{ headerFromDate | date:'dd/MM/y'}}</span> <span class=\"calender-date\" style=\"font-size: 13px\" data-ng-if=\" report.name == 'District-wise Performance of the State for Mobile Academy' || report.reportEnum == 'Kilkari_Cumulative_Summary' \">Up</span><span class=\"calender-date\">to</span> <span class=\"calender-date\">{{headerToDate | date:'dd/MM/y'}}</span></i>\n" +
            "\t\t\t\t<img alt=\"export to excel\" data-ng-click = \"exportToExcel()\" style=\"float: right;margin-right: 7px; cursor : pointer\" src=\"images/export3.png\">\n" +
            "\t\t\t\t<img alt=\"export to csv\" data-ng-click = \"exportCsv()\" style=\"float: right;margin-right: 7px; cursor : pointer\" src=\"images/export1.png\">\n" +
            "\t\t\t\t<img alt=\"export to pdf\" data-ng-hide=\"isIE9()\"data-ng-click = \"exportPdf()\" style=\"float: right;margin-right: 7px; cursor : pointer\" src=\"images/export2.png\">\n" +
            "\t\t\t\t<img alt=\"export to pdf\" data-ng-hide=\"!isIE9()\" data-ng-click = \"exportToPdf1()\" style=\"float: right;margin-right: 7px; cursor : pointer\" src=\"images/export2.png\">\n" +
            "\t\t\t</div>\n" +
            "\n" +
            "\t\t</div>\n" +
            "\n" +
            "\n" +
            "\t\t<div class=\"reportheader matrixHeader\" data-ng-hide=\"hideGrid || (report.reportEnum != 'Kilkari_Message_Matrix' && report.reportEnum != 'Kilkari_Repeat_Listener_Month_Wise')|| waiting\">\n" +
            "\t\t\t<p class=\"ng-binding\" style=\"display: inline-block;\">{{matrixContent1}}</p>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"row\">\n" +
            "\t\t\t<div class=\"block reports-body loading\" data-ng-if=\"waiting && isAggregateReport()\">\n" +
            "\t\t\t\t<img alt=\"page loading\" src=\"images/pageloader.gif\">\n" +
            "\t\t\t\t<p>Aggregate Report is being generated...</p>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div >\n" +
            "\t\t\t\t<div data-ng-class=\"{bold:footerBold()}\" id=\"grid1\" data-ui-grid=gridOptions class=\"grid\" data-ng-if =\"!hideGrid && !waiting\" data-ui-grid-exporter  data-ui-grid-infinite-scroll></div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\n" +
            "\t\t<div class=\"reportheader matrixHeader\" data-ng-hide=\"hideMessageMatrix || (report.reportEnum != 'Kilkari_Message_Matrix' && report.reportEnum != 'Kilkari_Repeat_Listener_Month_Wise')||hideGrid || waiting\">\n" +
            "\t\t\t<p class=\"ng-binding\" style=\"display: inline-block;\">{{matrixContent2}}</p>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"row\">\n" +
            "\t\t\t<div class=\"block reports-body loading\" data-ng-if=\"waiting && (report.reportEnum == 'Kilkari_Message_Matrix' && report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise')\">\n" +
            "\t\t\t\t<img alt=\"page loading\" src=\"images/pageloader.gif\">\n" +
            "\t\t\t\t<p>Aggregate Report is being generated...</p>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div >\n" +
            "\t\t\t\t<div id=\"grid2\" data-ui-grid=gridOptions_Message_Matrix class=\"grid\" data-ng-if =\"!hideMessageMatrix && !waiting\" data-ui-grid-exporter data-ui-grid-infinite-scroll></div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\n" +
            "\t\t<div style=\"margin-top : 15px;\">\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Thematic_Content')\" ><b>Note:</b>\n" +
            "\t\t\t\tThe total number of unique beneficiaries called is the number of unique beneficiaries called in the entire month.\n" +
            "\t\t\t\tA beneficiary can listen to messages that fall under different weeks in a given month.Thus, The total should not be compared with the aggregate value of the column.\n" +
            "\t\t\t</p>\n" +
            "\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Message_Matrix')\" ><b>Note:</b> A beneficiary can listen to messages that fall under different brackets, in a given month.\n" +
            "\t\t\t\tFor example, they could have listened a messages 6, 7, 8, 9 and listened to more than 75% conent for week 6 and 50-75% on Avg in weeks 7 - 9.\n" +
            "\t\t\t\tSo the beneficiary could be counted once under each bracket. Thus the count of beneficiaries in this report should not be totalled.</p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Message_Matrix')\" ></p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Repeat_Listener_Month_Wise')\" ><b>Note:</b> Beneficiary could listen to more than 5 calls when, either their LMP/Child DOB gets updated or the call record is not updated due to System issues.</p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber')\" ><b>Note1:</b> There may be a very low count observed for some locations under the column 'Total subscriptions At The Start Of The Period'.\n" +
            "\t\t\t\tThe reason is because Kilkari service was launched in that particular location after the start date of the selected period.</p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate')\" ><b>Note1:</b> There may be a very low count observed for some locations under the column 'Total subscriptions At The Start Of The Period'.\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber')\" ><b>Note2 :</b> There may be non-zero count for some locations under the column 'Total subscriptions At The Start Of The Period' even before the launch of Kilkari service in that particular location.\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate')\" ><b>Note2 :</b> There may be non-zero count for some locations under the column 'Total subscriptions At The Start Of The Period' even before the launch of Kilkari service in that particular location.\n" +
            "\t\t\t\tThe reason is because few anonymous subscriptions were created through IVR, which were later converted as beneficiaries after the launch.</p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber')\" ><b>Note3 :</b> rejection reasons are captured only after Sep 2017</p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate')\" ><b>Note3 :</b> rejection reasons are captured only after Sep 2017</p>\n" +
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && (report.reportEnum == 'Kilkari_Subscriber_with_RegistrationDate')\" ><b>Note4 :</b> Registration Dates are captured only after July 2023</p>\n"+
            "\t\t\t<p data-ng-if =\"!hideGrid && !waiting && ((report.reportEnum == 'MA_Subscriber') || (report.reportEnum == 'MA_Performance') || (report.reportEnum == 'District-wise Performance of the State for Mobile Academy'))\" ><b>Note:</b> This report is a dynamic report and number of ASHAs (in each column) might differ for two given time periods.</p>\n" +
            "\n" +
            "\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"block reports-body loading\" data-ng-if=\"waiting && !isAggregateReport()\">\n" +
            "\t\t\t<img alt=\"page loading\" src=\"images/pageloader.gif\">\n" +
            "\t\t\t<p>Report is being generated...</p>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"block reports-body\" data-ng-if=\"!waiting && status == 'success'\">\n" +
            "\t\t\t<p>The following report has been generated: </p>\n" +
            "\t\t\t<p>{{fileName}}</p>\n" +
            "\t\t\t<span data-ng-click=\"clearFile()\">\n" +
            "\t\t\t<a id=\"downloadReportLink\" href=\"{{downloadReportUrl}}\">Click here to download. <img alt=\"download\" src=\"images/export3.png\"></a>\n" +
            "\t\t</span>\n" +
            "\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div class=\"block reports-body\" data-ng-if=\"!waiting && status == 'fail'\">\n" +
            "\t\t\t<p>You do not have access to this report.</p>\n" +
            "\t\t\t<p>{{fileName}}</p>\n" +
            "\n" +
            "\t\t</div>\n" +
            "\t</div>";
}
