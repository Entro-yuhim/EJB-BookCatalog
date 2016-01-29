package ua.softserve.bandr.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.bandr.web.pagination.BookDataModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bandr on 29.01.2016.
 */
@ManagedBean
@ViewScoped
public class BookListController {
	private static final Logger LOG = LoggerFactory.getLogger(BookListController.class);
	@Inject
	private BookDataModel model;
	private Map<Long, Boolean> checked = new HashMap<>();
	private int count;

	public Map<Long, Boolean> getChecked() {
		//LOG.info("Triggering GetChecked");
		//un-comment to see the hilarity of jsf
		//LOG.info("Logging status of checked [{}]", checked);
		return checked;
	}

	public void setChecked(Map<Long, Boolean> checked) {
		this.checked = checked;
	}

	public BookDataModel getBooks() {
		checked = model.getCheckedData();
		LOG.info("Retrieving model [{}]", count);
		count++;
		return model;
	}

	public String doStuff() {
		LOG.info("Doing stuffz");
		model.getCheckedData().forEach((k, v) -> {
			if (v) {
				LOG.info("Checked id = [{}]", k);
			}
		});
		return "bookPage";
	}

}
