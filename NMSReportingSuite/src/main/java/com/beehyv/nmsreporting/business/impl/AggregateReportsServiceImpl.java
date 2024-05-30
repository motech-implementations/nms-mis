package com.beehyv.nmsreporting.business.impl;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.image.Image;
import com.beehyv.nmsreporting.business.AggregateReportsService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.AggregateExcelDto;
import com.beehyv.nmsreporting.entity.MASubscriberDto;
import com.beehyv.nmsreporting.entity.Report;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.*;
import com.ibm.icu.text.NumberFormat;
import org.apache.fontbox.ttf.CmapTable;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static com.beehyv.nmsreporting.utils.Constants.*;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;


/**
 * Created by beehyv on 19/9/17.
 */
@Service("aggregateReportsService")
@Transactional
public class AggregateReportsServiceImpl implements AggregateReportsService {

    private Logger logger = LoggerFactory.getLogger(AggregateReportsServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateCumulativeMADao aggregateCumulativeMADao;

    @Autowired
    private AggregateCumulativekilkariDao aggregateCumulativekilkariDao;

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private HealthFacilityDao healthFacilitydao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;

    @Autowired
    private MASubscriberDao maSubscriberDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateReportsServiceImpl.class);


    @Override
    public List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId, String locationType, Date toDate, boolean isCumulative) {
       LOGGER.info("todate is " + toDate );
        Date date = toDate;
        LOGGER.info("date is " + date );
        List<AggregateCumulativeMA> CumulativeSummery = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if (locationType.equalsIgnoreCase("State")) {
            List<State> states = stateDao.getStatesByServiceType("MOBILE_ACADEMY");
            for (State s : states) {
                Date serviceStartDate = stateServiceDao.getServiceStartDateForState(s.getStateId(), "MOBILE_ACADEMY");
                if (serviceStartDate == null) {
                    LOGGER.info("service start date is null for stateID"+s.getStateId());
                    continue;
                }
                if (date.before(serviceStartDate) && !isCumulative) {
                    date = serviceStartDate;
                }
                if (!isCumulative || !toDate.before(serviceStartDate)) {
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getStateId(), locationType, date));
                }
                date = toDate;
            }

        } else {
            if (locationType.equalsIgnoreCase("District")) {
                if (date.before(stateServiceDao.getServiceStartDateForState(locationId, "MOBILE_ACADEMY")) && !isCumulative) {
                    date = stateServiceDao.getServiceStartDateForState(locationId, "MOBILE_ACADEMY");
                }
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeMA stateCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId, "State", date);
                Integer ashasRegistered = 0;
                Integer ashasStarted = 0;
                Integer ashasNotStarted = 0;
                Integer ashasCompleted = 0;
                Integer ashasFailed = 0;
                Integer ashasRejected = 0;
                for (District d : districts) {
                    AggregateCumulativeMA distrcitCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(), locationType, date);
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(), locationType, date));
                    ashasStarted += distrcitCount.getAshasStarted();
                    ashasCompleted += distrcitCount.getAshasCompleted();
                    ashasFailed += distrcitCount.getAshasFailed();
                    ashasNotStarted += distrcitCount.getAshasNotStarted();
                    ashasRejected += distrcitCount.getAshasRejected();
                    ashasRegistered += distrcitCount.getAshasRegistered();
                }
                AggregateCumulativeMA noDistrictCount = new AggregateCumulativeMA();
                noDistrictCount.setAshasRejected(stateCounts.getAshasRejected() - ashasRejected);
                noDistrictCount.setAshasNotStarted(stateCounts.getAshasNotStarted() - ashasNotStarted);
                noDistrictCount.setAshasRegistered(stateCounts.getAshasRegistered() - ashasRegistered);
                noDistrictCount.setAshasFailed(stateCounts.getAshasFailed() - ashasFailed);
                noDistrictCount.setAshasCompleted(stateCounts.getAshasCompleted() - ashasCompleted);
                noDistrictCount.setAshasStarted(stateCounts.getAshasStarted() - ashasStarted);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId(stateCounts.getAshasRejected() - ashasRejected + stateCounts.getAshasNotStarted() - ashasNotStarted + stateCounts.getAshasRegistered() - ashasRegistered + stateCounts.getAshasFailed() - ashasFailed + stateCounts.getAshasCompleted() - ashasCompleted + stateCounts.getAshasStarted() - ashasStarted);
                noDistrictCount.setLocationId((long) -locationId);
                CumulativeSummery.add(noDistrictCount);
            } else {
                if (locationType.equalsIgnoreCase("Block")) {
                    if (date.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(), "MOBILE_ACADEMY")) && !isCumulative) {
                        date = stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(), "MOBILE_ACADEMY");
                    }
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeMA districtCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId, "District", date);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for (Block d : blocks) {
                        AggregateCumulativeMA blockCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(), locationType, date);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(), locationType, date));
                        ashasStarted += blockCount.getAshasStarted();
                        ashasCompleted += blockCount.getAshasCompleted();
                        ashasFailed += blockCount.getAshasFailed();
                        ashasNotStarted += blockCount.getAshasNotStarted();
                        ashasRejected += blockCount.getAshasRejected();
                        ashasRegistered += blockCount.getAshasRegistered();
                    }
                    AggregateCumulativeMA noBlockCount = new AggregateCumulativeMA();
                    noBlockCount.setAshasRejected(districtCounts.getAshasRejected() - ashasRejected);
                    noBlockCount.setAshasNotStarted(districtCounts.getAshasNotStarted() - ashasNotStarted);
                    noBlockCount.setAshasRegistered(districtCounts.getAshasRegistered() - ashasRegistered);
                    noBlockCount.setAshasFailed(districtCounts.getAshasFailed() - ashasFailed);
                    noBlockCount.setAshasCompleted(districtCounts.getAshasCompleted() - ashasCompleted);
                    noBlockCount.setAshasStarted(districtCounts.getAshasStarted() - ashasStarted);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId(districtCounts.getAshasRejected() - ashasRejected + districtCounts.getAshasNotStarted() - ashasNotStarted + districtCounts.getAshasRegistered() - ashasRegistered + districtCounts.getAshasFailed() - ashasFailed + districtCounts.getAshasCompleted() - ashasCompleted + districtCounts.getAshasStarted() - ashasStarted);
                    noBlockCount.setLocationId((long) -locationId);
                    CumulativeSummery.add(noBlockCount);
                } else {
                    if (date.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(), "MOBILE_ACADEMY")) && !isCumulative) {
                        date = stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(), "MOBILE_ACADEMY");
                    }
                    List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
                    List<HealthSubFacility> subcenters = new ArrayList<>();
                    for (HealthFacility hf : healthFacilities) {
                        subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
                    }
                    AggregateCumulativeMA blockCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId, "block", date);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for (HealthSubFacility s : subcenters) {
                        AggregateCumulativeMA subcenterCount = aggregateCumulativeMADao.getMACumulativeSummery(s.getHealthSubFacilityId(), locationType, date);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getHealthSubFacilityId(), locationType, date));
                        ashasStarted += subcenterCount.getAshasStarted();
                        ashasCompleted += subcenterCount.getAshasCompleted();
                        ashasFailed += subcenterCount.getAshasFailed();
                        ashasNotStarted += subcenterCount.getAshasNotStarted();
                        ashasRejected += subcenterCount.getAshasRejected();
                        ashasRegistered += subcenterCount.getAshasRegistered();
                    }
                    AggregateCumulativeMA noSubcenterCount = new AggregateCumulativeMA();
                    noSubcenterCount.setAshasRejected(blockCounts.getAshasRejected() - ashasRejected);
                    noSubcenterCount.setAshasNotStarted(blockCounts.getAshasNotStarted() - ashasNotStarted);
                    noSubcenterCount.setAshasRegistered(blockCounts.getAshasRegistered() - ashasRegistered);
                    noSubcenterCount.setAshasFailed(blockCounts.getAshasFailed() - ashasFailed);
                    noSubcenterCount.setAshasCompleted(blockCounts.getAshasCompleted() - ashasCompleted);
                    noSubcenterCount.setAshasStarted(blockCounts.getAshasStarted() - ashasStarted);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId(blockCounts.getAshasRejected() - ashasRejected + blockCounts.getAshasNotStarted() - ashasNotStarted + blockCounts.getAshasRegistered() - ashasRegistered + blockCounts.getAshasFailed() - ashasFailed + blockCounts.getAshasCompleted() - ashasCompleted + blockCounts.getAshasStarted() - ashasStarted);
                    noSubcenterCount.setLocationId((long) -locationId);
                    CumulativeSummery.add(noSubcenterCount);
                }
            }
        }

        return CumulativeSummery;
    }


    private void createHeadersForAggreagateExcels(XSSFWorkbook workbook, AggregateExcelDto gridData) {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);


        String encodingPrefix = "base64,";
        String pngImageURL = header_base64;
        int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array


        final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);

        final CreationHelper helper = workbook.getCreationHelper();
        final Drawing drawing = spreadsheet.createDrawingPatriarch();

        final ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);


        anchor.setCol1(0);
        anchor.setRow1(0);
        anchor.setRow2(4);
        anchor.setCol2(8);
        drawing.createPicture(anchor, pictureIndex);


        spreadsheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 7));

        rowid = rowid + 3;
        XSSFRow row = spreadsheet.createRow(rowid++);
        XSSFCellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(5);
        Cell cell4 = row.createCell(6);

        CellRangeAddress range1 = new CellRangeAddress(4, 5, 0, 0);
        cleanBeforeMergeOnValidCells(spreadsheet, range1, style);
        spreadsheet.addMergedRegion(range1);
        CellRangeAddress range2 = new CellRangeAddress(4, 5, 1, 4);
        cleanBeforeMergeOnValidCells(spreadsheet, range2, style);
        spreadsheet.addMergedRegion(range2);
        CellRangeAddress range3 = new CellRangeAddress(4, 5, 5, 5);
        cleanBeforeMergeOnValidCells(spreadsheet, range3, style);
        spreadsheet.addMergedRegion(range3);
        CellRangeAddress range4 = new CellRangeAddress(4, 5, 6, 7);
        cleanBeforeMergeOnValidCells(spreadsheet, range4, style);
        spreadsheet.addMergedRegion(range4);
        XSSFRow row1 = spreadsheet.createRow(++rowid);
        Cell cell5 = row1.createCell(0);
        Cell cell6 = row1.createCell(1);
        Cell cell7 = row1.createCell(3);
        Cell cell8 = row1.createCell(4);
        Cell cell9 = row1.createCell(6);
        Cell cell10 = row1.createCell(7);
        cell1.setCellValue("Report:");
        cell2.setCellValue(gridData.getReportName());

        cell3.setCellValue("Period:");
        cell4.setCellValue(gridData.getTimePeriod());

        cell5.setCellValue("State:");
        cell6.setCellValue(gridData.getStateName());

        cell7.setCellValue("District:");
        cell8.setCellValue(gridData.getDistrictName());

        cell9.setCellValue("Block:");
        cell10.setCellValue(gridData.getBlockName());

        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);

        CellRangeAddress range5 = new CellRangeAddress(6, 6, 1, 2);
        cleanBeforeMergeOnValidCells(spreadsheet, range5, style);
        spreadsheet.addMergedRegion(range5);
        CellRangeAddress range6 = new CellRangeAddress(6, 6, 4, 5);
        cleanBeforeMergeOnValidCells(spreadsheet, range6, style);
        spreadsheet.addMergedRegion(range6);
        CellRangeAddress range7 = new CellRangeAddress(6, 6, 7, 7);
        cleanBeforeMergeOnValidCells(spreadsheet, range7, style);
        spreadsheet.addMergedRegion(range7);

        XSSFRow dateRow = spreadsheet.createRow(7);
        Cell cellA = dateRow.createCell(0);
        cellA.setCellValue("Report Generated on:");
        cellA.setCellStyle(style);
        Cell cellB = dateRow.createCell(3);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int DateValue = calendar.get(Calendar.DATE);
        int DateYear = (calendar.get(Calendar.YEAR));
        String DateString;
        if (DateValue < 10) {
            DateString = "0" + String.valueOf(DateValue);
        } else {
            DateString = String.valueOf(DateValue);
        }
        String MonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        String YearString = String.valueOf(DateYear);

        cellB.setCellValue(DateString + " " + MonthString + " " + YearString);
        cellB.setCellStyle(style);
        CellRangeAddress dateRange = new CellRangeAddress(7, 7, 0, 2);
        cleanBeforeMergeOnValidCells(spreadsheet, dateRange, style);
        spreadsheet.addMergedRegion(dateRange);

    }

    @Override
    public void createSpecificAggreagateExcel(XSSFWorkbook workbook, AggregateExcelDto gridData) {


        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.protectSheet("123");

        XSSFCellStyle backgroundStyle = workbook.createCellStyle();
        XSSFCellStyle backgroundStyle1 = workbook.createCellStyle();
        XSSFCellStyle backgroundStyle2 = workbook.createCellStyle();
        XSSFCellStyle backgroundStyle3 = workbook.createCellStyle();

        backgroundStyle1.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle1.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
        backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle1.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle1.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle1.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle1.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle1.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle1.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle1.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle1.setTopBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle1.setWrapText(true);
        backgroundStyle1.setLocked(false);

        backgroundStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(243, 243, 243)));
        backgroundStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle2.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle2.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle2.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle2.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle2.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle2.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle2.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle2.setTopBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle2.setWrapText(true);
        backgroundStyle2.setLocked(false);

        backgroundStyle3.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle3.setFillForegroundColor(new XSSFColor(new java.awt.Color(233, 233, 233)));
        backgroundStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle3.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle3.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle3.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle3.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle3.setTopBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle3.setWrapText(true);
        backgroundStyle3.setLocked(false);

        backgroundStyle.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle.setTopBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setWrapText(true);


        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        backgroundStyle.setFont(font);

        Font font1 = workbook.createFont();
        font1.setFontName(HSSFFont.FONT_ARIAL);
        backgroundStyle1.setFont(font1);
        backgroundStyle2.setFont(font1);

        Font font2 = workbook.createFont();
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);

        XSSFCellStyle style = workbook.createCellStyle();//Create style
        style.setFont(font2);//set it to bold
        style.setWrapText(true);
        backgroundStyle3.setFont(font2);

        spreadsheet.setColumnWidth(0, 4000);

        for (int i = 1; i < 15; i++) {
            spreadsheet.setColumnWidth(i, 6000);
        }

        XSSFRow row;
        int rowid = 8;

        if (gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix") || gridData.getReportName().equalsIgnoreCase("Kilkari Beneficiary Completion")
        || gridData.getReportName().equalsIgnoreCase("Kilkari Usage") || gridData.getReportName().equalsIgnoreCase("Kilkari Call")) {
            for (int i = 1; i < 15; i++) {
                spreadsheet.setColumnWidth(i, 10000);
            }
        }

        if (gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix for only successful calls")) {
            row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue("Kilkari Pregnancy Content Data");
            cell.setCellStyle(style);
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid - 1, rowid - 1, 0, gridData.getColumnHeaders().size()));
        }

        if (gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")) {
            row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue("Beneficiary Count");
            cell.setCellStyle(style);
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid - 1, rowid - 1, 0, gridData.getColumnHeaders1().size()));
        }

        row = spreadsheet.createRow(rowid++);
        row.setHeight((short) 1100);
        int colid = 0;
        int tabrow = 0;
        Cell sno = row.createCell(colid++);
        sno.setCellValue("S.No");
        sno.setCellStyle(backgroundStyle);
        Integer index =0;
        Map<String, List<String>> headerComment = getHeaderComment();
        List<String>comments = headerComment.get(gridData.getReportName());

        for (String header : gridData.getColumnHeaders()) {
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(header);
            cell1.setCellStyle(backgroundStyle);
            if(comments != null && comments.size()!=0 && comments.get(index)!=null) {
                CreationHelper creationHelper = workbook.getCreationHelper();
                Drawing drawing = spreadsheet.createDrawingPatriarch();
//            ClientAnchor clientAnchor = drawing.createAnchor(0, 0, 0, 0, 0, 7, 12, 17);
                ClientAnchor anchor = creationHelper.createClientAnchor();
                anchor.setCol1(cell1.getColumnIndex());
                anchor.setCol2(cell1.getColumnIndex() + 3);
                anchor.setRow1(row.getRowNum());
                anchor.setRow2(row.getRowNum() + 1);
                Comment comment = (Comment) drawing.createCellComment(anchor);
                RichTextString richTextString = creationHelper.createRichTextString(comments.get(index));
                comment.setString(richTextString);
                cell1.setCellComment(comment);
                index++;
            }
        }

        for (ArrayList<String> rowData : gridData.getReportData()) {
            row = spreadsheet.createRow(rowid++);
            colid = 0;
            Cell SNrow = row.createCell(colid++);
            if ((gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content") || gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix")) && tabrow + 1 == gridData.getReportData().size()) {
                SNrow.setCellValue("");
                SNrow.setCellStyle(backgroundStyle3);
            } else {
                SNrow.setCellValue(tabrow + 1);
                if (tabrow % 2 == 0) {
                    SNrow.setCellStyle(backgroundStyle1);
                } else {
                    SNrow.setCellStyle(backgroundStyle2);
                }
            }


            for (String cellData : rowData) {
                Cell cell1 = row.createCell(colid++);
                if (colid == 2 || cellData.equalsIgnoreCase("N/A") || (gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content") && colid == 3)) {
                    cell1.setCellValue(cellData);
                } else {
                    try {
                        cell1.setCellValue(cellData);
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); //prints error
                        logger.error("Error while parsing double ", e);
                    }
                }

                if (tabrow % 2 == 0) {
                    if ((gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content") || gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix")) && tabrow + 1 == gridData.getReportData().size()) {
                        cell1.setCellStyle(backgroundStyle3);
                    } else {
                        cell1.setCellStyle(backgroundStyle1);
                    }
                } else {
                    if ((gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content") || gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix")) && tabrow + 1 == gridData.getReportData().size()) {
                        cell1.setCellStyle(backgroundStyle3);
                    } else {
                        cell1.setCellStyle(backgroundStyle2);
                    }
                }

            }
            tabrow++;
        }

        row = spreadsheet.createRow(rowid++);
        colid = 0;
        if (!(gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content") || gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix") || gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise"))) {
            Cell SNofooot = row.createCell(colid++);
            SNofooot.setCellValue("");
            SNofooot.setCellStyle(backgroundStyle3);
        }

        for (String footer : gridData.getColunmFooters()) {
            if(footer == null){
                continue;
            }
            Cell cell1 = row.createCell(colid++);
            if (colid == 2 || footer.equalsIgnoreCase("N/A")) {
                cell1.setCellValue(footer);
            } else {
                NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
                format.setMaximumFractionDigits(2);
                double value = parseDouble(footer);
                cell1.setCellValue(format.format(value));
            }
            cell1.setCellStyle(backgroundStyle3);
        }

        if (gridData.getReportName().equalsIgnoreCase("kilkari message matrix for only successful calls")) {
            row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue("Kilkari Child Content Data");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid - 1, rowid - 1, 0, gridData.getColumnHeaders1().size()));
        }

        if (gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")) {
            row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue("Beneficiary Percentage");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid - 1, rowid - 1, 0, gridData.getColumnHeaders1().size()));
        }

        row = spreadsheet.createRow(rowid++);
        if (gridData.getReportName().equalsIgnoreCase("kilkari message matrix for only successful calls") || gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")) {
            row.setHeight((short) 1100);
            colid = 0;
            int tabrow1 = 0;
            Cell sno1 = row.createCell(colid++);
            sno1.setCellValue("S.No");
            sno1.setCellStyle(backgroundStyle);
            for (String header : gridData.getColumnHeaders1()) {
                Cell cell1 = row.createCell(colid++);
                cell1.setCellValue(header);
                cell1.setCellStyle(backgroundStyle);

            }

            for (ArrayList<String> rowData : gridData.getReportData1()) {
                row = spreadsheet.createRow(rowid++);
                colid = 0;

                Cell SNrow1 = row.createCell(colid++);
                SNrow1.setCellValue(tabrow1 + 1);
                if (tabrow % 2 == 0) {
                    SNrow1.setCellStyle(backgroundStyle1);
                } else {
                    SNrow1.setCellStyle(backgroundStyle2);
                }


                for (String cellData : rowData) {
                    Cell cell1 = row.createCell(colid++);
//                    if (colid == 2) {
                        cell1.setCellValue(cellData);
//                    } else {
//                        cell1.setCellValue(parseDouble(cellData));
//                    }
                    if (tabrow % 2 == 0) {
                        cell1.setCellStyle(backgroundStyle1);
                    } else {
                        cell1.setCellStyle(backgroundStyle2);
                    }

                }
                tabrow1++;
            }
            row = spreadsheet.createRow(rowid++);
            colid = 0;
            Cell SNofooot = row.createCell(colid++);
            SNofooot.setCellValue("");
            SNofooot.setCellStyle(backgroundStyle3);
            for (String footer : gridData.getColunmFooters1()) {
                Cell cell1 = row.createCell(colid++);
                if (colid == 2 || footer.equalsIgnoreCase("N/A")) {
                    cell1.setCellValue(footer);
                } else {
                    com.ibm.icu.text.NumberFormat format = com.ibm.icu.text.NumberFormat.getNumberInstance(new Locale("en", "in"));
                    format.setMaximumFractionDigits(2);
                    double value = parseDouble(footer);
                    cell1.setCellValue(format.format(value));
                }
                cell1.setCellStyle(backgroundStyle3);
            }

        }
