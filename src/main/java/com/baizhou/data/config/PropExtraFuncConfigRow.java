package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class PropExtraFuncConfigRow implements IByteConfig {

    public int id;
    public String param;
    public String desc;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.param = reader.ReadUFTString();
        this.desc = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " param: " + this.param;
        log += " desc: " + this.desc;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "PropExtraFuncConfigRow";
    }
}
