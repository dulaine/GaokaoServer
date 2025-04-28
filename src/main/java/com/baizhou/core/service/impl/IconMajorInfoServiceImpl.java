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
public class IconMajorInfoServiceImpl  implements IconMajorInfoService {


    @Autowired
    private IconMajorInfoRepo imp;
    /**
     * 保存
     * @param iconMajorInfo
     * @return
     */
    @Override
    public IconMajorInfo saveIconMajorInfo(IconMajorInfoVO iconMajorInfo) {

        return imp.save(BeanMapper.map(iconMajorInfo, IconMajorInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        IconMajorInfo entity = imp.findById(id).orElse(null);
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
    public IconMajorInfoVO findOneById(Long id) {
        IconMajorInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, IconMajorInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<IconMajorInfoVO> getAllIconMajorInfos() {
        return BeanMapper.mapList(imp.findAll(), IconMajorInfoVO.class);
    }
    /**
     * 更新记录
     * @param iconMajorInfoVO
     * @return
     */
    @Override
    public String updateIconMajorInfo(IconMajorInfoVO iconMajorInfoVO) {
        IconMajorInfo iconMajorInfo = BeanMapper.map(iconMajorInfoVO, IconMajorInfo.class);
        imp.save(iconMajorInfo);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<IconMajorInfoVO> findByUniMajorCode(String uniMajorCode){
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
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
  public List<IconMajorInfoVO> findByUniMajorCodeLike(String uniMajorCode){
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
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
  public List<IconMajorInfoVO> findBySchoolName(String schoolName){
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
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
  public List<IconMajorInfoVO> findBySchoolNameLike(String schoolName){
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过MajorName的like查询
     * @param majorName
     * @return
     */
    @Override
  public List<IconMajorInfoVO> findByMajorName(String majorName){
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findByMajorName(majorName);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过MajorName的like查询
     * @param majorName
     * @return
     */
    @Override
  public List<IconMajorInfoVO> findByMajorNameLike(String majorName){
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findByMajorNameLike("%"+majorName+"%");
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
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
    public Map<String, Object> listbyPage(String uniMajorCode,String schoolName,String majorName,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<IconMajorInfo> pageResult = null;
        if (StringUtils.isEmpty(uniMajorCode)  && StringUtils.isEmpty(schoolName)  && StringUtils.isEmpty(majorName) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<IconMajorInfo> spec = (Specification<IconMajorInfo>) (root, query, cb) -> {
                    
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

                if (!StringUtils.isEmpty(majorName)) {
                   String[] majorNameSplits = majorName.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < majorNameSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("majorName"), "%" + majorNameSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("majorName"), "%" + majorNameSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<IconMajorInfoVO> list = BeanMapper.mapList(pageResult.getContent(), IconMajorInfoVO.class);
        List<com.msg.IconMajorInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls1IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls1IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls2IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls2IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls3IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls3IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls4IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls4IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls5IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls5IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls6IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls6IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls7IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls7IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls8IsNot(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls8IsNot(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<IconMajorInfoVO> findBySchoolNameAndCls1Is(String schoolName, String cls1) {
        List<IconMajorInfoVO> voList = new ArrayList<>();
        List<IconMajorInfo> inList = imp.findBySchoolNameAndCls1Is(schoolName, cls1);
        if(  inList != null){
            for (IconMajorInfo inf:inList) {
                voList.add(BeanMapper.map(inf,IconMajorInfoVO.class));
            }
        }
        return  voList;
    }
}
