package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AggregateReportsService;

import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.AggregateExcelDto;
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

import javax.transaction.Transactional;
import java.util.*;
import java.util.List;

import static com.beehyv.nmsreporting.utils.Constants.image_base64;
import static java.lang.Double.parseDouble;
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

