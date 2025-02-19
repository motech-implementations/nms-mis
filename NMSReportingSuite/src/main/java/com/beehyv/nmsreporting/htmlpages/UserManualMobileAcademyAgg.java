package com.beehyv.nmsreporting.htmlpages;

public class UserManualMobileAcademyAgg {
    public static String pageContent ="<div class=\"container-fluid\" data-ng-controller=\"mobileAcademyAggregateController\" style=\"padding-left: 30px\">" +
            "    <div data-ng-hide=\"flag\">\n" +
            "        <h2 class=\"user-manual-header\">Mobile Academy Aggregate Reports</h2>\n" +
            "        <div style=\"color: #0071bc; font-size: 16px\">\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('csr')\"><u>Mobile Academy Performance Report Report</u></div>\n" +
            "            </p>\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('sr')\"><u>MA Subscriber Report</u></div>\n" +
            "            </p>\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('pr')\"><u>MA Performance Report</u></div>\n" +
            "            </p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div data-ng-hide=\"!flag\" class=\"user-manual-reports\">\n" +
            "        <div><img alt=\"user manual\" src=\"./images/back1.png\" data-ng-click=\"flag=!flag\" class=\"user-manual-back\">\n" +
            "            <div style=\"display: inline-block\" class=\"imageModify\"><b>Mobile Academy Aggregate Report</b><br>\n" +
            "                <div data-ng-hide=\"selectRole != 1 && selectRole !=5\"><!--National User-->\n" +
            "                    <div data-ng-hide=\"param!=='csr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mobile Academy Performance Report Report</b></p><br>\n" +
            "                        <p>This report provides the performance of the Mobile Academy project from the launch date of\n" +
            "                            the service in the state to till date. It gives information about the number of ASHAs\n" +
            "                            registered with the program, the number of ASHAs that have started the course, the number of\n" +
            "                            ASHAs who have not yet started the course, the number of ASHAs who have passed the course\n" +
            "                            and the number of ASHAs who have failed the course. All the above counts are for ASHAs with\n" +
            "                            job status (GF status) ‘ACTIVE’. </p>\n" +
            "                        <p>This report will help to represent the overall performance of course completion by ASHA in\n" +
            "                            selected state.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns.</p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of registered ASHAs</li>\n" +
            "                            <li>No of ASHAs started course</li>\n" +
            "                            <li>No of ASHAs not started course</li>\n" +
            "                            <li>No of ASHAs successfully completed the course</li>\n" +
            "                            <li>No of ASHAs who failed the course</li>\n" +
            "                            <li>% not started course</li>\n" +
            "                            <li>% successfully completed</li>\n" +
            "                            <li>% failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>Columns 6 & 9 take into consideration only the first successful completion and not\n" +
            "                                repeat attempts.\n" +
            "                            </li>\n" +
            "                            <li>The percentages displayed in the report follow the below rules based on data in its\n" +
            "                                respective rows only\n" +
            "                                <ol>\n" +
            "                                    <li>% Not Started Course: (Column 5/Column 3) * 100</li>\n" +
            "                                    <li>% Successfully Completed: (Column 6/Column 4) * 100</li>\n" +
            "                                    <li>% Failed the course: (Column 7/Column 4) * 100</li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>The report displays data based on the As-On date selected while downloading the report</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Cumul_Summary_1.PNG\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cummulative Summary\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Cumul_Summary_2.PNG\"><br><br><br>\n" +
            "                        <p>4) Select an ‘End Date’ for which the cumulative summary report is required. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Cumul_Summary_3.PNG\"><br><br><br>\n" +
            "                        <p>5) Click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Cumul_Summary_4.PNG\"><br><br><br>\n" +
            "                        <p>6) The report will be generated</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Cumul_Summary_5.PNG\"><br><br><br>\n" +
            "                        <p>7) Once the report is generated, it can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Cumul_Summary_6.PNG\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='sr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Subscriber Report</b></p><br>\n" +
            "                        <p>This report provides a view of the data volume on the Mobile Academy service. It shows how\n" +
            "                            many ASHAs who have registered but not completed the course at the start of the period, the\n" +
            "                            number of ASHAs who have joined the course and number of who have completed the course. All\n" +
            "                            the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period start)</li>\n" +
            "                            <li>No. of ASHA records received through web service</li>\n" +
            "                            <li>No. of ASHA records rejected</li>\n" +
            "                            <li>No. of ASHA records added</li>\n" +
            "                            <li>No. of ASHA successfully completed the course</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period end)</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6, 7, 8 should be for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the start of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHA Records that have been received from web\n" +
            "                                    service from MCTS/RCH during the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of the records that have been rejected due to wrong\n" +
            "                                    mobile number and other reasons in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have been added/subscribed in MA course\n" +
            "                                    for the first time in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    the first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 8 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the end of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_1.PNG\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Subscriber Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_2.PNG\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_3.PNG\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_4.PNG\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_5.PNG\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_6.PNG\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_7.PNG\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_8.PNG\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_9.PNG\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_10.PNG\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_11.PNG\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Sub_12.PNG\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='pr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Performance Report</b></p><br>\n" +
            "                        <p>This report provides the information about how many ASHAs have started the course in the\n" +
            "                            selected period, number of ASHAs who have accessed the course during the period, number of\n" +
            "                            ASHAs who had started the course previously but have not accessed the course during the\n" +
            "                            selected period, number of ASHAs who have completed the course successfully in the selected\n" +
            "                            period and those who have completed the course in the selected period but could not secure\n" +
            "                            pass marks. All the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of ASHA started course</li>\n" +
            "                            <li>No of ASHA pursuing course</li>\n" +
            "                            <li>No of ASHA not pursuing course</li>\n" +
            "                            <li>No of ASHA successfully completed the course</li>\n" +
            "                            <li>No of ASHA who failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6 and 7 is for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have started the course for the first time\n" +
            "                                    in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period, had accessed the course at least once with one bookmark during the selected\n" +
            "                                    period but not completed the course till the end of the selected period\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period but had not accessed the course even once during the selected period. The\n" +
            "                                    count does NOT include ASHAs who have any successful completion till the end of the\n" +
            "                                    selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have completed the course during the\n" +
            "                                    selected period but did not secure passing marks even once till the end of selected\n" +
            "                                    period.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_1.PNG\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Performance Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_2.PNG\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_3.PNG\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_4.PNG\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_5.PNG\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_6.PNG\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_7.PNG\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_8.PNG\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_9.PNG\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_10.PNG\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_11.PNG\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/aggregates/MA/MA_Perf_12.PNG\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"selectRole != 2 && selectRole != 6\">\n" +
            "                    <div data-ng-hide=\"param!=='csr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mobile Academy Performance Report Report</b></p><br>\n" +
            "                        <p>This report provides the performance of the Mobile Academy project from the launch date of\n" +
            "                            the service in the state to till date. It gives information about the number of ASHAs\n" +
            "                            registered with the program, the number of ASHAs that have started the course, the number of\n" +
            "                            ASHAs who have not yet started the course, the number of ASHAs who have passed the course\n" +
            "                            and the number of ASHAs who have failed the course. All the above counts are for ASHAs with\n" +
            "                            job status (GF status) ‘ACTIVE’. </p>\n" +
            "                        <p>This report will help to represent the overall performance of course completion by ASHA in\n" +
            "                            selected state.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns.</p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of registered ASHAs</li>\n" +
            "                            <li>No of ASHAs started course</li>\n" +
            "                            <li>No of ASHAs not started course</li>\n" +
            "                            <li>No of ASHAs successfully completed the course</li>\n" +
            "                            <li>No of ASHAs who failed the course</li>\n" +
            "                            <li>% not started course</li>\n" +
            "                            <li>% successfully completed</li>\n" +
            "                            <li>% failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>Columns 6 & 9 take into consideration only the first successful completion and not\n" +
            "                                repeat attempts.\n" +
            "                            </li>\n" +
            "                            <li>The percentages displayed in the report follow the below rules based on data in its\n" +
            "                                respective rows only\n" +
            "                                <ol>\n" +
            "                                    <li>% Not Started Course: (Column 5/Column 3) * 100</li>\n" +
            "                                    <li>% Successfully Completed: (Column 6/Column 4) * 100</li>\n" +
            "                                    <li>% Failed the course: (Column 7/Column 4) * 100</li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>The report displays data based on the As-On date selected while downloading the report</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cummulative Summary\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/csr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select an ‘End Date’ for which the cumulative summary report is required. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/csr/3.png\"><br><br><br>\n" +
            "                        <p>5) Click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/csr/4.png\"><br><br><br>\n" +
            "                        <p>6) The report will be generated</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/csr/5.png\"><br><br><br>\n" +
            "                        <p>7) Once the report is generated, it can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/csr/d.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='sr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Subscriber Report</b></p><br>\n" +
            "                        <p>This report provides a view of the data volume on the Mobile Academy service. It shows how\n" +
            "                            many ASHAs who have registered but not completed the course at the start of the period, the\n" +
            "                            number of ASHAs who have joined the course and number of who have completed the course. All\n" +
            "                            the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period start)</li>\n" +
            "                            <li>No. of ASHA records received through web service</li>\n" +
            "                            <li>No. of ASHA records rejected</li>\n" +
            "                            <li>No. of ASHA records added</li>\n" +
            "                            <li>No. of ASHA successfully completed the course</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period end)</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6, 7, 8 should be for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the start of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHA Records that have been received from web\n" +
            "                                    service from MCTS/RCH during the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of the records that have been rejected due to wrong\n" +
            "                                    mobile number and other reasons in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have been added/subscribed in MA course\n" +
            "                                    for the first time in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    the first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 8 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the end of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Subscriber Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/3.png\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/4.png\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/5.png\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/6.png\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/d.png\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/7.png\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/8.png\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/9.png\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/10.png\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/sr/11.png\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='pr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Performance Report</b></p><br>\n" +
            "                        <p>This report provides the information about how many ASHAs have started the course in the\n" +
            "                            selected period, number of ASHAs who have accessed the course during the period, number of\n" +
            "                            ASHAs who had started the course previously but have not accessed the course during the\n" +
            "                            selected period, number of ASHAs who have completed the course successfully in the selected\n" +
            "                            period and those who have completed the course in the selected period but could not secure\n" +
            "                            pass marks. All the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of ASHA started course</li>\n" +
            "                            <li>No of ASHA pursuing course</li>\n" +
            "                            <li>No of ASHA not pursuing course</li>\n" +
            "                            <li>No of ASHA successfully completed the course</li>\n" +
            "                            <li>No of ASHA who failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6 and 7 is for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have started the course for the first time\n" +
            "                                    in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period, had accessed the course at least once with one bookmark during the selected\n" +
            "                                    period but not completed the course till the end of the selected period\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period but had not accessed the course even once during the selected period. The\n" +
            "                                    count does NOT include ASHAs who have any successful completion till the end of the\n" +
            "                                    selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have completed the course during the\n" +
            "                                    selected period but did not secure passing marks even once till the end of selected\n" +
            "                                    period.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Performance Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/3.png\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/4.png\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/5.png\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/6.png\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/d.png\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/7.png\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/8.png\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/9.png\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/10.png\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/state/pr/11.png\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                </div>\n";
    public static String pageContent2 =
            "                <div data-ng-hide=\"selectRole != 3 && selectRole != 7\"><!--Disrict User-->\n" +
            "                    <div data-ng-hide=\"param!=='csr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mobile Academy Performance Report Report</b></p><br>\n" +
            "                        <p>This report provides the performance of the Mobile Academy project from the launch date of\n" +
            "                            the service in the state to till date. It gives information about the number of ASHAs\n" +
            "                            registered with the program, the number of ASHAs that have started the course, the number of\n" +
            "                            ASHAs who have not yet started the course, the number of ASHAs who have passed the course\n" +
            "                            and the number of ASHAs who have failed the course. All the above counts are for ASHAs with\n" +
            "                            job status (GF status) ‘ACTIVE’. </p>\n" +
            "                        <p>This report will help to represent the overall performance of course completion by ASHA in\n" +
            "                            selected state.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns.</p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of registered ASHAs</li>\n" +
            "                            <li>No of ASHAs started course</li>\n" +
            "                            <li>No of ASHAs not started course</li>\n" +
            "                            <li>No of ASHAs successfully completed the course</li>\n" +
            "                            <li>No of ASHAs who failed the course</li>\n" +
            "                            <li>% not started course</li>\n" +
            "                            <li>% successfully completed</li>\n" +
            "                            <li>% failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>Columns 6 & 9 take into consideration only the first successful completion and not\n" +
            "                                repeat attempts.\n" +
            "                            </li>\n" +
            "                            <li>The percentages displayed in the report follow the below rules based on data in its\n" +
            "                                respective rows only\n" +
            "                                <ol>\n" +
            "                                    <li>% Not Started Course: (Column 5/Column 3) * 100</li>\n" +
            "                                    <li>% Successfully Completed: (Column 6/Column 4) * 100</li>\n" +
            "                                    <li>% Failed the course: (Column 7/Column 4) * 100</li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>The report displays data based on the As-On date selected while downloading the report</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cummulative Summary\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/csr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select an ‘End Date’ for which the cumulative summary report is required. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/csr/3.png\"><br><br><br>\n" +
            "                        <p>5) Click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/csr/4.png\"><br><br><br>\n" +
            "                        <p>6) The report will be generated</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/csr/5.png\"><br><br><br>\n" +
            "                        <p>7) Once the report is generated, it can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/csr/d.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='sr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Subscriber Report</b></p><br>\n" +
            "                        <p>This report provides a view of the data volume on the Mobile Academy service. It shows how\n" +
            "                            many ASHAs who have registered but not completed the course at the start of the period, the\n" +
            "                            number of ASHAs who have joined the course and number of who have completed the course. All\n" +
            "                            the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period start)</li>\n" +
            "                            <li>No. of ASHA records received through web service</li>\n" +
            "                            <li>No. of ASHA records rejected</li>\n" +
            "                            <li>No. of ASHA records added</li>\n" +
            "                            <li>No. of ASHA successfully completed the course</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period end)</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6, 7, 8 should be for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the start of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHA Records that have been received from web\n" +
            "                                    service from MCTS/RCH during the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of the records that have been rejected due to wrong\n" +
            "                                    mobile number and other reasons in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have been added/subscribed in MA course\n" +
            "                                    for the first time in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    the first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 8 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the end of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Subscriber Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/3.png\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/4.png\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/5.png\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/6.png\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/d.png\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/7.png\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/8.png\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/9.png\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/10.png\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/sr/11.png\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='pr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Performance Report</b></p><br>\n" +
            "                        <p>This report provides the information about how many ASHAs have started the course in the\n" +
            "                            selected period, number of ASHAs who have accessed the course during the period, number of\n" +
            "                            ASHAs who had started the course previously but have not accessed the course during the\n" +
            "                            selected period, number of ASHAs who have completed the course successfully in the selected\n" +
            "                            period and those who have completed the course in the selected period but could not secure\n" +
            "                            pass marks. All the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of ASHA started course</li>\n" +
            "                            <li>No of ASHA pursuing course</li>\n" +
            "                            <li>No of ASHA not pursuing course</li>\n" +
            "                            <li>No of ASHA successfully completed the course</li>\n" +
            "                            <li>No of ASHA who failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6 and 7 is for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have started the course for the first time\n" +
            "                                    in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period, had accessed the course at least once with one bookmark during the selected\n" +
            "                                    period but not completed the course till the end of the selected period\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period but had not accessed the course even once during the selected period. The\n" +
            "                                    count does NOT include ASHAs who have any successful completion till the end of the\n" +
            "                                    selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have completed the course during the\n" +
            "                                    selected period but did not secure passing marks even once till the end of selected\n" +
            "                                    period.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Performance Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/3.png\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/4.png\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/5.png\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/6.png\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/d.png\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/7.png\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/8.png\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/9.png\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/10.png\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/district/pr/11.png\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div data-ng-hide=\"selectRole !=4\">\n" +
            "                    <div data-ng-hide=\"param!=='csr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mobile Academy Performance Report Report</b></p><br>\n" +
            "                        <p>This report provides the performance of the Mobile Academy project from the launch date of\n" +
            "                            the service in the state to till date. It gives information about the number of ASHAs\n" +
            "                            registered with the program, the number of ASHAs that have started the course, the number of\n" +
            "                            ASHAs who have not yet started the course, the number of ASHAs who have passed the course\n" +
            "                            and the number of ASHAs who have failed the course. All the above counts are for ASHAs with\n" +
            "                            job status (GF status) ‘ACTIVE’. </p>\n" +
            "                        <p>This report will help to represent the overall performance of course completion by ASHA in\n" +
            "                            selected state.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns.</p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of registered ASHAs</li>\n" +
            "                            <li>No of ASHAs started course</li>\n" +
            "                            <li>No of ASHAs not started course</li>\n" +
            "                            <li>No of ASHAs successfully completed the course</li>\n" +
            "                            <li>No of ASHAs who failed the course</li>\n" +
            "                            <li>% not started course</li>\n" +
            "                            <li>% successfully completed</li>\n" +
            "                            <li>% failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>Columns 6 & 9 take into consideration only the first successful completion and not\n" +
            "                                repeat attempts.\n" +
            "                            </li>\n" +
            "                            <li>The percentages displayed in the report follow the below rules based on data in its\n" +
            "                                respective rows only\n" +
            "                                <ol>\n" +
            "                                    <li>% Not Started Course: (Column 5/Column 3) * 100</li>\n" +
            "                                    <li>% Successfully Completed: (Column 6/Column 4) * 100</li>\n" +
            "                                    <li>% Failed the course: (Column 7/Column 4) * 100</li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>The report displays data based on the As-On date selected while downloading the report</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cummulative Summary\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/csr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select an ‘End Date’ for which the cumulative summary report is required. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/csr/3.png\"><br><br><br>\n" +
            "                        <p>5) Click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/csr/4.png\"><br><br><br>\n" +
            "                        <p>6) The report will be generated</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/csr/5.png\"><br><br><br>\n" +
            "                        <p>7) Once the report is generated, it can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/csr/d.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='sr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Subscriber Report</b></p><br>\n" +
            "                        <p>This report provides a view of the data volume on the Mobile Academy service. It shows how\n" +
            "                            many ASHAs who have registered but not completed the course at the start of the period, the\n" +
            "                            number of ASHAs who have joined the course and number of who have completed the course. All\n" +
            "                            the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period start)</li>\n" +
            "                            <li>No. of ASHA records received through web service</li>\n" +
            "                            <li>No. of ASHA records rejected</li>\n" +
            "                            <li>No. of ASHA records added</li>\n" +
            "                            <li>No. of ASHA successfully completed the course</li>\n" +
            "                            <li>No. of ASHA registered but not completed (period end)</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6, 7, 8 should be for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the start of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHA Records that have been received from web\n" +
            "                                    service from MCTS/RCH during the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of the records that have been rejected due to wrong\n" +
            "                                    mobile number and other reasons in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have been added/subscribed in MA course\n" +
            "                                    for the first time in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    the first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 8 displays the number of ASHAs who have registered in the MA course prior to\n" +
            "                                    the end of the selected period but have not completed the course.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Subscriber Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/3.png\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/4.png\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/5.png\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/6.png\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/d.png\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/7.png\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/8.png\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/9.png\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/10.png\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/sr/11.png\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='pr'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>MA Performance Report</b></p><br>\n" +
            "                        <p>This report provides the information about how many ASHAs have started the course in the\n" +
            "                            selected period, number of ASHAs who have accessed the course during the period, number of\n" +
            "                            ASHAs who had started the course previously but have not accessed the course during the\n" +
            "                            selected period, number of ASHAs who have completed the course successfully in the selected\n" +
            "                            period and those who have completed the course in the selected period but could not secure\n" +
            "                            pass marks. All the above counts are for ASHAs with job status (GF status) ‘ACTIVE’.</p>\n" +
            "                        <p>The report displays the data in the following sequence of columns </p>\n" +
            "                        <ol>\n" +
            "                            <li>Serial Number</li>\n" +
            "                            <li>State</li>\n" +
            "                            <li>No of ASHA started course</li>\n" +
            "                            <li>No of ASHA pursuing course</li>\n" +
            "                            <li>No of ASHA not pursuing course</li>\n" +
            "                            <li>No of ASHA successfully completed the course</li>\n" +
            "                            <li>No of ASHA who failed the course</li>\n" +
            "                        </ol>\n" +
            "                        <p>The rules governing the display components of the report is given below</p>\n" +
            "                        <ol>\n" +
            "                            <li>The data pertaining to column 3, 4, 5, 6 and 7 is for the selected period only.\n" +
            "                            </li>\n" +
            "                            <ol>\n" +
            "                                <li>Column 3 displays the number of ASHAs who have started the course for the first time\n" +
            "                                    in the selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 4 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period, had accessed the course at least once with one bookmark during the selected\n" +
            "                                    period but not completed the course till the end of the selected period\n" +
            "                                </li>\n" +
            "                                <li>Column 5 displays the number of ASHAs who had started the course before the selected\n" +
            "                                    period but had not accessed the course even once during the selected period. The\n" +
            "                                    count does NOT include ASHAs who have any successful completion till the end of the\n" +
            "                                    selected period.\n" +
            "                                </li>\n" +
            "                                <li>Column 6 displays the number of ASHAs who have successfully completed the course for\n" +
            "                                    first time, during the selected period and secured pass marks.\n" +
            "                                </li>\n" +
            "                                <li>Column 7 displays the number of ASHAs who have completed the course during the\n" +
            "                                    selected period but did not secure passing marks even once till the end of selected\n" +
            "                                    period.\n" +
            "                                </li>\n" +
            "                            </ol>\n" +
            "                        </ol>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"http://192.168.200.13:8080/\">http://192.168.200.13:8080/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/1.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Performance Report\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/2.png\"><br><br><br>\n" +
            "                        <p>4) Select ‘Year’ under ‘Period Type’. </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/3.png\"><br><br><br>\n" +
            "                        <p>5) Select the year for which report is needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/4.png\"><br><br><br>\n" +
            "                        <p>6) Click on ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/5.png\"><br><br><br>\n" +
            "                        <p>7) The report will be generated.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/6.png\"><br><br><br>\n" +
            "                        <p>8) Once report is generated, it can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/d.png\"><br><br><br>\n" +
            "                        <p>9) Select ‘Financial Year’ under ‘Period Type’ and choose the start year for which report is\n" +
            "                            needed.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/7.png\"><br><br><br>\n" +
            "                        <p>10) Click on ‘Submit’ to generate the report</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/8.png\"><br><br><br>\n" +
            "                        <p>11) The report will be generated which can be downloaded in PDF, CSV or XLSX formats.</p>\n" +
            "                        <p>12) Select ‘Quarter’ in the ‘Period Type’ and select the year and quarter for which the\n" +
            "                            report is needed. Then click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/9.png\"><br><br><br>\n" +
            "                        <p>13) The generated report can be downloaded in PDF, CSV and XLSX formats.</p>\n" +
            "                        <p>14) Select ‘Month’ under ‘Period type’ and choose a month and click ‘Submit’.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/10.png\"><br><br><br>\n" +
            "                        <p>15) The report will be generated for the chosen month and can be downloaded in PDF, CSV and\n" +
            "                            XLSX formats.</p>\n" +
            "                        <p>16) ‘Custom Range’ under ‘Period Type’ can also be selected. After that, you need to select a\n" +
            "                            start date and an end date for which report is needed, then click on ‘Submit’</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/ma_agg/block/pr/11.png\"><br><br><br>\n" +
            "                        <p>17) The report will be generated for the specified date range, which can be downloaded in\n" +
            "                            PDF, CSV and XLSX formats.</p>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>" +
                    "</div>";
}
