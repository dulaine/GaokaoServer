package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface OrderInfoService {
    /**
     * 保存OrderInfo
     * @param orderInfo
     * @return
     */
    OrderInfo saveOrderInfo(OrderInfoVO orderInfo);

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
    String updateOrderInfo(OrderInfoVO orderInfoVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    OrderInfoVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<OrderInfoVO> getAllOrderInfos();
    /**
     *  根据Name查询
     * @param name
     * @return
     */
    public List<OrderInfoVO> findByName(String name); 
    /**
     *  根据Name查询
     * @param name
     * @return
     */
    public List<OrderInfoVO> findByNameLike(String name); 
    /**
     *  根据Tel查询
     * @param tel
     * @return
     */
    public List<OrderInfoVO> findByTel(String tel); 
    /**
     *  根据Tel查询
     * @param tel
     * @return
     */
    public List<OrderInfoVO> findByTelLike(String tel); 
    /**
     *  根据Status查询
     * @param status
     * @return
     */
    public List<OrderInfoVO> findByStatus(Integer status); 
    /**
     *  根据CreatorName查询
     * @param creatorName
     * @return
     */
    public List<OrderInfoVO> findByCreatorName(String creatorName); 
    /**
     *  根据CreatorName查询
     * @param creatorName
     * @return
     */
    public List<OrderInfoVO> findByCreatorNameLike(String creatorName); 
    /**
     *  根据AssignedTeacherId查询
     * @param assignedTeacherId
     * @return
     */
    public List<OrderInfoVO> findByAssignedTeacherId(Long assignedTeacherId); 
    /**
     *  根据AssignedTeacher查询
     * @param assignedTeacher
     * @return
     */
    public List<OrderInfoVO> findByAssignedTeacher(String assignedTeacher); 
    /**
     *  根据AssignedTeacher查询
     * @param assignedTeacher
     * @return
     */
    public List<OrderInfoVO> findByAssignedTeacherLike(String assignedTeacher); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<OrderInfoVO> findByProvince(String province); 
    /**
     *  根据Province查询
     * @param province
     * @return
     */
    public List<OrderInfoVO> findByProvinceLike(String province); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<OrderInfoVO> findByExamYear(String examYear); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<OrderInfoVO> findByExamYearLike(String examYear); 
    /**
     *  根据IsDelete查询
     * @param isDelete
     * @return
     */
    public List<OrderInfoVO> findByIsDelete(Integer isDelete);

    /**
     *  根据HasSelPhysicLimit查询
     * @param hasSelPhysicLimit
     * @return
     */
    public List<OrderInfoVO> findByHasSelPhysicLimit(Integer hasSelPhysicLimit);


    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String name, String tel, Integer status, Long assignedTeacherId, String authorTeacherIds, String authorTeacherIds2,String province, String examYear, Integer isDelete, Integer hasSelPhysicLimit, Pageable pageable);


    Map<String, Object> listTelAndValidTeacherbyPage(String tel,String authorTeacherIds,Integer isDelete,Pageable pageable);
}
