package com.baizhou.data.enumdefine;

public enum EnumGroupState {
    Norm(0),
    Add(1),
    Del(2);
    //专业组状态:  0: 无; 1:客户新增; 2:客户删除
    private int stateID;

    EnumGroupState(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
