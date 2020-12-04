package com.ensicaen.springbootlibrary.author

import com.ensicaen.openapi.api.AuthorsApi
import com.ensicaen.openapi.springbootlibrary.api.AuthorDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val LOGGER = LoggerFactory.getLogger(AuthorController::class.java)

@RestController
@RequestMapping("api/v1/")
class AuthorController(private val authorService: AuthorService) : AuthorsApi {

    override fun createAuthor(authorDto: AuthorDto): ResponseEntity<AuthorDto> {
        LOGGER.info("Request to create author {}", authorDto)
        return status(CREATED).body(authorService.create(authorDto))
    }

    override fun readAuthors(): ResponseEntity<List<AuthorDto>> {
        LOGGER.info("Request to read all authors")
        return ok(authorService.readAll())
    }

    override fun readAuthor(id: String): ResponseEntity<AuthorDto> {
        LOGGER.info("Request to read author with id {}", id)
        return ok(authorService.read(id))
    }

    override fun deleteAuthor(id: String): ResponseEntity<Unit> {
        LOGGER.info("Request to delete author with id {}", id)
        authorService.delete(id)
        return noContent().build()
    }
}