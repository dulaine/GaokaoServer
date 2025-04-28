package com.baizhou.data.enumdefine;

public enum EnumOpUserType {
    Create(1),
    Update(2),
    Delete(3);

    private int stateID;

    EnumOpUserType(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }


    public static EnumOpUserType Get(int stateID) {
        switch (stateID) {
            case 1: {
                return EnumOpUserType.Create;
            }
            case 2: {
                return EnumOpUserType.Update;
            }

            case 3: {
                return EnumOpUserType.Delete;
            }

        }
        return EnumOpUserType.Update;
    }
}