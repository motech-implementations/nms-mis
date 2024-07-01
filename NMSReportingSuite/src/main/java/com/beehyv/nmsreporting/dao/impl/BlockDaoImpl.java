package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.model.Block;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("blockDao")
@Transactional
public class BlockDaoImpl extends AbstractDao<Integer, Block> implements BlockDao {
    @Override
    public Block findByblockId(Integer blockId) {
        return getByKey(blockId);
    }

    @Override
    public Block findByLocationId(Long locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (Block) criteria.list().get(0);
    }

    @Override
    public List<Block> findByName(String blockName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockName", blockName).ignoreCase());
        return (List<Block>) criteria.list();
    }

    @Override
    public List<Block> getBlocksOfDistrict(Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtOfBlock", districtId));
        return (List<Block>) criteria.list();
    }

    @Override
    public Integer getDistrictOfBlock(Block block) {
        return block.getDistrictOfBlock();
    }

    @Override
    public List<Block> findByIds(Set<Integer> blockIds){
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("blockId",blockIds));
        return (List<Block>) criteria.list();
    }
}
