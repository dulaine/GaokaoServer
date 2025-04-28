package com.baizhou.data.enumdefine;

public enum EnumRoleType {
    Admin(0), //管理员
    Super(1),  //超级管理员
    Reviwer(2),
    Expert(3),
    Operator(4),
    Client(5),
    Assistant(6), //助理
    ;

    private int stateID;

    EnumRoleType(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }


    public static EnumRoleType Get(int stateID) {
        switch (stateID) {
            case 1: {
                return EnumRoleType.Super;
            }
            case 2: {
                return EnumRoleType.Reviwer;
            }

            case 3: {
                return EnumRoleType.Expert;
            }

            case 4: {
                return EnumRoleType.Operator;
            }

        }
        return EnumRoleType.Operator;
    }
}

