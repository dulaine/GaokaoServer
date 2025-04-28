package com.baizhou.data.enumdefine;

public enum EnumDeleteStatus {
    UnDeleted(0),
    Deleted(1),
    All(2);

    private int stateID;

    EnumDeleteStatus(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
