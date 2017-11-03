package com.t1_network.taiyi.model.event;

import com.t1_network.taiyi.db.TYSP;

/**
 * 功能:
 * 创建者: David
 * 创建日期: 16/1/22 13:53
 * 修改记录:
 */
public class UpdateCompareEvent {

    private long numCompare;

    public UpdateCompareEvent() {

        numCompare = TYSP.getCompareGood().size();

    }

    public long getNumCompare() {
        return numCompare;
    }

    public void setNumCompare(long numCompare) {
        this.numCompare = numCompare;
    }
}
