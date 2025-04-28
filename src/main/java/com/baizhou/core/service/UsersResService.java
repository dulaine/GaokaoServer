package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface UsersResService {
    /**
     * 保存UsersRes
     * @param usersRes
     * @return
     */
    UsersRes saveUsersRes(UsersResVO usersRes);

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
    String updateUsersRes(UsersResVO usersResVO);

    /**
     * 根据id返回单条记录
     * @param id
     * @return
     */
    UsersResVO findOneById(Long id);

    /**
    * 返回所有
    * */
   List<UsersResVO> getAllUsersRess();
    /**
     *  根据Tel查询
     * @param tel
     * @return
     */
    public List<UsersResVO> findByTel(String tel); 

    public List<UsersResVO> findByTelAndIsDelete(String tel, Integer isDelete);
    /**
     *  根据Tel查询
     * @param tel
     * @return
     */
    public List<UsersResVO> findByTelLike(String tel); 
    /**
     *  根据Name查询
     * @param name
     * @return
     */
    public List<UsersResVO> findByName(String name); 
    /**
     *  根据Name查询
     * @param name
     * @return
     */
    public List<UsersResVO> findByNameLike(String name); 
    /**
     *  根据Role查询
     * @param role
     * @return
     */
    public List<UsersResVO> findByRole(Integer role);
    public List<UsersResVO> findByRoleIsNot(Integer role);
    /**
     *  根据Token查询
     * @param token
     * @return
     */
    public List<UsersResVO> findByToken(String token); 
    /**
     *  根据Token查询
     * @param token
     * @return
     */
    public List<UsersResVO> findByTokenLike(String token); 
    /**
     *  根据IsDelete查询
     * @param isDelete
     * @return
     */
    public List<UsersResVO> findByIsDelete(Integer isDelete); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<UsersResVO> findByExamYear(String examYear); 
    /**
     *  根据ExamYear查询
     * @param examYear
     * @return
     */
    public List<UsersResVO> findByExamYearLike(String examYear); 

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String tel, String name, Integer role, Integer isDelete, String examYear, Pageable pageable);

}
