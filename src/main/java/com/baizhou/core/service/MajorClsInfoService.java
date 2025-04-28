package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface MajorClsInfoService {
    /**
     * 保存MajorClsInfo
     * @param majorClsInfo
     * @return
     */
    MajorClsInfo saveMajorClsInfo(MajorClsInfoVO majorClsInfo);

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
    String updateMajorClsInfo(MajorClsInfoVO majorClsInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    MajorClsInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<MajorClsInfoVO> getAllMajorClsInfos();
    /**
     *  根据Major3rdCls查询
     * @param major3rdCls
     * @return
     */
    public List<MajorClsInfoVO> findByMajor3rdCls(String major3rdCls); 
    /**
     *  根据Major3rdCls查询
     * @param major3rdCls
     * @return
     */
    public List<MajorClsInfoVO> findByMajor3rdClsLike(String major3rdCls); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Pageable pageable);


    public List<MajorClsInfoVO> findByMajor1stClsAndMajor2ndClsAndMajor3rdCls(String major1,String major2, String major3rdCls);
}