//        if (gridData.getReportName().equalsIgnoreCase("MA Subscriber")) {
//           List rejectedAshas = maSubscriberDao.getRejectedAshas();
//        }



//            if(gridData.getReportName().equalsIgnoreCase("MA Subscriber") ||
//                    gridData.getReportName().equalsIgnoreCase("MA Performance")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Call")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Usage")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Aggregate Beneficiaries")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Beneficiary Completion")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Message Listenership")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")||
//                    gridData.getReportName().equalsIgnoreCase("Kilkari Cumulative Summary")||
//                    gridData.getReportName().equalsIgnoreCase("District-wise Performance of the State for Mobile Academy")){
                spreadsheet.autoSizeColumn(1);
//            }

        createHeadersForAggreagateExcels(workbook, gridData);
    }

    private Map<String,List<String>> getHeaderComment() {


        Map<String,List<String>> map = new HashMap<String, List<String>>();
       map.put("MA Performance",Arrays.asList("Location Name",
               "Total Number of active Asha from beginning",
               "ASHAs who have started the course for the first time in the selected period.",
               "Number of ASHAs who had started the course before the selected period, had accessed the course at least once with one bookmark during the selected period and not completed the course till the end of the selected period ",
               "Doing course more than once",
               "Number of ASHAs who have successfully completed the course for first time, during the selected period and secured pass marks.",
               "Number of ASHAs Started and Completed the Course in the Given time period",
               "Number of ASHAs who had started the course before the selected period and had not accessed the course once during the selected period. The count does NOT include ASHAs who have any successful completion till the end of the selected period." ,
               "Number of ASHAs who have completed the course during the selected period and did not secure passing marks even once till the end of selected period",
               "Total Number of Asha joined during the selected time period",
               "Total Number of asha deactivated or left during the selected time period"));
       map.put("MA Subscriber",Arrays.asList("State","Number of ASHAs who have registered in the MA course prior to the start of the period but have not completed the course.",
               "Number of ASHA Records that have been received from web service from MCTS/RCH during the period",
               "Number of the records that have been rejected",
               "number of ASHAs who have been added/subscribed in MA course for the first time in the selected period",
               "Number of ASHAs who have successfully completed the course for the first time",
               "Number of ASHAs presently Subscribed in the Mobile Academy program but are yet to start or complete the course."
               ));
       map.put("District-wise Performance of the State for Mobile Academy",Arrays.asList("State",
               "Number of ASHAs registered with the program.","Number of ASHA have started the course","% Started (of total registered)",
               "number of ASHAs who have passed the course (first successful completion only)",
               "% Completed (of total registered)","Number of ASHA have not started the course","% Not Started (of total registered)",
               "No of ASHA Failed Course","% Failed (of total registered)"));

       map.put("Course Completion",Arrays.asList("ASHA Name","ASHA MCTS/RCH ID","Mobile Number","State","District","Taluka",
               "Health Block","Health Facility","Health Sub Facility","Village","Date when ASHA records came in to the Mobile Academy system for the first time",
               "ASHA’s Status as received from MCTS/RCH","The date when ASHA’s successfully completed the Mobile Academy course for the first time",
               "Refenrece ID generated and SMS sent from our system"));
            return map;
    }

    public HashMap<Long, MASubscriberDto> getMASubscriberCounts (Integer locationId, String locationType, Date fromDate, Date toDate){

        Date fromDateTemp = fromDate;
        HashMap<Long,MASubscriberDto> countMap = new HashMap<>();

        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("MOBILE_ACADEMY");
            for(State s:states){
                Integer failedCount = 0;
                if(fromDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"MOBILE_ACADEMY"))){
                    fromDateTemp = stateServiceDao.getServiceStartDateForState(s.getStateId(),"MOBILE_ACADEMY");
                }
                MASubscriberDto statePerformance = new MASubscriberDto();
                if(!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"MOBILE_ACADEMY"))) {
                    failedCount = maSubscriberDao.getRejectedAshas(s.getStateId(), locationType, fromDateTemp, toDate);
                }
                statePerformance.setAshasFailed(failedCount);
                countMap.put((long)s.getStateId(),statePerformance);

                fromDateTemp = fromDate;
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                if(fromDateTemp.before(stateServiceDao.getServiceStartDateForState(locationId,"MOBILE_ACADEMY"))){
                    fromDateTemp = stateServiceDao.getServiceStartDateForState(locationId,"MOBILE_ACADEMY");
                }
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                Integer stateCounts3 = maSubscriberDao.getRejectedAshas(locationId,"State",fromDateTemp,toDate);
                Integer failedCount = 0;
                for(District d:districts){
                    MASubscriberDto districtPerformance = new MASubscriberDto();
                    Integer districtCount3 = maSubscriberDao.getRejectedAshas(d.getDistrictId(),locationType,fromDateTemp,toDate);
                    districtPerformance.setAshasFailed(districtCount3);
                    countMap.put((long)d.getDistrictId(),districtPerformance);
                    failedCount+=districtCount3;

                }
                Integer noDistrictCount3 = 0;
                MASubscriberDto noDistrictPerformance = new MASubscriberDto();
                noDistrictCount3= stateCounts3 - failedCount;

                noDistrictPerformance.setAshasFailed(noDistrictCount3);
                countMap.put((long)-locationId,noDistrictPerformance);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    if(fromDateTemp.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"MOBILE_ACADEMY"))){
                        fromDateTemp = stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"MOBILE_ACADEMY");
                    }
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    Integer districtCounts3 = maSubscriberDao.getRejectedAshas(locationId,"District",fromDateTemp,toDate);
                    Integer failedCount = 0;
                    for (Block d : blocks) {
                        MASubscriberDto blockPerformance = new MASubscriberDto();
                        Integer blockCount3 = maSubscriberDao.getRejectedAshas(d.getBlockId(),locationType,fromDateTemp,toDate);

                        blockPerformance.setAshasFailed(blockCount3);
                        countMap.put((long)d.getBlockId(),blockPerformance);
                        failedCount+=blockCount3;
                    }
                    Integer noBlockCount3 = 0;
                    MASubscriberDto noBlockPerformance = new MASubscriberDto();
                    noBlockCount3= districtCounts3 - failedCount;

                    noBlockPerformance.setAshasFailed(noBlockCount3);
                    countMap.put((long)-locationId,noBlockPerformance);
                }
                else {
                    if(fromDateTemp.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"MOBILE_ACADEMY"))){
                        fromDateTemp = stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"MOBILE_ACADEMY");
                    }
                    List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
                    List<HealthSubFacility> subcenters = new ArrayList<>();
                    for(HealthFacility hf :healthFacilities){
                        subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
                    }
                    Integer blockCounts3 = maSubscriberDao.getRejectedAshas(locationId,"block",fromDateTemp,toDate);
                    Integer failedCount = 0;
                    for(HealthSubFacility s: subcenters){
                        MASubscriberDto subcentrePerformance = new MASubscriberDto();
                        Integer subcentreCount3 = maSubscriberDao.getRejectedAshas(s.getHealthSubFacilityId(),locationType,fromDateTemp,toDate);

                        subcentrePerformance.setAshasFailed(subcentreCount3);
                        countMap.put((long)s.getHealthSubFacilityId(),subcentrePerformance);
                        failedCount+=subcentreCount3;
                    }
                    Integer noSubcentreCount3 = 0;
                    MASubscriberDto noSubcentrePerformance = new MASubscriberDto();
                    noSubcentreCount3= blockCounts3 - failedCount;

                    noSubcentrePerformance.setAshasFailed(noSubcentreCount3);
                    countMap.put((long)-locationId,noSubcentrePerformance);
                }
            }
        }
        return countMap;
    }

    @Override
    public void createSpecificAggreagatePdf(PDDocument document, AggregateExcelDto gridData) throws IOException {
        PDFont font = PDType1Font.TIMES_BOLD;
        PDFont font1 = PDType1Font.TIMES_ROMAN;

        int fontSize = 16;
        int fontSize1 = 12;


//            //Creating a blank page
//
        PDPage page = new PDPage();
//            //Adding the blank page to the document
        document.addPage(page);
        PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);
        PDRectangle mediaBox = page.getMediaBox();

        String encodingPrefix = "base64,";
        String pngImageURL1 = national_base64;
        int contentStartIndex = pngImageURL1.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData1 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL1.substring(contentStartIndex));//workbook.addPicture can use this byte array

        ByteArrayInputStream bis1 = new ByteArrayInputStream(imageData1);



        Image image1 = new Image(ImageIO.read(bis1));
        bis1.close();

        float startX = (mediaBox.getWidth() - 180) / 2;
        float imageWidth = 180;
        image1 = image1.scaleByWidth(imageWidth);
        image1.draw(document, contents, startX+20, 720);


        //second image
        String pngImageURL2 = women_base64;
        contentStartIndex = pngImageURL2.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData2 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL2.substring(contentStartIndex));//workbook.addPicture can use this byte array

        ByteArrayInputStream bis2 = new ByteArrayInputStream(imageData2);



        Image image2 = new Image(ImageIO.read(bis2));
        bis2.close();

        startX = (mediaBox.getWidth() - 180) / 2;
        imageWidth = 180;
        image2 = image2.scaleByWidth(imageWidth);
        image2.draw(document, contents, startX, 450);

        //third image
        String pngImageURL3 = digital_base64;
        contentStartIndex = pngImageURL3.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData3 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL3.substring(contentStartIndex));//workbook.addPicture can use this byte array

        ByteArrayInputStream bis3 = new ByteArrayInputStream(imageData3);



        Image image3 = new Image(ImageIO.read(bis3));
        bis3.close();

        startX = (mediaBox.getWidth() - 180) / 2;
        imageWidth = 180;
        image3 = image3.scaleByWidth(imageWidth);
        image3.draw(document, contents, startX, 300);

