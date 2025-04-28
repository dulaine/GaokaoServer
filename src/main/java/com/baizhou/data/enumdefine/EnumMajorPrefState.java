package com.baizhou.data.enumdefine;

public enum EnumMajorPrefState {
    Neutral(0),
    Fav(1),
    Unfav(2);

    private int stateID;

    EnumMajorPrefState(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }


    public static EnumMajorPrefState Get(int stateID) {
        switch (stateID) {
            case 0: {
                return EnumMajorPrefState.Neutral;
            }
            case 1: {
                return EnumMajorPrefState.Fav;
            }
            case 2: {
                return EnumMajorPrefState.Unfav;
            }


        }
        return EnumMajorPrefState.Neutral;
    }
}
