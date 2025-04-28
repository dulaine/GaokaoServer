package com.baizhou.core.service.impl;

import com.baizhou.core.dao.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.service.*;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;
import java.util.*;


@Service
public class UsersResServiceImpl  implements UsersResService {


    @Autowired
    private UsersResRepo imp;
    /**
     * 保存
     * @param usersRes
     * @return
     */
    @Override
    public UsersRes saveUsersRes(UsersResVO usersRes) {

        return imp.save(BeanMapper.map(usersRes, UsersRes.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        UsersRes entity = imp.findById(id).orElse(null);
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
    public UsersResVO findOneById(Long id) {
        UsersRes entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, UsersResVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<UsersResVO> getAllUsersRess() {
        return BeanMapper.mapList(imp.findAll(), UsersResVO.class);
    }
    /**
     * 更新记录
     * @param usersResVO
     * @return
     */
    @Override
    public String updateUsersRes(UsersResVO usersResVO) {
        UsersRes usersRes = BeanMapper.map(usersResVO, UsersRes.class);
        imp.save(usersRes);
        return "success";
    }

     /**
     *  通过Tel的like查询
     * @param tel
     * @return
     */
    @Override
  public List<UsersResVO> findByTel(String tel){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByTel(tel);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
  }

    @Override
    public List<UsersResVO> findByTelAndIsDelete(String tel, Integer isDelete) {
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByTelAndIsDelete(tel, isDelete);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
    }


     /**
     *  通过Tel的like查询
     * @param tel
     * @return
     */
    @Override
  public List<UsersResVO> findByTelLike(String tel){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByTelLike("%"+tel+"%");
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Name的like查询
     * @param name
     * @return
     */
    @Override
  public List<UsersResVO> findByName(String name){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByName(name);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Name的like查询
     * @param name
     * @return
     */
    @Override
  public List<UsersResVO> findByNameLike(String name){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByNameLike("%"+name+"%");
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Role的like查询
     * @param role
     * @return
     */
    @Override
  public List<UsersResVO> findByRole(Integer role){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByRole(role);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
  }

    @Override
    public List<UsersResVO> findByRoleIsNot(Integer role){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByRoleIsNot(role);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
    }

     /**
     *  通过Token的like查询
     * @param token
     * @return
     */
    @Override
  public List<UsersResVO> findByToken(String token){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByToken(token);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Token的like查询
     * @param token
     * @return
     */
    @Override
  public List<UsersResVO> findByTokenLike(String token){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByTokenLike("%"+token+"%");
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
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
  public List<UsersResVO> findByIsDelete(Integer isDelete){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByIsDelete(isDelete);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
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
  public List<UsersResVO> findByExamYear(String examYear){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByExamYear(examYear);
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
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
  public List<UsersResVO> findByExamYearLike(String examYear){
        List<UsersResVO> voList = new ArrayList<>();
        List<UsersRes> inList = imp.findByExamYearLike("%"+examYear+"%");
        if(  inList != null){
            for (UsersRes inf:inList) {
                voList.add(BeanMapper.map(inf,UsersResVO.class));
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
    public Map<String, Object> listbyPage(String tel,String name,Integer role,Integer isDelete,String examYear,Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<UsersRes> pageResult = null;
//        if (StringUtils.isEmpty(tel)  && StringUtils.isEmpty(name)  && StringUtils.isEmpty(role)  && StringUtils.isEmpty(isDelete)  && StringUtils.isEmpty(examYear) ) {
//            pageResult = imp.findAll(pageable);
//        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<UsersRes> spec = (Specification<UsersRes>) (root, query, cb) -> {
                    
                if (!StringUtils.isEmpty(tel)) {
                   String[] telSplits = tel.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < telSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("tel"), "%" + telSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("tel"), "%" + telSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(name)) {
                   String[] nameSplits = name.split("\\|");
                    Predicate p = null;
                    for (int i = 0; i < nameSplits.length; i++) {
                        if(p == null){
                            p = cb.like(root.get("name"), "%" + nameSplits[i] + "%");
                        }else {
                            p = cb.or(p, cb.like(root.get("name"), "%" + nameSplits[i] + "%"));
                        }
                    }
                    predicates.add(p);
                }

                if (!StringUtils.isEmpty(role)) {
                    predicates.add(cb.equal(root.get("role"), role));
                }else {
                    //排除客户信息
                    predicates.add(cb.notEqual(root.get("role"), EnumRoleType.Client.getStateID()));
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
//        }

        List<UsersResVO> list = BeanMapper.mapList(pageResult.getContent(), UsersResVO.class);
        List<com.msg.UsersRes> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }
}
