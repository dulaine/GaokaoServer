package com.baizhou.core.model.vo;

import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.gameutil.StringParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Service层参数和返回值使用VO
 */
@Data
@Scope("prototype")
@Component
@Getter
@Setter
public class UniGroupInfoZhuanKeLatestVO {
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
     * 志愿年份
     * Len20
     */
    private String examYear = "";
    /**
     * 22组位次
     */
    private Integer rankFor22;
    /**
     * 专业名称
     * Len1000
     */
    private String majorName = "";
    /**
     * 22年低位次
     */
    private Integer major22LowRank;
    /**
     * 22组分数
     */
    private Integer scoreFor22;
    /**
     * 22年低
     */
    private Integer major22LowScore;
    /**
     * 科目要求
     * Len100
     */
    private String subjectRequirement = "";
    /**
     * 数据版本
     */
    private Integer dataVersion;

    /*
专业类表 ids [id,id,id]
* */
    private String majorClsIds;

    /*
    专业类表 名字 [id,id,id]
    * */
    private String majorNames;

//    public com.msg.UniGroupInfoZhuanKeLatest ConvertToDTO(){
//    com.msg.UniGroupInfoZhuanKeLatest.Builder info = com.msg.UniGroupInfoZhuanKeLatest.newBuilder();
//    info.setId(id == null ? 0 : id);
//    info.setUniMajorCode(uniMajorCode);
//    info.setPici(pici == null ? 0 : pici);
//    info.setSchoolName(schoolName);
//    info.setSchoolLvl1(schoolLvl1 == null ? 0 : schoolLvl1);
//    info.setSchoolLvl2(schoolLvl2 == null ? 0 : schoolLvl2);
//    info.setSchoolLvl3(schoolLvl3 == null ? 0 : schoolLvl3);
//    info.setSchoolType(schoolType);
//    info.setProvince(province);
//    info.setExamYear(examYear);
//    info.setRankFor22(rankFor22 == null ? 0 : rankFor22);
//    info.setMajorName(majorName);
//    info.setMajor22LowRank(major22LowRank == null ? 0 : major22LowRank);
//    info.setScoreFor22(scoreFor22 == null ? 0 : scoreFor22);
//    info.setMajor22LowScore(major22LowScore == null ? 0 : major22LowScore);
//    info.setSubjectRequirement(subjectRequirement);
//
//    return info.build();
//   }

    public  UniGroupInfoZhuanKeLatestVO  ConvertToDTO(){
        return  this;
    }

