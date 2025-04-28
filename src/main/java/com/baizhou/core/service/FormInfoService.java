package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface FormInfoService {
    /**
     * 保存FormInfo
     * @param formInfo
     * @return
     */
    FormInfo saveFormInfo(FormInfoVO formInfo);

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
    String updateFormInfo(FormInfoVO formInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    FormInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<FormInfoVO> getAllFormInfos();
    /**
     *  根据OrderId查询
     * @param orderId
     * @return
     */
    public List<FormInfoVO> findByOrderId(Long orderId); 
    /**
     *  根据FormName查询
     * @param formName
     * @return
     */
    public List<FormInfoVO> findByFormName(String formName); 
    /**
     *  根据FormName查询
     * @param formName
     * @return
     */
    public List<FormInfoVO> findByFormNameLike(String formName); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<FormInfoVO> findByPici(Integer pici); 
    /**
     *  根据IsDelete查询
     * @param isDelete
     * @return
     */
    public List<FormInfoVO> findByIsDelete(Integer isDelete); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<FormInfoVO> findByExamYear(String examYear); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<FormInfoVO> findByExamYearLike(String examYear); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Long orderId, Integer pici, Integer isDelete, String examYear, Pageable pageable);

    List<FormInfoVO> findByFormNameAndOrderId(String formName, Long orderId);

    public List<FormInfoVO> findByExamYearAndPici(String examYear, Integer pici);
}
