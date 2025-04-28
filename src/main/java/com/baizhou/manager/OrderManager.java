package com.baizhou.manager;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.entity.OrderInfo;
import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.db.DBProxy;
import com.baizhou.gameutil.StringParser;
import com.baizhou.util.GameUtil;
import com.msg.ClientInfo;
import com.msg.Pref;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {
    private static OrderManager instance;

    public static OrderManager GetInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }


    //根据用户手机查询
    public Map<String, Object> GetOrderByBothClientTelAndValidTeacherByPage(Long teacherId, String clientTel, int page, int limit) {
        String validTeacherIds = GetValidTeacherIdsByPhoneOrTeacherName(clientTel);

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "creationDate")); //排序
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));
//        Pageable pageable = PageRequest.of(page - 1, limit);
        Map<String, Object> map = DBProxy.Getinstance().OrderInfoService.listTelAndValidTeacherbyPage(clientTel, validTeacherIds, EnumDeleteStatus.UnDeleted.getStateID(), pageable);
        return map;
    }

//    //根据用户手机查询
//    public Map<String, Object> GetOrderByClientTelByPage(String clientTel, int page, int limit) {
//        Pageable pageable = PageRequest.of(page - 1, limit);
//        Map<String, Object> map = DBProxy.Getinstance().OrderInfoService.listbyPage(null, clientTel, null, null, null, null, null, null, EnumDeleteStatus.UnDeleted.getStateID(), pageable);
//        return map;
//    }
//
//    //根据授权的老师名  查询
//    public Map<String, Object> GetOrderByValidTeacherNameByPage(String validTeacherName, int page, int limit) {
//
//        String validTeacherIds = GetValidTeacherIds(validTeacherName);
//        Pageable pageable = PageRequest.of(page - 1, limit);
//        Map<String, Object> map = DBProxy.Getinstance().OrderInfoService.listbyPage(null, null, null, null, validTeacherIds, null, null, null, EnumDeleteStatus.UnDeleted.getStateID(), pageable);
//        return map;
//    }

    private String GetValidTeacherIds(String validTeacherName) {
        List<UsersResVO> teachers = DBProxy.Getinstance().UsersResService.findByNameLike(validTeacherName);
        //组成授权id
        String validTeacherIds = "";
        for (int i = 0; i < teachers.size(); i++) {
            if (i > 0) validTeacherIds += ConstDefine.ItemSperator1;
            validTeacherIds += teachers.get(i).getId();
        }

        if (validTeacherIds.isEmpty()) validTeacherIds = "xxxx";//如果没有找到授权的老师, 用一个不存在的id
        return validTeacherIds;
    }

    private String GetValidTeacherIdsByPhoneOrTeacherName(String validTeachePhone) {
        List<UsersResVO> teachers = DBProxy.Getinstance().UsersResService.findByTelLike(validTeachePhone);
        List<UsersResVO> teachers2 = DBProxy.Getinstance().UsersResService.findByNameLike(validTeachePhone);

        List<Long> teacherIdList = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            teacherIdList.add(teachers.get(i).getId());
        }
        for (int i = 0; i < teachers2.size(); i++) {
            teacherIdList.add(teachers2.get(i).getId());
        }

        //组成授权id
        String validTeacherIds = "";
        for (int i = 0; i < teacherIdList.size(); i++) {
            if (i > 0) validTeacherIds += ConstDefine.ItemSperator1;
            validTeacherIds += teacherIdList.get(i);
        }

        if (validTeacherIds.isEmpty()) validTeacherIds = "xxxx";//如果没有找到授权的老师, 用一个不存在的id
        return validTeacherIds;
    }


    //根据 查询  志愿填报
    public Map<String, Object> GetOrderByAssignedTeacherByPage(Long teacherId, Integer status, String clientName, String province, String examYear, int page, int limit, String clientTelOrTeacher,  Integer physicLimit,  Integer incluDelete) {
//        UsersResVO teacher = DBProxy.Getinstance().UsersResService.findOneById(teacherId);
//        Pageable pageable = PageRequest.of(page - 1, limit);
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "status")); //排序
        orders.add(new Sort.Order(Sort.Direction.DESC, "creationDate")); //排序
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));

        boolean useClientTel = false;
        boolean useValidTeacherName = false;
        if (!clientTelOrTeacher.trim().isEmpty()) {
            List<ClientInfoVO> teachers = DBProxy.Getinstance().ClientInfoService.findOneByTelLike(clientTelOrTeacher);
            if (teachers.size() > 0) useClientTel = true;
            List<UsersResVO> teachers2 = DBProxy.Getinstance().UsersResService.findByNameLike(clientTelOrTeacher);
            if (teachers2.size() > 0) useValidTeacherName = true;
            if (!useClientTel && !useValidTeacherName && !clientTelOrTeacher.isEmpty()) {
                //都不存在
                useClientTel = true;
            }
        }


        boolean isSuperAdmin = false;

        //如果调用的用户 是管理员, 可以查看所有人
        String authoredTeacherIds = "";
