package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.model.Block;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.Taluka;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("blockDao")
public class BlockDaoImpl extends AbstractDao<Integer, Block> implements BlockDao {
    @Override
    public Block findByblockId(Integer blockId) {
        return getByKey(blockId);
    }

    @Override
    public List<Block> findByName(String blockName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockName", blockName).ignoreCase());
        return (List<Block>) criteria.list();
    }

    @Override
    public List<Block> getBlocksOfTaluka(Taluka taluka) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("talukaOfBlock", taluka));
        return (List<Block>) criteria.list();
    }

    @Override
    public List<Block> getBlocksOfDistrict(District district) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtOfBlock", district));
        return (List<Block>) criteria.list();
    }

    @Override
    public List<Block> getBlocksOfState(State state) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateOfBlock", state));
        return (List<Block>) criteria.list();
    }

    @Override
    public List<Block> getAllBlocks() {
        Criteria criteria = createEntityCriteria();
        return (List<Block>) criteria.list();
    }

    @Override
    public District getDistrictOfBlock(Block block) {
        return block.getDistrictOfBlock();
    }

    @Override
    public void saveBlock(Block block) {
        persist(block);
    }

    @Override
    public void deleteBlock(Block block) {
        delete(block);
    }
}
