package com.baizhou.util;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.*;
import com.baizhou.gameutil.StringParser;
import com.baizhou.manager.*;
import com.msg.UniMajorInfo;
import com.msg.scoreHistItem;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

public class GameUtil {


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//获取奖励

    //从数量-数量中获取数量
    public static Integer GetNumFromRange(String numRange) {
        String[] nums = numRange.split(ConstDefine.ItemSperator4);
        if (nums.length > 1) {
            Integer from = Integer.parseInt(nums[0]);
            Integer to = Integer.parseInt(nums[1]);
            return CommonUtil.RandomRange(from, to);
        } else {
            return Integer.parseInt(numRange.trim());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static long id = 0;

    //获取唯一id
    public static String GetUID() {
        ++id;
        return new Date().getTime() + "p" + id;
    }

    public synchronized static String GetKrpanoID() {
        if (id >= 10000) id = 0;
        ++id;
        return Long.toString(System.currentTimeMillis()) + "p" + id;///CommonUtil.CreateTimestamp();//   CommonUtil.CreateNonceStr();
    }


    public static boolean IsLinuxOS() {
        String os = System.getProperty("os.name");
        //Windows操作系统
//        if (os != null && os.toLowerCase().startsWith("windows")) {
//            System.out.println(String.format("当前系统版本是:%s", os));
//        } else

        if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
            return true;
        } else { //其它操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
        }

        return false;
    }
    public static boolean CheckIfFormSame(com.msg.FormDetailInfo detailInfo1, com.msg.FormDetailInfo detailInfo2) {

        List<com.msg.UniMajorGroupInfo> group1s = detailInfo1.getGroupsList();
        List<com.msg.UniMajorGroupInfo> group2s = detailInfo2.getGroupsList();

        for (int i = 0; i < group1s.size(); i++) {
            com.msg.UniMajorGroupInfo group1 = group1s.get(i);

            List<com.msg.UniMajorInfo> group1MajorsList = group1.getMajorsList();
            if(group1MajorsList.size() > 0){
                String group1Code = group1MajorsList.get(0).getUniMajorCode();

                boolean exist = false;
                for (int j = 0; j < group2s.size(); j++) {
                    com.msg.UniMajorGroupInfo group2 = group2s.get(j);
                    if(group2.getMajorsList().size() == group1.getMajorsList().size()){

                        List<com.msg.UniMajorInfo> group2MajorsList = group2.getMajorsList();
                        String group2Code = group2MajorsList.get(0).getUniMajorCode();
                        if(group1Code.equals(group2Code)){
                                //检测每个专业是否相同
//                            if(!CheckMajors(group1MajorsList, group2MajorsList)){
//                                exist = false;
//                                return  false;
//                            }else {
                                exist = true;
                                break;
//                            }
                        }
                    }

                }


                if(!exist){
                    return  false;
                }
            }
        }

        return  true;


    }

    public static boolean CheckIfFormSeqSame(com.msg.FormDetailInfo detailInfo1, com.msg.FormDetailInfo detailInfo2) {

        List<com.msg.UniMajorGroupInfo> group1s = detailInfo1.getGroupsList();
        List<com.msg.UniMajorGroupInfo> group2s = detailInfo2.getGroupsList();

        if(group1s.size() != group2s.size()) return true;

        for (int i = 0; i < group1s.size(); i++) {
            com.msg.UniMajorGroupInfo group1 = group1s.get(i);

            List<com.msg.UniMajorInfo> group1MajorsList = group1.getMajorsList();
            if(group1MajorsList.size() > 0){
                String group1Code = group1MajorsList.get(0).getUniMajorCode();

                com.msg.UniMajorGroupInfo group2 = group2s.get(i);
                List<com.msg.UniMajorInfo> group2MajorsList = group2.getMajorsList();
                String group2Code = group2MajorsList.get(0).getUniMajorCode();

                if(!group1Code.equals(group2Code)){
                    return true;
                }
            }
        }

        return  false;
    }


    public static boolean CheckMajors( List<com.msg.UniMajorInfo> group1MajorsList, List<com.msg.UniMajorInfo> group2MajorsList){
        if(group1MajorsList.size() != group2MajorsList.size())  return false;

        for (int i = 0; i < group1MajorsList.size(); i++) {
            com.msg.UniMajorInfo majorInfo1 = group1MajorsList.get(i);

            boolean exist = false;
            for (int j = 0; j < group2MajorsList.size(); j++) {
                com.msg.UniMajorInfo majorInfo2 = group2MajorsList.get(j);
                if(majorInfo1.getId() == majorInfo2.getId()){
                    exist = true;
                    break;
                }
            }

            if(!exist){
                return false;
            }
        }

        return true;
    }

    public static com.msg.FormDetailInfo DelAlreadyExistInClientNotInTeacher(com.msg.FormDetailInfo newTFormDetailInfo, com.msg.FormDetailInfo oldClientFormDetailInfo, com.msg.FormDetailInfo oldTecherFormDetailInfo) {

        List<com.msg.UniMajorGroupInfo> newTFormGroups = new ArrayList<>(newTFormDetailInfo.getGroupsList());
        //newTFormDetailInfo.getGroupsList();
        List<com.msg.UniMajorGroupInfo> oldClientFromGroups = oldClientFormDetailInfo.getGroupsList();

        //遍历new form 数据, 如果某个数据, 在old client 中 存在, 就直接删除;
        Integer newFormSize = newTFormGroups.size();
        for (int i = newFormSize - 1; i >= 0; i--) {
            com.msg.UniMajorGroupInfo newFormGroup = newTFormGroups.get(i);

            List<com.msg.UniMajorInfo> newMajorList = newFormGroup.getMajorsList();
            if(newMajorList.size() > 0){
                String newGroupCode = newMajorList.get(0).getUniMajorCode();

                //使用new form 的 groupCode;  遍历client 的GroupCode
//                boolean exist = false;
//                for (int j = 0; j < oldClientFromGroups.size(); j++) {
//                    com.msg.UniMajorGroupInfo clientGroup = oldClientFromGroups.get(j);
//
//                    List<com.msg.UniMajorInfo> clientMajorInfos = clientGroup.getMajorsList();
//                    if(clientMajorInfos.size() > 0){
//                        String clientGroupCode = clientMajorInfos.get(0).getUniMajorCode();
//
//                        //检测是否存在
//                        if(newGroupCode.equals(clientGroupCode)){
//                            exist = true;
//                            break;
//                        }
//                    }
//                }
                boolean exist = CheckExistInGroup(newGroupCode, oldClientFormDetailInfo);
                boolean existInOldTeacher = CheckExistInGroup(newGroupCode, oldTecherFormDetailInfo);

                if(exist && !existInOldTeacher){
                    newTFormGroups.remove(i);
                }
            }

        }

        com.msg.FormDetailInfo.Builder formDetailBuild = com.msg.FormDetailInfo.newBuilder();
        formDetailBuild.clearGroups();
        formDetailBuild.addAllGroups(newTFormGroups);
        formDetailBuild.addAllSelectedMajorIds(new ArrayList<>());
        return formDetailBuild.build();
    }

    private static  boolean CheckExistInGroup(String groupCode, com.msg.FormDetailInfo oldTecherFormDetailInfo){
        List<com.msg.UniMajorGroupInfo> oldClientFromGroups = oldTecherFormDetailInfo.getGroupsList();

        boolean exist = false;
        for (int j = 0; j < oldClientFromGroups.size(); j++) {
            com.msg.UniMajorGroupInfo clientGroup = oldClientFromGroups.get(j);

            List<com.msg.UniMajorInfo> clientMajorInfos = clientGroup.getMajorsList();
            if(clientMajorInfos.size() > 0){
                String clientGroupCode = clientMajorInfos.get(0).getUniMajorCode();

                //检测是否存在
                if(groupCode.equals(clientGroupCode)){
                    return true;
                }
            }
        }
        return false;
    }


    public static com.msg.FormDetailInfo RemAlreadyDelInClient(com.msg.FormDetailInfo newTFormDetailInfo, com.msg.FormDetailInfo oldWebFormDetailInfo) {

        List<com.msg.UniMajorGroupInfo> newTFormGroups = new ArrayList<>(newTFormDetailInfo.getGroupsList());
        //newTFormDetailInfo.getGroupsList();
        List<com.msg.UniMajorGroupInfo> oldWebFormGroups = oldWebFormDetailInfo.getGroupsList();

        //遍历new form 数据, 如果某个数据, 在old web 中 已经删除, 就直接删除;
        Integer newFormSize = newTFormGroups.size();
        for (int i = newFormSize - 1; i >= 0; i--) {
            com.msg.UniMajorGroupInfo newFormGroup = newTFormGroups.get(i);

            List<com.msg.UniMajorInfo> newMajorList = newFormGroup.getMajorsList();
            if(newMajorList.size() > 0){
                String newGroupCode = newMajorList.get(0).getUniMajorCode();

                //使用new form 的 groupCode;  遍历old web 的GroupCode
                boolean exist = false;
                for (int j = 0; j < oldWebFormGroups.size(); j++) {
                    com.msg.UniMajorGroupInfo webGroup = oldWebFormGroups.get(j);

                    List<com.msg.UniMajorInfo> oldWebMajorInfos = webGroup.getMajorsList();
                    //在old web 中 已经删除, 就直接删除;
                    if(webGroup.getGroupStates() == EnumGroupState.Del.getStateID() &&  oldWebMajorInfos.size() > 0){
                        String oldWebGroupCode = oldWebMajorInfos.get(0).getUniMajorCode();
                        //检测是否存在
                        if(newGroupCode.equals(oldWebGroupCode)){
                            exist = true;
                            break;
                        }
                    }
                }

                if(exist){
                    newTFormGroups.remove(i);
                }
            }

        }

//        com.msg.FormDetailInfo.Builder formDetailBuild = oldWebFormDetailInfo.toBuilder() ;
//        formDetailBuild.clearGroups();
//        formDetailBuild.addAllGroups(newTFormGroups);

        com.msg.FormDetailInfo.Builder formDetailBuild = com.msg.FormDetailInfo.newBuilder();
        formDetailBuild.clearGroups();
        formDetailBuild.addAllGroups(newTFormGroups);
        formDetailBuild.addAllSelectedMajorIds(new ArrayList<>());
        return formDetailBuild.build();
    }


    public static com.msg.FormDetailInfo newFormDeleteOldClient(com.msg.FormDetailInfo newTFormDetailInfo, com.msg.FormDetailInfo oldClientFormDetailInfo) {

        List<com.msg.UniMajorGroupInfo> newTFormGroups = newTFormDetailInfo.getGroupsList();
//        List<com.msg.UniMajorGroupInfo> oldClientFromGroups = oldClientFormDetailInfo.getGroupsList();
        List<com.msg.UniMajorGroupInfo> oldClientFromGroups = new ArrayList<>(oldClientFormDetailInfo.getGroupsList());

        //遍历old client 数据, 如果某个数据, 在新update中没有, 就直接删除;
        Integer oldSize = oldClientFromGroups.size();
        for (int i = oldSize - 1; i >= 0; i--) {
            com.msg.UniMajorGroupInfo oldClientGroup = oldClientFromGroups.get(i);

            List<com.msg.UniMajorInfo> oldMajorInfos = oldClientGroup.getMajorsList();
            if(oldMajorInfos.size() > 0){
                String oldGroupCode = oldMajorInfos.get(0).getUniMajorCode();

                //使用old client的 groupCode;  遍历newFrom的GroupCode
                boolean exist = false;
                for (int j = 0; j < newTFormGroups.size(); j++) {
                    com.msg.UniMajorGroupInfo newFormGroup = newTFormGroups.get(j);

                    List<com.msg.UniMajorInfo> newMajorInfos = newFormGroup.getMajorsList();
                    if(newMajorInfos.size() > 0){
                        String newGroupCode = newMajorInfos.get(0).getUniMajorCode();

                        //检测是否存在
                        if(oldGroupCode.equals(newGroupCode)){
                            exist = true;
                            break;
                        }
                    }
                }

                if(!exist){
                    oldClientFromGroups.remove(i);
                }
            }

        }

        com.msg.FormDetailInfo.Builder formDetailBuild = oldClientFormDetailInfo.toBuilder() ;
        formDetailBuild.clearGroups();
        formDetailBuild.addAllGroups(oldClientFromGroups);
        return formDetailBuild.build();
    }

    public static List<com.msg.UniMajorGroupInfo> MergeTeachAndClientDataClientFirst(List<com.msg.UniMajorGroupInfo> teacherSels, List<com.msg.UniMajorGroupInfo> clientSels) {
        // 最近更新的是client
//        1. 先选择用户数据：
//        如果用户数据是空， 跳过， 直接用教师数据； 标识都为normal
//        否则 对所有数据标识  ： 用户更新  1
//        2. 在遍历教师数据；
//        如果数据在用户中已经存在： 对用户数据 标识  0
//        如果在用户数据中不存在：  添加到用户数据， 标识 2删除
        List<com.msg.UniMajorGroupInfo> total = new ArrayList<>();
//        1. 先选择用户数据：
        if(clientSels.size() == 0){
            //        如果用户数据是空， 跳过， 直接用教师数据； 标识都为normal
            for (int i = 0; i < teacherSels.size(); i++) {
                com.msg.UniMajorGroupInfo tempClientGroup = teacherSels.get(i);
                com.msg.UniMajorGroupInfo.Builder tempBuilder = tempClientGroup.toBuilder();
                tempBuilder.setGroupStates(EnumGroupState.Norm.getStateID());
                total.add(tempBuilder.build());
            }
            return total;
        }else {
            ////        否则 对所有数据标识  ： 用户更新  1
            for (int i = 0; i < clientSels.size(); i++) {
                com.msg.UniMajorGroupInfo tempClientGroup = clientSels.get(i);
                com.msg.UniMajorGroupInfo.Builder tempBuilder = tempClientGroup.toBuilder();
                tempBuilder.setGroupStates(EnumGroupState.Add.getStateID());
                total.add(tempBuilder.build());
            }

//        2. 在遍历教师数据；
//        如果数据在用户中已经存在： 对用户数据 标识  0
//        如果在用户数据中不存在：  添加到用户数据， 标识 2删除
            for (int i = 0; i < teacherSels.size(); i++) {
                com.msg.UniMajorGroupInfo tempTeacherGroup = teacherSels.get(i);

                List<com.msg.UniMajorInfo> teacherMajorInfos = tempTeacherGroup.getMajorsList();
                if(teacherMajorInfos.size() > 0){
                    String teacherGroupCode = teacherMajorInfos.get(0).getUniMajorCode();

                //遍历所有 教师数据;
                    boolean exist = false;
                    for (int j = 0; j < total.size(); j++) {
                        com.msg.UniMajorGroupInfo tempClientGroup = total.get(j);

                        List<com.msg.UniMajorInfo> clientMajorInfos = tempClientGroup.getMajorsList();
                        if(clientMajorInfos.size() > 0){
                            String clientGroupCode = clientMajorInfos.get(0).getUniMajorCode();

                            //检测是否存在
                            if(clientGroupCode.equals(teacherGroupCode)){
                                exist = true;
                                // 如果数据在用户中已经存在： 对用户数据 标识  0
                                com.msg.UniMajorGroupInfo.Builder tempBuilder = tempClientGroup.toBuilder();
                                tempBuilder.setGroupStates(EnumGroupState.Norm.getStateID());
                                total.set(j, tempBuilder.build());
                                break;
                            }
                        }
                    }

                    if(!exist){
                        //        如果在用户数据中不存在：  添加到用户数据， 标识 2删除
                        com.msg.UniMajorGroupInfo.Builder tempBuilder = tempTeacherGroup.toBuilder();
                        tempBuilder.setGroupStates(EnumGroupState.Del.getStateID());
                        total.add(tempBuilder.build());
                    }

                }

            }


        }



        return total;
    }

    public static List<com.msg.UniMajorGroupInfo> MergeTeachAndClientDataTeacherFirst(List<com.msg.UniMajorGroupInfo> teacherSels, List<com.msg.UniMajorGroupInfo> clientSels, Integer lastOpType) {
        // 最近更新的是teacher
//        1. 先选择teacher数据：
//        如果teacher数据是空， 跳过， 直接用client数据；  标识都为 add
//        否则 对所有数据标识  ：  normal  ---todo 用户删除: 新建一个用户删除的保存栏位
//        2. 在遍历client数据；
//        如果数据在teacher中已经存在： 对用户数据 标识  0
//        如果在teacher数据中不存在：  添加到用户数据， 标识 新增
        List<com.msg.UniMajorGroupInfo> total = new ArrayList<>();
//        1. 先选择用户数据：
        if(teacherSels.size() == 0){
           // 如果teacher数据是空， 跳过， 直接用client数据；  标识都为 add
            for (int i = 0; i < clientSels.size(); i++) {
                com.msg.UniMajorGroupInfo tempClientGroup = clientSels.get(i);
                com.msg.UniMajorGroupInfo.Builder tempBuilder = tempClientGroup.toBuilder();
                tempBuilder.setGroupStates(EnumGroupState.Add.getStateID());
                total.add(tempBuilder.build());
            }
            return total;
        }else {
            //        否则 对所有数据标识  ：  normal
            for (int i = 0; i < teacherSels.size(); i++) {
                com.msg.UniMajorGroupInfo tempTeacherGroup = teacherSels.get(i);
                com.msg.UniMajorGroupInfo.Builder tempBuilder = tempTeacherGroup.toBuilder();
                tempBuilder.setGroupStates(lastOpType == EnumFormOpType.ByTeacher.getStateID() ? EnumGroupState.Norm.getStateID(): EnumGroupState.Del.getStateID());
                total.add(tempBuilder.build());
            }

//        2. 在遍历client数据；
//        如果数据在teacher中已经存在： 对用户数据 标识  0
//        如果在teacher数据中不存在：  添加到用户数据， 标识 新增
            for (int i = 0; i < clientSels.size(); i++) {
                com.msg.UniMajorGroupInfo tempClientGroup = clientSels.get(i);

                List<com.msg.UniMajorInfo> clientMajorInfos = tempClientGroup.getMajorsList();
                if(clientMajorInfos.size() > 0){
                    String clientGroupCode = clientMajorInfos.get(0).getUniMajorCode();

                    //遍历所有 client数据;
                    boolean exist = false;
                    for (int j = 0; j < total.size(); j++) {
                        com.msg.UniMajorGroupInfo tempTeacherGroup = total.get(j);

                        List<com.msg.UniMajorInfo> teacherMajorInfos = tempTeacherGroup.getMajorsList();
                        if(teacherMajorInfos.size() > 0){
                            String teacherGroupCode = teacherMajorInfos.get(0).getUniMajorCode();

                            //检测是否存在
                            if(teacherGroupCode.equals(clientGroupCode)){
                                exist = true;
                                //        如果数据在teacher中已经存在： 对用户数据 标识  0
                                com.msg.UniMajorGroupInfo.Builder tempBuilder = tempTeacherGroup.toBuilder();
                                tempBuilder.setGroupStates(EnumGroupState.Norm.getStateID());
                                total.set(j, tempBuilder.build());
                                break;
                            }
                        }
                    }

                    if(!exist){
                        //        如果在teacher数据中不存在：  添加到用户数据， 标识 新增
                        com.msg.UniMajorGroupInfo.Builder tempBuilder = tempClientGroup.toBuilder();
                        tempBuilder.setGroupStates(EnumGroupState.Add.getStateID());
                        total.add(tempBuilder.build());
                    }

                }

            }


        }



        return total;
    }


    public static List<com.msg.UniMajorGroupInfo> ConvertUniMajorGroupProtoFromStr(String formDetail, Integer pici ) {

        List<com.msg.UniMajorGroupInfo> groupInfos = new ArrayList<>();

        List<String> groupStr = com.baizhou.gameutil.StringParser.SplitString(formDetail, ConstDefine.ItemSperator3);
        for (int i = 0; i < groupStr.size(); i++) {
            String tempStr = groupStr.get(i);
            List<Long> mjIds = com.baizhou.gameutil.StringParser.SplitLongNumber(tempStr, ConstDefine.ItemSperator7);

            //查询专业组内信息
            List<UniMajorInfo> temp = UniMajorManager.GetInstance().GetInHisDB(mjIds, EnumPici.Get(pici));


            List<UniMajorInfo> protos = new ArrayList<>();
            //按照mjIds排序
            for (int j = 0; j < mjIds.size(); j++) {
                Long mjId = mjIds.get(j);

                for (int k = 0; k < temp.size(); k++) {
                    if (temp.get(k).getId() == mjId) {
                        protos.add(temp.get(k));
                        temp.remove(k);
                        break;
                    }
                }
            }

            //生成group proto
            com.msg.UniMajorGroupInfo.Builder groupBuild = com.msg.UniMajorGroupInfo.newBuilder();
            groupBuild.addAllMajors(protos);

            groupInfos.add(groupBuild.build());
        }

        return groupInfos;

    }

    public static boolean IsSuperAdim(UsersResVO usersResVO){
        if(usersResVO == null) return false;
        return  usersResVO.getRole() == EnumRoleType.Super.getStateID();
    }

    public static boolean IsAdim(UsersResVO usersResVO){
        if(usersResVO == null) return false;
        return  usersResVO.getRole() == EnumRoleType.Super.getStateID() || usersResVO.getRole() == EnumRoleType.Admin.getStateID();
    }

    public static List<com.msg.UniMajorGroupInfo> GetGroupInfoFromWebProto(com.msg.FormDetailInfo formDetailInfo, com.msg.FormDetailInfo formDetailInfoServer, boolean isClient) {

        boolean createNewForm = formDetailInfoServer == null;

        List<com.msg.UniMajorGroupInfo> newMajorInfoList = new ArrayList<>();

        List<com.msg.UniMajorGroupInfo> majorInfoWebList = formDetailInfo.getGroupsList();
        List<com.msg.UniMajorGroupInfo> majorInfoServerList = createNewForm ? new ArrayList<>() : new ArrayList<>(formDetailInfoServer.getGroupsList());

        for (int i = 0; i < majorInfoWebList.size(); i++) {
            com.msg.UniMajorGroupInfo groupInfo = majorInfoWebList.get(i);
            if(groupInfo.getMajorsList().size() == 0) continue;

            boolean isExist = false;

            if(!createNewForm){

                for (int j = majorInfoServerList.size() - 1; j >=0 ; j--) {
                    com.msg.UniMajorGroupInfo tempInfo = majorInfoServerList.get(j);

                    if(groupInfo.getMajorsList().get(0).getUniMajorCode().equals( tempInfo.getMajorsList().get(0).getUniMajorCode() )){
                        //有相同的， 保存成不变数据   == 从Server删除
                        //添加到 新list    状态用server数据
                        com.msg.UniMajorGroupInfo.Builder tempBuilder = groupInfo.toBuilder();// tempInfo.toBuilder();
                        tempBuilder.setGroupStates(tempInfo.getGroupStates());
                        newMajorInfoList.add(tempBuilder.build());
                        //== 从Server删除
                        majorInfoServerList.remove(j);

                        isExist = true;
                        break;
                    }


                }
            }


            if(!isExist){
                //weib中， 存server没有的， 就是新增；  == 从web删除
                //添加到 新list  = 状态新增
                com.msg.UniMajorGroupInfo.Builder tempBuilder = groupInfo.toBuilder();
                tempBuilder.setGroupStates(isClient ? EnumGroupState.Add.getStateID() : EnumGroupState.Norm.getStateID());
                newMajorInfoList.add(tempBuilder.build());

            }

        }


        ////最后server剩下的就是client删除的。
        ////添加到 新list     = 状态delete
        if(isClient){
            for (int i = 0; i < majorInfoServerList.size(); i++) {
                com.msg.UniMajorGroupInfo.Builder tempBuilder = majorInfoServerList.get(i).toBuilder();
                tempBuilder.setGroupStates(EnumGroupState.Del.getStateID());
                newMajorInfoList.add(tempBuilder.build());
            }
        }



        return  newMajorInfoList;
    }

    public static List<com.msg.UniMajorGroupInfo> GetGroupInfoFromWebTemplateProto(com.msg.TemplateDetailInfo formDetailInfo) {

        boolean isClient = false;
        boolean createNewForm = true;// formDetailInfoServer == null;

        List<com.msg.UniMajorGroupInfo> newMajorInfoList = new ArrayList<>();

        List<com.msg.UniMajorGroupInfo> majorInfoWebList = formDetailInfo.getGroupsList();
//        List<com.msg.UniMajorGroupInfo> majorInfoServerList = createNewForm ? new ArrayList<>() : null;//new ArrayList<>(formDetailInfoServer.getGroupsList());

        //遍历网页发送 的 proto信息
        for (int i = 0; i < majorInfoWebList.size(); i++) {
            com.msg.UniMajorGroupInfo groupInfo = majorInfoWebList.get(i);
            if(groupInfo.getMajorsList().size() == 0) continue;

            boolean isExist = false;

//            if(!createNewForm){
//
//                for (int j = majorInfoServerList.size() - 1; j >=0 ; j--) {
//                    com.msg.UniMajorGroupInfo tempInfo = majorInfoServerList.get(j);
//
//                    if(groupInfo.getMajorsList().get(0).getUniMajorCode().equals( tempInfo.getMajorsList().get(0).getUniMajorCode() )){
//                        //有相同的， 保存成不变数据   == 从Server删除
//                        //添加到 新list    状态用server数据
//                        com.msg.UniMajorGroupInfo.Builder tempBuilder = groupInfo.toBuilder();// tempInfo.toBuilder();
//                        tempBuilder.setGroupStates(tempInfo.getGroupStates());
//                        newMajorInfoList.add(tempBuilder.build());
//                        //== 从Server删除
//                        majorInfoServerList.remove(j);
//
//                        isExist = true;
//                        break;
//                    }
//
//
//                }
//            }


            if(!isExist){
                //weib中， 存server没有的， 就是新增；  == 从web删除
                //添加到 新list  = 状态新增
                com.msg.UniMajorGroupInfo.Builder tempBuilder = groupInfo.toBuilder();
                tempBuilder.setGroupStates(isClient ? EnumGroupState.Add.getStateID() : EnumGroupState.Norm.getStateID());
                newMajorInfoList.add(tempBuilder.build());

            }

        }


//        ////最后server剩下的就是client删除的。
//        ////添加到 新list     = 状态delete
//        if(isClient){
//            for (int i = 0; i < majorInfoServerList.size(); i++) {
//                com.msg.UniMajorGroupInfo.Builder tempBuilder = majorInfoServerList.get(i).toBuilder();
//                tempBuilder.setGroupStates(EnumGroupState.Del.getStateID());
//                newMajorInfoList.add(tempBuilder.build());
//            }
//        }



        return  newMajorInfoList;
    }


    public static boolean FormDetailNeedUpdate( Integer dataVersion, String examYear,Integer pici ){
        boolean needUpdate = false;
        if(dataVersion == null){
            //原本没有, 需要更新
            needUpdate = true;
        }else {
            //有版本信息, 需要比较
            LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion(EnumPici.Get(pici), examYear);
            if(latestVerInfoVO != null){
                needUpdate =  dataVersion != (latestVerInfoVO.getDataVersion());
            }else {
                needUpdate = false;
            }
        }
        return needUpdate;
    }

    private static HashMap<Long, UniMajorInfo> tempHistDBMajor = new HashMap<>();  //历史数据
    private static HashMap<Long, UniMajorInfo> tempHistDB2Major = new HashMap<>();  //历史数据
    private static HashMap<String, HashMap<String, UniMajorInfo>> tempLatestDBMajor = new HashMap<>(); //历史 转到 最新数据
    //从db字符  转   GroupProto
     //  groupState@@@@Item5  id,id2,id3   _Item3   _Item3
    // change to
    //groupState@@@@Item5  id,id2,id3 @@@@ templatId, tempid,  _Item3   _Item3
    public static List<com.msg.UniMajorGroupInfo> ConvertUniMajorGroupProtoFromDBStr(String formDetail, Integer pici , HashMap<Long, Long> majorIdDic, Integer dataVersion, String examYear, boolean needUpdate) {

//        boolean needUpdate = false;
//        if(dataVersion == null){
//            //原本没有, 需要更新
//            needUpdate = true;
//        }else {
//            //有版本信息, 需要比较
//            LatestVerInfoVO latestVerInfoVO = UniMajorMgrInMem.GetInstance().FindLatestVersion(EnumPici.Get(pici), examYear);
//            if(latestVerInfoVO != null){
//                needUpdate =  dataVersion != (latestVerInfoVO.getDataVersion());
//            }else {
//                needUpdate = true;
//            }
//        }


//        HashMap<Long, Long> majorIdDic = new HashMap<>(); // 旧major - 新 majorID

        List<List<Long>> groupList = new ArrayList<>();// 按次序保存 majorid
        List<Integer> stateList = new ArrayList<>();// 按次序保存 state
        List<List<Long>> templateList = new ArrayList<>();// 按次序保存 template

        List<Long> majorIds = new ArrayList<>(); //所有的major


        List<com.msg.UniMajorGroupInfo> groupInfos = new ArrayList<>();

        List<String> groupStr = com.baizhou.gameutil.StringParser.SplitString(formDetail, ConstDefine.ItemSperator3);
        for (int i = 0; i < groupStr.size(); i++) {
            String tempStr = groupStr.get(i);

            List<String> groupState = com.baizhou.gameutil.StringParser.SplitString(tempStr, ConstDefine.ItemSperator5);

            //group状态
            Integer state = groupState.size() > 1 ?  Integer.parseInt(groupState.get(0))  : EnumGroupState.Norm.getStateID();

            //
            String groupMajorStr =  groupState.size() > 1 ?  groupState.get(1)  : groupState.get(0);

            List<Long> mjIds = com.baizhou.gameutil.StringParser.SplitLongNumber(groupMajorStr, ConstDefine.ItemSperator7);

            //解析 templateId
            List<Long> templateIds = new ArrayList<>();
            if( groupState.size() > 2 ){
                String tempIdStr =  groupState.get(2);
                templateIds = com.baizhou.gameutil.StringParser.SplitLongNumber(tempIdStr, ConstDefine.ItemSperator7);
            }

            //记录字符串解析信息, 按group 保存
            groupList.add(mjIds);
            templateList.add(templateIds);
            stateList.add(state);

            //记录所有majro
            majorIds.addAll(mjIds);




//            //查询专业组内信息
//            List<List<UniMajorInfo> > temp = UniMajorManager.GetInstance().GetInHisDBToLatestByAllGroup(mjIds, EnumPici.Get(pici), dataVersion, examYear);
//            List<UniMajorInfo> protos = temp;//new ArrayList<>();
//            //生成group proto
//            com.msg.UniMajorGroupInfo.Builder groupBuild = com.msg.UniMajorGroupInfo.newBuilder();
//            groupBuild.addAllMajors(protos);
//            groupBuild.setGroupStates(state);
//            groupBuild.addAllTemplateIds(templateIds);
//
//            groupInfos.add(groupBuild.build());
//
//            //记录新旧 map
//            for (int j = 0; j < mjIds.size(); j++) {
//                majorIdDic.put(mjIds.get(j),  protos.get(j).getId());
//            }
        }


//        //如果版本没有更新, 直接 查询后返回数据 ; 否则  查询更新后的数据
//        if(!needUpdate){
//            ConvertUniMajorGroupProtoFromDBStr_OnNonChange(groupList, stateList, templateList, majorIds, majorIdDic);
//
//        }else {
//            ConvertUniMajorGroupProtoFromDBStr_OnChange(groupList, stateList, templateList, majorIds, majorIdDic);
//
//        }
        //查询专业组内信息
        List<List<UniMajorInfo> > temp = UniMajorManager.GetInstance().GetInHisDBToLatestByAllGroup(majorIds, EnumPici.Get(pici), examYear, groupList, needUpdate, majorIdDic);

        for (int i = 0; i < temp.size(); i++) {

            List<UniMajorInfo> protos = temp.get(i);

            //生成group proto
            com.msg.UniMajorGroupInfo.Builder groupBuild = com.msg.UniMajorGroupInfo.newBuilder();
            groupBuild.addAllMajors(protos);
            groupBuild.setGroupStates(stateList.get(i));
            groupBuild.addAllTemplateIds(templateList.get(i));

            groupInfos.add(groupBuild.build());

//            //记录新旧 map
//            for (int j = 0; j < mjIds.size(); j++) {
//                majorIdDic.put(mjIds.get(j),  protos.get(j).getId());
//            }

        }

        return groupInfos;

    }

    //数据没有更新
    private static void ConvertUniMajorGroupProtoFromDBStr_OnNonChange(List<List<Long>> groupList, List<Integer> stateList,List<List<Long>> templateList, List<Long> majorIds,HashMap<Long, Long> majorIdDic  ){
        //直接 查询 所有  majrid; 按顺序 返回即可


//        //记录新旧 map
//        for (int j = 0; j < mjIds.size(); j++) {
//            majorIdDic.put(mjIds.get(j),  protos.get(j).getId());
//        }

    }

    //数据有更新
    private static void ConvertUniMajorGroupProtoFromDBStr_OnChange(List<List<Long>> groupList, List<Integer> stateList,List<List<Long>> templateList, List<Long> majorIds,HashMap<Long, Long> majorIdDic , EnumPici pici, String examYear,boolean needUpdate){
        //2.  先总结所有unicode =》 查询对应的 majorid     ==》 majorid 按照 旧数据排序。
    }


    //按顺序 返回
    public static List<List<UniMajorInfo>> GetBySameOrder(List<UniMajorInfo> latestProtoList, List<List<Long>> groupList){

        //按unicode 重新整理
        HashMap<String, HashMap<Long, UniMajorInfo>> unicodeDic = new HashMap<>(); // key unicode, value: <histMajorId, VO>
        HashMap<Long, String> histMajorIdDic = new HashMap<>(); //  <histMajorId, VO>
//                    HashMap<String, Integer> unicodeIndexDic = new HashMap<>(); // key: unicode, 在 志愿表中的 index.
        for (int i = 0; i < latestProtoList.size(); i++) {
            UniMajorInfo tempLatestVo = latestProtoList.get(i);
            String tempUniCode = tempLatestVo.getUniMajorCode();
            Long tempMajorHistId = tempLatestVo.getId();

            HashMap<Long, UniMajorInfo> majorIdDic =  unicodeDic.getOrDefault(tempUniCode, null);
            if(majorIdDic == null){
                majorIdDic = new HashMap<>();
                unicodeDic.put(tempUniCode, majorIdDic);
            }

            majorIdDic.put(tempMajorHistId, tempLatestVo);

            histMajorIdDic.put(tempMajorHistId, tempUniCode);
        }


        //unicode 顺序
        List<String> uniCodeOrder = new ArrayList<>();
        HashMap<String, Integer> unicodeIndex = new HashMap<>();
        for (int i = 0; i < groupList.size(); i++) {

            List<Long> tmpMajorlist = groupList.get(i);
            for (int j = 0; j < tmpMajorlist.size(); j++) {
                Long tempMajorId = tmpMajorlist.get(j);

                String uniCode = histMajorIdDic.getOrDefault(tempMajorId, null);
                if(uniCode != null) {
                    uniCodeOrder.add(uniCode);
                    unicodeIndex.put(uniCode, i);
                    break;
                }
            }
        }


        //每个unicode中,  majorid 保持和原来一致.  新的放在最后
        List<List<UniMajorInfo>>  newGroupInfo = new ArrayList<>();
        for (int i = 0; i < uniCodeOrder.size(); i++) {
            String uniCode = uniCodeOrder.get(i);
            List<UniMajorInfo> newIdOrder  = new ArrayList<>();//新顺序

            Integer indxInForm = unicodeIndex.getOrDefault(uniCode, null);
            HashMap<Long, UniMajorInfo> majorIdDic = unicodeDic.getOrDefault(uniCode, null);
            if(indxInForm != null){
                List<Long> originIdOrder = groupList.get(indxInForm); //旧顺序
                if(majorIdDic != null){

                    //按旧顺序 添加 新
                    for (int j = 0; j < originIdOrder.size(); j++) {
                        UniMajorInfo newInfo = majorIdDic.getOrDefault(originIdOrder.get(j), null);
                        if(newInfo != null){
                            newIdOrder.add(newInfo);
                            majorIdDic.remove(originIdOrder.get(j)); //删除已经添加
                        }
                    }

                }

            }

            //添加完后, 剩余的专业, 添加在后面
            if(majorIdDic != null){
                for (Map.Entry<Long, UniMajorInfo> entry: majorIdDic.entrySet()) {
                    newIdOrder.add(entry.getValue());
                }
            }

            newGroupInfo.add(newIdOrder);
        }



        //不需要更新的情况, 直接添加
        List<List<UniMajorInfo> > protoGroupList = new ArrayList<>();
        for (int i = 0; i < newGroupInfo.size(); i++) {
            List<UniMajorInfo>  group = newGroupInfo.get(i);

            List<UniMajorInfo> protoList = new ArrayList<>();
            for (int j = 0; j < group.size(); j++) {
                protoList.add(group.get(j));
            }
            protoGroupList.add(protoList);
        }

        return  protoGroupList;
    }


    //从db字符  转   GroupProto
    //  groupState@@@@Item5  id,id2,id3   _Item3   _Item3
    // change to
    //groupState@@@@Item5  id,id2,id3 @@@@ templatId, tempid,  _Item3   _Item3
    public static List<com.msg.UniMajorGroupInfo> ConvertUniMajorGroupProtoFromDBStrV1Old(String formDetail, Integer pici , HashMap<Long, Long> majorIdDic, Integer dataVersion, String examYear) {

//        HashMap<Long, Long> majorIdDic = new HashMap<>(); // 旧major - 新 majorID

        List<com.msg.UniMajorGroupInfo> groupInfos = new ArrayList<>();

        List<String> groupStr = com.baizhou.gameutil.StringParser.SplitString(formDetail, ConstDefine.ItemSperator3);
        for (int i = 0; i < groupStr.size(); i++) {
            String tempStr = groupStr.get(i);

            List<String> groupState = com.baizhou.gameutil.StringParser.SplitString(tempStr, ConstDefine.ItemSperator5);

            //group状态
            Integer state = groupState.size() > 1 ?  Integer.parseInt(groupState.get(0))  : EnumGroupState.Norm.getStateID();
            //
            String groupMajorStr =  groupState.size() > 1 ?  groupState.get(1)  : groupState.get(0);

//            //解析majorId 和 templateId    ==id|temp1-temp2,id2,id3
//            List<String> mjIdAndTempId = com.baizhou.gameutil.StringParser.SplitString(groupMajorStr, ConstDefine.ItemSperator7);
//
//            List<Long> mjIds = new ArrayList<>();
//            List<Long> templateIds = new ArrayList<>();
//            for (int j = 0; j < mjIdAndTempId.size(); j++) {
//                String temp = mjIdAndTempId.get(j);
//                List<String> list = com.baizhou.gameutil.StringParser.SplitString(temp, ConstDefine.ItemSperator1Split);
//                Long mjId = Long.parseLong(list.get(0));
//                mjIds.add(mjId);
//
//                if(list.size() > 1){
//                    String templateIdStr = list.get(1);
//                    templateIds = com.baizhou.gameutil.StringParser.SplitLongNumber(templateIdStr, ConstDefine.ItemSperator4);
//                }
//            }
            List<Long> mjIds = com.baizhou.gameutil.StringParser.SplitLongNumber(groupMajorStr, ConstDefine.ItemSperator7);

            //解析 templateId
            List<Long> templateIds = new ArrayList<>();
            if( groupState.size() > 2 ){
                String tempIdStr =  groupState.get(2);
                templateIds = com.baizhou.gameutil.StringParser.SplitLongNumber(tempIdStr, ConstDefine.ItemSperator7);
            }

            //查询专业组内信息
//            List<UniMajorInfo> temp = UniMajorManager.GetInstance().GetInHisDB(mjIds, EnumPici.Get(pici));
            List<UniMajorInfo> temp = UniMajorManager.GetInstance().GetInHisDBToLatest(mjIds, EnumPici.Get(pici));


            List<UniMajorInfo> protos = temp;//new ArrayList<>();
//            //按照mjIds排序
//            for (int j = 0; j < mjIds.size(); j++) {
//                Long mjId = mjIds.get(j);
//
//                for (int k = 0; k < temp.size(); k++) {
//                    if (temp.get(k).getId() == mjId) {
//                        protos.add(temp.get(k));
//                        temp.remove(k);
//                        break;
//                    }
//                }
//            }

            //生成group proto
            com.msg.UniMajorGroupInfo.Builder groupBuild = com.msg.UniMajorGroupInfo.newBuilder();
            groupBuild.addAllMajors(protos);
            groupBuild.setGroupStates(state);
            groupBuild.addAllTemplateIds(templateIds);

            groupInfos.add(groupBuild.build());

            //记录新旧 map
            for (int j = 0; j < mjIds.size(); j++) {
                majorIdDic.put(mjIds.get(j),  protos.get(j).getId());
            }
        }

        return groupInfos;

    }

    //
    public static String ConvToDBStr( List<com.msg.UniMajorGroupInfo> ConvertDetailFromProto){
        //专业组信息 state@@@id,id_ state@@@id,id
        //change to
        //groupState@@@@Item5  id|temp1-temp2,id2,id3   _Item3   _Item3
        String detailString = "";
        for (int i = 0; i < ConvertDetailFromProto.size(); i++) {
            com.msg.UniMajorGroupInfo groupInfo = ConvertDetailFromProto.get(i);
            List<UniMajorInfo> majorInfoList = groupInfo.getMajorsList();
            if (majorInfoList.size() == 0) continue;

            if (i > 0) detailString += ConstDefine.ItemSperator3;

            detailString += groupInfo.getGroupStates();
            detailString += ConstDefine.ItemSperator5;

            for (int j = 0; j < majorInfoList.size(); j++) {
                UniMajorInfo majorInfo = majorInfoList.get(j);
                if (j > 0) detailString += ConstDefine.ItemSperator7;
                detailString += majorInfo.getId();
            }

            List<Long> templateIdsList = groupInfo.getTemplateIdsList();
            if (templateIdsList.size() == 0) continue;
            detailString += ConstDefine.ItemSperator5;
            for (int j = 0; j < templateIdsList.size(); j++) {
                Long tempid = templateIdsList.get(j);
                if (j > 0) detailString += ConstDefine.ItemSperator7;
                detailString += tempid;
            }

        }

        return  detailString;
    }

    public static  List<Long> ConvAuthTeachID(String authorTeacherIds, String splitter){
        List<String> templist = StringParser.SplitString(authorTeacherIds, splitter);
        List<Long> templist2 = new ArrayList<>();
        for (int i = 0; i < templist.size(); i++) {
            templist2.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
        }
        return  templist2;
    }



    public static boolean ValidSubject(List<String> userSelect, String majorRequirement){
        boolean validSelecte  = false;//用户选课合格
        if(majorRequirement.equals(ConstDefine.UnLimit))  return  true; //无限制 选课

        String allSubs = "";

//        //查看 单科限制
//        for (int i = 0; i < userSelect.size(); i++) {
//            String tempSubj = userSelect.get(i);
//            if(majorRequirement.equals(tempSubj)) return true; // 单科满足
//
//        }

        //2科目限制
        for (int i = 0; i < userSelect.size(); i++) {

            String tempSubj = userSelect.get(i);

            if(majorRequirement.equals(tempSubj)) return true; // 单科满足

            if(majorRequirement.contains(tempSubj+ "/")) return true; // 1科满足
            if(majorRequirement.contains( "/"+tempSubj)) return true; // 1科满足


            //2个科目要求
            for (int j = i + 1; j < userSelect.size(); j++) {
//                p = cb.or(p, cb.like(expression, "" + sub1 + "+" + examSubj.get(j) + ""));

                if(majorRequirement.contains(tempSubj+ "+" +userSelect.get(j))) return true; // 2科满足
            }

            //3科目限制
            if (i > 0) {
                allSubs += "+";
            }
            allSubs += tempSubj;
        }

        if(majorRequirement.equals(allSubs)) return true;  //3科目限制



        return  false;
    }

    //获取group中所有的major名  "major1, major2"
    public static  String GetAllMajorInGroup(String groupCode, HashMap<String, List<UniMajorInfo>> groupDic){
        String info = "";
        List<UniMajorInfo> majorInfos = groupDic.getOrDefault(groupCode, null);
        if(majorInfos != null){
            for (int i = 0; i < majorInfos.size(); i++) {
                UniMajorInfo major = majorInfos.get(i);
                List<String> majorNames = major.getMajorNameDetailsList();

                for (int j = 0; j < majorNames.size(); j++) {
                    String tempName = majorNames.get(j);
                    if((info + ConstDefine.ItemSperator7).contains(tempName + ConstDefine.ItemSperator7)) continue; //去除重复
                    if(!info.isEmpty()) info += ConstDefine.ItemSperator7;
                    info += tempName;
                }

            }
        }

        return info;

    }


    public static List<Long> GetTemplatListOfTemplateOrForm(boolean useTempId, Long additionId ){
        //先添加 订单/志愿表对应信息
//                boolean isClientUpdate = teacher.getRole() == EnumRoleType.Client.getStateID();
        List<com.msg.UniMajorGroupInfo> cGroupInfos = null;
        if(useTempId){
            //根据指定志愿表id查询
            TemplateInfoVO templateInfoVO = TemplateManager.GetInstance().GetTemplateById(additionId);
            com.msg.TemplateDetailInfo detailInfo = templateInfoVO.ConvToDetail();
            cGroupInfos = detailInfo.getGroupsList();
        }else {
            //根据指定志愿表id查询
            FormInfoVO formInfoVO = FormInfoManager.GetInstance().GetFormInfo(additionId);
            com.msg.FormDetailInfo detailInfo = formInfoVO.ConvToFormDetailForWeb2(false);

            cGroupInfos = detailInfo.getGroupsList();
        }

        //获取所有templateId
        List<Long> incluedTemplates = new ArrayList<>();
        for (int i = 0; i < cGroupInfos.size(); i++) {
            List<Long> temps = cGroupInfos.get(i).getTemplateIdsList();
            for (int j = 0; j < temps.size(); j++) {
                Long tempId = temps.get(j);
                if(incluedTemplates.indexOf(tempId) >=0) continue;
                incluedTemplates.add(tempId);
            }
//            incluedTemplates.addAll(temps);
        }
        return  incluedTemplates;
    }

    public static List<Long> GetNewTemplatList(List<Long> incluTemplate, List<com.msg.TemplateInfo> templateInfos ){
        List<Long> newTemplateList = new ArrayList<>();

        for (int i = 0; i < incluTemplate.size(); i++) {
            Long tempId = incluTemplate.get(i);

            boolean included = false;
            for (int j = 0; j < templateInfos.size(); j++) {
                com.msg.TemplateInfo templateInfo = templateInfos.get(j);
                if(tempId == templateInfo.getId()){
                    included = true;
                    break;
                }
            }
            if(!included){
                newTemplateList.add(tempId);
            }
        }

        return newTemplateList;
    }

    public static List<String> ParseMajroName(List<String> getMajorNamesList){
        //专业名中, 有二类专业的解析出来
        List<String> majarNameList = new ArrayList<>(getMajorNamesList);// req.getMajorNamesList();
        List<String> tempNameList = new ArrayList<>();
        for (int i = majarNameList.size() - 1; i >=0 ; i--) {
            List<String> parseMajors = MajorClsManager.GetInstance().GetMajorNamesByCls2(majarNameList.get(i));
            if(parseMajors.size() > 0) majarNameList.remove(i);
            for (int j = 0; j < parseMajors.size(); j++) {
                tempNameList.add(parseMajors.get(j));
            }
        }
        for (int i = 0; i < tempNameList.size(); i++) {
            majarNameList.add(tempNameList.get(i));
        }

        return majarNameList;
    }


    public static String ReplaceSpecialChar(String origin){
        origin = origin.replace('＋', '+');
        origin = origin.replace('－', '-');

        return origin;
    }



    //解析Order的专业喜好 => proto
    public static List<com.msg.Pref> ConvertDBPrefStringToProto(String favoredMajorGroupList, String unfavoredGroupMajorList,String neutralGroupMajorList) {

//        HashMap<String, List<String>> favDic = ParseDBPrefToDic(favoredMajorGroupList); //解析  喜欢的专业
//        HashMap<String, List<String>> unfavDic = ParseDBPrefToDic(unfavoredGroupMajorList);
//        HashMap<String, List<String>> neutralDic = ParseDBPrefToDic(neutralGroupMajorList);

        //统一添加到 map
        HashMap<String, com.msg.Pref.Builder>  tempPrefDic =ConvertDBPrefStringToDic(favoredMajorGroupList, unfavoredGroupMajorList, neutralGroupMajorList);// AddPrefToMap(favDic, unfavDic, neutralDic);

//        //转成proto
//        List<com.msg.Pref> list = new ArrayList<>();
//        for (Map.Entry<String, com.msg.Pref.Builder> entry : tempPrefDic.entrySet()){
//            list.add(entry.getValue().build());
//        }
//
//        return list;
        return ConvertDBPrefStringToProto(tempPrefDic);
    }

    public static List<com.msg.Pref> ConvertDBPrefStringToProto( HashMap<String, com.msg.Pref.Builder> tempPrefDic) {

//        HashMap<String, List<String>> favDic = ParseDBPrefToDic(favoredMajorGroupList); //解析  喜欢的专业
//        HashMap<String, List<String>> unfavDic = ParseDBPrefToDic(unfavoredGroupMajorList);
//        HashMap<String, List<String>> neutralDic = ParseDBPrefToDic(neutralGroupMajorList);

        //统一添加到 map
//        HashMap<String, com.msg.Pref.Builder>  tempPrefDic =ConvertDBPrefStringToDic(favoredMajorGroupList, unfavoredGroupMajorList, neutralGroupMajorList);// AddPrefToMap(favDic, unfavDic, neutralDic);
        //转成proto
        List<com.msg.Pref> list = new ArrayList<>();
        for (Map.Entry<String, com.msg.Pref.Builder> entry : tempPrefDic.entrySet()){
            list.add(entry.getValue().build());
        }

        return list;
    }

    public static  HashMap<String, com.msg.Pref.Builder>  ConvertDBPrefStringToDic(String favoredMajorGroupList, String unfavoredGroupMajorList,String neutralGroupMajorList){
        HashMap<String, List<String>> favDic = ParseDBPrefToDic(favoredMajorGroupList); //解析  喜欢的专业
        HashMap<String, List<String>> unfavDic = ParseDBPrefToDic(unfavoredGroupMajorList);
        HashMap<String, List<String>> neutralDic = ParseDBPrefToDic(neutralGroupMajorList);

        //统一添加到 map
        HashMap<String, com.msg.Pref.Builder>  tempPrefDic = AddPrefToMap(favDic, unfavDic, neutralDic);

        return tempPrefDic;
    }




    private static HashMap<String, com.msg.Pref.Builder> AddPrefToMap( HashMap<String, List<String>> favDic,
                                      HashMap<String, List<String>> unfavDic ,
                                      HashMap<String, List<String>> neutralDic ){
        HashMap<String, com.msg.Pref.Builder>  tempPrefDic = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : favDic.entrySet()){
            String year = entry.getKey();
            List<String> ids = entry.getValue();

            com.msg.Pref.Builder builder =  tempPrefDic.getOrDefault(year, null);
            if(builder == null){
                builder = com.msg.Pref.newBuilder();
                builder.setExamYear(year);
                builder.addAllFavoredMajorGroupList(new ArrayList<>());
                builder.addAllNeutralGroupMajorList(new ArrayList<>());
                builder.addAllUnfavoredGroupMajorList(new ArrayList<>());
                tempPrefDic.put(year, builder);
            }
            builder.addAllFavoredMajorGroupList(ids);
        }

        for (Map.Entry<String, List<String>> entry : unfavDic.entrySet()){
            String year = entry.getKey();
            List<String> ids = entry.getValue();

            com.msg.Pref.Builder builder =  tempPrefDic.getOrDefault(year, null);
            if(builder == null){
                builder = com.msg.Pref.newBuilder();
                builder.setExamYear(year);
                builder.addAllFavoredMajorGroupList(new ArrayList<>());
                builder.addAllNeutralGroupMajorList(new ArrayList<>());
                builder.addAllUnfavoredGroupMajorList(new ArrayList<>());
                tempPrefDic.put(year, builder);
            }
            builder.addAllUnfavoredGroupMajorList(ids);
        }

        for (Map.Entry<String, List<String>> entry : neutralDic.entrySet()){
            String year = entry.getKey();
            List<String> ids = entry.getValue();

            com.msg.Pref.Builder builder =  tempPrefDic.getOrDefault(year, null);
            if(builder == null){
                builder = com.msg.Pref.newBuilder();
                builder.setExamYear(year);
                builder.addAllFavoredMajorGroupList(new ArrayList<>());
                builder.addAllNeutralGroupMajorList(new ArrayList<>());
                builder.addAllUnfavoredGroupMajorList(new ArrayList<>());
                tempPrefDic.put(year, builder);
            }
            builder.addAllNeutralGroupMajorList(ids);
        }

        return tempPrefDic;
    }