//        if (!clientTelOrTeacher.isEmpty()) {
//            authoredTeacherIds = GetValidTeacherIdsByPhoneOrTeacherName(clientTelOrTeacher);
//        }
        if (teacherId != null) {
            UsersResVO teacher = DBProxy.Getinstance().UsersResService.findOneById(teacherId);
            isSuperAdmin = GameUtil.IsSuperAdim(teacher);
            if (teacher.getRole() == EnumRoleType.Super.getStateID() || teacher.getRole() == EnumRoleType.Admin.getStateID()) {
                //管理员可以查看 所有人
                authoredTeacherIds = "";
            } else {
                //否则只看自己的能看的
                authoredTeacherIds = teacherId + "";
            }
        }


        //同时可以查看的 授权老师
        String alsoAuthoredTeacherIds = "";
        if (useValidTeacherName) {
            alsoAuthoredTeacherIds = GetValidTeacherIds(clientTelOrTeacher);
        }



//        Map<String, Object> map = DBProxy.Getinstance().OrderInfoService.listbyPage(clientName, useClientTel ? clientTelOrTeacher : null, status, null, authoredTeacherIds, alsoAuthoredTeacherIds, province, examYear, isSuperAdmin ? null : EnumDeleteStatus.UnDeleted.getStateID() , pageable);
        Map<String, Object> map = DBProxy.Getinstance().OrderInfoService.listbyPage(clientName, useClientTel ? clientTelOrTeacher : null, status, null, authoredTeacherIds, alsoAuthoredTeacherIds, province, examYear, incluDelete , physicLimit, pageable);
        return map;
    }

    public Map<String, Object> GetOrderByClientOnlyByPage(Long teacherId, Integer status, String clientName, String province, String examYear, int page, int limit, String clientTelOrTeacher, Integer physicLimit) {

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "status")); //排序
        orders.add(new Sort.Order(Sort.Direction.DESC, "creationDate")); //排序
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(orders));

        UsersResVO teacher =  DBProxy.Getinstance().UsersResService.findOneById(teacherId);


        //同时可以查看的 授权老师
        String alsoAuthoredTeacherIds = "";


        Map<String, Object> map = DBProxy.Getinstance().OrderInfoService.listbyPage(clientName, teacher.getTel(), status, null, "", alsoAuthoredTeacherIds, province, examYear, EnumDeleteStatus.UnDeleted.getStateID(), physicLimit, pageable);
        return map;
    }


    //创建用户
    public void CreateOrderByClientInfo(Long teacherId, com.msg.ClientInfo clientInfo) {

        com.msg.ClientInfo.Builder clientInfoBuilder = clientInfo.toBuilder();
        clientInfoBuilder.addAuthorTeacherIds(teacherId);
        com.msg.ClientInfo tempClient = clientInfoBuilder.build();

        //创建client 信息, 保存db
        ClientInfoVO clientInfoVO = ClientInfoVO.ConvertFromDTO(tempClient);
        com.baizhou.core.model.entity.ClientInfo clientInfo1 = DBProxy.Getinstance().ClientInfoService.saveClientInfo(clientInfoVO);

        //创建order 信息, 保存db
        OrderInfoVO orderInfoVO = OrderInfoVO.CreateFrom(clientInfoVO, teacherId);
        OrderInfo orderInfo = DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);
        orderInfoVO.setId(orderInfo.getId());

        //新建用户
        CreateUserClient(clientInfoVO);


        UpdateOrderInfo(clientInfoVO, tempClient);
    }

    private void CreateUserClient(ClientInfoVO clientInfoVO) {
        String tel = clientInfoVO.getTel();
        String pwd = clientInfoVO.getPassword();

        //检测用户是否已经创建
        UsersResVO exist = PlayerManager.GetInstance().GetPlayerByTel(tel);
        if (exist != null) {
            boolean needUpdate = false;
            if(exist.getUserExpireDate() == null || clientInfoVO.getUserExpireDate().getTime() != exist.getUserExpireDate().getTime()){
                needUpdate = true;
                exist.setUserExpireDate(clientInfoVO.getUserExpireDate());
            }

            //更新用户密码
            if(!exist.getPwd().equals(pwd)){
                needUpdate = true;
                exist.setPwd(pwd);
            }
            if(needUpdate){
                PlayerManager.GetInstance().UpdatePlayerInfo(exist.ConvertToDTO());
            }
            return;
        }

        //创建用户
        UsersResVO player = PlayerManager.GetInstance().CreatePlayerByNameAndPwd(clientInfoVO.getName(),tel, pwd, EnumRoleType.Client, clientInfoVO.getUserExpireDate());//新建账号
    }

    public OrderInfoVO GetOrderById(Long id) {

        //1. 从内存中获取
        OrderInfoVO orderInfoVO = null;//m_UserNameTableDic.getOrDefault(tel, null);

        if (orderInfoVO == null) {
            //2. 从db 获取
            orderInfoVO = DBProxy.Getinstance().OrderInfoService.findOneById(id);
//            if (player == null || player.size() == 0) return null;
//            orderInfoVO = player.get(0);
//            this.RecordUserInfo(usersVO, true);
        }


        return orderInfoVO;
    }


    public OrderInfoVO GetOrderByClientTel(String tel) {

        //1. 从内存中获取
        OrderInfoVO orderInfoVO = null;//m_UserNameTableDic.getOrDefault(tel, null);

        if (orderInfoVO == null) {
            //2. 从db 获取
            List<OrderInfoVO> list = DBProxy.Getinstance().OrderInfoService.findByTel(tel);
            if (list == null || list.size() == 0) return null;
            orderInfoVO = list.get(0);
//            this.RecordUserInfo(usersVO, true);
        }


        return orderInfoVO;
    }


    public ClientInfoVO GetClientByTel(String tel) {

        //1. 从内存中获取
        ClientInfoVO clientInfoVO = null;//m_UserNameTableDic.getOrDefault(tel, null);

        if (clientInfoVO == null) {
            //2. 从db 获取
            clientInfoVO = DBProxy.Getinstance().ClientInfoService.findOneByTel(tel);
//            if (player == null || player.size() == 0) return null;
//            orderInfoVO = player.get(0);
//            this.RecordUserInfo(usersVO, true);
        }


        return clientInfoVO;
    }


    //更新订单信息
    public void UpdateOrderInfo(com.msg.OrderInfo protoOrder) {
        OrderInfoVO orderInfoVO = GetOrderById(protoOrder.getId());

        if (orderInfoVO == null) {
            System.out.println("没有订单信息  " + protoOrder.getId());
            return;
        }

        orderInfoVO = orderInfoVO.UpdateFromDTO(protoOrder);

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }

    public void DeleteOrderInfo(Long orderId) {
        OrderInfoVO orderInfoVO = GetOrderById(orderId);

        if (orderInfoVO == null) {
            System.out.println("DeleteOrderInfo没有订单信息  " + orderId);
            return;
        }

        orderInfoVO.setIsDelete(EnumDeleteStatus.Deleted.getStateID());

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }

    public void RecoverOrderInfo(Long orderId) {
        OrderInfoVO orderInfoVO = GetOrderById(orderId);

        if (orderInfoVO == null) {
            System.out.println("recover OrderInfo没有订单信息  " + orderId);
            return;
        }

        orderInfoVO.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }


    public void UpdateOrderFormNum(Long orderId, Integer addFormNum) {
        OrderInfoVO orderInfoVO = GetOrderById(orderId);
        if (orderInfoVO == null) {
            System.out.println(" UpdateOrderFormNum 没有订单信息  " + orderId);
            return;
        }

        orderInfoVO.setFormNum(orderInfoVO.getFormNum() + addFormNum);

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);
    }


    public void UpdateOrderToTeacher(OrderInfoVO orderInfoVO, UsersResVO usersResVO) {
//        OrderInfoVO orderInfoVO = GetOrderById(protoOrder.getId());

//        if(orderInfoVO == null) {
//            System.out.println("没有订单信息  " + protoOrder.getId());
//            return;
//        }
//
//        orderInfoVO = orderInfoVO.UpdateFromDTO(protoOrder);

        orderInfoVO.setAssignedTeacherId(usersResVO.getId());
        orderInfoVO.setAssignedTeacher(usersResVO.getName());

        List<String> templist = StringParser.SplitString(orderInfoVO.getAuthorTeacherIds(), ConstDefine.ItemSperator7);
        List<Long> validTeachers = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            validTeachers.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
        }
