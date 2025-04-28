package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface MajorInfoY3Service {
    /**
     * 保存MajorInfoY3
     * @param majorInfoY3
     * @return
     */
    MajorInfoY3 saveMajorInfoY3(MajorInfoY3VO majorInfoY3);

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
    String updateMajorInfoY3(MajorInfoY3VO majorInfoY3VO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    MajorInfoY3VO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<MajorInfoY3VO> getAllMajorInfoY3s();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoY3VO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoY3VO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<MajorInfoY3VO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoY3VO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoY3VO> findBySchoolNameLike(String schoolName); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String uniMajorCode, Integer pici, String schoolName, Pageable pageable);

    public List<MajorInfoY3VO> findBySchoolNameAndPici(String schoolName, Integer pici);
}
