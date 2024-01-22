package hu.bca.library.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import hu.bca.library.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	List<Book> findByAuthorsCountryAndYearGreaterThanEqualOrderByYearDesc(String country, Integer year,
			Pageable pageable);

//	List<Book> findByAuthorsCountryAndYearGreaterThanEqualOrderByYearDesc(String country, Optional<Integer> fromYear, Pageable pageable);

	List<Book> findByAuthorsCountry(String string, PageRequest pageable);

}
