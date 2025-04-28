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
public class gameDataRecordServiceImpl  implements gameDataRecordService {


    @Autowired
    private gameDataRecordRepo imp;
    /**
     * 保存
     * @param gameDataRecord
     * @return
     */
    @Override
    public gameDataRecord savegameDataRecord(gameDataRecordVO gameDataRecord) {

        return imp.save(BeanMapper.map(gameDataRecord, gameDataRecord.class));
    }
    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteByKeyId(Long id) {
        gameDataRecord entity = imp.findById(id).orElse(null);
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
    public gameDataRecordVO findOneById(Long id) {
        gameDataRecord entity = imp.findById(id).orElse(null);
        if(entity == null) return null;
        return  BeanMapper.map(entity, gameDataRecordVO.class);
    }
    /**
    获取所有信息
     */
    @Override
    public List<gameDataRecordVO> getAllgameDataRecords() {
        return BeanMapper.mapList(imp.findAll(), gameDataRecordVO.class);
    }
    /**
     * 更新记录
     * @param gameDataRecordVO
     * @return
     */
    @Override
    public String updategameDataRecord(gameDataRecordVO gameDataRecordVO) {
        gameDataRecord gameDataRecord = BeanMapper.map(gameDataRecordVO, gameDataRecord.class);
        imp.save(gameDataRecord);
        return "success";
    }

    /**
     * 查询页面信息
     * @param pageable
     * @return
     */
    @Override
    public Map<String, Object> listbyPage(Pageable pageable) {
        Map<String, Object> data = new HashMap<>();
        org.springframework.data.domain.Page<gameDataRecord> pageResult = null;
        if (true) {
            pageResult = imp.findAll(pageable);
        } else {
            List<Predicate> predicates = new ArrayList<>();
            Specification<gameDataRecord> spec = (Specification<gameDataRecord>) (root, query, cb) -> {
                    
                
                return cb.and(predicates.toArray(new Predicate[]{}));
            };
             pageResult = imp.findAll(spec, pageable);
        }

        List<gameDataRecordVO> list = BeanMapper.mapList(pageResult.getContent(), gameDataRecordVO.class);
        List<gameDataRecordVO> protoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            protoList.add(list.get(i).ConvertToDTO());
        }
        data.put("items", protoList);
        data.put("total", pageResult.getTotalElements());////所有信息总数
        data.put("totalPage", pageResult.getTotalPages());//所有page总页数
        return data;
    }
}
