package com.baizhou.core.model.vo;

import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumGroupState;
import com.baizhou.data.enumdefine.EnumLockStatus;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.*;
import com.baizhou.util.GameUtil;
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
public class FormInfoVO {
    /**
     * 志愿表唯一id
     */
    private Long id;
    /**
     * 关联的客单id
     */
    private Long orderId;
    /*
     * 创建时间
     * */
    private Date creationDate;

    /*
     * 最后编辑时间
     * */
    private Date lastUpdateDate;

    /**
     * 志愿表名
     * Len100
     */
    private String formName = "";
    /**
     * 科目
     * Len100
     */
    private String examSubject = "";
    /**
     * 位次
     * Len100
     */
    private String rank = "";
    /**
     * 分数
     * Len100
     */
    private String examScore = "";
    /**
     * 创建人
     * Len100
     */
    private String creator = "";
    /**
     * 志愿详情 maj1_HistDBID,maj2__HistDBID|maj1__HistDBID,maj2__HistDBID
     * Len10000
     */
    private String formDetail = "";
    /**
     * 已经选择的majorids =  id,id,id
     * Len1000
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

    /*
     * 状态: 0:未锁定  1:锁定
     * */
    private Integer status;

    /*
    最近一次教师的修改
    * */
    private String formDetailTeacher;

    /*
被用户删除的
* */
    private String delByUser;

    /*
     * 状态: 0:最近教师更新  1:最近用户更新
     * */
    private Integer lastOpType;

    /*
     * 锁定form的用户id
     * */
    private Long lockerId;

    /*
志愿年份
* */
    private String examYear;
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

    public com.msg.FormInfo ConvertToDTO() {
        com.msg.FormInfo.Builder info = com.msg.FormInfo.newBuilder();
        info.setId(id == null ? 0 : id);
//    info.setOrderId(orderId == null ? 0 : orderId);
        info.setLastUpdateDate(lastUpdateDate.getTime());
        info.setFormName(formName);
//        info.setExamSubject(examSubject);
        info.addAllExamSubject(StringParser.SplitString(examSubject, ConstDefine.ItemSperator7));
        info.setRank(rank);
        info.setExamScore(examScore);
        info.setCreator(creator);
//    info.setFormDetail(formDetail);
//    info.setSelectedMajorId(selectedMajorId);
        info.setPici(pici == null ? 0 : pici);
        info.setIsDelete(isDelete);
        info.setStatus(status == null ? EnumLockStatus.Unlock.getStateID() : status);
        //
//        System.out.println(" add FormDetailInfo detail");
        if(lockerId == null){
           OrderInfoVO orderInfoVO =  OrderManager.GetInstance().GetOrderById(orderId);
           lockerId = orderInfoVO.getCreatorId();
        }else {

        }
        info.setLockerId(lockerId);

        UsersResVO usersResVO = PlayerManager.GetInstance().GetPlayerById(lockerId);
        info.setLockerRole(usersResVO.getRole());

        info.setExamYear(examYear == null ? "2023" : examYear);
        return info.build();
    }

