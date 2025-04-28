package com.baizhou.core.service.impl;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.dao.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.service.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumGender;
import com.baizhou.db.DBProxy;
import com.baizhou.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.util.StringUtils;
import java.util.*;


@Transactional
@Service
public class UniMajorInfoBServiceImpl  implements UniMajorInfoBService {


    @Autowired
    private UniMajorInfoBRepo imp;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存
     * @param uniMajorInfoA
     * @return
     */
    @Override
    public UniMajorInfoB saveUniMajorInfoB(UniMajorInfoBVO uniMajorInfoA) {

        return imp.save(BeanMapper.map(uniMajorInfoA, UniMajorInfoB.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        UniMajorInfoB entity = imp.findById(id).orElse(null);
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
    public UniMajorInfoBVO findOneById(Long id) {
        UniMajorInfoB entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, UniMajorInfoBVO.class);
    }
    @Override
    public List<UniMajorInfoBVO> findByIdIn(List<Long> id) {
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByIdIn(id);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
            }
        }
        return  voList;
    }    /**
    获取所有信息
     */
    @Override
    public List<UniMajorInfoBVO> getAllUniMajorInfoBs() {
        return BeanMapper.mapList(imp.findAll(), UniMajorInfoBVO.class);
    }
    /**
     * 更新记录
     * @param uniMajorInfoAVO
     * @return
     */
    @Override
    public String updateUniMajorInfoB(UniMajorInfoBVO uniMajorInfoAVO) {
        UniMajorInfoB uniMajorInfoA = BeanMapper.map(uniMajorInfoAVO, UniMajorInfoB.class);
        imp.save(uniMajorInfoA);
        return "success";
    }

