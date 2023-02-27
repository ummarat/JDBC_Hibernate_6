package org.example;

import org.example.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import javax.persistence.Query;
import javax.persistence.criteria.*;

public class AuthorHelper {

    private static final Logger LOG = Logger.getLogger( AuthorHelper.class.getName() );
    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void updateName() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Author> cd = cb.createCriteriaUpdate(Author.class);
        Root<Author> root = cd.from(Author.class);
        cd= cd.set(root.get("name"), "1").where(cb.like (root.<String>get("lastName" ), "%_______" ));
        Query query = session.createQuery(cd);
        int updateValues = query.executeUpdate();
        System.out.println("Оновлено записів: " + updateValues);
        session.getTransaction().commit();
        session.close();
    }

}
