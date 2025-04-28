package com.baizhou.core.service.impl;

import com.baizhou.common.CommonUtil;
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

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;
import java.util.*;


@Service
public class UniGroupInfoPreBLatestServiceImpl  implements UniGroupInfoPreBLatestService {


    @Autowired
    private UniGroupInfoPreBLatestRepo imp;
    /**
     * 保存
     * @param uniGroupInfoPreBLatest
     * @return
     */
    @Override
    public UniGroupInfoPreBLatest saveUniGroupInfoPreBLatest(UniGroupInfoPreBLatestVO uniGroupInfoPreBLatest) {

        return imp.save(BeanMapper.map(uniGroupInfoPreBLatest, UniGroupInfoPreBLatest.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        UniGroupInfoPreBLatest entity = imp.findById(id).orElse(null);
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
    public UniGroupInfoPreBLatestVO findOneById(Long id) {
        UniGroupInfoPreBLatest entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, UniGroupInfoPreBLatestVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<UniGroupInfoPreBLatestVO> getAllUniGroupInfoPreBLatests() {
        return BeanMapper.mapList(imp.findAll(), UniGroupInfoPreBLatestVO.class);
    }
    /**
     * 更新记录
     * @param uniGroupInfoPreBLatestVO
     * @return
     */
    @Override
    public String updateUniGroupInfoPreBLatest(UniGroupInfoPreBLatestVO uniGroupInfoPreBLatestVO) {
        UniGroupInfoPreBLatest uniGroupInfoPreBLatest = BeanMapper.map(uniGroupInfoPreBLatestVO, UniGroupInfoPreBLatest.class);
        imp.save(uniGroupInfoPreBLatest);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findByUniMajorCode(String uniMajorCode){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findByUniMajorCodeLike(String uniMajorCode){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findByPici(Integer pici){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByPici(pici);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findBySchoolName(String schoolName){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findBySchoolNameLike(String schoolName){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolLvl1的like查询
     * @param schoolLvl1
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findBySchoolLvl1(Integer schoolLvl1){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolLvl1(schoolLvl1);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolLvl2的like查询
     * @param schoolLvl2
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findBySchoolLvl2(Integer schoolLvl2){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolLvl2(schoolLvl2);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolLvl3的like查询
     * @param schoolLvl3
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findBySchoolLvl3(Integer schoolLvl3){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolLvl3(schoolLvl3);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolType的like查询
     * @param schoolType
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findBySchoolType(String schoolType){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolType(schoolType);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过SchoolType的like查询
     * @param schoolType
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findBySchoolTypeLike(String schoolType){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findBySchoolTypeLike("%"+schoolType+"%");
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Province的like查询
     * @param province
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findByProvince(String province){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByProvince(province);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Province的like查询
     * @param province
     * @return
     */
    @Override
  public List<UniGroupInfoPreBLatestVO> findByProvinceLike(String province){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByProvinceLike("%"+province+"%");
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findByExamYear(String examYear){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByExamYear(examYear);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findByExamYearLike(String examYear){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByExamYearLike("%"+examYear+"%");
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findByMajorName(String majorName){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByMajorName(majorName);
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
  public List<UniGroupInfoPreBLatestVO> findByMajorNameLike(String majorName){
        List<UniGroupInfoPreBLatestVO> voList = new ArrayList<>();
        List<UniGroupInfoPreBLatest> inList = imp.findByMajorNameLike("%"+majorName+"%");
        if(  inList != null){
            for (UniGroupInfoPreBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniGroupInfoPreBLatestVO.class));
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
//    public Map<String, Object> listbyPage(String uniMajorCode,Integer pici,String schoolName,Integer schoolLvl1,Integer schoolLvl2,Integer schoolLvl3,String schoolType,String province,String examYear,String majorName,Pageable pageable) {
    public Map<String, Object> listbyPage(Integer pici,  List<String> uniMajorCode,
                                          List<String> schoolName,
                                          Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3,
                                          List<String> schoolType,
                                          List<String> province, List<String> majorName,

                                          Integer minRank, Integer maxRank, //预估分数
                                          List<String> examSubj,   //科目 list
                                          List<String> majorName2,

                                          String examYear, //年份
                                          Pageable pageable){
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<UniGroupInfoPreBLatest> pageResult = null;
        if (StringUtils.isEmpty(pici) && uniMajorCode.size() == 0
                && schoolName.size() == 0 &&
                StringUtils.isEmpty(schoolLvl1) && StringUtils.isEmpty(schoolLvl2) && StringUtils.isEmpty(schoolLvl3) &&
                StringUtils.isEmpty(examYear) &&
                schoolType.size() == 0 &&
                province.size() == 0 && majorName.size() == 0 &&
                StringUtils.isEmpty(minRank) && StringUtils.isEmpty(maxRank)) {
//        if (StringUtils.isEmpty(uniMajorCode)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(schoolName)  && StringUtils.isEmpty(schoolLvl1)  && StringUtils.isEmpty(schoolLvl2)  && StringUtils.isEmpty(schoolLvl3)  && StringUtils.isEmpty(schoolType)  && StringUtils.isEmpty(province)  && StringUtils.isEmpty(majorName) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<UniGroupInfoPreBLatest> spec = (Specification<UniGroupInfoPreBLatest>) (root, query, cb) -> {


//                if (uniMajorCode.size() > 0) {
//                    Predicate p = null;
//                    Expression<String> expression = root.get("uniMajorCode");
////                    for (int i = 0; i < uniMajorCode.size(); i++) {
////                        if (p == null) {
////                            p = cb.equal(expression, "" + uniMajorCode.get(i) + "");
////                        } else {
////                            p = cb.or(p, cb.equal(expression, "" + uniMajorCode.get(i) + ""));
////                        }
////                    }
////                    predicates.add(p);
//                }


                if (!StringUtils.isEmpty(pici)) {
                    predicates.add(cb.equal(root.get("pici"), pici));
                }


                if (schoolName.size() > 0) {
                    Predicate p = null;
                    Expression<String> expression = root.get("schoolName");
                    Expression<String> expression2 = root.get("uniMajorCode");

                    for (int i = 0; i < schoolName.size(); i++) {
                        String mName = schoolName.get(i);

                        Predicate tempP = null;
                        if (!CommonUtil.hasNumeric(mName)) {
                            tempP = cb.like(expression, "%" + mName + "%");
                        }else {
                            tempP = cb.like(expression2, "%" + mName + "%");
                        }

                        if (p == null) {
                            p = tempP;
                        } else {
                            p = cb.or(p, tempP);
                        }
                    }

                    predicates.add(p);

                }


                if (!StringUtils.isEmpty(schoolLvl1)) {
                    predicates.add(cb.equal(root.get("schoolLvl1"), schoolLvl1));
                }

                if (!StringUtils.isEmpty(schoolLvl2)) {
                    predicates.add(cb.equal(root.get("schoolLvl2"), schoolLvl2));
                }

                if (!StringUtils.isEmpty(schoolLvl3)) {
                    predicates.add(cb.equal(root.get("schoolLvl3"), schoolLvl3));
                }


                if (schoolType.size() > 0) {
//                    String[] schoolTypeSplits = schoolType.split("\\|");
                    Predicate p = null;
                    Expression<String> expression = root.get("schoolType");
                    for (int i = 0; i < schoolType.size(); i++) {
                        if (p == null) {
                            p = cb.like(expression, "%" + schoolType.get(i) + "%");
                        } else {
                            p = cb.or(p, cb.like(expression, "%" + schoolType.get(i) + "%"));
                        }
                    }
                    predicates.add(p);
                }


                if (province.size() > 0) {
//                    String[] provinceSplits = province.split("\\|");
                    Predicate p = null;
                    Expression<String> expression = root.get("province");
                    for (int i = 0; i < province.size(); i++) {
                        if (p == null) {
                            p = cb.like(expression, "%" + province.get(i) + "%");
                        } else {
                            p = cb.or(p, cb.like(expression, "%" + province.get(i) + "%"));
                        }
                    }
                    predicates.add(p);
                }


                if (majorName.size() > 0) {
                    Predicate p = null;
                    Expression<String> expression = root.get("majorNames");
                    // 是搜索专业组下所有专业的合集  包含输入的专业
//                    Expression<String> expression2 = root.get("majorNames");

                    for (int i = 0; i < majorName.size(); i++) {
                        String mName = majorName.get(i);
                        Predicate tempP = cb.like(expression, "%" + mName + "%");


                        if (majorName2.size() > 0) {
                            for (int j = 0; j < majorName2.size(); j++) {
                                String mName2 = majorName2.get(j);
                                Predicate tempP2 = cb.and(tempP, cb.like(expression, "%" + mName2 + "%")); //专业2
                                if (p == null) {
                                    p = tempP2;
                                } else {
                                    p = cb.or(p, tempP2);//
                                }
                            }
                        } else {
                            if (p == null) {
                                p = tempP;
                            } else {
                                p = cb.or(p, tempP);
                            }
                        }

                    }
                    predicates.add(p);
                }



                if (!StringUtils.isEmpty(minRank) && !StringUtils.isEmpty(maxRank)) {

                    Expression<Integer> rank22 =root.get("rankFor22");
                    Expression<Integer> major22 =root.get("major22LowRank");

                    Predicate tempP1 = cb.and(cb.lessThanOrEqualTo(rank22, minRank), cb.greaterThanOrEqualTo(rank22, maxRank)); //专业2
                    Predicate tempP2 = cb.and(cb.lessThanOrEqualTo(major22, minRank), cb.greaterThanOrEqualTo(major22, maxRank)); //专业2
                    Predicate p = cb.or(tempP1, tempP2);
                    predicates.add(p);
                }

                if (examSubj.size() > 0) {
//                    String[] majorNameSplits = majorName.split("\\|");
                    Predicate p = null;
                    Expression<String> expression = root.get("subjectRequirement");
                    p = cb.equal(expression, ConstDefine.UnLimit);
                    String allSubs = "";
                    for (int i = 0; i < examSubj.size(); i++) {
                        String sub1 = examSubj.get(i);
                        p = cb.or(p, cb.equal(expression, sub1)); //单科目
                        p = cb.or(p, cb.like(expression, "%" + sub1 + "/%")); //like /生/
                        p = cb.or(p, cb.like(expression, "%/" + sub1 + "%"));// like /生/

                        //2个科目要求
                        for (int j = i + 1; j < examSubj.size(); j++) {
                            p = cb.or(p, cb.like(expression, "" + sub1 + "+" + examSubj.get(j) + ""));
                        }


                        if (i > 0) {
                            allSubs += "+";
                        }
                        allSubs += sub1;
                    }
                    p = cb.or(p, cb.equal(expression, allSubs));

                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(examYear)) {
                    predicates.add(cb.equal(root.get("examYear"), examYear));
                }

                return cb.and(predicates.toArray(new Predicate[]{}));
            };
            pageResult = imp.findAll(spec, pageable);
        }

        List<UniGroupInfoPreBLatestVO> list = BeanMapper.mapList(pageResult.getContent(), UniGroupInfoPreBLatestVO.class);
//        List<com.msg.UniGroupInfoPreBLatest> protoList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            protoList.add(list.get(i).ConvertToDTO());
//        }
        data.put("items", list);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public void deleteByExamYear(String examYear) {
        imp.deleteByExamYear(examYear);
    }

}
