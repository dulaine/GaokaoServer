package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.data.enumdefine.EnumGender;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface UniMajorInfoZXBService {
    /**
     * 保存UniMajorInfoZXB
     * @param uniMajorInfoA
     * @return
     */
    UniMajorInfoZXB saveUniMajorInfoZXB(UniMajorInfoZXBVO uniMajorInfoA);

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
    String updateUniMajorInfoZXB(UniMajorInfoZXBVO uniMajorInfoAVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    UniMajorInfoZXBVO findOneById(Long id);

    List<UniMajorInfoZXBVO> findByIdIn(List<Long> id);
    /**
    * 返回所有
    * */
   List<UniMajorInfoZXBVO> getAllUniMajorInfoZXBs();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<UniMajorInfoZXBVO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<UniMajorInfoZXBVO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<UniMajorInfoZXBVO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolNameLike(String schoolName); 
    /**
     *  根据SchoolLvl1查询
     * @param schoolLvl1
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolLvl1(Integer schoolLvl1); 
    /**
     *  根据SchoolLvl2查询
     * @param schoolLvl2
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolLvl2(Integer schoolLvl2); 
    /**
     *  根据SchoolLvl3查询
     * @param schoolLvl3
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolLvl3(Integer schoolLvl3); 
    /**
     *  根据SchoolType查询
     * @param schoolType
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolType(String schoolType); 
    /**
     *  根据SchoolType查询
     * @param schoolType
     * @return
     */
    public List<UniMajorInfoZXBVO> findBySchoolTypeLike(String schoolType); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<UniMajorInfoZXBVO> findByProvince(String province); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<UniMajorInfoZXBVO> findByProvinceLike(String province); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<UniMajorInfoZXBVO> findByMajorName(String majorName); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<UniMajorInfoZXBVO> findByMajorNameLike(String majorName);

    /**
     *  根据IsZhongWai查询
     * @param isZhongWai
     * @return
     */
    public List<UniMajorInfoZXBVO> findByIsZhongWai(Integer isZhongWai);
    /**
     *  根据IsBenSuo查询
     * @param isBenSuo
     * @return
     */
    public List<UniMajorInfoZXBVO> findByIsBenSuo(Integer isBenSuo);

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    //Map<String, Object> listbyPage(String uniMajorCode, Integer pici, String schoolName, Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3, String schoolType, String province, String majorName, Pageable pageable);
    Map<String, Object> listbyPage(Integer pici, List<String> uniMajorCode,
                                   List<String> schoolName,
                                   Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3,
                                   List<String> schoolType,
                                   List<String> province, List<String> majorName,

                                   Integer minRank, Integer maxRank, //预估分数
                                   List<String> examSubj,   //科目 list
                                   List<String> majorName2,

                                   String examYear, //年份
                                   EnumGender gender, //性别

                                   Integer isZhongWai,  //是否中外合作
                                   Integer isBenSuo,   //是否 本硕
                                   Pageable pageable);

    List<UniMajorInfoZXBVO> findByUniMajorCodeIn(List<String> ids);

    void deleteByExamYear(String examYear);

    List<UniMajorInfoZXBVO> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
    List<UniMajorInfoZXBVO> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

    Map<String, Object> listbyPage( Pageable pageable);

    Integer findMaxVersion(String year);

    // 批量存储的方法
    List<UniMajorInfoZXB> batchSave(List<UniMajorInfoZXB> list);

    // 批量更新的方法
    List<UniMajorInfoZXB> batchUpdate(List<UniMajorInfoZXB> list);

    List<String> selectDistinctYear();

    List<UniMajorInfoZXBVO> findByPiciAndExamYear(Integer pici, String examYear);

    void deleteByIdIn(List<Long> ids);
}

