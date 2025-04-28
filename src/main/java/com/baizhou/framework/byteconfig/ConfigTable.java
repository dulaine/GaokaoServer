package com.baizhou.framework.byteconfig;

import com.baizhou.util.ByteParser;

import java.text.MessageFormat;
import java.util.Hashtable;

public class ConfigTable<T extends IByteConfig> {
    private Hashtable<Integer, T> configDic = new Hashtable<>();

    public void Load(byte[] bytes, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        ByteParser reader = new ByteParser(bytes);
        int rowNum = reader.ReadInt();
        //解析行
        for (int i = 0; i < rowNum; i++) {
            T configData = tClass.newInstance();
            configData.LoadRowBytes(reader);
            if (this.configDic.contains(configData.GetID())) {
                System.out.println(MessageFormat.format("配置表 %s ID %s 重复", configData.GetTableName(), configData.GetID()));
                continue;
            }
            this.configDic.put(configData.GetID(), configData);
        }

//        PrintTable();
    }


    //根据id获取
    public T GetByID(int id) {
        T data = this.configDic.get(id);
        return data;
    }

    //获取总数
    public Object[] GetAll() {
        return this.configDic.values().toArray();
    }

    //获取表数据总数
    public int GetCount() {
        return this.configDic.size();
    }

    public void PrintTable() {
        Object[] allRows = this.configDic.values().toArray();
        String tableString = "\n";
        for (int index = 0; index < allRows.length; index++) {
            IByteConfig row = (IByteConfig)allRows[index];
            if (index == 0) {
                tableString += "打印表 " + row.GetTableName() + "=========================================================================\n";
            }
            tableString += row.Print();
        }
        tableString += "表尾=========================================================================\n";
        System.out.println(tableString);
    }
}
