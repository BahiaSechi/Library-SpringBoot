package com.ensicaen.springbootlibrary.loan

import com.ensicaen.openapi.springbootlibrary.api.BookState.AVAILABLE
import com.ensicaen.openapi.springbootlibrary.api.BookState.BORROWED
import com.ensicaen.openapi.springbootlibrary.api.LoanDto
import com.ensicaen.springbootlibrary.book.BookEntity
import com.ensicaen.springbootlibrary.book.BookRepository
import com.ensicaen.springbootlibrary.book.exception.BookNotFoundException
import com.ensicaen.springbootlibrary.loan.exception.BookNotAvailableException
import com.ensicaen.springbootlibrary.loan.exception.LoanNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoanServiceImpl(
    private val loanRepository: LoanRepository,
    private val bookRepository: BookRepository,
    private val loanMapper: LoanMapper
) : LoanService {

    override fun create(loanDto: LoanDto): LoanDto {
        val bookEntity = getBookEntity(loanDto.bookId.toLong())
        assertThatBookIsAvailable(bookEntity)
        bookEntity.state = BORROWED
        val loanEntityToSave = loanMapper.fromDto(loanDto, bookEntity)
        val loanEntitySaved = loanRepository.save(loanEntityToSave)
        return loanMapper.fromEntity(loanEntitySaved)
    }

    private fun assertThatBookIsAvailable(bookEntity: BookEntity) {
        if (bookEntity.state != AVAILABLE) {
            throw BookNotAvailableException(
                "Book with id ${bookEntity.id} cannot be borrowed because " +
                    "it's not available. Current state = ${bookEntity.state}"
            )
        }
    }

    private fun getBookEntity(bookId: Long) =
        bookRepository.findByIdOrNull(bookId)
            ?: throw BookNotFoundException("Book with id $bookId not found")

    override fun read(loanId: String): LoanDto {
        val loanEntity = getLoanEntity(loanId.toLong())
        return loanMapper.fromEntity(loanEntity)
    }

    override fun readUnfinishedLoans(): List<LoanDto> =
        loanRepository.findAll()
            .filter { it.returnDatetime == null }
            .map { loanMapper.fromEntity(it) }

    override fun delete(loanId: String) {
        val loanEntity = getLoanEntity(loanId.toLong())
        loanEntity.book.state = AVAILABLE
        loanRepository.save(loanEntity)
        loanRepository.delete(loanEntity)
    }

    private fun getLoanEntity(loanId: Long) =
        loanRepository.findByIdOrNull(loanId)
            ?: throw LoanNotFoundException("Loan with id $loanId not found")
}
