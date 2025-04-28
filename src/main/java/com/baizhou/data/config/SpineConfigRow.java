package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class SpineConfigRow implements IByteConfig {

    public int id;
    public String SpinePath;
    public boolean Loop;
    public float PoolSize;
    public float[] SpineSize;
    public float[] SpinePivot;
    public float Duration;
    public float ReplayMode;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadFloat();
        this.SpinePath = reader.ReadUFTString();
        this.Loop = reader.ReadInt() > 0;
        this.PoolSize = reader.ReadFloat();
        short SpineSizeLen = reader.ReadShort();
        this.SpineSize= new float [SpineSizeLen];
        for (int index = 0; index < SpineSizeLen; index++) {
            this.SpineSize[index] = reader.ReadFloat();
        }
        short SpinePivotLen = reader.ReadShort();
        this.SpinePivot= new float [SpinePivotLen];
        for (int index = 0; index < SpinePivotLen; index++) {
            this.SpinePivot[index] = reader.ReadFloat();
        }
        this.Duration = reader.ReadFloat();
        this.ReplayMode = reader.ReadFloat();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " SpinePath: " + this.SpinePath;
        log += " Loop: " + this.Loop;
        log += " PoolSize: " + this.PoolSize;
        log += " SpineSize: ";
        int SpineSizecount = this.SpineSize.length;
        for (int t = 0; t < SpineSizecount; t++)
        {
            log += this.SpineSize[t];
            if (t < SpineSizecount - 1) log += ",";
        }
        log += " SpinePivot: ";
        int SpinePivotcount = this.SpinePivot.length;
        for (int t = 0; t < SpinePivotcount; t++)
        {
            log += this.SpinePivot[t];
            if (t < SpinePivotcount - 1) log += ",";
        }
        log += " Duration: " + this.Duration;
        log += " ReplayMode: " + this.ReplayMode;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "SpineConfigRow";
    }
}
