package com.baizhou.core.model.vo;

import com.baizhou.common.CommonUtil;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumGroupState;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.TemplateManager;
import com.baizhou.manager.UniMajorMgrInMem;
import com.baizhou.util.GameUtil;
import com.msg.Major1stClsInfo;
import com.msg.UniMajorGroupInfo;
import com.msg.UniMajorInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
public class TemplateInfoVO {
    /**
     * 志愿表唯一id
     */
    private Long id;
    /*
     * 创建时间
     * */
    private Date creationDate;

    /*
     * 最后编辑时间
     * */
    private Date lastUpdateDate;

    /**
     * 模板名 不能重名
     * Len100
     */
    private String templateName = "";
    /**
     * 创建人
     * Len100
     */
    private String creatorName = "";
    /**
     * 创建老师Id
     */
    private Long creatorId;
    /**
     * 授权可查看的老师Ids [(id),(id),(id)]
     * Len1000
     */
    private String authorTeacherIds = "";
    /**
     * 1: 公开; 0: 没有公开
     */
    private Integer isPublic;
    /**
     * 志愿年份
     * Len20
     */
    private String examYear = "";
    /**
     * 志愿详情 maj1_HistDBID,maj2__HistDBID|maj1__HistDBID,maj2__HistDBID
     * Len10000
     */
    private String formDetail = "";
    /**
     * 已经选择的majorids =  id,id,id
     * Len10000
     */
    private String selectedMajorId = "";
    /**
     * 批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     */
    private Integer pici;
    /**
     * 1: 已经删除; 0: 没有删除
     */
    private Integer isDelete;
    /**
     * 数据版本
     */
    private Integer dataVersion;
    /**
     * 最早版本的 志愿详情 maj1_HistDBID,maj2__HistDBID|maj1__HistDBID,maj2__HistDBID
     * Len50000
     */
    private String originFormDetail = "";
    /**
     * 最早版本的 已经选择的majorids =  id,id,id
     * Len50000
     */
    private String originSelectedMajorId = "";
    /**
     * 相关的的专业list [1类专业_2类专业%专业id, 专业名@专业id, 专业名:2类专业%专业id, 专业名@专业id, 专业名 |  1类专业_2类专业%专业id, 专业名 ]
     * Len10000
     */
    private String relativeMajorList = "";

    public com.msg.TemplateInfo ConvertToDTO(){
    com.msg.TemplateInfo.Builder info = com.msg.TemplateInfo.newBuilder();
    info.setId(id == null ? 0 : id);
    info.setLastUpdateDate(lastUpdateDate.getTime());
    info.setTemplateName(templateName);
    info.setCreatorName(creatorName);


        List<Long> templist2 = GameUtil.ConvAuthTeachID(authorTeacherIds, ConstDefine.ItemSperator7);
        info.addAllAuthorTeacherIds(templist2);

//    info.setAuthorTeacherIds(authorTeacherIds);
    info.setIsPublic(isPublic == null ? 0 : isPublic);
    info.setExamYear(examYear);
//    info.setFormDetail(formDetail);
//    info.setSelectedMajorId(selectedMajorId);
    info.setPici(pici == null ? 0 : pici);
    info.setIsDelete(isDelete == null ? 0 : isDelete);
    info.setCreatorId(creatorId == null ? 0 : creatorId);

    //添加相关专业
        List<Major1stClsInfo> favMajor1stClsInfos = CommonUtil.ParsMajorItemToProto(relativeMajorList);
        info.addAllFavoredMajorCls1List(favMajor1stClsInfos);

    return info.build();
   }

    public com.msg.TemplateDetailInfo ConvToDetail() {
        com.msg.TemplateDetailInfo.Builder builder = com.msg.TemplateDetailInfo.newBuilder();

        boolean needUpdate = GameUtil.FormDetailNeedUpdate(this.dataVersion, this.examYear, this.pici);
        HashMap<Long, Long> majorIdDic = new HashMap<>();
        List<com.msg.UniMajorGroupInfo> cGroupInfos = GameUtil.ConvertUniMajorGroupProtoFromDBStr(this.formDetail, this.pici,majorIdDic, this.dataVersion, this.examYear,needUpdate);
//        List<com.msg.UniMajorGroupInfo>  tGroupInfos = GameUtil.ConvertUniMajorGroupProtoFromStr(this.formDetailTeacher, this.pici);
        List<Long> newSelMajorIds = new ArrayList<>();
        List<Long> oldSelMajorIds = StringParser.SplitLongNumber(selectedMajorId, ConstDefine.ItemSperator7);
        for (int i = 0; i < oldSelMajorIds.size(); i++) {
            Long temp = majorIdDic.getOrDefault(oldSelMajorIds.get(i), null);
            if(temp != null) newSelMajorIds.add(temp);
        }

        List<com.msg.UniMajorGroupInfo> newGroups = cGroupInfos;
//        if (forClient) {
//            for (int i = newGroups.size() - 1; i >= 0; i--) {
//                com.msg.UniMajorGroupInfo groupInfo = newGroups.get(i);
//                if (groupInfo.getGroupStates() == EnumGroupState.Del.getStateID()) {
//                    newGroups.remove(i);
//                }
//            }
//        } else {

            newGroups = cGroupInfos;
//        }

        builder.addAllGroups(newGroups);
        builder.addAllSelectedMajorIds(newSelMajorIds); //分解字符串

        com.msg.TemplateDetailInfo ret = builder.build();
        if(needUpdate){
            LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion(EnumPici.Get(pici), examYear);
            TemplateManager.GetInstance().UpdateDataVerion(this.id, ret, latestVerInfoVO != null ? latestVerInfoVO.getDataVersion() : dataVersion);
        }
        return ret;
    }


    public static TemplateInfoVO ConvertFromProto(com.msg.TemplateDetailInfo formDetailInfo) {

        List<com.msg.UniMajorGroupInfo> ConvertDetailFromProto = GameUtil.GetGroupInfoFromWebTemplateProto(formDetailInfo);


        //专业组信息 state@@@id,id_ state@@@id,id
        String detailString = GameUtil.ConvToDBStr(ConvertDetailFromProto);//"";
//        for (int i = 0; i < ConvertDetailFromProto.size(); i++) {
//            com.msg.UniMajorGroupInfo groupInfo = ConvertDetailFromProto.get(i);
//            List<UniMajorInfo> majorInfoList = groupInfo.getMajorsList();
//            if (majorInfoList.size() == 0) continue;
//
//            if (i > 0) detailString += ConstDefine.ItemSperator3;
//
//            detailString += groupInfo.getGroupStates();
//            detailString += ConstDefine.ItemSperator5;
//
//            for (int j = 0; j < majorInfoList.size(); j++) {
//                UniMajorInfo majorInfo = majorInfoList.get(j);
//                if (j > 0) detailString += ConstDefine.ItemSperator7;
//                detailString += majorInfo.getId();
//            }
//        }


        String seleMajorIds = StringParser.MakeLongString(formDetailInfo.getSelectedMajorIdsList(), ConstDefine.ItemSperator7);//组成字符串


        TemplateInfoVO formInfoVO = new TemplateInfoVO();
        formInfoVO.setSelectedMajorId(seleMajorIds);
        formInfoVO.setFormDetail(detailString);
//        formInfoVO.setFormDetailTeacher(detailString);

        return formInfoVO;

    }


}
