package com.baizhou.core.model.vo;

import com.baizhou.common.CommonUtil;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumHasSelLimit;
import com.baizhou.db.DBProxy;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PhysLimitationManager;
import com.baizhou.manager.RankManager;
import com.baizhou.util.GameUtil;
import com.msg.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Service层参数和返回值使用VO
 */
@Data
@Scope("prototype")
@Component
@Getter
@Setter
public class ClientInfoVO {
    /**
     * 手机号
     * Len20
     */
    private String tel = "";
    /**
     * 学生姓名
     * Len100
     */
    private String name = "";
    /**
     * 考试科目  以竖线分割; [物理|滑雪|生物]
     * Len200
     */
    private String examSubject = "";
    /**
     * 性别
     * Len10
     */
    private String gender = "";
    /**
     * 高考预估分数_低
     * Len20
     */
    private String examEstScoreMin = "";
    /**
     * 高考预估分数_最高
     * Len20
     */
    private String examEstScoreMax = "";
    /**
     * 高考预估位次_低
     * Len20
     */
    private String examEstRankMin = "";
    /**
     * 高考预估位次_最高
     * Len20
     */
    private String examEstRankMax = "";
    /**
     * 省份
     * Len100
     */
    private String province = "";
    /**
     * 高考年份
     * Len20
     */
    private String examYear = "";
    /**
     * 所在学校
     * Len100
     */
    private String highSchool = "";
    /**
     * 第一次英语成绩
     * Len20
     */
    private String eng1stScore = "";
    /**
     * 高考成绩
     * Len20
     */
    private String examScore = "";
    /**
     * 单科成绩
     * Len100
     */
    private String singleSubjScore = "";
    /**
     * 体检限报
     * Len100
     */
    private String physExam = "";
    /**
     * 提前批报考意向
     * Len100
     */
    private String preSubmission = "";
    /**
     * 父亲所在单位
     * Len100
     */
    private String companyF = "";
    /**
     * 手机号
     * Len100
     */
    private String telF = "";
    /**
     * 职务
     * Len100
     */
    private String jobF = "";
    /**
     * 社会关系
     * Len200
     */
    private String socialF = "";
    /**
     * 母亲所在单位
     * Len100
     */
    private String companyM = "";
    /**
     * 母亲手机号
     * Len100
     */
    private String telM = "";
    /**
     * 母亲职务
     * Len100
     */
    private String jobM = "";
    /**
     * 母亲社会关系
     * Len200
     */
    private String socialM = "";
    /**
     * 院校   强基计划
     * Len200
     */
    private String uniPlan = "";
    /**
     * 专业   强基计划
     * Len200
     */
    private String uniMajor = "";
    /**
     * 备注   强基计划
     * Len200
     */
    private String uniComment = "";
    /**
     * 是否入围  强基计划
     * Len200
     */
    private String uniSubmission = "";
    /**
     * 考生情况评估（100个字符）
     * Len200
     */
    private String studentIntro = "";
    /**
     * 历次成绩 [用,区分]
     * Len10000
     */
    private String scoreHist = "";
    /**
     * 喜欢的专业list [用,区分专业id, 专业名]
     * Len10000
     */
    private String favoredMajorList = "";
    /**
     * 不喜欢的专业lit
     * Len10000
     */
    private String unfavoredMajorList = "";

    private String examRank;

    /*
体检限报信息
* */
    private String limitationInfos;
    /*
    客户密码
    * */
    private String password;

    /*
     * 用户登录失效时间
     * */
    private Date userExpireDate;

    /*
身高
* */
    private String height;

    /*
     * 是否在界面 选择过体检限报  1: 选择过  0:未选择过
     * */
    private Integer hasSelPhysicLimit;

