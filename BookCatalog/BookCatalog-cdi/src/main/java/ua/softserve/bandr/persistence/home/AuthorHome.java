package ua.softserve.bandr.persistence.home;

import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//Should this be an actual Home JEE pattern?
@Stateless
public class AuthorHome extends AbstractHome<Author>{
}
