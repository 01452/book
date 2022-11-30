package telran.java2022.book.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java2022.book.dao.AuthorRepository;
import telran.java2022.book.dao.BookRepository;
import telran.java2022.book.dao.PublisherRepository;
import telran.java2022.book.dto.AuthorDto;
import telran.java2022.book.dto.BookDto;
import telran.java2022.book.exceptions.EntityNotFoundException;
import telran.java2022.book.model.Author;
import telran.java2022.book.model.Book;
import telran.java2022.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(p -> authorRepository.findById(p.getName())
						.orElse(authorRepository.save(new Author(p.getName(), p.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		bookRepository.delete(book);
		Set<AuthorDto> authors = book.getAuthors().stream().map(p -> new AuthorDto(p.getName(), p.getBirthDate()))
				.collect(Collectors.toSet());
		return new BookDto(book.getIsbn(), book.getTitle(), authors, book.getPublisher().getPublisherName());
	}

	@Override
	@Transactional
	public BookDto updateBook(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new EntityNotFoundException());
		book.setTitle(title);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		//TODO
//		Author author = authorRepository.findById(authorName).orElseThrow(() -> new EntityNotFoundException());
//		Iterable<Book> books = bookRepository.findBooksByAuthor(author);
//		return StreamSupport.stream(books.spliterator(), false)
//				.map(p -> new BookDto(p.getIsbn(),p.getTitle(), p.getAuthors().stream().map(g -> new AuthorDto(g.getName(), g.getBirthDate()))
//						.collect(Collectors.toSet()),
//						p.getPublisher().getPublisherName()))
//				.collect(Collectors.toList());
		return null;
	}

	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<AuthorDto> findBookAuthors(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<String> findPublishersByAuthor(String authorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		//TODO
		Author author = authorRepository.findById(authorName).orElseThrow(() -> new EntityNotFoundException());
		authorRepository.delete(author);
		return modelMapper.map(author, AuthorDto.class);
	}

}