    /**
     * 户籍所在地
     * Len100
     */
    private String birthPlace = "";
    /**
     * 应届生类型 1: 城镇应届   2: 农村应届     3: 城镇往届  4: 农村往届
     */
    private Integer studentType;
    /**
     * 病史  1: 无既往病史  2:  有既往病史
     */
    private Integer medicalHist;
    /**
     *
     * Len20
     */
    private String weight = "";
    /**
     *
     * Len20
     */
    private String bmi = "";
    /**
     * 报考老师
     * Len20
     */
    private String creatorTeacher = "";
    /**
     * 助理老师
     * Len20
     */
    private String assistantTeacher = "";
    /**
     * 父亲姓名
     * Len10
     */
    private String nameF = "";
    /**
     * 母亲姓名
     * Len10
     */
    private String nameM = "";
    /**
     * 父母专业倾向
     * Len10000
     */
    private String parentPreferenct = "";
    /**
     * 报考意向  [提前批] , 用@@@@区分, 1@@@@2
     * Len200
     */
    private String preAdminPref = "";
    /**
     * 目标院校  [提前批]
     * Len400
     */
    private String preAdminUnis = "";
    /**
     * 目标专业  [提前批]
     * Len400
     */
    private String preAdminMajors = "";
    /**
     * 体检是否合格  [提前批]
     * Len10
     */
    private String preAdminPhysExam = "";
    /**
     * 提前批/综评备注
     * Len10000
     */
    private String preAdminComments = "";
    /**
     * 报考意向  [中外合作], 用@@@@区分, 1@@@@2
     * Len50
     */
    private String overseasPref = "";
    /**
     * 学费接受程度  [中外合作]
     */
    private Integer overseasTuition;
    /**
     *  学制  [中外合作] , 用@@@@区分, 1@@@@2
     * Len50
     */
    private String overseasDuration = "";
    /**
     * 中外合作/港澳备注  [中外合作]
     * Len10000
     */
    private String overseasComments = "";
    /**
     * 地域倾向  [普通批意向]
     * Len100
     */
    private String adminPrefRegion = "";
    /**
     * 优选专业  [普通批意向]
     * Len100
     */
    private String adminPrefMajor1 = "";
    /**
     * 次选专业  [普通批意向]
     * Len100
     */
    private String adminPrefMajor2 = "";
    /**
     * 排斥专业  [普通批意向]
     * Len100
     */
    private String adminExcluMajor = "";
    /**
     *  普通批备注  [普通批意向]
     * Len10000
     */
    private String adminComments = "";
    /**
     * 重要沟通记录  [ time%%%%type%%%%majors @@@@ time%%%%type%%%%majors]
     * Len20000
     */
    private String importRecs = "";

