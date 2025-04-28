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
public class MajorInfoY3ServiceImpl  implements MajorInfoY3Service {


    @Autowired
    private MajorInfoY3Repo imp;
    /**
     * 保存
     * @param majorInfoY3
     * @return
     */
    @Override
    public MajorInfoY3 saveMajorInfoY3(MajorInfoY3VO majorInfoY3) {

        return imp.save(BeanMapper.map(majorInfoY3, MajorInfoY3.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        MajorInfoY3 entity = imp.findById(id).orElse(null);
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
    public MajorInfoY3VO findOneById(Long id) {
        MajorInfoY3 entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, MajorInfoY3VO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<MajorInfoY3VO> getAllMajorInfoY3s() {
        return BeanMapper.mapList(imp.findAll(), MajorInfoY3VO.class);
    }
    /**
     * 更新记录
     * @param majorInfoY3VO
     * @return
     */
    @Override
    public String updateMajorInfoY3(MajorInfoY3VO majorInfoY3VO) {
        MajorInfoY3 majorInfoY3 = BeanMapper.map(majorInfoY3VO, MajorInfoY3.class);
        imp.save(majorInfoY3);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<MajorInfoY3VO> findByUniMajorCode(String uniMajorCode){
        List<MajorInfoY3VO> voList = new ArrayList<>();
        List<MajorInfoY3> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (MajorInfoY3 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY3VO.class));
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
  public List<MajorInfoY3VO> findByUniMajorCodeLike(String uniMajorCode){
        List<MajorInfoY3VO> voList = new ArrayList<>();
        List<MajorInfoY3> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (MajorInfoY3 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY3VO.class));
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
  public List<MajorInfoY3VO> findByPici(Integer pici){
        List<MajorInfoY3VO> voList = new ArrayList<>();
        List<MajorInfoY3> inList = imp.findByPici(pici);
        if(  inList != null){
            for (MajorInfoY3 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY3VO.class));
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
  public List<MajorInfoY3VO> findBySchoolName(String schoolName){
        List<MajorInfoY3VO> voList = new ArrayList<>();
        List<MajorInfoY3> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (MajorInfoY3 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY3VO.class));
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
  public List<MajorInfoY3VO> findBySchoolNameLike(String schoolName){
        List<MajorInfoY3VO> voList = new ArrayList<>();
        List<MajorInfoY3> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (MajorInfoY3 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY3VO.class));
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
        org.springframework.data.domain.Page<MajorInfoY3> pageResult = null;
        if (StringUtils.isEmpty(uniMajorCode)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(schoolName) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<MajorInfoY3> spec = (Specification<MajorInfoY3>) (root, query, cb) -> {
                    
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

        List<MajorInfoY3VO> list = BeanMapper.mapList(pageResult.getContent(), MajorInfoY3VO.class);
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
    public List<MajorInfoY3VO> findBySchoolNameAndPici(String schoolName, Integer pici){
        List<MajorInfoY3VO> voList = new ArrayList<>();
        List<MajorInfoY3> inList = imp.findBySchoolNameAndPici(schoolName, pici);
        if(  inList != null){
            for (MajorInfoY3 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY3VO.class));
            }
        }
        return  voList;
    }
}
