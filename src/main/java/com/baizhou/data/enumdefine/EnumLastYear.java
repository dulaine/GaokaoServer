package com.baizhou.data.enumdefine;


public enum EnumLastYear {
    LastYear1(1),
    LastYear2(2),
    LastYear3(4),;

    private int stateID;

    EnumLastYear(int stateID) {
        this.stateID = stateID;
    }

    public Integer getStateID() {
        return stateID;
    }


    public static EnumLastYear Get(int stateID) {
        switch (stateID) {
            case 1: {
                return EnumLastYear.LastYear1;
            }
            case 2: {
                return EnumLastYear.LastYear2;
            }

            case 3: {
                return EnumLastYear.LastYear3;
            }

        }
        return EnumLastYear.LastYear1;
    }
}