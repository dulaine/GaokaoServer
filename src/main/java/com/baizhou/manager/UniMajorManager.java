package com.baizhou.manager;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.entity.UniMajorInfoALatest;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumGender;
import com.baizhou.data.enumdefine.EnumLastYear;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.db.DBProxy;
import com.baizhou.util.GameUtil;
import com.msg.UniMajorInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class UniMajorManager {

    public Map<String, Object> mapForA = null;


    private static UniMajorManager instance;

    public static UniMajorManager GetInstance() {
        if (instance == null) {
            instance = new UniMajorManager();
        }
        return instance;
    }

    public List<UniMajorInfo> GetInHisDB(List<Long> id, EnumPici pici) {

        List<UniMajorInfo> protoList = new ArrayList<>();

        switch (pici) {

            case A: {
                List<UniMajorInfoAVO> list = DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(id);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

            }
            break;
            case B: {
                List<UniMajorInfoBVO> list = DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(id);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

            }
            break;
            case PreA: {
                List<UniMajorInfoPreAVO> list = DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(id);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

            }
            break;
            case PreB: {
                List<UniMajorInfoPreBVO> list = DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(id);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

                break;
            }
            case ZhuanKe:
            case ZXA:
            case ZXB:{

                List<UniMajorInfoPreBVO> list = DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(id);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

                break;
            }

        }
        return protoList;
    }


    public Map<String, Object> GetUniMajorByPage(EnumPici pici,
                                                 List<String> schoolNames, //搜索学校名
                                                 Integer is1stcls, Integer is985, Integer is211,
                                                 List<String> schoolType, //院校类型： 综合, 理工、综合、师范、医药

                                                 List<String> provs,  //省份
                                                 List<String> majorNames,   //搜索专业名

                                                 Integer minScore, Integer maxScore, //预估分数
                                                 List<String> examSubj,   //科目 list
                                                 List<String> majorNames2,

                                                 int page, int limit, String year , EnumGender gender) {

        boolean saveForA = false;
//                pici == EnumPici.A && schoolNames.size() == 0 && is1stcls == null && is985 == null && is211== null
//                && schoolType.size() == 0 && provs.size() == 0 && majorNames.size() == 0 && examSubj.size() == 0
//                && minScore == 0 && maxScore >= 740;
        if (saveForA && mapForA != null) {
            return mapForA;
        }


        //转换科目
        List<String> subj = CommonUtil.ConvSubj(examSubj);

        //转换位次
        Integer minRank = maxScore;// RankManager.GetInstance().GetRankByScore(minScore);
        Integer maxRank = minScore;// RankManager.GetInstance().GetRankByScore(maxScore);

//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.DESC, "scoreFor22")); //排序  专业组分数从高到低
//        orders.add(new Sort.Order(Sort.Direction.DESC, "major22LowScore")); //排序  专业组分数从高到低
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));
        Pageable pageable = PageRequest.of(page - 1, limit);

        Map<String, Object> map = null;
        switch (pici) {

            case A: {

                List<String> groupCode = new ArrayList<>();
                for (int i = 0; i < majorNames.size(); i++) {
                    List<UniMajorInfoALatestVO> list = DBProxy.Getinstance().UniMajorInfoALatestService.findByMajorNameLike(majorNames.get(i));
                    for (int j = 0; j < list.size(); j++) {
                        String uniMajorCode = list.get(j).getUniMajorCode();
                        if (groupCode.contains(uniMajorCode)) continue;
                        groupCode.add(uniMajorCode);
                    }
                }


                map = DBProxy.Getinstance().UniMajorInfoALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, new ArrayList<>(),
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,
                        0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");
                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);

                List<com.msg.UniMajorInfo> allList = new ArrayList<>();
                List<UniMajorInfoALatestVO> tempList = DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数
//                Long in5 = new Date().getTime();
//                System.out.println("4 conver to proto  " + (in5 - in4) / 1000f);
                return data;


//                if (saveForA && mapForA == null) {
//                    mapForA = map;
//                }


            }
