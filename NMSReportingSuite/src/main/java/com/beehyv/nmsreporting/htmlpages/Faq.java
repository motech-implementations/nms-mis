package com.beehyv.nmsreporting.htmlpages;

public class Faq {
    public static String pageContent ="    <div class=\"col-md-12 \">\n" +
            "        <table>\n" +
            "            <tr>\n" +
            "            <th><p style = \"font-size: 30px; margin-top: 10px; padding-left: 15px;font-weight: 300\">FAQ's</p></th>\n" +
            "            <th><hr class=\"infoHR\"/></th>\n" +
            "            </tr>\n" +
            "        </table>\n" +
            "    </div>\n" +
            "    <div class=\"faqMargins\"><h4>Frequently Asked Questions</h4></div>\n" +
            "    <br/><br/>\n" +
            "\n" +
            "    <div class=\"col-md-3 col-lg-3 col-sm-3 col-xs-3 user-manual\">\n" +
            "\n" +
            "        <div data-ng-click=\"active1='faq-general'; func('faq-general')\" data-ng-class=\"{'sideNavbar': active1 =='faq-general'}\">General Information\n" +
            "        </div>\n" +
            "        <div data-ng-click=\"active1='faq-login'; func('faq-login')\" data-ng-class=\"{'sideNavbar': active1 =='faq-login'}\">Login and Registration\n" +
            "        </div>\n" +
            "        <div data-ng-click=\"active1='faq-reports'; func('faq-reports')\" data-ng-class=\"{'sideNavbar': active1 =='faq-reports'}\">Reports Information\n" +
            "        </div>\n" +
            "        <div data-ng-click=\"active1='faq-line-listing'; func('faq-line-listing')\" data-ng-class=\"{'sideNavbar': active1 =='faq-line-listing'}\">Line Listing Reports\n" +
            "        </div>\n" +
            "        <div data-ng-click=\"active1='faq-aggregate'; func('faq-aggregate')\" data-ng-class=\"{'sideNavbar': active1 =='faq-aggregate'}\">Aggregate Reports\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"col-md-9 col-sm-9 col-lg-9 col-xs-9 faq-right\">\n" +
            "        <div data-ng-app=\"myApp\">\n" +
            "\n" +
            "            <div data-ui-view></div>\n" +
            "\n" +
            "        </div>\n" +
            "    </div>";
}
