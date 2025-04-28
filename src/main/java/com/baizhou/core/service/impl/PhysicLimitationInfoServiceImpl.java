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
public class PhysicLimitationInfoServiceImpl  implements PhysicLimitationInfoService {


    @Autowired
    private PhysicLimitationInfoRepo imp;
    /**
     * 保存
     * @param physicLimitationInfo
     * @return
     */
    @Override
    public PhysicLimitationInfo savePhysicLimitationInfo(PhysicLimitationInfoVO physicLimitationInfo) {

        return imp.save(BeanMapper.map(physicLimitationInfo, PhysicLimitationInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        PhysicLimitationInfo entity = imp.findById(id).orElse(null);
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
     * @param id
     * @return
     */
    @Override
    public PhysicLimitationInfoVO findOneById(Long id) {
        PhysicLimitationInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, PhysicLimitationInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<PhysicLimitationInfoVO> getAllPhysicLimitationInfos() {
        return BeanMapper.mapList(imp.findAll(), PhysicLimitationInfoVO.class);
    }
    /**
     * 更新记录
     * @param physicLimitationInfoVO
     * @return
     */
    @Override
    public String updatePhysicLimitationInfo(PhysicLimitationInfoVO physicLimitationInfoVO) {
        PhysicLimitationInfo physicLimitationInfo = BeanMapper.map(physicLimitationInfoVO, PhysicLimitationInfo.class);
        imp.save(physicLimitationInfo);
        return "success";
    }

     /**
     *  通过Title2的like查询
     * @param title2
     * @return
     */
    @Override
  public List<PhysicLimitationInfoVO> findByTitle2(String title2){
        List<PhysicLimitationInfoVO> voList = new ArrayList<>();
        List<PhysicLimitationInfo> inList = imp.findByTitle2(title2);
        if(  inList != null){
            for (PhysicLimitationInfo inf:inList) {
                voList.add(BeanMapper.map(inf,PhysicLimitationInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Title2的like查询
     * @param title2
     * @return
     */
    @Override
  public List<PhysicLimitationInfoVO> findByTitle2Like(String title2){
        List<PhysicLimitationInfoVO> voList = new ArrayList<>();
        List<PhysicLimitationInfo> inList = imp.findByTitle2Like("%"+title2+"%");
        if(  inList != null){
            for (PhysicLimitationInfo inf:inList) {
                voList.add(BeanMapper.map(inf,PhysicLimitationInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Type的like查询
     * @param type
     * @return
     */
    @Override
  public List<PhysicLimitationInfoVO> findByType(String type){
        List<PhysicLimitationInfoVO> voList = new ArrayList<>();
        List<PhysicLimitationInfo> inList = imp.findByType(type);
        if(  inList != null){
            for (PhysicLimitationInfo inf:inList) {
                voList.add(BeanMapper.map(inf,PhysicLimitationInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Type的like查询
     * @param type
     * @return
     */
    @Override
  public List<PhysicLimitationInfoVO> findByTypeLike(String type){
        List<PhysicLimitationInfoVO> voList = new ArrayList<>();
        List<PhysicLimitationInfo> inList = imp.findByTypeLike("%"+type+"%");
        if(  inList != null){
            for (PhysicLimitationInfo inf:inList) {
                voList.add(BeanMapper.map(inf,PhysicLimitationInfoVO.class));
            }
        }
        return  voList;
  }

    /**
     * 查询页面信息
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listbyPage(Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<PhysicLimitationInfo> pageResult = null;
        if (true) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<PhysicLimitationInfo> spec = (Specification<PhysicLimitationInfo>) (root, query, cb) -> {
                    
                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<PhysicLimitationInfoVO> list = BeanMapper.mapList(pageResult.getContent(), PhysicLimitationInfoVO.class);
        List<com.msg.PhysicLimitationInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO().build());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }
}
