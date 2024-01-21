package hu.bca.library.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import hu.bca.library.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	// @Query(value = "SELECT a.books from Author where country = :country ")
//	List<Book> getBooksFromCountryNotOlderThan(String country, Long from);

	
	
	List<Book> findByAuthorsCountryAndYearLessThanEqualOrderByYearDesc(String country, Integer year, Pageable pageable);

//	List<Book> findByAuthorsCountryAndYearLessThanEqualOrderByYearDesc(String country, Optional<Integer> fromYear, Pageable pageable);


}
