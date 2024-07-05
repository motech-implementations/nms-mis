package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Block;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    public Block findByblockId(Integer blockId);

    Block findByLocationId(Long stateId);


    public List<Block> findByName(String blockName);

    public List<Block> getBlocksOfDistrict(Integer districtId);

    public Integer getDistrictOfBlock(Block block);

    List<Block> findByIds(Set<Integer> blockIds);
}
