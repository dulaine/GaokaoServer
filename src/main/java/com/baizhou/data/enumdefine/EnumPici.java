package com.baizhou.data.enumdefine;

public enum EnumPici {
    A(1),
    B(2),
    PreA(3),
    PreB(4),

    ZhuanKe(5),
    ZXA(6),//征询本A批次
    ZXB(7),//征询本B批次
    ;

    private int stateID;

    EnumPici(int stateID) {
        this.stateID = stateID;
    }

    public Integer getStateID() {
        return stateID;
    }


    public static EnumPici Get(int stateID) {
        switch (stateID) {
            case 1: {
                return EnumPici.A;
            }
            case 2: {
                return EnumPici.B;
            }

            case 3: {
                return EnumPici.PreA;
            }
            case 4: {
                return EnumPici.PreB;
            }

            case 5: {
                return EnumPici.ZhuanKe;
            }
            case 6: {
                return EnumPici.ZXA;
            }
            case 7: {
                return EnumPici.ZXB;
            }
        }
        return EnumPici.PreB;
    }
}
