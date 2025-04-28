package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class CollectionConfigRow implements IByteConfig {

    public int id;
    public String unlockCondConfigId;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.unlockCondConfigId = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " unlockCondConfigId: " + this.unlockCondConfigId;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "CollectionConfigRow";
    }
}
