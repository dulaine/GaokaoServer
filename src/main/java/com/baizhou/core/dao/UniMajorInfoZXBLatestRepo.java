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
 * UniMajorInfoZXBLatest的DAO层
 */
public interface UniMajorInfoZXBLatestRepo extends BaseJPARepository<UniMajorInfoZXBLatest,Long> {

  List<UniMajorInfoZXBLatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoZXBLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoZXBLatest> findByPici(Integer pici);
  List<UniMajorInfoZXBLatest> findBySchoolName(String schoolName);
  List<UniMajorInfoZXBLatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoZXBLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoZXBLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoZXBLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoZXBLatest> findBySchoolType(String schoolType);
  List<UniMajorInfoZXBLatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoZXBLatest> findByProvince(String province);
  List<UniMajorInfoZXBLatest> findByProvinceLike(String province);
  List<UniMajorInfoZXBLatest> findByMajorName(String majorName);
  List<UniMajorInfoZXBLatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoZXBLatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoZXBLatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoZXBLatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoZXBLatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoZXBLatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoZXBLatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoZXBLatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_infozxblatest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_infozxb WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoZXBLatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoZXBLatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoZXBLatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infozxblatest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infozxblatest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoZXBLatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

