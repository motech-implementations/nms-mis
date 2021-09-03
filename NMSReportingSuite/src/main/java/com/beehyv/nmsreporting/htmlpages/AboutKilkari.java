package com.beehyv.nmsreporting.htmlpages;

import java.util.Properties;
import static com.beehyv.nmsreporting.utils.Global.retrieveExternalProperties;

public class AboutKilkari {

    private Properties props = retrieveExternalProperties();

    private String tollfree = props.getProperty("kilkari.tollfree");
    private String stats_starttime = props.getProperty("kilkari.stats.starttime");
    private String stats_endtime = props.getProperty("kilkari.stats.endtime");
    private String stats_subscribers = props.getProperty("kilkari.stats.subscribers");
    private String stats_minutes = props.getProperty("kilkari.stats.minutes");
    private String stats_calls = props.getProperty("kilkari.stats.calls");
    private String support_endtime = props.getProperty("kilkari.support.endtime");
    private String support_totalregions = props.getProperty("kilkari.support.totalregions");
    private String support_regions = props.getProperty("kilkari.support.regions");
    private String totallanguages = props.getProperty("kilkari.totallanguages");
    private String languages = props.getProperty("kilkari.languages");

    public String pageContent ="<div class=\"container-fluid infoPages4\" data-ng-controller=\"AboutKilkariController\" >" +
            "  <div class=\"row\">\n" +
            "        <div class=\"col-xs-12\">\n" +
            "            <table>\n" +
            "               <tr>\n" +
            "                <th><p  style = \"font-size: 30px; margin-top: 10px; padding-left: 15px;font-weight: 300\">Kilkari</p></th>\n" +
            "                <th><hr class=\"infoHR\"/></th>\n" +
            "               </tr>\n" +
            "            </table>\n" +
            "        </div>\n" +
            "        <div class=\"col-xs-12\">\n" +
            "            <p><b>Kilkari</b> is an IVR based mobile health service that has been designed to deliver free, weekly and\n" +
            "                time-appropriate audio messages about pregnancy, childbirth and childcare directly to families’\n" +
            "                mobile phone. The service consists of 72 messages starting from the fourth month of pregnancy up until\n" +
            "                the child is one-year-old. Kilkari aims to increase the capacity of pregnant women, new mothers and their\n" +
            "                families to adopt healthy behaviours by increasing their knowledge, shifting attitudes and building self-efficacy.\n" +
            "                The objective is to improve family health – including family planning, reproductive, maternal, neonatal\n" +
            "                and child health, nutrition, sanitation and hygiene - by generating demand for healthy practices. </p>\n" +
            "\n" +
            "            <div>\n" +
            "                <ul>\n" +
            "                    <li>Once a pregnant woman or mother of a child under the age of one year registers her pregnancy with\n" +
            "                        the Accredited Social Health Activist (ASHA)/Auxiliary Nurse Midwife (ANM) and her data is entered\n" +
            "                        into the Government’s Maternal and Child Tracking System (MCTS)/Reproductive Child Health (RCH) database,\n" +
            "                        she will automatically begin to receive Kilkari messages.</li>\n" +
            "                    <br/>\n" +
            "                <li>Kilkari subscription is designed in two parts: The first part caters the pregnancy stage where a subscriber\n" +
            "                    gets 24 weekly calls if she gets registered in the first quarter of her pregnancy. The second part of the\n" +
            "                    subscription starts as soon the mother delivers, (data needs to be updated in MCTS/RCH) and this phase\n" +
            "                    continues for the next 48 weeks. The overall Kilkari subscription consists of 72 messages (24 + 48).</li>\n" +
            "                    <br/>\n" +
            "                <li>If a beneficiary has missed the weekly call or want to listen to the weekly message once again the beneficiary\n" +
            "                    can dial " + tollfree + " toll free to listen to the weekly message again from her registered mobile number.</li>\n" +
            "                    <br/>\n" +
            "                <li>From " + stats_starttime + " to " + stats_endtime + " we have around " + stats_subscribers + " subscribers and over " + stats_minutes + " minutes\n" +
            "                    of heard Kilkari content. Kilkari is approximately calling more than " + stats_calls + " subscribers every week.</li>\n" +
            "                    <br/>\n" +
            "                <li>As of " + support_endtime + " Kilkari is live in " + support_totalregions + " states & UTs including of " + support_regions + ".</li>\n" +
            "                    <br/>\n" +
            "                <li>Kilkari is currently live in " + totallanguages + " languages, which are – " + languages + ".</li>\n" +
            "                </ul>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>" +
            "</div>";
}
