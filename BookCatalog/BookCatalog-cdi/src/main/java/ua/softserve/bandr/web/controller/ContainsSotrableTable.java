package ua.softserve.bandr.web.controller;

import com.google.common.collect.Maps;
import org.richfaces.component.SortOrder;

import java.util.Map;

/**
 * Created by bandr on 01.02.2016.
 */
public abstract class ContainsSotrableTable {

	private Map<String, SortOrder> sortOrders = Maps.newHashMap();
	private String sortProperty = "";
	private Map<String, String> filterValues = Maps.newHashMap();

	public Map<String, String> getFilterValues() {
		//LOG.info("Current filter: [{}]", filterValues);
		return filterValues;
	}

	public void setFilterValues(Map<String, String> filterValues) {
		this.filterValues = filterValues;
	}

	public Map<String, SortOrder> getSortOrders() {
		return sortOrders;
	}

	public void setSortOrders(Map<String, SortOrder> sortOrders) {
		this.sortOrders = sortOrders;
	}

	public String getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

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