    public com.msg.ClientInfo ConvertToDTO() {
        com.msg.ClientInfo.Builder info = com.msg.ClientInfo.newBuilder();
        info.setTel(tel);
        info.setName(name);
        info.addAllExamSubject(StringParser.SplitString(examSubject, ConstDefine.ItemSperator7));
        info.setGender(gender);
        info.setExamEstScoreMin(examEstScoreMin);
        info.setExamEstScoreMax(examEstScoreMax);
        info.setExamEstRankMin(examEstRankMin);
        info.setExamEstRankMax(examEstRankMax);
        info.setProvince(province);
        info.setExamYear(examYear);
        info.setHighSchool(highSchool);
        info.setEng1StScore(eng1stScore);
        info.setExamScore(examScore);


        info.setSingleSubjScore(singleSubjScore);
        info.setPhysExam(physExam);
        info.setPreSubmission(preSubmission);
        info.setCompanyF(companyF);
        info.setTelF(telF);
        info.setJobF(jobF);
        info.setSocialF(socialF);
        info.setCompanyM(companyM);
        info.setTelM(telM);
        info.setJobM(jobM);
        info.setSocialM(socialM);
        info.setUniPlan(uniPlan);
        info.setUniMajor(uniMajor);
        info.setUniComment(uniComment);
        info.setUniSubmission(uniSubmission);
        info.setStudentIntro(studentIntro);
//        info.setScoreHist(scoreHist);
        info.addAllScoreHist(ParseScoreHistItemToProto(scoreHist));


        com.msg.scoreHistItem histItem = ParseScoreHistItemToSingleProto(singleSubjScore);
        info.setChineseSingle(histItem.getChinese());
        info.setMathSingle(histItem.getMath());
        info.setEnglishSingle(histItem.getEnglish());
        info.setPhysicSingle(histItem.getPhysic());
        info.setChemistrySingle(histItem.getChemistry());
        info.setPoliticsSingle(histItem.getPolitics());
        info.setHistorySingle(histItem.getHistory());
        info.setGeologySingle(histItem.getGeology());
        info.setBiologySingle(histItem.getBiology());

        info.setExamRank(examRank == null ? "0" : examRank);


//            builder.setChinese(items.size() <= 1 ? "0" : items.get(1));
//            builder.setMath(items.size() <= 2 ? "0" : items.get(2));
//            builder.setEnglish(items.size() <= 3 ? "0" : items.get(3));
//            builder.setPhysic(items.size() <= 4 ? "0" : items.get(4));
//            builder.setChemistry(items.size() <= 5 ? "0" : items.get(5));
//            builder.setPolitics(items.size() <= 6 ? "0" : items.get(6));
//            builder.setHistory(items.size() <= 7 ? "0" : items.get(7));
//            builder.setGeology(items.size() <= 8 ? "0" : items.get(8));

//        com.msg.scoreHistItem limitationInfos = ParsLimitationItemToProto(singleSubjScore);
        info.addAllLimitationInfos(ParsLimitationItemToProto(limitationInfos == null? "": limitationInfos));
        info.setPassword(password == null ? "" : password);


        if (unfavoredMajorList.contains(ConstDefine.ItemSperator3)  || favoredMajorList.contains(ConstDefine.ItemSperator3)) {
            //新版数据
//            List<String> favMajorInfo = StringParser.SplitString(favoredMajorList, ConstDefine.ItemSperator5);
//            List<String> unfavMajorInfo = StringParser.SplitString(unfavoredMajorList, ConstDefine.ItemSperator5);
//
//            info.addAllFavoredMajorList(StringParser.SplitLongNumber(favMajorInfo.size() == 0 ? "" : favMajorInfo.get(0), ConstDefine.ItemSperator7));
//            info.addAllUnfavoredMajorList(StringParser.SplitLongNumber(unfavMajorInfo.size() == 0 ? "" : unfavMajorInfo.get(0), ConstDefine.ItemSperator7));
//
//            info.addAllFavoredMajorNameList(StringParser.SplitString(favMajorInfo.size() < 2 ? "" : favMajorInfo.get(1), ConstDefine.ItemSperator7));
//            info.addAllUnfavoredMajorNameList(StringParser.SplitString(unfavMajorInfo.size() < 2 ? "" : unfavMajorInfo.get(1), ConstDefine.ItemSperator7));


            List<Major1stClsInfo> favMajor1stClsInfos = CommonUtil.ParsMajorItemToProto(favoredMajorList);
            List<Major1stClsInfo> unfavMajor1stClsInfos = CommonUtil.ParsMajorItemToProto(unfavoredMajorList);
            info.addAllFavoredMajorCls1List(favMajor1stClsInfos);
            info.addAllUnfavoredMajorCls1List(unfavMajor1stClsInfos);

            //添加喜欢的专业id和名字
            List<String> allfavName = new ArrayList<>();
            List<Long> allfavId = new ArrayList<>();
            for (int i = 0; i < favMajor1stClsInfos.size(); i++) {
                Major1stClsInfo major1stClsInfo = favMajor1stClsInfos.get(i);
                for (int j = 0; j < major1stClsInfo.getMajor2NdClsInfoCount(); j++) {
                    Major2ndClsInfo major2ndClsInfo = major1stClsInfo.getMajor2NdClsInfo(j);
                    for (int k = 0; k < major2ndClsInfo.getMajor3RdClsInfoCount(); k++) {
                        Major3rdClsInfo major3rdClsInfo = major2ndClsInfo.getMajor3RdClsInfo(k);
                        allfavId.add(major3rdClsInfo.getId());
                        allfavName.add(major3rdClsInfo.getName());
                    }
                }
            }
            info.addAllFavoredMajorList(allfavId);
            info.addAllFavoredMajorNameList(allfavName);

            //添加不喜欢的专业id和名字
            List<String> allunfavName = new ArrayList<>();
            List<Long> allunfavId = new ArrayList<>();
            for (int i = 0; i < unfavMajor1stClsInfos.size(); i++) {
                Major1stClsInfo major1stClsInfo = unfavMajor1stClsInfos.get(i);
                for (int j = 0; j < major1stClsInfo.getMajor2NdClsInfoCount(); j++) {
                    Major2ndClsInfo major2ndClsInfo = major1stClsInfo.getMajor2NdClsInfo(j);
                    for (int k = 0; k < major2ndClsInfo.getMajor3RdClsInfoCount(); k++) {
                        Major3rdClsInfo major3rdClsInfo = major2ndClsInfo.getMajor3RdClsInfo(k);
                        allunfavId.add(major3rdClsInfo.getId());
                        allunfavName.add(major3rdClsInfo.getName());
                    }
                }
            }
            info.addAllUnfavoredMajorList(allunfavId);
            info.addAllUnfavoredMajorNameList(allunfavName);


        } else {
            //旧数据
            List<String> favMajorInfo = StringParser.SplitString(favoredMajorList, ConstDefine.ItemSperator5);
            List<String> unfavMajorInfo = StringParser.SplitString(unfavoredMajorList, ConstDefine.ItemSperator5);

            info.addAllFavoredMajorList(StringParser.SplitLongNumber(favMajorInfo.size() == 0 ? "" : favMajorInfo.get(0), ConstDefine.ItemSperator7));
            info.addAllUnfavoredMajorList(StringParser.SplitLongNumber(unfavMajorInfo.size() == 0 ? "" : unfavMajorInfo.get(0), ConstDefine.ItemSperator7));

            info.addAllFavoredMajorNameList(StringParser.SplitString(favMajorInfo.size() < 2 ? "" : favMajorInfo.get(1), ConstDefine.ItemSperator7));
            info.addAllUnfavoredMajorNameList(StringParser.SplitString(unfavMajorInfo.size() < 2 ? "" : unfavMajorInfo.get(1), ConstDefine.ItemSperator7));


            //创建对象
            List<Major1stClsInfo> major1stClsInfoList = new ArrayList<>();
            if(info.getFavoredMajorListCount() > 0){
                major1stClsInfoList.add(GetTempMajor1stCls(info.getFavoredMajorListList(), info.getFavoredMajorNameListList()));
            }
            info.addAllFavoredMajorCls1List(major1stClsInfoList);

            List<Major1stClsInfo> unFavmajor1stClsInfoList = new ArrayList<>();
            if(info.getUnfavoredMajorListCount() > 0){
                unFavmajor1stClsInfoList.add(GetTempMajor1stCls(info.getUnfavoredMajorListList(), info.getUnfavoredMajorNameListList()));
            }

            info.addAllUnfavoredMajorCls1List(unFavmajor1stClsInfoList);

        }

        //查询授权用户
        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderByClientTel(this.tel);
        List<Long> templist2 = orderInfoVO.ConvAuthTeachID(orderInfoVO.getAuthorTeacherIds(), ConstDefine.ItemSperator7);
        info.addAllAuthorTeacherIds(templist2);


        info.setUserExpireDate(userExpireDate == null ? 0 : userExpireDate.getTime());

        info.setHeight(height == null ? "0" : height);

        //是否 在界面 选择过体检限报
        int limitcount = info.getLimitationInfosCount();
        info.setHasSelectPhysicLimitation(hasSelPhysicLimit== null ? (limitcount > 0 ? EnumHasSelLimit.SelLimit.getStateID() : EnumHasSelLimit.Selected.getStateID()) :  hasSelPhysicLimit);


        info.setBirthPlace(birthPlace == null ? "" :  birthPlace);
        info.setStudentType(studentType == null ? 1 : studentType);
        info.setMedicalHist(medicalHist == null ? 1 : medicalHist);
        info.setWeight(weight== null ? "" :weight);
        info.setBmi(bmi== null ? "" :bmi);
        info.setCreatorTeacher(creatorTeacher== null ? "" :creatorTeacher);
        info.setAssistantTeacher(assistantTeacher== null ? "" :assistantTeacher);
        info.setNameF(nameF== null ? "" :nameF);
        info.setNameM(nameM== null ? "" :nameM);
        info.setParentPreferenct(parentPreferenct== null ? "" :parentPreferenct);

        List<Integer> preAdminPrefItems = StringParser.SplitNumber(preAdminPref, ConstDefine.ItemSperator5);
        info.addAllPreAdminPref(preAdminPrefItems);
//        info.setPreAdminPref(preAdminPref);

        info.setPreAdminUnis(preAdminUnis== null ? "" :preAdminUnis);
        info.setPreAdminMajors(preAdminMajors== null ? "" :preAdminMajors);
        info.setPreAdminPhysExam(preAdminPhysExam== null ? "" :preAdminPhysExam);
        info.setPreAdminComments(preAdminComments== null ? "" :preAdminComments);

//        info.setOverseasPref(overseasPref);
        List<String> preAdminoverseasPref = StringParser.SplitString(overseasPref, ConstDefine.ItemSperator5);
        info.addAllOverseasPref(preAdminoverseasPref);

        info.setOverseasTuition(overseasTuition == null ? 0 : overseasTuition);

//        info.setOverseasDuration(overseasDuration);
        List<String> preAdminoverseasDuration= StringParser.SplitString(overseasDuration, ConstDefine.ItemSperator5);
        info.addAllOverseasDuration(preAdminoverseasDuration);

        info.setOverseasComments(overseasComments== null ? "" :overseasComments);
        info.setAdminPrefRegion(adminPrefRegion== null ? "" :adminPrefRegion);
        info.setAdminPrefMajor1(adminPrefMajor1== null ? "" :adminPrefMajor1);
        info.setAdminPrefMajor2(adminPrefMajor2== null ? "" :adminPrefMajor2);
        info.setAdminExcluMajor(adminExcluMajor== null ? "" :adminExcluMajor);
        info.setAdminComments(adminComments== null ? "" : adminComments);

        //添加重要记录
        info.addAllImportRecs(GameUtil.ParseImportantRecToProto(importRecs));

        return info.build();
    }

