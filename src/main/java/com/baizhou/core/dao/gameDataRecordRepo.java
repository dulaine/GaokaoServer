package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * gameDataRecord的DAO层
 */
public interface gameDataRecordRepo extends BaseJPARepository<gameDataRecord,Long> {

}