//        List<Long> validTeachers = StringParser.SplitLongNumber(orderInfoVO.getAuthorTeacherIds(), ConstDefine.ItemSperator7);


        boolean existAlready = false;
        for (int i = 0; i < validTeachers.size(); i++) {
            if (validTeachers.get(i) == usersResVO.getId()) {
                existAlready = true;
                break;
            }
        }
        if (!existAlready) {
            validTeachers.add(usersResVO.getId());
//            orderInfoVO.setAuthorTeacherIds(StringParser.MakeLongString(validTeachers, ConstDefine.ItemSperator7));
            orderInfoVO.setAuthorTeacherIds(CommonUtil.ConvertIdToString(validTeachers));

        }


        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }

    public void UpdatePreferenceInfo(OrderInfoVO orderInfoVO, List<String> favMajorGroupList, List<String> unfavMajorGroupList, List<String> neutralMajorGroupList) {

        orderInfoVO.setFavoredMajorGroupList(StringParser.MakeString(favMajorGroupList, ConstDefine.ItemSperator7));
        orderInfoVO.setUnfavoredGroupMajorList(StringParser.MakeString(unfavMajorGroupList, ConstDefine.ItemSperator7));
        orderInfoVO.setNeutralGroupMajorList(StringParser.MakeString(neutralMajorGroupList, ConstDefine.ItemSperator7));

//        orderInfoVO = orderInfoVO.UpdateFromDTO(protoOrder);

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }

    public void UpdatePreferenceInfo(OrderInfoVO orderInfoVO,  HashMap<String, Pref.Builder> protoDic) {

//        orderInfoVO.setFavoredMajorGroupList(StringParser.MakeString(favMajorGroupList, ConstDefine.ItemSperator7));
//        orderInfoVO.setUnfavoredGroupMajorList(StringParser.MakeString(unfavMajorGroupList, ConstDefine.ItemSperator7));
//        orderInfoVO.setNeutralGroupMajorList(StringParser.MakeString(neutralMajorGroupList, ConstDefine.ItemSperator7));
//
////        orderInfoVO = orderInfoVO.UpdateFromDTO(protoOrder);
        List<String> list = GameUtil.ConvPrefProtoToDBStr( GameUtil.ConvertDBPrefStringToProto(protoDic));
        orderInfoVO.setFavoredMajorGroupList(list.get(0));
        orderInfoVO.setUnfavoredGroupMajorList(list.get(1));
        orderInfoVO.setNeutralGroupMajorList(list.get(2));

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }

    //更新用户信息
    public void UpdateClientInfo(com.msg.ClientInfo protoClient) {
        ClientInfoVO clientInfoVO = GetClientByTel(protoClient.getTel());

        if (clientInfoVO == null) {
            System.out.println("没有Client信息  " + clientInfoVO.getTel());
            return;
        }

        clientInfoVO = clientInfoVO.UpdateFromDTO(protoClient);

        //新建用户
        CreateUserClient(clientInfoVO);

        DBProxy.Getinstance().ClientInfoService.saveClientInfo(clientInfoVO);

        //更新对应OrderIn
        this.UpdateOrderInfo(clientInfoVO,protoClient);

    }

    public void UpdateOrderInfo(ClientInfoVO clientInfoVO, com.msg.ClientInfo protoClient) {
        OrderInfoVO orderInfoVO = GetOrderByClientTel(clientInfoVO.getTel());

        if (orderInfoVO == null) {
            System.out.println("没有订单信息  " + orderInfoVO.getId());
            return;
        }

        orderInfoVO = orderInfoVO.UpdateFromClientInfo(clientInfoVO,protoClient);

        DBProxy.Getinstance().OrderInfoService.saveOrderInfo(orderInfoVO);

    }

}
