package com.beehyv.nmsreporting.htmlpages;

public class UserManualMobileAcademy {
    public static String pageContent ="<div class=\"container-fluid\" data-ng-controller=\"mobileAcademyController\" style=\"padding-left: 30px\">" +
            "\n" +
            "    <div data-ng-hide=\"flag\">\n" +
            "        <h2 class=\"user-manual-header\">Mobile Academy Line Listing Reports</h2>\n" +
            "        <div style=\"color: #0071bc; font-size: 16px\">\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('ccc')\"><u>Course Completion</u></div>\n" +
            "            </p>\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('cwau')\"><u>Circle wise Anonymous Users</u></div>\n" +
            "            </p>\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('ciu')\"><u>Cumulative Inactive Users</u></div>\n" +
            "            </p>\n" +
            "            <p>\n" +
            "            <div data-ng-click=\"func('arr')\"><u>Asha Rejected Records</u></div>\n" +
            "            </p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div data-ng-hide=\"!flag\" class=\"user-manual-reports\">\n" +
            "        <div><img alt=\"user manual\" src=\"./images/back1.png\" data-ng-click=\"flag=!flag\" class=\"user-manual-back\">\n" +
            "            <div style=\"display: inline-block\" class=\"imageModify\"><b>Mobile Academy Line Listing Report</b><br>\n" +
            "                <div data-ng-hide=\"selectRole != 1 && selectRole !=5\"><!--National User-->\n" +
            "                    <div data-ng-hide=\"param!=='ccc'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Course Completion</b></p><br>\n" +
            "                        <p>This report is generated from the launch of service in the state till the end of selected\n" +
            "                            month </p>\n" +
            "                        <p>This report gives the line-listing of all the ASHAs who have successfully completed the\n" +
            "                            course for the very first time in the selected month, with details such as - when an ASHA\n" +
            "                            started the course and when she completed the course. It has following columns: </p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                First Completion Date – the date when ASHA’s successfully completed the Mobile Academy\n" +
            "                                course for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                SMS Sent Notification – delivery status of the SMS sent to ASHA on successful\n" +
            "                                completion. ‘True’ means the system received the delivery notification and ‘False’ means\n" +
            "                                the system didn’t receive the delivery notification due to network issue.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"\n" +
            "                             alt=\"user Manual Mobile Academy Reports\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Course Completion\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"\n" +
            "                             alt=\"user manual Course Completion\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_Cal.png\"\n" +
            "                             alt=\"user manual Reports Month\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_State.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_Submit-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CCC_Submit.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='cwau'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Circle wise Anonymous Users</b></p><br>\n" +
            "                        <p>This report gives a line-listing of all such users (i.e. mobile numbers) who had attempted\n" +
            "                            the Mobile Academy toll-free number but these calls got rejected by the system as the users\n" +
            "                            were not registered in the system. It is presumed that these anonymous numbers are the\n" +
            "                            numbers that are used by ASHAs but are not registered with MCTS/RCH. The States / UTs can\n" +
            "                            act upon this report and call up these numbers and after verifying that these numbers indeed\n" +
            "                            belong to the working ASHAs should advise ASHA to get their numbers registered in the\n" +
            "                            MCTS/RCH application. In this way, the application can assist States / UTs to register\n" +
            "                            correct mobile numbers of ASHAs and bring all the ASHAs under the ambit of Mobile Academy\n" +
            "                            program. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Circle name – this is the telecom circle name as defined by TRAI (Telecom Regulatory\n" +
            "                                Authority of India), because this user is not registered with the backend system, no\n" +
            "                                profile information is available, therefore the system cannot provide the state\n" +
            "                                name.<br>\n" +
            "                                Please note the telecom circles that cover the following state\n" +
            "                                <ol>\n" +
            "                                    <li>\n" +
            "                                        Assam – covers the state of Assam\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Bihar – covers the states of Bihar & Jharkhand\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Delhi – covers Delhi NCR including Gurugram, Noida, Ghaziabad & Faridabad\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Haryana – covers the state of Haryana\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Himachal Pradesh – covers the state of Himachal Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Kolkata – covers Kolkata city\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Madhya Pradesh – covers the states of Madhya Pradesh & Chhattisgarh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Odisha – covers the state of Odisha\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Rajasthan – covers the state of Rajasthan\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP East – covers the state of Uttarakhand and eastern Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP West – covers Western Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        West Bengal – covers the state of West Bengal (except Kolkata)\n" +
            "                                    </li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile number – the number that made the call to Mobile Academy service\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Last called date – the date when the last call was made\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Circle wise Anonymous Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CWAU_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select respective Circle from \"Circle\" dropdown:</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CWAU_CIRCLE.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CWAU_SUBMIT-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CWA_DOWNLOAD.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CWAU_SUBMIT.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='ciu'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Cumulative Inactive Users</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all such ASHAs who have not yet started the course as\n" +
            "                            on date. The State / UT can act on this report and contact the ASHAs and motivate them to\n" +
            "                            access the Mobile Academy course and complete the course. In this way a higher percentage of\n" +
            "                            ASHAs accessing and completing the Mobile Academy program can be achieved. This report is a\n" +
            "                            very useful actionable report for the State / UT to monitor the ASHAs accessing the Mobile\n" +
            "                            Academy course. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cumulative Inactive Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIU_CAL.png\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIA_STATE.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIU_DISTRICT.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIU_BLOCK.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIU_SUBMIT-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIU_DOWNLOADS.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_CIU_SUBMIT.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='arr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Asha Rejected Records</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all ASHAs whose records are received from MCTS / RCH\n" +
            "                            but got rejected by the acceptance logic run by the Mobile Academy system, because the\n" +
            "                            registered mobile numbers in the records are either incorrect or are duplicate. . The report\n" +
            "                            contains the MCTS or RCH IDs. This report is generated once every week. This line listing\n" +
            "                            report is available for download for the respective district and block users. The State / UT\n" +
            "                            / district / block can act upon this report and contact the ASHAs in the field and ask them\n" +
            "                            to update / correct their mobile numbers in the MCTS/RCH Application. This report is a very\n" +
            "                            useful actionable report for the State / UT / district / block to correct the ASHA contact\n" +
            "                            mobile number. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Reason for rejection – this gives why the ASHA record was rejected, it could be because\n" +
            "                                the mobile number was incorrect, or duplicate in the database – i.e. is already assigned\n" +
            "                                to another ASHA who is active on Mobile Academy.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Asha Rejected Records\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4.a) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_CAL.png\"><br><br><br>\n" +
            "                        <p>4.b)Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\":</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_CalDate.png\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_STATE.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_DISTRICT.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_BLOCK.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_SUBMIT-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_ARR_SUBMIT.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n";
    public static String pageContent2 =
            "                <div data-ng-hide=\"selectRole != 2 && selectRole !=6\">\n" +
            "                    <div data-ng-hide=\"param!=='ccc'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Course Completion</b></p><br>\n" +
            "                        <p>This report is generated from the launch of service in the state till the end of selected\n" +
            "                            month </p>\n" +
            "                        <p>This report gives the line-listing of all the ASHAs who have successfully completed the\n" +
            "                            course for the very first time in the selected month, with details such as - when an ASHA\n" +
            "                            started the course and when she completed the course. It has following columns: </p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                First Completion Date – the date when ASHA’s successfully completed the Mobile Academy\n" +
            "                                course for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                SMS Sent Notification – delivery status of the SMS sent to ASHA on successful\n" +
            "                                completion. ‘True’ means the system received the delivery notification and ‘False’ means\n" +
            "                                the system didn’t receive the delivery notification due to network issue.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_RC.png\"\n" +
            "                             alt=\"user Manual Mobile Academy Reports\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Course Completion\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_Rep.png\"\n" +
            "                             alt=\"user manual Course Completion\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_CCC_Cal.png\"\n" +
            "                             alt=\"user manual Reports Month\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_CCC_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_CCC_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_CCC_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_CCC_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_CCC_Reset.jpg\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='cwau'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Circle wise Anonymous Users</b></p><br>\n" +
            "                        <p>This report gives a line-listing of all such users (i.e. mobile numbers) who had attempted\n" +
            "                            the Mobile Academy toll-free number but these calls got rejected by the system as the users\n" +
            "                            were not registered in the system. It is presumed that these anonymous numbers are the\n" +
            "                            numbers that are used by ASHAs but are not registered with MCTS/RCH. The States / UTs can\n" +
            "                            act upon this report and call up these numbers and after verifying that these numbers indeed\n" +
            "                            belong to the working ASHAs should advise ASHA to get their numbers registered in the\n" +
            "                            MCTS/RCH application. In this way, the application can assist States / UTs to register\n" +
            "                            correct mobile numbers of ASHAs and bring all the ASHAs under the ambit of Mobile Academy\n" +
            "                            program. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Circle name – this is the telecom circle name as defined by TRAI (Telecom Regulatory\n" +
            "                                Authority of India), because this user is not registered with the backend system, no\n" +
            "                                profile information is available, therefore the system cannot provide the state\n" +
            "                                name.<br>\n" +
            "                                Please note the telecom circles that cover the following state\n" +
            "                                <ol>\n" +
            "                                    <li>\n" +
            "                                        Assam – covers the state of Assam\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Bihar – covers the states of Bihar & Jharkhand\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Delhi – covers Delhi NCR including Gurugram, Noida, Ghaziabad & Faridabad\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Haryana – covers the state of Haryana\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Himachal Pradesh – covers the state of Himachal Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Kolkata – covers Kolkata city\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Madhya Pradesh – covers the states of Madhya Pradesh & Chhattisgarh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Odisha – covers the state of Odisha\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Rajasthan – covers the state of Rajasthan\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP East – covers the state of Uttarakhand and eastern Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP West – covers Western Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        West Bengal – covers the state of West Bengal (except Kolkata)\n" +
            "                                    </li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile number – the number that made the call to Mobile Academy service\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Last called date – the date when the last call was made\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Circle wise Anonymous Users\"</p><br>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA2/SA_MA_CWAU_Cal.png\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'Circle' option is already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA2/SA_MA_CWAU_Circle.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA2/SA_MA_CWAU_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA2/SA_MA_CWAU_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA2/SA_MA_CWAU_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='ciu'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Cumulative Inactive Users</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all such ASHAs who have not yet started the course as\n" +
            "                            on date. The State / UT can act on this report and contact the ASHAs and motivate them to\n" +
            "                            access the Mobile Academy course and complete the course. In this way a higher percentage of\n" +
            "                            ASHAs accessing and completing the Mobile Academy program can be achieved. This report is a\n" +
            "                            very useful actionable report for the State / UT to monitor the ASHAs accessing the Mobile\n" +
            "                            Academy course. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cumulative Inactive Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA3/SA_MA_CIU_Month.png\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA3/SA_MA_CIU_District.png\"><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA3/SA_MA_CIU_Blocks.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA3/SA_MA_CIU_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA3/SA_MA_CIU_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA3/SA_MA_CIU_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='arr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Asha Rejected Records</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all ASHAs whose records are received from MCTS / RCH\n" +
            "                            but got rejected by the acceptance logic run by the Mobile Academy system, because the\n" +
            "                            registered mobile numbers in the records are either incorrect or are duplicate. . The report\n" +
            "                            contains the MCTS or RCH IDs. This report is generated once every week. This line listing\n" +
            "                            report is available for download for the respective district and block users. The State / UT\n" +
            "                            / district / block can act upon this report and contact the ASHAs in the field and ask them\n" +
            "                            to update / correct their mobile numbers in the MCTS/RCH Application. This report is a very\n" +
            "                            useful actionable report for the State / UT / district / block to correct the ASHA contact\n" +
            "                            mobile number. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Reason for rejection – this gives why the ASHA record was rejected, it could be because\n" +
            "                                the mobile number was incorrect, or duplicate in the database – i.e. is already assigned\n" +
            "                                to another ASHA who is active on Mobile Academy.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Asha Rejected Records\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA1/SA_MA_Rep.png\"><br><br><br>\n" +
            "                        <p>4.a) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Cal.png\"><br><br><br>\n" +
            "                        <p>4.b)Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\":</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Caldate.png\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Dist.png\"><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA4/SA_MA_ARR_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "\n" +
            "                <div data-ng-hide=\"selectRole != 3 && selectRole !=7\"><!--District User-->\n" +
            "                    <div data-ng-hide=\"param!=='ccc'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Course Completion</b></p><br>\n" +
            "                        <p>This report is generated from the launch of service in the state till the end of selected\n" +
            "                            month </p>\n" +
            "                        <p>This report gives the line-listing of all the ASHAs who have successfully completed the\n" +
            "                            course for the very first time in the selected month, with details such as - when an ASHA\n" +
            "                            started the course and when she completed the course. It has following columns: </p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                First Completion Date – the date when ASHA’s successfully completed the Mobile Academy\n" +
            "                                course for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                SMS Sent Notification – delivery status of the SMS sent to ASHA on successful\n" +
            "                                completion. ‘True’ means the system received the delivery notification and ‘False’ means\n" +
            "                                the system didn’t receive the delivery notification due to network issue.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"\n" +
            "                             alt=\"user Manual Mobile Academy Reports\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Course Completion\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_All.png\"\n" +
            "                             alt=\"user manual Course Completion\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CCC_Cal.png\"\n" +
            "                             alt=\"user manual Reports Month\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CCC_Block.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CCC_Sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CCC_DownLoad.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CCC_Sub.png\"><br>\n" +
            "                    </div>\n" +
            "\n" ;
    public static String pageContent3 =
            "                    <div data-ng-hide=\"param!=='cwau'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Circle wise Anonymous Users</b></p><br>\n" +
            "                        <p>This report gives a line-listing of all such users (i.e. mobile numbers) who had attempted\n" +
            "                            the Mobile Academy toll-free number but these calls got rejected by the system as the users\n" +
            "                            were not registered in the system. It is presumed that these anonymous numbers are the\n" +
            "                            numbers that are used by ASHAs but are not registered with MCTS/RCH. The States / UTs can\n" +
            "                            act upon this report and call up these numbers and after verifying that these numbers indeed\n" +
            "                            belong to the working ASHAs should advise ASHA to get their numbers registered in the\n" +
            "                            MCTS/RCH application. In this way, the application can assist States / UTs to register\n" +
            "                            correct mobile numbers of ASHAs and bring all the ASHAs under the ambit of Mobile Academy\n" +
            "                            program. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Circle name – this is the telecom circle name as defined by TRAI (Telecom Regulatory\n" +
            "                                Authority of India), because this user is not registered with the backend system, no\n" +
            "                                profile information is available, therefore the system cannot provide the state\n" +
            "                                name.<br>\n" +
            "                                Please note the telecom circles that cover the following state\n" +
            "                                <ol>\n" +
            "                                    <li>\n" +
            "                                        Assam – covers the state of Assam\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Bihar – covers the states of Bihar & Jharkhand\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Delhi – covers Delhi NCR including Gurugram, Noida, Ghaziabad & Faridabad\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Haryana – covers the state of Haryana\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Himachal Pradesh – covers the state of Himachal Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Kolkata – covers Kolkata city\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Madhya Pradesh – covers the states of Madhya Pradesh & Chhattisgarh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Odisha – covers the state of Odisha\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Rajasthan – covers the state of Rajasthan\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP East – covers the state of Uttarakhand and eastern Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP West – covers Western Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        West Bengal – covers the state of West Bengal (except Kolkata)\n" +
            "                                    </li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile number – the number that made the call to Mobile Academy service\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Last called date – the date when the last call was made\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Circle wise Anonymous Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CWAU_Cal.png\"><br><br><br>\n" +
            "                        <p>5) For District users, their 'Circle' option is already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/MA2/SA_MA_CWAU_Circle.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CWAU_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CWAU_dowload.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CWAU_sub.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='ciu'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Cumulative Inactive Users</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all such ASHAs who have not yet started the course as\n" +
            "                            on date. The State / UT can act on this report and contact the ASHAs and motivate them to\n" +
            "                            access the Mobile Academy course and complete the course. In this way a higher percentage of\n" +
            "                            ASHAs accessing and completing the Mobile Academy program can be achieved. This report is a\n" +
            "                            very useful actionable report for the State / UT to monitor the ASHAs accessing the Mobile\n" +
            "                            Academy course. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cumulative Inactive Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CIU_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CIU_Block.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CIU_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CIU_DownLoad.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_CIU_sub.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='arr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Asha Rejected Records</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all ASHAs whose records are received from MCTS / RCH\n" +
            "                            but got rejected by the acceptance logic run by the Mobile Academy system, because the\n" +
            "                            registered mobile numbers in the records are either incorrect or are duplicate. . The report\n" +
            "                            contains the MCTS or RCH IDs. This report is generated once every week. This line listing\n" +
            "                            report is available for download for the respective district and block users. The State / UT\n" +
            "                            / district / block can act upon this report and contact the ASHAs in the field and ask them\n" +
            "                            to update / correct their mobile numbers in the MCTS/RCH Application. This report is a very\n" +
            "                            useful actionable report for the State / UT / district / block to correct the ASHA contact\n" +
            "                            mobile number. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Reason for rejection – this gives why the ASHA record was rejected, it could be because\n" +
            "                                the mobile number was incorrect, or duplicate in the database – i.e. is already assigned\n" +
            "                                to another ASHA who is active on Mobile Academy.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Asha Rejected Records\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_All.png\"><br><br><br>\n" +
            "                        <p>4.a) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_ARR_Cal.png\"><br><br><br>\n" +
            "                        <p>4.b)Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\":</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_ARR_CalDate.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from “Block”, under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_ARR_Block.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_ARR_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_ARR_download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_MA_ARR_sub.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n";
    public static String pageContent4 =
            "                <div data-ng-hide=\"selectRole != 4\">\n" +
            "                    <div data-ng-hide=\"param!=='ccc'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Course Completion</b></p><br>\n" +
            "                        <p>This report is generated from the launch of service in the state till the end of selected\n" +
            "                            month </p>\n" +
            "                        <p>This report gives the line-listing of all the ASHAs who have successfully completed the\n" +
            "                            course for the very first time in the selected month, with details such as - when an ASHA\n" +
            "                            started the course and when she completed the course. It has following columns: </p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                First Completion Date – the date when ASHA’s successfully completed the Mobile Academy\n" +
            "                                course for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                SMS Sent Notification – delivery status of the SMS sent to ASHA on successful\n" +
            "                                completion. ‘True’ means the system received the delivery notification and ‘False’ means\n" +
            "                                the system didn’t receive the delivery notification due to network issue.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"\n" +
            "                             alt=\"user Manual Mobile Academy Reports\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Course Completion\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_Report.png\"\n" +
            "                             alt=\"user manual Course Completion\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_CCC_Cal.png\"\n" +
            "                             alt=\"user manual Reports Month\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_CCC_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_CCC_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_CCC_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='cwau'\" style=\"display: inline-block\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Circle wise Anonymous Users</b></p><br>\n" +
            "                        <p>This report gives a line-listing of all such users (i.e. mobile numbers) who had attempted\n" +
            "                            the Mobile Academy toll-free number but these calls got rejected by the system as the users\n" +
            "                            were not registered in the system. It is presumed that these anonymous numbers are the\n" +
            "                            numbers that are used by ASHAs but are not registered with MCTS/RCH. The States / UTs can\n" +
            "                            act upon this report and call up these numbers and after verifying that these numbers indeed\n" +
            "                            belong to the working ASHAs should advise ASHA to get their numbers registered in the\n" +
            "                            MCTS/RCH application. In this way, the application can assist States / UTs to register\n" +
            "                            correct mobile numbers of ASHAs and bring all the ASHAs under the ambit of Mobile Academy\n" +
            "                            program. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Circle name – this is the telecom circle name as defined by TRAI (Telecom Regulatory\n" +
            "                                Authority of India), because this user is not registered with the backend system, no\n" +
            "                                profile information is available, therefore the system cannot provide the state\n" +
            "                                name.<br>\n" +
            "                                Please note the telecom circles that cover the following state\n" +
            "                                <ol>\n" +
            "                                    <li>\n" +
            "                                        Assam – covers the state of Assam\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Bihar – covers the states of Bihar & Jharkhand\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Delhi – covers Delhi NCR including Gurugram, Noida, Ghaziabad & Faridabad\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Haryana – covers the state of Haryana\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Himachal Pradesh – covers the state of Himachal Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Kolkata – covers Kolkata city\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Madhya Pradesh – covers the states of Madhya Pradesh & Chhattisgarh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Odisha – covers the state of Odisha\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        Rajasthan – covers the state of Rajasthan\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP East – covers the state of Uttarakhand and eastern Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        UP West – covers Western Uttar Pradesh\n" +
            "                                    </li>\n" +
            "                                    <li>\n" +
            "                                        West Bengal – covers the state of West Bengal (except Kolkata)\n" +
            "                                    </li>\n" +
            "                                </ol>\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile number – the number that made the call to Mobile Academy service\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Last called date – the date when the last call was made\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Circle wise Anonymous Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_Report.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA1/BU_MA_CWAU_Cal.png\"><br><br><br>\n" +
            "                        <p>5) For Block level users, the circle is selected by default and cannot be changed.</p>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA1/BU_MA_CWAU_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA1/BU_MA_CWAU_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA1/BU_MA_CWAU_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='ciu'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Cumulative Inactive Users</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all such ASHAs who have not yet started the course as\n" +
            "                            on date. The State / UT can act on this report and contact the ASHAs and motivate them to\n" +
            "                            access the Mobile Academy course and complete the course. In this way a higher percentage of\n" +
            "                            ASHAs accessing and completing the Mobile Academy program can be achieved. This report is a\n" +
            "                            very useful actionable report for the State / UT to monitor the ASHAs accessing the Mobile\n" +
            "                            Academy course. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Creation Date – this is the date when ASHA records came in to the Mobile Academy\n" +
            "                                system for the first time\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Cumulative Inactive Users\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_Report.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA2/BU_MA_CIU_Month.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA2/BU_MA_CIU_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA2/BU_MA_CIU_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA2/BU_MA_CIU_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='arr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Asha Rejected Records</b></p><br>\n" +
            "                        <p>This report gives the line-listing of all ASHAs whose records are received from MCTS / RCH\n" +
            "                            but got rejected by the acceptance logic run by the Mobile Academy system, because the\n" +
            "                            registered mobile numbers in the records are either incorrect or are duplicate. . The report\n" +
            "                            contains the MCTS or RCH IDs. This report is generated once every week. This line listing\n" +
            "                            report is available for download for the respective district and block users. The State / UT\n" +
            "                            / district / block can act upon this report and contact the ASHAs in the field and ask them\n" +
            "                            to update / correct their mobile numbers in the MCTS/RCH Application. This report is a very\n" +
            "                            useful actionable report for the State / UT / district / block to correct the ASHA contact\n" +
            "                            mobile number. It has following columns:</p>\n" +
            "                        <ol>\n" +
            "                            <li>\n" +
            "                                S. No.\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Name – name of the ASHA\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA MCTS/RCH ID – ASHA ID as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Mobile Number – ASHA’s mobile number as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                State – ASHA’s state as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                District – ASHA’s district as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Taluka – ASHA’s taluka as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Block – ASHA’s health block as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Facility – ASHA’s health facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Health Sub Facility – ASHA’s health sub facility as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Village – AHSA’s Village as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                ASHA Job Status – ASHA’s GF Status as received from MCTS/RCH\n" +
            "                            </li>\n" +
            "                            <li>\n" +
            "                                Reason for rejection – this gives why the ASHA record was rejected, it could be because\n" +
            "                                the mobile number was incorrect, or duplicate in the database – i.e. is already assigned\n" +
            "                                to another ASHA who is active on Mobile Academy.\n" +
            "                            </li>\n" +
            "                        </ol>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Under \"Reports Category\" select \"Mobile Academy Reports\", then</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Under \"Reports\" select \"Asha Rejected Records\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_Report.png\"><br><br><br>\n" +
            "                        <p>4.a) Then select the Month for which the reports are needed </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA3/BU_MA_ARR_Cal.png\"><br><br><br>\n" +
            "                        <p>4.b)Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\":</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA3/BU_MA_ARR_CalDate.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA3/BU_MA_ARR_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA3/BU_MA_ARR_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA3/BU_MA_ARR_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>" +
                    "</div>";
}
