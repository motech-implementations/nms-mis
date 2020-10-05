package com.beehyv.nmsreporting.htmlpages;

public class Sitemap {
    public static String pageContent ="<div class=\"container-fluid sitemap\" data-ng-controller=\"SitemapController\">" +
            "   <div data-ui-view></div>\n" +
            "    <div class=\"container\">\n" +
            "    <div class=\"row\">\n" +
            "        <div class=\"col-md-1\"></div>\n" +
            "        <div class=\"col-md-8\">\n" +
            "            <h1>SiteMap</h1>\n" +
            "            <div>\n" +
            "                <div class=\"sitemap-title\">Primary Links</div>\n" +
            "                <div class=\"sitemap-content\">\n" +
            "                    <ul>\n" +
            "                        <li><span><a  data-ng-class=\"{dis1: disableCursor()}\" href=\"https://mohfw.gov.in/\" target=\"_blank\">About us</a></span></li>\n" +
            "                        <li data-ng-if='!checkLogin()' ><span  data-ng-click=\"func_aboutKilkari()\">About Kilkari</span></li>\n" +
            "                        <li data-ng-if='!checkLogin()' ><span  data-ng-click=\"func_aboutMA()\">About Mobile Academy</span></li>\n" +
            "                        <li data-ng-if='!checkLogin()'><span  data-ng-click=\"func_UM()\">User Manual</span></li>\n" +
            "                        <li data-ng-if='!checkLogin()'><span  data-ng-click=\"func_FAQ()\">FAQ</span></li>\n" +
            "                        <li data-ng-if='!checkLogin()'><span  data-ng-click=\"func_Feedback()\">Feedback Form</span></li>\n" +
            "                        <li><span data-ng-click=\"func_Contactus()\">Contact us</span></li>\n" +
            "                        <!--<li><span data-ng-click=\"func_downloads()\">Downloads</span></li>-->\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div>\n" +
            "                <div class=\"sitemap-title\">Footer</div>\n" +
            "                <div class=\"sitemap-content\">\n" +
            "                    <ul>\n" +
            "                        <li><span><a  data-ng-class=\"{dis1: disableCursor()}\" href=\"http://nhm.gov.in/terms-conditions.html\" target=\"_blank\">Terms & Conditions</a></span></li>\n" +
            "                        <li><span><a  data-ng-class=\"{dis1: disableCursor()}\" href=\"http://nhm.gov.in/privacy-policy.html\" target=\"_blank\">Privacy Policy</a></span></li>\n" +
            "                        <li><span><a  data-ng-class=\"{dis1: disableCursor()}\" href=\"http://nhm.gov.in/copyright-policy.html\" target=\"_blank\">Copyright Policy</a></span></li>\n" +
            "                        <li><span><a  data-ng-class=\"{dis1: disableCursor()}\" href=\"http://nhm.gov.in/hyperlinking-policy.html\" target=\"_blank\">Hyper Linking Policy</a></span></li>\n" +
            "                        <li><span><a  data-ng-class=\"{dis1: disableCursor()}\" href=\"http://nhm.gov.in/disclaimer.html\" target=\"_blank\">Disclaimer</a></span></li>\n" +
            "                        <li data-ng-if='!checkLogin()' ><span data-ng-click=\"func_Contactus()\">Help</span></li>\n" +
            "                    </ul>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"col-md-3\"></div>\n" +
            "    </div>\n" +
            "    </div>" +
            "</div>";
}
