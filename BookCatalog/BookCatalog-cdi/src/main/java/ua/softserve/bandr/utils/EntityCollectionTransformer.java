package ua.softserve.bandr.utils;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import ua.softserve.bandr.entity.Persistable;

import java.util.Collection;

/**
 * Created by bandr on 28.01.2016.
 */

//TODO: rename class or add DTO converters here.
public final class EntityCollectionTransformer {
	private static final Transformer idTransformer = new BeanToPropertyValueTransformer("id");

	private EntityCollectionTransformer() {
	}

	public static Collection getIdCollection(Collection<? extends Persistable> entityCollection) {
		return CollectionUtils.collect(entityCollection, idTransformer);
	}
}
