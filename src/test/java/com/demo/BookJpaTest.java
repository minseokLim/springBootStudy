package com.demo;

import com.demo.domain.Book;
import com.demo.repository.BookRepository;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookJpaTest {

    private final static String BOOT_TEST_TITLE = "Spring Boot Test Book";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void Book_save_test() {
        Book book = Book.builder().title(BOOT_TEST_TITLE).publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book);
        Assert.assertThat(bookRepository.getOne(book.getIdx()), Matchers.is(book));
    }

    @Test
    public void BookList_save_and_test() {
        Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book1);
        Book book2 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book2);
        Book book3 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book3);

        List<Book> bookList = bookRepository.findAll();
        Assert.assertThat(bookList, Matchers.hasSize(3));
        Assert.assertThat(bookList, Matchers.contains(book1, book2, book3));
    }

    @Test
    public void BookList_save_delete_test() {
        Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book1);
        Book book2 = Book.builder().title(BOOT_TEST_TITLE + "1").publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book2);

        bookRepository.deleteAll();
        Assert.assertThat(bookRepository.findAll(), IsEmptyCollection.empty());
    }
}
