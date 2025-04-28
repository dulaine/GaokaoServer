package com.baizhou.data.enumdefine;

public enum EnumLockStatus {
    Unlock(0),
    Lock(1);

    private int stateID;

    EnumLockStatus(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
