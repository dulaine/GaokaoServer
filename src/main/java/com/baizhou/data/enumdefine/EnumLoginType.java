package com.baizhou.data.enumdefine;

public enum EnumLoginType {
    Common(0),
    Wechat(1)
    ;

    private int stateID;

    EnumLoginType(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
