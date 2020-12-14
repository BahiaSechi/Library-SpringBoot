package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.api.BooksApi
import com.ensicaen.openapi.springbootlibrary.api.BookDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val LOGGER = LoggerFactory.getLogger(BookController::class.java)

@RestController
@RequestMapping("api/v1/")
class BookController(private val bookService: BookService) : BooksApi {

    override fun createBook(bookDto: BookDto): ResponseEntity<BookDto> {
        LOGGER.info("Request to create book {}", bookDto)
        return status(CREATED).body(bookService.create(bookDto))
    }

    override fun readBooks(): ResponseEntity<List<BookDto>> {
        LOGGER.info("Request to read all books")
        return ok(bookService.readAll())
    }

    override fun readBook(id: String): ResponseEntity<BookDto> {
        LOGGER.info("Request to read book with id {}", id)
        return ok(bookService.read(id))
    }

    override fun deleteBook(id: String): ResponseEntity<Unit> {
        LOGGER.info("Request to delete book with id {}", id)
        bookService.delete(id)
        return noContent().build()
    }
}