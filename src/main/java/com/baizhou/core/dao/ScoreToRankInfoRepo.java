package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * ScoreToRankInfo的DAO层
 */
public interface ScoreToRankInfoRepo extends BaseJPARepository<ScoreToRankInfo,Long> {

  List<ScoreToRankInfo> findByExamYearOrderByScoreDesc(String examYear);
  List<ScoreToRankInfo> findByExamYearLike(String examYear);

  List<ScoreToRankInfo> findAllByOrderByScoreDesc();

  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
