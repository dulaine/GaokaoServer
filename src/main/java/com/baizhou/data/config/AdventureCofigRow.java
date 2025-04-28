package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class AdventureCofigRow implements IByteConfig {

    public int id;
    public float duration;
    public float probability;
    public float adventureRewardId;
    public String events;
    public int coinSpawnConfigId;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.duration = reader.ReadFloat();
        this.probability = reader.ReadFloat();
        this.adventureRewardId = reader.ReadFloat();
        this.events = reader.ReadUFTString();
        this.coinSpawnConfigId = reader.ReadInt();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " duration: " + this.duration;
        log += " probability: " + this.probability;
        log += " adventureRewardId: " + this.adventureRewardId;
        log += " events: " + this.events;
        log += " coinSpawnConfigId: " + this.coinSpawnConfigId;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "AdventureCofigRow";
    }
}
