package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class AchievementPointRewardRow implements IByteConfig {

    public int id;
    public float rank;
    public float rewardId;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.rank = reader.ReadFloat();
        this.rewardId = reader.ReadFloat();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " rank: " + this.rank;
        log += " rewardId: " + this.rewardId;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "AchievementPointRewardRow";
    }
}
