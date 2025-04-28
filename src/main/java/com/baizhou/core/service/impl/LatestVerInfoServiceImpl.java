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
public class LatestVerInfoServiceImpl  implements LatestVerInfoService {


    @Autowired
    private LatestVerInfoRepo imp;
    /**
     * 保存
     * @param latestVerInfo
     * @return
     */
    @Override
    public LatestVerInfo saveLatestVerInfo(LatestVerInfoVO latestVerInfo) {

        return imp.save(BeanMapper.map(latestVerInfo, LatestVerInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        LatestVerInfo entity = imp.findById(id).orElse(null);
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
    public LatestVerInfoVO findOneById(Long id) {
        LatestVerInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, LatestVerInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<LatestVerInfoVO> getAllLatestVerInfos() {
        return BeanMapper.mapList(imp.findAll(), LatestVerInfoVO.class);
    }
    /**
     * 更新记录
     * @param latestVerInfoVO
     * @return
     */
    @Override
    public String updateLatestVerInfo(LatestVerInfoVO latestVerInfoVO) {
        LatestVerInfo latestVerInfo = BeanMapper.map(latestVerInfoVO, LatestVerInfo.class);
        imp.save(latestVerInfo);
        return "success";
    }

     /**
     *  通过Year的like查询
     * @param year
     * @return
     */
    @Override
  public List<LatestVerInfoVO> findByYear(String year){
        List<LatestVerInfoVO> voList = new ArrayList<>();
        List<LatestVerInfo> inList = imp.findByYear(year);
        if(  inList != null){
            for (LatestVerInfo inf:inList) {
                voList.add(BeanMapper.map(inf,LatestVerInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Year的like查询
     * @param year
     * @return
     */
    @Override
  public List<LatestVerInfoVO> findByYearLike(String year){
        List<LatestVerInfoVO> voList = new ArrayList<>();
        List<LatestVerInfo> inList = imp.findByYearLike("%"+year+"%");
        if(  inList != null){
            for (LatestVerInfo inf:inList) {
                voList.add(BeanMapper.map(inf,LatestVerInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Pici的like查询
     * @param pici
     * @return
     */
    @Override
  public List<LatestVerInfoVO> findByPici(Integer pici){
        List<LatestVerInfoVO> voList = new ArrayList<>();
        List<LatestVerInfo> inList = imp.findByPici(pici);
        if(  inList != null){
            for (LatestVerInfo inf:inList) {
                voList.add(BeanMapper.map(inf,LatestVerInfoVO.class));
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
    public Map<String, Object> listbyPage(String year,Integer pici,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<LatestVerInfo> pageResult = null;
        if (StringUtils.isEmpty(year)  && StringUtils.isEmpty(pici) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<LatestVerInfo> spec = (Specification<LatestVerInfo>) (root, query, cb) -> {
                    
                if (!StringUtils.isEmpty(year)) {
                   String[] yearSplits = year.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < yearSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("year"), "%" + yearSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("year"), "%" + yearSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(pici)) {
                    predicates.add(cb.equal(root.get("pici"), pici));
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<LatestVerInfoVO> list = BeanMapper.mapList(pageResult.getContent(), LatestVerInfoVO.class);
        List<LatestVerInfoVO> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public LatestVerInfoVO findByPiciAndYear(Integer pici, String year){
        LatestVerInfo entity = imp.findByPiciAndYear(pici, year);
        if(entity == null) return null;
        return  BeanMapper.map(entity, LatestVerInfoVO.class);
    }
}
