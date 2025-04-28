package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class ItemPosConfigRow implements IByteConfig {

    public int id;
    public int itemType2;
    public int scene;
    public String pos;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.itemType2 = reader.ReadInt();
        this.scene = reader.ReadInt();
        this.pos = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " itemType2: " + this.itemType2;
        log += " scene: " + this.scene;
        log += " pos: " + this.pos;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "ItemPosConfigRow";
    }
}
