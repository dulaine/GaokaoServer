package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class RewardConfigRow implements IByteConfig {

    public int id;
    public int rewardType;
    public String rewardInfo;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.rewardType = reader.ReadInt();
        this.rewardInfo = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " rewardType: " + this.rewardType;
        log += " rewardInfo: " + this.rewardInfo;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "RewardConfigRow";
    }
}
