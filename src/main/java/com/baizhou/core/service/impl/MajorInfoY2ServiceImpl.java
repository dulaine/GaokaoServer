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
public class MajorInfoY2ServiceImpl  implements MajorInfoY2Service {


    @Autowired
    private MajorInfoY2Repo imp;
    /**
     * 保存
     * @param majorInfoY2
     * @return
     */
    @Override
    public MajorInfoY2 saveMajorInfoY2(MajorInfoY2VO majorInfoY2) {

        return imp.save(BeanMapper.map(majorInfoY2, MajorInfoY2.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        MajorInfoY2 entity = imp.findById(id).orElse(null);
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
    public MajorInfoY2VO findOneById(Long id) {
        MajorInfoY2 entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, MajorInfoY2VO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<MajorInfoY2VO> getAllMajorInfoY2s() {
        return BeanMapper.mapList(imp.findAll(), MajorInfoY2VO.class);
    }
    /**
     * 更新记录
     * @param majorInfoY2VO
     * @return
     */
    @Override
    public String updateMajorInfoY2(MajorInfoY2VO majorInfoY2VO) {
        MajorInfoY2 majorInfoY2 = BeanMapper.map(majorInfoY2VO, MajorInfoY2.class);
        imp.save(majorInfoY2);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<MajorInfoY2VO> findByUniMajorCode(String uniMajorCode){
        List<MajorInfoY2VO> voList = new ArrayList<>();
        List<MajorInfoY2> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (MajorInfoY2 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY2VO.class));
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
  public List<MajorInfoY2VO> findByUniMajorCodeLike(String uniMajorCode){
        List<MajorInfoY2VO> voList = new ArrayList<>();
        List<MajorInfoY2> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (MajorInfoY2 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY2VO.class));
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
  public List<MajorInfoY2VO> findByPici(Integer pici){
        List<MajorInfoY2VO> voList = new ArrayList<>();
        List<MajorInfoY2> inList = imp.findByPici(pici);
        if(  inList != null){
            for (MajorInfoY2 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY2VO.class));
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
  public List<MajorInfoY2VO> findBySchoolName(String schoolName){
        List<MajorInfoY2VO> voList = new ArrayList<>();
        List<MajorInfoY2> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (MajorInfoY2 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY2VO.class));
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
  public List<MajorInfoY2VO> findBySchoolNameLike(String schoolName){
        List<MajorInfoY2VO> voList = new ArrayList<>();
        List<MajorInfoY2> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (MajorInfoY2 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY2VO.class));
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
        org.springframework.data.domain.Page<MajorInfoY2> pageResult = null;
        if (StringUtils.isEmpty(uniMajorCode)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(schoolName) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<MajorInfoY2> spec = (Specification<MajorInfoY2>) (root, query, cb) -> {
                    
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

        List<MajorInfoY2VO> list = BeanMapper.mapList(pageResult.getContent(), MajorInfoY2VO.class);
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
    public List<MajorInfoY2VO> findBySchoolNameAndPici(String schoolName, Integer pici){
        List<MajorInfoY2VO> voList = new ArrayList<>();
        List<MajorInfoY2> inList = imp.findBySchoolNameAndPici(schoolName, pici);
        if(  inList != null){
            for (MajorInfoY2 inf:inList) {
                voList.add(BeanMapper.map(inf,MajorInfoY2VO.class));
            }
        }
        return  voList;
    }
}
