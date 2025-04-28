package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface MajorInfoYAllService {
    /**
     * 保存MajorInfoYAll
     * @param majorInfoYAll
     * @return
     */
    MajorInfoYAll saveMajorInfoYAll(MajorInfoYAllVO majorInfoYAll);

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
    String updateMajorInfoYAll(MajorInfoYAllVO majorInfoYAllVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    MajorInfoYAllVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<MajorInfoYAllVO> getAllMajorInfoYAlls();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoYAllVO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<MajorInfoYAllVO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<MajorInfoYAllVO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoYAllVO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<MajorInfoYAllVO> findBySchoolNameLike(String schoolName); 
    /**
     *  根据Year查询
     * @param year
     * @return
     */
    public List<MajorInfoYAllVO> findByYear(String year); 
    /**
     *  根据Year查询
     * @param year
     * @return
     */
    public List<MajorInfoYAllVO> findByYearLike(String year); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Pageable pageable);


    public List<MajorInfoYAllVO> findBySchoolNameAndPiciAndYear(String schoolName, Integer pici,String year);

    public void deleteByYear(String examYear);
}
