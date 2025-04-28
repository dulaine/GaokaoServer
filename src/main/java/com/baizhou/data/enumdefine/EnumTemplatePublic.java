package com.baizhou.data.enumdefine;

public enum EnumTemplatePublic {
    Pub(1), //
    Unpub(0),  //

    ;

    private int stateID;

    EnumTemplatePublic(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