    private Major1stClsInfo GetTempMajor1stCls(List<Long> majorIds,  List<String> majorNames){
        com.msg.Major1stClsInfo.Builder builder = Major1stClsInfo.newBuilder();
        builder.setName("");
        List<Major2ndClsInfo> builder2List = new ArrayList<>();
        com.msg.Major2ndClsInfo.Builder builder2 = Major2ndClsInfo.newBuilder();
        builder2.setName("");

        List<Major3rdClsInfo> builder3List = new ArrayList<>();
        for (int i = 0; i <majorIds.size(); i++) {
            com.msg.Major3rdClsInfo.Builder builder3 = Major3rdClsInfo.newBuilder();
            builder3.setId(majorIds.get(i));
            builder3.setName(majorNames.get(i));
            builder3List.add(builder3.build());
        }
        builder2.addAllMajor3RdClsInfo(builder3List);
        builder2List.add(builder2.build());
        builder.addAllMajor2NdClsInfo(builder2List);

        return  builder.build();
    }


    public static ClientInfoVO ConvertFromDTO(com.msg.ClientInfo p) {
        ClientInfoVO info = new ClientInfoVO();
        info = info.UpdateFromDTO(p);
//        info.setTel(p.getTel());
//        info.setName(p.getName());
//        info.setExamSubject(p.getExamSubject());
//        info.setGender(p.getGender());
//        info.setExamEstScoreMin(p.getExamEstScoreMin());
//        info.setExamEstScoreMax(p.getExamEstScoreMax());
////        info.setExamEstRankMin();
////        info.setExamEstRankMax();
//        info.setProvince(p.getProvince());
//        info.setExamYear(p.getExamYear());
//        info.setHighSchool(p.getHighSchool());
//        info.setEng1stScore(p.getEng1StScore());
//        info.setExamScore(p.getExamScore());
//        info.setSingleSubjScore(p.getSingleSubjScore());
//        info.setPhysExam(p.getPhysExam());
//        info.setPreSubmission(p.getPreSubmission());
//        info.setCompanyF(p.getCompanyF());
//        info.setTelF(p.getTelF());
//        info.setJobF(p.getJobF());
//        info.setSocialF(p.getSocialF());
//        info.setCompanyM(p.getCompanyM());
//        info.setTelM(p.getTelM());
//        info.setJobM(p.getJobM());
//        info.setSocialM(p.getSocialM());
//        info.setUniPlan(p.getUniPlan());
//        info.setUniMajor(p.getUniMajor());
//        info.setUniComment(p.getUniComment());
//        info.setUniSubmission(p.getUniSubmission());
//        info.setStudentIntro(p.getStudentIntro());
//        info.setScoreHist(ClientInfoVO.ParseScoreHistItemFromProto(p.getScoreHistList()));
//        info.setFavoredMajorList(StringParser.MakeLongString(p.getFavoredMajorListList(), ConstDefine.ItemSperator7));
//        info.setUnfavoredMajorList(StringParser.MakeLongString(p.getUnfavoredMajorListList(), ConstDefine.ItemSperator7));
////        info.setUnfavoredMajorList(p.getUnfavoredMajorListCount());

        return info;
    }


