package hu.bca.library.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import hu.bca.library.models.Book;

public interface BookService {
	Book addAuthor(Long bookId, Long authorId);

	void addFirstPublishDateToAllBooks();

	Book addFirstPublishDate(Long bookId, String workId);

	String findPublishDateByWorkId(String workId);

	List<Book> getSpecificUKBooks();

	

}
