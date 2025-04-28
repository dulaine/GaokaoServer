package com.baizhou.manager;

public class CacheManager {
    private static CacheManager instance;

    public static CacheManager GetInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    /////////素材库[全景图] start////////////////////////////////////////////////////////////////////////////////
//    private Hashtable<Long, PanoPicResVO> m_PanoPicDic = new Hashtable<>(); //以id为key的字典


}
