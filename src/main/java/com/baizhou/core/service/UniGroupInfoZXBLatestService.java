package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface UniGroupInfoZXBLatestService {
    /**
     * 保存UniGroupInfoZXBLatest
     * @param uniGroupInfoZXBLatest
     * @return
     */
    UniGroupInfoZXBLatest saveUniGroupInfoZXBLatest(UniGroupInfoZXBLatestVO uniGroupInfoZXBLatest);

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
    String updateUniGroupInfoZXBLatest(UniGroupInfoZXBLatestVO uniGroupInfoZXBLatestVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    UniGroupInfoZXBLatestVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<UniGroupInfoZXBLatestVO> getAllUniGroupInfoZXBLatests();
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByUniMajorCode(String uniMajorCode); 
    /**
     *  根据UniMajorCode查询
     * @param uniMajorCode
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByUniMajorCodeLike(String uniMajorCode); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByPici(Integer pici); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolName(String schoolName); 
    /**
     *  根据SchoolName查询
     * @param schoolName
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolNameLike(String schoolName); 
    /**
     *  根据SchoolLvl1查询
     * @param schoolLvl1
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolLvl1(Integer schoolLvl1); 
    /**
     *  根据SchoolLvl2查询
     * @param schoolLvl2
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolLvl2(Integer schoolLvl2); 
    /**
     *  根据SchoolLvl3查询
     * @param schoolLvl3
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolLvl3(Integer schoolLvl3); 
    /**
     *  根据SchoolType查询
     * @param schoolType
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolType(String schoolType); 
    /**
     *  根据SchoolType查询
     * @param schoolType
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findBySchoolTypeLike(String schoolType); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByProvince(String province); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByProvinceLike(String province); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByExamYear(String examYear); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByExamYearLike(String examYear); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByMajorName(String majorName); 
    /**
     *  根据MajorName查询
     * @param majorName
     * @return
     */
    public List<UniGroupInfoZXBLatestVO> findByMajorNameLike(String majorName); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
//    Map<String, Object> listbyPage(String uniMajorCode, Integer pici, String schoolName, Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3, String schoolType, String province, String examYear, String majorName, Pageable pageable);
    Map<String, Object> listbyPage(Integer pici,  List<String> uniMajorCode,
                                   List<String> schoolName,
                                   Integer schoolLvl1, Integer schoolLvl2, Integer schoolLvl3,
                                   List<String> schoolType,
                                   List<String> province, List<String> majorName,

                                   Integer minRank, Integer maxRank, //预估分数
                                   List<String> examSubj,   //科目 list
                                   List<String> majorName2,

                                   String examYear, //年份
                                   Pageable pageable);
    void deleteByExamYear(String examYear);

}
