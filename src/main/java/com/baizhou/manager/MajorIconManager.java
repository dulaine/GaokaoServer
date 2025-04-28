package com.baizhou.manager;


import com.baizhou.core.model.vo.IconMajorInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.msg.IconMajorInfo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MajorIconManager {

    private static MajorIconManager instance;

    public static MajorIconManager GetInstance() {
        if (instance == null) {
            instance = new MajorIconManager();
        }
        return instance;
    }

//    HashMap<String, HashMap<String, HashMap<String, MajorClsInfoVO>>> majorDic = new HashMap<>();


    public List<IconMajorInfo> GetByIcon(String schoolName, String majorClsId) {

        List<IconMajorInfoVO> list = null;
        if (majorClsId.equals("2")) {
            //硕士点
          list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls2IsNot(schoolName, "");
        } else if (majorClsId.equals("3")) {
            //双一流
            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls3IsNot(schoolName, "");
        } else if (majorClsId.equals("4")) {
//卓越
            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls4IsNot(schoolName, "");
        } else if (majorClsId.equals("5")) {
//双玩
            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls5IsNot(schoolName, "");
        } else if (majorClsId.equals("6")) {
//重点学科
            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls6IsNot(schoolName, "");
        } else if (majorClsId.equals("7")) {
            //特色学科
            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls7IsNot(schoolName, "");
        } else if (majorClsId.equals("8")) {
//其他
            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls8IsNot(schoolName, "");
//        } else if (majorClsId.equals("9")) {
        } else  {
            //A + B
//            list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls1IsNot(schoolName, "");
            if(StringUtils.isEmpty(majorClsId)){
                list = new ArrayList<>();
            }else {
                Integer id = Integer.parseInt(majorClsId);
                Integer idx = id - 9;
                if(idx <  ConstDefine.ClassLvl.length){
                    list = DBProxy.Getinstance().IconMajorInfoService.findBySchoolNameAndCls1Is(schoolName, ConstDefine.ClassLvl[idx]);
                }
            }


        }

        List<IconMajorInfo> proto = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            proto.add(list.get(i).ConvertToDTO());
        }
        return proto;

    }


}
