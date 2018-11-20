package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AshaInEachBlock;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;

public interface AshaEachBlockServiceDao {

    List<AshaInEachBlock> setQuery(Integer districtId, Integer year);
}
