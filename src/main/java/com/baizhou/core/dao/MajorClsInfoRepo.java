package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * MajorClsInfo的DAO层
 */
public interface MajorClsInfoRepo extends BaseJPARepository<MajorClsInfo,Long> {

  List<MajorClsInfo> findByMajor3rdCls(String major3rdCls);
  List<MajorClsInfo> findByMajor3rdClsLike(String major3rdCls);

  List<MajorClsInfo> findByMajor1stClsAndMajor2ndClsAndMajor3rdCls(String major1,String major2, String major3rdCls);
}