    public com.msg.FormDetailInfo ConvToFormDetail(boolean forClient) {
        com.msg.FormDetailInfo.Builder builder = com.msg.FormDetailInfo.newBuilder();
        if (this.formDetailTeacher == null) {
            this.formDetailTeacher = formDetail;
        }
        List<com.msg.UniMajorGroupInfo> groupInfos = GameUtil.ConvertUniMajorGroupProtoFromStr(forClient ? this.formDetail : this.formDetailTeacher, this.pici);
//        List<com.msg.UniMajorGroupInfo>  groupInfos = new ArrayList<>();
//
//         List<String> groupStr = StringParser.SplitString(this.formDetail, ConstDefine.ItemSperator3);
//        for (int i = 0; i < groupStr.size(); i++) {
//            String tempStr = groupStr.get(i);
//            List<Long> mjIds = StringParser.SplitLongNumber(tempStr, ConstDefine.ItemSperator7);
//
//            //查询专业组内信息
//            List<UniMajorInfo> temp = UniMajorManager.GetInstance().GetInHisDB(mjIds, EnumPici.Get(this.pici) );
//
//
//            List<UniMajorInfo> protos = new ArrayList<>();
//            //按照mjIds排序
//            for (int j = 0; j < mjIds.size(); j++) {
//                Long mjId = mjIds.get(j);
//
//                for (int k = 0; k < temp.size(); k++) {
//                    if(temp.get(k).getId() == mjId){
//                        protos.add(temp.get(k));
//                        temp.remove(k);
//                        break;
//                    }
//                }
//            }
//
//            //生成group proto
//            com.msg.UniMajorGroupInfo.Builder groupBuild = com.msg.UniMajorGroupInfo.newBuilder();
//            groupBuild.addAllMajors(protos);
//
//            groupInfos.add(groupBuild.build());
//        }


        builder.addAllGroups(groupInfos);
        builder.addAllSelectedMajorIds(StringParser.SplitLongNumber(selectedMajorId, ConstDefine.ItemSperator7)); //分解字符串

        return builder.build();
    }

    //返回志愿表给 客户端
    public com.msg.FormDetailInfo ConvToFormDetailForWeb(boolean forClient) {
        com.msg.FormDetailInfo.Builder builder = com.msg.FormDetailInfo.newBuilder();

        List<com.msg.UniMajorGroupInfo> cGroupInfos = GameUtil.ConvertUniMajorGroupProtoFromStr(this.formDetail, this.pici);
        if (this.formDetailTeacher == null) {
            this.formDetailTeacher = formDetail;
        }
        List<com.msg.UniMajorGroupInfo> tGroupInfos = GameUtil.ConvertUniMajorGroupProtoFromStr(this.formDetailTeacher, this.pici);

        List<com.msg.UniMajorGroupInfo> newGroups = null;
        if (forClient) {
            newGroups = GameUtil.MergeTeachAndClientDataClientFirst(tGroupInfos, cGroupInfos);
            //如果是 用户请求，  去除那些 用户删除的数据
            for (int i = newGroups.size() - 1; i >= 0; i--) {
                com.msg.UniMajorGroupInfo groupInfo = newGroups.get(i);
                if (groupInfo.getGroupStates() == EnumGroupState.Del.getStateID()) {
                    newGroups.remove(i);
                }
            }
        } else {
            newGroups = GameUtil.MergeTeachAndClientDataTeacherFirst(tGroupInfos, cGroupInfos, this.lastOpType);
        }


        builder.addAllGroups(newGroups);
        builder.addAllSelectedMajorIds(StringParser.SplitLongNumber(selectedMajorId, ConstDefine.ItemSperator7)); //分解字符串

        return builder.build();
    }


    public static FormInfoVO ConvertFromProto(com.msg.FormDetailInfo formDetailInfo) {

        //专业组信息 state@@@id,id_ state@@@id,id
        String detailString = "";
        for (int i = 0; i < formDetailInfo.getGroupsCount(); i++) {
            com.msg.UniMajorGroupInfo groupInfo = formDetailInfo.getGroups(i);

            if (i > 0) detailString += ConstDefine.ItemSperator3;

            List<UniMajorInfo> majorInfoList = groupInfo.getMajorsList();
            for (int j = 0; j < majorInfoList.size(); j++) {
                UniMajorInfo majorInfo = majorInfoList.get(j);

                if (j > 0) detailString += ConstDefine.ItemSperator7;
                detailString += majorInfo.getId();

            }

        }

        String seleMajorIds = StringParser.MakeLongString(formDetailInfo.getSelectedMajorIdsList(), ConstDefine.ItemSperator7);//组成字符串


        FormInfoVO formInfoVO = new FormInfoVO();
        formInfoVO.setSelectedMajorId(seleMajorIds);
        formInfoVO.setFormDetail(detailString);
        formInfoVO.setFormDetailTeacher(detailString);

        return formInfoVO;
//        formInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());
//        formInfoVO.setFormName(proto.getFormName());
////        formInfoVO.setExamSubject(proto.getExamSubject());
//        formInfoVO.setExamSubject(StringParser.MakeString(proto.getExamSubjectList(), ConstDefine.ItemSperator7));
//
//        formInfoVO.setRank(proto.getRank());
//        formInfoVO.setExamScore(proto.getExamScore());
//        formInfoVO.setCreator();

    }

