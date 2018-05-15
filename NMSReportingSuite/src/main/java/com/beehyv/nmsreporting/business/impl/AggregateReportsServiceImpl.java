package com.beehyv.nmsreporting.business.impl;

import be.quodlibet.boxable.BaseTable;
import com.beehyv.nmsreporting.business.AggregateReportsService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.AggregateExcelDto;
import com.beehyv.nmsreporting.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.*;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.beehyv.nmsreporting.utils.Constants.image_base64;
import static java.lang.Double.parseDouble;





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

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private HealthFacilityDao healthFacilitydao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;


    @Override
    public List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId,String locationType,Date toDate, boolean isCumulative){
        Date date = toDate;
        List<AggregateCumulativeMA> CumulativeSummery = new ArrayList<>();
        List<String> Headers = new ArrayList<>();
        if(locationType.equalsIgnoreCase("State")){
            List<State> states=stateDao.getStatesByServiceType("M");
            for(State s:states){
                if(date.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"M"))&&!isCumulative){
                    date = stateServiceDao.getServiceStartDateForState(s.getStateId(),"M");
                }
                if(!isCumulative||!toDate.before(stateServiceDao.getServiceStartDateForState(s.getStateId(),"M"))){
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getStateId(),locationType,date));}
                date = toDate;
            }

        }
        else{
            if(locationType.equalsIgnoreCase("District")){
                if(date.before(stateServiceDao.getServiceStartDateForState(locationId,"M"))&&!isCumulative){
                    date = stateServiceDao.getServiceStartDateForState(locationId,"M");
                }
                List<District> districts = districtDao.getDistrictsOfState(locationId);
                AggregateCumulativeMA stateCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"State",date);
                Integer ashasRegistered = 0;
                Integer ashasStarted = 0;
                Integer ashasNotStarted = 0;
                Integer ashasCompleted = 0;
                Integer ashasFailed = 0;
                Integer ashasRejected = 0;
                for(District d:districts){
                    AggregateCumulativeMA distrcitCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,date);
                    CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getDistrictId(),locationType,date));
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
                    if(date.before(stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"M"))&&!isCumulative){
                        date = stateServiceDao.getServiceStartDateForState(districtDao.findByDistrictId(locationId).getStateOfDistrict(),"M");
                    }
                    List<Block> blocks = blockDao.getBlocksOfDistrict(locationId);
                    AggregateCumulativeMA districtCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"District",date);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for (Block d : blocks) {
                        AggregateCumulativeMA blockCount = aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(),locationType,date);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(d.getBlockId(), locationType,date));
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
                    if(date.before(stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"M"))&&!isCumulative){
                        date = stateServiceDao.getServiceStartDateForState(blockDao.findByblockId(locationId).getStateOfBlock(),"M");
                    }
                    List<HealthFacility> healthFacilities = healthFacilitydao.findByHealthBlockId(locationId);
                    List<HealthSubFacility> subcenters = new ArrayList<>();
                    for(HealthFacility hf :healthFacilities){
                        subcenters.addAll(healthSubFacilityDao.findByHealthFacilityId(hf.getHealthFacilityId()));
                    }
                    AggregateCumulativeMA blockCounts = aggregateCumulativeMADao.getMACumulativeSummery(locationId,"block",date);
                    Integer ashasRegistered = 0;
                    Integer ashasStarted = 0;
                    Integer ashasNotStarted = 0;
                    Integer ashasCompleted = 0;
                    Integer ashasFailed = 0;
                    Integer ashasRejected = 0;
                    for(HealthSubFacility s: subcenters){
                        AggregateCumulativeMA subcenterCount = aggregateCumulativeMADao.getMACumulativeSummery(s.getHealthSubFacilityId(),locationType,date);
                        CumulativeSummery.add(aggregateCumulativeMADao.getMACumulativeSummery(s.getHealthSubFacilityId(), locationType,date));
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
    }


    private void createHeadersForAggreagateExcels(XSSFWorkbook workbook, AggregateExcelDto gridData)  {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);


        String encodingPrefix = "base64,";
        String pngImageURL = image_base64;
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

        XSSFRow dateRow=spreadsheet.createRow(7);
        Cell cellA = dateRow.createCell(0);
        cellA.setCellValue("Date Filed");
        cellA.setCellStyle(style);
        Cell cellB = dateRow.createCell(1);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int DateValue =calendar.get(Calendar.DATE);
        int DateYear =(calendar.get(Calendar.YEAR));
        String DateString;
        if(DateValue <10) {
            DateString ="0"+String.valueOf(DateValue);
        }
        else {
            DateString =String.valueOf(DateValue);
        }
        String MonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String YearString =String.valueOf(DateYear);

        cellB.setCellValue(DateString+" "+MonthString+" "+YearString);
        CellRangeAddress dateRange = new CellRangeAddress(7,7,1,3);
        cleanBeforeMergeOnValidCells(spreadsheet,dateRange,style);
        spreadsheet.addMergedRegion(dateRange);

    }

    @Override
    public void createSpecificAggreagateExcel(XSSFWorkbook workbook, AggregateExcelDto gridData)  {



        XSSFSheet spreadsheet = workbook.getSheetAt(0);

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


        for(int i =0;i<15;i++){
            spreadsheet.setColumnWidth(i, 4000);}


        XSSFRow row;
        int rowid =8;

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue( "Kilkari Pregnancy Content Data");
            cell.setCellStyle(style);
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid-1,rowid-1,0,gridData.getColumnHeaders().size()));}

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellValue( "Beneficiary Count");
            cell.setCellStyle(style);
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid-1,rowid-1,0,gridData.getColumnHeaders1().size()));}

        row = spreadsheet.createRow(rowid++);
        row.setHeight((short)1100);
        int colid =0;
        int tabrow =0;
        Cell sno = row.createCell(colid++);
        sno.setCellValue("S.No");
        sno.setCellStyle(backgroundStyle);
        for (String header : gridData.getColumnHeaders()) {
            Cell cell1 = row.createCell(colid++);
            cell1.setCellValue(header);
            cell1.setCellStyle(backgroundStyle);
        }

        for (ArrayList<String> rowData : gridData.getReportData()) {
            row = spreadsheet.createRow(rowid++);
            colid =0;
            Cell SNrow = row.createCell(colid++);
            if((gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix"))&&tabrow+1==gridData.getReportData().size()){
                SNrow.setCellValue("");
                SNrow.setCellStyle(backgroundStyle3);
            }else{
                SNrow.setCellValue(tabrow+1);
                if(tabrow %2 ==0){
                    SNrow.setCellStyle(backgroundStyle1);
                }
                else {
                    SNrow.setCellStyle(backgroundStyle2);
                }}


            for (String cellData : rowData) {
                Cell cell1 = row.createCell(colid++);
                if(colid==2||cellData.equalsIgnoreCase("N/A")||(gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")&&colid==3)){
                    cell1.setCellValue(cellData);}
                else{cell1.setCellValue(parseDouble(cellData));}

                if(tabrow %2 ==0){
                    if((gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix"))&&tabrow+1==gridData.getReportData().size()){
                        cell1.setCellStyle(backgroundStyle3);
                    }else{
                        cell1.setCellStyle(backgroundStyle1);}
                }
                else {
                    if((gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix"))&&tabrow+1==gridData.getReportData().size()){
                        cell1.setCellStyle(backgroundStyle3);
                    }else{
                        cell1.setCellStyle(backgroundStyle2);}
                }

            }
            tabrow++;
        }

        row = spreadsheet.createRow(rowid++);
        colid =0;
        if(!(gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix")||gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")||gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise"))){
            Cell SNofooot = row.createCell(colid++);
            SNofooot.setCellValue("");
            SNofooot.setCellStyle(backgroundStyle3);
        }

        for (String footer : gridData.getColunmFooters()) {
            Cell cell1 = row.createCell(colid++);
            if(colid==2||footer.equalsIgnoreCase("N/A")){
                cell1.setCellValue(footer);}
            else{
                cell1.setCellValue(parseDouble(footer));
            }
            cell1.setCellStyle(backgroundStyle3);
        }

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue( "Kilkari Child Content Data");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid-1,rowid-1,0,gridData.getColumnHeaders1().size()));}

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise"))
        {row = spreadsheet.createRow(rowid++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue( "Beneficiary Percentage");
            CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
            spreadsheet.addMergedRegion(new CellRangeAddress(rowid-1,rowid-1,0,gridData.getColumnHeaders1().size()));}

        row = spreadsheet.createRow(rowid++);
        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")||gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")){
            row.setHeight((short)1100);
            colid =0;
            int tabrow1 =0;
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
                colid =0;

                Cell SNrow1 = row.createCell(colid++);
                SNrow1.setCellValue(tabrow1+1);
                if(tabrow %2 ==0){
                    SNrow1.setCellStyle(backgroundStyle1);
                }
                else {
                    SNrow1.setCellStyle(backgroundStyle2);
                }


                for (String cellData : rowData) {
                    Cell cell1 = row.createCell(colid++);
                    if(colid==2){
                        cell1.setCellValue(cellData);}
                    else{
                        cell1.setCellValue(parseDouble(cellData));
                    }
                    if(tabrow %2 ==0){
                        cell1.setCellStyle(backgroundStyle1);
                    }
                    else {
                        cell1.setCellStyle(backgroundStyle2);
                    }

                }
                tabrow1++;
            }}
        createHeadersForAggreagateExcels(workbook,gridData);
    }

    @Override
    public void createSpecificAggreagatePdf(PDDocument document, AggregateExcelDto gridData) throws IOException {
        PDFont font = PDType1Font.TIMES_BOLD;
        PDFont font1 = PDType1Font.TIMES_ROMAN;

        int fontSize = 16;
        int fontSize1=12;

//            //Creating a blank page
//
            PDPage page = new PDPage();
//            //Adding the blank page to the document
           document.addPage( page );


        //Creating PDImageXObject object
        PDImageXObject pdImage1 = PDImageXObject.createFromFile("/home/beehyv/NMS-Reporting/nms-mis/app/images/national emblem.jpeg",document);
        PDImageXObject pdImage2 = PDImageXObject.createFromFile("/home/beehyv/NMS-Reporting/nms-mis/app/images/national-health-mission.png",document);
        PDImageXObject pdImage3 = PDImageXObject.createFromFile("/home/beehyv/NMS-Reporting/nms-mis/app/images/digital_logo.png",document);
        //combineImagesIntoPDF("/home/beehyv/Documents/my_doc.pdf","/home/beehyv/NMS-Reporting/nms-mis/app/images/national emblem.jpeg","/home/beehyv/NMS-Reporting/nms-mis/app/images/national-health-mission.png","/home/beehyv/NMS-Reporting/nms-mis/app/images/digital_logo.png");
        //creating the PDPageContentStream object
        PDPageContentStream contents = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);
        PDRectangle mediaBox = page.getMediaBox();
