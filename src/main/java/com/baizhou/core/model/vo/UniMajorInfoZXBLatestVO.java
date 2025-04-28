package com.baizhou.core.model.vo;

import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumHasGenderScore;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.MajorClsManager;
import com.baizhou.util.GameUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;


/**
 * Service层参数和返回值使用VO
 */
@Data
@Scope("prototype")
@Component
@Getter
@Setter
public class UniMajorInfoZXBLatestVO {
    /**
     * id
     */
    private Long id;
    /**
     * 院校专业组代码
     * Len100
     */
    private String uniMajorCode = "";
    /**
     * 批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     */
    private Integer pici;
    /**
     * 院校名称
     * Len100
     */
    private String schoolName = "";
    /**
     * 双一流,
     */
    private Integer schoolLvl1;
    /**
     * 985; 用1表示 正确
     */
    private Integer schoolLvl2;
    /**
     * 211 用1表示
     */
    private Integer schoolLvl3;
    /**
     * 院校类型 综合, 理工、综合、师范、医药
     * Len100
     */
    private String schoolType = "";
    /**
     * 省
     * Len100
     */
    private String province = "";
    /**
     * 市
     * Len100
     */
    private String city = "";
    /**
     * 隶属
     * Len100
     */
    private String belongDep = "";
    /**
     * 录取规则
     * Len100
     */
    private String admissionRule = "";
    /**
     * 排名
     * Len100
     */
    private String schoolRank = "";
    /**
     * 22计划数 = 组内求和
     */
    private Integer studentNum22Plan;
    /**
     * 科目要求
     * Len100
     */
    private String subjectRequirement = "";
    /**
     * 22录取数 = 组内求和
     */
    private Integer studentNum22Admit;
    /**
     * 22组分数
     */
    private Integer scoreFor22;
    /**
     * 22组位次
     */
    private Integer rankFor22;
    /**
     * 21年计划数 = 组内求和
     */
    private Integer studentNum21Plan;
    /**
     * 21录取数 = 组内求和
     */
    private Integer studentNum21Admit;
    /**
     * 21组分数
     */
    private Integer scoreFor21;
    /**
     * 21组位次
     */
    private Integer rankFor21;
    /**
     * 20年计划数 = 组内求和
     */
    private Integer studentNum20Plan;
    /**
     * 20录取数 = 组内求和
     */
    private Integer studentNum20Admit;
    /**
     * 20组分数
     */
    private Integer scoreFor20;
    /**
     * 20组位次
     */
    private Integer rankFor20;
    /**
     * 专业代码
     * Len100
     */
    private String majorCode = "";
    /**
     * 专业名称
     * Len1000
     */
    private String majorName = "";
    /**
     * 专业备注
     * Len1000
     */
    private String majorComments = "";
    /**
     * 专业其他信息
     * Len100
     */
    private String majorOtherInfo = "";
    /**
     * 外语语种
     * Len100
     */
    private String requireLang = "";
    /**
     * 是否口试
     * Len100
     */
    private String requireInterview = "";
    /**
     * 学制
     * Len100
     */
    private String majorDuration = "";
    /**
     * 收费标准
     * Len100
     */
    private String majorTuition = "";
    /**
     * 22年计划数
     */
    private Integer major22Plan;
    /**
     * 22录取数
     */
    private Integer major22Admin;
    /**
     * 22年低
     */
    private Integer major22LowScore;
    /**
     * 22年低位次
     */
    private Integer major22LowRank;
    /**
     * 21年计划数
     */
    private Integer major21Plan;
    /**
     * 21录取数
     */
    private Integer major21Admin;
    /**
     * 21年低
     */
    private Integer major21LowScore;
    /**
     * 21年低位次
     */
    private Integer major21LowRank;
    /**
     * 20年计划数
     */
    private Integer major20Plan;
    /**
     * 20录取数
     */
    private Integer major20Admin;
    /**
     * 20年低
     */
    private Integer major20LowScore;
    /**
     * 20年低位次
     */
    private Integer major20LowRank;
    /**
     * 数据版本
     */
    private Integer dataVersion;
    /**
     * 如果是最新的专业信息DB, 对应到专业总DB中该专业组的id
     */
    private Long idInHistDB;
    /**
     * 专业类表 ids [id,id,id]
     * Len50000
     */
    private String majorClsIds = "";

