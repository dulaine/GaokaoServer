package com.baizhou.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 读取int .flot string的字节
 */
public class ByteBufferUtil {
    private static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
    /**
     * short 转 byte[]
     * 大端
     * @param data
     * @return
     */
    public static byte[] getShortBytes(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(BYTE_ORDER);
        buffer.putShort(data);
        byte[] bytes = buffer.array();
        return bytes;
    }

    /**
     * chart 转 byte[]
     * 大端
     * @param data
     * @return
     */
    public static byte[] getCharBytes(char data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(BYTE_ORDER);
        buffer.putChar(data);
        byte[] bytes = buffer.array();
        return bytes;
    }

    /**
     * int 转 byte[]
     * 大端
     * @param data
     * @return
     */
    public static byte[] getIntBytes(int data) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(BYTE_ORDER);
        buffer.putInt(data);
        byte[] bytes = buffer.array();
        return bytes;
    }

    /**
     * long 转 byte[]
     * 大端
     * @param data
     * @return
     */
    public static byte[] getLongBytes(long data) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(BYTE_ORDER);
        buffer.putLong(data);
        byte[] bytes = buffer.array();
        return bytes;
    }

    /**
     * float 转 byte[]
     * 大端
     * @param data
     * @return
     */
    public static byte[] getFloatBytes(float data) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(BYTE_ORDER);
        buffer.putFloat(data);
        byte[] bytes = buffer.array();
        return bytes;
    }

    /**
     * double 转 byte[]
     * 大端
     * @param data
     * @return
     */
    public static byte[] getDoubleBytes(double data) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(BYTE_ORDER);
        buffer.putDouble(data);
        byte[] bytes = buffer.array();
        return bytes;
    }

    /**
     * String 转 byte[]
     *
     * @param data
     * @param charsetName  GB2312、GBK、UTF-16、UTF-8
     * @return
     */
    public static byte[] getStringBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        byte[] bytes = data.getBytes(charset);
        return bytes;
    }

    /**
     * String 转 byte[]
     *
     * @param data
     * @return
     */
    public static byte[] getStringBytes(String data) {
        byte[] bytes = null;
        if(data != null){
            bytes = data.getBytes();
        }else{
            bytes = new byte[0];
        }
        return bytes;
    }

    /*****************************************************************************************************************************/

    /**
     * byte[] 转short
     * 大端
     * @param bytes
     * @return
     */
    public static short getShort(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        short result = buffer.getShort(0);
        return result;
    }

    /**
     * byte[] 转 char
     * 大端
     * @param bytes
     * @return
     */
    public static char getChar(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        char result = buffer.getChar(0);
        return result;
    }

    /**
     * byte[] 转 int
     * 大端
     * @param bytes
     * @return
     */
    public static int getInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        int result = buffer.getInt(0);
        return result;
    }

    /**
     * byte[] 转 long
     *
     * @param bytes
     * @return
     */
    public static long getLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        long result = buffer.getLong(0);
        return result;
    }

    /**
     * byte[] 转 float
     *
     * @param bytes
     * @return
     */
    public static float getFloat(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        float result = buffer.getFloat(0);
        return result;
    }

    /**
     * byte[] 转 double
     *
     * @param bytes
     * @return
     */
    public static double getDouble(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(BYTE_ORDER);
        buffer.put(bytes);
        double result = buffer.getDouble(0);
        return result;
    }

    /**
     * byte[] 转 String
     *
     * @param bytes
     * @param charsetName
     * @return
     */
    public static String getString(byte[] bytes, String charsetName) {
        String result = new String(bytes, Charset.forName(charsetName));
        return result;
    }

    /**
     * byte[] 转 String
     *
     * @param bytes
     * @return
     */
    public static String getString(byte[] bytes) {
        String result = new String(bytes);
        return result;
    }
}
