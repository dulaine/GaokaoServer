package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class AdventureRewardConfigRow implements IByteConfig {

    public int id;
    public String rewardInfo;
    public int maxRewardCount;
    public String leastRewardInfo;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.rewardInfo = reader.ReadUFTString();
        this.maxRewardCount = reader.ReadInt();
        this.leastRewardInfo = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " rewardInfo: " + this.rewardInfo;
        log += " maxRewardCount: " + this.maxRewardCount;
        log += " leastRewardInfo: " + this.leastRewardInfo;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "AdventureRewardConfigRow";
    }
}
