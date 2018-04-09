package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ThemeDao;
import com.beehyv.nmsreporting.model.Theme;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("ThemeDao")
public class ThemeDaoImpl extends AbstractDao<Integer, Theme> implements ThemeDao {

    @Override
    public Map<Integer,Theme> getAllThemes() {
        Criteria criteria = createEntityCriteria();
        Map<Integer,Theme> resultMap = new HashMap<>();
        for(int i =0 ;i<72;i++){
            Theme theme = (Theme)criteria.list().get(i);
            resultMap.put(theme.getId(),theme);
        }
        return resultMap;
    }
}
