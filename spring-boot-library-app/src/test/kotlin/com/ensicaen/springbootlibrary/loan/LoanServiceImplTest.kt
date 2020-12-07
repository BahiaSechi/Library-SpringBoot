package com.ensicaen.springbootlibrary.loan

import com.ensicaen.openapi.springbootlibrary.api.BookState
import com.ensicaen.openapi.springbootlibrary.api.BookState.*
import com.ensicaen.openapi.springbootlibrary.api.LoanDto
import com.ensicaen.springbootlibrary.book.BookEntity
import com.ensicaen.springbootlibrary.book.BookRepository
import com.ensicaen.springbootlibrary.book.exception.BookNotFoundException
import com.ensicaen.springbootlibrary.loan.exception.BookNotAvailableException
import com.ensicaen.springbootlibrary.loan.exception.LoanNotFoundException
import com.ensicaen.springbootlibrary.publisher.PublisherEntity
import io.mockk.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal.ONE
import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import java.time.temporal.ChronoUnit.MINUTES

internal class LoanServiceImplTest {

    private val loanRepositoryMock: LoanRepository = mockk(relaxed = true)
    private val bookRepositoryMock: BookRepository = mockk(relaxed = true)
    private val loanService = LoanServiceImpl(
        loanRepository = loanRepositoryMock,
        bookRepository = bookRepositoryMock,
        loanMapper = LoanMapperImpl()
    )

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `create with default duration`() {
        // given
        val loanDtoToCreate = createLoanDto()
        val bookEntity = createBookEntity()
        val loanEntityCaptor = slot<LoanEntity>()
        every { loanRepositoryMock.findByIdOrNull(1) } returns null
        every { loanRepositoryMock.save(capture(loanEntityCaptor)) } answers { loanEntityCaptor.captured }
        every { bookRepositoryMock.findByIdOrNull(1) } returns bookEntity

        // when
        val createdLoanDto = loanService.create(loanDtoToCreate)

        // then
        verify(exactly = 1) { loanRepositoryMock.save(capture(loanEntityCaptor)) }

        assertThat(createdLoanDto.bookId).isEqualTo(ONE)
        assertThat(createdLoanDto.clientId).isEqualTo("1")
        assertThat(createdLoanDto.startDatetime).isCloseToUtcNow(byLessThan(1, MINUTES))
        assertThat(createdLoanDto.endDatetime).isEqualTo(createdLoanDto.startDatetime?.plusDays(15))
        assertThat(createdLoanDto.duration).isNull()
        assertThat(createdLoanDto.returnDatetime).isNull()
    }

    @Test
    fun `create with custom duration`() {
        // given
        val loanDtoToCreate = LoanDto(bookId = ONE, clientId = "1", duration = "PT48H")
        val bookEntity = createBookEntity()
        val loanEntityCaptor = slot<LoanEntity>()
        every { loanRepositoryMock.findByIdOrNull(1) } returns null
        every { loanRepositoryMock.save(capture(loanEntityCaptor)) } answers { loanEntityCaptor.captured }
        every { bookRepositoryMock.findByIdOrNull(1) } returns bookEntity

        // when
        val createdLoanDto = loanService.create(loanDtoToCreate)

        // then
        verify(exactly = 1) { loanRepositoryMock.save(capture(loanEntityCaptor)) }

        assertThat(createdLoanDto.bookId).isEqualTo(ONE)
        assertThat(createdLoanDto.clientId).isEqualTo("1")
        assertThat(createdLoanDto.startDatetime).isCloseToUtcNow(byLessThan(1, MINUTES))
        assertThat(createdLoanDto.endDatetime).isEqualTo(createdLoanDto.startDatetime?.plusDays(2))
        assertThat(createdLoanDto.duration).isNull()
        assertThat(createdLoanDto.returnDatetime).isNull()
    }

    @Test
    fun `create with book not available`() {
        // given
        val loanDtoToCreate = createLoanDto()
        val bookEntity = createBookEntity(bookState = UNAVAILABLE)
        every { bookRepositoryMock.findByIdOrNull(1) } returns bookEntity

        // when and then
        assertThatExceptionOfType(BookNotAvailableException::class.java)
            .isThrownBy { loanService.create(loanDtoToCreate) }
            .withMessage(
                "Book with id 1 cannot be borrowed because " +
                    "it's not available. Current state = UNAVAILABLE"
            )
    }

    @Test
    fun `create with book not found`() {
        // given
        val loanDtoToCreate = createLoanDto()
        every { bookRepositoryMock.findByIdOrNull(1) } returns null

        // when and then
        assertThatExceptionOfType(BookNotFoundException::class.java)
            .isThrownBy { loanService.create(loanDtoToCreate) }
            .withMessage("Book with id 1 not found")
    }

