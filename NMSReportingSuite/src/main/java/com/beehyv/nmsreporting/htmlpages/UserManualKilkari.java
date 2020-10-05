package com.beehyv.nmsreporting.htmlpages;

public class UserManualKilkari {
    public static String pageContent ="<div class=\"container-fluid\" data-ng-controller=\"kilkariController\" style=\"padding-left: 30px\">" +
            "    <div data-ng-hide=\"flag\">\n" +
            "        <h2 class=\"user-manual-header\">Kilkari Line Listing Reports</h2>\n" +
            "        <div class=\"user-manual-right-text\">\n" +
            "            <!--<p><div data-ng-click=\"func('abr')\"><u>Aggrgate Beneficiary Report</u></div></p>-->\n" +
            "            <p data-ng-click=\"func('dfna')\"><u>Deactivated for not answering</u></p>\n" +
            "            <p data-ng-click=\"func('dfll')\"><u>Deactivated for Low Listenership</u></p>\n" +
            "            <p data-ng-click=\"func('bl')\"><u>Listened to &lt; 25% this month</u></p>\n" +
            "            <p data-ng-click=\"func('bwd')\"><u>Self Deactivation</u></p>\n" +
            "            <!--<p><div data-ng-click=\"func('blr')\"><u>Beneficiary listenership report</u></div></p>\n" +
            "            <p><div data-ng-click=\"func('kcsr')\"><u>Kilkari Cumulative Summary report</u></div></p>-->\n" +
            "\n" +
            "            <!--        <p><div data-ng-click=\"func('mtr')\"><u>Monthly Thematic Report</u></div></p>\n" +
            "                    <p><div data-ng-click=\"func('rlr')\"><u>Repeat Listenership report</u></div></p>\n" +
            "                    <p><div data-ng-click=\"func('ksr')\"><u>Kilkari subscriber report</u></div></p>\n" +
            "                    <p><div data-ng-click=\"func('kmlr')\"><u>Kilkari message listenership report</u></div></p>\n" +
            "                    <p><div data-ng-click=\"func('kbcr')\"><u>Kilkari beneficiary completion report</u></div></p>\n" +
            "                    <p><div data-ng-click=\"func('klmr')\"><u>Kilkari Listener matrix report</u></div></p>-->\n" +
            "            <p data-ng-click=\"func('mrr')\"><u>Mother Rejected Records</u></p>\n" +
            "            <p data-ng-click=\"func('crr')\"><u>Child Rejected Records</u></p>\n" +
            "            <!--<p><div data-ng-click=\"func('ku')\"><u>Kilkari Usage</u></div></p>\n" +
            "            <p><div data-ng-click=\"func('kc')\"><u>Kilkari Call</u></div></p>\n" +
            "            <p><div data-ng-click=\"func('kmm')\"><u>Kilkari Message Matrix</u></div></p>\n" +
            "            <p><div data-ng-click=\"func('krl')\"><u>Kilkari Repeat Listener</u></div></p>-->\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div data-ng-hide=\"!flag\" class=\"user-manual-reports\">\n" +
            "        <div><img alt=\"user manual\" src=\"./images/back1.png\" data-ng-click=\"flag=!flag\" class=\"user-manual-back\">\n" +
            "            <div style=\"display: inline-block\" class=\"imageModify\"><b>Kilkari Line Listing Report</b><br>\n" +
            "\n" +
            "                <div data-ng-hide=\"selectRole != 1 && selectRole != 5\">\n" +
            "                    <div data-ng-hide=\"param!=='bl'\" style=\"display: inline-block; \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Listened to &lt; 25% this month</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who listened to an average of\n" +
            "                            less than 25% of the content across all calls answered by the beneficiary, in the selected\n" +
            "                            month. This report tracks beneficiaries who have either not answered any calls during the\n" +
            "                            concerned month or answered call(s) but not listened to the complete message (i.e., the\n" +
            "                            average content heard on the calls answered during the reported month is <25%). </p>\n" +
            "                        <p>The concerned State / UT / district / block officials can act upon this report and encourage\n" +
            "                            ASHAs of the concerned geography to contact the said beneficiaries and understand why they\n" +
            "                            are not listening to the Kilkari calls for the full duration. If the mobile number\n" +
            "                            registered against the concerned beneficiary is correct, then they should be motivated to\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number (to their\n" +
            "                            respective ANMs) that would be accessible to the beneficiary more frequently. ASHAs should\n" +
            "                            be encouraged to update status of the listed non-answering numbers to the concerned\n" +
            "                            officials so that the necessary data corrections in the system can be made.</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Listen to &lt; 25% this month\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_State.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_Sub-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_DownLoad.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_L25_Sub.png\"><br><br><br>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='bwd'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Self Deactivation</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have used the option\n" +
            "                            available on the Kilkari call (IVR menu option), to deactivate themselves, during the the\n" +
            "                            selected month, for the selected geography . This line listing report is available for\n" +
            "                            download for the respective state, district and block users.</p><p>The State / UT / district /\n" +
            "                            block can act upon this report and encourage ASHAs of the concerned geography to contact the\n" +
            "                            said beneficiaries and understand why they have deactivated from the Kilkari service. If the\n" +
            "                            mobile number belongs to the correct beneficiary, then they should be motivated to\n" +
            "                            reactivate their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH\n" +
            "                            record and listen to the Kilkari content for the complete duration of 90 secs on each call.\n" +
            "                            If the mobile number registered against that beneficiary does not belong to the beneficiary\n" +
            "                            or that number is not accessible to them under normal calling hours, then the beneficiary\n" +
            "                            should be advised to provide the correct mobile number or provide an alternative number that\n" +
            "                            would be accessible to the beneficiary more frequently. ASHAs should be encouraged to update\n" +
            "                            status of the listed non-answering numbers to the concerned officials so that the necessary\n" +
            "                            data corrections in the system can be made. </p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Self Deactivation\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_State.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_DownLoad.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_SD_sub.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='dfna'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for not answering</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for not answering a single call for six consecutive weeks, for the selected\n" +
            "                            month, for the selected geography (state/district/block).</p>\n" +
            "                        <p>List of system-deactivated users for not listening, is available at State/UT, district, block\n" +
            "                            level -as per user selection. The concerned State / UT officials can act upon this report\n" +
            "                            and encourage ASHAs of the concerned geography to contact the said beneficiaries and\n" +
            "                            understand why they are not listening to the Kilkari calls. If the mobile number registered\n" +
            "                            against the concerned beneficiary is correct, then they should be motivated to reactivate\n" +
            "                            their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH record and\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number that would be\n" +
            "                            accessible to the beneficiary more frequently. ASHAs should be encouraged to update status\n" +
            "                            of the listed non-answering numbers to the concerned officials so that the necessary data\n" +
            "                            corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) select \"Kilkari Reports\" under \"Reports Category\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for not answering\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_State.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_Block.png\"><br><br><br>\n" +
            "                        <p>8) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_DownLoad.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DNA_sub.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='dfll'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for Low Listenership</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for listening less than 25% of the content for each call answered by the\n" +
            "                            beneficiary over a period of six consecutive weeks, for the selected month, for the selected\n" +
            "                            geography (state/district/block).</p>\n" +
            "                        <p>The State / UT / district / block can act upon this report and encourage ASHAs of the\n" +
            "                            concerned geography to contact the said beneficiaries and understand why they were not\n" +
            "                            listening to the Kilkari calls for the full duration. If the mobile number belongs to the\n" +
            "                            correct beneficiary, then they should be motivated to reactivate their Kilkari Subscription\n" +
            "                            by visiting their ANM and updating their MCTS/RCH record and listen to the Kilkari content\n" +
            "                            for the complete duration of 90 secs on each call. If the mobile number registered against\n" +
            "                            that beneficiary does not belong to the beneficiary or that number is not accessible to them\n" +
            "                            under normal calling hours, then the beneficiary should be advised to provide the correct\n" +
            "                            mobile number or provide an alternative number that would be accessible to the beneficiary\n" +
            "                            more frequently. ASHAs should be encouraged to update status of the listed non-answering\n" +
            "                            numbers to the concerned officials so that the necessary data corrections in the system can\n" +
            "                            be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for Low Listenership\" under \"Reports\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_State.png\"><br><br><br>\n" +
            "                        <p>6) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_Block.png\"><br><br><br>\n" +
            "                        <p>8) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_DownLoad.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_DLL_sub.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='mrr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mother Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all Mother beneficiaries records that are received\n" +
            "                            from MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because\n" +
            "                            the registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Mother Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_State.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_Dist.png\"><br><br><br>\n" +
            "                        <p>8) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_Block.png\"><br><br><br>\n" +
            "                        <p>9) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_Sub-Copy.png\"><br><br><br>\n" +
            "                        <p>10) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_download.png\"><br><br><br>\n" +
            "                        <p>11) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_MRR_Sub.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='crr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Child Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all child beneficiaries records that are received from\n" +
            "                            MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because the\n" +
            "                            registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/MA/NA_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Child Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) Then select an option in the 'State' dropdown for a specific state or to get all states\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_State.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'District' dropdown in the same manner: (does not work if\n" +
            "                            State is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_Dist.png\"><br><br><br>\n" +
            "                        <p>8) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_Block.png\"><br><br><br>\n" +
            "                        <p>9) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>10) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_Download.png\"><br><br><br>\n" +
            "                        <p>11) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/National/kilkari/NA_KR_CRR_sub.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "                <div data-ng-hide=\"selectRole != 2 && selectRole != 6\">\n" +
            "                    <div data-ng-hide=\"param!=='bl'\" style=\"display: inline-block; \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Listened to &lt; 25% this month</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who listened to an average of\n" +
            "                            less than 25% of the content across all calls answered by the beneficiary, in the selected\n" +
            "                            month. This report tracks beneficiaries who have either not answered any calls during the\n" +
            "                            concerned month or answered call(s) but not listened to the complete message (i.e., the\n" +
            "                            average content heard on the calls answered during the reported month is <25%). </p>\n" +
            "                        <p>The concerned State / UT / district / block officials can act upon this report and encourage\n" +
            "                            ASHAs of the concerned geography to contact the said beneficiaries and understand why they\n" +
            "                            are not listening to the Kilkari calls for the full duration. If the mobile number\n" +
            "                            registered against the concerned beneficiary is correct, then they should be motivated to\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number (to their\n" +
            "                            respective ANMs) that would be accessible to the beneficiary more frequently. ASHAs should\n" +
            "                            be encouraged to update status of the listed non-answering numbers to the concerned\n" +
            "                            officials so that the necessary data corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_RC.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>3) Then select \"Listen to &lt; 25% this month\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\" style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_LT25_Cal.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_LT25_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_LT25_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_LT25_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_LT25_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_LT25_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='bwd'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Self Deactivation</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have used the option\n" +
            "                            available on the Kilkari call (IVR menu option), to deactivate themselves, during the the\n" +
            "                            selected month, for the selected geography . This line listing report is available for\n" +
            "                            download for the respective state, district and block users.</p><p>The State / UT / district /\n" +
            "                        block can act upon this report and encourage ASHAs of the concerned geography to contact the\n" +
            "                        said beneficiaries and understand why they have deactivated from the Kilkari service. If the\n" +
            "                        mobile number belongs to the correct beneficiary, then they should be motivated to\n" +
            "                        reactivate their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH\n" +
            "                        record and listen to the Kilkari content for the complete duration of 90 secs on each call.\n" +
            "                        If the mobile number registered against that beneficiary does not belong to the beneficiary\n" +
            "                        or that number is not accessible to them under normal calling hours, then the beneficiary\n" +
            "                        should be advised to provide the correct mobile number or provide an alternative number that\n" +
            "                        would be accessible to the beneficiary more frequently. ASHAs should be encouraged to update\n" +
            "                        status of the listed non-answering numbers to the concerned officials so that the necessary\n" +
            "                        data corrections in the system can be made. </p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Self Deactivation\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_SD_Cal.png\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_SD_Dist.png\"><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_SD_Block.png\"><br><br><br>\n" +
            "                        <p>8) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_SD_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_SD_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA3/SA_KA_SD_Reset.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='dfna'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for not answering</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for not answering a single call for six consecutive weeks, for the selected\n" +
            "                            month, for the selected geography (state/district/block).</p>\n" +
            "                        <p>List of system-deactivated users for not listening, is available at State/UT, district, block\n" +
            "                            level -as per user selection. The concerned State / UT officials can act upon this report\n" +
            "                            and encourage ASHAs of the concerned geography to contact the said beneficiaries and\n" +
            "                            understand why they are not listening to the Kilkari calls. If the mobile number registered\n" +
            "                            against the concerned beneficiary is correct, then they should be motivated to reactivate\n" +
            "                            their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH record and\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number that would be\n" +
            "                            accessible to the beneficiary more frequently. ASHAs should be encouraged to update status\n" +
            "                            of the listed non-answering numbers to the concerned officials so that the necessary data\n" +
            "                            corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) select \"Kilkari Reports\" under \"Reports Category\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for not answering\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_DFNA_Cal.png\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_DFNA_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_DFNA_Block.png\"><br><br><br>\n" +
            "                        <p>8) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_DFNA_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_DFNA_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_DFNA_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" ;
    public static String pageContent2 =
            "                    <div data-ng-hide=\"param!=='dfll'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for Low Listenership</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for listening less than 25% of the content for each call answered by the\n" +
            "                            beneficiary over a period of six consecutive weeks, for the selected month, for the selected\n" +
            "                            geography (state/district/block).</p>\n" +
            "                        <p>The State / UT / district / block can act upon this report and encourage ASHAs of the\n" +
            "                            concerned geography to contact the said beneficiaries and understand why they were not\n" +
            "                            listening to the Kilkari calls for the full duration. If the mobile number belongs to the\n" +
            "                            correct beneficiary, then they should be motivated to reactivate their Kilkari Subscription\n" +
            "                            by visiting their ANM and updating their MCTS/RCH record and listen to the Kilkari content\n" +
            "                            for the complete duration of 90 secs on each call. If the mobile number registered against\n" +
            "                            that beneficiary does not belong to the beneficiary or that number is not accessible to them\n" +
            "                            under normal calling hours, then the beneficiary should be advised to provide the correct\n" +
            "                            mobile number or provide an alternative number that would be accessible to the beneficiary\n" +
            "                            more frequently. ASHAs should be encouraged to update status of the listed non-answering\n" +
            "                            numbers to the concerned officials so that the necessary data corrections in the system can\n" +
            "                            be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for Low Listenership\" under \"Reports\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA2/SA_KA_DFLL_Cal.png\"><br><br><br>\n" +
            "                        <p>5) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>6) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA2/SA_KA_DFLL_Dist.png\"><br><br><br>\n" +
            "                        <p>7) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA2/SA_KA_DFLL_Block.png\"><br><br><br>\n" +
            "                        <p>8) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA2/SA_KA_DFLL_Submit.png\"><br><br><br>\n" +
            "                        <p>9) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA2/SA_KA_DFLL_Download.png\"><br><br><br>\n" +
            "                        <p>10) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA2/SA_KA_DFLL_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='mrr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mother Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all Mother beneficiaries records that are received\n" +
            "                            from MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because\n" +
            "                            the registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Mother Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>7) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_Dist.png\"><br><br><br>\n" +
            "                        <p>8) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_Block.png\"><br><br><br>\n" +
            "                        <p>9) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_Submit.png\"><br><br><br>\n" +
            "                        <p>10) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_Download.png\"><br><br><br>\n" +
            "                        <p>11) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA5/SA_KA_MRR_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='crr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Child Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all child beneficiaries records that are received from\n" +
            "                            MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because the\n" +
            "                            registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Child Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA1/SA_KA_Rep.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) For State users, their 'State' option is already selected.</p>\n" +
            "                        <p>7) Select an option in the 'District' dropdown for a specific state or to get all districts\n" +
            "                            data, select 'All':</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_Dist.png\"><br><br><br>\n" +
            "                        <p>8) Next, select an option from the 'Block' dropdown in the same manner: (does not work if\n" +
            "                            District is set to all)</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_Block.png\"><br><br><br>\n" +
            "                        <p>9) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_Submit.png\"><br><br><br>\n" +
            "                        <p>10) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_Download.png\"><br><br><br>\n" +
            "                        <p>11) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/State/KA6/SA_KA_CRR_Reset.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "                <div data-ng-hide=\"selectRole != 3 && selectRole != 7\"><!--Disrict User-->\n" +
            "                    <div data-ng-hide=\"param!=='bl'\" style=\"display: inline-block; \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Listened to <25% this month</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who listened to an average of\n" +
            "                            less than 25% of the content across all calls answered by the beneficiary, in the selected\n" +
            "                            month. This report tracks beneficiaries who have either not answered any calls during the\n" +
            "                            concerned month or answered call(s) but not listened to the complete message (i.e., the\n" +
            "                            average content heard on the calls answered during the reported month is <25%). </p>\n" +
            "                        <p>The concerned State / UT / district / block officials can act upon this report and encourage\n" +
            "                            ASHAs of the concerned geography to contact the said beneficiaries and understand why they\n" +
            "                            are not listening to the Kilkari calls for the full duration. If the mobile number\n" +
            "                            registered against the concerned beneficiary is correct, then they should be motivated to\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number (to their\n" +
            "                            respective ANMs) that would be accessible to the beneficiary more frequently. ASHAs should\n" +
            "                            be encouraged to update status of the listed non-answering numbers to the concerned\n" +
            "                            officials so that the necessary data corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>3) Then select \"Listen to &lt; 25% this month\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_All.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_L25_Cal.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from “Block\" dropdown, under\n" +
            "                            specified \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_L25_Block.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_L25_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_L25_DownLoad.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_L25_sub.png\"><br>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='bwd'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Self Deactivation</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have used the option\n" +
            "                            available on the Kilkari call (IVR menu option), to deactivate themselves, during the the\n" +
            "                            selected month, for the selected geography . This line listing report is available for\n" +
            "                            download for the respective state, district and block users.</p><p>The State / UT / district /\n" +
            "                        block can act upon this report and encourage ASHAs of the concerned geography to contact the\n" +
            "                        said beneficiaries and understand why they have deactivated from the Kilkari service. If the\n" +
            "                        mobile number belongs to the correct beneficiary, then they should be motivated to\n" +
            "                        reactivate their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH\n" +
            "                        record and listen to the Kilkari content for the complete duration of 90 secs on each call.\n" +
            "                        If the mobile number registered against that beneficiary does not belong to the beneficiary\n" +
            "                        or that number is not accessible to them under normal calling hours, then the beneficiary\n" +
            "                        should be advised to provide the correct mobile number or provide an alternative number that\n" +
            "                        would be accessible to the beneficiary more frequently. ASHAs should be encouraged to update\n" +
            "                        status of the listed non-answering numbers to the concerned officials so that the necessary\n" +
            "                        data corrections in the system can be made. </p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Self Deactivation\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_SD_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_SD_Block.png\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_SD_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_SD_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_SD_sub.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='dfna'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for not answering</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for not answering a single call for six consecutive weeks, for the selected\n" +
            "                            month, for the selected geography (state/district/block).</p>\n" +
            "                        <p>List of system-deactivated users for not listening, is available at State/UT, district, block\n" +
            "                            level -as per user selection. The concerned State / UT officials can act upon this report\n" +
            "                            and encourage ASHAs of the concerned geography to contact the said beneficiaries and\n" +
            "                            understand why they are not listening to the Kilkari calls. If the mobile number registered\n" +
            "                            against the concerned beneficiary is correct, then they should be motivated to reactivate\n" +
            "                            their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH record and\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number that would be\n" +
            "                            accessible to the beneficiary more frequently. ASHAs should be encouraged to update status\n" +
            "                            of the listed non-answering numbers to the concerned officials so that the necessary data\n" +
            "                            corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) select \"Kilkari Reports\" under \"Reports Category\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for not answering\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DNA_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DNA_Block.png\"><br><br><br>\n" +
            "                        <p>6) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DNA_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DNA_DownLoad.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DNA_sub.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='dfll'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for Low Listenership</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for listening less than 25% of the content for each call answered by the\n" +
            "                            beneficiary over a period of six consecutive weeks, for the selected month, for the selected\n" +
            "                            geography (state/district/block).</p>\n" +
            "                        <p>The State / UT / district / block can act upon this report and encourage ASHAs of the\n" +
            "                            concerned geography to contact the said beneficiaries and understand why they were not\n" +
            "                            listening to the Kilkari calls for the full duration. If the mobile number belongs to the\n" +
            "                            correct beneficiary, then they should be motivated to reactivate their Kilkari Subscription\n" +
            "                            by visiting their ANM and updating their MCTS/RCH record and listen to the Kilkari content\n" +
            "                            for the complete duration of 90 secs on each call. If the mobile number registered against\n" +
            "                            that beneficiary does not belong to the beneficiary or that number is not accessible to them\n" +
            "                            under normal calling hours, then the beneficiary should be advised to provide the correct\n" +
            "                            mobile number or provide an alternative number that would be accessible to the beneficiary\n" +
            "                            more frequently. ASHAs should be encouraged to update status of the listed non-answering\n" +
            "                            numbers to the concerned officials so that the necessary data corrections in the system can\n" +
            "                            be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for Low Listenership\" under \"Reports\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DLL_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DLL_Block.png\"><br><br><br>\n" +
            "                        <p>6) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DLL_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DLL_DownLoad.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_DLL_sub.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='mrr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mother Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all Mother beneficiaries records that are received\n" +
            "                            from MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because\n" +
            "                            the registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Mother Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_MRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_MRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_MRR_Block.png\"><br><br><br>\n" +
            "                        <p>7) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_MRR_sub-Copy.png\"><br><br><br>\n" +
            "                        <p>8) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_MRR_download.png\"><br><br><br>\n" +
            "                        <p>9) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_MRR_sub.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='crr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Child Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all child beneficiaries records that are received from\n" +
            "                            MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because the\n" +
            "                            registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/MA/DA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Child Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_All.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_CRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_CRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) Since this is for District user/admin, Select any/all block from 'Block', under specified\n" +
            "                            \"District\" and \"State\".</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_CRR_Block.png\"><br><br><br>\n" +
            "                        <p>7) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_CRR_Sub-Copy.png\"><br><br><br>\n" +
            "                        <p>8) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_CRR_Download.png\"><br><br><br>\n" +
            "                        <p>9) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/District/kilkari/DA_KR_CRR_Sub.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "\n";
    public static String pageContent3 =
            "                <div data-ng-hide=\"selectRole !=4\">\n" +
            "                    <div data-ng-hide=\"param!=='bl'\" style=\"display: inline-block; \">\n" +
            "                        <p style=\"font-size: 30px\"><b>Listened to &lt; 25% this month</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who listened to an average of\n" +
            "                            less than 25% of the content across all calls answered by the beneficiary, in the selected\n" +
            "                            month. This report tracks beneficiaries who have either not answered any calls during the\n" +
            "                            concerned month or answered call(s) but not listened to the complete message (i.e., the\n" +
            "                            average content heard on the calls answered during the reported month is <25%). </p>\n" +
            "                        <p>The concerned State / UT / district / block officials can act upon this report and encourage\n" +
            "                            ASHAs of the concerned geography to contact the said beneficiaries and understand why they\n" +
            "                            are not listening to the Kilkari calls for the full duration. If the mobile number\n" +
            "                            registered against the concerned beneficiary is correct, then they should be motivated to\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number (to their\n" +
            "                            respective ANMs) that would be accessible to the beneficiary more frequently. ASHAs should\n" +
            "                            be encouraged to update status of the listed non-answering numbers to the concerned\n" +
            "                            officials so that the necessary data corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>3) Then select \"Listen to &lt; 25% this month\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_Reports.png\" style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA3/BU_KA_LT25_Cal.png\"\n" +
            "                             style=\"max-width: 828px;\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA3/BU_KA_LT25_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA3/BU_KA_LT25_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA3/BU_KA_LT25_Reset.png\"><br>\n" +
            "\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='bwd'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Self Deactivation</b></p><br>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have used the option\n" +
            "                            available on the Kilkari call (IVR menu option), to deactivate themselves, during the the\n" +
            "                            selected month, for the selected geography . This line listing report is available for\n" +
            "                            download for the respective state, district and block users.</p><p>The State / UT / district /\n" +
            "                        block can act upon this report and encourage ASHAs of the concerned geography to contact the\n" +
            "                        said beneficiaries and understand why they have deactivated from the Kilkari service. If the\n" +
            "                        mobile number belongs to the correct beneficiary, then they should be motivated to\n" +
            "                        reactivate their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH\n" +
            "                        record and listen to the Kilkari content for the complete duration of 90 secs on each call.\n" +
            "                        If the mobile number registered against that beneficiary does not belong to the beneficiary\n" +
            "                        or that number is not accessible to them under normal calling hours, then the beneficiary\n" +
            "                        should be advised to provide the correct mobile number or provide an alternative number that\n" +
            "                        would be accessible to the beneficiary more frequently. ASHAs should be encouraged to update\n" +
            "                        status of the listed non-answering numbers to the concerned officials so that the necessary\n" +
            "                        data corrections in the system can be made. </p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Self Deactivation\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_Reports.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA4/BU_KA_SD_Month.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA4/BU_KA_SD_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA4/BU_KA_SD_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA4/BU_KA_SD_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='dfna'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for not answering</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for not answering a single call for six consecutive weeks, for the selected\n" +
            "                            month, for the selected geography (state/district/block).</p>\n" +
            "                        <p>List of system-deactivated users for not listening, is available at State/UT, district, block\n" +
            "                            level -as per user selection. The concerned State / UT officials can act upon this report\n" +
            "                            and encourage ASHAs of the concerned geography to contact the said beneficiaries and\n" +
            "                            understand why they are not listening to the Kilkari calls. If the mobile number registered\n" +
            "                            against the concerned beneficiary is correct, then they should be motivated to reactivate\n" +
            "                            their Kilkari Subscription by visiting their ANM and updating their MCTS/RCH record and\n" +
            "                            listen to the Kilkari content for the complete duration of 90 secs on each call. If the\n" +
            "                            mobile number registered against that beneficiary does not belong to the beneficiary or that\n" +
            "                            number is not accessible to them under normal calling hours, then the beneficiary should be\n" +
            "                            advised to provide the correct mobile number or provide an alternative number that would be\n" +
            "                            accessible to the beneficiary more frequently. ASHAs should be encouraged to update status\n" +
            "                            of the listed non-answering numbers to the concerned officials so that the necessary data\n" +
            "                            corrections in the system can be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) select \"Kilkari Reports\" under \"Reports Category\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for not answering\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_Reports.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_DFNA_Month.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_DFNA_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_DFNA_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_DFNA_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div data-ng-hide=\"param!=='dfll'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Deactivated for Low Listenership</b></p>\n" +
            "                        <p>This report provides a line-listing of all the beneficiaries who have been deactivated by the\n" +
            "                            Kilkari system for listening less than 25% of the content for each call answered by the\n" +
            "                            beneficiary over a period of six consecutive weeks, for the selected month, for the selected\n" +
            "                            geography (state/district/block).</p>\n" +
            "                        <p>The State / UT / district / block can act upon this report and encourage ASHAs of the\n" +
            "                            concerned geography to contact the said beneficiaries and understand why they were not\n" +
            "                            listening to the Kilkari calls for the full duration. If the mobile number belongs to the\n" +
            "                            correct beneficiary, then they should be motivated to reactivate their Kilkari Subscription\n" +
            "                            by visiting their ANM and updating their MCTS/RCH record and listen to the Kilkari content\n" +
            "                            for the complete duration of 90 secs on each call. If the mobile number registered against\n" +
            "                            that beneficiary does not belong to the beneficiary or that number is not accessible to them\n" +
            "                            under normal calling hours, then the beneficiary should be advised to provide the correct\n" +
            "                            mobile number or provide an alternative number that would be accessible to the beneficiary\n" +
            "                            more frequently. ASHAs should be encouraged to update status of the listed non-answering\n" +
            "                            numbers to the concerned officials so that the necessary data corrections in the system can\n" +
            "                            be made.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Deactivated for Low Listenership\" under \"Reports\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_Reports.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month for which the reports are needed</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA2/BU_KA_DFLL_Month.png\"><br><br><br>\n" +
            "                        <p>5) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>6) To download the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA2/BU_KA_DFLL_Submit.png\"><br><br><br>\n" +
            "                        <p>7) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA2/BU_KA_DFLL_Download.png\"><br><br><br>\n" +
            "                        <p>8) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA2/BU_KA_DFLL_Reset.png\"><br><br><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='mrr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Mother Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all Mother beneficiaries records that are received\n" +
            "                            from MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because\n" +
            "                            the registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Mother Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_Reports.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA5/BU_KA_MRR_Month.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA5/BU_KA_MRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>7) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA5/BU_KA_MRR_Submit.png\"><br><br><br>\n" +
            "                        <p>8) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA5/BU_KA_MRR_Download.png\"><br><br><br>\n" +
            "                        <p>9) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA5/BU_KA_MRR_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                    <div data-ng-hide=\"param!=='crr'\">\n" +
            "                        <p style=\"font-size: 30px\"><b>Child Rejected Records</b></p>\n" +
            "                        <p>This report provides a line-listing of all child beneficiaries records that are received from\n" +
            "                            MCTS/RCH but were rejected by the acceptance logic run by the Kilkari system because the\n" +
            "                            registered mobile numbers are either incorrect or are duplicate. The report contains the\n" +
            "                            MCTS or RCH IDs of the concerned beneficiary records. This report would be generated weekly.\n" +
            "                            This line listing report is available for download for the respective district and block\n" +
            "                            users. The State / UT / district / block official(s) can act upon this report and encourage\n" +
            "                            ASHAs in the concerned geography to contact the said beneficiaries and get their correct\n" +
            "                            mobile numbers registered in the RCH/MCTS Application. In this way the mobile numbers of\n" +
            "                            beneficiaries can be corrected and a higher percentage of genuine beneficiaries can benefit\n" +
            "                            from the Kilkari program rolled out by the MoHFW. This report is a very useful actionable\n" +
            "                            report for the State / UT to correct the beneficiary contact mobile number.</p>\n" +
            "                        <p>Steps to download the reports</p>\n" +
            "                        <p>1) The users can login into the system using the URL: <a href=\"https://rchivrreports.in/\">https://rchivrreports.in/</a>.\n" +
            "                        </p>\n" +
            "                        <p>2) Select \"Kilkari Reports\" under \"Reports Category\"</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/MA0/BU_MA_RC.png\"><br><br><br>\n" +
            "                        <p>3) Then select \"Child Rejected Records\" under \"Reports\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA1/BU_KA_Reports.png\"><br><br><br>\n" +
            "                        <p>4) Then select the Month under \"Select Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA6/BU_KA_CRR_Cal.png\"><br><br><br>\n" +
            "                        <p>5) Then select the 1st day of week(Sunday) for which the report is needed under \"Select\n" +
            "                            Week\" </p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA6/BU_KA_CRR_CalDate.png\"><br><br><br>\n" +
            "                        <p>6) Since this is for block user, location details upto “Block” are already selected.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/usermanual-blocklevel1.jpg\"><br><br><br>\n" +
            "                        <p>7) To view the result, click the \"Submit\" button</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA6/BU_KA_CRR_Submit.png\"><br><br><br>\n" +
            "                        <p>8) Click on the download link to download the report. The selected report will be downloaded\n" +
            "                            in Excel files.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA6/BU_KA_CRR_Download.png\"><br><br><br>\n" +
            "                        <p>9) Clicking on ‘Reset’ button will reset all the fields to their default values.</p>\n" +
            "                        <img alt=\"user manual\" src=\"./images/Block/KA6/BU_KA_CRR_Reset.png\"><br>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                </span></div>\n" +
            "        </div>\n" +
            "\n" +
            "    </div>" +
                    "</div>";
}
