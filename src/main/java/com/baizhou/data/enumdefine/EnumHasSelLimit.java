package com.baizhou.data.enumdefine;

public enum EnumHasSelLimit {
    Unsel(0),
    Selected(1),
    SelLimit(2)
    ;// 0:未选择过    1: 选择正常     2: 存在体检限报, ;

    private int stateID;

    EnumHasSelLimit(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