    public ClientInfoVO UpdateFromDTO(com.msg.ClientInfo p) {
//        ClientInfoVO info = new ClientInfoVO();
        this.setTel(p.getTel());
        this.setName(p.getName());
        this.setExamSubject(StringParser.MakeString(p.getExamSubjectList(), ConstDefine.ItemSperator7));
        this.setGender(p.getGender());
        this.setExamEstScoreMin(p.getExamEstScoreMin());
        this.setExamEstScoreMax(p.getExamEstScoreMax());
        this.setExamEstRankMin(p.getExamEstRankMin());
        this.setExamEstRankMax(p.getExamEstRankMax());
//        this.setExamEstRankMax(RankManager.GetInstance().GetRankByScore(p.getExamEstScoreMax()) + "");
//        this.setExamEstRankMin(RankManager.GetInstance().GetRankByScore(p.getExamEstScoreMin()) + "");

        this.setProvince(p.getProvince());
        this.setExamYear(p.getExamYear());
        this.setHighSchool(p.getHighSchool());
        this.setEng1stScore(p.getEng1StScore());
        this.setExamScore(p.getExamScore());
//        this.setSingleSubjScore(ParseSingleScoreHistItemFromProto(p.getSingleSubjScore()));
        this.setPhysExam(p.getPhysExam());
        this.setPreSubmission(p.getPreSubmission());
        this.setCompanyF(p.getCompanyF());
        this.setTelF(p.getTelF());
        this.setJobF(p.getJobF());
        this.setSocialF(p.getSocialF());
        this.setCompanyM(p.getCompanyM());
        this.setTelM(p.getTelM());
        this.setJobM(p.getJobM());
        this.setSocialM(p.getSocialM());
        this.setUniPlan(p.getUniPlan());
        this.setUniMajor(p.getUniMajor());
        this.setUniComment(p.getUniComment());
        this.setUniSubmission(p.getUniSubmission());
        this.setStudentIntro(p.getStudentIntro());
        this.setScoreHist(ClientInfoVO.ParseScoreHistItemFromProto(p.getScoreHistList()));


//        this.setFavoredMajorList(StringParser.MakeLongString(p.getFavoredMajorListList(), ConstDefine.ItemSperator7)
//                + ConstDefine.ItemSperator5 +
//                StringParser.MakeString(p.getFavoredMajorNameListList(), ConstDefine.ItemSperator7)
//        );
//        this.setUnfavoredMajorList(StringParser.MakeLongString(p.getUnfavoredMajorListList(), ConstDefine.ItemSperator7)
//                + ConstDefine.ItemSperator5 +
//                StringParser.MakeString(p.getUnfavoredMajorNameListList(), ConstDefine.ItemSperator7)
//        );

        String favMajor1stClsInfos = CommonUtil.ParseMajorItemFromProto(p.getFavoredMajorCls1ListList());
        String unfavMajor1stClsInfos = CommonUtil.ParseMajorItemFromProto(p.getUnfavoredMajorCls1ListList());
        this.setFavoredMajorList(favMajor1stClsInfos);
        this.setUnfavoredMajorList(unfavMajor1stClsInfos);



//        info.setUnfavoredMajorList(p.getUnfavoredMajorListCount());


//        this.setSingleSubjScore(ParseSingleScoreHistItemFromProto(p.getSingleSubjScore()));
        com.msg.scoreHistItem.Builder builder = scoreHistItem.newBuilder();
        builder.setExamName("e");
        builder.setChinese(p.getChineseSingle().isEmpty() ? "0" : p.getChineseSingle());
        builder.setMath(p.getMathSingle().isEmpty() ? "0" : p.getMathSingle());
        builder.setEnglish(p.getEnglishSingle().isEmpty() ? "0" : p.getEnglishSingle());
        builder.setPhysic(p.getPhysicSingle().isEmpty() ? "0" : p.getPhysicSingle());
        builder.setChemistry(p.getChemistrySingle().isEmpty() ? "0" : p.getChemistrySingle());
        builder.setPolitics(p.getPoliticsSingle().isEmpty() ? "0" : p.getPoliticsSingle());
        builder.setHistory(p.getHistorySingle().isEmpty() ? "0" : p.getHistorySingle());
        builder.setGeology(p.getGeologySingle().isEmpty() ? "0" : p.getGeologySingle());
        builder.setBiology(p.getBiologySingle().isEmpty() ? "0" : p.getBiologySingle());

        builder.setTotalScore("0");
        builder.setClsRank("0");
        builder.setSchoolRank("0");
        builder.setDistRank("0");
        builder.setTargetScore("0");

        this.setSingleSubjScore(ParseSingleScoreHistItemFromProto(builder.build()));

        //去除自动获取位次
//        if(StringUtils.isEmpty(examScore) || StringUtils.isEmpty(examYear)){
//            this.setExamRank("");
//        }else {
//            Integer rank = RankManager.GetInstance().GetRankByScore(getExamScore(), getExamYear());
//            this.setExamRank(rank + ""); // this.setExamRank(p.getExamRank());
//        }
        this.setExamRank(p.getExamRank());

        this.setPassword(p.getPassword());


        this.setLimitationInfos(ParseLimitationItemFromProto(p.getLimitationInfosList()));


        this.setUserExpireDate(new Date(p.getUserExpireDate()));

        int limitCount = p.getLimitationInfosCount();
        this.setHasSelPhysicLimit(limitCount >0 ? EnumHasSelLimit.SelLimit.getStateID() : p.getHasSelectPhysicLimitation());


        this.setBirthPlace(p.getBirthPlace());
        this.setStudentType(p.getStudentType());
        this.setMedicalHist(p.getMedicalHist());
        this.setHeight(p.getHeight());
        this.setWeight(p.getWeight());
        this.setBmi(p.getBmi());
        this.setCreatorTeacher(p.getCreatorTeacher());
        this.setAssistantTeacher(p.getAssistantTeacher());
        this.setNameF(p.getNameF());
        this.setNameM(p.getNameM());
        this.setParentPreferenct(p.getParentPreferenct());

        this.setPreAdminPref(StringParser.MakeIntString(p.getPreAdminPrefList(), ConstDefine.ItemSperator5));

        this.setPreAdminUnis(p.getPreAdminUnis());
        this.setPreAdminMajors(p.getPreAdminMajors());
        this.setPreAdminPhysExam(p.getPreAdminPhysExam());
        this.setPreAdminComments(p.getPreAdminComments());

        this.setOverseasPref(StringParser.MakeString(p.getOverseasPrefList(), ConstDefine.ItemSperator5));

        this.setOverseasTuition(p.getOverseasTuition());
        this.setOverseasDuration(StringParser.MakeString(p.getOverseasDurationList(), ConstDefine.ItemSperator5));
        this.setOverseasComments(p.getOverseasComments());

        this.setAdminPrefRegion(p.getAdminPrefRegion());
        this.setAdminPrefMajor1(p.getAdminPrefMajor1());
        this.setAdminPrefMajor2(p.getAdminPrefMajor2());
        this.setAdminExcluMajor(p.getAdminExcluMajor());
        this.setAdminComments(p.getAdminComments());

        this.setImportRecs(GameUtil.ParseImportantRecFromProto(p.getImportRecsList()));
        return this;
    }