    @Test
    fun read() {
        // given
        val loanId = "1"
        val bookEntity = createBookEntity(bookState = BORROWED)
        val loanEntity = createLoanEntity(bookEntity = bookEntity)
        every { loanRepositoryMock.findByIdOrNull(loanId.toLong()) } returns loanEntity

        // when
        val loanDto = loanService.read(loanId)

        // then
        assertThat(loanDto.bookId).isEqualTo(ONE)
        assertThat(loanDto.clientId).isEqualTo("1")
        assertThat(loanDto.startDatetime).isCloseToUtcNow(byLessThan(1, MINUTES))
        assertThat(loanDto.endDatetime).isEqualTo(loanDto.startDatetime?.plusDays(15))
        assertThat(loanDto.duration).isNull()
        assertThat(loanDto.returnDatetime).isNull()
    }

    @Test
    fun `read with loanId not found`() {
        // given
        val loanId = "1"
        every { loanRepositoryMock.findByIdOrNull(loanId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(LoanNotFoundException::class.java)
            .isThrownBy { loanService.read(loanId) }
            .withMessage("Loan with id 1 not found")
    }

    @Test
    fun readUnfinishedLoans() {
        // given
        val loanEntity1 = createLoanEntity()
        val loanEntity2 = createLoanEntity(bookEntity = createBookEntity(bookId = 2), clientId = "2")
        val finishedLoanEntity = createLoanEntity(bookEntity = createBookEntity(bookId = 3), clientId = "2", returnDatetime = now())
        every { loanRepositoryMock.findAll() } returns listOf(loanEntity1, loanEntity2, finishedLoanEntity)

        // when
        val loansDto = loanService.readUnfinishedLoans()

        // then
        assertThat(loansDto.size).isEqualTo(2)

        val loansDtoSortedByClientId = loansDto.sortedBy { it.clientId }

        val firstLoanDto = loansDtoSortedByClientId.component1()
        assertThat(firstLoanDto.bookId).isEqualTo(ONE)
        assertThat(firstLoanDto.clientId).isEqualTo("1")
        assertThat(firstLoanDto.startDatetime).isCloseToUtcNow(byLessThan(1, MINUTES))
        assertThat(firstLoanDto.endDatetime).isEqualTo(firstLoanDto.startDatetime?.plusDays(15))
        assertThat(firstLoanDto.duration).isNull()
        assertThat(firstLoanDto.returnDatetime).isNull()

        val secondLoanDto = loansDtoSortedByClientId.component2()
        assertThat(secondLoanDto.bookId).isEqualTo(2.toBigDecimal())
        assertThat(secondLoanDto.clientId).isEqualTo("2")
        assertThat(secondLoanDto.startDatetime).isCloseToUtcNow(byLessThan(1, MINUTES))
        assertThat(secondLoanDto.endDatetime).isEqualTo(secondLoanDto.startDatetime?.plusDays(15))
        assertThat(secondLoanDto.duration).isNull()
        assertThat(secondLoanDto.returnDatetime).isNull()
    }

    @Test
    fun delete() {
        // given
        val loanId = "1"
        val loanEntity = createLoanEntity(bookEntity = createBookEntity(bookState = BORROWED))
        val loanEntityCaptor = slot<LoanEntity>()
        every { loanRepositoryMock.findByIdOrNull(loanId.toLong()) } returns loanEntity
        every { loanRepositoryMock.save(capture(loanEntityCaptor)) } answers { loanEntityCaptor.captured }

        // when
        loanService.delete(loanId)

        // then
        verify(exactly = 1) { loanRepositoryMock.save(capture(loanEntityCaptor)) }
        assertThat(loanEntityCaptor.captured.book.state).isEqualTo(AVAILABLE)
        verify(exactly = 1) { loanRepositoryMock.delete(loanEntity) }
    }

    @Test
    fun `delete with loanId not found`() {
        // given
        val loanId = "1"
        every { loanRepositoryMock.findByIdOrNull(loanId.toLong()) } returns null

        // when and then
        assertThatExceptionOfType(LoanNotFoundException::class.java)
            .isThrownBy { loanService.delete(loanId) }
            .withMessage("Loan with id 1 not found")
    }
}

internal fun createPublisherEntity() = PublisherEntity(name = "PublisherName").apply { id = 1 }

internal fun createBookEntity(
    bookId: Long = 1,
    bookState: BookState = AVAILABLE,
    publisherEntity: PublisherEntity = createPublisherEntity()
) = BookEntity(title = "BookTitle", state = bookState, publisher = publisherEntity).apply { id = bookId }

internal fun createLoanDto() = LoanDto(bookId = ONE, clientId = "1")

internal fun createLoanEntity(
    bookEntity: BookEntity = createBookEntity(),
    clientId: String = "1",
    returnDatetime: OffsetDateTime? = null
) = LoanEntity(book = bookEntity, clientId = clientId, returnDatetime = returnDatetime)

