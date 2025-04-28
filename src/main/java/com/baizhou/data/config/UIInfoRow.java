package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class UIInfoRow implements IByteConfig {

    public int index;
    public String UIID;
    public String atlas;
    public String[] unpackImagList;
    public float uiOpenAnimType;
    public float uiCloseAnimType;
    public float uiAutoCloseType;
    public float uiShowType;
    public float depth;
    public boolean showImageBG;
    public String imageBGPath;
    public String imageBGSizeGrid;
    public String UIWebPathPrefix;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.index = (int)reader.ReadFloat();
        this.UIID = reader.ReadUFTString();
        this.atlas = reader.ReadUFTString();
        short unpackImagListLen = reader.ReadShort();
        this.unpackImagList= new String [unpackImagListLen];
        for (int index = 0; index < unpackImagListLen; index++) {
            this.unpackImagList[index] = reader.ReadUFTString();
        }
        this.uiOpenAnimType = reader.ReadFloat();
        this.uiCloseAnimType = reader.ReadFloat();
        this.uiAutoCloseType = reader.ReadFloat();
        this.uiShowType = reader.ReadFloat();
        this.depth = reader.ReadFloat();
        this.showImageBG = reader.ReadInt() > 0;
        this.imageBGPath = reader.ReadUFTString();
        this.imageBGSizeGrid = reader.ReadUFTString();
        this.UIWebPathPrefix = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.index; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " index: " + this.index;
        log += " UIID: " + this.UIID;
        log += " atlas: " + this.atlas;
        log += " unpackImagList: ";
        int unpackImagListcount = this.unpackImagList.length;
        for (int t = 0; t < unpackImagListcount; t++)
        {
            log += this.unpackImagList[t];
            if (t < unpackImagListcount - 1) log += ",";
        }
        log += " uiOpenAnimType: " + this.uiOpenAnimType;
        log += " uiCloseAnimType: " + this.uiCloseAnimType;
        log += " uiAutoCloseType: " + this.uiAutoCloseType;
        log += " uiShowType: " + this.uiShowType;
        log += " depth: " + this.depth;
        log += " showImageBG: " + this.showImageBG;
        log += " imageBGPath: " + this.imageBGPath;
        log += " imageBGSizeGrid: " + this.imageBGSizeGrid;
        log += " UIWebPathPrefix: " + this.UIWebPathPrefix;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "UIInfoRow";
    }
}
