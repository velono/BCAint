package hu.bca.library.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.bca.library.models.Author;
import hu.bca.library.models.Book;
import hu.bca.library.repositories.AuthorRepository;
import hu.bca.library.repositories.BookRepository;
import hu.bca.library.services.BookService;

@Service
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	private final ObjectMapper objectMapper;

	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
			ObjectMapper objectMapper) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;

		this.objectMapper = objectMapper;
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


	@Override
	public void addFirstPublishDateToAllBooks() { 
		Iterable<Book> allBooks = this.bookRepository.findAll();

		for (Book book : allBooks) {
			Long bookId = book.getId();
			String workId = book.getWorkId();

			addFirstPublishDate(bookId, workId);
		}
	}

	@Override
	public Book addFirstPublishDate(Long bookId, String workId) { 
		Optional<Book> book = this.bookRepository.findById(bookId);
		if (book.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book with id %s not found", bookId));
		}
		String firstPublishDate = findPublichDateByWorkId(workId);
		book.get().setFirst_publish_date(firstPublishDate);

		System.out.println(workId + ": " + firstPublishDate);
		return this.bookRepository.save(book.get());
	}

	@Override
	public String findPublichDateByWorkId(String workId) { 
		String firstPublishedOn = "";
		final String uri = "https://openlibrary.org/works/" + workId + ".json";
		RestTemplate restTemplate = new RestTemplate();
		String jsonResponse = restTemplate.getForObject(uri, String.class);
		try {
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			firstPublishedOn = jsonNode.at("/first_publish_date").asText();
			return firstPublishedOn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
