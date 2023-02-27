package ex_002_select_where;

import ex_002_select_where.entity.Author;
import org.jboss.logging.Logger;
import java.util.List;

public class Main {

    private static final Logger LOG = Logger.getLogger(HibernateUtil.class.getName());

    public static void main(String[] args) {
        AuthorHelper ah = new AuthorHelper();
        List<Author> authors =
                ah.getAuthorListForFrase("%sil%", "%us");
        for (Author author : authors){
            System.out.println(author.getId() + " " + author.getName() + " "
            + author.getLastName());
        }

        ah.deleteCriteria("%ar%");
        ah.deleteCriteriaLogic("%ras%", "%chenko%");
    }
}
