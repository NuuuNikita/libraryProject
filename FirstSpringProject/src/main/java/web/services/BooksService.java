package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.Book;
import web.models.Person;
import web.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getBooks(Integer pageNumber)
    {
        return booksRepository.findAll(PageRequest.of(pageNumber, 3)).getContent();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book book) {
        Book oldBook = booksRepository.findById(id).get();
        book.setId(id);
        book.setTakenAt(oldBook.getTakenAt());
        book.setOwner(oldBook.getOwner());
        booksRepository.save(book);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }

    public Person findOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public List<Book> searchBooks(String bookName) {
        return booksRepository.findByBookNameStartingWith(bookName);
    }

    @Transactional
    public void freeBook(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setTakenAt(null);
            book.setOwner(null);
        });
    }

    @Transactional
    public void assignBook(int id, Person person) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setTakenAt(new Date());
            book.setOwner(person);
        });
    }
}
