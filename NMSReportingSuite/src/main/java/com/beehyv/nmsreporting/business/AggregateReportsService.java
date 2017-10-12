package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.dao.AggCumulativeBeneficiaryComplDao;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
public interface AggregateReportsService {

    List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId, String locationType, Date toDate);

    List<AggregateCumulativekilkariDto> getKilkariCumulativeSummary(ReportRequest reportRequest, User currentUser);

    List<AggCumulativeBeneficiaryComplDto> getCumulativeBeneficiaryCompletion(ReportRequest reportRequest, User currentUser);

    List<AggregateBeneficiaryDto> getBeneficiaryReport(ReportRequest reportRequest, User currentUser);

    List<UsageDto> getUsageReport(ReportRequest reportRequest, User currentUser);

    List<ListeningMatrixDto> getListeningMatrixReport(ReportRequest reportRequest, User currentUser);

    List<KilkariCallReportDto> getKilkariCallReport(ReportRequest reportRequest, User currentUser);


    }
