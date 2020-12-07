package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import com.ensicaen.springbootlibrary.book.exception.BookNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val bookMapper: BookMapper
) : BookService {

    override fun create(bookDto: BookDto): BookDto {
        val bookEntityToSave = bookMapper.fromDto(bookDto)
        val bookEntitySaved = bookRepository.save(bookEntityToSave)
        return bookMapper.fromEntity(bookEntitySaved)
    }

    override fun read(bookId: String): BookDto = bookMapper.fromEntity(getBookEntity(bookId.toLong()))

    override fun readAll(): List<BookDto> = bookRepository.findAll().map { bookMapper.fromEntity(it) }

    override fun delete(bookId: String) {
        bookRepository.delete(getBookEntity(bookId.toLong()))
    }

    private fun getBookEntity(bookId: Long) =
        bookRepository.findByIdOrNull(bookId)
            ?: throw BookNotFoundException("Book with id $bookId not found")
}
