package com.baizhou.core.model.vo;

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
public class MajorInfoY3VO {
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
     * 科目要求
     * Len100
     */
    private String subjectRequirement = "";
    /**
     * 22组分数
     */
    private Integer scoreFor22;
    /**
     * 22组位次
     */
    private Integer rankFor22;
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
     * 22年高分
     */
    private Integer major22HScore;
    /**
     * 22年高位次
     */
    private Integer major22HRank;
    /**
     * 22年平分
     */
    private Integer major22AvgScore;
    /**
     * 22年平位次
     */
    private Integer major22AvgRank;

    private String piciName;


    public com.msg.MajorInfoYear ConvertToDTO() {
        com.msg.MajorInfoYear.Builder info = com.msg.MajorInfoYear.newBuilder();
        info.setId(id == null ? 0 : id);
        info.setUniMajorCode(uniMajorCode);
        info.setPici(pici == null ? 0 : pici);
        info.setSchoolName(schoolName);
        info.setSubjectRequirement(subjectRequirement);
        info.setScoreFor22(scoreFor22 == null ? "0" : scoreFor22 + "");
        info.setRankFor22(rankFor22 == null ? "0" : rankFor22 + "");
        info.setMajorCode(majorCode);
        info.setMajorName(majorName);
        info.setMajorComments(majorComments);
        info.setRequireLang(requireLang);
        info.setRequireInterview(requireInterview);
        info.setMajorDuration(majorDuration);
        info.setMajorTuition(majorTuition);
        info.setMajor22Plan(major22Plan == null ? "0" : major22Plan + "");
        info.setMajor22Admin(major22Admin == null ? "0" : major22Admin + "");
        info.setMajor22LowScore(major22LowScore == null ? "0" : major22LowScore + "");
        info.setMajor22LowRank(major22LowRank == null ? "0" : major22LowRank + "");
        info.setMajor22HScore(major22HScore == null ? "0" : major22HScore + "");
        info.setMajor22HRank(major22HRank == null ? "0" : major22HRank + "");
        info.setMajor22AvgScore(major22AvgScore == null ? "0" : major22AvgScore + "");
        info.setMajor22AvgRank(major22AvgRank == null ? "0" : major22AvgRank + "");
        info.setPiciName(piciName);

        return info.build();
    }


    public static MajorInfoY3VO ConvertFromDTO(com.msg.MajorInfoYear proto) {
        MajorInfoY3VO info = new MajorInfoY3VO();
        info.setId(proto.getId());
        info.setUniMajorCode(proto.getUniMajorCode());
        info.setPici(proto.getPici());
        info.setSchoolName(proto.getSchoolName());
        info.setSubjectRequirement(proto.getSubjectRequirement());

        info.setScoreFor22(Integer.parseInt(proto.getScoreFor22()));
        info.setRankFor22(Integer.parseInt(proto.getRankFor22()));
        info.setMajorCode(proto.getMajorCode());
        info.setMajorName(proto.getMajorName());
        info.setMajorComments(proto.getMajorComments());

        info.setRequireLang(proto.getRequireLang());
        info.setRequireInterview(proto.getRequireInterview());
        info.setMajorDuration(proto.getMajorDuration());
        info.setMajorTuition(proto.getMajorTuition());
        info.setMajor22Plan(Integer.parseInt(proto.getMajor22Plan()));
        info.setMajor22Admin(Integer.parseInt(proto.getMajor22Admin()));
        info.setMajor22LowScore(Integer.parseInt(proto.getMajor22LowScore()));
        info.setMajor22LowRank(Integer.parseInt(proto.getMajor22LowRank()));

        info.setMajor22HScore(Integer.parseInt(proto.getMajor22HScore()));
        info.setMajor22HRank(Integer.parseInt(proto.getMajor22HRank()));
        info.setMajor22AvgScore(Integer.parseInt(proto.getMajor22AvgScore()));
        info.setMajor22AvgRank(Integer.parseInt(proto.getMajor22AvgRank()));
        info.setPiciName(proto.getPiciName());

        return info;
    }
}
