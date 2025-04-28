package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface LatestVerInfoService {
    /**
     * 保存LatestVerInfo
     * @param latestVerInfo
     * @return
     */
    LatestVerInfo saveLatestVerInfo(LatestVerInfoVO latestVerInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteByKeyId(Long id);


    /**
     * 删除所有
     * @return
     */
    void deleteAll();

    /**
    * 更新
    * */
    String updateLatestVerInfo(LatestVerInfoVO latestVerInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    LatestVerInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<LatestVerInfoVO> getAllLatestVerInfos();
    /**
     *  根据Year查询
     * @param year
     * @return
     */
    public List<LatestVerInfoVO> findByYear(String year); 
    /**
     *  根据Year查询
     * @param year
     * @return
     */
    public List<LatestVerInfoVO> findByYearLike(String year); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<LatestVerInfoVO> findByPici(Integer pici); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String year, Integer pici, Pageable pageable);

    public LatestVerInfoVO findByPiciAndYear(Integer pici, String year);
}
