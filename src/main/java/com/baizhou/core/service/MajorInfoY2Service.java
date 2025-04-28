package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface MajorInfoY2Service {
    /**
     * 保存MajorInfoY2
     * @param majorInfoY2
     * @return
     */
    MajorInfoY2 saveMajorInfoY2(MajorInfoY2VO majorInfoY2);

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
    String updateMajorInfoY2(MajorInfoY2VO majorInfoY2VO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    MajorInfoY2VO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<MajorInfoY2VO> getAllMajorInfoY2s();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoY2VO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoY2VO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<MajorInfoY2VO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoY2VO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoY2VO> findBySchoolNameLike(String schoolName); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String uniMajorCode, Integer pici, String schoolName, Pageable pageable);


    public List<MajorInfoY2VO> findBySchoolNameAndPici(String schoolName, Integer pici);

}
