package com.baizhou.manager;

import com.baizhou.core.model.entity.FormInfo;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.enumdefine.*;
import com.baizhou.db.DBProxy;
import com.baizhou.util.GameUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FormInfoManager {
    private static FormInfoManager instance;

    public static FormInfoManager GetInstance() {
        if (instance == null) {
            instance = new FormInfoManager();
        }
        return instance;
    }

    //根据 查询  志愿填报
    public Map<String, Object> GetFormByPage(Long teacherId, Long orderId, int page, int limit, Integer pici, EnumDeleteStatus deleteStatus) {
//        UsersResVO teacher = DBProxy.Getinstance().UsersResService.findOneById(teacherId);
//        Pageable pageable = PageRequest.of(page - 1, limit);
//        UsersResVO teacher = DBProxy.Getinstance().UsersResService.findOneById(teacherId);
//        boolean isSuperAdmin = false;// GameUtil.IsSuperAdim(teacher);  //志愿不显示已经删除的志愿

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "creationDate")); //排序
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));
//        Map<String, Object> map = DBProxy.Getinstance().FormInfoService.listbyPage(orderId, pici, isSuperAdmin ? null : EnumDeleteStatus.UnDeleted.getStateID(), null, pageable);
        Map<String, Object> map = DBProxy.Getinstance().FormInfoService.listbyPage(orderId, pici, deleteStatus == null ? null : deleteStatus.getStateID(), null, pageable);
        return map;
    }


    public FormInfoVO CreateForm(Long teacherId, Long orderId, String formName, Integer pici, com.msg.FormDetailInfo detailInfo, Long lockedFormId, String examYear) {

        //获取原来form信息
        FormInfoVO lockedFormInfoVO = null;
        if (lockedFormId != null && lockedFormId > 0) {
            lockedFormInfoVO = GetFormInfo(lockedFormId);
            if (lockedFormInfoVO != null) {
                orderId = lockedFormInfoVO.getOrderId();
                pici = lockedFormInfoVO.getPici();
            }
        }


//        FormInfoVO formInfoVO = FormInfoVO.class
        UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(teacherId);
        boolean isClientUpdate = teacher.getRole() == EnumRoleType.Client.getStateID();

        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);
        ClientInfoVO clientInfoVO = OrderManager.GetInstance().GetClientByTel(orderInfoVO.getTel());


        //获取专业组信息
//        FormInfoVO formInfoVO = FormInfoVO.ConvertFromProto(detailInfo, null, isClientUpdate);

        com.msg.FormDetailInfo detailInfoOldWeb = lockedFormInfoVO == null ? null : lockedFormInfoVO.ConvToFormDetailForWeb2(false);//  formInfoVO.ConvToFormDetailForWeb(isClientUpdate);
        FormInfoVO formInfoVO = FormInfoVO.ConvertFromProto(detailInfo, detailInfoOldWeb, isClientUpdate);

//        if(lockedFormInfoVO == null){
//            if(isClientUpdate){
////客户端更新
//                formInfoVO.setFormDetailTeacher("");
//            }else {
////教师更新
//                formInfoVO.setFormDetailTeacher(formInfoVO.getFormDetail()); //
//                formInfoVO.setFormDetail("");
//            }
//        }else {
//            //锁定的form 重新生成
//            if(isClientUpdate){
////客户端更新
//                formInfoVO.setFormDetailTeacher(lockedFormInfoVO.getFormDetailTeacher());
//            }else {
////教师更新
//                formInfoVO.setFormDetailTeacher(formInfoVO.getFormDetail()); //
//                formInfoVO.setFormDetail(lockedFormInfoVO.getFormDetail());
//            }
//        }


        //表单信息

        formInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());
        formInfoVO.setFormName(formName);
//        formInfoVO.setExamSubject(proto.getExamSubject());
        formInfoVO.setExamSubject(clientInfoVO.getExamSubject());

        formInfoVO.setRank(clientInfoVO.getExamEstRankMin());
        formInfoVO.setExamScore(clientInfoVO.getExamEstScoreMin());
        formInfoVO.setCreator(teacher == null ? "" : teacher.getName());
        formInfoVO.setPici(pici);
        formInfoVO.setOrderId(orderId);

        formInfoVO.setCreationDate(new Date());
        formInfoVO.setLastUpdateDate(new Date());

        formInfoVO.setStatus(EnumLockStatus.Lock.getStateID());
        formInfoVO.setLastOpType(isClientUpdate ? EnumFormOpType.ByUser.getStateID() : EnumFormOpType.ByTeacher.getStateID());

        formInfoVO.setExamYear(examYear);

