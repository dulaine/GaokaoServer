package com.baizhou.manager;

import com.baizhou.core.model.vo.MajorClsInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;
import com.baizhou.gameutil.StringParser;
import com.msg.Major1stClsInfo;
import com.msg.Major2ndClsInfo;
import com.msg.Major3rdClsInfo;
import org.springframework.util.StringUtils;

import java.util.*;

public class MajorClsManager {
    private static MajorClsManager instance;

    public static MajorClsManager GetInstance() {
        if (instance == null) {
            instance = new MajorClsManager();
        }
        return instance;
    }

    HashMap<String, HashMap<String, HashMap<String, MajorClsInfoVO>>> majorDic = new HashMap<>();
//    HashMap<String, com.msg.Major1stClsInfo.Builder> majorProtoDic = new HashMap<>();

    List<com.msg.Major1stClsInfo> major1stClsInfoList = null;


    HashMap<String, List<String>> major1Dic = new HashMap<>(); //1类专业, 对应的 专业名
    HashMap<String, List<String>> major2Dic = new HashMap<>();//2类专业, 对应的 专业名

    HashMap<String, MajorClsInfoVO> major3ByNameDic = new HashMap<>();//<专业名,  VO>

    HashMap<Long, MajorClsInfoVO> major3ByIdDic = new HashMap<>();//<专业名Id,  VO>
    HashMap<Long, List<String>> major3PhysicLimitByIdDic = new HashMap<>();//<专业Id,  List of 体检限报Type3>
    HashMap<Long, Integer> major3PhysicLimitColorByIdDic = new HashMap<>();//<专业Id,  显示颜色  数字最小的颜色>
    HashMap<Long, List<Integer>> major3PhysicLimitColorListByIdDic = new HashMap<>();//<专业Id,  List of 颜色数字>


    public List<com.msg.Major1stClsInfo> findMajorCls() {

        if (major1stClsInfoList != null) return major1stClsInfoList;

        List<MajorClsInfoVO> list = DBProxy.Getinstance().MajorClsInfoService.getAllMajorClsInfos();

        for (int i = 0; i < list.size(); i++) {
            MajorClsInfoVO vo = list.get(i);

            String major1stClsName = vo.getMajor1stCls();
            HashMap<String, HashMap<String, MajorClsInfoVO>> firstClsDic = majorDic.getOrDefault(major1stClsName, null);
            if (firstClsDic == null) {
                //没有保存 1stcls
                firstClsDic = new HashMap<>();
                majorDic.put(major1stClsName, firstClsDic);
            }


            String major2ndClsName = vo.getMajor2ndCls();
            HashMap<String, MajorClsInfoVO> secondClsDic = firstClsDic.getOrDefault(major2ndClsName, null);
            if (secondClsDic == null) {
                //没有保存2nd cls
                secondClsDic = new HashMap<>();
                firstClsDic.put(major2ndClsName, secondClsDic);
            }


            secondClsDic.put(vo.getMajor3rdCls(), vo);


            String major3rdClsName = vo.getMajor3rdCls();
            //保存1类 专业的,  子专业
            List<String> major1List = major1Dic.getOrDefault(major1stClsName, null);
            if(major1List == null){
                major1List = new ArrayList<>();
                major1Dic.put(major1stClsName, major1List);
            }
            major1List.add(major3rdClsName);

            //保存2类 专业的,  子专业
            List<String> major2List = major2Dic.getOrDefault(major2ndClsName, null);
            if(major2List == null){
                major2List = new ArrayList<>();
                major2Dic.put(major2ndClsName, major2List);
            }
            major2List.add(major3rdClsName);


            //保存3类
            major3ByNameDic.put(major3rdClsName, vo);


            Long majorId = vo.getId();
            major3ByIdDic.put(majorId, vo);

            //获取对应的  体检限报 类型
            List<String> limits = StringParser.SplitString(vo.getPhysicLimits(), ConstDefine.ItemSperator7);
            major3PhysicLimitByIdDic.put(majorId, limits);
            //获取对应的  体检限报 颜色
//            List<Integer> colors = new ArrayList<>();
//            for (int j = 0; j < limits.size(); j++) {
//                String temp = limits.get(j);
//                if(temp.startsWith("一")){
//                    colors.add(1);
//                }else if(temp.startsWith("二")){
//                    colors.add(2);
//                }else {
//                    colors.add(3);
//                }
//            }
            List<Integer> colorList = new ArrayList<>();
            Integer colors = -1;
            for (int j = 0; j < limits.size(); j++) {
                String temp = limits.get(j);
                if(StringUtils.isEmpty(temp)) continue;

                List<String> tempSplits = StringParser.SplitString(temp, ConstDefine.ItemSperator4);
                if(tempSplits.size() < 2) continue;
                Integer num = Integer.parseInt(tempSplits.get(1)); //保存数字
                //选择最大的那个颜色
                if(temp.startsWith("一")){
                    colorList.add(1*10 + num);
                    if(colors < 0 || colors > 1) colors = 1;
                }else if(temp.startsWith("二")){
                    colorList.add(2*10 + num);
                    if(colors < 0 ||colors > 2) colors = 2;
                }else {
                    colorList.add(3*10 + num);
                    if(colors < 0 ||colors > 3) colors = 3;
                }
            }
            major3PhysicLimitColorByIdDic.put(majorId, colors);
            major3PhysicLimitColorListByIdDic.put(majorId, colorList);
        }



        major1stClsInfoList = new ArrayList<>();
        for (Map.Entry<String, HashMap<String, HashMap<String, MajorClsInfoVO>>> majorEntry : majorDic.entrySet()) {
            String cls1name = majorEntry.getKey();
            HashMap<String, HashMap<String, MajorClsInfoVO>> cls2Dic = majorEntry.getValue();

            com.msg.Major1stClsInfo.Builder major1stCls = com.msg.Major1stClsInfo.newBuilder(); //1大类
            major1stCls.setName(cls1name);

            List<com.msg.Major2ndClsInfo> majors2List = new ArrayList<>();
            for (Map.Entry<String, HashMap<String, MajorClsInfoVO>> major2Entry : cls2Dic.entrySet()) {
                String cls2Name = major2Entry.getKey();
                HashMap<String, MajorClsInfoVO> cls3Dic = major2Entry.getValue();

                com.msg.Major2ndClsInfo.Builder major2ndCls = com.msg.Major2ndClsInfo.newBuilder(); //2大类
                major2ndCls.setName(cls2Name);

                List<com.msg.Major3rdClsInfo> majorsList = new ArrayList<>();
                for (Map.Entry<String, MajorClsInfoVO> major3Entry : cls3Dic.entrySet()) {
                    String cls3Name = major3Entry.getKey();
                    MajorClsInfoVO vo = major3Entry.getValue();
                    //3大类
                    com.msg.Major3rdClsInfo.Builder major3 = vo.ConvertToDTO();
//                    major2ndCls.addMajor3RdClsInfo(major3);
                    majorsList.add(major3.build());
                }

                //排序
                majorsList.sort(new Comparator<Major3rdClsInfo>() {
                    @Override
                    public int compare(Major3rdClsInfo o1, Major3rdClsInfo o2) {
                        return ((Long) o1.getId()).compareTo((Long) o2.getId());
                    }
                });
                major2ndCls.addAllMajor3RdClsInfo(majorsList);


//                major1stCls.addMajor2NdClsInfo(major2ndCls);
                majors2List.add(major2ndCls.build());
            }

            majors2List.sort(new Comparator<Major2ndClsInfo>() {
                @Override
                public int compare(Major2ndClsInfo o1, Major2ndClsInfo o2) {
                    return ((Long)o1.getMajor3RdClsInfoList().get(0).getId()).compareTo(((Long)o2.getMajor3RdClsInfoList().get(0).getId())) ;
                }
            });
            major1stCls.addAllMajor2NdClsInfo(majors2List);

            major1stClsInfoList.add(major1stCls.build());
        }


//        list.sort(new Comparator<UniMajorInfo>() {
//            @Override
//            public int compare(UniMajorInfo o1, UniMajorInfo o2) {
//                return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
//            }
//        });

        major1stClsInfoList.sort(new Comparator<Major1stClsInfo>() {
            @Override
            public int compare(Major1stClsInfo o1, Major1stClsInfo o2) {
                return ((Long)o1.getMajor2NdClsInfo(0).getMajor3RdClsInfoList().get(0).getId()).compareTo(((Long)o2.getMajor2NdClsInfo(0).getMajor3RdClsInfoList().get(0).getId())) ;
            }
        });


        return major1stClsInfoList;


//        m_YFYDDic.put(key, yfydvoDictionary);
    }


