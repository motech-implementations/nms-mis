package com.beehyv.nmsreporting.business;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.User;

import java.util.List;

public interface AggregateKilkariReportsService {

    List<AggregateCumulativeKilkariDto> getKilkariCumulativeSummary(ReportRequest reportRequest, User currentUser);

    AggregateKilkariReportsDto getKilkariSubscriberCountReport(ReportRequest reportRequest);

    AggregateKilkariReportsDto getKilkariSubscriberCountReportBasedOnRegistrationDate(ReportRequest reportRequest);

    List<KilkariAggregateBeneficiariesDto> getBeneficiaryReport(ReportRequest reportRequest, User currentUser);

    List<UsageDto> getUsageReport(ReportRequest reportRequest, User currentUser);

    List<UsageDto> getMotherUsageReport(ReportRequest reportRequest, User currentUser);

    List<UsageDto> getChildUsageReport(ReportRequest reportRequest, User currentUser);

    AggregateKilkariReportsDto getKilkariMessageListenershipReport(ReportRequest reportRequest);

    AggregateKilkariReportsDto getWhatsAppSubscriberCountReport(ReportRequest reportRequest);

    AggregateKilkariReportsDto getWhatsAppMessageCountReport(ReportRequest reportRequest);

    AggregateKilkariReportsDto getWhatsAppReport(ReportRequest reportRequest);

    List<AggCumulativeBeneficiaryComplDto> getCumulativeBeneficiaryCompletion(ReportRequest reportRequest, User currentUser);

    List<ListeningMatrixDto> getListeningMatrixReport(ReportRequest reportRequest, User currentUser);

    List<KilkariHomePageReportsDto> getkilkariHomePageReport(ReportRequest reportRequest);

    AggregateKilkariReportsDto getKilkariThematicContentReport(ReportRequest reportRequest);

    MessageMatrixResponseDto getMessageMatrixReport(ReportRequest reportRequest, User currentUser);

    AggregateKilkariRepeatListenerMonthWiseDto getKilkariRepeatListenerMonthWiseReport(ReportRequest reportRequest);

    List<KilkariCallReportDto> getKilkariCallReport(ReportRequest reportRequest, User currentUser);

    List<KilkariCallReportWithBeneficiariesDto> getKilkariCallReportWithBeneficiaries(ReportRequest reportRequest, User currentUser);
}

