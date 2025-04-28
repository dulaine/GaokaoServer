package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class UnlockCondConfigRow implements IByteConfig {

    public int id;
    public String condition;
    public float param;
    public String desc;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.condition = reader.ReadUFTString();
        this.param = reader.ReadFloat();
        this.desc = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " condition: " + this.condition;
        log += " param: " + this.param;
        log += " desc: " + this.desc;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "UnlockCondConfigRow";
    }
}
