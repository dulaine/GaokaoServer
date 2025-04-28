package com.baizhou.util.idgenerator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.id.Configurable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Properties;

@Component
public class IdGenerator implements Configurable,IdentifierGenerator {

    private BasicEntityGenerator basicEntityGenerator = new BasicEntityGenerator(1,1);

//    private Long dataCenter;
//    private Long system;//xx
//    private Long componentp;//xxx
//    private Long biz;//xxx

//@Autowired
//    BasicEntityGenerator basicEntityGenerator;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry)
            throws MappingException {

//        this.dataCenter = (Long) params.get("dc");
//        this.system = (Long) params.get("ma");
//

    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return basicEntityGenerator.nextId();
    }
}
