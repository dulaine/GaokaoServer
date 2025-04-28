package com.baizhou.manager;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumGender;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.db.DBProxy;
import com.baizhou.util.GameUtil;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.msg.UniMajorInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.*;

public class UniMajorMgrInMem {


    private static UniMajorMgrInMem instance;

    public static UniMajorMgrInMem GetInstance() {
        if (instance == null) {
            instance = new UniMajorMgrInMem();
        }
        return instance;
    }

    Map<Long, UniMajorInfo> UniMajorInHistDB = new HashMap<>(); // 整体DB数据 dic<histDB_id, major in hist>
    Map<String,  Map<EnumPici, Map<String, com.msg.UniMajorGroupInfo>>> LatestUniGroupByYearDic = new HashMap<>();  //最新版本的数据, dic<year , pici, list of group>
//    Map<String, Boolean> ValidExamedYear = new HashMap<>(); //有效年份
    public List<String> ValidExamYear = new ArrayList<>();

    //启动后, 初始化院校池 数据
    public void InitDBOnStart() {

//        //===>按年保存 院校池  => 用字典保存
//        List<UniGroupInfoALatestVO> temp = DBProxy.Getinstance().UniGroupInfoALatestService.getAllUniGroupInfoALatests();
        Map<String, Boolean> examedYear = new HashMap<>();
//        for (int i = 0; i < temp.size(); i++) {
//            String year = temp.get(i).getExamYear();
//            if(ConstDefine.FastTest2023){
//                if(!year.equals("2023")) continue;
//            }
//            if(!examedYear.containsKey(year)){
//                System.out.println("prepare unigroup year " + year);
//                examedYear.put(year, true);
//                RefreshAfterDatatUpdate(year);
//                if(ConstDefine.FastTest2023) break;
//            }
//
//        }

        List<String> list = DBProxy.Getinstance().UniMajorInfoALatestService.selectDistinctYear();
        for (int i = 0; i < list.size(); i++) {
            String year = list.get(i);
            if(StringUtils.isEmpty(year)) continue;

            if(!examedYear.containsKey(year)){
                System.out.println("prepare unigroup year " + year);
                examedYear.put(year, true);
                RefreshAfterDatatUpdate(year);
//                if(ConstDefine.FastTest2023) break;
            }
        }

    }

    //新院校数据更新 ,   体检限报信息更新
    public void RefreshAfterDatatUpdate(String updateYear) {
        if(StringUtils.isEmpty(updateYear)) return;
//        UniGroupInfoALatestVO uniGroupInfoPreALatestVO = DBProxy.Getinstance().UniGroupInfoALatestService.findOneById(1L);
//        Integer latestVerion =  uniGroupInfoPreALatestVO.getDataVersion();
//        if(latestVerion <= this.latestVerion) return;
//
//        this.latestVerion = latestVerion;

        //删除最新 数据版本
        //更新最新版本数据
//        if(!ValidExamedYear.containsKey(updateYear)){
//            ValidExamedYear.put(updateYear, true);
//        }
        if(!ValidExamYear.contains(updateYear)){
            ValidExamYear.add(updateYear);
            ValidExamYear.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int one = (int) Integer.parseInt(o1);
                    int two = (int) Integer.parseInt(o2);
                    if (one == two) return 0;
                    return one - two > 0 ? 1 : -1;
                }
            });
        }
        if(true)return;
