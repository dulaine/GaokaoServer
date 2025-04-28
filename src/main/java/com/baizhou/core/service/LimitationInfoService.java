package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface LimitationInfoService {
    /**
     * 保存LimitationInfo
     * @param limitationInfo
     * @return
     */
    LimitationInfo saveLimitationInfo(LimitationInfoVO limitationInfo);

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
    String updateLimitationInfo(LimitationInfoVO limitationInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    LimitationInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<LimitationInfoVO> getAllLimitationInfos();
    /**
     *  根据Content查询
     * @param content
     * @return
     */
    public List<LimitationInfoVO> findByContent(String content); 
    /**
     *  根据Content查询
     * @param content
     * @return
     */
    public List<LimitationInfoVO> findByContentLike(String content); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Pageable pageable);

}
