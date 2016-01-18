package ua.softserve.bandr.ejb.facades;

import com.google.common.base.Optional;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.cfg.NotYetImplementedException;
import ua.softserve.bandr.entity.Author;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AuthorFacade {
    @Inject
    private QueryManager<Author> authorQueryManager;
    public List<Author> getAll(){
        return null;
        //return authorQueryManager.executeQuery("Author.getByLastName", Author.class);
    }

    public List<Author> getByLastName(String lastName) {
        return null;
//        return authorQueryManager.executeQuery("Author.getByLastName", Author.class,
//                Pair.of("lastName", lastName));
    }

    public List<Author> getPagedFilteredSorted(Optional<Integer> startWith, Optional<Integer> pageSize,
                                               Optional<String> filterText, Optional<Author.AuthorSorting> sorting){
        throw new NotYetImplementedException();
    }

}
