package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.dao.AggCumulativeBeneficiaryComplDao;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
public interface AggregateReportsService {

    List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId, String locationType, Date toDate, boolean isCumulative);

     void createSpecificAggreagateExcel(XSSFWorkbook workbook, AggregateExcelDto gridData);

}
