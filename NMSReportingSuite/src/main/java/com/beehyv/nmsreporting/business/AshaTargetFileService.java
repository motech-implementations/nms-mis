package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.business.impl.TargetFileNotification;

public interface AshaTargetFileService {

    TargetFileNotification generateTargetFile();

    void processTargetFile();

}
