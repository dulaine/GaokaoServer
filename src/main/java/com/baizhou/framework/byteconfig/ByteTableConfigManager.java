package com.baizhou.framework.byteconfig;

import com.baizhou.manager.ConfigManager;
import com.baizhou.util.FileUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class ByteTableConfigManager {
    private static String BIN_PATH = "./configData/configs/{0}.bin";
    private static String BIN_CONFIG_DATA_PACK_NAME = "com.baizhou.data.config.{0}Row";

    private static ByteTableConfigManager _instance;

    public static ByteTableConfigManager GetInstance() {
        if (_instance == null) _instance = new ByteTableConfigManager();
        return _instance;
    }

    Hashtable<String, ConfigTable> m_Config = new Hashtable<>();

    public void Init() {

    }

    /**
     * 加载配置文件
     *
     * @param byteFileName
     */
    public void LoadByteFile(String byteFileName) {
        try {
            ReadByteFile(byteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载bin二进制配置文件
     *
     * @param configName
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void ReadByteFile(String configName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        byte[] bytes = FileUtils.readByteData(MessageFormat.format(BIN_PATH, configName)); //
        ConfigTable configTable = new ConfigTable<>();
        Class tClass = Class.forName(MessageFormat.format(BIN_CONFIG_DATA_PACK_NAME, configName));
        configTable.Load(bytes, tClass);
        m_Config.put(tClass.getName(), configTable);
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
        ConfigTable<T> config = m_Config.getOrDefault(tClass.getName(), null);
        if (config != null) {
            return config.GetByID(id);
        }
        return null;
    }

    /**
     * 获取所有配置内容
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T extends IByteConfig> List<T> GetAllConfig(Class<T> tClass) {
        List<T> list = new ArrayList<>();
        ConfigTable<T> config = m_Config.getOrDefault(tClass.getName(), null);
        if (config != null) {
            Object[] allObject = config.GetAll();
            for (int i = 0; i < allObject.length; i++) {
                Object obj = allObject[i];
                list.add((T) obj);
            }

            list.sort(new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    if (o1.GetID() > o2.GetID()) return 1;
                    if (o1.GetID() == o2.GetID()) return 0;
                    return -1;
                }
            });
        }
        return list;
    }
}
