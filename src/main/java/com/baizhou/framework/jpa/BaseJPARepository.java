package com.baizhou.framework.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.math.BigInteger;

@NoRepositoryBean
public interface BaseJPARepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
//    Boolean existName(String var1);
//
//    Boolean existId(ID var1);

//    void delete(ID[] var1);

//    T findOneNonDeleted(ID var1);

//    T findByNameWithInAParent(String var1, BigInteger var2);
//
//    T findByOrgIDAndOrgRootFlagAndDeleteFlag(BigInteger var1, Boolean var2, Boolean var3);
}
