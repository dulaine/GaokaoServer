package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class NPCDialogConfigRow implements IByteConfig {

    public int id;
    public String content;
    public String headerImgPath;
    public String headerImgAtlas;
    public String headerpos;
    public String hedersize;
    public int type;
    public int state;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.content = reader.ReadUFTString();
        this.headerImgPath = reader.ReadUFTString();
        this.headerImgAtlas = reader.ReadUFTString();
        this.headerpos = reader.ReadUFTString();
        this.hedersize = reader.ReadUFTString();
        this.type = reader.ReadInt();
        this.state = reader.ReadInt();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " content: " + this.content;
        log += " headerImgPath: " + this.headerImgPath;
        log += " headerImgAtlas: " + this.headerImgAtlas;
        log += " headerpos: " + this.headerpos;
        log += " hedersize: " + this.hedersize;
        log += " type: " + this.type;
        log += " state: " + this.state;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "NPCDialogConfigRow";
    }
}
