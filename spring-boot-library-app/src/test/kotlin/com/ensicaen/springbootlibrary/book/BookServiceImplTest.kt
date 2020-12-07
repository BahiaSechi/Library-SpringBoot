package com.ensicaen.springbootlibrary.book

import com.ensicaen.openapi.springbootlibrary.api.BookDto
import com.ensicaen.openapi.springbootlibrary.api.BookState.BORROWED
import com.ensicaen.springbootlibrary.book.exception.BookNotFoundException
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

internal class BookServiceImplTest {

    private val bookRepositoryMock: BookRepository = mockk(relaxed = true)
    private val bookService = BookServiceImpl(
        bookRepository = bookRepositoryMock,
        bookMapper = BookMapperImpl()
    )

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun create() {
        // given
        val bookDtoToCreate = BookDto(title = "BookTitle", state = BORROWED)
        val bookId = "1"
        val bookEntityCaptor = slot<BookEntity>()
        every { bookRepositoryMock.findByIdOrNull(bookId.toLong()) } returns null
        every { bookRepositoryMock.save(capture(bookEntityCaptor)) } answers { bookEntityCaptor.captured }

        // when
        val createdBookDto = bookService.create(bookDtoToCreate)

        // then
        verify(exactly = 1) { bookRepositoryMock.save(capture(bookEntityCaptor)) }

        assertThat(createdBookDto.title).isEqualTo("BookTitle")
        assertThat(createdBookDto.state).isEqualTo(BORROWED)
    }

    @Test
    fun read() {
        // given
        val bookId = "1"
        every { bookRepositoryMock.findByIdOrNull(bookId.toLong()) } returns BookEntity(title = "BookTitle", state = BORROWED)

        // when
        val bookDto = bookService.read(bookId)

        // then
        assertThat(bookDto.title).isEqualTo("BookTitle")
        assertThat(bookDto.state).isEqualTo(BORROWED)
    }

    @Test
    fun `read with bookId not found`() {
        // given
        val bookId = "1"
        every { bookRepositoryMock.findByIdOrNull(bookId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(BookNotFoundException::class.java)
            .isThrownBy { bookService.read(bookId) }
            .withMessage("Book with id 1 not found")
    }

    @Test
    fun readAll() {
        // given
        val bookEntity1 = BookEntity(title = "BookTitle1", state = BORROWED)
        val bookEntity2 = BookEntity(title = "BookTitle2", state = BORROWED)
        val expectedBookDto1 = BookDto(title = "BookTitle1", state = BORROWED)
        val expectedBookDto2 = BookDto(title = "BookTitle2", state = BORROWED)
        every { bookRepositoryMock.findAll() } returns listOf(bookEntity1, bookEntity2)

        // when
        val booksDto = bookService.readAll()

        // then
        assertThat(booksDto).containsExactlyInAnyOrder(expectedBookDto1, expectedBookDto2)
    }

    @Test
    fun delete() {
        // given
        val bookId = "1"
        val bookEntity = BookEntity(title = "BookTitle", state = BORROWED)
        every { bookRepositoryMock.findByIdOrNull(bookId.toLong()) } returns bookEntity

        // when
        bookService.delete(bookId)

        // then
        verify(exactly = 1) { bookRepositoryMock.delete(bookEntity) }
    }

    @Test
    fun `delete with bookId not found`() {
        // given
        val bookId = "1"
        every { bookRepositoryMock.findByIdOrNull(bookId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(BookNotFoundException::class.java)
            .isThrownBy { bookService.delete(bookId) }
            .withMessage("Book with id 1 not found")
    }
}