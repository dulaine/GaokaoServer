package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class InvitationConfigRow implements IByteConfig {

    public int id;
    public String iconName;
    public String iconAtals;
    public int rewardId;
    public String acivityName;
    public String activityDesc;
    public int isShowOnUI;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.iconName = reader.ReadUFTString();
        this.iconAtals = reader.ReadUFTString();
        this.rewardId = reader.ReadInt();
        this.acivityName = reader.ReadUFTString();
        this.activityDesc = reader.ReadUFTString();
        this.isShowOnUI = reader.ReadInt();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " iconName: " + this.iconName;
        log += " iconAtals: " + this.iconAtals;
        log += " rewardId: " + this.rewardId;
        log += " acivityName: " + this.acivityName;
        log += " activityDesc: " + this.activityDesc;
        log += " isShowOnUI: " + this.isShowOnUI;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "InvitationConfigRow";
    }
}
