package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * ClientInfo的DAO层
 */
public interface ClientInfoRepo extends BaseJPARepository<ClientInfo,String> {

  Optional<ClientInfo> findByTel(String tel);
  List<ClientInfo> findByTelLike(String tel);
  List<ClientInfo> findByName(String name);
  List<ClientInfo> findByNameLike(String name);
}
