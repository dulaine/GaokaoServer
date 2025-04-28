package com.baizhou.core.model.vo;

import com.baizhou.common.CommonUtil;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumOrderStatus;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PlayerManager;
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
public class OrderInfoVO {
    /**
     * 客单唯一id
     */
    private Long id;
    /**
     * 客户名==和ClientInfo中名对应
     * Len100
     */
    private String name = "";
    /**
     * 客户手机号==和ClientInfo中对应
     * Len20
     */
    private String tel = "";
    /**
     * 状态:
     * 0:待处理（等待创建方案）
     * 1:已处理（等待审核方案）
     * 2:已审核（等待用户确认）
     * 3:已完成（用户已确认
     */
    private Integer status;
    /*
     * 创建时间
     * */
    private Date creationDate;

    /**
     * 创建老师Id
     */
    private Long creatorId;
    /**
     * 创建老师名
     * Len100
     */
    private String creatorName = "";
    /**
     * 分配负责的老师Id
     */
    private Long assignedTeacherId;
    /**
     * 分配负责的老师
     * Len100
     */
    private String assignedTeacher = "";
    /**
     * 授权可查看的老师Ids [id,id,id]
     * Len1000
     */
    private String authorTeacherIds = "";
    /**
     * 省份 ==和ClientInfo中名对应
     * Len100
     */
    private String province = "";
    /**
     * 高考年份==和ClientInfo中名对应
     * Len20
     */
    private String examYear = "";
    /**
     * 喜欢的专业list [用,区分专业id] == 针对每个客单 year_id,id|year_id,id
     * Len10000
     */
    private String favoredMajorGroupList = "";
    /**
     * 不喜欢的专业组id lit == 针对每个客单
     * Len10000
     */
    private String unfavoredGroupMajorList = "";
    /**
     * 中立的专业组id lit == 针对每个客单
     * Len10000
     */
    private String neutralGroupMajorList = "";
    /**
     * 志愿表数量
     */
    private Integer formNum;

    /**
     * 1: 已经删除; 0: 没有删除
     */
    private Integer isDelete;

    /*
     * 是否在界面 选择过体检限报  1: 选择过  0:未选择过
     * */
    private Integer hasSelPhysicLimit;

    public com.msg.OrderInfo ConvertToDTO() {
        com.msg.OrderInfo.Builder info = com.msg.OrderInfo.newBuilder();
        info.setId(id == null ? 0 : id);
        info.setName(name);
        info.setTel(tel);
        info.setStatus(status == null ? 0 : status);
        info.setCreationDate(creationDate.getTime());
        info.setCreatorName(creatorName);
        info.setAssignedTeacher(assignedTeacher);
//        info.setAuthorTeacherIds(authorTeacherIds);

//        List<String> templist = StringParser.SplitString(authorTeacherIds, ConstDefine.ItemSperator7);
//        List<Long> templist2 = new ArrayList<>();
//        for (int i = 0; i < templist.size(); i++) {
//            templist2.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
//        }
        List<Long> templist2 = ConvAuthTeachID(authorTeacherIds, ConstDefine.ItemSperator7);
        info.addAllAuthorTeacherIds(templist2);
//        info.addAllAuthorTeacherIds(StringParser.SplitLongNumber(authorTeacherIds, ConstDefine.ItemSperator7));
        info.setProvince(province);
        info.setExamYear(examYear);
//        info.addAllFavoredMajorGroupList(StringParser.SplitString(favoredMajorGroupList, ConstDefine.ItemSperator7));
//        info.addAllUnfavoredGroupMajorList(StringParser.SplitString(unfavoredGroupMajorList, ConstDefine.ItemSperator7));
//        info.addAllNeutralGroupMajorList(StringParser.SplitString(neutralGroupMajorList, ConstDefine.ItemSperator7));
        info.setFormNum(formNum == null ? 0 : formNum);

        info.setIsDelete(isDelete);

        //
        ClientInfoVO clientInfoVO = OrderManager.GetInstance().GetClientByTel(tel);
        info.setExamScore(clientInfoVO.getExamScore());
        info.setExamRank(clientInfoVO.getExamRank());
//        info.addAllLimitationInfos(ClientInfoVO.ParsLimitationItemToProto(clientInfoVO.getLimitationInfos()) );

        info.setClientInfo(clientInfoVO.ConvertToDTO());
        info.addAllLimitationInfos(info.getClientInfo().getLimitationInfosList());
        info.addAllMajorPref(GameUtil.ConvertDBPrefStringToProto(favoredMajorGroupList,unfavoredGroupMajorList,neutralGroupMajorList));
        return info.build();
    }
    public List<Long> ConvAuthTeachID(String authorTeacherIds, String splitter){
//        List<String> templist = StringParser.SplitString(authorTeacherIds, splitter);
//        List<Long> templist2 = new ArrayList<>();
//        for (int i = 0; i < templist.size(); i++) {
//            templist2.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
//        }
//        return  templist2;
        return GameUtil.ConvAuthTeachID(authorTeacherIds, splitter);
    }

