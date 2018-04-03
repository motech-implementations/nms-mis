package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AggregateReportsService;

import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.AggregateExcelDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.*;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import static java.lang.Integer.parseInt;


/**
 * Created by beehyv on 19/9/17.
 */
@Service("aggregateReportsService")
@Transactional
public class AggregateReportsServiceImpl implements AggregateReportsService {

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
    private SubcenterDao subcenterDao;

    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateCumulativeMADao aggregateCumulativeMADao;

    @Autowired
    private AggregateCumulativekilkariDao aggregateCumulativekilkariDao;


    @Override
    public List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId,String locationType,Date toDate){
        List<AggregateCumulativeMA> CumulativeSummery = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("M");
            for(State s:states){
                CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getStateId(),locationType,toDate));
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeMA stateCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"State",toDate);
                Integer ashasRegistered = 0;
                Integer ashasStarted = 0;
                Integer ashasNotStarted = 0;
                Integer ashasCompleted = 0;
                Integer ashasFailed = 0;
                Integer ashasRejected = 0;
                for(District d:districts){
                    AggregateCumulativeMA distrcitCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,toDate);
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,toDate));
                    ashasStarted+=distrcitCount.getAshasStarted();
                    ashasCompleted+=distrcitCount.getAshasCompleted();
                    ashasFailed+=distrcitCount.getAshasFailed();
                    ashasNotStarted+=distrcitCount.getAshasNotStarted();
                    ashasRejected+=distrcitCount.getAshasRejected();
                    ashasRegistered+=distrcitCount.getAshasRegistered();
                }
                AggregateCumulativeMA noDistrictCount = new AggregateCumulativeMA();
                noDistrictCount.setAshasRejected(stateCounts.getAshasRejected()-ashasRejected);
                noDistrictCount.setAshasNotStarted(stateCounts.getAshasNotStarted()-ashasNotStarted);
                noDistrictCount.setAshasRegistered(stateCounts.getAshasRegistered()-ashasRegistered);
                noDistrictCount.setAshasFailed(stateCounts.getAshasFailed()-ashasFailed);
                noDistrictCount.setAshasCompleted(stateCounts.getAshasCompleted()-ashasCompleted);
                noDistrictCount.setAshasStarted(stateCounts.getAshasStarted()-ashasStarted);
                noDistrictCount.setLocationType("DifferenceState");
                noDistrictCount.setId(stateCounts.getAshasRejected()-ashasRejected+stateCounts.getAshasNotStarted()-ashasNotStarted+stateCounts.getAshasRegistered()-ashasRegistered+stateCounts.getAshasFailed()-ashasFailed+stateCounts.getAshasCompleted()-ashasCompleted+stateCounts.getAshasStarted()-ashasStarted);
                noDistrictCount.setLocationId((long)-locationId);
                CumulativeSummery.add(noDistrictCount);
            }
            else{
                if(locationType.equalsIgnoreCase("Block")) {
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeMA districtCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"District",toDate);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for (Block d : blocks) {
                        AggregateCumulativeMA blockCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(), locationType,toDate));
                        ashasStarted+=blockCount.getAshasStarted();
                        ashasCompleted+=blockCount.getAshasCompleted();
                        ashasFailed+=blockCount.getAshasFailed();
                        ashasNotStarted+=blockCount.getAshasNotStarted();
                        ashasRejected+=blockCount.getAshasRejected();
                        ashasRegistered+=blockCount.getAshasRegistered();
                    }
                    AggregateCumulativeMA noBlockCount = new AggregateCumulativeMA();
                    noBlockCount.setAshasRejected(districtCounts.getAshasRejected()-ashasRejected);
                    noBlockCount.setAshasNotStarted(districtCounts.getAshasNotStarted()-ashasNotStarted);
                    noBlockCount.setAshasRegistered(districtCounts.getAshasRegistered()-ashasRegistered);
                    noBlockCount.setAshasFailed(districtCounts.getAshasFailed()-ashasFailed);
                    noBlockCount.setAshasCompleted(districtCounts.getAshasCompleted()-ashasCompleted);
                    noBlockCount.setAshasStarted(districtCounts.getAshasStarted()-ashasStarted);
                    noBlockCount.setLocationType("DifferenceDistrict");
                    noBlockCount.setId(districtCounts.getAshasRejected()-ashasRejected+districtCounts.getAshasNotStarted()-ashasNotStarted+districtCounts.getAshasRegistered()-ashasRegistered+districtCounts.getAshasFailed()-ashasFailed+districtCounts.getAshasCompleted()-ashasCompleted+districtCounts.getAshasStarted()-ashasStarted);
                    noBlockCount.setLocationId((long)-locationId);
                    CumulativeSummery.add(noBlockCount);
                }
                else {
                    List<Subcenter> subcenters = subcenterDao.getSubcentersOfBlock(locationId);
                    AggregateCumulativeMA blockCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"block",toDate);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for(Subcenter s: subcenters){
                        AggregateCumulativeMA subcenterCount = aggregateCumulativeMADao.getMACumulativeSummery(s.getSubcenterId(),locationType,toDate);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getSubcenterId(), locationType,toDate));
                        ashasStarted+=subcenterCount.getAshasStarted();
                        ashasCompleted+=subcenterCount.getAshasCompleted();
                        ashasFailed+=subcenterCount.getAshasFailed();
                        ashasNotStarted+=subcenterCount.getAshasNotStarted();
                        ashasRejected+=subcenterCount.getAshasRejected();
                        ashasRegistered+=subcenterCount.getAshasRegistered();
                    }
                    AggregateCumulativeMA noSubcenterCount = new AggregateCumulativeMA();
                    noSubcenterCount.setAshasRejected(blockCounts.getAshasRejected()-ashasRejected);
                    noSubcenterCount.setAshasNotStarted(blockCounts.getAshasNotStarted()-ashasNotStarted);
                    noSubcenterCount.setAshasRegistered(blockCounts.getAshasRegistered()-ashasRegistered);
                    noSubcenterCount.setAshasFailed(blockCounts.getAshasFailed()-ashasFailed);
                    noSubcenterCount.setAshasCompleted(blockCounts.getAshasCompleted()-ashasCompleted);
                    noSubcenterCount.setAshasStarted(blockCounts.getAshasStarted()-ashasStarted);
                    noSubcenterCount.setLocationType("DifferenceBlock");
                    noSubcenterCount.setId(blockCounts.getAshasRejected()-ashasRejected+blockCounts.getAshasNotStarted()-ashasNotStarted+blockCounts.getAshasRegistered()-ashasRegistered+blockCounts.getAshasFailed()-ashasFailed+blockCounts.getAshasCompleted()-ashasCompleted+blockCounts.getAshasStarted()-ashasStarted);
                    noSubcenterCount.setLocationId((long)-locationId);
                    CumulativeSummery.add(noSubcenterCount);
                }
            }
        }

        return CumulativeSummery;
    };


    private void createHeadersForAggreagateExcels(XSSFWorkbook workbook, AggregateExcelDto gridData)  {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);


        String encodingPrefix = "base64,";
        String pngImageURL = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABQ0AAABoCAIAAADU50GAAAAAiHpUWHRSYXcgcHJvZmlsZSB0eXBlIGV4aWYAAHjaVY7RDcNACEP/maIjcMAZGKdKE6kbdPyCLlWa9wGWhWxo/7wPejSDhWx6IAEuLC3lWSJ4ocxDePSuuTi3jlJy2aSyBDKc7Tq00/8xFYHDzR0TGzapdNlVVGp2UKdyv5F/rS+eq/buI+7h9AVIiixSCQcvRwAACghpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+Cjx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IlhNUCBDb3JlIDQuNC4wLUV4aXYyIj4KIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIKICAgIHhtbG5zOmV4aWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vZXhpZi8xLjAvIgogICAgeG1sbnM6dGlmZj0iaHR0cDovL25zLmFkb2JlLmNvbS90aWZmLzEuMC8iCiAgIGV4aWY6UGl4ZWxYRGltZW5zaW9uPSIxMjkzIgogICBleGlmOlBpeGVsWURpbWVuc2lvbj0iMTA0IgogICB0aWZmOkltYWdlV2lkdGg9IjEyOTMiCiAgIHRpZmY6SW1hZ2VIZWlnaHQ9IjEwNCIKICAgdGlmZjpPcmllbnRhdGlvbj0iMSIvPgogPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg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";
        int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array



        final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);

        final CreationHelper helper = workbook.getCreationHelper();
        final Drawing drawing = spreadsheet.createDrawingPatriarch();

        final ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );


        anchor.setCol1( 0 );
        anchor.setRow1( 0 );
        anchor.setRow2( 4 );
        anchor.setCol2( 9 );
        drawing.createPicture( anchor, pictureIndex );


        spreadsheet.addMergedRegion(new CellRangeAddress(0,3,0,8));

        rowid = rowid+3;
        XSSFRow row=spreadsheet.createRow(rowid++);
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

        Cell cell1=row.createCell(0);
        Cell cell2=row.createCell(1);
        Cell cell3=row.createCell(5);
        Cell cell4=row.createCell(6);

        CellRangeAddress range1 = new CellRangeAddress(4,5,0,0);
            cleanBeforeMergeOnValidCells(spreadsheet,range1,style );
            spreadsheet.addMergedRegion(range1);
        CellRangeAddress range2 = new CellRangeAddress(4,5,1,4);
            cleanBeforeMergeOnValidCells(spreadsheet,range2,style );
            spreadsheet.addMergedRegion(range2);
        CellRangeAddress range3 = new CellRangeAddress(4,5,5,5);
            cleanBeforeMergeOnValidCells(spreadsheet,range3,style );
            spreadsheet.addMergedRegion(range3);
        CellRangeAddress range4 = new CellRangeAddress(4,5,6,8);
            cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
            spreadsheet.addMergedRegion(range4);
        XSSFRow row1=spreadsheet.createRow(++rowid);
        Cell cell5=row1.createCell(0);
        Cell cell6=row1.createCell(1);
        Cell cell7=row1.createCell(3);
        Cell cell8=row1.createCell(4);
        Cell cell9=row1.createCell(6);
        Cell cell10=row1.createCell(7);
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

        CellRangeAddress range5 = new CellRangeAddress(6,6,1,2);
            cleanBeforeMergeOnValidCells(spreadsheet,range5,style );
            spreadsheet.addMergedRegion(range5);
        CellRangeAddress range6 = new CellRangeAddress(6,6,4,5);
            cleanBeforeMergeOnValidCells(spreadsheet,range6,style );
            spreadsheet.addMergedRegion(range6);
        CellRangeAddress range7 = new CellRangeAddress(6,6,7,8);
            cleanBeforeMergeOnValidCells(spreadsheet,range7,style );
            spreadsheet.addMergedRegion(range7);




    }

    @Override
    public void createSpecificAggreagateExcel(XSSFWorkbook workbook, AggregateExcelDto gridData)  {



        XSSFSheet spreadsheet = workbook.getSheetAt(0);

        CellStyle backgroundStyle = workbook.createCellStyle();
        CellStyle backgroundStyle1 = workbook.createCellStyle();

        backgroundStyle1.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle1.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        backgroundStyle1.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle1.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        backgroundStyle1.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle1.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        backgroundStyle1.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle1.setTopBorderColor(IndexedColors.WHITE.getIndex());

        backgroundStyle.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setRightBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle.setTopBorderColor(IndexedColors.WHITE.getIndex());
        backgroundStyle.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        backgroundStyle.setFont(font);

        Font font1 = workbook.createFont();
        font1.setFontName(HSSFFont.FONT_ARIAL);
        backgroundStyle1.setFont(font1);

        Font font2 = workbook.createFont();
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);


        for(int i =0;i<15;i++){
        spreadsheet.setColumnWidth(i, 4000);}


        XSSFRow row;
        int rowid =8;

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue( "Kilkari Maternal Health Content Data");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:F9"));}

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue( "Beneficiary Count ");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:H9"));}

        row = spreadsheet.createRow(rowid++);
        row.setHeight((short)1100);
        int colid =0;
        int tabrow =0;
        for (String header : gridData.getColumnHeaders()) {
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(header);
            cell1.setCellStyle(backgroundStyle);
        }

        for (ArrayList<String> rowData : gridData.getReportData()) {
            row = spreadsheet.createRow(rowid++);
            colid =0;
            for (String cellData : rowData) {
                Cell cell1 = row.createCell(colid++);
                cell1.setCellValue(cellData);

                if(tabrow %2 ==0){
                    backgroundStyle1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                    backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    }
                else {
                    backgroundStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    }

                cell1.setCellStyle(backgroundStyle1);
                CellUtil.setAlignment(cell1, workbook, CellStyle.ALIGN_CENTER);
            }
            tabrow++;
        }

        row = spreadsheet.createRow(rowid++);
        colid =0;
        for (String footer : gridData.getColunmFooters()) {
            backgroundStyle1.setFont(font2);
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(footer);
            if(tabrow %2 ==0){
                backgroundStyle1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);}
            else {
                backgroundStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);}
            cell1.setCellStyle(backgroundStyle1);
            CellUtil.setAlignment(cell1, workbook, CellStyle.ALIGN_CENTER);
        }

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix"))
        {row = spreadsheet.createRow(rowid++);
        Cell cell = row.createCell(0);
        cell.setCellValue( "Kilkari Child Health Content Data");
        CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
           spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A17:F17"));}

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue( "Beneficiary Percentage");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A19:H19"));}

        row = spreadsheet.createRow(rowid++);
        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")||gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener")){
        row.setHeight((short)1100);}
         colid =0;
        int tabrow1 =0;
        for (String header : gridData.getColumnHeaders1()) {
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(header);
            cell1.setCellStyle(backgroundStyle);

        }

        for (ArrayList<String> rowData : gridData.getReportData1()) {
            row = spreadsheet.createRow(rowid++);
            colid =0;
            for (String cellData : rowData) {
                Cell cell1 = row.createCell(colid++);
                cell1.setCellValue(cellData);
                if(tabrow1 %2 ==0){
                    backgroundStyle1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                    backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);}
                else {
                    backgroundStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    backgroundStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);}

                cell1.setCellStyle(backgroundStyle1);
                CellUtil.setAlignment(cell1, workbook, CellStyle.ALIGN_CENTER);
            }
            tabrow1++;
        }
        createHeadersForAggreagateExcels(workbook,gridData);
    }

    private void cleanBeforeMergeOnValidCells(XSSFSheet sheet,CellRangeAddress region, XSSFCellStyle cellStyle )
    {
        for(int rowNum =region.getFirstRow();rowNum<=region.getLastRow();rowNum++){
            XSSFRow row= sheet.getRow(rowNum);
            if(row==null){
                row= sheet.createRow(rowNum);
            }
            for(int colNum=region.getFirstColumn();colNum<=region.getLastColumn();colNum++){
                XSSFCell currentCell = row.getCell(colNum);
                if(currentCell==null){
                    currentCell = row.createCell(colNum);

                }

                currentCell.setCellStyle(cellStyle);

            }
        }


    }

}