    /*****************************************************************************/
    //转成proto
    private static List<scoreHistItem> ParseScoreHistItemToProto(String scoreHist) {
        List<scoreHistItem> list = new ArrayList<>();

        List<String> hists = StringParser.SplitString(scoreHist, ConstDefine.ItemSperator6, true);
        for (int i = 0; i < hists.size(); i++) {
            String hist = hists.get(i);
//            List<String> items = StringParser.SplitString(hist, ConstDefine.ItemSperator5);
//            if (items.size() == 0) {
//                // System.out.println("eror");
//            }
//            com.msg.scoreHistItem.Builder builder = scoreHistItem.newBuilder();
//            builder.setExamName(items.size() <= 0 ? "emptyExam" : items.get(0));
//            builder.setChinese(items.size() <= 1 ? "0" : items.get(1));
//            builder.setMath(items.size() <= 2 ? "0" : items.get(2));
//            builder.setEnglish(items.size() <= 3 ? "0" : items.get(3));
//            builder.setPhysic(items.size() <= 4 ? "0" : items.get(4));
//            builder.setChemistry(items.size() <= 5 ? "0" : items.get(5));
//            builder.setPolitics(items.size() <= 6 ? "0" : items.get(6));
//            builder.setHistory(items.size() <= 7 ? "0" : items.get(7));
//            builder.setGeology(items.size() <= 8 ? "0" : items.get(8));
//            builder.setTotalScore(items.size() <= 9 ? "0" : items.get(9));
//            builder.setClsRank(items.size() <= 10 ? "0" : items.get(10));
//            builder.setSchoolRank(items.size() <= 11 ? "0" : items.get(11));
//            builder.setDistRank(items.size() <= 12 ? "0" : items.get(12));
//            builder.setTargetScore(items.size() <= 13 ? "0" : items.get(13));

//            list.add(builder.build());
            list.add(ParseScoreHistItemToSingleProto(hist));
        }

        return list;
    }

