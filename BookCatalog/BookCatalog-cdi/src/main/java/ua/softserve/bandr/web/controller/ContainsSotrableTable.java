package ua.softserve.bandr.web.controller;

import org.richfaces.component.SortOrder;

import java.util.Map;

/**
 * Created by bandr on 01.02.2016.
 */
public abstract class ContainsSotrableTable {

	abstract Map<String, SortOrder> getSortOrders();

	abstract void setSortOrders(Map<String, SortOrder> sortOrders);

	abstract String getSortProperty();

	abstract void setSortProperty(String sortProperty);

	public void toggleSort() {
		for (Map.Entry<String, SortOrder> entry : getSortOrders().entrySet()) {
			SortOrder newOrder;

			if (entry.getKey().equals(getSortProperty())) {
				if (entry.getValue() == SortOrder.ascending) {
					newOrder = SortOrder.descending;
				} else {
					newOrder = SortOrder.ascending;
				}
			} else {
				newOrder = SortOrder.unsorted;
			}

			entry.setValue(newOrder);
		}
	}
}
