package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class ItemConfigRow implements IByteConfig {

    public int id;
    public String iconName;
    public String iconAtals;
    public String spineSkinName;
    public String spineSkinAtals;
    public int type1;
    public int type2;
    public int itemState;
    public int grade;
    public int suite;
    public String detail;
    public String itemName;
    public String itemDesc;
    public String extraFuncIds;
    public float coinExchangValue;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.iconName = reader.ReadUFTString();
        this.iconAtals = reader.ReadUFTString();
        this.spineSkinName = reader.ReadUFTString();
        this.spineSkinAtals = reader.ReadUFTString();
        this.type1 = reader.ReadInt();
        this.type2 = reader.ReadInt();
        this.itemState = reader.ReadInt();
        this.grade = reader.ReadInt();
        this.suite = reader.ReadInt();
        this.detail = reader.ReadUFTString();
        this.itemName = reader.ReadUFTString();
        this.itemDesc = reader.ReadUFTString();
        this.extraFuncIds = reader.ReadUFTString();
        this.coinExchangValue = reader.ReadFloat();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " iconName: " + this.iconName;
        log += " iconAtals: " + this.iconAtals;
        log += " spineSkinName: " + this.spineSkinName;
        log += " spineSkinAtals: " + this.spineSkinAtals;
        log += " type1: " + this.type1;
        log += " type2: " + this.type2;
        log += " itemState: " + this.itemState;
        log += " grade: " + this.grade;
        log += " suite: " + this.suite;
        log += " detail: " + this.detail;
        log += " itemName: " + this.itemName;
        log += " itemDesc: " + this.itemDesc;
        log += " extraFuncIds: " + this.extraFuncIds;
        log += " coinExchangValue: " + this.coinExchangValue;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "ItemConfigRow";
    }
}
