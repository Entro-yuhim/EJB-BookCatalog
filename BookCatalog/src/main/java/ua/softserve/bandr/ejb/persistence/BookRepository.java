package ua.softserve.bandr.ejb.persistence;

import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Review;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bandr on 05.01.2016.
 */
@Stateless
public class BookRepository {

    @PersistenceContext(name="pg_BC")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> getBooks(){
        return entityManager.createNamedQuery("Books.getAll", Book.class).getResultList();
    }

    public void saveBook(Book book){
        System.out.println("got a book:");
        System.out.println(book);
        Review review1 = new Review();
        review1.setMessage("message1");
        review1.setRating(1);
        review1.setUsername("user1");

        Review review2 = new Review();
        review2.setMessage("message1");
        review2.setRating(1);
        review2.setUsername("user1");

        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        book.setReviews(reviews);
        Author author1 = new Author();
        author1.setFirstName("a first name 1");
        Author author2 = new Author();
        author2.setFirstName("a first name 2");
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        book.setAuthors(authors);
        entityManager.persist(book);
    }
}
