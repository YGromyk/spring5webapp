package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            PublisherRepository publisherRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Started Bootstrap");

        Publisher publisher = new Publisher();

        publisher.setAddressLine1("A line");
        publisher.setName("a publisher");
        publisher.setCity("Kyiv");
        publisher.setState("Ukraine");
        publisher.setZip("01001");

        publisherRepository.save(publisher);


        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "41");
        saveData(publisher, eric, ddd);


        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EEE Development without EJB", "2");
        saveData(publisher, rod, noEJB);


        showContentWithHeader(bookRepository, "Books");
        showContentWithHeader(authorRepository, "Authors");
        showContentWithHeader(publisherRepository, "Publishers");

        System.out.println("Finished Bootstrap");
    }

    private void saveData(Publisher publisher, Author author, Book book) {
        author.getBooks().add(book);
        book.getAuthors().add(author);
        book.setPublisher(publisher);
        publisher.getBooks().add(book);

        authorRepository.save(author);
        bookRepository.save(book);
        publisherRepository.save(publisher);
    }

    private<T> void showContentWithHeader(CrudRepository<T, Long> repository, String name) {
        System.out.println("Number of " + name + ": " + repository.count());
        repository.findAll().forEach(System.out::println);
    }
}
