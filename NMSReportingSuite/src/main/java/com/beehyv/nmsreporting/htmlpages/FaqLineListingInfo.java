package com.beehyv.nmsreporting.htmlpages;

public class FaqLineListingInfo {
    public static String pageContent ="<div class=\"container-fluid\" data-ng-controller=\"faqLineListingInfoController\">" +
            "    <div data-ui-view></div>\n" +
            "\n" +
            "    <h1 style=\"font-size: 24px\"><b>FAQ's Line Listing Reports</b></h1>\n" +
            "    <div>\n" +
            "        <div class=\"panel-group\">\n" +
            "            <div class=\"panel panel-default\">\n" +
            "                <div class=\"panel-heading\">\n" +
            "                    <div class=\"panel-title\">\n" +
            "                        <div data-ng-click=fun(10)\n" +
            "                             data-ng-class=\"{'expandcollapse-heading-collapsed': current!=10, 'expandcollapse-heading-expanded': !current!=10}\"\n" +
            "                             style=\"outline:0px\"><span\n" +
            "                                data-ng-class=\"{'glyphicon glyphicon-chevron-up': !current!=10, 'glyphicon glyphicon-chevron-down': current!=10}\"></span>\n" +
            "                            <div>What are line-listing reports?</div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"current!=10\" class=\"panel-collapse slideDown\">\n" +
            "                    <div class=\"panel-body\">\n" +
            "                        <div>\n" +
            "                            <p>\n" +
            "                                Line-listing reports are set of reports which report those beneficiaries and Ashas on\n" +
            "                                whom\n" +
            "                                specific action needs to be taken. For example, in case a pregnant mother is not\n" +
            "                                listening\n" +
            "                                to Kilkari messages continuously, she needs to be educated on the importance of the\n" +
            "                                service and\n" +
            "                                encouraged to listen the full content.\n" +
            "\n" +
            "                            </p>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"panel panel-default\">\n" +
            "                <div class=\"panel-heading\">\n" +
            "                    <div class=\"panel-title\">\n" +
            "                        <div data-ng-click=fun(11)\n" +
            "                             data-ng-class=\"{'expandcollapse-heading-collapsed': current!=11, 'expandcollapse-heading-expanded': !current!=11}\"\n" +
            "                             style=\"outline:0px\"><span\n" +
            "                                data-ng-class=\"{'glyphicon glyphicon-chevron-up': !current!=11, 'glyphicon glyphicon-chevron-down': current!=11}\"></span>\n" +
            "                            <div>What is the follow-up activity that needs to be performed on the line-listing\n" +
            "                                reports?\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"current!=11\" class=\"panel-collapse slideDown\">\n" +
            "                    <div class=\"panel-body\">\n" +
            "                        <div>\n" +
            "                            <p>Each line-listing report is emailed to your email ID once it is generated along with the\n" +
            "                                instructions that need to be followed for the report. Please check your email on the\n" +
            "                                next steps that need to be followed.\n" +
            "                                For any further clarifications, share your queries through the contact us form.</p>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>" +
            "</div>";
}
