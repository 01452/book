package telran.java2022.book.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java2022.book.model.Author;
import telran.java2022.book.model.Book;

public interface BookRepository extends CrudRepository<Book, String> {
//	Iterable<Book> findBooksByAuthor(Author author);
}