    public OrderInfoVO UpdateFromDTO(com.msg.OrderInfo proto) {
//        com.msg.OrderInfo.Builder info = com.msg.OrderInfo.newBuilder();
//        info.setId(id == null ? 0 : id);
        if (!proto.getName().isEmpty()) this.setName(proto.getName());
        if (!proto.getTel().isEmpty()) this.setTel(proto.getTel());
        this.setStatus(proto.getStatus());
//        this.setCreationDate(creationDate.getTime());
//        this.setCreatorName(creatorName);
        this.setAssignedTeacher(proto.getAssignedTeacher());

//        List<Long> tempIds = proto.getAuthorTeacherIdsList();
////        List<String> tempStrs = new ArrayList<>();
////        for (int i = 0; i < tempIds.size(); i++) {
////            tempStrs.add(CommonUtil.AddBracket(tempIds.get(i) + ""));
////        }
////        this.setAuthorTeacherIds(StringParser.MakeString(tempStrs, ConstDefine.ItemSperator7));
//        this.setAuthorTeacherIds(CommonUtil.ConvertIdToString(tempIds));
        this.SetAuthorTeacherIdsFromProto(proto.getAuthorTeacherIdsList());
        if (!proto.getProvince().isEmpty()) this.setProvince(proto.getProvince());
        if (!proto.getExamYear().isEmpty()) this.setExamYear(proto.getExamYear());

//        this.setFormNum(formNum == null ? 0 : formNum);

        return this;
    }

    private void SetAuthorTeacherIdsFromProto(List<Long> tempIds){
        this.setAuthorTeacherIds(CommonUtil.ConvertIdToString(tempIds));
    }

    public OrderInfoVO UpdateFromClientInfo(ClientInfoVO clientInfoVO,com.msg.ClientInfo protoClient) {
////        com.msg.OrderInfo.Builder info = com.msg.OrderInfo.newBuilder();
////        info.setId(id == null ? 0 : id);
//        if (!proto.getName().isEmpty()) this.setName(proto.getName());
//        if (!proto.getTel().isEmpty()) this.setTel(proto.getTel());
//        this.setStatus(proto.getStatus());
////        this.setCreationDate(creationDate.getTime());
////        this.setCreatorName(creatorName);
//        this.setAssignedTeacher(proto.getAssignedTeacher());
//
//        List<Long> tempIds = proto.getAuthorTeacherIdsList();
//        List<String> tempStrs = new ArrayList<>();
//        for (int i = 0; i < tempIds.size(); i++) {
//            tempStrs.add(CommonUtil.AddBracket(tempIds.get(i) + ""));
//        }
//        this.setAuthorTeacherIds(StringParser.MakeString(tempStrs, ConstDefine.ItemSperator7));
//        if (!proto.getProvince().isEmpty()) this.setProvince(proto.getProvince());
//        if (!proto.getExamYear().isEmpty()) this.setExamYear(proto.getExamYear());

//        this.setFormNum(formNum == null ? 0 : formNum);

        this.setName(clientInfoVO.getName());
        this.setProvince(clientInfoVO.getProvince());
        this.setExamYear(clientInfoVO.getExamYear());
        this.SetAuthorTeacherIdsFromProto(protoClient.getAuthorTeacherIdsList());
        this.setHasSelPhysicLimit(clientInfoVO.getHasSelPhysicLimit());
        return this;
    }



    public static OrderInfoVO CreateFrom(ClientInfoVO clientInfoVO, Long teacherId) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();

        orderInfoVO.setName(clientInfoVO.getName());
        orderInfoVO.setTel(clientInfoVO.getTel());
        orderInfoVO.setStatus(EnumOrderStatus.Wait.getStateID());
        orderInfoVO.setCreationDate(new Date());
        orderInfoVO.setCreatorId(teacherId);

        UsersResVO vo = PlayerManager.GetInstance().GetPlayerById(teacherId);
        if (vo == null) {
            System.out.println("没找到用户 " + teacherId);
        }
        orderInfoVO.setCreatorName(vo == null ? "NonUser" : vo.getName());


        orderInfoVO.setAssignedTeacher(vo.getName());
        orderInfoVO.setAssignedTeacherId(teacherId);
        orderInfoVO.setAuthorTeacherIds(CommonUtil.AddBracket(teacherId + ""));

        orderInfoVO.setProvince(clientInfoVO.getProvince());
        orderInfoVO.setExamYear(clientInfoVO.getExamYear());
        orderInfoVO.setFavoredMajorGroupList("");
        orderInfoVO.setUnfavoredGroupMajorList("");
        orderInfoVO.setNeutralGroupMajorList("");
        orderInfoVO.setFormNum(0);
        orderInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());

        orderInfoVO.setHasSelPhysicLimit(clientInfoVO.getHasSelPhysicLimit());

        return orderInfoVO;
    }

}
