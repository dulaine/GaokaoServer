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
public class MajorInfoY1ServiceImpl  implements MajorInfoY1Service {


    @Autowired
    private MajorInfoY1Repo imp;
    /**
     * 保存
     * @param majorInfoY1
     * @return
     */
    @Override
    public MajorInfoY1 saveMajorInfoY1(MajorInfoY1VO majorInfoY1) {

        return imp.save(BeanMapper.map(majorInfoY1, MajorInfoY1.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        MajorInfoY1 entity = imp.findById(id).orElse(null);
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
    public MajorInfoY1VO findOneById(Long id) {
        MajorInfoY1 entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, MajorInfoY1VO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<MajorInfoY1VO> getAllMajorInfoY1s() {
        return BeanMapper.mapList(imp.findAll(), MajorInfoY1VO.class);
    }
    /**
     * 更新记录
     * @param majorInfoY1VO
     * @return
     */
    @Override
    public String updateMajorInfoY1(MajorInfoY1VO majorInfoY1VO) {
        MajorInfoY1 majorInfoY1 = BeanMapper.map(majorInfoY1VO, MajorInfoY1.class);
        imp.save(majorInfoY1);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<MajorInfoY1VO> findByUniMajorCode(String uniMajorCode){
        List<MajorInfoY1VO> voList = new ArrayList<>();
        List<MajorInfoY1> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (MajorInfoY1 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY1VO.class));
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
  public List<MajorInfoY1VO> findByUniMajorCodeLike(String uniMajorCode){
        List<MajorInfoY1VO> voList = new ArrayList<>();
        List<MajorInfoY1> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (MajorInfoY1 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY1VO.class));
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
  public List<MajorInfoY1VO> findByPici(Integer pici){
        List<MajorInfoY1VO> voList = new ArrayList<>();
        List<MajorInfoY1> inList = imp.findByPici(pici);
        if(  inList != null){
            for (MajorInfoY1 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY1VO.class));
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
  public List<MajorInfoY1VO> findBySchoolName(String schoolName){
        List<MajorInfoY1VO> voList = new ArrayList<>();
        List<MajorInfoY1> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (MajorInfoY1 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY1VO.class));
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
  public List<MajorInfoY1VO> findBySchoolNameLike(String schoolName){
        List<MajorInfoY1VO> voList = new ArrayList<>();
        List<MajorInfoY1> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (MajorInfoY1 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY1VO.class));
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
    public Map<String, Object> listbyPage(String uniMajorCode,Integer pici,String schoolName,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<MajorInfoY1> pageResult = null;
        if (StringUtils.isEmpty(uniMajorCode)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(schoolName) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<MajorInfoY1> spec = (Specification<MajorInfoY1>) (root, query, cb) -> {
                    
                if (!StringUtils.isEmpty(uniMajorCode)) {
                   String[] uniMajorCodeSplits = uniMajorCode.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < uniMajorCodeSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("uniMajorCode"), "%" + uniMajorCodeSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("uniMajorCode"), "%" + uniMajorCodeSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(pici)) {
                    predicates.add(cb.equal(root.get("pici"), pici));
                }

                if (!StringUtils.isEmpty(schoolName)) {
                   String[] schoolNameSplits = schoolName.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < schoolNameSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("schoolName"), "%" + schoolNameSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("schoolName"), "%" + schoolNameSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<MajorInfoY1VO> list = BeanMapper.mapList(pageResult.getContent(), MajorInfoY1VO.class);
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
    public List<MajorInfoY1VO> findBySchoolNameAndPici(String schoolName, Integer pici){
        List<MajorInfoY1VO> voList = new ArrayList<>();
        List<MajorInfoY1> inList = imp.findBySchoolNameAndPici(schoolName, pici);
        if(  inList != null){
            for (MajorInfoY1 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY1VO.class));
            }
        }
        return  voList;
    }
}
