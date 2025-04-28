package com.baizhou.manager;

import com.baizhou.core.model.entity.ScoreToRankInfo;
import com.baizhou.core.model.vo.ScoreToRankInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.db.DBProxy;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class RankManager {
    private static RankManager instance;

    public static RankManager GetInstance() {
        if (instance == null) {
            instance = new RankManager();
        }
        return instance;
    }

    List<ScoreToRankInfoVO> rankInfoVOList = null;
    private Dictionary<Integer, ScoreToRankInfoVO> rankInfoDic = new Hashtable<>();


    private HashMap<String, HashMap<Integer, ScoreToRankInfoVO>> rankInfoByYearDic = new HashMap<>(); //<年份,   rank字典>
    private HashMap<String,  List<ScoreToRankInfoVO>> rankInfoVOListByYearDic = new HashMap<>();//<年份,   List>

    //初始化
    public void Init(){

    }

    //更新年份数据
    public void UpdateYearData(String year){


        HashMap<Integer, ScoreToRankInfoVO> dic = new HashMap<>();
        List<ScoreToRankInfoVO> rankList = RefreshYFYDInDB(year, dic);

        List<ScoreToRankInfoVO> tempList = rankInfoVOListByYearDic.getOrDefault(year, null);
        if(tempList == null){
            rankInfoVOListByYearDic.remove(year);
            rankInfoByYearDic.remove(year);
        }
        rankInfoVOListByYearDic.put(year, rankList);
        rankInfoByYearDic.put(year, dic);

    }


    private List<ScoreToRankInfoVO> findYFYDInDB(String examYear) {
        List<ScoreToRankInfoVO> rankList = rankInfoVOListByYearDic.getOrDefault(examYear, null);

        if (rankList == null) {
//            rankInfoVOList = DBProxy.Getinstance().ScoreToRankInfoService.findByExamYear(examYear);//.getAllScoreToRankInfos();
//            Integer lastRank = ConstDefine.DefaultWeici;
//            for (int i = rankInfoVOList.size() - 1; i >= 0; i--) {
//                ScoreToRankInfoVO rank = rankInfoVOList.get(i);
//                if (rank.getRank() == 0){
//                    rank.setRank(lastRank);
//                }else {
//                    lastRank = rank.getRank();
//                }
//                rankInfoDic.put(rank.getScore(), rank);
//
//            }

            UpdateYearData(examYear);
            rankList = rankInfoVOListByYearDic.getOrDefault(examYear, null);
        }


        return rankList;

//        m_YFYDDic.put(key, yfydvoDictionary);
    }


//更新DB
    private List<ScoreToRankInfoVO> RefreshYFYDInDB(String examYear, HashMap<Integer, ScoreToRankInfoVO> dic) {
//        List<ScoreToRankInfoVO> rankList = rankInfoVOListByYearDic.getOrDefault(examYear, null);
        if(dic == null) dic = new HashMap<>();
//        if (rankList == null) {
        List<ScoreToRankInfoVO>rankList = DBProxy.Getinstance().ScoreToRankInfoService.findByExamYear(examYear);//.getAllScoreToRankInfos();
            Integer lastRank = ConstDefine.DefaultWeici;
            for (int i = rankList.size() - 1; i >= 0; i--) {
                ScoreToRankInfoVO rank = rankList.get(i);
                if (rank.getRank() == 0){
                    rank.setRank(lastRank);
                }else {
                    lastRank = rank.getRank();
                }
                dic.put(rank.getScore(), rank);
//            }
        }

        return rankList;
    }


    //查询字典
    private  HashMap<Integer, ScoreToRankInfoVO> findYFYDDicInDB(String examYear) {

        HashMap<Integer, ScoreToRankInfoVO> dic = rankInfoByYearDic.getOrDefault(examYear, null);
        if (dic == null) {
            UpdateYearData(examYear);
            dic = rankInfoByYearDic.getOrDefault(examYear, null);
        }

        return dic;
    }


    //根据分数获取位数
    public Integer GetRankByScore(Integer score, String examYear) {
        List<ScoreToRankInfoVO> list = findYFYDInDB(examYear);
        if (list == null) return ConstDefine.DefaultWeici;//获取分数对应的位数

        if (list.size() > 0) {

            if (score > list.get(0).getScore()) return list.get(0).getRank(); // 返回最高位数
            if (score < list.get(list.size() - 1).getScore()) return list.get(list.size() - 1).getRank();

            HashMap<Integer, ScoreToRankInfoVO> dic = findYFYDDicInDB(examYear);
            if (dic == null) return ConstDefine.DefaultWeici;//获取分数对应的位数

            ScoreToRankInfoVO vo = dic.get(score);
            if (vo == null) return ConstDefine.DefaultWeici;//获取分数对应的位数
            return vo.getRank();
        }
        return ConstDefine.DefaultWeici;
    }

    public Integer GetRankByScore(String score,String examYear) {
        Integer scoreNum = (int) Float.parseFloat(score);
        return GetRankByScore(scoreNum, examYear);
    }

}
