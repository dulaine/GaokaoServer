package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * UniMajorInfoPreBLatest的DAO层
 */
public interface UniMajorInfoPreBLatestRepo extends BaseJPARepository<UniMajorInfoPreBLatest,Long> {

  List<UniMajorInfoPreBLatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoPreBLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoPreBLatest> findByPici(Integer pici);
  List<UniMajorInfoPreBLatest> findBySchoolName(String schoolName);
  List<UniMajorInfoPreBLatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoPreBLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoPreBLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoPreBLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoPreBLatest> findBySchoolType(String schoolType);
  List<UniMajorInfoPreBLatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoPreBLatest> findByProvince(String province);
  List<UniMajorInfoPreBLatest> findByProvinceLike(String province);
  List<UniMajorInfoPreBLatest> findByMajorName(String majorName);
  List<UniMajorInfoPreBLatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoPreBLatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoPreBLatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoPreBLatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoPreBLatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoPreBLatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoPreBLatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoPreBLatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_info_preblatest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_info_preb WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoPreBLatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoPreBLatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoPreBLatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_info_preblatest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_info_preblatest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoPreBLatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