//        //添加最新版本到HistDB
//        //按批次查询 最新的数据
//
//        //获取 UniGroup 的数据,   解析对应的专业详情信息, 转成proto
//
//        Date start = new Date();
//
//        /////////////////////////////////////////////////
//        //按批次保存
//        Map<EnumPici, Map<String, com.msg.UniMajorGroupInfo>> UniGroupDicByPic = new HashMap<>(); //<pic,  groupCode, group>
//
//        EnumPici[] enumPicis = EnumPici.values();
//        for (EnumPici pici : enumPicis) {
//            Date dat1 = new Date();
//
//            if(ConstDefine.FastTest2023){
////                if(pici != EnumPici.A) continue;
//                if(pici == EnumPici.ZhuanKe) continue;
//                if(pici == EnumPici.ZXB) continue;
//                if(pici == EnumPici.ZXA) continue;
//            }
//
//            //按批次查询 Group
//            Map<String, com.msg.UniMajorGroupInfo> UniGroupDicByGroupCode = GetAllGroupByPici(pici, updateYear, EnumGender.Male);
//
//            //记录 HistDB最新的MajorInfo,
//            for (Map.Entry<String, com.msg.UniMajorGroupInfo> entry : UniGroupDicByGroupCode.entrySet()){
//                List<com.msg.UniMajorInfo> majorInfos = entry.getValue().getMajorsList();
//                for (int i = 0; i < majorInfos.size(); i++) {
//                    com.msg.UniMajorInfo majorInfo = majorInfos.get(i);
//                    UniMajorInHistDB.put(majorInfo.getId(), majorInfo);
//                }
//            }
//
//
//            //按批次保存 院校池
//            UniGroupDicByPic.put(pici, UniGroupDicByGroupCode);
//
//            Date dat2 = new Date();
//            System.out.println("parse pici " + pici.name() + ", updateYear: " + updateYear+ "," + (dat2.getTime()  - dat1.getTime())/1000);
//        }
//        /////////////////////////////////////////////////
//
//
//        //更新 最新的 院校池数据;  按年份保存
//        this.LatestUniGroupByYearDic.remove(updateYear);
//        this.LatestUniGroupByYearDic.put(updateYear, UniGroupDicByPic);
//
//        Date end = new Date();
//        System.out.println("finish update "  +  updateYear + ", cost " +  (end.getTime() - start.getTime())/1000);
    }

    //获取对应年份, 对应pici的院校池
    private Map<String, com.msg.UniMajorGroupInfo> GetAllGroupByPici(EnumPici pici, String examYear, EnumGender enumGender) {
        Map<String, com.msg.UniMajorGroupInfo> GroupDic = new HashMap<>(); //保存 Dic<groupCode, List major>

        switch (pici) {
            case A: {

                List<UniGroupInfoALatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoALatestService.findByExamYear(examYear);//getAllUniGroupInfoALatests();

                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoALatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoALatestVO> majors = DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }

                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }


                break;
            }
            case B: {
                List<UniGroupInfoBLatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoBLatestService.findByExamYear(examYear);//getAllUniGroupInfoBLatests();


                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoBLatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoBLatestVO> majors = DBProxy.Getinstance().UniMajorInfoBLatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);//findByUniMajorCode(uniCode);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);
                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }
                break;
            }
            case PreA: {
                List<UniGroupInfoPreALatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoPreALatestService.findByExamYear(examYear);//getAllUniGroupInfoPreALatests();


                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoPreALatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoPreALatestVO> majors = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);//findByUniMajorCode(uniCode);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);
                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }
                break;
            }
            case PreB: {
                List<UniGroupInfoPreBLatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoPreBLatestService.findByExamYear(examYear);//getAllUniGroupInfoPreBLatests();


                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoPreBLatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoPreBLatestVO> majors = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);//findByUniMajorCode(uniCode);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);
                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }
                break;
            }
            case ZhuanKe: {
                List<UniGroupInfoZhuanKeLatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoZhuanKeLatestService.findByExamYear(examYear);//getAllUniGroupInfoZhuanKeLatests();


                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoZhuanKeLatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoZhuanKeLatestVO> majors = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);//findByUniMajorCode(uniCode);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);
                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }
                break;
            }
            case ZXA: {
                List<UniGroupInfoZXALatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoZXALatestService.findByExamYear(examYear);//getAllUniGroupInfoZXALatests();


                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoZXALatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoZXALatestVO> majors = DBProxy.Getinstance().UniMajorInfoZXALatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);//findByUniMajorCode(uniCode);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);
                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }
                break;
            }
            case ZXB: {
                List<UniGroupInfoZXBLatestVO> grouplist = DBProxy.Getinstance().UniGroupInfoZXBLatestService.findByExamYear(examYear);//getAllUniGroupInfoZXBLatests();


                //查询专业组内 major
                for (int i = 0; i < grouplist.size(); i++) {
                    UniGroupInfoZXBLatestVO groupInfoVO = grouplist.get(i);
                    String uniCode = groupInfoVO.getUniMajorCode();

                    List<UniMajorInfoZXBLatestVO> majors = DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByUniMajorCodeAndExamYear(uniCode, examYear);//findByUniMajorCode(uniCode);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < majors.size(); j++) {
                        protoMajor.add(majors.get(j).ConvertToDTO());
                    }
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor,enumGender);
                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    GroupDic.put(uniCode, groupProto.build());
                }
                break;
            }
        }


        return GroupDic;
    }




    private com.msg.UniMajorGroupInfo FindLatestByYearAndPiciAndGroupCode(String examYear, EnumPici pici, String groupCode){
        Map<EnumPici, Map<String, com.msg.UniMajorGroupInfo>>  UniGroupDicByPic =  LatestUniGroupByYearDic.getOrDefault(examYear ,null);

        if(UniGroupDicByPic == null){
            System.out.println("not get Latest by Year" + examYear);
            //


        }else {
            Map<String, com.msg.UniMajorGroupInfo> UniGroupDicByCode = UniGroupDicByPic.getOrDefault(pici, null);

            if(UniGroupDicByCode == null){
                System.out.println("not get Latest by pici " + pici.name());
            }else {

                com.msg.UniMajorGroupInfo groupInfo = UniGroupDicByCode.getOrDefault(groupCode, null);

                return groupInfo;
            }

        }

        return  null;

    }



    //获取院校池 ixn
    public Map<String, Object> GetUniMajorByList(EnumPici pici,
                                                 List<String> schoolNames, //搜索学校名
                                                 Integer is1stcls, Integer is985, Integer is211,
                                                 List<String> schoolType, //院校类型： 综合, 理工、综合、师范、医药

                                                 List<String> provs,  //省份
                                                 List<String> majorNames,   //搜索专业名

                                                 Integer minScore, Integer maxScore, //预估分数
                                                 List<String> examSubj,   //科目 list
                                                 List<String> majorNames2,   //搜索专业名2
                                                 int page, int limit,
                                                 String examYear  //搜索年份
                                                  ) {

        int tempPage = page;  //start from 1;
        int tempLimit = limit;
        //[startIdx, endIdx)
        int startIdx_inclu = (page - 1) * limit;
        int endIdx_exclu = (page) * limit;

        //转换科目
        List<String> subj = CommonUtil.ConvSubj(examSubj);

        //转换位次
        Integer minRank = maxScore;
        Integer maxRank = minScore;
        if (maxScore < minScore) {
            minRank = minScore;
            maxRank = maxScore;
        } else {
            minRank = maxScore;
            maxRank = minScore;
        }

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "scoreFor22")); //排序  专业组分数从高到低
//        orders.add(new Sort.Order(Sort.Direction.DESC, "major22LowScore")); //排序  专业组分数从高到低
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));
        Pageable pageable = PageRequest.of(0, 20000, Sort.by(orders));
