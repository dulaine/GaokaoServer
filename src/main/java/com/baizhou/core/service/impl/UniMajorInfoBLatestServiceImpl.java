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


@Service
public class UniMajorInfoBLatestServiceImpl implements UniMajorInfoBLatestService {


    @Autowired
    private UniMajorInfoBLatestRepo imp;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存
     *
     * @param uniMajorInfoALatest
     * @return
     */
    @Override
    public UniMajorInfoBLatest saveUniMajorInfoBLatest(UniMajorInfoBLatestVO uniMajorInfoALatest) {

        return imp.save(BeanMapper.map(uniMajorInfoALatest, UniMajorInfoBLatest.class));
    }

    /**
     * 删除单条记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        UniMajorInfoBLatest entity = imp.findById(id).orElse(null);
        if (entity == null) return false;
        imp.delete(entity);
        return true;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    @Override
    public void deleteAll() {
        imp.deleteAll();
    }

    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @Override
    public UniMajorInfoBLatestVO findOneById(Long id) {
        UniMajorInfoBLatest entity = imp.findById(id).orElse(null);
        if (entity == null) return null;
        return BeanMapper.map(entity, UniMajorInfoBLatestVO.class);
    }

    @Override
    public List<UniMajorInfoBLatestVO> findByIdIn(List<Long> id) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByIdIn(id);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 获取所有信息
     */
    @Override
    public List<UniMajorInfoBLatestVO> getAllUniMajorInfoBLatests() {
        return BeanMapper.mapList(imp.findAll(), UniMajorInfoBLatestVO.class);
    }

    /**
     * 更新记录
     *
     * @param uniMajorInfoALatestVO
     * @return
     */
    @Override
    public String updateUniMajorInfoBLatest(UniMajorInfoBLatestVO uniMajorInfoALatestVO) {
        UniMajorInfoBLatest uniMajorInfoALatest = BeanMapper.map(uniMajorInfoALatestVO, UniMajorInfoBLatest.class);
        imp.save(uniMajorInfoALatest);
        return "success";
    }