    //
    private static String ParseScoreHistItemFromProto(List<scoreHistItem> protoList) {

        String str = "";
        for (int i = 0; i < protoList.size(); i++) {
            scoreHistItem item = protoList.get(i);
//            str += item.getExamName();
//            str += ConstDefine.ItemSperator5;
//            str += item.getChinese();
//            str += ConstDefine.ItemSperator5;
//            str += item.getMath();
//            str += ConstDefine.ItemSperator5;
//            str += item.getEnglish();
//            str += ConstDefine.ItemSperator5;
//            str += item.getPhysic();
//            str += ConstDefine.ItemSperator5;
//            str += item.getChemistry();
//            str += ConstDefine.ItemSperator5;
//            str += item.getPolitics();
//            str += ConstDefine.ItemSperator5;
//            str += item.getHistory();
//            str += ConstDefine.ItemSperator5;
//            str += item.getGeology();
//            str += ConstDefine.ItemSperator5;
//            str += item.getTotalScore();
//            str += ConstDefine.ItemSperator5;
//            str += item.getClsRank();
//            str += ConstDefine.ItemSperator5;
//            str += item.getSchoolRank();
//            str += ConstDefine.ItemSperator5;
//            str += item.getDistRank();
//            str += ConstDefine.ItemSperator5;
//            str += item.getTargetScore();
            str += ParseSingleScoreHistItemFromProto(item);
            if (i < protoList.size() - 1) {
                str += ConstDefine.ItemSperator6;
            }

        }

        return str;
    }

