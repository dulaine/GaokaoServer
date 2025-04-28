package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class AchievementConfigRow implements IByteConfig {

    public int id;
    public String desc;
    public String param;
    public int showInUI;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.desc = reader.ReadUFTString();
        this.param = reader.ReadUFTString();
        this.showInUI = reader.ReadInt();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " desc: " + this.desc;
        log += " param: " + this.param;
        log += " showInUI: " + this.showInUI;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "AchievementConfigRow";
    }
}
