package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface MajorInfoY1Service {
    /**
     * 保存MajorInfoY1
     * @param majorInfoY1
     * @return
     */
    MajorInfoY1 saveMajorInfoY1(MajorInfoY1VO majorInfoY1);

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
    String updateMajorInfoY1(MajorInfoY1VO majorInfoY1VO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    MajorInfoY1VO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<MajorInfoY1VO> getAllMajorInfoY1s();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoY1VO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoY1VO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<MajorInfoY1VO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoY1VO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoY1VO> findBySchoolNameLike(String schoolName); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String uniMajorCode, Integer pici, String schoolName, Pageable pageable);


    public List<MajorInfoY1VO> findBySchoolNameAndPici(String schoolName, Integer pici);
}
