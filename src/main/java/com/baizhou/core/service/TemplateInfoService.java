package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface TemplateInfoService {
    /**
     * 保存TemplateInfo
     * @param templateInfo
     * @return
     */
    TemplateInfo saveTemplateInfo(TemplateInfoVO templateInfo);

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
    String updateTemplateInfo(TemplateInfoVO templateInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    TemplateInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<TemplateInfoVO> getAllTemplateInfos();
    /**
     *  根据TemplateName查询
     * @param templateName
     * @return
     */
    public List<TemplateInfoVO> findByTemplateName(String templateName); 
    /**
     *  根据TemplateName查询
     * @param templateName
     * @return
     */
    public List<TemplateInfoVO> findByTemplateNameLike(String templateName); 
    /**
     *  根据CreatorName查询
     * @param creatorName
     * @return
     */
    public List<TemplateInfoVO> findByCreatorName(String creatorName); 
    /**
     *  根据CreatorName查询
     * @param creatorName
     * @return
     */
    public List<TemplateInfoVO> findByCreatorNameLike(String creatorName); 
    /**
     *  根据CreatorId查询
     * @param creatorId
     * @return
     */
    public List<TemplateInfoVO> findByCreatorId(Long creatorId); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<TemplateInfoVO> findByExamYear(String examYear); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<TemplateInfoVO> findByExamYearLike(String examYear); 
    /**
     *  根据Pici查询
     * @param pici
     * @return
     */
    public List<TemplateInfoVO> findByPici(Integer pici); 
    /**
     *  根据IsDelete查询
     * @param isDelete
     * @return
     */
    public List<TemplateInfoVO> findByIsDelete(Integer isDelete); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String templateName, String authorTeacherIds, Integer isPublic, String examYear, Integer pici, Integer isDelete, Pageable pageable);


    public List<TemplateInfoVO> findByExamYearAndPici(String examYear, Integer pici);
}
