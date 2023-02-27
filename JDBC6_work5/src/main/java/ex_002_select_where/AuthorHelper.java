package ex_002_select_where;

import ex_002_select_where.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList(){
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        Selection[] selections = {root.get("id"), root.get("name")};

        cq.select(cb.construct(Author.class, selections))
                                   .where(cb.like(root.<String>get("name"), "%Push%"));

        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();
        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id); // получение объекта по id
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

    //Пошук автора по певному виразу
    public List<Author> getAuthorListForFrase(String frase, String frase2){
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        Selection[] selections = {root.get("name"), root.get("lastName")};

        cq.select(cb.construct(Author.class, selections))
                .where(cb.or(cb.like(root.<String>get("name"), frase)),
                        cb.like(root.<String>get("lastName"), frase2)
                );

        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();
        return authorList;
    }

    // Видалення за певним ім'ям автора (рег. вираз)
    // щоб переробити за прізвищем, просто зробити такий самий метод
    //тільки замість "name" - написати "last_name"
    public void deleteCriteria(String regex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);
        cd.where(cb.like(root.<String>get("name"), regex));
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    //видалення автора за кількома параметрами, з логічними виразами
    public void deleteCriteriaLogic(String regexName, String regexLastName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);
        cd.where(cb.or(
                cb.and(
                        cb.like(root.get("name"), regexName),
                        cb.like(root.get("lastName"), regexLastName)
                ),
                cb.equal(root.get("name"), regexName)
        ));
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
