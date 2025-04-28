package com.baizhou.util;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;


//public class BeanMapper {
//}
public final class BeanMapper {
    private static Mapper dozer = DozerBeanMapperBuilder.buildDefault();

    private BeanMapper() {
    }

    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        Iterator var3 = sourceList.iterator();

        while(var3.hasNext()) {
            Object sourceObject = var3.next();
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }

        return destinationList;
    }

    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }
}