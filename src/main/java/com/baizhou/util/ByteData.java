package com.baizhou.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ByteData {
    private List bytesList = null;
    public ByteData()
    {
        bytesList = new ArrayList();
    }
    public void Close()
    {
        bytesList.clear();
        bytesList = null;
    }

    public byte[] ToBytes()
    {
        byte[] bytes = new byte[bytesList.size()];
        for (int i = 0; i < bytesList.size(); i++) {
            bytes[i] = (byte)bytesList.get(i);
        }
        return bytes;
    }

    public void WriteBytes(byte[] bytes){
        for (int i = 0; i < bytes.length; i++) {
            bytesList.add(bytes[i]);
        }
    }


    public  void WriteString (String data) {
        byte[] stringBytes = ByteBufferUtil.getStringBytes(data, "UTF-8");
        WriteBytes(stringBytes);
    }

    public void WriteShort(short value){
        byte[] bytes = ByteBufferUtil.getShortBytes(value);
        WriteBytes(bytes);
    }

    public void WriteInt(int intValue){
        byte[] intBytes = ByteBufferUtil.getIntBytes(intValue);
        WriteBytes(intBytes);
    }
}
