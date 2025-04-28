package com.baizhou.framework.byteconfig;

import com.baizhou.util.ByteParser;

public interface IByteConfig {
    void LoadRowBytes( ByteParser reader);//加载一行配置表

    int GetID();

    //辅助方法
    String GetTableName();

    String Print();
}