//        Pageable pageable = PageRequest.of(page - 1, limit);

        Map<String, Object> map = null;
        List<com.msg.UniMajorGroupInfo> allList = new ArrayList<>();
        switch (pici) {

            case A: {

                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );

                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoALatestVO> list = (List<UniGroupInfoALatestVO>) map.get("items");


//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;

//                Map<String, Object> data = new HashMap<>();
//                data.put("items", allList);
//                data.put("total", map.get("total"));////所有信息总数
//                data.put("totalPage", map.get("totalPage"));//所有page总页数
//                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
//                return data;


            }

            case B: {
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoBLatestVO> list = (List<UniGroupInfoBLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                break;
            }
//            break;
            case PreA: {
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoPreALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoPreALatestVO> list = (List<UniGroupInfoPreALatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                break;

            }
//            break;
            case PreB: {
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoPreBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoPreBLatestVO> list = (List<UniGroupInfoPreBLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                break;
            }
//            break;

            case ZhuanKe:{
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoZhuanKeLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoZhuanKeLatestVO> list = (List<UniGroupInfoZhuanKeLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;
            }
            case ZXA:{
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoZXALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoZXALatestVO> list = (List<UniGroupInfoZXALatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;
            }
            case ZXB:{
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniGroupInfoZXBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniGroupInfoZXBLatestVO> list = (List<UniGroupInfoZXBLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
                    allList.add(groupInfo);
                }
                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;
            }

        }


//        allList = CommonUtil.SortGroupByGroup(allList);

        Map<String, Object> data = new HashMap<>();
        data.put("items", allList);
        data.put("total", map.get("total"));////所有信息总数
        data.put("totalPage", map.get("totalPage"));//所有page总页数


        return data;
    }


    //获取院校池 , 搜索 Majro表
    public Map<String, Object> GetUniMajorByList_ByMajor(EnumPici pici,
                                                         List<String> schoolNames, //搜索学校名
                                                         Integer is1stcls, Integer is985, Integer is211,
                                                         List<String> schoolType, //院校类型： 综合, 理工、综合、师范、医药

                                                         List<String> provs,  //省份
                                                         List<String> majorNames,   //搜索专业名

                                                         Integer minScore, Integer maxScore, //预估分数
                                                         List<String> examSubj,   //科目 list
                                                         List<String> majorNames2,   //搜索专业名2
                                                         int page, int limit,
                                                         String examYear,  //搜索年份

                                                         EnumGender enumGender, //性别
                                                         Integer isZhongwai, Integer isBensuo

    ) {

        isZhongwai = isZhongwai < 0 ? null : isZhongwai;
        isBensuo = isBensuo < 0 ? null : isBensuo;

        int tempPage = page;  //start from 1;
        int tempLimit = limit;
        //[startIdx, endIdx)
        int startIdx_inclu = (page - 1) * limit;
        int endIdx_exclu = (page) * limit;

        //转换科目
        List<String> subj = CommonUtil.ConvSubj(examSubj);

        //转换位次
        Integer minRank = maxScore;
        Integer maxRank = minScore;
        if (maxScore < minScore) {
            minRank = minScore;
            maxRank = maxScore;
        } else {
            minRank = maxScore;
            maxRank = minScore;
        }
        if(minRank >=60000) minRank = Integer.MAX_VALUE;

        List<Sort.Order> orders = new ArrayList<>();
        if(enumGender == EnumGender.Femal){
            orders.add(new Sort.Order(Sort.Direction.DESC, "scoreFor22Nv")); //排序  专业组分数从高到低
        }else {
            orders.add(new Sort.Order(Sort.Direction.DESC, "scoreFor22")); //排序  专业组分数从高到低
        }

//        orders.add(new Sort.Order(Sort.Direction.DESC, "major22LowScore")); //排序  专业组分数从高到低
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));
        Pageable pageable = PageRequest.of(0, 20000, Sort.by(orders));
//        Pageable pageable = PageRequest.of(page - 1, limit);

        Map<String, Object> map = null;
        List<com.msg.UniMajorGroupInfo> allList = new ArrayList<>();
        switch (pici) {

            case A: {

                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );

//                Long in2 = new Date().getTime();
//                System.out.println("1 search major page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoALatestVO> list = (List<UniMajorInfoALatestVO>) map.get("items");
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();

//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
//
////                List<String> uniMajorCodes = GameUtil.GetUniqueUniMajorCodeA(list,startIdx_inclu, endIdx_exclu);
////                Long in3 = new Date().getTime();
////                System.out.println("2 GetUniqueUniMajorCodeA major page  " + (in3 - in1) / 1000f);

                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);

//                Long in5 = new Date().getTime();
//                System.out.println("3 convert to proto group " + (in5 - in2) / 1000f);
                break;
            }

            case B: {
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );


//                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoBLatestVO> list = (List<UniMajorInfoBLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);

                break;
            }
