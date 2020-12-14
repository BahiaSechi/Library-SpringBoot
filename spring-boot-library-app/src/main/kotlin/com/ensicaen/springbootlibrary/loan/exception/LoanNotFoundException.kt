package com.ensicaen.springbootlibrary.loan.exception

import com.ensicaen.openapi.springbootlibrary.api.ErrorCode._05
import com.ensicaen.springbootlibrary.exception.SpringBootLibraryException
import org.springframework.http.HttpStatus.NOT_FOUND

class LoanNotFoundException(message: String) : SpringBootLibraryException(
    message = message,
    httpStatus = NOT_FOUND,
    errorCode = _05
)

