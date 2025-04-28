package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface ClientInfoService {
    /**
     * 保存ClientInfo
     * @param clientInfo
     * @return
     */
    ClientInfo saveClientInfo(ClientInfoVO clientInfo);

    /**
     * 删除
     * @param tel
     * @return
     */
    boolean deleteByKeyTel(String tel);


    /**
     * 删除所有
     * @return
     */
    void deleteAll();

    /**
    * 更新
    * */
    String updateClientInfo(ClientInfoVO clientInfoVO);

    /**
     * 根据tel返回单条记录
     * @param tel
     * @return
     */
    ClientInfoVO findOneByTel(String tel);
    List<ClientInfoVO> findOneByTelLike(String tel);
    /**
    * 返回所有
    * */
   List<ClientInfoVO> getAllClientInfos();
    /**
     *  根据Name查询
     * @param name
     * @return
     */
    public List<ClientInfoVO> findByName(String name); 
    /**
     *  根据Name查询
     * @param name
     * @return
     */
    public List<ClientInfoVO> findByNameLike(String name); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String tel, String name, Pageable pageable);

}
