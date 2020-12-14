package com.ensicaen.springbootlibrary.loan

import com.ensicaen.openapi.springbootlibrary.api.LoanDto

interface LoanService {
    fun create(loanDto: LoanDto): LoanDto
    fun read(loanId: String): LoanDto
    fun readUnfinishedLoans(): List<LoanDto>
    fun delete(loanId: String)
    fun returnLoan(loanId: String)
}