package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.User;

import java.util.List;
import java.util.Map;

public interface CertificateService {
    List<Map<String, String>> createSpecificCertificate(Long mobileNo, User currentUser);
}
