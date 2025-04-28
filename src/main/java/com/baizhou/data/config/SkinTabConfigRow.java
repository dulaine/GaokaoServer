package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class SkinTabConfigRow implements IByteConfig {

    public int id;
    public String tabiconname;
    public String tabiconatlas;
    public String subItemTypeString;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.tabiconname = reader.ReadUFTString();
        this.tabiconatlas = reader.ReadUFTString();
        this.subItemTypeString = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " tabiconname: " + this.tabiconname;
        log += " tabiconatlas: " + this.tabiconatlas;
        log += " subItemTypeString: " + this.subItemTypeString;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "SkinTabConfigRow";
    }
}