    /**
     * 通过UniMajorCode的like查询
     *
     * @param uniMajorCode
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByUniMajorCode(String uniMajorCode) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByUniMajorCode(uniMajorCode);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过UniMajorCode的like查询
     *
     * @param uniMajorCode
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByUniMajorCodeLike(String uniMajorCode) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByUniMajorCodeLike("%" + uniMajorCode + "%");
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Pici的like查询
     *
     * @param pici
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByPici(Integer pici) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByPici(pici);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolName的like查询
     *
     * @param schoolName
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolName(String schoolName) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolName(schoolName);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolName的like查询
     *
     * @param schoolName
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolNameLike(String schoolName) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolNameLike("%" + schoolName + "%");
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolLvl1的like查询
     *
     * @param schoolLvl1
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolLvl1(Integer schoolLvl1) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolLvl1(schoolLvl1);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolLvl2的like查询
     *
     * @param schoolLvl2
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolLvl2(Integer schoolLvl2) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolLvl2(schoolLvl2);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolLvl3的like查询
     *
     * @param schoolLvl3
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolLvl3(Integer schoolLvl3) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolLvl3(schoolLvl3);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolType的like查询
     *
     * @param schoolType
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolType(String schoolType) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolType(schoolType);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过SchoolType的like查询
     *
     * @param schoolType
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findBySchoolTypeLike(String schoolType) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findBySchoolTypeLike("%" + schoolType + "%");
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Province的like查询
     *
     * @param province
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByProvince(String province) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByProvince(province);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Province的like查询
     *
     * @param province
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByProvinceLike(String province) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByProvinceLike("%" + province + "%");
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过MajorName的like查询
     *
     * @param majorName
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByMajorName(String majorName) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByMajorName(majorName);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过MajorName的like查询
     *
     * @param majorName
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByMajorNameLike(String majorName) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByMajorNameLike("%" + majorName + "%");
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }


    /**
     *  通过IsZhongWai的like查询
     * @param isZhongWai
     * @return
     */
    @Override
    public List<UniMajorInfoBLatestVO> findByIsZhongWai(Integer isZhongWai){
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByIsZhongWai(isZhongWai);
        if(  inList != null){
            for (UniMajorInfoBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBLatestVO.class));
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
    public List<UniMajorInfoBLatestVO> findByIsBenSuo(Integer isBenSuo){
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByIsBenSuo(isBenSuo);
        if(  inList != null){
            for (UniMajorInfoBLatest inf:inList) {
                voList.add(BeanMapper.map(inf,UniMajorInfoBLatestVO.class));
            }
        }
        return  voList;
    }

    /**
     * 查询页面信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listbyPage(Integer pici, List<String> uniMajorCode,
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
                                          Pageable pageable) {

//        boolean isMajorCode = false;
//        if(majorName.size() > 0){
//            String tempName =  majorName.get(0);
//            isMajorCode = CommonUtil.isNumeric(tempName);
//        }
//        boolean isMajor2Code = false;
//        if(majorName2.size() > 0){
//            String tempName =  majorName2.get(0);
//            isMajor2Code = CommonUtil.isNumeric(tempName);
//        }

//    public Map<String, Object> listbyPage(String uniMajorCode,Integer pici,String schoolName,Integer schoolLvl1,Integer schoolLvl2,Integer schoolLvl3,String schoolType,String province,String majorName,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<UniMajorInfoBLatest> pageResult = null;
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
//            boolean finalIsUniMajorCode =  isUniMajorCode;
//            boolean finalIsMajorCode =  isMajorCode;
//            boolean finalIsMajor2Code = isMajor2Code;
            List<Predicate> predicates = new ArrayList<>();
            Specification<UniMajorInfoBLatest> spec = (Specification<UniMajorInfoBLatest>) (root, query, cb) -> {

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

//                if (majorName.size() > 0) {
////                    String[] majorNameSplits = majorName.split("\\|");
//                    Predicate p = null;
//                    Expression<String> expression = root.get("majorName");
//
//                    Expression<String> expression2 = root.get("uniMajorCode");
//                    for (int i = 0; i < majorName.size(); i++) {
//                        String mName = majorName.get(i);
//
//                        Predicate tempP = null;
//                        if (!CommonUtil.hasNumeric(mName)) {
//                            tempP = cb.like(expression, "%" + mName + "%");
//                        }else {
//                            tempP = cb.like(expression2, "%" + mName + "%");
//                        }
//
//                        if (majorName2.size() > 0) {
//                            for (int j = 0; j < majorName2.size(); j++) {
//                                String mName2 = majorName2.get(j);
//                                Predicate tempP2 = cb.and(tempP, cb.like(expression, "%" + mName2 + "%")); //专业2
//                                if (p == null) {
//                                    p = tempP2;
//                                } else {
//                                    p = cb.or(p, tempP2);//
//                                }
//                            }
//                        } else {
//                            if (p == null) {
//                                p = tempP;
//                            } else {
//                                p = cb.or(p, tempP);
//                            }
//                        }
//                    }
//                    predicates.add(p);
//                }
                if (majorName.size() > 0) {
                    Predicate p = null;
//                    Expression<String> expression = root.get("majorName");
                    Expression<String> expression = root.get("majorGroupMajorName");
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
                    p = cb.equal(expression, ConstDefine.UnLimit);
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

                query.groupBy(root.get("uniMajorCode"));
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
            pageResult = imp.findAll(spec, pageable);
//            pageResult = imp.findPage(spec, pageable);
        }

        List<UniMajorInfoBLatestVO> list = BeanMapper.mapList(pageResult.getContent(), UniMajorInfoBLatestVO.class);
//        List<com.msg.UniMajorInfo> protoList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            protoList.add(list.get(i).ConvertToDTO());
//        }
        data.put("items", list);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public List<UniMajorInfoBLatestVO> findByUniMajorCodeIn(List<String> ids) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByUniMajorCodeIn(ids);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }
    @Override
    public void deleteByExamYear(String examYear) {
        imp.deleteByExamYear(examYear);
    }

    @Override
    public List<UniMajorInfoBLatestVO> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByUniMajorCodeAndExamYear(uniMajorCode, examYear);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    @Override
    public List<UniMajorInfoBLatestVO> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear)
    {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByHistMajorIdsAndExamYear( histDBIds, examYear);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }


    @Override
    public List<UniMajorInfoBLatestVO> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear) {
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByUniMajorCodeAndMajorCodeAndExamYear(uniMajorCode, majorCode, examYear);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }


    @Override
    public Map<String, Object> listbyPage( Pageable pageable) {

        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<UniMajorInfoBLatest> pageResult = null;

          pageResult = imp.findAll(pageable);


        List<UniMajorInfoBLatestVO> list = BeanMapper.mapList(pageResult.getContent(), UniMajorInfoBLatestVO.class);

        data.put("items", list);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public List<UniMajorInfoBLatestVO> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear){
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByUniMajorCodeInAndExamYear(uniMajorCodes, examYear);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    // 批量存储的方法
    @Override
    @Transactional
    public List<UniMajorInfoBLatest> batchSave(List<UniMajorInfoBLatest> list){
        for (int i = 0; i < list.size(); i++) {
            if(i>0 && i % ConstDefine.BATCH_SIZE == 0){
                entityManager.flush(); // flush 触发事务 同步
                entityManager.clear(); // 清除缓存;  清除实体
            }
            entityManager.persist(list.get(i));
        }

        entityManager.flush(); // flush 触发事务 同步
        entityManager.clear(); // 清除缓存;  清除实体

        return list;
    }

    // 批量更新的方法
    @Override
    @Transactional
    public List<UniMajorInfoBLatest> batchUpdate(List<UniMajorInfoBLatest> list){
        for (int i = 0; i < list.size(); i++) {
            if(i>0 && i % ConstDefine.BATCH_SIZE == 0){
                entityManager.flush(); // flush 触发事务 同步
                entityManager.clear(); // 清除缓存;  清除实体
            }
            entityManager.merge(list.get(i));
        }

        entityManager.flush(); // flush 触发事务 同步
        entityManager.clear(); // 清除缓存;  清除实体
        return list;
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
    public List<UniMajorInfoBLatestVO> findByPiciAndExamYear(Integer pici, String examYear){
        List<UniMajorInfoBLatestVO> voList = new ArrayList<>();
        List<UniMajorInfoBLatest> inList = imp.findByPiciAndExamYear(pici, examYear);
        if (inList != null) {
            for (UniMajorInfoBLatest inf : inList) {
                voList.add(BeanMapper.map(inf, UniMajorInfoBLatestVO.class));
            }
        }
        return voList;
    }

    @Override
    public void deleteByIdIn(List<Long> ids){
        imp.deleteByIdIn(ids);
    }
}

