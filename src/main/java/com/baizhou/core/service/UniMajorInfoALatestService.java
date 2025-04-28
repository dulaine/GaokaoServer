package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import com.baizhou.data.enumdefine.EnumGender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Date;
import java.util.Map;

public interface UniMajorInfoALatestService {
    /**
     * 保存UniMajorInfoALatest
     * @param uniMajorInfoALatest
     * @return
     */
    UniMajorInfoALatest saveUniMajorInfoALatest(UniMajorInfoALatestVO uniMajorInfoALatest);

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
    String updateUniMajorInfoALatest(UniMajorInfoALatestVO uniMajorInfoALatestVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    UniMajorInfoALatestVO findOneById(Long id);
    List<UniMajorInfoALatestVO> findByIdIn(List<Long> id);
    /**
    * 返回所有
    * */
   List<UniMajorInfoALatestVO> getAllUniMajorInfoALatests();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<UniMajorInfoALatestVO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<UniMajorInfoALatestVO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<UniMajorInfoALatestVO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolNameLike(String schoolName); 
    /**
     *  根据SchoolLvl1查询
     * @param schoolLvl1
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolLvl1(Integer schoolLvl1); 
    /**
     *  根据SchoolLvl2查询
     * @param schoolLvl2
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolLvl2(Integer schoolLvl2); 
    /**
     *  根据SchoolLvl3查询
     * @param schoolLvl3
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolLvl3(Integer schoolLvl3); 
    /**
     *  根据SchoolType查询
     * @param schoolType
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolType(String schoolType); 
    /**
     *  根据SchoolType查询
     * @param schoolType
     * @return
     */
    public List<UniMajorInfoALatestVO> findBySchoolTypeLike(String schoolType); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<UniMajorInfoALatestVO> findByProvince(String province); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<UniMajorInfoALatestVO> findByProvinceLike(String province); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<UniMajorInfoALatestVO> findByMajorName(String majorName); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<UniMajorInfoALatestVO> findByMajorNameLike(String majorName);

    /**
     *  根据IsZhongWai查询
     * @param isZhongWai
     * @return
     */
    public List<UniMajorInfoALatestVO> findByIsZhongWai(Integer isZhongWai);
    /**
     *  根据IsBenSuo查询
     * @param isBenSuo
     * @return
     */
    public List<UniMajorInfoALatestVO> findByIsBenSuo(Integer isBenSuo);

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    //Map<String, Object> listbyPage(String uniMajorCode, Integer pici, String schoolName, Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3, String schoolType, String province, String majorName, Pageable pageable);
    Map<String, Object> listbyPage(Integer pici,  List<String> uniMajorCode,
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

    List<UniMajorInfoALatestVO> findByUniMajorCodeIn(List<String> ids);


    void deleteByExamYear(String examYear);

    List<UniMajorInfoALatestVO> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

    List<UniMajorInfoALatestVO> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

    List<UniMajorInfoALatestVO> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

    List<UniMajorInfoALatestVO> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

    Map<String, Object> listbyPage( Pageable pageable);


    Integer findMaxVersion(String year);

    // 批量存储的方法
    List<UniMajorInfoALatest> batchSave(List<UniMajorInfoALatest> list);

    // 批量更新的方法
    List<UniMajorInfoALatest> batchUpdate(List<UniMajorInfoALatest> list);

    List<String> selectDistinctYear();

    List<UniMajorInfoALatestVO> findByPiciAndExamYear(Integer pici, String examYear);

    void deleteByIdIn(List<Long> ids);
}