    /*
保存图片类型1,2,3
* */
    private String majorIcon;

    /**
     * 22年高分
     */
    private Integer major22HScore;
    /**
     * 22年高位次
     */
    private Integer major22HRank;
    /**
     * 22年平均分
     */
    private Integer major22AvgScore;
    /**
     * 22年平均位次
     */
    private Integer major22AvgRank;
    /**
     * 21年高分
     */
    private Integer major21HScore;
    /**
     * 21年高位次
     */
    private Integer major21HRank;
    /**
     * 21年平均分
     */
    private Integer major21AvgScore;
    /**
     * 21年平均位次
     */
    private Integer major21AvgRank;
    /**
     * 20年高分
     */
    private Integer major20HScore;
    /**
     * 20年高位次
     */
    private Integer major20HRank;
    /**
     * 20年平均分
     */
    private Integer major20AvgScore;
    /**
     * 20年平均位次
     */
    private Integer major20AvgRank;

    /*
     * 23年计划数
     * */
    private Integer studentNum23Plan;

    /*
     * 23年专业计划数
     * */
    private Integer major23Plan;
    /*
    体检限报信息(1),(2)
    * */
    private String limitationInfos;

    /*
     * 英语成绩
     * */
    private Integer englishScore;

    /*
     * 数学成绩
     * */
    private Integer mathScore;

    /*
志愿年份
* */
    private String examYear;
    /*
    身高限制
    * */
    private String heightLimit;

    /*
     * 对应到最新的专业信息DB专业
     * */
    private Long idInLatestDB;

    /**
     * 22组分数 女子
     */
    private Integer scoreFor22Nv;
    /**
     * 22组位次 女子
     */
    private Integer rankFor22Nv;
    /**
     * 22年低 女子
     */
    private Integer major22LowScoreNv;
    /**
     * 22年低位次 女子
     */
    private Integer major22LowRankNv;

    /**
     * 是否区分 男女分数 0: 不区分  1:区分男女
     */
    private Integer hasGenderScore;

    /**
     * 是否中外合作;  用1表示  正
     */
    private Integer isZhongWai;
    /**
     * 是否本硕; 用1表示 正确
     */
    private Integer isBenSuo;

    /**
     * 保研率
     * Len100
     */
    private String baoYanLv = "";
    /**
     * 升学率
     * Len100
     */
    private String shengXueLv = "";

    /**
     * 专业组中, 包含类表 ids [id,id,id] 用|区分
     * Len50000
     */
    private String majorGroupClsIds = "";
    /**
     * 专业组中,专业名称, 用|区分
     * Len50000
     */
    private String majorGroupMajorName = "";
    /**
     * 专业名称, 用,区分
     * Len50000
     */
    private String majorNameDetails = "";

