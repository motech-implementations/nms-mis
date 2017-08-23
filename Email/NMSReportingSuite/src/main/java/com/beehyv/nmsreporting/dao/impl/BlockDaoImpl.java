package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.model.Block;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("blockDao")
public class BlockDaoImpl extends AbstractDao<Integer, Block> implements BlockDao {
    @Override
    public Block findByblockId(Integer blockId) {
        return getByKey(blockId);
    }
}
