package ua.softserve.bandr.utils;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import ua.softserve.bandr.entity.Persistable;

import java.util.Collection;
import java.util.List;

/**
 * Created by bandr on 28.01.2016.
 */
public class EntityCollectionTransformer {
    private static BeanToPropertyValueTransformer idTransformer = new BeanToPropertyValueTransformer("id");

    public static Collection getIdCollection(List<? extends Persistable> entityCollection) {
        return CollectionUtils.collect(entityCollection, idTransformer);
    }
}
