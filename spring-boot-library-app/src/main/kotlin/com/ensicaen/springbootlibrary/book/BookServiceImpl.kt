package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import com.ensicaen.springbootlibrary.author.AuthorEntity
import com.ensicaen.springbootlibrary.author.AuthorRepository
import com.ensicaen.springbootlibrary.author.exception.AuthorNotFoundException
import com.ensicaen.springbootlibrary.book.exception.BookNotFoundException
import com.ensicaen.springbootlibrary.publisher.PublisherRepository
import com.ensicaen.springbootlibrary.publisher.exception.PublisherNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val publisherRepository: PublisherRepository,
    private val bookMapper: BookMapper,
    private val authorRepository: AuthorRepository
) : BookService {

    override fun create(bookDto: BookDto): BookDto {
        val publisherEntity = getPublisherEntity(bookDto.publisherId.toLong())
        val authorEntities: List<AuthorEntity> = bookDto.authorsId.map { getAuthorEntity(it.toLong()) }
        val bookEntityToSave = bookMapper.fromDto(bookDto, publisherEntity, authorEntities)
        val bookEntitySaved = bookRepository.save(bookEntityToSave)
        return bookMapper.fromEntity(bookEntitySaved)
    }

    private fun getPublisherEntity(publisherId: Long) =
        publisherRepository.findByIdOrNull(publisherId)
            ?: throw PublisherNotFoundException("Publisher with id $publisherId not found")

    private fun getAuthorEntity(authorId: Long) =
        authorRepository.findByIdOrNull(authorId)
            ?: throw AuthorNotFoundException("Author with id $authorId not found")

    override fun read(bookId: String): BookDto = bookMapper.fromEntity(getBookEntity(bookId.toLong()))

    override fun readAll(): List<BookDto> = bookRepository.findAll().map { bookMapper.fromEntity(it) }

    override fun delete(bookId: String) {
        bookRepository.delete(getBookEntity(bookId.toLong()))
    }

    private fun getBookEntity(bookId: Long) =
        bookRepository.findByIdOrNull(bookId)
            ?: throw BookNotFoundException("Book with id $bookId not found")
}
