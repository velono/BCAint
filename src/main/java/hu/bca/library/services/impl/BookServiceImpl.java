package hu.bca.library.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.bca.library.models.Author;
import hu.bca.library.models.Book;
import hu.bca.library.repositories.AuthorRepository;
import hu.bca.library.repositories.BookRepository;
import hu.bca.library.services.BookService;

@Service
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}

	@Override
	public Book addAuthor(Long bookId, Long authorId) {
		Optional<Book> book = this.bookRepository.findById(bookId);
		if (book.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", bookId));
		}
		Optional<Author> author = this.authorRepository.findById(authorId);
		if (author.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Author with id %s not found", authorId));
		}

		List<Author> authors = book.get().getAuthors();
		authors.add(author.get());

		book.get().setAuthors(authors);
		return this.bookRepository.save(book.get());
	}

	
	////////////////////////////
	@Override
	public void addFirstPublishDateToAllBooks() {	// This method iterates through all books.
		Iterable<Book> allBooks = this.bookRepository.findAll();

		for (Book book : allBooks) {
			Long bookId = book.getId();
			String workId = book.getWorkId();
			
			addFirstPublishDate(bookId, workId);
		}
	}

	@Override
	public Book addFirstPublishDate(Long bookId, String workId) {		// this method updates each book with publish date.
		Optional<Book> book = this.bookRepository.findById(bookId);
		if (book.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", bookId));
		}
		book.get().setFirst_publish_date(findPublichDateByWorkId(workId));
		
		return this.bookRepository.save(book.get());
	}
	

	@Override
	public String findPublichDateByWorkId(String workId) {	// This method goes to the internet to find the date to add.
		return "1990-jan-01";
	}

}
