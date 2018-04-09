package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Theme;

import java.util.List;
import java.util.Map;

public interface ThemeDao {
    public Map<Integer,Theme> getAllThemes();
}
