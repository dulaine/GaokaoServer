package com.baizhou.manager;

//import com.msg.Template;/

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.db.DBProxy;
import com.baizhou.util.GameUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.net.PortUnreachableException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager instance;

    public static PlayerManager GetInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }


    /*************************************************************************************************************
     * 玩家基础信息
     */
//    private Hashtable<Long, UsersResVO> m_UserTableDic = new Hashtable<>(); //以id为key的字典
    private Hashtable<String, UsersResVO> m_UserNameTableDic = new Hashtable<>();//以accountName为key的字典
    private Hashtable<String, UsersResVO> m_UserTokenTableDic = new Hashtable<>();//以Token为key的字典


    //登录初始化, 获取所有用户信息
    public void InitAllUserInfo() {
        List<UsersResVO> allUsersRes = DBProxy.Getinstance().UsersResService.getAllUsersRess();
        for (int i = 0; i < allUsersRes.size(); i++) {
//            this.RecordUserInfo(allUsersRes.get(i), false);
        }
    }

    public UsersResVO GetPlayerById(Long id) {

        //1. 从内存中获取
        UsersResVO usersVO = null;//m_UserNameTableDic.getOrDefault(tel, null);

        if (usersVO == null) {
            //2. 从db 获取
            usersVO = DBProxy.Getinstance().UsersResService.findOneById(id);
//            if (player == null || player.size() == 0) return null;
//            usersVO = player.get(0);
//            this.RecordUserInfo(usersVO, true);
        }


        return usersVO;
    }

    public UsersResVO GetPlayerByTel(String tel) {

        //1. 从内存中获取
        UsersResVO usersVO = m_UserNameTableDic.getOrDefault(tel, null);

        if (usersVO == null) {
            //2. 从db 获取
            List<UsersResVO> player = DBProxy.Getinstance().UsersResService.findByTelAndIsDelete(tel, EnumDeleteStatus.UnDeleted.getStateID());
            if (player == null || player.size() == 0) return null;
            usersVO = player.get(0);
            this.RecordUserInfo(usersVO, true);
        }


        return usersVO;
    }

    //根据用户名获取信息
    public UsersResVO GetPlayerByToken(String token) {
        UsersResVO usersVO = m_UserTokenTableDic.getOrDefault(token, null);
        if (usersVO == null) {
            List<UsersResVO> list = DBProxy.Getinstance().UsersResService.findByToken(token);
            if (list == null || list.size() == 0) return null;
            usersVO = list.get(0);
            this.RecordUserInfo(usersVO, true);
        }
        return usersVO;
    }

    public Map<String, Object> GetPlayerByPage(Long teacherId, String tel, String name, Integer role, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        if(role != null && role < 0) role = null;
        boolean isSuperAdmin  = false;
        if(teacherId != null){
            isSuperAdmin = GameUtil.IsSuperAdim(GetPlayerById(teacherId));
        }
        Map<String, Object> map = DBProxy.Getinstance().UsersResService.listbyPage( tel,  name,  role,isSuperAdmin ? null : EnumDeleteStatus.UnDeleted.getStateID(), null, pageable);
        return map;
    }

    /**
     * 创建管理员
     *
     * @param playerName
     * @param pwd
     * @return
     */
    public UsersResVO CreateAdminPlayer(String playerName, String pwd) {
//        com.msg.UsersRes.Builder builder = com.msg.UsersRes.newBuilder();
//        builder.setId(0);
//        builder.setName(playerName);
//        builder.setTel(playerName);
//        builder.setPwd(pwd);
//        builder.setRole(EnumRoleType.Super.getStateID());
//        return CreatePlayer(builder.build());
        return  CreatePlayerByNameAndPwd("super admin",playerName, pwd, EnumRoleType.Super, null);
    }

    public UsersResVO CreatePlayerByNameAndPwd(String playerName, String tel, String pwd, EnumRoleType roleType, Date tempDate) {
        com.msg.UsersRes.Builder builder = com.msg.UsersRes.newBuilder();
        builder.setId(0);
        builder.setName(playerName);
        builder.setTel(tel);
        builder.setPwd(pwd);
        builder.setRole(roleType.getStateID());
        return CreatePlayer(builder.build(), tempDate);
    }

    public UsersResVO CreatePlayer(com.msg.UsersRes usersRes, Date tempDate) {

        //检测tel是否重复
        UsersResVO exist = GetPlayerByTel(usersRes.getTel());
        if (exist != null) return null;

        UsersResVO player = new UsersResVO();
        player.setRole(usersRes.getRole());
        player.setTel(usersRes.getTel());
        player.setPwd(usersRes.getPwd());
        player.setName(usersRes.getName());

        player = RefreshLoginToken(player);
        player.setIsDelete(EnumDeleteStatus.UnDeleted.getStateID());

        if(player.getRole() == EnumRoleType.Client.getStateID()){
            Date date = new Date();
            Date day2Date = tempDate == null ? new Date(date.getYear(), 9-1, 20) : tempDate;
            player.setUserExpireDate(day2Date);
        }

        long id = DBProxy.Getinstance().UsersResService.saveUsersRes(player).getId();
        player.setId(id);
        return player;
    }

    //记录用户信息
    public void RecordUserInfo(UsersResVO usersVO, boolean refreshToken) {
        if (refreshToken) usersVO = this.UpdateTokenTable(usersVO);
//        m_UserTableDic.put(usersVO.getId(), usersVO);
        m_UserNameTableDic.put(usersVO.getTel(), usersVO);
        m_UserTokenTableDic.put(usersVO.getToken(), usersVO);
        DBProxy.Getinstance().UsersResService.saveUsersRes(usersVO);
    }

    //
    //更新玩家信息Token
    private UsersResVO UpdateTokenTable(UsersResVO playerVo) {
        //删除以前的token字典
        String oldToken = playerVo.getToken();
        if (oldToken != null && !oldToken.isEmpty()) m_UserTokenTableDic.remove(oldToken);

        //记录新的token
        playerVo = RefreshLoginToken(playerVo);

        m_UserTokenTableDic.put(playerVo.getToken(), playerVo);
        return playerVo;
    }

    //刷新login token
    private UsersResVO RefreshLoginToken(UsersResVO playerVo) {

        //记录新的token
        playerVo.setToken(CommonUtil.GenLoginToken());

        Date now = new Date();
        now.setTime(now.getTime() + ConstDefine.TokenExpireTime);
        playerVo.setTokenExpireDate(now);

        return playerVo;
    }


    //