    public static FormInfoVO ConvertFromProto(com.msg.FormDetailInfo formDetailInfo, com.msg.FormDetailInfo formDetailInfoServer, boolean isClient) {

        List<com.msg.UniMajorGroupInfo> ConvertDetailFromProto = GameUtil.GetGroupInfoFromWebProto(formDetailInfo, formDetailInfoServer, isClient);


        //专业组信息 state@@@id,id_ state@@@id,id
        String detailString =  GameUtil.ConvToDBStr(ConvertDetailFromProto);// "";
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


        FormInfoVO formInfoVO = new FormInfoVO();
        formInfoVO.setSelectedMajorId(seleMajorIds);
        formInfoVO.setFormDetail(detailString);
        formInfoVO.setFormDetailTeacher(detailString);

        return formInfoVO;

    }

    public com.msg.FormDetailInfo ConvToFormDetailForWeb2(boolean forClient) {
        com.msg.FormDetailInfo.Builder builder = com.msg.FormDetailInfo.newBuilder();

        boolean needUpdate = GameUtil.FormDetailNeedUpdate(this.dataVersion, this.examYear, this.pici);
        HashMap<Long, Long> majorIdDic = new HashMap<>();
        List<com.msg.UniMajorGroupInfo> cGroupInfos = GameUtil.ConvertUniMajorGroupProtoFromDBStr(this.formDetail, this.pici,majorIdDic, this.dataVersion, this.examYear,needUpdate);
//        List<com.msg.UniMajorGroupInfo>  tGroupInfos = GameUtil.ConvertUniMajorGroupProtoFromStr(this.formDetailTeacher, this.pici);
        List<Long> newSelMajorIds = new ArrayList<>();
        List<Long> oldSelMajorIds = StringParser.SplitLongNumber(selectedMajorId, ConstDefine.ItemSperator7);
        for (int i = 0; i < oldSelMajorIds.size(); i++) {
            Long temp = majorIdDic.getOrDefault(oldSelMajorIds.get(i), null);
            if(temp!=null){
                newSelMajorIds.add(temp);
            }else {
                System.out.println("form id " + this.id + ", 其中 oldSelMajorIds " + oldSelMajorIds.get(i) + " 不存在");
            }

        }

        List<com.msg.UniMajorGroupInfo> newGroups = cGroupInfos;
        if (forClient) {
            for (int i = newGroups.size() - 1; i >= 0; i--) {
                com.msg.UniMajorGroupInfo groupInfo = newGroups.get(i);
                if (groupInfo.getGroupStates() == EnumGroupState.Del.getStateID()) {
                    newGroups.remove(i);
                }
            }
        } else {
//            newGroups =  GameUtil.MergeTeachAndClientDataTeacherFirst(tGroupInfos, cGroupInfos, this.lastOpType);
            newGroups = cGroupInfos;
        }

        builder.addAllGroups(newGroups);
        builder.addAllSelectedMajorIds(newSelMajorIds); //分解字符串

        com.msg.FormDetailInfo ret = builder.build();
        //保存新数据
        if(needUpdate){
            LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion(EnumPici.Get(pici), examYear);
            FormInfoManager.GetInstance().UpdateDataVerion(this.id, ret, latestVerInfoVO != null ? latestVerInfoVO.getDataVersion() : dataVersion);
        }

        return ret;
    }


}