    //转成proto
    private static scoreHistItem ParseScoreHistItemToSingleProto(String hist) {
//        List<scoreHistItem> list = new ArrayList<>();

//        List<String> hists = StringParser.SplitString(scoreHist, ConstDefine.ItemSperator6);
//        for (int i = 0; i < hists.size(); i++) {
//            String hist = hists.get(i);
        List<String> items = StringParser.SplitString(hist, ConstDefine.ItemSperator5, true);
        if (items.size() == 0) {
            // System.out.println("eror");
        }
        com.msg.scoreHistItem.Builder builder = scoreHistItem.newBuilder();
        builder.setExamName(items.size() <= 0 ? "emptyExam" : items.get(0));
        builder.setChinese(items.size() <= 1 ? "0" : items.get(1));
        builder.setMath(items.size() <= 2 ? "0" : items.get(2));
        builder.setEnglish(items.size() <= 3 ? "0" : items.get(3));
        builder.setPhysic(items.size() <= 4 ? "0" : items.get(4));
        builder.setChemistry(items.size() <= 5 ? "0" : items.get(5));
        builder.setPolitics(items.size() <= 6 ? "0" : items.get(6));
        builder.setHistory(items.size() <= 7 ? "0" : items.get(7));
        builder.setGeology(items.size() <= 8 ? "0" : items.get(8));
        builder.setBiology(items.size() <= 9 ? "0" : items.get(9));
        builder.setTotalScore(items.size() <= 10 ? "0" : items.get(10));
        builder.setClsRank(items.size() <= 11 ? "0" : items.get(11));
        builder.setSchoolRank(items.size() <= 12 ? "0" : items.get(12));
        builder.setDistRank(items.size() <= 13 ? "0" : items.get(13));
        builder.setTargetScore(items.size() <= 14 ? "0" : items.get(14));
        builder.setExameDate(items.size() <= 15 ? 0 :  Long.parseLong(items.get(15)));
        builder.setComment(items.size() <= 16 ? "" : items.get(16));

//            list.add(builder.build());
//        }

        return builder.build();
    }

    //
    private static String ParseSingleScoreHistItemFromProto(scoreHistItem item) {

        String str = "";

//            scoreHistItem item = protoList.get(i);
        str += item.getExamName();
        str += ConstDefine.ItemSperator5;
        str += item.getChinese();
        str += ConstDefine.ItemSperator5;
        str += item.getMath();
        str += ConstDefine.ItemSperator5;
        str += item.getEnglish();
        str += ConstDefine.ItemSperator5;
        str += item.getPhysic();
        str += ConstDefine.ItemSperator5;
        str += item.getChemistry();
        str += ConstDefine.ItemSperator5;
        str += item.getPolitics();
        str += ConstDefine.ItemSperator5;
        str += item.getHistory();
        str += ConstDefine.ItemSperator5;
        str += item.getGeology();
        str += ConstDefine.ItemSperator5;
        str += item.getBiology();
        str += ConstDefine.ItemSperator5;
        str += item.getTotalScore();
        str += ConstDefine.ItemSperator5;
        str += item.getClsRank();
        str += ConstDefine.ItemSperator5;
        str += item.getSchoolRank();
        str += ConstDefine.ItemSperator5;
        str += item.getDistRank();
        str += ConstDefine.ItemSperator5;
        str += item.getTargetScore();

        str += ConstDefine.ItemSperator5;
        str += item.getExameDate();
        str += ConstDefine.ItemSperator5;
        str += item.getComment();

//            if (i < protoList.size() - 1) {
//                str += ConstDefine.ItemSperator6;
//            }

//        }

        return str;
    }


    /*****************************************************************************/
    //体检限报
    //转成proto
    public static List<PhysicLimitationInfo> ParsLimitationItemToProto(String scoreHist) {
       return CommonUtil.ParsLimitationItemToProto(scoreHist);
    }

    //
    private static String ParseLimitationItemFromProto(List<PhysicLimitationInfo> protoList) {
        return CommonUtil.ParseLimitationItemFromProto(protoList);
    }


    /*****************************************************************************/


}
