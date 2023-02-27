package org.example;

import org.example.entity.Author;
import org.jboss.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {
        BookHelper helper = new BookHelper();

        Author author = new AuthorHelper().getAuthorById(1);
        helper.deleteBookForAuthor(author);

        helper.deleteBookForId(2);


    }

}