//            break;
            case PreA: {
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoPreALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoPreALatestVO> list = (List<UniMajorInfoPreALatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);
                break;

            }
//            break;
            case PreB: {
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoPreBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );


                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoPreBLatestVO> list = (List<UniMajorInfoPreBLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);
                break;
            }
//            break;

            case ZhuanKe:{
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );


//                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoZhuanKeLatestVO> list = (List<UniMajorInfoZhuanKeLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);
//                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;
            }
            case ZXA:{
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoZXALatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );


//                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoZXALatestVO> list = (List<UniMajorInfoZXALatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);
//                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;
            }
            case ZXB:{
                List<String> groupCode = new ArrayList<>();
                Long in1 = new Date().getTime();
                //查询Group DB表,  再从mem中获取对应的Majro的proto
                map = DBProxy.Getinstance().UniMajorInfoZXBLatestService.listbyPage(pici.getStateID(), groupCode,
                        schoolNames,
                        is1stcls, is985, is211,
                        schoolType, provs, majorNames,
                        minRank, maxRank,
                        subj,
                        majorNames2,
                        examYear,
                        enumGender,
                        isZhongwai,
                        isBensuo,
                        pageable );


//                Long in2 = new Date().getTime();
//                System.out.println("1 search group page  " + (in2 - in1) / 1000f);
                List<UniMajorInfoZXBLatestVO> list = (List<UniMajorInfoZXBLatestVO>) map.get("items");
//                for (int i = 0; i < list.size(); i++) {
                if(list.size() < endIdx_exclu)  endIdx_exclu = list.size();
//                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
//                    com.msg.UniMajorGroupInfo  groupInfo= FindLatestByYearAndPiciAndGroupCode(examYear, pici, list.get(i).getUniMajorCode());
//                    allList.add(groupInfo);
//                }
                List<String> unicodes = new ArrayList<>();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    unicodes.add( list.get(i).getUniMajorCode());
                }
                List<com.msg.UniMajorGroupInfo>  groupInfo= GetAllGroupByPici(pici,examYear,  unicodes, enumGender);
                allList = (groupInfo);
