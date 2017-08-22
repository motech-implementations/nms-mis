package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Block;

/**
 * Created by beehyv on 4/5/17.
 */
public interface BlockDao {

    public Block findByblockId(Integer blockId);
}
