package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.model.Block;

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
        return null;
    }

    @Override
    public List<Block> getChildBlocks(int blockId) {
        return null;
    }

    @Override
    public void saveBlock(Block block) {

    }

    @Override
    public void deleteBlock(Block block) {

    }
}