    private  List<String> empty = new ArrayList<>();
    public List<String> GetMajorNamesByCls(String clsName){
        MajorClsManager.GetInstance().findMajorCls();

        List<String> major1List = major1Dic.getOrDefault(clsName, null);
        if(major1List == null){
            List<String> major2List = major2Dic.getOrDefault(clsName, null);
            if(major2List == null){
                return empty;
            }else {
                return  major2List;
            }
        }else {
            return  major1List;
        }
    }

    public List<String> GetMajorNamesByCls2(String clsName){
        MajorClsManager.GetInstance().findMajorCls();

//        List<String> major1List = null;//major1Dic.getOrDefault(clsName, null);
//        if(major1List == null){
            List<String> major2List = major2Dic.getOrDefault(clsName, null);
            if(major2List == null){
                return empty;
            }else {
                return  major2List;
            }
//        }else {
//            return  major1List;
//        }
    }


    //根据专业名  获取VO
    public MajorClsInfoVO GetMajorVOByName(String clsName){
        MajorClsManager.GetInstance().findMajorCls();

        MajorClsInfoVO major2List = major3ByNameDic.getOrDefault(clsName, null);

            return  major2List;

    }

    //根据专业名  获取VO
    public MajorClsInfoVO GetMajorVOById(Long clsName){
        MajorClsInfoVO major2List = major3ByIdDic.getOrDefault(clsName, null);
        return  major2List;

    }

    //获取专业名 , 对应的  体检限制类型
    public List<String> GetPhysicLimitByMajrName(Long clsName){
//        MajorClsManager.GetInstance().findMajorCls();
        List<String> major2List = major3PhysicLimitByIdDic.getOrDefault(clsName, null);
        return  major2List;

    }
    public Integer GetPhysicLimitColorByMajrName(Long clsName){
//        MajorClsManager.GetInstance().findMajorCls();
        Integer color = major3PhysicLimitColorByIdDic.getOrDefault(clsName, null);
        if(color == null || color < 0) return  null;
        return  color;

    }

    public List<Integer> GetPhysicLimitColorListByMajorID(Long clsName){
        List<Integer> color = major3PhysicLimitColorListByIdDic.getOrDefault(clsName, null);
        if(color == null || color.size() == 0) return  null;
        return  color;
    }

}
