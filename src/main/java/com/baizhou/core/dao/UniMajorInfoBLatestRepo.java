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
 * UniMajorInfoBLatest的DAO层
 */
public interface UniMajorInfoBLatestRepo extends BaseJPARepository<UniMajorInfoBLatest,Long> {

  List<UniMajorInfoBLatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoBLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoBLatest> findByPici(Integer pici);
  List<UniMajorInfoBLatest> findBySchoolName(String schoolName);
  List<UniMajorInfoBLatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoBLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoBLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoBLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoBLatest> findBySchoolType(String schoolType);
  List<UniMajorInfoBLatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoBLatest> findByProvince(String province);
  List<UniMajorInfoBLatest> findByProvinceLike(String province);
  List<UniMajorInfoBLatest> findByMajorName(String majorName);
  List<UniMajorInfoBLatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoBLatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoBLatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoBLatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoBLatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoBLatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoBLatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoBLatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_infoblatest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_infob WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoBLatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoBLatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoBLatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoblatest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoblatest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoBLatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