    public com.msg.UniMajorInfo ConvertToDTO() {
        com.msg.UniMajorInfo.Builder info = com.msg.UniMajorInfo.newBuilder();
//        info.setId(id == null ? 0 : id);
        info.setId(idInHistDB == null ? 0 : idInHistDB);
        info.setUniMajorCode(uniMajorCode);
        info.setPici(pici == null ? 0 : pici);
        info.setSchoolName(schoolName);
        info.setSchoolLvl1(schoolLvl1 == null ? 0 : schoolLvl1);
        info.setSchoolLvl2(schoolLvl2 == null ? 0 : schoolLvl2);
        info.setSchoolLvl3(schoolLvl3 == null ? 0 : schoolLvl3);
        info.setSchoolType(schoolType);
        info.setProvince(province);
        info.setCity(city);
        info.setBelongDep(belongDep);
        info.setAdmissionRule(admissionRule);
        info.setSchoolRank(schoolRank);
        info.setStudentNum22Plan(studentNum22Plan + "");
        info.setSubjectRequirement(subjectRequirement + "");
        info.setStudentNum22Admit(studentNum22Admit + "");
        info.setScoreFor22(scoreFor22 + "");
        info.setRankFor22(rankFor22 + "");
        info.setStudentNum21Plan(studentNum21Plan + "");
        info.setStudentNum21Admit(studentNum21Admit + "");
        info.setScoreFor21(scoreFor21 + "");
        info.setRankFor21(rankFor21 + "");
        info.setStudentNum20Plan(studentNum20Plan + "");
        info.setStudentNum20Admit(studentNum20Admit + "");
        info.setScoreFor20(scoreFor20 + "");
        info.setRankFor20(rankFor20 + "");
        info.setMajorCode(majorCode);
        info.setMajorName(majorName);
        info.setMajorComments(majorComments);
        info.setMajorOtherInfo(majorOtherInfo);
        info.setRequireLang(requireLang);
        info.setRequireInterview(requireInterview);
        info.setMajorDuration(majorDuration);
        info.setMajorTuition(majorTuition);
        info.setMajor22Plan(major22Plan + "");
        info.setMajor22Admin(major22Admin + "");
        info.setMajor22LowScore(major22LowScore + "");
        info.setMajor22LowRank(major22LowRank + "");
        info.setMajor21Plan(major21Plan + "");
        info.setMajor21Admin(major21Admin + "");
        info.setMajor21LowScore(major21LowScore + "");
        info.setMajor21LowRank(major21LowRank + "");
        info.setMajor20Plan(major20Plan + "");
        info.setMajor20Admin(major20Admin + "");
        info.setMajor20LowScore(major20LowScore + "");
        info.setMajor20LowRank(major20LowRank + "");
        info.addAllMajorClsIds(StringParser.SplitLongNumber(majorClsIds, ConstDefine.ItemSperator7));
        info.addAllMajorIcon(StringParser.SplitString(majorIcon, ConstDefine.ItemSperator7));


        info.setMajor20HScore(major20HScore + "");
        info.setMajor20HRank(major20HRank + "");
        info.setMajor20AvgScore(major20AvgScore + "");
        info.setMajor20AvgRank(major20AvgRank + "");

        info.setMajor21HScore(major21HScore + "");
        info.setMajor21HRank(major21HRank + "");
        info.setMajor21AvgScore(major21AvgScore + "");
        info.setMajor21AvgRank(major21AvgRank + "");


        info.setMajor22HScore(major22HScore + "");
        info.setMajor22HRank(major22HRank + "");
        info.setMajor22AvgScore(major22AvgScore + "");
        info.setMajor22AvgRank(major22AvgRank + "");


        info.setStudentNum23Plan((studentNum23Plan == null ? "0" : studentNum23Plan) + "");

        info.setMajor23Plan((major23Plan == null ? "0" : major23Plan) + "");
        info.addAllLimitationInfos(StringParser.SplitString((limitationInfos == null ? "" : limitationInfos) , ConstDefine.ItemSperator6));// (limitationInfos == null ? "" : limitationInfos) + "");

        info.setEnglishScore(englishScore == null ? 0 : englishScore);
//        info.setEnglishScore(uniMajorCode.endsWith("2") ? 100 : 0);
        info.setMathScore(mathScore == null ? 0 : mathScore);

        info.setExamYear(examYear == null ? "2023" : examYear);

        //体检限报信息
        List<Long> majorIds = StringParser.SplitLongNumber(majorClsIds, ConstDefine.ItemSperator7);
//        List<String> physicLimitName = new ArrayList<>();
//        List<Integer> physicLimitColor = new ArrayList<>();
//        for (int i = 0; i < majorIds.size(); i++) {
//            Long majorId = majorIds.get(i);
//            Integer color = MajorClsManager.GetInstance().GetPhysicLimitColorByMajrName(majorId);
//            if(color != null){
//                physicLimitName.add(MajorClsManager.GetInstance().GetMajorVOById(majorId).getMajor3rdCls());
//                physicLimitColor.add(color);
//            }
//        }
//
//        info.addAllLimitedMajorNames(physicLimitName);
//        info.addAllLimitedMajorColor(physicLimitColor);
        GameUtil.AddPhysicLimit(majorIds, info);

        info.setHeightLimit(heightLimit == null ? "0" : heightLimit);


        info.setScoreFor22Nv(scoreFor22Nv == null ? "" : scoreFor22Nv + "");
        info.setRankFor22Nv(rankFor22Nv == null ? "" :rankFor22Nv + "");
        info.setMajor22LowScoreNv(major22LowScoreNv == null ? "" :major22LowScoreNv + "");
        info.setMajor22LowRankNv(major22LowRankNv == null ? "" :major22LowRankNv + "");
        info.setHasGenderScore(hasGenderScore == null ? EnumHasGenderScore.No.getStateID():hasGenderScore);

        info.setIsZhongWai(isZhongWai == null ? 0:isZhongWai);
        info.setIsBenSuo(isBenSuo == null ? 0:isBenSuo);

        info.setBaoYanLv(baoYanLv== null ? "" :baoYanLv);
        info.setShengXueLv(shengXueLv== null ? "" :shengXueLv);

        //        info.addAllMajorGroupMajorName(new ArrayList<>());
//        info.addAllMajorGroupClsIds(new ArrayList<>());
//        info.addAllMajorNameDetails(new ArrayList<>());
        return info.build();
    }


