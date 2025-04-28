package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class ShopConfigRow implements IByteConfig {

    public int id;
    public int itemId;
    public int priceType;
    public float price;
    public int shopType;
    public String itemName;
    public String desc;
    public String iconName;
    public String iconAtals;
    public String shopName;
    public int itemType;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.itemId = reader.ReadInt();
        this.priceType = reader.ReadInt();
        this.price = reader.ReadFloat();
        this.shopType = reader.ReadInt();
        this.itemName = reader.ReadUFTString();
        this.desc = reader.ReadUFTString();
        this.iconName = reader.ReadUFTString();
        this.iconAtals = reader.ReadUFTString();
        this.shopName = reader.ReadUFTString();
        this.itemType = reader.ReadInt();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " itemId: " + this.itemId;
        log += " priceType: " + this.priceType;
        log += " price: " + this.price;
        log += " shopType: " + this.shopType;
        log += " itemName: " + this.itemName;
        log += " desc: " + this.desc;
        log += " iconName: " + this.iconName;
        log += " iconAtals: " + this.iconAtals;
        log += " shopName: " + this.shopName;
        log += " itemType: " + this.itemType;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "ShopConfigRow";
    }
}
