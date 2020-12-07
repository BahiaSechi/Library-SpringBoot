package com.ensicaen.springbootlibrary.loan

import com.ensicaen.openapi.springbootlibrary.api.LoanDto
import com.ensicaen.springbootlibrary.book.BookEntity

interface LoanMapper {
    fun fromDto(loanDto: LoanDto, bookEntity: BookEntity): LoanEntity
    fun fromEntity(loanEntity: LoanEntity): LoanDto
}


