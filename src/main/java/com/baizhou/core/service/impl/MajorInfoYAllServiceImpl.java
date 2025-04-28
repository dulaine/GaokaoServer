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
public class MajorInfoYAllServiceImpl  implements MajorInfoYAllService {


    @Autowired
    private MajorInfoYAllRepo imp;
    /**
     * 保存
     * @param majorInfoYAll
     * @return
     */
    @Override
    public MajorInfoYAll saveMajorInfoYAll(MajorInfoYAllVO majorInfoYAll) {

        return imp.save(BeanMapper.map(majorInfoYAll, MajorInfoYAll.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        MajorInfoYAll entity = imp.findById(id).orElse(null);
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
    public MajorInfoYAllVO findOneById(Long id) {
        MajorInfoYAll entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, MajorInfoYAllVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<MajorInfoYAllVO> getAllMajorInfoYAlls() {
        return BeanMapper.mapList(imp.findAll(), MajorInfoYAllVO.class);
    }
    /**
     * 更新记录
     * @param majorInfoYAllVO
     * @return
     */
    @Override
    public String updateMajorInfoYAll(MajorInfoYAllVO majorInfoYAllVO) {
        MajorInfoYAll majorInfoYAll = BeanMapper.map(majorInfoYAllVO, MajorInfoYAll.class);
        imp.save(majorInfoYAll);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<MajorInfoYAllVO> findByUniMajorCode(String uniMajorCode){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<MajorInfoYAllVO> findByUniMajorCodeLike(String uniMajorCode){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
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
  public List<MajorInfoYAllVO> findByPici(Integer pici){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findByPici(pici);
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolName的like查询
     * @param schoolName
     * @return
     */
    @Override
  public List<MajorInfoYAllVO> findBySchoolName(String schoolName){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolName的like查询
     * @param schoolName
     * @return
     */
    @Override
  public List<MajorInfoYAllVO> findBySchoolNameLike(String schoolName){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
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
  public List<MajorInfoYAllVO> findByYear(String year){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findByYear(year);
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
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
  public List<MajorInfoYAllVO> findByYearLike(String year){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findByYearLike("%"+year+"%");
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
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
        org.springframework.data.domain.Page<MajorInfoYAll> pageResult = null;
        if (true) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<MajorInfoYAll> spec = (Specification<MajorInfoYAll>) (root, query, cb) -> {
                    
                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<MajorInfoYAllVO> list = BeanMapper.mapList(pageResult.getContent(), MajorInfoYAllVO.class);
        List<com.msg.MajorInfoYear> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }



    @Override
    public List<MajorInfoYAllVO> findBySchoolNameAndPiciAndYear(String schoolName, Integer pici,String year){
        List<MajorInfoYAllVO> voList = new ArrayList<>();
        List<MajorInfoYAll> inList = imp.findBySchoolNameAndPiciAndYear(schoolName, pici, year);
        if(  inList != null){
            for (MajorInfoYAll inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoYAllVO.class));
            }
        }
        return  voList;
    }

    @Override
    public void deleteByYear(String examYear){
        imp.deleteByYear(examYear);
    }
}