//            break;
            case B: {
                List<String> groupCode = new ArrayList<>();
                for (int i = 0; i < majorNames.size(); i++) {
                    List<UniMajorInfoBLatestVO> list = DBProxy.Getinstance().UniMajorInfoBLatestService.findByMajorNameLike(majorNames.get(i));
                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
                        String uniMajorCode = list.get(j).getUniMajorCode();
                        if (groupCode.contains(uniMajorCode)) continue;
                        groupCode.add(uniMajorCode);
                    }
                }

                map = DBProxy.Getinstance().UniMajorInfoBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, new ArrayList<>(),
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);
            }
            break;
            case PreA: {
                List<String> groupCode = new ArrayList<>();
                for (int i = 0; i < majorNames.size(); i++) {
                    List<UniMajorInfoPreALatestVO> list = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByMajorNameLike(majorNames.get(i));
                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
                        String uniMajorCode = list.get(j).getUniMajorCode();
                        if (groupCode.contains(uniMajorCode)) continue;
                        groupCode.add(uniMajorCode);
                    }
                }

                map = DBProxy.Getinstance().UniMajorInfoPreALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, new ArrayList<>(),
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);

            }
            break;
            case PreB: {
                List<String> groupCode = new ArrayList<>();
                for (int i = 0; i < majorNames.size(); i++) {
                    List<UniMajorInfoPreBLatestVO> list = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByMajorNameLike(majorNames.get(i));
                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
                        String uniMajorCode = list.get(j).getUniMajorCode();
                        if (groupCode.contains(uniMajorCode)) continue;
                        groupCode.add(uniMajorCode);
                    }
                }

                map = DBProxy.Getinstance().UniMajorInfoPreBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, new ArrayList<>(),
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);
            }
            break;
        }


        return map;
    }


    public Map<String, Object> GetUniMajorByList(EnumPici pici,
                                                 List<String> schoolNames, //搜索学校名
                                                 Integer is1stcls, Integer is985, Integer is211,
                                                 List<String> schoolType, //院校类型： 综合, 理工、综合、师范、医药

                                                 List<String> provs,  //省份
                                                 List<String> majorNames,   //搜索专业名

                                                 Integer minScore, Integer maxScore, //预估分数
                                                 List<String> examSubj,   //科目 list
                                                 List<String> majorNames2,   //搜索专业名2
                                                 int page, int limit, String year, EnumGender gender
    ) {

//        majorNames2 = new ArrayList<>();
//        majorNames2.add("电子");
//        majorNames2.add("食品");
//        majorNames2.add("动物");

        //转换科目
        List<String> subj = CommonUtil.ConvSubj(examSubj);

        //转换位次
        Integer minRank = maxScore;// RankManager.GetInstance().GetRankByScore(minScore);
        Integer maxRank = minScore;// RankManager.GetInstance().GetRankByScore(maxScore);
        if (maxScore < minScore) {
            minRank = minScore;
            maxRank = maxScore;
        } else {
            minRank = maxScore;
            maxRank = minScore;
        }

//        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.DESC, "scoreFor22")); //排序  专业组分数从高到低
//        orders.add(new Sort.Order(Sort.Direction.DESC, "major22LowScore")); //排序  专业组分数从高到低
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));
        Pageable pageable = PageRequest.of(page - 1, limit);

        Map<String, Object> map = null;
        switch (pici) {

            case A: {

                List<String> groupCode = new ArrayList<>();
//                for (int i = 0; i < majorNames.size(); i++) {
//                    List<UniMajorInfoALatestVO> list = DBProxy.Getinstance().UniMajorInfoALatestService.findByMajorNameLike(majorNames.get(i));
//                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
//                    }
//                }

                Long in1 = new Date().getTime();
                map = DBProxy.Getinstance().UniMajorInfoALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);

                Long in2 = new Date().getTime();
//                System.out.println("1 search page  " + (in2 - in1) / 1000f);
                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");
//                list.sort(new Comparator<UniMajorInfo>() {
//                    @Override
//                    public int compare(UniMajorInfo o1, UniMajorInfo o2) {
//                        return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
//                    }
//                });
//
//                String lastUniMajorCode = "";
//
//                List<String> uniMajorCodes = new ArrayList<>();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfo tempUniMajor = list.get(i);
//                    String tempUniMajorCode = tempUniMajor.getUniMajorCode();
//
//                    if (lastUniMajorCode.equals(tempUniMajorCode)) continue;
//                    lastUniMajorCode = tempUniMajorCode;
////                    List<UniMajorInfoALatestVO> tempList =  DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCode(tempUniMajorCode);
////                    for (int j = 0; j < tempList.size(); j++) {
////                        allList.add(tempList.get(j).ConvertToDTO());
////                    }
//                    uniMajorCodes.add(lastUniMajorCode);
//                }

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                Long in3 = new Date().getTime();
//                System.out.println("2 sort & find code  " + (in3 - in2) / 1000f + ", codecount: " + uniMajorCodes.size());

                List<com.msg.UniMajorInfo> allList = new ArrayList<>();
                List<UniMajorInfoALatestVO> tempList = DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeIn(uniMajorCodes);
                Long in4 = new Date().getTime();
//                System.out.println("3 search all code  " + (in4 - in3) / 1000f);

                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数
                Long in5 = new Date().getTime();
//                System.out.println("4 conver to proto  " + (in5 - in4) / 1000f);
                return data;


            }

            case B: {
                List<String> groupCode = new ArrayList<>();
//                for (int i = 0; i < majorNames.size(); i++) {
//                    List<UniMajorInfoBLatestVO> list = DBProxy.Getinstance().UniMajorInfoBLatestService.findByMajorNameLike(majorNames.get(i));
//                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
//                    }
//                }

                map = DBProxy.Getinstance().UniMajorInfoBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");
//                list.sort(new Comparator<UniMajorInfo>() {
//                    @Override
//                    public int compare(UniMajorInfo o1, UniMajorInfo o2) {
//                        return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
//                    }
//                });
//
//                String lastUniMajorCode = "";
//
//                List<String> uniMajorCodes = new ArrayList<>();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfo tempUniMajor = list.get(i);
//                    String tempUniMajorCode = tempUniMajor.getUniMajorCode();
//
//                    if (lastUniMajorCode.equals(tempUniMajorCode)) continue;
//                    lastUniMajorCode = tempUniMajorCode;
////                    List<UniMajorInfoALatestVO> tempList =  DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCode(tempUniMajorCode);
////                    for (int j = 0; j < tempList.size(); j++) {
////                        allList.add(tempList.get(j).ConvertToDTO());
////                    }
//                    uniMajorCodes.add(lastUniMajorCode);
//                }

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                List<com.msg.UniMajorInfo> allList = new ArrayList<>();
                List<UniMajorInfoBLatestVO> tempList = DBProxy.Getinstance().UniMajorInfoBLatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数

                return data;
            }
