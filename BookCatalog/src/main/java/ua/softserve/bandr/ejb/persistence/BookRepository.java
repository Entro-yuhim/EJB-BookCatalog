package ua.softserve.bandr.ejb.persistence;

import ua.softserve.bandr.entity.Author;
import ua.softserve.bandr.entity.Book;
import ua.softserve.bandr.entity.Comment;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
        Comment comment1 = new Comment();
        comment1.setMessage("message1");
        comment1.setRating(1);
        comment1.setUsername("user1");

        Comment comment2 = new Comment();
        comment2.setMessage("message1");
        comment2.setRating(1);
        comment2.setUsername("user1");

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        book.setComments(comments);
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
