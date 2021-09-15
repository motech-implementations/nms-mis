package com.beehyv.nmsreporting.htmlpages;

import java.util.Properties;
import static com.beehyv.nmsreporting.utils.Global.retrieveExternalProperties;

public class AboutMA {

    private Properties props = retrieveExternalProperties();

    private String tollfree = props.getProperty("ma.tollfree");
    private String course_started = props.getProperty("ma.course.started");
    private String course_completed = props.getProperty("ma.course.completed");
    private String support_totalregions = props.getProperty("ma.support.totalregions");
    private String support_regions = props.getProperty("ma.support.regions");
    private String totallanguages = props.getProperty("ma.totallanguages");
    private String languages = props.getProperty("ma.languages");

    public String pageContent ="<div class=\"container-fluid infoPages2\" data-ng-controller=\"AboutMAController\" >" +
            " <div class=\"row\">\n" +
            "        <div class=\"col-xs-12\">\n" +
            "            <table>\n" +
            "                <tr>\n" +
            "                <th><p  style = \"font-size: 30px; margin-top: 10px; padding-left: 15px;font-weight: 300\">Mobile Academy</p></th>\n" +
            "                <th><hr class=\"infoHR\"/></th>\n" +
            "                </tr>\n" +
            "            </table>\n" +
            "        </div>\n" +
            "        <div class=\"col-xs-12\">\n" +
            "            <p><b>Mobile Academy</b> is an IVR-based mobile training course designed to refresh knowledge of ASHAs on simple steps families can take to improve the health of mothers and babies, and\n" +
            "                to improve their ability to communicate these steps clearly to mothers and their families.</p>\n" +
            "\n" +
            "            <div>\n" +
            "                <ul>\n" +
            "                    <li>To access the course, all the  ASHA needs to do is dial " + tollfree + " toll free from her mobile number that is\n" +
            "                        registered on the government’s Maternal and Child Tracking System (MCTS)/Reproductive Child Health (RCH) database and she can begin the course.</li>\n" +
            "                    <br/>\n" +
            "                    <li>Mobile Academy is 240 minutes long, and consists of 11 chapters. Each chapter has four lessons each (total of 44 lessons)\n" +
            "                        and at the end of each chapter there is a multiple-choice quiz. The ASHA has to press 1 or 2 on her mobile phone to select the correct answer.</li>\n" +
            "                    <br/>\n" +
            "                    <li>At the end of the course, the  ASHA gets an SMS with her final score. If she answered at least 50% of the quiz\n" +
            "                        questions correctly, she gets a certificate from the Government of India for successful completion of the training course.</li>\n" +
            "                    <br/>\n" +
            "                    <li>Mobile Academy is now live in " + support_totalregions + " states of " + support_regions + ".</li>\n" +
            "                    <br/>\n" +
            "                    <li>More than " + course_started + " ASHAs have started the course so far and around " + course_completed + " ASHAs have successfully completed the course.</li>\n" +
            "                    <br/>\n" +
            "                    <li>Mobile Academy has been rolled out " + totallanguages + " languages (" + languages + ").</li>\n" +
            "                </ul>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>" +
            "</div>";
}
