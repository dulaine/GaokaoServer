package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface ScoreToRankInfoService {
    /**
     * 保存ScoreToRankInfo
     * @param scoreToRankInfo
     * @return
     */
    ScoreToRankInfo saveScoreToRankInfo(ScoreToRankInfoVO scoreToRankInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteByKeyId(Long id);


    /**
     * 删除所有
     * @return
     */
    void deleteAll();

    /**
    * 更新
    * */
    String updateScoreToRankInfo(ScoreToRankInfoVO scoreToRankInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    ScoreToRankInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<ScoreToRankInfoVO> getAllScoreToRankInfos();
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<ScoreToRankInfoVO> findByExamYear(String examYear); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<ScoreToRankInfoVO> findByExamYearLike(String examYear); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Pageable pageable);

    public void deleteByExamYear(String examYear);
}
