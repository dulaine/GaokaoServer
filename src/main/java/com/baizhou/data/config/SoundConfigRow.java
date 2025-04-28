package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class SoundConfigRow implements IByteConfig {

    public int index;
    public String URL;
    public float Type;
    public float Duration;
    public boolean Loop;
    public float PlayMode;
    public float PoolSize;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.index = (int)reader.ReadFloat();
        this.URL = reader.ReadUFTString();
        this.Type = reader.ReadFloat();
        this.Duration = reader.ReadFloat();
        this.Loop = reader.ReadInt() > 0;
        this.PlayMode = reader.ReadFloat();
        this.PoolSize = reader.ReadFloat();

    }

    @Override
    public int GetID()  { return this.index; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " index: " + this.index;
        log += " URL: " + this.URL;
        log += " Type: " + this.Type;
        log += " Duration: " + this.Duration;
        log += " Loop: " + this.Loop;
        log += " PlayMode: " + this.PlayMode;
        log += " PoolSize: " + this.PoolSize;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "SoundConfigRow";
    }
}