//        System.out.println("Image inserted");
        contents.beginText();
        contents.setFont(font,fontSize1);
        //Setting the leading
        contents.setLeading(14.5f);

        //Setting the position for the line
        contents.newLineAtOffset(200, 90);

        //Adding text in the form of string
        contents.showText("Management and Information System");
        //contents.newLine();
        contents.newLineAtOffset(25, 20);
        contents.setFont(font,fontSize1);
        contents.showText("Mobile Academy and Kilkari");
        //contents.newLine();
        contents.setFont(font,fontSize);
        contents.newLineAtOffset(-50, 30);
        contents.showText("Ministry of Health and Family Welfare");
        contents.newLine();
        contents.endText();



        //Drawing the image in the PDF document
        try { float startX=(mediaBox.getWidth() - 200) / 2;
            contents.drawImage(pdImage1, startX, 520 ,200, 150);
            startX=(mediaBox.getWidth() - 120) / 2;
            contents.drawImage(pdImage2, startX, 360,120,100);
            startX=(mediaBox.getWidth() - 120) / 2;
            contents.drawImage(pdImage3, startX, 240,120,100);
            //Closing the PDPageContentStream object
            contents.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Image inserted");


        page = new PDPage();
          //Adding the blank page to the document
        document.addPage( page );



       // contents = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);

//        // Define the table structure first
//
//        TableBuilder tableBuilder = new TableBuilder();
//        tableBuilder.addColumnOfWidth(25).setFontSize(8)
//                .setFont(PDType1Font.HELVETICA);
//        for(int i=0;i<gridData.getColumnHeaders().size();i++){
//            tableBuilder.addColumnOfWidth(120);
//            }
//
//
//
//        // Header ...
//
//        RowBuilder headerRow= new RowBuilder();
//        headerRow.add(org.vandeseer.pdfbox.easytable.Cell.withText("S No").setHorizontalAlignment(CENTER));
//
//        for(int i=0;i<gridData.getColumnHeaders().size();i++){
//            headerRow.add(org.vandeseer.pdfbox.easytable.Cell.withText(gridData.getColumnHeaders().get(i)).setHorizontalAlignment(CENTER));
//        }
//        headerRow.setBackgroundColor(java.awt.Color.YELLOW);
//        tableBuilder.addRow(headerRow.build());
////        tableBuilder.addRow(new RowBuilder()
////                .add(org.vandeseer.pdfbox.easytable.Cell.withText("This is right aligned without a border").setHorizontalAlignment(RIGHT))
////                .add(org.vandeseer.pdfbox.easytable.Cell.withText("And this is another cell"))
////                .add(org.vandeseer.pdfbox.easytable.Cell.withText("Sum").setBackgroundColor(java.awt.Color.ORANGE))
////                .setBackgroundColor(java.awt.Color.BLUE)
////                .build());
//
////        for (int i = 0; i < 10; i++) {
////            tableBuilder.addRow(new RowBuilder()
////                    .add(org.vandeseer.pdfbox.easytable.Cell.withText(i).withAllBorders())
////                    .add(org.vandeseer.pdfbox.easytable.Cell.withText(i * i).withAllBorders())
////                    .add(org.vandeseer.pdfbox.easytable.Cell.withText(i + (i * i)).withAllBorders())
////                    .setBackgroundColor(i % 2 == 0 ? java.awt.Color.LIGHT_GRAY : java.awt.Color.WHITE)
////                    .build());
////        }
//
//        // Define the starting point
//        final float startY = page.getMediaBox().getHeight() - 50;
//        final int startX = 50;
//
//// Draw!
//        (new TableDrawer(contents, tableBuilder.build(), startX, startY)).draw();


////Dummy Table
//        float margin = 10;
//// starting y position is whole page height subtracted by top and bottom margin
//        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
//// we want table across whole page width (subtracted by left and right margin ofcourse)
//        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
//
//        boolean drawContent = true;
//        float yStart = yStartNewPage;
//        float bottomMargin = 70;
//// y position is your coordinate of top left corner of the table
//        float yPosition = 550;
//
//        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
//        be.quodlibet.boxable.Row<PDPage> headerRow = table.createRow(15f);
//        be.quodlibet.boxable.Cell<PDPage> cell = headerRow.createCell(10, "S No");
//        for(int i=0;i<gridData.getColumnHeaders().size();i++){
//        cell = headerRow.createCell(15, gridData.getColumnHeaders().get(i));}
//
//        table.addHeaderRow(headerRow);
//
//
//       // be.quodlibet.boxable.Row<PDPage> row = table.createRow(12);
//        for(int i=0;i<gridData.getReportData().size();i++){
//            be.quodlibet.boxable.Row<PDPage> row = table.createRow(12);
//            cell = row.createCell(10, Integer.toString(i+1));
//
//            for(int j=0;j<gridData.getReportData().get(i).size();j++){
//                cell=row.createCell(15,gridData.getReportData().get(i).get(j));
//
//            }
//
//        table.addHeaderRow(row);
//        }
//
////        cell = row.createCell(30, "Data 1");
////        cell = row.createCell(70, "Some value");
//
//        table.draw();

       // System.out.println(gridData.getReportData().size()+"lololololololololololololololol");



        float cellWidth=0;
        float tableMargin=10;
        float tableFont=7;
        //contents.close();

        if(gridData.getReportName().equalsIgnoreCase("MA Cumulative Summary")||gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")){
            cellWidth=10f; tableMargin=60; tableFont=7;
        } else if(gridData.getReportName().equalsIgnoreCase("MA Subscriber")||gridData.getReportName().equalsIgnoreCase("Kilkari Beneficiary Completion")){
            cellWidth=12f; tableMargin=75;
        } else if(gridData.getReportName().equalsIgnoreCase("MA Performance")||gridData.getReportName().equalsIgnoreCase("Kilkari Listening Matrix")){
                cellWidth=15f; tableMargin=60;
        }else if(gridData.getReportName().equalsIgnoreCase("Kilkari Thematic Content")||gridData.getReportName().equalsIgnoreCase("Kilkari Cumulative Summary")||gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")){
            cellWidth=15f; tableMargin=90;
        }
        else if(gridData.getReportName().equalsIgnoreCase("Kilkari Usage")||gridData.getReportName().equalsIgnoreCase("Kilkari Subscriber")||gridData.getReportName().equalsIgnoreCase("Kilkari Message Listenership")){
            cellWidth=12f; tableMargin=45;
        } else if(gridData.getReportName().equalsIgnoreCase("Kilkari Call")){
            cellWidth=10f; tableMargin=30;
        } else if(gridData.getReportName().equalsIgnoreCase("Kilkari Aggregate Beneficiaries")){
            cellWidth=9f; tableMargin=30;
        }

        //System.out.println("this is the cellwidth " + gridData.getReportName());
        PDPageContentStream stream = new PDPageContentStream(document, page);

        String title=gridData.getReportName() + " Report";
        float titleWidth=font.getStringWidth(title) / 1000 * fontSize;
        float x=(page.getMediaBox().getWidth()-titleWidth)/2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize, x, 700, java.awt.Color.BLACK);

        //stream.newLine();
       title="State : "+gridData.getStateName();
         titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
         x=(page.getMediaBox().getWidth()-titleWidth)/2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 650, java.awt.Color.BLACK);
        title="District : "+gridData.getDistrictName();
        titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
        x=(page.getMediaBox().getWidth()-titleWidth)/2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 630, java.awt.Color.BLACK);
        title="Block : "+gridData.getBlockName();
        titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
        x=(page.getMediaBox().getWidth()-titleWidth)/2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 610, java.awt.Color.BLACK);
        title="Period : "+gridData.getTimePeriod();
        titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
        x=(page.getMediaBox().getWidth()-titleWidth)/2;
        be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font1, fontSize1, x, 590, java.awt.Color.BLACK);

        float margin = 10;




        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

        // Initialize table

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")){
            title="Kilkari Pregnancy Content Data";
            titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
            x=(page.getMediaBox().getWidth()-titleWidth)/2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, 540, java.awt.Color.BLACK);

        }

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")){
            title="Beneficiary Count";
            titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
            x=(page.getMediaBox().getWidth()-titleWidth)/2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, 540, java.awt.Color.BLACK);

        }
        stream.close();

        float tableWidth = page.getMediaBox().getWidth() - (2 * 50);
        cellWidth=tableWidth/(gridData.getColumnHeaders().size()+1)/5;
        boolean drawContent = true;
        float yStart = 500;
        float bottomMargin = 50;
        BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, tableMargin, document, page, true,
                drawContent);

        System.out.println("Tablewidthhhhhhhhhhhhhhhhhhhhh"+tableWidth);
        System.out.println("cellwidthhhhhhhhhhh"+cellWidth);


        // Create Header row
        be.quodlibet.boxable.Row<PDPage> headerRow = table.createRow(15f);
       be.quodlibet.boxable.Cell<PDPage> cell = headerRow.createCell((8), "S No",be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
        for(int i=0;i<gridData.getColumnHeaders().size();i++){
        cell = headerRow.createCell(cellWidth, gridData.getColumnHeaders().get(i),be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
        cell.setFontSize(tableFont);
            cell.setFont(PDType1Font.TIMES_BOLD);
            cell.setFillColor( java.awt.Color.LIGHT_GRAY);

//            cell.setFont(PDType1Font.HELVETICA_BOLD);

//            cell.setTextColor(java.awt.Color.WHITE);
        }
//



        // Create 2 column row
        be.quodlibet.boxable.Row<PDPage> row ;


        for(int i=0;i<gridData.getReportData().size();i++){
            row = table.createRow(10f);
            cell = row.createCell((8) , Integer.toString(i+1) ,be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
            for(int j=0;j<gridData.getReportData().get(i).size();j++){
                cell=row.createCell((cellWidth),gridData.getReportData().get(i).get(j),be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
                cell.setFontSize(tableFont);

            }


        }

        table.draw();

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Repeat Listener Month Wise")){
            page = new PDPage();
            //Adding the blank page to the document
            document.addPage( page );
             stream = new PDPageContentStream(document, page);
            yStart=yStartNewPage;
            tableWidth = page.getMediaBox().getWidth() - (2 * 50);

            title="Beneficiary Percentage";
            titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
            x=(page.getMediaBox().getWidth()-titleWidth)/2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, yStart, java.awt.Color.BLACK);
            stream.close();
            table = new BaseTable(yStart-30, yStartNewPage, bottomMargin, tableWidth, tableMargin, document, page, true,
                    drawContent);

            // Create Header row
             headerRow = table.createRow(15f);
             cell = headerRow.createCell(8f, "S No",be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
            for(int i=0;i<gridData.getColumnHeaders1().size();i++){
                cell = headerRow.createCell(cellWidth, gridData.getColumnHeaders1().get(i),be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
                cell.setFontSize(tableFont);
            cell.setFont(PDType1Font.TIMES_BOLD);
//            cell.setFillColor(java.awt.Color.BLACK);
//            cell.setTextColor(java.awt.Color.WHITE);
            }


            for(int i=0;i<gridData.getReportData1().size();i++){
                row = table.createRow(10f);
                cell = row.createCell(8f , Integer.toString(i+1) ,be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));

                for(int j=0;j<gridData.getReportData1().get(i).size();j++){
                    cell=row.createCell((cellWidth),gridData.getReportData1().get(i).get(j),be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
                    cell.setFontSize(tableFont);

                }


            }
            table.draw();



        }

        if(gridData.getReportName().equalsIgnoreCase("Kilkari Message Matrix")){
            page = new PDPage();
            cellWidth=12; tableMargin=60;
            //Adding the blank page to the document
            document.addPage( page );
            stream = new PDPageContentStream(document, page);
            yStart=yStartNewPage;
            tableWidth = page.getMediaBox().getWidth() - (2 * 50);

            title="Kilkari Child Content Data";
            titleWidth=font1.getStringWidth(title) / 1000 * fontSize1;
            x=(page.getMediaBox().getWidth()-titleWidth)/2;
            be.quodlibet.boxable.utils.PDStreamUtils.write(stream, title, font, fontSize1, x, yStart, java.awt.Color.BLACK);
            stream.close();
            table = new BaseTable(yStart-30, yStartNewPage, bottomMargin, tableWidth, tableMargin, document, page, true,
                    drawContent);

            // Create Header row
            headerRow = table.createRow(15f);
            cell = headerRow.createCell((8), "S No",be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
            for(int i=0;i<gridData.getColumnHeaders1().size();i++){
                cell = headerRow.createCell(cellWidth, gridData.getColumnHeaders1().get(i),be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
                cell.setFontSize(tableFont);
                cell.setFont(PDType1Font.TIMES_BOLD);
//            cell.setFillColor(java.awt.Color.BLACK);
//            cell.setTextColor(java.awt.Color.WHITE);
            }


            for(int i=0;i<gridData.getReportData1().size();i++){
                row = table.createRow(10f);
                cell = row.createCell((8) , Integer.toString(i+1) ,be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));

                for(int j=0;j<gridData.getReportData1().get(i).size();j++){
                    cell=row.createCell((cellWidth),gridData.getReportData1().get(i).get(j),be.quodlibet.boxable.HorizontalAlignment.get("center"),be.quodlibet.boxable.VerticalAlignment.get("middle"));
                    cell.setFontSize(tableFont);

                }


            }
            table.draw();



        }



        try {document.save("/home/beehyv/Documents/sample.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
//    private static void addImage(){
//
//
//        float startY = mediaBox.getHeight() - marginTop - titleHeight;
//
//    }


    private static List<String[]> getFacts() {
        List<String[]> facts = new ArrayList<String[]>();
        facts.add(new String[] { "Oil Painting was invented by the Belgian van Eyck brothers", "art", "inventions",
                "science" });
        facts.add(new String[] { "The Belgian Adolphe Sax invented the Saxophone", "inventions", "music", "" });
        facts.add(new String[] { "11 sites in Belgium are on the UNESCO World Heritage List", "art", "history", "" });
        facts.add(new String[] { "Belgium was the second country in the world to legalize same-sex marriage",
                "politics", "image:150dpi.png", "" });
        facts.add(new String[] { "In the seventies, schools served light beer during lunch", "health", "school",
                "beer" });
        facts.add(new String[] { "Belgium has the sixth fastest domestic internet connection in the world", "science",
                "technology", "" });
        facts.add(new String[] { "Belgium hosts the World's Largest Sand Sculpture Festival", "art", "festivals",
                "world championship" });
        facts.add(
                new String[] { "Belgium has compulsary education between the ages of 6 and 18", "education", "", "" });
        facts.add(new String[] {
                "Belgium also has more comic makers per square kilometer than any other country in the world", "art",
                "social", "world championship" });
        facts.add(new String[] {
                "Belgium has one of the lowest proportion of McDonald's restaurants per inhabitant in the developed world",
                "food", "health", "" });
        facts.add(new String[] { "Belgium has approximately 178 beer breweries", "beer", "food", "" });
        facts.add(new String[] { "Gotye was born in Bruges, Belgium", "music", "celebrities", "" });
        facts.add(new String[] { "The Belgian Coast Tram is the longest tram line in the world", "technology",
                "world championship", "" });
        facts.add(new String[] { "Stefan Everts is the only motocross racer with 10 World Championship titles.",
                "celebrities", "sports", "world champions" });
        facts.add(new String[] { "Tintin was conceived by Belgian artist Hergé", "art", "celebrities", "inventions" });
        facts.add(new String[] { "Brussels Airport is the world's biggest selling point of chocolate", "food",
                "world champions", "" });
        facts.add(new String[] { "Tomorrowland is the biggest electronic dance music festival in the world",
                "festivals", "music", "world champion" });
        facts.add(new String[] { "French Fries are actually from Belgium", "food", "inventions", "image:300dpi.png" });
        facts.add(new String[] { "Herman Van Rompy is the first full-time president of the European Council",
                "politics", "", "" });
        facts.add(new String[] { "Belgians are the fourth most money saving people in the world", "economy", "social",
                "" });
        facts.add(new String[] {
                "The Belgian highway system is the only man-made structure visible from the moon at night",
                "technology", "world champions", "" });
        facts.add(new String[] { "Andreas Vesalius, the founder of modern human anatomy, is from Belgium",
                "celebrities", "education", "history" });
        facts.add(
                new String[] { "Napoleon was defeated in Waterloo, Belgium", "celebrities", "history", "politicians" });
        facts.add(new String[] {
                "The first natural color picture in National Geographic was of a flower garden in Gent, Belgium in 1914",
                "art", "history", "science" });
        facts.add(new String[] { "Rock Werchter is the Best Festival in the World", "festivals", "music",
                "world champions" });

        // Make the table a bit bigger
        facts.addAll(facts);
        facts.addAll(facts);
        facts.addAll(facts);

        return facts;
    }
}



