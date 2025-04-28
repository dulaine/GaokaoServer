package com.baizhou.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * 将字节解析成int float utfString
 */
public class ByteParser {
    private static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
    ByteBuffer buffer = null;

    public ByteParser(byte[] bytes){
        buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        buffer.position(0);
    }

    public short ReadShort(){
        return buffer.getShort();
    }

    public int ReadInt(){
        return buffer.getInt();
    }

    public float ReadFloat(){
        return buffer.getFloat();
    }


    public String ReadUFTString() {
        int len = buffer.getShort();
        byte[] str = new byte[len];
        buffer.get(str);
        String result = new String(str, Charset.forName("UTF-8"));
        return result;
    }

}
