package com.baizhou.gameutil;

import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.util.GameUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StringParser {


    //解析数字
    public static List<Integer> SplitNumber(String numbers, String splitter) {
        List<Integer> list = new ArrayList<>();
        if(StringUtils.isEmpty(numbers)) return list;
        String[] itemIdString = numbers.split(splitter);
        for (int i = 0; i < itemIdString.length; i++) {
            if (itemIdString[i].isEmpty()) continue;
            list.add((int) Float.parseFloat(itemIdString[i].trim()));
        }
        return list;
    }

    public static List<Integer> SplitInt(String numbers) {
        String[] itemIdString = numbers.split(ConstDefine.ItemSperator1Split);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < itemIdString.length; i++) {
            if (itemIdString[i].isEmpty()) continue;
            list.add(Integer.parseInt(itemIdString[i]));
        }
        return list;
    }

    //解析: 物品id_物品id
    public static List<Integer> ParseItems(String items) {
        String[] itemIdString = items.split(ConstDefine.ItemSperator3);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < itemIdString.length; i++) {
            if (itemIdString[i].isEmpty()) continue;
            list.add(Integer.parseInt(itemIdString[i]));
        }
        return list;
    }


    //解析: 物品id_物品id
    public static List<String> ParsePicUrl(String pic) {
        String[] itemIdString = pic.split(ConstDefine.ItemSperator1Split);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < itemIdString.length; i++) {
            if (itemIdString[i].isEmpty()) continue;
            list.add(itemIdString[i]);
        }
        return list;
    }

    //解析数字
    public static List<String> SplitString(String numbers, String splitter) {
        List<String> list = new ArrayList<>();
        if(StringUtils.isEmpty(numbers)) return list;
        String[] itemIdString = numbers.split(splitter);
        for (int i = 0; i < itemIdString.length; i++) {
            if (itemIdString[i].trim().isEmpty()) continue;
            list.add(itemIdString[i].trim());
        }
        return list;
    }

    public static List<String> SplitString(String numbers, String splitter, boolean incluEmpty) {
        List<String> list = new ArrayList<>();
        if(StringUtils.isEmpty(numbers)) return list;
        String[] itemIdString = numbers.split(splitter);
        for (int i = 0; i < itemIdString.length; i++) {
            if (itemIdString[i].trim().isEmpty() && !incluEmpty) continue;
            list.add(itemIdString[i].trim());
        }
        return list;
    }



    //解析数字
    public static List<Long> SplitLongNumber(String numbers, String splitter) {
        String[] itemIdString = numbers.split(splitter);
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < itemIdString.length; i++) {
            String longstri = itemIdString[i];
            if (longstri.isEmpty()) continue;
            if(longstri.contains(".")){
                int index = longstri.indexOf(".");
                longstri = longstri.substring(0, index);
                if(longstri.isEmpty()) longstri = "0";
                list.add(Long.parseLong(longstri));
            }else {
                list.add(Long.parseLong(longstri));
            }
//            if(Long.parseLong(longstri))
//            list.add((long) Float.parseFloat(itemIdString[i]));

        }
        return list;
    }


    public static String MakeLongString(List<Long> strs, String splitter) {
        String str = "";
        for (int i = 0; i < strs.size(); i++) {
            str += strs.get(i);
            if (i < strs.size() - 1) str += splitter;
        }

        return str;
    }

    public static String MakeString(List<String> pic, String splitter) {
        String str = "";
        for (int i = 0; i < pic.size(); i++) {
            str += pic.get(i);
            if (i < pic.size() - 1) str += splitter;
        }

        return str;
    }

    public static String MakeIntString(List<Integer> pic, String splitter) {
        String str = "";
        for (int i = 0; i < pic.size(); i++) {
            str += pic.get(i);
            if (i < pic.size() - 1) str += splitter;
        }

        return str;
    }

}
