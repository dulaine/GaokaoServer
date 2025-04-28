package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class CoinSpawnConfigRow implements IByteConfig {

    public int id;
    public int funcId;
    public float refreshDuration;
    public String pileNum;
    public String coinNum;
    public float maxExistingNumType;
    public int maxExistingNum;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.funcId = reader.ReadInt();
        this.refreshDuration = reader.ReadFloat();
        this.pileNum = reader.ReadUFTString();
        this.coinNum = reader.ReadUFTString();
        this.maxExistingNumType = reader.ReadFloat();
        this.maxExistingNum = reader.ReadInt();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " funcId: " + this.funcId;
        log += " refreshDuration: " + this.refreshDuration;
        log += " pileNum: " + this.pileNum;
        log += " coinNum: " + this.coinNum;
        log += " maxExistingNumType: " + this.maxExistingNumType;
        log += " maxExistingNum: " + this.maxExistingNum;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "CoinSpawnConfigRow";
    }
}
