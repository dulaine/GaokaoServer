package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * IconMajorInfo的DAO层
 */
public interface IconMajorInfoRepo extends BaseJPARepository<IconMajorInfo,Long> {

  List<IconMajorInfo> findByUniMajorCode(String uniMajorCode);
  List<IconMajorInfo> findByUniMajorCodeLike(String uniMajorCode);
  List<IconMajorInfo> findBySchoolName(String schoolName);
  List<IconMajorInfo> findBySchoolNameLike(String schoolName);
  List<IconMajorInfo> findByMajorName(String majorName);
  List<IconMajorInfo> findByMajorNameLike(String majorName);
  List<IconMajorInfo> findBySchoolNameAndCls1IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls2IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls3IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls4IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls5IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls6IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls7IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls8IsNot(String schoolName, String cls1);
  List<IconMajorInfo> findBySchoolNameAndCls1Is(String schoolName, String cls1);
}
