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
 * UniMajorInfoALatest的DAO层
 */
public interface UniMajorInfoALatestRepo extends BaseJPARepository<UniMajorInfoALatest,Long> {

  List<UniMajorInfoALatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoALatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoALatest> findByPici(Integer pici);
  List<UniMajorInfoALatest> findBySchoolName(String schoolName);
  List<UniMajorInfoALatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoALatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoALatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoALatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoALatest> findBySchoolType(String schoolType);
  List<UniMajorInfoALatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoALatest> findByProvince(String province);
  List<UniMajorInfoALatest> findByProvinceLike(String province);
  List<UniMajorInfoALatest> findByMajorName(String majorName);
  List<UniMajorInfoALatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoALatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoALatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoALatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoALatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoALatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoALatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoALatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_infoalatest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_infoa WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoALatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoALatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoALatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoalatest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoalatest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoALatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}
