package com.baizhou.data.enumdefine;

public enum EnumFormOpType {
    ByTeacher(0),
    ByUser(1);
    //状态: 0:最近教师更新  1:最近用户更新
    private int stateID;

    EnumFormOpType(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
