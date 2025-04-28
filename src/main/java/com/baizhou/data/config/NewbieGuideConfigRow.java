package com.baizhou.data.config;

import com.baizhou.framework.byteconfig.IByteConfig;
import com.baizhou.util.ByteParser;
public class NewbieGuideConfigRow implements IByteConfig {

    public int id;
    public int type;
    public String param;
    public String uiName;
    public String triggerUIName;
    public int triggerUIAction;
    public String clickTargetName;
    public String dialogInfo;
    public boolean recordStep;
    public boolean checkPrerequisteOnLogin;
    public String fingerPromptText;

    @Override
    public void LoadRowBytes(ByteParser reader) {
        
        this.id = (int)reader.ReadInt();
        this.type = reader.ReadInt();
        this.param = reader.ReadUFTString();
        this.uiName = reader.ReadUFTString();
        this.triggerUIName = reader.ReadUFTString();
        this.triggerUIAction = reader.ReadInt();
        this.clickTargetName = reader.ReadUFTString();
        this.dialogInfo = reader.ReadUFTString();
        this.recordStep = reader.ReadInt() > 0;
        this.checkPrerequisteOnLogin = reader.ReadInt() > 0;
        this.fingerPromptText = reader.ReadUFTString();

    }

    @Override
    public int GetID()  { return this.id; }//每个表必须有唯一的主键栏位


    @Override
    public String Print() {
         String log = "";
        log += " id: " + this.id;
        log += " type: " + this.type;
        log += " param: " + this.param;
        log += " uiName: " + this.uiName;
        log += " triggerUIName: " + this.triggerUIName;
        log += " triggerUIAction: " + this.triggerUIAction;
        log += " clickTargetName: " + this.clickTargetName;
        log += " dialogInfo: " + this.dialogInfo;
        log += " recordStep: " + this.recordStep;
        log += " checkPrerequisteOnLogin: " + this.checkPrerequisteOnLogin;
        log += " fingerPromptText: " + this.fingerPromptText;
        log += "\n";
        return log;
    }

    @Override
    public String GetTableName() {
        return "NewbieGuideConfigRow";
    }
}
