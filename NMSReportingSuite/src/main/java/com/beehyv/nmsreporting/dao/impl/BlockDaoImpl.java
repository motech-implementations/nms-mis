package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.model.Block;
import com.beehyv.nmsreporting.model.Taluka;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
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
    public void saveBlock(Block block) {
        persist(block);
    }

    @Override
    public void deleteBlock(Block block) {
        delete(block);
    }
}
