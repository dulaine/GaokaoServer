package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class DailyTaskConfigRow implements IByteConfig {

    public int id;
    public String desc;
    public float param;
    public int rewardId;
    public int duration;
    public int triggerType;
    public String triggerDesc;
    public String iconName;
    public String iconAtlas;
    public float showCount;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.desc = reader.ReadUFTString();
        this.param = reader.ReadFloat();
        this.rewardId = reader.ReadInt();
        this.duration = reader.ReadInt();
        this.triggerType = reader.ReadInt();
        this.triggerDesc = reader.ReadUFTString();
        this.iconName = reader.ReadUFTString();
        this.iconAtlas = reader.ReadUFTString();
        this.showCount = reader.ReadFloat();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " desc: " + this.desc;
        log += " param: " + this.param;
        log += " rewardId: " + this.rewardId;
        log += " duration: " + this.duration;
        log += " triggerType: " + this.triggerType;
        log += " triggerDesc: " + this.triggerDesc;
        log += " iconName: " + this.iconName;
        log += " iconAtlas: " + this.iconAtlas;
        log += " showCount: " + this.showCount;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "DailyTaskConfigRow";
    }
}
