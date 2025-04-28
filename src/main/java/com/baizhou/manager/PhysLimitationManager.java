package com.baizhou.manager;

import com.baizhou.core.model.vo.MajorClsInfoVO;
import com.baizhou.core.model.vo.PhysicLimitationInfoVO;
import com.baizhou.db.DBProxy;
import com.msg.*;

import java.util.*;

public class PhysLimitationManager {
    private static PhysLimitationManager instance;

    public static PhysLimitationManager GetInstance() {
        if (instance == null) {
            instance = new PhysLimitationManager();
        }
        return instance;
    }

    List<com.msg.PhysicLimitation1stClsInfo> physicLimitationInfoList = null;

    HashMap<String, List<String>> type1Dic = new HashMap<>(); //1类专业, 对应的 专业名
    HashMap<String, List<String>> type2Dic = new HashMap<>();//2类专业, 对应的 专业名
    HashMap<Long, com.msg.PhysicLimitationInfo> type3Dic = new HashMap<>();//3类专业 <id, PhysicInfo>
    HashMap<String, HashMap<String, HashMap<String, PhysicLimitationInfoVO>>> physicLimitDic = new HashMap<>();



    public void InitDB(){

        List<PhysicLimitationInfoVO> list = DBProxy.Getinstance().PhysicLimitationInfoService.getAllPhysicLimitationInfos();

        for (int i = 0; i < list.size(); i++) {
            PhysicLimitationInfoVO vo = list.get(i);

            String major1stClsName = vo.getTitle();
            HashMap<String, HashMap<String, PhysicLimitationInfoVO>> firstClsDic = physicLimitDic.getOrDefault(major1stClsName, null);
            if (firstClsDic == null) {
                //没有保存 1stcls
                firstClsDic = new HashMap<>();
                physicLimitDic.put(major1stClsName, firstClsDic);
            }


            String major2ndClsName = vo.getTitle2();
            HashMap<String, PhysicLimitationInfoVO> secondClsDic = firstClsDic.getOrDefault(major2ndClsName, null);
            if (secondClsDic == null) {
                //没有保存2nd cls
                secondClsDic = new HashMap<>();
                firstClsDic.put(major2ndClsName, secondClsDic);
            }

            secondClsDic.put(vo.getType(), vo);




            String major3rdClsName = vo.getType();
            //保存1类 专业的,  子专业
            List<String> major1List = type1Dic.getOrDefault(major1stClsName, null);
            if(major1List == null){
                major1List = new ArrayList<>();
                type1Dic.put(major1stClsName, major1List);
            }
            major1List.add(major3rdClsName);

            //保存2类 专业的,  子专业
            List<String> major2List = type2Dic.getOrDefault(major2ndClsName, null);
            if(major2List == null){
                major2List = new ArrayList<>();
                type2Dic.put(major2ndClsName, major2List);
            }
            major2List.add(major3rdClsName);

        }


        physicLimitationInfoList = new ArrayList<>();
        for (Map.Entry<String, HashMap<String, HashMap<String, PhysicLimitationInfoVO>>> majorEntry : physicLimitDic.entrySet()) {
            String cls1name = majorEntry.getKey();
            HashMap<String, HashMap<String, PhysicLimitationInfoVO>> cls2Dic = majorEntry.getValue();

            com.msg.PhysicLimitation1stClsInfo.Builder major1stCls = com.msg.PhysicLimitation1stClsInfo.newBuilder(); //1大类
            major1stCls.setName(cls1name);

            List<com.msg.PhysicLimitation2ndClsInfo> majors2List = new ArrayList<>();
            for (Map.Entry<String, HashMap<String, PhysicLimitationInfoVO>> major2Entry : cls2Dic.entrySet()) {
                String cls2Name = major2Entry.getKey();
                HashMap<String, PhysicLimitationInfoVO> cls3Dic = major2Entry.getValue();

                com.msg.PhysicLimitation2ndClsInfo.Builder major2ndCls = com.msg.PhysicLimitation2ndClsInfo.newBuilder(); //2大类
                major2ndCls.setName(cls2Name);

                List<com.msg.PhysicLimitationInfo> majorsList = new ArrayList<>();
                for (Map.Entry<String, PhysicLimitationInfoVO> major3Entry : cls3Dic.entrySet()) {
                    String cls3Name = major3Entry.getKey();
                    PhysicLimitationInfoVO vo = major3Entry.getValue();
                    //3大类
                    com.msg.PhysicLimitationInfo.Builder major3 = vo.ConvertToDTO();
                    com.msg.PhysicLimitationInfo type3 = major3.build();
                    majorsList.add(type3);

                    type3Dic.put(vo.getId(), type3);
                }

                //排序
                majorsList.sort(new Comparator<com.msg.PhysicLimitationInfo>() {
                    @Override
                    public int compare(PhysicLimitationInfo o1, PhysicLimitationInfo o2) {
                        return ((Long) o1.getId()).compareTo((Long) o2.getId());
                    }
                });
                major2ndCls.addAllLimit3RdClsInfo(majorsList);


//                major1stCls.addMajor2NdClsInfo(major2ndCls);
                majors2List.add(major2ndCls.build());
            }

            majors2List.sort(new Comparator<PhysicLimitation2ndClsInfo>() {
                @Override
                public int compare(PhysicLimitation2ndClsInfo o1, PhysicLimitation2ndClsInfo o2) {
                    return ((Long)o1.getLimit3RdClsInfoList().get(0).getId()).compareTo(((Long)o2.getLimit3RdClsInfoList().get(0).getId())) ;
                }
            });
            major1stCls.addAllLimit2NdClsInfo(majors2List);

            physicLimitationInfoList.add(major1stCls.build());
        }


//        list.sort(new Comparator<UniMajorInfo>() {
//            @Override
//            public int compare(UniMajorInfo o1, UniMajorInfo o2) {
//                return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
//            }
//        });

        physicLimitationInfoList.sort(new Comparator<PhysicLimitation1stClsInfo>() {
            @Override
            public int compare(PhysicLimitation1stClsInfo o1, PhysicLimitation1stClsInfo o2) {
                return ((Long)o1.getLimit2NdClsInfo(0).getLimit3RdClsInfoList().get(0).getId()).compareTo(((Long)o2.getLimit2NdClsInfo(0).getLimit3RdClsInfoList().get(0).getId())) ;
            }
        });

    }


    public List<PhysicLimitation1stClsInfo> getLimits() {
        return physicLimitationInfoList;
    }


    //按id,获取体检限制
    public  PhysicLimitationInfo GetLimitByType3(Long limitId){

        PhysicLimitationInfo temp = type3Dic.getOrDefault(limitId, null);
        if(temp == null) {
            System.out.println("non physic litmit type3");
        }
        return temp;
    }

}
