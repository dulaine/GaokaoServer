package com.baizhou.manager;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.entity.FormInfo;
import com.baizhou.core.model.entity.TemplateInfo;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.*;
import com.baizhou.db.DBProxy;
import com.baizhou.gameutil.StringParser;
import com.baizhou.util.GameUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TemplateManager {

    private static TemplateManager instance;

    public static TemplateManager GetInstance() {
        if (instance == null) {
            instance = new TemplateManager();
        }
        return instance;
    }


    public TemplateInfoVO GetTemplateById(Long formId) {
        TemplateInfoVO formInfoVO = DBProxy.Getinstance().TemplateInfoService.findOneById(formId);
        return formInfoVO;
    }


    //1. 查询: 获取  自己创建/ 公开的 template/  被授权的     ;     管理员 all   +  当前年份/用户id
    public Map<String, Object> GetTemplateByAssignedTeacherByPage(Long teacherId, Integer isPublic, Integer Pici,  String templateName,  String examYear, int page, int limit,
                                                                  Integer additionType, Long additionId) {
//        UsersResVO teacher = DBProxy.Getinstance().UsersResService.findOneById(teacherId);
//        Pageable pageable = PageRequest.of(page - 1, limit);
        boolean needFilter = additionType != 0;
        boolean useTempId = additionType == 1;//是否使用 指定 tempId / formId

        int tempPage = needFilter ? 0 : page - 1;  //start from 1;
        int tempLimit = needFilter ? 20000 : limit;
        int startIdx_inclu = (page - 1) * limit;
        int endIdx_exclu = (page) * limit;

        List<Sort.Order> orders = new ArrayList<>();
//        orders.add(new Sort.Order(Sort.Direction.ASC, "status")); //排序
        orders.add(new Sort.Order(Sort.Direction.DESC, "creationDate")); //排序
        Pageable pageable = PageRequest.of(tempPage, tempLimit, Sort.by(orders));


        boolean isSuperAdmin = false;

        //如果调用的用户 是管理员, 可以查看所有人
        String authoredTeacherIds = "";
//        if (teacherId != null) {
            UsersResVO teacher = DBProxy.Getinstance().UsersResService.findOneById(teacherId);
            isSuperAdmin = GameUtil.IsSuperAdim(teacher);



            boolean isAdmin = GameUtil.IsAdim(teacher);
            if (isAdmin) {
                //管理员可以查看 所有人
                authoredTeacherIds = "";
            } else {
                //否则只看自己的能看的
                authoredTeacherIds = teacherId + "";
            }
//        }


        Map<String, Object> map = DBProxy.Getinstance().TemplateInfoService.listbyPage(templateName, authoredTeacherIds, isPublic, examYear, Pici, isSuperAdmin ? null : EnumDeleteStatus.UnDeleted.getStateID(),   pageable);

            if(needFilter){

                //订单包含的Templateid 是否 在返回的template中
                List<Long> incluTemplate = GameUtil.GetTemplatListOfTemplateOrForm(useTempId, additionId);
                List<com.msg.TemplateInfo> templateInfos = (List<com.msg.TemplateInfo>) map.get("items");
                //获取新增的TemplateId
                List<Long> newTemplates = GameUtil.GetNewTemplatList(incluTemplate, templateInfos);

                int newInfoCount = newTemplates.size();
                boolean hasNewFilerInfo = newInfoCount > 0; //可添加新数据
                if(hasNewFilerInfo){
                    //添加新Template到list
                    for (int i = 0; i < newTemplates.size(); i++) {
                        TemplateInfoVO vo = TemplateManager.GetInstance().GetTemplateById(newTemplates.get(i));
                        templateInfos.add(vo.ConvertToDTO());
                    }
                }

                //
                List<com.msg.TemplateInfo> protoList = new ArrayList<>();
                if(templateInfos.size() < endIdx_exclu)  endIdx_exclu = templateInfos.size();
                for (int i = startIdx_inclu; i < endIdx_exclu; i++) {
                    protoList.add(templateInfos.get(i));
                }

                int newTotal =templateInfos.size() ;//+ newInfoCount;
                map.put("items", protoList);
                map.put("total", (long)newTotal);////所有信息总数
                map.put("totalPage", newTotal/limit);//所有page总页数

            }

            return map;
    }

    //同年份, 不能有同名
    public boolean IsTemplateNameExist(String formName, String examYear) {
        boolean exist = true;
        List<TemplateInfoVO> list = DBProxy.Getinstance().TemplateInfoService.findByTemplateName(formName);
        for (int i = 0; i < list.size(); i++) {
            TemplateInfoVO vo = list.get(i);
            if(!vo.getExamYear().equals(examYear)) continue;
            if(vo.getIsDelete() == EnumDeleteStatus.UnDeleted.getStateID()) return true;  //不查询 删除客单的名字
        }


        return false;// list.size() > 0 && !formName.isEmpty();
    }



    public TemplateInfoVO Create(Long teacherId, String formName, Integer pici, com.msg.TemplateDetailInfo detailInfo, String year, int isPublic, List<com.msg.Major1stClsInfo> major1stClsInfoList) {

//        //获取原来form信息
//        FormInfoVO lockedFormInfoVO = null;
//        if (lockedFormId != null && lockedFormId > 0) {
//            lockedFormInfoVO = GetFormInfo(lockedFormId);
//            if (lockedFormInfoVO != null) {
//                orderId = lockedFormInfoVO.getOrderId();
//                pici = lockedFormInfoVO.getPici();
//            }
//        }


//        UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(teacherId);
//        boolean isClientUpdate = teacher.getRole() == EnumRoleType.Client.getStateID();

//        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);
//        ClientInfoVO clientInfoVO = OrderManager.GetInstance().GetClientByTel(orderInfoVO.getTel());


        //获取专业组信息

//        com.msg.FormDetailInfo detailInfoOldWeb = lockedFormInfoVO == null ? null : lockedFormInfoVO.ConvToFormDetailForWeb2(false);//  formInfoVO.ConvToFormDetailForWeb(isClientUpdate);
        TemplateInfoVO formInfoVO = TemplateInfoVO.ConvertFromProto(detailInfo);


        //模板信息

        formInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());
        formInfoVO.setCreationDate(new Date());
        formInfoVO.setLastUpdateDate(new Date());

        formInfoVO.setTemplateName(formName);


        formInfoVO.setCreatorId(teacherId);
        UsersResVO vo = PlayerManager.GetInstance().GetPlayerById(teacherId);
        if (vo == null) {
            System.out.println("没找到用户 " + teacherId);
        }
        formInfoVO.setCreatorName(vo == null ? "NonUser" : vo.getName());

        formInfoVO.setAuthorTeacherIds(CommonUtil.AddBracket(teacherId + ""));

        formInfoVO.setIsPublic(isPublic);//(EnumTemplatePublic.Unpub.getStateID());

        //设置 相关专业
        String relativeMajor1stClsInfos = CommonUtil.ParseMajorItemFromProto(major1stClsInfoList);
        formInfoVO.setRelativeMajorList(relativeMajor1stClsInfos);


        formInfoVO.setPici(pici);
        formInfoVO.setExamYear(year);

        //保存版本信息
        LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion(EnumPici.Get(pici), year);
        if(latestVerInfoVO != null) formInfoVO.setDataVersion(latestVerInfoVO.getDataVersion());
        formInfoVO.setOriginSelectedMajorId(formInfoVO.getSelectedMajorId());
        formInfoVO.setOriginFormDetail(formInfoVO.getFormDetail());

        TemplateInfo formInfoEntity = DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(formInfoVO);
        formInfoVO.setId(formInfoEntity.getId());

        //更新 Order 对应的的志愿数量
//        OrderManager.GetInstance().UpdateOrderFormNum(orderInfoVO.getId(), 1);


        return formInfoVO;

    }