//        contents.beginText();
//        contents.setFont(font, fontSize1);
//        //Setting the leading
//        contents.setLeading(14.5f);
//
//        //Setting the position for the line
//        contents.newLineAtOffset(200, 90);
//
//        //Adding text in the form of string
//        contents.showText("Management and Information System");
//        //contents.newLine();
//        contents.newLineAtOffset(25, 20);
//        contents.setFont(font, fontSize1);
//        contents.showText("Mobile Academy and Kilkari");
//        //contents.newLine();
//        contents.setFont(font, fontSize);
//        contents.newLineAtOffset(-50, 30);
//        contents.showText("Ministry of Health and Family Welfare");
//        contents.newLine();
//        contents.endText();
        String title = "Ministry of Health and Family Welfare";
        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(contents, title, font, fontSize, x, 150, java.awt.Color.BLACK);

         title = "Mobile Academy and Kilkari";
         titleWidth = font.getStringWidth(title) / 1000 * fontSize1;
         x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(contents, title, font, fontSize1, x, 120, java.awt.Color.BLACK);

         title = "Management and Information System";
         titleWidth = font.getStringWidth(title) / 1000 * fontSize1;
         x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(contents, title, font, fontSize1, x, 100, java.awt.Color.BLACK);



        contents.close();

        System.out.println("Image inserted");


        page = new PDPage();
        //Adding the blank page to the document
        document.addPage(page);



        float cellWidth = 0;
        float tableMargin = 10;
        float tableFont = 7;
        //contents.close();

        if (gridData.getReportName().equalsIgnoreCase("District-wise Performance of the State for Mobile Academy") || gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")) {
            cellWidth = 10f;
            tableMargin = 60;
            tableFont = 7;
        } else if (gridData.getReportName().equalsIgnoreCase("MA Subscriber")) {
            cellWidth = 15f;
            tableMargin = 75;
        }  else if (gridData.getReportName().equalsIgnoreCase("Kilkari Beneficiary Completion")) {
            cellWidth = 20f;
            tableMargin = 75;
        } else if (gridData.getReportName().equalsIgnoreCase("MA Performance") || gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix")) {
            cellWidth = 18f;
            tableMargin = 60;
        } else if (gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content") || gridData.getReportName().equalsIgnoreCase("Kilkari Cumulative Summary") || gridData.getReportName().equalsIgnoreCase("kilkari message matrix for only successful calls")) {
            cellWidth = 16f;
            tableMargin = 80;
        } else if (gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")) {
            cellWidth = 22f;
            tableMargin = 80;
        } else if (gridData.getReportName().equalsIgnoreCase("Kilkari Subscriber") || gridData.getReportName().equalsIgnoreCase("Kilkari Message Listenership")) {
            cellWidth = 12f;
            tableMargin = 40;
        } else if (gridData.getReportName().equalsIgnoreCase("Kilkari Usage")) {
            cellWidth = 20f;
            tableMargin = 40;
        } else if (gridData.getReportName().equalsIgnoreCase("Kilkari Call")) {
            cellWidth = 10f;
            tableMargin = 30;
        } else if (gridData.getReportName().equalsIgnoreCase("Kilkari Aggregate Beneficiaries")) {
            cellWidth = 9f;
            tableMargin = 30;
        }

        //System.out.println("this is the cellwidth " + gridData.getReportName());
        PDPageContentStream stream = new PDPageContentStream(document, page);

         title = gridData.getReportName() + " Report";
         titleWidth = font.getStringWidth(title) / 1000 * fontSize;
         x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize, x, 700, java.awt.Color.BLACK);

        //stream.newLine();
        title = "State : " + gridData.getStateName();
        titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
        x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 650, java.awt.Color.BLACK);
        title = "District : " + gridData.getDistrictName();
        titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
        x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 630, java.awt.Color.BLACK);
        title = "Health Block : " + gridData.getBlockName();
        titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
        x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 610, java.awt.Color.BLACK);
        title = "Period : " + gridData.getTimePeriod();
        titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
        x = (page.getMediaBox().getWidth() - titleWidth) / 2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 590, java.awt.Color.BLACK);

        float margin = 10;


        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

        // Initialize table

        if (gridData.getReportName().equalsIgnoreCase("kilkari message matrix for only successful calls")) {
            title = "Kilkari Pregnancy Content Data";
            titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
            x = (page.getMediaBox().getWidth() - titleWidth) / 2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, 540, java.awt.Color.BLACK);

        }

        if (gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")) {
            title = "Beneficiary Count";
            titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
            x = (page.getMediaBox().getWidth() - titleWidth) / 2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, 540, java.awt.Color.BLACK);

        }
        stream.close();

        float tableWidth = page.getMediaBox().getWidth() - (2 * 50);

        boolean drawContent = true;
        float yStart = 500;
        float bottomMargin = 50;
        BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, tableMargin, document, page, true,
                drawContent);



        // Create Header row
        be.quodlibet.boxable.Row<PDPage> headerRow = table.createRow(15f);
        be.quodlibet.boxable.Cell<PDPage> cell = headerRow.createCell((8), "S No", be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
        for (int i = 0; i < gridData.getColumnHeaders().size(); i++) {
            cell = headerRow.createCell(cellWidth, gridData.getColumnHeaders().get(i), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
            cell.setFontSize(tableFont);
            cell.setFont(PDType1Font.TIMES_BOLD);

        }

        // Create 2 column row
        be.quodlibet.boxable.Row<PDPage> row;


        for (int i = 0; i < gridData.getReportData().size(); i++) {
            row = table.createRow(10f);
            cell = row.createCell((8), Integer.toString(i + 1), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
            for (int j = 0; j < gridData.getReportData().get(i).size(); j++) {
                cell = row.createCell((cellWidth), gridData.getReportData().get(i).get(j), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
                cell.setFontSize(tableFont);
                if(!gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")&&!gridData.getReportName().equalsIgnoreCase("kilkari message matrix for only successful calls"))
                if(i ==gridData.getReportData().size()-1){
                    cell.setFont(PDType1Font.TIMES_BOLD);
                }

            }


        }

        table.draw();

        if (gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")) {
            page = new PDPage();
            //Adding the blank page to the document
            document.addPage(page);
            stream = new PDPageContentStream(document, page);
            yStart = yStartNewPage;
            tableWidth = page.getMediaBox().getWidth() - (2 * 70);
            tableMargin = 45;
            cellWidth=13f;
            title = "Beneficiary Percentage";
            titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
            x = (page.getMediaBox().getWidth() - titleWidth) / 2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, yStart-20, java.awt.Color.BLACK);
            stream.close();
            table = new BaseTable(yStart - 60, yStartNewPage, bottomMargin, tableWidth, tableMargin, document, page, true,
                    drawContent);

            // Create Header row
            headerRow = table.createRow(15f);
            cell = headerRow.createCell(8f, "S No", be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
            for (int i = 0; i < gridData.getColumnHeaders1().size(); i++) {
                cell = headerRow.createCell(cellWidth, gridData.getColumnHeaders1().get(i), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
                cell.setFontSize(tableFont);
                cell.setFont(PDType1Font.TIMES_BOLD);
//            cell.setFillColor(java.awt.Color.BLACK);
//            cell.setTextColor(java.awt.Color.WHITE);
            }


            for (int i = 0; i < gridData.getReportData1().size(); i++) {
                row = table.createRow(10f);
                cell = row.createCell(8f, Integer.toString(i + 1), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));

                for (int j = 0; j < gridData.getReportData1().get(i).size(); j++) {
                    cell = row.createCell((cellWidth), gridData.getReportData1().get(i).get(j), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
                    cell.setFontSize(tableFont);

                }


            }
            table.draw();


        }

        if (gridData.getReportName().equalsIgnoreCase("kilkari message matrix for only successful calls")) {
            page = new PDPage();
            cellWidth = 15f;
            tableMargin = 90;
            //Adding the blank page to the document
            document.addPage(page);
            stream = new PDPageContentStream(document, page);
            yStart = yStartNewPage;
            tableWidth = page.getMediaBox().getWidth() - (2 * 50);

            title = "Kilkari Child Content Data";
            titleWidth = font1.getStringWidth(title) / 1000 * fontSize1;
            x = (page.getMediaBox().getWidth() - titleWidth) / 2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, yStart-20, java.awt.Color.BLACK);
            stream.close();
            table = new BaseTable(yStart - 60, yStartNewPage, bottomMargin, tableWidth, tableMargin, document, page, true,
                    drawContent);

            // Create Header row
            headerRow = table.createRow(15f);
            cell = headerRow.createCell((8), "S No", be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
            for (int i = 0; i < gridData.getColumnHeaders1().size(); i++) {
                cell = headerRow.createCell(cellWidth, gridData.getColumnHeaders1().get(i), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
                cell.setFontSize(tableFont);
                cell.setFont(PDType1Font.TIMES_BOLD);
//            cell.setFillColor(java.awt.Color.BLACK);
//            cell.setTextColor(java.awt.Color.WHITE);
            }


            for (int i = 0; i < gridData.getReportData1().size(); i++) {
                row = table.createRow(10f);
                cell = row.createCell((8), Integer.toString(i + 1), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));

                for (int j = 0; j < gridData.getReportData1().get(i).size(); j++) {
                    cell = row.createCell((cellWidth), gridData.getReportData1().get(i).get(j), be.quodlibet.boxable.HorizontalAlignment.get("center"), be.quodlibet.boxable.VerticalAlignment.get("middle"));
                    cell.setFontSize(tableFont);


                }


            }
            table.draw();


        }

        for(int i=0;i<document.getNumberOfPages();i++){
            PDPage docPage= document.getPage(i);
            PDPageContentStream footerContent = new PDPageContentStream(document, docPage,AppendMode.APPEND, true, true);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int DateValue = calendar.get(Calendar.DATE);
            int DateYear = (calendar.get(Calendar.YEAR));
            String DateString;
            if (DateValue < 10) {
                DateString = "0" + String.valueOf(DateValue);
            } else {
                DateString = String.valueOf(DateValue);
            }
            String MonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
            String YearString = String.valueOf(DateYear);


            String dateFiled = "Date Filed : " + DateString + " " + MonthString + " " + YearString;
            fontSize=8;
            float dateWidth = font.getStringWidth(dateFiled) / 1000 * fontSize;
            x = page.getMediaBox().getWidth()-dateWidth-10;
            be.quodlibet.boxable.utils.PDStreamUtils.write(footerContent, dateFiled, font1, fontSize, x, 20, java.awt.Color.BLACK);
            footerContent.close();
        }


    }

    private void cleanBeforeMergeOnValidCells(XSSFSheet sheet, CellRangeAddress region, XSSFCellStyle cellStyle) {
        for (int rowNum = region.getFirstRow(); rowNum <= region.getLastRow(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            for (int colNum = region.getFirstColumn(); colNum <= region.getLastColumn(); colNum++) {
                XSSFCell currentCell = row.getCell(colNum);
                if (currentCell == null) {
                    currentCell = row.createCell(colNum);

                }

                currentCell.setCellStyle(cellStyle);

            }
        }


    }
//    private static void addImage(){
//
//
//        float startY = mediaBox.getHeight() - marginTop - titleHeight;
//
//    }


    public static void main(String[] args) {
        File imageFile = new File(System.getProperty("user.home") +"/NMS-Reporting/nms-mis/NMSReportingSuite/src/main/java/com/beehyv/nmsreporting/business/impl/images/digital_logo.png");
        System.out.println(imageFile.getAbsolutePath());
    }
}


