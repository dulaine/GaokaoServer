package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class AdventureEventTriggerConfigRow implements IByteConfig {

    public int id;
    public String mats;
    public String eventId;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.mats = reader.ReadUFTString();
        this.eventId = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " mats: " + this.mats;
        log += " eventId: " + this.eventId;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "AdventureEventTriggerConfigRow";
    }
}
