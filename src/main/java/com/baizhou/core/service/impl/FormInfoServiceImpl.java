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
public class FormInfoServiceImpl  implements FormInfoService {


    @Autowired
    private FormInfoRepo imp;
    /**
     * 保存
     * @param formInfo
     * @return
     */
    @Override
    public FormInfo saveFormInfo(FormInfoVO formInfo) {

        return imp.save(BeanMapper.map(formInfo, FormInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        FormInfo entity = imp.findById(id).orElse(null);
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
    public FormInfoVO findOneById(Long id) {
        FormInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, FormInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<FormInfoVO> getAllFormInfos() {
        return BeanMapper.mapList(imp.findAll(), FormInfoVO.class);
    }
    /**
     * 更新记录
     * @param formInfoVO
     * @return
     */
    @Override
    public String updateFormInfo(FormInfoVO formInfoVO) {
        FormInfo formInfo = BeanMapper.map(formInfoVO, FormInfo.class);
        imp.save(formInfo);
        return "success";
    }

     /**
     *  通过OrderId的like查询
     * @param orderId
     * @return
     */
    @Override
  public List<FormInfoVO> findByOrderId(Long orderId){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByOrderId(orderId);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过FormName的like查询
     * @param formName
     * @return
     */
    @Override
  public List<FormInfoVO> findByFormName(String formName){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByFormName(formName);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过FormName的like查询
     * @param formName
     * @return
     */
    @Override
  public List<FormInfoVO> findByFormNameLike(String formName){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByFormNameLike("%"+formName+"%");
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
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
  public List<FormInfoVO> findByPici(Integer pici){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByPici(pici);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过IsDelete的like查询
     * @param isDelete
     * @return
     */
    @Override
  public List<FormInfoVO> findByIsDelete(Integer isDelete){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByIsDelete(isDelete);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过ExamYear的like查询
     * @param examYear
     * @return
     */
    @Override
  public List<FormInfoVO> findByExamYear(String examYear){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByExamYear(examYear);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过ExamYear的like查询
     * @param examYear
     * @return
     */
    @Override
  public List<FormInfoVO> findByExamYearLike(String examYear){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByExamYearLike("%"+examYear+"%");
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
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
    public Map<String, Object> listbyPage(Long orderId,Integer pici,Integer isDelete,String examYear,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<FormInfo> pageResult = null;
        if (StringUtils.isEmpty(orderId)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(isDelete)  && StringUtils.isEmpty(examYear) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<FormInfo> spec = (Specification<FormInfo>) (root, query, cb) -> {
                    
                if (!StringUtils.isEmpty(orderId)) {
                    predicates.add(cb.equal(root.get("orderId"), orderId));
                }

                if (!StringUtils.isEmpty(pici)) {
                    predicates.add(cb.equal(root.get("pici"), pici));
                }

                if (!StringUtils.isEmpty(isDelete)) {
                    predicates.add(cb.equal(root.get("isDelete"), isDelete));
                }

                if (!StringUtils.isEmpty(examYear)) {
                   String[] examYearSplits = examYear.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < examYearSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("examYear"), "%" + examYearSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("examYear"), "%" + examYearSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<FormInfoVO> list = BeanMapper.mapList(pageResult.getContent(), FormInfoVO.class);
        List<com.msg.FormInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }


    @Override
    public List<FormInfoVO> findByFormNameAndOrderId(String formName, Long orderId) {
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByFormNameAndOrderId(formName, orderId);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
    }

    @Override
    public List<FormInfoVO> findByExamYearAndPici(String examYear, Integer pici){
        List<FormInfoVO> voList = new ArrayList<>();
        List<FormInfo> inList = imp.findByExamYearAndPici(examYear,pici);
        if(  inList != null){
            for (FormInfo inf:inList) {
                voList.add(BeanMapper.map(inf,FormInfoVO.class));
            }
        }
        return  voList;
    }
}
