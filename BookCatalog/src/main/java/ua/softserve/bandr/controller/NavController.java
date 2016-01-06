package ua.softserve.bandr.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 * Created by bandr on 06.01.2016.
 */
@ManagedBean(name="navigationController")
@RequestScoped
public class NavController {
    public String moveToPage1(){
        return "page1";
    }
}
