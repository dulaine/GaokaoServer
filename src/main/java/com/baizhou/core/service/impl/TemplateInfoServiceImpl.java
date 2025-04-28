package com.baizhou.core.service.impl;

import com.baizhou.core.dao.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.service.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;
import java.util.*;


@Service
public class TemplateInfoServiceImpl  implements TemplateInfoService {


    @Autowired
    private TemplateInfoRepo imp;
    /**
     * 保存
     * @param templateInfo
     * @return
     */
    @Override
    public TemplateInfo saveTemplateInfo(TemplateInfoVO templateInfo) {

        return imp.save(BeanMapper.map(templateInfo, TemplateInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        TemplateInfo entity = imp.findById(id).orElse(null);
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
    public TemplateInfoVO findOneById(Long id) {
        TemplateInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, TemplateInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<TemplateInfoVO> getAllTemplateInfos() {
        return BeanMapper.mapList(imp.findAll(), TemplateInfoVO.class);
    }
    /**
     * 更新记录
     * @param templateInfoVO
     * @return
     */
    @Override
    public String updateTemplateInfo(TemplateInfoVO templateInfoVO) {
        TemplateInfo templateInfo = BeanMapper.map(templateInfoVO, TemplateInfo.class);
        imp.save(templateInfo);
        return "success";
    }

     /**
     *  通过TemplateName的like查询
     * @param templateName
     * @return
     */
    @Override
  public List<TemplateInfoVO> findByTemplateName(String templateName){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByTemplateName(templateName);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过TemplateName的like查询
     * @param templateName
     * @return
     */
    @Override
  public List<TemplateInfoVO> findByTemplateNameLike(String templateName){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByTemplateNameLike("%"+templateName+"%");
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过CreatorName的like查询
     * @param creatorName
     * @return
     */
    @Override
  public List<TemplateInfoVO> findByCreatorName(String creatorName){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByCreatorName(creatorName);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过CreatorName的like查询
     * @param creatorName
     * @return
     */
    @Override
  public List<TemplateInfoVO> findByCreatorNameLike(String creatorName){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByCreatorNameLike("%"+creatorName+"%");
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过CreatorId的like查询
     * @param creatorId
     * @return
     */
    @Override
  public List<TemplateInfoVO> findByCreatorId(Long creatorId){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByCreatorId(creatorId);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
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
  public List<TemplateInfoVO> findByExamYear(String examYear){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByExamYear(examYear);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
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
  public List<TemplateInfoVO> findByExamYearLike(String examYear){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByExamYearLike("%"+examYear+"%");
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
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
  public List<TemplateInfoVO> findByPici(Integer pici){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByPici(pici);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
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
  public List<TemplateInfoVO> findByIsDelete(Integer isDelete){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByIsDelete(isDelete);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
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
    public Map<String, Object> listbyPage(String templateName, String authorTeacherIds,Integer isPublic,String examYear,Integer pici,Integer isDelete,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<TemplateInfo> pageResult = null;
        if (StringUtils.isEmpty(authorTeacherIds)  && StringUtils.isEmpty(isPublic)  && StringUtils.isEmpty(examYear)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(isDelete) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<TemplateInfo> spec = (Specification<TemplateInfo>) (root, query, cb) -> {
                    
//                if (!StringUtils.isEmpty(authorTeacherIds)) {
//                   String[] authorTeacherIdsSplits = authorTeacherIds.split("\\|");
//                    Predicate p = null;
//                    for (int i = 0; i < authorTeacherIdsSplits.length; i++) {
//                        if(p == null){
//                            p = cb.like(root.get("authorTeacherIds"), "%" + authorTeacherIdsSplits[i] + "%");
//                        }else {
//                            p = cb.or(p, cb.like(root.get("authorTeacherIds"), "%" + authorTeacherIdsSplits[i] + "%"));
//                        }
//                    }
//                    predicates.add(p);
//                }

                if (!StringUtils.isEmpty(authorTeacherIds)) {
                    String[] authorTeacherIdsSplits = authorTeacherIds.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < authorTeacherIdsSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("authorTeacherIds"), "%" + ConstDefine.BracketL + authorTeacherIdsSplits[i] + ConstDefine.BracketR + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("authorTeacherIds"), "%" + ConstDefine.BracketL + authorTeacherIdsSplits[i] + ConstDefine.BracketR + "%"));
                        }
                    }


                    if (!StringUtils.isEmpty(isPublic)) {
                        p = cb.or(p, cb.equal(root.get("isPublic"), isPublic));
                    }

                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(templateName)) {
                    String[] examYearSplits = templateName.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < examYearSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("templateName"), "%" + examYearSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("templateName"), "%" + examYearSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }


//                if (!StringUtils.isEmpty(isPublic)) {
//                    predicates.add(cb.equal(root.get("isPublic"), isPublic));
//                }

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

                if (!StringUtils.isEmpty(pici)) {
                    predicates.add(cb.equal(root.get("pici"), pici));
                }

                if (!StringUtils.isEmpty(isDelete)) {
                    predicates.add(cb.equal(root.get("isDelete"), isDelete));
                }

                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<TemplateInfoVO> list = BeanMapper.mapList(pageResult.getContent(), TemplateInfoVO.class);
        List<com.msg.TemplateInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public List<TemplateInfoVO> findByExamYearAndPici(String examYear, Integer pici){
        List<TemplateInfoVO> voList = new ArrayList<>();
        List<TemplateInfo> inList = imp.findByExamYearAndPici(examYear, pici);
        if(  inList != null){
            for (TemplateInfo inf:inList) {
                voList.add(BeanMapper.map(inf,TemplateInfoVO.class));
            }
        }
        return  voList;
    }
}
