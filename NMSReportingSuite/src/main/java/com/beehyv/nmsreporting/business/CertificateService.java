package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.CertificateRequest;
import com.beehyv.nmsreporting.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CertificateService {
    List<Map<String, String>> createSpecificCertificate(Long mobileNo, User currentUser);
    Map<String, String> createCertificateInBulk(CertificateRequest certificateRequest, String forMonth, boolean auditable , String queryLevel, String zipDir, User currentUser, String stateName, HashMap<Integer, String> districtMap, HashMap<Integer, HashMap<Integer, String>> blockMap);
}