//            break;
            case PreA: {
                List<String> groupCode = new ArrayList<>();
//                for (int i = 0; i < majorNames.size(); i++) {
//                    List<UniMajorInfoPreALatestVO> list = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByMajorNameLike(majorNames.get(i));
//                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
//                    }
//                }

                map = DBProxy.Getinstance().UniMajorInfoPreALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");
//                list.sort(new Comparator<UniMajorInfo>() {
//                    @Override
//                    public int compare(UniMajorInfo o1, UniMajorInfo o2) {
//                        return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
//                    }
//                });
//
//                String lastUniMajorCode = "";
//
//                List<String> uniMajorCodes = new ArrayList<>();
//                for (int i = 0; i < list.size(); i++) {
//                    UniMajorInfo tempUniMajor = list.get(i);
//                    String tempUniMajorCode = tempUniMajor.getUniMajorCode();
//
//                    if (lastUniMajorCode.equals(tempUniMajorCode)) continue;
//                    lastUniMajorCode = tempUniMajorCode;
//                    uniMajorCodes.add(lastUniMajorCode);
//                }

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                List<com.msg.UniMajorInfo> allList = new ArrayList<>();
                List<UniMajorInfoPreALatestVO> tempList = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数

                return data;

            }
//            break;
            case PreB: {
                List<String> groupCode = new ArrayList<>();
//                for (int i = 0; i < majorNames.size(); i++) {
//                    List<UniMajorInfoPreBLatestVO> list = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByMajorNameLike(majorNames.get(i));
//                    for (int j = 0; j < list.size(); j++) {
//                        groupCode.add(list.get(j).getUniMajorCode());
//                    }
//                }

                map = DBProxy.Getinstance().UniMajorInfoPreBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                List<com.msg.UniMajorInfo> allList = new ArrayList<>();
                List<UniMajorInfoPreBLatestVO> tempList = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数

                return data;
            }