    public static UniMajorInfoZXBLatestVO ConvertFromDTO(com.msg.UniMajorInfo proto) {
        UniMajorInfoZXBLatestVO info = new UniMajorInfoZXBLatestVO();
        info.setId(proto.getId());

        return ConvertFromDTO(proto, info);
    }

    public static UniMajorInfoZXBLatestVO ConvertFromDTO(com.msg.UniMajorInfo proto,  UniMajorInfoZXBLatestVO info) {
//        UniMajorInfoZXBLatestVO info = new UniMajorInfoZXBLatestVO();
//        info.setId(proto.getId());
        info.setUniMajorCode(proto.getUniMajorCode());
        info.setPici(proto.getPici());
        info.setSchoolName(proto.getSchoolName());
        info.setSchoolLvl1(proto.getSchoolLvl1());
        info.setSchoolLvl2(proto.getSchoolLvl2());
        info.setSchoolLvl3(proto.getSchoolLvl3());
        info.setSchoolType(proto.getSchoolType());
        info.setProvince(proto.getProvince());
        info.setCity(proto.getCity());
        info.setBelongDep(proto.getBelongDep());
        info.setAdmissionRule(proto.getAdmissionRule());
        info.setSchoolRank(proto.getSchoolRank());
        info.setStudentNum22Plan(Integer.parseInt(proto.getStudentNum22Plan()));
        info.setSubjectRequirement(proto.getSubjectRequirement());
        info.setStudentNum22Admit(Integer.parseInt(proto.getStudentNum22Admit()));
        info.setScoreFor22(Integer.parseInt(proto.getScoreFor22()));
        info.setRankFor22(Integer.parseInt(proto.getRankFor22()));
        info.setStudentNum21Plan(Integer.parseInt(proto.getStudentNum21Plan()));
        info.setStudentNum21Admit(Integer.parseInt(proto.getStudentNum21Admit()));
        info.setScoreFor21(Integer.parseInt(proto.getScoreFor21()));
        info.setRankFor21(Integer.parseInt(proto.getRankFor21()));
        info.setStudentNum20Plan(Integer.parseInt(proto.getStudentNum20Plan()));
        info.setStudentNum20Admit(Integer.parseInt(proto.getStudentNum20Admit()));
        info.setScoreFor20(Integer.parseInt(proto.getScoreFor20()));
        info.setRankFor20(Integer.parseInt(proto.getRankFor20()));
        info.setMajorCode(proto.getMajorCode());
        info.setMajorName(proto.getMajorName());
        info.setMajorComments(proto.getMajorComments());
        info.setMajorOtherInfo(proto.getMajorOtherInfo());
        info.setRequireLang(proto.getRequireLang());
        info.setRequireInterview(proto.getRequireInterview());
        info.setMajorDuration(proto.getMajorDuration());
        info.setMajorTuition(proto.getMajorTuition());
        info.setMajor22Plan(Integer.parseInt(proto.getMajor22Plan()));
        info.setMajor22Admin(Integer.parseInt(proto.getMajor22Admin()));
        info.setMajor22LowScore(Integer.parseInt(proto.getMajor22LowScore()));
        info.setMajor22LowRank(Integer.parseInt(proto.getMajor22LowRank()));
        info.setMajor21Plan(Integer.parseInt(proto.getMajor21Plan()));
        info.setMajor21Admin(Integer.parseInt(proto.getMajor21Admin()));
        info.setMajor21LowScore(Integer.parseInt(proto.getMajor21LowScore()));
        info.setMajor21LowRank(Integer.parseInt(proto.getMajor21LowRank()));
        info.setMajor20Plan(Integer.parseInt(proto.getMajor20Plan()));
        info.setMajor20Admin(Integer.parseInt(proto.getMajor20Admin()));
        info.setMajor20LowScore(Integer.parseInt(proto.getMajor20LowScore()));
        info.setMajor20LowRank(Integer.parseInt(proto.getMajor20LowRank()));
        info.setMajorClsIds(StringParser.MakeLongString(proto.getMajorClsIdsList(), ConstDefine.ItemSperator7));
        info.setMajorIcon(StringParser.MakeString(proto.getMajorIconList(), ConstDefine.ItemSperator7));



        info.setMajor20HScore(Integer.parseInt(proto.getMajor20HScore()));
        info.setMajor20HRank(Integer.parseInt(proto.getMajor20HRank()));
        info.setMajor20AvgScore(Integer.parseInt(proto.getMajor20AvgScore()));
        info.setMajor20AvgRank(Integer.parseInt(proto.getMajor20AvgRank()));


        info.setMajor21HScore(Integer.parseInt(proto.getMajor21HScore()));
        info.setMajor21HRank(Integer.parseInt(proto.getMajor21HRank()));
        info.setMajor21AvgScore(Integer.parseInt(proto.getMajor21AvgScore()));
        info.setMajor21AvgRank(Integer.parseInt(proto.getMajor21AvgRank()));

        info.setMajor22HScore(Integer.parseInt(proto.getMajor22HScore()));
        info.setMajor22HRank(Integer.parseInt(proto.getMajor22HRank()));
        info.setMajor22AvgScore(Integer.parseInt(proto.getMajor22AvgScore()));
        info.setMajor22AvgRank(Integer.parseInt(proto.getMajor22AvgRank()));

        info.setStudentNum23Plan(Integer.parseInt(proto.getStudentNum23Plan()));
        info.setMajor23Plan(Integer.parseInt(proto.getMajor23Plan()));

        info.setLimitationInfos(StringParser.MakeString(proto.getLimitationInfosList(), ConstDefine.ItemSperator6));//(proto.getLimitationInfos());
        info.setEnglishScore(proto.getEnglishScore());
        info.setMathScore(proto.getMathScore());
        info.setExamYear(proto.getExamYear());

        info.setScoreFor22Nv(Integer.parseInt(proto.getScoreFor22Nv()));
        info.setRankFor22Nv(Integer.parseInt(proto.getRankFor22Nv()));
        info.setMajor22LowScoreNv(Integer.parseInt(proto.getMajor22LowScoreNv()));
        info.setMajor22LowRankNv(Integer.parseInt(proto.getMajor22LowRankNv()));
        info.setHasGenderScore(proto.getHasGenderScore());

        info.setIsZhongWai(proto.getIsZhongWai());
        info.setIsBenSuo(proto.getIsBenSuo());

        info.setBaoYanLv(proto.getBaoYanLv());
        info.setShengXueLv(proto.getShengXueLv());

        info.setMajorGroupClsIds(StringParser.MakeLongString(proto.getMajorGroupClsIdsList(), ConstDefine.ItemSperator7));
        info.setMajorGroupMajorName(StringParser.MakeString(proto.getMajorGroupMajorNameList(), ConstDefine.ItemSperator7));
        info.setMajorNameDetails(StringParser.MakeString(proto.getMajorNameDetailsList(), ConstDefine.ItemSperator7));
        return info;
    }
}

