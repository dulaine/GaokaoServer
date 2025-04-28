package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * TemplateInfo的DAO层
 */
public interface TemplateInfoRepo extends BaseJPARepository<TemplateInfo,Long> {

  List<TemplateInfo> findByTemplateName(String templateName);
  List<TemplateInfo> findByTemplateNameLike(String templateName);
  List<TemplateInfo> findByCreatorName(String creatorName);
  List<TemplateInfo> findByCreatorNameLike(String creatorName);
  List<TemplateInfo> findByCreatorId(Long creatorId);
  List<TemplateInfo> findByExamYear(String examYear);
  List<TemplateInfo> findByExamYearLike(String examYear);
  List<TemplateInfo> findByPici(Integer pici);
  List<TemplateInfo> findByIsDelete(Integer isDelete);

  List<TemplateInfo> findByExamYearAndPici(String examYear, Integer pici);
}
