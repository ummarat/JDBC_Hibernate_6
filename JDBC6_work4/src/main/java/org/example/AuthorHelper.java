package org.example;

import org.example.entity.Author;
import org.example.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class AuthorHelper {

    private static final Logger LOG = Logger.getLogger( AuthorHelper.class.getName() );
    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList(){
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        Selection[] selections = {root.get("id"), root.get("name")};

        cq.select(cb.construct(Author.class, selections));
        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id); // получение объекта по id

//        for (Book book : author.getBooks()) {
//            LOG.debug(book.getName());
//        }

        session.close();

        return author;
    }

    public Author addAuthor(Author author){

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(author); // сгенерит ID и вставит в объект

        session.getTransaction().commit();

        session.close();
        return author;

    }

        public void deleteById(long id) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Author author = session.get(Author.class, id);

        session.delete(author); // сгенерит ID и вставит в объект

        session.getTransaction().commit();

        session.close();

    }

    public void deleteCriteria()  {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);
        cd.where(cb.like(root.<String>get("name"), "%1%"));
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteCriteriaLogic()  {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);

        cd.where(cb.or(
                cb.and(
                        cb.like(root.<String>get("name"), "%author%"),
                        cb.like(root.<String>get("lastName"), "%2%")
                ),
                cb.equal(root.get("name"), "Lermontov")
        ));

        //этап выполнения запроса
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
