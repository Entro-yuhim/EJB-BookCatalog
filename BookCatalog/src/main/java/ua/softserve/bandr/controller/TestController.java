package ua.softserve.bandr.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 * Created by bandr on 06.01.2016.
 */
@ManagedBean(name="message", eager=false)
@RequestScoped
public class TestController {
    public TestController() {
        System.out.println("Building object for request");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message = "A message";
}
