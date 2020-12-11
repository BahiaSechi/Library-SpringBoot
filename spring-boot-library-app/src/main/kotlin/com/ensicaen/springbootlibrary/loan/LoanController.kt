package com.ensicaen.springbootlibrary.loan

import com.ensicaen.openapi.api.LoansApi
import com.ensicaen.openapi.springbootlibrary.api.LoanDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val LOGGER = LoggerFactory.getLogger(LoanController::class.java)

@RestController
@RequestMapping("api/v1/")
class LoanController(private val loanService: LoanService) : LoansApi {

    override fun createLoan(loanDto: LoanDto): ResponseEntity<LoanDto> {
        LOGGER.info("Request to create loan {}", loanDto)
        return status(CREATED).body(loanService.create(loanDto))
    }

    override fun readUnfinishedLoans(): ResponseEntity<List<LoanDto>> {
        LOGGER.info("Request to read all unfinished loans")
        return ok(loanService.readUnfinishedLoans())
    }

    override fun readLoan(id: String): ResponseEntity<LoanDto> {
        LOGGER.info("Request to read loan with id {}", id)
        return ok(loanService.read(id))
    }

    override fun deleteLoan(id: String): ResponseEntity<Unit> {
        LOGGER.info("Request to delete loan with id {}", id)
        loanService.delete(id)
        return noContent().build()
    }

    override fun returnLoan(id: String): ResponseEntity<Unit> {
        LOGGER.info("Request to return loan with id {}", id)
        loanService.returnLoan(id)
        return noContent().build()
    }
}