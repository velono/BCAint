package hu.bca.library.controllers;

import java.util.List;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import hu.bca.library.models.Book;
import hu.bca.library.services.BookService;

@RepositoryRestController("books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@ResponseStatus(HttpStatus.CREATED)

	@RequestMapping("/{bookId}/add_author/{authorId}")
	@ResponseBody
	Book addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
		return this.bookService.addAuthor(bookId, authorId);
	}

	@RequestMapping("/update-all-with-year")
	ResponseEntity<String> updatePublishDates() {
		bookService.addFirstPublishDateToAllBooks();
		return new ResponseEntity<String>("Updated.", HttpStatus.OK);
	}

	@RequestMapping("/query/{country}")
	ResponseEntity<List<Book>> getBooksFromCountry(@PathVariable String country,
			@RequestParam(required = false) Integer from, @RequestParam(required = false) Integer to) {
		List<Book> result = bookService.getSpecificBooks(country, from, to);
		return new ResponseEntity<List<Book>>(result, HttpStatus.OK);
	}
}
