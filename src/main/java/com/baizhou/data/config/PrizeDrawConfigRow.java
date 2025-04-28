package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class PrizeDrawConfigRow implements IByteConfig {

    public int id;
    public float prob;
    public float fail10SingleDraw;
    public float fail1ContinueDraw;
    public int IsIndependentPro;
    public String rewardType;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.prob = reader.ReadFloat();
        this.fail10SingleDraw = reader.ReadFloat();
        this.fail1ContinueDraw = reader.ReadFloat();
        this.IsIndependentPro = reader.ReadInt();
        this.rewardType = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " prob: " + this.prob;
        log += " fail10SingleDraw: " + this.fail10SingleDraw;
        log += " fail1ContinueDraw: " + this.fail1ContinueDraw;
        log += " IsIndependentPro: " + this.IsIndependentPro;
        log += " rewardType: " + this.rewardType;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "PrizeDrawConfigRow";
    }
}
