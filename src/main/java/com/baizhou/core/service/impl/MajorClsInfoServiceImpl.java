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
public class MajorClsInfoServiceImpl  implements MajorClsInfoService {


    @Autowired
    private MajorClsInfoRepo imp;
    /**
     * 保存
     * @param majorClsInfo
     * @return
     */
    @Override
    public MajorClsInfo saveMajorClsInfo(MajorClsInfoVO majorClsInfo) {

        return imp.save(BeanMapper.map(majorClsInfo, MajorClsInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        MajorClsInfo entity = imp.findById(id).orElse(null);
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
    public MajorClsInfoVO findOneById(Long id) {
        MajorClsInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, MajorClsInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<MajorClsInfoVO> getAllMajorClsInfos() {
        return BeanMapper.mapList(imp.findAll(), MajorClsInfoVO.class);
    }
    /**
     * 更新记录
     * @param majorClsInfoVO
     * @return
     */
    @Override
    public String updateMajorClsInfo(MajorClsInfoVO majorClsInfoVO) {
        MajorClsInfo majorClsInfo = BeanMapper.map(majorClsInfoVO, MajorClsInfo.class);
        imp.save(majorClsInfo);
        return "success";
    }

     /**
     *  通过Major3rdCls的like查询
     * @param major3rdCls
     * @return
     */
    @Override
  public List<MajorClsInfoVO> findByMajor3rdCls(String major3rdCls){
        List<MajorClsInfoVO> voList = new ArrayList<>();
        List<MajorClsInfo> inList = imp.findByMajor3rdCls(major3rdCls);
        if(  inList != null){
            for (MajorClsInfo inf:inList) {
                voList.add(BeanMapper.map(inf,MajorClsInfoVO.class));
            }
        }
        return  voList;
  }

     /**
     *  通过Major3rdCls的like查询
     * @param major3rdCls
     * @return
     */
    @Override
  public List<MajorClsInfoVO> findByMajor3rdClsLike(String major3rdCls){
        List<MajorClsInfoVO> voList = new ArrayList<>();
        List<MajorClsInfo> inList = imp.findByMajor3rdClsLike("%"+major3rdCls+"%");
        if(  inList != null){
            for (MajorClsInfo inf:inList) {
                voList.add(BeanMapper.map(inf,MajorClsInfoVO.class));
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
    public Map<String, Object> listbyPage(Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<MajorClsInfo> pageResult = null;
        if (true) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<MajorClsInfo> spec = (Specification<MajorClsInfo>) (root, query, cb) -> {
                    
                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<MajorClsInfoVO> list = BeanMapper.mapList(pageResult.getContent(), MajorClsInfoVO.class);
//        List<com.msg.MajorClsInfo> protoList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            protoList.add(list.get(i).ConvertToDTO());
//        }
//        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public List<MajorClsInfoVO> findByMajor1stClsAndMajor2ndClsAndMajor3rdCls(String major1,String major2, String major3rdCls){
        List<MajorClsInfoVO> voList = new ArrayList<>();
        List<MajorClsInfo> inList = imp.findByMajor1stClsAndMajor2ndClsAndMajor3rdCls(major1, major2, major3rdCls);
        if(  inList != null){
            for (MajorClsInfo inf:inList) {
                voList.add(BeanMapper.map(inf,MajorClsInfoVO.class));
            }
        }
        return  voList;
    }
}
