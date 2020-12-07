package com.ensicaen.springbootlibrary.loan

import com.ensicaen.openapi.springbootlibrary.api.LoanDto
import com.ensicaen.springbootlibrary.book.BookEntity
import org.springframework.stereotype.Service

@Service
class LoanMapperImpl : LoanMapper {

    override fun fromDto(loanDto: LoanDto, bookEntity: BookEntity) =
        LoanEntity(
            clientId = loanDto.clientId,
            book = bookEntity,
            durationString = loanDto.duration
        )

    override fun fromEntity(loanEntity: LoanEntity) =
        LoanDto(
            id = loanEntity.id?.toBigDecimal(),
            clientId = loanEntity.clientId,
            bookId = loanEntity.book.id!!.toBigDecimal(),
            startDatetime = loanEntity.startDatetime,
            returnDatetime = loanEntity.returnDatetime,
            endDatetime = loanEntity.endDatetime
        )
}