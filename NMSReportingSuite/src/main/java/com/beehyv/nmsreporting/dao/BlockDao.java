package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Block;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.Taluka;


import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    public Block findByblockId(Integer blockId);

    public List<Block> findByName(String blockName);

    public List<Block> getBlocksOfTaluka(Taluka taluka);

    public List<Block> getBlocksOfDistrict(District district);

    public List<Block> getBlocksOfState(State state);

    public List<Block> getAllBlocks();

    public void saveBlock(Block block);

    public void deleteBlock(Block block);
}