//
//    //分配template 到 teacher
//    public void AssignToTeacher(TemplateInfoVO orderInfoVO, UsersResVO usersResVO) {
//
//        List<String> templist = StringParser.SplitString(orderInfoVO.getAuthorTeacherIds(), ConstDefine.ItemSperator7);
//        List<Long> validTeachers = new ArrayList<>();
//        for (int i = 0; i < templist.size(); i++) {
//            validTeachers.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
//        }
//
//        boolean existAlready = false;
//        for (int i = 0; i < validTeachers.size(); i++) {
//            if (validTeachers.get(i) == usersResVO.getId()) {
//                existAlready = true;
//                break;
//            }
//        }
//        if (!existAlready) {
//            validTeachers.add(usersResVO.getId());
//            orderInfoVO.setAuthorTeacherIds(CommonUtil.ConvertIdToString(validTeachers));
//
//        }
//
//        DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(orderInfoVO);
//
//    }

    public void AssignToTeacher(TemplateInfoVO orderInfoVO, List<Long> users) {

        List<String> templist = StringParser.SplitString(orderInfoVO.getAuthorTeacherIds(), ConstDefine.ItemSperator7);
        List<Long> validTeachers = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            validTeachers.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
        }

        for (int j = 0; j < users.size(); j++) {
            Long newUserId = users.get(j);

            boolean existAlready = false;
            for (int i = 0; i < validTeachers.size(); i++) {
                if (validTeachers.get(i) == newUserId) {
                    existAlready = true;
                    break;
                }
            }
            if (!existAlready) {
                validTeachers.add(newUserId);
                orderInfoVO.setAuthorTeacherIds(CommonUtil.ConvertIdToString(validTeachers));

            }

        }



        DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(orderInfoVO);

    }


    public TemplateInfoVO UpdateStatus(Long templateId, EnumTemplatePublic enumTemplatePublic) {
        TemplateInfoVO templateInfoVO = TemplateManager.GetInstance().GetTemplateById(templateId);
        templateInfoVO.setIsPublic(enumTemplatePublic.getStateID());

        DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(templateInfoVO);
        return templateInfoVO;
    }


    public void Update(Long formId, String formName, com.msg.TemplateDetailInfo detailInfo,List<com.msg.Major1stClsInfo> major1stClsInfoList) {


        TemplateInfoVO formInfoVO = GetTemplateById(formId);
        if(formInfoVO == null) {
            System.out.println("no template id " + formId);
            return;
        }

        formInfoVO.setTemplateName(formName);

        //检测专业是否有更新
        TemplateInfoVO tempFormInfo = TemplateInfoVO.ConvertFromProto(detailInfo);
        formInfoVO.setFormDetail(tempFormInfo.getFormDetail()); //
        formInfoVO.setSelectedMajorId(tempFormInfo.getSelectedMajorId());

        //设置 相关专业
        String relativeMajor1stClsInfos = CommonUtil.ParseMajorItemFromProto(major1stClsInfoList);
        formInfoVO.setRelativeMajorList(relativeMajor1stClsInfos);

        formInfoVO.setLastUpdateDate(new Date());

        DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(formInfoVO);

    }

    public void UpdateDataVerion(Long formId, com.msg.TemplateDetailInfo detailInfo, Integer dateversion){

        TemplateInfoVO formInfoVO = GetTemplateById(formId);
        if(formInfoVO == null) {
            System.out.println("no template id " + formId);
            return;
        }

        TemplateInfoVO tempFormInfo = TemplateInfoVO.ConvertFromProto(detailInfo);
        formInfoVO.setFormDetail(tempFormInfo.getFormDetail()); //
        formInfoVO.setSelectedMajorId(tempFormInfo.getSelectedMajorId());

        //更新版本号
        formInfoVO.setDataVersion(dateversion);

        DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(formInfoVO);
    }

    public void Delete(Long formId) {
        TemplateInfoVO formInfoVO = GetTemplateById(formId);
        if (formInfoVO == null) {
            return;
        }
        formInfoVO.setIsDelete(EnumDeleteStatus.Deleted.getStateID());


//        OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(formInfoVO.getOrderId());
//        //更新 Order 对应的的志愿数量
//        OrderManager.GetInstance().UpdateOrderFormNum(orderInfoVO.getId(), -1);


        DBProxy.Getinstance().TemplateInfoService.saveTemplateInfo(formInfoVO);
    }


}
