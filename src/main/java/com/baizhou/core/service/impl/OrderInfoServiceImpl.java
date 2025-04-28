package com.baizhou.core.service.impl;

import com.baizhou.core.dao.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.service.*;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumHasSelLimit;
import com.baizhou.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class OrderInfoServiceImpl implements OrderInfoService {


    @Autowired
    private OrderInfoRepo imp;

    /**
     * 保存
     *
     * @param orderInfo
     * @return
     */
    @Override
    public OrderInfo saveOrderInfo(OrderInfoVO orderInfo) {

        return imp.save(BeanMapper.map(orderInfo, OrderInfo.class));
    }

    /**
     * 删除单条记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        OrderInfo entity = imp.findById(id).orElse(null);
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
    public OrderInfoVO findOneById(Long id) {
        OrderInfo entity = imp.findById(id).orElse(null);
        if (entity == null) return null;
        return BeanMapper.map(entity, OrderInfoVO.class);
    }

    /**
     * 获取所有信息
     */
    @Override
    public List<OrderInfoVO> getAllOrderInfos() {
        return BeanMapper.mapList(imp.findAll(), OrderInfoVO.class);
    }

    /**
     * 更新记录
     *
     * @param orderInfoVO
     * @return
     */
    @Override
    public String updateOrderInfo(OrderInfoVO orderInfoVO) {
        OrderInfo orderInfo = BeanMapper.map(orderInfoVO, OrderInfo.class);
        imp.save(orderInfo);
        return "success";
    }

    /**
     * 通过Name的like查询
     *
     * @param name
     * @return
     */
    @Override
    public List<OrderInfoVO> findByName(String name) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByName(name);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Name的like查询
     *
     * @param name
     * @return
     */
    @Override
    public List<OrderInfoVO> findByNameLike(String name) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByNameLike("%" + name + "%");
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Tel的like查询
     *
     * @param tel
     * @return
     */
    @Override
    public List<OrderInfoVO> findByTel(String tel) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByTel(tel);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Tel的like查询
     *
     * @param tel
     * @return
     */
    @Override
    public List<OrderInfoVO> findByTelLike(String tel) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByTelLike("%" + tel + "%");
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过Status的like查询
     *
     * @param status
     * @return
     */
    @Override
    public List<OrderInfoVO> findByStatus(Integer status) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByStatus(status);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过CreatorName的like查询
     *
     * @param creatorName
     * @return
     */
    @Override
    public List<OrderInfoVO> findByCreatorName(String creatorName) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByCreatorName(creatorName);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过CreatorName的like查询
     *
     * @param creatorName
     * @return
     */
    @Override
    public List<OrderInfoVO> findByCreatorNameLike(String creatorName) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByCreatorNameLike("%" + creatorName + "%");
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过AssignedTeacherId的like查询
     *
     * @param assignedTeacherId
     * @return
     */
    @Override
    public List<OrderInfoVO> findByAssignedTeacherId(Long assignedTeacherId) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByAssignedTeacherId(assignedTeacherId);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过AssignedTeacher的like查询
     *
     * @param assignedTeacher
     * @return
     */
    @Override
    public List<OrderInfoVO> findByAssignedTeacher(String assignedTeacher) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByAssignedTeacher(assignedTeacher);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过AssignedTeacher的like查询
     *
     * @param assignedTeacher
     * @return
     */
    @Override
    public List<OrderInfoVO> findByAssignedTeacherLike(String assignedTeacher) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByAssignedTeacherLike("%" + assignedTeacher + "%");
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
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
    public List<OrderInfoVO> findByProvince(String province) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByProvince(province);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
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
    public List<OrderInfoVO> findByProvinceLike(String province) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByProvinceLike("%" + province + "%");
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过ExamYear的like查询
     *
     * @param examYear
     * @return
     */
    @Override
    public List<OrderInfoVO> findByExamYear(String examYear) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByExamYear(examYear);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 通过ExamYear的like查询
     *
     * @param examYear
     * @return
     */
    @Override
    public List<OrderInfoVO> findByExamYearLike(String examYear) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByExamYearLike("%" + examYear + "%");
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     *  通过HasSelPhysicLimit的like查询
     * @param hasSelPhysicLimit
     * @return
     */
    @Override
    public List<OrderInfoVO> findByHasSelPhysicLimit(Integer hasSelPhysicLimit){
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByHasSelPhysicLimit(hasSelPhysicLimit);
        if(  inList != null){
            for (OrderInfo inf:inList) {
                voList.add(BeanMapper.map(inf,OrderInfoVO.class));
            }
        }
        return  voList;
    }

    /**
     * 通过IsDelete的like查询
     *
     * @param isDelete
     * @return
     */
    @Override
    public List<OrderInfoVO> findByIsDelete(Integer isDelete) {
        List<OrderInfoVO> voList = new ArrayList<>();
        List<OrderInfo> inList = imp.findByIsDelete(isDelete);
        if (inList != null) {
            for (OrderInfo inf : inList) {
                voList.add(BeanMapper.map(inf, OrderInfoVO.class));
            }
        }
        return voList;
    }

    /**
     * 查询页面信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listbyPage(String name, String tel, Integer status, Long assignedTeacherId, String authorTeacherIds, String authorTeacherIds2, String province, String examYear, Integer isDelete,  Integer hasSelPhysicLimit, Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<OrderInfo> pageResult = null;
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(tel) && StringUtils.isEmpty(status) && StringUtils.isEmpty(assignedTeacherId) && StringUtils.isEmpty(authorTeacherIds) && StringUtils.isEmpty(authorTeacherIds2) && StringUtils.isEmpty(province)
                && StringUtils.isEmpty(examYear) && StringUtils.isEmpty(isDelete)&& StringUtils.isEmpty(hasSelPhysicLimit)) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<OrderInfo> spec = (Specification<OrderInfo>) (root, query, cb) -> {

                if (!StringUtils.isEmpty(name)) {
                    String[] nameSplits = name.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < nameSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("name"), "%" + nameSplits[i] + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("name"), "%" + nameSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(tel)) {
                    String[] telSplits = tel.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < telSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("tel"), "%" + telSplits[i] + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("tel"), "%" + telSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(status)) {
                    predicates.add(cb.equal(root.get("status"), status));
                }

                if (!StringUtils.isEmpty(assignedTeacherId)) {
                    predicates.add(cb.equal(root.get("assignedTeacherId"), assignedTeacherId));
                }

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
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(authorTeacherIds2)) {
                    String[] authorTeacherIdsSplits = authorTeacherIds2.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < authorTeacherIdsSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("authorTeacherIds"), "%" + ConstDefine.BracketL + authorTeacherIdsSplits[i] + ConstDefine.BracketR + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("authorTeacherIds"), "%" + ConstDefine.BracketL + authorTeacherIdsSplits[i] + ConstDefine.BracketR + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(province)) {
                    String[] provinceSplits = province.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < provinceSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("province"), "%" + provinceSplits[i] + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("province"), "%" + provinceSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(examYear)) {
                    String[] examYearSplits = examYear.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < examYearSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("examYear"), "%" + examYearSplits[i] + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("examYear"), "%" + examYearSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(isDelete)) {
                    predicates.add(cb.equal(root.get("isDelete"), isDelete));
                }


                if (!StringUtils.isEmpty(hasSelPhysicLimit)) {
                    if(hasSelPhysicLimit == EnumHasSelLimit.Selected.getStateID()){
                        predicates.add(cb.notEqual(root.get("hasSelPhysicLimit"), EnumHasSelLimit.SelLimit.getStateID()));
                    }else {
                        predicates.add(cb.equal(root.get("hasSelPhysicLimit"), hasSelPhysicLimit));
                    }

                }


                return cb.and(predicates.toArray(new Predicate[]{}));
            };
            pageResult = imp.findAll(spec, pageable);
        }

        List<OrderInfoVO> list = BeanMapper.mapList(pageResult.getContent(), OrderInfoVO.class);
        List<com.msg.OrderInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }


    /**
     * 查询页面信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listTelAndValidTeacherbyPage(String tel, String authorTeacherIds, Integer isDelete, Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<OrderInfo> pageResult = null;
        if (StringUtils.isEmpty(tel) && StringUtils.isEmpty(authorTeacherIds) && StringUtils.isEmpty(isDelete)) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<OrderInfo> spec = (Specification<OrderInfo>) (root, query, cb) -> {

                Predicate p = null;
                if (!StringUtils.isEmpty(tel)) {
                    String[] telSplits = tel.split("\\|");

                    for (int i = 0; i < telSplits.length; i++) {
                        if (p == null) {
                            p = cb.like(root.get("tel"), "%" + telSplits[i] + "%");
                        } else {
                            p = cb.or(p, cb.like(root.get("tel"), "%" + telSplits[i] + "%"));
                        }
                    }
//                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(authorTeacherIds)) {
                    String[] authorTeacherIdsSplits = authorTeacherIds.split("\\|");
                    for (int i = 0; i < authorTeacherIdsSplits.length; i++) {
                        if (p == null) {
                            p = cb.equal(root.get("authorTeacherIds"), "" + authorTeacherIdsSplits[i] + "");
                        } else {
                            p = cb.or(p, cb.equal(root.get("authorTeacherIds"), "" + authorTeacherIdsSplits[i] + ""));
                        }
                    }
                }
                predicates.add(p);

                if (!StringUtils.isEmpty(isDelete)) {
                    predicates.add(cb.equal(root.get("isDelete"), isDelete));
                }


                return cb.and(predicates.toArray(new Predicate[]{}));
            };
            pageResult = imp.findAll(spec, pageable);
        }

        List<OrderInfoVO> list = BeanMapper.mapList(pageResult.getContent(), OrderInfoVO.class);
        List<com.msg.OrderInfo> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }


}