//    //记录用户信息
//    private void RecordUserInfo(UsersResVO usersVO, boolean refreshToken) {
//        if (refreshToken) this.RefreshLoginToken(usersVO);
//        m_UserTableDic.put(usersVO.getId(), usersVO);
//        m_UserNameTableDic.put(usersVO.getOpenId(), usersVO);
////        m_UserTokenTableDic.put(usersVO.getToken(), usersVO);
//    }
//
    //更新玩家基础信息
    public UsersResVO UpdatePlayerInfo(com.msg.UsersRes usersRes) {
//        if (refreshToken) this.RefreshLoginToken(usersVO);
//        m_UserTableDic.put(usersVO.getId(), usersVO);
        UsersResVO vo = GetPlayerByTel(usersRes.getTel());
        if(vo != null){
            vo.UpdateByProto(usersRes);
            RecordUserInfo(vo, true);
        }

        return vo;
//        m_UserNameTableDic.put(usersVO.getTel(), usersVO);
//        m_UserTokenTableDic.put(usersVO.getToken(), usersVO);
//        DBProxy.Getinstance().UsersResService.saveUsersRes(usersVO);
    }

    public UsersResVO UpdatePlayerExamYear(UsersResVO player, String examYear){
        player.setExamYear(examYear);
        RecordUserInfo(player, true);
        return player;
    }