//            break;


            case ZhuanKe:
            {
                List<String> groupCode = new ArrayList<>();

                if(pici == EnumPici.ZhuanKe){

                }
                map = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                List<com.msg.UniMajorInfo> allList = new ArrayList<>();

                if(pici == EnumPici.ZhuanKe){

                }
                List<UniMajorInfoZhuanKeLatestVO> tempList = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数

                return data;
            }
            case ZXA:
            {
                List<String> groupCode = new ArrayList<>();

                if(pici == EnumPici.ZhuanKe){

                }
                map = DBProxy.Getinstance().UniMajorInfoZXALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                List<com.msg.UniMajorInfo> allList = new ArrayList<>();

                if(pici == EnumPici.ZhuanKe){

                }
                List<UniMajorInfoZXALatestVO> tempList = DBProxy.Getinstance().UniMajorInfoZXALatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数

                return data;
            }
            case ZXB:{
                List<String> groupCode = new ArrayList<>();

                if(pici == EnumPici.ZhuanKe){

                }
                map = DBProxy.Getinstance().UniMajorInfoZXBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        year,
                        gender,0,0,
                        pageable);


                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");

                List<String> uniMajorCodes = this.GetUniqueUniMajorCode(list);
                List<com.msg.UniMajorInfo> allList = new ArrayList<>();

                if(pici == EnumPici.ZhuanKe){

                }
                List<UniMajorInfoZXBLatestVO> tempList = DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByUniMajorCodeIn(uniMajorCodes);
                for (int j = 0; j < tempList.size(); j++) {
                    allList.add(tempList.get(j).ConvertToDTO());
                }


                Map<String, Object> data = new HashMap<>();
                data.put("items", allList);
                data.put("total", map.get("total"));////所有信息总数
                data.put("totalPage", map.get("totalPage"));//所有page总页数

                return data;
            }

        }


        return map;
    }

    public List<com.msg.MajorInfoYear> GetAllMajorBySchoolNameAndPici(String schoolName, EnumPici pici, EnumLastYear lastYear) {

        List<com.msg.MajorInfoYear> years = new ArrayList<>();

        if (lastYear == EnumLastYear.LastYear1) {
            List<MajorInfoY1VO> majorInfoY1VOList = DBProxy.Getinstance().MajorInfoY1Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
            for (int i = 0; i < majorInfoY1VOList.size(); i++) {
                years.add(majorInfoY1VOList.get(i).ConvertToDTO());
            }
        } else if (lastYear == EnumLastYear.LastYear2) {
            List<MajorInfoY2VO> majorInfoY2VOList = DBProxy.Getinstance().MajorInfoY2Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
            for (int i = 0; i < majorInfoY2VOList.size(); i++) {
                years.add(majorInfoY2VOList.get(i).ConvertToDTO());
            }
        } else {
            List<MajorInfoY3VO> majorInfoY3VOList = DBProxy.Getinstance().MajorInfoY3Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
            for (int i = 0; i < majorInfoY3VOList.size(); i++) {
                years.add(majorInfoY3VOList.get(i).ConvertToDTO());
            }
        }
        return years;


//
//        switch (pici) {
//
//            case A: {
//
//                if(lastYear == EnumLastYear.LastYear1){
//                    List<MajorInfoY1VO> majorInfoY1VOList = DBProxy.Getinstance().MajorInfoY1Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
//                    for (int i = 0; i < majorInfoY1VOList.size(); i++) {
//                        years.add(majorInfoY1VOList.get(i).ConvertToDTO());
//                    }
//                }else if(lastYear == EnumLastYear.LastYear2){
//                    List<MajorInfoY2VO> majorInfoY2VOList = DBProxy.Getinstance().MajorInfoY2Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
//                    for (int i = 0; i < majorInfoY2VOList.size(); i++) {
//                        years.add(majorInfoY2VOList.get(i).ConvertToDTO());
//                    }
//                }else {
//                    List<MajorInfoY3VO> majorInfoY3VOList = DBProxy.Getinstance().MajorInfoY3Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
//                    for (int i = 0; i < majorInfoY3VOList.size(); i++) {
//                        years.add(majorInfoY3VOList.get(i).ConvertToDTO());
//                    }
//                }
//
//
//
//
//
//
//
//                break;
//            }
//            case B: {
//                if(lastYear == EnumLastYear.LastYear1){
//                    List<MajorInfoY1VO> majorInfoY1VOList = DBProxy.Getinstance().MajorInfoY1Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
//                    for (int i = 0; i < majorInfoY1VOList.size(); i++) {
//                        years.add(majorInfoY1VOList.get(i).ConvertToDTO());
//                    }
//                }else if(lastYear == EnumLastYear.LastYear2){
//                    List<MajorInfoY2VO> majorInfoY2VOList = DBProxy.Getinstance().MajorInfoY2Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
//                    for (int i = 0; i < majorInfoY2VOList.size(); i++) {
//                        years.add(majorInfoY2VOList.get(i).ConvertToDTO());
//                    }
//                }else {
//                    List<MajorInfoY3VO> majorInfoY3VOList = DBProxy.Getinstance().MajorInfoY3Service.findBySchoolNameAndPici(schoolName, pici.getStateID());
//                    for (int i = 0; i < majorInfoY3VOList.size(); i++) {
//                        years.add(majorInfoY3VOList.get(i).ConvertToDTO());
//                    }
//                }
//            }
//            break;
//            case PreA: {
//
//            }
//            break;
//            case PreB: {
//
//            }
//            break;
//        }

    }

    private List<String> GetUniqueUniMajorCode(List<UniMajorInfo> list) {
//        List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");
        list.sort(new Comparator<UniMajorInfo>() {
            @Override
            public int compare(UniMajorInfo o1, UniMajorInfo o2) {
                return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
            }
        });

        String lastUniMajorCode = "";

        List<String> uniMajorCodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UniMajorInfo tempUniMajor = list.get(i);
            String tempUniMajorCode = tempUniMajor.getUniMajorCode();

            if (lastUniMajorCode.equals(tempUniMajorCode)) continue;
            lastUniMajorCode = tempUniMajorCode;
//                    List<UniMajorInfoALatestVO> tempList =  DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCode(tempUniMajorCode);
//                    for (int j = 0; j < tempList.size(); j++) {
//                        allList.add(tempList.get(j).ConvertToDTO());
//                    }
            uniMajorCodes.add(lastUniMajorCode);
        }

        return uniMajorCodes;
    }


    public List<com.msg.MajorInfoYear> GetAllMajorBySchoolNameAndPiciAndYear(String schoolName, EnumPici pici, String year) {

        List<com.msg.MajorInfoYear> years = new ArrayList<>();

        List<MajorInfoYAllVO> majorInfoY1VOList = DBProxy.Getinstance().MajorInfoYAllService.findBySchoolNameAndPiciAndYear(schoolName, pici.getStateID(), year);
        for (int i = 0; i < majorInfoY1VOList.size(); i++) {
            years.add(majorInfoY1VOList.get(i).ConvertToDTO());
        }

        return years;

    }

    public  List<List<UniMajorInfo> > GetInHisDBToLatestByAllGroup(List<Long> id, EnumPici pici, String examYear, List<List<Long>> groupList , boolean needUpdate,HashMap<Long, Long> majorIdDic) {
//        boolean needUpdate = false;
//        if(dataVersion == null){
//            //原本没有, 需要更新
//            needUpdate = true;
//        }else {
//            //有版本信息, 需要比较
//            LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion((pici), examYear);
//            if(latestVerInfoVO != null){
//                needUpdate =  dataVersion != (latestVerInfoVO.getDataVersion());
//            }else {
//                needUpdate = true;
//            }
//        }


        List<List<UniMajorInfo> > protoGroupList = new ArrayList<>();
        List<UniMajorInfo> latestProtoList = new ArrayList<>();
        switch (pici) {

            case A: {


                List<UniMajorInfoAVO> list = DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }

                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }

                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }


                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }

                }



            }
            break;
            case B: {
                latestProtoList = new ArrayList<>();

                List<UniMajorInfoBVO> list = DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }

                    //                protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }
                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoBLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }
                    
                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }
                    
                }


            }
            break;
            case PreA: {
               latestProtoList = new ArrayList<>();

                List<UniMajorInfoPreAVO> list = DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }
                    //                protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }

                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoPreALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreALatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }


                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }

                }

            }
            break;
            case PreB: {
                latestProtoList = new ArrayList<>();

                List<UniMajorInfoPreBVO> list = DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }

                    //                protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }
                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoPreBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }


                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }

                }


                break;
            }
            case ZhuanKe:
            {

               latestProtoList = new ArrayList<>();

                List<UniMajorInfoZhuanKeVO> list = DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }

                    //                protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }

                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoZhuanKeLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }



                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }

                }


                break;
            }
            case ZXA:
            {
                latestProtoList = new ArrayList<>();

                List<UniMajorInfoZXAVO> list = DBProxy.Getinstance().UniMajorInfoZXAService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }

                    //                protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }
                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoZXALatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXALatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }


                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }

                }

                break;
            }
            case ZXB:{
                latestProtoList = new ArrayList<>();

                List<UniMajorInfoZXBVO> list = DBProxy.Getinstance().UniMajorInfoZXBService.findByIdIn(id);
                if(!needUpdate){
                    //不需要更新的情况, 直接添加
                    for (int i = 0; i < list.size(); i++) {
                        latestProtoList.add(list.get(i).ConvertToDTO());
                    }

                    //                protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
                    //保存新旧id 对比
                    for (int i = 0; i < list.size(); i++) {
                        majorIdDic.put(list.get(i).getId(), list.get(i).getIdInLatestDB());
                    }
                }else {
                    //重新 按unicode 查询
                    Set<String> uniMajorCode = new HashSet<>();
                    for (int i = 0; i < list.size(); i++) {
                        uniMajorCode.add(list.get(i).getUniMajorCode());
                    }
                    //
                    List<UniMajorInfoZXBLatestVO> latestDBs =  DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByUniMajorCodeInAndExamYear(new ArrayList<>(uniMajorCode), examYear);
                    for (int i = 0; i < latestDBs.size(); i++) {
                        latestProtoList.add(latestDBs.get(i).ConvertToDTO());
                    }


                    //新旧id 对比
                    HashMap<Long, String> IdToUID_OLD = new HashMap<>();
                    for (int i = 0; i < list.size(); i++) {
                        String uniMajorCode_old = list.get(i).getUniMajorCode();
                        String majorCode_old = list.get(i).getMajorCode();
                        Long id_old = list.get(i).getId();
                        IdToUID_OLD.put(id_old, uniMajorCode_old + "_" + majorCode_old);
                    }

                    HashMap<String, Long> IdToUID_New = new HashMap<>();
                    for (int i = 0; i < latestDBs.size(); i++) {
                        String uniMajorCode_old = latestDBs.get(i).getUniMajorCode();
                        String majorCode_old = latestDBs.get(i).getMajorCode();
                        Long id_old = latestDBs.get(i).getIdInHistDB();
                        IdToUID_New.put(uniMajorCode_old + "_" + majorCode_old, id_old);
                    }

                    //切换 新id 对比
                    for (int i = 0; i < list.size(); i++) {
                        Long oldId = list.get(i).getId();
                        String uid = IdToUID_OLD.getOrDefault(oldId, null);
                        if(uid != null){
                            Long newId = IdToUID_New.getOrDefault(uid, null);
                            if(newId != null){
                                majorIdDic.put(oldId, newId);
                            }
                        }

                    }

                    //groupId 更新新uid
                    for (int i = 0; i < groupList.size(); i++) {
                        List<Long> tempMajorIds = groupList.get(i);
                        for (int j = 0; j < tempMajorIds.size(); j++) {
                            Long tempId = tempMajorIds.get(j);
                            Long newId = majorIdDic.getOrDefault(tempId, null);
                            if(newId != null){
                                tempMajorIds.set(j, newId);
                            }
                        }
                    }

                }


                break;
            }

        }



        protoGroupList = GameUtil.GetBySameOrder(latestProtoList, groupList);
        return protoGroupList;
    }

    //原始版本
    public List<UniMajorInfo> GetInHisDBToLatest(List<Long> id, EnumPici pici) {

        List<UniMajorInfo> protoList = new ArrayList<>();

        switch (pici) {

            case A: {
                List<UniMajorInfoAVO> list = DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(id);

                HashMap<Long, UniMajorInfoAVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoAVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoAVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    Long idInLatestDB = tempVo == null ? null : tempVo.getIdInLatestDB();
                    latestIds.add(idInLatestDB == null ? id.get(i) : idInLatestDB);
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoAVO> listLatest = DBProxy.Getinstance().UniMajorInfoAService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }

            }
            break;
            case B: {
                List<UniMajorInfoBVO> list = DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(id);
//                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
//                }

                HashMap<Long, UniMajorInfoBVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoBVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoBVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    latestIds.add(tempVo.getIdInLatestDB());
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoBVO> listLatest = DBProxy.Getinstance().UniMajorInfoBService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }


            }
            break;
            case PreA: {
                List<UniMajorInfoPreAVO> list = DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(id);

                HashMap<Long, UniMajorInfoPreAVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoPreAVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoPreAVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    latestIds.add(tempVo.getIdInLatestDB());
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoPreAVO> listLatest = DBProxy.Getinstance().UniMajorInfoPreAService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }

            }
            break;
            case PreB: {
                List<UniMajorInfoPreBVO> list = DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(id);

                HashMap<Long, UniMajorInfoPreBVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoPreBVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoPreBVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    latestIds.add(tempVo.getIdInLatestDB());
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoPreBVO> listLatest = DBProxy.Getinstance().UniMajorInfoPreBService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }

                break;
            }
            case ZhuanKe:
            {

                List<UniMajorInfoZhuanKeVO> list = DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByIdIn(id);

                HashMap<Long, UniMajorInfoZhuanKeVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoZhuanKeVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoZhuanKeVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    latestIds.add(tempVo.getIdInLatestDB());
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoZhuanKeVO> listLatest = DBProxy.Getinstance().UniMajorInfoZhuanKeService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }


                break;
            }
            case ZXA:
            {

                List<UniMajorInfoZXAVO> list = DBProxy.Getinstance().UniMajorInfoZXAService.findByIdIn(id);

                HashMap<Long, UniMajorInfoZXAVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoZXAVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoZXAVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    latestIds.add(tempVo.getIdInLatestDB());
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoZXAVO> listLatest = DBProxy.Getinstance().UniMajorInfoZXAService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }


                break;
            }
            case ZXB:{

                List<UniMajorInfoZXBVO> list = DBProxy.Getinstance().UniMajorInfoZXBService.findByIdIn(id);

                HashMap<Long, UniMajorInfoZXBVO> tempHistDBMajor = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
//                    protoList.add(list.get(i).ConvertToDTO());
                    UniMajorInfoZXBVO tempVo = list.get(i);
                    tempHistDBMajor.put(tempVo.getId(), tempVo);
                }

                //最新id的顺序, 和 旧的要 一致
                List<Long> latestIds = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    UniMajorInfoZXBVO tempVo = tempHistDBMajor.getOrDefault(id.get(i), null);
                    latestIds.add(tempVo.getIdInLatestDB());
                }

                HashMap<Long, UniMajorInfo> tempHistDBLatestMajor = new HashMap<>();
                //用最新的id查询
                List<UniMajorInfoZXBVO> listLatest = DBProxy.Getinstance().UniMajorInfoZXBService.findByIdIn(latestIds);
                for (int i = 0; i < listLatest.size(); i++) {
                    UniMajorInfo tempMajor = listLatest.get(i).ConvertToDTO();
                    tempHistDBLatestMajor.put(tempMajor.getId(), tempMajor);
                }

                //重新排序
                for (int i = 0; i < latestIds.size(); i++) {
                    UniMajorInfo tempMajor = tempHistDBLatestMajor.getOrDefault(latestIds.get(i), null);
                    protoList.add(tempMajor);
                }


                break;
            }

        }
        return protoList;
    }

    //保存利用过去的majorId 查询最新 的major信息; 代替 GetInHisDB()
    public List<UniMajorInfo> GetByHisDBIdConvertToLatest(List<Long> id, EnumPici pici) {
        List<String> yearList = UniMajorMgrInMem.GetInstance().ValidExamYear;
        String latestYear = yearList.get(yearList.size() - 1);

        List<UniMajorInfo> protoList = new ArrayList<>();

        switch (pici) {

            case A: {
                List<UniMajorInfoALatestVO> list = DBProxy.Getinstance().UniMajorInfoALatestService.findByHistMajorIdsAndExamYear(id,latestYear);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

            }
            break;
            case B: {
                List<UniMajorInfoBLatestVO> list = DBProxy.Getinstance().UniMajorInfoBLatestService.findByHistMajorIdsAndExamYear(id,latestYear);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

            }
            break;
            case PreA: {
                List<UniMajorInfoPreALatestVO> list = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByHistMajorIdsAndExamYear(id,latestYear);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

            }
            break;
            case PreB: {
                List<UniMajorInfoPreBLatestVO> list = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByHistMajorIdsAndExamYear(id,latestYear);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

                break;
            }
            case ZhuanKe:
            case ZXA:
            case ZXB:{

                List<UniMajorInfoPreBLatestVO> list = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByHistMajorIdsAndExamYear(id,latestYear);
                for (int i = 0; i < list.size(); i++) {
                    protoList.add(list.get(i).ConvertToDTO());
                }

                break;
            }

        }
        return protoList;
    }

}