    private static HashMap<String, List<String>> ParseDBPrefToDic(String favoredMajorGroupList){
        HashMap<String, List<String>> favDic = new HashMap<>();

        List<String>  tempYearsFav = com.baizhou.gameutil.StringParser.ParsePicUrl(favoredMajorGroupList);
        for (int i = 0; i < tempYearsFav.size(); i++) {
            String temp = tempYearsFav.get(i);
            List<String> tempItems = ParseDBPrefItem(temp);

            Integer lastIndx = tempItems.size() - 1;
            String year = tempItems.get(lastIndx);
            tempItems.remove(lastIndx);
            //记录各个年份
            favDic.put(year, tempItems);
        }

        return favDic;
    }

    //year_id,id
    private static List<String> ParseDBPrefItem(String temp){
        String year = "";
        String idsStr = "";

        List<String> temps = StringParser.SplitString(temp, ConstDefine.ItemSperator3);
        if(temps.size() == 2){
            //新版本
            year = temps.get(0);
            idsStr = temps.get(1);
        }else {
            //旧版本, 默认2023
            year = "2023";
            idsStr = temps.get(0);
        }

        List<String> idList =  StringParser.SplitString(idsStr, ConstDefine.ItemSperator7);

        idList.add(year);

        return idList;
    }


    //转proto 到 string:   year_id,id|year_id,id
    public static List<String> ConvPrefProtoToDBStr( List<com.msg.Pref> protoList){

        List<String> list = new ArrayList<>();

        //专业组信息 state@@@id,id_ state@@@id,id
        //change to
        //groupState@@@@Item5  id|temp1-temp2,id2,id3   _Item3   _Item3
        String favString = "";
        String unfavString = "";
        String neutralString = "";
        for (int i = 0; i < protoList.size(); i++) {
            com.msg.Pref temp = protoList.get(i);

            String year = temp.getExamYear();

            //
            List<String> favList = temp.getFavoredMajorGroupListList();
            if(!StringUtils.isEmpty(favString)){
                favString += ConstDefine.ItemSperator1;
            }
            favString += year + ConstDefine.ItemSperator3 + StringParser.MakeString(favList, ConstDefine.ItemSperator7);

            //
            List<String> unfavList = temp.getUnfavoredGroupMajorListList();
            if(!StringUtils.isEmpty(unfavString)){
                unfavString += ConstDefine.ItemSperator1;
            }
            unfavString += year + ConstDefine.ItemSperator3 + StringParser.MakeString(unfavList, ConstDefine.ItemSperator7);

            //
            List<String> neutralList = temp.getNeutralGroupMajorListList();
            if(!StringUtils.isEmpty(neutralString)){
                neutralString += ConstDefine.ItemSperator1;
            }
            neutralString += year + ConstDefine.ItemSperator3 + StringParser.MakeString(neutralList, ConstDefine.ItemSperator7);
        }


        list.add(favString);
        list.add(unfavString);
        list.add(neutralString);

        return  list;
    }