//
//
//    //根据用户id获取信息 本地玩家id
//    public UsersResVO GetUserTableInfo(Long playerId) {
//        UsersResVO usersVO = m_UserTableDic.getOrDefault(playerId, null);
//        if (usersVO == null) {
//            usersVO = DBProxy.Getinstance().UsersResService.findOneById(playerId);
//            if (usersVO == null) return null;
//            this.RecordUserInfo(usersVO, false);
//        }
//        return usersVO;
//    }
//

    //    //根据用户名获取信息
//    public UsersResVO GetUserTableInfoByToken(String token) {
//        UsersResVO usersVO = m_UserTokenTableDic.getOrDefault(token, null);
//        if (usersVO == null) {
//            List<UsersResVO> list = DBProxy.Getinstance().UsersResService.findByToken(token);
//            if (list == null || list.size() == 0) return null;
//            usersVO = list.get(0);
//            this.RecordUserInfo(usersVO, false);
//        }
//        return usersVO;
//    }
//
//
    //删除用户信息
    public UsersResVO DeleteUserInfo(com.msg.UsersRes usersRes) {
        //删除内存中的用户信息
        UsersResVO usersVO = GetPlayerByTel(usersRes.getTel());
        if (usersVO != null) {
//            m_UserTableDic.remove(usersVO.getId());
            m_UserNameTableDic.remove(usersVO.getTel());
            m_UserTokenTableDic.remove(usersVO.getToken());
        }

        usersVO.setIsDelete(EnumDeleteStatus.Deleted.getStateID());
        RecordUserInfo(usersVO, false);
        return usersVO;
//        //删除数据库
//        DBProxy.Getinstance().UsersResService.deleteByKeyId(usersVO.getId());
    }
//
//
//    public Map<String, Object> ListUser(String account, List<Integer> roles, int page, int limit) {
//        Pageable pageable = PageRequest.of(page - 1, limit);
//        Map<String, Object> map = DBProxy.Getinstance().UsersResService.listbyPage(account, roles, pageable);
//        return map;
//    }
//
//    //更新用户的Token
//    public boolean UpdateAuthToken(UsersResVO usersResVO) {
//        if (ConstDefine.InTestMode) return true;
//        JSONObject accessTokenJsonObj = GLinkUtil.GetAccessToken(true, "", usersResVO.getAuthRefreshToken());
//        String error = accessTokenJsonObj.getString("error");
//        if (error == null) {
//            String access_token = accessTokenJsonObj.getString("access_token");
//            String refresh_token = accessTokenJsonObj.getString("refresh_token");
//            String id_token = accessTokenJsonObj.getString("id_token");
//            Long expireSec = accessTokenJsonObj.getLong("expires_in");
//
////            usersResVO.setAuthAccessToken(access_token);
////            usersResVO.setAuthRefreshToken(refresh_token);
////            Date now = new Date();
////            usersResVO.setAuthExpiresInDate(new Date(now.getTime() + (expireSec - 360) * 1000));
////            UpdateUserTableInfo(usersResVO, false);
//            ResetAuthToken(usersResVO, access_token, refresh_token, id_token, expireSec);
//            return true;
//        } else {
//            String error_description = accessTokenJsonObj.getString("error_description");
//            System.out.println("UpdateAuthToken error " + error);
//            System.out.println("UpdateAuthToken error des" + error_description);
//
//            return false;
//        }
//
//
//    }
//
//    public void ResetAuthToken(UsersResVO usersResVO, String access_token, String refresh_token, String id_token, Long expireSec) {
//        usersResVO.setAuthAccessToken(access_token);
//        usersResVO.setAuthRefreshToken(refresh_token);
//        Date now = new Date();
//        usersResVO.setAuthExpiresInDate(new Date(now.getTime() + (expireSec - 360) * 1000));
//        usersResVO.setAuthIdToken(id_token);
//        UpdateUserTableInfo(usersResVO, false);
//    }

    /**************************************************************************************************************/


}
