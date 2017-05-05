package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Block;


import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    public Block findByblockId(Integer blockId);

    public List<Block> findByName(String blockName);




    public void saveBlock(Block block);

    public void deleteBlock(Block block);
}