     /**
     *  通过UniMajorCode的like查询
     * @param uniMajorCode
     * @return
     */
    @Override
  public List<UniMajorInfoBVO> findByUniMajorCode(String uniMajorCode){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByUniMajorCode(uniMajorCode);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findByUniMajorCodeLike(String uniMajorCode){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByUniMajorCodeLike("%"+uniMajorCode+"%");
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findByPici(Integer pici){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByPici(pici);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolName(String schoolName){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolName(schoolName);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolNameLike(String schoolName){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolNameLike("%"+schoolName+"%");
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolLvl1(Integer schoolLvl1){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolLvl1(schoolLvl1);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolLvl2(Integer schoolLvl2){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolLvl2(schoolLvl2);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolLvl3(Integer schoolLvl3){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolLvl3(schoolLvl3);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolType(String schoolType){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolType(schoolType);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findBySchoolTypeLike(String schoolType){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findBySchoolTypeLike("%"+schoolType+"%");
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findByProvince(String province){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByProvince(province);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findByProvinceLike(String province){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByProvinceLike("%"+province+"%");
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findByMajorName(String majorName){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByMajorName(majorName);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
  public List<UniMajorInfoBVO> findByMajorNameLike(String majorName){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByMajorNameLike("%"+majorName+"%");
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
            }
        }
        return  voList;
  }



    /**
     *  通过IsZhongWai的like查询
     * @param isZhongWai
     * @return
     */
    @Override
    public List<UniMajorInfoBVO> findByIsZhongWai(Integer isZhongWai){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByIsZhongWai(isZhongWai);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
            }
        }
        return  voList;
    }

    /**
     *  通过IsBenSuo的like查询
     * @param isBenSuo
     * @return
     */
    @Override
    public List<UniMajorInfoBVO> findByIsBenSuo(Integer isBenSuo){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByIsBenSuo(isBenSuo);
        if(  inList != null){
            for (UniMajorInfoB inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBVO.class));
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
    public Map<String, Object> listbyPage(Integer pici,  List<String> uniMajorCode,
                                          List<String> schoolName,
                                          Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3,
                                          List<String> schoolType,
                                          List<String> province, List<String> majorName,

                                          Integer minRank, Integer maxRank, //预估分数
                                          List<String> examSubj,   //科目 list
                                          List<String> majorName2,

                                          String examYear, //年份
                                          EnumGender gender,

                                          Integer isZhongWai,  //是否中外合作
                                          Integer isBenSuo,   //是否 本硕
                                          Pageable pageable){
//    public Map<String, Object> listbyPage(String uniMajorCode,Integer pici,String schoolName,Integer schoolLvl1,Integer schoolLvl2,Integer schoolLvl3,String schoolType,String province,String majorName,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<UniMajorInfoB> pageResult = null;
        if (StringUtils.isEmpty(pici) && uniMajorCode.size() == 0
                && schoolName.size() == 0 &&
                StringUtils.isEmpty(schoolLvl1) && StringUtils.isEmpty(schoolLvl2) && StringUtils.isEmpty(schoolLvl3) &&
                StringUtils.isEmpty(isZhongWai) && StringUtils.isEmpty(isBenSuo) &&
                StringUtils.isEmpty(examYear) &&
                schoolType.size() == 0 &&
                province.size() == 0 && majorName.size() == 0 &&
                StringUtils.isEmpty(minRank) && StringUtils.isEmpty(maxRank)) {
//        if (StringUtils.isEmpty(uniMajorCode)  && StringUtils.isEmpty(pici)  && StringUtils.isEmpty(schoolName)  && StringUtils.isEmpty(schoolLvl1)  && StringUtils.isEmpty(schoolLvl2)  && StringUtils.isEmpty(schoolLvl3)  && StringUtils.isEmpty(schoolType)  && StringUtils.isEmpty(province)  && StringUtils.isEmpty(majorName) ) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<UniMajorInfoB> spec = (Specification<UniMajorInfoB>) (root, query, cb) -> {

//                if (!StringUtils.isEmpty(uniMajorCode)) {
//                   String[] uniMajorCodeSplits = uniMajorCode.split("\\|");
//                    Predicate p = null;
//                    for (int i = 0; i < uniMajorCodeSplits.length; i++) {
//                        if(p == null){
//                            p = cb.like(root.get("uniMajorCode"), "%" + uniMajorCodeSplits[i] + "%");
//                        }else {
//                            p = cb.or(p, cb.like(root.get("uniMajorCode"), "%" + uniMajorCodeSplits[i] + "%"));
//                        }
//                    }
//                    predicates.add(p);
//                }
                if (uniMajorCode.size() > 0) {
                    Predicate p = null;
                    Expression<String> expression = root.get("uniMajorCode");
                    for (int i = 0; i < uniMajorCode.size(); i++) {
                        if (p == null) {
                            p = cb.equal(expression, "" + uniMajorCode.get(i) + "");
                        } else {
                            p = cb.or(p, cb.equal(expression, "" + uniMajorCode.get(i) + ""));
                        }
                    }
                    predicates.add(p);
                }


                if (!StringUtils.isEmpty(pici)) {
                    predicates.add(cb.equal(root.get("pici"), pici));
                }

//                if (!StringUtils.isEmpty(schoolName)) {
//                   String[] schoolNameSplits = schoolName.split("\\|");
//                    Predicate p = null;
//                    for (int i = 0; i < schoolNameSplits.length; i++) {
//                        if(p == null){
//                            p = cb.like(root.get("schoolName"), "%" + schoolNameSplits[i] + "%");
//                        }else {
//                            p = cb.or(p, cb.like(root.get("schoolName"), "%" + schoolNameSplits[i] + "%"));
//                        }
//                    }
//                    predicates.add(p);
//                }
                if (schoolName.size() > 0) {
//                    Predicate p = null;
//                    Expression<String> expression = root.get("schoolName");
//                    for (int i = 0; i < schoolName.size(); i++) {
//                        if (p == null) {
//                            p = cb.like(expression, "%" + schoolName.get(i) + "%");
//                        } else {
//                            p = cb.or(p, cb.like(expression, "%" + schoolName.get(i) + "%"));
//                        }
//                    }
//                    predicates.add(p);
                    Predicate p = null;
                    Expression<String> expression = root.get("schoolName");

//                    Expression<String> expression2 = root.get("uniMajorCode");
                    for (int i = 0; i < schoolName.size(); i++) {
                        String tempName = schoolName.get(i);
//                        if (!CommonUtil.hasNumeric(tempName)) {
                            if (p == null) {
                                p = cb.like(expression, "%" + tempName + "%");
                            } else {
                                p = cb.or(p, cb.like(expression, "%" + tempName + "%"));
                            }
//                        } else {
//                            if (p == null) {
//                                p = cb.like(expression2, "%" + tempName + "%");
//                            } else {
//                                p = cb.or(p, cb.like(expression2, "%" + tempName + "%"));
//                            }
//                        }
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

                if (!StringUtils.isEmpty(isZhongWai)) {
                    predicates.add(cb.equal(root.get("isZhongWai"), isZhongWai));
                }

                if (!StringUtils.isEmpty(isBenSuo)) {
                    predicates.add(cb.equal(root.get("isBenSuo"), isBenSuo));
                }

//                if (!StringUtils.isEmpty(schoolType)) {
//                   String[] schoolTypeSplits = schoolType.split("\\|");
//                    Predicate p = null;
//                    for (int i = 0; i < schoolTypeSplits.length; i++) {
//                        if(p == null){
//                            p = cb.like(root.get("schoolType"), "%" + schoolTypeSplits[i] + "%");
//                        }else {
//                            p = cb.or(p, cb.like(root.get("schoolType"), "%" + schoolTypeSplits[i] + "%"));
//                        }
//                    }
//                    predicates.add(p);
//                }
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
                    Expression<String> expression = root.get("majorGroupMajorName");
//                    Expression<String> expression = root.get("majorName");
                    Expression<String> expression2 = root.get("uniMajorCode");
                    for (int i = 0; i < majorName.size(); i++) {
                        String mName = majorName.get(i);

//                        Predicate tempP = cb.like(expression, "%" + mName + "%");
                        Predicate tempP = null;
                        if (!CommonUtil.hasNumeric(mName)) {
                            tempP = cb.like(expression, "%" + mName + "%");
                        }else {
                            tempP = cb.like(expression2, "%" + mName + "%");
                        }

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

                    Expression<Integer> rank22 = gender == EnumGender.Male ? root.get("rankFor22") : root.get("rankFor22Nv");
                    Expression<Integer> major22 = gender == EnumGender.Male ? root.get("major22LowRank"): root.get("major22LowRankNv");

                    Predicate tempP1 = cb.and(cb.lessThanOrEqualTo(rank22, minRank), cb.greaterThanOrEqualTo(rank22, maxRank)); //专业2
                    Predicate tempP2 = cb.and(cb.lessThanOrEqualTo(major22, minRank), cb.greaterThanOrEqualTo(major22, maxRank)); //专业2
                    Predicate p = cb.or(tempP1, tempP2);
                    predicates.add(p);

                }

                if (examSubj.size() > 0) {
//                    String[] majorNameSplits = majorName.split("\\|");
                    Predicate p = null;
                    Expression<String> expression = root.get("subjectRequirement");
                    p = cb.equal(expression, "不限");
                    String allSubs = "";
                    for (int i = 0; i < examSubj.size(); i++) {
                        String sub1 = examSubj.get(i);
                        p = cb.or(p, cb.equal(expression, sub1)); //单科目
                        p = cb.or(p, cb.like(expression, "%" + sub1 + "/%")); //like /生/
                        p = cb.or(p, cb.like(expression, "%/" + sub1 + "%"));// like /生/
//                        if (p == null) {
////                            p = cb.like(root.get("majorName"), "%" + examSubj.get(i) + "%");
//                            p = cb.equal(root.get("subjectRequirement"), "不限");
//
//                        } else {
//                        p = cb.or(p, cb.like(root.get("subjectRequirement"), "%" + examSubj.get(i) + "%"));
//                        }

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

        List<UniMajorInfoBVO> list = BeanMapper.mapList(pageResult.getContent(), UniMajorInfoBVO.class);
        List<com.msg.UniMajorInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public List<UniMajorInfoBVO> findByUniMajorCodeIn(List<String> ids)
    {
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByUniMajorCodeIn( ids);
        if (inList != null) {
            for (UniMajorInfoB inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBVO.class));
            }
        }
        return voList;
    }

    @Override
    public void deleteByExamYear(String examYear) {
        imp.deleteByExamYear(examYear);
    }

    @Override
    public List<UniMajorInfoBVO> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear) {
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByUniMajorCodeAndExamYear(uniMajorCode, examYear);
        if (inList != null) {
            for (UniMajorInfoB inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBVO.class));
            }
        }
        return voList;
    }

    @Override
    public List<UniMajorInfoBVO> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear) {
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByUniMajorCodeAndMajorCodeAndExamYear(uniMajorCode, majorCode, examYear);
        if (inList != null) {
            for (UniMajorInfoB inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBVO.class));
            }
        }
        return voList;
    }

    @Override
    public Map<String, Object> listbyPage( Pageable pageable) {

        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<UniMajorInfoB> pageResult = null;

        pageResult = imp.findAll(pageable);


        List<UniMajorInfoBVO> list = BeanMapper.mapList(pageResult.getContent(), UniMajorInfoBVO.class);

        data.put("items", list);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }


    // 批量存储的方法
    @Override
    @Transactional
    public List<UniMajorInfoB> batchSave(List<UniMajorInfoB> entityList){
//        List<UniMajorInfoB> entityList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            entityList.add(BeanMapper.map(list.get(i), UniMajorInfoB.class));
//        }
        for (int i = 0; i < entityList.size(); i++) {
            if(i>0 && i % ConstDefine.BATCH_SIZE == 0){
                entityManager.flush(); // flush 触发事务 同步
                entityManager.clear(); // 清除缓存;  清除实体
            }
//            entityManager.persist(BeanMapper.map(list.get(i), UniMajorInfoB.class));
            entityManager.persist(entityList.get(i));
        }

        entityManager.flush(); // flush 触发事务 同步
        entityManager.clear(); // 清除缓存;  清除实体

        return entityList;
    }

    // 批量更新的方法
    @Override
    @Transactional
    public List<UniMajorInfoB> batchUpdate(List<UniMajorInfoB> list){
        for (int i = 0; i < list.size(); i++) {
            if(i>0 && i % ConstDefine.BATCH_SIZE == 0){
                entityManager.flush(); // flush 触发事务 同步
                entityManager.clear(); // 清除缓存;  清除实体
            }
            entityManager.merge(list.get(i));
        }

        entityManager.flush(); // flush 触发事务 同步
        entityManager.clear(); // 清除缓存;  清除实体

        return  list;
    }

    @Override
    public Integer findMaxVersion(String year){
//        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        Integer inList = imp.findMaxVersion(year);
        if(inList == null) return  0;
        return inList;
    }

    @Override
    public List<String> selectDistinctYear(){
        List<String> list = imp.selectDistinctYear();
        if(list == null) return new ArrayList<>();
        return list;
    }

    @Override
    public List<UniMajorInfoBVO> findByPiciAndExamYear(Integer pici, String examYear){
        List<UniMajorInfoBVO> voList = new ArrayList<>();
        List<UniMajorInfoB> inList = imp.findByPiciAndExamYear(pici, examYear);
        if (inList != null) {
            for (UniMajorInfoB inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBVO.class));
            }
        }
        return voList;
    }

    @Override
    public void deleteByIdIn(List<Long> ids){
        imp.deleteByIdIn(ids);
    }
}

