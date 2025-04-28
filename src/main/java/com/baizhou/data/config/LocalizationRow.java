package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class LocalizationRow implements IByteConfig {

    public int index;
    public String CHN;
    public String ENG;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.index = (int)reader.ReadFloat();
        this.CHN = reader.ReadUFTString();
        this.ENG = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.index; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " index: " + this.index;
        log += " CHN: " + this.CHN;
        log += " ENG: " + this.ENG;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "LocalizationRow";
    }
}
