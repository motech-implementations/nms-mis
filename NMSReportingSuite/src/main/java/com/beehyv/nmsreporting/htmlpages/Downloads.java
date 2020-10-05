package com.beehyv.nmsreporting.htmlpages;

public class Downloads {
    public static String pageContent ="<div data-ng-app=\"nmsReports\" class=\"\"  data-ng-controller=\"DownloadsController\">" +
            "    <div>Downloads Page</div>\n" +
            "    <ul>\n" +
            "        <li data-ng-repeat=\" downloaditem in downloadList\">\n" +
            "           {{downloaditem.id}} : {{downloaditem.name}}\n" +
            "        </li>\n" +
            "\n" +
            "    </ul>" +
            "</div>";
}
