package org.example;

import org.example.entity.Author;
import org.example.entity.Book;
import org.jboss.logging.Logger;

import java.util.List;

public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {
        AuthorHelper ah = new AuthorHelper();

        ah.updateName();
    }
}
