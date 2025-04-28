package com.baizhou.core.service.impl;

import com.baizhou.core.dao.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.service.*;
import com.baizhou.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;
import java.util.*;


@Service
public class AdminUserInfoServiceImpl  implements AdminUserInfoService {


    @Autowired
    private AdminUserInfoRepo imp;
    /**
     * 保存
     * @param adminUserInfo
     * @return
     */
    @Override
    public AdminUserInfo saveAdminUserInfo(AdminUserInfoVO adminUserInfo) {

        return imp.save(BeanMapper.map(adminUserInfo, AdminUserInfo.class));
    }
    /**
     * 删除单条记录
     * @param userId
     * @return
     */
    @Override
    public boolean deleteByKeyUserId(String userId) {
        AdminUserInfo entity = imp.findByUserId(userId).orElse(null);
        if(entity == null) return false;
        imp.delete(entity);
        return true ;
    }
    /**
     * 删除所有记录
     * @return
     */
    @Override
    public void deleteAll() {
        imp.deleteAll();
    }

    /**
     * 查询单条记录
     * @param userId
     * @return
     */
    @Override
    public AdminUserInfoVO findOneByUserId(String userId) {
        AdminUserInfo entity = imp.findByUserId(userId).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, AdminUserInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<AdminUserInfoVO> getAllAdminUserInfos() {
        return BeanMapper.mapList(imp.findAll(), AdminUserInfoVO.class);
    }
    /**
     * 更新记录
     * @param adminUserInfoVO
     * @return
     */
    @Override
    public String updateAdminUserInfo(AdminUserInfoVO adminUserInfoVO) {
        AdminUserInfo adminUserInfo = BeanMapper.map(adminUserInfoVO, AdminUserInfo.class);
        imp.save(adminUserInfo);
        return "success";
    }

    /**
     * 查询页面信息
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listbyPage(String userId,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<AdminUserInfo> pageResult = null;
        if (StringUtils.isEmpty(userId) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<AdminUserInfo> spec = (Specification<AdminUserInfo>) (root, query, cb) -> {
                    
                if (!StringUtils.isEmpty(userId)) {
                   String[] userIdSplits = userId.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < userIdSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("userId"), "%" + userIdSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("userId"), "%" + userIdSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<AdminUserInfoVO> list = BeanMapper.mapList(pageResult.getContent(), AdminUserInfoVO.class);
        List<AdminUserInfoVO> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }
}
