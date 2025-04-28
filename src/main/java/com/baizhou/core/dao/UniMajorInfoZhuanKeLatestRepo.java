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
 * UniMajorInfoZhuanKeLatest的DAO层
 */
public interface UniMajorInfoZhuanKeLatestRepo extends BaseJPARepository<UniMajorInfoZhuanKeLatest,Long> {

  List<UniMajorInfoZhuanKeLatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoZhuanKeLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoZhuanKeLatest> findByPici(Integer pici);
  List<UniMajorInfoZhuanKeLatest> findBySchoolName(String schoolName);
  List<UniMajorInfoZhuanKeLatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoZhuanKeLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoZhuanKeLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoZhuanKeLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoZhuanKeLatest> findBySchoolType(String schoolType);
  List<UniMajorInfoZhuanKeLatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoZhuanKeLatest> findByProvince(String province);
  List<UniMajorInfoZhuanKeLatest> findByProvinceLike(String province);
  List<UniMajorInfoZhuanKeLatest> findByMajorName(String majorName);
  List<UniMajorInfoZhuanKeLatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoZhuanKeLatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoZhuanKeLatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoZhuanKeLatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoZhuanKeLatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoZhuanKeLatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoZhuanKeLatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoZhuanKeLatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_info_zhuan_ke_latest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_info_zhuan_ke WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoZhuanKeLatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoZhuanKeLatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoZhuanKeLatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_info_zhuan_ke_latest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_info_zhuan_ke_latest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoZhuanKeLatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

