package com.baizhou.data.enumdefine;

public enum EnumOrderStatus {
    Wait(0),
    Processed(1),
    ReViewed(2),
    Completed(3),;

    private int stateID;

    EnumOrderStatus(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }


    public static EnumOrderStatus Get(int stateID) {
        switch (stateID) {
            case 0: {
                return EnumOrderStatus.Wait;
            }
            case 1: {
                return EnumOrderStatus.Processed;
            }
            case 2: {
                return EnumOrderStatus.ReViewed;
            }

            case 3: {
                return EnumOrderStatus.Completed;
            }

        }
        return EnumOrderStatus.Completed;
    }

}
