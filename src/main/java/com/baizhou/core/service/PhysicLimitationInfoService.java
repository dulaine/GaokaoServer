package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface PhysicLimitationInfoService {
    /**
     * 保存PhysicLimitationInfo
     * @param physicLimitationInfo
     * @return
     */
    PhysicLimitationInfo savePhysicLimitationInfo(PhysicLimitationInfoVO physicLimitationInfo);

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
    String updatePhysicLimitationInfo(PhysicLimitationInfoVO physicLimitationInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    PhysicLimitationInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<PhysicLimitationInfoVO> getAllPhysicLimitationInfos();
    /**
     *  根据Title2查询
     * @param title2
     * @return
     */
    public List<PhysicLimitationInfoVO> findByTitle2(String title2); 
    /**
     *  根据Title2查询
     * @param title2
     * @return
     */
    public List<PhysicLimitationInfoVO> findByTitle2Like(String title2); 
    /**
     *  根据Type查询
     * @param type
     * @return
     */
    public List<PhysicLimitationInfoVO> findByType(String type); 
    /**
     *  根据Type查询
     * @param type
     * @return
     */
    public List<PhysicLimitationInfoVO> findByTypeLike(String type); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Pageable pageable);

}