    public static UniGroupInfoZhuanKeLatestVO ConvertFromDTO(com.msg.UniMajorInfo proto) {
        UniGroupInfoZhuanKeLatestVO info = new UniGroupInfoZhuanKeLatestVO();
        info.setId(proto.getId());
        info.setUniMajorCode(proto.getUniMajorCode());
        info.setPici(proto.getPici());
        info.setSchoolName(proto.getSchoolName());
        info.setSchoolLvl1(proto.getSchoolLvl1());
        info.setSchoolLvl2(proto.getSchoolLvl2());
        info.setSchoolLvl3(proto.getSchoolLvl3());
        info.setSchoolType(proto.getSchoolType());
        info.setProvince(proto.getProvince());

//        info.setCity(proto.getCity());
//        info.setBelongDep(proto.getBelongDep());
//        info.setAdmissionRule(proto.getAdmissionRule());
//        info.setSchoolRank(proto.getSchoolRank());
//        info.setStudentNum22Plan(Integer.parseInt(proto.getStudentNum22Plan()));

        info.setSubjectRequirement(proto.getSubjectRequirement());
//        info.setStudentNum22Admit(Integer.parseInt(proto.getStudentNum22Admit()));
        info.setScoreFor22(Integer.parseInt(proto.getScoreFor22()));
        info.setRankFor22(Integer.parseInt(proto.getRankFor22()));
//        info.setStudentNum21Plan(Integer.parseInt(proto.getStudentNum21Plan()));
//        info.setStudentNum21Admit(Integer.parseInt(proto.getStudentNum21Admit()));
//        info.setScoreFor21(Integer.parseInt(proto.getScoreFor21()));
//        info.setRankFor21(Integer.parseInt(proto.getRankFor21()));
//        info.setStudentNum20Plan(Integer.parseInt(proto.getStudentNum20Plan()));
//        info.setStudentNum20Admit(Integer.parseInt(proto.getStudentNum20Admit()));
//        info.setScoreFor20(Integer.parseInt(proto.getScoreFor20()));
//        info.setRankFor20(Integer.parseInt(proto.getRankFor20()));
//        info.setMajorCode(proto.getMajorCode());
        info.setMajorName(proto.getMajorName());
//        info.setMajorComments(proto.getMajorComments());
//        info.setMajorOtherInfo(proto.getMajorOtherInfo());
//        info.setRequireLang(proto.getRequireLang());
//        info.setRequireInterview(proto.getRequireInterview());
//        info.setMajorDuration(proto.getMajorDuration());
//        info.setMajorTuition(proto.getMajorTuition());
//        info.setMajor22Plan(Integer.parseInt(proto.getMajor22Plan()));
//        info.setMajor22Admin(Integer.parseInt(proto.getMajor22Admin()));
        info.setMajor22LowScore(Integer.parseInt(proto.getMajor22LowScore()));
        info.setMajor22LowRank(Integer.parseInt(proto.getMajor22LowRank()));

        info.setExamYear(proto.getExamYear());

        info.setMajorClsIds(StringParser.MakeLongString(proto.getMajorClsIdsList(), ConstDefine.ItemSperator7));
        info.setMajorNames(StringParser.MakeString(proto.getMajorNameDetailsList(), ConstDefine.ItemSperator7));
//        info.setMajor21Plan(Integer.parseInt(proto.getMajor21Plan()));
//        info.setMajor21Admin(Integer.parseInt(proto.getMajor21Admin()));
//        info.setMajor21LowScore(Integer.parseInt(proto.getMajor21LowScore()));
//        info.setMajor21LowRank(Integer.parseInt(proto.getMajor21LowRank()));
//        info.setMajor20Plan(Integer.parseInt(proto.getMajor20Plan()));
//        info.setMajor20Admin(Integer.parseInt(proto.getMajor20Admin()));
//        info.setMajor20LowScore(Integer.parseInt(proto.getMajor20LowScore()));
//        info.setMajor20LowRank(Integer.parseInt(proto.getMajor20LowRank()));
//        info.setMajorClsIds(StringParser.MakeLongString(proto.getMajorClsIdsList(), ConstDefine.ItemSperator7));
//        info.setMajorIcon(StringParser.MakeString(proto.getMajorIconList(), ConstDefine.ItemSperator7));
//        info.setMajor20HScore(Integer.parseInt(proto.getMajor20HScore()));
//        info.setMajor20HRank(Integer.parseInt(proto.getMajor20HRank()));
//        info.setMajor20AvgScore(Integer.parseInt(proto.getMajor20AvgScore()));
//        info.setMajor20AvgRank(Integer.parseInt(proto.getMajor20AvgRank()));
//
//
//        info.setMajor21HScore(Integer.parseInt(proto.getMajor21HScore()));
//        info.setMajor21HRank(Integer.parseInt(proto.getMajor21HRank()));
//        info.setMajor21AvgScore(Integer.parseInt(proto.getMajor21AvgScore()));
//        info.setMajor21AvgRank(Integer.parseInt(proto.getMajor21AvgRank()));
//
//        info.setMajor22HScore(Integer.parseInt(proto.getMajor22HScore()));
//        info.setMajor22HRank(Integer.parseInt(proto.getMajor22HRank()));
//        info.setMajor22AvgScore(Integer.parseInt(proto.getMajor22AvgScore()));
//        info.setMajor22AvgRank(Integer.parseInt(proto.getMajor22AvgRank()));
//
//        info.setStudentNum23Plan(Integer.parseInt(proto.getStudentNum23Plan()));
//        info.setMajor23Plan(Integer.parseInt(proto.getMajor23Plan()));
//        info.setLimitationInfos(StringParser.MakeString(proto.getLimitationInfosList(), ConstDefine.ItemSperator6));//
//        info.setEnglishScore(proto.getEnglishScore());
//        info.setMathScore(proto.getMathScore());
        return info;
    }
}
