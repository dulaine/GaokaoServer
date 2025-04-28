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
public class ScoreToRankInfoServiceImpl  implements ScoreToRankInfoService {


    @Autowired
    private ScoreToRankInfoRepo imp;
    /**
     * 保存
     * @param scoreToRankInfo
     * @return
     */
    @Override
    public ScoreToRankInfo saveScoreToRankInfo(ScoreToRankInfoVO scoreToRankInfo) {

        return imp.save(BeanMapper.map(scoreToRankInfo, ScoreToRankInfo.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        ScoreToRankInfo entity = imp.findById(id).orElse(null);
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
    public ScoreToRankInfoVO findOneById(Long id) {
        ScoreToRankInfo entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, ScoreToRankInfoVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<ScoreToRankInfoVO> getAllScoreToRankInfos() {
//        return BeanMapper.mapList(imp.findAll(), ScoreToRankInfoVO.class);
        return BeanMapper.mapList(imp.findAllByOrderByScoreDesc(), ScoreToRankInfoVO.class);
    }
    /**
     * 更新记录
     * @param scoreToRankInfoVO
     * @return
     */
    @Override
    public String updateScoreToRankInfo(ScoreToRankInfoVO scoreToRankInfoVO) {
        ScoreToRankInfo scoreToRankInfo = BeanMapper.map(scoreToRankInfoVO, ScoreToRankInfo.class);
        imp.save(scoreToRankInfo);
        return "success";
    }

     /**
     *  通过ExamYear的like查询
     * @param examYear
     * @return
     */
    @Override
  public List<ScoreToRankInfoVO> findByExamYear(String examYear){
        List<ScoreToRankInfoVO> voList = new ArrayList<>();
        List<ScoreToRankInfo> inList = imp.findByExamYearOrderByScoreDesc(examYear);
        if(  inList != null){
            for (ScoreToRankInfo inf:inList) {
                voList.add(BeanMapper.map(inf,ScoreToRankInfoVO.class));
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
  public List<ScoreToRankInfoVO> findByExamYearLike(String examYear){
        List<ScoreToRankInfoVO> voList = new ArrayList<>();
        List<ScoreToRankInfo> inList = imp.findByExamYearLike("%"+examYear+"%");
        if(  inList != null){
            for (ScoreToRankInfo inf:inList) {
                voList.add(BeanMapper.map(inf,ScoreToRankInfoVO.class));
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
        org.springframework.data.domain.Page<ScoreToRankInfo> pageResult = null;
        if (true) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<ScoreToRankInfo> spec = (Specification<ScoreToRankInfo>) (root, query, cb) -> {
                    
                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<ScoreToRankInfoVO> list = BeanMapper.mapList(pageResult.getContent(), ScoreToRankInfoVO.class);
        List<ScoreToRankInfoVO> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }

    @Override
    public void deleteByExamYear(String examYear) {
        imp.deleteByExamYear(examYear);
    }
}
