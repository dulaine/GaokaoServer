package com.baizhou.manager;


import com.baizhou.framework.byteconfig.ByteTableConfigManager;
import com.baizhou.framework.byteconfig.IByteConfig;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ConfigManager {
    private static String BIN_PATH = "./configData/configs/{0}.bin";
    private static String BIN_CONFIG_DATA_PACK_NAME = "com.baizhou.data.config.{0}Data";

    private static ConfigManager _instance;

    public static ConfigManager GetInstance() {
        if (_instance == null) _instance = new ConfigManager();
        return _instance;
    }

    public void Init() {

        ByteTableConfigManager.GetInstance().Init();
//        ByteTableConfigManager.GetInstance().LoadByteFile("PrizeDrawConfig");

    }

    /**
     * 获取配置
     *
     * @param id
     * @param tClass
     * @param <T>
     * @return
     */
    public <T extends IByteConfig> T GetConfig(int id, Class<T> tClass) {
        return ByteTableConfigManager.GetInstance().GetConfig(id, tClass);
    }

    /**
     * 获取所有配置内容
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T extends IByteConfig> List<T> GetAllConfig(Class<T> tClass) {
        return ByteTableConfigManager.GetInstance().GetAllConfig(tClass);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}

