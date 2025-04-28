package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface IconMajorInfoService {
    /**
     * 保存IconMajorInfo
     * @param iconMajorInfo
     * @return
     */
    IconMajorInfo saveIconMajorInfo(IconMajorInfoVO iconMajorInfo);

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
    String updateIconMajorInfo(IconMajorInfoVO iconMajorInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    IconMajorInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<IconMajorInfoVO> getAllIconMajorInfos();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<IconMajorInfoVO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<IconMajorInfoVO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<IconMajorInfoVO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<IconMajorInfoVO> findBySchoolNameLike(String schoolName); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<IconMajorInfoVO> findByMajorName(String majorName); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<IconMajorInfoVO> findByMajorNameLike(String majorName); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String uniMajorCode, String schoolName, String majorName, Pageable pageable);


    List<IconMajorInfoVO> findBySchoolNameAndCls1IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls2IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls3IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls4IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls5IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls6IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls7IsNot(String schoolName, String cls1);
    List<IconMajorInfoVO> findBySchoolNameAndCls8IsNot(String schoolName, String cls1);

    List<IconMajorInfoVO> findBySchoolNameAndCls1Is(String schoolName, String cls1);
}
