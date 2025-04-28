package com.baizhou.util;

import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.util.GameUtil;

import java.util.ArrayList;
import java.util.List;

public class StringParser {


    //解析数字
    public static List<Integer> SplitNumber(String numbers, String splitter){
        String[] itemIdString = numbers.split(splitter);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < itemIdString.length; i++) {
            if(itemIdString[i].isEmpty()) continue;
            list.add((int)Float.parseFloat(itemIdString[i].trim()));
        }
        return list;
    }

    //解析: 物品id_物品id
    public static List<Integer> ParseItems(String items) {
        String[] itemIdString = items.split(ConstDefine.ItemSperator3);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < itemIdString.length; i++) {
            if(itemIdString[i].isEmpty()) continue;
            list.add(Integer.parseInt(itemIdString[i]));
        }
        return list;
    }


}
