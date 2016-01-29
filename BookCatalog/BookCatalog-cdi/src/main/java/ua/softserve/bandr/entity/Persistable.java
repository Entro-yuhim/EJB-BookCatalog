package ua.softserve.bandr.entity;

import java.io.Serializable;

/**
 * Created by bandr on 28.01.2016.
 */
public interface Persistable extends Serializable {
	Long getId();

	String getEntityName();
}
