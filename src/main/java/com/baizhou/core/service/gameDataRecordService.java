package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface gameDataRecordService {
    /**
     * 保存gameDataRecord
     * @param gameDataRecord
     * @return
     */
    gameDataRecord savegameDataRecord(gameDataRecordVO gameDataRecord);

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
    String updategameDataRecord(gameDataRecordVO gameDataRecordVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    gameDataRecordVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<gameDataRecordVO> getAllgameDataRecords();

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(Pageable pageable);

}
