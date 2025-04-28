package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * MajorInfoY3的DAO层
 */
public interface MajorInfoY3Repo extends BaseJPARepository<MajorInfoY3,Long> {

  List<MajorInfoY3> findByUniMajorCode(String uniMajorCode);
  List<MajorInfoY3> findByUniMajorCodeLike(String uniMajorCode);
  List<MajorInfoY3> findByPici(Integer pici);
  List<MajorInfoY3> findBySchoolName(String schoolName);
  List<MajorInfoY3> findBySchoolNameLike(String schoolName);
  List<MajorInfoY3> findBySchoolNameAndPici(String schoolName, Integer pici);
}