//                Long in5 = new Date().getTime();
//                System.out.println(" convert to proto group " + (in5 - in2) / 1000f);
                break;
            }

        }


//        allList = CommonUtil.SortGroupByGroup(allList);

        Map<String, Object> data = new HashMap<>();
        data.put("items", allList);
        data.put("total", map.get("total"));////所有信息总数
        data.put("totalPage", map.get("totalPage"));//所有page总页数


        return data;
    }


    //获取对应年份, 对应pici的院校池
    private List<com.msg.UniMajorGroupInfo> GetAllGroupByPici(EnumPici pici, String examYear, List<String> uniCode, EnumGender enumGender) {
//        Map<String, com.msg.UniMajorGroupInfo> GroupDic = new HashMap<>(); //保存 Dic<groupCode, List major>

        switch (pici) {
            case A: {


//                Long in1 = new Date().getTime();
                List<UniMajorInfoALatestVO> majors = DBProxy.Getinstance().UniMajorInfoALatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoALatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoALatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoALatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;

//                break;
            }


            case B: {

//                Long in1 = new Date().getTime();
                List<UniMajorInfoBLatestVO> majors = DBProxy.Getinstance().UniMajorInfoBLatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoBLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoBLatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoBLatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;


            }
            case PreA: {

                //                Long in1 = new Date().getTime();
                List<UniMajorInfoPreALatestVO> majors = DBProxy.Getinstance().UniMajorInfoPreALatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoPreALatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoPreALatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoPreALatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;

            }
            case PreB: {

                //                Long in1 = new Date().getTime();
                List<UniMajorInfoPreBLatestVO> majors = DBProxy.Getinstance().UniMajorInfoPreBLatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoPreBLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoPreBLatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoPreBLatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;
            }
            case ZhuanKe: {

                //                Long in1 = new Date().getTime();
                List<UniMajorInfoZhuanKeLatestVO> majors = DBProxy.Getinstance().UniMajorInfoZhuanKeLatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoZhuanKeLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoZhuanKeLatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoZhuanKeLatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;
            }
            case ZXA: {

                //                Long in1 = new Date().getTime();
                List<UniMajorInfoZXALatestVO> majors = DBProxy.Getinstance().UniMajorInfoZXALatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoZXALatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoZXALatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoZXALatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;
            }
            case ZXB: {

                //                Long in1 = new Date().getTime();
                List<UniMajorInfoZXBLatestVO> majors = DBProxy.Getinstance().UniMajorInfoZXBLatestService.findByUniMajorCodeInAndExamYear(uniCode, examYear);

//                Long in2 = new Date().getTime();
//                System.out.println(" convert to proto group1 " + (in2 - in1) / 1000f);
                //保存数据 到dic
                HashMap<String, List<UniMajorInfoZXBLatestVO>> tempDic = new HashMap<>();
                for (int i = 0; i < majors.size(); i++) {
                    String tempUniCode = majors.get(i).getUniMajorCode();
                    List<UniMajorInfoZXBLatestVO> tempList = tempDic.getOrDefault(tempUniCode, null);
                    if(tempList == null){
                        tempList = new ArrayList<>();
                        tempDic.put(tempUniCode, tempList);
                    }
                    tempList.add(majors.get(i));
                }

                //转proto
                List<com.msg.UniMajorGroupInfo> tempGroups = new ArrayList<>();
                for (int i = 0; i < uniCode.size(); i++) {
                    String tempuniCode = uniCode.get(i);

                    List<UniMajorInfoZXBLatestVO> tempmajors = tempDic.getOrDefault(tempuniCode, null);

                    //转proto
                    List<com.msg.UniMajorInfo> protoMajor = new ArrayList<>();
                    for (int j = 0; j < tempmajors.size(); j++) {
                        protoMajor.add(tempmajors.get(j).ConvertToDTO());
                    }
//                    Long in3 = new Date().getTime();
//                    System.out.println(" convert to proto group2 " + (in3 - in1) / 1000f);
                    // major排序
                    protoMajor = CommonUtil.SortGroupByMajor(protoMajor, enumGender);

                    //转group
                    com.msg.UniMajorGroupInfo.Builder groupProto = com.msg.UniMajorGroupInfo.newBuilder();
                    groupProto.addAllMajors(protoMajor);
                    groupProto.addAllTemplateIds(new ArrayList<>());

                    tempGroups.add(groupProto.build());
                }

//                Long in4 = new Date().getTime();
//                System.out.println(" convert to proto group3 " + (in4 - in1) / 1000f);

                return tempGroups;
            }


        }


        return null;
    }




    private com.msg.UniMajorGroupInfo FindLatestByYearAndPiciAndGroupCodeCur(String examYear, EnumPici pici, String groupCode){
        Map<EnumPici, Map<String, com.msg.UniMajorGroupInfo>>  UniGroupDicByPic =  LatestUniGroupByYearDic.getOrDefault(examYear ,null);

        if(UniGroupDicByPic == null){
            System.out.println("not get Latest by Year" + examYear);
            //


        }else {
            Map<String, com.msg.UniMajorGroupInfo> UniGroupDicByCode = UniGroupDicByPic.getOrDefault(pici, null);

            if(UniGroupDicByCode == null){
                System.out.println("not get Latest by pici " + pici.name());
            }else {

                com.msg.UniMajorGroupInfo groupInfo = UniGroupDicByCode.getOrDefault(groupCode, null);

                return groupInfo;
            }

        }

        return  null;

    }


    //保存数据
    public void SaveLatestVersion(EnumPici pici, String year,Integer NewVersion){

        LatestVerInfoVO vo =  FindLatestVersion(pici, year);
        if(vo == null) {
            vo = new LatestVerInfoVO();
            vo.setPici(pici.getStateID());
            vo.setYear(year);
        }
        vo.setDataVersion(NewVersion);

        DBProxy.Getinstance().LatestVerInfoService.saveLatestVerInfo(vo);
    }

    //保存数据
    public LatestVerInfoVO FindLatestVersion(EnumPici pici, String year){
        return DBProxy.Getinstance().LatestVerInfoService.findByPiciAndYear(pici.getStateID(), year);
    }

}
