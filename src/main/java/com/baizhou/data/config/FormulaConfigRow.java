package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class FormulaConfigRow implements IByteConfig {

    public int id;
    public String materialItemIds;
    public float targetItemId;
    public float type;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.materialItemIds = reader.ReadUFTString();
        this.targetItemId = reader.ReadFloat();
        this.type = reader.ReadFloat();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " materialItemIds: " + this.materialItemIds;
        log += " targetItemId: " + this.targetItemId;
        log += " type: " + this.type;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "FormulaConfigRow";
    }
}
