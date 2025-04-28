package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * MajorInfoY1的DAO层
 */
public interface MajorInfoY1Repo extends BaseJPARepository<MajorInfoY1,Long> {

  List<MajorInfoY1> findByUniMajorCode(String uniMajorCode);
  List<MajorInfoY1> findByUniMajorCodeLike(String uniMajorCode);
  List<MajorInfoY1> findByPici(Integer pici);
  List<MajorInfoY1> findBySchoolName(String schoolName);
  List<MajorInfoY1> findBySchoolNameLike(String schoolName);
  List<MajorInfoY1> findBySchoolNameAndPici(String schoolName, Integer pici);
}