//        formInfoVO = LockForm(formInfoVO.getId(), teacher.getId(), EnumLockStatus.Lock.getStateID());
        formInfoVO = SetLockState(formInfoVO, teacherId, EnumLockStatus.Lock.getStateID());

        //保存版本信息
        LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion(EnumPici.Get(pici), examYear);
        if(latestVerInfoVO != null) formInfoVO.setDataVersion(latestVerInfoVO.getDataVersion());
        formInfoVO.setOriginSelectedMajorId(formInfoVO.getSelectedMajorId());
        formInfoVO.setOriginFormDetail(formInfoVO.getFormDetail());

        FormInfo formInfoEntity = DBProxy.Getinstance().FormInfoService.saveFormInfo(formInfoVO);
        formInfoVO.setId(formInfoEntity.getId());



        //更新 Order 对应的的志愿数量
        OrderManager.GetInstance().UpdateOrderFormNum(orderInfoVO.getId(), 1);


        return formInfoVO;
//        //创建client 信息, 保存db
//        ClientInfoVO clientInfoVO = ClientInfoVO.ConvertFromDTO(clientInfo);
//        com.baizhou.core.model.entity.ClientInfo clientInfo1 = DBProxy.Getinstance().ClientInfoService.saveClientInfo(clientInfoVO);
//
//        //创建order 信息, 保存db
//        OrderInfoVO orderInfoVO = OrderInfoVO.CreateFrom(clientInfoVO, teacherId);
//        OrderInfo orderInfo = DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);
//        orderInfoVO.setId(orderInfo.getId());


    }

    public void UpdateForm(Long formId, String formName, com.msg.FormDetailInfo detailInfo, Long teacherId, String examYear) {

        UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(teacherId);
        boolean isClientUpdate = teacher.getRole() == EnumRoleType.Client.getStateID();

        FormInfoVO formInfoVO = GetFormInfo(formId);// DBProxy.Getinstance().FormInfoService.findOneById(formId);
        if (formInfoVO.getStatus() == null) formInfoVO.setStatus(EnumLockStatus.Unlock.getStateID());
        //如果当前form 是锁定状态 & 客户更新  == 创建信息订单
        if (formInfoVO.getStatus() == EnumLockStatus.Lock.getStateID() && isClientUpdate) {
            CreateForm(teacherId, formInfoVO.getOrderId(), formName, formInfoVO.getPici(), detailInfo, formId,examYear);
            return;
        }


        formInfoVO.setFormName(formName);


        //检测专业是否有更新
        com.msg.FormDetailInfo detailInfoOldWeb = formInfoVO.ConvToFormDetailForWeb2(false);//  formInfoVO.ConvToFormDetailForWeb(isClientUpdate);
        FormInfoVO tempFormInfo = FormInfoVO.ConvertFromProto(detailInfo, detailInfoOldWeb, isClientUpdate);
        formInfoVO.setFormDetail(tempFormInfo.getFormDetail()); //
        formInfoVO.setSelectedMajorId(tempFormInfo.getSelectedMajorId());

        formInfoVO.setStatus(EnumLockStatus.Lock.getStateID()); //默认都 lock
//            boolean hasUpdateMajor = GameUtil.CheckIfFormSeqSame(detailInfo, detailInfoOldWeb);
//        if(hasUpdateMajor){
//            //客户更新
//            if(isClientUpdate){
//                //获取专业组信息
////                FormInfoVO tempFormInfo = FormInfoVO.ConvertFromProto(detailInfo);
//
//
//                //教师存在,  用户存在 : 加
//                //教师存在,  用户不存在: 不加
//                //教师不存在: 加
//                com.msg.FormDetailInfo clientDetailInfo = formInfoVO.ConvToFormDetail(true);
//                com.msg.FormDetailInfo teacherDetailInfo = formInfoVO.ConvToFormDetail(false);
//
//                //获取专业组信息;
//                //client有, 但是 teach 没有的 : 不加
//                //client和 teach 都有的 加
//                //client中没存在的 , 加
//                com.msg.FormDetailInfo newClientInfo  =  GameUtil.DelAlreadyExistInClientNotInTeacher(detailInfo, teacherDetailInfo,clientDetailInfo);
//                FormInfoVO tempFormInfo = FormInfoVO.ConvertFromProto(newClientInfo);
//
////客户端更新
//                //1. 如果是 客户更新， 只修改 a
//                formInfoVO.setFormDetail(tempFormInfo.getFormDetail()); //
////                formInfoVO.setFormDetailTeacher("");
//            }else {
////教师更新
//                com.msg.FormDetailInfo clientDetailInfo = formInfoVO.ConvToFormDetail(true);
//                com.msg.FormDetailInfo teacherDetailInfo = formInfoVO.ConvToFormDetail(false);
//
//                //获取专业组信息;
//                //client有, 但是 teach 没有的 : 不加
//                //client和 teach 都有的 加
//                //client中没存在的 , 加
//                com.msg.FormDetailInfo newTeacherInfo  =   GameUtil.DelAlreadyExistInClientNotInTeacher(detailInfo, clientDetailInfo,teacherDetailInfo);
////                //todo 检测 newTeacherInfo中哪个是 被删除的
////                com.msg.FormDetailInfo newTeacherInfo1  =   GameUtil.RemAlreadyDelInClient(newTeacherInfo, clientDetailInfo);
//
//                FormInfoVO tempFormInfo = FormInfoVO.ConvertFromProto(newTeacherInfo);
//                formInfoVO.setFormDetailTeacher(tempFormInfo.getFormDetail()); //
//
//               // 2 如果是 教师更新，  修改b， 同时 如果a中数据被删除， 也需要修改。c
//                com.msg.FormDetailInfo newClientInfo  =   GameUtil.newFormDeleteOldClient(detailInfo, clientDetailInfo);
//                FormInfoVO tempFormClientInfo = FormInfoVO.ConvertFromProto(newClientInfo);
//                formInfoVO.setFormDetail(tempFormClientInfo.getFormDetail());
//            }
////            formInfoVO.setFormDetail(tempFormInfo.getFormDetail());
////            formInfoVO.setSelectedMajorId(tempFormInfo.getSelectedMajorId());
//            if(teacher != null){
//                formInfoVO.setLastOpType(isClientUpdate ? EnumFormOpType.ByUser.getStateID() : EnumFormOpType.ByTeacher.getStateID());
//            }
//        }

        formInfoVO.setLastUpdateDate(new Date());

//        LockForm(formInfoVO.getId(), teacher.getId(), EnumLockStatus.Lock.getStateID());
        formInfoVO = SetLockState(formInfoVO, teacherId, EnumLockStatus.Lock.getStateID());

        DBProxy.Getinstance().FormInfoService.saveFormInfo(formInfoVO);

    }

    public void UpdateDataVerion(Long formId,  com.msg.FormDetailInfo detailInfo, Integer dateversion){

        FormInfoVO formInfoVO = GetFormInfo(formId);
        if(formInfoVO == null) {
            System.out.println("no form id " + formId);
            return;
        }

        FormInfoVO newFormInfo = FormInfoVO.ConvertFromProto(detailInfo, null, false);
        formInfoVO.setFormDetail(newFormInfo.getFormDetail()); //
        formInfoVO.setSelectedMajorId(newFormInfo.getSelectedMajorId());

        //更新版本号
        formInfoVO.setDataVersion(dateversion);
        DBProxy.Getinstance().FormInfoService.saveFormInfo(formInfoVO);
    }

    public FormInfoVO GetFormInfo(Long formId) {
        FormInfoVO formInfoVO = DBProxy.Getinstance().FormInfoService.findOneById(formId);
        return formInfoVO;
    }

    public void DeleteFormInfo(Long formId) {
        FormInfoVO formInfoVO = GetFormInfo(formId);
        if (formInfoVO == null) {
            return;
        }
        formInfoVO.setIsDelete(EnumDeleteStatus.Deleted.getStateID());


        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(formInfoVO.getOrderId());
        //更新 Order 对应的的志愿数量
        OrderManager.GetInstance().UpdateOrderFormNum(orderInfoVO.getId(), -1);


        DBProxy.Getinstance().FormInfoService.saveFormInfo(formInfoVO);
    }

    public void RecoverFormInfo(Long formId) {
        FormInfoVO formInfoVO = GetFormInfo(formId);
        if (formInfoVO == null) {
            return;
        }
        formInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());


        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(formInfoVO.getOrderId());
        //更新 Order 对应的的志愿数量
        OrderManager.GetInstance().UpdateOrderFormNum(orderInfoVO.getId(), 1);


        DBProxy.Getinstance().FormInfoService.saveFormInfo(formInfoVO);
    }

    public FormInfoVO LockForm(Long formId, Long teacherId, Integer lockStatus) {
        FormInfoVO formInfoVO = GetFormInfo(formId);
        if (formInfoVO == null) {
            return null;
        }

////        formInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());
//        formInfoVO.setStatus(lockStatus);
//        formInfoVO.setLockerId( teacherId);
////        formInfoVO.setStatus(formInfoVO.getStatus() == EnumLockStatus.Lock.getStateID() ? EnumLockStatus.Unlock.getStateID());

        formInfoVO = SetLockState(formInfoVO, teacherId, lockStatus);

        DBProxy.Getinstance().FormInfoService.saveFormInfo(formInfoVO);

        return formInfoVO;
    }

    private FormInfoVO  SetLockState( FormInfoVO formInfoVO , Long teacherId, Integer lockStatus){
        formInfoVO.setStatus(lockStatus);
        formInfoVO.setLockerId( teacherId);

        return formInfoVO;
    }



    public boolean IsFormNameExist(String formName) {
        boolean exist = true;
        List<FormInfoVO> list = DBProxy.Getinstance().FormInfoService.findByFormName(formName);

        return list.size() > 0;
    }
    //查询 客单下
    public boolean IsFormNameInOrderExist(String formName, Long orderId) {
        boolean exist = true;

//        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);
        List<FormInfoVO> list = DBProxy.Getinstance().FormInfoService.findByFormNameAndOrderId(formName, orderId);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getIsDelete() == EnumDeleteStatus.UnDeleted.getStateID()) return true;  //不查询 删除客单的名字
        }
//        return list.size() > 0;
        return false;
    }
}
