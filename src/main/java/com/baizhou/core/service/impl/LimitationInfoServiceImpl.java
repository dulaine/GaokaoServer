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
public class LimitationInfoServiceImpl  implements LimitationInfoService {


    @Autowired
    private LimitationInfoRepo imp;
    /**
     * 保存
     * @param limitationInfo
     * @return
     */
    @Override
    public LimitationInfo saveLimitationInfo(LimitationInfoVO limitationInfo) {

        return imp.save(BeanMapper.map(limitationInfo, LimitationInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        LimitationInfo entity = imp.findById(id).orElse(null);
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
    public LimitationInfoVO findOneById(Long id) {
        LimitationInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, LimitationInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<LimitationInfoVO> getAllLimitationInfos() {
        return BeanMapper.mapList(imp.findAll(), LimitationInfoVO.class);
    }
    /**
     * 更新记录
     * @param limitationInfoVO
     * @return
     */
    @Override
    public String updateLimitationInfo(LimitationInfoVO limitationInfoVO) {
        LimitationInfo limitationInfo = BeanMapper.map(limitationInfoVO, LimitationInfo.class);
        imp.save(limitationInfo);
        return "success";
    }

     /**
     *  通过Content的like查询
     * @param content
     * @return
     */
    @Override
  public List<LimitationInfoVO> findByContent(String content){
        List<LimitationInfoVO> voList = new ArrayList<>();
        List<LimitationInfo> inList = imp.findByContent(content);
        if(  inList != null){
            for (LimitationInfo inf:inList) {
                voList.add(BeanMapper.map(inf,LimitationInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Content的like查询
     * @param content
     * @return
     */
    @Override
  public List<LimitationInfoVO> findByContentLike(String content){
        List<LimitationInfoVO> voList = new ArrayList<>();
        List<LimitationInfo> inList = imp.findByContentLike("%"+content+"%");
        if(  inList != null){
            for (LimitationInfo inf:inList) {
                voList.add(BeanMapper.map(inf,LimitationInfoVO.class));
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
        org.springframework.data.domain.Page<LimitationInfo> pageResult = null;
        if (true) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<LimitationInfo> spec = (Specification<LimitationInfo>) (root, query, cb) -> {
                    
                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<LimitationInfoVO> list = BeanMapper.mapList(pageResult.getContent(), LimitationInfoVO.class);
//        List<com.msg.LimitationInfo> protoList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            protoList.add(list.get(i).ConvertToDTO());
//        }
        data.put("items", list);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }
}
