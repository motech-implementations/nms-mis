package com.beehyv.nmsreporting.htmlpages;

public class FaqReportsInfo {
    public static String pageContent ="<div class=\"container-fluid\" data-ng-controller=\"faqReportsInfoController\">" +
            "    <div data-ui-view ></div>\n" +
            "\n" +
            "    <h1 style=\"font-size: 24px\"><b>FAQ's Reports Information</b></h1>\n" +
            "    <div>\n" +
            "        <div class=\"panel-group\">\n" +
            "            <div class=\"panel panel-default\">\n" +
            "                <div class=\"panel-heading\">\n" +
            "                    <div class=\"panel-title\">\n" +
            "                        <div data-ng-click=fun(18) data-ng-class=\"{'expandcollapse-heading-collapsed': current!=18, 'expandcollapse-heading-expanded': !current!=18}\"\n" +
            "                             style=\"outline:0px\"><span data-ng-class=\"{'glyphicon glyphicon-chevron-up': !current!=18, 'glyphicon glyphicon-chevron-down': current!=18}\"></span>\n" +
            "                            <div>Why am I not able to select other locations?</div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"current!=18\" class=\"panel-collapse slideDown\">\n" +
            "                    <div class=\"panel-body\">\n" +
            "                        <div>\n" +
            "                            <p>The MIS system has role-based access implemented in it. It will restrict your access to the location to which you are linked.\n" +
            "                                If you think your access level is not accurate, please reach out to your administrator.</p>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"panel panel-default\">\n" +
            "                <div class=\"panel-heading\">\n" +
            "                    <div class=\"panel-title\">\n" +
            "                        <div data-ng-click=fun(19) data-ng-class=\"{'expandcollapse-heading-collapsed': current!=19, 'expandcollapse-heading-expanded': !current!=19}\"\n" +
            "                             style=\"outline:0px\"><span data-ng-class=\"{'glyphicon glyphicon-chevron-up': !current!=19, 'glyphicon glyphicon-chevron-down': current!=19}\"></span>\n" +
            "                            <div>Why am I not able to select a specific period for report generation?</div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"current!=19\" class=\"panel-collapse slideDown\">\n" +
            "                    <div class=\"panel-body\">\n" +
            "                        <div class=\"table-faq\">\n" +
            "                            <table>\n" +
            "                                <tr>\n" +
            "                                    <th>State</th>\n" +
            "                                    <th>Kilkari Start Date</th>\n" +
            "                                    <th>Mobile Academy Start Date</th>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>JHARKHAND</td>\n" +
            "                                    <td>Dec 1, 2016</td>\n" +
            "                                    <td>Nov, 30, 2015</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>DELHI</td>\n" +
            "                                    <td>Apr 11, 2017</td>\n" +
            "                                    <td>Aug 24, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>ODISHA</td>\n" +
            "                                    <td>Dec 1, 2016</td>\n" +
            "                                    <td>July 20, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>UTTAR PRADESH</td>\n" +
            "                                    <td>Dec 1, 2016</td>\n" +
            "                                    <td>Apr 26, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>MADHYA PRADESH</td>\n" +
            "                                    <td>Dec 1, 2016</td>\n" +
            "                                    <td>Dec 1, 2015</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>RAJASTHAN</td>\n" +
            "                                    <td>Dec 1, 2016</td>\n" +
            "                                    <td>Dec 1, 2015</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>UTTARAKHAND</td>\n" +
            "                                    <td>Dec 1, 2016</td>\n" +
            "                                    <td>Dec 1, 2015</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>BIHAR</td>\n" +
            "                                    <td>Mar 4, 2017</td>\n" +
            "                                    <td>Dec 13, 2016</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>HARYANA</td>\n" +
            "                                    <td>Mar 4, 2017</td>\n" +
            "                                    <td>Aug 11, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>HIMACHAL PRADESH</td>\n" +
            "                                    <td>Mar 4, 2017</td>\n" +
            "                                    <td>Apr 6, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>CHHATTISGARH</td>\n" +
            "                                    <td>Apr 6, 2017</td>\n" +
            "                                    <td>Apr 6, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>ASSAM</td>\n" +
            "                                    <td>July 13, 2017</td>\n" +
            "                                    <td>Oct 3, 2017</td>\n" +
            "                                </tr>\n" +
            "                                <tr>\n" +
            "                                    <td>WEST BENGAL</td>\n" +
            "                                    <td>Feb 27, 2018</td>\n" +
            "                                    <td>Jan 25, 2018</td>\n" +
            "                                </tr>\n" +
            "                            </table>\n" +
            "                            <br/>\n" +
            "                            <p>For any further queries, please reach out to us providing relevant details, through the Contact Us form.\n" +
            "                            </p>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"panel panel-default\">\n" +
            "                <div class=\"panel-heading\">\n" +
            "                    <div class=\"panel-title\">\n" +
            "                        <div data-ng-click=fun(20) data-ng-class=\"{'expandcollapse-heading-collapsed': current!=20, 'expandcollapse-heading-expanded': !current!=20}\"\n" +
            "                             style=\"outline:0px\"><span data-ng-class=\"{'glyphicon glyphicon-chevron-up': !current!=20, 'glyphicon glyphicon-chevron-down': current!=20}\"></span>\n" +
            "                            <div>I am not able to access a report!</div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"current!=20\" class=\"panel-collapse slideDown\">\n" +
            "                    <div class=\"panel-body\">\n" +
            "                        <div>\n" +
            "                            <p>Please follow the steps in the User Manual for your role, to access the reports.\n" +
            "                                If you are still facing issues, please reach out to us through the Contact Us form. </p>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>" +
            "</div>";
}
