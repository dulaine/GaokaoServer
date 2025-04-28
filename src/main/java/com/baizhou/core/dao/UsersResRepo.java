package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * UsersRes的DAO层
 */
public interface UsersResRepo extends BaseJPARepository<UsersRes,Long> {

  List<UsersRes> findByTel(String tel);
  List<UsersRes> findByTelAndIsDelete(String tel, Integer isDelete);
  List<UsersRes> findByTelLike(String tel);
  List<UsersRes> findByName(String name);
  List<UsersRes> findByNameLike(String name);
  List<UsersRes> findByRole(Integer role);
  List<UsersRes> findByRoleIsNot(Integer role);
  List<UsersRes> findByToken(String token);
  List<UsersRes> findByTokenLike(String token);
  List<UsersRes> findByIsDelete(Integer isDelete);
  List<UsersRes> findByExamYear(String examYear);
  List<UsersRes> findByExamYearLike(String examYear);
}
