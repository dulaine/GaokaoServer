package com.baizhou.data.enumdefine;

public enum EnumHasGenderScore {
    No(0),
    Yes(1);

    private int stateID;

    EnumHasGenderScore(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
