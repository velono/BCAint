package hu.bca.library.controllers;

import org.springframework.data.rest.webmvc.RepositoryRestController;

import hu.bca.library.repositories.AuthorRepository;

@RepositoryRestController("/authors")
public class AuthorController {

	private final AuthorRepository authorRepository;

	public AuthorController(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
}
