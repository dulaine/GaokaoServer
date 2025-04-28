package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * MajorInfoY2的DAO层
 */
public interface MajorInfoY2Repo extends BaseJPARepository<MajorInfoY2,Long> {

  List<MajorInfoY2> findByUniMajorCode(String uniMajorCode);
  List<MajorInfoY2> findByUniMajorCodeLike(String uniMajorCode);
  List<MajorInfoY2> findByPici(Integer pici);
  List<MajorInfoY2> findBySchoolName(String schoolName);
  List<MajorInfoY2> findBySchoolNameLike(String schoolName);
  List<MajorInfoY2> findBySchoolNameAndPici(String schoolName, Integer pici);
}
