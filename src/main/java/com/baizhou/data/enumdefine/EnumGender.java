package com.baizhou.data.enumdefine;

public enum EnumGender {
    Male(1),
    Femal(2);

    private int stateID;

    EnumGender(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