    public static List<String> ConvertProtoString(List<String> strings){

        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
                list.add(strings.get(i));
        }

        return list;
    }


    public static EnumPici GetPiciBySheetId(Integer sheetIdx){
        EnumPici pici = EnumPici.A;
        if(sheetIdx == 0){
//            System.out.println("开始解析提前批A");
            pici = EnumPici.PreA;
        }else  if(sheetIdx == 1){
//            System.out.println("开始解析提前批B");
            pici = EnumPici.PreB;
        }else  if(sheetIdx == 2){
//            System.out.println("开始解析本A");
            pici = EnumPici.A;
        }else  if(sheetIdx == 3){
//            System.out.println("开始解析本B");
            pici = EnumPici.B;
        }else  if(sheetIdx == 4){

        }else  if(sheetIdx == 5){
//            System.out.println("开始解析专科");
            pici = EnumPici.ZhuanKe;
        }else  if(sheetIdx == 6){
//            System.out.println("开始解析征询本A批次");
            pici = EnumPici.ZXA;
        }else  if(sheetIdx == 7){
//            System.out.println("开始解析征询本B批次");
            pici = EnumPici.ZXB;
        }

        return pici;
    }

    //添加体检限报信息
    public static void AddPhysicLimit( List<Long> majorIds, com.msg.UniMajorInfo.Builder info){
        List<String> physicLimitName = new ArrayList<>();
        List<Integer> physicLimitColor = new ArrayList<>();
        for (int i = 0; i < majorIds.size(); i++) {
            Long majorId = majorIds.get(i);
            //旧版本 只显示一个 专业限制
//            Integer color = MajorClsManager.GetInstance().GetPhysicLimitColorByMajrName(majorId);
//            if(color != null){
//                physicLimitName.add(MajorClsManager.GetInstance().GetMajorVOById(majorId).getMajor3rdCls());
//                physicLimitColor.add(color);
//            }

            List<Integer> color = MajorClsManager.GetInstance().GetPhysicLimitColorListByMajorID(majorId);
            if(color != null){
                String majorName = MajorClsManager.GetInstance().GetMajorVOById(majorId).getMajor3rdCls();
                for (int j = 0; j < color.size(); j++) {
                    physicLimitName.add(majorName);
                    physicLimitColor.add(color.get(j));
                }

            }

        }

        info.addAllLimitedMajorNames(physicLimitName);
        info.addAllLimitedMajorColor(physicLimitColor);
    }

    //获取group中所有的  最低的专业位次
    public static Integer GetLowestMajorRankInGroup(String groupCode, HashMap<String, List<UniMajorInfo>> groupDic){
        Integer lowRank = 0;
        List<UniMajorInfo> majorInfos = groupDic.getOrDefault(groupCode, null);
        if(majorInfos != null){
            for (int i = 0; i < majorInfos.size(); i++) {
                UniMajorInfo major = majorInfos.get(i);
                Integer tempRank = Integer.parseInt(major.getMajor22LowRank());

                if(tempRank > lowRank){
                    lowRank = tempRank;
                }

            }
        }

        return lowRank;

    }

    //获取group中所有的  最低的专业组位次
    public static Integer GetLowestGroupRankInGroup(String groupCode, HashMap<String, List<UniMajorInfo>> groupDic){
        Integer lowRank = 0;
        List<UniMajorInfo> majorInfos = groupDic.getOrDefault(groupCode, null);
        if(majorInfos != null){
            for (int i = 0; i < majorInfos.size(); i++) {
                UniMajorInfo major = majorInfos.get(i);
                Integer tempRank = Integer.parseInt(major.getRankFor22());

                if(tempRank > lowRank){
                    lowRank = tempRank;
                }

            }
        }

        return lowRank;

    }


    /********************************************************************************************/
    public static List<String> GetUniqueUniMajorCodeA(List<UniMajorInfoALatestVO> list, int startIndx, int endIndx) {
//        list.sort(new Comparator<UniMajorInfoALatestVO>() {
//            @Override
//            public int compare(UniMajorInfoALatestVO o1, UniMajorInfoALatestVO o2) {
//                return o1.getUniMajorCode().compareTo(o2.getUniMajorCode());
//            }
//        });

        String lastUniMajorCode = "";

        List<String> uniMajorCodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UniMajorInfoALatestVO tempUniMajor = list.get(i);
            String tempUniMajorCode = tempUniMajor.getUniMajorCode();

            if (lastUniMajorCode.equals(tempUniMajorCode)) continue;
            lastUniMajorCode = tempUniMajorCode;
            uniMajorCodes.add(lastUniMajorCode);
        }

        return uniMajorCodes;
    }

    /********************************************************************************************/


    /*****************************************************************************/
    //重要沟通记录解析

    //转成proto
    public static List<com.msg.importantRecord> ParseImportantRecToProto(String scoreHist) {
        List<com.msg.importantRecord> list = new ArrayList<>();

        List<String> hists = StringParser.SplitString(scoreHist, ConstDefine.ItemSperator5);
        for (int i = 0; i < hists.size(); i++) {
            String hist = hists.get(i);
            list.add(ParseImportRecItemToProto(hist));
        }

        return list;
    }
    //转成proto
    private static com.msg.importantRecord ParseImportRecItemToProto(String hist) {

        List<String> items = StringParser.SplitString(hist, ConstDefine.ItemSperator6, true);
//        if (items.size() == 0) {
//            // System.out.println("eror");
//        }
        com.msg.importantRecord.Builder builder = com.msg.importantRecord.newBuilder();
        builder.setRecDate(items.size() <= 0 ? 0 : Long.parseLong(items.get(0)));
        builder.setCommType(items.size() <= 1 ? 1 : Integer.parseInt(items.get(1)) );
        builder.setCommTarget(items.size() <= 2 ? 1 : Integer.parseInt(items.get(2)));
        builder.setCommTargetOthers(items.size() <= 3 ? "" : items.get(3));
        builder.setContent(items.size() <= 4 ? "" : items.get(4));
        builder.setMajorAndUni(items.size() <= 5 ? "" : items.get(5));
        builder.setBackground(items.size() <= 6 ? "" : items.get(6));
        builder.setNxDate(items.size() <= 7 ? 0 : Long.parseLong(items.get(7)));
//        builder.setGeology(items.size() <= 8 ? "0" : items.get(8));
//        builder.setBiology(items.size() <= 9 ? "0" : items.get(9));
//        builder.setTotalScore(items.size() <= 10 ? "0" : items.get(10));
//        builder.setClsRank(items.size() <= 11 ? "0" : items.get(11));
//        builder.setSchoolRank(items.size() <= 12 ? "0" : items.get(12));
//        builder.setDistRank(items.size() <= 13 ? "0" : items.get(13));
//        builder.setTargetScore(items.size() <= 14 ? "0" : items.get(14));

        return builder.build();
    }


    //转成字符串
    public static String ParseImportantRecFromProto(List<com.msg.importantRecord> protoList) {

        String str = "";
        for (int i = 0; i < protoList.size(); i++) {
            com.msg.importantRecord item = protoList.get(i);
            str += ParseImportantRecItemFromProto(item);
            if (i < protoList.size() - 1) {
                str += ConstDefine.ItemSperator5;
            }

        }

        return str;
    }

    //
    private static String ParseImportantRecItemFromProto(com.msg.importantRecord item) {

        String str = "";
//            scoreHistItem item = protoList.get(i);
        str += item.getRecDate();
        str += ConstDefine.ItemSperator6;
        str += item.getCommType();
        str += ConstDefine.ItemSperator6;
        str += item.getCommTarget();
        str += ConstDefine.ItemSperator6;
        str += item.getCommTargetOthers();
        str += ConstDefine.ItemSperator6;
        str += item.getContent();
        str += ConstDefine.ItemSperator6;
        str += item.getMajorAndUni();
        str += ConstDefine.ItemSperator6;
        str += item.getBackground();
        str += ConstDefine.ItemSperator6;
        str += item.getNxDate();
        str += ConstDefine.ItemSperator6;
//        str += item.getGeology();
//        str += ConstDefine.ItemSperator6;
//        str += item.getBiology();
//        str += ConstDefine.ItemSperator6;
//        str += item.getTotalScore();
//        str += ConstDefine.ItemSperator6;
//        str += item.getClsRank();
//        str += ConstDefine.ItemSperator6;
//        str += item.getSchoolRank();
//        str += ConstDefine.ItemSperator6;
//        str += item.getDistRank();
//        str += ConstDefine.ItemSperator6;
//        str += item.getTargetScore();
//
////            if (i < protoList.size() - 1) {
////                str += ConstDefine.ItemSperator6;
////            }

//        }

        return str;
    }


    /*****************************************************************************/


    //解析数据  男：665 女：680  ;; 21148/9671
    public static List<Integer> SplitBoyAndGirlData(String cellData){

        List<Integer> retList = new ArrayList<>();

        //
        cellData = cellData.trim();
        cellData = cellData.replace('／', '/');
        cellData = cellData.replace('：', ':');


        if(cellData.contains("/")){
            retList = StringParser.SplitNumber(cellData, "/");
            if(retList.size() < 1) retList.add(0); //保证一定有2个数据
            if(retList.size() < 2) retList.add(0); //保证一定有2个数据

        }else if(cellData.contains("女")){
            List<String> tempStrs = StringParser.SplitString(cellData, "女");
            for (int i = 0; i < tempStrs.size(); i++) {
                String tempStr = tempStrs.get(i);
                retList.add((int) Float.parseFloat(ExtractDigit(tempStr)));
            }

            if(retList.size() < 1) retList.add(0); //保证一定有2个数据
            if(retList.size() < 2) retList.add(0); //保证一定有2个数据


        }else {
            //正常的单数据
            if(StringUtils.isEmpty(cellData) || !CommonUtil.isNumeric(cellData)){
                cellData = "0";//ExtractDigit(cellData);
            }
            retList.add((int) Float.parseFloat(cellData));
        }



        return retList;

    }

    public static String ExtractDigit(String data){

        String temp = "";
        for (int i = 0; i < data.length(); i++) {
//            System.out.println(str.charAt(i));
            char tempChar = data.charAt(i);
            if (tempChar == '.') {
                temp += tempChar;
                continue;
            }
            if (Character.isDigit(tempChar)) {
                temp += tempChar;
            }
        }

        if(StringUtils.isEmpty(temp)) return  "0";
        if(temp.length() == 1 && temp.equals(".")) return "0";
        return temp;

    }

    //专业名中, 有二类专业的解析出来
    public static List<String> GetMajorNameByCls2(List<String> reqMajorNamesList){
        List<String> majarNameList = new ArrayList<>(reqMajorNamesList);// req.getMajorNamesList();
        List<String> tempNameList = new ArrayList<>();
        for (int i = majarNameList.size() - 1; i >=0 ; i--) {
            List<String> parseMajors = MajorClsManager.GetInstance().GetMajorNamesByCls2(majarNameList.get(i));
            if(parseMajors.size() > 0) majarNameList.remove(i);
            for (int j = 0; j < parseMajors.size(); j++) {
                tempNameList.add(parseMajors.get(j));
            }
        }
        for (int i = 0; i < tempNameList.size(); i++) {
            majarNameList.add(tempNameList.get(i));
        }

        return majarNameList;
    }

    ////////////////////////////////////////////////////////////////
    public static List<Long> GetMajorIDsFromDetail(String formDetail) {


        List<Long> majors = new ArrayList<>();

        List<String> groupStr = com.baizhou.gameutil.StringParser.SplitString(formDetail, ConstDefine.ItemSperator3);
        for (int i = 0; i < groupStr.size(); i++) {
            String tempStr = groupStr.get(i);

            List<String> groupState = com.baizhou.gameutil.StringParser.SplitString(tempStr, ConstDefine.ItemSperator5);

            //group状态
//            Integer state = groupState.size() > 1 ?  Integer.parseInt(groupState.get(0))  : EnumGroupState.Norm.getStateID();
            //
            String groupMajorStr =  groupState.size() > 1 ?  groupState.get(1)  : groupState.get(0);


            List<Long> mjIds = com.baizhou.gameutil.StringParser.SplitLongNumber(groupMajorStr, ConstDefine.ItemSperator7);

            majors.addAll(mjIds);
        }

        return majors;

    }


}
